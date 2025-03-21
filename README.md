# SpringRAG
This is a demo project to test Retreival Augmented Generation with Spring AI.

To run, you need a `OPEN_API_KEY` environment variable, and start the PgVector Database:
```
docker-compose up
```
See `compose.yml` for database credentials.
## Document ingestion
Add documents to `src/resources/docs` and update the `IngestionService` class.
## API
Send a request to `localhost:8080/chat` with a String query.