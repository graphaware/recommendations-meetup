<a name="top"/>
GraphAware Recommendations Meetup
=================================

[![Build Status](https://travis-ci.org/graphaware/recommendations-meetup.png)](https://travis-ci.org/graphaware/recommendations-meetup)

Getting Started
---------------

1. Clone this repository: `git clone https://github.com/graphaware/recommendations-meetup.git`
2. `cd recommendations-meetup`
3. `mvn clean test`, the build should be successful
4. Open in your favourite IDE, preferable IntelliJ IDEA (we can help you with that)
5. Start hacking

Generating Sample Data
----------------------

1. Go to http://graphgen.neoxygen.io
2. Generate graph according to your requirements
3. Click Export -> Cypher Queries Statement
4. Paste the statements into /src/test/resources/test-data.cyp

Deploying the Engine
--------------------

1. [Download Neo4j 2.2.2](http://neo4j.com/download-thanks/?edition=community&release=2.2.2&flavour=unix)
2. unzip to a location
3. [Download GraphAware](http://graphaware.com/downloads/graphaware-server-community-all-2.2.2.31.jar) and place into `plugins` of Neo4j
4. run `mvn clean package` on your recommendation engine
5. take the produced .jar **with dependencies** from `target` and place into `plugins` as well
6. (re)start Neo4j
7. populate your local Neo4j instance (e.g. using Graphgen)
8. hit one of the URLs you defined in your controllers, e.g. [http://localhost:7474/graphaware/recommendation/Napoleon](http://localhost:7474/graphaware/recommendation/Napoleon)
