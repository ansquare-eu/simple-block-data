package eu.ansquare.sbd;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataChunkComponent implements Component {
	private static Map<String, NbtCompound> map = new HashMap<>();

	@Override
	public void readFromNbt(NbtCompound tag) {
		List<NbtElement> list = tag.getList("blocks", 8);
		map = new HashMap<>();
		for (NbtElement e : list) {
			if (e instanceof NbtString string) {
				map.put(string.asString(), tag.getCompound(string.asString()));
			}
		}
		;
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList list = new NbtList();
		for (String s: map.keySet()){
			list.add(NbtString.of(s));
			tag.put(s, map.get(s));
		}
		tag.put("blocks", list);
	}

	public NbtCompound get(BlockPos pos) {
		String key = blockPosToKey(pos);
		return map.containsKey(key) ? map.get(key) : new NbtCompound();
	}

	public void set(BlockPos pos, NbtCompound compound) {
		String key = blockPosToKey(pos);
		map.put(key, compound);
	}

	public static String blockPosToKey(BlockPos pos) {
		StringBuilder builder = new StringBuilder();
		builder.append(pos.getX());
		builder.append(":");
		builder.append(pos.getY());
		builder.append(":");
		builder.append(pos.getZ());
		return builder.toString();
	}
}
