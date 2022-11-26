package weblogic.servlet;

import java.util.EventListener;
import javax.servlet.ServletContextEvent;

public interface WebLogicServletContextListener extends EventListener {
   void contextPrepared(ServletContextEvent var1);
}
