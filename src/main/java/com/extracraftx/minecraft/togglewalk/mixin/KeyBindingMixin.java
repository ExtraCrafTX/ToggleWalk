package com.extracraftx.minecraft.togglewalk.mixin;

import com.extracraftx.minecraft.togglewalk.interfaces.ToggleableKeyBinding;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.options.KeyBinding;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements ToggleableKeyBinding{

    @Shadow
    private boolean pressed;
    
    private boolean toggled = false;

    @Override
    public void toggle() {
        toggled = !toggled;
    }

    @Override
    public void setToggled(boolean value) {
        toggled = value;
    }
    
    public boolean isPressed() {
        return pressed || toggled;
    }

}