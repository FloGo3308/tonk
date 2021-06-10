package me.flogo.tonk.main;

public class GameLoop extends Thread{
    private static GameLoop INSTANCE = null;
    public static int TARGET_TPS = 10;
    public int ticks = 0;
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
            if (!(delta <= 1000/TARGET_TPS*2 && delta > 0)) {
                Tonk.LOGGER.error("we fucked");
            }
            while ((System.currentTimeMillis()-lastTimeMS+(delta <= 1000/TARGET_TPS*5  && delta > 1000/TARGET_TPS /**&& !Tonk.loading**/ ? delta-1000/TARGET_TPS : 0)) < 1000/TARGET_TPS) {;} //+(delta < 100 && delta > -100 && !Tonk.loading ? (int)delta : 0)
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
//            Tonk.LOGGER.info(String.valueOf(delta));
            if ((delta)/1000F != 1F/TARGET_TPS) {
                String text = (delta-1000/TARGET_TPS) >= 0 ? "RUNNING " + (delta-1000/TARGET_TPS) + "MS  BEHIND, SKIPPING THOSE NEXT TICK." :
                        "SKIPPED " + -(delta-1000/TARGET_TPS) + " MS.";
//                Tonk.LOGGER.info("BAD TIMING! "+text);
            }
            delta = (int) (System.currentTimeMillis()-lastTimeMS);
            lastTimeMS = System.currentTimeMillis();
            ticks++;
        }
    }

    private void pre() {
        if (ticks % TARGET_TPS == 0) {
            Tonk.LOGGER.info("important timed thingie " + ticks);
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
