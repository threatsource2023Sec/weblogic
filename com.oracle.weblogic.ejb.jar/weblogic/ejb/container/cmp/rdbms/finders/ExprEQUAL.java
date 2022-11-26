package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprEQUAL extends BaseExpr implements Expr, ExpressionTypes {
   private static final int TYPE_EQ_CMP_FIELD = 0;
   private static final int TYPE_EQ_SUBQUERY_BEAN = 1;
   private static final int TYPE_EQ_ON_BEAN = 2;
   private static final int TYPE_EQ_ON_BOOLEAN_LITERAL = 3;
   private static final int OP_EQ_ON_BEAN = 0;
   private static final int OP_SUBQUERY_IN_ON_BEAN = 1;
   private static final int OP_EQ_SUBQUERY_ANY_ON_BEAN = 2;
   private static final int OP_EQ_SUBQUERY_ALL_ON_BEAN = 3;
   private static final int OP_EQ_SUBQUERY_ON_BEAN = 4;
   private static final int OP_NOT_EQ_ON_BEAN = 5;
   private static final int OP_SUBQUERY_NOT_IN_ON_BEAN = 6;
   private static final int OP_NOT_EQ_SUBQUERY_ANY_ON_BEAN = 7;
   private static final int OP_NOT_EQ_SUBQUERY_ALL_ON_BEAN = 8;
   private static final int OP_NOT_EQ_SUBQUERY_ON_BEAN = 9;
   private int eqType;
   protected boolean isEqual = true;
   private StringBuffer preCalcSQLBuf = null;

   protected ExprEQUAL(int type, Expr left, Expr right) {
      super(type, left, right);
      this.isEqual = true;
      this.debugClassName = "ExprEQUAL ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (Exception var4) {
         this.addCollectionException(var4);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (Exception var3) {
         this.addCollectionException(var3);
      }

      try {
         this.eqType = this.getEqType();
      } catch (Exception var2) {
         this.addCollectionException(var2);
         this.throwCollectionException();
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.preCalcSQLBuf = new StringBuffer();
      switch (this.eqType) {
         case 0:
            doSimpleEQ(this.globalContext, this.queryTree, this, this.preCalcSQLBuf, this.isEqual);
            return;
         case 1:
            doCalcEQonSubQuerySelectBean(this.globalContext, this.queryTree, this, this.preCalcSQLBuf, this.isEqual, false);
            return;
         case 2:
            doCalcEQonBean(this.globalContext, this.queryTree, this, this.preCalcSQLBuf, this.isEqual);
            return;
         case 3:
            doCalcEQonBooleanLiteral(this.globalContext, this.queryTree, this, this.preCalcSQLBuf, this.isEqual);
            return;
         default:
            this.markExcAndThrowCollectionException(new Exception("Internal Error. " + this.debugClassName + " unknown EQ Type: '" + this.eqType + "'"));
      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      BaseExpr newExprNOT_EQUAL = new ExprNOT_EQUAL(10, this.term1, this.term2);
      newExprNOT_EQUAL.setPreEJBQLFrom(this);
      newExprNOT_EQUAL.setMainEJBQL("<> ");
      newExprNOT_EQUAL.setPostEJBQLFrom(this);
      return newExprNOT_EQUAL;
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
      return this.preCalcSQLBuf.toString();
   }

   private int getEqType() throws ErrorCollectionException {
      if (isCalcEQonSubQuerySelectBean(this)) {
         return 1;
      } else if (isCalcEQonBean(this)) {
         return 2;
      } else {
         return isCalcEQonBooleanLiteral(this) ? 3 : 0;
      }
   }

   public static boolean isCalcEQonSubQuerySelectBean(Expr q) throws ErrorCollectionException {
      if (q.type() != 5 && q.type() != 10 && q.type() != 13) {
         return false;
      } else if (q.getTerm2() == null) {
         return false;
      } else {
         Expr subQExpr;
         Expr otherExpr;
         if (q.getTerm1().type() == 43) {
            subQExpr = q.getTerm1();
            otherExpr = q.getTerm2();
         } else {
            if (q.getTerm2().type() != 43) {
               return false;
            }

            subQExpr = q.getTerm2();
            otherExpr = q.getTerm1();
         }

         Expr expr = BaseExpr.getExpressionFromTerms(subQExpr, 34);
         if (expr == null) {
            return false;
         } else {
            expr = BaseExpr.getAggregateExpressionFromSubQuerySelect(subQExpr);
            if (expr != null) {
               return false;
            } else {
               if (otherExpr instanceof ExprID) {
                  if (((ExprID)otherExpr).isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
                     return false;
                  }
               } else if (otherExpr.type() == 25) {
                  Loggable l = EJBLogger.logejbqlSubQueryBeansCannotTestVariablesLoggable();
                  otherExpr.markExcAndThrowCollectionException(new ErrorCollectionException(l.getMessageText()));
               }

               return true;
            }
         }
      }
   }

   public static boolean isCalcEQonBean(Expr q) throws ErrorCollectionException {
      if (q.type() != 5 && q.type() != 10) {
         return false;
      } else {
         ExprID idExpr;
         Expr otherExpr;
         if (q.getTerm1().type() == 17) {
            idExpr = (ExprID)q.getTerm1();
            otherExpr = q.getTerm2();
         } else {
            if (q.getTerm2().type() != 17) {
               return false;
            }

            idExpr = (ExprID)q.getTerm2();
            otherExpr = q.getTerm1();
         }

         return !idExpr.isPathExpressionEndingInCmpFieldWithNoSQLGen();
      }
   }

   public static boolean isCalcEQonBooleanLiteral(Expr q) {
      if (q.type() != 5 && q.type() != 10) {
         return false;
      } else {
         Expr idExpr;
         Expr boolExpr;
         if (q.getTerm1().type() == 17) {
            idExpr = q.getTerm1();
            boolExpr = q.getTerm2();
         } else {
            if (q.getTerm2().type() != 17) {
               return false;
            }

            idExpr = q.getTerm2();
            boolExpr = q.getTerm1();
         }

         return boolExpr.type() == 14 || boolExpr.type() == 15;
      }
   }

   private static void doSimpleEQ(QueryContext globalContext, QueryNode queryTree, Expr q, StringBuffer sb, boolean equals) throws ErrorCollectionException {
      try {
         if (q.getTerm1() instanceof ExprID) {
            ((ExprID)q.getTerm1()).calcTableAndColumnForCmpField();
         } else {
            q.getTerm1().calculate();
         }
      } catch (ErrorCollectionException var8) {
         q.addCollectionException(var8);
      }

      try {
         if (q.getTerm2() instanceof ExprID) {
            ((ExprID)q.getTerm2()).calcTableAndColumnForCmpField();
         } else {
            q.getTerm2().calculate();
         }
      } catch (ErrorCollectionException var7) {
         q.addCollectionException(var7);
      }

      q.throwCollectionException();
      sb.append(q.getTerm1().toSQL());

      try {
         sb.append(BaseExpr.getComparisonOpStringFromType(q.type()));
      } catch (ErrorCollectionException var6) {
         q.markExcAndThrowCollectionException(var6);
      }

      sb.append(q.getTerm2().toSQL());
      q.throwCollectionException();
   }

   private static void doCalcEQonBean(QueryContext globalContext, QueryNode queryTree, Expr q, StringBuffer sb, boolean equals) throws ErrorCollectionException {
      ExprID idExpr = null;
      Expr otherExpr = null;
      if (q.getTerm1().type() == 17) {
         idExpr = (ExprID)q.getTerm1();
         otherExpr = q.getTerm2();
      } else if (q.getTerm2().type() == 17) {
         idExpr = (ExprID)q.getTerm2();
         otherExpr = q.getTerm1();
      }

      String id = idExpr.getDealiasedEjbqlID();
      if (otherExpr.type() != 25 && otherExpr.type() != 17) {
         Loggable l = EJBLogger.logejbqlCanOnlyTestBeanVsSameBeanTypeLoggable(id);
         Exception e = new IllegalExpressionException(7, l.getMessageText());
         q.markExcAndThrowCollectionException(new ErrorCollectionException(e));
      }

      if (otherExpr.type() == 17) {
         try {
            idExpr.calculate();
         } catch (ErrorCollectionException var20) {
            q.addCollectionException(var20);
         }

         try {
            otherExpr.calculate();
         } catch (ErrorCollectionException var19) {
            q.addCollectionException(var19);
         }

         q.throwCollectionException();

         try {
            if (equals) {
               doCalcEQonBean_ID_ID(globalContext, queryTree, idExpr, otherExpr, sb, 0);
            } else {
               doCalcEQonBean_ID_ID(globalContext, queryTree, idExpr, otherExpr, sb, 5);
            }
         } catch (ErrorCollectionException var18) {
            q.addCollectionExceptionAndThrow(var18);
         }

      } else {
         Expr varExpr = otherExpr;
         int variableNumber = Integer.parseInt(otherExpr.getSval());
         Class beanInterface = globalContext.getFinderParameterTypeAt(variableNumber - 1);
         if (idExpr.isPathExpressionEndingInRemoteInterfaceWithSQLGen()) {
            try {
               idExpr.calculate();
            } catch (Exception var21) {
               q.addCollectionException(var21);
            }

            q.throwCollectionException();
            String paramClassName = beanInterface.getName();
            if (!paramClassName.equals("javax.ejb.EJBObject")) {
               Exception e = new IllegalExpressionException(7, "<cmr-field> " + id + ", the input parameter to be tested against this field must be of type javax.ejb.EJBObject.  Instead, it is: " + beanInterface.getName());
               idExpr.markExcAndAddCollectionException(e);
               q.addCollectionExceptionAndThrow(e);
            }

            RDBMSBean rbean = idExpr.getRDBMSBeanWithSQLGen();
            String cmrField = idExpr.getLastField();
            EjbEntityRef eref = idExpr.getEntityRefWithSQLGen(cmrField);
            String remoteHomeName = eref.getHome();
            String pname = "param" + (variableNumber - 1);
            ParamNode beanNode = new ParamNode(rbean, pname, variableNumber, beanInterface, id, remoteHomeName, true, false, (Class)null, false, globalContext.isOracleNLSDataType(cmrField));
            globalContext.addFinderInternalQueryParmList(beanNode);
            globalContext.addFinderRemoteBeanParamList(beanNode);
            if (equals) {
               globalContext.setFinderRemoteBeanCommandEQ(true);
            } else {
               globalContext.setFinderRemoteBeanCommandEQ(false);
            }

         } else {
            try {
               idExpr.calculate();
            } catch (ErrorCollectionException var23) {
               q.addCollectionException(var23);
            }

            q.throwCollectionException();

            try {
               if (equals) {
                  doCalcEQonBean_ID_VARIABLE(globalContext, queryTree, idExpr, varExpr, sb, 0);
               } else {
                  doCalcEQonBean_ID_VARIABLE(globalContext, queryTree, idExpr, varExpr, sb, 5);
               }
            } catch (ErrorCollectionException var22) {
               q.addCollectionExceptionAndThrow(var22);
            }

         }
      }
   }

   public static void doCalcEQonSubQuerySelectBean(QueryContext globalContext, QueryNode queryTree, Expr q, StringBuffer sb, boolean equals) throws ErrorCollectionException {
      doCalcEQonSubQuerySelectBean(globalContext, queryTree, q, sb, equals, false);
   }

   public static void doCalcEQonSubQuerySelectBean(QueryContext globalContext, QueryNode queryTree, Expr q, StringBuffer sb, boolean equals, boolean isIn) throws ErrorCollectionException {
      int clauseType = 0;
      Expr subQExpr = q.getTerm1();
      Expr otherExpr = q.getTerm2();
      if (q.getTerm2().type() == 43) {
         subQExpr = q.getTerm2();
         otherExpr = q.getTerm1();
      }

      Expr subQIdExpr = subQExpr.getTerm1();
      if (subQIdExpr.type() != 19) {
         Exception e = new IllegalExpressionException(7, "Error in doCalcEQonSubQuerySelectBean(), the first term in SUBQUERY token is not INTEGER as expected !");
         subQIdExpr.markExcAndAddCollectionException(e);
         q.addCollectionExceptionAndThrow(e);
      }

      int subQueryId = (int)subQIdExpr.getIval();
      Expr expr = subQExpr.getTerm2();
      if (isIn) {
         if (equals) {
            clauseType = 1;
         } else {
            clauseType = 6;
         }
      } else if (expr.type() == 64) {
         if (equals) {
            clauseType = 2;
         } else {
            clauseType = 7;
         }
      } else if (expr.type() == 49) {
         if (equals) {
            clauseType = 3;
         } else {
            clauseType = 8;
         }
      } else if (expr.type() == 34) {
         if (equals) {
            clauseType = 4;
         } else {
            clauseType = 9;
         }
      } else {
         Exception e = new IllegalExpressionException(7, "Error in doCalcEQonSubQuerySelectBean(), unable to handle Expression type: '" + expr.type() + "'");
         subQExpr.markExcAndAddCollectionException(e);
         q.addCollectionExceptionAndThrow(e);
      }

      if (otherExpr instanceof ExprID) {
         doCalcEQonBean_ID_ID(globalContext, queryTree, otherExpr, (Expr)null, sb, clauseType);
         switch (clauseType) {
            case 2:
            case 7:
               sb.append("ANY ");
               break;
            case 3:
            case 8:
               sb.append("ALL ");
            case 4:
            case 5:
            case 6:
         }

         subQExpr.calculate();
         sb.append(subQExpr.toSQL());
         String id = ((ExprID)otherExpr).getDealiasedEjbqlID();
         RDBMSBean idBean = ((ExprID)otherExpr).getRDBMSBeanWithSQLGen();
         QueryNode subQueryNode = queryTree.getQueryNodeForQueryId(subQueryId);
         if (subQueryNode == null) {
            Exception e = new IllegalExpressionException(7, "Unable to locate SubQuery Node for SubQuery number '" + subQueryId + "'");
            subQExpr.markExcAndAddCollectionException(e);
            q.addCollectionExceptionAndThrow(e);
         } else {
            List selectList = subQueryNode.getSelectList();
            if (selectList.size() > 1) {
               Loggable l = EJBLogger.logejbqlSubQuerySelectCanOnlyHaveOneItemLoggable();
               Exception e = new IllegalExpressionException(7, l.getMessageText());
               subQExpr.markExcAndAddCollectionException(e);
               q.addCollectionExceptionAndThrow(e);
            }

            SelectNode sn = (SelectNode)selectList.get(0);
            RDBMSBean subQueryBean = sn.getSelectBean();
            Loggable l;
            IllegalExpressionException e;
            if (subQueryBean != null) {
               if (!subQueryBean.equals(idBean)) {
                  l = EJBLogger.logejbqlCanOnlyTestBeanVsSameBeanTypeLoggable(id);
                  e = new IllegalExpressionException(7, l.getMessageText());
                  subQExpr.markExcAndAddCollectionException(e);
                  q.addCollectionExceptionAndThrow(e);
               }

               CMPBeanDescriptor bd = idBean.getCMPBeanDescriptor();
               if (bd.hasComplexPrimaryKey()) {
                  Loggable l = EJBLogger.logejbqlSubQueryBeansCanOnlyHaveSimplePKsLoggable(id);
                  Exception e = new IllegalExpressionException(7, l.getMessageText());
                  subQExpr.markExcAndAddCollectionException(e);
                  q.addCollectionExceptionAndThrow(e);
               }

               return;
            }

            l = EJBLogger.logejbqlCanOnlyTestBeanVsSameBeanTypeLoggable(id);
            e = new IllegalExpressionException(7, l.getMessageText());
            subQExpr.markExcAndAddCollectionException(e);
            q.addCollectionExceptionAndThrow(e);
         }
      } else if (otherExpr.type() == 25) {
         Loggable l = EJBLogger.logejbqlSubQueryBeansCannotTestVariablesLoggable();
         Exception e = new IllegalExpressionException(7, l.getMessageText());
         otherExpr.markExcAndAddCollectionException(e);
         q.addCollectionExceptionAndThrow(e);
      }

   }

   private static void doCalcEQonBean_ID_ID(QueryContext globalContext, QueryNode queryTree, Expr expr1, Expr expr2, StringBuffer sb, int clauseType) throws ErrorCollectionException {
      boolean twoSided = false;
      switch (clauseType) {
         case 0:
         case 5:
            twoSided = true;
            break;
         default:
            twoSided = false;
      }

      String id1 = ((ExprID)expr1).getDealiasedEjbqlID();
      RDBMSBean rbean1 = ((ExprID)expr1).getRDBMSBeanWithSQLGen();
      String id2 = null;
      IllegalExpressionException e;
      if (twoSided) {
         id2 = ((ExprID)expr2).getDealiasedEjbqlID();
         RDBMSBean rbean2 = ((ExprID)expr2).getRDBMSBeanWithSQLGen();
         if (!rbean1.equals(rbean2)) {
            Loggable l = EJBLogger.logejbqlCanOnlyTestBeanVsSameBeanTypeLoggable(id1);
            e = new IllegalExpressionException(7, l.getMessageText());
            expr2.markExcAndAddCollectionException(e);
         }
      }

      List pkList = rbean1.getPrimaryKeyFields();
      int pkCount = pkList.size();
      e = null;
      String tableAndField2 = null;
      if (twoSided) {
         sb.append("(");
      }

      for(int i = 0; i < pkCount; ++i) {
         String pkField = (String)pkList.get(i);
         String pathId1 = id1 + (id1.length() > 0 ? "." : "") + pkField;
         String tableAndField1 = null;

         Loggable l;
         try {
            tableAndField1 = ExprID.calcTableAndColumn(globalContext, queryTree, pathId1);
         } catch (Exception var21) {
            l = EJBLogger.logfinderCouldNotGetTableAndFieldLoggable(id1);
            expr1.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
         }

         switch (clauseType) {
            case 0:
            case 5:
               String pathId2 = id2 + (id2.length() > 0 ? "." : "") + pkField;

               try {
                  tableAndField2 = ExprID.calcTableAndColumn(globalContext, queryTree, pathId2);
               } catch (Exception var20) {
                  l = EJBLogger.logfinderCouldNotGetTableAndFieldLoggable(id2);
                  expr2.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
               }
         }

         switch (clauseType) {
            case 0:
               sb.append(tableAndField1).append(" = ").append(tableAndField2).append(" AND ");
               break;
            case 1:
               sb.append(tableAndField1).append(" IN ");
               break;
            case 2:
            case 3:
            case 4:
               sb.append(tableAndField1).append(" = ");
               break;
            case 5:
               sb.append(tableAndField1).append(" != ").append(tableAndField2).append("  OR ");
               break;
            case 6:
               sb.append(tableAndField1).append(" NOT IN ");
               break;
            case 7:
            case 8:
            case 9:
               sb.append(tableAndField1).append(" <> ");
               break;
            default:
               expr1.markExcAndThrowCollectionException(new IllegalExpressionException(7, "unknown operation encountered for Equality on Bean: '" + clauseType + "'"));
         }
      }

      if (twoSided) {
         if (sb.length() > 5) {
            sb.setLength(sb.length() - 5);
         }

         sb.append(")");
      }

      if (debugLogger.isDebugEnabled()) {
         debug(" Bean equal SQL is: " + sb.toString() + "\n\n\n");
      }

   }

   private static void doCalcEQonBean_ID_VARIABLE(QueryContext globalContext, QueryNode queryTree, Expr idExpr, Expr varExpr, StringBuffer sb, int clauseType) throws ErrorCollectionException {
      int variableNumber = Integer.parseInt(varExpr.getSval());
      Class inputBeanInterface = globalContext.getFinderParameterTypeAt(variableNumber - 1);
      String inputBeanInterfaceName = inputBeanInterface.getName();
      String id = ((ExprID)idExpr).getDealiasedEjbqlID();
      RDBMSBean rbean = ((ExprID)idExpr).getRDBMSBeanWithSQLGen();
      CMPBeanDescriptor bd = rbean.getCMPBeanDescriptor();
      boolean typeMatch = false;
      String idBeanInterfaceName = "";
      if (bd.hasLocalClientView()) {
         idBeanInterfaceName = bd.getLocalInterfaceName();
         if (idBeanInterfaceName.equals(inputBeanInterfaceName)) {
            typeMatch = true;
         }
      }

      if (!typeMatch && bd.hasRemoteClientView()) {
         idBeanInterfaceName = bd.getRemoteInterfaceName();
         if (idBeanInterfaceName.equals(inputBeanInterfaceName)) {
            typeMatch = true;
         }
      }

      if (!typeMatch) {
         Loggable l = EJBLogger.logejbqlWrongBeanTestedAgainstVariableLoggable(id, idBeanInterfaceName, Integer.toString(variableNumber), inputBeanInterfaceName);
         idExpr.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
      }

      boolean hasComplexPK = bd.hasComplexPrimaryKey();
      Class pkClass = null;
      if (hasComplexPK) {
         pkClass = bd.getPrimaryKeyClass();
      }

      String pname = "param" + (variableNumber - 1);
      ParamNode beanNode = new ParamNode(rbean, pname, variableNumber, inputBeanInterface, id, "", true, false, pkClass, hasComplexPK, false);
      if (debugLogger.isDebugEnabled()) {
         debug(" processing Bean Parameter Node: " + beanNode);
      }

      String beanPath = id;
      List pkList = rbean.getPrimaryKeyFields();
      int pkCount = pkList.size();
      sb.append("(");

      for(int i = 0; i < pkCount; ++i) {
         String pkField = (String)pkList.get(i);
         pkClass = bd.getFieldClass(pkField);
         if (pkClass == null) {
            if (debugLogger.isDebugEnabled()) {
               debug("  PK CLASS: " + pkField + " is NULL !!!!");
            }

            Loggable l = EJBLogger.logfinderNoPKClassForFieldLoggable(pkField);
            idExpr.markExcAndThrowCollectionException(new IllegalExpressionException(7, "Bean: " + rbean.getEjbName() + "  " + l.getMessageText()));
         }

         boolean isNLS = globalContext.isOracleNLSDataType(pkField);
         if (i == 0 && !hasComplexPK) {
            beanNode.setPrimaryKeyClass(pkClass);
            beanNode.setOracleNLSDataType(isNLS);
         }

         String pathId = beanPath + (beanPath.length() > 0 ? "." : "") + pkField;
         ParamNode pNode = new ParamNode(rbean, "N_A", variableNumber, pkClass, pkField, "", false, false, pkClass, false, isNLS);
         if (debugLogger.isDebugEnabled()) {
            debug(" added ParamNode: " + pNode.toString());
         }

         beanNode.addParamSubList(pNode);
         String tableAndField = null;

         try {
            tableAndField = ExprID.calcTableAndColumn(globalContext, queryTree, pathId);
         } catch (Exception var29) {
            Loggable l = EJBLogger.logfinderCouldNotGetTableAndFieldLoggable(id);
            idExpr.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
         }

         switch (clauseType) {
            case 0:
               sb.append(tableAndField).append(" = ? ").append(" AND ");
               break;
            case 5:
               sb.append(tableAndField).append(" != ? ").append("  OR ");
               break;
            default:
               idExpr.markExcAndThrowCollectionException(new IllegalExpressionException(7, "unknown operation: " + clauseType + ", encountered for QJB QL WHERE clause testing Equality of Bean of Interface type '" + idBeanInterfaceName + "' to Input Variable '?" + variableNumber + "'."));
         }
      }

      if (sb.length() > 5) {
         sb.setLength(sb.length() - 5);
      }

      sb.append(")");
      if (debugLogger.isDebugEnabled()) {
         debug(" Bean Parameter SQL is: " + sb.toString());
      }

      globalContext.addFinderInternalQueryParmList(beanNode);
   }

   private static void doCalcEQonBooleanLiteral(QueryContext globalContext, QueryNode queryTree, Expr q, StringBuffer sb, boolean equals) throws ErrorCollectionException {
      Expr idExpr = null;
      Expr boolExpr = null;
      if (q.getTerm1().type() == 17) {
         idExpr = q.getTerm1();
         boolExpr = q.getTerm2();
      } else if (q.getTerm2().type() == 17) {
         idExpr = q.getTerm2();
         boolExpr = q.getTerm1();
      }

      String id = ((ExprID)idExpr).getDealiasedEjbqlID();
      String tableAndColumn = ((ExprID)idExpr).calcTableAndColumnForCmpField();
      if (boolExpr.type() == 14) {
         sb.append(tableAndColumn);
         if (equals) {
            sb.append(" = ");
         } else {
            sb.append(" <> ");
         }

         sb.append("1 ");
      } else if (boolExpr.type() == 15) {
         sb.append(tableAndColumn);
         if (equals) {
            sb.append(" = ");
         } else {
            sb.append(" <> ");
         }

         sb.append("0 ");
      } else {
         Loggable l = EJBLogger.logfinderInvalidBooleanLiteralLoggable();
         idExpr.markExcAndThrowCollectionException(new IllegalExpressionException(7, "<cmr-field> " + id + ", " + l.getMessageText()));
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprEQUAL] " + s);
   }
}
