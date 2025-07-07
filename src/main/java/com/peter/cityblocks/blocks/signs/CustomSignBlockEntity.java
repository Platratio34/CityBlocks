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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class CustomSignBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    private final Text screenName;

    private int variant = 0;
    private String[] text = new String[0];
    private ArrayList<SignSticker> stickers = new ArrayList<SignSticker>();

    public CustomSignBlockEntity(BlockEntityType<? extends CustomSignBlockEntity> type, BlockPos pos, BlockState state,
            Text screenName) {
        super(type, pos, state);
        this.screenName = screenName;
    }
    
    public CustomSignBlock getBlock() {
        return (CustomSignBlock) getCachedState().getBlock();
    }

    public int getVariant() {
        int maxV = getMaxVariant();
        if (variant > maxV) {
            variant = maxV;
            markDirty();
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
            markDirty();
        }
    }

    public int getMaxVariant() {
        return getBlock().getMaxVariant(getCachedState());
    }

    public String getTexture() {
        return getBlock().getTexture(getCachedState(), getVariant());
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
            markDirty();
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
        markDirty();
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
        return getBlock().getMaxTextLines(getCachedState(), getVariant());
    }

    public int getMaxTextLines(int texture) {
        return getBlock().getMaxTextLines(getCachedState(), texture);
    }

    public SignSticker[] getStickers() {
        return stickers.toArray(new SignSticker[0]);
    }

    public Vector3d getTexturePosition() {
        return getBlock().getTexturePosition(getCachedState(), getVariant());
    }
    public Vector3d getTextureSize() {
        return getBlock().getTextureSize(getCachedState(), getVariant());
    }
    public Vector2f getTextureUVSize() {
        return getBlock().getTextureUVSize(getCachedState(), getVariant());
    }

    public Direction getFacing() {
        return getBlock().getFacing(getCachedState());
    }

    public String[] getVariantNames() {
        return getBlock().getVariantNames(getCachedState());
    }

    public TextLineInfo[] getTextInfo() {
        return getBlock().getTextInfo(getCachedState(), getVariant());
    }

    @Override
    public Text getDisplayName() {
        return screenName;
    }

    public static final String NBT_VARIANT = "variant";
    public static final String NBT_TEXT = "text";
    public static final String NBT_STICKERS = "stickers";
    public static final String NBT_BLOCK_STATE = "block_state";
    
    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put(NBT_BLOCK_STATE, NbtCompound.CODEC, getBlock().getNbt(getCachedState(), variant));
        view.putInt(NBT_VARIANT, variant);
        if (text.length > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < text.length; i++) {
                list.add(text[i]);
            }
            view.put(NBT_TEXT, Codec.list(Codec.STRING), list);
        }
        // if (stickers.size() > 0) {
        //     view.put(NBT_STICKERS, Codec.list(SignSticker.CODEC), stickers);
        // }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        
        variant = view.getInt(NBT_VARIANT, 0);
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
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        super.markDirty();
    }

    public void markDirty(BlockState state) {
        world.updateListeners(pos, getCachedState(), state, Block.NOTIFY_LISTENERS);
        super.markDirty();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CustomSignScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayerEntity)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosScreenPacket(this.pos);
    }

    public boolean isTextOnly() {
        return getBlock().isTextOnly(getCachedState(), variant);
    }

    public static NbtCompound getNbtFromStack(ItemStack stack) {
        NbtComponent nbtComp = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.DEFAULT);
        if (!nbtComp.isEmpty()) {
            return nbtComp.copyNbt();
        }
        return null;
    }
    public static NbtCompound getBlockStateNbtFromStack(ItemStack stack) {
        NbtComponent nbtComp = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.DEFAULT);
        if (!nbtComp.isEmpty()) {
            NbtCompound nbt = nbtComp.copyNbt();
            return nbt.getCompound(NBT_BLOCK_STATE).orElse(null);
        }
        return null;
    }

}
