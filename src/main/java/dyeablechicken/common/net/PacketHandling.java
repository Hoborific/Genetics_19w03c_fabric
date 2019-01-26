package dyeablechicken.common.net;

import dyeablechicken.Main;
import dyeablechicken.common.genetics.IGenetics;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.networking.PacketContext;
import net.minecraft.client.network.packet.CustomPayloadClientPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.CustomPayloadServerPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import static dyeablechicken.util.Logger.debugLog;

public class PacketHandling {

    public static Identifier GENETIC_SYNC_PACKET = new Identifier(Main.MODID + "genetic_sync");
    public static Identifier GENETIC_REQUEST_PACKET = new Identifier(Main.MODID + "genetic_request");
    public static Identifier GENOME_SYNC_PACKET = new Identifier(Main.MODID + "genome_sync");
    public static Identifier GENOME_REQUEST_PACKET = new Identifier(Main.MODID + "genome_request");

    public static BiConsumer<PacketContext, PacketByteBuf> GENOME_SYNC_PACKET_CONSUMER = (PacketContext, PacketByteBuf) -> {
        Runnable runnable = () -> {
            PlayerEntity player = PacketContext.getPlayer();

            int id = PacketByteBuf.readInt();
            List<int[]> newGenes = new ArrayList<int[]>();

            for (int i=0; i< (Main.GENOMELENGTH * 2); i++) {
                newGenes.add(PacketByteBuf.readIntArray());
            }

            if (player == null) return;
            World world = player.getEntityWorld();
            if (world == null) return;
            Entity entity = world.getEntityById(id);
            if (entity == null) {
                debugLog("Genome Sync Packet Received but no reference of entityID " + id + " in the world.");
            } else {
                debugLog("Genome Sync Packet Being Read for entityID " + id);
                ((IGenetics) entity).setGenesFromPacket(newGenes);
            }
        };
        PacketContext.getTaskQueue().execute(runnable);
    };


    public static BiConsumer<PacketContext, PacketByteBuf> GENOME_REQUEST_PACKET_CONSUMER = (PacketContext, PacketByteBuf) -> {
        int id = PacketByteBuf.readInt();
        PlayerEntity player = PacketContext.getPlayer();
        Runnable runnable = () -> {
            World world = player.world.getWorld();
            Entity en = world.getEntityById(id);
            List<int[]> genes = ((IGenetics) en).getGenome();
            sendPacketToPlayer(craftGenomePacket(id, genes), player.world, player.getPos());
        };
        PacketContext.getTaskQueue().executeFuture(runnable);
    };

    public static Packet craftGenomePacket(int id, List<int[]> genes) {
        PacketByteBuf myBuf = new PacketByteBuf(Unpooled.buffer());

        myBuf.writeInt(id);

        for(int i=0; i < genes.size(); i++) {
            myBuf.writeIntArray(genes.get(i));
        }

        return new CustomPayloadClientPacket(GENOME_SYNC_PACKET, myBuf);
    }

    public static Packet craftGenomeRequestPacket(int id) {
        PacketByteBuf myBuf = new PacketByteBuf(Unpooled.buffer());
        myBuf.writeInt(id);
        return new CustomPayloadServerPacket(GENOME_REQUEST_PACKET, myBuf);
    }



    /* public static BiConsumer<PacketContext, PacketByteBuf> SYNC_PACKET_CONSUMER = (PacketContext, PacketByteBuf) -> {
        Runnable runnable = () -> {
            int id = PacketByteBuf.readInt();
            int[] arr = PacketByteBuf.readIntArray();
            PlayerEntity player = PacketContext.getPlayer();
            if (player == null) return;
            World world = player.getEntityWorld();
            if (world == null) return;
            Entity entity = world.getEntityById(id);
            if (entity == null) {
                debugLog("Packet received but no reference of entityID in world: " + id);
            } else {
                debugLog("packet being read: " + id + " arr: " + Arrays.toString(arr));
                ((IGeneticBase) entity).setGeneticsFromPacket(arr);
            }
        };
        PacketContext.getTaskQueue().execute(runnable);
    };

    public static BiConsumer<PacketContext, PacketByteBuf> REQUEST_PACKET_CONSUMER = (PacketContext, PacketByteBuf) -> {
        int id = PacketByteBuf.readInt();
        PlayerEntity player = PacketContext.getPlayer();
        Runnable runnable = () -> {
            World wrld = player.world.getWorld();
            Entity en = wrld.getEntityById(id);
            int[] genes = ((IGeneticBase) en).getGenetics();
            sendPacketToPlayer(craftGeneticPacket(id, genes), player.world, player.getPos());
        };
        PacketContext.getTaskQueue().executeFuture(runnable);
    }; */

    public static Packet craftGeneticPacket(int id, int[] genes) {
        PacketByteBuf myBuf = new PacketByteBuf(Unpooled.buffer());
        myBuf.writeInt(id);
        myBuf.writeIntArray(genes);
        return new CustomPayloadClientPacket(GENETIC_SYNC_PACKET, myBuf);
    }

    public static Packet craftGeneticRequestPacket(int id) {
        PacketByteBuf myBuf = new PacketByteBuf(Unpooled.buffer());
        myBuf.writeInt(id);
        return new CustomPayloadServerPacket(GENETIC_REQUEST_PACKET, myBuf);
    }

    public static void sendPacketToPlayer(Packet pak, World world, BlockPos pos) {
        debugLog("begin sendPacketToPlayer");
        if (!world.isClient) {
            debugLog("getting player list");
            List<ServerPlayerEntity> players = world.getPlayers(ServerPlayerEntity.class, Objects::nonNull);
            if (players.size() > 0) {
                debugLog("for each player");
                for (ServerPlayerEntity player : players) {
                    debugLog("sending packet to player");
                    player.networkHandler.sendPacket(pak);
                }
                debugLog("out of loop");
            }
            debugLog("end sendPacketToPlayer");
        }
    }

    public void netcode() {

    }


}
