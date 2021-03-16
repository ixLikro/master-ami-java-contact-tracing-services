package de.ami.team1.util;

import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.lang.reflect.Field;

/**
 * Provider for Environment Variables
 */

@Startup
@Singleton
public class EnvProvider {

    // field name -> env variable name, field value -> the default value that ist used if no env variable can be found
    private String ANALYSE_URL = "http://localhost:8081";
    private String DATA_ACCEPTANCE_URL = "http://localhost:8082";
    private String PREPROCESSING_URL = "http://localhost:8083";


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
     * @return the url of the analysis part, default value = http://localhost:8081
     */
    public String getANALYSE_URL() {
        return ANALYSE_URL;
    }

    /**
     * @return the url of the data acceptance part, default value = http://localhost:8082
     */
    public String getDATA_ACCEPTANCE_URL() {
        return DATA_ACCEPTANCE_URL;
    }

    /**
     * @return the url of the PREPROCESSING part, default value = http://localhost:8083
     */
    public String getPREPROCESSING_URL() {
        return PREPROCESSING_URL;
    }
}
