package main.groovy.groovuinoml.dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;

public class GroovuinoMLBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;

	private GroovuinoMLModel model;
	
	public GroovuinoMLBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public GroovuinoMLBinding(Map variables) {
		super(variables);
	}
	
	public GroovuinoMLBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setGroovuinoMLModel(GroovuinoMLModel model) {
		this.model = model;
	}

	public GroovuinoMLModel getGroovuinoMLModel() {
		return this.model;
	}
}
