package net.glasslauncher.mods.networking.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.networking.GlassNetworking;
import net.minecraft.network.packet.login.LoginHelloPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoginHelloPacket.class)
public class LoginHelloMixin {
    @Shadow
    public String username;

    @Shadow public long worldSeed;

    @Inject(
            method = "<init>(Ljava/lang/String;IJB)V",
            at = @At("RETURN")
    )
    @Environment(EnvType.SERVER)
    private void glassnetworking_injectGlassNetworkingFlagAndModList(String username, int protocolVersion, long worldSeed, byte dimensionId, CallbackInfo ci) {
        this.username += "glassnetworking;";
    }

    @Inject(
            method = "<init>(Ljava/lang/String;I)V",
            at = @At("RETURN")
    )
    @Environment(EnvType.CLIENT)
    private void glassnetworking_injectGlassNetworkingFlag(String username, int protocolVersion, CallbackInfo ci) {
        worldSeed |= GlassNetworking.MASK;
    }

    @ModifyConstant(
            method = "read",
            constant = @Constant(
                    ordinal = 0,
                    intValue = 16
            )
    )
    private int glassnetworking_injectHugeStringLimit(int constant) {
        return Short.MAX_VALUE;
    }
}
