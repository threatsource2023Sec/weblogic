package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.ConstructorExecutor;
import com.bea.core.repackaged.springframework.expression.ConstructorResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.common.ExpressionUtils;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.SpelNode;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectiveConstructorExecutor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConstructorReference extends SpelNodeImpl {
   private boolean isArrayConstructor = false;
   @Nullable
   private SpelNodeImpl[] dimensions;
   @Nullable
   private volatile ConstructorExecutor cachedExecutor;

   public ConstructorReference(int pos, SpelNodeImpl... arguments) {
      super(pos, arguments);
      this.isArrayConstructor = false;
   }

   public ConstructorReference(int pos, SpelNodeImpl[] dimensions, SpelNodeImpl... arguments) {
      super(pos, arguments);
      this.isArrayConstructor = true;
      this.dimensions = dimensions;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      return this.isArrayConstructor ? this.createArray(state) : this.createNewInstance(state);
   }

   private TypedValue createNewInstance(ExpressionState state) throws EvaluationException {
      Object[] arguments = new Object[this.getChildCount() - 1];
      List argumentTypes = new ArrayList(this.getChildCount() - 1);

      for(int i = 0; i < arguments.length; ++i) {
         TypedValue childValue = this.children[i + 1].getValueInternal(state);
         Object value = childValue.getValue();
         arguments[i] = value;
         argumentTypes.add(TypeDescriptor.forObject(value));
      }

      ConstructorExecutor executorToUse = this.cachedExecutor;
      if (executorToUse != null) {
         try {
            return executorToUse.execute(state.getEvaluationContext(), arguments);
         } catch (AccessException var9) {
            if (var9.getCause() instanceof InvocationTargetException) {
               Throwable rootCause = var9.getCause().getCause();
               if (rootCause instanceof RuntimeException) {
                  throw (RuntimeException)rootCause;
               }

               String typeName = (String)this.children[0].getValueInternal(state).getValue();
               throw new SpelEvaluationException(this.getStartPosition(), rootCause, SpelMessage.CONSTRUCTOR_INVOCATION_PROBLEM, new Object[]{typeName, FormatHelper.formatMethodForMessage("", argumentTypes)});
            }

            this.cachedExecutor = null;
         }
      }

      String typeName = (String)this.children[0].getValueInternal(state).getValue();
      Assert.state(typeName != null, "No type name");
      executorToUse = this.findExecutorForConstructor(typeName, argumentTypes, state);

      try {
         this.cachedExecutor = executorToUse;
         if (executorToUse instanceof ReflectiveConstructorExecutor) {
            this.exitTypeDescriptor = CodeFlow.toDescriptor(((ReflectiveConstructorExecutor)executorToUse).getConstructor().getDeclaringClass());
         }

         return executorToUse.execute(state.getEvaluationContext(), arguments);
      } catch (AccessException var8) {
         throw new SpelEvaluationException(this.getStartPosition(), var8, SpelMessage.CONSTRUCTOR_INVOCATION_PROBLEM, new Object[]{typeName, FormatHelper.formatMethodForMessage("", argumentTypes)});
      }
   }

   private ConstructorExecutor findExecutorForConstructor(String typeName, List argumentTypes, ExpressionState state) throws SpelEvaluationException {
      EvaluationContext evalContext = state.getEvaluationContext();
      List ctorResolvers = evalContext.getConstructorResolvers();
      Iterator var6 = ctorResolvers.iterator();

      while(var6.hasNext()) {
         ConstructorResolver ctorResolver = (ConstructorResolver)var6.next();

         try {
            ConstructorExecutor ce = ctorResolver.resolve(state.getEvaluationContext(), typeName, argumentTypes);
            if (ce != null) {
               return ce;
            }
         } catch (AccessException var9) {
            throw new SpelEvaluationException(this.getStartPosition(), var9, SpelMessage.CONSTRUCTOR_INVOCATION_PROBLEM, new Object[]{typeName, FormatHelper.formatMethodForMessage("", argumentTypes)});
         }
      }

      throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.CONSTRUCTOR_NOT_FOUND, new Object[]{typeName, FormatHelper.formatMethodForMessage("", argumentTypes)});
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder("new ");
      int index = 0;
      sb.append(this.getChild(index++).toStringAST());
      sb.append("(");

      for(int i = index; i < this.getChildCount(); ++i) {
         if (i > index) {
            sb.append(",");
         }

         sb.append(this.getChild(i).toStringAST());
      }

      sb.append(")");
      return sb.toString();
   }

   private TypedValue createArray(ExpressionState state) throws EvaluationException {
      Object intendedArrayType = this.getChild(0).getValue(state);
      if (!(intendedArrayType instanceof String)) {
         throw new SpelEvaluationException(this.getChild(0).getStartPosition(), SpelMessage.TYPE_NAME_EXPECTED_FOR_ARRAY_CONSTRUCTION, new Object[]{FormatHelper.formatClassNameForMessage(intendedArrayType != null ? intendedArrayType.getClass() : null)});
      } else {
         String type = (String)intendedArrayType;
         TypeCode arrayTypeCode = TypeCode.forName(type);
         Class componentType;
         if (arrayTypeCode == TypeCode.OBJECT) {
            componentType = state.findType(type);
         } else {
            componentType = arrayTypeCode.getType();
         }

         Object newArray;
         TypeConverter typeConverter;
         int d;
         if (!this.hasInitializer()) {
            if (this.dimensions != null) {
               SpelNodeImpl[] var11 = this.dimensions;
               int var12 = var11.length;

               for(d = 0; d < var12; ++d) {
                  SpelNodeImpl dimension = var11[d];
                  if (dimension == null) {
                     throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.MISSING_ARRAY_DIMENSION, new Object[0]);
                  }
               }
            }

            typeConverter = state.getEvaluationContext().getTypeConverter();
            if (this.dimensions.length == 1) {
               TypedValue o = this.dimensions[0].getTypedValue(state);
               d = ExpressionUtils.toInt(typeConverter, o);
               newArray = Array.newInstance(componentType, d);
            } else {
               int[] dims = new int[this.dimensions.length];

               for(d = 0; d < this.dimensions.length; ++d) {
                  TypedValue o = this.dimensions[d].getTypedValue(state);
                  dims[d] = ExpressionUtils.toInt(typeConverter, o);
               }

               newArray = Array.newInstance(componentType, dims);
            }
         } else {
            if (this.dimensions == null || this.dimensions.length > 1) {
               throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.MULTIDIM_ARRAY_INITIALIZER_NOT_SUPPORTED, new Object[0]);
            }

            typeConverter = state.getEvaluationContext().getTypeConverter();
            InlineList initializer = (InlineList)this.getChild(1);
            if (this.dimensions[0] != null) {
               TypedValue dValue = this.dimensions[0].getTypedValue(state);
               int i = ExpressionUtils.toInt(typeConverter, dValue);
               if (i != initializer.getChildCount()) {
                  throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.INITIALIZER_LENGTH_INCORRECT, new Object[0]);
               }
            }

            d = initializer.getChildCount();
            newArray = Array.newInstance(componentType, d);
            if (arrayTypeCode == TypeCode.OBJECT) {
               this.populateReferenceTypeArray(state, newArray, typeConverter, initializer, componentType);
            } else if (arrayTypeCode == TypeCode.BOOLEAN) {
               this.populateBooleanArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.BYTE) {
               this.populateByteArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.CHAR) {
               this.populateCharArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.DOUBLE) {
               this.populateDoubleArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.FLOAT) {
               this.populateFloatArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.INT) {
               this.populateIntArray(state, newArray, typeConverter, initializer);
            } else if (arrayTypeCode == TypeCode.LONG) {
               this.populateLongArray(state, newArray, typeConverter, initializer);
            } else {
               if (arrayTypeCode != TypeCode.SHORT) {
                  throw new IllegalStateException(arrayTypeCode.name());
               }

               this.populateShortArray(state, newArray, typeConverter, initializer);
            }
         }

         return new TypedValue(newArray);
      }
   }

   private void populateReferenceTypeArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer, Class componentType) {
      TypeDescriptor toTypeDescriptor = TypeDescriptor.valueOf(componentType);
      Object[] newObjectArray = (Object[])((Object[])newArray);

      for(int i = 0; i < newObjectArray.length; ++i) {
         SpelNode elementNode = initializer.getChild(i);
         Object arrayEntry = elementNode.getValue(state);
         newObjectArray[i] = typeConverter.convertValue(arrayEntry, TypeDescriptor.forObject(arrayEntry), toTypeDescriptor);
      }

   }

   private void populateByteArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      byte[] newByteArray = (byte[])((byte[])newArray);

      for(int i = 0; i < newByteArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newByteArray[i] = ExpressionUtils.toByte(typeConverter, typedValue);
      }

   }

   private void populateFloatArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      float[] newFloatArray = (float[])((float[])newArray);

      for(int i = 0; i < newFloatArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newFloatArray[i] = ExpressionUtils.toFloat(typeConverter, typedValue);
      }

   }

   private void populateDoubleArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      double[] newDoubleArray = (double[])((double[])newArray);

      for(int i = 0; i < newDoubleArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newDoubleArray[i] = ExpressionUtils.toDouble(typeConverter, typedValue);
      }

   }

   private void populateShortArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      short[] newShortArray = (short[])((short[])newArray);

      for(int i = 0; i < newShortArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newShortArray[i] = ExpressionUtils.toShort(typeConverter, typedValue);
      }

   }

   private void populateLongArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      long[] newLongArray = (long[])((long[])newArray);

      for(int i = 0; i < newLongArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newLongArray[i] = ExpressionUtils.toLong(typeConverter, typedValue);
      }

   }

   private void populateCharArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      char[] newCharArray = (char[])((char[])newArray);

      for(int i = 0; i < newCharArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newCharArray[i] = ExpressionUtils.toChar(typeConverter, typedValue);
      }

   }

   private void populateBooleanArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      boolean[] newBooleanArray = (boolean[])((boolean[])newArray);

      for(int i = 0; i < newBooleanArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newBooleanArray[i] = ExpressionUtils.toBoolean(typeConverter, typedValue);
      }

   }

   private void populateIntArray(ExpressionState state, Object newArray, TypeConverter typeConverter, InlineList initializer) {
      int[] newIntArray = (int[])((int[])newArray);

      for(int i = 0; i < newIntArray.length; ++i) {
         TypedValue typedValue = initializer.getChild(i).getTypedValue(state);
         newIntArray[i] = ExpressionUtils.toInt(typeConverter, typedValue);
      }

   }

   private boolean hasInitializer() {
      return this.getChildCount() > 1;
   }

   public boolean isCompilable() {
      if (this.cachedExecutor instanceof ReflectiveConstructorExecutor && this.exitTypeDescriptor != null) {
         if (this.getChildCount() > 1) {
            int c = 1;

            for(int max = this.getChildCount(); c < max; ++c) {
               if (!this.children[c].isCompilable()) {
                  return false;
               }
            }
         }

         ReflectiveConstructorExecutor executor = (ReflectiveConstructorExecutor)this.cachedExecutor;
         if (executor == null) {
            return false;
         } else {
            Constructor constructor = executor.getConstructor();
            return Modifier.isPublic(constructor.getModifiers()) && Modifier.isPublic(constructor.getDeclaringClass().getModifiers());
         }
      } else {
         return false;
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      ReflectiveConstructorExecutor executor = (ReflectiveConstructorExecutor)this.cachedExecutor;
      Assert.state(executor != null, "No cached executor");
      Constructor constructor = executor.getConstructor();
      String classDesc = constructor.getDeclaringClass().getName().replace('.', '/');
      mv.visitTypeInsn(187, classDesc);
      mv.visitInsn(89);
      SpelNodeImpl[] arguments = new SpelNodeImpl[this.children.length - 1];
      System.arraycopy(this.children, 1, arguments, 0, this.children.length - 1);
      generateCodeForArguments(mv, cf, constructor, arguments);
      mv.visitMethodInsn(183, classDesc, "<init>", CodeFlow.createSignatureDescriptor(constructor), false);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
