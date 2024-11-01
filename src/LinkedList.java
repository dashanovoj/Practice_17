import java.io.*;
import java.util.Scanner;

public class LinkedList {

    // 1. Класс, описывающий узел списка - элемент картотеки
    public static class Element {
        // Поля данных
        String name;
        int age;
        Element next;

        // Конструктор класса
        public Element(String name, int age) {
            this.name = name;
            this.age = age;
            this.next = null;
        }

        // Функция для считывания атрибута объекта с консоли
        public void readFromConsole() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите имя: ");
            this.name = sc.nextLine();
            System.out.println("Введите возраст: ");
            this.age = sc.nextInt();
        }

        // Вывод атрибутов на экран
        public void print() {
            System.out.println("Имя: " + name + "\nВозраст: " + age);
        }
    }

    // 2. Контейнерный класс - Список
    public static class List {
        private Element head;

        // Функция создания пустого списка
        public List() {
            head = null;
        }

        // Функция добавления элемента (узла) списка
        public void add(Element element) {
            element.next = head; // новый элемент становится головным
            head = element;
        }

        // Функция удаления элемента из списка
        public void remove(String name) {
            Element current = head;
            Element previous = null;

            while (current != null) { // Проходимся по списку
                if (current.name.equals(name)) { // Находим необходимый для удаления элемент
                    if (previous == null) { // Если удаляемый элемент - головной
                        head = current.next; // Головным становится следующий элемент
                    } else {
                        // Удаляем элемент (устанавливаем next предыдущего элемента на следующий элемент после текущего)
                        previous.next = current.next;
                    }
                    return; // выходим из метода, чтобы избежать дальнейшей итерации по списку
                }
                // Если имя текущего элемента не совпадает с заданным - смещаемся на элемент вперёд
                previous = current;
                current = current.next;
            }
            // При завершении цикла и ненахождении элемента выводим
            System.out.println("Элемент с именем " + name + " не найден.");
        }

        // Функция вывода элемента (узла) списка на экран
        public void printElement(String name) {
            Element current = head;

            // Проходимся по списку
            while (current != null) { // Если имя текущего элемента совпадает с заданным
                if (current.name.equals(name)) {
                    current.print(); // с помощью метода класса Element выводим элемент
                    return; // выходим из цикла
                }
                // Если не совпадает
                current = current.next; // перемещаемся дальше
            }
            // При завершении цикла и ненахождении элемента выводим
            System.out.println("Элемент с именем " + name + " не найден.");
        }

        // Функция очистки списка
        public void clear() {
            /* Удаляет ссылку на первый элемент списка,
            что делает весь список недоступным для дальнейшего использования */
            head = null;
        }

        // Функция проверки списка на пустоту
        public boolean isEmpty() {
            return head == null; // если первый элемента - пустой, то и весь список тоже пустой
        }

        // Функция записи списка в файл
        public void saveToFile(String fileName) {
            // Создаём объект PrintWriter, который будет записывать данные в файл
            try (PrintWriter writer = new PrintWriter(fileName)) {
                Element current = head;
                // Проходимся по списку
                while (current != null) {
                    // Записываем данные текущего элемента в файл.
                    writer.println(current.name + "," + current.age);
                    current = current.next; // переходим к следующему элементу
                }
            } catch (
                    FileNotFoundException e) { // Обработка исключения (файл не может быть создан или открыт для записи)
                System.out.println("Ошибка записи в файл: " + e.getMessage());
            }
        }

        // Функция чтения списка из файла
        public void readFromFile(String fileName) {
            /* Создаём объект BufferedReader, который будет читать данные из файла
            FileReader используется для открытия файла, а BufferedReader обеспечивает более эффективное чтение строк */
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line; // Поле для хранения строк, считанных из файла
                // Проходимся по строкам
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(","); // разбиваем строку по запятым
                    String name = parts[0]; // имя
                    int age = Integer.parseInt(parts[1]); // возраст (строку переводим в целочисленный тип)
                    add(new Element(name, age)); // через метод add добавляем элемент в список
                }
            } catch (IOException e) { // Обработка исключения (файл не существует или недоступен)
                System.out.println("Ошибка чтения из файла: " + e.getMessage());
            }
        }
    }

    // 3. Класс Тестер
    public static class Tester {
        public static void main(String[] args) {
            List list = new List();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Добавить элемент");
                System.out.println("2. Удалить элемент");
                System.out.println("3. Вывести элемент");
                System.out.println("4. Очистить список");
                System.out.println("5. Проверить пустоту списка");
                System.out.println("6. Сохранить список в файл");
                System.out.println("7. Загрузить список из файла");
                System.out.println("0. Выход");
                System.out.print("Выберите действие: ");

                int choice = sc.nextInt();
                sc.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1:
                        Element element = new Element("", 0); // создаём экземпляр класса
                        element.readFromConsole(); // передаём имя и возраст
                        list.add(element); // добавляем в список
                        break;
                    case 2:
                        System.out.print("Введите имя элемента для удаления: ");
                        String nameToRemove = sc.nextLine(); // получаем имя для удаления
                        list.remove(nameToRemove); // удаляем элемент
                        break;
                    case 3:
                        System.out.print("Введите имя элемента для вывода: ");
                        String nameToDisplay = sc.nextLine(); // получаем имя для вывода
                        list.printElement(nameToDisplay); // выводим элемент
                        break;
                    case 4:
                        list.clear(); // очищаем список
                        System.out.println("Список очищен.");
                        break;
                    case 5:
                        if (list.isEmpty()) { // проверка списка на пустоту
                            System.out.println("Список пуст.");
                        } else {
                            System.out.println("Список не пуст.");
                        }
                        break;
                    case 6:
                        System.out.print("Введите имя файла для сохранения: ");
                        String fileNameToSave = sc.nextLine(); // считываем файл, в который запишем список
                        list.saveToFile(fileNameToSave); // записываем список в файл
                        break;
                    case 7:
                        System.out.print("Введите имя файла для загрузки: ");
                        String fileNameToLoad = sc.nextLine(); // считываем файл, с которого получим список
                        list.readFromFile(fileNameToLoad); // считываем список
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        sc.close(); // выход из программы
                        return;
                    default: // если что-то другое ввели
                        System.out.println("Некорректный выбор.");
                }
            }

        }
    }
}
