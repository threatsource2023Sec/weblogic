package weblogic.apache.xerces.impl.dv.xs;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;
import weblogic.apache.xerces.util.XMLChar;

public abstract class TypeValidator {
   private static final boolean USE_CODE_POINT_COUNT_FOR_STRING_LENGTH;
   public static final short LESS_THAN = -1;
   public static final short EQUAL = 0;
   public static final short GREATER_THAN = 1;
   public static final short INDETERMINATE = 2;

   public abstract short getAllowedFacets();

   public abstract Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException;

   public void checkExtraRules(Object var1, ValidationContext var2) throws InvalidDatatypeValueException {
   }

   public boolean isIdentical(Object var1, Object var2) {
      return var1.equals(var2);
   }

   public int compare(Object var1, Object var2) {
      return -1;
   }

   public int getDataLength(Object var1) {
      if (var1 instanceof String) {
         String var2 = (String)var1;
         return !USE_CODE_POINT_COUNT_FOR_STRING_LENGTH ? var2.length() : this.getCodePointLength(var2);
      } else {
         return -1;
      }
   }

   public int getTotalDigits(Object var1) {
      return -1;
   }

   public int getFractionDigits(Object var1) {
      return -1;
   }

   private int getCodePointLength(String var1) {
      int var2 = var1.length();
      int var3 = 0;

      for(int var4 = 0; var4 < var2 - 1; ++var4) {
         if (XMLChar.isHighSurrogate(var1.charAt(var4))) {
            ++var4;
            if (XMLChar.isLowSurrogate(var1.charAt(var4))) {
               ++var3;
            } else {
               --var4;
            }
         }
      }

      return var2 - var3;
   }

   public static final boolean isDigit(char var0) {
      return var0 >= '0' && var0 <= '9';
   }

   public static final int getDigit(char var0) {
      return isDigit(var0) ? var0 - 48 : -1;
   }

   static {
      USE_CODE_POINT_COUNT_FOR_STRING_LENGTH = AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               return Boolean.getBoolean("weblogic.apache.xerces.impl.dv.xs.useCodePointCountForStringLength") ? Boolean.TRUE : Boolean.FALSE;
            } catch (SecurityException var2) {
               return Boolean.FALSE;
            }
         }
      }) == Boolean.TRUE;
   }
}
