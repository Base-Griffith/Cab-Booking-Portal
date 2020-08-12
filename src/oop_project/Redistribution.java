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
import static oop_project.Driver.loc;

/**
 *
 * @author LENOVO
 */
            class Redistribution extends Driver
            { 
                public Redistribution(int start, int end,int No_of_cities) 
                {
                    super(start,end,No_of_cities);
                }
                int setnode()
                {
                    
                    int min,minl=0;

                    int max=1;
                    int[] loc_noof_driv = new int[No_of_cities];//no of cities
                    
                    for(int j=0;j<No_of_cities;j++)///////--here 9 is no.of drivers.
                    {
                      if(loc[j]!=-1)
                        loc_noof_driv[loc[j]]++;//look into check
                    }
                    for(int j=0;j<arraylen;j++)
                        System.out.print("loc"+loc[j]);
                    for(int j=0;j<No_of_cities;j++)
                    {
                        System.out.println("randa"+loc_noof_driv[j]);
                    }
                    if(loc_noof_driv[end]<max)/////////////////////max being predefined.
                    {
                        loc[driver_index] = end;
                        System.out.println("it went ot if");
                        minl=end;
                        try
                        {
                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project","root","tiger");
                        Statement stmt=con.createStatement();
                        String query="Update driver set Location="+end+" where S_No="+((int)(driver_index+1))+";";
                        System.out.print(query);
                        stmt.executeUpdate(query);
                        stmt.close();
                        con.close();
                        }
                        catch (HeadlessException | SQLException e)
                        {JOptionPane.showMessageDialog(null,"error");}
                        loc_noof_driv[end]+=1;
                    }
                    else
                    {
                            ShortestPath t = new ShortestPath(end);
                            t.dijkstra(end);
                            int[][] min_dist = ShortestPath.mat;
                            int  i=0;
                            min=100000;
                            for(i=0;i<No_of_cities;i++)//no of locations 
                            {
                                if(min_dist[end][i]<min && loc_noof_driv[i]<max)
                                {
                                    
                                    min=min_dist[end][i];
                                    System.out.println("DIst"+min+end+i);
                                    minl=i;
                                }
                            }
                            loc[driver_index] = minl;//----here we now need to put him to sleep till he reaches minl.
                            
                    }
                    return minl;
                }
            }
