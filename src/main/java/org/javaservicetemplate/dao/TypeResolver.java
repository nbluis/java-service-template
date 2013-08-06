package org.javaservicetemplate.dao;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public class TypeResolver<T> {

	private TypeLiteral<T> type;

	@Inject
	public TypeResolver(TypeLiteral<T> type) {
		this.type = type;
	}

	public Class<T> getTypeClass() {
		return (Class<T>) type.getRawType();
	}
}
