import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPCompareAttrNames;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPReferralException;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.LDAPSortKey;
import netscape.ldap.LDAPUrl;
import netscape.ldap.controls.LDAPSortControl;
import netscape.ldap.controls.LDAPVirtualListControl;
import netscape.ldap.controls.LDAPVirtualListResponse;
import netscape.ldap.util.DSMLWriter;
import netscape.ldap.util.GetOpt;
import netscape.ldap.util.LDAPWriter;
import netscape.ldap.util.LDIFWriter;
import netscape.ldap.util.MimeBase64Encoder;

public class LDAPSearch extends LDAPTool {
   private static final String DSML_INTRO = "<dsml:dsml xmlns:dsml=\"http://www.dsml.org/DSML\">";
   private static final String DSML_END = "</dsml:dsml>";
   private static final String DSML_RESULTS_INTRO = " <dsml:directory-entries>";
   private static final String DSML_RESULTS_END = " </dsml:directory-entries>";
   private static boolean m_attrsonly = false;
   private static int m_deref = 0;
   private static int m_scope = 2;
   private static int m_sizelimit = 0;
   private static int m_timelimit = 0;
   private static int verbose = 0;
   private static String[] m_attrs = null;
   private static String m_base = "o=ace industry,c=us";
   private static String m_filter = null;
   private static String m_sep = ":";
   private static Vector m_sort = new Vector();
   private static boolean m_sortOnServer = false;
   private static boolean m_tempFiles = false;
   private static int m_beforeCount = 0;
   private static int m_afterCount = 0;
   private static int m_index = 0;
   private static int m_count = 0;
   private static int m_vlvTokens = 0;
   private static String m_searchVal = null;
   private static boolean m_foldLine = true;
   private static final int MAX_LINE = 77;
   private static PrintWriter m_pw;
   private static MimeBase64Encoder m_encoder;
   private static boolean m_printDSML;

   public static void main(String[] var0) {
      extractParameters(var0);
      if (!m_justShow) {
         try {
            m_client = new LDAPConnection();
            m_client.connect(m_ldaphost, m_ldapport);
         } catch (Exception var4) {
            System.err.println("Error: client connection failed!");
            System.exit(0);
         }

         try {
            m_client.authenticate(m_version, m_binddn, m_passwd);
         } catch (Exception var3) {
            System.err.println(var3.toString());
            System.exit(0);
         }

         dosearch();
         m_pw.flush();
         m_pw.close();

         try {
            m_client.disconnect();
         } catch (Exception var2) {
            System.err.println(var2.toString());
         }
      }

      System.exit(0);
   }

   private static void doUsage() {
      System.err.println("usage: LDAPSearch -b basedn [options] filter [attributes...]");
      System.err.println("options");
      System.err.println("  -h host       LDAP server name or IP address");
      System.err.println("  -p port       LDAP server TCP port number");
      System.err.println("  -V version    LDAP protocol version number (default is 3)");
      System.err.println("  -D binddn     bind dn");
      System.err.println("  -w password   bind passwd (for simple authentication)");
      System.err.println("  -v            run in verbose mode");
      System.err.println("  -n            show what would be done but don't actually do it");
      System.err.println("  -d level      set LDAP debugging level to 'level'");
      System.err.println("  -R            do not automatically follow referrals");
      System.err.println("  -O hop limit  maximum number of referral hops to traverse");
      System.err.println("  -H            display usage information");
      System.err.println("  -t            write values to files");
      System.err.println("  -A            retrieve attribute names only");
      System.err.println("  -F sep        print 'sep' instead of '=' between attribute names and values");
      System.err.println("  -S attr       sort the results by attribute 'attr'");
      System.err.println("  -s scope      one of base, one, or sub (search scope)");
      System.err.println("  -a deref      one of never, always, search, or find (alias dereferencing)");
      System.err.println("  -l timelimit  time limit (in seconds) for search");
      System.err.println("  -T            do not fold (wrap) long lines (default is to fold)");
      System.err.println("  -x            perform sorting on server");
      System.err.println("  -M            manage references (treat them as regular entries)");
      System.err.println("  -z sizelimit  size limit (in entries) for search");
      System.err.println("  -G before:after:index:count | before:after:value where 'before' and 'after' are the number of entries surrounding 'index'. 'count' is the content count, 'value' is the search value.");
      System.err.println("  -y proxy-DN   DN to use for access control");
      System.err.println("  -X            output DSML instead of LDIF");
   }

