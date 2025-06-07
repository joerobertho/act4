import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts = new HashMap<>();

    public void load(String filename) {
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public void save(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
    }

    public void delete(String number) {
        contacts.remove(number);
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);
        String filename = "contacts.csv";

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    addressBook.load(filename);
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número telefónico: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    addressBook.save(filename);
                    System.out.println("Contacto creado exitosamente.");
                    break;
                case 3:
                    System.out.print("Ingrese el número telefónico a eliminar: ");
                    String deleteNumber = scanner.nextLine();
                    addressBook.delete(deleteNumber);
                    addressBook.save(filename);
                    System.out.println("Contacto eliminado exitosamente.");
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}