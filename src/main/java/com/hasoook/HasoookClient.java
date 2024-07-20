package com.hasoook;

import com.hasoook.commands.NbtCommand;
import com.hasoook.eitity.ModEntities;
import com.hasoook.eitity.clint.CattivaRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class HasoookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CATTIVA, CattivaRenderer::new);
        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, registryAccess, environment) -> NbtCommand.register(dispatcher))
        );
    }
}
