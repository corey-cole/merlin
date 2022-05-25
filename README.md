# merlin
Spring Boot example used to test CI/CD

## Build Process
The CodeBuild files present in this project provide a CodeBuild batch configuration.
Discrete build steps (guardrail, ci, containerize) are ordered in a DAG via the top-level [buildspec file](./buildspec.yml).

This is not intended as a production-grade build process and should be taken as an example starting point.
