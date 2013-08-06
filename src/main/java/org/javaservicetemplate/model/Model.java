package org.javaservicetemplate.model;

import java.io.Serializable;

public interface Model<T> extends Serializable {

	T getId();

	void setId(T id);

}
