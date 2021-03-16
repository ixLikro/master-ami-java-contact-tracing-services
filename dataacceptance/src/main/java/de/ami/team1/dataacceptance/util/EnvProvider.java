package de.ami.team1.dataacceptance.util;

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

    // filed name -> env variable name, field value -> the default value that ist used if no env variable can be found
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
     * @return the url of the preprocessing part of, default value = http://localhost:8083
     */
    public String getPREPROCESSING_URL() {
        return PREPROCESSING_URL;
    }
}
