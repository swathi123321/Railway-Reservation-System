import java.time.LocalDate;
import java.util.*;

class User {
    String id;
    String name;
    String email;

    User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }
}

class Train {
    String id;
    String number;
    String name;
    String source;
    String destination;
    LocalDate date;
    int seatsAvailable;

    Train(String number, String name, String source, String destination, LocalDate date, int seatsAvailable) {
        this.id = UUID.randomUUID().toString();
        this.number = number;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.seatsAvailable = seatsAvailable;
    }
}

class Booking {
    String pnr;
    User user;
    Train train;
    int seatsBooked;
    boolean cancelled;

    Booking(User user, Train train, int seatsBooked) {
        this.pnr = "PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.user = user;
        this.train = train;
        this.seatsBooked = seatsBooked;
        this.cancelled = false;
    }
}

public class RailwayReservationSystem {
    private List<User> users = new ArrayList<>();
    private List<Train> trains = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    // Register user
    public User register(String name, String email) {
        User u = new User(name, email);
        users.add(u);
        return u;
    }

    // Search trains
    public List<Train> searchTrains(String source, String destination, LocalDate date) {
        List<Train> results = new ArrayList<>();
        for (Train t : trains) {
            if (t.source.equalsIgnoreCase(source) &&
                t.destination.equalsIgnoreCase(destination) &&
                t.date.equals(date)) {
                results.add(t);
            }
        }
        return results;
    }

    // Book tickets
    public Booking bookTickets(User user, Train train, int seats) {
        if (train.seatsAvailable >= seats) {
            train.seatsAvailable -= seats;
            Booking b = new Booking(user, train, seats);
            bookings.add(b);
            return b;
        }
        return null;
    }

    // Cancel booking
    public boolean cancelBooking(String pnr) {
        for (Booking b : bookings) {
            if (b.pnr.equals(pnr) && !b.cancelled) {
                b.cancelled = true;
                b.train.seatsAvailable += b.seatsBooked;
                return true;
            }
        }
        return false;
    }

    // Demo run
    public static void main(String[] args) {
        RailwayReservationSystem system = new RailwayReservationSystem();

        // Seed trains
        system.trains.add(new Train("12627", "Karnataka Express", "Bengaluru", "Delhi", LocalDate.now().plusDays(2), 100));
        system.trains.add(new Train("16590", "Rani Chennamma", "Bengaluru", "Belagavi", LocalDate.now().plusDays(1), 50));

        // Register user
        User swathi = system.register("Swathi", "swathi@example.com");

        // Search trains
        List<Train> results = system.searchTrains("Bengaluru", "Belagavi", LocalDate.now().plusDays(1));
        System.out.println("Available trains: " + results.size());

        // Book tickets
        if (!results.isEmpty()) {
            Train chosen = results.get(0);
            Booking booking = system.bookTickets(swathi, chosen, 2);
            if (booking != null) {
                System.out.println("Booking successful! PNR: " + booking.pnr);
                System.out.println("Remaining seats: " + chosen.seatsAvailable);

                // Cancel booking
                boolean cancelled = system.cancelBooking(booking.pnr);
                System.out.println("Booking cancelled: " + cancelled);
                System.out.println("Seats restored: " + chosen.seatsAvailable);
            } else {
                System.out.println("Booking failed. Not enough seats.");
            }
        }
    }
}
