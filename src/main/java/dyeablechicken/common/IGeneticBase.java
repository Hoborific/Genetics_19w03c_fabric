package dyeablechicken.common;

public interface IGeneticBase {

    int[] GENETIC_VALUES = null;

    void initializeGenetics();
    int[] getGenes();
    void setGenes(int[] in);
}
