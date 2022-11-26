package weblogic.wtc.gwt;

import java.io.Serializable;
import weblogic.wtc.jatmi.TPException;

public interface TuxedoCorbaConnectionFactory extends Serializable {
   String JNDI_NAME = "tuxedo.services.TuxedoCorbaConnection";

   TuxedoCorbaConnection getTuxedoCorbaConnection() throws TPException;
}
