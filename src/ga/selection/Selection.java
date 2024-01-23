package ga.selection;

import ga.chromosome.Chromosome;
import ga.population.PopulationIsland;

public interface Selection {
	Chromosome select(PopulationIsland population);
}
