package me.shinsu.mH.actionEvents.sphere;


import lombok.Getter;
import lombok.Setter;
import me.shinsu.mH.MH;
import org.bukkit.Location;

public class Sphere {
    @Getter
    private final MH plugin;
    @Getter
    private final int radiusOfSphere;
    @Getter
    private final Location locationOfSphere;
    @Getter
    private final SphereEffectType sphereEffectType;
    @Getter
    private final long durationOfSphereEffect;
    @Getter
    @Setter
    private boolean SphereActive;

    public Sphere(MH plugin,int radiusOfSphere, Location locationOfSphere, SphereEffectType sphereEffectType, long durationOfSphereEffect){
        this.plugin = plugin;
        this.durationOfSphereEffect = durationOfSphereEffect;
        this.sphereEffectType= sphereEffectType;
        this.locationOfSphere = locationOfSphere;
        this.radiusOfSphere =radiusOfSphere;
    }

    public void Show(){
        SphereActive = true;
        new SphereTask(getPlugin(), this).start();
    }

}
