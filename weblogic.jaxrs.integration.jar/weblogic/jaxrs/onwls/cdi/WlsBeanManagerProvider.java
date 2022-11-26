package weblogic.jaxrs.onwls.cdi;

import javax.annotation.Priority;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.jersey.ext.cdi1x.internal.spi.BeanManagerProvider;
import weblogic.diagnostics.debug.DebugLogger;

@Priority(5001)
public class WlsBeanManagerProvider implements BeanManagerProvider {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugRestJersey2Integration");

   public BeanManager getBeanManager() {
      InitialContext initialContext = null;

      Object var3;
      try {
         initialContext = new InitialContext();
         BeanManager var2 = (BeanManager)initialContext.lookup("java:comp/BeanManager");
         return var2;
      } catch (Exception var13) {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Failed to obtain BeanManager from JNDI lookup.", var13);
         }

         var3 = null;
      } finally {
         if (initialContext != null) {
            try {
               initialContext.close();
            } catch (NamingException var12) {
            }
         }

      }

      return (BeanManager)var3;
   }
}
