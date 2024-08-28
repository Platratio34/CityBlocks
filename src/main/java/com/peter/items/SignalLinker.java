package com.peter.items;

import org.jetbrains.annotations.Nullable;

import com.peter.CityBlocks;
import com.peter.blocks.signal.SignalControllerBlockEntity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalLinker extends Item {

    public static final String NAME = "signal_linker";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final Item ITEM = Registry.register(Registries.ITEM, ID, new SignalLinker(new Settings()));

    public static final ComponentType<BlockPos> LINKED_CONTROLLER_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE, CityBlocks.identifier("linked_controller"),
            ComponentType.<BlockPos>builder().codec(BlockPos.CODEC).build());

    public SignalLinker(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SignalControllerBlockEntity controller = getLinkedController(world, user.getStackInHand(hand));
        if (!world.isClient && controller != null) {
            controller.cycle(user);
        }
        return super.use(world, user, hand);
    }

    

    @Nullable
    public static SignalControllerBlockEntity getLinkedController(World world, ItemStack stack) {
        if (!stack.contains(LINKED_CONTROLLER_COMPONENT))
            return null;
        BlockPos linkedPos = stack.get(LINKED_CONTROLLER_COMPONENT);
        BlockEntity be = world.getBlockEntity(linkedPos);
        if (be instanceof SignalControllerBlockEntity)
            return (SignalControllerBlockEntity) be;
        stack.remove(LINKED_CONTROLLER_COMPONENT);
        return null;
    }

    public static void linkController(ItemStack stack, SignalControllerBlockEntity controller) {
        stack.set(LINKED_CONTROLLER_COMPONENT, controller.getPos());
    }
    public static void linkController(ItemStack stack, BlockPos pos) {
        stack.set(LINKED_CONTROLLER_COMPONENT, pos);
    }

    public static boolean isLinked(ItemStack stack, BlockPos pos) {
        if (!stack.contains(LINKED_CONTROLLER_COMPONENT)) {
            return false;
        }
        return stack.get(LINKED_CONTROLLER_COMPONENT).equals(pos);
    }

    public static void unLinkController(ItemStack stack) {
        stack.remove(LINKED_CONTROLLER_COMPONENT);
    }

}
