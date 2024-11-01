import java.io.*;
import java.util.Scanner;

public class DoublyLinkedList {

    // 1. Класс, описывающий узел списка - элемент картотеки
    public static class Element {
        // Поля данных
        String name;
        int age;
        Element prev;
        Element next;

        // Конструктор для создания узла
        public Element(String name, int age) {
            this.name = name;
            this.age = age;
            this.prev = null;
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
        private Element head; // головной элемент списка
        private Element tail; // хвостовой элемент списка

        // Функция создания пустого списка
        public List() {
            head = null;
            tail = null;
        }

        // Функция добавления элемента (узла) списка
        public void add(Element element) {
            if (isEmpty()) { // если список - пустой
                head = element; // элемент относится как к головному
                tail = element; // так и хвостовому элементам (1 элемент)
            } else {
                tail.next = element; // добавляем элемент в конец списка
                element.prev = tail; // хвостовой элемент переносим в предыдущий
                tail = element; // хвостовым элементом делаем добавленный элемент
            }
        }

        // Функция удаления элемента из списка
        public void remove(String name) {
            Element current = head;
            // Проходимся по списку
            while (current != null) {
                if (current.name.equals(name)) { // Если текущий элемент совпадает с заданным
                    if (current == head) { // Если элемент является головным
                        head = current.next; // Головным элементом делаем следующий элемент
                        if (head != null) { // Если список не стал пустым
                            head.prev = null; // Устанавливаем предыдущий элемент на null
                        } else {
                            tail = null; // список пустой, тогда и хвостовой элемент пустой
                        }
                    } else if (current == tail) { // Если элемент является хвостовым
                        tail = current.prev; // Хвостовым элементом становится предыдущий
                        tail.next = null; // Ссылка на следующий элемент после хвостового
                    } else {
                        current.prev.next = current.next; // Связываем предыдущий элемент со следующим
                        current.next.prev = current.prev; // Связываем предыдущий элемент следующего элемента с предыдущим
                    }
                    return; // выходим из цикла
                }
                current = current.next; // переходим к следующему элементу
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
            /* Удаляет ссылку на головной и хвостовой элементы списка,
            что делает весь список недоступным для дальнейшего использования */
            head = null;
            tail = null;
        }

        // Функция проверки списка на пустоту
        public boolean isEmpty() {
            return head == null; // если первый элемента - пустой, то и весь список тоже пустой
        }

        // Функция записи списка в файл
        public void saveToFile(String fileName) {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                Element current = head;
                while (current != null) {
                    writer.println(current.name + "," + current.age);
                    current = current.next;
                }
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
                        Element element = new Element("", 0);
                        element.readFromConsole();
                        list.add(element);
                        break;
                    case 2:
                        System.out.print("Введите имя элемента для удаления: ");
                        String nameToRemove = sc.nextLine();
                        list.remove(nameToRemove);
                        break;
                    case 3:
                        System.out.print("Введите имя элемента для вывода: ");
                        String nameToDisplay = sc.nextLine();
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
                        String fileNameToSave = sc.nextLine();
                        list.saveToFile(fileNameToSave);
                        break;
                    case 7:
                        System.out.print("Введите имя файла для загрузки: ");
                        String fileNameToLoad = sc.nextLine();
                        list.readFromFile(fileNameToLoad);
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Некорректный выбор.");
                }
            }
        }
    }
}
