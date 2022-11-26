package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class Usysfl32 implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int INITMSK = 167772161;
   public static final int CURSID = 33554434;
   public static final int CURSOC = 33554435;
   public static final int LEVKEY = 167772164;
   public static final int STATLIN = 167772165;
   public static final int FORMNAM = 167772166;
   public static final int UPDTMOD = 7;
   public static final int SRVCNM = 167772168;
   public static final int NEWFORM = 167772169;
   public static final int CHGATTS = 201326602;
   public static final int USYS1FLD = 167772171;
   public static final int USYS2FLD = 167772172;
   public static final int USYS3FLD = 167772173;
   public static final int USYS4FLD = 201326606;
   public static final int USYS5FLD = 201326607;
   public static final int USYS6FLD = 201326608;
   public static final int DESTSRVC = 167772177;
   public static final int MODS = 201326610;
   public static final int VALONENTRY = 167772179;
   public static final int BQCMD = 167772241;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(167772161), "INITMSK");
         this.fieldtonameHashTable.put(new Integer(33554434), "CURSID");
         this.fieldtonameHashTable.put(new Integer(33554435), "CURSOC");
         this.fieldtonameHashTable.put(new Integer(167772164), "LEVKEY");
         this.fieldtonameHashTable.put(new Integer(167772165), "STATLIN");
         this.fieldtonameHashTable.put(new Integer(167772166), "FORMNAM");
         this.fieldtonameHashTable.put(new Integer(7), "UPDTMOD");
         this.fieldtonameHashTable.put(new Integer(167772168), "SRVCNM");
         this.fieldtonameHashTable.put(new Integer(167772169), "NEWFORM");
         this.fieldtonameHashTable.put(new Integer(201326602), "CHGATTS");
         this.fieldtonameHashTable.put(new Integer(167772171), "USYS1FLD");
         this.fieldtonameHashTable.put(new Integer(167772172), "USYS2FLD");
         this.fieldtonameHashTable.put(new Integer(167772173), "USYS3FLD");
         this.fieldtonameHashTable.put(new Integer(201326606), "USYS4FLD");
         this.fieldtonameHashTable.put(new Integer(201326607), "USYS5FLD");
         this.fieldtonameHashTable.put(new Integer(201326608), "USYS6FLD");
         this.fieldtonameHashTable.put(new Integer(167772177), "DESTSRVC");
         this.fieldtonameHashTable.put(new Integer(201326610), "MODS");
         this.fieldtonameHashTable.put(new Integer(167772179), "VALONENTRY");
         this.fieldtonameHashTable.put(new Integer(167772241), "BQCMD");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("INITMSK", new Integer(167772161));
         this.nametofieldHashTable.put("CURSID", new Integer(33554434));
         this.nametofieldHashTable.put("CURSOC", new Integer(33554435));
         this.nametofieldHashTable.put("LEVKEY", new Integer(167772164));
         this.nametofieldHashTable.put("STATLIN", new Integer(167772165));
         this.nametofieldHashTable.put("FORMNAM", new Integer(167772166));
         this.nametofieldHashTable.put("UPDTMOD", new Integer(7));
         this.nametofieldHashTable.put("SRVCNM", new Integer(167772168));
         this.nametofieldHashTable.put("NEWFORM", new Integer(167772169));
         this.nametofieldHashTable.put("CHGATTS", new Integer(201326602));
         this.nametofieldHashTable.put("USYS1FLD", new Integer(167772171));
         this.nametofieldHashTable.put("USYS2FLD", new Integer(167772172));
         this.nametofieldHashTable.put("USYS3FLD", new Integer(167772173));
         this.nametofieldHashTable.put("USYS4FLD", new Integer(201326606));
         this.nametofieldHashTable.put("USYS5FLD", new Integer(201326607));
         this.nametofieldHashTable.put("USYS6FLD", new Integer(201326608));
         this.nametofieldHashTable.put("DESTSRVC", new Integer(167772177));
         this.nametofieldHashTable.put("MODS", new Integer(201326610));
         this.nametofieldHashTable.put("VALONENTRY", new Integer(167772179));
         this.nametofieldHashTable.put("BQCMD", new Integer(167772241));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("INITMSK"), new String("CURSID"), new String("CURSOC"), new String("LEVKEY"), new String("STATLIN"), new String("FORMNAM"), new String("UPDTMOD"), new String("SRVCNM"), new String("NEWFORM"), new String("CHGATTS"), new String("USYS1FLD"), new String("USYS2FLD"), new String("USYS3FLD"), new String("USYS4FLD"), new String("USYS5FLD"), new String("USYS6FLD"), new String("DESTSRVC"), new String("MODS"), new String("VALONENTRY"), new String("BQCMD")};
      return retval;
   }
}
