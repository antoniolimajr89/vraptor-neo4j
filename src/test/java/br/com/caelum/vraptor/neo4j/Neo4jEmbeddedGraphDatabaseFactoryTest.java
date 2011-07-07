package br.com.caelum.vraptor.neo4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;

import org.junit.Test;

public class Neo4jEmbeddedGraphDatabaseFactoryTest {

	@Test
	public void should_use_default_database_directory_when_nothing_was_configured() {
		ServletContext context = mock(ServletContext.class);
		when(context.getInitParameter("br.com.caelum.vraptor.neo4j.database")).thenReturn(null);
		Neo4jEmbeddedGraphDatabaseFactory factory = new Neo4jEmbeddedGraphDatabaseFactory(context);
		
		assertEquals("/tmp/database", factory.databaseDirectory);
	}
	
	@Test
	public void should_use_the_configured_database_directory_when_configured() {
		ServletContext context = mock(ServletContext.class);
		when(context.getInitParameter("br.com.caelum.vraptor.neo4j.database")).thenReturn("/a/db/directory");
		Neo4jEmbeddedGraphDatabaseFactory factory = new Neo4jEmbeddedGraphDatabaseFactory(context);
		
		assertEquals("/a/db/directory", factory.databaseDirectory);
	}
}
