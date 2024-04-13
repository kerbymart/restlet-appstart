# Restlet AppStart

Restlet AppStart is a web application designed to run on Google App Engine. It leverages the Restlet framework to provide a lightweight, RESTful web API. The application includes Swagger UI for API interaction and testing, and is built using Java with Maven for dependency management.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 8 or higher
- Apache Maven

### Installation

1. Clone the repository:

```bash
git clone https://github.com/kerbymart/restlet-appstart.git
```

2. Navigate to the project directory:

```bash
cd restlet-appstart
```

3. Build the project with Maven:

```bash
mvn clean install
```

### Running Locally

To run the application locally using Maven:

```bash
mvn appengine:devserver
```

The application should now be running on `http://localhost:8080`.

## Accessing the Swagger UI

To access the Swagger UI and interact with the API:

1. Ensure the application is running (as per the above instructions).
2. Open a web browser and navigate to:

```
http://localhost:8080/ui/
```

3. You can now explore and test the API endpoints.

## Deployment

To deploy the application to Google App Engine:

```bash
mvn appengine:deploy
```

Follow the prompts to authenticate and deploy the application to your Google Cloud project.

## Built With

- [Java](https://www.java.com) - The programming language used
- [Maven](https://maven.apache.org/) - Dependency Management
- [Restlet Framework](https://restlet.talend.com/) - Used to create the RESTful web service
- [Swagger UI](https://swagger.io/tools/swagger-ui/) - Used for API documentation and testing
- [Google App Engine](https://cloud.google.com/appengine) - PaaS for hosting web applications

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/kerbymart/restlet-appstart/tags).

## Authors

- **Kerby Martino** - *Initial work* - [kerbymart](https://github.com/kerbymart)

See also the list of [contributors](https://github.com/kerbymart/restlet-appstart/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Hat tip to anyone whose code was used