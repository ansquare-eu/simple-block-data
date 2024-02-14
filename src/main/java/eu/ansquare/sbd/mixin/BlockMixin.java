package eu.ansquare.sbd.mixin;

import eu.ansquare.sbd.BlockDataApi;
import eu.ansquare.sbd.ChangeType;
import eu.ansquare.sbd.PersistenceType;
import eu.ansquare.sbd.SimpleBlockData;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
@Mixin(Block.class)
public class BlockMixin {

	@Inject(method = "afterBreak",at = @At("TAIL"), cancellable = true)
	public void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci){
		BlockDataApi.clear(pos, world, ChangeType.TO_AIR);
	}
}
