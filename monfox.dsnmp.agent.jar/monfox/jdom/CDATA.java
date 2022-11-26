package monfox.jdom;

import java.io.Serializable;

public class CDATA implements Serializable, Cloneable {
   protected String text;

   protected CDATA() {
   }

   public CDATA(String var1) {
      String var2;
      if ((var2 = Verifier.checkCDATASection(var1)) != null) {
         throw new IllegalDataException(var1, a("V\n\u0006\u001b45=\",\u0001|!)"), var2);
      } else {
         this.text = var1;
      }
   }

   public String getText() {
      return this.text;
   }

   /** @deprecated */
   public void setText(String var1) {
      this.text = var1;
   }

   public String toString() {
      return a("N\r\u0003\u000e!Ttg") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      return a(")o\u001c\f1T\u001a\u0006\u0014") + this.text + a("H\u0013y");
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      CDATA var1 = new CDATA(this.text);
      return var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 21;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 71;
               break;
            case 3:
               var10003 = 79;
               break;
            default:
               var10003 = 117;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
