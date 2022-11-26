package weblogic.servlet.httppubsub;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public interface PubSubHelper {
   Object getAuthSubject(HttpSession var1);

   Object getWeblogicWebAppBean(ServletContext var1);

   String getApplicationName(ServletContext var1);

   String getContextPath(ServletContext var1);

   String getSecurityModel(ServletContext var1);
}
