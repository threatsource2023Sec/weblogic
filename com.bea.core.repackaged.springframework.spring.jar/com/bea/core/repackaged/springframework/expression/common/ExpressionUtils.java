package com.bea.core.repackaged.springframework.expression.common;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public abstract class ExpressionUtils {
   @Nullable
   public static Object convertTypedValue(@Nullable EvaluationContext context, TypedValue typedValue, @Nullable Class targetType) {
      Object value = typedValue.getValue();
      if (targetType == null) {
         return value;
      } else if (context != null) {
         return context.getTypeConverter().convertValue(value, typedValue.getTypeDescriptor(), TypeDescriptor.valueOf(targetType));
      } else if (ClassUtils.isAssignableValue(targetType, value)) {
         return value;
      } else {
         throw new EvaluationException("Cannot convert value '" + value + "' to type '" + targetType.getName() + "'");
      }
   }

   public static int toInt(TypeConverter typeConverter, TypedValue typedValue) {
      return (Integer)convertValue(typeConverter, typedValue, Integer.class);
   }

   public static boolean toBoolean(TypeConverter typeConverter, TypedValue typedValue) {
      return (Boolean)convertValue(typeConverter, typedValue, Boolean.class);
   }

   public static double toDouble(TypeConverter typeConverter, TypedValue typedValue) {
      return (Double)convertValue(typeConverter, typedValue, Double.class);
   }

   public static long toLong(TypeConverter typeConverter, TypedValue typedValue) {
      return (Long)convertValue(typeConverter, typedValue, Long.class);
   }

   public static char toChar(TypeConverter typeConverter, TypedValue typedValue) {
      return (Character)convertValue(typeConverter, typedValue, Character.class);
   }

   public static short toShort(TypeConverter typeConverter, TypedValue typedValue) {
      return (Short)convertValue(typeConverter, typedValue, Short.class);
   }

   public static float toFloat(TypeConverter typeConverter, TypedValue typedValue) {
      return (Float)convertValue(typeConverter, typedValue, Float.class);
   }

   public static byte toByte(TypeConverter typeConverter, TypedValue typedValue) {
      return (Byte)convertValue(typeConverter, typedValue, Byte.class);
   }

   private static Object convertValue(TypeConverter typeConverter, TypedValue typedValue, Class targetType) {
      Object result = typeConverter.convertValue(typedValue.getValue(), typedValue.getTypeDescriptor(), TypeDescriptor.valueOf(targetType));
      if (result == null) {
         throw new IllegalStateException("Null conversion result for value [" + typedValue.getValue() + "]");
      } else {
         return result;
      }
   }
}
