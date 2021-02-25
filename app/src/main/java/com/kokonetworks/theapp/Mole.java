package com.kokonetworks.theapp;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class Mole {
    private final Field field;

    private int currentLevel = 0;
    private long startTimeForLevel;
    private final int[] LEVELS = new int[]{1000,900,800,700,600,500,400,300,200,100};
    private final long LEVEL_DURATION_MS = 10000;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> future;

    public Mole(Field field) {
        this.field = field;
    }

    public void startHopping(){
        field.getListener().onLevelChange(getCurrentLevel());
        startTimeForLevel = System.currentTimeMillis();

        future = scheduledExecutorService.scheduleAtFixedRate(() -> {
            field.setActive(nextHole());

            if(System.currentTimeMillis()-startTimeForLevel >= LEVEL_DURATION_MS && getCurrentLevel() < LEVELS.length){
                nextLevel();
            }
        },LEVELS[currentLevel], LEVELS[currentLevel], TimeUnit.MILLISECONDS);
    }

    public void stopHopping(){
        future.cancel(false);
    }

    private void nextLevel(){
        currentLevel++;
        future.cancel(false);
        startHopping();
    }

    public int getCurrentLevel(){
        return currentLevel+1;
    }

    private int nextHole(){
        int hole = new Random().nextInt(field.totalCircles()-1);
        if(hole == field.getCurrentCircle()){
            return nextHole();
        }
        return hole;
    }
}
