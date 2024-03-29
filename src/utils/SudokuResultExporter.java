package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class SudokuResultExporter {
    public static void exportResultToCSV(String path, List<SudokuResult> results) {
        try(FileWriter writer = new FileWriter(path)){
            // header
            writer.append("Level, Index, Time, Algorithms\n");

            // write result
            for(SudokuResult res : results) {
                writer.append(res.level())
                        .append(",")
                        .append(String.valueOf(res.index()))
                        .append(",")
                        .append(String.format("%.6f", res.time()))
                        .append(",")
                        .append(String.valueOf(res.algorithm()))
                        .append("\n");
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}

