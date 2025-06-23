package me.shinsu.mH.actionEvents.sphere;

import me.shinsu.mH.MH;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SphereManager {

    private final MH plugin;

    private List<Sphere> spheres = new ArrayList<>();

    public SphereManager(MH plugin){
        this.plugin = plugin;
    }

    public void addSphere(Sphere sphere){
        spheres.add(sphere);
    }
    public void removeSphere(Sphere sphere){
        spheres.remove(sphere);
    }
    public List<Sphere>getActiveSphere(){
        return spheres.stream().filter(Sphere::isSphereActive).toList();
    }





}
