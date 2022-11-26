package org.apache.openjpa.jdbc.schema;

import java.util.Date;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;

public class Schemas {
   public static final Column[] EMPTY_COLUMNS = new Column[0];
   public static final ForeignKey[] EMPTY_FOREIGN_KEYS = new ForeignKey[0];
   public static final Index[] EMPTY_INDEXES = new Index[0];
   public static final Unique[] EMPTY_UNIQUES = new Unique[0];
   public static final Object[] EMPTY_VALUES = new Object[0];

   public static String getNewTableSchema(JDBCConfiguration conf) {
      if (conf.getSchema() != null) {
         return conf.getSchema();
      } else {
         String[] schemas = conf.getSchemasList();
         if (schemas.length == 0) {
            return null;
         } else {
            int dotIdx = schemas[0].lastIndexOf(46);
            if (dotIdx == 0) {
               return null;
            } else {
               return dotIdx == -1 ? schemas[0] : schemas[0].substring(0, dotIdx);
            }
         }
      }
   }

   public static String getJDBCName(int type) {
      switch (type) {
         case -7:
            return "bit";
         case -6:
            return "tinyint";
         case -5:
            return "bigint";
         case -4:
            return "longvarbinary";
         case -3:
            return "varbinary";
         case -2:
            return "binary";
         case -1:
            return "longvarchar";
         case 0:
            return "null";
         case 1:
            return "char";
         case 2:
            return "numeric";
         case 3:
            return "decimal";
         case 4:
            return "integer";
         case 5:
            return "smallint";
         case 6:
            return "float";
         case 7:
            return "real";
         case 8:
            return "double";
         case 12:
            return "varchar";
         case 91:
            return "date";
         case 92:
            return "time";
         case 93:
            return "timestamp";
         case 1111:
            return "other";
         case 2000:
            return "java_object";
         case 2001:
            return "distinct";
         case 2002:
            return "struct";
         case 2003:
            return "array";
         case 2004:
            return "blob";
         case 2005:
            return "clob";
         case 2006:
            return "ref";
         default:
            return "unknown(" + type + ")";
      }
   }

   public static int getJDBCType(String name) {
      if ("array".equalsIgnoreCase(name)) {
         return 2003;
      } else if ("bigint".equalsIgnoreCase(name)) {
         return -5;
      } else if ("binary".equalsIgnoreCase(name)) {
         return -2;
      } else if ("bit".equalsIgnoreCase(name)) {
         return -7;
      } else if ("blob".equalsIgnoreCase(name)) {
         return 2004;
      } else if ("char".equalsIgnoreCase(name)) {
         return 1;
      } else if ("clob".equalsIgnoreCase(name)) {
         return 2005;
      } else if ("date".equalsIgnoreCase(name)) {
         return 91;
      } else if ("decimal".equalsIgnoreCase(name)) {
         return 3;
      } else if ("distinct".equalsIgnoreCase(name)) {
         return 2001;
      } else if ("double".equalsIgnoreCase(name)) {
         return 8;
      } else if ("float".equalsIgnoreCase(name)) {
         return 6;
      } else if ("integer".equalsIgnoreCase(name)) {
         return 4;
      } else if ("java_object".equalsIgnoreCase(name)) {
         return 2000;
      } else if ("longvarbinary".equalsIgnoreCase(name)) {
         return -4;
      } else if ("longvarchar".equalsIgnoreCase(name)) {
         return -1;
      } else if ("null".equalsIgnoreCase(name)) {
         return 0;
      } else if ("numeric".equalsIgnoreCase(name)) {
         return 2;
      } else if ("other".equalsIgnoreCase(name)) {
         return 1111;
      } else if ("real".equalsIgnoreCase(name)) {
         return 7;
      } else if ("ref".equalsIgnoreCase(name)) {
         return 2006;
      } else if ("smallint".equalsIgnoreCase(name)) {
         return 5;
      } else if ("struct".equalsIgnoreCase(name)) {
         return 2002;
      } else if ("time".equalsIgnoreCase(name)) {
         return 92;
      } else if ("timestamp".equalsIgnoreCase(name)) {
         return 93;
      } else if ("tinyint".equalsIgnoreCase(name)) {
         return -6;
      } else if ("varbinary".equalsIgnoreCase(name)) {
         return -3;
      } else if ("varchar".equalsIgnoreCase(name)) {
         return 12;
      } else if (name != null && !name.toLowerCase().startsWith("unknown")) {
         throw new IllegalArgumentException("name = " + name);
      } else {
         return 1111;
      }
   }

   public static Class getJavaType(int type, int size, int decimals) {
      switch (type) {
         case -7:
            return Boolean.TYPE;
         case -6:
            return Byte.TYPE;
         case -5:
            return Long.TYPE;
         case 1:
            if (size == 1) {
               return Character.TYPE;
            }
         case -1:
         case 12:
         case 2005:
            return String.class;
         case 2:
         case 8:
            return Double.TYPE;
         case 3:
            if (decimals == 0 && size < 10) {
               return Integer.TYPE;
            } else {
               if (decimals == 0) {
                  return Long.TYPE;
               }

               return Double.TYPE;
            }
         case 4:
            return Integer.TYPE;
         case 5:
            return Short.TYPE;
         case 6:
         case 7:
            return Float.TYPE;
         case 91:
         case 92:
         case 93:
            return Date.class;
         default:
            return Object.class;
      }
   }
}
