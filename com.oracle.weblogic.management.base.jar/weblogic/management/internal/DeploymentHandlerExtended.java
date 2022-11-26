package weblogic.management.internal;

import java.util.Collection;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateRejectedException;

public interface DeploymentHandlerExtended {
   void prepareDeployments(Collection var1, boolean var2) throws BeanUpdateRejectedException;

   void activateDeployments() throws BeanUpdateFailedException;

   void rollbackDeployments();

   void destroyDeployments(Collection var1);
}
