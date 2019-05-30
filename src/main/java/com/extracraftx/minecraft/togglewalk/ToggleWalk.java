package com.extracraftx.minecraft.togglewalk;

import com.extracraftx.minecraft.togglewalk.interfaces.ToggleableKeyBinding;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

public class ToggleWalk implements ClientModInitializer {

    KeyBinding[] bindings;

    @Override
    public void onInitializeClient() {
        ClientTickCallback.EVENT.register((mc)->{
            onTick(mc);
        });
    }

    public void onTick(MinecraftClient mc){
        if(bindings == null){
            bindings = new KeyBinding[]{mc.options.keyForward, mc.options.keySneak};
        }
        for(KeyBinding kb : bindings){
            if(kb.wasPressed()){
                ToggleableKeyBinding tkb = (ToggleableKeyBinding)kb;
                tkb.toggle();
            }
        }
    }

}