package br.com.caelum.vraptor.neo4j;

import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class Neo4jTransactionInterceptor implements Interceptor {

	private final AbstractGraphDatabase db;
	private final Validator validator;
	
	public Neo4jTransactionInterceptor(AbstractGraphDatabase db, Validator validator) {
		this.db = db;
		this.validator = validator;
	}

	public boolean accepts(ResourceMethod method) {
		return true;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object object) throws InterceptionException {
		Transaction tx = db.beginTx();
		try {
			stack.next(method, object);
			if(! validator.hasErrors()) {
				tx.success();
			} else {
				tx.failure();
			}
		} finally {
			tx.finish();
		}
	}
}
