package genetics.common.net;

import genetics.Main;
import genetics.common.genetics.IGeneticBase;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import static genetics.util.Logger.debugLog;

public class PacketHandling {

    public static Identifier GENETIC_SYNC_PACKET = new Identifier(Main.MODID + "genetic_sync");
    public static Identifier GENETIC_REQUEST_PACKET = new Identifier(Main.MODID + "genetic_request");

    public static BiConsumer<PacketContext, PacketByteBuf> SYNC_PACKET_CONSUMER = (PacketContext, PacketByteBuf) -> {
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
    };

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
