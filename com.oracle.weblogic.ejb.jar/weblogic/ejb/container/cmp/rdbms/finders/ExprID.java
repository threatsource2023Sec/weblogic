package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprID extends BaseExpr implements Expr, SingleExprIDHolder, ExpressionTypes {
   private static final char DOT = '.';
   public static final int ABSTRACT_SCHEMA_NAME = 0;
   public static final int RANGE_VARIABLE = 1;
   public static final int COLLECTION_MEMBER_ID = 2;
   public static final int PATH_EXPRESSION_CMP = 3;
   public static final int PATH_EXPRESSION_CMR = 4;
   private String ejbqlId;
   private String dealiasedEjbqlId;
   private String sqlString = null;
   private boolean prepForSQLGenDone = false;
   private char[] allowBackwardIdChars = new char[]{'@', '-'};

   protected ExprID(int type, String id) {
      super(type, id);
      this.ejbqlId = id;
      this.debugClassName = "ExprID - '" + this.ejbqlId + "' ";
   }

   public void init_method() throws ErrorCollectionException {
      if (this.ejbqlId != null && this.ejbqlId.length() > 0) {
         this.validateID();
      } else {
         throw new AssertionError(" ERROR encountered a NULL or EMPTY ejbqlId ");
      }
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public String toSQL() throws ErrorCollectionException {
      if (this.sqlString == null) {
         this.calcTableAndColumnForCmpField();
      }

      return this.sqlString;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public ExprID getExprID() {
      return this;
   }

   private void validateID() {
      char[] carray = this.ejbqlId.toCharArray();
      if (carray.length > 0 && carray[0] != '.' && !Character.isJavaIdentifierStart(carray[0])) {
         Loggable l = EJBLogger.logInvalidStartCharacterForEJBQLIdentifierLoggable(carray[0], this.ejbqlId);
         Exception e = new Exception(l.getMessageText());
         this.globalContext.addWarning(e);
         if (this.allowBackwardsCompatibleChar(carray[0])) {
            l = EJBLogger.logEJBQLCharAllowedForBackwardsCompatibilityLoggable(carray[0], this.ejbqlId);
            e = new Exception(l.getMessageText());
            this.globalContext.addWarning(e);
         }
      }

      if (carray.length > 1) {
         for(int i = 1; i < carray.length; ++i) {
            if (carray[i] != '.' && !Character.isJavaIdentifierPart(carray[i])) {
               Loggable l = EJBLogger.logInvalidPartCharacterForEJBQLIdentifierLoggable(carray[i], this.ejbqlId);
               Exception e = new Exception(l.getMessageText());
               this.globalContext.addWarning(e);
               if (this.allowBackwardsCompatibleChar(carray[i])) {
                  l = EJBLogger.logEJBQLCharAllowedForBackwardsCompatibilityLoggable(carray[i], this.ejbqlId);
                  e = new Exception(l.getMessageText());
                  this.globalContext.addWarning(e);
               }
            }
         }
      }

   }

   private boolean allowBackwardsCompatibleChar(char c) {
      char[] var2 = this.allowBackwardIdChars;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char allowBackwardIdChar = var2[var4];
         if (allowBackwardIdChar == c) {
            return true;
         }
      }

      return false;
   }

   public int getRelationshipTypeForPathExpressionWithNoSQLGen() throws ErrorCollectionException {
      try {
         return this.queryTree.getRelationshipTypeForPathExpressionWithNoSQLGen(this.getDealiasedEjbqlID());
      } catch (Exception var2) {
         this.markExcAndThrowCollectionException(var2);
         return -1;
      }
   }

   public boolean isPathExpressionEndingInCmpFieldWithNoSQLGen() throws ErrorCollectionException {
      int relType = this.getRelationshipTypeForPathExpressionWithNoSQLGen();
      return relType == 0;
   }

   private boolean isPathExpressionEndingInCmpFieldWithSQLGen() throws ErrorCollectionException {
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      String deAliasedPathExpr = this.getDealiasedEjbqlID();
      if (deAliasedPathExpr == null) {
         return false;
      } else {
         boolean retval = false;

         try {
            retval = this.globalContext.pathExpressionEndsInField(this.queryTree, deAliasedPathExpr);
         } catch (Exception var4) {
            this.markExcAndThrowCollectionException(var4);
         }

         return retval;
      }
   }

   public boolean isPathExpressionEndingInRemoteInterfaceWithSQLGen() throws ErrorCollectionException {
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      String deAliasedPathExpr = this.getDealiasedEjbqlID();
      if (deAliasedPathExpr == null) {
         return false;
      } else {
         boolean retval = false;

         try {
            retval = this.globalContext.pathExpressionEndsInRemoteInterface(this.queryTree, deAliasedPathExpr);
         } catch (IllegalExpressionException var4) {
            this.markExcAndThrowCollectionException(var4);
         }

         return retval;
      }
   }

   public boolean isPathExpressionEndingInBlobClobColumnWithSQLGen() throws ErrorCollectionException {
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      if (!this.isPathExpressionEndingInCmpFieldWithSQLGen()) {
         return false;
      } else {
         RDBMSBean bean = this.getRDBMSBeanWithSQLGen();
         String cmpField = QueryContext.getLastFieldFromId(this.getEjbqlID());
         return bean.isBlobCmpColumnTypeForField(cmpField) || bean.isClobCmpColumnTypeForField(cmpField);
      }
   }

   public RDBMSBean getRDBMSBeanWithSQLGen() throws ErrorCollectionException {
      RDBMSBean thisBean = null;
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      String deAliasedPathExpr = this.getDealiasedEjbqlID();

      try {
         thisBean = this.queryTree.getLastRDBMSBeanForPathExpression(deAliasedPathExpr);
      } catch (Exception var4) {
         this.markExcAndThrowCollectionException(var4);
      }

      if (thisBean == null) {
         this.markExcAndThrowCollectionException(new IllegalExpressionException(7, "Fatal Internal Error, could not get RDBMSBean for path expression: '" + deAliasedPathExpr + "'"));
      }

      return thisBean;
   }

   public EjbEntityRef getEntityRefWithSQLGen(String cmrField) throws ErrorCollectionException {
      RDBMSBean thisBean = null;

      try {
         thisBean = this.getRDBMSBeanWithSQLGen();
      } catch (Exception var4) {
         this.markExcAndThrowCollectionException(var4);
      }

      if (thisBean == null) {
         throw new ErrorCollectionException(" getEntityRefFromRDBMSBean passed NULL RDBMSBean ! ");
      } else {
         EjbEntityRef eref = thisBean.getEjbEntityRef(cmrField);
         if (eref == null) {
            this.markExcAndThrowCollectionException(new Exception(" <cmr-field> " + cmrField + " could not get EjbEntityRef from RDBMSBean: " + thisBean.getEjbName() + " ! "));
         }

         return eref;
      }
   }

   public String getEjbqlID() {
      return this.ejbqlId;
   }

   public void setEjbqlID(String id) {
      this.ejbqlId = id;
      this.prepForSQLGenDone = false;
   }

   public String getDealiasedEjbqlID() {
      if (debugLogger.isDebugEnabled()) {
         debug(" getDealiasedEjbqlID  on '" + this.ejbqlId + "'");
      }

      if (debugLogger.isDebugEnabled() && this.globalContext == null) {
         debug("  globalContext is  NULL !");
      }

      this.dealiasedEjbqlId = this.globalContext.replaceIdAliases(this.ejbqlId);
      return this.dealiasedEjbqlId;
   }

   public void setSQL(String sql) {
      this.sqlString = sql;
   }

   public String calcTableAndColumnForCmpField() throws ErrorCollectionException {
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      String deAliasedID = this.getDealiasedEjbqlID();
      if (!this.isPathExpressionEndingInCmpFieldWithSQLGen()) {
         this.markExcAndThrowCollectionException(new IllegalExpressionException(7, " Internal Error:  in ExprID.calcTableAndColumnForCmpField() attempt to execute Method on a pathExpression that is not terminated by a cmp-field:  '" + deAliasedID + "'"));
      }

      String tableAndField = null;

      try {
         JoinNode joinTree = this.getJoinTreeForID();
         tableAndField = JoinNode.getTableAndField(this.queryTree, joinTree, deAliasedID);
         if (!this.queryTree.thisQueryNodeOwnsId(deAliasedID)) {
         }
      } catch (IllegalExpressionException var4) {
         this.markExcAndThrowCollectionException(var4);
      }

      this.setSQL(tableAndField + " ");
      return tableAndField;
   }

   public JoinNode getJoinTreeForID() throws ErrorCollectionException {
      String deAliasedID = this.getDealiasedEjbqlID();
      JoinNode joinTree = null;

      try {
         joinTree = this.queryTree.getJoinTreeForId(deAliasedID);
      } catch (IllegalExpressionException var4) {
         throw new ErrorCollectionException(var4.getMessage());
      }

      if (joinTree == null) {
         this.markExcAndThrowCollectionException(new IllegalExpressionException(7, " Internal Error:  in ExprID.getJoinTreeForID() attempt to get joinTree for ID: '" + deAliasedID + "' yielded a NULL joinTree."));
      }

      return joinTree;
   }

   public JoinNode getJoinNodeForLastCmrFieldWithSQLGen() throws ErrorCollectionException {
      String deAliasedID = this.getDealiasedEjbqlID();
      if (!this.prepForSQLGenDone) {
         this.prepareIdentifierForSQLGen();
      }

      JoinNode joinTree = this.getJoinTreeForID();
      JoinNode joinNodeForLastCmrF = null;

      try {
         joinNodeForLastCmrF = JoinNode.getNode(joinTree, deAliasedID);
      } catch (IllegalExpressionException var5) {
         this.markExcAndThrowCollectionException(var5);
      }

      return joinNodeForLastCmrF;
   }

   public String getFirstField() {
      return JoinNode.getFirstFieldFromId(this.getDealiasedEjbqlID());
   }

   public String getLastField() {
      return JoinNode.getLastFieldFromId(this.getDealiasedEjbqlID());
   }

   public int countPathNodes() {
      return JoinNode.countPathNodes(this.getEjbqlID());
   }

   public void prepareIdentifierForSQLGen() throws ErrorCollectionException {
      try {
         this.queryTree.prepareIdentifierForSQLGen(this.getDealiasedEjbqlID());
      } catch (IllegalExpressionException var2) {
         this.markExcAndThrowCollectionException(var2);
      }

      this.prepForSQLGenDone = true;
   }

   public static ExprID newInitExprID(QueryContext globalContext, QueryNode queryTree, String id) throws ErrorCollectionException {
      ExprID exprId = new ExprID(17, id);
      exprId.init(globalContext, queryTree);
      return exprId;
   }

   public static String calcTableAndColumn(QueryContext globalContext, QueryNode queryTree, String id) throws ErrorCollectionException {
      ExprID exprId = new ExprID(17, id);
      exprId.init(globalContext, queryTree);
      exprId.calculate();
      return exprId.calcTableAndColumnForCmpField();
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprID] " + s);
   }
}
