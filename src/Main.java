import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) {


         SelectionScreen selection = new SelectionScreen((p1, p2) -> {


            GameCtrl game = new GameCtrl(p1, p2, winner -> {
                System.out.println(winner);
            });

            Scene gameScene = new Scene(game.getRoot(), 900, 600);
            stage.setScene(gameScene);
            stage.show();

            game.start();
        });


        Scene selectScene = new Scene((Parent) selection.getRoot(), 400, 500);

        stage.setTitle("Battle Arena");
        stage.setScene(selectScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}