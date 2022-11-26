package netscape.ldap;

import java.io.Serializable;

public class LDAPConstraints implements Cloneable, Serializable {
   static final long serialVersionUID = 6506767263918312029L;
   private int m_hop_limit;
   private LDAPBind m_bind_proc;
   private LDAPRebind m_rebind_proc;
   private boolean referrals;
   private int m_time_limit;
   private LDAPControl[] m_clientControls;
   private LDAPControl[] m_serverControls;

   public LDAPConstraints() {
      this.m_time_limit = 0;
      this.referrals = false;
      this.m_bind_proc = null;
      this.m_rebind_proc = null;
      this.m_hop_limit = 5;
      this.m_clientControls = null;
      this.m_serverControls = null;
   }

   public LDAPConstraints(int var1, boolean var2, LDAPRebind var3, int var4) {
      this.m_time_limit = var1;
      this.referrals = var2;
      this.m_bind_proc = null;
      this.m_rebind_proc = var3;
      this.m_hop_limit = var4;
      this.m_clientControls = null;
      this.m_serverControls = null;
   }

   public LDAPConstraints(int var1, boolean var2, LDAPBind var3, int var4) {
      this.m_time_limit = var1;
      this.referrals = var2;
      this.m_bind_proc = var3;
      this.m_rebind_proc = null;
      this.m_hop_limit = var4;
      this.m_clientControls = null;
      this.m_serverControls = null;
   }

   public int getTimeLimit() {
      return this.m_time_limit;
   }

   public boolean getReferrals() {
      return this.referrals;
   }

   public LDAPBind getBindProc() {
      return this.m_bind_proc;
   }

   public LDAPRebind getRebindProc() {
      return this.m_rebind_proc;
   }

   public int getHopLimit() {
      return this.m_hop_limit;
   }

   public LDAPControl[] getClientControls() {
      return this.m_clientControls;
   }

   public LDAPControl[] getServerControls() {
      return this.m_serverControls;
   }

   public void setTimeLimit(int var1) {
      this.m_time_limit = var1;
   }

   public void setReferrals(boolean var1) {
      this.referrals = var1;
   }

   public void setBindProc(LDAPBind var1) {
      this.m_bind_proc = var1;
      if (var1 != null) {
         this.m_rebind_proc = null;
      }

   }

   public void setRebindProc(LDAPRebind var1) {
      this.m_rebind_proc = var1;
      if (var1 != null) {
         this.m_bind_proc = null;
      }

   }

   public void setHopLimit(int var1) {
      this.m_hop_limit = var1;
   }

   public void setClientControls(LDAPControl var1) {
      this.m_clientControls = new LDAPControl[1];
      this.m_clientControls[0] = var1;
   }

   public void setClientControls(LDAPControl[] var1) {
      this.m_clientControls = var1;
   }

   public void setServerControls(LDAPControl var1) {
      this.m_serverControls = new LDAPControl[1];
      this.m_serverControls[0] = var1;
   }

   public void setServerControls(LDAPControl[] var1) {
      this.m_serverControls = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LDAPConstraints {");
      var1.append("time limit " + this.getTimeLimit() + ", ");
      var1.append("referrals " + this.getReferrals() + ", ");
      var1.append("hop limit " + this.getHopLimit() + ", ");
      var1.append("bind_proc " + this.getBindProc() + ", ");
      var1.append("rebind_proc " + this.getRebindProc());
      LDAPControl[] var2 = this.getClientControls();
      int var3;
      if (var2 != null) {
         var1.append(", client controls ");

         for(var3 = 0; var3 < var2.length; ++var3) {
            var1.append(var2[var3].toString());
            if (var3 < var2.length - 1) {
               var1.append(" ");
            }
         }
      }

      var2 = this.getServerControls();
      if (var2 != null) {
         var1.append(", server controls ");

         for(var3 = 0; var3 < var2.length; ++var3) {
            var1.append(var2[var3].toString());
            if (var3 < var2.length - 1) {
               var1.append(" ");
            }
         }
      }

      var1.append('}');
      return var1.toString();
   }

   public Object clone() {
      try {
         LDAPConstraints var1 = (LDAPConstraints)super.clone();
         int var2;
         if (this.m_clientControls != null && this.m_clientControls.length > 0) {
            var1.m_clientControls = new LDAPControl[this.m_clientControls.length];

            for(var2 = 0; var2 < this.m_clientControls.length; ++var2) {
               var1.m_clientControls[var2] = (LDAPControl)this.m_clientControls[var2].clone();
            }
         }

         if (this.m_serverControls != null && this.m_serverControls.length > 0) {
            var1.m_serverControls = new LDAPControl[this.m_serverControls.length];

            for(var2 = 0; var2 < this.m_serverControls.length; ++var2) {
               var1.m_serverControls[var2] = (LDAPControl)this.m_serverControls[var2].clone();
            }
         }

         return var1;
      } catch (CloneNotSupportedException var3) {
         return null;
      }
   }
}
