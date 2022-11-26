package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Function;
import com.kenai.jffi.ObjectParameterInfo;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jnr.ffi.Runtime;
import jnr.ffi.Variable;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import org.python.objectweb.asm.ClassVisitor;

class AsmBuilder {
   private final Runtime runtime;
   private final String classNamePath;
   private final ClassVisitor classVisitor;
   private final AsmClassLoader classLoader;
   private final ObjectNameGenerator functionId = new ObjectNameGenerator("functionAddress");
   private final ObjectNameGenerator contextId = new ObjectNameGenerator("callContext");
   private final ObjectNameGenerator toNativeConverterId = new ObjectNameGenerator("toNativeConverter");
   private final ObjectNameGenerator toNativeContextId = new ObjectNameGenerator("toNativeContext");
   private final ObjectNameGenerator fromNativeConverterId = new ObjectNameGenerator("fromNativeConverter");
   private final ObjectNameGenerator fromNativeContextId = new ObjectNameGenerator("fromNativeContext");
   private final ObjectNameGenerator objectParameterInfoId = new ObjectNameGenerator("objectParameterInfo");
   private final ObjectNameGenerator variableAccessorId = new ObjectNameGenerator("variableAccessor");
   private final ObjectNameGenerator genericObjectId = new ObjectNameGenerator("objectField");
   private final Map toNativeConverters = new IdentityHashMap();
   private final Map toNativeContexts = new IdentityHashMap();
   private final Map fromNativeConverters = new IdentityHashMap();
   private final Map fromNativeContexts = new IdentityHashMap();
   private final Map objectParameterInfo = new HashMap();
   private final Map variableAccessors = new HashMap();
   private final Map callContextMap = new HashMap();
   private final Map functionAddresses = new HashMap();
   private final Map genericObjects = new IdentityHashMap();
   private final List objectFields = new ArrayList();

   AsmBuilder(Runtime runtime, String classNamePath, ClassVisitor classVisitor, AsmClassLoader classLoader) {
      this.runtime = runtime;
      this.classNamePath = classNamePath;
      this.classVisitor = classVisitor;
      this.classLoader = classLoader;
   }

   public String getClassNamePath() {
      return this.classNamePath;
   }

   ClassVisitor getClassVisitor() {
      return this.classVisitor;
   }

   public AsmClassLoader getClassLoader() {
      return this.classLoader;
   }

   public Runtime getRuntime() {
      return this.runtime;
   }

   ObjectField addField(Map map, Object value, Class klass, ObjectNameGenerator objectNameGenerator) {
      ObjectField field = new ObjectField(objectNameGenerator.generateName(), value, klass);
      this.objectFields.add(field);
      map.put(value, field);
      return field;
   }

   ObjectField getField(Map map, Object value, Class klass, ObjectNameGenerator objectNameGenerator) {
      ObjectField field = (ObjectField)map.get(value);
      return field != null ? field : this.addField(map, value, klass, objectNameGenerator);
   }

   String getCallContextFieldName(Function function) {
      return this.getField(this.callContextMap, function.getCallContext(), CallContext.class, this.contextId).name;
   }

   String getCallContextFieldName(CallContext callContext) {
      return this.getField(this.callContextMap, callContext, CallContext.class, this.contextId).name;
   }

   String getFunctionAddressFieldName(Function function) {
      return this.getField(this.functionAddresses, function.getFunctionAddress(), Long.TYPE, this.functionId).name;
   }

   ObjectField getRuntimeField() {
      return this.getObjectField(this.runtime, this.runtime.getClass());
   }

   String getFromNativeConverterName(FromNativeConverter converter) {
      return this.getFromNativeConverterField(converter).name;
   }

   String getToNativeConverterName(ToNativeConverter converter) {
      return this.getToNativeConverterField(converter).name;
   }

   private static Class nearestClass(Object obj, Class defaultClass) {
      return Modifier.isPublic(obj.getClass().getModifiers()) ? obj.getClass() : defaultClass;
   }

   ObjectField getToNativeConverterField(ToNativeConverter converter) {
      return this.getField(this.toNativeConverters, converter, nearestClass(converter, ToNativeConverter.class), this.toNativeConverterId);
   }

   ObjectField getFromNativeConverterField(FromNativeConverter converter) {
      return this.getField(this.fromNativeConverters, converter, nearestClass(converter, FromNativeConverter.class), this.fromNativeConverterId);
   }

   ObjectField getToNativeContextField(ToNativeContext context) {
      return this.getField(this.toNativeContexts, context, nearestClass(context, ToNativeContext.class), this.toNativeContextId);
   }

   ObjectField getFromNativeContextField(FromNativeContext context) {
      return this.getField(this.fromNativeContexts, context, nearestClass(context, FromNativeContext.class), this.fromNativeContextId);
   }

   String getObjectParameterInfoName(ObjectParameterInfo info) {
      return this.getField(this.objectParameterInfo, info, ObjectParameterInfo.class, this.objectParameterInfoId).name;
   }

   String getObjectFieldName(Object obj, Class klass) {
      return this.getField(this.genericObjects, obj, klass, this.genericObjectId).name;
   }

   ObjectField getObjectField(Object obj, Class klass) {
      return this.getField(this.genericObjects, obj, klass, this.genericObjectId);
   }

   String getVariableName(Variable variableAccessor) {
      return this.getField(this.variableAccessors, variableAccessor, Variable.class, this.variableAccessorId).name;
   }

   ObjectField[] getObjectFieldArray() {
      return (ObjectField[])this.objectFields.toArray(new ObjectField[this.objectFields.size()]);
   }

   Object[] getObjectFieldValues() {
      Object[] fieldObjects = new Object[this.objectFields.size()];
      int i = 0;

      ObjectField f;
      for(Iterator var3 = this.objectFields.iterator(); var3.hasNext(); fieldObjects[i++] = f.value) {
         f = (ObjectField)var3.next();
      }

      return fieldObjects;
   }

   void emitFieldInitialization(SkinnyMethodAdapter init, int objectsParameterIndex) {
      int i = 0;

      ObjectField f;
      for(Iterator var4 = this.objectFields.iterator(); var4.hasNext(); init.putfield(this.getClassNamePath(), f.name, CodegenUtils.ci(f.klass))) {
         f = (ObjectField)var4.next();
         this.getClassVisitor().visitField(18, f.name, CodegenUtils.ci(f.klass), (String)null, (Object)null);
         init.aload(0);
         init.aload(objectsParameterIndex);
         init.pushInt(i++);
         init.aaload();
         if (f.klass.isPrimitive()) {
            Class boxedType = AsmUtil.boxedType(f.klass);
            init.checkcast(boxedType);
            AsmUtil.unboxNumber(init, boxedType, f.klass);
         } else {
            init.checkcast(f.klass);
         }
      }

   }

   public static final class ObjectField {
      public final String name;
      public final Object value;
      public final Class klass;

      public ObjectField(String fieldName, Object fieldValue, Class fieldClass) {
         this.name = fieldName;
         this.value = fieldValue;
         this.klass = fieldClass;
      }
   }

   private static final class ObjectNameGenerator {
      private final String baseName;
      private int value;

      ObjectNameGenerator(String baseName) {
         this.baseName = baseName;
         this.value = 0;
      }

      String generateName() {
         return this.baseName + "_" + ++this.value;
      }
   }
}
