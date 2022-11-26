package weblogic.wtc.jatmi;

import java.io.Serializable;

public abstract class StandardTypes implements TypedBuffer, Serializable {
   private static final long serialVersionUID = 5828737263190556409L;
   private int hint_index = -1;
   private String myType;
   private String mySubtype;
   private Object tfmhCache;
   static final String RPCRQ_STRING = "rpcrqst";
   static final String RPCRP_STRING = "rpcrply";
   static final String MTTYP_STRING = "metamsg";
   static final String TPINITTYPE_STRING = "TPINIT";
   static final String COMPOS_STRING = "COMPOS";
   static final String ITCM_STRING = "ITCM";
   static final String TM_STRING = "TM";
   static final String TRAN_STRING = "TRAN";
   static final String WS_STRING = "WS";
   static final String UNSOL_STRING = "UNSOL";
   static final String CMPS_HDR_STRING = "CMPS_HDR";
   static final String TGIOP_STRING = "TGIOP";
   static final String WSRPCRQ_STRING = "wsrpcrq";
   static final String ROUTE_STRING = "ROUTE";
   static final String PROP_STRING = "PROP";
   static final String CALLOUT_STRING = "CALLOUT";
   static final String TDOM_VALS_STRING = "TDOM_VALS";
   static final String CARRAY_STRING = "CARRAY";
   static final String STRING_STRING = "STRING";
   static final String FML_STRING = "FML";
   static final String VIEW_STRING = "VIEW";
   static final String X_OCTET_STRING = "X_OCTET";
   static final String X_C_TYPE_STRING = "X_C_TYPE";
   static final String X_COMMON_STRING = "X_COMMON";
   static final String FML32_STRING = "FML32";
   static final String VIEW32_STRING = "VIEW32";
   static final String XML_STRING = "XML";
   static final String MBSTRING_STRING = "MBSTRING";
   public static final int RPCRQ_HINT = 0;
   public static final int RPCRP_HINT = 1;
   public static final int MTTYP_HINT = 2;
   public static final int TPINITTYPE_HINT = 3;
   public static final int COMPOS_HINT = 4;
   public static final int TM_HINT = 5;
   public static final int TRAN_HINT = 6;
   public static final int WS_HINT = 7;
   public static final int UNSOL_HINT = 8;
   public static final int CMPS_HDR_HINT = 9;
   public static final int TGIOP_HINT = 10;
   public static final int WSRPCRQ_HINT = 11;
   public static final int ROUTE_HINT = 12;
   public static final int PROP_HINT = 13;
   public static final int CALLOUT_HINT = 14;
   public static final int TDOM_VALS_HINT = 15;
   public static final int CARRAY_HINT = 16;
   public static final int STRING_HINT = 17;
   public static final int FML_HINT = 18;
   public static final int VIEW_HINT = 19;
   public static final int X_OCTET_HINT = 20;
   public static final int X_C_TYPE_HINT = 21;
   public static final int X_COMMON_HINT = 22;
   public static final int FML32_HINT = 23;
   public static final int VIEW32_HINT = 24;
   public static final int XML_HINT = 25;
   public static final int MBSTRING_HINT = 26;
   static final int MAXKNOWNTYPE = 27;

   public StandardTypes() {
   }

   public StandardTypes(String type, int index) {
      this.setTypeSubtypeHint(type, (String)null, index);
   }

   public StandardTypes(String type, String subtype, int index) {
      this.setTypeSubtypeHint(type, subtype, index);
   }

   public int getHintIndex() {
      return this.hint_index;
   }

   public String getType() {
      return this.myType;
   }

   public String getSubtype() {
      return this.mySubtype;
   }

   private void setTypeSubtypeHint(String type, String subtype, int index) {
      this.myType = type;
      this.mySubtype = subtype;
      if (index >= 0 && index < 27) {
         String hint_subtype;
         if (!hint_to_type(index).equals(type) || (hint_subtype = this.hint_to_subtype(index)) != null && !hint_subtype.equals(subtype)) {
            this.hint_index = this.string_to_hint(type, subtype);
         } else {
            this.hint_index = index;
         }
      } else {
         this.hint_index = index;
      }

   }

   private int string_to_hint(String type, String subtype) {
      if (type.equals("rpcrqst")) {
         return 0;
      } else if (type.equals("rpcrply")) {
         return 1;
      } else if (type.equals("metamsg")) {
         return 2;
      } else if (type.equals("TPINIT")) {
         return 3;
      } else if (type.equals("COMPOS")) {
         return 4;
      } else {
         if (type.equals("ITCM")) {
            if (subtype == null) {
               return 27;
            }

            if (subtype.equals("TM")) {
               return 5;
            }

            if (subtype.equals("TRAN")) {
               return 6;
            }

            if (subtype.equals("WS")) {
               return 7;
            }

            if (subtype.equals("UNSOL")) {
               return 8;
            }

            if (subtype.equals("CMPS_HDR")) {
               return 9;
            }

            if (subtype.equals("ROUTE")) {
               return 12;
            }

            if (type.equals("PROP")) {
               return 13;
            }

            if (type.equals("CALLOUT")) {
               return 14;
            }

            if (type.equals("TDOM_VALS")) {
               return 15;
            }
         }

         if (type.equals("TGIOP")) {
            return 10;
         } else if (type.equals("wsrpcrq")) {
            return 11;
         } else if (type.equals("CARRAY")) {
            return 16;
         } else if (type.equals("STRING")) {
            return 17;
         } else if (type.equals("FML")) {
            return 18;
         } else if (type.equals("VIEW")) {
            return 19;
         } else if (type.equals("X_OCTET")) {
            return 20;
         } else if (type.equals("X_C_TYPE")) {
            return 21;
         } else if (type.equals("X_COMMON")) {
            return 22;
         } else if (type.equals("FML32")) {
            return 23;
         } else if (type.equals("VIEW32")) {
            return 24;
         } else if (type.equals("XML")) {
            return 25;
         } else {
            return type.equals("MBSTRING") ? 26 : 27;
         }
      }
   }

   public static String hint_to_type(int hint) {
      switch (hint) {
         case 0:
            return "rpcrqst";
         case 1:
            return "rpcrply";
         case 2:
            return "metamsg";
         case 3:
            return "TPINIT";
         case 4:
            return "COMPOS";
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 12:
         case 13:
         case 14:
         case 15:
            return "ITCM";
         case 10:
            return "TGIOP";
         case 11:
            return "wsrpcrq";
         case 16:
            return "CARRAY";
         case 17:
            return "STRING";
         case 18:
            return "FML";
         case 19:
            return "VIEW";
         case 20:
            return "X_OCTET";
         case 21:
            return "X_C_TYPE";
         case 22:
            return "X_COMMON";
         case 23:
            return "FML32";
         case 24:
            return "VIEW32";
         case 25:
            return "XML";
         case 26:
            return "MBSTRING";
         default:
            return null;
      }
   }

   private String hint_to_subtype(int hint) {
      switch (hint) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 10:
         case 11:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
            return null;
         case 5:
            return "TM";
         case 6:
            return "TRAN";
         case 7:
            return "WS";
         case 8:
            return "UNSOL";
         case 9:
            return "CMPS_HDR";
         case 12:
            return "ROUTE";
         case 13:
            return "PROP";
         case 14:
            return "CALLOUT";
         case 15:
            return "TDOM_VALS";
         default:
            return null;
      }
   }

   public Object getTfmhCache() {
      return this.tfmhCache;
   }

   public void setTfmhCache(Object toCache) {
      this.tfmhCache = toCache;
   }
}
