package com.graphaware.meetup.web;

import com.graphaware.meetup.MyRecommendationEngine;
import com.graphaware.reco.generic.config.SimpleConfig;
import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.result.Recommendation;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import static com.graphaware.common.util.IterableUtils.getSingle;

@Controller
public class RecommendationController {

    private final GraphDatabaseService database;

    private TopLevelRecommendationEngine<Node, Node> engine = new MyRecommendationEngine();

    @Autowired
    public RecommendationController(GraphDatabaseService database) {
        this.database = database;
    }

    @RequestMapping("/recommendation/{name}")
    @ResponseBody
    public List<RecommendationVO> recommend(@PathVariable String name, @RequestParam(defaultValue = "10") int limit) {
        try (Transaction tx = database.beginTx()) {
            return convert(engine.recommend(findByName(name), new SimpleConfig(limit)));
        }
    }

    private Node findByName(String name) {
        return getSingle(database.findNodes(Label.label("Person"), "name", name), "Person with name " + name + " does not exist.");
    }

    private List<RecommendationVO> convert(List<Recommendation<Node>> recommendations) {
        List<RecommendationVO> result = new LinkedList<>();

        for (Recommendation<Node> recommendation : recommendations) {
            result.add(new RecommendationVO(recommendation.getUuid(), recommendation.getItem().getProperty("name", "unknown").toString(), recommendation.getScore()));
        }

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String translateException(NotFoundException e) {
        return e.getMessage();
    }
}
