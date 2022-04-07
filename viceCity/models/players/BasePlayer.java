package viceCity.models.players;

import static viceCity.common.ExceptionMessages.*;
import viceCity.models.guns.Gun;
import viceCity.repositories.interfaces.GunRepository;
import viceCity.repositories.interfaces.Repository;

public class BasePlayer implements Player{

    private String name;
    private int lifePoints;
    private Repository<Gun> gunRepository;

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(NAME_NULL);
        }
        this.name = name;
    }

    public void setLifePoints(int lifePoints) {
        if(lifePoints<0){
            throw new IllegalArgumentException(PLAYER_LIFE_POINTS_LESS_THAN_ZERO);
        }
        this.lifePoints = lifePoints;
    }

    public BasePlayer(String name, int lifePoints) {
        this.setLifePoints(lifePoints);
        this.setName(name);
        this.gunRepository = new GunRepository();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLifePoints() {
        return this.lifePoints;
    }

    @Override
    public boolean isAlive() {
        return this.lifePoints>0;
    }

    @Override
    public Repository<Gun> getGunRepository() {
        return this.gunRepository;
    }

    @Override
    public void takeLifePoints(int points) {
        this.lifePoints-=points;
    }
}
