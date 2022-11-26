package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class Usysflds implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int INITMSK = 40961;
   public static final int CURSID = 8194;
   public static final int CURSOC = 8195;
   public static final int LEVKEY = 40964;
   public static final int STATLIN = 40965;
   public static final int FORMNAM = 40966;
   public static final int UPDTMOD = 7;
   public static final int SRVCNM = 40968;
   public static final int NEWFORM = 40969;
   public static final int CHGATTS = 49162;
   public static final int USYS1FLD = 40971;
   public static final int USYS2FLD = 40972;
   public static final int USYS3FLD = 40973;
   public static final int USYS4FLD = 49166;
   public static final int USYS5FLD = 49167;
   public static final int USYS6FLD = 49168;
   public static final int DESTSRVC = 40977;
   public static final int MODS = 49170;
   public static final int VALONENTRY = 40979;
   public static final int BQCMD = 41041;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(40961), "INITMSK");
         this.fieldtonameHashTable.put(new Integer(8194), "CURSID");
         this.fieldtonameHashTable.put(new Integer(8195), "CURSOC");
         this.fieldtonameHashTable.put(new Integer(40964), "LEVKEY");
         this.fieldtonameHashTable.put(new Integer(40965), "STATLIN");
         this.fieldtonameHashTable.put(new Integer(40966), "FORMNAM");
         this.fieldtonameHashTable.put(new Integer(7), "UPDTMOD");
         this.fieldtonameHashTable.put(new Integer(40968), "SRVCNM");
         this.fieldtonameHashTable.put(new Integer(40969), "NEWFORM");
         this.fieldtonameHashTable.put(new Integer(49162), "CHGATTS");
         this.fieldtonameHashTable.put(new Integer(40971), "USYS1FLD");
         this.fieldtonameHashTable.put(new Integer(40972), "USYS2FLD");
         this.fieldtonameHashTable.put(new Integer(40973), "USYS3FLD");
         this.fieldtonameHashTable.put(new Integer(49166), "USYS4FLD");
         this.fieldtonameHashTable.put(new Integer(49167), "USYS5FLD");
         this.fieldtonameHashTable.put(new Integer(49168), "USYS6FLD");
         this.fieldtonameHashTable.put(new Integer(40977), "DESTSRVC");
         this.fieldtonameHashTable.put(new Integer(49170), "MODS");
         this.fieldtonameHashTable.put(new Integer(40979), "VALONENTRY");
         this.fieldtonameHashTable.put(new Integer(41041), "BQCMD");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("INITMSK", new Integer(40961));
         this.nametofieldHashTable.put("CURSID", new Integer(8194));
         this.nametofieldHashTable.put("CURSOC", new Integer(8195));
         this.nametofieldHashTable.put("LEVKEY", new Integer(40964));
         this.nametofieldHashTable.put("STATLIN", new Integer(40965));
         this.nametofieldHashTable.put("FORMNAM", new Integer(40966));
         this.nametofieldHashTable.put("UPDTMOD", new Integer(7));
         this.nametofieldHashTable.put("SRVCNM", new Integer(40968));
         this.nametofieldHashTable.put("NEWFORM", new Integer(40969));
         this.nametofieldHashTable.put("CHGATTS", new Integer(49162));
         this.nametofieldHashTable.put("USYS1FLD", new Integer(40971));
         this.nametofieldHashTable.put("USYS2FLD", new Integer(40972));
         this.nametofieldHashTable.put("USYS3FLD", new Integer(40973));
         this.nametofieldHashTable.put("USYS4FLD", new Integer(49166));
         this.nametofieldHashTable.put("USYS5FLD", new Integer(49167));
         this.nametofieldHashTable.put("USYS6FLD", new Integer(49168));
         this.nametofieldHashTable.put("DESTSRVC", new Integer(40977));
         this.nametofieldHashTable.put("MODS", new Integer(49170));
         this.nametofieldHashTable.put("VALONENTRY", new Integer(40979));
         this.nametofieldHashTable.put("BQCMD", new Integer(41041));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("INITMSK"), new String("CURSID"), new String("CURSOC"), new String("LEVKEY"), new String("STATLIN"), new String("FORMNAM"), new String("UPDTMOD"), new String("SRVCNM"), new String("NEWFORM"), new String("CHGATTS"), new String("USYS1FLD"), new String("USYS2FLD"), new String("USYS3FLD"), new String("USYS4FLD"), new String("USYS5FLD"), new String("USYS6FLD"), new String("DESTSRVC"), new String("MODS"), new String("VALONENTRY"), new String("BQCMD")};
      return retval;
   }
}
