package com.graphaware.meetup;

import com.graphaware.meetup.engine.DummyEngine;
import com.graphaware.reco.generic.engine.RecommendationEngine;
import com.graphaware.reco.generic.log.Logger;
import com.graphaware.reco.generic.post.PostProcessor;
import com.graphaware.reco.neo4j.engine.Neo4jTopLevelDelegatingEngine;
import org.neo4j.graphdb.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyRecommendationEngine extends Neo4jTopLevelDelegatingEngine {

    public MyRecommendationEngine() {
        super(new MyContextFactory());
    }

    @Override
    protected List<RecommendationEngine<Node, Node>> engines() {
        return Arrays.<RecommendationEngine<Node, Node>>asList(
                new DummyEngine() //todo remove this engine and add your own
        );
    }

    @Override
    protected List<PostProcessor<Node, Node>> postProcessors() {
        return Collections.emptyList(); //todo add your own post processors
    }

    @Override
    protected List<Logger<Node, Node>> loggers() {
        return Collections.emptyList(); //todo optionally add your own logger
    }
}
