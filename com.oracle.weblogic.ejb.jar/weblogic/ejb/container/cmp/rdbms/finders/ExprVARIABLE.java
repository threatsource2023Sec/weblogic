package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprVARIABLE extends BaseExpr implements Expr, ExpressionTypes {
   private String fieldName;
   private int variableNumber;

   protected ExprVARIABLE(int type, String value, String fName) {
      super(type, value);
      this.debugClassName = "ExprVARIABLE -  ?" + value;
      this.fieldName = fName;
   }

   public void init_method() throws ErrorCollectionException {
      this.variableNumber = Integer.parseInt(this.getSval());
      if (this.variableNumber < 1) {
         Loggable l = EJBLogger.logFinderParamsMustBeGTOneLoggable(this.globalContext.getFinderMethodName(), Integer.toString(this.variableNumber));
         Exception ex = new IllegalExpressionException(7, l.getMessageText());
         this.markExcAndThrowCollectionException(ex);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.clearSQLBuf();
      if (this.globalContext.isKeyFinder() && this.variableNumber > 1) {
         this.appendSQLBuf("? ");
      } else {
         Class fClass = this.globalContext.getFinderParameterTypeAt(this.variableNumber - 1);
         if (fClass == null) {
            Loggable l = EJBLogger.logFinderParamMissingLoggable(this.globalContext.getFinderMethodName(), Integer.toString(this.variableNumber));
            Exception ex = new IllegalExpressionException(7, l.getMessageText());
            this.markExcAndThrowCollectionException(ex);
         }

         String pname = "param" + (this.variableNumber - 1);
         ParamNode pNode = new ParamNode(this.globalContext.getRDBMSBean(), pname, this.variableNumber, fClass, "", "", false, false, (Class)null, false, this.globalContext.isOracleNLSDataType(this.fieldName));
         this.globalContext.addFinderInternalQueryParmList(pNode);
         this.appendSQLBuf("? ");
      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return this.getSQLBuf().toString();
   }

   public int getVariableNumber() {
      return this.variableNumber;
   }
}
