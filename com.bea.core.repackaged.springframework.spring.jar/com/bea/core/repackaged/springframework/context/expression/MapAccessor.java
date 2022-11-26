package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.CompilablePropertyAccessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Map;

public class MapAccessor implements CompilablePropertyAccessor {
   public Class[] getSpecificTargetClasses() {
      return new Class[]{Map.class};
   }

   public boolean canRead(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return target instanceof Map && ((Map)target).containsKey(name);
   }

   public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      Assert.state(target instanceof Map, "Target must be of type Map");
      Map map = (Map)target;
      Object value = map.get(name);
      if (value == null && !map.containsKey(name)) {
         throw new MapAccessException(name);
      } else {
         return new TypedValue(value);
      }
   }

   public boolean canWrite(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
      return true;
   }

   public void write(EvaluationContext context, @Nullable Object target, String name, @Nullable Object newValue) throws AccessException {
      Assert.state(target instanceof Map, "Target must be a Map");
      Map map = (Map)target;
      map.put(name, newValue);
   }

   public boolean isCompilable() {
      return true;
   }

   public Class getPropertyType() {
      return Object.class;
   }

   public void generateCode(String propertyName, MethodVisitor mv, CodeFlow cf) {
      String descriptor = cf.lastDescriptor();
      if (descriptor == null || !descriptor.equals("Ljava/util/Map")) {
         if (descriptor == null) {
            cf.loadTarget(mv);
         }

         CodeFlow.insertCheckCast(mv, "Ljava/util/Map");
      }

      mv.visitLdcInsn(propertyName);
      mv.visitMethodInsn(185, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
   }

   private static class MapAccessException extends AccessException {
      private final String key;

      public MapAccessException(String key) {
         super("");
         this.key = key;
      }

      public String getMessage() {
         return "Map does not contain a value for key '" + this.key + "'";
      }
   }
}
