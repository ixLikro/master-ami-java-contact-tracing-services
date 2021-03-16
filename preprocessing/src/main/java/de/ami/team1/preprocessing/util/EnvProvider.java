package de.ami.team1.preprocessing.util;

import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.lang.reflect.Field;

/**
 * a service that handles application properties.
 * all properties can be overwritten by an environment variable with the same name.
 */
@Startup
@Singleton
public class EnvProvider {

    // filed name -> env variable name, field value -> the default value that ist used if no env variable can be found
    private String CONTACT_TRACKING_URL = "http://localhost:8087/";


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

    public String getCONTACT_TRACKING_URL() {
        return CONTACT_TRACKING_URL;
    }
}
