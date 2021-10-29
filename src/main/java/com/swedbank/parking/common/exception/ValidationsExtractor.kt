package com.swedbank.parking.common.exception

import com.fasterxml.jackson.databind.JsonMappingException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

object ValidationsExtractor {
    fun extract(ex: MethodArgumentNotValidException): List<ValidationError> {
        return extract(ex.bindingResult)
    }

    fun extract(ex: BindException): List<ValidationError> {
        return extract(ex.bindingResult)
    }

    fun extract(ex: ConstraintViolationException): List<ValidationError> {
        return ex.constraintViolations.map { violationToValidation(it) }
    }

    fun extract(ex: MissingServletRequestParameterException): List<ValidationError> {
        return listOf(ValidationError(ex.parameterName, ex.message))
    }

    fun extract(ex: MethodArgumentTypeMismatchException): List<ValidationError> {
        val requiredType = ex.requiredType?.name ?: "unknown type"
        return listOf(ValidationError(ex.name, ex.name + " should be of type " + requiredType))
    }

    private fun extract(bindingResult: BindingResult): List<ValidationError> {
        val errors = bindingResult.globalErrors.map { objectErrorToValidation(it) }.toMutableList()
        val fieldErrors = bindingResult.fieldErrors.map { fieldErrorToValidation(it) }
        errors.addAll(fieldErrors)
        return errors
    }

    fun extract(ex: HttpMessageNotReadableException): List<ValidationError> {
        val validationErrors = mutableListOf<ValidationError>()
        if (ex.cause is JsonMappingException) {
            validationErrors.add(
                mappingExceptionToValidation(ex.cause as JsonMappingException)
            )
        }
        return validationErrors
    }

    private fun mappingExceptionToValidation(e: JsonMappingException): ValidationError {
        val fieldPath = e.path
        if (fieldPath != null && fieldPath.size > 0) {
            val pathBuilder = StringBuilder()
            pathBuilder.append(jacksonReferenceToField(fieldPath[0], false))
            fieldPath.drop(1)
                .map { jacksonReferenceToField(it, true) }
                .forEach { pathBuilder.append(it) }
            return ValidationError(pathBuilder.toString(), "Incorrect request parameter value.")
        }
        return ValidationError("", e.originalMessage)
    }

    private fun jacksonReferenceToField(
        reference: JsonMappingException.Reference?,
        prefixFieldNameWithDot: Boolean
    ): String {
        if (reference == null) {
            return ""
        }
        return if (reference.fieldName == null) {
            "[" + reference.index + "]"
        } else if (prefixFieldNameWithDot) {
            "." + reference.fieldName
        } else {
            reference.fieldName
        }
    }

    private fun objectErrorToValidation(objectError: ObjectError): ValidationError {
        return ValidationError("", objectError.defaultMessage ?: "")
    }

    private fun fieldErrorToValidation(fieldError: FieldError): ValidationError {
        return ValidationError(fieldError.field, fieldError.defaultMessage ?: "")
    }

    private fun violationToValidation(violation: ConstraintViolation<*>): ValidationError {
        val field = cutField(violation.propertyPath.toString())
        return ValidationError(field, violation.message)
    }

    private fun cutField(fieldPath: String): String {
        return try {
            val fieldPathItems = fieldPath.split("\\.").toTypedArray()
            fieldPathItems[fieldPathItems.size - 1]
        } catch (e: Exception) {
            // ignore exception
            fieldPath
        }
    }
}
