package me.starcrazzy.economy.user.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.starcrazzy.economy.database.table.TableRow;
import me.starcrazzy.economy.user.dao.EconomyUserDAO;
import me.starcrazzy.economy.user.data.EconomyUser;

import java.util.HashMap;


/**
 * @author oNospher
 **/
public class EconomyUserManager {

    @Getter
    private static final HashMap<String, EconomyUser> users = Maps.newHashMap();

    public static EconomyUser find(String username) {
        if (EconomyUserManager.users.containsKey(username)) {
            return EconomyUserManager.users.get(username);
        }
        return new EconomyUserDAO<>().find("username", username);
}
    public static EconomyUser toUser(TableRow row) { ;

        return new EconomyUser(
                row.getString("username"),
                row.getDouble("coins")
        );
    }
}
