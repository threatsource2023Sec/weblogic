package netscape.ldap;

import java.util.StringTokenizer;
import java.util.Vector;

public class LDAPReferralException extends LDAPException {
   static final long serialVersionUID = 1771536577344289897L;
   private String[] m_referrals = null;

   public LDAPReferralException() {
   }

   public LDAPReferralException(String var1) {
      super(var1);
   }

   public LDAPReferralException(String var1, int var2, String var3) {
      super(var1, var2, var3);
   }

   public LDAPReferralException(String var1, int var2, String[] var3) {
      super(var1, var2, (String)null);
      this.m_referrals = var3;
   }

   public LDAPUrl[] getURLs() {
      return this.getLDAPErrorMessage() == null ? this.constructsURL(this.m_referrals) : this.constructsURL(this.extractReferrals(this.getLDAPErrorMessage()));
   }

   private LDAPUrl[] constructsURL(String[] var1) {
      if (var1 == null) {
         return null;
      } else {
         LDAPUrl[] var2 = new LDAPUrl[var1.length];
         if (var2 == null) {
            return null;
         } else {
            for(int var3 = 0; var3 < var1.length; ++var3) {
               try {
                  var2[var3] = new LDAPUrl(var1[var3]);
               } catch (Exception var5) {
                  return null;
               }
            }

            return var2;
         }
      }
   }

   private String[] extractReferrals(String var1) {
      if (var1 == null) {
         return null;
      } else {
         StringTokenizer var2 = new StringTokenizer(var1, "\n");
         Vector var3 = new Vector();
         boolean var4 = false;

         while(var2.hasMoreTokens()) {
            String var5 = var2.nextToken();
            if (var4) {
               var3.addElement(var5);
            } else if (var5.startsWith("Referral:")) {
               var4 = true;
            }
         }

         if (var3.size() == 0) {
            return null;
         } else {
            String[] var7 = new String[var3.size()];

            for(int var6 = 0; var6 < var3.size(); ++var6) {
               var7[var6] = (String)var3.elementAt(var6);
            }

            return var7;
         }
      }
   }

   public String toString() {
      String var1 = super.toString();

      for(int var2 = 0; var2 < this.m_referrals.length; ++var2) {
         var1 = var1 + "\n" + this.m_referrals[var2];
      }

      return var1;
   }
}
