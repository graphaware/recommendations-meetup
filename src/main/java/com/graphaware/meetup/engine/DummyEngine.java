package com.graphaware.meetup.engine;

import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.engine.SingleScoreRecommendationEngine;
import org.neo4j.graphdb.Node;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * A very stupid engine, just recommends the fist X nodes, where X is the requested limit.
 */
public class DummyEngine extends SingleScoreRecommendationEngine<Node, Node> {

    @Override
    protected Map<Node, Float> doRecommendSingle(Node input, Context<Node, Node> context) {
        Map<Node, Float> result = new HashMap<>();

        for (Node node : GlobalGraphOperations.at(input.getGraphDatabase()).getAllNodes()) {
            addToResult(result, node, (float) Math.random());

            if (context.limit() <= result.size()) {
                break;
            }
        }

        return result;
    }

    @Override
    public String name() {
        return "dummyScore";
    }
}
