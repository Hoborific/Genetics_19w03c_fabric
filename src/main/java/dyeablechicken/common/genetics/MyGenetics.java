package dyeablechicken.common.genetics;


import dyeablechicken.Main;
import dyeablechicken.common.net.PacketHandling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dyeablechicken.util.Logger.debugLog;

public class MyGenetics {

    private Entity myself;
    public Boolean hasGenetics = false;
    public Boolean hasParents = false;
    public int parent1;
    public int parent2;
    protected List<int[]> paternalGenes = new ArrayList<int[]>();
    protected List<int[]> maternalGenes = new ArrayList<int[]>();

    private DataTracker myDT;

    public MyGenetics(Entity en) {
        this.myself = en;
        myDT = new DataTracker(en);
        for (int i = 0; i < Main.GENOMELENGTH; i++) {
            paternalGenes.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
            maternalGenes.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        }
    }

    public int[] getGenetics() {
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED getGenetics on CLIENT");
        }
        int[] father = getGenetics(true);
        int[] mother = getGenetics(false);
        int[] temp = new int[Main.CHROMOSOMELENGTH];

        for (int i = 0; i < Main.CHROMOSOMELENGTH; i++ ) {
            temp[i] = ((father[i] + mother[i]) / 2);
        }

        return temp;
    }

    public int[] getGenetics(int chromosome) {
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED getGenetics on CLIENT");
        }
        int[] father = getGenetics(true, chromosome);
        int[] mother = getGenetics(false, chromosome);
        int[] temp = new int[Main.CHROMOSOMELENGTH];

        for (int i = 0; i < Main.CHROMOSOMELENGTH; i++ ) {
            temp[i] = ((father[i] + mother[i]) / 2);
        }

        return temp;
    }

    public int[] getGenetics(boolean isFather) {
        return (isFather) ? paternalGenes.get(0) : maternalGenes.get(0);
    }

    public int[] getGenetics(boolean isFather, int chromosome) {
        return (isFather) ? paternalGenes.get(chromosome) : maternalGenes.get(chromosome);
    }

    public int getGeneticsByIndex(int in) {
        if (Main.CHROMOSOMELENGTH >= in) {
            return paternalGenes.get(0)[in];
        } else {
            return -1;
        }
    }

    public World getWorld() {
        return this.myself.getEntityWorld();
    }
    public void setGenetics(int[] genes) {
        paternalGenes.set(0, genes);
        if (myself instanceof PlayerEntity) return;
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED setGenetics on CLIENT");
            myself.setCustomName(new StringTextComponent(Arrays.toString(genes)));
            myself.setCustomNameVisible(true);
        } else {
            debugLog("sending packet");
            Packet pacman = PacketHandling.craftGeneticPacket(myself.getEntityId(), paternalGenes);
            PacketHandling.sendPacketToPlayer(pacman, myself.world, myself.getPos());
        }
    }

    public void setGenetics(int[] genes, int chromosome) {
        paternalGenes.set(0, genes);
        if (myself instanceof PlayerEntity) return;
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED setGenetics on CLIENT");
            myself.setCustomName(new StringTextComponent(Arrays.toString(genes)));
            myself.setCustomNameVisible(true);
        } else {
            debugLog("sending packet");
            Packet pacman = PacketHandling.craftGeneticPacket(myself.getEntityId(), paternalGenes);
            PacketHandling.sendPacketToPlayer(pacman, myself.world, myself.getPos());
        }
    }

    public void setGenetics(List<int[]> genes) {
        paternalGenes = genes;
        if (myself instanceof PlayerEntity) return;
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED setGenetics on CLIENT");
            myself.setCustomName(new StringTextComponent(Arrays.toString(genes.get(0))));
            myself.setCustomNameVisible(true);
        } else {
            debugLog("sending packet");
            Packet pacman = PacketHandling.craftGeneticPacket(myself.getEntityId(), paternalGenes);
            PacketHandling.sendPacketToPlayer(pacman, myself.world, myself.getPos());
        }
    }


    public int[] getClientGenetics() {
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED getClientGenetics on CLIENT");
        }
        return paternalGenes.get(0);
    }

    public int getEntityID() {
        return myself.getEntityId();
    }

    public int getGeneticsByIndex(int chromosome, int in) {
        return paternalGenes.get(chromosome)[in];
    }
}
