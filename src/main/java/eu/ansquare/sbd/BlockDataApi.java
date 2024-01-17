package eu.ansquare.sbd;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockDataApi {
	public static NbtCompound getCompound(BlockPos pos, World world){
		if(!SimpleBlockData.DATA_CHUNK_COMPONENT.maybeGet(world.getChunk(pos)).isPresent()) return new NbtCompound();
		DataChunkComponent component = SimpleBlockData.DATA_CHUNK_COMPONENT.get(world.getChunk(pos));
		return component.get(pos);
	}
	public static void updateCompound(BlockPos pos, World world, NbtCompound compound){
		if(!SimpleBlockData.DATA_CHUNK_COMPONENT.isProvidedBy(world.getChunk(pos))) return;
		DataChunkComponent component = SimpleBlockData.DATA_CHUNK_COMPONENT.get(world.getChunk(pos));
		component.set(pos, compound);
		world.getChunk(pos).setNeedsSaving(true);
	}
	public static boolean getBoolean(BlockPos pos, World world, String key){
		NbtCompound compound = getCompound(pos, world);
		return compound.getBoolean(key);
	}
	public static void setBoolean(BlockPos pos, World world, String key, boolean value){
		NbtCompound compound = getCompound(pos, world);
		compound.putBoolean(key, value);
		updateCompound(pos, world, compound);
	}
}
