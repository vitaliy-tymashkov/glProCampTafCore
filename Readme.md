# Gl Procamp Testing Framework

## Manual

How to run tests:

1. Execute test directly "src/main/java/com/gl/procamp/tests/functional/smoke/LoginTest.java"

2. Execute Test Suite "src/main/resources/testngSmokeTest.xml"

### Tasks:

1. Create framework structure - [x]

1.1. It shall contain folders for

1.1.1. configuration - [x]

1.1.2. application specific libraries/helpers - [x]

1.1.3. app api clients - [x]

1.1.4. page objects - [x]

1.1.5. tests - [x]

2. Create Config class - [x]

2.1. It shall be possible to set config variables via Environment variables - [x]

2.2. It shall be possible to set config variables via YAML file - [x]

2.3. It shall be possible to set config variables inside Config class - [x]

3. Create class for sending HTTP requests - [x]

3.1. It shall be able to send requests:
- GET - [x]
- PUT - [x] 
- POST - [x]
- DELETE - [x]
