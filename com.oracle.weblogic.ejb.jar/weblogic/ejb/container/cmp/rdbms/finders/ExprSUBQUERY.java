package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprSUBQUERY extends BaseExpr implements Expr, ExpressionTypes {
   private int subqueryNumber = 0;
   private boolean hasQualifier = false;
   private boolean hasExistsQualifier = false;
   private String subqueryQualifier = null;
   private QueryNode subqueryTree = null;
   private ExprEXISTS exprEXISTS;
   private ExprSELECT exprSELECT;
   private ExprFROM exprFROM;
   private ExprWHERE exprWHERE;
   private ExprGROUPBY exprGROUPBY;

   protected ExprSUBQUERY(int type, ExprINTEGER exprInteger, Expr expr2) {
      super(type, exprInteger, (Expr)expr2);
      this.debugClassName = "ExprSUBQUERY";
   }

   protected ExprSUBQUERY(int type, ExprINTEGER exprInteger, Expr expr2, Expr expr3) {
      super(type, exprInteger, expr2, expr3);
      this.debugClassName = "ExprSUBQUERY";
   }

   protected ExprSUBQUERY(int type, ExprINTEGER exprInteger, Expr expr2, Expr expr3, Expr expr4) {
      super(type, exprInteger, expr2, expr3, expr4);
      this.debugClassName = "ExprSUBQUERY";
   }

   protected ExprSUBQUERY(int type, ExprINTEGER exprInteger, Expr expr2, Expr expr3, Expr expr4, Expr expr5) {
      super(type, exprInteger, expr2, expr3, expr4, expr5);
      this.debugClassName = "ExprSUBQUERY";
   }

   protected ExprSUBQUERY(int type, ExprINTEGER exprInteger, Expr expr2, Expr expr3, Expr expr4, Expr expr5, Expr expr6) {
      super(type, exprInteger, expr2, expr3, expr4, expr5, expr6);
      this.debugClassName = "ExprSUBQUERY";
   }

   public void init_method() throws ErrorCollectionException {
      try {
         this.setExprVars();
      } catch (ErrorCollectionException var5) {
         this.addCollectionExceptionAndThrow(var5);
      }

      this.subqueryTree = this.globalContext.newQueryNode(this.queryTree, this.subqueryNumber);
      this.exprFROM.init(this.globalContext, this.subqueryTree);

      try {
         this.exprSELECT.init(this.globalContext, this.subqueryTree);
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      if (this.exprWHERE != null) {
         try {
            this.exprWHERE.init(this.globalContext, this.subqueryTree);
         } catch (ErrorCollectionException var3) {
            this.addCollectionException(var3);
         }
      }

      try {
         if (this.exprGROUPBY != null) {
            this.exprGROUPBY.init(this.globalContext, this.subqueryTree);
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         this.exprSELECT.calculate();
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      try {
         this.exprFROM.calculate();
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      if (this.exprWHERE != null) {
         try {
            this.exprWHERE.calculate();
         } catch (ErrorCollectionException var3) {
            this.addCollectionException(var3);
         }
      }

      try {
         if (this.exprGROUPBY != null) {
            this.exprGROUPBY.calculate();
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      if (this.term3 != null) {
         this.term3.appendEJBQLTokens(l);
      }

      if (this.term4 != null) {
         this.term4.appendEJBQLTokens(l);
      }

      if (this.term5 != null) {
         this.term5.appendEJBQLTokens(l);
      }

      if (this.term6 != null) {
         this.term6.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      StringBuffer sb = new StringBuffer();
      if (this.hasQualifier) {
         sb.append(this.subqueryQualifier).append(" ");
      }

      if (this.hasExistsQualifier) {
         sb.append(this.exprEXISTS.toSQL());
      }

      try {
         sb.append("( ");
         this.toSQLselect(sb);
         this.toSQLfrom(sb);
         this.toSQLwhere(sb);
         this.toSQLgroupby(sb);
         sb.append(") ");
      } catch (ErrorCollectionException var3) {
         this.addCollectionExceptionAndThrow(var3);
      }

      return sb.toString();
   }

   private void setExprVars() throws ErrorCollectionException {
      int tCount = this.numTerms();
      this.resetTermNumber();
      Expr currTerm = this.getNextTerm();
      String missingClause;
      Loggable l;
      IllegalExpressionException e;
      if (currTerm.type() != 19) {
         missingClause = "SUBQUERY INTEGER";
         l = EJBLogger.logejbqlSubQueryMissingClauseLoggable(missingClause);
         e = new IllegalExpressionException(7, l.getMessageText());
         currTerm.markExcAndThrowCollectionException(e);
      }

      this.subqueryNumber = (int)currTerm.getIval();
      currTerm = this.getNextTerm();
      if (currTerm.type() == 65) {
         this.hasExistsQualifier = true;
         this.exprEXISTS = (ExprEXISTS)currTerm;
         this.subqueryQualifier = " <ExprSUBQUERY: NOTSET> ";
         this.exprEXISTS = (ExprEXISTS)currTerm;
         currTerm = this.getNextTerm();
      } else if (currTerm.type() == 49) {
         this.hasQualifier = true;
         this.subqueryQualifier = "ALL";
         currTerm = this.getNextTerm();
      } else if (currTerm.type() == 64) {
         this.hasQualifier = true;
         this.subqueryQualifier = "ANY";
         currTerm = this.getNextTerm();
      }

      if (currTerm.type() != 34) {
         missingClause = "SELECT";
         l = EJBLogger.logejbqlSubQueryMissingClauseLoggable(missingClause);
         e = new IllegalExpressionException(7, l.getMessageText());
         currTerm.markExcAndThrowCollectionException(e);
      }

      this.exprSELECT = (ExprSELECT)currTerm;
      currTerm = this.getNextTerm();
      if (currTerm.type() != 27) {
         missingClause = "FROM";
         l = EJBLogger.logejbqlSubQueryMissingClauseLoggable(missingClause);
         e = new IllegalExpressionException(7, l.getMessageText());
         currTerm.markExcAndThrowCollectionException(e);
      }

      this.exprFROM = (ExprFROM)currTerm;
      if (tCount > this.getCurrTermNumber()) {
         currTerm = this.getNextTerm();
         this.exprWHERE = (ExprWHERE)currTerm;
      }

      if (tCount > this.getCurrTermNumber()) {
         currTerm = this.getNextTerm();
         this.exprGROUPBY = (ExprGROUPBY)currTerm;
      }

   }

   private void toSQLselect(StringBuffer sb) throws ErrorCollectionException {
      sb.append("SELECT ");
      List selectList = this.subqueryTree.getSelectList();
      IllegalExpressionException e;
      if (selectList.size() > 1) {
         Loggable l = EJBLogger.logejbqlSubQuerySelectCanOnlyHaveOneItemLoggable();
         e = new IllegalExpressionException(7, l.getMessageText());
         throw new ErrorCollectionException(e);
      } else {
         SelectNode sn = (SelectNode)selectList.get(0);
         if (sn.getIsAggregate()) {
            sb.append(sn.getSelectTypeName());
            sb.append("(");
            if (sn.getIsAggregateDistinct()) {
               sb.append("DISTINCT ");
            }

            sb.append(sn.getDbmsTarget()).append(" ");
            sb.append(") ");
         } else if (sn.getSelectType() == 17) {
            sb.append(sn.getDbmsTarget()).append(" ");
         } else {
            if (sn.getSelectType() != 61) {
               e = new IllegalExpressionException(7, " SELECT clause of a SubQuery has a SELECT target type: " + ExpressionTypes.TYPE_NAMES[sn.getSelectType()] + ".  This type is not supported as a SubQuery SELECT target. ");
               throw new ErrorCollectionException(e);
            }

            String tableAlias = sn.getDbmsTarget();
            RDBMSBean rbean = sn.getSelectBean();
            List pkList = rbean.getPrimaryKeyFields();
            String pkField = (String)pkList.get(0);
            String colname = rbean.getCmpColumnForField(pkField);
            sb.append(" ").append(tableAlias).append(".").append(colname).append(" ");
         }

      }
   }

   private void toSQLfrom(StringBuffer sb) throws ErrorCollectionException {
      sb.append("FROM ");

      try {
         sb.append(this.subqueryTree.getFROMDeclaration(0));
      } catch (Exception var3) {
         throw new ErrorCollectionException(var3);
      }
   }

   private void toSQLwhere(StringBuffer sb) throws ErrorCollectionException {
      String joinBuffer;
      if (this.exprWHERE == null) {
         try {
            joinBuffer = this.subqueryTree.getMainORJoinBuffer();
            if (joinBuffer.length() > 0) {
               sb.append("WHERE ");
               sb.append(joinBuffer);
            }

         } catch (Exception var3) {
            throw new ErrorCollectionException(var3);
         }
      } else {
         try {
            sb.append(this.exprWHERE.toSQL());
            joinBuffer = this.subqueryTree.getMainORJoinBuffer();
            if (joinBuffer.length() > 0) {
               sb.append("AND ").append(joinBuffer);
            }

         } catch (Exception var4) {
            throw new ErrorCollectionException(var4);
         }
      }
   }

   ExprWHERE getExprWHERE() {
      return this.exprWHERE;
   }

   private void toSQLgroupby(StringBuffer sb) throws ErrorCollectionException {
      try {
         if (this.exprGROUPBY != null) {
            sb.append(this.exprGROUPBY.toSQL());
         }

      } catch (Exception var3) {
         throw new ErrorCollectionException(var3);
      }
   }
}
