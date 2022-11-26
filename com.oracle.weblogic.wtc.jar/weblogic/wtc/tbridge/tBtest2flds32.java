package weblogic.wtc.tbridge;

import weblogic.wtc.jatmi.FldTbl;

public final class tBtest2flds32 implements FldTbl {
   public static final int ELINK_APP_ERR = 167780353;
   public static final int ELINK_ADAPTER_ERR = 167780354;
   public static final int ELINK_ADAPTER_ERR_CODE = 167780355;
   public static final int STRING = 167772417;
   public static final int CARRAY = 201326850;
   public static final int LONG = 33554691;
   public static final int SHORT = 260;
   public static final int DOUBLE = 134217989;
   public static final int FLOAT = 100663558;
   public static final int CHAR = 67109127;
   public static final int FMLVO_NAME = 201327105;

   public String Fldid_to_name(int fldid) {
      switch (fldid) {
         case 260:
            return new String("SHORT");
         case 33554691:
            return new String("LONG");
         case 67109127:
            return new String("CHAR");
         case 100663558:
            return new String("FLOAT");
         case 134217989:
            return new String("DOUBLE");
         case 167772417:
            return new String("STRING");
         case 167780353:
            return new String("ELINK_APP_ERR");
         case 167780354:
            return new String("ELINK_ADAPTER_ERR");
         case 167780355:
            return new String("ELINK_ADAPTER_ERR_CODE");
         case 201326850:
            return new String("CARRAY");
         case 201327105:
            return new String("FMLVO_NAME");
         default:
            return null;
      }
   }

   public int name_to_Fldid(String name) {
      if (name.equals("ELINK_APP_ERR")) {
         return 167780353;
      } else if (name.equals("ELINK_ADAPTER_ERR")) {
         return 167780354;
      } else if (name.equals("ELINK_ADAPTER_ERR_CODE")) {
         return 167780355;
      } else if (name.equals("STRING")) {
         return 167772417;
      } else if (name.equals("CARRAY")) {
         return 201326850;
      } else if (name.equals("LONG")) {
         return 33554691;
      } else if (name.equals("SHORT")) {
         return 260;
      } else if (name.equals("DOUBLE")) {
         return 134217989;
      } else if (name.equals("FLOAT")) {
         return 100663558;
      } else if (name.equals("CHAR")) {
         return 67109127;
      } else {
         return name.equals("FMLVO_NAME") ? 201327105 : -1;
      }
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("ELINK_APP_ERR"), new String("ELINK_ADAPTER_ERR"), new String("ELINK_ADAPTER_ERR_CODE"), new String("STRING"), new String("CARRAY"), new String("LONG"), new String("SHORT"), new String("DOUBLE"), new String("FLOAT"), new String("CHAR"), new String("FMLVO_NAME")};
      return retval;
   }
}
