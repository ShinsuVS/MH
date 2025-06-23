package me.shinsu.mH.actionEvents.sphere;

import jakarta.xml.bind.annotation.XmlAnyAttribute;
import lombok.val;
import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.tasks.TaskTimer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SphereTask extends TaskTimer {

    private long time;
    private Sphere sphere;

    public SphereTask(MH plugin, Sphere sphere ) {
        super(plugin);
        this.time = sphere.getDurationOfSphereEffect();
        this.sphere = sphere;
    }

    @Override
    public void execute() {
        if(time == 0){
            sphere.setSphereActive(false);
            cancel();
        }
        else {
            ShowAnimation();
            val manager = getPlugin().getManager();
            manager.getActiveGames().forEach(
                    game ->
                    {
                        game.getSpeedRunners().forEach(speed ->{
                            if(isPlayerInRadius(speed.getPlayer(), sphere.getLocationOfSphere(), sphere.getRadiusOfSphere())){
                                speed.getPlayer().addPotionEffect(new PotionEffect(typeConverter(sphere.getSphereEffectType()), (int) sphere.getDurationOfSphereEffect() * 20, 5, false, false ));
                            }
                        });
                    }
            );
        }
        time--;
    }

    public void ShowAnimation(){
            Location center = sphere.getLocationOfSphere();
            int radius = 8;
            double appleSize = 1.5;
            double appleHeight = 0.15; // Фиксированная высота яблока

            // Цвета для золотого яблока
            Color goldColor = Color.fromRGB(255, 215, 0);
            Color stemColor = Color.fromRGB(139, 69, 19);

            new BukkitRunnable() {
                int tick = 0;
                final int totalTicks = 20;
                final double rotationSpeed = Math.PI / 8; // Скорость вращения яблока

                @Override
                public void run() {
                    if (tick >= totalTicks) {
                        this.cancel();
                        return;
                    }

                    double progress = tick / (double)totalTicks;
                    double time = 2 * Math.PI * progress;

                    // 1. 3D-спираль (вращается вокруг яблока)
                    for (int angleDegrees = 0; angleDegrees < 360; angleDegrees += 5) {
                        double angleRadians = Math.toRadians(angleDegrees);
                        double spiralProgress = (angleDegrees / 360.0 + progress) % 1.0;
                        double x = radius * Math.cos(angleRadians);
                        double z = radius * Math.sin(angleRadians);
                        double y = appleHeight + Math.sin(2 * Math.PI * spiralProgress) * 0.5;

                        sphere.getLocationOfSphere().getWorld().spawnParticle(
                                Particle.DUST,
                                center.clone().add(x, y, z),
                                1,
                                new Particle.DustOptions(Color.fromRGB(0, 255, 255), 1.0F)
                        );
                    }

                    // 2. 3D-модель золотого яблока (вращается на месте)
                    double rotation = time * rotationSpeed;

                    // Основное тело (сфера)
                    for (double phi = 0; phi <= Math.PI; phi += Math.PI/10) {
                        for (double theta = 0; theta <= 2*Math.PI; theta += Math.PI/10) {
                            double x = appleSize * Math.sin(phi) * Math.cos(theta);
                            double y = appleSize * Math.cos(phi) * 0.8;
                            double z = appleSize * Math.sin(phi) * Math.sin(theta);

                            // Вращение модели вокруг оси Y
                            Vector rotated = new Vector(
                                    x * Math.cos(rotation) - z * Math.sin(rotation),
                                    y,
                                    x * Math.sin(rotation) + z * Math.cos(rotation)
                            );

                            sphere.getLocationOfSphere().getWorld().spawnParticle(
                                    Particle.DUST,
                                    center.clone().add(rotated.getX(), appleHeight + 5 + rotated.getY(), rotated.getZ()),
                                    1,
                                    new Particle.DustOptions(goldColor, 1.2F)
                            );
                        }
                    }

                    // Стебель яблока
                    for (double y = 0; y <= appleSize*0.3; y += 0.2) {
                        double radiusStem = appleSize * 0.1 * (1 - y/(appleSize*0.3));
                        for (double angle = 0; angle < 2*Math.PI; angle += Math.PI/3) {
                            Vector rotated = new Vector(
                                    radiusStem * Math.cos(angle + rotation),
                                    appleSize * 0.8 + y,
                                    radiusStem * Math.sin(angle + rotation)
                            );

                            sphere.getLocationOfSphere().getWorld().spawnParticle(
                                    Particle.DUST,
                                    center.clone().add(rotated.getX(), appleHeight + 5 + rotated.getY(), rotated.getZ()),
                                    1,
                                    new Particle.DustOptions(stemColor, 0.8F)
                            );
                        }
                    }

                    // 3. Эффект золотого сияния
                    if (tick % 3 == 0) {
                        sphere.getLocationOfSphere().getWorld().spawnParticle(
                                Particle.GLOW,
                                center.clone().add(0, appleHeight + 5 + appleSize*0.5, 0),
                                3,
                                appleSize*0.3, appleSize*0.2, appleSize*0.3,
                                0
                        );
                    }

                    tick++;
                }
            }.runTaskTimer(getPlugin(), 0, 1);

        }

    public boolean isPlayerInRadius(Player player, Location center, double radius) {
        Location playerLoc = player.getLocation();

        // Игнорируем ось Y, сравниваем только X и Z
        double dx = playerLoc.getX() - center.getX();
        double dz = playerLoc.getZ() - center.getZ();

        // Проверяем расстояние по теореме Пифагора (без Y)
        double distanceSquared = dx * dx + dz * dz;

        return distanceSquared <= radius * radius;
    }

    public PotionEffectType typeConverter(SphereEffectType type){
        if(type.equals(SphereEffectType.HEAL)){
            return PotionEffectType.REGENERATION;
        }
        if(type.equals(SphereEffectType.DEVOUR)){
            return PotionEffectType.POISON;
        }
        return null;
    }



}



