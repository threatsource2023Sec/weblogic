import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPConstraints;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPRebind;
import netscape.ldap.LDAPRebindAuth;
import netscape.ldap.controls.LDAPProxiedAuthControl;
import netscape.ldap.util.GetOpt;

class LDAPTool {
   protected static int m_ldapport = 389;
   protected static String m_binddn = null;
   protected static String m_ldaphost = "localhost";
   protected static String m_passwd = null;
   protected static int m_version = 3;
   protected static int m_debugLevel = 0;
   protected static int m_hopLimit = 10;
   protected static boolean m_referrals = true;
   protected static LDAPConnection m_client = null;
   protected static boolean m_justShow = false;
   protected static boolean m_verbose = false;
   protected static boolean m_ordinary = false;
   protected static LDAPControl m_proxyControl = null;

   protected static GetOpt extractParameters(String var0, String[] var1) {
      GetOpt var2 = new GetOpt("vnRMD:h:O:p:w:d:V:y:" + var0, var1);
      if (var2.hasOption('n')) {
         m_justShow = true;
      }

      if (var2.hasOption('v')) {
         m_verbose = true;
      }

      if (var2.hasOption('R')) {
         m_referrals = false;
      }

      if (var2.hasOption('D')) {
         m_binddn = var2.getOptionParam('D');
      }

      if (var2.hasOption('h')) {
         m_ldaphost = var2.getOptionParam('h');
      }

      if (var2.hasOption('p')) {
         try {
            m_ldapport = Integer.parseInt(var2.getOptionParam('p'));
         } catch (NumberFormatException var7) {
            m_ldapport = 389;
         }
      }

      if (var2.hasOption('O')) {
         try {
            m_hopLimit = Integer.parseInt(var2.getOptionParam('O'));
         } catch (NumberFormatException var6) {
            m_hopLimit = 10;
         }
      }

      if (var2.hasOption('d')) {
         try {
            m_debugLevel = Integer.parseInt(var2.getOptionParam('d'));
         } catch (NumberFormatException var5) {
            m_debugLevel = 0;
         }
      }

      if (var2.hasOption('V')) {
         try {
            m_version = Integer.parseInt(var2.getOptionParam('V'));
         } catch (NumberFormatException var4) {
            m_version = 3;
         }
      }

      if (var2.hasOption('w')) {
         m_passwd = var2.getOptionParam('w');
      }

      if (var2.hasOption('y')) {
         m_proxyControl = new LDAPProxiedAuthControl(var2.getOptionParam('y'), true);
      }

      if (var2.hasOption('M')) {
         m_ordinary = true;
      }

      return var2;
   }

   protected static void setDefaultReferralCredentials(LDAPConstraints var0) {
      LDAPRebind var1 = new LDAPRebind() {
         public LDAPRebindAuth getRebindAuthentication(String var1, int var2) {
            return new LDAPRebindAuth(LDAPTool.m_client.getAuthenticationDN(), LDAPTool.m_client.getAuthenticationPassword());
         }
      };
      var0.setReferrals(true);
      var0.setRebindProc(var1);
   }
}
