package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

import java.util.*;

public class GunRepository implements Repository<Gun>{

    private Map<String, Gun> models;

    public GunRepository() {
        this.models = new LinkedHashMap<>();
    }

    @Override
    public Collection<Gun> getModels() {
        return Collections.unmodifiableCollection(this.models.values());
    }

    @Override
    public void add(Gun gun){
        if(gun == null){
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_REPOSITORY);
        }
        this.models.put(gun.getName(), gun);
    }

    @Override
    public boolean remove(Gun gun){
        return this.models.remove(gun.getName())!=null;
    }

    @Override
    public Gun findByName(String name){
        return this.models.get(name);
    }

}
