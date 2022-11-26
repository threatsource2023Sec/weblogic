package monfox.toolkit.snmp.metadata;

public class MetadataError {
   public static final int critical = 1;
   public static final int major = 2;
   public static final int minor = 3;
   public static final int warning = 4;
   public static final int info = 5;
   public static final int noSuchTableEntry = 1;
   public static final int processingFailure = 2;
   public static final int invalidType = 3;
   public static final int invalidObject = 4;
   public static final int invalidTable = 5;
   public static final int invalidNotification = 6;
   public static final int invalidNotificationGroup = 7;
   public static final int invalidObjectGroup = 8;
   public static final int invalidModuleIdentity = 9;
   public static final int invalidOID = 10;
   public static final int invalidRange = 11;
   public static final int invalidNamedNumber = 12;
   public static final int elementMissing = 13;
   public static final int noSuchType = 14;
   public static final int noSuchModule = 15;
   public static final int moduleNotFound = 16;
   public static final int duplicateOID = 17;
   private int _type = -1;
   private int _severity = 1;
   private String _module;
   private String _message;

   public MetadataError(int var1, int var2, String var3, String var4) {
      this._module = var3;
      this._message = var4;
      this._type = var1;
      this._severity = var2;
   }

   public String getModule() {
      return this._module;
   }

   public int getType() {
      return this._type;
   }

   public int getSeverity() {
      return this._severity;
   }

   public void setSeverity(int var1) {
      this._severity = var1;
   }

   public String getMessage() {
      return this._message;
   }

   public void setMessage(String var1) {
      this._message = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a(this.getSeverity())).append(a("%a"));
      var1.append("'");
      var1.append(b(this.getType()));
      var1.append("'");
      if (this.getModule() != null) {
         var1.append(a("?(*\u00126p%1^>?f") + this.getModule() + "'");
      }

