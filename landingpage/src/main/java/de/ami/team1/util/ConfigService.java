package de.ami.team1.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.lang.reflect.Field;

/**
 * a service that handles application properties.
 * all properties can be overwritten by an environment variable with the same name.
 */
@Startup
@Singleton
@Named
public class ConfigService {

    // filed name -> env variable name, field value -> the default value that ist used if no env variable can be found
    private String DATA_GENERATOR_URL = "http://localhost:8080";
    private String ANALYSE_URL = "http://localhost:8081";
    private String PREPROCESSING_URL = "http://localhost:8083";
    private String DATA_ACCEPTANCE_URL = "http://localhost:8082";
    private String TREND_URL = "http://localhost:8086/trend";
    private String VISUALIZATION_URL = "http://localhost:8085/visualization";


    @PostConstruct
    public void init() {
        for (Field f : getClass().getDeclaredFields()) {
            String env = System.getenv(f.getName());
            f.setAccessible(true);
            try {
                if (env == null) {
                    System.out.println("No Env-Variable " + f.getName() + " found, using default value: " + f.get(this));
                } else {
                    f.set(this, env);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the url of the analyse microservice, default value: http://localhost:8081/
     */
    public String getANALYSE_URL() {
        return ANALYSE_URL;
    }

    /**
     * @return the url of the jsf page trend, default value: http://localhost:8086/
     */
    public String getTREND_URL() {
        return TREND_URL;
    }

    /**
     * @return the url of the jsf page visualization, default value: http://localhost:8085/
     */
    public String getVISUALIZATION_URL() {
        return VISUALIZATION_URL;
    }

    /**
     * @return the url of the data generator, default value: http://localhost:8080
     */
    public String getDATA_GENERATOR_URL() {
        return DATA_GENERATOR_URL;
    }

    /**
     * @return the url of the preprocessing, default value: http://localhost:8083
     */
    public String getPREPROCESSING_URL() {
        return PREPROCESSING_URL;
    }

    /**
     * @return the url of the data acceptance, default value: http://localhost:8082
     */
    public String getDATA_ACCEPTANCE_URL() {
        return DATA_ACCEPTANCE_URL;
    }
}