   protected static void extractParameters(String[] var0) {
      String var1 = "HATtxvnXa:b:F:l:s:S:z:G:";
      GetOpt var2 = LDAPTool.extractParameters(var1, var0);
      if (var2.hasOption('H')) {
         doUsage();
         System.exit(0);
      }

      if (var2.hasOption('A')) {
         m_attrsonly = true;
      }

      if (var2.hasOption('x')) {
         m_sortOnServer = true;
      }

      if (var2.hasOption('t')) {
         m_tempFiles = true;
      }

      if (var2.hasOption('F')) {
         m_sep = var2.getOptionParam('F');
      }

      String var3;
      if (var2.hasOption('a')) {
         var3 = var2.getOptionParam('a');
         if (var3.equalsIgnoreCase("never")) {
            m_deref = 0;
         } else if (var3.equalsIgnoreCase("search")) {
            m_deref = 1;
         } else if (var3.equalsIgnoreCase("find")) {
            m_deref = 2;
         } else if (var3.equalsIgnoreCase("always")) {
            m_deref = 3;
         } else {
            System.err.println("Error: alias deref option should be never, search, find, or always.");
         }
      }

      if (var2.hasOption('b')) {
         m_base = var2.getOptionParam('b');
      }

      if (var2.hasOption('S')) {
         m_sort.addElement(var2.getOptionParam('S'));
      }

      if (var2.hasOption('l')) {
         try {
            m_timelimit = Integer.parseInt(var2.getOptionParam('l'));
         } catch (NumberFormatException var8) {
            m_timelimit = 0;
         }
      }

      if (var2.hasOption('s')) {
         var3 = var2.getOptionParam('s');
         if (var3.equalsIgnoreCase("base")) {
            m_scope = 0;
         } else if (var3.equalsIgnoreCase("one")) {
            m_scope = 1;
         } else if (var3.equalsIgnoreCase("sub")) {
            m_scope = 2;
         } else {
            System.err.println("Error: scope should be base, one or sub.");
         }
      }

      if (var2.hasOption('z')) {
         try {
            m_sizelimit = Integer.parseInt(var2.getOptionParam('z'));
         } catch (NumberFormatException var7) {
            m_sizelimit = 0;
         }
      }

      if (var2.hasOption('T')) {
         m_foldLine = false;
      }

      if (var2.hasOption('X')) {
         m_printDSML = true;
      }

      parseVlv(var2);
      Enumeration var9 = var2.getParameters().elements();
      Vector var4 = new Vector();

      while(var9.hasMoreElements()) {
         var4.addElement(var9.nextElement());
      }

      int var5 = var4.size();
      if (var5 <= 0) {
         System.err.println("Error: must supply filter string!");
         doUsage();
         System.exit(0);
      }

      if (var5 == 1) {
         m_filter = (String)var4.elementAt(0);
         if (m_verbose) {
            System.err.println("filter pattern: " + m_filter);
         }

         m_attrs = null;
         if (m_verbose) {
            System.err.println("returning: ALL");
            System.err.println("filter is: (" + m_filter + ")");
         }
      }

      if (var5 > 1) {
         m_filter = (String)var4.elementAt(0);
         if (m_verbose) {
            System.err.println("filter pattern: " + m_filter);
            System.err.print("returning:");
         }

         m_attrs = new String[var5];

         for(int var6 = 1; var6 < var5; ++var6) {
            m_attrs[var6 - 1] = (String)var4.elementAt(var6);
            if (m_verbose) {
               System.err.print(" " + m_attrs[var6 - 1]);
            }
         }

         if (m_verbose) {
            System.err.println();
            System.err.println("filter is: (" + m_filter + ")");
         }
      }

   }

