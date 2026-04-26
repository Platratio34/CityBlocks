package com.peter.cityblocks.blocks.signs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.peter.cityblocks.networking.BlockPosScreenPacket;
import com.mojang.serialization.Codec;
import com.peter.cityblocks.gui.CustomSignScreenHandler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CustomSignBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    private final Component screenName;

    private int variant = 0;
    private String[] text = new String[0];
    private ArrayList<SignSticker> stickers = new ArrayList<SignSticker>();

    public CustomSignBlockEntity(BlockEntityType<? extends CustomSignBlockEntity> type, BlockPos pos, BlockState state,
            Component screenName) {
        super(type, pos, state);
        this.screenName = screenName;
    }
    
    public CustomSignBlock getBlock() {
        return (CustomSignBlock) getBlockState().getBlock();
    }

    public int getVariant() {
        int maxV = getMaxVariant();
        if (variant > maxV) {
            variant = maxV;
            setChanged();
        }
        return variant;
    }

    public void setVariant(int variant) {
        // if (world.isClient) {
        //     CityBlocksClientNetworking.sendCustomSignUpdatePacket(this, variant);
        // }
        boolean changed = this.variant != variant;
        this.variant = variant;
        int maxV = getMaxVariant();
        if (variant > maxV) {
            variant = maxV;
            changed = true;
        }
        if (changed) {
            text = new String[getMaxTextLines()];
            for (int i = 0; i < text.length; i++) {
                text[i] = "";
            }
            TextLineInfo[] textInfo = getTextInfo();
            for (int i = 0; i < textInfo.length; i++) {
                if (textInfo[i].defaultText != null) {
                    text[textInfo[i].lineN] = textInfo[i].defaultText;
                }
            }
            setChanged();
        }
    }

    public int getMaxVariant() {
        return getBlock().getMaxVariant(getBlockState());
    }

    public String getTexture() {
        return getBlock().getTexture(getBlockState(), getVariant());
    }

    public String[] getText() {
        if (text.length != getMaxTextLines()) {
            text = new String[getMaxTextLines()];
            for (int i = 0; i < text.length; i++) {
                text[i] = "";
            }
            TextLineInfo[] textInfo = getTextInfo();
            for (int i = 0; i < textInfo.length; i++) {
                if (textInfo[i].defaultText != null) {
                    text[textInfo[i].lineN] = textInfo[i].defaultText;
                }
            }
            setChanged();
        }
        return text;
    }

    public void setText(String[] text) {
        int nLines = getMaxTextLines();
        this.text = new String[nLines];
        if (text.length > nLines) {
            for (int i = 0; i < nLines; i++) {
                this.text[i] = text[i];
            }
        } else {
            for (int i = 0; i < text.length; i++) {
                this.text[i] = text[i];
            }
            if (text.length < nLines) {
                for (int i = text.length; i < nLines; i++) {
                    this.text[i] = "";
                }
                TextLineInfo[] textInfo = getTextInfo();
                for (int i = 0; i < textInfo.length; i++) {
                    if (textInfo[i].defaultText != null && textInfo[i].lineN >= text.length) {
                        text[textInfo[i].lineN] = textInfo[i].defaultText;
                    }
                }
            }
        }
        // if (world.isClient) {
        //     CityBlocksClientNetworking.sendCustomSignUpdatePacket(this, this.text);
        // }
        setChanged();
    }

    public String getLineText(int line) {
        if (line < 0 || line > getMaxTextLines()) {
            return "";
        }
        return text[line];
    }

    public void setLineText(int line, String text) {
        if (line < 0 || line > getMaxTextLines()) {
            return;
        }
        this.text[line] = text;
        // if (world.isClient) {
        //     CityBlocksClientNetworking.sendCustomSignUpdatePacket(this, this.text);
        // }
    }

    public int getMaxTextLines() {
        return getBlock().getMaxTextLines(getBlockState(), getVariant());
    }

    public int getMaxTextLines(int texture) {
        return getBlock().getMaxTextLines(getBlockState(), texture);
    }

    public SignSticker[] getStickers() {
        return stickers.toArray(new SignSticker[0]);
    }

    public Vector3d getTexturePosition() {
        return getBlock().getTexturePosition(getBlockState(), getVariant());
    }
    public Vector3d getTextureSize() {
        return getBlock().getTextureSize(getBlockState(), getVariant());
    }
    public Vector2f getTextureUVSize() {
        return getBlock().getTextureUVSize(getBlockState(), getVariant());
    }

    public Direction getFacing() {
        return getBlock().getFacing(getBlockState());
    }

    public String[] getVariantNames() {
        return getBlock().getVariantNames(getBlockState());
    }

    public TextLineInfo[] getTextInfo() {
        return getBlock().getTextInfo(getBlockState(), getVariant());
    }

    @Override
    public Component getDisplayName() {
        return screenName;
    }

    public static final String NBT_VARIANT = "variant";
    public static final String NBT_TEXT = "text";
    public static final String NBT_STICKERS = "stickers";
    public static final String NBT_BLOCK_STATE = "block_state";
    
    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.store(NBT_BLOCK_STATE, CompoundTag.CODEC, getBlock().getNbt(getBlockState(), variant));
        view.putInt(NBT_VARIANT, variant);
        if (text.length > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < text.length; i++) {
                list.add(text[i]);
            }
            view.store(NBT_TEXT, Codec.list(Codec.STRING), list);
        }
        // if (stickers.size() > 0) {
        //     view.put(NBT_STICKERS, Codec.list(SignSticker.CODEC), stickers);
        // }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        
        variant = view.getIntOr(NBT_VARIANT, 0);
        int maxV = getMaxVariant();
        if (variant > maxV) {
            variant = maxV;
        }
        
        Optional<List<String>> opt = view.read(NBT_TEXT, Codec.list(Codec.STRING));
        if (!opt.isEmpty()) {
            List<String> lines = opt.get();
            int nLines = getMaxTextLines();
            text = new String[nLines];
            if (lines.size() > nLines) {
                for (int i = 0; i < nLines; i++) {
                    text[i] = lines.get(i);
                }
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    text[i] = lines.get(i);
                }
                for (int i = lines.size(); i < nLines; i++) {
                    text[i] = "";
                }
                TextLineInfo[] textInfo = getTextInfo();
                for (int i = 0; i < textInfo.length; i++) {
                    if (textInfo[i].defaultText != null && textInfo[i].lineN >= lines.size()) {
                        text[textInfo[i].lineN] = textInfo[i].defaultText;
                    }
                }
            }
        }
        // if (nbt.contains(NBT_STICKERS)) {
        //     NbtList stickerList = (NbtList) nbt.get(NBT_STICKERS);
        //     stickers = new ArrayList<SignSticker>();
        //     for (int i = 0; i < stickerList.size(); i++) {
        //         stickers.add(SignSticker.fromNbt(stickerList.getCompound(i)));
        //     }
        // }
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        super.setChanged();
    }

    public void markDirty(BlockState state) {
        level.sendBlockUpdated(worldPosition, getBlockState(), state, Block.UPDATE_CLIENTS);
        super.setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new CustomSignScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayer)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayer player) {
        return new BlockPosScreenPacket(this.worldPosition);
    }

    public boolean isTextOnly() {
        return getBlock().isTextOnly(getBlockState(), variant);
    }

    public static CompoundTag getNbtFromStack(ItemStack stack) {
        CustomData nbtComp = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        if (!nbtComp.isEmpty()) {
            return nbtComp.copyTag();
        }
        return null;
    }
    public static CompoundTag getBlockStateNbtFromStack(ItemStack stack) {
        CustomData nbtComp = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        if (!nbtComp.isEmpty()) {
            CompoundTag nbt = nbtComp.copyTag();
            return nbt.getCompound(NBT_BLOCK_STATE).orElse(null);
        }
        return null;
    }

}
