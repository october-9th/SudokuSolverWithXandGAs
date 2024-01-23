package ga.selection;

import java.util.ArrayList;
import java.util.Random;

import ga.chromosome.Chromosome;
import ga.population.PopulationIsland;

public class RouletteWheel implements Selection {

    @Override
    public Chromosome select(PopulationIsland population) {
        // TODO Auto-generated method stub
        double tongFitness = population.getTongDiemThichNghi();
        double totalInverseFitness = 0;
        ArrayList<Integer> tungCaThe = population.getDoThichNghi();
        for (int fitness : tungCaThe) {
            totalInverseFitness += 1.0 / fitness;
        }
        double randNum = new Random().nextDouble() * totalInverseFitness;
        double partSum = 0;

        for (int i = 0; i < tungCaThe.size(); i++) {
            partSum += 1.0 / tungCaThe.get(i);
            if (partSum >= randNum) {
                return population.getChromosome(i);
            }
        }
        return population.getChromosome(population.getKichThuocQT() - 1);
    }


}
