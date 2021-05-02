package com.company;



import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
   public static Scanner scanner = new Scanner(System.in);
    public static Bank banktransaction = new Bank();
   public static void main(String[] args) {


        int choice=0;

       System.out.println("enter user name");
       String user=scanner.nextLine();
       System.out.println("enter password");
       String password=scanner.nextLine();
       boolean quit1=banktransaction.CheckUserName(user);
       boolean quit2=banktransaction.CheckPassword(password);

      if(quit1 && quit2){

        printmenu();
      }

       while(quit1 && quit2){
         try {

              choice=scanner.nextInt();
         }catch (InputMismatchException e){
             System.out.println("Invalid Selection" );

         }
         scanner.nextLine();


            switch (choice) {
                case 1:
                    despositamount();
                    System.out.println("press 6 to go back to menu");
                    break;
                case 2:
                    withdrawamount();
                    System.out.println("press 6 to go back to menu");
                    break;
                case 3:
                    balancecheck();
                    System.out.println("press 6 to go back to menu");
                    break;
                case 4:
                    lasttransaction();
                    System.out.println("press 6 to go back to menu");
                    break;
                case 5:
                    quit1=false;
                    quit2=false;
                    try {
                        banktransaction.CloseConnection();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case 6:
                    printmenu();
                    break;
                default:
                    System.out.println("please enter number from menu selection and to go to menu press 6");
                    break;
            }
        }


    }


public static void despositamount(){
    System.out.println("enter amount that you want to deposit");
    double despoistAmount=scanner.nextDouble();



        banktransaction.depositAmount(despoistAmount);


}
public static void withdrawamount(){
    System.out.println("enter amount that you want to withdraw");
       double withdrawAmount=scanner.nextDouble();

           banktransaction.WithdrawAmount(withdrawAmount);


}
public static void balancecheck(){
       banktransaction.checkBalance();
}
public static void lasttransaction(){
    System.out.println("enter Password again please");
    String password=scanner.nextLine();
       banktransaction.transcationHistorys(password);
}
public static void printmenu(){
    System.out.println("enter 1 for deposit\n"+
                       "enter 2 for withdraw\n"+
                       "enter 3 to checkbalance\n"+
                       "enter 4 to check last transcation\n"+
                       "enter 5 if you done banking\n");
}
}