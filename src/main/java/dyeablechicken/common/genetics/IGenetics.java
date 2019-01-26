package dyeablechicken.common.genetics;

import dyeablechicken.Main;
import net.minecraft.entity.Entity;

import java.util.List;

public interface IGenetics {
    public static int genomeSize = Main.GENOMELENGTH;
    public static int chromosomeSize = Main.CHROMOSOMELENGTH;


    // Overloading here.
    // If getGenes is called without parameters, we will return the averaged value of both parents' first arrays
    // If getGenes is called with an index, we will return the averaged value of both parents' [index]th arrays
    // If getGenes is called with a bool and an index, we return the specified parent's [index]th array.
    //  -> if isFather is true, we return the father's; if it's false, we return the mother's.
    //
    // We also need to be able to get individual genes' values, I guess.
    //
    // We also want to be able to just get the entire genome, or all of one parent's half.

    public int[] getGenes();
    public int[] getGenes(int index);
    public int[] getGenes(int index, boolean isFather);
    public int getGenes(int index, int position);
    public int getGenes(int index, int position, boolean isFather);
    public List<int[]> getGenome();
    public List<int[]> getGenome(boolean isFather);



    // Overloading here, too
    // The following are the cases for setting genes:
    // 1) The entire genome
    // 2) The entirety of one parent's genes
    // 3) One of one parent's chromosomes
    // 4) A specific gene of a specific parent's chromosome
    // So, if setGenes, if passed a List<int[]>, will set the entire genome to that value.
    //  -> This list passed must be twice the normal size, and the first half will be the father's, and the second half
    //  -> the mother's.
    //  If we want to set a specific parent's genes, we need a List<int[]> and a boolean to determine father or mother.
    // If we want to set a specific parent's chromosome, we need an int[] that is the new data, and int that is the
    //  -> chromosome to be set, and a boolean.
    // If we want to set a specific gene of a specific parent's chromosome, we need an int that is the new data, and an
    // -> int that is the chromosome, and an int that is the value, and a boolean to determine father or mother.

    public void setGenes(List<int[]> newGenes);
    public void setGenes(List<int[]> newGenes, boolean isFather);
    public void setGenes(int[] newGenes, int chromosome, boolean isFather);
    public void setGenes(int newGene, int chromosome, int position, boolean isFather);

    public void setGenesFromPacket(List<int[]> newGenes);


    // Overloading everywhere. You get an overload and you get an overload and
    // The following are the cases for needing to generate genes:
    // 1) Animal was naturally spawned via world gen
    // 2) Animal is a child and we are creating a genome from parental contribution
    // 3) Animal is a parent and we need to create a haploid gene set

    public void generateGenes();
    public void generateGenes(Entity fatherEntity, Entity motherEntity);
    public void generateGenes(List<int[]> fatherGenes, List<int[]> motherGenes);
    public List<int[]> generateHaploidGenes();


    // Accessor methods
    // Note: we do not need methods to set the hasGenes boolean, as the length of either array being non-zero means we do.

    public boolean getHasParents();
    public boolean getHasGenes();
    public void setHasParents(int parent1, int parent2);
    public int getParent(boolean isFather);
    public void setHasGenes(boolean bool);


    // Updates the information, client-server talk, etc.

    public void updateGenes();

}
