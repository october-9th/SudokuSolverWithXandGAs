package ga.mutation;

import java.util.ArrayList;
import java.util.Random;

import ga.chromosome.Chromosome;

public class Scramble implements Mutation {
	private ArrayList<ArrayList<Integer>> cauDo;

	public Scramble(ArrayList<ArrayList<Integer>> cauDo) {
		this.cauDo = cauDo;
	}

	@Override
	public Chromosome mutate(Chromosome chromosome) {
		Chromosome chromosome2 = new Chromosome();
		ArrayList<Integer> hangDotBien;

		for (int hang = 0; hang < chromosome.getGiaiPhapSudoku().size(); hang++) {
			hangDotBien = new ArrayList<Integer>(this.cauDo.get(hang));

			ArrayList<Integer> scrambleSizehang = new ArrayList<Integer>();
			int scramblehangSize = 0;

			for (int phanTuPosition = 0; phanTuPosition < chromosome.getGiaiPhapSudoku().size(); phanTuPosition++) {
				if (hangDotBien.get(phanTuPosition) == 0) {
					scrambleSizehang.add(chromosome.getGiaiPhapSudoku().get(hang).get(phanTuPosition));
					scramblehangSize++;
				}
			}

			// Check if scramblehangSize is greater than 1
			if (scramblehangSize > 1) {
				int viTriDau = new Random().nextInt(scramblehangSize - 1);
				int viTriCuoi = viTriDau;

				while (!(viTriCuoi - viTriDau > 0)) {
					viTriCuoi = new Random().nextInt(scramblehangSize);
				}

				ArrayList<Integer> mutatedScrambleSizehang = new ArrayList<Integer>(scrambleSizehang);
				ArrayList<Integer> scrambledPositions = new ArrayList<Integer>(viTriCuoi - viTriDau);

				for (int i = viTriDau; i <= viTriCuoi; i++) {
					int randomSwap;
					do {
						randomSwap = new Random().nextInt(viTriCuoi - viTriDau + 1) + viTriDau;
					} while (scrambledPositions.contains(randomSwap));

					scrambledPositions.add(randomSwap);
					mutatedScrambleSizehang.set(i, scrambleSizehang.get(randomSwap));
				}

				int phanTuDotBien = 0;
				for (int phanTu = 0; phanTu < scramblehangSize; phanTu++) {
					while (true) {
						if (hangDotBien.get(phanTuDotBien) == 0) {
							hangDotBien.set(phanTuDotBien++, mutatedScrambleSizehang.get(phanTu));
							break;
						}
						phanTuDotBien++;
					}
				}
			}

			chromosome2.getGiaiPhapSudoku().add(hangDotBien);
		}
		chromosome2.setDoThichNghi(chromosome2.tinhDoThichNghi(chromosome.getGiaiPhapSudoku().size()));
		return chromosome2;
	}
}
