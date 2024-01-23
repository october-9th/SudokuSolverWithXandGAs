package ga.crossover;

import java.util.ArrayList;
import ga.chromosome.Chromosome;

public class PartiallyMapped implements Crossover {
	private ArrayList<Chromosome> parents;
	private ArrayList<ArrayList<Integer>> cauDo;
	
	public PartiallyMapped(ArrayList<ArrayList<Integer>> cauDo, ArrayList<Chromosome> parents) {
		this.parents = parents;
		this.cauDo = cauDo;
		
	}

	@Override
	public Chromosome cross() {
		// TODO Auto-generated method stub
		Chromosome chromosome = new Chromosome();//

		// Ap dung moi dong thuc hien lai PMX
		for (int hang = 0; hang < this.parents.get(0).getGiaiPhapSudoku().size(); hang++) {
			ArrayList<Integer> hangCon = new ArrayList<Integer>(this.cauDo.get(hang)); // Copy 1 dong
			ArrayList<ArrayList<Integer>> pmxHangChaMe = new ArrayList<ArrayList<Integer>>(this.parents.get(0).getGiaiPhapSudoku().size());

			for (int i = 0; i < 2; i++) {
				pmxHangChaMe.add(new ArrayList<Integer>());
			}

			for (int viTriPhanTu = 0; viTriPhanTu < this.parents.get(0).getGiaiPhapSudoku().size(); viTriPhanTu++) {
				for (int parentCount = 0; parentCount < 2; parentCount++) {
					pmxHangChaMe.get(parentCount)
							.add(parents.get(parentCount).getGiaiPhapSudoku().get(hang).get(viTriPhanTu));
				}
			}

			int diemCat1 = (int) (Math.random() * parents.get(0).getGiaiPhapSudoku().get(0).size());
			int diemCat2 = (int) (Math.random() * parents.get(0).getGiaiPhapSudoku().get(0).size());
			if (diemCat1 > diemCat2) {
				int temp = diemCat1;
				diemCat1 = diemCat2;
				diemCat2 = temp;
			}
			
			

			for (int i = diemCat1; i <= diemCat2; i++) {
				hangCon.set(i, pmxHangChaMe.get(0).get(i));
			}
			for (int i = diemCat1; i <= diemCat2; i++) {
				int gene = pmxHangChaMe.get(1).get(i);
				if (!hangCon.contains(gene)) {
					int j = i;
					while (hangCon.get(j) != 0) {
						j = pmxHangChaMe.get(1).indexOf(hangCon.get(j));
					}
					hangCon.set(j, gene);
				}
			}
			for (int i = 0; i < pmxHangChaMe.get(0).size(); i++) {
				if (hangCon.get(i) == 0) {
					hangCon.set(i, pmxHangChaMe.get(1).get(i));
				}
			}
			chromosome.getGiaiPhapSudoku().add(hangCon);

		}

		chromosome.setDoThichNghi(chromosome.tinhDoThichNghi(this.parents.get(0).getGiaiPhapSudoku().size()));
		return chromosome;
		 
	}

}
