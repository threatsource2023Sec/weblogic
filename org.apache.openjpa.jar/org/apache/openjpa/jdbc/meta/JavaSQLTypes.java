package org.apache.openjpa.jdbc.meta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.openjpa.meta.JavaTypes;
import serp.util.Numbers;

public class JavaSQLTypes extends JavaTypes {
   public static final int SQL_ARRAY = 1000;
   public static final int ASCII_STREAM = 1001;
   public static final int BINARY_STREAM = 1002;
   public static final int BLOB = 1003;
   public static final int BYTES = 1004;
   public static final int CHAR_STREAM = 1005;
   public static final int CLOB = 1006;
   public static final int SQL_DATE = 1007;
   public static final int SQL_OBJECT = 1008;
   public static final int REF = 1009;
   public static final int TIME = 1010;
   public static final int TIMESTAMP = 1011;
   public static final int JDBC_DEFAULT = 1012;
   private static final Byte ZERO_BYTE = new Byte((byte)0);
   private static final Character ZERO_CHAR = new Character('\u0000');
   private static final Double ZERO_DOUBLE = new Double(0.0);
   private static final Float ZERO_FLOAT = new Float(0.0F);
   private static final Short ZERO_SHORT = new Short((short)0);
   private static final BigDecimal ZERO_BIGDECIMAL = new BigDecimal(0.0);
   private static final Byte NONZERO_BYTE = new Byte((byte)1);
   private static final Character NONZERO_CHAR = new Character('a');
   private static final Double NONZERO_DOUBLE = new Double(1.0);
   private static final Float NONZERO_FLOAT = new Float(1.0F);
   private static final Short NONZERO_SHORT = new Short((short)1);
   private static final BigInteger NONZERO_BIGINTEGER = new BigInteger("1");
   private static final BigDecimal NONZERO_BIGDECIMAL = new BigDecimal(1.0);

   public static int getDateTypeCode(Class dtype) {
      if (dtype == Date.class) {
         return 14;
      } else if (dtype == java.sql.Date.class) {
         return 1007;
      } else if (dtype == Timestamp.class) {
         return 1011;
      } else {
         return dtype == Time.class ? 1010 : 8;
      }
   }

   public static Object getEmptyValue(int type) {
      switch (type) {
         case 0:
         case 16:
            return Boolean.FALSE;
         case 1:
         case 17:
            return ZERO_BYTE;
         case 2:
         case 18:
            return ZERO_CHAR;
         case 3:
         case 19:
            return ZERO_DOUBLE;
         case 4:
         case 20:
            return ZERO_FLOAT;
         case 5:
         case 21:
            return Numbers.valueOf(0);
         case 6:
         case 22:
            return Numbers.valueOf(0L);
         case 7:
         case 23:
            return ZERO_SHORT;
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return null;
         case 9:
            return "";
         case 10:
         case 24:
            return ZERO_BIGDECIMAL;
         case 25:
            return BigInteger.ZERO;
      }
   }

   public static Object getNonEmptyValue(int type) {
      switch (type) {
         case 0:
         case 16:
            return Boolean.TRUE;
         case 1:
         case 17:
            return NONZERO_BYTE;
         case 2:
         case 18:
            return NONZERO_CHAR;
         case 3:
         case 19:
            return NONZERO_DOUBLE;
         case 4:
         case 20:
            return NONZERO_FLOAT;
         case 5:
         case 21:
            return Numbers.valueOf(1);
         case 6:
         case 22:
            return Numbers.valueOf(1L);
         case 7:
         case 23:
            return NONZERO_SHORT;
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return null;
         case 9:
            return "x";
         case 10:
         case 24:
            return NONZERO_BIGDECIMAL;
         case 25:
            return NONZERO_BIGINTEGER;
      }
   }
}
