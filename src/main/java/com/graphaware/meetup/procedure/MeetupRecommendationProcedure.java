package com.graphaware.meetup.procedure;

import com.graphaware.meetup.MyRecommendationEngine;
import com.graphaware.reco.generic.config.SimpleConfig;
import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.result.Recommendation;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MeetupRecommendationProcedure {

    private static final int DEFAULT_RECO_LIMIT = 10;

    @Context
    public GraphDatabaseService database;

    private static TopLevelRecommendationEngine<Node, Node> engine = new MyRecommendationEngine();

    @Procedure("ga.demo.reco")
    public Stream<RecommendationResult> getRecommendations(@Name("name") String name) {
        List<RecommendationResult> result = new ArrayList<>();

        Node input = database.findNode(Label.label("Person"), "name", name);
        if (null == input) {
            return Stream.empty();
        }

        for (Recommendation<Node> recommendation : engine.recommend(input, new SimpleConfig(DEFAULT_RECO_LIMIT))) {
            result.add(new RecommendationResult(recommendation.getItem(), recommendation.getScore().getTotalScore()));
        }

        return result.stream();
    }

}
