package net.glasslauncher.mods.networking.mixin.client;

import net.glasslauncher.mods.networking.GlassNetworkHandler;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.network.packet.login.LoginHelloPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Inject(
            method = "onHello",
            at = @At("HEAD")
    )
    private void stationapi_onLoginSuccess(LoginHelloPacket packet, CallbackInfo ci) {
        List<String> splitName = Arrays.asList(packet.username.split(";"));
        if (splitName.contains("glassnetworking")) {
            ((GlassNetworkHandler) this).glass_Networking$setHasGlassNetworking();
        }
    }
}
