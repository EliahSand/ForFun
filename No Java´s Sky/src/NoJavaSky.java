import java.util.Random;
import java.util.Scanner;

public class NoJavaSky {

    private Jugador player;
    private MapaGalactico galacticMap;
    private Scanner scanner;

    public NoJavaSky() {
        this.scanner = new Scanner(System.in);
        this.galacticMap = new MapaGalactico();
    }

    // Main function for starting the gameplay
    public void startGame() {
        System.out.println("Welcome to NoJavaSky!\n");
        System.out.println("You are in a galaxy far far away, and you want to reach the galactic centre.");
        System.out.println("The galactic centre is somewhere in the galaxy, and you need to find it!");
        System.out.println("To reach the galactic centre your spaceship needs at least 50% efficiency.");
        System.out.println("To upgrade your spaceship you can trade resources with locals on certain planets.");
        System.out.println("Gathering resources drains your energy, and if your energy runs out you die and respawn at the start!");
        System.out.println("Depending on the planet and its enviroment, different planets will drain your energy at different speeds.");
        System.out.println("But dont worry, you can upgrade your spacesuit so it can handle more - in other words; you loose less energy when gathering resources.\n");
        System.out.println("To start the game, please write the name of your character:");

        // Get the player's name and create the player object
        String playerName = scanner.nextLine();
        player = new Jugador(playerName, scanner, galacticMap);
        System.out.println("Hello, " + player.getName() + "! Your adventure begins now.");

        // Generate the first planet and set player's position
        galacticMap.generadorPlaneta(); // Generate planet 0
        galacticMap.planetPosition = 0;

        // Start the game loop
        gameLoop();
    }

