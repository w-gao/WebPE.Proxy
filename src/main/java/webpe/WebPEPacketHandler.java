package webpe;

import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;

public class WebPEPacketHandler implements BedrockPacketHandler {

    private final WebPEProxy proxy;
    private final String identifier;

    public WebPEPacketHandler(WebPEProxy proxy, String identifier) {

        this.proxy = proxy;
        this.identifier = identifier;
    }

    public boolean handle(DisconnectPacket packet) {
        System.out.println("Disconnect!");

        return true;
    }

    public boolean handle(ResourcePacksInfoPacket packet) {
        System.out.println(packet);
        return true;
    }

    public boolean handle(ResourcePackStackPacket packet) {
        System.out.println(packet);
        return true;
    }

    public boolean handle(PlayStatusPacket packet) {
        System.out.println(packet);
        return true;
    }

    public boolean handle(ServerToClientHandshakePacket packet) {
        System.out.println(packet);
        return true;
    }

    public boolean handle(ClientToServerHandshakePacket packet) {

        System.out.println(packet);
        return true;
    }


    public boolean handle(AdventureSettingsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AnimatePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AnvilDamagePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AvailableEntityIdentifiersPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BlockEntityDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BlockPickRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BookEditPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ClientCacheBlobStatusPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ClientCacheMissResponsePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ClientCacheStatusPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CommandBlockUpdatePacket packet) {
        return false;
    }

    public boolean handle(CommandRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CompletedUsingItemPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ContainerClosePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CraftingEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EducationSettingsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EmotePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EntityEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EntityFallPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EntityPickRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(EventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(InteractPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(InventoryContentPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(InventorySlotPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(InventoryTransactionPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ItemFrameDropItemPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LabTablePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LecternUpdatePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelEventGenericPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelSoundEvent1Packet packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelSoundEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LoginPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MapInfoRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MobArmorEquipmentPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MobEquipmentPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ModalFormResponsePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MoveEntityAbsolutePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MovePlayerPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MultiplayerSettingsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(NetworkStackLatencyPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PhotoTransferPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerActionPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerAuthInputPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerHotbarPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerInputPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerSkinPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PurchaseReceiptPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(RequestChunkRadiusPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ResourcePackChunkRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ResourcePackClientResponsePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(RiderJumpPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ServerSettingsRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetDefaultGameTypePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetLocalPlayerAsInitializedPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetPlayerGameTypePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SubClientLoginPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddBehaviorTreePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddEntityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddHangingEntityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddItemEntityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddPaintingPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AddPlayerPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AvailableCommandsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BlockEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BossEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CameraPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ChangeDimensionPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ChunkRadiusUpdatedPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ClientboundMapItemDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CommandOutputPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ContainerOpenPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ContainerSetDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(CraftingDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ExplodePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelChunkPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(GameRulesChangedPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(GuiDataPickItemPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(HurtArmorPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(AutomationClientConnectPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MapCreateLockedCopyPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MobEffectPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ModalFormRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(MoveEntityDeltaPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(NetworkSettingsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(NpcRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(OnScreenTextureAnimationPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlayerListPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(PlaySoundPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(RemoveEntityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(RemoveObjectivePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ResourcePackChunkDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ResourcePackDataInfoPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(RespawnPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ScriptCustomEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ServerSettingsResponsePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetCommandsEnabledPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetDifficultyPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetDisplayObjectivePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetEntityDataPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetEntityLinkPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetEntityMotionPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetHealthPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetLastHurtByPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetScoreboardIdentityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetScorePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetSpawnPositionPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetTimePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SettingsCommandPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SetTitlePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ShowCreditsPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ShowProfilePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ShowStoreOfferPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SimpleEventPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SpawnExperienceOrbPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(SpawnParticleEffectPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(StartGamePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(StopSoundPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(StructureBlockUpdatePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(StructureTemplateDataExportRequestPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(StructureTemplateDataExportResponsePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(TakeItemEntityPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(TextPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(TickSyncPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(TransferPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateAttributesPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateBlockPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateBlockPropertiesPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateBlockSyncedPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateEquipPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateSoftEnumPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(UpdateTradePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(BiomeDefinitionListPacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(LevelSoundEvent2Packet packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(NetworkChunkPublisherUpdatePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(VideoStreamConnectPacket packet) {
        System.out.println(packet);
        return false;
    }

}
