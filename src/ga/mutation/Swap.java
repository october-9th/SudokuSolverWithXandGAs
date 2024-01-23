package ga.mutation;

import java.util.ArrayList;
import java.util.Random;

import ga.chromosome.Chromosome;

public class Swap implements Mutation {
	private ArrayList<ArrayList<Integer>> cauDo;
	
	public Swap(ArrayList<ArrayList<Integer>> cauDo) {
		this.cauDo = cauDo;
	}

	@Override
	public Chromosome mutate(Chromosome chromosome) {
		// TODO Auto-generated method stub
		Chromosome chromosome1 = new Chromosome();
		for(int count = 0; count < chromosome.getGiaiPhapSudoku().size(); count++) {
			ArrayList<Integer> hangDotBien = new ArrayList<Integer>(chromosome.getGiaiPhapSudoku().get(count));
			int tiLeDotBienHang = new Random().nextInt(4);
			if (tiLeDotBienHang == 0) { // Nếu tạo random = 1
				int soLanHoanDoi = new Random().nextInt(2) + 1; 
				for (int swaps = 0; swaps < soLanHoanDoi; swaps++) {
					int viTriDau = -1, viTriCuoi = -1;
					// Tìm vị trí ban đầu.
					while (true) {
						viTriDau = new Random().nextInt(chromosome.getGiaiPhapSudoku().size()); 
						if (this.cauDo.get(count).get(viTriDau) == 0) { 
							break;
						}
					}

					while (true) {
						viTriCuoi = new Random().nextInt(chromosome.getGiaiPhapSudoku().size());
						if (this.cauDo.get(count).get(viTriCuoi) == 0 && viTriDau != viTriCuoi) {
							break;// thoát khỏi vòng lặp, thực hiện hoán vị.
						}
					}

					// Hoan vi cac ca the bằng phương pháp 3 ngôi
					int tempSwap = hangDotBien.get(viTriDau);
					hangDotBien.set(viTriDau, hangDotBien.get(viTriCuoi));
					hangDotBien.set(viTriCuoi, tempSwap);
				}
			}
			chromosome1.getGiaiPhapSudoku().add(hangDotBien);
			
		}
		
		chromosome1.setDoThichNghi(chromosome1.tinhDoThichNghi(chromosome.getGiaiPhapSudoku().size())); // tính toạn độ thích nghi của cá thể vừa đột
		// biến
		return chromosome1;
	}

}
