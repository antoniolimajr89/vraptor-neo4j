package br.com.caelum.vraptor.neo4j;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.AbstractGraphDatabase;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;

public class Neo4jTransactionInterceptorTest {

	@Test
	public void should_be_a_successful_transaction_if_has_no_validation_error() {
		AbstractGraphDatabase db = mock(AbstractGraphDatabase.class);
		Validator validator = mock(Validator.class);
		Transaction tx = mock(Transaction.class);
		InterceptorStack stack = mock(InterceptorStack.class);
		
		when(db.beginTx()).thenReturn(tx);
		when(validator.hasErrors()).thenReturn(false);
		Neo4jTransactionInterceptor interceptor = new Neo4jTransactionInterceptor(db, validator);
		
		interceptor.intercept(stack, null, null);
		
		verify(tx).success();
		verify(tx).finish();
	}
	
	@Test
	public void should_be_marked_as_failed_transaction_if_has_validation_error() {
		AbstractGraphDatabase db = mock(AbstractGraphDatabase.class);
		Validator validator = mock(Validator.class);
		Transaction tx = mock(Transaction.class);
		InterceptorStack stack = mock(InterceptorStack.class);
		
		when(db.beginTx()).thenReturn(tx);
		when(validator.hasErrors()).thenReturn(true);
		Neo4jTransactionInterceptor interceptor = new Neo4jTransactionInterceptor(db, validator);
		
		interceptor.intercept(stack, null, null);
		
		verify(tx).failure();
		verify(tx).finish();
	}
}
