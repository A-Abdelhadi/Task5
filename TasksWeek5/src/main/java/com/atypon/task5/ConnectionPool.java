package com.atypon.task5;

import java.sql.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ConnectionPool {
    private BlockingQueue<Connection> queue ;
    private static int availablePoolCount = 0;

    ConnectionPool(int capacity){
        initiate(capacity);
    }

    public void initiate(int capacity){

        try {

            queue = new ArrayBlockingQueue<Connection>(capacity);
            Class.forName("com.mysql.jdbc.Driver");

            for (int i = 0 ; i < capacity ; i++){
                queue.put( DriverManager.getConnection("jdbc:mysql://localhost/task5?" + "user=root&password=mysqlserver@12345"));
                System.out.println("new connection added " + i);
            }

            availablePoolCount = capacity;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        ReentrantLock lock = new ReentrantLock();



        try {
            System.out.println("Thread " + Thread.currentThread().getId() + "is trying to Get a Connection! " +
                    " availablePoolCount: " + availablePoolCount);

            lock.lock();
            try {
                if ( availablePoolCount != 0 )
                    availablePoolCount --;
            }finally {
                lock.unlock();
            }

            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void returnConnection(Connection con) {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {
            availablePoolCount ++;
        }finally {
            lock.unlock();
        }

        System.out.println("Thread " + Thread.currentThread().getId()  + " is Returning a Connection! " +
                " availablePoolCount: " + availablePoolCount);
        queue.add(con);
    }
}
