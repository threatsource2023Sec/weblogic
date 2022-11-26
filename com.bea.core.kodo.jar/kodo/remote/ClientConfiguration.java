package kodo.remote;

import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface ClientConfiguration extends OpenJPAConfiguration {
   KodoCommandIO getCommandIO();
}
