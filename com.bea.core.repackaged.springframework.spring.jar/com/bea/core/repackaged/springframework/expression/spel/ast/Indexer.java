package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectivePropertyAccessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Indexer extends SpelNodeImpl {
   @Nullable
   private String cachedReadName;
   @Nullable
   private Class cachedReadTargetType;
   @Nullable
   private PropertyAccessor cachedReadAccessor;
   @Nullable
   private String cachedWriteName;
   @Nullable
   private Class cachedWriteTargetType;
   @Nullable
   private PropertyAccessor cachedWriteAccessor;
   @Nullable
   private IndexedType indexedType;

   public Indexer(int pos, SpelNodeImpl expr) {
      super(pos, expr);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      return this.getValueRef(state).getValue();
   }

   public void setValue(ExpressionState state, @Nullable Object newValue) throws EvaluationException {
      this.getValueRef(state).setValue(newValue);
   }

   public boolean isWritable(ExpressionState expressionState) throws SpelEvaluationException {
      return true;
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      TypedValue context = state.getActiveContextObject();
      Object target = context.getValue();
      TypeDescriptor targetDescriptor = context.getTypeDescriptor();
      TypedValue indexValue;
      Object index;
      if (target instanceof Map && this.children[0] instanceof PropertyOrFieldReference) {
         PropertyOrFieldReference reference = (PropertyOrFieldReference)this.children[0];
         index = reference.getName();
         indexValue = new TypedValue(index);
      } else {
         try {
            state.pushActiveContextObject(state.getRootContextObject());
            indexValue = this.children[0].getValueInternal(state);
            index = indexValue.getValue();
            Assert.state(index != null, "No index");
         } finally {
            state.popActiveContextObject();
         }
      }

      if (target == null) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.CANNOT_INDEX_INTO_NULL_VALUE, new Object[0]);
      } else {
         Assert.state(targetDescriptor != null, "No type descriptor");
         if (target instanceof Map) {
            Object key = index;
            if (targetDescriptor.getMapKeyTypeDescriptor() != null) {
               key = state.convertValue(index, targetDescriptor.getMapKeyTypeDescriptor());
            }

            this.indexedType = Indexer.IndexedType.MAP;
            return new MapIndexingValueRef(state.getTypeConverter(), (Map)target, key, targetDescriptor);
         } else if (!target.getClass().isArray() && !(target instanceof Collection) && !(target instanceof String)) {
            TypeDescriptor valueType = indexValue.getTypeDescriptor();
            if (valueType != null && String.class == valueType.getType()) {
               this.indexedType = Indexer.IndexedType.OBJECT;
               return new PropertyIndexingValueRef(target, (String)index, state.getEvaluationContext(), targetDescriptor);
            } else {
               throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.INDEXING_NOT_SUPPORTED_FOR_TYPE, new Object[]{targetDescriptor});
            }
         } else {
            int idx = (Integer)state.convertValue(index, TypeDescriptor.valueOf(Integer.class));
            if (target.getClass().isArray()) {
               this.indexedType = Indexer.IndexedType.ARRAY;
               return new ArrayIndexingValueRef(state.getTypeConverter(), target, idx, targetDescriptor);
            } else if (target instanceof Collection) {
               if (target instanceof List) {
                  this.indexedType = Indexer.IndexedType.LIST;
               }

               return new CollectionIndexingValueRef((Collection)target, idx, targetDescriptor, state.getTypeConverter(), state.getConfiguration().isAutoGrowCollections(), state.getConfiguration().getMaximumAutoGrowSize());
            } else {
               this.indexedType = Indexer.IndexedType.STRING;
               return new StringIndexingLValue((String)target, idx, targetDescriptor);
            }
         }
      }
   }

   public boolean isCompilable() {
      if (this.indexedType == Indexer.IndexedType.ARRAY) {
         return this.exitTypeDescriptor != null;
      } else if (this.indexedType == Indexer.IndexedType.LIST) {
         return this.children[0].isCompilable();
      } else if (this.indexedType == Indexer.IndexedType.MAP) {
         return this.children[0] instanceof PropertyOrFieldReference || this.children[0].isCompilable();
      } else if (this.indexedType != Indexer.IndexedType.OBJECT) {
         return false;
      } else {
         return this.cachedReadAccessor != null && this.cachedReadAccessor instanceof ReflectivePropertyAccessor.OptimalPropertyAccessor && this.getChild(0) instanceof StringLiteral;
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      String descriptor = cf.lastDescriptor();
      if (descriptor == null) {
         cf.loadTarget(mv);
      }

      if (this.indexedType == Indexer.IndexedType.ARRAY) {
         byte insn;
         if ("D".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[D");
            insn = 49;
         } else if ("F".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[F");
            insn = 48;
         } else if ("J".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[J");
            insn = 47;
         } else if ("I".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[I");
            insn = 46;
         } else if ("S".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[S");
            insn = 53;
         } else if ("B".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[B");
            insn = 51;
         } else if ("C".equals(this.exitTypeDescriptor)) {
            mv.visitTypeInsn(192, "[C");
            insn = 52;
         } else {
            mv.visitTypeInsn(192, "[" + this.exitTypeDescriptor + (CodeFlow.isPrimitiveArray(this.exitTypeDescriptor) ? "" : ";"));
            insn = 50;
         }

         SpelNodeImpl index = this.children[0];
         cf.enterCompilationScope();
         index.generateCode(mv, cf);
         cf.exitCompilationScope();
         mv.visitInsn(insn);
      } else if (this.indexedType == Indexer.IndexedType.LIST) {
         mv.visitTypeInsn(192, "java/util/List");
         cf.enterCompilationScope();
         this.children[0].generateCode(mv, cf);
         cf.exitCompilationScope();
         mv.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
      } else if (this.indexedType == Indexer.IndexedType.MAP) {
         mv.visitTypeInsn(192, "java/util/Map");
         if (this.children[0] instanceof PropertyOrFieldReference) {
            PropertyOrFieldReference reference = (PropertyOrFieldReference)this.children[0];
            String mapKeyName = reference.getName();
            mv.visitLdcInsn(mapKeyName);
         } else {
            cf.enterCompilationScope();
            this.children[0].generateCode(mv, cf);
            cf.exitCompilationScope();
         }

         mv.visitMethodInsn(185, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
      } else if (this.indexedType == Indexer.IndexedType.OBJECT) {
         ReflectivePropertyAccessor.OptimalPropertyAccessor accessor = (ReflectivePropertyAccessor.OptimalPropertyAccessor)this.cachedReadAccessor;
         Assert.state(accessor != null, "No cached read accessor");
         Member member = accessor.member;
         boolean isStatic = Modifier.isStatic(member.getModifiers());
         String classDesc = member.getDeclaringClass().getName().replace('.', '/');
         if (!isStatic) {
            if (descriptor == null) {
               cf.loadTarget(mv);
            }

            if (descriptor == null || !classDesc.equals(descriptor.substring(1))) {
               mv.visitTypeInsn(192, classDesc);
            }
         }

         if (member instanceof Method) {
            mv.visitMethodInsn(isStatic ? 184 : 182, classDesc, member.getName(), CodeFlow.createSignatureDescriptor((Method)member), false);
         } else {
            mv.visitFieldInsn(isStatic ? 178 : 180, classDesc, member.getName(), CodeFlow.toJvmDescriptor(((Field)member).getType()));
         }
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder("[");

      for(int i = 0; i < this.getChildCount(); ++i) {
         if (i > 0) {
            sb.append(",");
         }

         sb.append(this.getChild(i).toStringAST());
      }

      sb.append("]");
      return sb.toString();
   }

   private void setArrayElement(TypeConverter converter, Object ctx, int idx, @Nullable Object newValue, Class arrayComponentType) throws EvaluationException {
      if (arrayComponentType == Boolean.TYPE) {
         boolean[] array = (boolean[])((boolean[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Boolean)this.convertValue(converter, newValue, Boolean.class);
      } else if (arrayComponentType == Byte.TYPE) {
         byte[] array = (byte[])((byte[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Byte)this.convertValue(converter, newValue, Byte.class);
      } else if (arrayComponentType == Character.TYPE) {
         char[] array = (char[])((char[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Character)this.convertValue(converter, newValue, Character.class);
      } else if (arrayComponentType == Double.TYPE) {
         double[] array = (double[])((double[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Double)this.convertValue(converter, newValue, Double.class);
      } else if (arrayComponentType == Float.TYPE) {
         float[] array = (float[])((float[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Float)this.convertValue(converter, newValue, Float.class);
      } else if (arrayComponentType == Integer.TYPE) {
         int[] array = (int[])((int[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Integer)this.convertValue(converter, newValue, Integer.class);
      } else if (arrayComponentType == Long.TYPE) {
         long[] array = (long[])((long[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Long)this.convertValue(converter, newValue, Long.class);
      } else if (arrayComponentType == Short.TYPE) {
         short[] array = (short[])((short[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = (Short)this.convertValue(converter, newValue, Short.class);
      } else {
         Object[] array = (Object[])((Object[])ctx);
         this.checkAccess(array.length, idx);
         array[idx] = this.convertValue(converter, newValue, arrayComponentType);
      }

   }

   private Object accessArrayElement(Object ctx, int idx) throws SpelEvaluationException {
      Class arrayComponentType = ctx.getClass().getComponentType();
      if (arrayComponentType == Boolean.TYPE) {
         boolean[] array = (boolean[])((boolean[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "Z";
         return array[idx];
      } else if (arrayComponentType == Byte.TYPE) {
         byte[] array = (byte[])((byte[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "B";
         return array[idx];
      } else if (arrayComponentType == Character.TYPE) {
         char[] array = (char[])((char[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "C";
         return array[idx];
      } else if (arrayComponentType == Double.TYPE) {
         double[] array = (double[])((double[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "D";
         return array[idx];
      } else if (arrayComponentType == Float.TYPE) {
         float[] array = (float[])((float[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "F";
         return array[idx];
      } else if (arrayComponentType == Integer.TYPE) {
         int[] array = (int[])((int[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "I";
         return array[idx];
      } else if (arrayComponentType == Long.TYPE) {
         long[] array = (long[])((long[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "J";
         return array[idx];
      } else if (arrayComponentType == Short.TYPE) {
         short[] array = (short[])((short[])ctx);
         this.checkAccess(array.length, idx);
         this.exitTypeDescriptor = "S";
         return array[idx];
      } else {
         Object[] array = (Object[])((Object[])ctx);
         this.checkAccess(array.length, idx);
         Object retValue = array[idx];
         this.exitTypeDescriptor = CodeFlow.toDescriptor(arrayComponentType);
         return retValue;
      }
   }

   private void checkAccess(int arrayLength, int index) throws SpelEvaluationException {
      if (index > arrayLength) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.ARRAY_INDEX_OUT_OF_BOUNDS, new Object[]{arrayLength, index});
      }
   }

   private Object convertValue(TypeConverter converter, @Nullable Object value, Class targetType) {
      Object result = converter.convertValue(value, TypeDescriptor.forObject(value), TypeDescriptor.valueOf(targetType));
      if (result == null) {
         throw new IllegalStateException("Null conversion result for index [" + value + "]");
      } else {
         return result;
      }
   }

   private class StringIndexingLValue implements ValueRef {
      private final String target;
      private final int index;
      private final TypeDescriptor typeDescriptor;

      public StringIndexingLValue(String target, int index, TypeDescriptor typeDescriptor) {
         this.target = target;
         this.index = index;
         this.typeDescriptor = typeDescriptor;
      }

      public TypedValue getValue() {
         if (this.index >= this.target.length()) {
            throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.STRING_INDEX_OUT_OF_BOUNDS, new Object[]{this.target.length(), this.index});
         } else {
            return new TypedValue(String.valueOf(this.target.charAt(this.index)));
         }
      }

      public void setValue(@Nullable Object newValue) {
         throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.INDEXING_NOT_SUPPORTED_FOR_TYPE, new Object[]{this.typeDescriptor.toString()});
      }

      public boolean isWritable() {
         return true;
      }
   }

   private class CollectionIndexingValueRef implements ValueRef {
      private final Collection collection;
      private final int index;
      private final TypeDescriptor collectionEntryDescriptor;
      private final TypeConverter typeConverter;
      private final boolean growCollection;
      private final int maximumSize;

      public CollectionIndexingValueRef(Collection collection, int index, TypeDescriptor collectionEntryDescriptor, TypeConverter typeConverter, boolean growCollection, int maximumSize) {
         this.collection = collection;
         this.index = index;
         this.collectionEntryDescriptor = collectionEntryDescriptor;
         this.typeConverter = typeConverter;
         this.growCollection = growCollection;
         this.maximumSize = maximumSize;
      }

      public TypedValue getValue() {
         this.growCollectionIfNecessary();
         if (this.collection instanceof List) {
            Object ox = ((List)this.collection).get(this.index);
            Indexer.this.exitTypeDescriptor = CodeFlow.toDescriptor(Object.class);
            return new TypedValue(ox, this.collectionEntryDescriptor.elementTypeDescriptor(ox));
         } else {
            int pos = 0;

            for(Iterator var2 = this.collection.iterator(); var2.hasNext(); ++pos) {
               Object o = var2.next();
               if (pos == this.index) {
                  return new TypedValue(o, this.collectionEntryDescriptor.elementTypeDescriptor(o));
               }
            }

            throw new IllegalStateException("Failed to find indexed element " + this.index + ": " + this.collection);
         }
      }

      public void setValue(@Nullable Object newValue) {
         this.growCollectionIfNecessary();
         if (this.collection instanceof List) {
            List list = (List)this.collection;
            if (this.collectionEntryDescriptor.getElementTypeDescriptor() != null) {
               newValue = this.typeConverter.convertValue(newValue, TypeDescriptor.forObject(newValue), this.collectionEntryDescriptor.getElementTypeDescriptor());
            }

            list.set(this.index, newValue);
         } else {
            throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.INDEXING_NOT_SUPPORTED_FOR_TYPE, new Object[]{this.collectionEntryDescriptor.toString()});
         }
      }

      private void growCollectionIfNecessary() {
         if (this.index >= this.collection.size()) {
            if (!this.growCollection) {
               throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.COLLECTION_INDEX_OUT_OF_BOUNDS, new Object[]{this.collection.size(), this.index});
            }

            if (this.index >= this.maximumSize) {
               throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.UNABLE_TO_GROW_COLLECTION, new Object[0]);
            }

            if (this.collectionEntryDescriptor.getElementTypeDescriptor() == null) {
               throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.UNABLE_TO_GROW_COLLECTION_UNKNOWN_ELEMENT_TYPE, new Object[0]);
            }

            TypeDescriptor elementType = this.collectionEntryDescriptor.getElementTypeDescriptor();

            try {
               Constructor ctor = ReflectionUtils.accessibleConstructor(elementType.getType());

               for(int newElements = this.index - this.collection.size(); newElements >= 0; --newElements) {
                  this.collection.add(ctor.newInstance());
               }
            } catch (Throwable var4) {
               throw new SpelEvaluationException(Indexer.this.getStartPosition(), var4, SpelMessage.UNABLE_TO_GROW_COLLECTION, new Object[0]);
            }
         }

      }

      public boolean isWritable() {
         return true;
      }
   }

   private class PropertyIndexingValueRef implements ValueRef {
      private final Object targetObject;
      private final String name;
      private final EvaluationContext evaluationContext;
      private final TypeDescriptor targetObjectTypeDescriptor;

      public PropertyIndexingValueRef(Object targetObject, String value, EvaluationContext evaluationContext, TypeDescriptor targetObjectTypeDescriptor) {
         this.targetObject = targetObject;
         this.name = value;
         this.evaluationContext = evaluationContext;
         this.targetObjectTypeDescriptor = targetObjectTypeDescriptor;
      }

      public TypedValue getValue() {
         Class targetObjectRuntimeClass = Indexer.this.getObjectClass(this.targetObject);

         try {
            if (Indexer.this.cachedReadName != null && Indexer.this.cachedReadName.equals(this.name) && Indexer.this.cachedReadTargetType != null && Indexer.this.cachedReadTargetType.equals(targetObjectRuntimeClass)) {
               PropertyAccessor accessor = Indexer.this.cachedReadAccessor;
               Assert.state(accessor != null, "No cached read accessor");
               return accessor.read(this.evaluationContext, this.targetObject, this.name);
            }

            List accessorsToTry = AstUtils.getPropertyAccessorsToTry(targetObjectRuntimeClass, this.evaluationContext.getPropertyAccessors());
            Iterator var3 = accessorsToTry.iterator();

            while(var3.hasNext()) {
               PropertyAccessor accessorx = (PropertyAccessor)var3.next();
               if (accessorx.canRead(this.evaluationContext, this.targetObject, this.name)) {
                  if (accessorx instanceof ReflectivePropertyAccessor) {
                     accessorx = ((ReflectivePropertyAccessor)accessorx).createOptimalAccessor(this.evaluationContext, this.targetObject, this.name);
                  }

                  Indexer.this.cachedReadAccessor = accessorx;
                  Indexer.this.cachedReadName = this.name;
                  Indexer.this.cachedReadTargetType = targetObjectRuntimeClass;
                  if (accessorx instanceof ReflectivePropertyAccessor.OptimalPropertyAccessor) {
                     ReflectivePropertyAccessor.OptimalPropertyAccessor optimalAccessor = (ReflectivePropertyAccessor.OptimalPropertyAccessor)accessorx;
                     Member member = optimalAccessor.member;
                     Indexer.this.exitTypeDescriptor = CodeFlow.toDescriptor(member instanceof Method ? ((Method)member).getReturnType() : ((Field)member).getType());
                  }

                  return accessorx.read(this.evaluationContext, this.targetObject, this.name);
               }
            }
         } catch (AccessException var7) {
            throw new SpelEvaluationException(Indexer.this.getStartPosition(), var7, SpelMessage.INDEXING_NOT_SUPPORTED_FOR_TYPE, new Object[]{this.targetObjectTypeDescriptor.toString()});
         }

         throw new SpelEvaluationException(Indexer.this.getStartPosition(), SpelMessage.INDEXING_NOT_SUPPORTED_FOR_TYPE, new Object[]{this.targetObjectTypeDescriptor.toString()});
      }

      public void setValue(@Nullable Object newValue) {
         Class contextObjectClass = Indexer.this.getObjectClass(this.targetObject);

         try {
            if (Indexer.this.cachedWriteName != null && Indexer.this.cachedWriteName.equals(this.name) && Indexer.this.cachedWriteTargetType != null && Indexer.this.cachedWriteTargetType.equals(contextObjectClass)) {
               PropertyAccessor accessor = Indexer.this.cachedWriteAccessor;
               Assert.state(accessor != null, "No cached write accessor");
               accessor.write(this.evaluationContext, this.targetObject, this.name, newValue);
            } else {
               List accessorsToTry = AstUtils.getPropertyAccessorsToTry(contextObjectClass, this.evaluationContext.getPropertyAccessors());
               Iterator var4 = accessorsToTry.iterator();

               PropertyAccessor accessorx;
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  accessorx = (PropertyAccessor)var4.next();
               } while(!accessorx.canWrite(this.evaluationContext, this.targetObject, this.name));

               Indexer.this.cachedWriteName = this.name;
               Indexer.this.cachedWriteTargetType = contextObjectClass;
               Indexer.this.cachedWriteAccessor = accessorx;
               accessorx.write(this.evaluationContext, this.targetObject, this.name, newValue);
            }
         } catch (AccessException var6) {
            throw new SpelEvaluationException(Indexer.this.getStartPosition(), var6, SpelMessage.EXCEPTION_DURING_PROPERTY_WRITE, new Object[]{this.name, var6.getMessage()});
         }
      }

      public boolean isWritable() {
         return true;
      }
   }

   private class MapIndexingValueRef implements ValueRef {
      private final TypeConverter typeConverter;
      private final Map map;
      @Nullable
      private final Object key;
      private final TypeDescriptor mapEntryDescriptor;

      public MapIndexingValueRef(TypeConverter typeConverter, Map map, @Nullable Object key, TypeDescriptor mapEntryDescriptor) {
         this.typeConverter = typeConverter;
         this.map = map;
         this.key = key;
         this.mapEntryDescriptor = mapEntryDescriptor;
      }

      public TypedValue getValue() {
         Object value = this.map.get(this.key);
         Indexer.this.exitTypeDescriptor = CodeFlow.toDescriptor(Object.class);
         return new TypedValue(value, this.mapEntryDescriptor.getMapValueTypeDescriptor(value));
      }

      public void setValue(@Nullable Object newValue) {
         if (this.mapEntryDescriptor.getMapValueTypeDescriptor() != null) {
            newValue = this.typeConverter.convertValue(newValue, TypeDescriptor.forObject(newValue), this.mapEntryDescriptor.getMapValueTypeDescriptor());
         }

         this.map.put(this.key, newValue);
      }

      public boolean isWritable() {
         return true;
      }
   }

   private class ArrayIndexingValueRef implements ValueRef {
      private final TypeConverter typeConverter;
      private final Object array;
      private final int index;
      private final TypeDescriptor typeDescriptor;

      ArrayIndexingValueRef(TypeConverter typeConverter, Object array, int index, TypeDescriptor typeDescriptor) {
         this.typeConverter = typeConverter;
         this.array = array;
         this.index = index;
         this.typeDescriptor = typeDescriptor;
      }

      public TypedValue getValue() {
         Object arrayElement = Indexer.this.accessArrayElement(this.array, this.index);
         return new TypedValue(arrayElement, this.typeDescriptor.elementTypeDescriptor(arrayElement));
      }

      public void setValue(@Nullable Object newValue) {
         TypeDescriptor elementType = this.typeDescriptor.getElementTypeDescriptor();
         Assert.state(elementType != null, "No element type");
         Indexer.this.setArrayElement(this.typeConverter, this.array, this.index, newValue, elementType.getType());
      }

      public boolean isWritable() {
         return true;
      }
   }

   private static enum IndexedType {
      ARRAY,
      LIST,
      MAP,
      STRING,
      OBJECT;
   }
}
