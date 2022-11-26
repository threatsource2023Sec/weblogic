package weblogic.server.embed;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;

public interface Config {
   void setSystemCredentials(String var1, String var2);

   void updateEditDomain(Action var1) throws EmbeddedServerException;

   void updateConfigDomain(Action var1) throws EmbeddedServerException;

   public interface Action {
      void execute(DomainMBean var1, ServerMBean var2);
   }
}
