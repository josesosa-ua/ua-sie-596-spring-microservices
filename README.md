# Textbook: Spring Microservices in Action

This is a project to follow the SIE 596 Cloud Native Software Engineering textbook:

Spring Microservices in Action, Second Edition by John Carnell

# Usage
Each chapter of the book will have its own branch on this project. Chapter branches will not be merged with main.
If you want to follow the code for a specific chapter, then got to the appropriate branch. Main branch will only contain readmes, general git configurations and other general files.

# Running the application
## Pre-requisites
- [Install Postman](https://www.postman.com/downloads/)
- [Install Docker](https://www.docker.com/get-started/)

## Testing with Postman
1. Open [Postman](https://learning.postman.com/docs/introduction/overview/).
1. Go to your collections and import `./http-tests/postma-cllection.json` into your Postman/Insomnia collections.
1. Run the collection.

# Start the Application in a Container
1. Clone the repository.
1. Build the project for good measure:
    ```sh
    ./gradlew clean build
    ```
1. Build the Licensing Service image:
    ```sh
    ./gradlew docker
    ```
1. Start the containers
    ```sh
    docker-compose up -d
    ```
