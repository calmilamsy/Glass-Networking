package net.glasslauncher.mods.networking.mixin;

import net.glasslauncher.mods.networking.GlassNetworkHandler;
import net.minecraft.network.NetworkHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetworkHandler.class)
public class PacketHandlerMixin implements GlassNetworkHandler {
    private boolean hasGlassNetworking = false;

    @Override
    public boolean glass_Networking$hasGlassNetworking() {
        return hasGlassNetworking;
    }

    @Override
    public void glass_Networking$setHasGlassNetworking() {
        hasGlassNetworking = true;
    }
}
