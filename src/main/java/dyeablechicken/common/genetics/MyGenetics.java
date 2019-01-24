package dyeablechicken.common.genetics;


import dyeablechicken.common.net.PacketHandling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Arrays;

import static dyeablechicken.util.Logger.debugLog;

public class MyGenetics {

    private Entity myself;
    public Boolean hasGenetics = false;
    public Boolean hasParents = false;
    public int parent1;
    public int parent2;
    protected int[] GENETIC_TRACKER;

    private DataTracker myDT;

    public MyGenetics(Entity en) {
        this.myself = en;
        myDT = new DataTracker(en);
        GENETIC_TRACKER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    public int[] getGenetics() {
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED getGenetics on CLIENT");
        }
        return GENETIC_TRACKER;
    }

    public int getGeneticByIndex(int in) {
        if (GENETIC_TRACKER.length >= in) {
            return GENETIC_TRACKER[in];
        } else {
            return -1;
        }
    }

    public World getWorld() {
        return this.myself.getEntityWorld();
    }
    public void setGenetics(int[] genes) {
        GENETIC_TRACKER = genes;
        if (myself instanceof PlayerEntity) return;
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED setGenetics on CLIENT");
            myself.setCustomName(new StringTextComponent(Arrays.toString(genes)));
            myself.setCustomNameVisible(true);
        } else {
            debugLog("sending packet");
            Packet pacman = PacketHandling.craftGeneticPacket(myself.getEntityId(), GENETIC_TRACKER);
            PacketHandling.sendPacketToPlayer(pacman, myself.world, myself.getPos());
        }
    }

    public int[] getClientGenetics() {
        if (myself.getEntityWorld().isClient) {
            debugLog("CALLED getClientGenetics on CLIENT");
        }
        return GENETIC_TRACKER;
    }

    public int getEntityID() {
        return myself.getEntityId();
    }
}
