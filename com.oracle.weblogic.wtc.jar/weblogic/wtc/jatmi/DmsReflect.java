package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

public class DmsReflect {
   private static DmsReflect instance = null;
   private Class ExecutionContext = null;
   private Method m_get = null;
   private Method m_getECID = null;
   private Method m_activate = null;
   private Method m_buildExecutionContextComponents = null;
   private Object ec = null;
   private Object factory = null;

   private DmsReflect() throws Exception {
      try {
         this.ExecutionContext = Class.forName("oracle.dms.context.ExecutionContext");
         Class DMSContextManager = Class.forName("oracle.dms.context.DMSContextManager");
         Class ContextComponentsFactory = Class.forName("oracle.dms.context.ContextComponentsFactory");
         Class ExecutionContextComponents = Class.forName("oracle.dms.context.ExecutionContextComponents");
         this.m_get = this.ExecutionContext.getMethod("get");
         this.m_getECID = this.ExecutionContext.getMethod("getECID");
         this.m_activate = this.ExecutionContext.getMethod("activate", ExecutionContextComponents);
         Method m_getContextComponentsFactory = DMSContextManager.getMethod("getContextComponentsFactory");
         this.m_buildExecutionContextComponents = ContextComponentsFactory.getMethod("buildExecutionContextComponents", String.class, String.class, Map.class, Map.class, Map.class, Level.class);
         this.factory = m_getContextComponentsFactory.invoke(DMSContextManager);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw var5;
      }
   }

   public static DmsReflect getInstance() throws Exception {
      if (instance == null) {
         instance = new DmsReflect();
      }

      return instance;
   }

   public String getECID() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DmsReflect/getECID/");
      }

      if (this.m_get != null && this.m_getECID != null) {
         Object ecid = null;

         try {
            this.ec = this.m_get.invoke(this.ExecutionContext);
            ecid = this.m_getECID.invoke(this.ec);
         } catch (Exception var4) {
            if (traceEnabled) {
               ntrace.doTrace("]/DmsReflect/getECID/call get method failed. " + var4.getStackTrace());
            }

            return null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/DmsReflect/getECID/ECID = " + ecid);
         }

         return (String)ecid;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/DmsReflect/getECID/get method is null, return");
         }

         return null;
      }
   }

   public void setECID(String ecid) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DmsReflect/setECID/ECID = " + ecid);
      }

      if (this.factory != null && this.ExecutionContext != null && this.m_buildExecutionContextComponents != null && this.m_activate != null) {
         try {
            Object components = this.m_buildExecutionContextComponents.invoke(this.factory, ecid, null, null, null, null, null);
            this.m_activate.invoke(this.ExecutionContext, components);
         } catch (Exception var4) {
            if (traceEnabled) {
               ntrace.doTrace("]/DmsReflect/setECID/call activate method failed. " + var4.getStackTrace());
            }

            return;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/DmsReflect/setECID/success");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/DmsReflect/setECID/no factory, return directly]");
         }

      }
   }
}
