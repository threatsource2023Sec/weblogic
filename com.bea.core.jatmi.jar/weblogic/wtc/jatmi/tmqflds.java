package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class tmqflds implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int TQ_DIAGNOSTIC = 8193;
   public static final int TQ_PRIORITY = 8194;
   public static final int TQ_FLAGS = 8195;
   public static final int TQ_TIME = 8196;
   public static final int TQ_FAILUREQ = 40971;
   public static final int TQ_REPLYQ = 40972;
   public static final int TQ_QNAME = 40973;
   public static final int TQ_CORRID = 49173;
   public static final int TQ_MSGID = 49174;
   public static final int TQ_DELIVERYQOS = 8197;
   public static final int TQ_REPLYQOS = 8198;
   public static final int TQ_EXPTIME = 8199;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(8193), "TQ_DIAGNOSTIC");
         this.fieldtonameHashTable.put(new Integer(8194), "TQ_PRIORITY");
         this.fieldtonameHashTable.put(new Integer(8195), "TQ_FLAGS");
         this.fieldtonameHashTable.put(new Integer(8196), "TQ_TIME");
         this.fieldtonameHashTable.put(new Integer(40971), "TQ_FAILUREQ");
         this.fieldtonameHashTable.put(new Integer(40972), "TQ_REPLYQ");
         this.fieldtonameHashTable.put(new Integer(40973), "TQ_QNAME");
         this.fieldtonameHashTable.put(new Integer(49173), "TQ_CORRID");
         this.fieldtonameHashTable.put(new Integer(49174), "TQ_MSGID");
         this.fieldtonameHashTable.put(new Integer(8197), "TQ_DELIVERYQOS");
         this.fieldtonameHashTable.put(new Integer(8198), "TQ_REPLYQOS");
         this.fieldtonameHashTable.put(new Integer(8199), "TQ_EXPTIME");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("TQ_DIAGNOSTIC", new Integer(8193));
         this.nametofieldHashTable.put("TQ_PRIORITY", new Integer(8194));
         this.nametofieldHashTable.put("TQ_FLAGS", new Integer(8195));
         this.nametofieldHashTable.put("TQ_TIME", new Integer(8196));
         this.nametofieldHashTable.put("TQ_FAILUREQ", new Integer(40971));
         this.nametofieldHashTable.put("TQ_REPLYQ", new Integer(40972));
         this.nametofieldHashTable.put("TQ_QNAME", new Integer(40973));
         this.nametofieldHashTable.put("TQ_CORRID", new Integer(49173));
         this.nametofieldHashTable.put("TQ_MSGID", new Integer(49174));
         this.nametofieldHashTable.put("TQ_DELIVERYQOS", new Integer(8197));
         this.nametofieldHashTable.put("TQ_REPLYQOS", new Integer(8198));
         this.nametofieldHashTable.put("TQ_EXPTIME", new Integer(8199));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("TQ_DIAGNOSTIC"), new String("TQ_PRIORITY"), new String("TQ_FLAGS"), new String("TQ_TIME"), new String("TQ_FAILUREQ"), new String("TQ_REPLYQ"), new String("TQ_QNAME"), new String("TQ_CORRID"), new String("TQ_MSGID"), new String("TQ_DELIVERYQOS"), new String("TQ_REPLYQOS"), new String("TQ_EXPTIME")};
      return retval;
   }
}
