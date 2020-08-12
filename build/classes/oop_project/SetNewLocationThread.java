/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_project;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static oop_project.Driver.driver_index;

/**
 *
 * @author LENOVO
 */
class MyThreadback implements Runnable
            {
                Thread t;
                int sleep_time;
                int driverindex;
                int finalpos;
                MyThreadback(int sleep_time,int driverindex,int finalpos)//the constructor of the thread
                {
                    this.sleep_time = sleep_time;
                    this.driverindex=driverindex;
                    this.finalpos=finalpos;
                    t = new Thread(this);
                    t.start();
                }
                public void run()
                {
                    try{
                        String username=UserDetails.user;
                        try{
                            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                            Statement stmt=con.createStatement();
                            String query="Update driver set Availability=1 where S_No="+((int)(driverindex+1))+";";//changes the database and sets the availability
                            //of the driver to 0 cause it has completed the trip
                            stmt.executeUpdate(query);
                            con.close();
                            stmt.close();//closes connection
                        }
                        catch (HeadlessException | SQLException e)
                        {
                            JOptionPane.showMessageDialog(null,"error");//catch statement  
                        }
                        System.out.print("Time to go back to new location is"+sleep_time);
                        t.sleep(sleep_time);
                        try{
                            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                            Statement stmt=con.createStatement();
                            String query2="Update driver set Location="+finalpos+" where S_No="+((int)(driver_index+1))+";";
                            stmt.executeUpdate(query2);
                            String query="Update driver set Availability=0 where S_No="+((int)(driverindex+1))+";";//changes the database and sets the availability
                            //of the driver to 0 cause it has completed the trip
                            stmt.executeUpdate(query);                            
                            con.close();
                            stmt.close();//closes connection
                        }
                        catch (HeadlessException | SQLException e)
                        {
                            JOptionPane.showMessageDialog(null,"error");//catch statement  
                        }
                        
                        System.out.print("Yes it slept");
                    }
                    catch(InterruptedException ie)
                    {
                        JOptionPane.showMessageDialog(null,"Sleep Interrupted");
                    }
                }
            }