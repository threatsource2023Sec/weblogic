package weblogic.servlet.httppubsub;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.session.SessionData;

public class WlsPubSubHelper implements PubSubHelper {
   public Object getAuthSubject(HttpSession session) {
      SessionData data = (SessionData)session;
      return data == null ? null : data.getInternalAttribute("weblogic.authuser");
   }

   public Object getWeblogicWebAppBean(ServletContext context) {
      WebAppServletContext w = (WebAppServletContext)context;
      return w.getWebAppModule().getWlWebAppBean();
   }

   public String getApplicationName(ServletContext context) {
      WebAppServletContext w = (WebAppServletContext)context;
      return w.getApplicationId();
   }

   public String getContextPath(ServletContext context) {
      WebAppServletContext w = (WebAppServletContext)context;
      return w.getContextPath();
   }

   public String getSecurityModel(ServletContext context) {
      WebAppServletContext w = (WebAppServletContext)context;
      if (w.getApplicationContext() != null) {
         AppDeploymentMBean mbean = w.getApplicationContext().getAppDeploymentMBean();
         if (mbean != null) {
            return mbean.getSecurityDDModel();
         }
      }

      return "DDOnly";
   }
}
