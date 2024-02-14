package eu.ansquare.sbd;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDataApi {
	public static boolean hasData(BlockPos pos, World world){
		return !getCompound(pos, world).getKeys().isEmpty();
	}
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
	public static String getString(BlockPos pos, World world, String key){
		NbtCompound compound = getCompound(pos, world);
		return compound.getString(key);
	}
	public static void setString(BlockPos pos, World world, String key, String value){
		NbtCompound compound = getCompound(pos, world);
		compound.putString(key, value);
		updateCompound(pos, world, compound);
	}
	public static int getInt(BlockPos pos, World world, String key){
		NbtCompound compound = getCompound(pos, world);
		return compound.getInt(key);
	}
	public static void setInt(BlockPos pos, World world, String key, int value){
		NbtCompound compound = getCompound(pos, world);
		compound.putInt(key, value);
		updateCompound(pos, world, compound);
	}
	public static NbtElement getNbtElement(BlockPos pos, World world, String key){
		NbtCompound compound = getCompound(pos, world);
		return compound.get(key);
	}
	public static void setNbtElement(BlockPos pos, World world, String key, NbtElement value){
		NbtCompound compound = getCompound(pos, world);
		compound.put(key, value);
		updateCompound(pos, world, compound);
	}
	public static void clear(BlockPos pos, World world, ChangeType changeType){
		/*PersistenceType type;
				try{
					type = PersistenceType.valueOf(getString(pos, world, "persistence_type"));
				} catch (IllegalArgumentException e){
					type = PersistenceType.NONE;
				}*/
		if(hasData(pos, world)) updateCompound(pos, world, new NbtCompound());
	}
	public static void transferData(BlockPos from, BlockPos to, World world){
		NbtCompound compound = getCompound(from, world);
		updateCompound(from, world, new NbtCompound());
		updateCompound(to, world, compound);
	}

}
