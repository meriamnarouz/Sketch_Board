
/*
//  Description: This file lets the user draw different shapes with different sizes
//				 and colors. The user can also erase everything or undo a shape or 
//				 undo everything that was just erased by the user. In order to do so  
//				 it uses private classes, ArrayLists, Buttons, ComboBoxes, Labels, 
//				 RadioButtons, Color, Line, Rectangle, Circle, and more.  
*/

import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class SketchPane extends BorderPane 
{

	//ArrayLists
	private ArrayList<Shape> shapeList;
	private ArrayList<Shape> tempList;
	
	//Buttons
	private Button undoButton;
	private Button eraseButton;
	
	//Labels
	private Label fillColorLabel;
	private Label strokeColorLabel;
	private Label strokeWidthLabel;
	
	//ComboBox
	private ComboBox<String> fillColorCombo;
	private ComboBox<String> strokeColorCombo;
	private ComboBox<String> strokeWidthCombo;
	
	//RadioButtons
	private RadioButton radioButtonLine;
	private RadioButton radioButtonRectangle;
	private RadioButton radioButtonCircle;
	
	//Pane
	private Pane sketchCanvas;
	
	//Color[]
	private Color[] colors;
	
	//String[]
	private String[] colorLabels;
	private String[] strokeWidth;
	
	//Color
	private Color currentStrokeColor;
	private Color currentFillColor;
	
	//integer
	private int currentStrokeWidth;
	
	//Lines
	private Line line;
	
	//Circles
	private Circle circle;
	
	//Rectangles
	private Rectangle rectangle;
	
	//double
	private double x1;
	private double y1;
	

	public SketchPane() 
	{
		// Colors, labels, and stroke widths that are available to the user
		colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME, Color.WHITE} ;
        colorLabels = new String[] {"black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime", "white"};
        fillColorLabel = new Label("Fill Color:");
        strokeColorLabel = new Label("Stroke Color:");
        strokeWidthLabel = new Label("Stroke Width:");
        strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13"};    
        
        //instantiate ArrayList
        shapeList = new ArrayList<Shape>(); //arrayList of shapes
        tempList = new ArrayList<Shape>(); //backup arrayList of shapes
        
        //instantiate ComboBox
        //fill color comboBox
        fillColorCombo = new ComboBox<String>();
        fillColorCombo.setValue("Fill Color"); //name it
        fillColorCombo.setOnAction(new ColorHandler()); //bind it
        fillColorCombo.getItems().addAll(colorLabels); //add String[] colorLabels
        fillColorCombo.getSelectionModel().select(0); //set default
        
        //stroke color comboBox
        strokeColorCombo = new ComboBox<String>();
        strokeColorCombo.setValue("Stroke Color"); //name it
        strokeColorCombo.setOnAction(new ColorHandler()); //bind it
        strokeColorCombo.getItems().addAll(colorLabels); //add String[] colorLabels
        strokeColorCombo.getSelectionModel().select(0); //set default
        
        //stroke width comboBox
        strokeWidthCombo = new ComboBox<String>();
        strokeWidthCombo.setValue("Stroke Width"); //name it
        strokeWidthCombo.setOnAction(new WidthHandler()); //bind it
        strokeWidthCombo.getItems().addAll(strokeWidth); //add String[] strokeWidth
        strokeWidthCombo.getSelectionModel().select(0); //set default
           
        //instantiate RadioButtons
        ToggleGroup tg = new ToggleGroup();//toggleGroup for radioButtons
        
        //line radioButton
        radioButtonLine = new RadioButton("Line"); 
        radioButtonLine.setToggleGroup(tg); //add it to toggleGroup
        radioButtonLine.setSelected(true); //set line as default
        
        //rectangle radioButton
        radioButtonRectangle = new RadioButton("Rectangle"); //rectangle
        radioButtonRectangle.setToggleGroup(tg);//add it to toggleGroup
        
        //circle radioButton
        radioButtonCircle = new RadioButton("Circle"); //circle
        radioButtonCircle.setToggleGroup(tg);//add it to toggleGroup
        
        
        //instantiate Buttons
        //create and name undoButton then bind it
        undoButton = new Button("Undo");
        undoButton.setOnAction(new ButtonHandler());
        
        //create and name eraseButton then bind it
        eraseButton = new Button("Erase");  
        eraseButton.setOnAction(new ButtonHandler());
        
        //instantiate integers
        x1 = 0;
        y1 = 0;

        //instantiate sketchCanvas and bind it to MouseHandler
        sketchCanvas = new Pane();
        sketchCanvas.setStyle("-fx-background-color: white;"); //set background to white
        sketchCanvas.setOnMousePressed(new MouseHandler()); //if mouse is pressed
        sketchCanvas.setOnMouseDragged(new MouseHandler()); //if mouse is dragged
        sketchCanvas.setOnMouseReleased(new MouseHandler()); //if mouse is released
        
        //set default current color, stroke, and width
        currentFillColor = Color.BLACK;
        currentStrokeColor = Color.BLACK;
        currentStrokeWidth = 1;
        
        //HBoxes
        //hBox1 for bar at the top
        HBox hBox1 = new HBox(20);
        //add comboBoxes to hBox1 in order
        hBox1.getChildren().addAll(fillColorLabel, fillColorCombo, 
        						strokeWidthLabel, strokeWidthCombo, 
        						strokeColorLabel, strokeColorCombo);
        hBox1.setAlignment(Pos.CENTER); //center it at the top
        hBox1.setStyle("-fx-background-color: lightgrey;"); //set background color of box
        hBox1.setMinWidth(20); //set width
        hBox1.setMinHeight(40); //set height
        
        //hBox2 for bar at the bottom
        HBox hBox2 = new HBox(20);
        //add all buttons to hBox2 in order
        hBox2.getChildren().addAll(radioButtonLine, radioButtonRectangle, 
        							radioButtonCircle, undoButton, eraseButton);
        hBox2.setAlignment(Pos.CENTER); //center it at the top
        hBox2.setStyle("-fx-background-color: lightgrey;"); //set background color of box
        hBox2.setMinWidth(20); //set width
        hBox2.setMinHeight(40); //set height
        
        //setup of stage
           
        this.setCenter(sketchCanvas); //set sketchCanvas as center of pane
        this.setTop(hBox1); //set hBox1 at top of pane 
        this.setBottom(hBox2); //set hBox2 as bottom of pane
	}//end of constructor

	//for whenever the mouse is pressed, dragged, or released
	private class MouseHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// Rectangle Example given!
			if (radioButtonRectangle.isSelected()) 
			{
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) 
				{
					x1 = event.getX();
					y1 = event.getY();
					rectangle = new Rectangle();
					rectangle.setX(x1);
					rectangle.setY(y1);
					shapeList.add(rectangle);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(rectangle);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) 
				{
					rectangle.setWidth(Math.abs(event.getX() - x1));
					rectangle.setHeight(Math.abs(event.getY() - y1));

				}
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
				{
					rectangle.setFill(currentFillColor);
					rectangle.setStroke(currentStrokeColor);
					rectangle.setStrokeWidth(currentStrokeWidth);
				}
			}//end of outer if statement for rectangle
			//else if line radioButton is selected
			else if (radioButtonLine.isSelected()) 
			{
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) 
				{
					x1 = event.getX(); //get x of mouse
					y1 = event.getY(); //get y of mouse
					line = new Line(); 
					//set coordinates of line
					line.setStartX(x1);
					line.setStartY(y1);
					line.setEndX(x1);
					line.setEndY(y1);
					shapeList.add(line); //add line to shapeList
					line.setFill(Color.WHITE); //set fill color of line
					line.setStroke(Color.BLACK); //set stroke color of line
					sketchCanvas.getChildren().add(line); //add line to sketchCanvas
				}//end of first part of inner if-else statement
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) 
				{
					//get new x and new y of mouse for the end of the line 
					line.setEndX((event.getX()));
					line.setEndY((event.getY()));
				}//end of second part of inner if-else statement
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
				{
					//set fill color, stroke color, and stroke width of line
					line.setFill(currentFillColor);
					line.setStroke(currentStrokeColor);
					line.setStrokeWidth(currentStrokeWidth);
				}//end of third part of inner if-else statement
			}//end of outer if statement for line
			//else if circle radioButton is selected
			else if (radioButtonCircle.isSelected()) 
			{
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) 
				{
					x1 = event.getX(); //get x of mouse
					y1 = event.getY(); //get y of mouse
					circle = new Circle();
					//set the center coordinates
					circle.setCenterX(x1);
					circle.setCenterY(y1);
					shapeList.add(circle); //add circle to shapeList
					circle.setFill(Color.WHITE); //set fill color of circle
					circle.setStroke(Color.BLACK); //set stroke color of circle
					sketchCanvas.getChildren().add(circle); //add circle to sketchCanvas
				}//end of first part of inner if-else statement
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) 
				{
					//get new x and new y for circle
					double newX = event.getX();
					double newY = event.getY();
					//call getDistance method to get radius of circle
					double radius = getDistance(x1, y1, newX, newY); 
					circle.setRadius(radius); //set radius
				}//end of second part of inner if-else statement
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
				{
					//set fill color, stroke color, and stroke width of circle
					circle.setFill(currentFillColor);
					circle.setStroke(currentStrokeColor);
					circle.setStrokeWidth(currentStrokeWidth);
				}//end of third part of inner if-else statement
			}//end of outer if statement for circle
		}//end of handle method
	}//end of private class MouseHandler
		
	//for if the undo or erase button are pressed
	private class ButtonHandler implements EventHandler<ActionEvent> 
	{
		@Override
		public void handle(ActionEvent event) 
		{
			//if-else statement for undoButton and eraseButton
			//if undoButton is chosen
			if(event.getSource() == undoButton)
			{
				//inner if-else statement
				//if shapeList is more than 0 then remove last shape
				if(shapeList.size() > 0)
				{
					int index = shapeList.size() - 1; //get the last index
					shapeList.remove(index); //remove it from shapeList
					sketchCanvas.getChildren().remove(index); //remove it from sketchCanvas
				}
				//else if shapeList is 0 meaning it was just erased then restore it
				else if(shapeList.size() == 0)
				{
					shapeList.addAll(tempList); //add all shapes from tempList to shapeList
					sketchCanvas.getChildren().addAll(shapeList); //add it to sketchCanvas
				}//end of inner if-else statement
			}
			//else if eraseButton is chosen
			else if(event.getSource() == eraseButton)
			{
				tempList.clear(); //clear current tempList
				tempList.addAll(shapeList); //add all shapes from shapeList to tempList as a backup
				shapeList.clear(); //clear shapeList
				sketchCanvas.getChildren().clear(); //clear sketchCanvas
			}//end of if-else statement
				
		}//end of handle method
	}//end of private class ButtonHandler

	//class to set color of fill and stroke as chosen by user
	private class ColorHandler implements EventHandler<ActionEvent> 
	{
		@Override
		public void handle(ActionEvent event) 
		{

			//get index and get the color corresponding to index
			int fillNum = fillColorCombo.getSelectionModel().getSelectedIndex();
			currentFillColor = colors[fillNum];
			
			//get index and get the color corresponding to index
			int strokeNum = strokeColorCombo.getSelectionModel().getSelectedIndex();
			currentStrokeColor = colors[strokeNum];
		}//end of handle method
	}//end of private class ColorHandler
	
	//class to set width of stroke as chosen by user
	private class WidthHandler implements EventHandler<ActionEvent> 
	{
		@Override
		public void handle(ActionEvent event)
		{

			//get index
			int widthNum = strokeWidthCombo.getSelectionModel().getSelectedIndex();
			//get the value of that number corresponding to the index from the array
			currentStrokeWidth = Integer.valueOf(strokeWidth[widthNum]);
		}//end of handle method
	}//end of private class WidthHnadler
	
		
	// Get the Euclidean distance between (x1,y1) and (x2,y2)
    private double getDistance(double x1, double y1, double x2, double y2)  {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}//end of outer class SketchPane 