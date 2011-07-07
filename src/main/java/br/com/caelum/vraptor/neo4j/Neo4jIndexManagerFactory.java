package br.com.caelum.vraptor.neo4j;

import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.AbstractGraphDatabase;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
@Component
public class Neo4jIndexManagerFactory implements ComponentFactory<IndexManager>{

	private IndexManager index;

	public Neo4jIndexManagerFactory(AbstractGraphDatabase db) {
		index = db.index();
	}
	
	public IndexManager getInstance() {
		return this.index;
	}
	
}
