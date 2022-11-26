package monfox.toolkit.snmp.mgr;

import java.util.Hashtable;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.util.Job;
import monfox.toolkit.snmp.util.SchedulerException;
import monfox.toolkit.snmp.util.WorkItem;

public abstract class SnmpJob extends Job {
   protected SnmpJobListener _listener = null;
   protected SnmpVarBindList _vbList = null;
   protected SnmpSession _session = null;
   protected SnmpPeer _peer = null;
   protected Hashtable _properties = null;
   protected SnmpResponseListener _responseListener = null;

   protected SnmpJob(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5, WorkItem var6) throws SchedulerException {
      super(var1, "-", var6);
      this._peer = var2;
      this._session = var3;
      this._listener = var4;
      this._vbList = var5;
      this._responseListener = new j(this);
   }

   public SnmpJobListener getJobListener() {
      return this._listener;
   }

   public SnmpVarBindList getVarBindList() {
      return this._vbList;
   }

   public SnmpSession getSession() {
      return this._session;
   }

   public SnmpPeer getPeer() {
      return this._peer;
   }

   protected void setSession(SnmpSession var1) {
      this._session = var1;
   }

   protected void setPeer(SnmpPeer var1) {
      this._peer = var1;
   }

   protected void setJobListener(SnmpJobListener var1) {
      this._listener = var1;
   }

   protected SnmpResponseListener getResponseListener() {
      return this._responseListener;
   }

   public void cancel() {
      super.cancel();
   }

   protected void validate() throws SchedulerException {
      if (this._listener == null || this._vbList == null || this._session == null || this._peer == null) {
         throw new SchedulerException(a("L9?(,l3i\u0003/gw\r(4dmi'5i;i?!i\",:"));
      }
   }

   public Hashtable getProperties() {
      return this._properties;
   }

   public boolean hasProperties() {
      return this._properties != null;
   }

   public void setProperties(Hashtable var1) {
      this._properties = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 5;
               break;
            case 1:
               var10003 = 87;
               break;
            case 2:
               var10003 = 73;
               break;
            case 3:
               var10003 = 73;
               break;
            default:
               var10003 = 64;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
