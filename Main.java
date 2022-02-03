// Josh Blick
// 2.3.22
// Project 0

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * A customer.
 */
class Customer implements Serializable {
    public String name;
    public int code;

    /**
     * Create a new customer
     *
     * @param name the name of the customer
     */
    public Customer(String name) {
        this.name = name;
        code = new Random().nextInt(90000) + 10000;
    }
}

/**
 * A theatre that has reservable seats.
 */
class Theatre implements Serializable {
    public int numSeats;
    public Customer[] seats;
    public String movie;

    /**
     * Create a new theatre
     *
     * @param movie    the name of the movie
     * @param numSeats the number of seats
     */
    public Theatre(String movie, int numSeats) {
        this.movie = movie;
        this.numSeats = numSeats;
        this.seats = new Customer[numSeats];
    }

    /**
     * Book a seat
     *
     * @param seatNumber the seat number
     * @param customer   the customer that's trying to reserve the seat
     * @return true if successfully reserved, false otherwise
     */
    public boolean bookSeat(int seatNumber, Customer customer) {
        if (seatNumber < 1 || seatNumber > numSeats) return false;
        if (seats[seatNumber - 1] != null) return false;
        seats[seatNumber - 1] = customer;
        return true;
    }

    /**
     * Show the report for the current theatre.
     */
    public void showReport() {
        System.out.printf("Report for %s%n%n", movie);
        for (int i = 0; i < numSeats; i++) {
            System.out.printf("Seat %02d: ", i + 1);
            if (seats[i] != null) {
                System.out.printf("%s, %d%n", seats[i].name, seats[i].code);
            } else {
                System.out.println("Empty");
            }
        }
    }

    /**
     * Check whether the theatre is full.
     *
     * @return true if the theatre is full, false otherwise.
     */
    public boolean isFull() {
        for (Customer customer : seats) {
            if (customer == null) return false;
        }
        return true;
    }

}

public class Main {
    public static void main(String[] args) throws IOException {
        // Create the theatres.
        Theatre[] theatres = {new Theatre("Harry Potter 12: Legend of the Scar", 10), new Theatre("Encanto 3: No More Powers", 20), new Theatre("Matrix 24: Resuscitated", 15)};

        Scanner input = new Scanner(System.in);
        int option = 1;
        // Menu loop
        System.out.println("\tWelcome to SuperBlicks ShowTime Central $1 Theatre!\t");
        while (option != 0) {
            option = mainMenu(input);
            switch (option) {
                case 1:
                    reservationMenu(input, theatres);
                    break;
                case 2:
                    managerReport(input, theatres);
                    break;
                case 0:
                    System.out.println("\nExiting..");
                    saveData(theatres);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    /**
     * Main menu.
     *
     * @param input the scanner
     * @return the option entered by the user
     */
    static int mainMenu(Scanner input) {
        System.out.println("1. Guest reservation");
        System.out.println("2. Manager report");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
        return Integer.parseInt(input.nextLine());
    }

    /**
     * Reservation menu.
     *
     * @param input    the scanner
     * @param theatres the theatres available
     */
    static void reservationMenu(Scanner input, Theatre[] theatres) {
        System.out.println();
        System.out.print("Enter your name: ");
        String customerName = input.nextLine().strip();
        Customer customer = new Customer(customerName);

        // Show available theatres.
        System.out.println("\nAvailable theatres: ");
        for (int i = 0; i < theatres.length; i++) {
            System.out.printf("\t%d. %s -- %d seats.%n", i + 1, theatres[i].movie, theatres[i].numSeats);
        }
        System.out.print("\nChoose theatre: ");
        int theatreNumber = Integer.parseInt(input.nextLine());

        // Return to the main menu if the selected theatre is already fully booked.
        if (theatres[theatreNumber - 1].isFull()) {
            System.out.println("The selected theatre is fully booked.");
        } else {
            // Reserve a seat.
            boolean reserved = false;
            while (!reserved) {
                System.out.print("Choose seat number: ");
                int seatNumber = Integer.parseInt(input.nextLine());
                System.out.println();
                if (theatres[theatreNumber - 1].bookSeat(seatNumber, customer)) {
                    System.out.println("Seat booked successfully.");
                    System.out.println("Your code is " + customer.code);
                    reserved = true;
                } else {
                    System.out.println("The seat you selected is unavailable. Try again.");
                }
            }
        }

        System.out.println("\n");
    }

    /**
     * Show the manager report.
     *
     * @param input    the scanner
     * @param theatres the available theatres
     */
    static void managerReport(Scanner input, Theatre[] theatres) {
        System.out.println("\nAvailable theatres: ");
        for (int i = 0; i < theatres.length; i++) {
            System.out.printf("\t%d. %s -- %d seats.%n", i + 1, theatres[i].movie, theatres[i].numSeats);
        }
        System.out.print("\nChoose theatre: ");
        int theatreNumber = Integer.parseInt(input.nextLine());
        System.out.println();
        theatres[theatreNumber - 1].showReport();
    }

    /**
     * Save data into binary files.
     *
     * @param theatres the theatres
     * @throws IOException if the files were inaccessible
     */
    static void saveData(Theatre[] theatres) throws IOException {
        for (Theatre theatre : theatres) {
            FileOutputStream fileOutputStream = new FileOutputStream(theatre.movie + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(theatre);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        }
    }

    /**
     * Test method to check if written data is successfully readable.
     *
     * @param fileName the input file name
     * @throws IOException            if the files were inaccessible
     * @throws ClassNotFoundException if the Theatre class is not found.
     */
    static void testReadData(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Theatre theatre = (Theatre) objectInputStream.readObject();
        managerReport(new Scanner(System.in), new Theatre[]{theatre});
    }
}
