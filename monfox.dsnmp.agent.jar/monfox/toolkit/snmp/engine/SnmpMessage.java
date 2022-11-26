package monfox.toolkit.snmp.engine;

import java.io.Serializable;
import java.util.Hashtable;
import monfox.toolkit.snmp.SnmpException;

public class SnmpMessage implements Serializable {
   static final long serialVersionUID = -3849842911969596709L;
   private SnmpOidMap a = null;
   private boolean b = false;
   private SnmpMessageProfile c = null;
   private SnmpSecurityParameters d = null;
   private SnmpMessageParameters e = null;
   private long f = -1L;
   private int g = 0;
   private int h = 0;
   private SnmpPDU i = null;
   private int j = 0;
   private SnmpContext k = null;
   private SnmpEngineID l = null;
   private Hashtable m = null;
   private boolean n = false;
   private static final String o = "$Id: SnmpMessage.java,v 1.9 2011/01/26 21:26:38 sking Exp $";

   public SnmpMessage() {
   }

   public SnmpMessage(SnmpMessage var1) {
      this.g = var1.g;
      this.k = var1.k;
      this.h = var1.h;
      this.c = var1.c;
      this.e = var1.e;
      this.d = var1.d;
      this.l = var1.l;
      this.j = var1.j;
   }

   public int getVersion() {
      return this.g;
   }

   public void setVersion(int var1) {
      this.g = var1;
   }

   public SnmpContext getContext() {
      return this.k;
   }

   public void setContext(SnmpContext var1) {
      this.k = var1;
   }

   public int getMaxSize() {
      return this.h;
   }

   public void setMaxSize(int var1) {
      this.h = var1;
   }

   public SnmpMessageParameters getMessageParameters() {
      return this.e;
   }

   public void setMessageParameters(SnmpMessageParameters var1) {
      this.e = var1;
   }

   public SnmpMessageProfile getMessageProfile() {
      return this.c;
   }

   public void setMessageProfile(SnmpMessageProfile var1) {
      this.c = var1;
   }

   public SnmpSecurityParameters getSecurityParameters() {
      return this.d;
   }

   public void setSecurityParameters(SnmpSecurityParameters var1) {
      this.d = var1;
   }

   public SnmpEngineID getSnmpEngineID() {
      return this.l;
   }

   public void setSnmpEngineID(SnmpEngineID var1) {
      this.l = var1;
   }

   public SnmpPDU getData() {
      return this.i;
   }

   public void setData(SnmpPDU var1) {
      this.i = var1;
   }

   public int getMsgID() {
      return this.j;
   }

   public void setMsgID(int var1) {
      this.j = var1;
   }

   public void setUserTable(Hashtable var1) {
      this.m = var1;
   }

   public Hashtable getUserTable() {
      return this.m;
   }

   public void isDiscovery(boolean var1) {
      this.b = var1;
   }

   public boolean isDiscovery() {
      return this.b;
   }

   public String toString() {
      boolean var2 = SnmpPDU.i;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u000f#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002"));
      var1.append(a("\u000f#\u0002XUkc_5cv}N\u001fc%.\u000fX&%.\u000fX&%.\u000fX&%.\u000fX&%.\u000fX&%.\u000fX&%.\u000fX&%.\u0002"));
      var1.append(a("\u000f#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002"));
      var1.append(a("\u000f.\u000fXp`|\\\u0011ik3"));
      switch (this.g) {
         case 0:
            var1.append(a("s?"));
            if (!var2) {
               break;
            }

            SnmpException.b = !SnmpException.b;
         case 1:
            var1.append(a("s<"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("s="));
         case 2:
      }

      var1.append(a("\u000f.\u000fXkvif<;")).append(this.j);
      var1.append(a("\u000f.\u000fXkvi\u007f\u0019tdc\\E")).append(this.e);
      var1.append(a("\u000f.\u000fXkvi\u007f\nicgC\u001d;")).append(this.c);
      var1.append(a("\u000f.\u000fXckiF\u0016cLJ\u0012")).append(this.l);
      var1.append(a("\u000f.\u000fXu`m\u007f\u0019tdc\\E")).append(this.d);
      var1.append(a("\u000f.\u000fXej`[\u001d~q3")).append(this.k);
      var1.append(a("\u000f.\u000fXkdv|\u0011|`3")).append(this.h);
      var1.append(a("\u000f.\u000fXovJF\u000bejxJ\n\u007f8")).append(this.b);
      var1.append(a("\u000f#\u000f+hh~\u007f<S%JN\fg?."));
      var1.append(this.i);
      var1.append(a("\u000f#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002U+(#\u0002"));
      return var1.toString();
   }

   public long getTimestamp() {
      return this.f;
   }

   public void setTimestamp(long var1) {
      this.f = var1;
   }

   public void isTimeSync(boolean var1) {
      this.n = var1;
   }

   public boolean isTimeSync() {
      return this.n;
   }

   public void setOidMap(SnmpOidMap var1) {
      this.a = var1;
   }

   public SnmpOidMap getOidMap() {
      return this.a;
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
               var10003 = 14;
               break;
            case 2:
               var10003 = 47;
               break;
            case 3:
               var10003 = 120;
               break;
            default:
               var10003 = 6;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
