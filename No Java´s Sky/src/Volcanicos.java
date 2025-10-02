public class Volcanicos extends Planeta implements planetProperties{

    protected int temperature;
    protected double platinum;

    /**
     * Generates a planet
     * @param radius
     * @param Temperature
     */
    public Volcanicos(int radius, int temperature){
        super(radius);
        setTemperature(temperature);
        setPlatinum(area, temperature);
        setEnergyConsumption(temperature);
        calculateHydrogenCrystals(area);
        calculateSodiumFlowers(area);
    }

    private void setTemperature(int temp){
        this.temperature = temp;
    }

    public int getTemperature(){
        return temperature;
    }

    private void setPlatinum(double area, int temperature){
        this.platinum = 0.25*area - 20.5*Math.pow(temperature,2);
    }

    public double getPlatinumAmount(){
        return platinum;
    }

    private void setEnergyConsumption(int temperature){
        this.energyConsumption = 0.08*temperature;
    }

    public double getEnergyConsumption(){
        return energyConsumption;
    }

    @Override
    public void calculateHydrogenCrystals(double area) {
        this.hydrogenCrystals = (int) Math.round(0.3*area);
    }

    @Override
    public void calculateSodiumFlowers(double area) {
        this.sodiumFlowers = 0;
    }

    @Override
    public boolean hasUranium() {
        return false;
    }

    @Override
    public boolean hasPlatinum() {
        return true;
    }
}
