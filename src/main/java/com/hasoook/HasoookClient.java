package com.hasoook;

import com.hasoook.eitity.ModEntities;
import com.hasoook.eitity.clint.CattivaRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HasoookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CATTIVA, CattivaRenderer::new);

    }
}
