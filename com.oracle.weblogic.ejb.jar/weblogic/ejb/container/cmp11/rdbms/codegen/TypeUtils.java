package weblogic.ejb.container.cmp11.rdbms.codegen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.utils.AssertionError;

public final class TypeUtils {
   private static byte[] byteArray = new byte[1];

   public static int getSQLTypeForClass(Class type) throws EJBCException {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return -2;
         }

         if (type == Byte.TYPE) {
            return 4;
         }

         if (type == Character.TYPE) {
            return 1;
         }

         if (type == Double.TYPE) {
            return 8;
         }

         if (type == Float.TYPE) {
            return 6;
         }

         if (type == Integer.TYPE) {
            return 4;
         }

         if (type == Long.TYPE) {
            return 4;
         }

         if (type == Short.TYPE) {
            return 4;
         }
      } else {
         if (type == String.class) {
            return 12;
         }

         if (type == BigDecimal.class) {
            return 2;
         }

         if (type == Boolean.class) {
            return -2;
         }

         if (type == Byte.class) {
            return 4;
         }

         if (type == Character.class) {
            return 1;
         }

         if (type == Double.class) {
            return 8;
         }

         if (type == Float.class) {
            return 6;
         }

         if (type == Integer.class) {
            return 4;
         }

         if (type == Long.class) {
            return 4;
         }

         if (type == Short.class) {
            return 4;
         }

         if (type == Date.class) {
            return 91;
         }

         if (type == java.sql.Date.class) {
            return 91;
         }

         if (type == Time.class) {
            return 92;
         }

         if (type == Timestamp.class) {
            return 93;
         }

         if (type == byteArray.getClass()) {
            return -3;
         }

         if (Serializable.class.isAssignableFrom(type)) {
            return -3;
         }
      }

      throw new EJBCException("CMP11 Could not handle a SQL type in TypeUtils.getSQLTypeForClass:  type = " + type);
   }

   public static boolean isValidSQLType(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return true;
         }

         if (type == Byte.TYPE) {
            return true;
         }

         if (type == Character.TYPE) {
            return true;
         }

         if (type == Double.TYPE) {
            return true;
         }

         if (type == Float.TYPE) {
            return true;
         }

         if (type == Integer.TYPE) {
            return true;
         }

         if (type == Long.TYPE) {
            return true;
         }

         if (type == Short.TYPE) {
            return true;
         }
      } else {
         if (type == String.class) {
            return true;
         }

         if (type == Boolean.class) {
            return true;
         }

         if (type == Byte.class) {
            return true;
         }

         if (type == Character.class) {
            return true;
         }

         if (type == Double.class) {
            return true;
         }

         if (type == Float.class) {
            return true;
         }

         if (type == Integer.class) {
            return true;
         }

         if (type == Long.class) {
            return true;
         }

         if (type == Short.class) {
            return true;
         }

         if (type == Date.class) {
            return true;
         }

         if (type == java.sql.Date.class) {
            return true;
         }

         if (type == Time.class) {
            return true;
         }

         if (type == Timestamp.class) {
            return true;
         }

         if (type == BigDecimal.class) {
            return true;
         }

         if (type == byteArray.getClass()) {
            return true;
         }
      }

      return false;
   }

   public static String getResultSetMethodPostfix(Class type) {
      return getMethodPostfix(type);
   }

   public static String getPreparedStatementMethodPostfix(Class type) {
      return getMethodPostfix(type);
   }

   public static String getMethodPostfix(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
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
            throw new AssertionError("Didn't handle a potential SQL type case in TypeUtils.getMethodPostfix: type = " + type);
         }
      } else if (type == String.class) {
         return "String";
      } else if (type == BigDecimal.class) {
         return "BigDecimal";
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
      } else {
         return type.isArray() && type.getComponentType() == Byte.TYPE ? "Bytes" : "Bytes";
      }
   }
}
