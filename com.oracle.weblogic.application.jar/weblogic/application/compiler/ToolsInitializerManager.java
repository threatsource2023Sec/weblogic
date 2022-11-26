package weblogic.application.compiler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import weblogic.application.utils.EarUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.GlobalServiceLocator;

public class ToolsInitializerManager {
   private static boolean initialized = false;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public static synchronized void init() {
      if (debugLogger.isDebugEnabled()) {
         printDebugMsg("Initiating");
      }

      if (!initialized) {
         initialized = true;

         ToolsInitializer initializer;
         for(Iterator initializers = getInitializers(); initializers.hasNext(); initializer.init()) {
            initializer = (ToolsInitializer)initializers.next();
            if (debugLogger.isDebugEnabled()) {
               printDebugMsg("Initializing " + initializer.getClass().getName());
            }
         }
      }

   }

   private static Iterator getInitializers() {
      ArrayList initializers = new ArrayList();

      ToolsInitializer initializer;
      for(Iterator listFromServices = ServiceLoader.load(ToolsInitializer.class).iterator(); listFromServices.hasNext(); initializers.add(initializer)) {
         initializer = (ToolsInitializer)listFromServices.next();
         if (debugLogger.isDebugEnabled()) {
            printDebugMsg("Found " + initializer.getClass().getName() + " using ServiceLoader");
         }
      }

      List moreInitalizers = GlobalServiceLocator.getServiceLocator().getAllServices(ToolsInitializer.class, new Annotation[0]);

      ToolsInitializer initializer;
      for(Iterator var3 = moreInitalizers.iterator(); var3.hasNext(); initializers.add(initializer)) {
         initializer = (ToolsInitializer)var3.next();
         if (debugLogger.isDebugEnabled()) {
            printDebugMsg("Found " + initializer.getClass().getName() + " using GlobalServiceLocator");
         }
      }

      try {
         initializers.add((ToolsInitializer)Class.forName("weblogic.wsee.tools.WSEEToolsInitializer").newInstance());
      } catch (Exception var5) {
      }

      return initializers.iterator();
   }

   private static void printDebugMsg(String msg) {
      debugLogger.debug(EarUtils.addClassName(msg));
   }
}
