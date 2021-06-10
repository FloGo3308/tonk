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
//            Tonk.LOGGER.info(String.valueOf((delta-1000/TARGET_TPS)));
            if (!(delta <= 100 && delta >= 0)) {
                Tonk.LOGGER.error("we fucked");
            }
            while ((System.currentTimeMillis()-lastTimeMS+(delta <= 100 && delta >= 0 && !Tonk.loading ? (delta-1000/TARGET_TPS)*2 : 0)) < 1000/TARGET_TPS) {;} //+(delta < 100 && delta > -100 && !Tonk.loading ? (int)delta : 0)
//            delta = (int) (System.currentTimeMillis()-lastTimeMS);
//            Tonk.LOGGER.info(String.valueOf(delta));
            if ((delta)/1000F != 1F/TARGET_TPS) {
                Tonk.LOGGER.info("BAD TIMING! RUNNING " + (delta-1000/TARGET_TPS) + "MS  BEHIND, SKIPPING THOSE NEXT TICK");
            }
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
//            Tonk.LOGGER.info(String.valueOf(delta));
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
