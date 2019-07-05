# Insurance Processing Demo: Spring Integration Java DSL

The intention of this demo implementation is to realize a scenario where a
client application is able to establish an insurance contract (processed by a insurance service application)
through an integration layer.
  

## Repo Content

This repo contents:

* The Integration-Layer application: This application leverages `Spring Integration` capabilities 
to create a facade between the client application and the insurance processing service (which may be legacy).
It exposes to REST endpoints for establishing an insurance contract and for retrieving details about a specific
insurance contract

* The Client Application: This is a REST client that consumes the API exposed by the integration layer. Implemented
not as a running application, but rather a client library. 

## Run the Sample

* You need Java 8 to run this demo.
* running the `no.fremtind.insurance.integrationlayer.IntegrationLayerApplication` class from within IntelliJ IDEA 
* or from the command line:

    $ ./mvnw spring-boot:run

The insurance processing service application is implemented in a separate repository (`https://github.com/talk2jk/demo-ensurance-service`).
It's a Spring based REST API application that should be ran separately.
