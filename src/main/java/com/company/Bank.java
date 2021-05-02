package com.company;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;



public class Bank extends  DatabaseConection {
    private String customerPassword;
    private double Balance;
    private double deposit;
    private double withdraw;
    private String accountnumber;
    private String Name;
    Date date = java.util.Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String transactionDate = formatter.format(date);


// checkUserName method for checking verifying user name information

    public  boolean CheckUserName(String user){
        ArrayList<String> CustomerName=super.getUsername(); // Accessing Parent class data.

        for(int i=0; i<CustomerName.size();i++){
            int indexuser=CustomerName.get(i).indexOf(user); //getting index of customer name
            // checking if customer exist return true, otherwise return false
            if(indexuser>=0){
              this.Name=CustomerName.get(i);
                return true;
            }
        }
        System.out.println("incorrect user name or user name does not exist");
        return false;
    }

    //CheckPassword method is for Customer password validation, whether it exist or not

    public  boolean CheckPassword(String pwd){

        ArrayList<String> passwrd=super.getCustomerPassword(); //Access parent class data to local array list
        ArrayList<String> Accounts=super.getAccountNumber();   //Access parent class data to local array list
        ArrayList<Double> Balancelist=super.getBalance();     //Access parent class data to local array list
        for(int i=0; i<passwrd.size();i++){
            int indexpassword=passwrd.get(i).indexOf(pwd);

          //Checking whether passord exist  or not
            if(indexpassword>=0){
                this.accountnumber=Accounts.get(i);     // Assigning values to instance variables for later use.
                this.Balance=Balancelist.get(i);
                this.customerPassword=passwrd.get(i);
                return true;
            }
        }
        System.out.println("you have entered incorrect password");
        return false;

    }

// depositAmount method for performing deposit operations in database

    public double depositAmount(double deposit){
        if(deposit>=20){
            this.Balance+=deposit;
            this.deposit=deposit;

            String upadateAmount="update customers set Balance="+this.Balance+" where Account_No="+this.accountnumber;


            try {
                super.insertHisory(this.transactionDate,this.deposit,this.withdraw,this.customerPassword); // calling Insert History method of DatabaseConnection Class
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            try {
                super.updateBalance(upadateAmount);  // Calling update method
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            System.out.println("$"+deposit+" sucessfully deposit");

        }else {
            System.out.println("Deposit failed");
            System.out.println("Minimum deposit amount limit is $20");
            System.out.println("please deposit more than $20");
        }
return deposit;
    }

    // WithdrawAmuont method is user for all withdraw operations

    public void WithdrawAmount(double withdraw){

        if(withdraw>=20){
            if(this.Balance>=withdraw){
                this.Balance-=withdraw;
                this.withdraw=withdraw;

                String insertamount="update customers set Balance="+this.Balance+" where Account_No="+this.accountnumber;

                try {
                    super.insertHisory(this.transactionDate,this.deposit,this.withdraw,this.customerPassword); // calling Insert History method of DatabaseConnection Class
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                try {
                    super.updateBalance(insertamount); //calling update method
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println("$"+withdraw+ " withdraw transcation done");

            }else{
                System.out.println("Your balance is less than you enter");
            }

        }else {
            System.out.println("Withdraw transaction failed");
            System.out.println("Minimum withdraw amount limit is $20");
            System.out.println("enter more than $20");
        }

    }
    // CheckBalance is providing Customer Banking Information
    public void checkBalance(){

        System.out.println("your current balance is "+this.Balance);
    }

    // transactionHistory Method to get transaction hisory of specific users.
    // control access from database based on user password
    public void transcationHistorys(String password){

        if(this.customerPassword.equals(password)){


            try {
                Fetchhistory(password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else{
            System.out.println("Incorrect Password");
        }

    }

}
