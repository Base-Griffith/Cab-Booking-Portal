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
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
class Driver
            {
                
                
                static int loc[]=new int[10000];
                int flag[]=new int[10000];
                int[][] min_dist = ShortestPath.mat;
                double rating[]=new double[10000];
                double driver_rating;
                int[] drivers = new int[10000];
                int start=0;
                int end=0;
                int min= 100000;
                static int driver_index=-1;
                static int arraylen=0;
                int No_of_cities;
                Driver(int start,int end,int No_of_cities)
                {
                    
                    this.start = start;
                    this.end = end;
                    this.No_of_cities=No_of_cities;
                    //System.out.print("Start="+this.start+"end"+this.end);
                }
                void set_Val()
                {
                        try
                    {
                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                        Statement stmt=con.createStatement();
                        String query="Select Availability,Location,Rating from driver;";
                        ResultSet m=stmt.executeQuery(query);
                        int i=0;
                        Arrays.fill(loc,-1);
                        while(m.next())
                        {
                            loc[i]=m.getInt("Location");
                            flag[i]=m.getInt("Availability");
                            System.out.println("LOC"+loc[i]);
                            rating[i]=m.getDouble("Rating");
                            i++;
                        }
                        arraylen=i;
                        m.close();
                        stmt.close();
                        con.close();
                    }
                    catch (Exception e)
                    {JOptionPane.showMessageDialog(null,"error");}
                    find_driver(flag);
                }
                void find_driver(int flag[])
                {
                   System.out.println(arraylen);
                    for(int i=0;i<arraylen;i++)
                    {
                        System.out.println(flag[i]);
                        if(min_dist[start][loc[i]]<min && flag[i]==0)
                        {
                        //System.out.print("it did enter");
                        min=min_dist[start][loc[i]];
                        System.out.println("MIn"+min+start+loc[i]);
                        driver_index=i;
                        }
                    }
                    for(int i=0;i<arraylen;i++)
                    {
                        if(min_dist[start][loc[i]]==min && flag[i]==0)
                        {
                          drivers[i]=1;
                        }
                    }
                    //System.out.print(min);
                   double max=0.0;
                   for(int i=0;i<arraylen;i++)
                   {
                        if(drivers[i]==1 && rating[i]>max)
                        {
                            
                            max = rating[i];
                            driver_index=i;
                            driver_rating = max;
                        }
                   }

                }
                void update_driver_status()
                {
                    String Driver_Name="";
                    try{
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                    Statement stmt=con.createStatement();
                    String query="Update driver set Availability=1 where S_No="+((int)(driver_index+1))+";";
                    String userquery="Update user set BookingStatus=1 where userid='"+UserDetails.user+"';";
                    String query2="Select Name,Rating,Car_No,Car_Model,PhoneNo from driver where S_No="+((int)(driver_index+1))+";";
                    System.out.println(query2);
                    stmt.executeUpdate(query);
                    stmt.executeUpdate(userquery);
                    ResultSet m=stmt.executeQuery(query2);
                    m.next();
                    String PhoneNo;
                    String dricarmod,dricarno;
                    Driver_Name=m.getString("Name");
                    driver_rating=m.getDouble("Rating");
                    dricarno=m.getString("Car_Model");
                    dricarmod=m.getString("Car_No");
                    PhoneNo=m.getString("PhoneNo");
                    con.close();
                    stmt.close();
                    m.close();
                    UserDetails o=new UserDetails(Driver_Name,dricarno,dricarmod,driver_rating,PhoneNo);
                    //JOptionPane.showMessageDialog(null,"driver "+Driver_Name+"(Rated:- "+driver_rating+")"+" called for the ride");
                    flag[driver_index]=1;// in SQL DATABASE, CREATE THIS FIELD ALSO, TO LET US KNOW THAT THE DRIVER IS OCCUPIED!!
                    int time = ShortestPath.give_time(start,end);
                    //System.out.print("Time is:"+time+start+end);
                    TravelThread t = new TravelThread(time,driver_index,start,end,No_of_cities,UserDetails.user);
                    flag[driver_index]=0;
                    }
                    catch (HeadlessException | SQLException e)
                    {JOptionPane.showMessageDialog(null,"Request Timed Out");}
                }
            }
