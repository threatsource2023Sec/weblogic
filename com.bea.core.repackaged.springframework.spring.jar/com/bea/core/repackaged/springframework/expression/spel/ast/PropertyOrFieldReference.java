package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.CompilablePropertyAccessor;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectivePropertyAccessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PropertyOrFieldReference extends SpelNodeImpl {
   private final boolean nullSafe;
   private final String name;
   @Nullable
   private String originalPrimitiveExitTypeDescriptor;
   @Nullable
   private volatile PropertyAccessor cachedReadAccessor;
   @Nullable
   private volatile PropertyAccessor cachedWriteAccessor;

   public PropertyOrFieldReference(boolean nullSafe, String propertyOrFieldName, int pos) {
      super(pos);
      this.nullSafe = nullSafe;
      this.name = propertyOrFieldName;
   }

   public boolean isNullSafe() {
      return this.nullSafe;
   }

   public String getName() {
      return this.name;
   }

   public ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      return new AccessorLValue(this, state.getActiveContextObject(), state.getEvaluationContext(), state.getConfiguration().isAutoGrowNullReferences());
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      TypedValue tv = this.getValueInternal(state.getActiveContextObject(), state.getEvaluationContext(), state.getConfiguration().isAutoGrowNullReferences());
      PropertyAccessor accessorToUse = this.cachedReadAccessor;
      if (accessorToUse instanceof CompilablePropertyAccessor) {
         CompilablePropertyAccessor accessor = (CompilablePropertyAccessor)accessorToUse;
         this.setExitTypeDescriptor(CodeFlow.toDescriptor(accessor.getPropertyType()));
      }

      return tv;
   }

   private TypedValue getValueInternal(TypedValue contextObject, EvaluationContext evalContext, boolean isAutoGrowNullReferences) throws EvaluationException {
      TypedValue result = this.readProperty(contextObject, evalContext, this.name);
      if (result.getValue() == null && isAutoGrowNullReferences && this.nextChildIs(new Class[]{Indexer.class, PropertyOrFieldReference.class})) {
         TypeDescriptor resultDescriptor = result.getTypeDescriptor();
         Assert.state(resultDescriptor != null, "No result type");
         if (List.class == resultDescriptor.getType()) {
            if (this.isWritableProperty(this.name, contextObject, evalContext)) {
               List newList = new ArrayList();
               this.writeProperty(contextObject, evalContext, this.name, newList);
               result = this.readProperty(contextObject, evalContext, this.name);
            }
         } else if (Map.class == resultDescriptor.getType()) {
            if (this.isWritableProperty(this.name, contextObject, evalContext)) {
               Map newMap = new HashMap();
               this.writeProperty(contextObject, evalContext, this.name, newMap);
               result = this.readProperty(contextObject, evalContext, this.name);
            }
         } else {
            try {
               if (this.isWritableProperty(this.name, contextObject, evalContext)) {
                  Class clazz = result.getTypeDescriptor().getType();
                  Object newObject = ReflectionUtils.accessibleConstructor(clazz).newInstance();
                  this.writeProperty(contextObject, evalContext, this.name, newObject);
                  result = this.readProperty(contextObject, evalContext, this.name);
               }
            } catch (InvocationTargetException var8) {
               throw new SpelEvaluationException(this.getStartPosition(), var8.getTargetException(), SpelMessage.UNABLE_TO_DYNAMICALLY_CREATE_OBJECT, new Object[]{result.getTypeDescriptor().getType()});
            } catch (Throwable var9) {
               throw new SpelEvaluationException(this.getStartPosition(), var9, SpelMessage.UNABLE_TO_DYNAMICALLY_CREATE_OBJECT, new Object[]{result.getTypeDescriptor().getType()});
            }
         }
      }

      return result;
   }

   public void setValue(ExpressionState state, @Nullable Object newValue) throws EvaluationException {
      this.writeProperty(state.getActiveContextObject(), state.getEvaluationContext(), this.name, newValue);
   }

   public boolean isWritable(ExpressionState state) throws EvaluationException {
      return this.isWritableProperty(this.name, state.getActiveContextObject(), state.getEvaluationContext());
   }

   public String toStringAST() {
      return this.name;
   }

   private TypedValue readProperty(TypedValue contextObject, EvaluationContext evalContext, String name) throws EvaluationException {
      Object targetObject = contextObject.getValue();
      if (targetObject == null && this.nullSafe) {
         return TypedValue.NULL;
      } else {
         PropertyAccessor accessorToUse = this.cachedReadAccessor;
         if (accessorToUse != null) {
            if (evalContext.getPropertyAccessors().contains(accessorToUse)) {
               try {
                  return accessorToUse.read(evalContext, contextObject.getValue(), name);
               } catch (Exception var10) {
               }
            }

            this.cachedReadAccessor = null;
         }

         List accessorsToTry = this.getPropertyAccessorsToTry(contextObject.getValue(), evalContext.getPropertyAccessors());

         try {
            Iterator var7 = accessorsToTry.iterator();

            while(var7.hasNext()) {
               PropertyAccessor accessor = (PropertyAccessor)var7.next();
               if (accessor.canRead(evalContext, contextObject.getValue(), name)) {
                  if (accessor instanceof ReflectivePropertyAccessor) {
                     accessor = ((ReflectivePropertyAccessor)accessor).createOptimalAccessor(evalContext, contextObject.getValue(), name);
                  }

                  this.cachedReadAccessor = accessor;
                  return accessor.read(evalContext, contextObject.getValue(), name);
               }
            }
         } catch (Exception var9) {
            throw new SpelEvaluationException(var9, SpelMessage.EXCEPTION_DURING_PROPERTY_READ, new Object[]{name, var9.getMessage()});
         }

         if (contextObject.getValue() == null) {
            throw new SpelEvaluationException(SpelMessage.PROPERTY_OR_FIELD_NOT_READABLE_ON_NULL, new Object[]{name});
         } else {
            throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.PROPERTY_OR_FIELD_NOT_READABLE, new Object[]{name, FormatHelper.formatClassNameForMessage(this.getObjectClass(contextObject.getValue()))});
         }
      }
   }

   private void writeProperty(TypedValue contextObject, EvaluationContext evalContext, String name, @Nullable Object newValue) throws EvaluationException {
      if (contextObject.getValue() != null || !this.nullSafe) {
         if (contextObject.getValue() == null) {
            throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.PROPERTY_OR_FIELD_NOT_WRITABLE_ON_NULL, new Object[]{name});
         } else {
            PropertyAccessor accessorToUse = this.cachedWriteAccessor;
            if (accessorToUse != null) {
               if (evalContext.getPropertyAccessors().contains(accessorToUse)) {
                  try {
                     accessorToUse.write(evalContext, contextObject.getValue(), name, newValue);
                     return;
                  } catch (Exception var10) {
                  }
               }

               this.cachedWriteAccessor = null;
            }

            List accessorsToTry = this.getPropertyAccessorsToTry(contextObject.getValue(), evalContext.getPropertyAccessors());

            try {
               Iterator var7 = accessorsToTry.iterator();

               while(var7.hasNext()) {
                  PropertyAccessor accessor = (PropertyAccessor)var7.next();
                  if (accessor.canWrite(evalContext, contextObject.getValue(), name)) {
                     this.cachedWriteAccessor = accessor;
                     accessor.write(evalContext, contextObject.getValue(), name, newValue);
                     return;
                  }
               }
            } catch (AccessException var9) {
               throw new SpelEvaluationException(this.getStartPosition(), var9, SpelMessage.EXCEPTION_DURING_PROPERTY_WRITE, new Object[]{name, var9.getMessage()});
            }

            throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.PROPERTY_OR_FIELD_NOT_WRITABLE, new Object[]{name, FormatHelper.formatClassNameForMessage(this.getObjectClass(contextObject.getValue()))});
         }
      }
   }

   public boolean isWritableProperty(String name, TypedValue contextObject, EvaluationContext evalContext) throws EvaluationException {
      Object value = contextObject.getValue();
      if (value != null) {
         List accessorsToTry = this.getPropertyAccessorsToTry(contextObject.getValue(), evalContext.getPropertyAccessors());
         Iterator var6 = accessorsToTry.iterator();

         while(var6.hasNext()) {
            PropertyAccessor accessor = (PropertyAccessor)var6.next();

            try {
               if (accessor.canWrite(evalContext, value, name)) {
                  return true;
               }
            } catch (AccessException var9) {
            }
         }
      }

      return false;
   }

   private List getPropertyAccessorsToTry(@Nullable Object contextObject, List propertyAccessors) {
      Class targetType = contextObject != null ? contextObject.getClass() : null;
      List specificAccessors = new ArrayList();
      List generalAccessors = new ArrayList();
      Iterator var6 = propertyAccessors.iterator();

      while(true) {
         while(var6.hasNext()) {
            PropertyAccessor resolver = (PropertyAccessor)var6.next();
            Class[] targets = resolver.getSpecificTargetClasses();
            if (targets == null) {
               generalAccessors.add(resolver);
            } else if (targetType != null) {
               Class[] var9 = targets;
               int var10 = targets.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  Class clazz = var9[var11];
                  if (clazz == targetType) {
                     specificAccessors.add(resolver);
                     break;
                  }

                  if (clazz.isAssignableFrom(targetType)) {
                     generalAccessors.add(resolver);
                  }
               }
            }
         }

         List resolvers = new ArrayList(specificAccessors);
         generalAccessors.removeAll(specificAccessors);
         resolvers.addAll(generalAccessors);
         return resolvers;
      }
   }

   public boolean isCompilable() {
      PropertyAccessor accessorToUse = this.cachedReadAccessor;
      return accessorToUse instanceof CompilablePropertyAccessor && ((CompilablePropertyAccessor)accessorToUse).isCompilable();
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      PropertyAccessor accessorToUse = this.cachedReadAccessor;
      if (!(accessorToUse instanceof CompilablePropertyAccessor)) {
         throw new IllegalStateException("Property accessor is not compilable: " + accessorToUse);
      } else {
         Label skipIfNull = null;
         if (this.nullSafe) {
            mv.visitInsn(89);
            skipIfNull = new Label();
            Label continueLabel = new Label();
            mv.visitJumpInsn(199, continueLabel);
            CodeFlow.insertCheckCast(mv, this.exitTypeDescriptor);
            mv.visitJumpInsn(167, skipIfNull);
            mv.visitLabel(continueLabel);
         }

         ((CompilablePropertyAccessor)accessorToUse).generateCode(this.name, mv, cf);
         cf.pushDescriptor(this.exitTypeDescriptor);
         if (this.originalPrimitiveExitTypeDescriptor != null) {
            CodeFlow.insertBoxIfNecessary(mv, this.originalPrimitiveExitTypeDescriptor);
         }

         if (skipIfNull != null) {
            mv.visitLabel(skipIfNull);
         }

      }
   }

   void setExitTypeDescriptor(String descriptor) {
      if (this.nullSafe && CodeFlow.isPrimitive(descriptor)) {
         this.originalPrimitiveExitTypeDescriptor = descriptor;
         this.exitTypeDescriptor = CodeFlow.toBoxedDescriptor(descriptor);
      } else {
         this.exitTypeDescriptor = descriptor;
      }

   }

   private static class AccessorLValue implements ValueRef {
      private final PropertyOrFieldReference ref;
      private final TypedValue contextObject;
      private final EvaluationContext evalContext;
      private final boolean autoGrowNullReferences;

      public AccessorLValue(PropertyOrFieldReference propertyOrFieldReference, TypedValue activeContextObject, EvaluationContext evalContext, boolean autoGrowNullReferences) {
         this.ref = propertyOrFieldReference;
         this.contextObject = activeContextObject;
         this.evalContext = evalContext;
         this.autoGrowNullReferences = autoGrowNullReferences;
      }

      public TypedValue getValue() {
         TypedValue value = this.ref.getValueInternal(this.contextObject, this.evalContext, this.autoGrowNullReferences);
         PropertyAccessor accessorToUse = this.ref.cachedReadAccessor;
         if (accessorToUse instanceof CompilablePropertyAccessor) {
            this.ref.setExitTypeDescriptor(CodeFlow.toDescriptor(((CompilablePropertyAccessor)accessorToUse).getPropertyType()));
         }

         return value;
      }

      public void setValue(@Nullable Object newValue) {
         this.ref.writeProperty(this.contextObject, this.evalContext, this.ref.name, newValue);
      }

      public boolean isWritable() {
         return this.ref.isWritableProperty(this.ref.name, this.contextObject, this.evalContext);
      }
   }
}
