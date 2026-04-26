package com.peter.cityblocks.blocks;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockSetType.PressurePlateSensitivity;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.peter.cityblocks.CityBlocks;

public class SlidingDoorBlock extends DoorBlock {

    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_CLOSED = Shapes.rotateHorizontal(Block.boxZ(16.0, 13.0, 16.0));
    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_OPEN_RIGHT = Shapes.rotateHorizontal(Shapes.box(14f/16f, 0f/16f, 12f/16f, 30f/16f, 16f/16f, 15f/16f));
    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_OPEN = Shapes.rotateHorizontal(Shapes.box(-14f/16f, 0f/16f, 12f/16f, 2f/16f, 16f/16f, 15f/16f));

    public static final String NAME = "sliding_door";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    public static final BlockSetType BLOCK_SET_TYPE = new BlockSetType(
            "sliding_door",
            true,
            true,
            true,
            PressurePlateSensitivity.EVERYTHING,
            SoundType.IRON, 
			SoundEvents.IRON_DOOR_CLOSE,
			SoundEvents.IRON_DOOR_OPEN,
			SoundEvents.IRON_TRAPDOOR_CLOSE,
			SoundEvents.IRON_TRAPDOOR_OPEN,
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
			SoundEvents.STONE_BUTTON_CLICK_OFF,
			SoundEvents.STONE_BUTTON_CLICK_ON
    );
    public static final SlidingDoorBlock BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new SlidingDoorBlock(BLOCK_SET_TYPE, Properties.of().noOcclusion().setId(Blocks.brk(ID))
                    .mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY)));

    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID,
            new Item.Properties().setId(Blocks.irk(ID)));

    public SlidingDoorBlock(BlockSetType type, Properties settings) {
        super(type, settings);
    }
    
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (!state.getValue(OPEN)) {
            return SHAPES_BY_DIRECTION_CLOSED.get(direction);
        }
        if (state.getValue(HINGE) == DoorHingeSide.RIGHT) {
            return SHAPES_BY_DIRECTION_OPEN_RIGHT.get(direction);
        }
        return SHAPES_BY_DIRECTION_OPEN.get(direction);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        boolean o1 = state.getValue(OPEN);
        InteractionResult result = super.useWithoutItem(state, world, pos, player, hit);
        if (world.isClientSide) {
            return result;
        }
        // CityBlocks.LOGGER.info("Test?");
        if (result == InteractionResult.SUCCESS) { // state changed
            // CityBlocks.LOGGER.info("Sliding door state change");
            Direction dir = state.getValue(FACING);
            boolean right = state.getValue(HINGE) == DoorHingeSide.RIGHT;
            BlockPos checkPos = switch (dir) {
                case NORTH -> pos.offset(right ? -1 : 1, 0, 0);
                case SOUTH -> pos.offset(right ? 1 : -1, 0, 0);
                case EAST -> pos.offset(0, 0, right ? -1 : 1);
                case WEST -> pos.offset(0, 0, right ? 1 : -1);
                default -> pos;
            };
            // CityBlocks.LOGGER.info("Checking {}", checkPos);
            BlockState s2 = world.getBlockState(checkPos);
            if (!(s2.getBlock() instanceof SlidingDoorBlock)) {
                return result;
            }
            if (s2.getValue(FACING) == dir && (s2.getValue(HINGE) == DoorHingeSide.RIGHT) != right) {
                setOpen(player, world, s2, checkPos, !o1);
            }
        }
        return result;
    }

}
