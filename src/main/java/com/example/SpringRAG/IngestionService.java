package com.example.SpringRAG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IngestionService implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;
    private final JdbcClient jdbcClient;
    @Value("classpath:/docs/document.pdf")
    private Resource myDoc;

    public IngestionService(VectorStore vectorStore, JdbcClient jdbcClient) {
        this.vectorStore = vectorStore;
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void run(String... args) throws Exception {
        Integer count = jdbcClient.sql("select count(*) from vector_store")
                .query(Integer.class)
                .single();

        if (count == 0) {
            var pdfReader = new ParagraphPdfDocumentReader(myDoc);
            TextSplitter textSplitter = new TokenTextSplitter();

            List<Document> splitDocumentChunks = textSplitter.apply(pdfReader.get());

            vectorStore.add(splitDocumentChunks);
            log.info("VectorStore loaded with data.");
        }
    }
}
