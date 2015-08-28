package com.test.webapp.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class TestView implements Serializable
{
	private static final long serialVersionUID = 7497221276833022736L;
	private String name;

	@PostConstruct
	public void init()
	{
		this.setName("Test");
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}