    // Main gameplay loop
    public void gameLoop() {
        boolean gameRunning = true;

        while (gameRunning) {
            System.out.println("\n");
            System.out.println("/////////////////////////////////////");
            System.out.println("\nYou are in orbit around Planet " + galacticMap.planetPosition);
            System.out.println("What would you like to do?");
            System.out.println("1. Visit the planet surface.");
            System.out.println("2. Travel to another planet.");
            System.out.println("3. Recharge spacesuit");
            System.out.println("4. Recharge spaceship.");
            System.out.println("5. Check energy/fuel levels.");
            System.out.println("6. Check inventory.");
            System.out.println("7. Exit game.");

            int choice = getPlayerChoice(1, 7); 

            switch (choice) {
                case 1:
                    visitPlanet(); 
                    
                    break;
                case 2:
                    jumpToPlanet(); 
                    randomPlanetEvent();
                    break;
                
                case 3: 
                    System.out.println("How many sodium units would you like to use to recharge your spacesuit?");
                    int sodio = scanner.nextInt();
                    player.recargarEnergiaProteccion(sodio);
                    break;
                case 4:
                    System.out.println("How many hydrogen units would you like to use to recharge your spaceship?");
                    int hydrogen = scanner.nextInt();
                    player.rechargeSpaceship(hydrogen); 
                    break;
                case 5:
                    checkEnergyLevels(); 
                    break;
                case 6:
                    player.checkInventory(); 
                    break;
                case 7:
                    System.out.println("Exiting the game. Goodbye!");
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
    

    public void visitPlanet() {
        Planeta currentPlanet = galacticMap.getCurrentPlanet();
        player.visitPlanet(currentPlanet);
    }

    // Jump to another planet
    public void jumpToPlanet() {
        System.out.println("In which direction would you like to jump? (1 for forward, -1 for backward): ");
        int direction = scanner.nextInt();

        System.out.println("How far would you like to jump? Enter the jump size: ");
        int jumpSize = scanner.nextInt();
        scanner.nextLine();

        player.spaceShip.viajarPlaneta(galacticMap, direction, jumpSize, scanner);
    }

    /**
     * Get player's choice between a range of valid inputs
     * @param min
     * @param max
     * @return
     */
    private int getPlayerChoice(int min, int max) {
        int choice;
        do {
            System.out.println("Please enter a number between " + min + " and " + max + ": ");
            choice = scanner.nextInt();
        } while (choice < min || choice > max);
        return choice;
    }

    /**
     * Displays the different energylevels
     */
    public void checkEnergyLevels() {
        System.out.println("Current Spacesuit Energy: " + player.getUnidadesEnergiaProteccion() + "/100");
        System.out.println("Spacesuit Efficiency: " + player.getEficienciaEnergiaProteccion() * 100 + "%");
        
        System.out.println("Current Spaceship Fuel: " + player.getSpaceshipFuel() + "/100");
        System.out.println("Spaceship Efficiency: " + player.getSpaceshipEfficiency() * 100 + "%");
    }

    /*
     * Makes the game more interesting
     */
    public void randomPlanetEvent() {
        Random rand = new Random();
        int event = rand.nextInt(20); 
    
        switch (event) {
            case 0:
                System.out.println("You discover a rare asteroid filled with precious gems! You receive 100 units of platinum.");
                player.addResource("platinum", 100);
                break;
            case 1:
                System.out.println("A cosmic storm approaches! Your ship’s energy shields take a hit.");
                player.removeEnergy(30);
                break;
            case 2:
                System.out.println("Friendly space traders offer to trade resources. You exchange 20 units of hydrogen for 50 units of sodium.");
                player.addResource("sodium", 50);
                player.removeResource("hydrogen", 20);
                break;
            case 3:
                System.out.println("You come across a derelict ship filled with fuel. You gain 60 units of hydrogen.");
                player.addResource("hydrogen", 60);
                break;
            case 4:
                System.out.println("Hostile pirates ambush you! They steal 50 units of platinum before disappearing.");
                player.removeResource("platinum", 50);
                break;
            case 5:
                System.out.println("A strange alien artifact is found drifting in space. Your ship’s systems improve.");
                player.spaceShip.upgradeSpaceship(10); // 10% boost to fuel efficiency
                break;
            case 6:
                System.out.println("You land on a moon rich with sodium. You extract 80 units of sodium.");
                player.addResource("sodium", 80);
                break;
            case 7:
                System.out.println("Your ship passes through a field of space debris, causing minor damage.");
                player.removeEnergy(15);
                break;
            case 8:
                System.out.println("You encounter a peaceful alien civilization. They gift you 40 units of uranium.");
                player.addResource("uranium", 40);
                break;
            case 9:
                System.out.println("A wormhole appears! You're teleported to a random planet.");
                galacticMap.planetPosition = rand.nextInt(galacticMap.planets.size()); // Randomly change player's position in the galaxy
                break;
            case 10:
                System.out.println("A solar flare boosts your ship’s energy shields! Energy restored.");
                player.recargarEnergiaProteccion(50);
                break;
            case 11:
                System.out.println("You explore a gas giant and collect rare hydrogen isotopes.");
                player.addResource("hydrogen", 70);
                break;
            case 12:
                System.out.println("A black hole’s gravitational pull damages your ship. You lose fuel and energy.");
                player.removeResource("hydrogen", 30);
                player.removeEnergy(20);
                break;
            case 13:
                System.out.println("An alien merchant offers rare upgrades for your ship.");
                player.spaceShip.upgradeSpaceship(100);  // Significant ship upgrade for exploration
                break;
            case 14:
                System.out.println("A space anomaly scrambles your ship’s systems. You lose 10 units of each resource.");
                player.removeResource("hydrogen", 10);
                player.removeResource("sodium", 10);
                player.removeResource("uranium", 10);
                player.removeResource("platinum", 10);
                break;
            case 15:
                System.out.println("You find a hidden vault on a distant moon. Inside, you discover 100 units of platinum.");
                player.addResource("platinum", 100);
                break;
            case 16:
                System.out.println("Your ship passes through a field of space radiation. Energy reduced.");
                player.removeEnergy(35);
                break;
            case 17:
                System.out.println("You encounter an alien ship in need of repairs. In gratitude, they share advanced fuel cells.");
                player.addResource("hydrogen", 100);
                break;
            case 18:
                System.out.println("A cosmic entity appears and offers you a cryptic choice: 'fuel or protection?'");
                System.out.println("1. Fuel");
                System.out.println("2. Protection");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    player.addResource("hydrogen", 100);
                    System.out.println("You received 100 units of hydrogen.");
                } else {
                    player.setUnidadesEnergiaProteccion(100);
                    System.out.println("Your energy is fully restored.");
                }
                break;
            default:
                System.out.println("The vastness of space remains calm and quiet. No events occur.");
                break;
        }
    }
    

    
    // Main entry point
    public static void main(String[] args) {
        NoJavaSky game = new NoJavaSky();
        game.startGame();
    }
}

