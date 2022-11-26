package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Opcodes;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.common.ExpressionUtils;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.SpelNode;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public abstract class SpelNodeImpl implements SpelNode, Opcodes {
   private static final SpelNodeImpl[] NO_CHILDREN = new SpelNodeImpl[0];
   protected final int pos;
   protected SpelNodeImpl[] children;
   @Nullable
   private SpelNodeImpl parent;
   @Nullable
   protected volatile String exitTypeDescriptor;

   public SpelNodeImpl(int pos, SpelNodeImpl... operands) {
      this.children = NO_CHILDREN;
      this.pos = pos;
      if (!ObjectUtils.isEmpty((Object[])operands)) {
         this.children = operands;
         SpelNodeImpl[] var3 = operands;
         int var4 = operands.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            SpelNodeImpl operand = var3[var5];
            Assert.notNull(operand, (String)"Operand must not be null");
            operand.parent = this;
         }
      }

   }

   protected boolean nextChildIs(Class... classes) {
      if (this.parent != null) {
         SpelNodeImpl[] peers = this.parent.children;
         int i = 0;

         for(int max = peers.length; i < max; ++i) {
            if (this == peers[i]) {
               if (i + 1 >= max) {
                  return false;
               }

               Class peerClass = peers[i + 1].getClass();
               Class[] var6 = classes;
               int var7 = classes.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Class desiredClass = var6[var8];
                  if (peerClass == desiredClass) {
                     return true;
                  }
               }

               return false;
            }
         }
      }

      return false;
   }

   @Nullable
   public final Object getValue(ExpressionState expressionState) throws EvaluationException {
      return this.getValueInternal(expressionState).getValue();
   }

   public final TypedValue getTypedValue(ExpressionState expressionState) throws EvaluationException {
      return this.getValueInternal(expressionState);
   }

   public boolean isWritable(ExpressionState expressionState) throws EvaluationException {
      return false;
   }

   public void setValue(ExpressionState expressionState, @Nullable Object newValue) throws EvaluationException {
      throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.SETVALUE_NOT_SUPPORTED, new Object[]{this.getClass()});
   }

   public SpelNode getChild(int index) {
      return this.children[index];
   }

   public int getChildCount() {
      return this.children.length;
   }

   @Nullable
   public Class getObjectClass(@Nullable Object obj) {
      if (obj == null) {
         return null;
      } else {
         return obj instanceof Class ? (Class)obj : obj.getClass();
      }
   }

   public int getStartPosition() {
      return this.pos >> 16;
   }

   public int getEndPosition() {
      return this.pos & '\uffff';
   }

   public boolean isCompilable() {
      return false;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      throw new IllegalStateException(this.getClass().getName() + " has no generateCode(..) method");
   }

   @Nullable
   public String getExitDescriptor() {
      return this.exitTypeDescriptor;
   }

   @Nullable
   protected final Object getValue(ExpressionState state, Class desiredReturnType) throws EvaluationException {
      return ExpressionUtils.convertTypedValue(state.getEvaluationContext(), this.getValueInternal(state), desiredReturnType);
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.NOT_ASSIGNABLE, new Object[]{this.toStringAST()});
   }

   public abstract TypedValue getValueInternal(ExpressionState var1) throws EvaluationException;

   protected static void generateCodeForArguments(MethodVisitor mv, CodeFlow cf, Member member, SpelNodeImpl[] arguments) {
      String[] paramDescriptors = null;
      boolean isVarargs = false;
      if (member instanceof Constructor) {
         Constructor ctor = (Constructor)member;
         paramDescriptors = CodeFlow.toDescriptors(ctor.getParameterTypes());
         isVarargs = ctor.isVarArgs();
      } else {
         Method method = (Method)member;
         paramDescriptors = CodeFlow.toDescriptors(method.getParameterTypes());
         isVarargs = method.isVarArgs();
      }

      int p;
      if (isVarargs) {
         int p = false;
         int childCount = arguments.length;

         for(p = 0; p < paramDescriptors.length - 1; ++p) {
            generateCodeForArgument(mv, cf, arguments[p], paramDescriptors[p]);
         }

         SpelNodeImpl lastChild = childCount == 0 ? null : arguments[childCount - 1];
         String arrayType = paramDescriptors[paramDescriptors.length - 1];
         if (lastChild != null && arrayType.equals(lastChild.getExitDescriptor())) {
            generateCodeForArgument(mv, cf, lastChild, paramDescriptors[p]);
         } else {
            arrayType = arrayType.substring(1);
            CodeFlow.insertNewArrayCode(mv, childCount - p, arrayType);

            for(int arrayindex = 0; p < childCount; ++p) {
               SpelNodeImpl child = arguments[p];
               mv.visitInsn(89);
               CodeFlow.insertOptimalLoad(mv, arrayindex++);
               generateCodeForArgument(mv, cf, child, arrayType);
               CodeFlow.insertArrayStore(mv, arrayType);
            }
         }
      } else {
         for(p = 0; p < paramDescriptors.length; ++p) {
            generateCodeForArgument(mv, cf, arguments[p], paramDescriptors[p]);
         }
      }

   }

   protected static void generateCodeForArgument(MethodVisitor mv, CodeFlow cf, SpelNodeImpl argument, String paramDesc) {
      cf.enterCompilationScope();
      argument.generateCode(mv, cf);
      String lastDesc = cf.lastDescriptor();
      Assert.state(lastDesc != null, "No last descriptor");
      boolean primitiveOnStack = CodeFlow.isPrimitive(lastDesc);
      if (primitiveOnStack && paramDesc.charAt(0) == 'L') {
         CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
      } else if (paramDesc.length() == 1 && !primitiveOnStack) {
         CodeFlow.insertUnboxInsns(mv, paramDesc.charAt(0), lastDesc);
      } else if (!paramDesc.equals(lastDesc)) {
         CodeFlow.insertCheckCast(mv, paramDesc);
      }

      cf.exitCompilationScope();
   }
}
