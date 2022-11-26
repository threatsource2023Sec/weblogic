package weblogic.ejb.container.deployer;

import java.util.Collection;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.internal.MDConnectionManager;

public interface MDBService {
   boolean register(Collection var1);

   void addDeployed(MDConnectionManager var1, MessageDrivenBeanInfo var2);

   void removeDeployed(MDConnectionManager var1);
}
