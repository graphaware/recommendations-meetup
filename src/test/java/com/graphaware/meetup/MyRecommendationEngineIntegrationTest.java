package com.graphaware.meetup;

import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.log.Slf4jRecommendationLogger;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.test.data.DatabasePopulator;
import com.graphaware.test.data.GraphgenPopulator;
import com.graphaware.test.integration.DatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static com.graphaware.common.util.IterableUtils.getSingle;
import static org.junit.Assert.assertFalse;

public class MyRecommendationEngineIntegrationTest extends DatabaseIntegrationTest {

    private TopLevelRecommendationEngine<Node, Node> engine;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        engine = new MyRecommendationEngine();
    }

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
    public void shouldRecommendSomething() {
        try (Transaction tx = getDatabase().beginTx()) {
            Node person = getSingle(getDatabase().findNodes(DynamicLabel.label("Person"), "name", "Napoleon"));
            List<Recommendation<Node>> recommendations = engine.recommend(person, 5);

            assertFalse(recommendations.isEmpty());

            printRecommendations(person, recommendations);
        }
    }

    private void printRecommendations(Node input, List<Recommendation<Node>> recommendations) {
        System.out.println(new Slf4jRecommendationLogger<Node, Node>().toString(input, recommendations, null));
    }
}
