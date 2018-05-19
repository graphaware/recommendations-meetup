package com.graphaware.meetup;

import com.graphaware.meetup.procedure.MeetupRecommendationProcedure;
import com.graphaware.reco.generic.config.SimpleConfig;
import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.log.Slf4jRecommendationLogger;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.test.data.DatabasePopulator;
import com.graphaware.test.data.GraphgenPopulator;
import com.graphaware.test.integration.EmbeddedDatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.internal.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.graphaware.common.util.IterableUtils.getSingle;
import static org.junit.Assert.assertFalse;

public class MyRecommendationEngineIntegrationTest extends EmbeddedDatabaseIntegrationTest {

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

    @Override
    protected void registerProceduresAndFunctions(Procedures procedures) throws Exception {
        super.registerProceduresAndFunctions(procedures);

        try {
            procedures.registerProcedure(MeetupRecommendationProcedure.class);
        } catch (KernelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldRecommendSomething() {
        try (Transaction tx = getDatabase().beginTx()) {
            Node person = getSingle(getDatabase().findNodes(Label.label("Person"), "name", "Napoleon"));
            List<Recommendation<Node>> recommendations = engine.recommend(person, new SimpleConfig(5));

            assertFalse(recommendations.isEmpty());

            printRecommendations(person, recommendations);
        }
    }

    @Test
    public void shouldRecommendSomethingWithProcedure() {
        Map<Node, Double> recommendations = new HashMap<Node, Double>();

        try (Transaction tx = getDatabase().beginTx()) {
            String napoleon = "Napoleon";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", napoleon);
            Result result = getDatabase().execute("CALL ga.demo.reco({name})", parameters);
            while (result.hasNext()) {
                Map<String, Object> record = result.next();
                Node item = (Node) record.get("node");
                double score = (double) record.get("score");
                recommendations.put(item, score);
            }
            tx.success();
        }
        assertFalse(recommendations.isEmpty());
    }

    private void printRecommendations(Node input, List<Recommendation<Node>> recommendations) {
        System.out.println(new Slf4jRecommendationLogger<Node, Node>().toString(input, recommendations, null));
    }
}
