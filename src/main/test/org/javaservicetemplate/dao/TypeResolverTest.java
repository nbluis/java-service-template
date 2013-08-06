package org.javaservicetemplate.dao;

import static org.junit.Assert.assertEquals;

import org.javaservicetemplate.model.TestModel;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.TypeLiteral;

public class TypeResolverTest {

	private Class<?> type;
	private TypeResolver<TestModel> resolver;

	@Before
	public void setUp() {
		type = TestModel.class;
		this.resolver = new TypeResolver<TestModel>((TypeLiteral<TestModel>) TypeLiteral.get(type));
	}

	@Test
	public void shouldRetrieveATypeClassToAGenericType() {
		assertEquals(type, resolver.getTypeClass());
	}

}
