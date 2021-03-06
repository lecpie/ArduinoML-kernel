package io.github.mosser.arduinoml.kernel;

import io.github.mosser.arduinoml.kernel.behavioral.State;
import io.github.mosser.arduinoml.kernel.language.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.structural.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App implements NamedElement, Visitable {

	private String name;
	private List<Brick> bricks = new ArrayList<Brick>();
	private List<State> states = new ArrayList<State>();
	private State initial;

	private Map <String, Library> loadedLibraries = new HashMap<>();
	private List <LibraryUse> usedLibraries = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public State getInitial() {
		return initial;
	}

	public void setInitial(State initial) {
		this.initial = initial;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Map <String, Library> getLoadedLibraries() {
		return loadedLibraries;
	}

	public void setLoadedLibraries(Map <String, Library> loadedLibraries) {
		this.loadedLibraries = loadedLibraries;
	}

	public List<LibraryUse> getUsedLibraries() {
		return usedLibraries;
	}

	public void setUsedLibraries(List<LibraryUse> usedLibraries) {
		this.usedLibraries = usedLibraries;
	}
}
