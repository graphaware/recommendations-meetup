package com.graphaware.meetup.procedure;

import org.neo4j.graphdb.Node;

public class RecommendationResult {

    public final Node node;

    public final double score;

    public RecommendationResult(Node node, double score) {
        this.node = node;
        this.score = score;
    }

}
