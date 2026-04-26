package com.peter.cityblocks.items;

import org.jetbrains.annotations.Nullable;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SignalLinker extends Item {

    public static final String NAME = "signal_linker";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    public static final Item ITEM = Registry.register(BuiltInRegistries.ITEM, ID, new SignalLinker(new Properties().setId(Items.irk(ID))));

    public static final DataComponentType<BlockPos> LINKED_CONTROLLER_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE, CityBlocks.identifier("linked_controller"),
            DataComponentType.<BlockPos>builder().persistent(BlockPos.CODEC).build());

    public SignalLinker(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        SignalControllerBlockEntity controller = getLinkedController(world, user.getItemInHand(hand));
        if (!world.isClientSide && controller != null) {
            controller.cycle(user);
        }
        return super.use(world, user, hand);
    }

    

    @Nullable
    public static SignalControllerBlockEntity getLinkedController(Level world, ItemStack stack) {
        if (!stack.has(LINKED_CONTROLLER_COMPONENT))
            return null;
        BlockPos linkedPos = stack.get(LINKED_CONTROLLER_COMPONENT);
        BlockEntity be = world.getBlockEntity(linkedPos);
        if (be instanceof SignalControllerBlockEntity)
            return (SignalControllerBlockEntity) be;
        stack.remove(LINKED_CONTROLLER_COMPONENT);
        return null;
    }

    public static void linkController(ItemStack stack, SignalControllerBlockEntity controller) {
        stack.set(LINKED_CONTROLLER_COMPONENT, controller.getBlockPos());
    }
    public static void linkController(ItemStack stack, BlockPos pos) {
        stack.set(LINKED_CONTROLLER_COMPONENT, pos);
    }

    public static boolean isLinked(ItemStack stack, BlockPos pos) {
        if (!stack.has(LINKED_CONTROLLER_COMPONENT)) {
            return false;
        }
        return stack.get(LINKED_CONTROLLER_COMPONENT).equals(pos);
    }

    public static void unLinkController(ItemStack stack) {
        stack.remove(LINKED_CONTROLLER_COMPONENT);
    }

}
