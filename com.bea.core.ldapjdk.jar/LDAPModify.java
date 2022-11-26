import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPConstraints;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPModification;
import netscape.ldap.LDAPModificationSet;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.util.GetOpt;
import netscape.ldap.util.LDIF;
import netscape.ldap.util.LDIFAddContent;
import netscape.ldap.util.LDIFAttributeContent;
import netscape.ldap.util.LDIFContent;
import netscape.ldap.util.LDIFDeleteContent;
import netscape.ldap.util.LDIFModDNContent;
import netscape.ldap.util.LDIFModifyContent;
import netscape.ldap.util.LDIFRecord;

public class LDAPModify extends LDAPTool {
   private static boolean m_continuous = false;
   private static boolean m_force = false;
   private static boolean m_add = false;
   private static boolean m_binaryFiles = false;
   private static String m_rejectsFile = null;
   private static LDIF m_ldif = null;
   private static String m_file = null;

   public static void main(String[] var0) {
      extractParameters(var0);

      try {
         if (!m_justShow) {
            m_client = new LDAPConnection();
            m_client.connect(m_ldaphost, m_ldapport);
         }
      } catch (Exception var5) {
         System.err.println("Error: client connection failed!");
         System.exit(0);
      }

      try {
         if (!m_justShow) {
            m_client.authenticate(m_version, m_binddn, m_passwd);
         }
      } catch (Exception var4) {
         System.err.println(var4.toString());
         System.exit(0);
      }

      try {
         if (m_file != null) {
            m_ldif = new LDIF(m_file);
         } else {
            m_ldif = new LDIF();
         }
      } catch (Exception var6) {
         if (m_file == null) {
            m_file = "stdin";
         }

         System.err.println("Failed to read LDIF file " + m_file + ", " + var6.toString());
         System.exit(0);
      }

      try {
         doModify();
      } catch (Exception var3) {
         System.err.println(var3.toString());
      }

      try {
         if (!m_justShow) {
            m_client.disconnect();
         }
      } catch (Exception var2) {
         System.err.println(var2.toString());
      }

      System.exit(0);
   }

   private static void doUsage() {
      System.err.println("usage: LDAPModify [options]");
      System.err.println("options");
      System.err.println("  -h host       LDAP server name or IP address");
      System.err.println("  -p port       LDAP server TCP port number");
      System.err.println("  -V version    LDAP protocol version number (default is 3)");
      System.err.println("  -D binddn     bind dn");
      System.err.println("  -w password   bind passwd (for simple authentication)");
      System.err.println("  -d level      set LDAP debugging level to 'level'");
      System.err.println("  -R            do not automatically follow referrals");
      System.err.println("  -O hop limit  maximum number of referral hops to traverse");
      System.err.println("  -H            display usage information");
      System.err.println("  -c            continuous mode (do not stop on errors)");
      System.err.println("  -M            manage references (treat them as regular entries)");
      System.err.println("  -f file       read modifications from file instead of standard input");
      System.err.println("  -a            add entries");
      System.err.println("  -b            read values that start with / from files (for bin attrs)");
      System.err.println("  -n            show what would be done but don't actually do it");
      System.err.println("  -v            run in verbose mode");
      System.err.println("  -r            replace existing values by default");
      System.err.println("  -e rejectfile save rejected entries in 'rejfile'");
      System.err.println("  -y proxy-DN   DN to use for access control");
   }

   protected static void extractParameters(String[] var0) {
      String var1 = "abcHFre:f:";
      GetOpt var2 = LDAPTool.extractParameters(var1, var0);
      if (var2.hasOption('H')) {
         doUsage();
         System.exit(0);
      }

      if (var2.hasOption('F')) {
         m_force = true;
      }

      if (var2.hasOption('a')) {
         m_add = true;
      }

      if (var2.hasOption('c')) {
         m_continuous = true;
      }

      if (var2.hasOption('r')) {
         m_add = false;
      }

      if (var2.hasOption('b')) {
         m_binaryFiles = true;
      }

      if (var2.hasOption('f')) {
         m_file = var2.getOptionParam('f');
      }

      if (var2.hasOption('e')) {
         m_rejectsFile = var2.getOptionParam('e');
      }

   }

