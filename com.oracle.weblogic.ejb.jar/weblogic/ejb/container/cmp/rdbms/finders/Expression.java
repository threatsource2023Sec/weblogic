package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Enumeration;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;

public final class Expression implements ExpressionTypes {
   private static final DebugLogger debugLogger;
   public int type;
   public Expression term1 = null;
   public Expression term2 = null;
   public Expression term3 = null;
   public Expression term4 = null;
   public Expression term5 = null;
   public Expression term6 = null;
   public Vector terms = null;
   private long ival;
   private double fval;
   private String sval;
   public int termCount;
   private int termVectorSize = 0;
   private int nextTerm = 1;

   public Expression(int type) {
      this.type = type;
      this.termCount = 0;
   }

   protected Expression(int type, Expression expr) {
      this.type = type;
      this.term1 = expr;
      this.termCount = 1;
   }

   protected Expression(int type, Expression expr1, Expression expr2) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.termCount = 2;
   }

   protected Expression(int type, Expression expr1, Expression expr2, Expression expr3) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.term3 = expr3;
      this.termCount = 3;
   }

   protected Expression(int type, Expression expr1, Expression expr2, Expression expr3, Expression expr4) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.term3 = expr3;
      this.term4 = expr4;
      this.termCount = 4;
   }

   protected Expression(int type, Expression expr1, Expression expr2, Expression expr3, Expression expr4, Expression expr5) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.term3 = expr3;
      this.term4 = expr4;
      this.term5 = expr5;
      this.termCount = 5;
   }

   protected Expression(int type, Expression expr1, Expression expr2, Expression expr3, Expression expr4, Expression expr5, Expression expr6) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.term3 = expr3;
      this.term4 = expr4;
      this.term5 = expr5;
      this.term6 = expr6;
      this.termCount = 6;
   }

   protected Expression(int type, Expression expr1, Vector terms) {
      this.type = type;
      this.term1 = expr1;
      this.terms = terms;
      this.termVectorSize = terms.size();
      this.termCount = 1 + this.termVectorSize;
   }

   protected Expression(int type, String lit) {
      this.type = type;
      if (type == 18) {
         this.sval = lit.substring(1, lit.length() - 1);
      } else {
         this.sval = lit;
      }

      if (type == 19) {
         this.sval = lit;
         this.ival = (long)Double.valueOf(lit).intValue();
      } else if (type == 20) {
         this.fval = Double.valueOf(lit);
         this.sval = lit;
      } else if (type == 25) {
         this.sval = lit;
         this.ival = (long)Integer.parseInt(lit);
      } else if (type == 53) {
         this.sval = lit;
      }

   }

   public int type() {
      return this.type;
   }

   public int numTerms() {
      return this.termCount + this.termVectorSize;
   }

   public int termVectSize() {
      return this.termVectorSize;
   }

   public void resetTermNumber() {
      this.nextTerm = 1;
   }

   public void setNextTerm(int termNumber) {
      this.nextTerm = termNumber;
   }

   public Expression getNextTerm() {
      return this.getTerm(this.nextTerm++);
   }

   public Expression getTerm(int termNumber) {
      switch (termNumber) {
         case 1:
            return this.term1;
         case 2:
            return this.term2;
         case 3:
            return this.term3;
         case 4:
            return this.term4;
         case 5:
            return this.term5;
         case 6:
            return this.term6;
         default:
            return null;
      }
   }

   public int getCurrTermNumber() {
      return this.nextTerm - 1;
   }

   public boolean hasMoreTerms() {
      return this.nextTerm <= this.termCount;
   }

   public String getSval() {
      return this.sval;
   }

   public void setSval(String s) {
      this.sval = s;
   }

   public long getIval() {
      return this.ival;
   }

   public double getFval() {
      return this.fval;
   }

   public static int numType(String txt) {
      return txt.indexOf(46) == -1 && txt.indexOf(69) == -1 ? 19 : 20;
   }

   public static int finderStringOrId(String txt) {
      if (txt.startsWith("@@")) {
         return 40;
      } else {
         int gtIndex = txt.indexOf(">>");
         if (gtIndex == -1) {
            return 17;
         } else {
            return txt.indexOf("find", gtIndex) != -1 ? 35 : 17;
         }
      }
   }

   public static String getEjbNameFromFinderString(String txt) {
      int gtIndex = txt.indexOf(">>");
      return gtIndex == -1 ? "" : txt.substring(0, gtIndex);
   }

   public static String getSelectCast(String s) {
      if (s.length() < 3) {
         return "";
      } else {
         return 40 != finderStringOrId(s) ? "" : s.substring(2);
      }
   }

   public static Expression getExpressionFromTerms(Expression q, int typeOut) {
      q.resetTermNumber();

      Expression expr;
      do {
         if (!q.hasMoreTerms()) {
            return null;
         }

         expr = q.getNextTerm();
      } while(expr.type != typeOut);

      return expr;
   }

   public static Expression getAggregateExpressionFromSubQuerySelect(Expression q) {
      if (q.type != 43) {
         return null;
      } else {
         Expression expr = null;
         q.resetTermNumber();

         while(q.hasMoreTerms()) {
            expr = q.getNextTerm();
            if (expr.type == 34) {
               break;
            }

            expr = null;
         }

         if (expr == null) {
            return null;
         } else if (expr.term2 == null) {
            return null;
         } else {
            return expr.term2.type != 44 && expr.term2.type != 45 && expr.term2.type != 46 && expr.term2.type != 47 && expr.term2.type != 48 ? null : expr.term2;
         }
      }
   }

   public String toString() {
      String val = this.toVal();
      return TYPE_NAMES[this.type] + ": " + (val == null ? this.term1 + "," + this.term2 : val);
   }

   private String toVal() {
      switch (this.type()) {
         case 17:
         case 18:
         case 25:
         case 40:
            return this.getSval();
         case 19:
            return this.getIval() + "";
         case 20:
            return this.getFval() + "";
         default:
            return null;
      }
   }

   public void dump() {
      dump(this, 0);
   }

   private static void dump(Expression q, int depth) {
      for(int i = 0; i < depth; ++i) {
         System.out.print("| ");
      }

      System.out.print(TYPE_NAMES[q.type()]);
      switch (q.type()) {
         case 16:
            System.out.println("-- NULL");
            break;
         case 17:
         case 25:
         case 35:
         case 40:
         case 53:
         case 61:
         case 62:
            System.out.println(" -- " + q.getSval());
            break;
         case 18:
            System.out.println(" -- \"" + q.getSval() + "\"");
            break;
         case 19:
            System.out.println(" -- " + q.getIval());
            break;
         case 20:
            System.out.println(" -- " + q.getFval());
            break;
         default:
            System.out.println();
      }

      if (q.term1 != null) {
         dump(q.term1, depth + 1);
      }

      if (q.term2 != null) {
         dump(q.term2, depth + 1);
      }

      if (q.term3 != null) {
         dump(q.term3, depth + 1);
      }

      if (q.term4 != null) {
         dump(q.term4, depth + 1);
      }

      if (q.term5 != null) {
         dump(q.term5, depth + 1);
      }

      if (q.term6 != null) {
         dump(q.term6, depth + 1);
      }

      if (q.terms != null) {
         Enumeration ts = q.terms.elements();

         while(ts.hasMoreElements()) {
            dump((Expression)ts.nextElement(), depth + 1);
         }
      }

   }

   public static Expression find(Expression q, int type) {
      if (debugLogger.isDebugEnabled()) {
         debug("Checking NODE: " + TYPE_NAMES[q.type()] + "  for type: " + TYPE_NAMES[type]);
      }

      if (q.type() == type) {
         return q;
      } else {
         Expression expr = null;
         if (q.term1 != null) {
            expr = find(q.term1, type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.term2 != null) {
            expr = find(q.term2, type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.term3 != null) {
            expr = find(q.term3, type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.term4 != null) {
            expr = find(q.term4, type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.term5 != null) {
            expr = find(q.term5, type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.terms != null) {
            Enumeration ts = q.terms.elements();

            while(ts.hasMoreElements()) {
               expr = find((Expression)ts.nextElement(), type);
               if (expr != null) {
                  return expr;
               }
            }
         }

         return null;
      }
   }

   public static String findIdForVariable(Expression q, int val) {
      if (q.type() == 5 && q.term2 != null && q.term2.type() == 25 && q.term2.getIval() == (long)val && q.term1 != null && q.term1.type() == 17) {
         if (debugLogger.isDebugEnabled()) {
            debug(" VARIABLE NODE: " + q.term2.getIval() + ".  returning ID: " + q.term1.getSval());
         }

         return q.term1.getSval();
      } else {
         String str = null;
         if (q.term1 != null) {
            str = findIdForVariable(q.term1, val);
            if (str != null) {
               return str;
            }
         }

         if (q.term2 != null) {
            str = findIdForVariable(q.term2, val);
            if (str != null) {
               return str;
            }
         }

         if (q.term3 != null) {
            str = findIdForVariable(q.term3, val);
            if (str != null) {
               return str;
            }
         }

         if (q.term4 != null) {
            str = findIdForVariable(q.term4, val);
            if (str != null) {
               return str;
            }
         }

         if (q.term5 != null) {
            str = findIdForVariable(q.term5, val);
            if (str != null) {
               return str;
            }
         }

         if (q.term6 != null) {
            str = findIdForVariable(q.term6, val);
            if (str != null) {
               return str;
            }
         }

         if (q.terms != null) {
            Enumeration ts = q.terms.elements();

            while(ts.hasMoreElements()) {
               str = findIdForVariable((Expression)ts.nextElement(), val);
               if (str != null) {
                  return str;
               }
            }
         }

         return str;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[Expression] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
