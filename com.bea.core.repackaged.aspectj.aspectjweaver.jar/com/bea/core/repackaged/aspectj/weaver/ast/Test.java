package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public abstract class Test extends ASTNode {
   public abstract void accept(ITestVisitor var1);

   public static Test makeAnd(Test a, Test b) {
      if (a == Literal.TRUE) {
         return b == Literal.TRUE ? a : b;
      } else if (b == Literal.TRUE) {
         return a;
      } else {
         return (Test)(a != Literal.FALSE && b != Literal.FALSE ? new And(a, b) : Literal.FALSE);
      }
   }

   public static Test makeOr(Test a, Test b) {
      if (a == Literal.FALSE) {
         return b;
      } else if (b == Literal.FALSE) {
         return a;
      } else {
         return (Test)(a != Literal.TRUE && b != Literal.TRUE ? new Or(a, b) : Literal.TRUE);
      }
   }

   public static Test makeNot(Test a) {
      if (a instanceof Not) {
         return ((Not)a).getBody();
      } else if (a == Literal.TRUE) {
         return Literal.FALSE;
      } else {
         return (Test)(a == Literal.FALSE ? Literal.TRUE : new Not(a));
      }
   }

   public static Test makeInstanceof(Var v, ResolvedType ty) {
      if (ty.equals(ResolvedType.OBJECT)) {
         return Literal.TRUE;
      } else {
         Object e;
         if (ty.isAssignableFrom(v.getType())) {
            e = Literal.TRUE;
         } else if (!ty.isCoerceableFrom(v.getType())) {
            e = Literal.FALSE;
         } else {
            e = new Instanceof(v, ty);
         }

         return (Test)e;
      }
   }

   public static Test makeHasAnnotation(Var v, ResolvedType annTy) {
      return new HasAnnotation(v, annTy);
   }

   public static Test makeCall(Member m, Expr[] args) {
      return new Call(m, args);
   }

   public static Test makeFieldGetCall(Member f, Member m, Expr[] args) {
      return new FieldGetCall(f, m, args);
   }
}
