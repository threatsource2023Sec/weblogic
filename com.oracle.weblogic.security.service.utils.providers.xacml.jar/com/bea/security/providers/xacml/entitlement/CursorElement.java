package com.bea.security.providers.xacml.entitlement;

public class CursorElement {
   private String resourceId;
   private String roleName;
   private String expression;
   private String auxiliary;
   private boolean isDeployment;
   private String collectionName;

   public CursorElement(String resourceId, String roleName, String expression, String auxiliary, boolean isDeployment, String collectionName) {
      this.resourceId = resourceId;
      this.roleName = roleName;
      this.expression = expression;
      this.auxiliary = auxiliary;
      this.isDeployment = isDeployment;
      this.collectionName = collectionName;
   }

   public String getResourceId() {
      return this.resourceId;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public String getExpression() {
      return this.expression;
   }

   public String getAuxiliary() {
      return this.auxiliary;
   }

   public boolean isDeployment() {
      return this.isDeployment;
   }

   public String getCollectionName() {
      return this.collectionName;
   }
}
