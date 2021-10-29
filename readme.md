### How to launch:
1. Docker-compose in the root where API app is bind to port 8080 and database to port 5432
   1. Command: todo
2. Alternatively, you may build your own image via Dockerfile and connect to your chosen database
   1. Command: todo
3. Or launch directly from the sources, again, with your own chosen database:
   1. `./gradlew bootRun`

Database is managed with liquibase, therefore in case of options 2 or 3, you just have to override the default connection properties with variables described below.

There is a prepared parking with uid `0c76420d-3e17-48c6-ac50-cf89b64d1e31` with two floors: 
(height: 3.0, weight_capacity: 250), 
(height: 5.0, weight_capacity: 500), 
two free parking lots each.

### Important environment variables to override the default settings:
todo

### Swagger:
todo

### Description:
* Entities:
  - Parking - the parking building itself
  - Parking floor - parking consists of floors
  - Parking lot - parking floor consists of lots, may be occupied by a vehicle
  - Vehicle - incoming vehicle that occupies the lot, consists merely of weight and height
  - Parking ticket - the main part of this task. Calculated on `POST /api/v1/parking/ticket/{parkingUid}?strategy=`, described below

* Parking ticket pricing
  - First, you have to pick a parking and pass it's uid to the url above as path variable. 
  - Second, in the request body, you have to pass mandatory vehicle description: weight and height. These two don't participate in the calculation process, but there's a check whether parking has any floor that can place the described vehicle.
  - Third, you may pass an optional request parameter `strategy` with value of `[default, floor_occupancy]`. Default will just respond with default price that is set in the properties, floor occupancy will add the floor occupancy coefficient to the default price.

P.S. Currently, there are no limitations neither regarding the amount of parking floors at parking building 
nor parking lots at floor, they're created on demand, so effectively every parking is infinite. Not a bug, just a feature. :)
Also, entities may contain excessive fields that I simply found appropriate beforehand but haven't implemented in any way. 

### Task:
The task is about autonomous parking lot. You should design and implement a backend system with
API to handle a parking lot.

Any client car, when arrives at the parking building, gets automatically scanned and its weight and
height are passed to the system as the car approaches the gate. The parking has N floors, where each
floor may have a different ceiling height and a different total weight capacity. The system must assign
the approaching car to the best suitable spot and calculate the resulting price per minute. The
payment, car transportation, and the rest is handled by other components, and you can simply
emulate them to build a business flow.

There are only few initial business requirements in that system, but it should be easily extensible. Try
to keep your code as readable as possible. We value code simplicity. Use object-oriented approach
with common design patterns where applicable.
