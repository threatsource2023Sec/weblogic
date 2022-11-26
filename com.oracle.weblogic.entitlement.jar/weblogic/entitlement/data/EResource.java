package weblogic.entitlement.data;

import weblogic.entitlement.expression.EExpression;

public class EResource extends BaseResource {
   private String mName;

   public EResource(String name, EExpression expr) {
      this(name, expr, false, (String)null);
   }

   public EResource(String name, EExpression expr, boolean deployData) {
      this(name, expr, deployData, (String)null);
   }

   public EResource(String name, EExpression expr, boolean deployData, String collectionName) {
      super(expr, deployData, collectionName);
      if (name == null) {
         throw new NullPointerException("null resource name");
      } else {
         this.mName = name;
      }
   }

   public Object getPrimaryKey() {
      return this.mName;
   }

   public String getName() {
      return this.mName;
   }
}
