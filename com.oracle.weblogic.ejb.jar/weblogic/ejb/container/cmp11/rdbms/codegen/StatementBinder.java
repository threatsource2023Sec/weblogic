package weblogic.ejb.container.cmp11.rdbms.codegen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import weblogic.utils.AssertionError;

public final class StatementBinder {
   public static String getStatementTypeNameForClass(Class type) {
      if (!type.isPrimitive()) {
         if (type == String.class) {
            return "String";
         } else if (type == Boolean.class) {
            return "Boolean";
         } else if (type == Byte.class) {
            return "Byte";
         } else if (type == Character.class) {
            return "String";
         } else if (type == Double.class) {
            return "Double";
         } else if (type == Float.class) {
            return "Float";
         } else if (type == Integer.class) {
            return "Int";
         } else if (type == Long.class) {
            return "Long";
         } else if (type == Short.class) {
            return "Short";
         } else if (type == Date.class) {
            return "Timestamp";
         } else if (type == java.sql.Date.class) {
            return "Date";
         } else if (type == Time.class) {
            return "Time";
         } else if (type == Timestamp.class) {
            return "Timestamp";
         } else if (type == BigDecimal.class) {
            return "BigDecimal";
         } else if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "Bytes";
         } else {
            return Serializable.class.isAssignableFrom(type) ? "Bytes" : "Object";
         }
      } else if (type == Boolean.TYPE) {
         return "Boolean";
      } else if (type == Byte.TYPE) {
         return "Byte";
      } else if (type == Character.TYPE) {
         return "String";
      } else if (type == Double.TYPE) {
         return "Double";
      } else if (type == Float.TYPE) {
         return "Float";
      } else if (type == Integer.TYPE) {
         return "Int";
      } else if (type == Long.TYPE) {
         return "Long";
      } else if (type == Short.TYPE) {
         return "Short";
      } else {
         throw new AssertionError("Missed a case.:" + type);
      }
   }
}
