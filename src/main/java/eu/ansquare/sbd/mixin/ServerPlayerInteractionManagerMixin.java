package eu.ansquare.sbd.mixin;

import eu.ansquare.sbd.BlockDataApi;
import eu.ansquare.sbd.ChangeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
	@Shadow
	@Final
	protected ServerPlayerEntity player;

	@Inject(method = "tryBreakBlock", at = @At("RETURN"), cancellable = true)
	public void tryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(cir.getReturnValueZ()){
			BlockDataApi.clear(pos, player.getServerWorld(), ChangeType.TO_AIR);
		}
	}
}
