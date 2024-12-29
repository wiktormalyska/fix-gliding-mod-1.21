package com.l33tfox.gliding;


/*
Duck interface to allow mixin to add accessible fields and methods
 */

public interface PlayerEntityDuck {

    public boolean isGliding();
    public void setIsGliding(boolean isPlayerGliding);
    public boolean isActivatingGlider();
    public void setIsActivatingGlider(boolean isPlayerActivatingGlider);

}
