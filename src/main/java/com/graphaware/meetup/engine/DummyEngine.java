package com.graphaware.meetup.engine;

import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.engine.SingleScoreRecommendationEngine;
import com.graphaware.reco.generic.result.PartialScore;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * A very stupid engine, just recommends the fist X nodes, where X is the requested limit.
 */
public class DummyEngine extends SingleScoreRecommendationEngine<Node, Node> {

    @Override
    protected Map<Node, PartialScore> doRecommendSingle(Node input, Context<Node, Node> context) {
        Map<Node, PartialScore> result = new HashMap<>();

        for (Node node : getAllNodes(input)) {
            addToResult(result, node, randomPartialScore());

            if (context.config().limit() <= result.size()) {
                break;
            }
        }

        return result;
    }

    @Override
    public String name() {
        return "dummyScore";
    }

    private PartialScore randomPartialScore() {
        float random = (float) Math.random();
        Map<String, Object> reasons = new HashMap<>();
        reasons.put("randomNumber", random);
        reasons.put("testReason", "just another reason");

        return new PartialScore(random, reasons);
    }

    private ResourceIterable<Node> getAllNodes(Node input) {
        return GlobalGraphOperations.at(input.getGraphDatabase()).getAllNodes();
    }
}
