package weblogic.entitlement.data;

import weblogic.entitlement.expression.EAuxiliary;
import weblogic.entitlement.expression.EExpression;

public class ERole extends BaseResource {
   private ERoleId mId;

   public ERole(ERoleId roleId, EExpression expr) {
      this(roleId, expr, false);
   }

   public ERole(ERoleId roleId, EExpression expr, boolean deployData) {
      super(expr, deployData);
      if (roleId == null) {
         throw new NullPointerException("null role primary key");
      } else {
         this.mId = roleId;
      }
   }

   public ERole(String resourceName, String roleName, EExpression expr) {
      this(new ERoleId(resourceName, roleName), expr, false);
   }

   public ERole(String resourceName, String roleName, EExpression expr, boolean deployData) {
      this(new ERoleId(resourceName, roleName), expr, deployData);
   }

   public ERole(ERoleId roleId, EExpression expr, EAuxiliary aux) {
      this(roleId, expr, aux, false);
   }

   public ERole(ERoleId roleId, EExpression expr, EAuxiliary aux, boolean deployData) {
      super(expr, aux, deployData);
      if (roleId == null) {
         throw new NullPointerException("null role primary key");
      } else {
         this.mId = roleId;
      }
   }

   public ERole(ERoleId roleId, EExpression expr, EAuxiliary aux, boolean deployData, String collectionName) {
      super(expr, aux, deployData, collectionName);
      if (roleId == null) {
         throw new NullPointerException("null role primary key");
      } else {
         this.mId = roleId;
      }
   }

   public ERole(String resourceName, String roleName, EExpression expr, EAuxiliary aux) {
      this(new ERoleId(resourceName, roleName), expr, aux, false);
   }

   public ERole(String resourceName, String roleName, EExpression expr, EAuxiliary aux, boolean deployData) {
      this(new ERoleId(resourceName, roleName), expr, aux, deployData);
   }

   public ERole(ERoleId roleId, EExpression expr, boolean deployData, String collectionName) {
      super(expr, deployData, collectionName);
      if (roleId == null) {
         throw new NullPointerException("null role primary key");
      } else {
         this.mId = roleId;
      }
   }

   public String getName() {
      return this.mId.getRoleName();
   }

   public String getResourceName() {
      return this.mId.getResourceName();
   }

   public Object getPrimaryKey() {
      return this.mId;
   }
}
