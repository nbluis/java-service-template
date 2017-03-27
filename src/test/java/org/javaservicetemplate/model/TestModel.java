package org.javaservicetemplate.model;

import javax.persistence.Id;

public class TestModel implements Model<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	public TestModel() {}

	public TestModel(Long id) {
		this();
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
