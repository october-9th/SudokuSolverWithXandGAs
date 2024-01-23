package ga.population;

import java.util.ArrayList;

public class Population {
	private ArrayList<PopulationIsland> populations;
	
	public Population() {
		// TODO Auto-generated constructor stub
		this.populations = new ArrayList<>();
		
	}
	
	public int size() {
		return this.populations.size();
	}
	
	public Population(ArrayList<PopulationIsland
			> populations) {
		this.populations = populations;
	}
	
	public void themQuanTheDao(PopulationIsland populationIsland) {
		this.populations.add(populationIsland);
	}

	public ArrayList<PopulationIsland> getPopulations() {
		return populations;
	}

	public void setPopulations(ArrayList<PopulationIsland> populations) {
		this.populations = populations;
	}
	
	
}
