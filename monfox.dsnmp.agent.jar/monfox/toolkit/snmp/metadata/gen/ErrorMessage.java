package monfox.toolkit.snmp.metadata.gen;

public class ErrorMessage extends Message {
   public static final int critical = 1;
   public static final int major = 2;
   public static final int minor = 3;
   public static final int warning = 4;
   public static final int info = 5;
   public static final int invalidOption = 1;
   public static final int missingModule = 2;
   public static final int duplicateOID = 3;
   public static final int unresolvedOID = 4;
   public static final int unresolvedType = 5;
   public static final int duplicateModule = 6;
   public static final int moduleMismatch = 7;
   public static final int undefinedType = 8;
   public static final int duplicateName = 10;
   public static final int processingFailure = 11;
   public static final int syntaxError = 12;
   public static final int undefinedOID = 13;
   public static final int circularDependency = 14;
   public static final int unresolvedTableOID = 15;
   public static final int undefinedElement = 16;
   public static final int duplicateType = 17;
   public static final int unresolvedObjectType = 18;
   public static final int genericWarning = 19;
   public static final int moduleLoadFailed = 20;
   public static final int fileOpenError = 21;
   public static final int fileSaveError = 22;
   public static final int fatalError = 23;
   public static final int unresolvedReference = 24;
   public static final int elementMismatch = 25;
   public static final int invalidOID = 26;
   public static final int missingColumn = 27;
   public static final int syntaxWarning = 28;
   private int c = -1;
   private int b = 1;
   private String d;

   public ErrorMessage(int var1, int var2, String var3, String var4) {
      super(var4);
      this.d = var3;
      this.c = var1;
      this.b = var2;
   }

   public String getModule() {
      return this.d;
   }

   public int getType() {
      return this.c;
   }

   public int getSeverity() {
      return this.b;
   }

   public void setSeverity(int var1) {
      this.b = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a(this.getSeverity())).append(a("}J"));
      var1.append("'");
      var1.append(b(this.getType()));
      var1.append("'");
      if (this.getModule() != null && this.getModule().length() > 0) {
         var1.append(a("g\u0003A\fV(\u000eZ@^gM") + this.getModule() + "'");
      }

      var1.append("\n");
      if (this.getFilename() != null && this.getLineNumber() >= 0) {
         var1.append(a(this.getSeverity())).append(a("}J"));
         var1.append(a("(\u0004\u000f@R)\u000f\u000f") + this.getLineNumber() + a("g\u0005I\f].\u0006J\f\u001c") + this.getFilename() + a("`D"));
         var1.append("\n");
      }

