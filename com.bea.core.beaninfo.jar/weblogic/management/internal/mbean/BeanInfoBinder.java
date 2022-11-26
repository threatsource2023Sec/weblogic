package weblogic.management.internal.mbean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;

public interface BeanInfoBinder {
   BeanInfo getBeanInfo(boolean var1, String var2) throws IntrospectionException;
}
