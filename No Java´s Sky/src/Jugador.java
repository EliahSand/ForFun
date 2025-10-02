import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Jugador {

    protected float unidadesEnergiaProteccion, eficienciaEnergiaProteccion;
    protected String name;
    protected Nave spaceShip;
    private Scanner scanner;

    private MapaGalactico galacticMap;

    //Initialize empty inventory
    Map<String,Double> inventory = new HashMap<>() {
        {put("hydrogen", 0.0);
        put("sodium", 0.0);
        put("uranium", 0.0);
        put("platinum", 0.0);
        }
    }; 

    /**
     * Initialzises a player with "new player" values
     */
    public Jugador(String chooseName, Scanner scanner, MapaGalactico mapaGalactico){
        this.unidadesEnergiaProteccion = 100;
        this.eficienciaEnergiaProteccion = 0;
        this.name = chooseName;
        this.scanner = scanner;
        this.spaceShip = new Nave();
        this.galacticMap = mapaGalactico;
    }

    
    /**
     * Returns the amount of a spesific resource in the players inventory 
     * @param resourceName
     * @return
     */
    public double getResourceAmount(String resourceName){
        return inventory.getOrDefault(resourceName, 0.0);
    }

    /**
     * Adds a specified amount of a resource to the players inventory
     * @param rescourceName
     * @param amount
     */
    public void addResource(String rescourceName, double amount){
        if (inventory.containsKey(rescourceName)){      //Do a check for more robust code
            inventory.put(rescourceName, inventory.get(rescourceName) + amount);
            System.out.println(amount + " of " + rescourceName + " added to your inventory!");
        } else{
            System.out.println("Invalid resource: " + rescourceName);
        }
    }
    
    /**
     * Removes a specified amount of a resource to the players inventory
     * @param resourceName
     * @param amount
     */
    public void removeResource(String resourceName, double amount) {
        if (inventory.containsKey(resourceName) && inventory.get(resourceName) >= amount) {
            inventory.put(resourceName, inventory.get(resourceName) - amount);
            System.out.println(amount + " units of " + resourceName + " removed from your inventory.");
        } else {
            System.out.println("Not enough " + resourceName + " in inventory to complete the trade.");
        }
    }

    /**
     * Displays the players inventory 
     */
    public void checkInventory() {
        System.out.println("Your inventory:");
        for (Map.Entry<String, Double> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Refuels the players spaceship given an amount of fuel 
     * @param hydrogenUnits
     */
    public void rechargeSpaceship(int hydrogenUnits) {
        spaceShip.recargarPropulsores(hydrogenUnits); // Call the spaceship's refuel method
        removeResource("hydrogen", hydrogenUnits);
    }

     // Method to visit a planet
    public void visitPlanet(Planeta planet) {
        if(planet instanceof GalacticCentre){
            if(((GalacticCentre)planet).isThePlayerReady(this)){
                System.out.println("Congratulations! Your journey ends here: you have reached the galatic centre!");
                System.out.println("Thanks for playing:)");
                System.exit(0);
            }
        }

        System.out.println("You have landed on Planet " + planet.getClass().getSimpleName() + " "+ galacticMap.planetPosition );
        System.out.println("Radius: " + planet.getRadius());
    
        System.out.println("What would you like to do?");
        System.out.println("1. Extract resources.");
        System.out.println("2. Interact with locals.");
        System.out.println("3. Return to orbit.");

        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                extractResources(planet);
                break;
            case 2:
                // Check if the planet has locals or settlements
                if (planet instanceof tieneAsentamientos) {
                    ((tieneAsentamientos) planet).visitarAsentamientos(this, scanner); // Use the interface method for settlements
                } else {
                    System.out.println("There are no locals to interact with on this planet.");
                }
                break;
            case 3:
                System.out.println("Returning to orbit...");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    

    /**
     * Interact with locals on the planet
     * @param planet
     */
    public void interactWithLocals(Planeta planet) {
        
        System.out.println("How many units of Uranium or Platinum would you like to trade?");
        int amount = scanner.nextInt();
        //scanner.nextLine();

        System.out.println("What would you like to upgrade?");
        System.out.println("1. Spacesuit");
        System.out.println("2. Spaceship");
        int choice = scanner.nextInt();
        //scanner.nextLine();

        if (choice == 1) {
            upgradeSpacesuit(amount);
        } else if (choice == 2) {
            playerUpgradeSpaceship(amount);
        }
    }


    /**
     *  Extract resources from the planet
     * @param planet
     */
    public void extractResources(Planeta planet) {
        
        // Display available resources based on the planet type
        System.out.println("Available resources on " + planet.getClass().getSimpleName() + ":");
        System.out.println("1. Hydrogen");
        System.out.println("2. Sodium");
        if (planet.hasUranium()) {
            System.out.println("3. Uranium");
        }
        if (planet.hasPlatinum()) {
            System.out.println("4. Platinum");
        }
    
        System.out.println("Select a resource to extract (1-4): ");
        int resourceChoice = scanner.nextInt();
        scanner.nextLine();
        String resourceName = "";
    
        // Determine which resource is being chosen
        switch (resourceChoice) {
            case 1:
                resourceName = "hydrogen";
                break;
            case 2:
                resourceName = "sodium";
                break;
            case 3:
                if (planet.hasUranium()) {
                    resourceName = "uranium";
                } else {
                    System.out.println("This planet does not have Uranium.");
                    return;
                }
                break;
            case 4:
                if (planet.hasPlatinum()) {
                    resourceName = "platinum";
                } else {
                    System.out.println("This planet does not have Platinum.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return; // Exit the method if invalid choice
        }
    
        // Ask for the amount to extract
        System.out.println("How many units of " + resourceName + " would you like to extract?");
        int amount = scanner.nextInt();
        scanner.nextLine();
    
        // Calculate the energy cost using the formula
        double energyCost = 0.5 * amount * planet.getEnergyConsumption()/100 * (1 - this.getEficienciaEnergiaProteccion() / 100.0);
    
        // Check if the player has enough energy for extraction
        boolean success = depleteEnergy(amount, energyCost);
    
        if (success) {
            // Add the extracted resource to the player's inventory
            addResource(resourceName, amount);
            System.out.println("You have successfully extracted " + amount + " units of " + resourceName + ".");
        } else {
            //System.out.println("You do not have enough energy to extract resources.");
        }
    }
    


    /**
     * Upgrades the spacesuit based on the traded resources
     * @param amountTraded
     */
    public void upgradeSpacesuit(int amountTraded) {
        // Calculate the efficiency and protection boost based on the amount traded
        double efficiencyBoost = 0.0002 * amountTraded;  // 2% per unit traded


        this.eficienciaEnergiaProteccion += efficiencyBoost;

        System.out.println("Spacesuit upgraded! Efficiency increased by " + (efficiencyBoost * 100) + "%!");
    }

    /**
     * Upgrades the spaceship of the player based on the amount of resources traded
     * @param amountTraded
     */
    public void playerUpgradeSpaceship(int amountTraded){
        spaceShip.upgradeSpaceship(amountTraded);
    }

    /**
     * Depletes the spacesuit of energy based on the amount of resources he gathers
     * @param amountOfResources
     * @param energyConsumption
     * @return
     */
    public boolean depleteEnergy(int amountOfResources, double energyConsumption){
        double operationCost = 0.5*amountOfResources*(energyConsumption/100)*(1-eficienciaEnergiaProteccion);
        if(unidadesEnergiaProteccion - operationCost <= 0 ){
            System.out.println("YOU ARE DEAD");
            System.out.println("You ran out of energy and perished. All resources have been lost");
            System.out.println("Returning to start...");
            resetPlayer();
            return false;
        }
        this.unidadesEnergiaProteccion -= operationCost;
        System.out.println("This operation cost you " + operationCost + " energy. You now have " + unidadesEnergiaProteccion + " energy left.");
        return true;
    }

    public void removeEnergy(int energyloss){
        if(unidadesEnergiaProteccion - energyloss <= 0 ){
            System.out.println("YOU ARE DEAD");
            System.out.println("you ran out of energy and perished. All resources have been lost");
            System.out.println("Returning to start...");
            resetPlayer();
        } 
        this.unidadesEnergiaProteccion -= energyloss;
        System.out.println("This event cost you "+ energyloss + " energy. You now have " + unidadesEnergiaProteccion + " left.");

    }

    
    /**
     * Recharges the EnergiaProteccion based on an amount of sodio 
     * @param sodio
     */
    public void recargarEnergiaProteccion(int sodio){
        if(inventory.get("sodium") < sodio){
            System.out.println("You cant use that much, you only have " + inventory.get("sodium") + " amount of sodium left");
        } else {

            double chargedUnits = 0.65*sodio*(1+eficienciaEnergiaProteccion);
            if(unidadesEnergiaProteccion + chargedUnits < 100){
                unidadesEnergiaProteccion += chargedUnits;
            System.out.println("Spacesuit charged to " + (unidadesEnergiaProteccion));
        } else {
            unidadesEnergiaProteccion = 100;
        System.out.println(("Spacesuit fully charged!"));

        }


        }
    }


    /**
     * Resets the to the start, deleting the inventory but keeping the progess in the spacesuit and spaceship
     */
    public void resetPlayer(){
        unidadesEnergiaProteccion = 100;
        inventory.replaceAll((k,v) -> 0.0);
        galacticMap.planetPosition = 0;
        spaceShip.setUnidadesCombustible(100);
        System.out.println("Respawning at start - planet zero.");
    }

    

    //GETTERS AND SETTERS  
    public float getUnidadesEnergiaProteccion() {
        return unidadesEnergiaProteccion;
    }

    public double getSpaceshipFuel (){
        return spaceShip.getUnidadesCombustible();
    }

    public double getSpaceshipEfficiency(){
        return spaceShip.getEficiencialPropulsor();
    }

    public void setUnidadesEnergiaProteccion(float unidadesEnergiaProteccion) {
        this.unidadesEnergiaProteccion = unidadesEnergiaProteccion;
    }

    public float getEficienciaEnergiaProteccion() {
        return eficienciaEnergiaProteccion;
    }

    public void setEficienciaEnergiaProteccion(float eficienciaEnergiaProteccion) {
        this.eficienciaEnergiaProteccion = eficienciaEnergiaProteccion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}