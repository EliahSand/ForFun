import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Helado extends Planeta implements planetProperties, tieneAsentamientos, trading {

    //declare variables
    private int temperature;
    private String alienSpecies;
    // Adding different species to inhabit the frozen planet
    private List<String> alienNames = new ArrayList<>(){
        {
            add("Mammuts");
            add("Icetrolls");
            add("Frostwreights");
            add("Glacians");
            add("Frostwings");
            add("Cryliths");
            add("Icetreads");
            add("Bliztari");
            add("Snowraiths");
            add("Frozharr");
        }
    };
    
    
    /**
     * Generates a planet
     * @param radius
     * @param Temperature
     */
    public Helado(int radius, int Temperature){
        super(radius);                           //use inheritance from "Planeta"
        setTemperature(Temperature);
        calculateHydrogenCrystals(area);
        setEnergyConsumption(Temperature);
        calculateSodiumFlowers(area);

        Random rand = new Random();
        alienSpecies = alienNames.get(rand.nextInt(alienNames.size()));
    }

    //getters and setters
    private void setTemperature (int temp){
        this.temperature = temp;
    }


    public double getEnergyConsumption(){
        return energyConsumption;
    }

    public int getTemperature(){
        return temperature;
    }

    public String getAlienName(){
        return alienSpecies;
    }

    private void setEnergyConsumption(int temperature){
        this.energyConsumption=0.15 * Math.abs(temperature);
    }

    @Override
    public void calculateHydrogenCrystals(double area) {
        this.hydrogenCrystals = (int) Math.round(0.65 * area);
    }

    @Override
    public void calculateSodiumFlowers(double area) {
        this.sodiumFlowers = (int) Math.round(0.35 * area);
    }


    @Override
    public void tradeForUpgrade(Scanner scan, Jugador jugador, String resourceName) {

        System.out.println("How many units of " + resourceName + " would you like to trade?");
        int amount = scan.nextInt();
        scan.nextLine();  // Clear the input buffer

        // Check if the player has enough resources to trade
        if (jugador.getResourceAmount(resourceName) >= amount) {
            // Deduct the resource from the player's inventory
            jugador.removeResource(resourceName, amount);

            // Ask the player what they want to upgrade
            System.out.println("What would you like to upgrade? Type 'spacesuit' or 'spaceship':");
            String upgradeChoice = scan.nextLine().toLowerCase();

            if (upgradeChoice.equals("spacesuit")) {
                jugador.upgradeSpacesuit(amount);

            } else if (upgradeChoice.equals("spaceship")) {
                jugador.playerUpgradeSpaceship(amount);

            } else {
                System.out.println("Invalid choice. No upgrades were made.");
            }
        } else {
            System.out.println("You don't have enough " + resourceName + " to trade.");
        }
    }

    @Override
    public boolean hasUranium() {
        return false;
    }

    @Override
    public boolean hasPlatinum() {
        return false;
    }

    @Override
    public void visitarAsentamientos(Jugador jugador, Scanner scanner) {
        System.out.println("Hello " + jugador.getName() + " we are the " + getAlienName() + "!" );
        System.out.println("Do you want to trade with us? We can offer upgrades if you have Uranium or Platinum. We dont speak english well so only answer 'yes' or 'no'");

    
        String answer = scanner.nextLine().toLowerCase(); //for more robust code in case someone writes big

        if( answer.equals("yes")){
            System.out.println("What would you like to trade? Type 'uranium' or 'platinum':");
            String tradeItem = scanner.nextLine().toLowerCase();

            switch (tradeItem) {
                case "uranium":
                    tradeForUpgrade(scanner, jugador, tradeItem);
                    break;
                case "platinum":
                    tradeForUpgrade(scanner, jugador, tradeItem);
                    break;
                default:
                    System.out.println("Write properly you are better than this");
                    break;
            }
        }
    }
}


