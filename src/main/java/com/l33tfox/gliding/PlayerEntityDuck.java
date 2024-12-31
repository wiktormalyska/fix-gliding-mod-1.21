package com.l33tfox.gliding;


/*
Duck interface to allow mixin to add accessible fields and methods
 */

public interface PlayerEntityDuck {

    public boolean gliding$isGliding();
    public void gliding$setIsGliding(boolean isPlayerGliding);
    public boolean gliding$isActivatingGlider();
    public void gliding$setIsActivatingGlider(boolean isPlayerActivatingGlider);

}
