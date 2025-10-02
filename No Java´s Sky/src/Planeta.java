public abstract class Planeta {
    
    // defining variables which are the same for every planet
    protected int radius, hydrogenCrystals, sodiumFlowers;


    protected double area, energyConsumption;

    /*
     * Constructor for each planet
     */
    public Planeta(int radio){
        this.radius = radio;
        this.area = 4*Math.PI*Math.pow(radio, 2);
    }


    //getters
    public int getRadius() {
        return radius;
    }

    public int getHydrogenCrystals() {
        return hydrogenCrystals;
    }

    public int getSodiumFlowers() {
        return sodiumFlowers;
    }

    public double getEnergyConsumption(){
        return energyConsumption;
    }

    public double getArea() {
        return area;
    }

    protected boolean visitar(Jugador jugador){
        return true;
    }

    protected boolean salir(Jugador jugador){
        return false;
    }

    public abstract boolean hasUranium();
    
    public abstract boolean hasPlatinum();
}
