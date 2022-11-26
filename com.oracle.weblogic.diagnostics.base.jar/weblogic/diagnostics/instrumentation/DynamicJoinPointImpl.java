package weblogic.diagnostics.instrumentation;

import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;

public class DynamicJoinPointImpl implements DynamicJoinPoint {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private JoinPoint delegateJp;
   private String monitorType = null;
   private Object[] unrenderedArgs;
   private Object unrenderedRetVal;
   private boolean argumentsSensitive = false;

   public DynamicJoinPointImpl(JoinPoint jp, Object[] args, Object retVal) {
      this.delegateJp = jp;
      this.argumentsSensitive = InstrumentationSupport.isSensitiveArray(args);
      if (jp.getPointcutHandlingInfoMap() == null) {
         if (!this.argumentsSensitive) {
            this.unrenderedArgs = InstrumentationSupport.toSensitive(args);
            this.argumentsSensitive = true;
         }

         String desc = jp.getMethodDescriptor();
         if (desc != null && desc.endsWith(")V")) {
            this.unrenderedRetVal = null;
         } else {
            this.unrenderedRetVal = "*****";
         }
      } else {
         this.unrenderedArgs = args;
         this.unrenderedRetVal = retVal;
      }

   }

   public String getModuleName() {
      return this.delegateJp.getModuleName();
   }

   public String getSourceFile() {
      return this.delegateJp.getSourceFile();
   }

   public String getClassName() {
      return this.delegateJp.getClassName();
   }

   public String getMethodName() {
      return this.delegateJp.getMethodName();
   }

   public String getMethodDescriptor() {
      return this.delegateJp.getMethodDescriptor();
   }

   public int getLineNumber() {
      return this.delegateJp.getLineNumber();
   }

   public Object[] getArguments() {
      if (this.unrenderedArgs == null) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("DynamicJoinPointImpl.getArguments() for " + this.monitorType + " unrenderedArgs is null, return null");
         }

         return null;
      } else if (this.argumentsSensitive) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("DynamicJoinPointImpl.getArguments() for " + this.monitorType + " handling info map is null, return pre-processed sensitive args");
         }

         return this.unrenderedArgs;
      } else {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("DynamicJoinPointImpl.getArguments() for " + this.monitorType + " render args for return using info map");
         }

         Map infoMap = this.delegateJp.getPointcutHandlingInfoMap();
         return ValueRenderingManager.renderArgumentValues(this.monitorType, !this.delegateJp.isStatic(), this.unrenderedArgs, infoMap);
      }
   }

   public void setArguments(Object[] args) {
      this.argumentsSensitive = InstrumentationSupport.isSensitiveArray(args);
      if (!this.argumentsSensitive && this.delegateJp.getPointcutHandlingInfoMap() == null) {
         this.unrenderedArgs = InstrumentationSupport.toSensitive(args);
         this.argumentsSensitive = true;
      } else {
         this.unrenderedArgs = args;
      }

   }

   public Object getReturnValue() {
      if (this.unrenderedRetVal == null) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("DynamicJoinPointImpl.getReturnValue() for " + this.monitorType + " unrenderedRetVal is null, return null");
         }

         return null;
      } else {
         Map infoMap = this.delegateJp.getPointcutHandlingInfoMap();
         if (infoMap == null) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("DynamicJoinPointImpl.getReturnValue() for " + this.monitorType + " handling info map is null, return null");
            }

            return this.unrenderedRetVal;
         } else {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("DynamicJoinPointImpl.getReturnValue() for " + this.monitorType + " render return value and return");
            }

            return ValueRenderingManager.renderReturnValue(this.monitorType, this.unrenderedRetVal, infoMap);
         }
      }
   }

   public void setReturnValue(Object retVal) {
      if (this.delegateJp.getPointcutHandlingInfoMap() == null) {
         this.unrenderedRetVal = "*****";
      } else {
         this.unrenderedRetVal = retVal;
      }

   }

   public JoinPoint getDelegate() {
      return this.delegateJp;
   }

   public String toString() {
      return this.delegateJp.toString();
   }

   public GatheredArgument[] getGatheredArguments() {
      return this.delegateJp.getGatheredArguments(this.monitorType);
   }

   public boolean isReturnGathered() {
      return this.delegateJp.isReturnGathered(this.monitorType);
   }

   public GatheredArgument[] getGatheredArguments(String monType) {
      return this.delegateJp.getGatheredArguments(monType);
   }

   public boolean isReturnGathered(String monType) {
      return this.delegateJp.isReturnGathered(monType);
   }

   public Map getPointcutHandlingInfoMap() {
      return this.delegateJp.getPointcutHandlingInfoMap();
   }

   public boolean isStatic() {
      return this.delegateJp.isStatic();
   }

   public void setMonitorType(String monitorType) {
      if (debugLog.isDebugEnabled()) {
         debugLog.debug("DynamicJoinPointImpl.setMonitorType(" + monitorType + ")");
      }

      this.monitorType = monitorType;
   }

   public String getCallerClassName() {
      return this.delegateJp.getCallerClassName();
   }

   public String getCallerMethodName() {
      return this.delegateJp.getCallerMethodName();
   }

   public String getCallerMethodDescriptor() {
      return this.delegateJp.getCallerMethodDescriptor();
   }
}
