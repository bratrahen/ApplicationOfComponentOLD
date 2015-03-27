import org.eclipse.swt.widgets.Display;

public class RunEditorApplication {

    public static void main(String[] args) {
        Display display = new Display();
        MainWindow mainWindow = new MainWindow(display, "DEV000", "waypoint");

        mainWindow.addApplicationName("CP4Basic");
        mainWindow.addApplicationName("Waypoint");
        mainWindow.addApplicationName("_kucmat1");

        mainWindow.open();

        while (!mainWindow.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
