// this project implement an Electricity data structure using combined sorted
// linked lists.
// The data input for this project will be the electricity data obtained from
// OCHA in a csv file (Electricity.csv)
// in this file contains a daily electricity record (Date, Israeli_Lines_MWs,
// Gaza_Power_Plant_MWs,Egyptian_Lines_MWs, Total_daily_Supply_available_in_MWs,
//  Overall_demand_in_MWs,Power_Cuts_hours_day_400mg, and Temp) separated by comma (,).
// the project have graphical user interface (GUI) using javaFX.
package com.example.datastructureproject3;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.channels.NotYetBoundException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Float.parseFloat;


public class HelloApplication extends Application {

    private File selectedFile;
    private  AVL yearTree;
    private FileChooser fileChooser;
    private String path;
    private static final   String  firstLine ="Date,Israeli_Lines_MWs,Gaza_Power_Plant_MWs,Egyptian_Lines_MWs,Total_daily_Supply_available_in_MWs,Overall_demand_in_MWs,Power_Cuts_hours_day_400mg,Temp" ;
    @Override
    public void start(Stage stage) throws IOException {

        // initialization of year tree
        yearTree=new AVL();

        // Create MenuBar
        MenuBar menuBar = new MenuBar();

        // Create "Management" menu
        Menu managementMenu = new Menu("Management");

        // Create menu items for "Insert," "Update," "Delete," and "Search"
        MenuItem insertItem = new MenuItem("Insert");
        MenuItem updateItem = new MenuItem("Update");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem searchItem = new MenuItem("Search");
        MenuItem printByLevel = new MenuItem("Print by level");
        MenuItem printHeight = new MenuItem("Print height");

        // Add menu items to the "Management" menu
        managementMenu.getItems().addAll(insertItem, updateItem, deleteItem, searchItem,printByLevel,printHeight);

        // Add "Management" menu to the MenuBar
        menuBar.getMenus().add(managementMenu);

        // Create "Statistics" menu
        Menu statisticsMenu = new Menu("Statistics");

        // Create menu items for "statistic for a given day," "statistic for a given month,"
        // " statistic for a given year" and "total statistic"
        MenuItem dayItem = new MenuItem("For a day");
        MenuItem monthItem = new MenuItem("For a month");
        MenuItem yearItem = new MenuItem("For a year");
        MenuItem totalItem = new MenuItem("Total statistic");

        // Add menu items to the "Statistics" menu
        statisticsMenu.getItems().addAll(dayItem, monthItem,yearItem , totalItem);

        // Add "Statistics" menu to the MenuBar
        menuBar.getMenus().add(statisticsMenu);

        // Create "File" menu
        Menu fileMenu = new Menu("File" );

        // Create menu items for "Open"
        MenuItem openItem = new MenuItem("Open");


        // Add menu items to the "File" menu
        fileMenu.getItems().add(openItem);

        // Add "File" menu to the MenuBar
        menuBar.getMenus().add(fileMenu);



        // Create "Save" menu
        Menu saveMenu = new Menu("Save" );

        // Create menu items for " "Save," "Save new File"
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveNewFileItem = new MenuItem("Save new file");

        // Add menu items to the "Save" menu
        saveMenu.getItems().addAll(saveItem, saveNewFileItem);

        // Add "File" menu to the MenuBar
        menuBar.getMenus().add(saveMenu);


        // initialization of file chooser
        fileChooser =new FileChooser();

        // program the open button
        openItem.setOnAction(e->{

                    // call the file chooser and store the path of the file
                     selectedFile =fileChooser.showOpenDialog(new Stage());
                    path=selectedFile.getPath();

                    try { // read the lines in the records
                        Scanner read_File = new Scanner(selectedFile);

                        // skip first line
                        read_File.nextLine();
                        while (read_File.hasNextLine()) {
                            // add the line from record to list
                            String data = read_File.nextLine();

                            // split the data
                            String[] values = data.split(",");

                            // store each filed
                            String date = values[0];
                            float israeliLinesMWs = parseFloat(values[1]);
                            float gazaPowerPlantMWs = parseFloat(values[2]);
                            float egyptianLinesMWs = parseFloat(values[3]);
                            float totalDailySupplyAvailableInMWs = parseFloat(values[4]);
                            float overallDemandInMWs = parseFloat(values[5]);
                            float powerCutsHoursDay400mg = parseFloat(values[6]);
                            float temp =parseFloat(values[7]);

                            // create new object of type electricity record to hold the record
                            ElectricityRecord electricityRecord = new ElectricityRecord(date,
                                    israeliLinesMWs,gazaPowerPlantMWs,egyptianLinesMWs,
                                    totalDailySupplyAvailableInMWs,overallDemandInMWs,
                                    powerCutsHoursDay400mg,temp);

                            // split the date in year ,month and day
                            int year, month, day;

                            String[] dateParts = date.split("-");

                            year = Integer.parseInt(dateParts[0]);
                            month = Integer.parseInt(dateParts[1]);
                            day = Integer.parseInt(dateParts[2]);

                            // add the record in the list
                           yearTree.add(day,month,year,electricityRecord);

                        }

                    } catch (Exception i) {
                        System.out.println("open another file ! ");
                    }

                }

        );

        // program the saveItem
        saveItem.setOnAction(e->{



            // create string builder
            StringBuilder s =new StringBuilder();

            //  the first line of the file
            s.append(firstLine+"\n");

            s.append(yearTree.printAllYear());

            // buffer writer to write the record in the file
            try (BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter(selectedFile))) {

                bufferedWriter.write(s.toString()); // write the record

            }catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        // program the save new file item
        saveNewFileItem.setOnAction(e->{



            // create string builder
            StringBuilder s =new StringBuilder();

            //  the first line of the file
            s.append(firstLine+"\n");

            s.append(yearTree.printAllYear());

            File selFile = fileChooser.showOpenDialog(new Stage());

            // buffer writer to write the record in the file
            try (BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter(selFile ))) {

                bufferedWriter.write(s.toString()); // write the record

            }catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        // program the insert item
        insertItem.setOnAction(e -> {
            Stage addStage = new Stage();
            addStage.setTitle("Add new record");

            GridPane addGridPane = new GridPane();
            addGridPane.setPadding(new Insets(30));
            addGridPane.setHgap(10);
            addGridPane.setVgap(10);

            // Create labels and text fields for record information
            Label Israeli_Lines_MWsLabel = new Label("Israeli_Lines_MWs:");
            TextField Israeli_Lines_MWsField = new TextField();
            Label Gaza_Power_Plant_MWsLabel = new Label("Gaza_Power_Plant_MWs:");
            TextField Gaza_Power_Plant_MWsField = new TextField();
            Label Egyptian_Lines_MWsLabel = new Label("Egyptian_Lines_MWs:");
            TextField Egyptian_Lines_MWsField = new TextField();
            Label Total_daily_Supply_available_in_MWsLabel = new Label("Total_daily_Supply_available_in_MWs:");
            TextField Total_daily_Supply_available_in_MWsField = new TextField();
            Label Overall_demand_in_MWsLabel = new Label("overall_demand_in_MWs:");
            TextField Overall_demand_in_MWsField = new TextField();
            Label Power_Cuts_hours_day_400mgLabel = new Label("Power_Cuts_hours_day_400mg:");
            TextField Power_Cuts_hours_day_400mgField = new TextField();
            Label TempLabel = new Label("Temp:");
            TextField TempField = new TextField();

            // create a datePicker
            DatePicker  datePicker =  new DatePicker();

            // Add labels and text fields to the GridPane
            addGridPane.add(Israeli_Lines_MWsLabel, 0, 0);
            addGridPane.add(Israeli_Lines_MWsField, 1, 0);
            addGridPane.add(Gaza_Power_Plant_MWsLabel, 0, 1);
            addGridPane.add(Gaza_Power_Plant_MWsField, 1, 1);
            addGridPane.add(Egyptian_Lines_MWsLabel, 0, 2);
            addGridPane.add(Egyptian_Lines_MWsField, 1, 2);
            addGridPane.add(Total_daily_Supply_available_in_MWsLabel, 0, 3);
            addGridPane.add(Total_daily_Supply_available_in_MWsField, 1, 3);
            addGridPane.add(Overall_demand_in_MWsLabel, 0, 4);
            addGridPane.add(Overall_demand_in_MWsField, 1, 4);
            addGridPane.add(Power_Cuts_hours_day_400mgLabel, 0, 5);
            addGridPane.add(Power_Cuts_hours_day_400mgField, 1, 5);
            addGridPane.add(TempLabel, 0, 6);
            addGridPane.add(TempField, 1, 6);
            addGridPane.add(new Label("Date :"), 0, 7);
            addGridPane.add(datePicker, 1, 7);

            // create add messageLabel
            Label messageLabel=new Label("");
            addGridPane.add(messageLabel, 0, 9);


            // create save button and add it in grid pane
            Button saveButton = new Button("Save");
            addGridPane.add(saveButton, 1, 9);



                            // Handle save button click event
                            saveButton.setOnAction(saveEvent -> {
                                try {
                                    // store each filed
                                    LocalDate selectedDate = datePicker.getValue();
                                    String date = selectedDate.toString();
                                    float israeliLinesMWs = Float.valueOf(Israeli_Lines_MWsField.getText());
                                    float gazaPowerPlantMWs = Float.valueOf(Gaza_Power_Plant_MWsField.getText());
                                    float egyptianLinesMWs = Float.valueOf(Egyptian_Lines_MWsField.getText());
                                    float totalDailySupplyAvailableInMWs = Float.valueOf(Total_daily_Supply_available_in_MWsField.getText());
                                    float overallDemandInMWs = Float.valueOf(Overall_demand_in_MWsField.getText());
                                    float powerCutsHoursDay400mg = Float.valueOf(Power_Cuts_hours_day_400mgField.getText());
                                    float temp = Float.valueOf(TempField.getText());

                                    // create new object of type electricity record to hold the record
                                    ElectricityRecord electricityRecord = new ElectricityRecord(date,
                                            israeliLinesMWs, gazaPowerPlantMWs, egyptianLinesMWs,
                                            totalDailySupplyAvailableInMWs, overallDemandInMWs,
                                            powerCutsHoursDay400mg, temp);

                                    // split the date in year ,month and day
                                    int year, month, day;

                                    String[] dateParts = date.split("-");

                                    year = Integer.parseInt(dateParts[0]);
                                    month = Integer.parseInt(dateParts[1]);
                                    day = Integer.parseInt(dateParts[2]);

                                   if( yearTree.add(day,month,year,electricityRecord) )
                                    messageLabel.setText("  added successfully.");
                                   else
                                       messageLabel.setText("  add failed!");

                                } catch (Exception ex){
                                    messageLabel.setText("  add failed!");
                                }

                            });

            Scene addScene = new Scene(addGridPane);
            addStage.setScene(addScene);
            addStage.show();
        });

        // program the update menuItem
        updateItem.setOnAction(e -> {
            Stage updateStage = new Stage();
            updateStage.setTitle("update record");

            GridPane updateGridPane = new GridPane();
            updateGridPane.setPadding(new Insets(30));
            updateGridPane.setHgap(10);
            updateGridPane.setVgap(10);

            // Create labels and text fields for record information
            Label Israeli_Lines_MWsLabel = new Label("New Israeli_Lines_MWs:");
            TextField Israeli_Lines_MWsField = new TextField();
            Label Gaza_Power_Plant_MWsLabel = new Label("New Gaza_Power_Plant_MWs:");
            TextField Gaza_Power_Plant_MWsField = new TextField();
            Label Egyptian_Lines_MWsLabel = new Label("New Egyptian_Lines_MWs:");
            TextField Egyptian_Lines_MWsField = new TextField();
            Label Total_daily_Supply_available_in_MWsLabel = new Label("New Total_daily_Supply_available_in_MWs:");
            TextField Total_daily_Supply_available_in_MWsField = new TextField();
            Label Overall_demand_in_MWsLabel = new Label("New overall_demand_in_MWs:");
            TextField Overall_demand_in_MWsField = new TextField();
            Label Power_Cuts_hours_day_400mgLabel = new Label("New Power_Cuts_hours_day_400mg:");
            TextField Power_Cuts_hours_day_400mgField = new TextField();
            Label TempLabel = new Label("New Temp:");
            TextField TempField = new TextField();

            // create a datePicker
            DatePicker  datePicker =  new DatePicker();

            // Add labels and text fields to the GridPane
            updateGridPane.add(Israeli_Lines_MWsLabel, 0, 0);
            updateGridPane.add(Israeli_Lines_MWsField, 1, 0);
            updateGridPane.add(Gaza_Power_Plant_MWsLabel, 0, 1);
            updateGridPane.add(Gaza_Power_Plant_MWsField, 1, 1);
            updateGridPane.add(Egyptian_Lines_MWsLabel, 0, 2);
            updateGridPane.add(Egyptian_Lines_MWsField, 1, 2);
            updateGridPane.add(Total_daily_Supply_available_in_MWsLabel, 0, 3);
            updateGridPane.add(Total_daily_Supply_available_in_MWsField, 1, 3);
            updateGridPane.add(Overall_demand_in_MWsLabel, 0, 4);
            updateGridPane.add(Overall_demand_in_MWsField, 1, 4);
            updateGridPane.add(Power_Cuts_hours_day_400mgLabel, 0, 5);
            updateGridPane.add(Power_Cuts_hours_day_400mgField, 1, 5);
            updateGridPane.add(TempLabel, 0, 6);
            updateGridPane.add(TempField, 1, 6);
            updateGridPane.add(new Label("Date :"), 0, 7);
            updateGridPane.add(datePicker, 1, 7);

            // create update  messageLabel
            Label messageLabel=new Label("");
            updateGridPane.add(messageLabel, 0, 9);


            // create save button and add it in grid pane
            Button saveButton = new Button("Save");
            updateGridPane.add(saveButton, 1, 9);

            // Handle save button click event
            saveButton.setOnAction(saveEvent -> {
                try {
                    // store each filed
                    LocalDate selectedDate = datePicker.getValue();
                    String date = selectedDate.toString();
                    float israeliLinesMWs = Float.valueOf(Israeli_Lines_MWsField.getText());
                    float gazaPowerPlantMWs = Float.valueOf(Gaza_Power_Plant_MWsField.getText());
                    float egyptianLinesMWs = Float.valueOf(Egyptian_Lines_MWsField.getText());
                    float totalDailySupplyAvailableInMWs = Float.valueOf(Total_daily_Supply_available_in_MWsField.getText());
                    float overallDemandInMWs = Float.valueOf(Overall_demand_in_MWsField.getText());
                    float powerCutsHoursDay400mg = Float.valueOf(Power_Cuts_hours_day_400mgField.getText());
                    float temp = Float.valueOf(TempField.getText());

                    // create new object of type electricity record to hold the record
                    ElectricityRecord electricityRecord = new ElectricityRecord(date,
                            israeliLinesMWs, gazaPowerPlantMWs, egyptianLinesMWs,
                            totalDailySupplyAvailableInMWs, overallDemandInMWs,
                            powerCutsHoursDay400mg, temp);

                    // split the date in year ,month and day
                    int year, month, day;

                    String[] dateParts = date.split("-");

                    year = Integer.parseInt(dateParts[0]);
                    month = Integer.parseInt(dateParts[1]);
                    day = Integer.parseInt(dateParts[2]);

                    // add the record in the list
          if (yearTree.update(day, month, year, electricityRecord))
                        messageLabel.setText("  update successfully.");
                    else
                        messageLabel.setText("  update failed!");

                }catch (Exception ex){

                    messageLabel.setText("  error !.");
                }

            });

            Scene addScene = new Scene(updateGridPane);
            updateStage.setScene(addScene);
            updateStage.show();
        });

        // program remove menuItem
        deleteItem.setOnAction(e -> {
            // Create a new stage to delete the record
            Stage deleteStage = new Stage();
           deleteStage.setTitle("Delete record");

            // create grid pane
            GridPane deleteGridPane = new GridPane();
            deleteGridPane.setPadding(new Insets(30));
            deleteGridPane.setHgap(10);
            deleteGridPane.setVgap(10);

            // Create a "Delete" button
            Button deleteButton = new Button("Delete");

            // create add messageLabel
            Label messageLabel=new Label("");

            // create a datePicker
            DatePicker  datePicker =  new DatePicker();

            // Add labels and text fields to the GridPane
            deleteGridPane.add(new Label("Date :"), 1, 0);
            deleteGridPane.add(datePicker, 1, 1);
            deleteGridPane.add(deleteButton, 1, 2);
            deleteGridPane.add(messageLabel, 1, 3);

            // Handle the "Remove" button click event
            deleteButton.setOnAction(removeEvent -> {

                // get the date
                LocalDate selectedDate =  datePicker.getValue();
                String date = selectedDate.toString();

                // split the date in year ,month and day
                int year, month, day;

                String[] dateParts = date.split("-");

                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]);
                day = Integer.parseInt(dateParts[2]);


                if (yearTree.search(day,month,year) == null) { // the record not found

                    messageLabel.setText("  Record not found!");

                } else {

                    // to chick if user shore to delete
                    AtomicBoolean isOK= new AtomicBoolean(false);

                    // Create a new stage to delete
                    Stage warningStage = new Stage();
                   warningStage.setTitle("Warning");

                    // create grid pane
                    GridPane warningGridPane = new GridPane();
                    warningGridPane.setPadding(new Insets(30));
                    warningGridPane.setHgap(10);
                    warningGridPane.setVgap(10);

                    // Create a "Delete" button
                    Button OkButton = new Button("OK");

                    // Add labels and text fields to the GridPane
                    warningGridPane.add(new Label("Are you shore to delete record ! "), 1, 0);
                    warningGridPane.add(OkButton, 1, 1);

                    // Handle the "Remove" button click event
                    OkButton.setOnAction(okEvent -> {

                        // the user is shore
                        yearTree.remove(day,month,year);
                        messageLabel.setText(" remove successfully.");
                        warningStage.close();

                    });

                    Scene inputScene = new Scene(warningGridPane);
                    warningStage.setScene(inputScene);
                    warningStage.show();

                }
            });

            Scene inputScene = new Scene(deleteGridPane);
            deleteStage.setScene(inputScene);
            deleteStage.show();
        });

        // program the update menuItem
        searchItem.setOnAction(e -> {
            Stage searchStage = new Stage();
            searchStage.setTitle("Search record");

            GridPane searchGridPane = new GridPane();
            searchGridPane.setPadding(new Insets(30));
            searchGridPane.setHgap(10);
            searchGridPane.setVgap(10);

            // Create labels and text fields for record information
            Label Israeli_Lines_MWsLabel = new Label("Israeli_Lines_MWs:");
            TextField Israeli_Lines_MWsField = new TextField();
            Label Gaza_Power_Plant_MWsLabel = new Label("Gaza_Power_Plant_MWs:");
            TextField Gaza_Power_Plant_MWsField = new TextField();
            Label Egyptian_Lines_MWsLabel = new Label("Egyptian_Lines_MWs:");
            TextField Egyptian_Lines_MWsField = new TextField();
            Label Total_daily_Supply_available_in_MWsLabel = new Label("Total_daily_Supply_available_in_MWs:");
            TextField Total_daily_Supply_available_in_MWsField = new TextField();
            Label Overall_demand_in_MWsLabel = new Label("overall_demand_in_MWs:");
            TextField Overall_demand_in_MWsField = new TextField();
            Label Power_Cuts_hours_day_400mgLabel = new Label("Power_Cuts_hours_day_400mg:");
            TextField Power_Cuts_hours_day_400mgField = new TextField();
            Label TempLabel = new Label("Temp:");
            TextField TempField = new TextField();

            // make textField non-editable
            Israeli_Lines_MWsField.setEditable(false);
            Gaza_Power_Plant_MWsField.setEditable(false);
            Egyptian_Lines_MWsField.setEditable(false);
            Total_daily_Supply_available_in_MWsField.setEditable(false);
            Overall_demand_in_MWsField.setEditable(false);
            Power_Cuts_hours_day_400mgField.setEditable(false);
            TempField.setEditable(false);

            // create a datePicker
            DatePicker  datePicker =  new DatePicker();

            // Add labels and text fields to the GridPane
            searchGridPane.add(Israeli_Lines_MWsLabel, 0, 0);
            searchGridPane.add(Israeli_Lines_MWsField, 1, 0);
            searchGridPane.add(Gaza_Power_Plant_MWsLabel, 0, 1);
            searchGridPane.add(Gaza_Power_Plant_MWsField, 1, 1);
            searchGridPane.add(Egyptian_Lines_MWsLabel, 0, 2);
            searchGridPane.add(Egyptian_Lines_MWsField, 1, 2);
            searchGridPane.add(Total_daily_Supply_available_in_MWsLabel, 0, 3);
            searchGridPane.add(Total_daily_Supply_available_in_MWsField, 1, 3);
            searchGridPane.add(Overall_demand_in_MWsLabel, 0, 4);
            searchGridPane.add(Overall_demand_in_MWsField, 1, 4);
            searchGridPane.add(Power_Cuts_hours_day_400mgLabel, 0, 5);
            searchGridPane.add(Power_Cuts_hours_day_400mgField, 1, 5);
            searchGridPane.add(TempLabel, 0, 6);
            searchGridPane.add(TempField, 1, 6);
            searchGridPane.add(new Label("Date :"), 0, 9);
            searchGridPane.add(datePicker, 1, 9);

            // create add messageLabel
            Label messageLabel=new Label("");
            searchGridPane.add(messageLabel, 0, 10);

            // create save button and add it in grid pane
            Button searchButton = new Button("search");
            searchGridPane.add(searchButton, 1, 10);

                        // Handle save button click event
                        searchButton.setOnAction(search -> {

                            try {



                            // make textField non-editable
                            Israeli_Lines_MWsField.setText("");
                            Gaza_Power_Plant_MWsField.setText("");
                            Egyptian_Lines_MWsField.setText("");
                            Total_daily_Supply_available_in_MWsField.setText("");
                            Overall_demand_in_MWsField.setText("");
                            Power_Cuts_hours_day_400mgField.setText("");
                            TempField.setText("");

                            // get the date
                            LocalDate selectedDate =  datePicker.getValue();
                            String date = selectedDate.toString();

                            // split the date in year ,month and day
                            int year, month, day;
                            String[] dateParts = date.split("-");
                            // store each field
                            year = Integer.parseInt(dateParts[0]);
                            month = Integer.parseInt(dateParts[1]);
                            day = Integer.parseInt(dateParts[2]);

                            // create new object of type electricity record to hold the record
                            ElectricityRecord electricityRecord = (ElectricityRecord) yearTree.search(day,month,year);

                            // chick if exist
                            if( electricityRecord ==null )
                                messageLabel.setText("  Record not found !");
                            else{
                                messageLabel.setText("  Record found ");

                                 Israeli_Lines_MWsField.setText(String.valueOf(electricityRecord.getIsraeliLinesMWs()));
                                Gaza_Power_Plant_MWsField.setText(String.valueOf(electricityRecord.getGazaPowerPlantMWs()));
                                Egyptian_Lines_MWsField.setText(String.valueOf(electricityRecord.getEgyptianLinesMWs()));
                                Total_daily_Supply_available_in_MWsField.setText(String.valueOf(electricityRecord.getTotalDailySupplyAvailableInMWs()));
                                Overall_demand_in_MWsField.setText(String.valueOf(electricityRecord.getOverallDemandInMWs()));
                                Power_Cuts_hours_day_400mgField.setText(String.valueOf(electricityRecord.getPowerCutsHoursDay400mg()));
                                TempField.setText(String.valueOf(electricityRecord.getTemp()));

                            }
                            } catch (NumberFormatException ex){
                                messageLabel.setText("error try again !");
                            }
                        });

            Scene searchScene = new Scene(searchGridPane);
            searchStage.setScene(searchScene);
            searchStage.show();
        });

        // program the day statistic
        dayItem.setOnAction(s->{

            // Create a new stage to day statistic
            Stage datStage = new Stage();
            datStage.setTitle("Day statistic");

            // create grid pane
            GridPane dayGridPane = new GridPane();
            dayGridPane .setPadding(new Insets(30));
            dayGridPane .setHgap(10);
            dayGridPane.setVgap(10);

            // create textField for enter the day
            TextField dayField =new TextField();

            // Create a "print" button
            Button printButton = new Button("print");

            // create text area and
            TextArea textArea=new TextArea();

            // make text area non-editable
            textArea.setEditable(false);

            // Add labels and text fields to the GridPane
            dayGridPane.add(new Label("Enter the day :"), 1, 0);
            dayGridPane.add(dayField, 1, 1);
            dayGridPane.add(printButton, 1, 2);
            dayGridPane.add(textArea, 1, 3);

            // add the error message label
            Label errorMessage =new Label("");
            dayGridPane.add(errorMessage,0,2);


                        // Handle the "print" button click event
                       printButton.setOnAction(printEvent -> {

                            try {


                            textArea.setText(yearTree.dayStatistic(Integer.parseInt(dayField.getText())));
                            }catch (NumberFormatException ex ){

                                errorMessage.setText("enter only digit !");
                            }
                        });

            Scene inputScene = new Scene(dayGridPane);
            datStage.setScene(inputScene);
            datStage.show();

    });

        // program the month statistic
        monthItem.setOnAction(s->{

            // Create a new stage to month statistic
            Stage monthStage = new Stage();
            monthStage.setTitle("month statistic");

            // create grid pane
            GridPane monthGridPane = new GridPane();
            monthGridPane .setPadding(new Insets(30));
            monthGridPane .setHgap(10);
            monthGridPane.setVgap(10);

            // create textField for enter the month
            TextField monthField =new TextField();

            // Create a "print" button
            Button printButton = new Button("print");

            // create text area
            TextArea textArea=new TextArea();

            // make text area non-editable
            textArea.setEditable(false);

            // Add labels and text fields to the GridPane
            monthGridPane.add(new Label("Enter the month :"), 1, 0);
            monthGridPane.add(monthField, 1, 1);
            monthGridPane.add(printButton, 1, 2);
            monthGridPane.add(textArea, 1, 3);

            // add the error message label
            Label errorMessage =new Label("");
            monthGridPane.add(errorMessage,0,2);


            // Handle the "print" button click event
            printButton.setOnAction(printEvent -> {
                try {
                    // print the statistic
                    textArea.setText(yearTree.monthStatistic(Integer.valueOf(monthField.getText())));
                } catch (NotYetBoundException ex){

                    errorMessage.setText("enter only digit !");
                }
            });

            Scene inputScene = new Scene(monthGridPane);
            monthStage.setScene(inputScene);
            monthStage.show();

        });

        // program the year statistic
        yearItem.setOnAction(s->{

            // Create a new stage to day statistic
            Stage yearStage = new Stage();
            yearStage.setTitle("year statistic");

            // create grid pane
            GridPane yearGridPane = new GridPane();
            yearGridPane .setPadding(new Insets(30));
            yearGridPane .setHgap(10);
            yearGridPane.setVgap(10);

            // create textField for enter the year
            TextField yearField =new TextField();

            // Create a "print" button
            Button printButton = new Button("print");

            // create text area and
            TextArea textArea=new TextArea();

            // make text area non-editable
            textArea.setEditable(false);

            // Add labels and text fields to the GridPane
            yearGridPane.add(new Label("Enter the year :"), 1, 0);
            yearGridPane.add(yearField, 1, 1);
            yearGridPane.add(printButton, 1, 2);
            yearGridPane.add(textArea, 1, 3);

            // add the error message label
            Label errorMessage =new Label("");
            yearGridPane.add(errorMessage,0,2);


            // Handle the "print" button click event
            printButton.setOnAction(printEvent -> {

                try {
                    textArea.setText(yearTree.yearStatistic(Integer.valueOf(yearField.getText())));
                } catch (NotYetBoundException ex ){
                    errorMessage.setText("enter only digit !");
                }
            });

            Scene inputScene = new Scene(yearGridPane);
            yearStage.setScene(inputScene);
            yearStage.show();

        });

        // program the total menuItem
        totalItem.setOnAction(e -> {
            Stage totalStage = new Stage();
            totalStage.setTitle("total record");

            GridPane totalGridPane = new GridPane();
            totalGridPane.setPadding(new Insets(30));
            totalGridPane.setHgap(10);
            totalGridPane.setVgap(10);

            // Create labels and text fields for record information
            Label Israeli_Lines_MWsLabel = new Label("Israeli_Lines_MWs:");
            Label Gaza_Power_Plant_MWsLabel = new Label("Gaza_Power_Plant_MWs:");
            Label Egyptian_Lines_MWsLabel = new Label("Egyptian_Lines_MWs:");
            Label Total_daily_Supply_available_in_MWsLabel = new Label("Total_daily_Supply_available_in_MWs:");
            Label Overall_demand_in_MWsLabel = new Label("overall_demand_in_MWs:");
            Label Power_Cuts_hours_day_400mgLabel = new Label("Power_Cuts_hours_day_400mg:");
            Label TempLabel = new Label("Temp:");

            // create min text feile and liable
            TextField min_Israeli_Lines_MWsField = new TextField();
            TextField min_Gaza_Power_Plant_MWsField = new TextField();
            TextField min_Egyptian_Lines_MWsField = new TextField();
            TextField min_Total_daily_Supply_available_in_MWsField = new TextField();
            TextField min_Overall_demand_in_MWsField = new TextField();
            TextField min_Power_Cuts_hours_day_400mgField = new TextField();
            TextField min_TempField = new TextField();

            //add the min in grid pane
            totalGridPane.add( min_Israeli_Lines_MWsField, 2, 1);
            totalGridPane.add( min_Gaza_Power_Plant_MWsField, 2, 2);
            totalGridPane.add( min_Egyptian_Lines_MWsField, 2, 3);
            totalGridPane.add( min_Total_daily_Supply_available_in_MWsField, 2, 4);
            totalGridPane.add( min_Overall_demand_in_MWsField, 2, 5);
            totalGridPane.add( min_Power_Cuts_hours_day_400mgField, 2, 6);
            totalGridPane.add(min_TempField, 2, 7);

            // create max text feile and liable
            TextField max_Israeli_Lines_MWsField = new TextField();
            TextField max_Gaza_Power_Plant_MWsField = new TextField();
            TextField max_Egyptian_Lines_MWsField = new TextField();
            TextField max_Total_daily_Supply_available_in_MWsField = new TextField();
            TextField max_Overall_demand_in_MWsField = new TextField();
            TextField max_Power_Cuts_hours_day_400mgField = new TextField();
            TextField max_TempField = new TextField();

            //add the max in grid pane
            totalGridPane.add(max_Israeli_Lines_MWsField, 3, 1);
            totalGridPane.add(max_Gaza_Power_Plant_MWsField, 3, 2);
            totalGridPane.add(max_Egyptian_Lines_MWsField, 3, 3);
            totalGridPane.add(max_Total_daily_Supply_available_in_MWsField, 3, 4);
            totalGridPane.add(max_Overall_demand_in_MWsField, 3, 5);
            totalGridPane.add(max_Power_Cuts_hours_day_400mgField, 3, 6);
            totalGridPane.add(max_TempField, 3, 7);

            // create sum text feile and liable
            TextField sum_Israeli_Lines_MWsField = new TextField();
            TextField sum_Gaza_Power_Plant_MWsField = new TextField();
            TextField sum_Egyptian_Lines_MWsField = new TextField();
            TextField sum_Total_daily_Supply_available_in_MWsField = new TextField();
            TextField sum_Overall_demand_in_MWsField = new TextField();
            TextField sum_Power_Cuts_hours_day_400mgField = new TextField();
            TextField sum_TempField = new TextField();

            //add the max in grid pane
            totalGridPane.add(sum_Israeli_Lines_MWsField, 4, 1);
            totalGridPane.add(sum_Gaza_Power_Plant_MWsField, 4, 2);
            totalGridPane.add(sum_Egyptian_Lines_MWsField, 4, 3);
            totalGridPane.add(sum_Total_daily_Supply_available_in_MWsField, 4, 4);
            totalGridPane.add(sum_Overall_demand_in_MWsField, 4, 5);
            totalGridPane.add(sum_Power_Cuts_hours_day_400mgField, 4, 6);
            totalGridPane.add(sum_TempField, 4, 7);

            // create avg text feile and liable
            TextField avg_Israeli_Lines_MWsField = new TextField();
            TextField avg_Gaza_Power_Plant_MWsField = new TextField();
            TextField avg_Egyptian_Lines_MWsField = new TextField();
            TextField avg_Total_daily_Supply_available_in_MWsField = new TextField();
            TextField avg_Overall_demand_in_MWsField = new TextField();
            TextField avg_Power_Cuts_hours_day_400mgField = new TextField();
            TextField avg_TempField = new TextField();

            //add the avg in grid pane
            totalGridPane.add(avg_Israeli_Lines_MWsField, 5, 1);
            totalGridPane.add(avg_Gaza_Power_Plant_MWsField, 5, 2);
            totalGridPane.add(avg_Egyptian_Lines_MWsField, 5, 3);
            totalGridPane.add(avg_Total_daily_Supply_available_in_MWsField, 5, 4);
            totalGridPane.add(avg_Overall_demand_in_MWsField, 5, 5);
            totalGridPane.add(avg_Power_Cuts_hours_day_400mgField, 5, 6);
            totalGridPane.add(avg_TempField, 5, 7);

            // create a datePicker
            DatePicker  datePicker =  new DatePicker();

            // crate sum avd min max label and add them
            Label sumLabel= new Label("Sum");
            Label averageLabel= new Label("Average");
            Label maximumLabel= new Label("maximum");
            Label minimumLabel= new Label("minimum");
            totalGridPane.add(sumLabel,4,0);
            totalGridPane.add(averageLabel,5,0);
            totalGridPane.add(maximumLabel,3,0);
            totalGridPane.add(minimumLabel,2,0);

            // Add labels and text fields to the GridPane
            totalGridPane.add(Israeli_Lines_MWsLabel, 0, 1);
            totalGridPane.add(Gaza_Power_Plant_MWsLabel, 0, 2);
            totalGridPane.add(Egyptian_Lines_MWsLabel, 0, 3);
            totalGridPane.add(Total_daily_Supply_available_in_MWsLabel, 0, 4);
            totalGridPane.add(Overall_demand_in_MWsLabel, 0, 5);
            totalGridPane.add(Power_Cuts_hours_day_400mgLabel, 0, 6);
            totalGridPane.add(TempLabel, 0, 7);

            // create save button and add it in grid pane
            Button printButton = new Button("print");
            totalGridPane.add(printButton, 1, 10);

            // Handle save button click event
            printButton.setOnAction(search -> {

                double[] ans = yearTree.total();

                min_Israeli_Lines_MWsField.setText(String.valueOf(ans[0]));
                min_Gaza_Power_Plant_MWsField.setText(String.valueOf(ans[1]));
                min_Egyptian_Lines_MWsField.setText(String.valueOf(ans[2]));
                min_Total_daily_Supply_available_in_MWsField.setText(String.valueOf(ans[3]));
                min_Overall_demand_in_MWsField.setText(String.valueOf(ans[4]));
                min_Power_Cuts_hours_day_400mgField.setText(String.valueOf(ans[5]));
                min_TempField.setText(String.valueOf(ans[6]));
                max_Israeli_Lines_MWsField.setText(String.valueOf(ans[7]));
                max_Gaza_Power_Plant_MWsField.setText(String.valueOf(ans[8]));
                max_Egyptian_Lines_MWsField.setText(String.valueOf(ans[9]));
                max_Total_daily_Supply_available_in_MWsField.setText(String.valueOf(ans[10]));
                max_Overall_demand_in_MWsField.setText(String.valueOf(ans[11]));
                max_Power_Cuts_hours_day_400mgField.setText(String.valueOf(ans[12]));
                max_TempField.setText(String.valueOf(ans[13]));
                sum_Israeli_Lines_MWsField.setText(String.valueOf(ans[14]));
                sum_Gaza_Power_Plant_MWsField.setText(String.valueOf(ans[15]));
                sum_Egyptian_Lines_MWsField.setText(String.valueOf(ans[16]));
                sum_Total_daily_Supply_available_in_MWsField.setText(String.valueOf(ans[17]));
                sum_Overall_demand_in_MWsField.setText(String.valueOf(ans[18]));
                sum_Power_Cuts_hours_day_400mgField.setText(String.valueOf(ans[19]));
                sum_TempField.setText(String.valueOf(ans[20]));
                avg_Israeli_Lines_MWsField.setText(String.valueOf(ans[21]));
                avg_Gaza_Power_Plant_MWsField.setText(String.valueOf(ans[22]));
                avg_Egyptian_Lines_MWsField.setText(String.valueOf(ans[23]));
                avg_Total_daily_Supply_available_in_MWsField.setText(String.valueOf(ans[24]));
                avg_Overall_demand_in_MWsField.setText(String.valueOf(ans[25]));
                avg_Power_Cuts_hours_day_400mgField.setText(String.valueOf(ans[26]));
                avg_TempField.setText(String.valueOf(ans[27]));


            });

            Scene searchScene = new Scene(totalGridPane);
            totalStage.setScene(searchScene);
            totalStage.show();
        });


        // program the printByLevel
        printByLevel.setOnAction(s->{

            // Create a new stage to printByLevel
            Stage printStage = new Stage();
            printStage.setTitle("printByLevel");

            // create grid pane
            GridPane printGridPane = new GridPane();
            printGridPane .setPadding(new Insets(30));
            printGridPane .setHgap(10);
            printGridPane.setVgap(10);

            // create textField for enter the year and month
            TextField monthField =new TextField();
            TextField yearField =new TextField();

            // Create a "print" button
            Button printButton = new Button("print");

            // create text area
            TextArea textArea=new TextArea();

            // make text area non-editable
            textArea.setEditable(false);

            // Add labels and text fields to the GridPane
            printGridPane.add(new Label("Enter the month :"), 1, 0);
            printGridPane.add(monthField, 1, 1);
            printGridPane.add(new Label("Enter the year :"), 2, 0);
            printGridPane.add(yearField, 2, 1);

            printGridPane.add(printButton, 1, 2);
            printGridPane.add(textArea, 1, 3);

            // add the error message label
            Label errorMessage =new Label("");
            printGridPane.add(errorMessage,0,2);


            // Handle the "print" button click event
            printButton.setOnAction(printEvent -> {
                try {
                    // print the statistic
                    textArea.setText(yearTree.printLevel(Integer.valueOf(yearField.getText()),Integer.valueOf(monthField.getText())));
                } catch (Exception ex){

                    errorMessage.setText("enter only digit !");
                }
            });

            Scene inputScene = new Scene(printGridPane,600,400);
            printStage.setScene(inputScene);
           printStage.show();

        });


        // program the printHeight
        printHeight.setOnAction(s->{

            // Create a new stage to printByHeight
            Stage printStage = new Stage();
            printStage.setTitle("printByHeight");

            // create grid pane
            GridPane printGridPane = new GridPane();
            printGridPane .setPadding(new Insets(30));
            printGridPane .setHgap(10);
            printGridPane.setVgap(10);

            // create textField for enter the year and month
            TextField monthField =new TextField();
            TextField yearField =new TextField();

            // Create a "print" button
            Button printButton = new Button("print");

            // create text area
            TextArea textArea=new TextArea();

            // make text area non-editable
            textArea.setEditable(false);

            // Add labels and text fields to the GridPane
            printGridPane.add(new Label("Enter the month :"), 1, 0);
            printGridPane.add(monthField, 1, 1);
            printGridPane.add(new Label("Enter the year :"), 2, 0);
            printGridPane.add(yearField, 2, 1);

            printGridPane.add(printButton, 1, 2);
            printGridPane.add(textArea, 1, 3);

            // add the error message label
            Label errorMessage =new Label("");
            printGridPane.add(errorMessage,0,2);


            // Handle the "print" button click event
            printButton.setOnAction(printEvent -> {
                try {
                    // print the statistic
                    textArea.setText(yearTree.printHeight(Integer.valueOf(yearField.getText()),Integer.valueOf(monthField.getText())));
                } catch (Exception ex){

                    errorMessage.setText("enter only digit !");
                }
            });

            Scene inputScene = new Scene(printGridPane,600,400);
            printStage.setScene(inputScene);
            printStage.show();

        });





        // Create a layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        // Create a scene
        Scene scene = new Scene(borderPane, 400, 300);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    // to print all the dat in the year







}