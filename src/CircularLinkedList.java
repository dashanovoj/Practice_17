import java.io.*;
import java.util.Scanner;

public class CircularLinkedList {

    // 1. Класс, описывающий узел списка - элемент картотеки
    public static class Element {
        // Поля данных
        String name;
        int age;
        Element prev; // Предыдущий элемент
        Element next; // Следующий элемент

        // Конструктор для создания узла
        public Element(String name, int age) {
            this.name = name;
            this.age = age;
            this.prev = null;
            this.next = null;
        }

        // Функция для считывания атрибутов объекта с консоли
        public void readFromConsole() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите имя: ");
            this.name = scanner.nextLine();
            System.out.print("Введите возраст: ");
            this.age = scanner.nextInt();
        }

        // Вывод атрибутов на экран
        public void print() {
            System.out.println("Имя: " + name + "\nВозраст: " + age);
        }
    }

    // 2. Контейнерный класс - Список
    public static class List {
        private Element head; // Головной элемент списка

        // Функция создания пустого списка
        public List() {
            head = null;
        }

        // Функция добавления элемента (узла) в список
        public void add(Element element) {
            if (isEmpty()) {
                head = element;
                element.next = element; // Устанавливаем связь с самим собой
                element.prev = element;
            } else {
                element.next = head; // Вставляем элемент перед головным
                element.prev = head.prev; // Устанавливаем связь с предыдущим элементом
                head.prev.next = element; // Обновляем связь предыдущего элемента
                head.prev = element; // Обновляем связь головного элемента
            }
        }

        // Функция удаления элемента из списка
        public void remove(String name) {
            Element current = head;
            do {
                if (current.name.equals(name)) {
                    if (current == head && current.next == current) { // Единственный элемент
                        head = null;
                    } else if (current == head) { // Удаление головного элемента
                        head = current.next;
                        head.prev = current.prev;
                        current.prev.next = head;
                    } else {
                        current.prev.next = current.next; // Связываем предыдущий элемент со следующим
                        current.next.prev = current.prev; // Связываем предыдущий элемент следующего элемента с предыдущим
                    }
                    return; // выходим из цикла
                }
                current = current.next; // переходим к следующему элементу
            } while (current != head);
            System.out.println("Элемент с именем " + name + " не найден.");
        }

        // Функция вывода элемента (узла) списка на экран
        public void printElement(String name) {
            Element current = head;
            do {
                if (current.name.equals(name)) {
                    current.print();
                    return;
                }
                current = current.next;
            } while (current != head);

            System.out.println("Элемент с именем " + name + " не найден.");
        }

        // Функция очистки списка
        public void clear() {
            head = null;
        }

        // Функция проверки списка на пустоту
        public boolean isEmpty() {
            return head == null;
        }

        // Функция записи списка в файл
        public void saveToFile(String fileName) {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                Element current = head;
                do {
                    writer.println(current.name + "," + current.age);
                    current = current.next;
                } while (current != head);
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка записи в файл: " + e.getMessage());
            }
        }

        // Функция чтения списка из файла
        public void readFromFile(String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    add(new Element(name, age));
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения из файла: " + e.getMessage());
            }
        }
    }

    // 3. Класс Тестер
    public static class Tester {
        public static void main(String[] args) {
            List list = new List();
            Scanner scanner = new Scanner(System.in);

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

                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1:
                        Element element = new Element("", 0);
                        element.readFromConsole();
                        list.add(element);
                        break;
                    case 2:
                        System.out.print("Введите имя элемента для удаления: ");
                        String nameToRemove = scanner.nextLine();
                        list.remove(nameToRemove);
                        break;
                    case 3:
                        System.out.print("Введите имя элемента для вывода: ");
                        String nameToDisplay = scanner.nextLine();
                        list.printElement(nameToDisplay);
                        break;
                    case 4:
                        list.clear();
                        System.out.println("Список очищен.");
                        break;
                    case 5:
                        if (list.isEmpty()) {
                            System.out.println("Список пуст.");
                        } else {
                            System.out.println("Список не пуст.");
                        }
                        break;
                    case 6:
                        System.out.print("Введите имя файла для сохранения: ");
                        String fileNameToSave = scanner.nextLine();
                        list.saveToFile(fileNameToSave);
                        break;
                    case 7:
                        System.out.print("Введите имя файла для загрузки: ");
                        String fileNameToLoad = scanner.nextLine();
                        list.readFromFile(fileNameToLoad);
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Некорректный выбор.");
                }
            }
        }
    }
}