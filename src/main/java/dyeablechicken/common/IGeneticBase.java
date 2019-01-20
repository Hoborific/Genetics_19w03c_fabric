package dyeablechicken.common;

public interface IGeneticBase {

    int[] GENETIC_VALUES = null;

    public void initializeGenetics();
    public int[] getGenetics();
    public void setGenetics(int[] in);
}
