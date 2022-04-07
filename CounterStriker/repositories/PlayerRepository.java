package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.players.Player;

import java.util.*;

public class PlayerRepository implements Repository<Player> {
    private Map<String, Player> models;

    public PlayerRepository() {
        this.models = new LinkedHashMap<>();
    }

    @Override
    public Collection<Player> getModels() {
        return Collections.unmodifiableCollection(this.models.values());
    }

    @Override
    public void add(Player player){
        if(player == null){
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_REPOSITORY);
        }
        this.models.put(player.getUsername(), player);
    }

    @Override
    public boolean remove(Player player){
       return this.models.remove(player.getUsername())!=null;
    }

    @Override
    public Player findByName(String name){
        return this.models.get(name);
    }

}
