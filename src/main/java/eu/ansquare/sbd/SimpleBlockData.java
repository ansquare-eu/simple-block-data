package eu.ansquare.sbd;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.sun.jdi.connect.Connector;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class SimpleBlockData implements ModInitializer, ChunkComponentInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Simple block data");
	public static final String MODID = "sbd";
	public static final ComponentKey<DataChunkComponent> DATA_CHUNK_COMPONENT =
			ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MODID, "data_chunk"), DataChunkComponent.class);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		if(QuiltLoader.isDevelopmentEnvironment()){
			Registry.register(Registries.ITEM, new Identifier(MODID, "sbd_data_tester"), new Item(new QuiltItemSettings().maxCount(1)){
				@Override
				public ActionResult useOnBlock(ItemUsageContext context) {
					if(!context.getWorld().isClient){
						if(context.getPlayer().isSneaking()){
							BlockDataApi.setString(context.getBlockPos(), context.getWorld(), "debug_value_avoid", "yes do avoid");
						} else {
							NbtCompound compound = BlockDataApi.getCompound(context.getBlockPos(), context.getWorld());
							LOGGER.info(compound.toString());
							BlockDataApi.clear(context.getBlockPos(), context.getWorld(), ChangeType.DIFFERENT_TYPE);
						}
						return ActionResult.SUCCESS;
					}
					return ActionResult.PASS;
				}
			});
		}
	}

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(DATA_CHUNK_COMPONENT, world -> new DataChunkComponent());
	}
}
