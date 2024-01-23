package ga.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ga.chromosome.Chromosome;
import ga.crossover.PartiallyMapped;
import ga.crossover.UniformOrder;
import ga.mutation.Scramble;
import ga.mutation.Swap;
import ga.population.Population;
import ga.population.PopulationIsland;
import ga.selection.RouletteWheel;
import ga.selection.Tournament;


/*
* Strategy for integrating algorithm X into genetic algorithm
1. Apply algorithm X to initialize the first generation of populations:
- Initialize a set of high quality chromosomes, this set of chromosomes will ensure that the population always achieves a better fitness threshold than random
2. Algorithm X will be used to refined promising chromosomes:
- After a certain n generations, use DLX to refine the promising chromosomes as close to the solution as possible
3. Stagnation detection
- Detect and eliminate chromosomes that have stagnated over generations and refine high-performance chromosomes

*/
public class ImplementGA implements Runnable {
    private ArrayList<ArrayList<Integer>> cauDo;
    private int vongTienHoa;
    private int vongDoiMoiDao;
    private double tiLeDotBien;
    private double tiLeLaiGhep;
    private int kichThuocQT;
    private int kichThuocDao;
    private Population population = new Population();

    private List<int[][]> solution = new ArrayList<>();
    private List<Chromosome> porposeCandidate = new ArrayList<>();

    /**
     * Constructor
     */
    public ImplementGA() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor full tham so
     *
     * @param cauDo
     * @param vongTienHoa
     * @param vongDoiMoiDao
     * @param tiLeDotBien
     * @param tiLeLaiGhep
     * @param kichThuocQT
     * @param kichThuocDao
     */
    public ImplementGA(ArrayList<ArrayList<Integer>> cauDo, int vongTienHoa, int vongDoiMoiDao, double tiLeDotBien, double tiLeLaiGhep, int kichThuocQT, int kichThuocDao) {
        this.cauDo = cauDo;
        this.vongTienHoa = vongTienHoa;
        this.vongDoiMoiDao = vongDoiMoiDao;
        this.tiLeDotBien = tiLeDotBien;
        this.tiLeLaiGhep = tiLeLaiGhep;
        this.kichThuocQT = kichThuocQT;
        this.kichThuocDao = kichThuocDao;
    }

    /**
     * Method Lai ghep
     *
     * @param chaMe
     * @return
     */
    public Chromosome crossOver(ArrayList<Chromosome> chaMe) {
        Chromosome conLai = new Chromosome();
        double tiLeLG = this.tiLeLaiGhep;
        double rand = new Random().nextDouble();

        if (tiLeLG <= rand) {
            int luaChonPhuongPhap = new Random().nextInt(2);
            if (luaChonPhuongPhap == 0) {
                conLai = new PartiallyMapped(cauDo, chaMe).cross();
            } else {
                conLai = new UniformOrder(cauDo, chaMe).cross();
            }

        } else {
            conLai = chaMe.get(new Random().nextInt(2));
        }
        return conLai;
    }

    /**
     * Method Dot bien
     *
     * @param x
     * @return
     */
    public Chromosome mutate(Chromosome x) {
        Chromosome y = new Chromosome();
        double tiLeDB = this.tiLeDotBien;
        double rand = new Random().nextDouble();

        if (rand < tiLeDB) {
            int luaChonPP = new Random().nextInt(2);
            if (luaChonPP == 0) {
                y = new Swap(this.cauDo).mutate(x);
            } else {
                y = new Scramble(this.cauDo).mutate(x);
            }
        } else {
            y = x;
        }
        return y;
    }

