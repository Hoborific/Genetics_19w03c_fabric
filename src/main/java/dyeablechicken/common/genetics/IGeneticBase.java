package dyeablechicken.common.genetics;

import com.sun.istack.internal.NotNull;

public interface IGeneticBase {
    int genomeSize = 10;

    int[] GENETIC_VALUES = null;

    void initializeGenetics();

    void setGeneticsFromPacket(int[] geneticarray);

    int[] getGeneticsForPacket();

    int[] generateGenetics(@NotNull int[] parent1, @NotNull int[] parent2);

    int[] generateGenetics();

    void setGeneticsInherited(int[] arr);
}