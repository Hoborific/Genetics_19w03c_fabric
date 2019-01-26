package dyeablechicken.common.genetics;

import dyeablechicken.Main;
import dyeablechicken.common.net.PacketHandling;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static dyeablechicken.util.Logger.debugLog;
import static dyeablechicken.util.Logger.log;

public class Genome implements IGenetics {

    // Reference to the entity who holds the instance of the class
    // Passed via constructor

    private Entity myself;


    // Used to avoid generating fresh genes over and over

    private Boolean hasGenetics = false;


    // If an entity generated naturally during world gen, it has no parents
    // This bool prevents us from shooting ourself in the foot trying to
    // check if two animals had the same parents.

    private Boolean hasParents = false;


    // IDs of parents, if any.

    private int fatherID;
    private int motherID;

    // The actual 'genes'.

    protected List<int[]> paternalGenes = new ArrayList<int[]>();
    protected List<int[]> maternalGenes = new ArrayList<int[]>();


    // Method Calls


    // Constructor
    public Genome() {
        for(int i=0; i< genomeSize; i++) {
            paternalGenes.add(new int[chromosomeSize]);
            maternalGenes.add(new int[chromosomeSize]);
        }
    }

    // Overload Constructor
    public Genome(Entity en) {
        for(int i=0; i< genomeSize; i++) {
            paternalGenes.add(new int[chromosomeSize]);
            maternalGenes.add(new int[chromosomeSize]);
        }
        myself = en;
    }


    // Returns the average of both parents first int[]

    @Override
    public int[] getGenes() {
        return getGenes(0);
    }


    // Returns the average of both parents [n]th int[]

    @Override
    public int[] getGenes(int index) {
        int[] tempFather = getGenes(index, true);
        int[] tempMother = getGenes(index, false);
        int[] temp = new int[chromosomeSize];

        for (int i=0; i < chromosomeSize; i++) {
            temp[i] = ((tempFather[i] + tempMother[i]) / 2);
        }

        return new int[0];
    }


    // Returns the [n]th int[] of the specified parent

    @Override
    public int[] getGenes(int index, boolean isFather) {

        if (isFather)
            return paternalGenes.get(index);
        else
            return maternalGenes.get(index);

    }


    // Returns the average of both parents 1st int[]'s nth position

    @Override
    public int getGenes(int index, int position) {

        return ((getGenes(index, position, true) + getGenes(index, position, false)) / 2);

    }


    // Returns the value of a specific gene in a specific int[] of the specified parent

    @Override
    public int getGenes(int index, int position, boolean isFather) {

        if (isFather)
            return (paternalGenes.get(index))[position];
        else
            return (maternalGenes.get(index))[position];

    }


    // Returns the whole genome

    @Override
    public List<int[]> getGenome() {
        List<int[]> tempList = new ArrayList<int[]>();

        for (int i=0; i < genomeSize; i++) {
            tempList.add(paternalGenes.get(i));
        }
        for (int i=0; i < genomeSize; i++) {
            tempList.add(maternalGenes.get(i));
        }

        return tempList;
    }


    // Returns the specified parent's genes

    @Override
    public List<int[]> getGenome(boolean isFather) {

        if (isFather)
            return paternalGenes;
        else
            return maternalGenes;

    }


    // Sets the entire genome. Requires a List<int[]> twice genomeLength;

    @Override
    public void setGenes(List<int[]> newGenes) {

        for(int i = 0; i < genomeSize; i++) {
            paternalGenes.set(i,newGenes.get(i));
            maternalGenes.set(i,newGenes.get(i + genomeSize));
        }

        updateGenes();
    }


    // Sets the specified parents' genes;

    @Override
    public void setGenes(List<int[]> newGenes, boolean isFather) {

        if (isFather)
            paternalGenes = newGenes;
        else
            maternalGenes = newGenes;

        updateGenes();
    }


    // Sets the specified parents' specified chromosome.

    @Override
    public void setGenes(int[] newGenes, int chromosome, boolean isFather) {

        if (isFather)
            paternalGenes.set(chromosome, newGenes);
        else
            maternalGenes.set(chromosome, newGenes);

        updateGenes();
    }


    // Sets the specified parents' specified chromosome's specified gene

    @Override
    public void setGenes(int newGene, int chromosome, int position, boolean isFather) {

        if (isFather)
            paternalGenes.get(chromosome)[position] = newGene;
        else
            maternalGenes.get(chromosome)[position] = newGene;

        updateGenes();
    }


    // Sets genes from packet; does not cause a packet to be sent to request sync (as that'd be a silly loop)

