package org.antlr.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.codegen.CodeGenerator;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public abstract class SemanticContext {
   public static final SemanticContext EMPTY_SEMANTIC_CONTEXT = new Predicate(-2);

   public abstract SemanticContext getGatedPredicateContext();

   public abstract ST genExpr(CodeGenerator var1, STGroup var2, DFA var3);

   public abstract boolean hasUserSemanticPredicate();

   public abstract boolean isSyntacticPredicate();

   public void trackUseOfSyntacticPredicates(Grammar g) {
   }

   public static SemanticContext and(SemanticContext a, SemanticContext b) {
      if (!(a instanceof FalsePredicate) && !(b instanceof FalsePredicate)) {
         SemanticContext[] terms = factorOr(a, b);
         SemanticContext commonTerms = terms[0];
         a = terms[1];
         b = terms[2];
         boolean factored = commonTerms != null && commonTerms != EMPTY_SEMANTIC_CONTEXT && !(commonTerms instanceof TruePredicate);
         if (factored) {
            return or(commonTerms, and(a, b));
         } else if (!(a instanceof FalsePredicate) && !(b instanceof FalsePredicate)) {
            if (a != EMPTY_SEMANTIC_CONTEXT && a != null) {
               if (b != EMPTY_SEMANTIC_CONTEXT && b != null) {
                  if (a instanceof TruePredicate) {
                     return b;
                  } else if (b instanceof TruePredicate) {
                     return a;
                  } else {
                     AND result = new AND(a, b);
                     return (SemanticContext)(result.operands.size() == 1 ? (SemanticContext)result.operands.iterator().next() : result);
                  }
               } else {
                  return a;
               }
            } else {
               return b;
            }
         } else {
            return new FalsePredicate();
         }
      } else {
         return new FalsePredicate();
      }
   }

   public static SemanticContext or(SemanticContext a, SemanticContext b) {
      if (!(a instanceof TruePredicate) && !(b instanceof TruePredicate)) {
         SemanticContext[] terms = factorAnd(a, b);
         SemanticContext commonTerms = terms[0];
         a = terms[1];
         b = terms[2];
         boolean factored = commonTerms != null && commonTerms != EMPTY_SEMANTIC_CONTEXT && !(commonTerms instanceof FalsePredicate);
         if (factored) {
            return and(commonTerms, or(a, b));
         } else if (a != EMPTY_SEMANTIC_CONTEXT && a != null && !(a instanceof FalsePredicate)) {
            if (b != EMPTY_SEMANTIC_CONTEXT && b != null && !(b instanceof FalsePredicate)) {
               if (!(a instanceof TruePredicate) && !(b instanceof TruePredicate) && !(commonTerms instanceof TruePredicate)) {
                  NOT n;
                  if (a instanceof NOT) {
                     n = (NOT)a;
                     if (n.ctx.equals(b)) {
                        return new TruePredicate();
                     }
                  } else if (b instanceof NOT) {
                     n = (NOT)b;
                     if (n.ctx.equals(a)) {
                        return new TruePredicate();
                     }
                  }

                  OR result = new OR(a, b);
                  return (SemanticContext)(result.operands.size() == 1 ? (SemanticContext)result.operands.iterator().next() : result);
               } else {
                  return new TruePredicate();
               }
            } else {
               return a;
            }
         } else {
            return b;
         }
      } else {
         return new TruePredicate();
      }
   }

   public static SemanticContext not(SemanticContext a) {
      if (a instanceof NOT) {
         return ((NOT)a).ctx;
      } else if (a instanceof TruePredicate) {
         return new FalsePredicate();
      } else {
         return (SemanticContext)(a instanceof FalsePredicate ? new TruePredicate() : new NOT(a));
      }
   }

   public static SemanticContext[] factorAnd(SemanticContext a, SemanticContext b) {
      if (a != EMPTY_SEMANTIC_CONTEXT && a != null && !(a instanceof FalsePredicate)) {
         if (b != EMPTY_SEMANTIC_CONTEXT && b != null && !(b instanceof FalsePredicate)) {
            if (!(a instanceof TruePredicate) && !(b instanceof TruePredicate)) {
               HashSet opsA = new HashSet(getAndOperands(a));
               HashSet opsB = new HashSet(getAndOperands(b));
               HashSet result = new HashSet(opsA);
               result.retainAll(opsB);
               if (result.isEmpty()) {
                  return new SemanticContext[]{EMPTY_SEMANTIC_CONTEXT, a, b};
               } else {
                  opsA.removeAll(result);
                  Object a;
                  if (opsA.isEmpty()) {
                     a = new TruePredicate();
                  } else if (opsA.size() == 1) {
                     a = (SemanticContext)opsA.iterator().next();
                  } else {
                     a = new AND(opsA);
                  }

                  opsB.removeAll(result);
                  Object b;
                  if (opsB.isEmpty()) {
                     b = new TruePredicate();
                  } else if (opsB.size() == 1) {
                     b = (SemanticContext)opsB.iterator().next();
                  } else {
                     b = new AND(opsB);
                  }

                  return result.size() == 1 ? new SemanticContext[]{(SemanticContext)result.iterator().next(), (SemanticContext)a, (SemanticContext)b} : new SemanticContext[]{new AND(result), (SemanticContext)a, (SemanticContext)b};
               }
            } else {
               return new SemanticContext[]{new TruePredicate(), EMPTY_SEMANTIC_CONTEXT, EMPTY_SEMANTIC_CONTEXT};
            }
         } else {
            return new SemanticContext[]{EMPTY_SEMANTIC_CONTEXT, a, b};
         }
      } else {
         return new SemanticContext[]{EMPTY_SEMANTIC_CONTEXT, a, b};
      }
   }

   public static SemanticContext[] factorOr(SemanticContext a, SemanticContext b) {
      HashSet opsA = new HashSet(getOrOperands(a));
      HashSet opsB = new HashSet(getOrOperands(b));
      HashSet result = new HashSet(opsA);
      result.retainAll(opsB);
      if (result.isEmpty()) {
         return new SemanticContext[]{EMPTY_SEMANTIC_CONTEXT, a, b};
      } else {
         opsA.removeAll(result);
         Object a;
         if (opsA.isEmpty()) {
            a = new FalsePredicate();
         } else if (opsA.size() == 1) {
            a = (SemanticContext)opsA.iterator().next();
         } else {
            a = new OR(opsA);
         }

         opsB.removeAll(result);
         Object b;
         if (opsB.isEmpty()) {
            b = new FalsePredicate();
         } else if (opsB.size() == 1) {
            b = (SemanticContext)opsB.iterator().next();
         } else {
            b = new OR(opsB);
         }

         return result.size() == 1 ? new SemanticContext[]{(SemanticContext)result.iterator().next(), (SemanticContext)a, (SemanticContext)b} : new SemanticContext[]{new OR(result), (SemanticContext)a, (SemanticContext)b};
      }
   }

   public static Collection getAndOperands(SemanticContext context) {
      if (context instanceof AND) {
         return ((AND)context).operands;
      } else if (!(context instanceof NOT)) {
         ArrayList result = new ArrayList();
         result.add(context);
         return result;
      } else {
         Collection operands = getOrOperands(((NOT)context).ctx);
         List result = new ArrayList(operands.size());
         Iterator i$ = operands.iterator();

         while(i$.hasNext()) {
            SemanticContext operand = (SemanticContext)i$.next();
            result.add(not(operand));
         }

         return result;
      }
   }

   public static Collection getOrOperands(SemanticContext context) {
      if (context instanceof OR) {
         return ((OR)context).operands;
      } else if (!(context instanceof NOT)) {
         ArrayList result = new ArrayList();
         result.add(context);
         return result;
      } else {
         Collection operands = getAndOperands(((NOT)context).ctx);
         List result = new ArrayList(operands.size());
         Iterator i$ = operands.iterator();

         while(i$.hasNext()) {
            SemanticContext operand = (SemanticContext)i$.next();
            result.add(not(operand));
         }

         return result;
      }
   }

   public static class NOT extends SemanticContext {
      protected SemanticContext ctx;

      public NOT(SemanticContext ctx) {
         this.ctx = ctx;
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         ST eST;
         if (templates != null) {
            eST = templates.getInstanceOf("notPredicate");
         } else {
            eST = new ST("!(<pred>)");
         }

         eST.add("pred", this.ctx.genExpr(generator, templates, dfa));
         return eST;
      }

      public SemanticContext getGatedPredicateContext() {
         SemanticContext p = this.ctx.getGatedPredicateContext();
         return p == null ? null : new NOT(p);
      }

      public boolean hasUserSemanticPredicate() {
         return this.ctx.hasUserSemanticPredicate();
      }

      public boolean isSyntacticPredicate() {
         return this.ctx.isSyntacticPredicate();
      }

      public void trackUseOfSyntacticPredicates(Grammar g) {
         this.ctx.trackUseOfSyntacticPredicates(g);
      }

      public boolean equals(Object object) {
         return !(object instanceof NOT) ? false : this.ctx.equals(((NOT)object).ctx);
      }

      public int hashCode() {
         return ~this.ctx.hashCode();
      }

      public String toString() {
         return "!(" + this.ctx + ")";
      }
   }

   public static class OR extends CommutativePredicate {
      public OR(SemanticContext a, SemanticContext b) {
         super(a, b);
      }

      public OR(HashSet contexts) {
         super(contexts);
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         ST eST;
         if (templates != null) {
            eST = templates.getInstanceOf("orPredicates");
         } else {
            eST = new ST("(<operands; separator=\"||\">)");
         }

         Iterator i$ = this.operands.iterator();

         while(i$.hasNext()) {
            SemanticContext semctx = (SemanticContext)i$.next();
            eST.add("operands", semctx.genExpr(generator, templates, dfa));
         }

         return eST;
      }

      public String getOperandString() {
         return "||";
      }

      public SemanticContext combinePredicates(SemanticContext left, SemanticContext right) {
         return SemanticContext.or(left, right);
      }

      public int calculateHashCode() {
         int hashcode = 0;

         SemanticContext context;
         for(Iterator i$ = this.operands.iterator(); i$.hasNext(); hashcode = ~hashcode ^ context.hashCode()) {
            context = (SemanticContext)i$.next();
         }

         return hashcode;
      }
   }

   public static class AND extends CommutativePredicate {
      public AND(SemanticContext a, SemanticContext b) {
         super(a, b);
      }

      public AND(HashSet contexts) {
         super(contexts);
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         ST result = null;
         Iterator i$ = this.operands.iterator();

         while(i$.hasNext()) {
            SemanticContext operand = (SemanticContext)i$.next();
            if (result == null) {
               result = operand.genExpr(generator, templates, dfa);
            } else {
               ST eST;
               if (templates != null) {
                  eST = templates.getInstanceOf("andPredicates");
               } else {
                  eST = new ST("(<left>&&<right>)");
               }

               eST.add("left", result);
               eST.add("right", operand.genExpr(generator, templates, dfa));
               result = eST;
            }
         }

         return result;
      }

      public String getOperandString() {
         return "&&";
      }

      public SemanticContext combinePredicates(SemanticContext left, SemanticContext right) {
         return SemanticContext.and(left, right);
      }

      public int calculateHashCode() {
         int hashcode = 0;

         SemanticContext context;
         for(Iterator i$ = this.operands.iterator(); i$.hasNext(); hashcode ^= context.hashCode()) {
            context = (SemanticContext)i$.next();
         }

         return hashcode;
      }
   }

   public abstract static class CommutativePredicate extends SemanticContext {
      protected final Set operands = new HashSet();
      protected int hashcode;

      public CommutativePredicate(SemanticContext a, SemanticContext b) {
         CommutativePredicate predicate;
         if (a.getClass() == this.getClass()) {
            predicate = (CommutativePredicate)a;
            this.operands.addAll(predicate.operands);
         } else {
            this.operands.add(a);
         }

         if (b.getClass() == this.getClass()) {
            predicate = (CommutativePredicate)b;
            this.operands.addAll(predicate.operands);
         } else {
            this.operands.add(b);
         }

         this.hashcode = this.calculateHashCode();
      }

      public CommutativePredicate(HashSet contexts) {
         Iterator i$ = contexts.iterator();

         while(i$.hasNext()) {
            SemanticContext context = (SemanticContext)i$.next();
            if (context.getClass() == this.getClass()) {
               CommutativePredicate predicate = (CommutativePredicate)context;
               this.operands.addAll(predicate.operands);
            } else {
               this.operands.add(context);
            }
         }

         this.hashcode = this.calculateHashCode();
      }

      public SemanticContext getGatedPredicateContext() {
         SemanticContext result = null;
         Iterator i$ = this.operands.iterator();

         while(i$.hasNext()) {
            SemanticContext semctx = (SemanticContext)i$.next();
            SemanticContext gatedPred = semctx.getGatedPredicateContext();
            if (gatedPred != null) {
               result = this.combinePredicates(result, gatedPred);
            }
         }

         return result;
      }

      public boolean hasUserSemanticPredicate() {
         Iterator i$ = this.operands.iterator();

         SemanticContext semctx;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            semctx = (SemanticContext)i$.next();
         } while(!semctx.hasUserSemanticPredicate());

         return true;
      }

      public boolean isSyntacticPredicate() {
         Iterator i$ = this.operands.iterator();

         SemanticContext semctx;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            semctx = (SemanticContext)i$.next();
         } while(!semctx.isSyntacticPredicate());

         return true;
      }

      public void trackUseOfSyntacticPredicates(Grammar g) {
         Iterator i$ = this.operands.iterator();

         while(i$.hasNext()) {
            SemanticContext semctx = (SemanticContext)i$.next();
            semctx.trackUseOfSyntacticPredicates(g);
         }

      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else {
            Set otherOperands;
            if (obj.getClass() == this.getClass()) {
               CommutativePredicate commutative = (CommutativePredicate)obj;
               otherOperands = commutative.operands;
               return this.operands.size() != otherOperands.size() ? false : this.operands.containsAll(otherOperands);
            } else {
               if (obj instanceof NOT) {
                  NOT not = (NOT)obj;
                  if (not.ctx instanceof CommutativePredicate && not.ctx.getClass() != this.getClass()) {
                     otherOperands = ((CommutativePredicate)not.ctx).operands;
                     if (this.operands.size() != otherOperands.size()) {
                        return false;
                     }

                     ArrayList temp = new ArrayList(this.operands.size());
                     Iterator i$ = otherOperands.iterator();

                     while(i$.hasNext()) {
                        SemanticContext context = (SemanticContext)i$.next();
                        temp.add(not(context));
                     }

                     return this.operands.containsAll(temp);
                  }
               }

               return false;
            }
         }
      }

      public int hashCode() {
         return this.hashcode;
      }

      public String toString() {
         StringBuilder buf = new StringBuilder();
         buf.append("(");
         int i = 0;

         for(Iterator i$ = this.operands.iterator(); i$.hasNext(); ++i) {
            SemanticContext semctx = (SemanticContext)i$.next();
            if (i > 0) {
               buf.append(this.getOperandString());
            }

            buf.append(semctx.toString());
         }

         buf.append(")");
         return buf.toString();
      }

      public abstract String getOperandString();

      public abstract SemanticContext combinePredicates(SemanticContext var1, SemanticContext var2);

      public abstract int calculateHashCode();
   }

   public static class FalsePredicate extends Predicate {
      public FalsePredicate() {
         super(0);
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         return templates != null ? templates.getInstanceOf("false") : new ST("false");
      }

      public boolean hasUserSemanticPredicate() {
         return false;
      }

      public String toString() {
         return "false";
      }
   }

   public static class TruePredicate extends Predicate {
      public TruePredicate() {
         super(-1);
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         return templates != null ? templates.getInstanceOf("true_value") : new ST("true");
      }

      public boolean hasUserSemanticPredicate() {
         return false;
      }

      public String toString() {
         return "true";
      }
   }

   public static class Predicate extends SemanticContext {
      public GrammarAST predicateAST;
      protected boolean gated = false;
      protected boolean synpred = false;
      public static final int INVALID_PRED_VALUE = -2;
      public static final int FALSE_PRED = 0;
      public static final int TRUE_PRED = -1;
      protected int constantValue = -2;

      public Predicate(int constantValue) {
         this.predicateAST = new GrammarAST();
         this.constantValue = constantValue;
      }

      public Predicate(GrammarAST predicate) {
         this.predicateAST = predicate;
         this.gated = predicate.getType() == 41 || predicate.getType() == 90;
         this.synpred = predicate.getType() == 90 || predicate.getType() == 14;
      }

      public Predicate(Predicate p) {
         this.predicateAST = p.predicateAST;
         this.gated = p.gated;
         this.synpred = p.synpred;
         this.constantValue = p.constantValue;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Predicate)) {
            return false;
         } else {
            Predicate other = (Predicate)o;
            if (this.constantValue != other.constantValue) {
               return false;
            } else {
               return this.constantValue != -2 ? true : this.predicateAST.getText().equals(other.predicateAST.getText());
            }
         }
      }

      public int hashCode() {
         if (this.constantValue != -2) {
            return this.constantValue;
         } else {
            return this.predicateAST == null ? 0 : this.predicateAST.getText().hashCode();
         }
      }

      public ST genExpr(CodeGenerator generator, STGroup templates, DFA dfa) {
         ST eST;
         if (templates != null) {
            if (this.synpred) {
               eST = templates.getInstanceOf("evalSynPredicate");
            } else {
               eST = templates.getInstanceOf("evalPredicate");
               generator.grammar.decisionsWhoseDFAsUsesSemPreds.add(dfa);
            }

            String description = this.predicateAST.enclosingRuleName;
            if (generator != null) {
               eST.add("pred", generator.translateAction(description, this.predicateAST));
            }

            if (generator != null) {
               description = generator.target.getTargetStringLiteralFromString(this.toString());
               eST.add("description", description);
            }

            return eST;
         } else {
            eST = new ST("<pred>");
            eST.add("pred", this.toString());
            return eST;
         }
      }

      public SemanticContext getGatedPredicateContext() {
         return this.gated ? this : null;
      }

      public boolean hasUserSemanticPredicate() {
         return this.predicateAST != null && (this.predicateAST.getType() == 41 || this.predicateAST.getType() == 83);
      }

      public boolean isSyntacticPredicate() {
         return this.predicateAST != null && (this.predicateAST.getType() == 90 || this.predicateAST.getType() == 14);
      }

      public void trackUseOfSyntacticPredicates(Grammar g) {
         if (this.synpred) {
            g.synPredNamesUsedInDFA.add(this.predicateAST.getText());
         }

      }

      public String toString() {
         return this.predicateAST == null ? "<nopred>" : this.predicateAST.getText();
      }
   }
}
