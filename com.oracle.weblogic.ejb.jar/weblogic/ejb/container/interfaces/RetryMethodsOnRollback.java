package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface RetryMethodsOnRollback {
   int getRetryCount();

   Collection getAllMethodDescriptors();
}
