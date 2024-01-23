package ga.selection;

import java.util.ArrayList;

import ga.chromosome.Chromosome;
import ga.population.PopulationIsland;

public class Tournament implements Selection {

	@Override
	public Chromosome select(PopulationIsland population) {
		// TODO Auto-generated method stub
		ArrayList<Integer> danhSachChonLoc = new ArrayList<Integer>();
		ArrayList<Integer> danhSachDoThichNghi = new ArrayList<Integer>();
		while(danhSachChonLoc.size() < 6) {
			int randomSe = new java.util.Random().nextInt(population.getKichThuocQT());
			if (!danhSachChonLoc.contains(randomSe)) {
				danhSachChonLoc.add(randomSe);
				danhSachDoThichNghi.add(population.getDoThichNghi().get(randomSe));
			}
		}
		
		int cha = 0;
		for(int i=1;i<6;i++) {
			if(danhSachDoThichNghi.get(i) < danhSachDoThichNghi.get(cha)) {
				cha = i;
			}
		}
		
		Chromosome x = population.getChromosome(danhSachChonLoc.get(cha));
		return x;
	}

}
