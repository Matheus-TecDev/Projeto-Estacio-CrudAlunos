package main;

import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class App {
    public static void main(String[] args) {
        // Garante que a GUI seja criada na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal();
        });
    }
}
