package monfox.jdom;

public class IllegalAddException extends IllegalArgumentException {
   public IllegalAddException(Element var1, Attribute var2, String var3) {
      super(a("S\u0018l\\ps\u0004{\u0015sr\u0004l\\3") + var2.getQualifiedName() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0004f\\eo\u0015)\u0019}b\u001dl\u0012e'R") + var1.getQualifiedName() + a("%J)") + var3);
   }

   public IllegalAddException(Element var1, Element var2, String var3) {
      super(a("S\u0018l\\tk\u0015d\u0019\u007fsP+") + var2.getQualifiedName() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0011z\\p'\u0013a\u0015}cPf\u001a1%") + var1.getQualifiedName() + a("%J)") + var3);
   }

   public IllegalAddException(Document var1, Element var2, String var3) {
      super(a("S\u0018l\\tk\u0015d\u0019\u007fsP+") + var2.getQualifiedName() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0011z\\eo\u0015)\u000e~h\u0004)\u0013w'\u0004a\u00191c\u001fj\t|b\u001e}F1") + var3);
   }

   public IllegalAddException(Element var1, ProcessingInstruction var2, String var3) {
      super(a("S\u0018l\\ANP+") + var2.getTarget() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0011z\\rh\u001e}\u0019\u007fsP}\u00131%") + var1.getQualifiedName() + a("%J)") + var3);
   }

   public IllegalAddException(Document var1, ProcessingInstruction var2, String var3) {
      super(a("S\u0018l\\ANP+") + var2.getTarget() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0004f\\eo\u0015)\b~wPe\u0019gb\u001c)\u0013w'\u0004a\u00191c\u001fj\t|b\u001e}F1") + var3);
   }

   public IllegalAddException(Element var1, Comment var2, String var3) {
      super(a("S\u0018l\\rh\u001dd\u0019\u007fsP+") + var2.getText() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0011z\\rh\u001e}\u0019\u007fsP}\u00131%") + var1.getQualifiedName() + a("%J)") + var3);
   }

   public IllegalAddException(Document var1, Comment var2, String var3) {
      super(a("S\u0018l\\rh\u001dd\u0019\u007fsP+") + var2.getText() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0004f\\eo\u0015)\b~wPe\u0019gb\u001c)\u0013w'\u0004a\u00191c\u001fj\t|b\u001e}F1") + var3);
   }

   public IllegalAddException(Element var1, Entity var2, String var3) {
      super(a("S\u0018l\\ti\u0004`\bh'R") + var2.getName() + a("%Pj\u0013dk\u0014)\u0012~sPk\u00191f\u0014m\u0019u'\u0011z\\rh\u001e}\u0019\u007fsP}\u00131%") + var1.getQualifiedName() + a("%J)") + var3);
   }

   public IllegalAddException(String var1) {
      super(var1);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 7;
               break;
            case 1:
               var10003 = 112;
               break;
            case 2:
               var10003 = 9;
               break;
            case 3:
               var10003 = 124;
               break;
            default:
               var10003 = 17;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
