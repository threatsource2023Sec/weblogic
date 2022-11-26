package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class islflds implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int IOR_HOST = 167772261;
   public static final int IOR_PORT = 33554534;
   public static final int IOR_CLTLMID = 167772263;
   public static final int ISL_CONNID = 104;
   public static final int ISL_CONNGEN = 33554537;
   public static final int ISH_CLTID = 167772266;
   public static final int ISH_DOMAIN = 167772267;
   public static final int ISH_PID = 33554540;
   public static final int ISH_QADDR = 33554541;
   public static final int ISL_GRPNO = 33554542;
   public static final int ISL_SRVID = 33554543;
   public static final int IOR_SSLSUPPORTS = 33554544;
   public static final int IOR_SSLREQUIRES = 33554545;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(167772261), "IOR_HOST");
         this.fieldtonameHashTable.put(new Integer(33554534), "IOR_PORT");
         this.fieldtonameHashTable.put(new Integer(167772263), "IOR_CLTLMID");
         this.fieldtonameHashTable.put(new Integer(104), "ISL_CONNID");
         this.fieldtonameHashTable.put(new Integer(33554537), "ISL_CONNGEN");
         this.fieldtonameHashTable.put(new Integer(167772266), "ISH_CLTID");
         this.fieldtonameHashTable.put(new Integer(167772267), "ISH_DOMAIN");
         this.fieldtonameHashTable.put(new Integer(33554540), "ISH_PID");
         this.fieldtonameHashTable.put(new Integer(33554541), "ISH_QADDR");
         this.fieldtonameHashTable.put(new Integer(33554542), "ISL_GRPNO");
         this.fieldtonameHashTable.put(new Integer(33554543), "ISL_SRVID");
         this.fieldtonameHashTable.put(new Integer(33554544), "IOR_SSLSUPPORTS");
         this.fieldtonameHashTable.put(new Integer(33554545), "IOR_SSLREQUIRES");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("IOR_HOST", new Integer(167772261));
         this.nametofieldHashTable.put("IOR_PORT", new Integer(33554534));
         this.nametofieldHashTable.put("IOR_CLTLMID", new Integer(167772263));
         this.nametofieldHashTable.put("ISL_CONNID", new Integer(104));
         this.nametofieldHashTable.put("ISL_CONNGEN", new Integer(33554537));
         this.nametofieldHashTable.put("ISH_CLTID", new Integer(167772266));
         this.nametofieldHashTable.put("ISH_DOMAIN", new Integer(167772267));
         this.nametofieldHashTable.put("ISH_PID", new Integer(33554540));
         this.nametofieldHashTable.put("ISH_QADDR", new Integer(33554541));
         this.nametofieldHashTable.put("ISL_GRPNO", new Integer(33554542));
         this.nametofieldHashTable.put("ISL_SRVID", new Integer(33554543));
         this.nametofieldHashTable.put("IOR_SSLSUPPORTS", new Integer(33554544));
         this.nametofieldHashTable.put("IOR_SSLREQUIRES", new Integer(33554545));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("IOR_HOST"), new String("IOR_PORT"), new String("IOR_CLTLMID"), new String("ISL_CONNID"), new String("ISL_CONNGEN"), new String("ISH_CLTID"), new String("ISH_DOMAIN"), new String("ISH_PID"), new String("ISH_QADDR"), new String("ISL_GRPNO"), new String("ISL_SRVID"), new String("IOR_SSLSUPPORTS"), new String("IOR_SSLREQUIRES")};
      return retval;
   }
}
