package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.server.GlobalServiceLocator;

public abstract class ProvisioningBaseCommand implements CommandInterface {
   protected static final long serialVersionUID = 1L;
   protected static final Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugLifecycleManager");
   protected static final String LCM_TEMPLATE_RUNTIME_NAME = "TemplateRuntime";
   @SharedState
   protected transient String componentName;
   @SharedState
   protected transient String partitionName;
   @SharedState(
      name = "configurableAttributes"
   )
   protected Map configurableAttributes;
   private transient ServiceLocator serviceLocator;
   protected transient RuntimeManager runtimeManager;

   public ProvisioningBaseCommand() {
      this.initialize();
   }

   public abstract boolean execute() throws LifecycleException;

   protected static final Map convert(Map configurableAttributes) {
      String className = ProvisioningBaseCommand.class.getName();
      String methodName = "convert";
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "convert" + " ENTRY " + configurableAttributes);
      }

      HashMap returnValue;
      if (configurableAttributes == null) {
         returnValue = null;
      } else {
         returnValue = new HashMap();
         if (!configurableAttributes.isEmpty()) {
            Collection rootEntries = configurableAttributes.entrySet();
            if (rootEntries != null && !rootEntries.isEmpty()) {
               Iterator var5 = rootEntries.iterator();

               while(var5.hasNext()) {
                  Map.Entry rootEntry = (Map.Entry)var5.next();
                  if (rootEntry != null) {
                     String componentName = (String)rootEntry.getKey();
                     if (componentName != null && !componentName.isEmpty()) {
                        Map componentAttributes = (Map)rootEntry.getValue();
                        if (componentAttributes != null && !componentAttributes.isEmpty()) {
                           Map componentAttributePropertyValues = convertComponentSpecificConfigurableAttributes(componentAttributes);
                           if (componentAttributePropertyValues != null && !componentAttributePropertyValues.isEmpty()) {
                              returnValue.put(componentName, PropertyValueFactory.getPropertiesPropertyValue(componentAttributePropertyValues));
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "convert" + " RETURN " + returnValue);
      }

      return returnValue;
   }

   private static final Map convertComponentSpecificConfigurableAttributes(Map map) {
      String className = ProvisioningBaseCommand.class.getName();
      String methodName = "convertComponentSpecificConfigurableAttributes";
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "convertComponentSpecificConfigurableAttributes" + " ENTRY " + map);
      }

      HashMap returnValue;
      if (map == null) {
         returnValue = null;
      } else {
         returnValue = new HashMap();
         Collection entries = map.entrySet();
         if (entries != null && !entries.isEmpty()) {
            Iterator var5 = entries.iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               if (entry != null) {
                  String key = (String)entry.getKey();
                  if (key != null && !key.isEmpty()) {
                     String value = (String)entry.getValue();
                     if (value != null) {
                        returnValue.put(key, PropertyValueFactory.getStringPropertyValue(value));
                     }
                  }
               }
            }
         }
      }

      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "convertComponentSpecificConfigurableAttributes" + " RETURN " + returnValue);
      }

      return returnValue;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      if (in != null) {
         in.defaultReadObject();
      }

      this.initialize();
   }

   protected final void initialize() {
      this.serviceLocator = GlobalServiceLocator.getServiceLocator();
      Objects.requireNonNull(this.serviceLocator);
      this.runtimeManager = (RuntimeManager)this.serviceLocator.getService(RuntimeManager.class, new Annotation[0]);
      Objects.requireNonNull(this.runtimeManager);
   }
}
