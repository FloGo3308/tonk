package me.flogo.tonk.main;

public class GameLoop extends Thread{
    private static GameLoop INSTANCE = null;
    public static int TARGET_TPS = 20;
    public int tick = 0;
    public long lastTimeMS = 0;
    private int delta = 0;

    /**
     * The main gameloop, most of the logic goes in here.
     * It runs(/tries to run) 20 times a second.(20 ticks per second/TPS)
     */
    @Override
    public void run() {
        while (true) {
            pre();
            packet();
            post();
            while ((System.currentTimeMillis()-lastTimeMS)/1000F < 1F/TARGET_TPS) {;}
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
            if ((delta)/1000F != 1F/TARGET_TPS) {
                Tonk.LOGGER.error("BAD TIMING! RUNNING " + (delta-1000/TARGET_TPS)/1000F + "SECONDS  BEHIND");
            }
            lastTimeMS = System.currentTimeMillis();
        }
    }

    private void pre() {

    }

    private void packet() {

    }

    private void post() {

    }

    public static boolean shouldMakeNewGameLoop() {
        return INSTANCE == null;
    }
}
