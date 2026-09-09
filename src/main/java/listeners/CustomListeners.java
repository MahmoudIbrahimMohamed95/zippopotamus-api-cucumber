package listeners;
import org.testng.IExecutionListener;
import readers.Log;
import readers.PropertyReader;
import reporter.AllureEnvironmentSetup;

public class CustomListeners implements  IExecutionListener {

    @Override
    public void onExecutionStart() {
        Log.info("Execution starts");
        PropertyReader.loadProperties();
        Log.info("Properties are loaded");
        AllureEnvironmentSetup.setAllureEnvironment();
        Log.info("Allure environment information are added");
    }

    @Override
    public void onExecutionFinish(){
        Log.info("Execution finish");
    }
}