package net.dms.fsync.settings.entities;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.business.SettingsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 18/04/2017.
 */
public class EverisConfig {
    private static EverisConfig INSTANCE;
    private Properties properties;
    private Properties overriderProperpeties;
    private Map<String, String> mapResponsableJiraEveris = new HashMap<>();
    private Map<String, String> jiraFilters = new HashMap<>();
    private static String WORKSPACE_PATH = "./";

    public static EverisConfig getInstance(){
        return getInstance(WORKSPACE_PATH);
    }

    public static EverisConfig getInstance(String workspacePath){
        if(INSTANCE == null){

            /*  TODO validate is not executed with 2 differents paths
            if (workspacePath != null && !workspacePath.equals(WORKSPACE_PATH)) {
                throw new AppException("The application is already initialized on workspace ");
            }
            */
            WORKSPACE_PATH = workspacePath;
            INSTANCE = new EverisConfig();
        }
        return INSTANCE;
    }

    private EverisConfig() {
        properties = new Properties();
        overriderProperpeties = new  Properties();
        try {

            properties.load(Thread.class.getResourceAsStream("/bmw/rsp/everis.conf"));
            //overriderProperpeties.load(Thread.class.getResourceAsStream("/bmw/rsp/everis_overriden.conf"));
            overriderProperpeties.load(new FileInputStream(WORKSPACE_PATH + "popeye-conf/configuration.properties"));

            overriderProperpeties.entrySet().stream().forEach( p ->
                properties.put(p.getKey(), p.getValue())
            );

            Set<String> responseblesKeys = properties.stringPropertyNames().stream().filter(k -> k.startsWith(EverisPropertiesType.RESPONSABLE_JIRA.getProperty())).collect(Collectors.toSet());
            for(String key : responseblesKeys) {
                mapResponsableJiraEveris.put(properties.getProperty(key), key.replace(EverisPropertiesType.RESPONSABLE_JIRA.getProperty()+ ".", ""));
            }

            Set<String> jiraFiltrosKeys = properties.stringPropertyNames().stream().filter(k -> k.startsWith(EverisPropertiesType.JIRA_FILTROS.getProperty())).collect(Collectors.toSet());
            for(String key : jiraFiltrosKeys) {
                jiraFilters.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    public String getProperty(EverisPropertiesType key){
        return properties.getProperty(key.getProperty());
    }

    public Map<String, String> getMapResponsableJiraEveris() {
        return mapResponsableJiraEveris;
    }

    public String searchEverisEmployIdByJiraId(String jiraId){
        return mapResponsableJiraEveris.get(jiraId);
    }

    public Map<String, String> getJiraFilters(){
        return jiraFilters;
    }
}
