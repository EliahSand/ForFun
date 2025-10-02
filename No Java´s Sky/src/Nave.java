import java.util.Scanner;

public class Nave {
    protected float unidadesCombustible, eficiencialPropulsor;

    //Constructor
    public Nave(){
        unidadesCombustible = 100;
        eficiencialPropulsor = 0;
    }


    /**
     * Refuels the spaceship given an input of hydrogencrystals
     * @param hydrgoeno
     */
    public void recargarPropulsores(int hydrogenUnits ){
        double unitsRecharged = 0.6*hydrogenUnits*(1+eficiencialPropulsor);
        if (unitsRecharged + unidadesCombustible >= 100){
            unidadesCombustible = 100;
            System.out.println("Spaceship fully fueled!");
        }   else {
                unidadesCombustible += unitsRecharged;
                System.out.println("Spaceship fueled to " + (unitsRecharged + unidadesCombustible));
            }
        }
        

    public boolean fuelUsed(int jumpSize) {
        double fuelCost = 0.75 * Math.pow(jumpSize, 2) * (1 - eficiencialPropulsor);
        if (unidadesCombustible < fuelCost) {
            System.out.println("You do not have the necessary amount of fuel for this trip");
            return false;
        }
        this.unidadesCombustible -= fuelCost;
        System.out.println("You now have "+ unidadesCombustible + " fuel left!");
        return true; 
    }

    public boolean viajarPlaneta(MapaGalactico MG, int direction, int tamanoSalto, Scanner scanner){
        //check jumpsize 
        if (tamanoSalto <= 0 ){
            System.out.println("Invalid jump size");
            return false;
        }
        //the target planet of the jump 
        int targetPosition = MG.planetPosition + (direction*tamanoSalto);

        if (targetPosition < 0) {
            System.out.println("You cannot jump backward beyond the first planet.");
            return false;
        }

        while ( targetPosition >= MG.planets.size()){
            System.out.println("Attempting to jump beyoond explored planets. Generating new planets...");
            MG.generadorPlaneta();
        }

        //check if there is enough fuel to make the jump
        boolean hasFuel = this.fuelUsed(tamanoSalto);
        if(!hasFuel){
            return false;
        }

        printSpaceshipArt();

        //update the current position
        MG.planetPosition = targetPosition;
        Planeta targetPlanet = MG.planets.get(targetPosition);
        //overview of the planet in question 
        System.out.println("Arrived at Planet " + targetPosition + "!");
        System.out.println("Planet Overview:");
        System.out.println("Type: " + targetPlanet.getClass().getSimpleName());
        System.out.printf("Area: %,f m^2\n", targetPlanet.getArea());
        System.out.println("Hydrogencrystals: " + Math.abs(targetPlanet.getHydrogenCrystals()));
        System.out.println("Sodiumflowers: "+ Math.abs(targetPlanet.getSodiumFlowers()));

        //Ask the player if they want to land
        System.out.println("Do you want to stop at this planet? (yes/no)");
        String landResponse = scanner.nextLine();

        if(landResponse.equals("yes")){
            System.out.println("You have landed on planet "+ targetPosition + "!");
            return true;
        }  else {
            System.out.println("So you choose to continue your journey...");
            return false;
        }
    }

    /**
     * upgrades the spaceship based on the amount of resources traded
     * @param amountTraded
     */
    public void upgradeSpaceship(int amountTraded){
        // Calculate the efficiency and protection boost based on the amount traded
        double efficiencyBoost = 0.0002 * amountTraded;  // 2% per unit traded
        //float protectionBoost = 5.0f * amountTraded;   // 5 units of protection per unit traded

        this.eficiencialPropulsor += efficiencyBoost;
        //this.unidadesEnergiaProteccion += protectionBoost;

        System.out.println("Spacesuit upgraded! Efficiency increased by " + (efficiencyBoost * 100) + "%!");
        
    }

    /**
     * prints a spaceship
     */
    public void printSpaceshipArt() {
        System.out.println("              _");
        System.out.println("             /^\\");
        System.out.println("            /___\\");
        System.out.println("           |=   =|");
        System.out.println("           |     |");
        System.out.println("           |     |");
        System.out.println("           |     |");
        System.out.println("           |     |");
        System.out.println("           |     |");
        System.out.println("           |     |");
        System.out.println("          /|##!##|\\");
        System.out.println("         / |##!##| \\");
        System.out.println("        /  |##!##|  \\");
        System.out.println("       |  / ^ | ^ \\  |");
        System.out.println("       | /  ( | )  \\ |");
        System.out.println("       |/   ( | )   \\|");
        System.out.println("           ((   ))");
        System.out.println("          ((  :  ))");
        System.out.println("          ((  :  ))");
        System.out.println("           ((   ))");
        System.out.println("            (( ))");
        System.out.println("             ( )");
        System.out.println("              .");
        System.out.println("              .");
        System.out.println("              .");
    }
    

    //getters and setters
    public float getUnidadesCombustible() {
        return unidadesCombustible;
    }

    public void setUnidadesCombustible(float unidadesCombustible) {
        this.unidadesCombustible = unidadesCombustible;
    }

    public float getEficiencialPropulsor() {
        return eficiencialPropulsor;
    }

    public void setEficiencialPropulsor(float eficiencialPropulsor) {
        this.eficiencialPropulsor = eficiencialPropulsor;
    }
    
}
