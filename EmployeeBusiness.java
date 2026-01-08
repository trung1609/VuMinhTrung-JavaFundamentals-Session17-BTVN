package Session17.business;

import Session17.entity.Employee;
import Session17.util.ConnectionDB;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBusiness {
    public static List<Employee> findAll() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Employee> listEmployee = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_all_employee()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                listEmployee = new ArrayList<>();
                ResultSet rs = callSt.getResultSet();
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmpId(rs.getInt("emp_id"));
                    employee.setEmpName(rs.getString("emp_name"));
                    employee.setAge(rs.getInt("emp_age"));
                    employee.setSalary(rs.getDouble("emp_salary"));
                    employee.setStatus(rs.getBoolean("emp_status"));
                    listEmployee.add(employee);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listEmployee;
    }

    public static boolean create(Employee employee) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call create_employee(?,?,?)");
            //1. Set giá trị cho tham số truyền vào
            callSt.setString(1, employee.getEmpName());
            callSt.setInt(2, employee.getAge());
            callSt.setBigDecimal(3, BigDecimal.valueOf(employee.getSalary()));
            callSt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static Employee findById(int empId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Employee employee = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_employee_by_id(?)}");
            //Set tham số vào
            callSt.setInt(1, empId);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    employee = new Employee();
                    employee.setEmpId(rs.getInt("emp_id"));
                    employee.setEmpName(rs.getString("emp_name"));
                    employee.setAge(rs.getInt("emp_age"));
                    employee.setSalary(rs.getDouble("emp_salary"));
                    employee.setStatus(rs.getBoolean("emp_status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employee;
    }

    public static boolean updateEmployee(Employee employee) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_employee(?,?,?,?,?)");
            callSt.setInt(1, employee.getEmpId());
            callSt.setString(2, employee.getEmpName());
            callSt.setInt(3, employee.getAge());
            callSt.setBigDecimal(4, BigDecimal.valueOf(employee.getSalary()));
            callSt.setBoolean(5, employee.isStatus());
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean delete(int empId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_employee(?)");
            callSt.setInt(1, empId);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static double getSalaryById(int empId) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{?=call get_salary_By_Id(?)}");
            //đăng ký kiểu dữ liệu của giá trị trả ra
            callSt.registerOutParameter(1, Types.NUMERIC);
            //set giá trị tham số vào id_in
            callSt.setInt(2, empId);
            callSt.execute();
            return callSt.getBigDecimal(1).doubleValue();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return 0;
    }

    public static List<Employee> getEmployeeByName(String empName) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Employee> listEmployee = null;
        Employee employee = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_emp_by_name(?)}");
            callSt.setString(1, empName);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                listEmployee = new ArrayList<>();
                while (rs.next()) {
                    employee = new Employee();
                    employee.setEmpId(rs.getInt("emp_id"));
                    employee.setEmpName(rs.getString("emp_name"));
                    employee.setAge(rs.getInt("emp_age"));
                    employee.setSalary(rs.getDouble("emp_salary"));
                    employee.setStatus(rs.getBoolean("emp_status"));
                    listEmployee.add(employee);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listEmployee;
    }
}