package weblogic.diagnostics.instrumentation.engine;

import java.io.Serializable;
import java.util.Map;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InvalidMonitorException;
import weblogic.diagnostics.instrumentation.engine.base.MonitorSpecificationBase;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitor;

public class MonitorSpecification extends MonitorSpecificationBase implements Serializable {
   static final long serialVersionUID = 8553011116555554668L;

   public MonitorSpecification(String type, int location, PointcutExpression pointcutExpr, boolean captureArgs, boolean captureRetVal) {
      super(type, location, pointcutExpr, captureArgs, captureRetVal);
   }

   public MonitorSpecification(InstrumentationEngineConfiguration engineConf, WlsMonitor monitorElement) throws InvalidMonitorException {
      this.type = monitorElement.getType();
      if (this.type == null) {
         throw new InvalidMonitorException("Required attribute type not defined for monitor specification");
      } else if (!isValidType(this.type)) {
         DiagnosticsLogger.logInvalidCharactersInMonitorType("InstrumentationEngineConfiguration", this.type);
         throw new InvalidMonitorException("Invalid character(s) found in the type attribute of a monitor: " + this.type);
      } else {
         String val = monitorElement.getScope();
         if (val == null) {
            throw new InvalidMonitorException("Required attribute scope not defined for monitor specification " + this.type);
         } else {
            if (val.equals("server")) {
               this.serverScoped = true;
            } else if (val.equals("application")) {
               this.applicationScoped = true;
            } else if (val.equals("all")) {
               this.serverScoped = true;
               this.applicationScoped = true;
            }

            val = monitorElement.getLocation();
            if (val == null) {
               throw new InvalidMonitorException("Required attribute location not defined for monitor specification " + this.type);
            } else {
               if (val.equals("before")) {
                  this.location = 1;
               } else if (val.equals("after")) {
                  this.location = 2;
               } else {
                  if (!val.equals("around")) {
                     throw new InvalidMonitorException("Invalid value " + val + " for attribute location for monitor specification " + this.type);
                  }

                  this.location = 3;
               }

               this.codeGenClassName = monitorElement.getCodeGenerator();
               if (this.codeGenClassName != null && this.codeGenClassName.trim().length() > 0) {
                  this.isStandard = true;
               } else {
                  this.isDelegating = true;
               }

               this.attributeNames = this.getStringList(monitorElement.getAttributeNames(), ",");
               this.actionGroupName = monitorElement.getActionGroup();
               if (this.actionGroupName != null) {
                  this.actionTypeNames = engineConf.getGroupActionTypes(this.actionGroupName);
                  if ((this.actionTypeNames == null || this.actionTypeNames.length == 0) && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_LOGGER.debug("Monitor " + this.type + " is using empty or non-existent action group " + this.actionGroupName);
                  }
               }

               this.captureArgs = monitorElement.getCaptureArgs();
               this.captureRetVal = monitorElement.getCaptureReturn();
               String pointcutName = monitorElement.getPointcut();
               if (pointcutName == null) {
                  throw new InvalidMonitorException("Monitor " + this.type + " does not use any pointcut");
               } else {
                  Map pointcutMap = engineConf.getPointcuts();
                  this.pointcutExpr = (PointcutExpression)pointcutMap.get(pointcutName);
                  if (this.pointcutExpr == null) {
                     throw new InvalidMonitorException("Monitor " + this.type + " uses Non-existent pointcut " + pointcutName);
                  } else {
                     this.serverManaged = monitorElement.getServerManaged();
                     if (this.serverManaged && !this.serverScoped) {
                        throw new InvalidMonitorException("Monitor " + this.type + " is marked as server managed but is not server scope");
                     } else {
                        this.diagnosticVolume = monitorElement.getDiagnosticVolume();
                        this.eventClassName = monitorElement.getEventClassName();
                     }
                  }
               }
            }
         }
      }
   }

   String getActionGroupName() {
      return this.actionGroupName;
   }

   void setActionTypes(String[] actionTypeNames) {
      this.actionTypeNames = actionTypeNames;
   }

   void setType(String type) {
      this.type = type;
   }
}
