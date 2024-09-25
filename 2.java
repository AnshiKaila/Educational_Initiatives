import java.util.ArrayList;
import java.util.List;

// ------------------ OBSERVER PATTERN ------------------

// Observer interface
interface Observer {
    void update(boolean isOccupied);
}

// AC observer class
class AC implements Observer {
    @Override
    public void update(boolean isOccupied) {
        if (isOccupied) {
            System.out.println("AC turned on.");
        } else {
            System.out.println("AC turned off.");
        }
    }
}

// Lights observer class
class Lights implements Observer {
    @Override
    public void update(boolean isOccupied) {
        if (isOccupied) {
            System.out.println("Lights turned on.");
        } else {
            System.out.println("Lights turned off.");
        }
    }
}

// Room class that manages observers (AC and Lights) and room status
class Room {
    private int roomNumber;
    private boolean occupied = false;
    private int maxCapacity = 0;
    private List<Observer> observers = new ArrayList<>();

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        observers.add(new AC());
        observers.add(new Lights());
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity > 0) {
            this.maxCapacity = maxCapacity;
            System.out.println("Room " + roomNumber + " maximum capacity set to " + maxCapacity + ".");
        } else {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void addOccupants(int count) {
        if (count >= 2) {
            occupied = true;
            System.out.println("Room " + roomNumber + " is now occupied by " + count + " persons.");
        } else {
            occupied = false;
            System.out.println("Room " + roomNumber + " occupancy insufficient to mark as occupied.");
        }
        notifyObservers();
    }

    public void releaseOccupants() {
        occupied = false;
        System.out.println("Room " + roomNumber + " is now unoccupied.");
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(occupied);
        }
    }

    public boolean isOccupied() {
        return occupied;
    }
}

// ------------------ SINGLETON PATTERN ------------------

// Singleton class for managing office configuration and global state
class OfficeManager {
    private static OfficeManager instance = null;
    private List<Room> rooms = new ArrayList<>();

    private OfficeManager() {}

    public static OfficeManager getInstance() {
        if (instance == null) {
            instance = new OfficeManager();
        }
        return instance;
    }

    public void configureRooms(int count) {
        for (int i = 1; i <= count; i++) {
            rooms.add(new Room(i));
        }
        System.out.println("Office configured with " + count + " meeting rooms.");
    }

    public Room getRoom(int roomNumber) {
        if (roomNumber > 0 && roomNumber <= rooms.size()) {
            return rooms.get(roomNumber - 1);
        } else {
            System.out.println("Invalid room number.");
            return null;
        }
    }
}

// ------------------ COMMAND PATTERN ------------------

// Command interface
interface Command {
    void execute();
}

// Command for booking a room
class BookRoomCommand implements Command {
    private Room room;
    private String startTime;
    private int duration;

    public BookRoomCommand(Room room, String startTime, int duration) {
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public void execute() {
        if (!room.isOccupied()) {
            System.out.println("Room " + room.getRoomNumber() + " booked from " + startTime + " for " + duration + " minutes.");
            room.addOccupants(2); // Assuming 2 persons to make it occupied
        } else {
            System.out.println("Room " + room.getRoomNumber() + " is already booked.");
        }
    }
}

// Command for canceling a room booking
class CancelBookingCommand implements Command {
    private Room room;

    public CancelBookingCommand(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {
        if (room.isOccupied()) {
            room.releaseOccupants();
            System.out.println("Booking for Room " + room.getRoomNumber() + " cancelled successfully.");
        } else {
            System.out.println("Room " + room.getRoomNumber() + " is not booked.");
        }
    }
}

// ------------------ MAIN CLASS ------------------

public class Main {
    public static void main(String[] args) {
        OfficeManager officeManager = OfficeManager.getInstance();

        // Configuring rooms
        officeManager.configureRooms(3);

        Room room1 = officeManager.getRoom(1);
        Room room2 = officeManager.getRoom(2);

        // Setting room capacities
        room1.setMaxCapacity(10);
        room2.setMaxCapacity(8);

        // Booking room 1
        Command bookRoom1 = new BookRoomCommand(room1, "09:00", 60);
        bookRoom1.execute();

        // Cancel booking for room 1
        Command cancelRoom1 = new CancelBookingCommand(room1);
        cancelRoom1.execute();

        // Trying to book room 1 again
        bookRoom1.execute();

        // Invalid cases
        Command bookRoom2 = new BookRoomCommand(room2, "09:00", 60);
        Command cancelRoom2 = new CancelBookingCommand(room2);
        cancelRoom2.execute();

        // Test occupancy changes
        room1.addOccupants(0);  // Occupancy less than 2, should not mark as occupied
        room1.addOccupants(3);  // Occupancy of 3, should mark as occupied
    }
}