      var1.append("\n");
      var1.append(this.getMessage());
      return var1.toString();
   }

   static String a(int var0) {
      switch (var0) {
         case 1:
            return a("D\u0004\u0016`\u0014M\u001c\u001fq\tB");
         case 2:
            return a("D\u0004\u0016`\u0014M\u001c\u001f\u007f\u0011B");
         case 3:
            return a("D\u0004\u0016`\u0014M\u001c\u001f\u007f\u0015B");
         case 4:
            return a("D\u0016\u0005`\u0015V\u000f\u0003o");
         case 5:
            return a("D\b\nt\u0014B");
         default:
            return a("D\b\nt\u0014B");
      }
   }

   static String b(int var0) {
      switch (var0) {
         case 1:
            return a("Q.da.|)df:}-!\u0012\u001eq56K");
         case 2:
            return a("O3+Q>l2-\\<?\u0007%[7j3!");
         case 3:
            return a("V/2S7v%df\"o$");
         case 4:
            return a("V/2S7v%d}9u$'F");
         case 5:
            return a("V/2S7v%df:}-!");
         case 6:
            return a("V/2S7v%d|4k(\"[8~5-]5");
         case 7:
            return a("V/2S7v%d|4k(\"[8~5-]5?\u00066].o");
         case 8:
            return a("V/2S7v%d}9u$'F{X3+G+");
         case 9:
            return a("V/2S7v%d\u007f4{4(W{V%!\\/v5=");
         case 10:
            return a("V/2S7v%d}\u0012[");
         case 11:
            return a("V/2S7v%d`:q&!");
         case 12:
            return a("V/2S7v%d|:r$ \u0012\u0015j,&W)");
         case 13:
            return a("Z-!_>q5d\u007f2l2-\\<");
         case 14:
            return a("Q.da.|)df\"o$");
         case 15:
            return a("Q.da.|)d\u007f4{4(W");
         case 16:
            return a("R\b\u0006\u0012\u0016p%1^>?\u000f+F{Y.1\\?");
         case 17:
            return a("[44^2| 0W{P\b\u0000");
         default:
            return a("X$*W)~-dw)m.6");
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
               var10003 = 31;
               break;
            case 1:
               var10003 = 65;
               break;
            case 2:
               var10003 = 68;
               break;
            case 3:
               var10003 = 50;
               break;
            default:
               var10003 = 91;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class InvalidNamedNumber extends InvalidElement {
      private String _identifier;
      private String _number;

      public InvalidNamedNumber(String var1, String var2, String var3, String var4) {
         super(12, 3, var1, var2, a("_<*k|_6|de[09x0\u0011") + var4 + a("\u0011r:eb\u0016;8o~B;:cuDr{") + var3 + "'");
         this._identifier = var3;
         this._number = var4;
      }

      public String getIdentifier() {
         return this._identifier;
      }

      public String getNumber() {
         return this._number;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 54;
                  break;
               case 1:
                  var10003 = 82;
                  break;
               case 2:
                  var10003 = 92;
                  break;
               case 3:
                  var10003 = 10;
                  break;
               default:
                  var10003 = 16;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class InvalidRange extends InvalidElement {
      public InvalidRange(String var1, String var2, String var3) {
         super(11, 3, var1, var2, var3);
      }
   }

   public static class InvalidObjectGroup extends InvalidElement {
      public InvalidObjectGroup(String var1, String var2, String var3) {
         super(8, 2, var1, var2, var3);
      }
   }

   public static class InvalidNotificationGroup extends InvalidElement {
      public InvalidNotificationGroup(String var1, String var2, String var3) {
         super(7, 2, var1, var2, var3);
      }
   }

   public static class InvalidNotification extends InvalidElement {
      public InvalidNotification(String var1, String var2, String var3) {
         super(6, 2, var1, var2, var3);
      }
   }

   public static class InvalidTable extends InvalidElement {
      public InvalidTable(String var1, String var2, String var3) {
         super(5, 2, var1, var2, var3);
      }
   }

   public static class DuplicateOID extends InvalidElement {
      public DuplicateOID(String var1, String var2, String var3) {
         super(17, 4, var1, var2, var3);
      }
   }

   public static class InvalidObject extends InvalidElement {
      public InvalidObject(String var1, String var2, String var3) {
         super(4, 2, var1, var2, var3);
      }
   }

   public static class InvalidType extends InvalidElement {
      public InvalidType(String var1, String var2, String var3) {
         super(3, 3, var1, var2, var3);
      }
   }

   public static class InvalidModuleIdentity extends InvalidElement {
      public InvalidModuleIdentity(String var1, String var2, String var3) {
         super(9, 3, var1, var2, var3);
      }
   }

   public static class InvalidOID extends InvalidElement {
      public InvalidOID(String var1, String var2, String var3) {
         super(10, 2, var1, var2, var3);
      }
   }

   public static class InvalidElement extends MetadataError {
      private String _name;
      private String _error;

      public InvalidElement(int var1, int var2, String var3, String var4, String var5) {
         super(var1, var2, var3, a("Wjp") + var4 + a("Pj30j\u001e$>!e\u0018$w<b\u0001+;<hY@wu,_") + var5 + ")");
         this._name = var4;
         this._error = var5;
      }

      public String getName() {
         return this._name;
      }

      public String getError() {
         return this._error;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 119;
                  break;
               case 1:
                  var10003 = 74;
                  break;
               case 2:
                  var10003 = 87;
                  break;
               case 3:
                  var10003 = 85;
                  break;
               default:
                  var10003 = 12;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ProcessingFailure extends MetadataError {
      public ProcessingFailure(String var1, String var2) {
         super(2, 2, var1, var2);
      }
   }

   public static class ElementMissing extends MetadataError {
      private String _elementType;
      private String _referencedBy;

      public ElementMissing(String var1, String var2, String var3) {
         super(13, 2, var1, a("\u000bj+") + var2 + a("\fjyD$N8nO!N.&C;\u000bm") + var3 + a("\fjbRbF#xR+E-%"));
         this._elementType = var2;
         this._referencedBy = var3;
      }

      public String getElementType() {
         return this._elementType;
      }

      public String getReferencedBy() {
         return this._referencedBy;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 43;
                  break;
               case 1:
                  var10003 = 74;
                  break;
               case 2:
                  var10003 = 11;
                  break;
               case 3:
                  var10003 = 33;
                  break;
               default:
                  var10003 = 66;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class NoSuchTableEntry extends MetadataError {
      private String _ref;
      private String _referencedBy;

      public NoSuchTableEntry(String var1, String var2, String var3) {
         super(1, 2, var1, a("F\"p&") + var2 + a("A\"\"dR\u0003p5oW\u0003f}cMF%") + var3 + "'");
         this._ref = var2;
         this._referencedBy = var3;
      }

      public String getRef() {
         return this._ref;
      }

      public String getReferencedBy() {
         return this._referencedBy;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 102;
                  break;
               case 1:
                  var10003 = 2;
                  break;
               case 2:
                  var10003 = 80;
                  break;
               case 3:
                  var10003 = 1;
                  break;
               default:
                  var10003 = 52;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class NoSuchType extends MetadataError {
      private String _typeref;
      private String _referencedBy;

      public NoSuchType(String var1, String var2, String var3) {
         super(14, 4, var1, a("\u0017JG") + var2 + a("\u0010J\u0015_GR\u0018\u0002TBR\u000eJXX\u0017M") + var3 + a("\u0010Dm") + a("\u0017JG\u0010\u0001Z\u000b\u001e\u001aOR\u000f\u0003\u001aUXJ\u000bU@SJ\u0003_QR\u0004\u0003_OCJ\nUEB\u0006\u0002I\u0001\u001d"));
         this._typeref = var2;
         this._referencedBy = var3;
      }

      public String getTypeRef() {
         return this._typeref;
      }

      public String getReferencedBy() {
         return this._referencedBy;
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
                  var10003 = 106;
                  break;
               case 2:
                  var10003 = 103;
                  break;
               case 3:
                  var10003 = 58;
                  break;
               default:
                  var10003 = 33;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ModuleNotFound extends MetadataError {
      public ModuleNotFound(String var1) {
         super(16, 2, var1, a("\u0006\u001f<i6d\u001fQK\u001bSSy\u0004X") + var1 + a("\u0001\u001frK\u000b\u0006YsQ\u0011B\u0011\u0016"));
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 38;
                  break;
               case 1:
                  var10003 = 63;
                  break;
               case 2:
                  var10003 = 28;
                  break;
               case 3:
                  var10003 = 36;
                  break;
               default:
                  var10003 = 127;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class NoSuchModule extends MetadataError {
      public NoSuchModule(String var1) {
         super(15, 2, var1, a("/6\u0011\u00016~cX!6k6\\<7zzTst") + var1 + a("(6_<'/p^&=k8;") + a("/6\u0011ysbyU&?j6\\& {6S6scyP76k6A!:`d\u0011'</yE;6}6\\<7zzT s%"));
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 15;
                  break;
               case 1:
                  var10003 = 22;
                  break;
               case 2:
                  var10003 = 49;
                  break;
               case 3:
                  var10003 = 83;
                  break;
               default:
                  var10003 = 83;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
