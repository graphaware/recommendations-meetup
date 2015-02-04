package com.graphaware.meetup;

import com.graphaware.reco.generic.filter.BlacklistBuilder;
import com.graphaware.reco.generic.filter.Filter;
import com.graphaware.reco.neo4j.context.Neo4jContextFactory;
import org.neo4j.graphdb.Node;

import java.util.Collections;
import java.util.List;

public class MyContextFactory extends Neo4jContextFactory {

    @Override
    protected List<BlacklistBuilder<Node, Node>> blacklistBuilders() {
        return Collections.emptyList(); //todo add your own blacklist builders instead
    }

    @Override
    protected List<Filter<Node, Node>> filters() {
        return Collections.emptyList(); //todo add your own filters instead
    }
}
