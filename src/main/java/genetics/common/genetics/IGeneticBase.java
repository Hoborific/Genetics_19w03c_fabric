package genetics.common.genetics;

import com.sun.istack.internal.NotNull;

public interface IGeneticBase {
    int genomeSize = 10;

    int[] GENETIC_VALUES = null;

    void initializeGenetics();

    void initializeGenetics(int[] mum, int[] dad);

    void setGeneticsFromPacket(int[] geneticarray);

    int[] getGenetics();

    int[] generateGenetics(@NotNull int[] parent1, @NotNull int[] parent2);

    int[] generateGenetics();

    int getGeneticByIndex(int in);

    void setGeneticsInherited(int[] arr);
}