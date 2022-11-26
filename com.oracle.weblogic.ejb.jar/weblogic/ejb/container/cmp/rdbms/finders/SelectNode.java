package weblogic.ejb.container.cmp.rdbms.finders;

import weblogic.ejb.container.cmp.rdbms.RDBMSBean;

public class SelectNode {
   private Expression selectItemExpr = null;
   private Expr selectItemBaseExpr = null;
   private int selectType;
   private String selectTypeName = "";
   private String selectTarget = null;
   private boolean isAggregate = false;
   private boolean isAggregateDistinct = false;
   private RDBMSBean selectBean = null;
   private Class selectClass = null;
   private String dbmsTarget = "";
   private String cachingElementGroupName = null;
   private RDBMSBean prevBean = null;
   private boolean correlatedSubQuery = false;

   public void setCorrelatedSubQuery() {
      this.correlatedSubQuery = true;
   }

   public boolean isCorrelatedSubQuery() {
      return this.correlatedSubQuery;
   }

   public void setSelectItemExpr(Expression e) {
      this.selectItemExpr = e;
   }

   public void setSelectItemBaseExpr(Expr be) {
      this.selectItemBaseExpr = be;
   }

   public void setSelectType(int i) {
      this.selectType = i;
   }

   public void setSelectTypeName(String s) {
      this.selectTypeName = s;
   }

   public void setSelectTarget(String s) {
      this.selectTarget = s;
   }

   public void setIsAggregate(boolean b) {
      this.isAggregate = b;
   }

   public void setIsAggregateDistinct(boolean b) {
      this.isAggregateDistinct = b;
   }

   public void setSelectBean(RDBMSBean r) {
      this.selectBean = r;
   }

   public void setSelectClass(Class c) {
      this.selectClass = c;
   }

   public void setDbmsTarget(String s) {
      this.dbmsTarget = s;
   }

   public Expression getSelectItemExpr() {
      return this.selectItemExpr;
   }

   public Expr getSelectItemBaseExpr() {
      return this.selectItemBaseExpr;
   }

   public int getSelectType() {
      return this.selectType;
   }

   public String getSelectTypeName() {
      return this.selectTypeName;
   }

   public String getSelectTarget() {
      return this.selectTarget;
   }

   public boolean getIsAggregate() {
      return this.isAggregate;
   }

   public boolean getIsAggregateDistinct() {
      return this.isAggregateDistinct;
   }

   public RDBMSBean getSelectBean() {
      return this.selectBean;
   }

   public Class setSelectClass() {
      return this.selectClass;
   }

   public String getDbmsTarget() {
      return this.dbmsTarget;
   }

   public String getCachingElementGroupName() {
      return this.cachingElementGroupName;
   }

   public void setCachingElementGroupName(String groupName) {
      this.cachingElementGroupName = groupName;
   }

   public void setPrevBean(RDBMSBean r) {
      this.prevBean = r;
   }

   public RDBMSBean getPrevBean() {
      return this.prevBean;
   }
}
