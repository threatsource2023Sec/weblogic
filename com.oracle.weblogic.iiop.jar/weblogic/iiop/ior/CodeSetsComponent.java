package weblogic.iiop.ior;

import java.io.UnsupportedEncodingException;
import weblogic.iiop.protocol.CodeSet;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class CodeSetsComponent extends TaggedComponent {
   private static final int[] DEFAULT_CHAR_CONVERSION_CODE_SETS = new int[]{65568, 65537, 83951617};
   private static final int[] DEFAULT_WCHAR_CONVERSION_CODE_SETS = new int[]{65792, 65801, 83951617};
   private static CodeSetsComponent DEFAULT;
   private int charNativeCodeSet;
   private int[] charConversionCodeSets;
   private int wcharNativeCodeSet;
   private int[] wcharConversionCodeSets;

   public int negotiatedCharCodeSet() throws UnsupportedEncodingException {
      if (CodeSet.supportedCharCodeSet(this.charNativeCodeSet)) {
         return this.charNativeCodeSet;
      } else {
         int[] var1 = this.charConversionCodeSets;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            int charConversionCodeSet = var1[var3];
            if (CodeSet.supportedCharCodeSet(charConversionCodeSet)) {
               return charConversionCodeSet;
            }
         }

         throw new UnsupportedEncodingException();
      }
   }

   public int negotiatedWcharCodeSet() throws UnsupportedEncodingException {
      if (this.supportedWcharCodeSet(83951617)) {
         return 83951617;
      } else if (CodeSet.supportedWcharCodeSet(this.wcharNativeCodeSet)) {
         return this.wcharNativeCodeSet;
      } else {
         int[] var1 = this.wcharConversionCodeSets;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            int wcharConversionCodeSet = var1[var3];
            if (CodeSet.supportedWcharCodeSet(wcharConversionCodeSet)) {
               return wcharConversionCodeSet;
            }
         }

         throw new UnsupportedEncodingException();
      }
   }

   private CodeSetsComponent() {
      super(1);
      this.charNativeCodeSet = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.charConversionCodeSets = DEFAULT_CHAR_CONVERSION_CODE_SETS;
      this.wcharNativeCodeSet = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.wcharConversionCodeSets = DEFAULT_WCHAR_CONVERSION_CODE_SETS;
   }

   public static CodeSetsComponent getDefault() {
      if (DEFAULT == null) {
         Class var0 = CodeSetsComponent.class;
         synchronized(CodeSetsComponent.class) {
            DEFAULT = new CodeSetsComponent();
         }
      }

      return DEFAULT;
   }

   public static void resetDefault() {
      Class var0 = CodeSetsComponent.class;
      synchronized(CodeSetsComponent.class) {
         DEFAULT = null;
      }
   }

   CodeSetsComponent(CorbaInputStream in) {
      super(1);
      this.read(in);
   }

   public final boolean supportedCharCodeSet(int cs) {
      if (this.charNativeCodeSet == cs) {
         return true;
      } else {
         int[] var2 = this.charConversionCodeSets;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int codeSet = var2[var4];
            if (codeSet == cs) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean supportedWcharCodeSet(int cs) {
      if (this.wcharNativeCodeSet == cs) {
         return true;
      } else {
         int[] var2 = this.wcharConversionCodeSets;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int codeSet = var2[var4];
            if (codeSet == cs) {
               return true;
            }
         }

         return false;
      }
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.charNativeCodeSet = in.read_long();
      this.charConversionCodeSets = this.readCodeSetArray(in);
      this.wcharNativeCodeSet = in.read_long();
      this.wcharConversionCodeSets = this.readCodeSetArray(in);
      in.endEncapsulation(handle);
   }

   private int[] readCodeSetArray(CorbaInputStream in) {
      int numCodeSets = in.read_long();
      int[] codeSets = new int[numCodeSets];

      for(int i = 0; i < numCodeSets; ++i) {
         codeSets[i] = in.read_long();
      }

      return codeSets;
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_long(this.charNativeCodeSet);
      this.writeCodeSets(out, this.charConversionCodeSets);
      out.write_long(this.wcharNativeCodeSet);
      this.writeCodeSets(out, this.wcharConversionCodeSets);
      out.endEncapsulation(handle);
   }

   public void writeCodeSets(CorbaOutputStream out, int[] codeSets) {
      out.write_long(codeSets.length);
      int[] var3 = codeSets;
      int var4 = codeSets.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int codeSet = var3[var5];
         out.write_long(codeSet);
      }

   }
}
