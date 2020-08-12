/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_project;

/**
 *
 * @author LENOVO
 */
class UserDetails
            {
                static String user;
                static String drivername,carno,carmodel;
                static int amount;
                static double rating;
                static String PhoneNo;
                UserDetails(String name)
                {
                    user=name;
                    
                }
                UserDetails(String drivername,String carno,String carmodel,double rating,String PhoneNo)
                {
                    this.drivername=drivername;
                    this.carno=carno;
                    this.carmodel=carmodel;
                    this.rating=rating;
                    this.PhoneNo=PhoneNo;
                }
            
            
            }
