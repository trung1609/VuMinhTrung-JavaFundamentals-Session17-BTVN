package Session17.kha2.presentation;

import Session17.kha2.business.ToDoBusiness;
import Session17.kha2.entity.ToDo;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ToDoManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("--- Quan ly cong viec ---");
            System.out.println("1. Them cong viec");
            System.out.println("2. Liet ke cong viec");
            System.out.println("3. Cap nhat trang thai");
            System.out.println("4. Xoa cong viec");
            System.out.println("5. Tim kiem cong viec");
            System.out.println("6. Thong ke cong viec");
            System.out.println("7. Thoat");
            System.out.print("Lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    addTask(sc);
                    break;
                case 2:
                    listTasks();
                    break;
                case 3:
                    updateTaskStatus(sc);
                    break;
                case 4:
                    deleteTask(sc);
                    break;
                case 5:
                    searchTaskByName(sc);
                    break;
                case 6:
                    taskStatistics();
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.err.println("Vui long nhap tu 1-7");
            }
        } while (true);
    }

    public static void addTask(Scanner sc) {
        ToDo toDo = new ToDo();
        toDo.input(sc);
        boolean result = ToDoBusiness.addTask(toDo.getTaskName(), toDo.isStatus());
        if (result) {
            System.out.println("Them cong viec thanh cong");
        } else {
            System.out.println("Co loi xay ra khi them cong viec");
        }
    }

    public static void listTasks() {
        List<ToDo> toDoList = ToDoBusiness.listTask();
        toDoList.forEach(System.out::println);
    }

    public static void updateTaskStatus(Scanner sc) {
        System.out.println("Nhap id cong viec can cap nhat:");
        int id = Integer.parseInt(sc.nextLine());
        ToDo toDo = ToDoBusiness.findTaskById(id);
        if (toDo == null) {
            System.out.println("Khong tim thay id cong viec.");
            return;
        }
        System.out.println("Nhap trang thai moi cua cong viec(true/false): ");
        toDo.setStatus(Boolean.parseBoolean(sc.nextLine()));
        boolean result = ToDoBusiness.updateTaskStatus(id, toDo.isStatus());
        if (result) {
            System.out.println("Cap nhat thong tin thanh cong");
        } else {
            System.out.println("Co loi xay ra khi cap nhat cong viec");
        }
    }

    public static void deleteTask(Scanner sc) {
        System.out.println("Nhap id cong viec can xoa: ");
        int id = Integer.parseInt(sc.nextLine());
        ToDo toDo =  ToDoBusiness.findTaskById(id);
        if (toDo == null){
            System.out.println("Khong tim thay id cong viec.");
            return;
        }
        boolean result = ToDoBusiness.deleteTask(id);
        if (result) {
            System.out.println("Xoa cong viec thanh cong");
        }else {
            System.out.println("Co loi xay ra khi xoa cong viec");
        }
    }

    public static void searchTaskByName(Scanner sc) {
        System.out.println("Nhap ten cong viec can tra cuu: ");
        String name = sc.nextLine();
        List<ToDo> toDoList = ToDoBusiness.getTaskByName(name);
        toDoList.forEach(System.out::println);
    }

    public static void taskStatistics() {
        ToDoBusiness.taskStatistics();
    }
}
