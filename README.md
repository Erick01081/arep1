# Simple Web Server

This project implements a simple web server in Java that supports concurrent requests and can serve various file types, including HTML, JavaScript, CSS, and images. Additionally, the project includes a web application that demonstrates asynchronous communication with REST services on the backend.

## Getting Started

These instructions will help you set up the project on your local machine for development and testing purposes.

## Prerequisites

To run this project, you'll need the following:

Java Development Kit (JDK) version 8 or higher
Maven (for building and managing dependencies)

## Installing

1. Clone the repository to your local machine:

```
git clone https://github.com/Erick01081/arep1.git
```

2. Navigate to the project directory:

```
cd simple-web-server
```

3. Build the project using Maven:

```
mvn clean install
```

This will compile the Java code and generate the necessary artifacts.

## Running the Server

To run the web server, execute the following command:

```
java -cp target/ASE-1.0-SNAPSHOT.jar edu.escuelaing.arep.ASE.app.SimpleWebServer
```

This will start the web server on port 8080.

Running the Web Application

To test the web application, open a web browser and navigate to http://localhost:8080/index.html. You should see the form-based web application, which includes the following features:

1. Form with GET: This form submits a GET request to the server with a parameter, and the server's response is displayed. The purpose of this GET function is to retrieve a static file using the provided filename and display it on the screen.
2. Form with POST: This form allows you to select a file and submit it to the server. The server will save the file and display a response. The function of the POST method is to accept a file and a name, and then create a static file with the given information.

## Prototype Architecture
The prototype is composed of the following main components:

1. **SimpleWebServer**: The main class that starts the server and handles incoming connections.
2. **ClientHandler**: A class that implements Runnable to manage each client request in a separate thread.
3. **GET and POST Request Handlers**: Methods within ClientHandler that process GET and POST requests respectively.
4. **Frontend Web Application**: An HTML page with JavaScript that allows users to interact with the server.

## Evaluation and Testing

The following manual tests were conducted to verify the functionality of the server:

1. **File Type Test**: Different file types (HTML, CSS, JavaScript, images) were requested to ensure that the server serves them correctly.

2. **GET Form Test**: The GET form in the web application was used to send requests to the server and verify the responses.

3. **POST File Upload Test**: The file upload functionality was tested using the POST form in the web application.

4. **Persistence Test**: It was verified that files uploaded via POST were correctly saved on the server and could be accessed later.

5. **Error Handling Test**: Requests for non-existent files were made to check the proper handling of 404 errors.

All these tests were conducted manually using web browsers and tools like Postman to simulate different types of requests.

## Deployment

To deploy the web server on a live system, you can package the application as a JAR file and run it using the same command as in the "Running the Server" section.

## Built With

* Java 22
* org.json library for parsing JSON
* HTTP server implementation using Java's built-in networking libraries

## Authors

Erick Montero
