package com.graphaware.meetup;

import com.graphaware.reco.generic.context.Mode;
import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.log.Slf4jRecommendationLogger;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.test.integration.DatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.List;

import static com.graphaware.common.util.IterableUtils.getSingle;
import static com.graphaware.meetup.CypherUtil.runCypher;
import static org.junit.Assert.assertFalse;

public class MyRecommendationEngineIntegrationTest extends DatabaseIntegrationTest {

    private TopLevelRecommendationEngine<Node, Node> engine;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        engine = new MyRecommendationEngine();
    }

    @Override
    protected GraphDatabaseService createDatabase() {
        GraphDatabaseService db = super.createDatabase();

        ExecutionEngine executionEngine = new ExecutionEngine(db);
        runCypher(executionEngine, "schema.cyp");
        runCypher(executionEngine, "data.cyp");

        return db;
    }

    @Test
    public void shouldRecommendSomething() {
        try (Transaction tx = getDatabase().beginTx()) {
            Node michal = getSingle(getDatabase().findNodesByLabelAndProperty(DynamicLabel.label("Person"), "name", "Napoleon"));
            List<Recommendation<Node>> recommendations = engine.recommend(michal, Mode.REAL_TIME, 5);

            assertFalse(recommendations.isEmpty());

            printRecommendations(michal, recommendations);
        }
    }

    private void printRecommendations(Node input, List<Recommendation<Node>> recommendations) {
        System.out.println(new Slf4jRecommendationLogger<Node, Node>().toString(input, recommendations, null));
    }
}
