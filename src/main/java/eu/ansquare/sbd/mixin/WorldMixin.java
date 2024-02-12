package eu.ansquare.sbd.mixin;

import eu.ansquare.sbd.BlockDataApi;
import eu.ansquare.sbd.ChangeType;
import eu.ansquare.sbd.PersistenceType;
import eu.ansquare.sbd.SimpleBlockData;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
@Mixin(ServerWorld.class)
public class WorldMixin {
	@Inject(method = "onBlockChanged",at = @At("HEAD"), cancellable = true)
	public void onSetBlockState(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci){
		ChangeType type;
		if(newBlock.isAir()){
			type = ChangeType.TO_AIR;
		} else if (oldBlock.getBlock() == newBlock.getBlock()) {
			type = ChangeType.KEEP_TYPE;
		} else {
			type = ChangeType.DIFFERENT_TYPE;
		}
		//BlockDataApi.clear(pos, (ServerWorld) (Object) this, type);

	}
}
