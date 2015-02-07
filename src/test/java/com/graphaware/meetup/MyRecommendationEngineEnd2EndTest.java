package com.graphaware.meetup;

import com.graphaware.test.data.DatabasePopulator;
import com.graphaware.test.data.GraphgenPopulator;
import com.graphaware.test.integration.GraphAwareApiTest;
import com.graphaware.test.util.TestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class MyRecommendationEngineEnd2EndTest extends GraphAwareApiTest {

    @Override
    protected DatabasePopulator databasePopulator() {
        return new GraphgenPopulator() {
            @Override
            protected String file() throws IOException {
                return new ClassPathResource("test-data.cyp").getFile().getAbsolutePath();
            }
        };
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
