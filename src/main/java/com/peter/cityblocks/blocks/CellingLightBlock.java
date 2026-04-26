package com.peter.cityblocks.blocks;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CellingLightBlock extends VariantPartialBlock {

    public static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                cube(2, 14, 0, 12, 6 ,16, Direction.NORTH),
                cube(2, 18, 0, 12, 6 ,16, Direction.NORTH),
            },
            new VoxelShape[] {
                cube(2, 14, 0, 12, 6 ,16, Direction.EAST),
                cube(2, 18, 0, 12, 6 ,16, Direction.EAST),
            },
            new VoxelShape[] {
                cube(2, 14, 0, 12, 6 ,16, Direction.SOUTH),
                cube(2, 18, 0, 12, 6 ,16, Direction.SOUTH),
            },
            new VoxelShape[] {
                cube(2, 14, 0, 12, 6 ,16, Direction.WEST),
                cube(2, 18, 0, 12, 6 ,16, Direction.WEST),
            }
    };

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("celling_light_0"),
            Blocks.blockId("celling_light_1"),
    };

    public static final MapCodec<CellingLightBlock> CODEC = simpleCodec(CellingLightBlock::new);

    public static final String NAME = "celling_light";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    public static final VariantPartialBlock BLOCK = new CellingLightBlock(new VariantSettings().setVariants(2).noOcclusion().lightLevel((state) -> {
                return 15;
            }));
    public static final BlockItem ITEM = BLOCK.item;

    protected CellingLightBlock(Properties settings) {
        super(settings, NAME, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(2, 14, 0, 12, 6 ,16, direction);
            case 1 -> cube(2, 18, 0, 12, 6 ,16, direction);
        
            default -> Shapes.block();
        };
    }

}
