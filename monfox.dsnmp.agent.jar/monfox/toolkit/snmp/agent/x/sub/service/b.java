package monfox.toolkit.snmp.agent.x.sub.service;

class b implements SubAgentXApi.StatusListener {
   protected final SubAgentXApi.StatusListener a;
   protected final SubAgentXApi.StatusListener b;
   private static final String c = "$Id: SubAgentXApiStatusMulticaster.java,v 1.2 2006/10/28 12:02:23 sking Exp $";

   protected b(SubAgentXApi.StatusListener var1, SubAgentXApi.StatusListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void connectionUp(SubAgentXApi var1) {
      this.a.connectionUp(var1);
      this.b.connectionUp(var1);
   }

   public void connectionDown(SubAgentXApi var1) {
      this.a.connectionDown(var1);
      this.b.connectionDown(var1);
   }

   public void sessionClosed(SubAgentXApi var1, SubAgentXApi.Session var2) {
      this.a.sessionClosed(var1, var2);
      this.b.sessionClosed(var1, var2);
   }

   public static SubAgentXApi.StatusListener add(SubAgentXApi.StatusListener var0, SubAgentXApi.StatusListener var1) {
      if (contains(var0, var1)) {
         return var0;
      } else if (var0 == null) {
         return var1;
      } else {
         return (SubAgentXApi.StatusListener)(var1 == null ? var0 : new b(var0, var1));
      }
   }

   public static SubAgentXApi.StatusListener remove(SubAgentXApi.StatusListener var0, SubAgentXApi.StatusListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof b) ? var0 : ((b)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SubAgentXApi.StatusListener remove(SubAgentXApi.StatusListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SubAgentXApi.StatusListener var2 = remove(this.a, var1);
         SubAgentXApi.StatusListener var3 = remove(this.b, var1);
         return (SubAgentXApi.StatusListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SubAgentXApi.StatusListener var0, SubAgentXApi.StatusListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof b) ? false : ((b)var0).contains(var1);
      }
   }

   public boolean contains(SubAgentXApi.StatusListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
