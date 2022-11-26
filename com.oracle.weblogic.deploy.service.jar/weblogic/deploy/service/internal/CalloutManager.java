package weblogic.deploy.service.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;

@Contract
public interface CalloutManager {
   boolean abortForExpectedChangeCallout(String var1);

   boolean abortForChangeReceivedCallout(String var1, DomainMBean var2, DomainMBean var3);

   void init(DomainMBean var1);

   void setupCalloutsList(DomainMBean var1);

   boolean hasExpectedChangeCallouts();

   boolean hasChangeReceivedCallouts();
}
