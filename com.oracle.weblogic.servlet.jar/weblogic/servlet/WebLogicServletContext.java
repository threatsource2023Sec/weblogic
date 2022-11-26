package weblogic.servlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.naming.Context;
import javax.servlet.ServletContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.management.DeploymentException;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;

public interface WebLogicServletContext extends ServletContext {
   String getApplicationId();

   boolean webflowCheckAccess(String var1, ServletRequestImpl var2, ServletResponseImpl var3);

   String getSecurityRealmName();

   Context getEnvironmentContext();

   URL[] getResources(String var1) throws MalformedURLException;

   /** @deprecated */
   @Deprecated
   void registerListener(String var1) throws DeploymentException;

   boolean isListenerRegistered(String var1);

   void registerFilter(String var1, String var2, String[] var3, String[] var4, Map var5) throws DeploymentException;

   void registerFilter(String var1, String var2, String[] var3, String[] var4, Map var5, String[] var6) throws DeploymentException;

   boolean isFilterRegistered(String var1);

   void registerServlet(String var1, String var2, String[] var3, Map var4, int var5) throws DeploymentException;

   boolean isServletRegistered(String var1);

   void bindResourceRef(String var1, String var2, String var3, String var4, boolean var5, String var6) throws DeploymentException;

   void bindEjbRef(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws DeploymentException;

   void bindEjbLocalRef(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws DeploymentException;

   boolean isResourceBound(String var1);

   void setJspParam(String var1, String var2) throws DeploymentException;

   void addAsyncInitServlet(AsyncInitServlet var1);

   void addMimeMapping(String var1, String var2);

   String getClasspath();

   ApplicationContextInternal getApplicationContext();

   String getModuleId();
}
