package net.glasslauncher.mods.networking;

import lombok.Getter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.ApiStatus;

import java.io.*;

public class GlassPacket extends Packet {
    private @Getter String modId;
    private @Getter String packetId;
    private @Getter NbtCompound nbt;

    private int length;

    /***
     * Use this when sending the packet.
     */
    public <T extends GlassPacket> GlassPacket(String modId, String packetId, NbtCompound nbt) {
        this.modId = modId;
        this.packetId = packetId;
        this.nbt = nbt;
    }

    /**
     * Don't use. Internal IMPL constructor.
     */
    @ApiStatus.Internal
    public GlassPacket() {}

    @Override
    public void read(DataInputStream stream) {
        nbt = new NbtCompound();
        nbt.read(stream);
        packetId = nbt.getString("glassnetworking:packetId");
        modId = nbt.getString("glassnetworking:modId");
    }

    @Override
    public void write(DataOutputStream stream) {
        nbt.putString("glassnetworking:packetId", packetId);
        nbt.putString("glassnetworking:modId", modId);
        length = GlassNetworking.writeAndGetNbtLength(nbt, stream);
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        GlassNetworking.PACKET_LISTENERS.get(getFullId()).accept(this, networkHandler);
    }

    @Override
    public int size() {
        return length;
    }

    public String getFullId() {
        return modId + ":" + packetId;
    }
}
