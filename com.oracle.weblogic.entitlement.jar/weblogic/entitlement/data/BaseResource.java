package weblogic.entitlement.data;

import weblogic.entitlement.expression.EAuxiliary;
import weblogic.entitlement.expression.EExpression;

public abstract class BaseResource {
   private EExpression mExpr;
   private EAuxiliary mAux;
   private boolean mDeployData = false;
   private String mCollectionName = null;

   public BaseResource(EExpression expr) {
      this.mExpr = expr;
   }

   public BaseResource(EExpression expr, boolean deployData) {
      this.mExpr = expr;
      this.mDeployData = deployData;
   }

   public BaseResource(EExpression expr, boolean deployData, String collectioName) {
      this.mExpr = expr;
      this.mDeployData = deployData;
      this.mCollectionName = collectioName;
   }

   public BaseResource(EExpression expr, EAuxiliary aux) {
      this.mExpr = expr;
      this.mAux = aux;
   }

   public BaseResource(EExpression expr, EAuxiliary aux, boolean deployData) {
      this.mExpr = expr;
      this.mAux = aux;
      this.mDeployData = deployData;
   }

   public BaseResource(EExpression expr, EAuxiliary aux, boolean deployData, String collectioName) {
      this.mExpr = expr;
      this.mAux = aux;
      this.mDeployData = deployData;
      this.mCollectionName = collectioName;
   }

   public abstract Object getPrimaryKey();

   public EExpression getExpression() {
      return this.mExpr;
   }

   public void setExpression(EExpression expr) {
      this.mExpr = expr;
   }

   public String getEntitlement() {
      return this.mExpr == null ? null : this.mExpr.externalize();
   }

   public EAuxiliary getAuxiliary() {
      return this.mAux;
   }

   public void setAuxiliary(EAuxiliary aux) {
      this.mAux = aux;
   }

   public boolean isDeployData() {
      return this.mDeployData;
   }

   public String getCollectionName() {
      return this.mCollectionName;
   }
}
