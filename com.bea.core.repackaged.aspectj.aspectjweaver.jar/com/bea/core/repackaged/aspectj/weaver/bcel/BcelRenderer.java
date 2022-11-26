package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ReferenceType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberImpl;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.ast.And;
import com.bea.core.repackaged.aspectj.weaver.ast.Call;
import com.bea.core.repackaged.aspectj.weaver.ast.CallExpr;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.FieldGet;
import com.bea.core.repackaged.aspectj.weaver.ast.FieldGetCall;
import com.bea.core.repackaged.aspectj.weaver.ast.HasAnnotation;
import com.bea.core.repackaged.aspectj.weaver.ast.IExprVisitor;
import com.bea.core.repackaged.aspectj.weaver.ast.ITestVisitor;
import com.bea.core.repackaged.aspectj.weaver.ast.Instanceof;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Not;
import com.bea.core.repackaged.aspectj.weaver.ast.Or;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.MatchingContextBasedTest;

public final class BcelRenderer implements ITestVisitor, IExprVisitor {
   private InstructionList instructions;
   private InstructionFactory fact;
   private BcelWorld world;
   InstructionHandle sk;
   InstructionHandle fk;
   InstructionHandle next = null;

   private BcelRenderer(InstructionFactory fact, BcelWorld world) {
      this.fact = fact;
      this.world = world;
      this.instructions = new InstructionList();
   }

   public static InstructionList renderExpr(InstructionFactory fact, BcelWorld world, Expr e) {
      BcelRenderer renderer = new BcelRenderer(fact, world);
      e.accept(renderer);
      return renderer.instructions;
   }

   public static InstructionList renderExpr(InstructionFactory fact, BcelWorld world, Expr e, Type desiredType) {
      BcelRenderer renderer = new BcelRenderer(fact, world);
      e.accept(renderer);
      InstructionList il = renderer.instructions;
      il.append(Utility.createConversion(fact, BcelWorld.makeBcelType((UnresolvedType)e.getType()), desiredType));
      return il;
   }

   public static InstructionList renderExprs(InstructionFactory fact, BcelWorld world, Expr[] es) {
      BcelRenderer renderer = new BcelRenderer(fact, world);

      for(int i = es.length - 1; i >= 0; --i) {
         es[i].accept(renderer);
      }

      return renderer.instructions;
   }

   public static InstructionList renderTest(InstructionFactory fact, BcelWorld world, Test e, InstructionHandle sk, InstructionHandle fk, InstructionHandle next) {
      BcelRenderer renderer = new BcelRenderer(fact, world);
      renderer.recur(e, sk, fk, next);
      return renderer.instructions;
   }

   private void recur(Test e, InstructionHandle sk, InstructionHandle fk, InstructionHandle next) {
      this.sk = sk;
      this.fk = fk;
      this.next = next;
      e.accept(this);
   }

   public void visit(And e) {
      InstructionHandle savedFk = this.fk;
      this.recur(e.getRight(), this.sk, this.fk, this.next);
      InstructionHandle ning = this.instructions.getStart();
      this.recur(e.getLeft(), ning, savedFk, ning);
   }

   public void visit(Or e) {
      InstructionHandle savedSk = this.sk;
      this.recur(e.getRight(), this.sk, this.fk, this.next);
      this.recur(e.getLeft(), savedSk, this.instructions.getStart(), this.instructions.getStart());
   }

   public void visit(Not e) {
      this.recur(e.getBody(), this.fk, this.sk, this.next);
   }

   public void visit(Instanceof i) {
      this.instructions.insert(this.createJumpBasedOnBooleanOnStack());
      this.instructions.insert(Utility.createInstanceof(this.fact, (ReferenceType)BcelWorld.makeBcelType(i.getType())));
      i.getVar().accept(this);
   }

