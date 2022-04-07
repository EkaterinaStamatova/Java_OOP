package CounterStriker.models.guns;

import CounterStriker.common.ExceptionMessages;

public class GunImpl implements Gun {
    private String name;
    private int bulletsCount;

    public GunImpl(String name, int bulletsCount) {
        this.setName(name);
        this.setBulletsCount(bulletsCount);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_NAME);
        }
        this.name = name;
    }

    @Override
    public int getBulletsCount() {
        return bulletsCount;
    }

    public void setBulletsCount(int bulletsCount) {
        if(this.bulletsCount<0){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_BULLETS_COUNT);
        }
        this.bulletsCount = bulletsCount;
    }

    @Override
    public int fire(){
        return Math.max(this.bulletsCount, 0);
    }

    protected void decreaseBullets(int amount) {
        this.bulletsCount -= amount;
    }
}
