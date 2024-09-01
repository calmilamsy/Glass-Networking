package net.glasslauncher.mods.networking;

import net.minecraft.network.NetworkHandler;

import java.util.function.*;

public interface GlassPacketListener {

    /**
     * Register packets here. If a packet has "_optional" at the end of it's ID, it will be ignored. Don't abuse it too much.
     */
    default void registerGlassPacket(String fullId, BiConsumer<GlassPacket, NetworkHandler> applicator, boolean clientBound, boolean serverBound) {
        GlassNetworking.PACKET_HANDLERS.computeIfPresent(fullId, (id, applicator2) -> {throw new RuntimeException("Packet ID \"" + id + "\" already exists!");});

        GlassNetworking.PACKET_HANDLERS.put(fullId, applicator);
        if (clientBound) {
            GlassNetworking.CLIENT_BOUND_PACKETS.add(fullId);
        }
        if (serverBound) {
            GlassNetworking.SERVER_BOUND_PACKETS.add(fullId);
        }
    }

    void registerGlassPackets();
}
