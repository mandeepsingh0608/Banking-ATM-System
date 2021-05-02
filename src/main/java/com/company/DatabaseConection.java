package com.company;


import java.sql.*;
import java.util.ArrayList;


public class DatabaseConection {
    private ArrayList<String> Username=new ArrayList<>();
    private ArrayList<String> CustomerPassword=new ArrayList<>();
    private ArrayList<String> AccountNumber=new ArrayList<>();
    private ArrayList<Double> Balance=new ArrayList<>();
    Connection connection;
    Statement statement;
    ResultSet rset;

    public DatabaseConection() {   // geenerate auto connection to database when applications run

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Banking";
            String user="ms";
            String password="mandeep";
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("connected");
           statement=connection.createStatement();
            String strSelect = "select Account_No, Name, Password,Balance from customers";
             rset = statement.executeQuery(strSelect);

            while(rset.next()) {   // Accessing data to arraylists from database

                String account=rset.getString("Account_No");
                String paswrd = rset.getString("Password");
                String name = rset.getString("name");
                double balance=rset.getDouble("Balance");
                Username.add(name);
                CustomerPassword.add(paswrd);
                AccountNumber.add(account);
                Balance.add(balance);

            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("not connected");
        }

    }
  //Updating customer Balance in Database
  public void updateBalance(String amount) throws SQLException {

        statement.executeUpdate(amount);

  }


 //Inserting transactions history of customers into database
  public void insertHisory(String  date, double deposit, double withdraw, String Customerpassword) throws SQLException {
     String insertquery = " insert into Transactions (Date,Deposit,Withdraw,CustomerPassword)"
             + " values (?, ?, ?, ?)";
     PreparedStatement preparedStmt = connection.prepareStatement(insertquery);
     preparedStmt.setString (1, date);
     preparedStmt.setDouble (2, deposit);
     preparedStmt.setDouble   (3, withdraw);
     preparedStmt.setString(4, Customerpassword);
     // execute the preparedstatement
     preparedStmt.execute();
 }

 //feching transactions history of customers from database

 public void Fetchhistory(String CustomerPassword) throws SQLException {
     String Selecthistroy="select * from Transactions where CustomerPassword like ?";
     PreparedStatement preparedStmt = connection.prepareStatement(Selecthistroy);
     preparedStmt.setString(1,CustomerPassword);
     rset = preparedStmt.executeQuery();
     System.out.println(String.format("%30s %25s %10s %25s %10s", "Date", "|", "Deposit($)", "|", "Withdraw"));
     System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
     while (rset.next()){
         String Date =rset.getString("Date");
         String Depositamount= String.valueOf(rset.getDouble("Deposit"));
         String withdrawamout= String.valueOf(rset.getDouble("Withdraw"));
         System.out.println(String.format("%30s %25s %10s %25s %10s", Date, "|", Depositamount, "|", withdrawamout));

     }

 }

    public ArrayList<String> getAccountNumber() {
        return AccountNumber;
    }

    public ArrayList<String> getUsername() {
        return Username;
    }

    public ArrayList<String> getCustomerPassword() {
        return CustomerPassword;
    }

    public ArrayList<Double> getBalance() {
        return Balance;
    }
    //Closing Connection of database once user done banking operation
    public void CloseConnection() throws SQLException {
        connection.close();
    }
}
