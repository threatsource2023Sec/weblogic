package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLToken;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprROOT extends BaseExpr implements Expr, ExpressionTypes {
   private ExprSELECT exprSELECT;
   private ExprFROM exprFROM;
   private ExprWHERE exprWHERE;
   private ExprORDERBY exprORDERBY;
   private ExprGROUPBY exprGROUPBY;
   private ExprSELECT_HINT exprSELECT_HINT;
   private List ejbqlTokenList = null;

   protected ExprROOT(int type, Expr e1) {
      super(type, e1);
      this.debugClassName = "ExprRoot";
   }

   protected ExprROOT(int type, Expr e1, Expr e2) {
      super(type, e1, e2);
      this.debugClassName = "ExprRoot";
   }

   protected ExprROOT(int type, Expr e1, Expr e2, Expr e3) {
      super(type, e1, e2, e3);
      this.debugClassName = "ExprRoot";
   }

   protected ExprROOT(int type, Expr e1, Expr e2, Expr e3, Expr e4) {
      super(type, e1, e2, e3, e4);
      this.debugClassName = "ExprRoot";
   }

   protected ExprROOT(int type, Expr e1, Expr e2, Expr e3, Expr e4, Expr e5) {
      super(type, e1, e2, e3, e4, e5);
      this.debugClassName = "ExprRoot";
   }

   public void init_method() throws ErrorCollectionException {
      this.setExprVars();
      this.exprFROM.init(this.globalContext, this.queryTree);

      try {
         this.exprSELECT.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var6) {
         this.addCollectionException(var6);
      }

      try {
         if (this.exprWHERE != null) {
            this.exprWHERE.init(this.globalContext, this.queryTree);
         }
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      try {
         if (this.exprORDERBY != null) {
            this.exprORDERBY.init(this.globalContext, this.queryTree);
         }
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      try {
         if (this.exprGROUPBY != null) {
            this.exprGROUPBY.init(this.globalContext, this.queryTree);
         }
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         if (this.exprSELECT_HINT != null) {
            this.exprSELECT_HINT.init(this.globalContext, this.queryTree);
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         this.exprSELECT.calculate();
      } catch (ErrorCollectionException var6) {
         this.addCollectionException(var6);
      }

      this.exprFROM.calculate();

      try {
         if (this.exprWHERE != null) {
            this.exprWHERE.calculate();
         }
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      try {
         if (this.exprORDERBY != null) {
            this.exprORDERBY.calculate();
         }
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      try {
         if (this.exprGROUPBY != null) {
            this.exprGROUPBY.calculate();
         }
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         if (this.exprSELECT_HINT != null) {
            this.exprSELECT_HINT.calculate();
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.resetTermNumber();

      while(this.hasMoreTerms()) {
         Expr term = this.getNextTerm();
         term.appendEJBQLTokens(l);
      }

   }

   public List getEJBQLTokenList() {
      this.ejbqlTokenList = new ArrayList();
      this.appendEJBQLTokens(this.ejbqlTokenList);
      return this.ejbqlTokenList;
   }

   protected String getEJBQLText() {
      List l = this.getEJBQLTokenList();
      StringBuffer sb = new StringBuffer();
      Iterator it = l.iterator();

      while(it.hasNext()) {
         EJBQLToken t = (EJBQLToken)it.next();
         sb.append(t.getTokenText());
      }

      return sb.toString();
   }

   public String toSQL(Properties props) {
      return "";
   }

   private void setExprVars() throws ErrorCollectionException {
      int totalTermCount = this.numTerms();
      Loggable l;
      if (totalTermCount > 0) {
         if (this.term1.type() != 34) {
            l = EJBLogger.logSelectClauseRequiredLoggable();
            this.term1.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
         }

         this.exprSELECT = (ExprSELECT)this.term1;
         if (totalTermCount < 2) {
            l = EJBLogger.logFromClauseRequiredLoggable();
            this.term1.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
         }
      }

      if (this.term2.type() != 27) {
         l = EJBLogger.logFromClauseRequiredLoggable();
         this.term2.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
      }

      this.exprFROM = (ExprFROM)this.term2;
      this.setNextTerm(3);

      while(this.hasMoreTerms()) {
         Expr term = this.getNextTerm();
         switch (term.type()) {
            case 26:
               this.exprWHERE = (ExprWHERE)term;
               break;
            case 36:
               this.exprORDERBY = (ExprORDERBY)term;
               break;
            case 60:
               this.exprSELECT_HINT = (ExprSELECT_HINT)term;
               break;
            case 68:
               this.exprGROUPBY = (ExprGROUPBY)term;
               break;
            default:
               this.markExcAndThrowCollectionException(new IllegalExpressionException(7, " unknown expr type: '" + term.type() + "' '" + BaseExpr.getTypeName(term.type()) + "' "));
         }
      }

   }

   public String getWhereSql() throws ErrorCollectionException {
      return this.exprWHERE == null ? "" : this.exprWHERE.toSQL();
   }

   ExprWHERE getExprWHERE() {
      return this.exprWHERE;
   }

   List getWHEREList() {
      List whereExprList = new ArrayList();
      Expr where = this.getWHEREchildFromParent(this);
      if (where == null) {
         return whereExprList;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("\n\nadding main WHERE clause to whereExprList");
         }

         whereExprList.add(where);
         this.addWHEREfromSUBQUERYs(whereExprList, where);
         return whereExprList;
      }
   }

   private void addWHEREfromSUBQUERYs(List l, Expr inputTerm) {
      if (inputTerm instanceof ExprSUBQUERY) {
         if (debugLogger.isDebugEnabled()) {
            debug("addWHEREfromSUBQUERYs, found SUBQUERY");
         }

         ExprWHERE where = this.getWHEREchildFromParent(inputTerm);
         if (where == null) {
            if (debugLogger.isDebugEnabled()) {
               debug("addWHEREfromSUBQUERYs   SUBQUERY has no WHERE clause terminate searching.");
            }

            return;
         }

         if (debugLogger.isDebugEnabled()) {
            debug("addWHEREfromSUBQUERYs  found SUBQUERY WHERE adding.");
         }

         l.add(where);
         this.addWHEREfromSUBQUERYs(l, where);
      } else {
         if (inputTerm.numTerms() == 0) {
            if (debugLogger.isDebugEnabled()) {
               debug("addWHEREfromSUBQUERYs,  term NOT SUBQUERY, no subterms, exiting.");
            }

            return;
         }

         inputTerm.resetTermNumber();

         while(inputTerm.hasMoreTerms()) {
            Expr term = inputTerm.getNextTerm();
            if (term == null) {
               break;
            }

            this.addWHEREfromSUBQUERYs(l, term);
         }

         if (inputTerm.termVectSize() > 0) {
            Vector v = inputTerm.getTermVector();
            Enumeration e = v.elements();

            while(e.hasMoreElements()) {
               Expr term = (Expr)e.nextElement();
               if (term == null) {
                  break;
               }

               this.addWHEREfromSUBQUERYs(l, term);
            }
         }
      }

   }

   private ExprWHERE getWHEREchildFromParent(Expr term) {
      term.resetTermNumber();

      Expr child;
      do {
         if (!term.hasMoreTerms()) {
            return null;
         }

         child = term.getNextTerm();
      } while(!(child instanceof ExprWHERE));

      return (ExprWHERE)child;
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprROOT] " + s);
   }
}
