import model.*;
import view.*;
import controller.*;
import utils.*;

public class Main {
    public static void main(String[] args) {

        // Model, View ve Controller oluşturma
        Personnel model = new Personnel();
        PersonnelView view = new PersonnelView();
        PersonnelController controller = new PersonnelController(model,view);

        // Görünümü açma
        view.setVisible(true);

        // Uygulama sonlanınca veritabanı bağlantısını kapatma
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeConnection();  // Bağlantıyı güvenli bir şekilde kapat
    }));
}}