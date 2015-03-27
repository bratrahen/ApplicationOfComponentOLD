import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;

public class MainWindow {

    StatusLineManager statusBar;
    List listOfApplications;
    String version;
    String component;
    private GridLayout gridLayout = new GridLayout();
    private Shell mainWindow;

    MainWindow(Display display, String version, String component) {
        this.version = version;
        this.component = component;

        createWindow(display);

        createListOfApplications();
        createButtonOk();
        createStatusBar();
    }

    public void addApplicationName(String name) {
        listOfApplications.add(name);
    }

    private void createWindow(Display display) {
        mainWindow = new Shell(display);
        mainWindow.setSize(250, 200);
        mainWindow.setMinimumSize(250, 200);
        gridLayout.numColumns = 1;
        mainWindow.setLayout(gridLayout);
        mainWindow.setText("Choose Application");
    }

    private void createListOfApplications() {
        listOfApplications = new List(mainWindow, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
        listOfApplications.setLayoutData(gridData);

        listOfApplications.addSelectionListener(onSelectApplicationName());
    }

    private SelectionListener onSelectApplicationName() {
        return new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                try {
                    String fullFilePath = generateFilePath(version, listOfApplications.getSelection()[0]);
                    statusBar.setMessage(fullFilePath);
                } catch (Exception e) {
                    statusBar.setMessage(e.getMessage());
                }
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                widgetSelected(event);
            }
        };
    }

    private void createButtonOk() {
        final Button result = new Button(mainWindow, SWT.PUSH);
        result.setText("    OK    ");
        GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
        result.setLayoutData(gridData);

        result.addSelectionListener(onClickButtonOk());

        mainWindow.setDefaultButton(result);
    }

    private SelectionListener onClickButtonOk() {
        return new SelectionListener() {

            public void widgetSelected(SelectionEvent event) {
                try {
                    String fullFilePath = generateFilePath(version, listOfApplications.getSelection()[0]);
                    new File(fullFilePath).mkdirs();
                    mainWindow.dispose();
                } catch (Exception e) {
                    statusBar.setMessage(e.getMessage());
                }
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                statusBar.setMessage("No worries!");
            }
        };
    }


    private void createStatusBar() {
        statusBar = new StatusLineManager();
        Control control = statusBar.createControl(mainWindow);
        GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
        control.setLayoutData(gridData);

        statusBar.setMessage("Choose application of <" + component + ">:\"");
    }

    String generateFilePath(String version, String application) throws Exception {
        final String ENV_WORKSPACE_PATH = "4GL_WORKSPACE";
        if (System.getenv(ENV_WORKSPACE_PATH) == null)
            throw new Exception("ERROR: <" + ENV_WORKSPACE_PATH + "> environment variable is not defined.");

        return System.getenv(ENV_WORKSPACE_PATH) + File.separator +
                version + File.separator + application + File.separator;
    }

    public void open() {
        mainWindow.open();
    }

    public boolean isDisposed() {
        return mainWindow.isDisposed();
    }
}