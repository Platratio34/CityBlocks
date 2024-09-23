package com.peter.cityblocks.blocks;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

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

    public static final String NAME = "celling_light";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final VariantPartialBlock BLOCK = new CellingLightBlock(new VariantSettings().setVariants(2).nonOpaque().luminance((state) -> {
                return 15;
            }));
    public static final BlockItem ITEM = BLOCK.item;

    public static final MapCodec<CellingLightBlock> CODEC = createCodec(CellingLightBlock::new);

    protected CellingLightBlock(Settings settings) {
        super(settings, NAME, SHAPES);
    }

}
