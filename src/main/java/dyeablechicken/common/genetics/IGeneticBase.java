package dyeablechicken.common.genetics;

import com.sun.istack.internal.NotNull;
import dyeablechicken.Main;

import java.util.List;

public interface IGeneticBase {
    int genomeSize = Main.GENOMELENGTH;
    int chromosomeSize = Main.CHROMOSOMELENGTH;

    int[] GENETIC_VALUES = null;

    void initializeGenetics();

    void initializeGenetics(List<int[]> mum, List<int[]> dad);

    void setGeneticsFromPacket(int[] geneticarray);

    int[] getGenetics();
    int[] getGenetics(int chromosome);

    List<int[]> generateGenetics(@NotNull List<int[]> parent1, @NotNull List<int[]> parent2);

    List<int[]> generateGenetics();

    int getGeneticsByIndex(int in);
    int getGeneticsByIndex(int chromosome, int in);

    void setGeneticsInherited(int[] arr);
}