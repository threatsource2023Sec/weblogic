package weblogic.management.jmx;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import javax.management.ObjectName;
import weblogic.management.NoAccessRuntimeException;

public interface ObjectSecurityManager {
   boolean isAnonAccessAllowed(ObjectName var1, String var2, String var3);

   void isAccessAllowed(ObjectName var1, String var2, String var3, BeanDescriptor var4, PropertyDescriptor var5) throws NoAccessRuntimeException;
}
