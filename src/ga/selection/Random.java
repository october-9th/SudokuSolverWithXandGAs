package ga.selection;

import ga.chromosome.Chromosome;
import ga.population.PopulationIsland;

public class Random implements Selection {

	@Override
	public Chromosome select(PopulationIsland population) {
		// TODO Auto-generated method stub
		int rand = new java.util.Random().nextInt(population.getKichThuocQT());
		return population.getChromosome(rand);
	}

}
