package weblogic.wtc.jatmi;

import java.util.Hashtable;

public final class evt_mib implements FldTbl {
   Hashtable nametofieldHashTable;
   Hashtable fieldtonameHashTable;
   public static final int TA_EVENT_NAME = 167779160;
   public static final int TA_EVENT_SEVERITY = 167779161;
   public static final int TA_EVENT_LMID = 167779162;
   public static final int TA_EVENT_TIME = 33561435;
   public static final int TA_EVENT_USEC = 33561436;
   public static final int TA_EVENT_DESCRIPTION = 167779165;
   public static final int TA_ERROR_STRING = 167779166;
   public static final int TA_EB_PID = 33561440;
   public static final int TA_EB_MID = 33561441;
   public static final int TA_EB_API_CALL = 167779170;
   public static final int TA_EB_SUBSCRIBER_TYPE = 33561443;
   public static final int TA_EB_LAST_SEQNO = 33561444;
   public static final int TA_EB_LAST_POLLED = 33561445;
   public static final int TA_EB_POLL_INTERVAL = 33561446;
   public static final int TA_EB_COMP_SUB = 7015;
   public static final int TA_EB_DB_SEQNO = 33561448;
   public static final int TA_EJB_NAME = 167779177;
   public static final int TA_EJB_METHOD_NAME = 167779178;

   public String Fldid_to_name(int fldid) {
      if (this.fieldtonameHashTable == null) {
         this.fieldtonameHashTable = new Hashtable();
         this.fieldtonameHashTable.put(new Integer(167779160), "TA_EVENT_NAME");
         this.fieldtonameHashTable.put(new Integer(167779161), "TA_EVENT_SEVERITY");
         this.fieldtonameHashTable.put(new Integer(167779162), "TA_EVENT_LMID");
         this.fieldtonameHashTable.put(new Integer(33561435), "TA_EVENT_TIME");
         this.fieldtonameHashTable.put(new Integer(33561436), "TA_EVENT_USEC");
         this.fieldtonameHashTable.put(new Integer(167779165), "TA_EVENT_DESCRIPTION");
         this.fieldtonameHashTable.put(new Integer(167779166), "TA_ERROR_STRING");
         this.fieldtonameHashTable.put(new Integer(33561440), "TA_EB_PID");
         this.fieldtonameHashTable.put(new Integer(33561441), "TA_EB_MID");
         this.fieldtonameHashTable.put(new Integer(167779170), "TA_EB_API_CALL");
         this.fieldtonameHashTable.put(new Integer(33561443), "TA_EB_SUBSCRIBER_TYPE");
         this.fieldtonameHashTable.put(new Integer(33561444), "TA_EB_LAST_SEQNO");
         this.fieldtonameHashTable.put(new Integer(33561445), "TA_EB_LAST_POLLED");
         this.fieldtonameHashTable.put(new Integer(33561446), "TA_EB_POLL_INTERVAL");
         this.fieldtonameHashTable.put(new Integer(7015), "TA_EB_COMP_SUB");
         this.fieldtonameHashTable.put(new Integer(33561448), "TA_EB_DB_SEQNO");
         this.fieldtonameHashTable.put(new Integer(167779177), "TA_EJB_NAME");
         this.fieldtonameHashTable.put(new Integer(167779178), "TA_EJB_METHOD_NAME");
      }

      return (String)this.fieldtonameHashTable.get(new Integer(fldid));
   }

   public int name_to_Fldid(String name) {
      if (this.nametofieldHashTable == null) {
         this.nametofieldHashTable = new Hashtable();
         this.nametofieldHashTable.put("TA_EVENT_NAME", new Integer(167779160));
         this.nametofieldHashTable.put("TA_EVENT_SEVERITY", new Integer(167779161));
         this.nametofieldHashTable.put("TA_EVENT_LMID", new Integer(167779162));
         this.nametofieldHashTable.put("TA_EVENT_TIME", new Integer(33561435));
         this.nametofieldHashTable.put("TA_EVENT_USEC", new Integer(33561436));
         this.nametofieldHashTable.put("TA_EVENT_DESCRIPTION", new Integer(167779165));
         this.nametofieldHashTable.put("TA_ERROR_STRING", new Integer(167779166));
         this.nametofieldHashTable.put("TA_EB_PID", new Integer(33561440));
         this.nametofieldHashTable.put("TA_EB_MID", new Integer(33561441));
         this.nametofieldHashTable.put("TA_EB_API_CALL", new Integer(167779170));
         this.nametofieldHashTable.put("TA_EB_SUBSCRIBER_TYPE", new Integer(33561443));
         this.nametofieldHashTable.put("TA_EB_LAST_SEQNO", new Integer(33561444));
         this.nametofieldHashTable.put("TA_EB_LAST_POLLED", new Integer(33561445));
         this.nametofieldHashTable.put("TA_EB_POLL_INTERVAL", new Integer(33561446));
         this.nametofieldHashTable.put("TA_EB_COMP_SUB", new Integer(7015));
         this.nametofieldHashTable.put("TA_EB_DB_SEQNO", new Integer(33561448));
         this.nametofieldHashTable.put("TA_EJB_NAME", new Integer(167779177));
         this.nametofieldHashTable.put("TA_EJB_METHOD_NAME", new Integer(167779178));
      }

      Integer fld = (Integer)this.nametofieldHashTable.get(name);
      return fld == null ? -1 : fld;
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("TA_EVENT_NAME"), new String("TA_EVENT_SEVERITY"), new String("TA_EVENT_LMID"), new String("TA_EVENT_TIME"), new String("TA_EVENT_USEC"), new String("TA_EVENT_DESCRIPTION"), new String("TA_ERROR_STRING"), new String("TA_EB_PID"), new String("TA_EB_MID"), new String("TA_EB_API_CALL"), new String("TA_EB_SUBSCRIBER_TYPE"), new String("TA_EB_LAST_SEQNO"), new String("TA_EB_LAST_POLLED"), new String("TA_EB_POLL_INTERVAL"), new String("TA_EB_COMP_SUB"), new String("TA_EB_DB_SEQNO"), new String("TA_EJB_NAME"), new String("TA_EJB_METHOD_NAME")};
      return retval;
   }
}