   public void visit(HasAnnotation hasAnnotation) {
      InstructionList il = new InstructionList();
      il.append(InstructionFactory.createBranchInstruction((short)198, this.fk));
      il.append(((BcelVar)hasAnnotation.getVar()).createLoad(this.fact));
      Member getClass = MemberImpl.method(UnresolvedType.OBJECT, 0, UnresolvedType.JL_CLASS, "getClass", UnresolvedType.NONE);
      il.append(Utility.createInvoke(this.fact, this.world, getClass));
      il.append(this.fact.createConstant(new ObjectType(hasAnnotation.getAnnotationType().getName())));
      Member isAnnotationPresent = MemberImpl.method(UnresolvedType.JL_CLASS, 0, UnresolvedType.BOOLEAN, "isAnnotationPresent", new UnresolvedType[]{UnresolvedType.JL_CLASS});
      il.append(Utility.createInvoke(this.fact, this.world, isAnnotationPresent));
      il.append(this.createJumpBasedOnBooleanOnStack());
      this.instructions.insert(il);
      hasAnnotation.getVar().accept(this);
   }

   public void visit(MatchingContextBasedTest matchingContextTest) {
      throw new UnsupportedOperationException("matching context extension not supported in bytecode weaving");
   }

   private InstructionList createJumpBasedOnBooleanOnStack() {
      InstructionList il = new InstructionList();
      if (this.sk == this.fk) {
         if (this.sk != this.next) {
            il.insert(InstructionFactory.createBranchInstruction((short)167, this.sk));
         }

         return il;
      } else {
         if (this.fk == this.next) {
            il.insert(InstructionFactory.createBranchInstruction((short)154, this.sk));
         } else if (this.sk == this.next) {
            il.insert(InstructionFactory.createBranchInstruction((short)153, this.fk));
         } else {
            il.insert(InstructionFactory.createBranchInstruction((short)167, this.sk));
            il.insert(InstructionFactory.createBranchInstruction((short)153, this.fk));
         }

         return il;
      }
   }

   public void visit(Literal literal) {
      if (literal == Literal.FALSE) {
         throw new BCException("visiting a false expression");
      }
   }

   public void visit(Call call) {
      Member method = call.getMethod();
      Expr[] args = call.getArgs();
      InstructionList callIl = new InstructionList();
      int i = 0;

      for(int len = args.length; i < len; ++i) {
         Type desiredType = BcelWorld.makeBcelType(method.getParameterTypes()[i]);
         Expr arg = args[i];
         if (arg == null) {
            InstructionList iList = new InstructionList();
            iList.append(InstructionFactory.createNull(desiredType));
            callIl.append(iList);
         } else {
            callIl.append(renderExpr(this.fact, this.world, arg, desiredType));
         }
      }

      callIl.append(Utility.createInvoke(this.fact, this.world, method));
      callIl.append(this.createJumpBasedOnBooleanOnStack());
      this.instructions.insert(callIl);
   }

   public void visit(FieldGetCall fieldGetCall) {
      Member field = fieldGetCall.getField();
      Member method = fieldGetCall.getMethod();
      InstructionList il = new InstructionList();
      il.append(Utility.createGet(this.fact, field));
      Expr[] args = fieldGetCall.getArgs();
      il.append(renderExprs(this.fact, this.world, args));
      il.append(Utility.createInvoke(this.fact, this.world, method));
      il.append(this.createJumpBasedOnBooleanOnStack());
      this.instructions.insert(il);
   }

   public void visit(Var var) {
      BcelVar bvar = (BcelVar)var;
      bvar.insertLoad(this.instructions, this.fact);
   }

   public void visit(FieldGet fieldGet) {
      Member field = fieldGet.getField();
      this.instructions.insert(Utility.createGet(this.fact, field));
   }

   public void visit(CallExpr call) {
      Member method = call.getMethod();
      Expr[] args = call.getArgs();
      InstructionList callIl = renderExprs(this.fact, this.world, args);
      callIl.append(Utility.createInvoke(this.fact, this.world, method));
      this.instructions.insert(callIl);
   }
}
