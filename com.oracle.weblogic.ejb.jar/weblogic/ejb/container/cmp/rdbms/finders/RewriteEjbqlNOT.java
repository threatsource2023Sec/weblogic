package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Iterator;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.utils.ErrorCollectionException;

public class RewriteEjbqlNOT {
   private static final DebugLogger debugLogger;

   private static void d(String s) {
      debugLogger.debug("[RewriteEjbqlNOT] " + s);
   }

   public static ExprROOT rewriteEjbqlNOT(ExprROOT exprRoot, boolean debug) throws ErrorCollectionException {
      Iterator it = exprRoot.getWHEREList().iterator();

      ExprWHERE where;
      for(int wherecnt = 0; it.hasNext(); findAndRewriteNOT(where, false, debug)) {
         ++wherecnt;
         where = (ExprWHERE)it.next();
         if (debug) {
            d(" processing WHERE #" + wherecnt);
         }
      }

      return exprRoot;
   }

   private static BaseExpr findAndRewriteNOT(BaseExpr expr, boolean parentInvertForNOT, boolean debug) throws ErrorCollectionException {
      boolean invertForNOT = parentInvertForNOT;
      String currClassName = expr.debugClassName;
      if (debug) {
         d(" processing expr '" + currClassName + "'");
         d("            expr '" + currClassName + "' numTerms is " + expr.numTerms());
      }

      BaseExpr childExpr;
      BaseExpr notChildExpr;
      BaseExpr notChildExpr;
      if (expr.numTerms() > 0) {
         int termNumber = 0;
         expr.resetTermNumber();

         label148:
         while(true) {
            while(true) {
               if (!expr.hasMoreTerms()) {
                  break label148;
               }

               ++termNumber;
               BaseExpr childExpr = (BaseExpr)expr.getNextTerm();
               String childDebugClassName = childExpr.debugClassName;
               if (childDebugClassName == null) {
                  childDebugClassName = " NULL CLASSNAME ";
               }

               if (debug) {
                  d(currClassName + " processing child #" + termNumber + ", type '" + childDebugClassName + "'");
               }

               if (childExpr.type() == 2) {
                  if (debug) {
                     d(currClassName + " processing child #" + termNumber + " is NOT, handle NOT");
                  }

                  boolean childInvertForNOT = !invertForNOT;
                  notChildExpr = (BaseExpr)childExpr.getTerm1();
                  if (notChildExpr == null) {
                     if (childExpr.termVectSize() > 0) {
                        throw new AssertionError("expr NOT child has unexpected vector of children when we were expecting only term1 to exist.");
                     }

                     throw new AssertionError("expr NOT has " + childExpr.numTerms() + " children, but we were expecting it to have exactly 1 child.");
                  }

                  while(notChildExpr.type() == 2) {
                     if (debug) {
                        d(currClassName + " encountered consecutive NOT, discard it. ");
                     }

                     childInvertForNOT = !childInvertForNOT;
                     notChildExpr = (BaseExpr)notChildExpr.getTerm1();
                  }

                  notChildExpr = findAndRewriteNOT(notChildExpr, childInvertForNOT, debug);
                  expr.replaceTermAt(notChildExpr, termNumber);
               } else {
                  if (debug) {
                     d(currClassName + " processing child #" + termNumber + " as normal child");
                  }

                  childExpr = findAndRewriteNOT(childExpr, invertForNOT, debug);
                  expr.replaceTermAt(childExpr, termNumber);
               }
            }
         }
      }

      if (expr.termVectSize() > 0) {
         Vector termV = expr.getTermVector();
         Iterator it = termV.iterator();
         int vectorPos = -1;

         label125:
         while(true) {
            while(true) {
               if (!it.hasNext()) {
                  break label125;
               }

               ++vectorPos;
               childExpr = (BaseExpr)it.next();
               if (debug) {
                  d(currClassName + " processing vector child #" + vectorPos + ", type '" + childExpr.debugClassName + "'");
               }

               if (childExpr.type() == 2) {
                  if (debug) {
                     d(currClassName + " processing vector child #" + vectorPos + " is NOT, handle NOT");
                  }

                  boolean childInvertForNOT = !invertForNOT;
                  notChildExpr = (BaseExpr)childExpr.getTerm1();
                  if (notChildExpr == null) {
                     if (childExpr.termVectSize() > 0) {
                        throw new AssertionError("expr NOT child has unexpected vector of children when we were expecting only term1 to exist.");
                     }

                     throw new AssertionError("expr NOT has " + childExpr.numTerms() + " children, but we were expecting it to have exactly 1 child.");
                  }

                  while(notChildExpr.type() == 2) {
                     if (debug) {
                        d(currClassName + " encountered consecutive NOT, discard it. ");
                     }

                     childInvertForNOT = !childInvertForNOT;
                     notChildExpr = (BaseExpr)notChildExpr.getTerm1();
                  }

                  BaseExpr newNotChildExpr = findAndRewriteNOT(notChildExpr, childInvertForNOT, debug);
                  termV.setElementAt(newNotChildExpr, vectorPos);
               } else {
                  if (debug) {
                     d(currClassName + " processing vector child #" + vectorPos + " as normal child");
                  }

                  notChildExpr = findAndRewriteNOT(childExpr, invertForNOT, debug);
                  termV.setElementAt(notChildExpr, vectorPos);
               }
            }
         }
      }

      if (debug) {
         d(currClassName + " we're done with our children, now take care of ourselves ");
      }

      if (invertForNOT) {
         BaseExpr newExpr = (BaseExpr)expr.invertForNOT();
         if (debug) {
            d(currClassName + " we need to  !!!  INVERT  !!!  ourself, morphing into " + newExpr.debugClassName + "\n\n");
         }

         return newExpr;
      } else {
         if (debug) {
            d(currClassName + " we do not need to invert ourself returning " + expr.debugClassName + "\n\n");
         }

         return expr;
      }
   }

   public static boolean hasNOTExpr(BaseExpr tree) {
      if (tree.numTerms() > 0) {
         tree.resetTermNumber();

         while(tree.hasMoreTerms()) {
            BaseExpr childExpr = (BaseExpr)tree.getNextTerm();
            if (childExpr.type() == 2) {
               return true;
            }

            if (hasNOTExpr(childExpr)) {
               return true;
            }
         }
      }

      if (tree.termVectSize() > 0) {
         Iterator it = tree.getTermVector().iterator();

         while(it.hasNext()) {
            BaseExpr childExpr = (BaseExpr)it.next();
            if (childExpr.type() == 2) {
               return true;
            }

            if (hasNOTExpr(childExpr)) {
               return true;
            }
         }
      }

      return false;
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
