package com.graphaware.meetup;

import org.apache.commons.io.FileUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class CypherUtil {

    private CypherUtil() {
    }

    public static void runCypher(ExecutionEngine executionEngine, String fileName) {
        String query;
        try {
            query = FileUtils.readFileToString(new ClassPathResource(fileName).getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String singleQuery : query.replaceAll("\n", "").split(";")) {
            executionEngine.execute(singleQuery).dumpToString();
        }
    }
}
