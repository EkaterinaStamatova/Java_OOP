package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer{
    private final static double DECREASING_FACTOR = 7;
    private final static double ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, ENERGY);
    }

    @Override
    public void search(){
        if(getEnergy()<=DECREASING_FACTOR){
            super.setEnergy(0);
        }else{
            super.setEnergy(this.getEnergy()-DECREASING_FACTOR);
        }
    }
}
