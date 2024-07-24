package com.hasoook;

import com.hasoook.commands.NbtCommand;
import com.hasoook.eitity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class HasoookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, registryAccess, environment) -> NbtCommand.register(dispatcher))
        );
    }
}
