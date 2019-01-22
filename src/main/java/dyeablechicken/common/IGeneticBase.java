package dyeablechicken.common;

public interface IGeneticBase {

    int[] GENETIC_VALUES = null;

    void initializeGenetics();

    void setGeneticsFromPacket(int[] geneticarray);

    int[] getGeneticsForPacket();
}