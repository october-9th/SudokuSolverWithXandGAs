package ga.population;

import java.util.ArrayList;

import dancingLinks.Solution;
import dancingLinks.Sudoku;
import ga.chromosome.Chromosome;
import ga.constant.ConstantGA;

public class PopulationIsland {
	private ArrayList<ArrayList<Integer>> cauDo;
	private ArrayList<Chromosome> quanThe;
	private ArrayList<Integer> doThichNghi;
	private int kichThuocQT;
	private int tongDiemThichNghi = 0;
	
	public PopulationIsland() {
		// TODO Auto-generated constructor stub
		this.cauDo = new ArrayList<>();
		this.quanThe = new ArrayList<>();
		this.doThichNghi = new ArrayList<>();
		this.kichThuocQT = (int)ConstantGA.SO_LUONG_QUAN_THE.getGiaTri();
	}
	
	public PopulationIsland(ArrayList<ArrayList<Integer>> cauDo, int kichThuocQT) {
		this.cauDo = cauDo;
		this.kichThuocQT = kichThuocQT;
	}

	public ArrayList<ArrayList<Integer>> getPotentialChromosome(){
		Sudoku findPartialSolution = new Sudoku(cauDo.size());
		int[][] convertedGrid = utils.Helper.convertSudokuGrid(cauDo);
		findPartialSolution.generatePotentialChromosome(convertedGrid);

		return utils.Helper.convertSudokuGrid(convertedGrid);
	}
	public void khoiTaoQuanThe() {
		System.out.println("Khoi tao quan the...");
		this.quanThe =  new ArrayList<>();
		this.doThichNghi =  new ArrayList<>();
		while(this.quanThe.size()< this.kichThuocQT) {
			Chromosome caThe = new Chromosome(cauDo);
			if (true) {
				this.quanThe.add(caThe);
				this.doThichNghi.add(caThe.getDoThichNghi());
				this.tongDiemThichNghi+=caThe.getDoThichNghi();
			}
		}
	}
	
	public void thayDoiQuanThe(int viTriCaThe, Chromosome caThe){
		this.quanThe.set(viTriCaThe, caThe);
		this.doThichNghi.set(viTriCaThe, caThe.getDoThichNghi());
		int tong = 0;
		for(int c : doThichNghi) {
			tong += c;
		}
		setTongDiemThichNghi(tong);
	}
	
	public int caTheKemNhat() {
		int kemNhat = 0;
		for(int i=0;i<this.doThichNghi.size();i++) {
			if (this.doThichNghi.get(i) > this.doThichNghi.get(kemNhat)) {
				kemNhat = i;
			}
		}
		return kemNhat;
	}

	public int getTongDiemThichNghi() {
		return tongDiemThichNghi;
	}

	public void setTongDiemThichNghi(int tongDiemThichNghi) {
		this.tongDiemThichNghi = tongDiemThichNghi;
	}

	public int getKichThuocQT() {
		return kichThuocQT;
	}

	public void setKichThuocQT(int kichThuocQT) {
		this.kichThuocQT = kichThuocQT;
	}

	public ArrayList<Chromosome> getQuanThe() {
		return quanThe;
	}

	public void setQuanThe(ArrayList<Chromosome> quanThe) {
		this.quanThe = quanThe;
	}

	public ArrayList<Integer> getDoThichNghi() {
		return doThichNghi;
	}

	public void setDoThichNghi(ArrayList<Integer> doThichNghi) {
		this.doThichNghi = doThichNghi;
	}
	
	public Chromosome getChromosome(int index) {
		return this.quanThe.get(index);
	}
	
	
	
	
	
}
