/**
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Game {
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theatre, pub, lab, office, cellar, direction, library, bathrooms;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar");
        direction = new Room("at the direction");
        library = new Room("in the librery");
        bathrooms = new Room(" in the bathrooms");

        outside.setExits("south", theatre);
        outside.setExits("east", lab);
        outside.setExits("west", pub);
        office.setExits("down", cellar);
        cellar.setExits("up", office);
        outside.setExits("north", direction);
        lab.setExits("up", library);
        library.setExits("down", lab);
        outside.setExits("south", bathrooms);

        // initialise room exits
        /*
         * outside.setExits(null, theatre, lab, pub);
         * theatre.setExits(null, null, null, outside);
         * pub.setExits(null, outside, null, null);
         * lab.setExits(outside, office, null, null);
         * office.setExits(null, null, null, lab);
         */

        currentRoom = outside; // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    private void printLocationInfo() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print(currentRoom.getExitString());
        /*
         * if (currentRoom.northExit != null)
         * System.out.print("north ");
         * if (currentRoom.eastExit != null)
         * System.out.print("east ");
         * if (currentRoom.southExit != null)
         * System.out.print("south ");
         * if (currentRoom.westExit != null)
         * System.out.print("west ");
         * System.out.println();
         */

    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /**
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        

        // Try to leave current room.
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        /*
        if (direction.equals("north"))
            nextRoom = currentRoom.northExit;
        if (direction.equals("east"))
            nextRoom = currentRoom.eastExit;
        if (direction.equals("south"))
            nextRoom = currentRoom.southExit;
        if (direction.equals("west"))
            nextRoom = currentRoom.westExit;
        if (direction.equals("down"))
            nextRoom = currentRoom.downExit;*/

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            printLocationInfo();
            /*
             * System.out.println("You are " + currentRoom.getDescription());
             * System.out.print("Exits: ");920
             * if(currentRoom.northExit != null)
             * System.out.print("north ");
             * if(currentRoom.eastExit != null)
             * System.out.print("east ");
             * if(currentRoom.southExit != null)
             * System.out.print("south ");
             * if(currentRoom.westExit != null)
             * System.out.print("west ");
             * System.out.println();
             */
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else
            return true; // signal that we want to quit
    }
}