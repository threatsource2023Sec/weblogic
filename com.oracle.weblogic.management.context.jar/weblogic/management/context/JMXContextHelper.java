package weblogic.management.context;

import java.io.IOException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.SerializableWorkContext;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public class JMXContextHelper {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugJMXContext");
   private static final JMXContextAccess DEFAULT_JMXCONTEXTACCESS = new DefaultJMXContextAccessImpl();
   private static JMXContextAccess SINGLETON;

   public static JMXContext getJMXContext(boolean create) {
      return SINGLETON.getJMXContext(create);
   }

   public static void putJMXContext(JMXContext jmxContext) {
      SINGLETON.putJMXContext(jmxContext);
   }

   public static void removeJMXContext() {
      SINGLETON.removeJMXContext();
   }

   public static synchronized void setJMXContextAccess(JMXContextAccess jmxContextAccess) {
      if (KernelStatus.isServer() && SINGLETON == DEFAULT_JMXCONTEXTACCESS && jmxContextAccess != null) {
         SINGLETON = jmxContextAccess;
      }
   }

   static {
      SINGLETON = DEFAULT_JMXCONTEXTACCESS;
   }

   private static class DefaultJMXContextAccessImpl implements JMXContextAccess {
      private DefaultJMXContextAccessImpl() {
      }

      public JMXContext getJMXContext(boolean create) {
         WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
         SerializableWorkContext workContext = (SerializableWorkContext)map.get("weblogic.management.JMXContext");
         JMXContext jmxContext = null;
         if (workContext != null) {
            jmxContext = (JMXContext)workContext.get();
         }

         if (create && jmxContext == null) {
            jmxContext = new JMXContextImpl();
         }

         return (JMXContext)jmxContext;
      }

      public void putJMXContext(JMXContext jmxContext) {
         WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();

         try {
            map.put("weblogic.management.JMXContext", PrimitiveContextFactory.create(jmxContext));
         } catch (PropertyReadOnlyException var4) {
            if (JMXContextHelper.DEBUG_LOGGER.isDebugEnabled()) {
               JMXContextHelper.DEBUG_LOGGER.debug("DefaultJMXContextAccessImpl.getJMXContext(): WorkContext property is read-only: " + var4.getStackTrace());
            }
         } catch (IOException var5) {
            if (JMXContextHelper.DEBUG_LOGGER.isDebugEnabled()) {
               JMXContextHelper.DEBUG_LOGGER.debug("DefaultJMXContextAccessImpl.getJMXContext(): IOException: " + var5.getStackTrace());
            }
         }

      }

      public void removeJMXContext() {
         try {
            WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
            WorkContext workContext = map.get("weblogic.management.JMXContext");
            if (workContext != null) {
               map.remove("weblogic.management.JMXContext");
            }
         } catch (NoWorkContextException var3) {
            if (JMXContextHelper.DEBUG_LOGGER.isDebugEnabled()) {
               JMXContextHelper.DEBUG_LOGGER.debug("DefaultJMXContextAccessImpl.removeJMXContext(): No WorkContext is available: " + var3.getMessage());
            }
         } catch (PropertyReadOnlyException var4) {
            if (JMXContextHelper.DEBUG_LOGGER.isDebugEnabled()) {
               JMXContextHelper.DEBUG_LOGGER.debug("DefaultJMXContextAccessImpl.removeJMXContext(): WorkContext property is read-only: " + var4.getMessage());
            }
         }

      }

      // $FF: synthetic method
      DefaultJMXContextAccessImpl(Object x0) {
         this();
      }
   }

   public interface JMXContextAccess {
      JMXContext getJMXContext(boolean var1);

      void putJMXContext(JMXContext var1);

      void removeJMXContext();
   }
}
