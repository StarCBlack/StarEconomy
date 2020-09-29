package me.starcrazzy.economy.database.runnable;


import me.starcrazzy.economy.database.manager.MySQLManager;

/**
 * @author oNospher
 **/
public class MySQLRefreshRunnable implements Runnable {

    @Override
    public void run() {
        new MySQLManager().refresh();
    }
}