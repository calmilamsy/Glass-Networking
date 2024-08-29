package net.glasslauncher.mods.networking.mixin.server;

import net.glasslauncher.mods.networking.GlassNetworkHandler;
import net.glasslauncher.mods.networking.GlassNetworking;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.packet.login.LoginHelloPacket;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Inject(
            method = "accept",
            at = @At("HEAD")
    )
    private void glassnetworking_handleLogin(LoginHelloPacket arg, CallbackInfo ci) {
        if ((arg.worldSeed & GlassNetworking.MASK) == GlassNetworking.MASK) {
            ((GlassNetworkHandler) this).glass_Networking$setHasGlassNetworking();
        }
    }

    @Inject(
            method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;<init>(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/network/Connection;Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
                    shift = At.Shift.BY,
                    by = 2
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void stationapi_checkModded(LoginHelloPacket arg, CallbackInfo ci, ServerPlayerEntity var2, ServerWorld var3, Vec3i var4, ServerPlayNetworkHandler var5) {
        GlassNetworkHandler moddedPacketHandler = ((GlassNetworkHandler) this);
        if (moddedPacketHandler.glass_Networking$hasGlassNetworking()) {
            ((GlassNetworkHandler) var5).glass_Networking$setHasGlassNetworking();
        }
    }
}
