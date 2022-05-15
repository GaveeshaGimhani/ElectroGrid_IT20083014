package model; 
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.Statement; 
 
public class PrivateSector { 
 // A common method to connect to the DB 
 private Connection connect() { 
  Connection con = null; 
 
  try { 
   Class.forName("com.mysql.jdbc.Driver"); 
   // Provide the correct details: DBServer/DBName, username, password 
   con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogridclient", "root", ""); 
 
   // For testing 
   System.out.print("Successfully connected"); 
 
  } catch (Exception e) { 
   e.printStackTrace(); 
  } 
 
  return con; 
 } 
 
 public String readPrivateSector() { 
  String output = ""; 
 
  try { 
   Connection con = connect(); 
   if (con == null) { 
    return "Error while connecting to the database for reading."; 
   } 
 
   // Prepare the html table to be displayed 
   output = "<table border='1'><tr><th>Customer Name</th>" + "<th>NIC</th><th>Customer Email</th>" 
     + "<th>No of units</th>"  + "<th>Charge per unit</th>" + "<th>Update</th><th>Remove</th></tr>"; 
 
   String query = "select * from privateSector"; 
   Statement stmt = con.createStatement(); 
   ResultSet rs = stmt.executeQuery(query); 
 
   // iterate through the rows in the result set 
   while (rs.next()) { 
 
    String customerID = Integer.toString(rs.getInt("customerID")); 
    String customerName = rs.getString("customerName");
    String NIC = rs.getString("NIC");
    String customerEmail = rs.getString("customerEmail"); 
    String noOfUnits = rs.getString("noOfUnits"); 
    String chargePerUnit = rs.getString("chargePerUnit");
 
    // Add into the html table 
 
    output += "<tr><td><input id='hidcustomerIDUpdate' name='hidcustomerIDUpdate' type='hidden' value='" 
      + customerID + "'>" + customerName + "</td>"; 
 
    output += "<td>" + NIC + "</td>"; 
    output += "<td>" + customerEmail + "</td>"; 
    output += "<td>" + noOfUnits + "</td>"; 
    output += "<td>" + chargePerUnit + "</td>";
    // buttons 
    output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" 
      + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customerID='" 
      + customerID + "'>" + "</td></tr>"; 
 
   } 
 
   con.close(); 
 
   // Complete the html table 
   output += "</table>"; 
  } catch (Exception e) { 
   output = "Error while reading the Private Sector Details."; 
   System.err.println(e.getMessage()); 
  } 
 
  return output; 
 } 
 
 // Insert Private Sector
 public String insertPrivateSector(String customerName,String NIC, String customerEmail, String noOfUnits, 
   String chargePerUnit) { 
  String output = ""; 
 
  try { 
   Connection con = connect(); 
 
   if (con == null) { 
    return "Error while connecting to the database"; 
   } 
 
   // create a prepared statement 
   String query = " insert into privateSector (`customerID`,`customerName`,`NIC`,`customerEmail`,`noOfUnits`,`chargePerUnit`)" 
     + " values (?, ?, ?, ?, ?,?)"; 
 
   PreparedStatement preparedStmt = con.prepareStatement(query); 
 
   // binding values 
   preparedStmt.setInt(1, 0); 
   preparedStmt.setString(2, customerName); 
   preparedStmt.setString(3, NIC); 
   preparedStmt.setString(4, customerEmail); 
   preparedStmt.setString(5, noOfUnits); 
   preparedStmt.setString(6, chargePerUnit);
 
   // execute the statement 
   preparedStmt.execute(); 
   con.close(); 
 
   // Create JSON Object to show successful msg. 
   String newPrivateSector = readPrivateSector(); 
   output = "{\"status\":\"success\", \"data\": \"" + newPrivateSector + "\"}"; 
  } catch (Exception e) { 
   // Create JSON Object to show Error msg. 
   output = "{\"status\":\"error\", \"data\": \"Error while Inserting Private Sector.\"}"; 
   System.err.println(e.getMessage()); 
  } 
 
  return output; 
 } 
 
 // Update Private Sector
 public String updatePrivateSector(String customerID, String customerName,String NIC, String customerEmail, String noOfUnits, 
   String chargePerUnit) { 
  String output = ""; 
 
  try { 
   Connection con = connect(); 
 
   if (con == null) { 
    return "Error while connecting to the database for updating.";


   } 
 
   // create a prepared statement 
   String query = "UPDATE privateSector SET customerName=?,NIC=?,customerEmail=?,noOfUnits=?,chargePerUnit=? WHERE customerID=?"; 
 
   PreparedStatement preparedStmt = con.prepareStatement(query); 
 
   // binding values 
   preparedStmt.setString(1, customerName); 
   preparedStmt.setString(2, NIC); 
   preparedStmt.setString(3, customerEmail); 
   preparedStmt.setString(4, noOfUnits); 
   preparedStmt.setString(5, chargePerUnit); 
   preparedStmt.setInt(6, Integer.parseInt(customerID)); 
 
   // execute the statement 
   preparedStmt.execute(); 
   con.close(); 
 
   // create JSON object to show successful msg 
   String newPrivateSector = readPrivateSector(); 
   output = "{\"status\":\"success\", \"data\": \"" + newPrivateSector + "\"}"; 
  } catch (Exception e) { 
   output = "{\"status\":\"error\", \"data\": \"Error while Updating Private Sector Details.\"}"; 
   System.err.println(e.getMessage()); 
  } 
 
  return output; 
 } 
 
 public String deletePrivateSector(String customerID) { 
  String output = ""; 
 
  try { 
   Connection con = connect(); 
 
   if (con == null) { 
    return "Error while connecting to the database for deleting."; 
   } 
 
   // create a prepared statement 
   String query = "DELETE FROM privateSector WHERE customerID=?"; 
 
   PreparedStatement preparedStmt = con.prepareStatement(query); 
 
   // binding values 
   preparedStmt.setInt(1, Integer.parseInt(customerID)); 
   // execute the statement 
   preparedStmt.execute(); 
   con.close(); 
 
   // create JSON Object 
   String newPrivateSector = readPrivateSector(); 
   output = "{\"status\":\"success\", \"data\": \"" + newPrivateSector + "\"}"; 
  } catch (Exception e) { 
   // Create JSON object 
   output = "{\"status\":\"error\", \"data\": \"Error while Deleting Private Sector.\"}"; 
   System.err.println(e.getMessage()); 
 
  } 
 
  return output; 
 }

public String insertPrivateSector(String parameter, String parameter2, String parameter3, String parameter4) {
	// TODO Auto-generated method stub
	return null;
}

public String updatePrivateSector(String string, String replace, String replace2, String replace3, String string2) {
	// TODO Auto-generated method stub
	return null;
} 
 
}
