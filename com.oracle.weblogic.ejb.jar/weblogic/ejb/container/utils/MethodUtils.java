package weblogic.ejb.container.utils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.utils.PlatformConstants;

public final class MethodUtils {
   private static final int MAX_LINE_CHARS = 80;
   private static final int THRESHOLD = 7;

   public static String convertToFinderName(String homeName) {
      if (homeName.startsWith("ejbSelect")) {
         return homeName;
      } else {
         String first = homeName.substring(0, 1);
         String rest = homeName.substring(1);
         return "ejb" + first.toUpperCase(Locale.ENGLISH) + rest;
      }
   }

   public static String convertToDDFinderName(String methodName) {
      return methodName.startsWith("ejbSelect") ? methodName : decapitalize(methodName.substring(3));
   }

   public static String tail(String name) {
      int i = name.lastIndexOf(".");
      return name.substring(i + 1);
   }

   public static String getParameterName(int index) {
      return getParameterName(index, "param");
   }

   public static String getParameterName(int index, String baseName) {
      return baseName + index;
   }

   public static String capitalize(String str) {
      return str.substring(0, 1).toUpperCase(Locale.ENGLISH) + str.substring(1);
   }

   public static String decapitalize(String str) {
      return str.substring(0, 1).toLowerCase(Locale.ENGLISH) + str.substring(1);
   }

