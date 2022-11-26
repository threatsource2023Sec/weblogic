package monfox.jdom;

import java.io.Serializable;

public class DocType implements Serializable, Cloneable {
   protected String elementName;
   protected String publicID;
   protected String systemID;

   protected DocType() {
   }

   public DocType(String var1, String var2, String var3) {
      this.elementName = var1;
      this.publicID = var2;
      this.systemID = var3;
   }

   public DocType(String var1, String var2) {
      this(var1, "", var2);
   }

   public DocType(String var1) {
      this(var1, "", "");
   }

   public String getElementName() {
      return this.elementName;
   }

   public String getPublicID() {
      return this.publicID;
   }

   public DocType setPublicID(String var1) {
      this.publicID = var1;
      return this;
   }

   public String getSystemID() {
      return this.systemID;
   }

   public DocType setSystemID(String var1) {
      this.systemID = var1;
      return this;
   }

   public String toString() {
      return a("\u0016M\u007fb\u001f4yu;k") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      boolean var1 = false;
      StringBuffer var2 = (new StringBuffer()).append(a("q(TN\b\u0019P@Dk")).append(this.elementName);
      if (this.publicID != null && !this.publicID.equals("")) {
         var2.append(a("mYEC\u0007\u0004J0#")).append(this.publicID).append("\"");
         var1 = true;
      }

      if (this.systemID != null && !this.systemID.equals("")) {
         if (!var1) {
            var2.append(a("mZIR\u001f\bD"));
         }

         var2.append(a("m+")).append(this.systemID).append("\"");
      }

      var2.append(">");
      return var2.toString();
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      DocType var1 = new DocType(this.elementName, this.publicID, this.systemID);
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
               var10003 = 77;
               break;
            case 1:
               var10003 = 9;
               break;
            case 2:
               var10003 = 16;
               break;
            case 3:
               var10003 = 1;
               break;
            default:
               var10003 = 75;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
