package com.resimulators.simukraft.packets;

import com.resimulators.simukraft.common.entity.sim.SimEntity;
import com.resimulators.simukraft.common.world.SavedWorldData;
import com.resimulators.simukraft.common.tileentity.ITile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class SimFireRequest implements IMessage {
    private UUID playerId;
    private UUID simId;
    private BlockPos pos;

    public SimFireRequest() {
    }

    public SimFireRequest(UUID playerId, UUID simId, BlockPos pos) {
        this.playerId = playerId;
        this.simId = simId;
        this.pos = pos;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeUniqueId(simId);
        buf.writeUniqueId(playerId);
    }

    @Override
    public void fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.simId = buf.readUniqueId();
        this.playerId = buf.readUniqueId();
    }

    @Nullable
    @Override
    public LogicalSide getExecutionSide() {
        return LogicalSide.SERVER;
    }

    @Override
    public void onExecute(NetworkEvent.Context ctxIn, boolean isLogicalServer) {
        if (ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(playerId) != null) {
            PlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(playerId);
            if (player != null){
            SavedWorldData data = SavedWorldData.get(player.world);
            int id = data.getFactionWithPlayer(player.getUniqueID()).getId();
            SimEntity sim = (SimEntity) ((ServerWorld)player.world).getEntityByUuid(simId);
            if (sim != null){
                ITile tile =((ITile)player.world.getTileEntity(pos));
                if (tile != null){
                    tile.setHired(false);
                System.out.println(player.world.getTileEntity(pos));
                data.fireSim(id, (SimEntity) ((ServerWorld)player.world).getEntityByUuid(simId));
                tile.setSimId(null);
                if (sim.getJob() != null){
                sim.getJob().removeJobAi();
                sim.setJob(null);
                sim.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);

                }

                    }
                }
                data.getFaction(id).sendPacketToFaction(new SimFirePacket(id, 0, pos));
            }

        }
    }
}
