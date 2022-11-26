package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class metaflds implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int TA_ECID = 197775061;
   public static final int TA_IMPORTEDFORMATID = 63557348;
   public static final int TA_IMPORTEDGTRID = 231329509;
   public static final int TA_IMPORTEDBQUAL = 231329510;
   public static final int TA_TRANAFFINITYCONTEXT_STR = 197775086;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(197775061), "TA_ECID");
         this.fieldtonameHashTable.put(new Integer(63557348), "TA_IMPORTEDFORMATID");
         this.fieldtonameHashTable.put(new Integer(231329509), "TA_IMPORTEDGTRID");
         this.fieldtonameHashTable.put(new Integer(231329510), "TA_IMPORTEDBQUAL");
         this.fieldtonameHashTable.put(new Integer(197775086), "TA_TRANAFFINITYCONTEXT_STR");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("TA_ECID", new Integer(197775061));
         this.nametofieldHashTable.put("TA_IMPORTEDFORMATID", new Integer(63557348));
         this.nametofieldHashTable.put("TA_IMPORTEDGTRID", new Integer(231329509));
         this.nametofieldHashTable.put("TA_IMPORTEDBQUAL", new Integer(231329510));
         this.nametofieldHashTable.put("TA_TRANAFFINITYCONTEXT_STR", new Integer(197775086));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("TA_ECID"), new String("TA_IMPORTEDFORMATID"), new String("TA_IMPORTEDGTRID"), new String("TA_IMPORTEDBQUAL"), new String("TA_TRANAFFINITYCONTEXT_STR")};
      return retval;
   }
}
