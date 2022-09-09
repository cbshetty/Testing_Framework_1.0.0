package com.bdd.base;

import io.cucumber.java.en.Given;

public class Sample_StepDef {
	@Given("sample step")
	public void sample_step() {
		System.out.println("Sample Step");
	}
	
}