    @Override
    public void setGenesFromPacket(List<int[]> newGenes) {

        for(int i = 0; i < genomeSize; i++) {
            paternalGenes.set(i,newGenes.get(i));
            maternalGenes.set(i,newGenes.get(i + genomeSize));
        }

    }


    // Generates a completely new set of genes

    @Override
    public void generateGenes() {
        List<int[]> tempMotherList = new ArrayList<int[]>();
        List<int[]> tempFatherList = new ArrayList<int[]>();

        log("Generating fresh genes, entity " + myself.getEntityId());

        for (int i = 0; i < genomeSize; i++) {
            Random randy = new Random();

            int[] tempMother = {randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10)};
            int[] tempFather = {randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10), randy.nextInt(10)};

            tempFatherList.add(tempFather);
            tempMotherList.add(tempMother);
        }

        setGenes(tempFatherList, true);
        setGenes(tempMotherList, false);

        setHasGenes(true);

    }


    // Generates a new set of genes based off the genes of two parent entities

    @Override
    public void generateGenes(Entity fatherEntity, Entity motherEntity) {
        setGenes(((IGenetics)fatherEntity).generateHaploidGenes(), true);
        setGenes(((IGenetics)motherEntity).generateHaploidGenes(), false);

        setHasGenes(true);
    }


    // Generates a new set of genes based off the provided lists of genes

    @Override
    public void generateGenes(List<int[]> fatherGenes, List<int[]> motherGenes) {

        if ((fatherGenes.size() == (genomeSize * 2)) && (motherGenes.size() == (genomeSize *2))) {
            List<int[]> fatherTempList = new ArrayList<int[]>();
            List<int[]> motherTempList = new ArrayList<int[]>();
            Random randy = new Random();

            for (int i = 0; i < genomeSize; i++) {
                int[] fatherTemp = new int[chromosomeSize];
                int[] motherTemp = new int[chromosomeSize];

                for (int j = 0; j < chromosomeSize; j++) {
                    fatherTemp[j] = (randy.nextBoolean() ? fatherGenes.get(i)[j] : fatherGenes.get(i + genomeSize)[j]);
                    motherTemp[j] = (randy.nextBoolean() ? motherGenes.get(i)[j] : motherGenes.get(i + genomeSize)[j]);
                }

                fatherTempList.add(fatherTemp);
                motherTempList.add(motherTemp);
            }

            setGenes(fatherTempList, true);
            setGenes(motherTempList, false);

        }
        else {
            setGenes(fatherGenes, true);
            setGenes(motherGenes, false);
        }
    }


    // Semi-randomly combines the entity's parents' genes and returns the product of that to be used as the father/mother
    // genes in a new entity

    @Override
    public List<int[]> generateHaploidGenes() {
        List<int[]> haploidGenes = new ArrayList<int[]>();

        for (int i = 0; i < genomeSize; i++) {
            Random randy = new Random();
            int[] temp = new int[chromosomeSize];
            int[] paternalGenes = getGenes(i, true);
            int[] maternalGenes = getGenes(i, false);

            for (int j = 0; j < chromosomeSize; j++) {
                temp[j] = (randy.nextBoolean() ? paternalGenes[j] : maternalGenes[j]);
            }

            haploidGenes.add(temp);
        }

        return haploidGenes;
    }


    // I can't believe I even started to comment this.
    // returns whether or not the animal had parents (true) or was naturally spawned (false)

    @Override
    public boolean getHasParents() {
        return hasParents;
    }


    // Returns whether or not the animal has genes

    @Override
    public boolean getHasGenes() {
        return hasGenetics;
    }


    // Sets whether or not the animal has parents

    @Override
    public void setHasParents(int father, int mother) {
        fatherID = father;
        motherID = mother;
        hasParents = true;
    }

    @Override
    public int getParent(boolean isFather) {
        return (isFather ? fatherID : motherID);
    }

    @Override
    public void setHasGenes(boolean bool) {
        hasGenetics = bool;
    }

    // Handles display name and server->client

    @Override
    public void updateGenes() {

        if (myself.getEntityWorld().isClient) {
            debugLog("Setting Genes as Client");
            myself.setCustomName(new StringTextComponent(Arrays.toString(getGenes())));
            myself.setCustomNameVisible(true);
        }
        else {
            debugLog("Setting Genes as Server, Sending Gene Packet");
            Packet pacman = PacketHandling.craftGenomePacket(myself.getEntityId(), getGenome());
            PacketHandling.sendPacketToPlayer(pacman, myself.world, myself.getPos());
        }

    }

}
