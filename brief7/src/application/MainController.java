package application;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class MainController implements Initializable{
	@FXML
	private Label id;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	@FXML
	private Label email;
	@FXML
	private Label adress;
	@FXML
	private Label city;
	@FXML
	private Label country;
	@FXML
	private TextField conId;
	@FXML
	private TextField conFirstName;
	@FXML
	private TextField conLastName;
	@FXML
	private TextField ConEmail;
	@FXML
	private TextField conAdress;
	@FXML
	private TextField conCity;
	@FXML
	private TextField conCountry;
	@FXML
	private TableView<Candidats> tabCondidats;
	@FXML
	private TableColumn<Candidats, Integer> clmnId;
	@FXML
	private TableColumn<Candidats, String> clmnFirstName;
	@FXML
	private TableColumn<Candidats, String> clmLastName;
	@FXML
	private TableColumn<Candidats, String> clmnEmail;
	@FXML
	private TableColumn<Candidats, String> ClmnAdress;
	@FXML
	private TableColumn<Candidats, String> ClmnCity;
	@FXML
	private TableColumn<Candidats, String> clmnCountry;
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDelete;
	@FXML
	private Label welcome;
	
	Connection conn = Connect.getconnect();
    Statement st;

	@FXML
    private void handleButtonAction(ActionEvent event) {        
        
        if(event.getSource() == btnCreate)
            Create();
        
	   else if (event.getSource() == btnUpdate)
            Update();
        
        else if(event.getSource() == btnDelete)
            Delete();
        
    }

	@Override
	 public void initialize(URL arg0, ResourceBundle arg1) {
		showCandidats();
   }
	
	public class Connect {
		private static String dbURL = "jdbc:postgresql://localhost:5432/brief7";
		private static String username = "postgres";
		private static String password = "postgres";
		 
		public static Connection getconnect() {
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(dbURL, username, password);
	            System.out.println("Connected successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return conn;
	    }
	}
	public ObservableList<Candidats> CandidatsList(){
		 ObservableList<Candidats> CList = FXCollections.observableArrayList();
	        String query = "SELECT * FROM candidats";
	        ResultSet rs;
	        
	        try{
				st = conn.createStatement();
	            rs = st.executeQuery(query);
	            Candidats C;
	            while(rs.next()){
	                C = new Candidats(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),rs.getString("address"),rs.getString("city"),rs.getString("country"));
	                CList.add(C);
	            }
	                
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return CList;
	 }
	
	public void showCandidats(){
    	ObservableList<Candidats> L = CandidatsList();
        
        clmnId.setCellValueFactory(new PropertyValueFactory<Candidats, Integer>("id"));
        clmnFirstName.setCellValueFactory(new PropertyValueFactory<Candidats, String>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<Candidats, String>("lastName"));
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Candidats, String>("mail"));
        ClmnAdress.setCellValueFactory(new PropertyValueFactory<Candidats, String>("adress"));
        ClmnCity.setCellValueFactory(new PropertyValueFactory<Candidats, String>("city"));
        clmnCountry.setCellValueFactory(new PropertyValueFactory<Candidats, String>("country"));
        
        tabCondidats.setItems(L);
    }
	
	public void Create(){
        String query = "INSERT INTO candidats VALUES (" +conId.getText() + ",'" + conFirstName.getText() + "','" + conLastName.getText() + "','" + ConEmail.getText() + "','" + conAdress.getText() + "','" + conCity.getText() + "','" + conCountry.getText() + "')";
       
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			System.out.println("Creating is Done.");
		}catch(Exception ex){
            ex.printStackTrace();
			System.out.println("Creating Did not go will.");
        }
		
        showCandidats();
    }
	
	public void Update(){
        String query = "UPDATE  candidats SET firstname = '" + conFirstName.getText() + "', lastname = '" + conLastName.getText() + "', email = '" + ConEmail.getText() + "',  address = '" + conAdress.getText() + "' , city = '" + conCity.getText() + "' , country = '" + conCountry.getText() + "' WHERE id =  "+ conId.getText() ;
        
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			System.out.println("Updating is Done.");
		}catch(Exception e){
            e.printStackTrace();
			System.out.println("Updating Did not go will.");
        }
		
        showCandidats();
    }
	
	public void Delete(){
        String query = "DELETE FROM candidats WHERE id =" + conId.getText() ;
        
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			  System.out.println("Deleting is Done.");
		}catch(Exception ex){
            ex.printStackTrace();
        }
		
        showCandidats();
    }
	
	@FXML
    void handleMouseAction(MouseEvent event) {
		Candidats candidat = tabCondidats.getSelectionModel().getSelectedItem();
		conId.setText(""+candidat.getId());
		conFirstName.setText(candidat.getFirstName());
		conLastName.setText(candidat.getLastName());
		ConEmail.setText(candidat.getMail());
		conAdress.setText(candidat.getAdress());
		conCity.setText(candidat.getCity());
		conCountry.setText(candidat.getCountry());
    }
}
