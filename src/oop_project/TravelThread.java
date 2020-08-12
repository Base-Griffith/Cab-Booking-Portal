/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_project;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author LENOVO
 */
class TravelThread implements Runnable
            {
                Thread t;
                int sleep_time;
                int driverindex;
                int start,end,No_of_cities;
                String olduser;
                double rating;
                TravelThread(int sleep_time,int driverindex,int start,int end,int No_of_cities,String olduser)//the constructor of the thread
                {
                    this.sleep_time = sleep_time;
                    this.driverindex=driverindex;
                    this.start=start;
                    this.end=end;
                    this.olduser=olduser;
                    this.No_of_cities=No_of_cities;
                    t = new Thread(this);
                    t.start();
                }
                public void run()
                {
                    try{
                        try{
                            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                            Statement stmt=con.createStatement();
                            String query="Select Location,Rating from driver where S_No="+((int)(driverindex+1))+";";
                            ResultSet m=stmt.executeQuery(query);
                            m.next();
                            int driverlocation=m.getInt("Location");
                            rating=m.getDouble("Rating");
                            ShortestPath s = new ShortestPath(driverlocation);
                            s.dijkstra(driverlocation);
                            int time=ShortestPath.give_time(driverlocation,start);
                            System.out.println("Sleep time for going to location is"+time);
                            t.sleep(time);
                        }
                        catch (HeadlessException | SQLException e)
                        {
                            JOptionPane.showMessageDialog(null,"error");//catch statement  
                        }
                        System.out.print("Time of travel"+sleep_time);
                        t.sleep(sleep_time);
                        try{
                            if(olduser.equals(UserDetails.user))
                                {
                                Object[] possibilities = {"Good", "Average", "Bad"};
                                String s = (String)JOptionPane.showInputDialog(
                                null,
                                "You Have reached your destination:\n"
                                + "Please Rate the Driver",
                                "Customized Dialog",
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                possibilities,
                                "Good");
                                if(s.equals("Good")&&rating<10.0)
                                    rating+=0.2;
                                else if(s.equals("Average"))
                                    rating=rating;
                                else if(rating>0)
                                    rating=rating-0.2;
                                }
                            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                            Statement stmt=con.createStatement();
                            String query="Update driver set Availability=0, Rating="+rating+" where S_No="+((int)(driverindex+1))+";";//changes the database and sets the availability
                            //of the driver to 0 cause it has completed the trip
                            stmt.executeUpdate(query);
                            String userquery="Update user set BookingStatus=0 where userid='"+olduser+"';";
                            stmt.executeUpdate(userquery);
                            con.close();
                            stmt.close();//closes connection
                            
                        }
                        catch (HeadlessException | SQLException e)
                        {
                            JOptionPane.showMessageDialog(null,"error");//catch statement  
                        }
                        Redistribution r=new Redistribution(start,end,No_of_cities);
                        int finalpos=r.setnode();
                        int time = ShortestPath.give_time(end,finalpos);
                        MyThreadback t = new MyThreadback(time,driverindex,finalpos);
                    }
                    catch(InterruptedException ie)
                    {
                        JOptionPane.showMessageDialog(null,"Sleep Interrupted");
                    }
                }
            }