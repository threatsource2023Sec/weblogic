package weblogic.nodemanager.rest.async;

import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.rest.utils.CommonUtils;

public abstract class Task {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   public static final String MANDATORY_ARGUMENTS_NULL;
   public final String description;
   public final String domainName;
   public final CommonUtils.ComponentType componentType;
   public final String componentName;
   public final CommonUtils.OperationType operationType;

   public Task(String desc, String domainName, CommonUtils.ComponentType compType, String compName, CommonUtils.OperationType opType) {
      if (desc != null && domainName != null && compType != null && compName != null && opType != null) {
         this.description = desc;
         this.domainName = domainName;
         this.componentType = compType;
         this.componentName = compName;
         this.operationType = opType;
      } else {
         throw new IllegalArgumentException(MANDATORY_ARGUMENTS_NULL);
      }
   }

   public String getDescription() {
      return this.description;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public CommonUtils.ComponentType getComponentType() {
      return this.componentType;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public CommonUtils.OperationType getOperationType() {
      return this.operationType;
   }

   public abstract Object execute();

   static {
      MANDATORY_ARGUMENTS_NULL = nmRestText.msgMandatoryArgumentsNull();
   }
}
