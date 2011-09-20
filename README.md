# VRaptor Neo4j Plugin

The VRaptor Neo4j plugin aims to bring to VRaptor smooth integration to Neo4j graph database,
by providing out of the box transaction control, Neo4j components dependency injection and easy 
indexing framework access.

## How to use

You can enable the Neo4j VRaptor plugin by adding a context-param to your web.xml file:

	<context-param>
	    <param-name>br.com.caelum.vraptor.packages</param-name>
	    <param-value>
	        br.com.caelum.vraptor.neo4j
	    </param-value>
	</context-param>
	
After this, you'll need to tell VRaptor where is your Neo4j database stored (if you don't do this,
VRaptor will assume /tmp/database directory):

	<context-param>
		<param-name>br.com.caelum.vraptor.neo4j.database</param-name>
		<param-value>/some/directory</param-value>
	</context-param>

Now, VRaptor will enable 3 components:

### The Neo4jEmbeddedGraphDatabaseFactory

This ApplicationScoped component is responsible for manage and provide the EmbeddedGraphDatabase instance,
so you can receive it in your components by VRaptor's dependency injection, just like:

	@Resource
	public class PersonController {
		private AbstractGraphDatabase db;
		
		public PersonController(AbstractGraphDatabase db) {
			this.db = db;
		}
		
		@Post
		public void save(Person p) {
			Node node = db.createNode();
			// and so on
		}
	}

Now, using the EmbeddedGraphDatabase instance is just as simple as declaring it as a constructor argument.

### The Neo4jIndexManagerFactory

Similar to the EmbeddedGraphDatabaseFactory, the IndexManagerFactory makes possible to receive by
dependency injection the Neo4j Index Manager, with whom you can create new indexes, and search using
Neo4j Lucene's integration, for instance.

	@Resource
	public class PersonController {
		private EmbeddedGraphDatabase db;
		private IndexManager index;
		
		public PersonController(EmbeddedGraphDatabase db, IndexManager index) {
			this.db = db;
			this.index = index;
		}
		
		@Get
		public void findByName(String nameToQuery) {
			Index<Node> nameIndex = index.forNodes("nameIndex");
			IndexHits<Node> hits = nameIndex.query("name", nameToQuery)
			// and so on
		}
	}

### The Neo4jTransactionInterceptor

The TransactionInterceptor takes care of correctly beginning, committing and if necessary, rolling back a
Neo4j transaction. So, it is guaranteed that whenever you're creating a Node, the operation will 
happen inside a transaction. The transaction opens on the beginning of each request and is committed
at the end of it. If a validation error or an exception occurs, the transaction is rolled back.
 