   public static boolean potentialBridgeCandidate(Method onIntf, Method onImpl) {
      if (!onIntf.getName().equals(onImpl.getName())) {
         return false;
      } else if (!onIntf.getReturnType().isAssignableFrom(onImpl.getReturnType())) {
         return false;
      } else {
         Class[] intfMethodArgs = onIntf.getParameterTypes();
         Class[] implMethodArgs = onImpl.getParameterTypes();
         if (intfMethodArgs.length != implMethodArgs.length) {
            return false;
         } else {
            for(int i = 0; i < intfMethodArgs.length; ++i) {
               if (!intfMethodArgs[i].isAssignableFrom(implMethodArgs[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static String[] classesToJavaSourceTypes(Class[] types) {
      String[] typeNames = new String[types.length];

      for(int iType = 0; iType < types.length; ++iType) {
         typeNames[iType] = ClassUtils.classToJavaSourceType(types[iType]);
      }

      return typeNames;
   }

   public static List getFinderMethodList(Class home) {
      List finds = new ArrayList();
      Method[] var2 = home.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("find")) {
            finds.add(m);
         }
      }

      return finds;
   }

   public static List getFinderMethodList(Class home, Class localHome) {
      List finds = new ArrayList();
      Method[] var3;
      int var4;
      int var5;
      Method m;
      if (home != null) {
         var3 = home.getMethods();
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            m = var3[var5];
            if (m.getName().startsWith("find")) {
               finds.add(m);
            }
         }
      }

      if (localHome != null) {
         var3 = localHome.getMethods();
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            m = var3[var5];
            if (m.getName().startsWith("find")) {
               finds.add(m);
            }
         }
      }

      return finds;
   }

   public static List getSelectMethodList(Class bean) {
      List finds = new ArrayList();
      Method[] var2 = bean.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbSelect")) {
            finds.add(m);
         }
      }

      return finds;
   }

   public static String getMethodName(String fieldName) {
      return "get" + capitalize(fieldName);
   }

   public static String setMethodName(String fieldName) {
      return "set" + capitalize(fieldName);
   }

   public static String getParameterList(Method method) {
      return getParameterList(method, "param");
   }

   public static String getParameterList(Method method, String baseName) {
      StringBuilder sb = new StringBuilder();
      Class[] parameterTypes = method.getParameterTypes();

      for(int i = 0; i < parameterTypes.length; ++i) {
         sb.append(ClassUtils.classToJavaSourceType(parameterTypes[i]));
         sb.append(" " + getParameterName(i, baseName));
         if (i < parameterTypes.length - 1) {
            sb.append(", ");
         }

         if (sb.length() > 73) {
            sb.append("" + PlatformConstants.EOL + "    ");
         }
      }

      return sb.toString();
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

   public static String convertToPrimitive(Class type, String name) {
      if (type == Boolean.class) {
         return name + ".booleanValue()";
      } else if (type == Byte.class) {
         return name + ".byteValue()";
      } else if (type == Character.class) {
         return name + ".charValue()";
      } else if (type == Double.class) {
         return name + ".doubleValue()";
      } else if (type == Float.class) {
         return name + ".floatValue()";
      } else if (type == Integer.class) {
         return name + ".intValue()";
      } else if (type == Long.class) {
         return name + ".longValue()";
      } else {
         return type == Short.class ? name + ".shortValue()" : name;
      }
   }

   public static int dbmsType2int(String dbString) {
      if (dbString == null) {
         return 0;
      } else {
         Integer i = (Integer)DDConstants.DBTYPE_MAP.get(dbString.toUpperCase(Locale.ENGLISH));
         return i != null ? i : 0;
      }
   }

   public static String getDefaultDBMSColType(Class type, int dbType) {
      switch (dbType) {
         case 0:
         case 10:
         default:
            return getDefaultDBMSColType(type);
         case 1:
            return getDefaultDBMSColType_DB_ORACLE(type);
         case 2:
         case 5:
         case 7:
            return getDefaultDBMSColType_DB_SQLSERVER(type);
         case 3:
            return getDefaultDBMSColType_DB_INFORMIX(type);
         case 4:
            return getDefaultDBMSColType_DB_DB2(type);
         case 6:
            return getDefaultDBMSColType_DB_POINTBASE(type);
         case 8:
            return getDefaultDBMSColType_DB_MYSQL(type);
         case 9:
            return getDefaultDBMDColType_DB_DERBY(type);
      }
   }

   private static String getDefaultDBMSColType_DB_POINTBASE(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "BOOLEAN";
         }

         if (type == Byte.TYPE) {
            return "SMALLINT";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL";
         }

         if (type == Short.TYPE) {
            return "SMALLINT";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(38,19)";
         }

         if (type == Boolean.class) {
            return "BOOLEAN";
         }

         if (type == Byte.class) {
            return "SMALLINT";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL";
         }

         if (type == Short.class) {
            return "SMALLINT";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "TIME";
         }

         if (type == Timestamp.class) {
            return "TIMESTAMP";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "BLOB";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "BLOB";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType_DB_MYSQL(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "SMALLINT";
         }

         if (type == Byte.TYPE) {
            return "SMALLINT";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL";
         }

         if (type == Short.TYPE) {
            return "SMALLINT";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(38,19)";
         }

         if (type == Boolean.class) {
            return "TINYINT";
         }

         if (type == Byte.class) {
            return "SMALLINT";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(32,0)";
         }

         if (type == Short.class) {
            return "SMALLINT";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "TIME";
         }

         if (type == Timestamp.class) {
            return "TIMESTAMP";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "BLOB";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "BLOB";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType_DB_ORACLE(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "INTEGER";
         }

         if (type == Byte.TYPE) {
            return "INTEGER";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "INTEGER";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(38,19)";
         }

         if (type == Boolean.class) {
            return "INTEGER";
         }

         if (type == Byte.class) {
            return "INTEGER";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "INTEGER";
         }

         if (type == Short.class) {
            return "INTEGER";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "DATE";
         }

         if (type == Timestamp.class) {
            return "DATE";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "RAW(1000)";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "RAW(1000)";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType_DB_SQLSERVER(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "BIT";
         }

         if (type == Byte.TYPE) {
            return "TINYINT";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL(38,0)";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(38,19)";
         }

         if (type == Boolean.class) {
            return "INTEGER";
         }

         if (type == Byte.class) {
            return "TINYINT";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(38,0)";
         }

         if (type == Short.class) {
            return "SMALLINT";
         }

         if (type == Date.class) {
            return "DATETIME";
         }

         if (type == java.sql.Date.class) {
            return "DATETIME";
         }

         if (type == Time.class) {
            return "DATETIME";
         }

         if (type == Timestamp.class) {
            return "DATETIME";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "IMAGE";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "IMAGE";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType_DB_INFORMIX(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "SMALLINT";
         }

         if (type == Byte.TYPE) {
            return "SMALLINT";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL(32)";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(32,16)";
         }

         if (type == Boolean.class) {
            return "SMALLINT";
         }

         if (type == Byte.class) {
            return "SMALLINT";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(32)";
         }

         if (type == Short.class) {
            return "INTEGER";
         }

         if (type == Date.class) {
            return "DATETIME YEAR TO DAY";
         }

         if (type == java.sql.Date.class) {
            return "DATETIME YEAR TO DAY";
         }

         if (type == Time.class) {
            return "DATETIME HOUR TO FRACTION";
         }

         if (type == Timestamp.class) {
            return "DATETIME YEAR TO FRACTION";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "BYTE";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "BYTE";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMDColType_DB_DERBY(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "INTEGER";
         }

         if (type == Byte.TYPE) {
            return "INTEGER";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL(31)";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(31,16)";
         }

         if (type == Boolean.class) {
            return "INTEGER";
         }

         if (type == Byte.class) {
            return "INTEGER";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(31)";
         }

         if (type == Short.class) {
            return "INTEGER";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "DATE";
         }

         if (type == Timestamp.class) {
            return "DATETIME";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "BLOB";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "BLOB";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType_DB_DB2(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "INTEGER";
         }

         if (type == Byte.TYPE) {
            return "INTEGER";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL(31)";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(31,16)";
         }

         if (type == Boolean.class) {
            return "INTEGER";
         }

         if (type == Byte.class) {
            return "INTEGER";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(31)";
         }

         if (type == Short.class) {
            return "INTEGER";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "DATE";
         }

         if (type == Timestamp.class) {
            return "DATETIME";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "RAW(1000)";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "RAW(1000)";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   private static String getDefaultDBMSColType(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "INTEGER";
         }

         if (type == Byte.TYPE) {
            return "INTEGER";
         }

         if (type == Character.TYPE) {
            return "CHAR(1)";
         }

         if (type == Double.TYPE) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.TYPE) {
            return "FLOAT";
         }

         if (type == Integer.TYPE) {
            return "INTEGER";
         }

         if (type == Long.TYPE) {
            return "DECIMAL(32)";
         }

         if (type == Short.TYPE) {
            return "INTEGER";
         }
      } else {
         if (type == String.class) {
            return "VARCHAR(150)";
         }

         if (type == BigDecimal.class) {
            return "DECIMAL(32,16)";
         }

         if (type == Boolean.class) {
            return "INTEGER";
         }

         if (type == Byte.class) {
            return "INTEGER";
         }

         if (type == Character.class) {
            return "CHAR(1)";
         }

         if (type == Double.class) {
            return "DOUBLE PRECISION";
         }

         if (type == Float.class) {
            return "FLOAT";
         }

         if (type == Integer.class) {
            return "INTEGER";
         }

         if (type == Long.class) {
            return "DECIMAL(32)";
         }

         if (type == Short.class) {
            return "INTEGER";
         }

         if (type == Date.class) {
            return "DATE";
         }

         if (type == java.sql.Date.class) {
            return "DATE";
         }

         if (type == Time.class) {
            return "DATE";
         }

         if (type == Timestamp.class) {
            return "DATETIME";
         }

         if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return "RAW(1000)";
         }

         if (!ClassUtils.isValidSQLType(type) && Serializable.class.isAssignableFrom(type)) {
            return "RAW(1000)";
         }
      }

      throw new IllegalArgumentException("Cannot determine database Column type for Java type: " + type);
   }

   public static String decodeArrayTypes(Class c) {
      return c.getName().indexOf("[L") != -1 ? decodeObjectTypeArrayMaybe(c) : decodePrimitiveTypeArrayMaybe(c);
   }

   public static String decodeObjectTypeArrayMaybe(Class c) {
      StringBuilder brs = new StringBuilder("[]");
      int multiDim = c.getName().lastIndexOf(91);

      for(int i = 0; i < multiDim; ++i) {
         brs = brs.append("[]");
      }

      Class tmp;
      for(tmp = c; tmp.isArray(); tmp = tmp.getComponentType()) {
      }

      return tmp.getName() + brs;
   }

   public static String decodePrimitiveTypeArrayMaybe(Class c) {
      StringBuilder brs = new StringBuilder();
      int multiDim = c.getName().length() - 1;
      if (multiDim > 0) {
         for(int i = 0; i < multiDim; ++i) {
            brs = brs.append("[]");
         }
      }

      if (c.getName().endsWith("[Z")) {
         return "boolean" + brs;
      } else if (c.getName().endsWith("[C")) {
         return "char" + brs;
      } else if (c.getName().endsWith("[B")) {
         return "byte" + brs;
      } else if (c.getName().endsWith("[S")) {
         return "short" + brs;
      } else if (c.getName().endsWith("[I")) {
         return "int" + brs;
      } else if (c.getName().endsWith("[J")) {
         return "long" + brs;
      } else if (c.getName().endsWith("[F")) {
         return "float" + brs;
      } else {
         return c.getName().endsWith("[D") ? "double" + brs : c.getName();
      }
   }

   public static String decodePrimitiveTypeArrayMaybe(String c) {
      if (c == null) {
         return c;
      } else {
         StringBuilder brs = new StringBuilder();
         int multiDim = c.length() - 1;
         if (multiDim > 0) {
            for(int i = 0; i < multiDim; ++i) {
               brs = brs.append("[]");
            }
         }

         if (c.endsWith("[Z")) {
            return "boolean" + brs;
         } else if (c.endsWith("[C")) {
            return "char" + brs;
         } else if (c.endsWith("[B")) {
            return "byte" + brs;
         } else if (c.endsWith("[S")) {
            return "short" + brs;
         } else if (c.endsWith("[I")) {
            return "int" + brs;
         } else if (c.endsWith("[J")) {
            return "long" + brs;
         } else if (c.endsWith("[F")) {
            return "float" + brs;
         } else {
            return c.endsWith("[D") ? "double" + brs : c;
         }
      }
   }
}
