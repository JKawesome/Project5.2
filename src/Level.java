import processing.core.PImage;

public class Level {

    private Room[] rooms;
    private Room currentRoom;
    private Entity[] entities;

    //for energy
    private int energy;
    private final int TOTAL_ENERGY = 100;
    private Point energyLocation = new Point(540, 24);
    private final int FIRST_ENERGY = 80;
    private final int SECOND_ENERGY = 60;
    private final int THIRD_ENERGY = 40;
    private final int FOURTH_ENERGY = 20;


    public Level(PImage cambreaker, PImage invisibleMan, PImage runner){
        this.rooms = RoomFactory.createRooms();
        this.currentRoom = rooms[0];
        this.entities = EntityFactory.createEntities(rooms, cambreaker, invisibleMan, runner);
        this.energy = TOTAL_ENERGY;
    }

    public Point getEnergyLocation()
    {
        return energyLocation;
    }

    public int getEnergyIndex()
    {
        // TO DO CHANGE NAMES?
        if(energy > FIRST_ENERGY)
        {
            return 0;
        }
        else if(energy > SECOND_ENERGY)
        {
            return 1;
        }
        else if(energy > THIRD_ENERGY)
        {
            return 2;
        }
        else if(energy > FOURTH_ENERGY)
        {
            return 3;
        }
        else
        {
            return 4;
        }
    }

    public boolean decreaseEnergy(int amt)
    {
        energy -= amt;
        if(energy <= 0)
        {
            return true;
        }
        return false;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public Room getRoom(int i){
        return rooms[i];
    }

    public void setRoom(int i)
    {
        currentRoom = rooms[i];
    }

    public Entity[] getEntities()
    {
        return entities;
    }

    public void nextRoom(Entity entity)
    {
        entity.setRoom(rooms[entity.getRoom().nextRoom(entity.getPos())]);
        entity.setPos(entity.getRoom().getStart());
    }

    public int getClickedRoom(Point p)
    {
        return currentRoom.getClickedRoom(p);
    }

    public void runLevel() {
        //NEED TO CHANGE THIS TO DO WITH THE LIST
        if (((Runner) getEntities()[2]).isRunning()) {
            ((Runner) getEntities()[2]).running();
            if (changeWorlds(((Runner) getEntities()[2]))) {
                ((Runner) getEntities()[2]).setRunning();
            }
        }



    }

    public void inEntityRooms(Entity entity){
        //IN ENTITY ROOMS
        if (entity.getClass().equals(Runner.class)) {
            if (!((Runner) entity).isRunning()) {
                entity.randomMovementTimer();
                if (entity.getRoom().equals(this.getCurrentRoom())) {
                    entity.stareDecrease();
                }
            }
        } else if(entity.getClass().equals(CamBreaker.class) &&
                entity.getRoom().isBlackScreen())
        {
            ((CamBreaker)entity).blackScreenTimer();
        }
        else
        {
            entity.randomMovementTimer();
            if (entity.getRoom().equals(this.getCurrentRoom())) {
                entity.stareDecrease();
            }
        }
    }

    public boolean inRoomHome(Entity entity){
        return entity.getRoom().equals(getRoom(0));
    }


    public boolean changeWorlds(Entity entity)
    {
        if(entity.getRoom().isDoor(entity.getPos()))
        {
            nextRoom(entity);
            return true;
        }
        return false;
    }
}
