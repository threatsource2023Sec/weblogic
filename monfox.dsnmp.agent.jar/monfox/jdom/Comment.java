package monfox.jdom;

import java.io.Serializable;

public class Comment implements Serializable, Cloneable {
   protected String text;
   protected Element parent;
   protected Document document;

   protected Comment() {
   }

   public Comment(String var1) {
      this.setText(var1);
   }

   public Element getParent() {
      return this.parent;
   }

   protected Comment setParent(Element var1) {
      this.parent = var1;
      return this;
   }

   public Document getDocument() {
      if (this.document != null) {
         return this.document;
      } else {
         Element var1 = this.getParent();
         return var1 != null ? var1.getDocument() : null;
      }
   }

   protected Comment setDocument(Document var1) {
      this.document = var1;
      return this;
   }

   public String getText() {
      return this.text;
   }

   public Comment setText(String var1) {
      String var2;
      if ((var2 = Verifier.checkCommentData(var1)) != null) {
         throw new IllegalDataException(var1, a("\"\u0013x\r~/\b"), var2);
      } else {
         this.text = var1;
         return this;
      }
   }

   public String toString() {
      return a("\u001a?z\rv$\u0012aZ;") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      return a("}]8M") + this.text + a("lQ+");
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      Comment var1 = new Comment(this.text);
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
               var10003 = 65;
               break;
            case 1:
               var10003 = 124;
               break;
            case 2:
               var10003 = 21;
               break;
            case 3:
               var10003 = 96;
               break;
            default:
               var10003 = 27;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
