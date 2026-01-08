package Session17.kha2.entity;

import java.util.Scanner;

public class ToDo {
    private int id;
    private String taskName;
    private boolean status;

    public ToDo() {
    }

    public ToDo(int id, String taskName, boolean status) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ID: %d - Task name: %s - Status: %s", id, taskName, status ? "Da hoan thanh" : "Chua hoan thanh");
    }

    public void input(Scanner sc) {
        System.out.println("Nhap ten cong viec: ");
        this.taskName = sc.nextLine();
        System.out.println("Nhap trang thai cong viec: ");
        this.status = Boolean.parseBoolean(sc.nextLine());
    }
}
