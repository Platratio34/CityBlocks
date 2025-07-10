package com.peter.cityblocks.blocks;

import java.util.Map;

import com.peter.cityblocks.CityBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.BlockSetType.ActivationRule;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SlidingDoorBlock extends DoorBlock {

    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_CLOSED = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidZShape(16.0, 13.0, 16.0));
    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_OPEN_RIGHT = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.cuboid(14f/16f, 1f/16f, 12f/16f, 30f/16f, 16f/16f, 15f/16f));
    private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION_OPEN = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.cuboid(-14f/16f, 1f/16f, 12f/16f, 2f/16f, 16f/16f, 15f/16f));

    public static final String NAME = "sliding_door";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final BlockSetType BLOCK_SET_TYPE = new BlockSetType(
            "sliding_door",
            true,
            true,
            true,
            ActivationRule.EVERYTHING,
            BlockSoundGroup.IRON, 
			SoundEvents.BLOCK_IRON_DOOR_CLOSE,
			SoundEvents.BLOCK_IRON_DOOR_OPEN,
			SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
			SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
			SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON,
			SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,
			SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON
    );
    public static final SlidingDoorBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new SlidingDoorBlock(BLOCK_SET_TYPE, new Settings().nonOpaque().registryKey(Blocks.brk(ID))
                    .mapColor(MapColor.IRON_GRAY).pistonBehavior(PistonBehavior.DESTROY)));

    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID,
            new Item.Settings().registryKey(Blocks.irk(ID)));

    public SlidingDoorBlock(BlockSetType type, Settings settings) {
        super(type, settings);
    }
    
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (!state.get(OPEN)) {
            return SHAPES_BY_DIRECTION_CLOSED.get(direction);
        }
        if (state.get(HINGE) == DoorHinge.RIGHT) {
            return SHAPES_BY_DIRECTION_OPEN_RIGHT.get(direction);
        }
        return SHAPES_BY_DIRECTION_OPEN.get(direction);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean o1 = state.get(OPEN);
        ActionResult result = super.onUse(state, world, pos, player, hit);
        if (world.isClient) {
            return result;
        }
        CityBlocks.LOGGER.info("Test?");
        if (result == ActionResult.SUCCESS) { // state changed
            CityBlocks.LOGGER.info("Sliding door state change");
            Direction dir = state.get(FACING);
            boolean right = state.get(HINGE) == DoorHinge.RIGHT;
            BlockPos checkPos = switch (dir) {
                case NORTH -> pos.add(right ? -1 : 1, 0, 0);
                case SOUTH -> pos.add(right ? 1 : -1, 0, 0);
                case EAST -> pos.add(0, 0, right ? -1 : 1);
                case WEST -> pos.add(0, 0, right ? 1 : -1);
                default -> pos;
            };
            CityBlocks.LOGGER.info("Checking {}", checkPos);
            BlockState s2 = world.getBlockState(checkPos);
            if (!(s2.getBlock() instanceof SlidingDoorBlock)) {
                return result;
            }
            if (s2.get(FACING) == dir && (s2.get(HINGE) == DoorHinge.RIGHT) != right) {
                setOpen(player, world, s2, checkPos, !o1);
            }
        }
        return result;
    }

}
