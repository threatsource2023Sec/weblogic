package weblogic.management.runtime;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public interface ServletRuntimeMBean extends RuntimeMBean {
   String getServletName();

   String getServletClassName();

   int getReloadTotalCount();

   int getInvocationTotalCount();

   int getPoolMaxCapacity();

   long getExecutionTimeTotal();

   int getExecutionTimeHigh();

   int getExecutionTimeLow();

   int getExecutionTimeAverage();

   String getServletPath();

   String getContextPath();

   String getURL();

   boolean isInternalServlet();

   String[] getURLPatterns();

   Serializable[] invokeAnnotatedMethods(Class var1, Serializable... var2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
