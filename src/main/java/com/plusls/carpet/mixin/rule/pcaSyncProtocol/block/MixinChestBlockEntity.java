package com.plusls.carpet.mixin.rule.pcaSyncProtocol.block;

import com.plusls.carpet.ModInfo;
import com.plusls.carpet.PcaSettings;
import com.plusls.carpet.network.PcaSyncProtocol;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

// 由于陷阱箱继承自箱子，因此不用 mixin 陷阱箱
// implements ChestAnimationProgress 会出错 不知道为啥
@Mixin(ChestBlockEntity.class)
public abstract class MixinChestBlockEntity extends LootableContainerBlockEntity {

    protected MixinChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(
                blockEntityType
                //#if MC >= 11700
                , blockPos, blockState
                //#endif
        );
    }

    /**
     * @see MixinBlockEntity#pca$onMarkDirty()
     */
    @SuppressWarnings({"unused", "MissingUnique"})
    protected void pca$onMarkDirty() {
        if (PcaSettings.pcaSyncProtocol && PcaSyncProtocol.syncBlockEntityToClient(this)) {
            ModInfo.LOGGER.debug("update ChestBlockEntity: {}", this.pos);
        }
    }
}
