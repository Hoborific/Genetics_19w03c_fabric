package dyeablechicken.items;

import dyeablechicken.common.genetics.IGeneticBase;
import dyeablechicken.common.genetics.MyGenetics;

public class GeneticsSyringe extends GeneticsBaseItem implements IGeneticBase {
    public int[] myGenes = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    public GeneticsSyringe(Settings item$Settings_1) {
        super(item$Settings_1);
    }


    @Override
    public void initializeGenetics() {

    }

    @Override
    public void initializeGenetics(int[] mum, int[] dad) {

    }

    @Override
    public void setGeneticsFromPacket(int[] geneticarray) {

    }

    @Override
    public int[] getGenetics() {
        return myGenes;
    }

    @Override
    public int[] generateGenetics(int[] parent1, int[] parent2) {
        return myGenes;
    }

    @Override
    public int[] generateGenetics() {
        return myGenes;
    }

    @Override
    public void setGeneticsInherited(int[] arr) {
        myGenes = arr;
    }

    @Override
    public void registerModels() {

    }
}
