<a name="top"/>

GraphAware Recommendations Meetup
=================================

Getting Started
---------------

1. Clone this repository: `git clone https://github.com/graphaware/recommendations-meetup.git`
2. `cd recommendations-meetup`
3. `mvn clean test`, the build should be successful
4. Open in your favourite IDE, preferable IntelliJ IDEA (we can help you with that)
5. Start hacking

Generating Sample Data
----------------------

1. Go to http://graphgen.graphaware.com
2. Generate graph according to your requirements
3. Click Export -> Cypher Queries Statement
4. Paste the statements into /src/test/resources/test-data.cyp

Deploying the Engine
--------------------

1. [Download Neo4j 3.5.14+](http://neo4j.com/download-thanks/?edition=community&release=3.5.17&flavour=unix)
2. unzip to a location
3. [Download GraphAware](http://graphaware.com/downloads/graphaware-server-all-3.5.14.58.jar) and place into `plugins` of Neo4j
4. add `dbms.thirdparty_jaxrs_classes=com.graphaware.server=/graphaware` to `conf/neo4j.conf`
5. run `mvn clean package` on your recommendation engine
6. take the produced .jar **with dependencies** from `target` and place into `plugins` as well
7. (re)start Neo4j
8. populate your local Neo4j instance (e.g. using Graphgen)
9. hit one of the URLs you defined in your controllers, e.g. [http://localhost:7474/graphaware/recommendation/Napoleon](http://localhost:7474/graphaware/recommendation/Napoleon)
