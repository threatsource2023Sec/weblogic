package weblogic.management.runtime;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidParameterException;

public interface SingleSignOnServicesRuntimeMBean extends RuntimeMBean {
   void publish(String var1) throws InvalidParameterException;

   void publish(String var1, boolean var2) throws InvalidParameterException, CreateException, AlreadyExistsException;
}
