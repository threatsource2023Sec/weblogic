package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.Shadow;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class PointcutRewriter {
   private static final boolean WATCH_PROGRESS = false;

   public Pointcut rewrite(Pointcut pc, boolean forceRewrite) {
      Pointcut result = pc;
      if (forceRewrite || !this.isDNF(pc)) {
         result = this.distributeNot(pc);
         result = this.pullUpDisjunctions(result);
      }

      result = this.simplifyAnds(result);
      result = this.removeNothings(result);
      result = this.sortOrs(result);
      return result;
   }

   public Pointcut rewrite(Pointcut pc) {
      return this.rewrite(pc, false);
   }

   private boolean isDNF(Pointcut pc) {
      return this.isDNFHelper(pc, true);
   }

   private boolean isDNFHelper(Pointcut pc, boolean canStillHaveOrs) {
      if (this.isAnd(pc)) {
         AndPointcut ap = (AndPointcut)pc;
         return this.isDNFHelper(ap.getLeft(), false) && this.isDNFHelper(ap.getRight(), false);
      } else if (this.isOr(pc)) {
         if (!canStillHaveOrs) {
            return false;
         } else {
            OrPointcut op = (OrPointcut)pc;
            return this.isDNFHelper(op.getLeft(), true) && this.isDNFHelper(op.getRight(), true);
         }
      } else {
         return this.isNot(pc) ? this.isDNFHelper(((NotPointcut)pc).getNegatedPointcut(), canStillHaveOrs) : true;
      }
   }

   public static String format(Pointcut p) {
      String s = p.toString();
      return s;
   }

   private Pointcut distributeNot(Pointcut pc) {
      Pointcut notBody;
      if (this.isNot(pc)) {
         NotPointcut npc = (NotPointcut)pc;
         notBody = this.distributeNot(npc.getNegatedPointcut());
         if (this.isNot(notBody)) {
            return ((NotPointcut)notBody).getNegatedPointcut();
         } else {
            Pointcut newLeft;
            Pointcut newRight;
            if (this.isAnd(notBody)) {
               AndPointcut apc = (AndPointcut)notBody;
               newLeft = this.distributeNot(new NotPointcut(apc.getLeft(), npc.getStart()));
               newRight = this.distributeNot(new NotPointcut(apc.getRight(), npc.getStart()));
               return new OrPointcut(newLeft, newRight);
            } else if (this.isOr(notBody)) {
               OrPointcut opc = (OrPointcut)notBody;
               newLeft = this.distributeNot(new NotPointcut(opc.getLeft(), npc.getStart()));
               newRight = this.distributeNot(new NotPointcut(opc.getRight(), npc.getStart()));
               return new AndPointcut(newLeft, newRight);
            } else {
               return new NotPointcut(notBody, npc.getStart());
            }
         }
      } else {
         Pointcut right;
         if (this.isAnd(pc)) {
            AndPointcut apc = (AndPointcut)pc;
            notBody = this.distributeNot(apc.getLeft());
            right = this.distributeNot(apc.getRight());
            return new AndPointcut(notBody, right);
         } else if (this.isOr(pc)) {
            OrPointcut opc = (OrPointcut)pc;
            notBody = this.distributeNot(opc.getLeft());
            right = this.distributeNot(opc.getRight());
            return new OrPointcut(notBody, right);
         } else {
            return pc;
         }
      }
   }

   private Pointcut pullUpDisjunctions(Pointcut pc) {
      if (this.isNot(pc)) {
         NotPointcut npc = (NotPointcut)pc;
         return new NotPointcut(this.pullUpDisjunctions(npc.getNegatedPointcut()));
      } else if (this.isAnd(pc)) {
         AndPointcut apc = (AndPointcut)pc;
         Pointcut left = this.pullUpDisjunctions(apc.getLeft());
         Pointcut right = this.pullUpDisjunctions(apc.getRight());
         Pointcut A;
         Pointcut B;
         if (this.isOr(left) && !this.isOr(right)) {
            A = ((OrPointcut)left).getLeft();
            B = ((OrPointcut)left).getRight();
            return this.pullUpDisjunctions(new OrPointcut(new AndPointcut(A, right), new AndPointcut(B, right)));
         } else if (this.isOr(right) && !this.isOr(left)) {
            A = ((OrPointcut)right).getLeft();
            B = ((OrPointcut)right).getRight();
            return this.pullUpDisjunctions(new OrPointcut(new AndPointcut(left, A), new AndPointcut(left, B)));
         } else if (this.isOr(right) && this.isOr(left)) {
            A = this.pullUpDisjunctions(((OrPointcut)left).getLeft());
            B = this.pullUpDisjunctions(((OrPointcut)left).getRight());
            Pointcut C = this.pullUpDisjunctions(((OrPointcut)right).getLeft());
            Pointcut D = this.pullUpDisjunctions(((OrPointcut)right).getRight());
            Pointcut newLeft = new OrPointcut(new AndPointcut(A, C), new AndPointcut(A, D));
            Pointcut newRight = new OrPointcut(new AndPointcut(B, C), new AndPointcut(B, D));
            return this.pullUpDisjunctions(new OrPointcut(newLeft, newRight));
         } else {
            return new AndPointcut(left, right);
         }
      } else if (this.isOr(pc)) {
         OrPointcut opc = (OrPointcut)pc;
         return new OrPointcut(this.pullUpDisjunctions(opc.getLeft()), this.pullUpDisjunctions(opc.getRight()));
      } else {
         return pc;
      }
   }

   public Pointcut not(Pointcut p) {
      return (Pointcut)(this.isNot(p) ? ((NotPointcut)p).getNegatedPointcut() : new NotPointcut(p));
   }

   public Pointcut createAndsFor(Pointcut[] ps) {
      if (ps.length == 1) {
         return ps[0];
      } else if (ps.length == 2) {
         return new AndPointcut(ps[0], ps[1]);
      } else {
         Pointcut[] subset = new Pointcut[ps.length - 1];

         for(int i = 1; i < ps.length; ++i) {
            subset[i - 1] = ps[i];
         }

         return new AndPointcut(ps[0], this.createAndsFor(subset));
      }
   }

   private Pointcut simplifyAnds(Pointcut pc) {
      if (this.isNot(pc)) {
         NotPointcut npc = (NotPointcut)pc;
         Pointcut notBody = npc.getNegatedPointcut();
         return (Pointcut)(this.isNot(notBody) ? this.simplifyAnds(((NotPointcut)notBody).getNegatedPointcut()) : new NotPointcut(this.simplifyAnds(npc.getNegatedPointcut())));
      } else if (this.isOr(pc)) {
         OrPointcut opc = (OrPointcut)pc;
         return new OrPointcut(this.simplifyAnds(opc.getLeft()), this.simplifyAnds(opc.getRight()));
      } else {
         return this.isAnd(pc) ? this.simplifyAnd((AndPointcut)pc) : pc;
      }
   }

   private Pointcut simplifyAnd(AndPointcut apc) {
      SortedSet nodes = new TreeSet(new PointcutEvaluationExpenseComparator());
      this.collectAndNodes(apc, nodes);
      Iterator iter = nodes.iterator();

      Pointcut right;
      while(iter.hasNext()) {
         Pointcut element = (Pointcut)iter.next();
         if (element instanceof NotPointcut) {
            right = ((NotPointcut)element).getNegatedPointcut();
            if (nodes.contains(right)) {
               return Pointcut.makeMatchesNothing(right.state);
            }
         }

         if (element instanceof IfPointcut && ((IfPointcut)element).alwaysFalse()) {
            return Pointcut.makeMatchesNothing(element.state);
         }

         if (element.couldMatchKinds() == Shadow.NO_SHADOW_KINDS_BITS) {
            return element;
         }
      }

      if (apc.couldMatchKinds() == Shadow.NO_SHADOW_KINDS_BITS) {
         return Pointcut.makeMatchesNothing(apc.state);
      } else {
         iter = nodes.iterator();

         Object result;
         for(result = (Pointcut)iter.next(); iter.hasNext(); result = new AndPointcut((Pointcut)result, right)) {
            right = (Pointcut)iter.next();
         }

         return (Pointcut)result;
      }
   }

   private Pointcut sortOrs(Pointcut pc) {
      SortedSet nodes = new TreeSet(new PointcutEvaluationExpenseComparator());
      this.collectOrNodes(pc, nodes);
      Iterator iter = nodes.iterator();

      Object result;
      Pointcut right;
      for(result = (Pointcut)iter.next(); iter.hasNext(); result = new OrPointcut((Pointcut)result, right)) {
         right = (Pointcut)iter.next();
      }

      return (Pointcut)result;
   }

   private Pointcut removeNothings(Pointcut pc) {
      Pointcut right;
      Pointcut left;
      if (this.isAnd(pc)) {
         AndPointcut apc = (AndPointcut)pc;
         right = this.removeNothings(apc.getRight());
         left = this.removeNothings(apc.getLeft());
         return (Pointcut)(!(left instanceof Pointcut.MatchesNothingPointcut) && !(right instanceof Pointcut.MatchesNothingPointcut) ? new AndPointcut(left, right) : new Pointcut.MatchesNothingPointcut());
      } else {
         if (this.isOr(pc)) {
            OrPointcut opc = (OrPointcut)pc;
            right = this.removeNothings(opc.getRight());
            left = this.removeNothings(opc.getLeft());
            if (left instanceof Pointcut.MatchesNothingPointcut && !(right instanceof Pointcut.MatchesNothingPointcut)) {
               return right;
            }

            if (right instanceof Pointcut.MatchesNothingPointcut && !(left instanceof Pointcut.MatchesNothingPointcut)) {
               return left;
            }

            if (!(left instanceof Pointcut.MatchesNothingPointcut) && !(right instanceof Pointcut.MatchesNothingPointcut)) {
               return new OrPointcut(left, right);
            }

            if (left instanceof Pointcut.MatchesNothingPointcut && right instanceof Pointcut.MatchesNothingPointcut) {
               return new Pointcut.MatchesNothingPointcut();
            }
         }

         return pc;
      }
   }

   private void collectAndNodes(AndPointcut apc, Set nodesSoFar) {
      Pointcut left = apc.getLeft();
      Pointcut right = apc.getRight();
      if (this.isAnd(left)) {
         this.collectAndNodes((AndPointcut)left, nodesSoFar);
      } else {
         nodesSoFar.add(left);
      }

      if (this.isAnd(right)) {
         this.collectAndNodes((AndPointcut)right, nodesSoFar);
      } else {
         nodesSoFar.add(right);
      }

   }

   private void collectOrNodes(Pointcut pc, Set nodesSoFar) {
      if (this.isOr(pc)) {
         OrPointcut opc = (OrPointcut)pc;
         this.collectOrNodes(opc.getLeft(), nodesSoFar);
         this.collectOrNodes(opc.getRight(), nodesSoFar);
      } else {
         nodesSoFar.add(pc);
      }

   }

   private boolean isNot(Pointcut pc) {
      return pc instanceof NotPointcut;
   }

   private boolean isAnd(Pointcut pc) {
      return pc instanceof AndPointcut;
   }

   private boolean isOr(Pointcut pc) {
      return pc instanceof OrPointcut;
   }
}
