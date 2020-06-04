//Standard javafx imports.
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Imports for visual components.
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

//Imports for layout.
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class InterestCalculator extends Application {
	
	//Create components at class level scope.
	Label lblCapital, lblInterestRate, lblInvestTerm, lblError, lblCheckBox;
	
	//Capital, interest rate,investment term and text area fields.
	TextField txtfCapital, txtfInterestRate, txtfInvestTerm;
	TextArea txtInterestInfo;
	
	//The buttons.
	Button btnQuit, btnCalculate, btnDateSelect;
	
	//DatePicker
	DatePicker dpStartDate, dpEndDate;
	
	//ToggleGroup
	ToggleGroup tgType;
	
	//Dates for start and end.
	LocalDate startDate, endDate;
	
	//Check boxes
	CheckBox simpleCB, compoundCB;
	
	//Used to format the capital and interest to one decimal place.
	DecimalFormat df;
	
	public InterestCalculator() {
		
	}//constructor
	
	@Override
	public void init() {
		//Instantiate components with new.
		lblCapital = new Label("Capital:");
		lblInterestRate = new Label("Interest Rate(%):");
		lblInvestTerm = new Label("Investment Term (years):");
		lblError = new Label("");
		//lblRadio = new Label("Choose Calculation:");
		lblCheckBox = new Label("Select an option:");
		
		txtfCapital = new TextField();
		txtfInterestRate = new TextField();
		txtfInvestTerm = new TextField();
		
		txtInterestInfo = new TextArea();
		
		//Decimal format.
		 df = new DecimalFormat("##.##");
		 
		 
		//Action event for quitting the application. 
		btnQuit = new Button("Quit");
		btnQuit.setOnAction(ae -> Platform.exit());
		
		//Action event to calculate the interest
		btnCalculate = new Button("Calculate");
		btnCalculate.setOnAction(ae -> {
			//Create and show the calculate dialog.
			showCalculate();
		}); 
		
		btnDateSelect = new Button("...");
		btnDateSelect.setOnAction(ae -> {
			//Create and show the calendar dialog.
			showCalender();
		});
		
		
		//check box
		simpleCB = new CheckBox("Simple");
		compoundCB = new CheckBox("Compound");		
	}//init()

	public void showCalculate() {
		try {
			double investTermToInt = Integer.parseInt(txtfInvestTerm.getText());

			double capitalToInt = Integer.parseInt(txtfCapital.getText());

			//double	siAndCapitalCalc = calculateSIAndCapital(txtfCapital, txtfInterestRate, txtfInvestTerm);	

			double si = calculateSimpleInterest(txtfCapital, txtfInterestRate, txtfInvestTerm);
			
			double ci = calculateCompoundInterest(txtfCapital, txtfInterestRate, txtfInvestTerm);
			
			
			//When user clicks both check boxes.
			if(simpleCB.isSelected() && compoundCB.isSelected() ) {
				txtInterestInfo.setText("Simple Interest: \n" + "Year: " + investTermToInt 
						+ "    Initial Capital: "+df.format(capitalToInt)  + "    Simple Interest: " + si 
						+  "   Final Amount: " +  df.format((capitalToInt +si )) + "\n\n" 
						
						+ "Compound Interest: \n" + "Year: " + investTermToInt 
						+ "    Initial Capital: "+ df.format(capitalToInt)  + "    Compound Interest: " + df.format(ci) +  "   Final Amount: " 
						+ df.format((ci + capitalToInt )));
			} //if
			
			//When user clicks simple interest check box.
			else if (simpleCB.isSelected() ) {
				txtInterestInfo.setText("Simple Interest: \n" + "Year: " + investTermToInt 
						+ "    Initial Capital: "+capitalToInt  + "    Simple Interest: " + si 
						+  "   Final Amount: " + (capitalToInt +si ));
			}//if
			
			//When user clicks compound interest check box.
			else if(compoundCB.isSelected()) {
				txtInterestInfo.setText("Compound Interest: \n" + "Year: " + investTermToInt 
						+ "    Initial Capital: "+ df.format(capitalToInt)  + "    Compound Interest: " + df.format(ci) +  "   Final Amount: " 
						+ df.format((ci + capitalToInt )));

			}//else-if
			
			
			
			else;
			
		}//try
		
		catch(NumberFormatException e) {
			lblError.setText("Enter a value for each field");
		}//catch
	}

	public void showCalender() {
		Stage dialog = new Stage();
		dialog.setTitle("Select Investment Term");
		dialog.setResizable(false);
		dialog.setMaximized(false);
		dialog.setAlwaysOnTop(true);
		
		//Calendars.
		dpStartDate = new DatePicker();
		dpStartDate.setValue(LocalDate.now());
		dpEndDate = new DatePicker();
		dpEndDate.setValue(LocalDate.now());
		
		//Label for a message.
		Label lblStart = new Label("Investment Start Date:");
		Label lblEnd = new Label("Investment  End Date:");
		Label lblStatus = new Label("");
		
		//OK button.
		Button btnCancel = new Button("Cancel");
		Button btnOK = new Button("OK");
		
		//Set min width for the OK and the cancel buttons.
		btnCancel.setMinWidth(40);
		btnOK.setMinWidth(40);
		
		
		//Handle click on the OK button.
		btnOK.setOnAction(ae -> {
			//Check date order. Get the start and end dates. 
			LocalDate start = dpStartDate.getValue();
			LocalDate end = dpEndDate.getValue();
			
			if(end.isAfter(start)) {
				//User feedback.
				//lblStatus.setText("Dates OK.");
				
				startDate = start;
				endDate = end;
				
				txtfInvestTerm.setText(startDate.toString());
				txtfInvestTerm.setText(endDate.toString());
				
				//To get the difference in years from the start date to the end date.
				long p = ChronoUnit.YEARS.between( 
						startDate, endDate);
				
				
				txtfInvestTerm.setText( p + "");
				//double in = Integer.parseInt(txtfInvestTerm.getText());
				
				dialog.close();
			} //IF
			
			else {
					lblStatus.setText("Invalid input. Check dates.");
			}//else
			
		});//btnOK
		
		//Handle click on the cancel button.
		btnCancel.setOnAction(ae -> {
			dialog.close();
			
		});//btnCancel
		
		GridPane dgp = new GridPane();
		
		//Add labels to the gridPane.
		dgp.add(lblStart, 0, 0);
		dgp.add(dpStartDate, 1, 0);
		dgp.add(lblEnd, 0, 1);
		dgp.add(dpEndDate, 1, 1);
		dgp.add(lblStatus, 1, 2);
		//dgp.setGridLinesVisible(true);
		
		//Layout.
		dgp.setPadding(new Insets(10));
		dgp.setHgap(10);
		dgp.setVgap(10);
		
		//Create a vertical box layout component.
		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(10));
		buttonBox.setSpacing(8);
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);
		
		
		//Add the OK and the cancel buttons.
		buttonBox.getChildren().add(btnOK);
		buttonBox.getChildren().add(btnCancel);
		
		//Add the HBox (buttonBox) to the gridPane.
		dgp.add(buttonBox, 1, 4);
				
		//Create a scene and add the gridPane to it.
		Scene s = new Scene(dgp,350,180);
		
		//Add the scene to the stage.
		dialog.setScene(s);
		dialog.show();
		
	
	}//showDialog()


	@Override 
	public void start(Stage pStage) throws Exception {
		//Set the width and height.
		pStage.setWidth(550);
		pStage.setHeight(400);

		//Set the title for the stage.
		pStage.setTitle("Interest Calculator");
		
		//Create a layout.
		GridPane gp = new GridPane();
		
		//Add the calculate and the cancel buttons to hbButton.
		HBox hbButton = new HBox();
		hbButton.getChildren().add(btnQuit);
		hbButton.getChildren().add(btnCalculate);
		hbButton.setAlignment(Pos.BASELINE_RIGHT);
		hbButton.setSpacing(10);
		
		//txtfInvestTerm.setAlignment(Pos.BASELINE_RIGHT);
		
		//Add the InvestTerm text field and the date select buttons to hbDateSelect.
		HBox hbDateSelect = new HBox();
		hbDateSelect.getChildren().add(txtfInvestTerm);
		hbDateSelect.getChildren().add(btnDateSelect);
		hbDateSelect.setSpacing(20);
		
		//Add simple, compound and both radio check-boxes to hbChckBox.
		HBox hbChckBox = new HBox();
		hbChckBox.getChildren().addAll(simpleCB, compoundCB);
		hbChckBox.setSpacing(20);
		
		//Add the labels, text-field and the hboxes to the gridPane..
		gp.add(lblCapital, 7, 1);
		gp.add(txtfCapital, 8, 1);
		gp.add(lblInterestRate, 7, 2);
		gp.add(txtfInterestRate, 8, 2);
		gp.add(lblInvestTerm, 7, 3);
		gp.add(hbDateSelect, 8, 3);
//		gp.add(lblRadio, 7, 4);
//		gp.add(hbRadio, 8, 4);
		gp.add(lblCheckBox, 7, 4);
		gp.add(hbChckBox, 8, 4);
		gp.add(lblError, 8, 5);
		//gp.setGridLinesVisible(true);
		
		//Setting the padding .
		//gp.setPadding(new Insets(10));
				
		//Setting the vertical and horizontal gaps between the columns 
		gp.setVgap(10); 
		gp.setHgap(15);
		
		
				
		//Populate the layout with components.
		//Put components into a vbox.
		VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		//vb.getChildren().add(lblError);
		
		vb.setSpacing(10);
		vb.getChildren().addAll(gp, txtInterestInfo,hbButton);
		
		
		//Create a scene
		Scene s = new Scene(vb);
		
		//Set the scene.
		pStage.setScene(s);
		
		//Show the stage.
		pStage.show();
		
	}//start()
	
	@Override
	public void stop() {
		
	}//stop()
	
	public double calculateSimpleInterest(TextField c, TextField r, TextField t) {
		
		int capital = Integer.parseInt(c.getText());
		int rate = Integer.parseInt(r.getText());
		int term = Integer.parseInt(t.getText());
		
		double simInt = (capital * rate * term) / 100.0 ;
		
		return simInt;
		
		
		
	}
	
	public double calculateCompoundInterest(TextField c, TextField r, TextField term1) {
		
		double t = 1;
		double amount, ci;
		
		double capital = Integer.parseInt(c.getText());
		double rate = Integer.parseInt(r.getText());
		double term = Integer.parseInt(term1.getText());
		
		rate = (1 + rate/100);
		
		for (int i = 0; i < term; i++) {
			t *= rate;
		}
		
		amount = capital * t;
		
		
		ci = (amount - capital);
		
		return ci;
	}
	public static void main(String[] args) {
		//Launch the application
		launch();
		
	}//main()

}//class
