package com.atypon.task5;

import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.*;

public class Task5Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/task5?user=root&password=mysqlserver@12345");
            selectOperationAndCall(connect);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void selectOperationAndCall(Connection connect) {
        Scanner in =  new Scanner(System.in);
        String operation = in.next();

        if ( operation.equalsIgnoreCase("Create"))
            create(connect);
        else if (operation.equalsIgnoreCase("Read"))
            read(connect);
        else if (operation.equalsIgnoreCase("Update"))
            update(connect);
        else if (operation.equalsIgnoreCase("Delete"))
            delete(connect);
    }

    private static void create(Connection connect) {
        Statement stmt = null;

        try{
            stmt = connect.createStatement();
            String sql="INSERT INTO employee ( EmployeeName, EmployeeDep, EmployeeSalary)" +
                    " VALUES ('Josh', 'HR',600)";
            stmt.executeUpdate(sql);

        }catch ( Exception e ){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void read(Connection connect) {

        Statement stmt = null;
        ResultSet rs = null;

        try{
            stmt = connect.createStatement();
            String sql="Select * from employee";
            rs = stmt.executeQuery(sql);

            System.out.println("EmployeeID    EmployeeName    EmployeeDep   EmployeeSalary");
            while (rs.next()){
                System.out.println(rs.getString("EmployeeID") + "             " +
                        rs.getString("EmployeeName") + "            " +
                        rs.getString("EmployeeDep") + "            " +
                        rs.getString("EmployeeSalary"));
            }

        }catch ( Exception e ){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void update(Connection connect) {
        Statement stmt = null;

        Scanner in =  new Scanner(System.in);

        read(connect);

        System.out.println("Enter employee ID of employee u want to update!");
        String empID = in.next();

        System.out.println("Enter which column u want to update: ");
        String column = in.next();

        System.out.println("Enter new value for selected column: ");
        String newValue = in.next();

        try{
            stmt = connect.createStatement();
            String sql="UPDATE employee " +
                    "SET " + column +" = '" + newValue +
                    "' WHERE EmployeeID = " + empID;
            stmt.executeUpdate(sql);

        }catch ( Exception e ){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void delete(Connection connect) {
        Statement stmt = null;

        Scanner in =  new Scanner(System.in);

        read(connect);


        System.out.println("Enter employee ID of employee u want to Delete!");
        String empID = in.next();

        try{
            stmt = connect.createStatement();
            String sql="DELETE FROM employee " +
                    " WHERE EmployeeID = " + empID;
            stmt.executeUpdate(sql);

        }catch ( Exception e ){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
