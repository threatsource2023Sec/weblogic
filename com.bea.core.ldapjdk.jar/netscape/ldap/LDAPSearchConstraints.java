package netscape.ldap;

public class LDAPSearchConstraints extends LDAPConstraints implements Cloneable {
   public static final int REFERRAL_ERROR_CONTINUE = 0;
   public static final int REFERRAL_ERROR_EXCEPTION = 1;
   private int deref;
   private int maxRes;
   private int batch;
   private int serverTimeLimit;
   private int maxBacklog = 100;
   private int referralErrors = 0;

   public LDAPSearchConstraints() {
      this.deref = 0;
      this.maxRes = 1000;
      this.batch = 1;
      this.serverTimeLimit = 0;
   }

   public LDAPSearchConstraints(int var1, int var2, int var3, boolean var4, int var5, LDAPRebind var6, int var7) {
      super(var1, var4, var6, var7);
      this.deref = var2;
      this.maxRes = var3;
      this.batch = var5;
   }

   public LDAPSearchConstraints(int var1, int var2, int var3, int var4, boolean var5, int var6, LDAPRebind var7, int var8) {
      super(var1, var5, var7, var8);
      this.serverTimeLimit = var2;
      this.deref = var3;
      this.maxRes = var4;
      this.batch = var6;
   }

   public LDAPSearchConstraints(int var1, int var2, int var3, int var4, boolean var5, int var6, LDAPBind var7, int var8) {
      super(var1, var5, var7, var8);
      this.serverTimeLimit = var2;
      this.deref = var3;
      this.maxRes = var4;
      this.batch = var6;
   }

   public int getServerTimeLimit() {
      return this.serverTimeLimit;
   }

   public int getDereference() {
      return this.deref;
   }

   public int getMaxResults() {
      return this.maxRes;
   }

   public int getBatchSize() {
      return this.batch;
   }

   public void setServerTimeLimit(int var1) {
      this.serverTimeLimit = var1;
   }

   public void setDereference(int var1) {
      this.deref = var1;
   }

   public void setMaxResults(int var1) {
      this.maxRes = var1;
   }

   public void setBatchSize(int var1) {
      this.batch = var1;
   }

   /** @deprecated */
   public void setMaxBacklog(int var1) {
      this.maxBacklog = var1;
   }

   /** @deprecated */
   public int getMaxBacklog() {
      return this.maxBacklog;
   }

   public int getReferralErrors() {
      return this.referralErrors;
   }

   public void setReferralErrors(int var1) {
      if (var1 != 0 && var1 != 1) {
         throw new IllegalArgumentException("Invalid error behavior: " + var1);
      } else {
         this.referralErrors = var1;
      }
   }

   public Object clone() {
      LDAPSearchConstraints var1 = (LDAPSearchConstraints)super.clone();
      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LDAPSearchConstraints {");
      var1.append(super.toString() + ' ');
      var1.append("size limit " + this.maxRes + ", ");
      var1.append("server time limit " + this.serverTimeLimit + ", ");
      var1.append("aliases " + this.deref + ", ");
      var1.append("batch size " + this.batch + ", ");
      var1.append("max backlog " + this.maxBacklog + ", ");
      var1.append("referralErrors " + this.referralErrors);
      var1.append('}');
      return var1.toString();
   }
}
