package br.com.caelum.vraptor.neo4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class Neo4jEmbeddedGraphDatabaseFactory implements ComponentFactory<AbstractGraphDatabase> {
	
	final String databaseDirectory;
	private EmbeddedGraphDatabase db;

	public Neo4jEmbeddedGraphDatabaseFactory(ServletContext ctx) {
		String configuredDirectory = ctx.getInitParameter("br.com.caelum.vraptor.neo4j.database");
		this.databaseDirectory = configuredDirectory!= null ? configuredDirectory: "/tmp/database";
	}
	
	@PostConstruct
	public void initialize() {
		db = new EmbeddedGraphDatabase(databaseDirectory);
	}

	public AbstractGraphDatabase getInstance() {
		return this.db;
	}

	@PreDestroy
	public void destroy() {
		db.shutdown();
	}
}
