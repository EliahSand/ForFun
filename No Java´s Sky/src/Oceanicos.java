import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oceanicos extends Planeta implements planetProperties, tieneAsentamientos, trading {

    protected int profundidad;
    private String alienSpecies;
    // Adding different species to inhabit the oceanic planet
    private List<String> alienNames = new ArrayList<>() {
        {
            add("Aquarids");
            add("Thalassians");
            add("Nautilux");
            add("Maridians");
            add("Coralytes");
            add("Hydronauts");
            add("Krakenids");
            add("Pelagorans");
            add("Tidewalkers");
            add("Lumalvians");
        }
    };


    /**
     * Generates a planet
     * @param radius
     * @param Temperature
     */
    public Oceanicos(int radio, int profundidad){
        super(radio);
        setProfunidad(profundidad);
        setEnergyConsumption(profundidad);
        calculateHydrogenCrystals(area);
        calculateSodiumFlowers(area);

        Random rand = new Random();
        alienSpecies = alienNames.get(rand.nextInt(alienNames.size()));
    }

    
    private void setProfunidad(int prof){
        this.profundidad = prof;
    }

    public String getAlienName(){
        return alienSpecies;
    }

    public int getProfunidad(){
        return profundidad;
    }

    private void setEnergyConsumption(int profundidad){
        this.energyConsumption = 0.002*Math.pow(profundidad,2);
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
        this.sodiumFlowers = (int) Math.round(0.65*area);
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