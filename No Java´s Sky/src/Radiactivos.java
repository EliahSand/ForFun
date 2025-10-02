public class Radiactivos extends Planeta implements planetProperties {

    protected int radiation;
    protected double uranium;


    /**
     * Generates a planet
     * @param radius
     * @param Temperature
     */
    public Radiactivos(int radius, int radiation){
        super(radius);
        setRadiation(radiation);
        setUranium(area, radiation);
        setEnergyConsumption(radiation);
        calculateHydrogenCrystals(area);
        calculateSodiumFlowers(area);
    }

    private void setRadiation(int rads){
        this.radiation = rads;
    }

    public int getRadiation(){
        return radiation;
    }

    private void setUranium(double area, int radiation){
        this.uranium = 0.25*area*radiation;
    }

    public double getUranium(){
        return uranium;
    }

    private void setEnergyConsumption(int radiation){
        this.energyConsumption = 0.3*radiation;
    }

    public double getEnergyConsumption(){
        return energyConsumption;
    }

    @Override
    public void calculateHydrogenCrystals(double area) {
        this.hydrogenCrystals = (int) Math.round(0.2*area);
    }

    @Override
    public void calculateSodiumFlowers(double area) {
        this.sodiumFlowers = (int) Math.round(0.2*area);
    }

    @Override
    public boolean hasUranium() {
        return true;
    }

    @Override
    public boolean hasPlatinum() {
        return false;
    }
    
}
