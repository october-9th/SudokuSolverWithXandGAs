package ga.crossover;

import java.util.ArrayList;
import ga.chromosome.Chromosome;

public class UniformOrder implements Crossover {
	private ArrayList<Chromosome> parents;
	private ArrayList<ArrayList<Integer>> cauDo;
	
	public UniformOrder(ArrayList<ArrayList<Integer>> cauDo, ArrayList<Chromosome> parents) {
		this.parents = parents;
		this.cauDo = cauDo;
	}

	@Override
	public Chromosome cross() {
		// TODO Auto-generated method stub
		Chromosome chromosome = new Chromosome();//// Tao ra 1 ca the moi.

		// Duyệt qua mỗi hàng
		for (int row = 0; row < this.parents.get(0).getGiaiPhapSudoku().size(); row++) {
			ArrayList<Integer> childRow = new ArrayList<Integer>(this.cauDo.get(row)); // tạo dòng con lưu trữ
																								// các dòng của đề bài
			ArrayList<ArrayList<Integer>> uniOrderParentsRow = new ArrayList<ArrayList<Integer>>(2); 
			int uniOrderPermutationSize = 0; // số lượng số 0 ở mỗi dòng

			// Thêm 2 mảng con.
			for (int parentCount = 0; parentCount < 2; parentCount++) {
				uniOrderParentsRow.add(new ArrayList<Integer>());
			}

			// Lặp lại tất cả các phần tử và tạo các mảng mới cho Uniform với các phần tử cố
			// định đã bị xóa
			for (int elementPosition = 0; elementPosition < this.parents.get(0).getGiaiPhapSudoku().size(); elementPosition++) {
				// Khi vi tri hien tai khong co dinh (Gia tri = 0)
				if (childRow.get(elementPosition) == 0) {
					for (int parentCount = 0; parentCount < 2; parentCount++) {
						// Sao chép phần tử của hàng cụ thể vào danh sách mới được tạo cho Uni.
						uniOrderParentsRow.get(parentCount)
								.add(parents.get(parentCount).getGiaiPhapSudoku().get(row).get(elementPosition));
					}
					uniOrderPermutationSize++;
				}
			}

			ArrayList<Integer> uniOrderMutatedRow = new ArrayList<Integer>(uniOrderParentsRow.get(0)); 
			// Sao chép phần tử cha đầu tiên vào phần tử con.
			for (int uniOrderCount = 0, copyCount = 1; uniOrderCount < uniOrderPermutationSize
					&& copyCount < uniOrderPermutationSize; uniOrderCount++) {
				boolean isCopied = false;
				// Lặp lại qua hàng mới được tạo và thay thế các phần tử được đặt ở vị trí chẵn.
				for (int uniOrderReplace = 0; uniOrderReplace < uniOrderPermutationSize; uniOrderReplace += 2) {
					// Kiểm tra phần tử tồn tại hay không trong cha 2
					if (uniOrderMutatedRow.get(uniOrderReplace) == uniOrderParentsRow.get(1).get(uniOrderCount)) {
						isCopied = true;
						break;
					}
				}

				if (!isCopied) {
					uniOrderMutatedRow.set(copyCount, uniOrderParentsRow.get(1).get(uniOrderCount));
					copyCount += 2;
				}
			}

			// sao chép phần tử vào hàng Con.
			for (Integer uniOrderRecombElement : uniOrderMutatedRow) {
				int childRowCount = 0;
				// Tìm phần tử còn trống.
				while (childRow.get(childRowCount) != 0) {
					childRowCount++;
				}
				childRow.set(childRowCount, uniOrderRecombElement);
			}

			chromosome.getGiaiPhapSudoku().add(childRow);
		}
		chromosome.setDoThichNghi(chromosome.tinhDoThichNghi(this.parents.get(0).getGiaiPhapSudoku().size()));
		return chromosome;
	}
	
}
