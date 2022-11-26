package monfox.toolkit.snmp.metadata;

import java.io.Serializable;
import monfox.toolkit.snmp.util.TextBuffer;

public class RangeItem implements Serializable {
   static final long serialVersionUID = -5088185265982162889L;
   public static final Long MIN = new Long(Long.MIN_VALUE);
   public static final Long MAX = new Long(Long.MAX_VALUE);
   private Long _lowerValue;
   private Long _upperValue;

   public RangeItem(String var1) throws NumberFormatException {
      this(a(var1));
   }

   private static Long a(String var0) throws NumberFormatException {
      if (var0 == null) {
         return null;
      } else {
         try {
            return new Long(var0);
         } catch (NumberFormatException var3) {
            var0 = var0.toUpperCase();
            if (var0.equals(b("-Me"))) {
               return MAX;
            } else if (var0.equals(b("-Es"))) {
               return MIN;
            } else if (var0.startsWith(b("Q4\t6"))) {
               return MAX;
            } else {
               String var2;
               if (var0.startsWith("'") && var0.endsWith(b("GD"))) {
                  var2 = var0.substring(1, var0.length() - 2);
                  return Long.decode(b("Pt") + var2);
               } else if (var0.startsWith("'") && var0.endsWith(b("GN"))) {
                  var2 = var0.substring(1, var0.length() - 2);
                  return Long.decode(var2);
               } else {
                  throw new NumberFormatException(b(")bKc\u0018\th\u001dL\u0001\rnXpN@") + var0);
               }
            }
         }
      }
   }

   public RangeItem(Long var1) {
      this._lowerValue = null;
      this._upperValue = null;
      this._lowerValue = var1;
   }

   public RangeItem(String var1, String var2) throws NumberFormatException {
      this(a(var1), a(var2));
   }

   public RangeItem(Long var1, Long var2) {
      this._lowerValue = null;
      this._upperValue = null;
      this._lowerValue = var1;
      this._upperValue = var2;
   }

   public boolean isSingle() {
      return this._upperValue == null;
   }

   public boolean isRange() {
      return this._upperValue != null;
   }

   public Long getSingleValue() {
      return this._lowerValue;
   }

   public Long getLowerValue() {
      return this._upperValue != null ? this._lowerValue : null;
   }

   public Long getUpperValue() {
      return this._upperValue;
   }

   public String toString() {
      TextBuffer var1 = new TextBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(TextBuffer var1) {
      if (this._upperValue != null) {
         var1.append((Object)this._lowerValue).append((Object)b("N\"")).append((Object)this._upperValue);
         if (!SnmpOidInfo.a) {
            return;
         }
      }

      var1.append((Object)this._lowerValue);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 96;
               break;
            case 1:
               var10003 = 12;
               break;
            case 2:
               var10003 = 61;
               break;
            case 3:
               var10003 = 2;
               break;
            default:
               var10003 = 116;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
