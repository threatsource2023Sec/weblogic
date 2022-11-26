package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ast.And;
import com.bea.core.repackaged.aspectj.weaver.ast.Call;
import com.bea.core.repackaged.aspectj.weaver.ast.FieldGetCall;
import com.bea.core.repackaged.aspectj.weaver.ast.HasAnnotation;
import com.bea.core.repackaged.aspectj.weaver.ast.ITestVisitor;
import com.bea.core.repackaged.aspectj.weaver.ast.Instanceof;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Not;
import com.bea.core.repackaged.aspectj.weaver.ast.Or;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.MatchingContextBasedTest;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionBasedReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionVar;
import com.bea.core.repackaged.aspectj.weaver.reflect.ShadowMatchImpl;
import com.bea.core.repackaged.aspectj.weaver.tools.ShadowMatch;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

class RuntimeTestWalker {
   private static final Field residualTestField;
   private static final Field varTypeField;
   private static final Field myClassField;
   @Nullable
   private final Test runtimeTest;

   public RuntimeTestWalker(ShadowMatch shadowMatch) {
      try {
         ReflectionUtils.makeAccessible(residualTestField);
         this.runtimeTest = (Test)residualTestField.get(shadowMatch);
      } catch (IllegalAccessException var3) {
         throw new IllegalStateException(var3);
      }
   }

   public boolean testsSubtypeSensitiveVars() {
      return this.runtimeTest != null && (new SubtypeSensitiveVarTypeTestVisitor()).testsSubtypeSensitiveVars(this.runtimeTest);
   }

   public boolean testThisInstanceOfResidue(Class thisClass) {
      return this.runtimeTest != null && (new ThisInstanceOfResidueTestVisitor(thisClass)).thisInstanceOfMatches(this.runtimeTest);
   }

   public boolean testTargetInstanceOfResidue(Class targetClass) {
      return this.runtimeTest != null && (new TargetInstanceOfResidueTestVisitor(targetClass)).targetInstanceOfMatches(this.runtimeTest);
   }

   static {
      try {
         residualTestField = ShadowMatchImpl.class.getDeclaredField("residualTest");
         varTypeField = ReflectionVar.class.getDeclaredField("varType");
         myClassField = ReflectionBasedReferenceTypeDelegate.class.getDeclaredField("myClass");
      } catch (NoSuchFieldException var1) {
         throw new IllegalStateException("The version of aspectjtools.jar / aspectjweaver.jar on the classpath is incompatible with this version of Spring: " + var1);
      }
   }

   private static class SubtypeSensitiveVarTypeTestVisitor extends TestVisitorAdapter {
      private final Object thisObj;
      private final Object targetObj;
      private final Object[] argsObjs;
      private boolean testsSubtypeSensitiveVars;

      private SubtypeSensitiveVarTypeTestVisitor() {
         super(null);
         this.thisObj = new Object();
         this.targetObj = new Object();
         this.argsObjs = new Object[0];
         this.testsSubtypeSensitiveVars = false;
      }

      public boolean testsSubtypeSensitiveVars(Test aTest) {
         aTest.accept(this);
         return this.testsSubtypeSensitiveVars;
      }

      public void visit(Instanceof i) {
         ReflectionVar v = (ReflectionVar)i.getVar();
         Object varUnderTest = v.getBindingAtJoinPoint(this.thisObj, this.targetObj, this.argsObjs);
         if (varUnderTest == this.thisObj || varUnderTest == this.targetObj) {
            this.testsSubtypeSensitiveVars = true;
         }

      }

      public void visit(HasAnnotation hasAnn) {
         ReflectionVar v = (ReflectionVar)hasAnn.getVar();
         int varType = this.getVarType(v);
         if (varType == 3 || varType == 4 || varType == 8) {
            this.testsSubtypeSensitiveVars = true;
         }

      }

      // $FF: synthetic method
      SubtypeSensitiveVarTypeTestVisitor(Object x0) {
         this();
      }
   }

   private static class ThisInstanceOfResidueTestVisitor extends InstanceOfResidueTestVisitor {
      public ThisInstanceOfResidueTestVisitor(Class thisClass) {
         super(thisClass, true, 0);
      }

      public boolean thisInstanceOfMatches(Test test) {
         return this.instanceOfMatches(test);
      }
   }

   private static class TargetInstanceOfResidueTestVisitor extends InstanceOfResidueTestVisitor {
      public TargetInstanceOfResidueTestVisitor(Class targetClass) {
         super(targetClass, false, 1);
      }

      public boolean targetInstanceOfMatches(Test test) {
         return this.instanceOfMatches(test);
      }
   }

   private abstract static class InstanceOfResidueTestVisitor extends TestVisitorAdapter {
      private final Class matchClass;
      private boolean matches;
      private final int matchVarType;

      public InstanceOfResidueTestVisitor(Class matchClass, boolean defaultMatches, int matchVarType) {
         super(null);
         this.matchClass = matchClass;
         this.matches = defaultMatches;
         this.matchVarType = matchVarType;
      }

      public boolean instanceOfMatches(Test test) {
         test.accept(this);
         return this.matches;
      }

      public void visit(Instanceof i) {
         int varType = this.getVarType((ReflectionVar)i.getVar());
         if (varType == this.matchVarType) {
            Class typeClass = null;
            ResolvedType type = (ResolvedType)i.getType();
            if (type instanceof ReferenceType) {
               ReferenceTypeDelegate delegate = ((ReferenceType)type).getDelegate();
               if (delegate instanceof ReflectionBasedReferenceTypeDelegate) {
                  try {
                     ReflectionUtils.makeAccessible(RuntimeTestWalker.myClassField);
                     typeClass = (Class)RuntimeTestWalker.myClassField.get(delegate);
                  } catch (IllegalAccessException var8) {
                     throw new IllegalStateException(var8);
                  }
               }
            }

            try {
               if (typeClass == null) {
                  typeClass = ClassUtils.forName(type.getName(), this.matchClass.getClassLoader());
               }

               this.matches = typeClass.isAssignableFrom(this.matchClass);
            } catch (ClassNotFoundException var7) {
               this.matches = false;
            }

         }
      }
   }

   private static class TestVisitorAdapter implements ITestVisitor {
      protected static final int THIS_VAR = 0;
      protected static final int TARGET_VAR = 1;
      protected static final int AT_THIS_VAR = 3;
      protected static final int AT_TARGET_VAR = 4;
      protected static final int AT_ANNOTATION_VAR = 8;

      private TestVisitorAdapter() {
      }

      public void visit(And e) {
         e.getLeft().accept(this);
         e.getRight().accept(this);
      }

      public void visit(Or e) {
         e.getLeft().accept(this);
         e.getRight().accept(this);
      }

      public void visit(Not e) {
         e.getBody().accept(this);
      }

      public void visit(Instanceof i) {
      }

      public void visit(Literal literal) {
      }

      public void visit(Call call) {
      }

      public void visit(FieldGetCall fieldGetCall) {
      }

      public void visit(HasAnnotation hasAnnotation) {
      }

      public void visit(MatchingContextBasedTest matchingContextTest) {
      }

      protected int getVarType(ReflectionVar v) {
         try {
            ReflectionUtils.makeAccessible(RuntimeTestWalker.varTypeField);
            return (Integer)RuntimeTestWalker.varTypeField.get(v);
         } catch (IllegalAccessException var3) {
            throw new IllegalStateException(var3);
         }
      }

      // $FF: synthetic method
      TestVisitorAdapter(Object x0) {
         this();
      }
   }
}
