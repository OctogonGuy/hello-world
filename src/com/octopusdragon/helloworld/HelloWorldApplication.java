package com.octopusdragon.helloworld;

import java.util.*;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.effect.*;
import javafx.geometry.*;

/**
 * Allows the user to create a custom message
 * @author Alex Gill
 * Last modified 10/2/2023
 */
public class HelloWorldApplication extends Application {
	
	// --- Constants ---
	
	// The padding around the message
	private static final double PADDING = 75.0;
	
	// The spacing between letters
	private static final double SPACING = 10.0;
	
	// The default message if the user does not choose one
	public static final String MESSAGE = "HELLO, WORLD!";
	
	// The colors to color the letters
	private static final String[] COLORS = {
			"red",
			"blue",
			"green",
			"yellow",
			"violet",
			"orange" };
	
	// The length of each frame of the animation in milliseconds
	private static final long DURATION = 800;
	
	
	// --- Variables ---
	
	// The index of the color of the first letter
	private int startColorIndex = 0;	
	
	
	// --- GUI components ---
	private Label[] messageLabel;	// The letters in the message
	
	/**
	 * Start method
	 * @param Stage primaryStage The stage to display the GUI.
	 */
	@Override
	public void start(Stage primaryStage) {
		
		// Prompt the user for a message
		TextInputDialog messageInputDialog = new TextInputDialog();
		messageInputDialog.setContentText("Message");
		messageInputDialog.setHeaderText("Input a message");
		messageInputDialog.setTitle("Custom Message Input");
		Optional<String> result = messageInputDialog.showAndWait();
		if (!result.isPresent()) {
			Platform.exit();
			return;
		}
		
		// Create the Label to display the message and put it in an HBox.
		messageLabel = buildLabel(!result.get().trim().isEmpty() ? result.get() : MESSAGE);
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-45.0);
		light.setElevation(60.0);
		Lighting lighting = new Lighting(light);
		lighting.setSurfaceScale(3.0);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setInput(lighting);
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(-5.0);
		dropShadow.setOffsetY(5.0);
		Reflection reflect = new Reflection();
		reflect.setInput(dropShadow);
		reflect.setFraction(0.475);
		for (Label l : messageLabel) {
			l.setStyle("-fx-text-alignment: center; " +
					   "-fx-font-family: sans-serif; " +
					   "-fx-font-weight: bold; " +
					   "-fx-font-size: 100px;");
			l.setEffect(reflect);
		}
		changeText();
		HBox hbox = new HBox(5, messageLabel);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(PADDING));
		hbox.setSpacing(SPACING);
		
		// Create a new Thread with a Timer on it.
		Timer textTimer = new Timer(true);
		textTimer.schedule(new ChangeTextTask(), DURATION, DURATION);
		
		// Create the Scene and display it.
		Scene scene = new Scene(hbox);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hello World Application");
		primaryStage.show();
	}
	
	/**
	 * Main method
	 * @param args The arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * The buildLabel method constructs a Label one character at a time so that
	 * each character can have a different color applied to it.
	 * @param str The text to set to the Label.
	 */
	public Label[] buildLabel(String str) {
		Label[] letterLabels = new Label[str.length()];
		
		// Create a Label for each letter in the String.
		for (int i = 0; i < str.length(); i++) {
			letterLabels[i] = new Label(String.valueOf(str.charAt(i)));
		}
		
		// Return the Label array.
		return letterLabels;
	}
	
	/**
	 * The changeText method changes the text of the message.
	 */
	public void changeText() {
		int colorIndex = startColorIndex;
		
		// Change the color of the character as long as it is not a space.
		for (Label l : messageLabel) {
			if (!l.getText().equals(" ")) {
				l.setStyle("-fx-text-alignment: center; " +
						   "-fx-font-family: sans-serif; " +
						   "-fx-font-weight: bold; " +
						   "-fx-font-size: 100px; " +
						   "-fx-text-fill: " + COLORS[colorIndex]);
				
				// Increment the current color.
				if (colorIndex == COLORS.length - 1)
					colorIndex = 0;
				else
					colorIndex++;
			}
		}
		
		// Increment the starting color.
		if (startColorIndex == 0)
			startColorIndex = COLORS.length - 1;
		else
			startColorIndex--;
	}
	
	/**
	 * This implementation of TimerTask changes the text in the hello world
	 * message.
	 */
	public class ChangeTextTask extends TimerTask {
		/**
		 * run method
		 */
		@Override
		public void run() {
			Platform.runLater(() -> {
				changeText();
			});
		}
	}
}
