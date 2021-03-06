/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.plugin.sparql;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.server.rest.AbstractRestFunctionalTestBase;

import com.sun.jersey.api.client.ClientResponse.Status;

public class SPARQLPluginFunctionalTest extends AbstractRestFunctionalTestBase
{
    private static final String ENDPOINT = "http://localhost:7474/db/data/ext/SPARQLPlugin/graphdb/execute_sparql";
    private static final String QUAD_ENDPOINT = "http://localhost:7474/db/data/ext/SPARQLPlugin/graphdb/insert_quad";

    @Test
    public void insert_quads()
    {

        String payload = "{\"s\":\"http://neo4j.org#joe\", \"p\":\"http://neo4j.org#knows\",\"o\":\"http://neo4j.org#sara\", \"c\":\"http://neo4j.org\"}";
        gen.get().payload( payload ).expectedStatus( Status.NO_CONTENT ).post(
                QUAD_ENDPOINT ).entity();

    }

    @Test
    public void querying_sparql()
    {

        String payload = "{\"s\":\"http://neo4j.org#joe\", \"p\":\"http://neo4j.org#knows\",\"o\":\"http://neo4j.org#sara\", \"c\":\"http://neo4j.org\"}";
        gen.get().payload( payload ).expectedStatus( Status.NO_CONTENT ).post(
                QUAD_ENDPOINT ).entity();

        payload = "{\"s\":\"http://neo4j.org#joe\", \"p\":\"http://neo4j.org#name\",\"o\":\"joe\", \"c\":\"http://neo4j.org\"}";
        gen.get().payload( payload ).expectedStatus( Status.NO_CONTENT ).post(
                QUAD_ENDPOINT ).entity();

        payload = "{\"query\":\"SELECT ?n WHERE { ?x <http://neo4j.org#knows> <http://neo4j.org#sara> . ?x <http://neo4j.org#name> ?n .}\"}";
        String entity = gen.get().payload( payload ).expectedStatus( Status.OK ).post(
                ENDPOINT ).entity();
        assertTrue( entity.contains( "joe" ) );
    }
    
    @Before
    public void cleanContent()
    {
//        cleanDatabase();
//        gen.get().setGraph( graphdb() );
    }
}
