package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class tmibflds implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int TA__ENCPASSWD = 201334492;
   public static final int TA__BBVERSION = 33562333;
   public static final int TA__TUXVERSION = 33562334;
   public static final int TA__DATA = 201334495;
   public static final int TA__DIRNAME = 167780064;
   public static final int TA__DIRPERM = 33562337;
   public static final int TA__FILENAME = 167780066;
   public static final int TA__LENGTH = 33562339;
   public static final int TA__OFFSET = 33562340;
   public static final int TA__TOTSIZE = 33562341;
   public static final int TA__TRUNCATE = 167780070;
   public static final int TA__SEC_PRINCIPAL_ENCPASSWORD = 201334503;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(201334492), "TA__ENCPASSWD");
         this.fieldtonameHashTable.put(new Integer(33562333), "TA__BBVERSION");
         this.fieldtonameHashTable.put(new Integer(33562334), "TA__TUXVERSION");
         this.fieldtonameHashTable.put(new Integer(201334495), "TA__DATA");
         this.fieldtonameHashTable.put(new Integer(167780064), "TA__DIRNAME");
         this.fieldtonameHashTable.put(new Integer(33562337), "TA__DIRPERM");
         this.fieldtonameHashTable.put(new Integer(167780066), "TA__FILENAME");
         this.fieldtonameHashTable.put(new Integer(33562339), "TA__LENGTH");
         this.fieldtonameHashTable.put(new Integer(33562340), "TA__OFFSET");
         this.fieldtonameHashTable.put(new Integer(33562341), "TA__TOTSIZE");
         this.fieldtonameHashTable.put(new Integer(167780070), "TA__TRUNCATE");
         this.fieldtonameHashTable.put(new Integer(201334503), "TA__SEC_PRINCIPAL_ENCPASSWORD");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("TA__ENCPASSWD", new Integer(201334492));
         this.nametofieldHashTable.put("TA__BBVERSION", new Integer(33562333));
         this.nametofieldHashTable.put("TA__TUXVERSION", new Integer(33562334));
         this.nametofieldHashTable.put("TA__DATA", new Integer(201334495));
         this.nametofieldHashTable.put("TA__DIRNAME", new Integer(167780064));
         this.nametofieldHashTable.put("TA__DIRPERM", new Integer(33562337));
         this.nametofieldHashTable.put("TA__FILENAME", new Integer(167780066));
         this.nametofieldHashTable.put("TA__LENGTH", new Integer(33562339));
         this.nametofieldHashTable.put("TA__OFFSET", new Integer(33562340));
         this.nametofieldHashTable.put("TA__TOTSIZE", new Integer(33562341));
         this.nametofieldHashTable.put("TA__TRUNCATE", new Integer(167780070));
         this.nametofieldHashTable.put("TA__SEC_PRINCIPAL_ENCPASSWORD", new Integer(201334503));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("TA__ENCPASSWD"), new String("TA__BBVERSION"), new String("TA__TUXVERSION"), new String("TA__DATA"), new String("TA__DIRNAME"), new String("TA__DIRPERM"), new String("TA__FILENAME"), new String("TA__LENGTH"), new String("TA__OFFSET"), new String("TA__TOTSIZE"), new String("TA__TRUNCATE"), new String("TA__SEC_PRINCIPAL_ENCPASSWORD")};
      return retval;
   }
}
