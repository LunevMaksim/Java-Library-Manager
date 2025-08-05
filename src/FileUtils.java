import java.io.*;
import java.util.Scanner;

public class FileUtils {
    private final Scanner consoleScanner;
    public FileUtils() {
        consoleScanner = new Scanner(System.in);
    }
    public void inFile() throws IOException { // Запись в файл
        File file = new File("library.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        try (PrintStream out = new PrintStream(new FileOutputStream(file, true))){
            System.out.print("Введите название книги: ");
            String title = consoleScanner.nextLine();
            System.out.print("Введите автора книги: ");
            String author = consoleScanner.nextLine();
            System.out.print("Введите год издания книги: ");
            int age = consoleScanner.nextInt();
            System.out.print("Введите оценку книги(1-10): ");
            int score = consoleScanner.nextInt();
            consoleScanner.nextLine();
            out.println(title + " | " + author + " | " + age + " | " + score);
        }
    }

    public void outFile() throws FileNotFoundException { // Чтение из файла
        File file = new File("library.txt");
        try (Scanner fileScanner = new Scanner(file)){
            double middle = 0.0;
            int count = 0;
            int max = 0;
            while (fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split(" \\| ");
                middle += Integer.parseInt(line[2]);
                count++;
                max = Math.max(max, Integer.parseInt(line[3]));
            }
            System.out.println("Средний год изданий: " + (middle / count));
            System.out.println("Количество книг: " + count);
            System.out.println("Самая высокая оценка: " + max);
        } catch (ArrayIndexOutOfBoundsException exception){
            System.out.println("Библиотека пуста!");
        }
    }
    public void authorInfo() {
        System.out.print("Введите автора для поиска: ");
        String searchAuthor = consoleScanner.nextLine().trim().toLowerCase();

        File file = new File("library.txt");
        if (!file.exists() || file.length() == 0) {
            System.out.println("Библиотека пуста!");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 4) continue;

                String author = parts[1].trim().toLowerCase();
                if (author.equals(searchAuthor)) {
                    System.out.println(line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Книги автора '" + searchAuthor + "' не найдены");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        }
    }

    public void displayInfo() throws IOException {
        while (true) {
            System.out.println("\n=== Меню управления библиотекой ===");
            System.out.println("1 - Добавить книгу");
            System.out.println("2 - Показать статистику");
            System.out.println("3 - Поиск по автору");
            System.out.println("4 - Выход");
            System.out.print("Введите номер операции: ");

            if (!consoleScanner.hasNextInt()) {
                consoleScanner.nextLine();
                System.out.println("Ошибка: введите число от 1 до 4");
                continue;
            }

            int choice = consoleScanner.nextInt();
            consoleScanner.nextLine();

            switch (choice) {
                case 1:
                    inFile();
                    break;
                case 2:
                    outFile();
                    break;
                case 3:
                    authorInfo();
                    break;
                case 4:
                    consoleScanner.close();
                    System.out.println("Работа завершена. До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