   private static void parseVlv(GetOpt var0) {
      if (var0.hasOption('G')) {
         String var1 = var0.getOptionParam('G');
         StringTokenizer var2 = new StringTokenizer(var1, ":");
         m_vlvTokens = var2.countTokens();
         if (m_vlvTokens < 3) {
            doUsage();
            System.exit(0);
         }

         try {
            m_beforeCount = Integer.parseInt((String)var2.nextElement());
         } catch (NumberFormatException var7) {
            m_beforeCount = 0;
         }

         try {
            m_afterCount = Integer.parseInt((String)var2.nextElement());
         } catch (NumberFormatException var6) {
            m_afterCount = 0;
         }

         if (m_vlvTokens == 3) {
            m_searchVal = (String)var2.nextElement();
         } else if (m_vlvTokens > 3) {
            try {
               m_index = Integer.parseInt((String)var2.nextElement());
            } catch (NumberFormatException var5) {
               m_index = 0;
            }

            try {
               m_count = Integer.parseInt((String)var2.nextElement());
            } catch (NumberFormatException var4) {
               m_count = 0;
            }
         }
      }

   }

   private static void dosearch() {
      LDAPControl[] var0 = null;

      try {
         Vector var1 = new Vector();
         LDAPSortControl var2 = null;
         if (m_sortOnServer && m_sort.size() > 0) {
            LDAPSortKey[] var3 = new LDAPSortKey[m_sort.size()];

            for(int var4 = 0; var4 < var3.length; ++var4) {
               var3[var4] = new LDAPSortKey((String)m_sort.elementAt(var4));
            }

            var2 = new LDAPSortControl(var3, true);
            var1.addElement(var2);
         }

         if (var2 == null && m_vlvTokens >= 3) {
            System.err.println("Server-side sorting is required for virtual list option");
            doUsage();
            System.exit(0);
         }

         LDAPVirtualListControl var9 = null;
         if (m_vlvTokens == 3) {
            var9 = new LDAPVirtualListControl(m_searchVal, m_beforeCount, m_afterCount);
         } else if (m_vlvTokens > 3) {
            var9 = new LDAPVirtualListControl(m_index, m_beforeCount, m_afterCount, m_count);
         }

         if (var9 != null) {
            var1.addElement(var9);
         }

         if (m_proxyControl != null) {
            var1.addElement(m_proxyControl);
         }

         if (m_ordinary) {
            LDAPControl var12 = new LDAPControl("2.16.840.1.113730.3.4.2", true, (byte[])null);
            var1.addElement(var12);
         }

         if (var1.size() > 0) {
            var0 = new LDAPControl[var1.size()];
            var1.copyInto(var0);
         }
      } catch (Exception var6) {
         System.err.println(var6.toString());
         System.exit(0);
      }

      LDAPSearchResults var7 = null;

      try {
         LDAPSearchConstraints var8 = m_client.getSearchConstraints();
         var8.setServerControls(var0);
         var8.setDereference(m_deref);
         var8.setMaxResults(m_sizelimit);
         var8.setServerTimeLimit(m_timelimit);
         var8.setReferrals(m_referrals);
         if (m_referrals) {
            setDefaultReferralCredentials(var8);
         }

         var8.setHopLimit(m_hopLimit);
         var7 = m_client.search(m_base, m_scope, m_filter, m_attrs, m_attrsonly, var8);
      } catch (Exception var5) {
         System.err.println(var5.toString());
         System.exit(1);
      }

      if (m_sort.size() > 0 && !m_sortOnServer) {
         String[] var10 = new String[m_sort.size()];

         for(int var11 = 0; var11 < var10.length; ++var11) {
            var10[var11] = (String)m_sort.elementAt(var11);
         }

         var7.sort(new LDAPCompareAttrNames(var10));
      }

      printResults(var7);
      showControls(m_client.getResponseControls());
   }

