import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPConstraints;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;
import netscape.ldap.util.GetOpt;

public class LDAPDelete extends LDAPTool {
   private static String[] m_delete_dn = null;
   private static boolean m_cont = false;
   private static BufferedReader m_reader = null;

   public static void main(String[] var0) {
      if (var0.length < 1) {
         doUsage();
         System.exit(1);
      }

      extractParameters(var0);
      if (!m_justShow) {
         try {
            m_client = new LDAPConnection();
            m_client.connect(m_ldaphost, m_ldapport);
         } catch (Exception var4) {
            System.err.println("Error: client connection failed!");
            System.exit(1);
         }

         try {
            m_client.authenticate(m_version, m_binddn, m_passwd);
         } catch (Exception var3) {
            System.err.println(var3.toString());
            System.exit(1);
         }

         dodelete();

         try {
            m_client.disconnect();
         } catch (Exception var2) {
            System.err.println(var2.toString());
         }
      } else {
         dodelete((LDAPConstraints)null);
      }

      System.exit(0);
   }

   private static void doUsage() {
      System.err.println("usage: LDAPDelete [options] dn");
      System.err.println("options");
      System.err.println("  -h host       LDAP server name or IP address");
      System.err.println("  -p port       LDAP server TCP port number");
      System.err.println("  -V version    LDAP protocol version number (default is 3)");
      System.err.println("  -D binddn     bind dn");
      System.err.println("  -w password   bind passwd (for simple authentication)");
      System.err.println("  -d level      set LDAP debugging level to 'level'");
      System.err.println("  -f file      read DNs to delete from file");
      System.err.println("  -R            do not automatically follow referrals");
      System.err.println("  -O hop limit  maximum number of referral hops to traverse");
      System.err.println("  -H            display usage information");
      System.err.println("  -c            continuous mode (do not stop on errors)");
      System.err.println("  -M            manage references (treat them as regular entries)");
      System.err.println("  -y proxy-DN   DN to use for access control");
   }

   protected static void extractParameters(String[] var0) {
      GetOpt var1 = LDAPTool.extractParameters("Hcf:", var0);
      if (var1.hasOption('H')) {
         doUsage();
         System.exit(0);
      }

      if (var1.hasOption('c')) {
         m_cont = true;
      }

      if (var1.hasOption('f')) {
         String var2 = var1.getOptionParam('f');
         if (var2 == null) {
            doUsage();
            System.exit(0);
         }

         try {
            FileInputStream var3 = new FileInputStream(var2);
            DataInputStream var4 = new DataInputStream(var3);
            m_reader = new BufferedReader(new InputStreamReader(var4));
         } catch (FileNotFoundException var5) {
            System.err.println("File " + var2 + " not found");
         } catch (IOException var6) {
            System.err.println("Error in opening the file " + var2);
         }
      }

      if (m_reader == null) {
         Enumeration var7 = var1.getParameters().elements();
         Vector var8 = new Vector();

         while(var7.hasMoreElements()) {
            var8.addElement(var7.nextElement());
         }

         if (var8.size() <= 0) {
            doUsage();
            System.exit(0);
         }

         m_delete_dn = new String[var8.size()];
         var8.copyInto(m_delete_dn);
      }

   }

   private static void dodelete() {
      boolean var0 = false;
      LDAPConstraints var1 = m_client.getConstraints();
      Vector var2 = new Vector();
      if (m_proxyControl != null) {
         var2.addElement(m_proxyControl);
      }

      if (m_ordinary) {
         var2.addElement(new LDAPControl("2.16.840.1.113730.3.4.2", true, (byte[])null));
      }

      if (var2.size() > 0) {
         LDAPControl[] var3 = new LDAPControl[var2.size()];
         var2.copyInto(var3);
         var1.setServerControls(var3);
      }

      var1.setReferrals(m_referrals);
      if (m_referrals) {
         setDefaultReferralCredentials(var1);
      }

      var1.setHopLimit(m_hopLimit);
      dodelete(var1);
   }

   private static void dodelete(LDAPConstraints var0) {
      try {
         if (m_reader == null) {
            for(int var1 = 0; var1 < m_delete_dn.length; ++var1) {
               if (!deleteEntry(m_delete_dn[var1], var0) && !m_cont) {
                  return;
               }
            }
         } else {
            String var3 = null;

            while((var3 = m_reader.readLine()) != null) {
               if (!deleteEntry(var3, var0) && !m_cont) {
                  return;
               }
            }
         }
      } catch (IOException var2) {
         System.err.println("Error in reading input");
      }

   }

   private static boolean deleteEntry(String var0, LDAPConstraints var1) {
      if (m_verbose) {
         System.err.println("Deleting entry: " + var0);
      }

      if (!m_justShow) {
         try {
            m_client.delete(var0, var1);
         } catch (LDAPException var3) {
            System.err.println("Delete " + var0 + " failed ");
            System.err.println("\t" + var3.errorCodeToString());
            System.err.println("\tmatched " + var3.getMatchedDN() + "\n");
            return false;
         }
      }

      return true;
   }
}
