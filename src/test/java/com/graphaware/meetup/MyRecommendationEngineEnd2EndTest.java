package com.graphaware.meetup;

import com.graphaware.test.integration.GraphAwareApiTest;
import com.graphaware.test.util.TestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;

import java.io.IOException;

import static com.graphaware.meetup.CypherUtil.runCypher;

public class MyRecommendationEngineEnd2EndTest extends GraphAwareApiTest {

    @Override
    protected GraphDatabaseService createDatabase() {
        GraphDatabaseService db = super.createDatabase();

        ExecutionEngine executionEngine = new ExecutionEngine(db);
        runCypher(executionEngine, "schema.cyp");
        runCypher(executionEngine, "data.cyp");

        return db;
    }

    @Test
    public void shouldRecommendSomething() throws IOException {
        String response = TestUtils.get(baseUrl() + "/recommendation/Napoleon", HttpStatus.OK_200);

        System.out.println("=== RESULT ===");
        System.out.println(response);
        System.out.println("=== END ===");
    }

    @Test
    public void shouldReturn404IfNodeNotFound() {
        TestUtils.get(baseUrl() + "/recommendation/NonExising", HttpStatus.NOT_FOUND_404);
    }
}
