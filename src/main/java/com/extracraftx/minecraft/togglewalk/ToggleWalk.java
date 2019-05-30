package com.extracraftx.minecraft.togglewalk;

import java.util.HashMap;

import com.extracraftx.minecraft.togglewalk.interfaces.ToggleableKeyBinding;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

public class ToggleWalk implements ModInitializer {

    boolean walking = false;
    boolean sneaking = false;

    KeyBinding[] bindings;

    HashMap<KeyBinding, Boolean> toggles = new HashMap<>();

    @Override
    public void onInitialize() {
        System.out.println("OnInitClient");
        ClientTickCallback.EVENT.register((mc)->{
            onTick(mc);
        });
    }

    public void onTick(MinecraftClient mc){
        // System.out.println("OnTick");
        if(bindings == null){
            System.out.println("BindingsArrayUpdated");
            bindings = new KeyBinding[]{mc.options.keyForward, mc.options.keySneak};
        }
        for(KeyBinding kb : bindings){
            if(!toggles.containsKey(kb)){
                System.out.println("KeyBind " + kb.getLocalizedName() + " added to map");
                toggles.put(kb, false);
            }
            if(kb.wasPressed()){
                System.out.println("KeyBind " + kb.getName() + " was pressed/toggled " + toggles.get(kb));
                toggles.put(kb, !toggles.get(kb));
                ToggleableKeyBinding tkb = (ToggleableKeyBinding)kb;
                tkb.setToggled(toggles.get(kb));
            }
            // System.out.println(kb.getLocalizedName() + " isPressed " + kb.isPressed() + " in " + mc.getClass().getName());
        }
    }

}