package weblogic.wtc.gwt;

import com.bea.core.jatmi.internal.ConfigHelper;
import java.io.Serializable;
import weblogic.wtc.jatmi.TPException;

public class TuxedoConnectionFactory implements Serializable {
   public static final String JNDI_NAME = "tuxedo.services.TuxedoConnection";
   private static boolean initialized = false;
   private static final long serialVersionUID = -7303521801232422151L;

   public TuxedoConnection getTuxedoConnection() throws TPException {
      if (!initialized) {
         if (ConfigHelper.getTuxedoServices() == null) {
            throw new TPException(12, "ERROR: getConnection can only run on a node where WTC has been configured");
         }

         initialized = true;
      }

      return new TuxedoConnectionImpl();
   }
}
