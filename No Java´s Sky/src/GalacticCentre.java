public class GalacticCentre extends Planeta{

    public GalacticCentre(int radius){
        super(radius);
    }

    public boolean isThePlayerReady(Jugador player){
        if(player.getSpaceshipEfficiency() > 50 ){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasUranium() {
        return true;
    }

    @Override
    public boolean hasPlatinum() {
        return true;
    }
}
