package me.starcrazzy.economy.user.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.starcrazzy.economy.database.data.Parameters;
import me.starcrazzy.economy.user.dao.EconomyUserDAO;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@RequiredArgsConstructor
@Getter @Setter
public class EconomyUser {

    public String username;
    public double coins;

    public EconomyUser(String username, double coins) {
        this.username = username;
        this.coins = coins;
    }
    public void update() throws SQLException {
        Parameters<String, Double> parameters = new Parameters<>(
                "coins",
                coins
        );

   new EconomyUserDAO<>()
                .update(
                        parameters,
                        this
                );

    }

    public void setCoins(double coins)  {
        this.coins = coins;
        try {
            this.update();
        }catch ( SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Player getPlayer () {
            return Bukkit.getPlayer(this.username);
        }

}
