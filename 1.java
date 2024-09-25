// ------------------ OBSERVER PATTERN ------------------

// Observer interface
interface WeatherObserver {
    void update(double temperature);
}

// Concrete Observer: Display devices
class MobileDisplay implements WeatherObserver {
    @Override
    public void update(double temperature) {
        System.out.println("Mobile display shows temperature: " + temperature + "°C");
    }
}

class TelevisionDisplay implements WeatherObserver {
    @Override
    public void update(double temperature) {
        System.out.println("Television display shows temperature: " + temperature + "°C");
    }
}

// Subject: Weather Station
class WeatherMonitor {
    private List<WeatherObserver> observers = new ArrayList<>();
    private double temperature;

    public void registerObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        notifyObservers();
    }

    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature);
        }
    }
}

// ------------------ STRATEGY PATTERN ------------------

// Strategy interface
interface SortingStrategy {
    void sort(int[] array);
}

// Concrete strategies
class BubbleSortStrategy implements SortingStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorting array using Bubble Sort");
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

class QuickSortStrategy implements SortingStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorting array using Quick Sort");
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                // Swap
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // Swap
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}

// Context class
class SorterContext {
    private SortingStrategy strategy;

    public void setSortingStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortArray(int[] array) {
        strategy.sort(array);
    }
}

// ------------------ SINGLETON PATTERN ------------------

// Singleton class
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Creating Database Connection...");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Executing Query: " + query);
    }
}

// ------------------ FACTORY PATTERN ------------------

// Product interface
interface Shape {
    void draw();
}

// Concrete products
class CircleShape implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class SquareShape implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Square");
    }
}

// Factory class
class ShapeFactory {
    public Shape createShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new CircleShape();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new SquareShape();
        }
        return null;
    }
}

// ------------------ ADAPTER PATTERN ------------------

// Existing interface
interface PaymentSystem {
    void processPayment(double amount);
}

// Legacy class (adaptee)
class LegacyPaymentSystem {
    public void makePayment(double amount) {
        System.out.println("Processing payment of: $" + amount + " using the legacy system.");
    }
}

// Adapter class
class PaymentAdapter implements PaymentSystem {
    private LegacyPaymentSystem legacySystem;

    public PaymentAdapter(LegacyPaymentSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    @Override
    public void processPayment(double amount) {
        legacySystem.makePayment(amount);
    }
}

// ------------------ DECORATOR PATTERN ------------------

// Component interface
interface Coffee {
    String getDescription();
    double cost();
}

// Concrete component
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double cost() {
        return 2.0;
    }
}

// Decorator class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost();
    }
}

// Concrete decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.2;
    }
}

// ------------------ MAIN CLASS ------------------

public class Main {
    public static void main(String[] args) {
        // 1. Observer Pattern Demo
        System.out.println("---- Observer Pattern Demo ----");
        WeatherMonitor weatherStation = new WeatherMonitor();
        WeatherObserver mobileDisplay = new MobileDisplay();
        WeatherObserver televisionDisplay = new TelevisionDisplay();
        weatherStation.registerObserver(mobileDisplay);
        weatherStation.registerObserver(televisionDisplay);
        weatherStation.setTemperature(25.5);

        // 2. Strategy Pattern Demo
        System.out.println("\n---- Strategy Pattern Demo ----");
        int[] array = {10, 5, 2, 8, 7};
        SorterContext sorterContext = new SorterContext();
        sorterContext.setSortingStrategy(new BubbleSortStrategy());
        sorterContext.sortArray(array);
        sorterContext.setSortingStrategy(new QuickSortStrategy());
        sorterContext.sortArray(array);

        // 3. Singleton Pattern Demo
        System.out.println("\n---- Singleton Pattern Demo ----");
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        dbConnection.executeQuery("SELECT * FROM users");

        // 4. Factory Pattern Demo
        System.out.println("\n---- Factory Pattern Demo ----");
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.createShape("CIRCLE");
        shape1.draw();
        Shape shape2 = shapeFactory.createShape("SQUARE");
        shape2.draw();

        // 5. Adapter Pattern Demo
        System.out.println("\n---- Adapter Pattern Demo ----");
        LegacyPaymentSystem legacySystem = new LegacyPaymentSystem();
        PaymentSystem paymentSystem = new PaymentAdapter(legacySystem);
        paymentSystem.processPayment(100);

        // 6. Decorator Pattern Demo
        System.out.println("\n---- Decorator Pattern Demo ----");
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.printf("%s costs $%.2f\n", coffee.getDescription(), coffee.cost());
    }
}
