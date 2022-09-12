//Austin Shaw ID: 117992496

package Implementation;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.application.*;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class InterestTabelGUI extends Application {
	private TextArea displayArea;
	private Button buttonSimple, buttonCompound, buttonBoth;

	@Override
	public void start(Stage primaryStage) {
		int sceneWidth = 500, sceneHeight = 400;

		// calculations display area
		displayArea = new TextArea();
		displayArea.setEditable(false);
		displayArea.setWrapText(true);

		FlowPane fieldsPane = new FlowPane();
		fieldsPane.setHgap(8);
		fieldsPane.setVgap(8);
		fieldsPane.setPadding(new Insets(10, 10, 40, 10));

		// Principal field
		Label principalLabel = new Label("Principal");
		TextField principalEntry = new TextField();
		principalEntry.setPrefWidth(40);
		fieldsPane.getChildren().addAll(principalLabel, principalEntry);

		// Interest field
		Label rateLabel = new Label("Rate(Percentage)");
		TextField rateEntry = new TextField();
		rateEntry.setPrefWidth(40);
		fieldsPane.getChildren().addAll(rateLabel, rateEntry);

		/* Adding scroll pane to calculations display area */
		ScrollPane scrollPane = new ScrollPane(displayArea);

		/* Set scrollPane to top of window */
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(scrollPane);
		BorderPane.setAlignment(scrollPane, Pos.TOP_CENTER);

		// Num of years slider/label
		Label numYearsLabel = new Label("Num of Years");
		fieldsPane.getChildren().addAll(numYearsLabel);

		Slider horizontalSlider = new Slider();
		horizontalSlider.setOrientation(Orientation.HORIZONTAL);
		horizontalSlider.setMin(0);
		horizontalSlider.setMax(25);
		horizontalSlider.setValue(1);
		horizontalSlider.setMajorTickUnit(5);
		horizontalSlider.setShowTickMarks(true);
		horizontalSlider.setShowTickLabels(true);
		fieldsPane.getChildren().addAll(horizontalSlider);

		// Interest selection buttons
		buttonSimple = new Button("Simple Interest");
		buttonCompound = new Button("Compound Interest");
		buttonBoth = new Button("Both Interests");
		fieldsPane.getChildren().addAll(buttonSimple, buttonCompound, buttonBoth);

		borderPane.setBottom(fieldsPane);

		// Computations-----------------------
		buttonSimple.setOnAction(e -> {
			double principal = Double.parseDouble(principalEntry.getText());
			double interest = Double.parseDouble(rateEntry.getText());
			double years = horizontalSlider.getValue();
			double amount = 0;
			String result = "";
			displayArea.clear();

			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

			result = "Principal: " + currencyFormatter.format(principal) + "  Rate: " + interest + "\n";

			for (int i = 1; i <= years; i++) {
				amount = Interest.InterestComp(principal, interest, i, "simple");
				result = result + i + "-->" + currencyFormatter.format(amount) + "\n";
			}

			displayArea.setText(result);
		});// simple interest

		buttonCompound.setOnAction(e -> {
			double principal = Double.parseDouble(principalEntry.getText());
			double interest = Double.parseDouble(rateEntry.getText());
			double years = horizontalSlider.getValue();
			double amount;
			String result = "";
			displayArea.clear();

			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

			result = "Principal: " + currencyFormatter.format(principal) + "  Rate: " + interest + "\n";

			for (int i = 1; i <= years; i++) {
				amount = Interest.InterestComp(principal, interest, i, "compound");

				result = result + i + "-->" + currencyFormatter.format(amount) + "\n";
			}

			displayArea.setText(result);
		});// compound interest

		buttonBoth.setOnAction(e -> {
			double principal = Double.parseDouble(principalEntry.getText());
			double interest = Double.parseDouble(rateEntry.getText());
			double years = horizontalSlider.getValue();
			double amount = 0;
			String result = "";

			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

			result = "Principal: " + currencyFormatter.format(principal) + "  Rate: " + interest + "\n";

			for (int i = 1; i <= years; i++) {
				amount = Interest.InterestComp(principal, interest, i, "simple");

				result = result + i + "-->" + currencyFormatter.format(amount) + "-->";

				amount = Interest.InterestComp(principal, interest, i, "compound");

				result = result + currencyFormatter.format(amount) + "\n";
			}

			displayArea.setText(result);
		});// both interests

		/* Display the stage */
		Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Interest Table");
		primaryStage.setScene(scene);
		primaryStage.show();
	}// Start

	public static void main(String[] args) {
		Application.launch(args);
	}// main
}// InterestTableGUI
