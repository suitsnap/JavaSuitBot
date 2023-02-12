package dev.suitsnap.suitbot;

import dev.suitsnap.suitbot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class SuitBot {

    private final Dotenv config;
    private final ShardManager shardManager;

    public SuitBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("everything."));
        builder.enableIntents(EnumSet.allOf(GatewayIntent.class));
        shardManager= builder.build();

        shardManager.addEventListener(new EventListener());
    }

    public Dotenv getConfig(){
        return config;
    }
    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            SuitBot bot = new SuitBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Login failed due to invalid token.");
        }
    }
}