    /**
     * Method chon loc bo me
     *
     * @param population
     * @return
     */
    public ArrayList<Chromosome> selectionParent(PopulationIsland population) {
        ArrayList<Chromosome> chaMe = new ArrayList<>();
        int luaChonPP = new Random().nextInt(3);
        if (luaChonPP == 0) {
            for (int i = 0; i < 2; i++) {
                chaMe.add(new Tournament().select(population));
            }

        } else if (luaChonPP == 1) {
            for (int i = 0; i < 2; i++) {
                chaMe.add(new ga.selection.Random().select(population));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                chaMe.add(new RouletteWheel().select(population));
            }
        }
        return chaMe;
    }

    public void thayDoiCaThe(PopulationIsland population, Chromosome x) {
        int rand = new Random().nextInt(2);
        if (rand == 0) {
            thayDoiKemNhat(population, x);
        } else {
            thayDoiNgauNhien(population, x);
        }
    }

    private void thayDoiNgauNhien(PopulationIsland populationIsland, Chromosome x) {
        int randomX = new Random().nextInt(populationIsland.getKichThuocQT());
        populationIsland.thayDoiQuanThe(randomX, x);
    }

    private void thayDoiKemNhat(PopulationIsland populationIsland, Chromosome x) {
        populationIsland.thayDoiQuanThe(populationIsland.caTheKemNhat(), x);
    }

    /**
     * @param populationIsland
     * @return
     */
    public int thucHienToanTuDiTruyen(PopulationIsland populationIsland, int island) {
        int giaiPhap = -1;
        for (int vongDoiMoiDao = 0; vongDoiMoiDao < this.vongDoiMoiDao; vongDoiMoiDao++) {
            Chromosome con = mutate(crossOver(selectionParent(populationIsland)));
            thayDoiCaThe(populationIsland, con);
            System.out.println("Island: " + island + " SL: " + populationIsland.getKichThuocQT() + " total fitness score: " + populationIsland.getTongDiemThichNghi());

            if (populationIsland.getDoThichNghi().contains(0)) {
                for (int i = 0; i < populationIsland.getKichThuocQT(); i++) {
                    if (populationIsland.getChromosome(i).getDoThichNghi() == 0) {
                        giaiPhap = i;
//						System.out.println("this is solution: " + populationIsland.getChromosome(giaiPhap)); 
                        // code của Hoàng Việt
                        System.out.println(">>>>> Solution found: <<<<<\n" + utils.Helper.sudokuBoard(populationIsland.getChromosome(giaiPhap).getGiaiPhapSudoku()));
                        solution.add(utils.Helper.convertSudokuGrid(populationIsland.getChromosome(giaiPhap).getGiaiPhapSudoku()));
                        break;
                    }
                }

                if (giaiPhap != -1) {
                    break;
                }
            }
        }

        return giaiPhap;
    }


    public void khoiTaoQuanThe() {
        for (int dao = 0; dao < this.kichThuocDao; dao++) {
            PopulationIsland populationIsland = new PopulationIsland(this.cauDo, this.kichThuocQT);
            populationIsland.khoiTaoQuanThe();
            population.themQuanTheDao(populationIsland);
        }
    }

    public void thuatToanDiTruyen() {
        khoiTaoQuanThe();
        boolean flag = false;
        for (int vong = 0; vong < this.vongTienHoa; vong++) {
            for (int dao = 0; dao < this.kichThuocDao; dao++) {
                if (thucHienToanTuDiTruyen(population.getPopulations().get(dao), dao) != -1) {
                    vong = this.vongTienHoa;
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("Can't find the solution");
        }

    }

    public void showQuanThe() {
        for (int i = 0; i < this.kichThuocDao; i++) {
            System.out.println(population.getPopulations().get(i).getQuanThe());
        }
    }

    public List<int[][]> getSolution() {
        return solution;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        khoiTaoQuanThe();
        boolean flag = false;

        for (int vong = 0; vong < this.vongTienHoa; vong++) {
            for (int dao = 0; dao < this.kichThuocDao; dao++) {
                if (thucHienToanTuDiTruyen(population.getPopulations().get(dao), dao) != -1) {
                    vong = this.vongTienHoa;
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("No solution found");
        }
    }
}
