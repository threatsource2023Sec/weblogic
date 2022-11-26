package weblogic.security.service;

import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.Manager;
import com.bea.core.security.managers.NotInitializedException;
import com.bea.core.security.managers.NotSupportedException;
import weblogic.management.security.ProviderMBean;
import weblogic.security.spi.AuditEvent;

public class Auditor implements SecurityService {
   public Auditor() {
   }

   public Auditor(String realmName, ProviderMBean[] configuration) {
      throw new NotSupportedException();
   }

   public void initialize(String realmName) throws InvalidParameterException {
      throw new NotSupportedException();
   }

   public void shutdown() {
      throw new NotSupportedException();
   }

   public void start() {
      throw new NotSupportedException();
   }

   public void suspend() {
      throw new NotSupportedException();
   }

   public void writeEvent(AuditEvent event) throws NotYetInitializedException {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var4) {
         throw new NotYetInitializedException(var4);
      }

      manager.writeEvent(event);
   }
}
