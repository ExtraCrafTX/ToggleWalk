package com.extracraftx.minecraft.togglewalk;

import java.util.Map;

import com.extracraftx.minecraft.togglewalk.config.Config;
import com.extracraftx.minecraft.togglewalk.config.Config.Toggle;
import com.extracraftx.minecraft.togglewalk.interfaces.ToggleableKeyBinding;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

public class ToggleWalk implements ClientModInitializer {

    private static Map<String, KeyBinding> keysById;
    private KeyBinding[] bindings;
    private KeyBinding[] opposites;

    @Override
    public void onInitializeClient() {
        ClientTickCallback.EVENT.register((mc)->{
            onTick(mc);
        });
    }

    public void onTick(MinecraftClient mc){
        if(bindings == null){
            keysById = ((ToggleableKeyBinding)mc.options.keyForward).getKeysIdMap();
            load(mc);
        }
        for(int i = 0; i < bindings.length; i++){
            KeyBinding kb = bindings[i];
            KeyBinding opp = opposites[i];
            if(kb.wasPressed()){
                ToggleableKeyBinding tkb = (ToggleableKeyBinding)kb;
                tkb.toggle();
            }
            if(opp != null && opp.wasPressed()){
                ToggleableKeyBinding tkb = (ToggleableKeyBinding)kb;
                tkb.setToggled(false);
            }
        }
    }

    public void load(MinecraftClient mc){
        Config.loadConfigs();
        bindings = new KeyBinding[Config.INSTANCE.toggles.length];
        opposites = new KeyBinding[bindings.length];
        for(int i = 0; i < bindings.length; i++){
            Toggle toggle = Config.INSTANCE.toggles[i];
            bindings[i] = keysById.get("key." + toggle.toggle);
            opposites[i] = keysById.get("key." + toggle.untoggle);
        }
    }

}