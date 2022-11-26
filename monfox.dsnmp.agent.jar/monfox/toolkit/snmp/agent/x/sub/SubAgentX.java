package monfox.toolkit.snmp.agent.x.sub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableListener;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.x.common.AgentXException;
import monfox.toolkit.snmp.agent.x.common.AgentXSocketFactory;
import monfox.toolkit.snmp.agent.x.sub.service.SubAgentXApi;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public class SubAgentX {
   public static Config DEFAULT_CONFIG = null;
   private static AgentXSocketFactory a;
   private SubAgentXApi b;
   private SubAgentXApi.Session c;
   private Listener d;
   private SnmpMibTableListener e;
   private SubStatusListener f;
   private Vector g;
   private Map h;
   private Logger i;
   public static int j;

   public SubAgentX(SnmpAgent var1) {
      this(var1, DEFAULT_CONFIG, false);
   }

   public SubAgentX(SnmpAgent var1, Config var2, boolean var3) {
      int var4 = j;
      super();
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = new TableListener();
      this.f = null;
      this.g = new Vector();
      this.h = new Hashtable();
      this.i = Logger.getInstance(a("W=\r9\u0000"), a("R)\u0006:\u0004>6"), a("@\u001b!57v\u00007,"));
      this.f = new SubStatusListener();
      this.b = new SubAgentXApi(var1, var2, var3);
      this.b.addStatusListener(this.f);
      if (var4 != 0) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public synchronized void startup() {
      this.b.startup();
   }

   public synchronized void shutdown() {
      this.b.shutdown();
   }

   public boolean pingMasterAgent() {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T\"v\t*\u0007$v\u001cc\u0007%q\u001a1\u001153\u000f7T${\u00070T$z\u0003&"));
         return false;
      } else {
         try {
            this.c.performPing();
            return true;
         } catch (AgentXException var2) {
            this.i.debug(a("c\u0007-\u0013pu\u000f*\u00185w"), var2);
         } catch (Exception var3) {
            this.i.debug(a("c\u0007-\u0013pu\u000f*\u00185w"), var3);
         }

         return false;
      }
   }

   public void addNotifyGroup(String var1) {
      this.b.addNotifyGroup(var1);
   }

   public void removeNotifyGroup(String var1) {
      this.b.removeNotifyGroup(var1);
   }

   public Subtree addSubtree(SnmpOid var1) {
      return this.addSubtree(new Subtree(var1));
   }

   public synchronized Subtree addSubtree(Subtree var1) {
      this.g.add(var1);
      this.a(var1);
      return var1;
   }

   public synchronized void removeSubtree(Subtree var1) {
      this.g.remove(var1);
      this.b(var1);
   }

   public synchronized void addSharedTable(SnmpMibTable var1) {
      int var4 = j;
      Iterator var2 = var1.getRows();

      while(true) {
         if (var2.hasNext()) {
            SnmpMibTableRow var3 = (SnmpMibTableRow)var2.next();
            this.addSharedTableRow(var3);
            if (var4 != 0) {
               break;
            }

            if (var4 == 0) {
               continue;
            }
         }

         var1.addListener(this.e);
         break;
      }

   }

   public synchronized void removeSharedTable(SnmpMibTable var1) {
      var1.removeListener(this.e);
      Iterator var2 = var1.getRows();

      while(var2.hasNext()) {
         SnmpMibTableRow var3 = (SnmpMibTableRow)var2.next();
         this.removeSharedTableRow(var3);
         if (j != 0) {
            break;
         }
      }

   }

   public synchronized void addSharedTableRow(SnmpMibTableRow var1) {
      this.addSharedTableRow((String)null, var1);
   }

   public synchronized void addSharedTableRow(String var1, SnmpMibTableRow var2) {
      SharedRow var3 = (SharedRow)this.h.get(var2);
      if (var3 == null) {
         var3 = new SharedRow(var1, var2);
         this.h.put(var2, var3);
         this.a(var3);
      }
   }

   public synchronized void removeSharedTableRow(SnmpMibTableRow var1) {
      SharedRow var2 = (SharedRow)this.h.remove(var1);
      if (var2 != null) {
         this.b(var2);
      }

   }

   private synchronized void a() {
      try {
         this.i.debug(a("|\u001e&\u001a9}\tc57v\u00007,p`\u000b0\u00079|\u0000"));
         this.c = this.b.performOpenSession();
         if (this.d != null) {
            this.d.sessionUp(this);
         }

         this.b();
         this.c();
      } catch (Exception var2) {
         this.i.error(a("p\u000f-\u001a?gN \u00065r\u001a&T#v\u001d0\u001d?}"), var2);
      }

   }

   private synchronized void b() {
      this.i.debug(a("a\u000b$\u001d#g\u000b15<\u007f=6\u0016$a\u000b&\u0007j"));
      Iterator var1 = ((Vector)((Vector)this.g.clone())).iterator();

      while(var1.hasNext()) {
         Subtree var2 = (Subtree)var1.next();
         this.a(var2);
         if (j != 0) {
            break;
         }
      }

   }

   private synchronized void c() {
      Vector var1 = new Vector();
      var1.addAll(this.h.values());
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         SharedRow var3 = (SharedRow)var2.next();
         this.a(var3);
         if (j != 0) {
            break;
         }
      }

   }

   private synchronized void d() {
      Vector var1 = new Vector();
      var1.addAll(this.h.values());
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         SharedRow var3 = (SharedRow)var2.next();
         this.b(var3);
         if (j != 0) {
            break;
         }
      }

   }

   private synchronized void a(SharedRow var1) {
      if (this.c(var1)) {
         this.e(var1);
      }

   }

   private synchronized void b(SharedRow var1) {
      this.d(var1);
      this.f(var1);
   }

   private synchronized boolean c(SharedRow var1) {
      int var9 = j;
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T1\u007f\u0002,\u00171g\u000bl\u00065t\u00070\u00005aN1\u001b'3\u000f7T${\u00070T$z\u0003&"));
         return false;
      } else {
         SnmpVarBindList var2 = new SnmpVarBindList();
         SnmpMibTableRow var3 = var1.b;

         Exception var10000;
         Exception var16;
         label82: {
            SnmpObjectInfo[] var5;
            SnmpValue[] var6;
            boolean var10001;
            try {
               SnmpMibTable var4 = var3.getTable();
               var5 = var4.getIndexInfo();
               var6 = var3.getIndexValues();
               if (var5.length != var6.length) {
                  this.i.error(a("0N*\u001a4v\u0016c\u0017?\u007f\u001b.\u001a#3O~Ts3\u0007-\u00105kN5\u0015<f\u000b0Np") + var3);
                  return false;
               }
            } catch (Exception var15) {
               var10000 = var15;
               var10001 = false;
               break label82;
            }

            AgentXException var18;
            label83: {
               label60: {
                  label76: {
                     try {
                        int var7 = 0;

                        while(var7 < var5.length) {
                           SnmpVarBind var8 = new SnmpVarBind(var5[var7].getOid(), var6[var7]);
                           var2.add(var8);
                           ++var7;
                           if (var9 != 0) {
                              break label76;
                           }

                           if (var9 != 0) {
                              break;
                           }
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        var10001 = false;
                        break label82;
                     }

                     var1.d = var2;

                     try {
                        this.c.performIndexAllocate(var1.a, var2);
                     } catch (AgentXException var12) {
                        var18 = var12;
                        var10001 = false;
                        break label83;
                     } catch (Exception var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label60;
                     }
                  }

                  try {
                     if (this.d != null) {
                     }

                     return true;
                  } catch (AgentXException var10) {
                     var18 = var10;
                     var10001 = false;
                     break label83;
                  } catch (Exception var11) {
                     var10000 = var11;
                     var10001 = false;
                  }
               }

               var16 = var10000;
               this.i.error(a("f\u0000&\f v\r7\u001143\u000b;\u00175c\u001a*\u001b>"), var16);
               return false;
            }

            AgentXException var17 = var18;
            if (this.d != null) {
               this.d.handleException(var17);
            }

            return false;
         }

         var16 = var10000;
         this.i.error(a("p\u000f-\u001a?gN3\u0006?p\u000b0\u0007pz\u0000'\u0011(v\u001dc\u0012?aN1\u001b')N") + var3, var16);
         return false;
      }
   }

   private synchronized boolean d(SharedRow var1) {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T1\u007f\u0002,\u00171g\u000bl\u00065t\u00070\u00005aN1\u001b'3\u000f7T${\u00070T$z\u0003&"));
         return false;
      } else {
         SnmpMibTableRow var2 = var1.b;
         SnmpVarBindList var3 = var1.d;
         if (var3 == null) {
            return false;
         } else {
            try {
               this.c.performIndexDeallocate(var1.a, var3);
               if (this.d != null) {
               }

               return true;
            } catch (AgentXException var5) {
               if (this.d != null) {
                  this.d.handleException(var5);
               }
            } catch (Exception var6) {
               this.i.error(a("f\u0000&\f v\r7\u001143\u000b;\u00175c\u001a*\u001b>"), var6);
            }

            return false;
         }
      }
   }

   private synchronized void e(SharedRow var1) {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T1\u007f\u0002,\u00171g\u000bl\u00065t\u00070\u00005aN1\u001b'3\u000f7T${\u00070T$z\u0003&"));
      } else {
         SnmpMibTableRow var2 = var1.b;
         SnmpMibTable var3 = var2.getTable();
         SnmpOid var4 = var2.getClassOid();
         SnmpOid var5 = (SnmpOid)var4.clone();
         var5.append(1L);
         var5.append(var2.getIndex());
         Subtree var6 = new Subtree(var5, var1.a, var4.getLength() + 1, var3.getMaxColumnNum(), -1, 127, true);
         var1.c = var6;
         this.a(var6);
      }
   }

   private synchronized void f(SharedRow var1) {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T1\u007f\u0002,\u00171g\u000bl\u00065t\u00070\u00005aN1\u001b'3\u000f7T${\u00070T$z\u0003&"));
      } else {
         SnmpMibTableRow var2 = var1.b;
         Subtree var3 = var1.c;
         if (var3 == null) {
            this.i.debug(a("a\u00014T#f\f7\u00065vN-\u001b$3\u001c&\u00139`\u001a&\u00065wBc\u001a?gN6\u001a\"v\t*\u0007$v\u001c*\u001a7)") + var2);
         } else {
            if (this.b(var3)) {
               var1.c = null;
            }

         }
      }
   }

   private synchronized boolean a(Subtree var1) {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T\"v\t*\u0007$v\u001cc\u0007%q\u001a1\u001153\u000f7T${\u00070T$z\u0003&"));
         return false;
      } else {
         try {
            this.c.performRegister(var1.getSubtreeOid(), var1.getContext(), var1.getRangeSubId(), var1.getRangeUpperBound(), var1.getTimeoutSecs(), var1.getPriority(), var1.isInstance());
            if (this.d != null) {
               this.d.subtreeRegistered(this, var1);
            }

            return true;
         } catch (AgentXException var3) {
            if (this.d != null) {
               this.d.handleException(var3);
            }
         } catch (Exception var4) {
            this.i.error(a("f\u0000&\f v\r7\u001143\u000b;\u00175c\u001a*\u001b>"), var4);
         }

         return false;
      }
   }

   private synchronized boolean b(Subtree var1) {
      if (this.c == null) {
         this.i.debug(a("}\u0001c\u00075`\u001d*\u001b>?N \u0015>}\u00017T\"v\t*\u0007$v\u001cc\u0007%q\u001a1\u001153\u000f7T${\u00070T$z\u0003&"));
         return false;
      } else {
         try {
            this.c.performUnregister(var1.getSubtreeOid(), var1.getContext(), var1.getRangeSubId(), var1.getRangeUpperBound(), var1.getPriority(), var1.isInstance());
            if (this.d != null) {
               this.d.subtreeUnregistered(this, var1);
            }

            return true;
         } catch (AgentXException var3) {
            if (this.d != null) {
               this.d.handleException(var3);
            }
         } catch (Exception var4) {
            this.i.error(a("f\u0000&\f v\r7\u001143\u000b;\u00175c\u001a*\u001b>"), var4);
         }

         return false;
      }
   }

   public void setListener(Listener var1) {
      this.d = var1;
   }

   public static AgentXSocketFactory getSocketFactory() {
      return a;
   }

   public static void setSocketFactory(AgentXSocketFactory var0) {
      a = var0;
   }

   static {
      try {
         DEFAULT_CONFIG = new Config(InetAddress.getByName(a("\u007f\u0001 \u0015<{\u00010\u0000")), 705);
      } catch (Exception var2) {
         Logger var1 = Logger.getInstance(a("W=\r9\u0000"), a("R)\u0006:\u0004>6"), a("@\u001b!57v\u00007,"));
         var1.error(a("P/\r:\u001fGN*\u001a9g\u0007\"\u00189i\u000bc\u00105u\u000f6\u0018$3=6\u0016\u0011t\u000b-\u0000\b=-,\u001a6z\t"), var2);
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 19;
               break;
            case 1:
               var10003 = 110;
               break;
            case 2:
               var10003 = 67;
               break;
            case 3:
               var10003 = 116;
               break;
            default:
               var10003 = 80;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class SubStatusListener implements SubAgentXApi.StatusListener {
      private SubStatusListener() {
      }

      public void connectionUp(SubAgentXApi var1) {
         SubAgentX.this.i.comms(a("1\u000b\u000bh_(L\riE\u001e\t\rrB\u001f\u0002N:~ RT") + var1);
         Thread var2 = new Thread() {
            public void run() {
               SubAgentX.this.a();
            }
         };
         var2.setDaemon(true);
         var2.start();
      }

      public void connectionDown(SubAgentXApi var1) {
         SubAgentX.this.i.comms(a("1\u000b\u000bh_(L\riE\u001e\t\rrB\u001f\u0002N:o?; 8\u0011") + var1);
         SubAgentX.this.c = null;
         if (SubAgentX.this.d != null) {
            SubAgentX.this.d.sessionDown(SubAgentX.this);
         }

      }

      public void sessionClosed(SubAgentXApi var1, SubAgentXApi.Session var2) {
         SubAgentX.this.i.comms(a("1\u000b\u000bh_(L\u001dcX\u0003\u0005\u0001h\u000b\u0013\u0000\u0001uN\u0014V") + var2);
         SubAgentX.this.c = null;
         if (SubAgentX.this.d != null) {
            SubAgentX.this.d.sessionDown(SubAgentX.this);
         }

      }

      // $FF: synthetic method
      SubStatusListener(Object var2) {
         this();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 112;
                  break;
               case 1:
                  var10003 = 108;
                  break;
               case 2:
                  var10003 = 110;
                  break;
               case 3:
                  var10003 = 6;
                  break;
               default:
                  var10003 = 43;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class TableListener implements SnmpMibTableListener {
      public void rowInit(SnmpMibTableRow var1) {
      }

      public void rowAdded(SnmpMibTableRow var1) {
         SubAgentX.this.addSharedTableRow(var1);
      }

      public void rowActivated(SnmpMibTableRow var1) {
      }

      public void rowDeactivated(SnmpMibTableRow var1) {
      }

      public void rowRemoved(SnmpMibTableRow var1) {
         SubAgentX.this.removeSharedTableRow(var1);
      }
   }

   class SharedRow {
      String a;
      SnmpMibTableRow b;
      Subtree c;
      SnmpVarBindList d;

      SharedRow(String var2, SnmpMibTableRow var3) {
         this.a = var2;
         this.b = var3;
      }
   }

   public static class Subtree {
      private SnmpOid a;
      private boolean b;
      private int c;
      private int d;
      private int e;
      private int f;
      private String g;

      public Subtree(SnmpOid var1) {
         this(var1, (String)null, 0, 0, 0, 127, false);
      }

      public Subtree(SnmpOid var1, String var2, int var3, int var4, int var5, int var6, boolean var7) {
         int var8 = SubAgentX.j;
         super();
         this.a = null;
         this.b = false;
         this.c = 0;
         this.d = 127;
         this.e = 0;
         this.f = 0;
         this.g = null;
         this.a = var1;
         this.b = var7;
         this.c = var5;
         this.d = var6;
         this.e = var3;
         this.f = var4;
         this.g = var2;
         if (SnmpException.b) {
            ++var8;
            SubAgentX.j = var8;
         }

      }

      public String getContext() {
         return this.g;
      }

      public void setContext(String var1) {
         this.g = var1;
      }

      public SnmpOid getSubtreeOid() {
         return this.a;
      }

      public void setSubtreeOid(SnmpOid var1) {
         this.a = var1;
      }

      public int getRangeSubId() {
         return this.e;
      }

      public void setRangeSubId(int var1) {
         this.e = var1;
      }

      public int getRangeUpperBound() {
         return this.f;
      }

      public void setRangeUpperBound(int var1) {
         this.f = var1;
      }

      public boolean isInstance() {
         return this.b;
      }

      public void isInstance(boolean var1) {
         this.b = var1;
      }

      public int getTimeoutSecs() {
         return this.c;
      }

      public void setTimeoutSecs(int var1) {
         this.c = var1;
      }

      public int getPriority() {
         return this.d;
      }

      public void setPriority(int var1) {
         this.d = var1;
      }
   }

   public static class Config extends SubAgentXApi.Config {
      public Config(String var1, int var2) throws UnknownHostException {
         super(var1, var2);
      }

      public Config(InetAddress var1, int var2) {
         super(var1, var2);
      }

      public SnmpOid getSubAgentId() {
         return super.getSubAgentId();
      }

      public void setSubAgentId(SnmpOid var1) {
         super.setSubAgentId(var1);
      }

      public String getSubAgentDescr() {
         return super.getSubAgentDescr();
      }

      public void setSubAgentDescr(String var1) {
         super.setSubAgentDescr(var1);
      }

      public int getConnectionRetryPeriodSecs() {
         return super.getConnectionRetryPeriodSecs();
      }

      public void setConnectionRetryPeriodSecs(int var1) {
         super.setConnectionRetryPeriodSecs(var1);
      }

      public int getSubAgentTimeoutSecs() {
         return super.getSubAgentTimeoutSecs();
      }

      public void setSubAgentTimeoutSecs(int var1) {
         super.setSubAgentTimeoutSecs(var1);
      }

      public InetAddress getMasterAgentAddr() {
         return super.getMasterAgentAddr();
      }

      public int getMasterAgentPort() {
         return super.getMasterAgentPort();
      }

      public boolean isNetworkByteOrder() {
         return super.isNetworkByteOrder();
      }

      public void isNetworkByteOrder(boolean var1) {
         super.isNetworkByteOrder(var1);
      }
   }

   public interface Listener {
      void sessionUp(SubAgentX var1);

      void sessionDown(SubAgentX var1);

      void subtreeRegistered(SubAgentX var1, Subtree var2);

      void subtreeUnregistered(SubAgentX var1, Subtree var2);

      void handleException(AgentXException var1);
   }
}
