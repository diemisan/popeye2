package net.dms.fsync.settings.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.Settings;

import java.io.FileInputStream;


/**
 * Created by dminanos on 19/11/2017.
 */
public class SettingsService {

    private static Settings settings;
    private static SettingsService INSTANCE;
    private static String WORKSPACE_PATH = "./";

    private SettingsService(){
        loadSettings();
    }

    public static SettingsService getInstance(){
        return getInstance(WORKSPACE_PATH);
    }

    public static SettingsService getInstance(String workspacePath){
        if(INSTANCE == null){

            /*  TODO validate is not executed with 2 differents paths
            if (workspacePath != null && !workspacePath.equals(WORKSPACE_PATH)) {
                throw new AppException("The application is already initialized on workspace ");
            }
            */

            WORKSPACE_PATH = workspacePath;
            INSTANCE = new SettingsService();
        }
        return INSTANCE;
    }

    private void loadSettings(){
        ObjectMapper mapper = new ObjectMapper();
        try {

            //settings = mapper.readValue(this.getClass().getResourceAsStream("/bmw/rsp/everis.json"),  Settings.class);
            settings = mapper.readValue(new FileInputStream(WORKSPACE_PATH + "popeye-conf/everis.json"),  Settings.class);
        } catch (Exception e) {
            throw new AppException(e);
        }

    }

    public Settings getSettings() {
        return settings;
    }
}
