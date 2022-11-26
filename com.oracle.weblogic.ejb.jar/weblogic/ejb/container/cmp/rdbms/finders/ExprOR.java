package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprOR extends BaseExpr implements Expr, ExpressionTypes {
   private ORJoinData orDataTerm1 = null;
   private ORJoinData orDataTerm2 = null;
   private List orJoinDataList = null;

   protected ExprOR(int type, Expr left, Expr right) {
      super(type, left, right);
      this.debugClassName = "ExprOR  ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);
      if (this.orJoinDataList != null) {
         this.queryTree.addORJoinDataListList(this.orJoinDataList);
      }

      try {
         this.orDataTerm1 = this.init_OR_term(this.globalContext, this.queryTree, this.term1);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         this.orDataTerm2 = this.init_OR_term(this.globalContext, this.queryTree, this.term2);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         this.calculate_OR_term(this.term1, this.orDataTerm1);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         this.calculate_OR_term(this.term2, this.orDataTerm2);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      BaseExpr newExprAND = new ExprAND(0, this.term1, this.term2);
      newExprAND.setPreEJBQLFrom(this);
      newExprAND.setMainEJBQL("AND ");
      newExprAND.setPostEJBQLFrom(this);
      return newExprAND;
   }

   private ORJoinData init_OR_term(QueryContext globalContext, QueryNode queryTree, Expr term) throws ErrorCollectionException {
      queryTree.pushOR(term);

      try {
         term.init(globalContext, queryTree);
      } catch (ErrorCollectionException var5) {
         queryTree.popOR();
         throw var5;
      }

      ORJoinData d = queryTree.popOR();
      if (!(term instanceof ExprOR)) {
         this.orJoinDataList.add(d);
      }

      return d;
   }

   private void calculate_OR_term(Expr term, ORJoinData orData) throws ErrorCollectionException {
      this.queryTree.pushOR(orData);

      try {
         term.calculate();
      } catch (ErrorCollectionException var4) {
         this.queryTree.popOR();
         throw var4;
      }

      this.queryTree.popOR();
   }

   private void toSQL_OR_term(Expr term, ORJoinData orData) throws ErrorCollectionException {
      String joinBuffer = null;
      this.queryTree.pushOR(orData);

      try {
         this.appendSQLBuf("(");
         this.appendSQLBuf(term.toSQL());
         joinBuffer = this.queryTree.getCurrentORJoinBuffer();
      } catch (IllegalExpressionException var5) {
         this.queryTree.popOR();
         term.markExcAndAddCollectionException(var5);
         this.addCollectionExceptionAndThrow(var5);
      }

      if (joinBuffer.length() > 0) {
         this.appendSQLBuf(" AND ");
         this.appendSQLBuf(joinBuffer);
      }

      this.appendSQLBuf(") ");
      this.queryTree.popOR();
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      this.toSQL_OR_term(this.term1, this.orDataTerm1);
      this.appendSQLBuf(" OR ");
      this.toSQL_OR_term(this.term2, this.orDataTerm2);
      return this.getSQLBuf().toString();
   }

   void setOrJoinDataList(List l) {
      this.orJoinDataList = l;
   }
}
