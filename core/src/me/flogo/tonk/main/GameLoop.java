package me.flogo.tonk.main;

import static me.flogo.tonk.main.Tonk.*;

public class GameLoop extends Thread{
    private static GameLoop INSTANCE = null;
    public static int TARGET_TPS = 20;
    public int lastTick = -1;
    public int tick = 0;
    public long lastTimeMS = 0;
    public boolean stable = false;
    public int stableLimit = 10;
    private int delta = 0;

    /**
     * The main gameloop, most of the logic goes in here.
     * It runs(/tries to run) 20 times a second.(20 ticks per second/TPS)
     */
//    @Override
//    public void run() {
//        while (true) {
//            pre();
//            packet();
//            post();
////            Tonk.LOGGER.info(String.valueOf((delta-1000/TARGET_TPS)));
//            if (!(delta <= 1000/TARGET_TPS*2 && delta > 0)) {
//                Tonk.LOGGER.error("we fucked");
//            }
//            while ((System.currentTimeMillis()-lastTimeMS+(delta <= 1000/TARGET_TPS*5  && delta > 1000/TARGET_TPS /**&& !Tonk.loading**/ ? delta-1000/TARGET_TPS : 0)) < 1000/TARGET_TPS) {;} //+(delta < 100 && delta > -100 && !Tonk.loading ? (int)delta : 0)
//            delta = (int) (System.currentTimeMillis()-lastTimeMS);
////            Tonk.LOGGER.info(String.valueOf(delta));
//            if ((delta)/1000F != 1F/TARGET_TPS) {
//                String text = (delta-1000/TARGET_TPS) >= 0 ? "RUNNING " + (delta-1000/TARGET_TPS) + "MS  BEHIND, SKIPPING THOSE NEXT TICK." :
//                        "SKIPPED " + -(delta-1000/TARGET_TPS) + " MS.";
////                Tonk.LOGGER.info("BAD TIMING! "+text);
//            }
//            delta = (int) (System.currentTimeMillis()-lastTimeMS);
//            lastTimeMS = System.currentTimeMillis();
//            ticks++;
//        }
//    }

    @Override
    public void run() {
        while (true) {
            pre();
            packet();
            post();
            while (System.currentTimeMillis() % (1000/TARGET_TPS) != 0 || System.currentTimeMillis() == lastTimeMS || System.currentTimeMillis()-lastTimeMS < (1000/4/TARGET_TPS)) {;} //+(delta < 100 && delta > -100 && !Tonk.loading ? (int)delta : 0)
            LOGGER.info(lastTimeMS+":"+System.currentTimeMillis()+" "+ System.currentTimeMillis() % 1000/TARGET_TPS);
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
            if ((delta)/1000F != 1F/TARGET_TPS) {
                String text = (delta-1000/TARGET_TPS) >= 0 ? "RUNNING " + (delta-1000/TARGET_TPS) + "MS  BEHIND, SKIPPING THOSE NEXT TICK." :
                        "SKIPPED " + -(delta-1000/TARGET_TPS) + " MS.";
                Tonk.LOGGER.info("BAD TIMING! "+text);
            }
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
            lastTimeMS = System.currentTimeMillis();
            tick++;
        }
    }

    private void pre() {
        if (tick % TARGET_TPS == 0) {
//            Tonk.LOGGER.info("important timed thingie " + tick);
        }
    }

    private void packet() {

    }

    private void post() {

    }

    public static boolean shouldMakeNewGameLoop() {
        return INSTANCE == null;
    }
}
