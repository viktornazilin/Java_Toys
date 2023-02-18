package view;

import controller.Controller;
import model.Toy;

import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    public void run() {
        Commands com = Commands.NONE;

        while (true) {
            try {
                String command = prompt("Enter the command (help - list of all commands): ");
                com = Commands.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command!");
                continue;
            }
            switch (com) {
                case EXIT:
                    return;
                case HELP:
                    System.out.println("List of commands:");
                    System.out.println("List - display list of all toys");
                    System.out.println("Read - display toy by ID");
                    System.out.println("Put - add new toy");
                    System.out.println("Edit - edit note by ID");
                    System.out.println("Delete - delete note by ID");
                    System.out.println("Get - start raffle");
                    System.out.println("Exit - exit from program");
                    break;
                case LIST:
                    controller.readToys().forEach(System.out::println);
                    break;
                case READ:
                    String id = prompt("Enter ID: ");
                    try {
                        Toy toy = controller.readToy(id);
                        System.out.println(toy);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case PUT:
                    try {
                        String name = prompt("Enter toy name: ");
                        Byte probability;
                        while (true) {
                            try {
                                probability = Byte.valueOf(prompt("Enter toy probability (1-100): "));
                                if (probability > 0 && probability <= 100)
                                    break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Input error! Try again!");
                            }
                        }
                        controller.saveToy(new Toy(name, probability));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case EDIT:
                    try {
                        String toyId = prompt("Enter toy ID: ");
                        String name = prompt("Enter toy name: ");
                        Byte probability;
                        while (true) {
                            try {
                                probability = Byte.valueOf(prompt("Enter toy probability (1-100): "));
                                if (probability > 0 && probability <= 100)
                                    break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Input error! Try again!");
                            }
                        }
                        controller.editToy(new Toy(toyId, name, probability));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case DELETE:
                    String toyId = prompt("Enter toy ID: ");
                    controller.deleteToy(toyId);
                    break;
                case GET:
                    controller.raffleToy();
                    break;
            }
        }
    }
}