   private static void doModify() throws IOException {
      PrintWriter var0 = null;
      LDAPConstraints var1 = null;
      if (!m_justShow) {
         var1 = m_client.getConstraints();
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
      }

      for(LDIFRecord var17 = m_ldif.nextRecord(); var17 != null; var17 = m_ldif.nextRecord()) {
         Object var18 = var1;
         if (!m_justShow) {
            LDAPControl[] var4 = var17.getControls();
            if (var4 != null) {
               LDAPControl[] var5 = var4;
               LDAPControl[] var6 = var1.getServerControls();
               if (var6 != null) {
                  var5 = new LDAPControl[var6.length + var4.length];

                  int var7;
                  for(var7 = 0; var7 < var6.length; ++var7) {
                     var5[var7] = var6[var7];
                  }

                  for(var7 = 0; var7 < var4.length; ++var7) {
                     var5[var7 + var6.length] = var4[var7];
                  }
               }

               var18 = (LDAPSearchConstraints)var1.clone();
               ((LDAPConstraints)var18).setServerControls(var5);
            }
         }

         LDIFContent var19 = var17.getContent();
         LDAPModification[] var20 = null;
         LDAPAttribute[] var21 = null;
         boolean var22 = false;
         boolean var8 = false;
         LDAPEntry var9 = null;
         int var11;
         if (var19 instanceof LDIFModifyContent) {
            var20 = ((LDIFModifyContent)var19).getModifications();
         } else if (var19 instanceof LDIFAddContent) {
            var21 = ((LDIFAddContent)var19).getAttributes();
         } else if (var19 instanceof LDIFAttributeContent) {
            if (m_add) {
               var21 = ((LDIFAttributeContent)var19).getAttributes();
            } else {
               LDAPAttribute[] var10 = ((LDIFAttributeContent)var19).getAttributes();
               var20 = new LDAPModification[var10.length];

               for(var11 = 0; var11 < var10.length; ++var11) {
                  var20[var11] = new LDAPModification(2, var10[var11]);
               }
            }
         } else if (var19 instanceof LDIFDeleteContent) {
            var22 = true;
         } else if (var19 instanceof LDIFModDNContent) {
            var8 = true;
         }

         if (var21 != null) {
            LDAPAttributeSet var23 = new LDAPAttributeSet();

            for(var11 = 0; var11 < var21.length; ++var11) {
               var23.add(var21[var11]);
            }

            var9 = new LDAPEntry(var17.getDN(), var23);
         }

         boolean var24 = false;
         int var12;
         if (m_binaryFiles) {
            LDAPAttribute var13;
            LDAPAttribute var14;
            if (var20 != null) {
               for(var11 = 0; var11 < var20.length; ++var11) {
                  LDAPModification var26 = var20[var11];
                  var13 = var20[var11].getAttribute();
                  var14 = checkFiles(var13);
                  if (var14 == null) {
                     var24 = true;
                  } else {
                     var20[var11] = new LDAPModification(var26.getOp(), var14);
                  }
               }
            } else if (var21 != null) {
               LDAPAttributeSet var25 = new LDAPAttributeSet();

               for(var12 = 0; var12 < var21.length; ++var12) {
                  var13 = var21[var12];
                  var14 = checkFiles(var13);
                  if (var14 == null) {
                     var24 = true;
                     break;
                  }

                  var25.add(var14);
               }

               if (!var24) {
                  var9 = new LDAPEntry(var17.getDN(), var25);
               }
            }
         }

         var11 = 0;
         if (!var24) {
            try {
               int var30;
               if (var20 != null) {
                  LDAPModificationSet var29 = new LDAPModificationSet();
                  System.out.println("\nmodifying entry " + var17.getDN());

                  for(var30 = 0; var30 < var20.length; ++var30) {
                     if (m_verbose) {
                        System.out.println("\t" + var20[var30]);
                     }

                     var29.add(var20[var30].getOp(), var20[var30].getAttribute());
                  }

                  if (!m_justShow) {
                     m_client.modify(var17.getDN(), (LDAPModificationSet)var29, (LDAPConstraints)var18);
                  }
               } else if (var9 == null) {
                  if (var22) {
                     System.out.println("\ndeleting entry " + var17.getDN());
                     if (!m_justShow) {
                        m_client.delete(var17.getDN(), (LDAPConstraints)var18);
                     }
                  } else if (var8) {
                     System.out.println("\nmodifying RDN of entry " + var17.getDN() + " and/or moving it beneath a new parent");
                     if (m_verbose) {
                        System.out.println("\t" + var19.toString());
                     }

                     if (!m_justShow) {
                        LDIFModDNContent var28 = (LDIFModDNContent)var19;
                        m_client.rename(var17.getDN(), var28.getRDN(), var28.getNewParent(), var28.getDeleteOldRDN(), (LDAPConstraints)var18);
                        System.out.println("rename completed");
                     }
                  }
               } else {
                  System.out.println("\nadding new entry " + var9.getDN());
                  if (m_verbose) {
                     LDAPAttributeSet var27 = var9.getAttributeSet();

                     for(var30 = 0; var30 < var27.size(); ++var30) {
                        System.out.println("\t" + var27.elementAt(var30));
                     }
                  }

                  if (!m_justShow) {
                     m_client.add(var9, (LDAPConstraints)var18);
                  }
               }
            } catch (LDAPException var16) {
               System.err.println(var17.getDN() + ": " + var16.errorCodeToString());
               if (var16.getLDAPErrorMessage() != null) {
                  System.err.println("additional info: " + var16.getLDAPErrorMessage());
               }

               if (!m_continuous) {
                  System.exit(1);
               }

               var24 = true;
               var11 = var16.getLDAPResultCode();
            }
         }

         if (var24 && m_rejectsFile != null) {
            try {
               if (var0 == null) {
                  var0 = new PrintWriter(new FileOutputStream(m_rejectsFile));
               }
            } catch (Exception var15) {
            }

            if (var0 != null) {
               var0.println("dn: " + var17.getDN() + " # Error: " + var11);
               if (var20 != null) {
                  for(var12 = 0; var12 < var20.length; ++var12) {
                     var0.println(var20[var12].toString());
                  }
               } else if (var9 != null) {
                  var0.println("Add " + var9.toString());
               } else if (var22) {
                  var0.println("Delete " + var17.getDN());
               } else if (var8) {
                  var0.println("ModDN " + ((LDIFModDNContent)var19).toString());
               }

               var0.flush();
            }
         }
      }

      System.exit(0);
   }

   private static LDAPAttribute checkFiles(LDAPAttribute var0) {
      LDAPAttribute var1 = new LDAPAttribute(var0.getName());
      Enumeration var2 = var0.getStringValues();
      if (var2 == null) {
         System.err.println("Failed to do string conversion for " + var0.getName());
         return var1;
      } else {
         while(true) {
            while(var2.hasMoreElements()) {
               String var3 = (String)var2.nextElement();
               if (var3 != null && var3.length() > 1) {
                  try {
                     File var4 = new File(var3);
                     FileInputStream var5 = new FileInputStream(var4);
                     byte[] var6 = new byte[(int)var4.length()];
                     var5.read(var6, 0, (int)var4.length());
                     var1.addValue(var6);
                  } catch (FileNotFoundException var7) {
                     var1.addValue(var3);
                  } catch (IOException var8) {
                     System.err.println("Unable to read value from file " + var3);
                     if (!m_continuous) {
                        System.exit(1);
                     }

                     var1 = null;
                  }
               } else {
                  var1.addValue(var3);
               }
            }

            return var1;
         }
      }
   }
}
