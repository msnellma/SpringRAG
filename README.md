# SpringRAG
This is a demo project to test Retreival Augmented Generation with Spring AI. The project uses OpenAI's GPT-4o LLM model. The desired OpenAI model can be specified in `application.yaml`.

To run, you need a `OPEN_AI_KEY` environment variable, and start the PgVector Database:
```
docker-compose up
```
See `compose.yml` for database credentials.

Alternatively, start the application and the container should spin up.
## Document ingestion
Add documents to `src/resources/docs` and update the `IngestionService` class.
## API
Send a request to `localhost:8080/chat` with a String query.
