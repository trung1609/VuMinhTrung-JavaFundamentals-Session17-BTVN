package Session17.kha2.business;

import Session17.kha2.entity.ToDo;
import Session17.kha2.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ToDoBusiness {
    public static boolean addTask(String taskName, Boolean status) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_task(?,?)");
            callSt.setString(1, taskName);
            callSt.setBoolean(2, status);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static List<ToDo> listTask() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<ToDo> toDoList = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call list_task()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                toDoList = new ArrayList<>();
                ResultSet rs = callSt.getResultSet();
                while (rs.next()) {
                    ToDo toDo = new ToDo();
                    toDo.setId(rs.getInt("id"));
                    toDo.setTaskName(rs.getString("task_name"));
                    toDo.setStatus(rs.getBoolean("status"));
                    toDoList.add(toDo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return toDoList;
    }

    public static ToDo findTaskById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        ToDo toDo = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_task_by_id(?)}");
            callSt.setInt(1, id);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    toDo = new ToDo();
                    toDo.setId(rs.getInt("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return toDo;
    }

    public static boolean updateTaskStatus(int taskId, Boolean status) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_task_status(?,?)");
            callSt.setInt(1, taskId);
            callSt.setBoolean(2, status);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean deleteTask(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_task(?)");
            callSt.setInt(1, id);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static List<ToDo> getTaskByName(String taskName) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<ToDo> toDoList = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_task_by_name(?)}");
            callSt.setString(1, taskName);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                toDoList = new ArrayList<>();
                while (rs.next()) {
                    ToDo toDo = new ToDo();
                    toDo.setId(rs.getInt("id"));
                    toDo.setTaskName(rs.getString("task_name"));
                    toDo.setStatus(rs.getBoolean("status"));
                    toDoList.add(toDo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return toDoList;
    }

    public static void taskStatistics() {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call task_statistics(?,?)");
            //dang ky kieu du lieu tra ra
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            int done = callSt.getInt(1);
            int notDone = callSt.getInt(2);
            System.out.println("So cong viec da hoan thanh: " + done);
            System.out.println("So cong viec chua hoan thanh: " + notDone);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
}