      var1.append("\n");
      var1.append(this.getMessage());
      var1.append("\n");
      return var1.toString();
   }

   static String a(int var0) {
      switch (var0) {
         case 1:
            return a("\u001c/}~t\u00157toi\u001a");
         case 2:
            return a("\u001c/}~t\u00157taq\u001a");
         case 3:
            return a("\u001c/}~t\u00157tau\u001a");
         case 4:
            return a("\u001c=n~u\u000e$hq");
         case 5:
            return a("\u001c#ajt\u001a");
         default:
            return a("\u001c#ajt\u001a");
      }
   }

   static String b(int var0) {
      switch (var0) {
         case 1:
            return a("\u000e\u0004YMW.\u000e\u000fcK3\u0003@B");
         case 2:
            return a("\n\u0003\\_R)\r\u000faT#\u001fCI");
         case 3:
            return a("\u0003\u001f_@R$\u000b[I\u001b\b#k");
         case 4:
            return a("\u0012\u0004]IH(\u0006YI_g%fh\u00134C");
         case 5:
            return a("\u0012\u0004]IH(\u0006YI_g>V\\^o\u0019\u0006");
         case 6:
            return a("\u0003\u001f_@R$\u000b[I\u001b\n\u0005KYW\"");
         case 7:
            return a("\n\u0005KYW\"JbEH*\u000b[OS");
         case 8:
            return a("\u0012\u0004KI].\u0004JH\u001b\u0013\u0013_I");
         case 9:
         default:
            return a("\u0000\u000fAII&\u0006");
         case 10:
            return a("\u0003\u001f_@R$\u000b[I\u001b\t\u000bBI");
         case 11:
            return a("\u0017\u0018@O^4\u0019FB\\g,NEW2\u0018J");
         case 12:
            return a("\u0014\u0013AXZ?Jj^I(\u0018");
         case 13:
            return a("\u0012\u0004KI].\u0004JH\u001b\b#k\u0004Hn");
         case 14:
            return a("\u0004\u0003]ON+\u000b]\f\u007f\"\u001aJB_\"\u0004LU");
         case 15:
            return a("\u0012\u0004]IH(\u0006YI_g>NNW\"J`e\u007f");
         case 16:
            return a("\u0012\u0004KI].\u0004JH\u001b\u0002\u0006JA^)\u001e");
         case 17:
            return a("\u0003\u001f_@R$\u000b[I\u001b\u0013\u0013_I");
         case 18:
            return a("\u0012\u0004]IH(\u0006YI_g%mf~\u0004>\u0002xb\u0017/");
         case 19:
            return a("\u0000\u000fAII.\t\u000f{Z5\u0004FB\\");
         case 20:
            return a("\n\u0005KYW\"JcCZ#JiMR+\u000fK");
         case 21:
            return a("\u0001\u0003CI\u001b\b\u001aJB\u001b\u0002\u0018]CI");
         case 22:
            return a("\u0001\u0003CI\u001b\u0014\u000bYI\u001b\u0002\u0018]CI");
         case 23:
            return a("\u0001\u000b[MWg/]^T5");
         case 24:
            return a("\u0012\u0004]IH(\u0006YI_g8JJ^5\u000fAO^");
         case 25:
            return a("\u0002\u0006JA^)\u001e\u000faR4\u0007NXX/");
         case 26:
            return a("\u000e\u0004YMW.\u000e\u000fcr\u0003");
         case 27:
            return a("\n\u0003\\_R)\r\u000foT+\u001fBB");
         case 28:
            return a("\u0014\u0013AXZ?JxMI)\u0003AK");
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 71;
               break;
            case 1:
               var10003 = 106;
               break;
            case 2:
               var10003 = 47;
               break;
            case 3:
               var10003 = 44;
               break;
            default:
               var10003 = 59;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class DuplicateType extends ErrorMessage {
      private String a;
      private String b;
      private String d;

      DuplicateType(String var1, String var2, String var3, String var4) {
         super(17, 4, var1, a("tpa") + var2 + "'" + a("^pa<,t\"$zWe\r{<") + var3 + a("^pa<,t\"$zWf\r{<") + var4);
         this.a = var3;
         this.b = var4;
         this.d = var2;
      }

      public String getTypename() {
         return this.d;
      }

      public String getRef1() {
         return this.a;
      }

      public String getRef2() {
         return this.b;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 84;
                  break;
               case 1:
                  var10003 = 80;
                  break;
               case 2:
                  var10003 = 65;
                  break;
               case 3:
                  var10003 = 28;
                  break;
               default:
                  var10003 = 12;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class DuplicateOID extends ErrorMessage {
      private String a;
      private String b;
      private String d;

      DuplicateOID(String var1, String var2, String var3, String var4) {
         super(3, 3, var1, a("\u0001\u00065\u0015je\u001c5") + var2 + a("+\u00065z\u0003\u0001Tp<x\u0010{/z") + var3 + a("+\u00065z\u0003\u0001Tp<x\u0013{/z") + var4);
         this.d = var2;
         this.a = var3;
         this.b = var4;
      }

      public String getOid() {
         return this.d;
      }

      public String getRef1() {
         return this.a;
      }

      public String getRef2() {
         return this.b;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 33;
                  break;
               case 1:
                  var10003 = 38;
                  break;
               case 2:
                  var10003 = 21;
                  break;
               case 3:
                  var10003 = 90;
                  break;
               default:
                  var10003 = 35;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class MissingColumn extends ErrorMessage {
      MissingColumn(String var1, String var2, int var3) {
         super(27, 5, var1, a("&8.\u0013f&{a6`kv.}") + var3 + a("!8g45ryl6p&?") + var2 + a("!8g)5kq})|h\u007f.e"));
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 6;
                  break;
               case 1:
                  var10003 = 24;
                  break;
               case 2:
                  var10003 = 14;
                  break;
               case 3:
                  var10003 = 90;
                  break;
               default:
                  var10003 = 21;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class MissingModule extends ErrorMessage {
      MissingModule(String var1) {
         super(2, 3, (String)null, a("\u001eY\u0016W0\u001e\nCz7\u001e\u0014Y}*R\u001c\u0016>") + var1 + "'");
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 62;
                  break;
               case 1:
                  var10003 = 121;
                  break;
               case 2:
                  var10003 = 54;
                  break;
               case 3:
                  var10003 = 25;
                  break;
               default:
                  var10003 = 95;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class InvalidOption extends ErrorMessage {
      InvalidOption(String var1) {
         super(1, 1, (String)null, var1);
      }
   }

   public static class UnresolvedType extends ErrorMessage {
      UnresolvedType(String var1, String var2) {
         super(5, 2, var1, var2);
      }
   }

   public static class UnresolvedObjectType extends ErrorMessage {
      private String a = null;

      UnresolvedObjectType(String var1, String var2) {
         super(18, 2, var1, var2);
         this.a = var2;
      }

      public String getObjectType() {
         return this.a;
      }
   }

   public static class UnresolvedOID extends ErrorMessage {
      UnresolvedOID(String var1, String var2) {
         super(4, 2, var1, var2);
      }
   }

   public static class UnresolvedTableOID extends ErrorMessage {
      UnresolvedTableOID(String var1, String var2) {
         super(15, 2, var1, (String)null);
         StringBuffer var3 = new StringBuffer();
         var3.append(a("V]eI\u0011\u0013)jJ\u0010\u0013)#"));
         var3.append(var2).append(a("Q'$\u0017\u0012\u001b`p_\u0014\u0018n$_\u001c\u0014ea\u0015S"));
         this.a = var3.toString();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 118;
                  break;
               case 1:
                  var10003 = 9;
                  break;
               case 2:
                  var10003 = 4;
                  break;
               case 3:
                  var10003 = 43;
                  break;
               default:
                  var10003 = 125;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class UnresolvedReference extends ErrorMessage {
      private String b;
      private String d;
      private String c;

      UnresolvedReference(String var1, String var2, String var3, String var4) {
         super(24, 2, var1, (String)null);
         StringBuffer var5 = new StringBuffer();
         var5.append(a("}C{"));
         var5.append(var2).append(a("}D")).append(var3).append("'");
         if (var4 != null) {
            var5.append(a("}\u0011>{F/\u00065~F9C9d\u0003z") + var4 + "'");
         }

         var5.append(".");
         this.a = var5.toString();
         this.b = var2;
         this.d = var3;
         this.c = var4;
      }

      public String getElementType() {
         return this.b;
      }

      public String getElementName() {
         return this.d;
      }

      public String getReferencedBy() {
         return this.c;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 93;
                  break;
               case 1:
                  var10003 = 99;
                  break;
               case 2:
                  var10003 = 91;
                  break;
               case 3:
                  var10003 = 29;
                  break;
               default:
                  var10003 = 35;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ElementMismatch extends ErrorMessage {
      private String b;
      private String d;
      private String c;
      private String e;

      ElementMismatch(String var1, String var2, String var3, String var4, String var5) {
         super(25, 3, var1, (String)null);
         StringBuffer var6 = new StringBuffer();
         var6.append(a("\u001ej1"));
         var6.append(var2).append(a("\u001em")).append(var3).append("'");
         if (var4 != null) {
            var6.append(a("\u001e8t\u001d*L/\u007f\u0018*Zjs\u0002o\u0019") + var4 + "'");
         }

         var6.append(a("4j1[\nF:t\u0018;[.1\u000f6N/1\\")).append(var5).append("'");
         var6.append(".");
         this.a = var6.toString();
         this.b = var2;
         this.d = var3;
         this.c = var4;
         this.e = var5;
      }

      public String getElementType() {
         return this.b;
      }

      public String getElementName() {
         return this.d;
      }

      public String getReferencedBy() {
         return this.c;
      }

      public String getExpectedType() {
         return this.e;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 62;
                  break;
               case 1:
                  var10003 = 74;
                  break;
               case 2:
                  var10003 = 17;
                  break;
               case 3:
                  var10003 = 123;
                  break;
               default:
                  var10003 = 79;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class InvalidOID extends ErrorMessage {
      InvalidOID(String var1, String var2) {
         super(26, 2, var1, var2);
      }
   }

   public static class UndefinedOID extends ErrorMessage {
      UndefinedOID(String var1, String var2) {
         super(13, 2, var1, var2);
      }
   }

   public static class UndefinedElement extends ErrorMessage {
      private String b = null;
      private String d = null;

      UndefinedElement(String var1, String var2, String var3) {
         super(16, 2, var1, (String)null);
         if (var2 != null) {
            var2 = var2.replace('_', '-');
         }

         this.b = var2;
         this.d = var3;
         StringBuffer var4 = new StringBuffer();
         var4.append(a("\teF?\bM \u0000\u0003\bL!FM"));
         var4.append(var2).append(a("\u000ee"));
         var4.append(var3);
         this.a = var4.toString();
      }

      public String getElementType() {
         return this.b;
      }

      public String getInfo() {
         return this.d;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 41;
                  break;
               case 1:
                  var10003 = 69;
                  break;
               case 2:
                  var10003 = 102;
                  break;
               case 3:
                  var10003 = 106;
                  break;
               default:
                  var10003 = 102;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class CircularDependency extends ErrorMessage {
      CircularDependency(String var1, String var2) {
         super(14, 3, var1, var2);
      }
   }

   public static class DuplicateModule extends ErrorMessage {
      private String a;

      DuplicateModule(String var1) {
         super(6, 4, (String)null, a("O\bMrc\bF\u0002<C\u0001OM\n_\u001fD\u0004-K\u001bMS"));
         this.a = var1;
      }

      public String getModule() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 111;
                  break;
               case 1:
                  var10003 = 40;
                  break;
               case 2:
                  var10003 = 109;
                  break;
               case 3:
                  var10003 = 78;
                  break;
               default:
                  var10003 = 42;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ModuleMismatch extends ErrorMessage {
      private String b;
      private String d;
      private String c;
      private String e;

      ModuleMismatch(String var1, String var2, String var3, String var4) {
         super(7, 4, (String)null, "");
         StringBuffer var5 = new StringBuffer();
         var5.append(a("BLj^\u0018\u0007\u0001/u\u0000BK") + var3 + a("EB"));
         if (var4 != null) {
            var5.append(a("hLj;&\u0007\n/i\u0011\f\u000f/\u007fT\u0000\u0015j<") + var4 + a("EB"));
         }

         if (var2 != null) {
            var5.append(a("hLj;1\u001a\u001c/x\u0000\u0007\bjv\u001b\u0006\u0019&~TE") + var2 + a("EB"));
         }

         this.a = var5.toString();
         this.b = var1;
         this.e = var2;
         this.d = var3;
         this.c = var4;
      }

      public String getModule() {
         return this.b;
      }

      public String getExpectedModule() {
         return this.e;
      }

      public String getElementName() {
         return this.d;
      }

      public String getReferer() {
         return this.c;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 98;
                  break;
               case 1:
                  var10003 = 108;
                  break;
               case 2:
                  var10003 = 74;
                  break;
               case 3:
                  var10003 = 27;
                  break;
               default:
                  var10003 = 116;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class UndefinedType extends ErrorMessage {
      UndefinedType(String var1, String var2) {
         super(8, 2, var1, var2);
      }
   }

   public static class DuplicateName extends ErrorMessage {
      public String _name1;
      public String _name2;
      public String _newName;

      DuplicateName(String var1, String var2, String var3, String var4) {
         super(10, 4, var1, (String)null);
         StringBuffer var5 = new StringBuffer();
         var5.append(a("Nx3[J\u0003=H\u0004vN\u007f") + this._name1 + "'");
         var5.append(a("dx3\u0015E\u000f5vn\u00193x4") + this._name2 + "'");
         if (var4 != null) {
            var5.append(a("dx3\u0015y\u000b6rXB\u0000?3\u0012") + var2 + a("IxgZ\u000bI") + var4 + "'");
         }

         this.a = var5.toString();
         this._name1 = var2;
         this._name2 = var3;
         this._newName = var4;
      }

      public String getName1() {
         return this._name1;
      }

      public String getName2() {
         return this._name2;
      }

      public String getNewName() {
         return this._newName;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 110;
                  break;
               case 1:
                  var10003 = 88;
                  break;
               case 2:
                  var10003 = 19;
                  break;
               case 3:
                  var10003 = 53;
                  break;
               default:
                  var10003 = 43;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ProcessingFailure extends ErrorMessage {
      ProcessingFailure(String var1) {
         super(11, 1, (String)null, var1);
      }
   }

   public static class SyntaxError extends ErrorMessage {
      private String a = null;
      private String b = null;

      SyntaxError(String var1, int var2, String var3, String var4) {
         super(12, 1, (String)null, "");
         this.setFilename(var1);
         this.setLineNumber(var2);
         this.a = var3;
         this.b = var4;
      }

      public String getMessage() {
         StringBuffer var1 = new StringBuffer(30);
         var1.append("{").append(this.a).append("}");
         if (this.b != null) {
            var1.append(a(" \u001ce\u0012")).append(this.b);
         }

         return var1.toString();
      }

      public String getLineText() {
         return this.a;
      }

      public String getErrorMessage() {
         return this.b;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 42;
                  break;
               case 1:
                  var10003 = 60;
                  break;
               case 2:
                  var10003 = 69;
                  break;
               case 3:
                  var10003 = 50;
                  break;
               default:
                  var10003 = 107;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class SyntaxWarning extends ErrorMessage {
      private int c = -1;
      private String a = null;
      private String b = null;
      private String d = null;

      SyntaxWarning(String var1, int var2, String var3, String var4) {
         super(28, 4, (String)null, "");
         this.a = var1;
         this.c = var2;
         this.b = var3;
         this.d = var4;
      }

      public String getMessage() {
         StringBuffer var1 = new StringBuffer(30);
         var1.append(ErrorMessage.a(4)).append(a("~\u0014"));
         var1.append(this.b);
         if (this.d != null) {
            var1.append("\n");
            var1.append(ErrorMessage.a(4)).append(a("~\u0014"));
            var1.append(this.d);
         }

         return var1.toString();
      }

      public String getFilename() {
         return this.a;
      }

      public int getLineNumber() {
         return this.c;
      }

      public String getLineText() {
         return this.b;
      }

      public String getErrorMessage() {
         return this.d;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 68;
                  break;
               case 1:
                  var10003 = 52;
                  break;
               case 2:
                  var10003 = 94;
                  break;
               case 3:
                  var10003 = 16;
                  break;
               default:
                  var10003 = 43;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class GenericWarning extends ErrorMessage {
      GenericWarning(String var1) {
         super(19, 4, (String)null, var1);
      }

      GenericWarning(String var1, String var2) {
         super(19, 4, var1, var2);
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         var1.append(a("aMD\r:sTB\u0002N\u001a"));
         var1.append(this.getMessage());
         return var1.toString();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 58;
                  break;
               case 1:
                  var10003 = 26;
                  break;
               case 2:
                  var10003 = 5;
                  break;
               case 3:
                  var10003 = 95;
                  break;
               default:
                  var10003 = 116;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ModuleLoadFailed extends ErrorMessage {
      ModuleLoadFailed(String var1, String var2) {
         super(20, 2, var1, var2);
      }
   }

   public static class FileOpenError extends ErrorMessage {
      private String a;

      FileOpenError(String var1, String var2) {
         super(21, 1, (String)null, var2 + a("\u0017[\u0003\u0005>") + var1 + "'");
         this.a = var1;
      }

      public String getFilename() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 55;
                  break;
               case 1:
                  var10003 = 101;
                  break;
               case 2:
                  var10003 = 61;
                  break;
               case 3:
                  var10003 = 37;
                  break;
               default:
                  var10003 = 25;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class FileSaveError extends ErrorMessage {
      private String a;

      public FileSaveError(String var1, String var2) {
         super(22, 1, (String)null, var2 + a("\u001e|c`\u0000") + var1 + "'");
         this.a = var1;
      }

      public String getFilename() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 62;
                  break;
               case 1:
                  var10003 = 66;
                  break;
               case 2:
                  var10003 = 93;
                  break;
               case 3:
                  var10003 = 64;
                  break;
               default:
                  var10003 = 39;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class FatalError extends ErrorMessage {
      public FatalError(String var1) {
         super(23, 1, (String)null, var1);
      }
   }
}