   private static void printResults(LDAPSearchResults var0) {
      boolean var1 = false;
      boolean var2 = false;
      Object var3;
      if (m_printDSML) {
         printString("<dsml:dsml xmlns:dsml=\"http://www.dsml.org/DSML\">");
         var3 = new DSMLWriter(m_pw);
      } else {
         var3 = new LDIFWriter(m_pw, m_attrsonly, m_sep, m_foldLine, m_tempFiles);
      }

      try {
         label53:
         while(var0.hasMoreElements()) {
            LDAPEntry var4 = null;

            try {
               var4 = var0.next();
            } catch (LDAPReferralException var8) {
               LDAPUrl[] var6 = var8.getURLs();
               System.err.println("Referral entries: ");
               int var7 = 0;

               while(true) {
                  if (var7 >= var6.length) {
                     continue label53;
                  }

                  System.err.println("\t" + var6[var7].getUrl().toString());
                  ++var7;
               }
            } catch (Exception var9) {
               m_pw.flush();
               System.err.println(var9.toString());
               continue;
            }

            if (isSchemaEntry(var4)) {
               ((LDAPWriter)var3).printSchema(var4);
            } else {
               if (m_printDSML && !var2) {
                  printString(" <dsml:directory-entries>");
                  var2 = true;
               }

               ((LDAPWriter)var3).printEntry(var4);
            }
         }
      } catch (Exception var10) {
         m_pw.flush();
         System.err.println(var10.toString());
         System.exit(0);
      }

      if (m_printDSML) {
         if (var2) {
            printString(" </dsml:directory-entries>");
         }

         printString("</dsml:dsml>");
      }

   }

   protected static void printString(String var0) {
      m_pw.print(var0);
      m_pw.print('\n');
   }

   protected static boolean isSchemaEntry(LDAPEntry var0) {
      LDAPAttribute var1 = var0.getAttribute("objectclass");
      if (var1 != null) {
         String[] var2 = var1.getStringValueArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var2[var3].equalsIgnoreCase("subschema")) {
               return true;
            }
         }
      }

      return false;
   }

   private static void showControls(LDAPControl[] var0) {
      if (var0 != null) {
         LDAPSortControl var1 = null;
         LDAPVirtualListResponse var2 = null;

         int var3;
         for(var3 = 0; var3 < var0.length; ++var3) {
            if (var0[var3] instanceof LDAPSortControl) {
               var1 = (LDAPSortControl)var0[var3];
            } else if (var0[var3] instanceof LDAPVirtualListResponse) {
               var2 = (LDAPVirtualListResponse)var0[var3];
            }
         }

         if (var1 != null) {
            String var5 = var1.getFailedAttribute();
            int var4 = var1.getResultCode();
            if (var4 != 0) {
               System.err.println("Error code: " + var4);
               if (var5 != null) {
                  System.err.println("Offending attribute: " + var5);
               } else {
                  System.err.println("No offending attribute returned");
               }
            } else {
               m_pw.println("Server indicated results sorted OK");
            }
         }

         if (var2 != null) {
            var3 = var2.getResultCode();
            if (var3 == 0) {
               m_pw.println("Server indicated virtual list positioning OK");
               m_pw.println("index " + var2.getFirstPosition() + " content count " + var2.getContentCount());
            } else {
               System.err.println("Virtual List Error: " + LDAPException.errorCodeToString(var3));
            }
         }

      }
   }

   static {
      m_pw = new PrintWriter(System.out);
      m_encoder = new MimeBase64Encoder();
      m_printDSML = false;
   }
}
