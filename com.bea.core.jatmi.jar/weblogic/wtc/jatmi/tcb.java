package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class tcb implements Serializable {
   private static final long serialVersionUID = -3679507271985678559L;
   protected short type;
   public static final short USER = 0;
   public static final short TM = 1;
   public static final short TRAN = 2;
   public static final short WS = 3;
   public static final short UNSOL = 4;
   public static final short COMPOS_HDR = 5;
   public static final short COMPOS_FML = 6;
   public static final short TDOM = 7;
   public static final short TDOM_SEC = 8;
   public static final short ROUTE = 9;
   public static final short TDOM_TRAN = 10;
   public static final short TEST_TCM = 11;
   public static final short PKCS7_TCM = 12;
   public static final short QS_TCM = 13;
   public static final short PROP_TCM = 14;
   public static final short AAA_TCM = 15;
   public static final short CALLOUT = 16;
   public static final short TDOM_VALS = 17;
   public static final short CODESET_TCM = 18;
   public static final short META_TCM = 19;
   public static final short MAXDEF_TCM = 20;
   private static final String USER_STRING = "USER";
   private static final String TM_STRING = "TM";
   private static final String TRAN_STRING = "TRAN";
   private static final String WS_STRING = "WS";
   private static final String UNSOL_STRING = "UNSOL";
   private static final String COMPOS_HDR_STRING = "COMPOS_HDR";
   private static final String COMPOS_FML_STRING = "COMPOS_FML";
   private static final String TDOM_STRING = "TDOM";
   private static final String TDOM_SEC_STRING = "TDOM_SEC";
   private static final String ROUTE_STRING = "ROUTE";
   private static final String TDOM_TRAN_STRING = "TDOM_TRAN";
   private static final String TEST_TCM_STRING = "TEST_TCM";
   private static final String PKCS7_TCM_STRING = "PKCS7_TCM";
   private static final String QS_TCM_STRING = "QS_TCM";
   private static final String PROP_TCM_STRING = "PROP_TCM";
   private static final String AAA_TCM_STRING = "AAA_TCM";
   private static final String CALLOUT_STRING = "CALLOUT";
   private static final String TDOM_VALS_STRING = "TDOM_VALS";
   private static final String CODESET_TCM_STRING = "CODESET_TCM";
   private static final String META_TCM_STRING = "META_TCM";
   private static final String MAXDEF_TCM_STRING = "MAXDEF_TCM";

   public tcb(short type) {
      this.type = type;
   }

   public short getType() {
      return this.type;
   }

   abstract boolean prepareForCache();

   abstract void _tmpresend(DataOutputStream var1, tch var2) throws TPException, IOException;

   abstract int _tmpostrecv(DataInputStream var1, int var2, int var3) throws IOException;

   public String toString() {
      switch (this.type) {
         case 0:
            return "USER";
         case 1:
            return "TM";
         case 2:
            return "TRAN";
         case 3:
            return "WS";
         case 4:
            return "UNSOL";
         case 5:
            return "COMPOS_HDR";
         case 6:
            return "COMPOS_FML";
         case 7:
            return "TDOM";
         case 8:
            return "TDOM_SEC";
         case 9:
            return "ROUTE";
         case 10:
            return "TDOM_TRAN";
         case 11:
            return "TEST_TCM";
         case 12:
            return "PKCS7_TCM";
         case 13:
            return "QS_TCM";
         case 14:
            return "PROP_TCM";
         case 15:
            return "AAA_TCM";
         case 16:
            return "CALLOUT";
         case 17:
            return "TDOM_VALS";
         case 18:
            return "CODESET_TCM";
         case 19:
            return "META_TCM";
         default:
            return new String("Unknown tcb (" + this.type + ")");
      }
   }
}
