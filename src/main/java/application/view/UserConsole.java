package application.view;

import application.model.HospitalInitializer;
import application.repository.Repo;
import middleware.proto.HospitalOuterClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import application.repository.IRepo;

public class UserConsole implements IUserKonsole {
    IRepo repo = Repo.getInstance();

    private static final String LOGGER_PATH = "project.log";
    private Scanner scanner;

    public UserConsole() {
        this.scanner = new Scanner(System.in);
    }

    public void open() {


        displayHints();

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            processUserInput(input);
        }
    }

    private void displayHints() {
        String welcomeMessage = "Welcome to HMS-System";
        String hints = "You can enter commands below.\n"
                + "Type 'help' to see a list of available commands.\n";
        String formattedMessage = String.format("%" + ((75 - welcomeMessage.length()) / 2 + welcomeMessage.length()) + "s%n", welcomeMessage);

        System.out.println(formattedMessage);
        System.out.println(hints);
    }

    private void processUserInput(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());

        if (!input.equalsIgnoreCase("clear")) {
            System.out.println("\n[" + timestamp + "]: " + input + "\n");
        }

        if (input.equalsIgnoreCase("help")) {
            displayHelp();
        } else if (input.toLowerCase().startsWith("setab ")) {

            try {
                int availableBeds = Integer.parseInt(input.substring(6).trim());
                setAB(availableBeds);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter for 'setAB'. Please provide an integer value.");
            }
        } else if (input.equalsIgnoreCase("gethi")) {

            getHI();
        } else if (input.equalsIgnoreCase("getahi")) {

            getAHI();
        } else if (input.equalsIgnoreCase("clear")) {

            clear();
        } else if (input.equalsIgnoreCase("printLogger")) {

            readLogger(LOGGER_PATH);
        }else if (input.equalsIgnoreCase("whoIsLeader")) {

           whoISLeader();
        }

    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private void displayHelp() {
        String helpMessage = "Available commands:\n"
                + "1. help - Display this help message\n"
                + "2. setAB <number> - Set available beds\n"
                + "3. getHI - Get hospital information\n"
                + "4. getAHI - Get all hospitals information in Network\n"
                + "5. whoIsLeader - to get leader id in Network\n"
                + "6. printLogger - to print all logged data \n"
                + "7. clear - Clear the view\n";
        System.out.println(helpMessage);
    }

    private void setAB(int availableBeds) {
        assert availableBeds >= 0;
        HospitalOuterClass.Hospital hospital = repo.get(0);
        if (repo.updateAvailableBeds(hospital.getHost(), hospital.getPort(), availableBeds)) {

            System.out.println("Hospital:" + hospital.getName() + " is successfully updated\n");

        } else {
            System.out.println("Hospital:" + hospital.getName() + " is unsuccessfully updated\n");
            System.out.println("Make sure that availble beds less than total beds or equal");
        }

    }

    private void getHI() {
        HospitalOuterClass.Hospital hospital = repo.get(0);
        StringBuilder hospitalInfo = new StringBuilder("Hospital Information:\n");
        hospitalInfo.append("Name: ").append(hospital.getName()).append("\n");
        hospitalInfo.append("ID: ").append(hospital.getId()).append("\n");
        hospitalInfo.append("Host: ").append(hospital.getHost()).append("\n");
        hospitalInfo.append("Port: ").append(hospital.getPort()).append("\n");
        hospitalInfo.append("Total Beds: ").append(hospital.getTotalBeds()).append("\n");
        hospitalInfo.append("Available Beds: ").append(hospital.getAvailableBeds()).append("\n");

        System.out.println(hospitalInfo);
    }

    private void getAHI() {

        ArrayList<HospitalOuterClass.Hospital> repo = this.repo.getRepo();
        if (!repo.isEmpty()) {
            System.out.println("All Hospital Information:");

            for (HospitalOuterClass.Hospital hospital : repo) {
                StringBuilder hospitalInfo = new StringBuilder();
                hospitalInfo.append("Hospital ID: ").append(hospital.getId()).append("\n");
                hospitalInfo.append("Name: ").append(hospital.getName()).append("\n");
                hospitalInfo.append("Host: ").append(hospital.getHost()).append("\n");
                hospitalInfo.append("Port: ").append(hospital.getPort()).append("\n");
                hospitalInfo.append("Total Beds: ").append(hospital.getTotalBeds()).append("\n");
                hospitalInfo.append("Available Beds: ").append(hospital.getAvailableBeds()).append("\n");
                hospitalInfo.append("------------------------------");
                System.out.println(hospitalInfo);
            }
        } else {
            System.out.println("No hospitals found.");

        }

    }
    private void whoISLeader() {
        System.out.println("leaderID:"+  HospitalInitializer.leaderID);

    }


    // Lese die Log-Einträge aus der Datei
    private void readLogger(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }

}
