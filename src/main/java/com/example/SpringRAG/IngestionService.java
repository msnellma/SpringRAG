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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IngestionService implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;
    @Value("classpath:/docs/document.pdf")
    private Resource myDoc;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        var pdfReader = new ParagraphPdfDocumentReader(myDoc);
        TextSplitter textSplitter = new TokenTextSplitter();

        List<Document> splitDocumentChunks = textSplitter.apply(pdfReader.get());

//        List<Document> documents = List.of(
//                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
//                new Document("The World is Big and Salvation Lurks Around the Corner"),
//                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));


        vectorStore.add(splitDocumentChunks);
        log.info("VectorStore loaded with data.");
    }
}
