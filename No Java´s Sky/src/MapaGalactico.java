import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MapaGalactico {

    // declare list and variables
    protected List<Planeta> planets = new ArrayList<>();
    protected int planetPosition;

    /**
     * Generates random radiuses between two values
     * @param min radius
     * @param max radius 
     */
    public int randomRadiusGenerator(int min, int max){
        if (min > max) {            // Ensure min is smaller than max so its works with "random" function 
            int temp = min;
            min = max;
            max = temp;
        }
        Random rand = new Random();
        int minRadius = (int) Math.pow(10, min);  
        int maxRadius = (int) Math.pow(10, max);  
        return rand.nextInt(maxRadius - minRadius + 1) + minRadius;
    }
    

    /**
     * Generates random temperature between two values
     * @param minTemp Smallest temperature
     * @param maxTemp Highest temperature 
     */
    public int randomTemperatureGenerator(int minTemp, int maxTemp){
        Random rand = new Random();
        return rand.nextInt(maxTemp - minTemp + 1) + minTemp;
    }

    /**
     * Generates a random planet and adds it to this galaxy
     */
    public void generadorPlaneta(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(99);  //creating a random number from 0 - 100 
        if(randomNumber < 30){                  // this is the same as 30%
            //creating frozen planet
            //add the planet to the list
            planets.add(new Helado(randomRadiusGenerator(3, 6), randomTemperatureGenerator(-120, -30)));
        } else if (randomNumber < 60){           //60%  
            //ocean planet
            Random randOc = new Random();
            int maxProf = (int) Math.pow(10, 3);
            int minProf = 30;
            int prof =  randOc.nextInt(maxProf - minProf + 1) + minProf;
            planets.add(new Oceanicos(randomRadiusGenerator(10, 6), prof));
        } else if (randomNumber < 80){          //20%
            Random randRa  = new Random();
            int maxRad = 50;
            int minRad = 10;
            int rad = randRa.nextInt(maxRad - minRad + 1 ) + minRad;
            planets.add(new Radiactivos(randomRadiusGenerator(4, 5), rad));
        } else if (randomNumber < 99 ){         //19%
            planets.add(new Volcanicos(randomRadiusGenerator(3, 5), randomTemperatureGenerator(120, 256)));
        } else{
            for(Planeta planet : planets){
                if(!(planet instanceof GalacticCentre)){
                    planets.add(new GalacticCentre(randomNumber));
                }
            }
            System.out.println("The galactic centre already exists at " + getCurrentPlanet() + "!");
        }
    }

    /**
     * Function to return the current postitin of the planet
     * @return
     */
    public Planeta getCurrentPlanet() {
        return planets.get(planetPosition);
    }

}
