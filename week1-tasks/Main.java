import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double salary;
    private String position;

    public Employee(int id, String name, double salary, String position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: $" + salary + ", Position: " + position;
    }
}

class EmployeeManagementSystem {
    private ArrayList<Employee> employees;
    private String dataFileName = "employees.txt";

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        loadData();
    }

    private void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFileName))) {
            outputStream.writeObject(employees);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFileName))) {
            employees = (ArrayList<Employee>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or an error occurs during deserialization,
            // it's fine to initialize an empty ArrayList.
            employees = new ArrayList<>();
        }
    }

    public void addEmployee(int id, String name, double salary, String position) {
        employees.add(new Employee(id, name, salary, position));
        saveData();
        System.out.println("Employee added successfully.");
    }

    public void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    public void updateEmployee(int id, String newName, double newSalary, String newPosition) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee = new Employee(id, newName, newSalary, newPosition);
                saveData();
                System.out.println("Employee updated successfully.");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void searchEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                System.out.println("Employee found:\n" + employee);
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void deleteEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.remove(employee);
                saveData();
                System.out.println("Employee with ID " + id + " deleted successfully.");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem employeeManagementSystem = new EmployeeManagementSystem();

        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. View Employee Details");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the buffer
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee ID. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    scanner.nextLine(); // Consume the newline character left by nextInt()

                    System.out.print("Enter Employee Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Employee Salary: ");
                    double salary;
                    try {
                        salary = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee Salary. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    scanner.nextLine(); // Consume the newline character left by nextDouble()

                    System.out.print("Enter Employee Position: ");
                    String position = scanner.nextLine();

                    employeeManagementSystem.addEmployee(id, name, salary, position);
                    break;

                case 2:
                    employeeManagementSystem.viewAllEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId;
                    try {
                        updateId = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee ID. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    scanner.nextLine(); // Consume the newline character left by nextInt()

                    System.out.print("Enter New Employee Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter New Employee Salary: ");
                    double newSalary;
                    try {
                        newSalary = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for New Employee Salary. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    scanner.nextLine(); // Consume the newline character left by nextDouble()

                    System.out.print("Enter New Employee Position: ");
                    String newPosition = scanner.nextLine();

                    employeeManagementSystem.updateEmployee(updateId, newName, newSalary, newPosition);
                    break;

                case 4:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId;
                    try {
                        searchId = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee ID. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    employeeManagementSystem.searchEmployee(searchId);
                    break;

                case 5:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee ID. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    employeeManagementSystem.deleteEmployee(deleteId);
                    break;

                case 6:
                    System.out.print("Enter Employee ID to view details: ");
                    int detailsId;
                    try {
                        detailsId = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for Employee ID. Please enter a number.");
                        scanner.nextLine(); // Clear the buffer
                        continue;
                    }

                    employeeManagementSystem.searchEmployee(detailsId);
                    break;

                case 7:
                    System.out.println("Exiting Employee Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
