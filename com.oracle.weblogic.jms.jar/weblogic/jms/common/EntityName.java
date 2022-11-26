package weblogic.jms.common;

import weblogic.jms.module.JMSBeanHelper;

public class EntityName extends ModuleName {
   private String entityName;
   private String fullyQualifiedEntityName;

   public EntityName(String paramApplicationName, String paramModuleName, String paramEntityName) {
      super(paramApplicationName, paramModuleName);
      this.entityName = paramEntityName;
      this.fullyQualifiedEntityName = JMSBeanHelper.getDecoratedName(this.getFullyQualifiedModuleName(), this.entityName);
   }

   public EntityName(ModuleName moduleName, String paramEntityName) {
      this(moduleName.getApplicationName(), moduleName.getEARModuleName(), paramEntityName);
   }

   public String getEntityName() {
      return this.entityName;
   }

   public String getFullyQualifiedEntityName() {
      return this.fullyQualifiedEntityName;
   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof EntityName) {
         EntityName mn = (EntityName)compareMe;
         return mn.fullyQualifiedEntityName.equals(this.fullyQualifiedEntityName);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.fullyQualifiedEntityName.hashCode();
   }

   public String toString() {
      return this.fullyQualifiedEntityName;
   }
}
