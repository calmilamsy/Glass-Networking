package net.glasslauncher.mods.networking.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.networking.GlassNetworking;
import net.minecraft.network.packet.handshake.HandshakePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.util.*;

@Mixin(HandshakePacket.class)
public class HandshakePacketMixin {
    @Shadow
    public String name;

    @Inject(
            method = "read",
            at = @At("TAIL")
    )
    @Environment(EnvType.CLIENT)
    private void glassnetworking_stopClientFromDying(DataInputStream par1, CallbackInfo ci) {
        String[] names = name.split(";");
        name = names[0];
        //noinspection deprecation
        GlassNetworking.setServerHasNetworking(Arrays.asList(names).contains("glassnetworking"));
    }

    @Inject(
            method = "write",
            at = @At("HEAD")
    )
    @Environment(EnvType.CLIENT)
    private void glassnetworking_injectGlassNetworkingFlag(DataOutputStream par1, CallbackInfo ci) {
        this.name += ";glassnetworking;";
    }
}
