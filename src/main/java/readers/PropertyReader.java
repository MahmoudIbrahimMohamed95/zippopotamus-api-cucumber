package readers;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    private static final String PROPERTIES_PATH="src/main/resources";

    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFiles;
            propertiesFiles = FileUtils.listFiles(new File(PROPERTIES_PATH),
                    new String[]{"properties"}, true);
            propertiesFiles.forEach(file->
            {
                try {
                    properties.load(FileUtils.openInputStream(file));
                 } catch (Exception e) {
                    Log.error("Exception in load properties " + e.getMessage());
                }
            });
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);
            Log.info("Properties loaded Successfully");
            return properties;
        } catch (Exception e) {
            Log.error("Exception in load properties " + e.getMessage());
            return null;
        }
    }

    public static String getProperty(String key){
        try {
            return System.getProperty(key);
        }catch (Exception e){
         Log.error("Exception in get property "+e.getMessage());
            return null;
        }
    }
}