package weblogic.apache.org.apache.velocity.runtime.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;

public class LogManager {
   public static LogSystem createLogSystem(RuntimeServices rsvc) throws Exception {
      Object o = rsvc.getProperty("runtime.log.logsystem");
      if (o != null && o instanceof LogSystem) {
         ((LogSystem)o).init(rsvc);
         return (LogSystem)o;
      } else {
         List classes = null;
         Object obj = rsvc.getProperty("runtime.log.logsystem.class");
         if (obj instanceof List) {
            classes = (List)obj;
         } else if (obj instanceof String) {
            classes = new ArrayList();
            ((List)classes).add(obj);
         }

         Iterator ii = ((List)classes).iterator();

         String claz;
         while(ii.hasNext()) {
            claz = (String)ii.next();
            if (claz != null && claz.length() > 0) {
               rsvc.info("Trying to use logger class " + claz);

               try {
                  o = Class.forName(claz).newInstance();
                  if (o instanceof LogSystem) {
                     ((LogSystem)o).init(rsvc);
                     rsvc.info("Using logger class " + claz);
                     return (LogSystem)o;
                  }

                  rsvc.error("The specifid logger class " + claz + " isn't a valid LogSystem");
               } catch (NoClassDefFoundError var9) {
                  rsvc.debug("Couldn't find class " + claz + " or necessary supporting classes in " + "classpath. Exception : " + var9);
               }
            }
         }

         claz = null;

         AvalonLogSystem als;
         try {
            als = new AvalonLogSystem();
            als.init(rsvc);
         } catch (NoClassDefFoundError var8) {
            String errstr = "PANIC : Velocity cannot find any of the specified or default logging systems in the classpath, or the classpath doesn't contain the necessary classes to support them. Please consult the documentation regarding logging. Exception : " + var8;
            System.out.println(errstr);
            System.err.println(errstr);
            throw var8;
         }

         rsvc.info("Using AvalonLogSystem as logger of final resort.");
         return als;
      }
   }
}
