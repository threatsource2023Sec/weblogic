package weblogic.deploy.service.internal.adminserver;

import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.service.internal.CalloutManager;
import weblogic.management.configuration.DomainMBean;

@Service
public class AdminCalloutManager implements CalloutManager {
   public boolean abortForChangeReceivedCallout(String location, DomainMBean active, DomainMBean pending) {
      return false;
   }

   public boolean abortForExpectedChangeCallout(String location) {
      return false;
   }

   public void init(DomainMBean domainMBean) {
   }

   public void setupCalloutsList(DomainMBean domainMBean) {
   }

   public boolean hasExpectedChangeCallouts() {
      return false;
   }

   public boolean hasChangeReceivedCallouts() {
      return false;
   }
}
