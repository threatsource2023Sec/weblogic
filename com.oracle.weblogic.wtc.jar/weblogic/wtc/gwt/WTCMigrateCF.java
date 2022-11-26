package weblogic.wtc.gwt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.jndi.Environment;
import weblogic.management.DistributedManagementException;
import weblogic.management.MBeanCreationException;
import weblogic.management.MBeanHome;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WTCExportMBean;
import weblogic.management.configuration.WTCImportMBean;
import weblogic.management.configuration.WTCLocalTuxDomMBean;
import weblogic.management.configuration.WTCPasswordMBean;
import weblogic.management.configuration.WTCRemoteTuxDomMBean;
import weblogic.management.configuration.WTCResourcesMBean;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.configuration.WTCtBridgeGlobalMBean;
import weblogic.management.configuration.WTCtBridgeRedirectMBean;

public final class WTCMigrateCF {
   private int ltdcnt = 0;
   private File c_fname = null;
   private HashMap accessMap = new HashMap();
   private HashMap accessLMap = new HashMap();
   private HashMap accessRMap = new HashMap();
   private HashMap accessIdMap = new HashMap();
   private String currSection = null;
   private MBeanHome configHome = null;
   private String serverName = null;
   private String domainName = null;
   private ServerMBean srvrmb = null;
   private String wtcsmbNm = null;
   private WTCServerMBean wtcsmb;
   private static boolean DebugOn = false;
   private static boolean DeployOn = false;
   private static String[] myFldTbls16;
   private static String[] myFldTbls32;
   private static String[] myViewTbls16;
   private static String[] myViewTbls32;
   private static final String localDomainSection = "T_DM_LOCAL_TDOMAIN";
   private static final String remoteDomainSection = "T_DM_REMOTE_TDOMAIN";
   private static final String exportSection = "T_DM_EXPORT";
   private static final String importSection = "T_DM_IMPORT";
   private static final String passwdSection = "T_DM_PASSWORD";
   private static final String resourceSection = "T_DM_RESOURCES";
   private static final String redirSection = "fromto";
   private static final String tbglobalSection = "tBridge";

   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("Usage: weblogic.wtc.gwt.WTCMigrateCF -url URL -username USERNAME -password PASSWORD -infile CONFIGWTC [-server SERVERNAME] [-domain DOMAIN] [-protocol PROTOCOL] [-deploy]");
      } else {
         WTCMigrateCF c = new WTCMigrateCF();
         if (System.getProperty("weblogic.wtc.migrateDebug") != null) {
            DebugOn = true;
         }

         c.migrate(args);
      }
   }

   WTCMigrateCF() {
   }

   void migrate(String[] cmdArgs) {
      String url = null;
      String username = null;
      String password = null;
      String infile = null;
      String protocol = null;

      for(int i = 0; i < cmdArgs.length; ++i) {
         if (cmdArgs[i].equals("-url")) {
            if (DebugOn) {
               System.out.println("URL: " + cmdArgs[i + 1]);
            }

            url = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-username")) {
            if (DebugOn) {
               System.out.println("USERNAME: " + cmdArgs[i + 1]);
            }

            username = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-password")) {
            if (DebugOn) {
               System.out.println("PASSWORD: " + cmdArgs[i + 1]);
            }

            password = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-infile")) {
            if (DebugOn) {
               System.out.println("INFILE: " + cmdArgs[i + 1]);
            }

            infile = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-server")) {
            if (DebugOn) {
               System.out.println("SERVERNAME: " + cmdArgs[i + 1]);
            }

            this.serverName = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-domain")) {
            if (DebugOn) {
               System.out.println("DOMAINNAME: " + cmdArgs[i + 1]);
            }

            this.domainName = cmdArgs[i + 1];
         } else if (cmdArgs[i].equals("-deploy")) {
            if (DebugOn) {
               System.out.println("DEPLOY is set: ");
            }

            DeployOn = true;
         } else if (cmdArgs[i].equals("-protocol")) {
            if (DebugOn) {
               System.out.println("PROTOCOL: " + cmdArgs[i + 1]);
            }

            protocol = cmdArgs[i + 1];
         }
      }

      if (url != null && username != null && password != null && infile != null) {
         boolean ok = true;

         try {
            Environment env = new Environment();
            if (protocol == null) {
               env.setProviderUrl("t3:" + url);
            } else {
               env.setProviderUrl(protocol + ":" + url);
            }

            env.setSecurityPrincipal(username);
            env.setSecurityCredentials(password);
            Context ctx = env.getInitialContext();
            this.configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
            this.c_fname = new File(infile);
            System.out.println("Migrating WTC config file: " + this.c_fname.getPath());
            int random = (int)(Math.random() * 1000000.0);
            this.wtcsmbNm = "WTCServer-" + random;
            if (this.serverName == null && this.domainName == null) {
               this.serverName = this.configHome.getMBeanServer().getServerName();
               this.domainName = this.configHome.getDomainName();
            } else if (this.serverName == null) {
               this.serverName = this.configHome.getMBeanServer().getServerName();
            } else if (this.domainName == null) {
               this.domainName = this.configHome.getDomainName();
            }

            this.srvrmb = (ServerMBean)this.configHome.getAdminMBean(this.serverName, "Server", this.domainName);
            this.wtcsmb = (WTCServerMBean)this.configHome.createAdminMBean(this.wtcsmbNm, "WTCServer", this.domainName);
            System.out.println("Using domain " + this.domainName + " and server " + this.serverName);
            if (!this.loadFile(this.c_fname)) {
               System.out.println("ERROR: Migration failed for configuration file!");
               return;
            }

            if (DeployOn) {
               try {
                  NamingEnumeration blist = ctx.listBindings("tuxedo.services");
                  if (blist.hasMore()) {
                     DeployOn = false;
                     System.out.println("WARNING: Ignoring deploy option! Only one WTCServerMBean deployment permitted(at a time per server)");
                  }
               } catch (NameNotFoundException var12) {
               }

               if (DeployOn) {
                  this.wtcsmb.addTarget(this.srvrmb);
               }
            }
         } catch (NamingException var13) {
            System.out.println("NamingException ERROR: " + var13.toString());
            ok = false;
         } catch (NullPointerException var14) {
            System.out.println("NullPointerException ERROR: " + var14.toString());
            ok = false;
         } catch (MBeanCreationException var15) {
            System.out.println("MBeanCreationException ERROR: " + var15.toString());
            ok = false;
         } catch (InstanceNotFoundException var16) {
            System.out.println("InstanceNotFoundException ERROR: " + var16.toString());
            ok = false;
         } catch (InvalidAttributeValueException var17) {
            System.out.println("InvalidattributeException ERROR: " + var17.toString());
            ok = false;
         } catch (DistributedManagementException var18) {
            System.out.println("DistributedManagementException ERROR: " + var18.toString());
            ok = false;
         }

         if (ok) {
            System.out.println("The WTC configuration file migration is done!");
            System.out.println("No error found!!!");
         }

      } else {
         System.out.println("Usage: weblogic.wtc.gwt.WTCMigrateCF -url URL -username USERNAME -password PASSWORD -infile BDMCONFIG [-server SERVERNAME] [-domain DOMAIN] [-deploy]");
      }
   }

   private boolean loadFile(File cfg) {
      try {
         Reader r = new FileReader(cfg);
         InputSource in = new InputSource(r);
         DOMParser parser = new DOMParser();
         parser.parse(in);
         r.close();
         r = null;
         in = null;
         Document doc = parser.getDocument();
         parser = null;
         if (doc == null) {
            System.out.println("ERROR: Parser returned null for Config doc.");
            return false;
         } else if (!this.extractInfo(doc)) {
            System.out.println("ERROR: Failed to extract the config attributes.");
            return false;
         } else {
            doc = null;
            return true;
         }
      } catch (FileNotFoundException var6) {
         System.out.println("ERROR: File not found, reason(" + var6.toString() + ")!");
         return false;
      } catch (IOException var7) {
         System.out.println("ERROR: IO Exception, reason(" + var7.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      } catch (SAXException var8) {
         System.out.println("ERROR: SAX Exception, reason(" + var8.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      } catch (NumberFormatException var9) {
         System.out.println("ERROR: Invalid number format(" + var9.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      } catch (Exception var10) {
         System.out.println("ERROR: Exception, reason(" + var10.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      }
   }

   private boolean extractInfo(Document doc) throws Exception {
      Element top = doc.getDocumentElement();
      if (top == null) {
         System.out.println("ERROR: No top element from file " + this.c_fname.getPath());
         return false;
      } else {
         String rootname = top.getTagName();
         if (rootname != null && rootname.equals("WTC_CONFIG")) {
            Element bdmcfg = null;
            Element tbridge = null;

            Node lc;
            Element e;
            String en;
            for(lc = top.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
               if (lc.getNodeType() == 1 && lc instanceof Element) {
                  e = (Element)lc;
                  en = e.getTagName();
                  if (en.equals("BDMCONFIG")) {
                     bdmcfg = e;
                  } else if (en.equals("tBridge")) {
                     tbridge = e;
                  }
               }
            }

            if (bdmcfg == null) {
               System.out.println("ERROR: No BDMCONFIG element in config file " + this.c_fname.getPath());
               return false;
            } else {
               for(lc = bdmcfg.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                  if (lc.getNodeType() == 1 && lc instanceof Element) {
                     e = (Element)lc;
                     en = e.getTagName();
                     if (en.equals("T_DM_LOCAL_TDOMAIN")) {
                        ++this.ltdcnt;
                     }
                  }
               }

               if (this.ltdcnt == 0) {
                  System.out.println("ERROR: Requires at least one local domain defined.");
                  return false;
               } else {
                  boolean ok = true;
                  int rtdi = 0;
                  int ltdi = 0;
                  int pwdi = 0;
                  int expi = 0;
                  int impi = 0;

                  for(lc = bdmcfg.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                     if (lc.getNodeType() == 1 && lc instanceof Element) {
                        e = (Element)lc;
                        en = e.getTagName();
                        if (en.equals("T_DM_LOCAL_TDOMAIN")) {
                           ok = this.setupLocalTD(e, ltdi);
                           if (!ok) {
                              System.out.println("ERROR: Could not complete processing of element " + en);
                              return false;
                           }

                           ++ltdi;
                        }
                     }
                  }

                  for(lc = bdmcfg.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                     if (lc.getNodeType() == 1 && lc instanceof Element) {
                        e = (Element)lc;
                        en = e.getTagName();
                        if (en.equals("T_DM_REMOTE_TDOMAIN")) {
                           ok = this.setupRemoteTD(e, rtdi);
                           if (!ok) {
                              System.out.println("ERROR: Could not complete processing of element " + en);
                              return false;
                           }

                           ++rtdi;
                        }
                     }
                  }

                  for(lc = bdmcfg.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                     if (lc.getNodeType() == 1 && lc instanceof Element) {
                        e = (Element)lc;
                        en = e.getTagName();
                        if (en.equals("T_DM_EXPORT")) {
                           ok = this.setupExport(e, expi);
                           if (ok) {
                              ++expi;
                           }
                        } else if (en.equals("T_DM_IMPORT")) {
                           ok = this.setupImport(e, impi);
                           if (ok) {
                              ++impi;
                           }
                        } else if (en.equals("T_DM_PASSWORD")) {
                           ok = this.setupPasswd(e, pwdi);
                           if (ok) {
                              ++pwdi;
                           }
                        } else if (en.equals("T_DM_RESOURCES")) {
                           ok = this.setupResources(e);
                        }

                        if (!ok) {
                           System.out.println("ERROR: Could not complete processing of element " + en);
                           return false;
                        }
                     }
                  }

                  if (tbridge == null) {
                     return true;
                  } else {
                     ok = this.setupTBGlobal(tbridge);
                     if (!ok) {
                        System.out.println("ERROR: Could not complete tBridge Global processing.");
                        return false;
                     } else {
                        Element redir = null;

                        for(lc = tbridge.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                           if (lc.getNodeType() == 1 && lc instanceof Element) {
                              e = (Element)lc;
                              en = e.getTagName();
                              if (redir == null && en.equals("redirect")) {
                                 int fti = 0;

                                 for(Node llc = e.getFirstChild(); llc != null; llc = llc.getNextSibling()) {
                                    if (llc.getNodeType() == 1 && llc instanceof Element) {
                                       Element se = (Element)llc;
                                       String sen = se.getTagName();
                                       if (sen.equals("fromto")) {
                                          ok = this.setupRedirect(se, fti);
                                          if (!ok) {
                                             System.out.println("ERROR: Could not complete tBridge Redirect processing.");
                                             return false;
                                          }

                                          ++fti;
                                       }
                                    }
                                 }

                                 return true;
                              }
                           }
                        }

                        return true;
                     }
                  }
               }
            }
         } else {
            System.out.println("ERROR: Bad top element name \"" + rootname + "\" from config file " + this.c_fname.getPath());
            return false;
         }
      }
   }

   private boolean setupLocalTD(Element ee, int ltdindex) throws Exception {
      String ltdname = "ltd" + ltdindex;
      System.out.println("Processing LTDMBEAN : " + ltdname + " started.");
      String oSection = this.currSection;
      this.currSection = "T_DM_LOCAL_TDOMAIN";

      try {
         WTCLocalTuxDomMBean ltdmb = (WTCLocalTuxDomMBean)this.configHome.createAdminMBean(ltdname, "WTCLocalTuxDom", this.domainName, this.wtcsmb);
         String ltdap = this.getEAVal(ee, "AccessPoint");
         ltdmb.setAccessPoint(ltdap);
         if (this.accessMap.put(ltdap, ltdmb) != null) {
            System.out.println("ERROR: Duplicated AccessPoint " + ltdap + " found in Local TDomain definitions!");
            this.currSection = oSection;
            throw new Exception("Duplicated LTD AccessPoint");
         }

         this.accessLMap.put(ltdap, ltdmb);
         String ltdapid = this.getSubElemText(ee, "AccessPointId", true);
         if (this.accessIdMap.put(ltdapid, ltdmb) != null) {
            System.out.println("ERROR: Duplicated AccessPointId " + ltdapid + " found in Local TDomain definitions!");
            this.currSection = oSection;
            throw new Exception("Duplicated LTD AccessPointId");
         }

         ltdmb.setAccessPointId(ltdapid);
         ltdmb.setNWAddr(this.getSubElemText(ee, "NWAddr", true));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "Security", false)) != null) {
            ltdmb.setSecurity(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "ConnectionPolicy", false)) != null) {
            ltdmb.setConnectionPolicy(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "Interoperate", false)) != null) {
            ltdmb.setInteroperate(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "RetryInterval", false)) != null) {
            ltdmb.setRetryInterval(Long.parseLong(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "MaxRetries", false)) != null) {
            ltdmb.setMaxRetries(Long.parseLong(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "BlockTime", false)) != null) {
            ltdmb.setBlockTime(Long.parseLong(tmps));
         }

         if ((tmps = this.getSubElemText(ee, "CmpLimit", false)) != null) {
            ltdmb.setCmpLimit(Integer.parseInt(tmps, 10));
         }

         int mineb = -1;
         if ((tmps = this.getSubElemText(ee, "MinEncryptBits", false)) != null) {
            mineb = Integer.parseInt(tmps, 10);
            ltdmb.setMinEncryptBits(tmps);
         }

         int maxeb = -1;
         if ((tmps = this.getSubElemText(ee, "MaxEncryptBits", false)) != null) {
            maxeb = Integer.parseInt(tmps, 10);
            ltdmb.setMaxEncryptBits(tmps);
         }

         if (mineb != -1 && maxeb != -1 && mineb > maxeb) {
            System.out.println("ERROR: MinEncryptBits > MaxEncryptBits  found in Local TDomain definition!");
            this.currSection = oSection;
            throw new Exception("MinEncryptBits > MaxEncryptBits");
         }
      } catch (MBeanCreationException var11) {
         System.out.println("MBeanCreationException ERROR: " + var11.toString());
         this.currSection = oSection;
         throw new Exception(var11.toString());
      } catch (InvalidAttributeValueException var12) {
         System.out.println("InvalidattributeException ERROR: " + var12.toString());
         this.currSection = oSection;
         throw new Exception(var12.toString());
      } catch (InstanceNotFoundException var13) {
         System.out.println("InstanceNotFoundException ERROR: " + var13.toString());
         this.currSection = oSection;
         throw new Exception(var13.toString());
      } catch (Exception var14) {
         System.out.println("ERROR: Could not create WTCLocalTuxDomMBean , reason(" + var14.toString() + ")!");
         this.currSection = oSection;
         throw var14;
      }

      this.currSection = oSection;
      System.out.println("Processing LTDMBEAN : " + ltdname + " done.");
      return true;
   }

   private boolean setupRemoteTD(Element ee, int rtdindex) throws Exception {
      String rtdname = "rtd" + rtdindex;
      System.out.println("Processing RTDMBEAN : " + rtdname + " started.");
      String oSection = this.currSection;
      this.currSection = "T_DM_REMOTE_TDOMAIN";

      try {
         WTCRemoteTuxDomMBean rtdmb = (WTCRemoteTuxDomMBean)this.configHome.createAdminMBean(rtdname, "WTCRemoteTuxDom", this.domainName, this.wtcsmb);
         String rtdap = this.getEAVal(ee, "AccessPoint");
         rtdmb.setAccessPoint(rtdap);
         if (this.accessMap.put(rtdap, rtdmb) != null) {
            System.out.println("ERROR: Duplicated AccessPoint " + rtdap + " found in Remote TDomain definition!");
            this.currSection = oSection;
            throw new Exception("Duplicated RTD AccessPoint");
         }

         this.accessRMap.put(rtdap, rtdmb);
         String lapnm = this.getSubElemText(ee, "LocalAccessPoint", true);
         if (!this.accessLMap.containsKey(lapnm)) {
            System.out.println("ERROR: Undefined LocalAccessPoint " + lapnm + " found in Remote TDomain definition!");
            this.currSection = oSection;
            throw new Exception("Undefined RTD LocalAccessPoint");
         }

         rtdmb.setLocalAccessPoint(lapnm);
         String rtdapid = this.getSubElemText(ee, "AccessPointId", true);
         StringBuffer compositeKeySB = (new StringBuffer(rtdapid)).append(lapnm);
         String compositeKey = compositeKeySB.toString();
         if (this.accessIdMap.put(compositeKey, rtdmb) != null) {
            System.out.println("ERROR: Duplicated combination of RemoteAccessPointId(" + rtdapid + ") and Local AccessPoint(" + lapnm + ") found in Remote TDomain definitions!");
            this.currSection = oSection;
            throw new Exception("Duplicated RTD AccessPointId");
         }

         rtdmb.setAccessPointId(rtdapid);
         rtdmb.setNWAddr(this.getSubElemText(ee, "NWAddr", true));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "AclPolicy", false)) != null) {
            rtdmb.setAclPolicy(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "CredentialPolicy", false)) != null) {
            rtdmb.setCredentialPolicy(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "TpUsrFile", false)) != null) {
            rtdmb.setTpUsrFile(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "CmpLimit", false)) != null) {
            rtdmb.setCmpLimit(Integer.parseInt(tmps, 10));
         }

         int mineb = -1;
         if ((tmps = this.getSubElemText(ee, "MinEncryptBits", false)) != null) {
            mineb = Integer.parseInt(tmps, 10);
            rtdmb.setMinEncryptBits(tmps);
         }

         int maxeb = -1;
         if ((tmps = this.getSubElemText(ee, "MaxEncryptBits", false)) != null) {
            maxeb = Integer.parseInt(tmps, 10);
            rtdmb.setMaxEncryptBits(tmps);
         }

         if (mineb != -1 && maxeb != -1 && mineb > maxeb) {
            System.out.println("ERROR: MinEncryptBits > MaxEncryptBits  found in Remote TDomain definition!");
            this.currSection = oSection;
            throw new Exception("MinEncryptBits > MaxEncryptBits");
         }

         if ((tmps = this.getSubElemText(ee, "ConnectionPolicy", false)) != null) {
            rtdmb.setConnectionPolicy(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "RetryInterval", false)) != null) {
            rtdmb.setRetryInterval(Long.parseLong(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "MaxRetries", false)) != null) {
            rtdmb.setMaxRetries(Long.parseLong(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "FederationURL", false)) != null) {
            rtdmb.setFederationURL(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "FederationName", false)) != null) {
            rtdmb.setFederationName(tmps);
         }
      } catch (MBeanCreationException var14) {
         System.out.println("MBeanCreationException ERROR: " + var14.toString());
         this.currSection = oSection;
         throw new Exception(var14.toString());
      } catch (InvalidAttributeValueException var15) {
         System.out.println("InvalidattributeException ERROR: " + var15.toString());
         this.currSection = oSection;
         throw new Exception(var15.toString());
      } catch (InstanceNotFoundException var16) {
         System.out.println("InstanceNotFoundException ERROR: " + var16.toString());
         this.currSection = oSection;
         throw new Exception(var16.toString());
      } catch (Exception var17) {
         System.out.println("ERROR: Could not create WTCRemoteTuxDomMBean , reason(" + var17.toString() + ")!");
         this.currSection = oSection;
         throw var17;
      }

      this.currSection = oSection;
      System.out.println("Processing RTDMBEAN : " + rtdname + " done.");
      return true;
   }

   private boolean setupExport(Element ee, int expindex) throws Exception {
      String expname = "exp" + expindex;
      System.out.println("Processing EXPMBEAN : " + expname + " started.");
      String oSection = this.currSection;
      this.currSection = "T_DM_EXPORT";

      try {
         WTCExportMBean expmb = (WTCExportMBean)this.configHome.createAdminMBean(expname, "WTCExport", this.domainName, this.wtcsmb);
         String lapnm = this.getEAVal(ee, "LocalAccessPoint");
         if (!this.accessLMap.containsKey(lapnm)) {
            System.out.println("ERROR: Undefined LocalAccessPoint " + lapnm + " found in Export definition!");
            this.currSection = oSection;
            throw new Exception("Undefined Export LocalAccessPoint");
         }

         expmb.setLocalAccessPoint(lapnm);
         expmb.setResourceName(this.getEAVal(ee, "ResourceName"));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "RemoteName", false)) != null) {
            expmb.setRemoteName(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "EJBName", false)) != null) {
            expmb.setEJBName(tmps);
         }
      } catch (MBeanCreationException var8) {
         System.out.println("MBeanCreationException ERROR: " + var8.toString());
         this.currSection = oSection;
         throw new Exception(var8.toString());
      } catch (InvalidAttributeValueException var9) {
         System.out.println("InvalidattributeException ERROR: " + var9.toString());
         this.currSection = oSection;
         throw new Exception(var9.toString());
      } catch (InstanceNotFoundException var10) {
         System.out.println("InstanceNotFoundException ERROR: " + var10.toString());
         this.currSection = oSection;
         throw new Exception(var10.toString());
      } catch (Exception var11) {
         System.out.println("ERROR: Could not create WTCExport , reason(" + var11.toString() + ")!");
         this.currSection = oSection;
         throw var11;
      }

      this.currSection = oSection;
      System.out.println("Processing EXPMBEAN : " + expname + " done.");
      return true;
   }

   private boolean setupImport(Element ee, int impindex) throws Exception {
      String impname = "imp" + impindex;
      System.out.println("Processing IMPMBEAN : " + impname + " started.");
      String oSection = this.currSection;
      this.currSection = "T_DM_IMPORT";

      try {
         WTCImportMBean impmb = (WTCImportMBean)this.configHome.createAdminMBean(impname, "WTCImport", this.domainName, this.wtcsmb);
         String lapnm = this.getEAVal(ee, "LocalAccessPoint");
         if (!this.accessLMap.containsKey(lapnm)) {
            System.out.println("ERROR: Undefined LocalAccessPoint " + lapnm + " found in Import definition!");
            this.currSection = oSection;
            throw new Exception("Undefined Import LocalAccessPoint");
         }

         impmb.setLocalAccessPoint(lapnm);
         impmb.setResourceName(this.getEAVal(ee, "ResourceName"));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "RemoteName", false)) != null) {
            impmb.setRemoteName(tmps);
         }

         String raplist = this.getEAVal(ee, "RemoteAccessPointList");
         if (raplist.indexOf(44) == -1) {
            if (!this.accessRMap.containsKey(raplist)) {
               System.out.println("ERROR: Undefined RemoteAccessPoint " + raplist + " in Import RemoteAccessPointList definition!");
               this.currSection = oSection;
               throw new Exception("Undefined Import RemoteAccessPoint");
            }
         } else {
            StringTokenizer st = new StringTokenizer(raplist, ",");

            while(st.hasMoreTokens()) {
               String rapnm = st.nextToken();
               if (!this.accessRMap.containsKey(rapnm)) {
                  System.out.println("ERROR: Undefined RemoteAccessPoint " + rapnm + " in Import RemoteAccessPointList definition!");
                  this.currSection = oSection;
                  throw new Exception("Undefined Import RemoteAccessPoint");
               }
            }
         }

         impmb.setRemoteAccessPointList(raplist);
      } catch (MBeanCreationException var11) {
         System.out.println("MBeanCreationException ERROR: " + var11.toString());
         this.currSection = oSection;
         throw new Exception(var11.toString());
      } catch (InvalidAttributeValueException var12) {
         System.out.println("InvalidattributeException ERROR: " + var12.toString());
         this.currSection = oSection;
         throw new Exception(var12.toString());
      } catch (InstanceNotFoundException var13) {
         System.out.println("InstanceNotFoundException ERROR: " + var13.toString());
         this.currSection = oSection;
         throw new Exception(var13.toString());
      } catch (Exception var14) {
         System.out.println("ERROR: Could not create WTCImport , reason(" + var14.toString() + ")!");
         this.currSection = oSection;
         throw var14;
      }

      this.currSection = oSection;
      System.out.println("Processing IMPMBEAN : " + impname + " done.");
      return true;
   }

   private boolean setupPasswd(Element ee, int pwdindex) throws Exception {
      String pwdname = "pwd" + pwdindex;
      System.out.println("Processing PASSWDMBEAN : " + pwdname + " started.");
      String oSection = this.currSection;
      this.currSection = "T_DM_PASSWORD";

      try {
         WTCPasswordMBean pwdmb = (WTCPasswordMBean)this.configHome.createAdminMBean(pwdname, "WTCPassword", this.domainName, this.wtcsmb);
         String lapnm = this.getEAVal(ee, "LocalAccessPoint");
         if (!this.accessLMap.containsKey(lapnm)) {
            System.out.println("ERROR: Undefined LocalAccessPoint " + lapnm + " found in Export definition!");
            this.currSection = oSection;
            throw new Exception("Undefined Export LocalAccessPoint");
         }

         pwdmb.setLocalAccessPoint(lapnm);
         String rapnm = this.getEAVal(ee, "RemoteAccessPoint");
         if (!this.accessRMap.containsKey(rapnm)) {
            System.out.println("ERROR: Undefined RemoteAccessPoint " + rapnm + " found in Password definition!");
            this.currSection = oSection;
            throw new Exception("Undefined Password RemoteAccessPoint");
         }

         pwdmb.setRemoteAccessPoint(rapnm);
         pwdmb.setLocalPassword(this.getSubElemText(ee, "LocalPassword", true));
         pwdmb.setLocalPasswordIV(this.getSubEAVal(ee, "LocalPassword", "IV"));
         pwdmb.setRemotePassword(this.getSubElemText(ee, "RemotePassword", true));
         pwdmb.setRemotePasswordIV(this.getSubEAVal(ee, "RemotePassword", "IV"));
      } catch (MBeanCreationException var8) {
         System.out.println("MBeanCreationException ERROR: " + var8.toString());
         this.currSection = oSection;
         throw new Exception(var8.toString());
      } catch (InvalidAttributeValueException var9) {
         System.out.println("InvalidattributeException ERROR: " + var9.toString());
         this.currSection = oSection;
         throw new Exception(var9.toString());
      } catch (InstanceNotFoundException var10) {
         System.out.println("InstanceNotFoundException ERROR: " + var10.toString());
         this.currSection = oSection;
         throw new Exception(var10.toString());
      } catch (Exception var11) {
         System.out.println("ERROR: Could not create WTCPassword , reason(" + var11.toString() + ")!");
         this.currSection = oSection;
         throw var11;
      }

      this.currSection = oSection;
      System.out.println("Processing PASSWDMBEAN : " + pwdname + " done.");
      return true;
   }

   private boolean setupResources(Element ee) throws Exception {
      System.out.println("Processing RESMBEAN started");
      String oSection = this.currSection;
      this.currSection = "T_DM_RESOURCES";

      WTCResourcesMBean resmb;
      try {
         resmb = (WTCResourcesMBean)this.configHome.createAdminMBean("res1", "WTCResources", this.domainName, this.wtcsmb);
      } catch (MBeanCreationException var22) {
         System.out.println("MBeanCreationException ERROR: " + var22.toString());
         this.currSection = oSection;
         throw new Exception(var22.toString());
      }

      for(Node cn = ee.getFirstChild(); cn != null; cn = cn.getNextSibling()) {
         if (cn.getNodeType() == 1 && cn instanceof Element) {
            Element se = (Element)cn;
            String sen = se.getTagName();
            int itbl16 = false;
            int itbl32 = false;
            Node tnode;
            Node ccn;
            Element sse;
            String ssen;
            String sseav;
            String tblname;
            int itbl16;
            int itbl32;
            if (sen.equals("FieldTables")) {
               itbl16 = 0;
               itbl32 = 0;

               for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                  if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                     sse = (Element)ccn;
                     ssen = sse.getTagName();
                     if (ssen.equals("FldTblClass")) {
                        sseav = this.getEAVal(sse, "Type");
                        if (sseav == null) {
                           System.out.println("ERROR: Type for the Field Table is not specified!");
                           this.currSection = oSection;
                           return false;
                        }

                        if (sseav.equals("fml32")) {
                           ++itbl32;
                        } else if (sseav.equals("fml16")) {
                           ++itbl16;
                        }
                     }
                  }
               }

               if (itbl16 != 0) {
                  myFldTbls16 = new String[itbl16];
               }

               if (itbl32 != 0) {
                  myFldTbls32 = new String[itbl32];
               }

               itbl16 = 0;
               itbl32 = 0;

               for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                  if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                     sse = (Element)ccn;
                     ssen = sse.getTagName();
                     if (ssen.equals("FldTblClass")) {
                        tnode = sse.getFirstChild();
                        if (tnode == null) {
                           System.out.println("ERROR: Can not get TNODE!");
                           this.currSection = oSection;
                           return false;
                        }

                        tblname = tnode.getNodeValue();
                        if (tblname == null) {
                           System.out.println("ERROR: Can not get FldTbl NODE value!");
                           this.currSection = oSection;
                           return false;
                        }

                        try {
                           sseav = this.getEAVal(sse, "Type");
                           if (sseav.equals("fml32")) {
                              myFldTbls32[itbl32] = tblname.trim();
                              ++itbl32;
                           } else if (sseav.equals("fml16")) {
                              myFldTbls16[itbl16] = tblname.trim();
                              ++itbl16;
                           } else {
                              System.out.println("WARNING: Unknown Type label for FldTblClass: " + sseav);
                           }
                        } catch (Exception var17) {
                           System.out.println("ERROR: Can not get Resources FldTbl Typeobject, reason(" + var17.toString() + ")!");
                           this.currSection = oSection;
                           throw var17;
                        }
                     }
                  }
               }

               try {
                  if (itbl16 != 0) {
                     resmb.setFldTbl16Classes(myFldTbls16);
                  }

                  if (itbl32 != 0) {
                     resmb.setFldTbl32Classes(myFldTbls32);
                  }
               } catch (InvalidAttributeValueException var21) {
                  System.out.println("InvalidattributeException ERROR: " + var21.toString());
                  this.currSection = oSection;
                  throw new Exception(var21.toString());
               }
            } else if (!sen.equals("ViewTables")) {
               if (sen.equals("AppPassword")) {
                  try {
                     tnode = se.getFirstChild();
                     if (tnode == null) {
                        System.out.println("ERROR: No TNODE for AppPassword was found!");
                        this.currSection = oSection;
                        return false;
                     }

                     String tmps = tnode.getNodeValue();
                     if (tmps == null) {
                        System.out.println("ERROR: Failed to get AppPassword text!");
                        this.currSection = oSection;
                        return false;
                     }

                     resmb.setAppPassword(tmps.trim());
                     resmb.setAppPasswordIV(this.getEAVal(se, "IV"));
                  } catch (InvalidAttributeValueException var18) {
                     System.out.println("InvalidattributeException ERROR: " + var18.toString());
                     this.currSection = oSection;
                     throw new Exception(var18.toString());
                  } catch (Exception var19) {
                     System.out.println("ERROR: Couldn't handle Resources App Passwd/IV info, reason(" + var19.toString() + ")!");
                     this.currSection = oSection;
                     throw var19;
                  }
               }
            } else {
               itbl16 = 0;
               itbl32 = 0;

               for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                  if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                     sse = (Element)ccn;
                     ssen = sse.getTagName();
                     if (ssen.equals("ViewTblClass")) {
                        sseav = this.getEAVal(sse, "Type");
                        if (sseav == null) {
                           System.out.println("ERROR: Type for the Field Table is not  specified!");
                           this.currSection = oSection;
                           return false;
                        }

                        if (sseav.equals("view32")) {
                           ++itbl32;
                        } else if (sseav.equals("view16")) {
                           ++itbl16;
                        }
                     }
                  }
               }

               if (itbl16 != 0) {
                  myViewTbls16 = new String[itbl16];
               }

               if (itbl32 != 0) {
                  myViewTbls32 = new String[itbl32];
               }

               itbl16 = 0;
               itbl32 = 0;

               for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                  if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                     sse = (Element)ccn;
                     ssen = sse.getTagName();
                     if (ssen.equals("ViewTblClass")) {
                        tnode = sse.getFirstChild();
                        if (tnode == null) {
                           System.out.println("ERROR: Can not get TNODE!");
                           this.currSection = oSection;
                           return false;
                        }

                        tblname = tnode.getNodeValue();
                        if (tblname == null) {
                           System.out.println("ERROR: Can not get ViewTbl NODE value!");
                           this.currSection = oSection;
                           return false;
                        }

                        try {
                           sseav = this.getEAVal(sse, "Type");
                           if (sseav.equals("view32")) {
                              myViewTbls32[itbl32] = tblname.trim();
                              ++itbl32;
                           } else if (sseav.equals("view16")) {
                              myViewTbls16[itbl16] = tblname.trim();
                              ++itbl16;
                           } else {
                              System.out.println("WARNING: Unknown Type label for ViewTblClass: " + sseav);
                           }
                        } catch (Exception var16) {
                           System.out.println("ERROR: Getting Resources ViewTbl Type, reason(" + var16.toString() + ")!");
                           this.currSection = oSection;
                           throw var16;
                        }
                     }
                  }
               }

               try {
                  if (itbl16 != 0) {
                     resmb.setViewTbl16Classes(myViewTbls16);
                  }

                  if (itbl32 != 0) {
                     resmb.setViewTbl32Classes(myViewTbls32);
                  }
               } catch (InvalidAttributeValueException var20) {
                  System.out.println("InvalidattributeException ERROR: " + var20.toString());
                  this.currSection = oSection;
                  throw new Exception(var20.toString());
               }
            }
         }
      }

      this.currSection = oSection;
      System.out.println("Processing RESMBEAN done");
      return true;
   }

   private boolean setupRedirect(Element ee, int rdindex) throws Exception {
      String rdname = "redir" + rdindex;
      System.out.println("Processing REDIRECTMBEAN : " + rdname + " started.");
      String oSection = this.currSection;
      this.currSection = "fromto";

      try {
         WTCtBridgeRedirectMBean rdmb = (WTCtBridgeRedirectMBean)this.configHome.createAdminMBean(rdname, "WTCtBridgeRedirect", this.domainName, this.wtcsmb);
         rdmb.setDirection(this.getSubElemText(ee, "direction", true));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "translateFML", false)) != null) {
            if (tmps.equalsIgnoreCase("NO")) {
               tmps = "NO";
            }

            rdmb.setTranslateFML(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "replyQ", false)) != null) {
            rdmb.setReplyQ(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "metadataFile", false)) != null) {
            rdmb.setMetaDataFile(tmps);
         }

         Element tmpe = this.getSubElem(ee, "source", true);
         rdmb.setSourceName(this.getSubElemText(tmpe, "Name", true));
         if ((tmps = this.getSubElemText(tmpe, "AccessPoint", false)) != null) {
            rdmb.setSourceAccessPoint(tmps);
         }

         if ((tmps = this.getSubElemText(tmpe, "Qspace", false)) != null) {
            rdmb.setSourceQspace(tmps);
         }

         tmpe = this.getSubElem(ee, "target", true);
         rdmb.setTargetName(this.getSubElemText(tmpe, "Name", true));
         if ((tmps = this.getSubElemText(tmpe, "AccessPoint", false)) != null) {
            rdmb.setTargetAccessPoint(tmps);
         }

         if ((tmps = this.getSubElemText(tmpe, "Qspace", false)) != null) {
            rdmb.setTargetQspace(tmps);
         }
      } catch (MBeanCreationException var8) {
         System.out.println("MBeanCreationException ERROR: " + var8.toString());
         this.currSection = oSection;
         throw new Exception(var8.toString());
      } catch (InvalidAttributeValueException var9) {
         System.out.println("InvalidattributeException ERROR: " + var9.toString());
         this.currSection = oSection;
         throw new Exception(var9.toString());
      } catch (InstanceNotFoundException var10) {
         System.out.println("InstanceNotFoundException ERROR: " + var10.toString());
         this.currSection = oSection;
         throw new Exception(var10.toString());
      } catch (Exception var11) {
         System.out.println("ERROR: Could not create WTCtBridgeRedirect , reason(" + var11.toString() + ")!");
         this.currSection = oSection;
         throw var11;
      }

      this.currSection = oSection;
      System.out.println("Processing REDIRECTMBEAN : " + rdname + " done.");
      return true;
   }

   private boolean setupTBGlobal(Element ee) throws Exception {
      System.out.println("Processing TBGLOBALMBEAN started.");
      String oSection = this.currSection;
      this.currSection = "tBridge";

      try {
         WTCtBridgeGlobalMBean tbgmb = (WTCtBridgeGlobalMBean)this.configHome.createAdminMBean("tbgbl1", "WTCtBridgeGlobal", this.domainName, this.wtcsmb);
         String tmps;
         if ((tmps = this.getSubElemText(ee, "transactional", false)) != null) {
            tbgmb.setTransactional(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "timeout", false)) != null) {
            tbgmb.setTimeout(Integer.parseInt(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "retries", false)) != null) {
            tbgmb.setRetries(Integer.parseInt(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "retryDelay", false)) != null) {
            tbgmb.setRetryDelay(Integer.parseInt(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "wlsErrorDestination", false)) != null) {
            tbgmb.setWlsErrorDestination(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "tuxErrorQueue", false)) != null) {
            tbgmb.setTuxErrorQueue(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "deliveryModeOverride", false)) != null) {
            tbgmb.setDeliveryModeOverride(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "defaultreplyDeliveryMode", false)) != null) {
            tbgmb.setDefaultReplyDeliveryMode(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "userID", false)) != null) {
            tbgmb.setUserId(tmps);
         }

         if ((tmps = this.getSubElemText(ee, "allowNonStandardTypes", false)) != null) {
            tbgmb.setAllowNonStandardTypes(tmps);
         }

         tbgmb.setJndiFactory(this.getSubElemText(ee, "jndiFactory", true));
         tbgmb.setJmsFactory(this.getSubElemText(ee, "jmsFactory", true));
         tbgmb.setTuxFactory(this.getSubElemText(ee, "tuxFactory", true));
         String pmaps;
         if ((pmaps = this.getPmaps(ee, "TuxtoJms")) != null) {
            tbgmb.setTuxToJmsPriorityMap(pmaps);
         }

         if ((pmaps = this.getPmaps(ee, "JmstoTux")) != null) {
            tbgmb.setJmsToTuxPriorityMap(pmaps);
         }
      } catch (MBeanCreationException var6) {
         System.out.println("MBeanCreationException ERROR: " + var6.toString());
         this.currSection = oSection;
         throw new Exception(var6.toString());
      } catch (InvalidAttributeValueException var7) {
         System.out.println("InvalidattributeException ERROR: " + var7.toString());
         this.currSection = oSection;
         throw new Exception(var7.toString());
      } catch (InstanceNotFoundException var8) {
         System.out.println("InstanceNotFoundException ERROR: " + var8.toString());
         this.currSection = oSection;
         throw new Exception(var8.toString());
      } catch (Exception var9) {
         System.out.println("ERROR: Could not create WTCtBridgeGlobal , reason(" + var9.toString() + ")!");
         this.currSection = oSection;
         throw var9;
      }

      this.currSection = oSection;
      System.out.println("Processing TBGLOBALMBEAN done.");
      return true;
   }

   private String getPmaps(Element tbridge, String direction) throws Exception {
      if (DebugOn) {
         System.out.println("getPmaps entry: " + direction);
      }

      if (tbridge == null) {
         System.out.println("ERROR: The tBridge input was not defined.");
         throw new Exception("Element should always be defined");
      } else {
         Element prm = this.getSubElem(tbridge, "priorityMapping", false);
         if (prm == null) {
            return null;
         } else {
            Element ee = this.getSubElem(prm, direction, true);
            String maps = null;

            for(Node lc = ee.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
               if (lc.getNodeType() == 1 && lc instanceof Element) {
                  Element e = (Element)lc;
                  String en = e.getTagName();
                  if (en.equals("pMap")) {
                     String map1 = this.getSubElemText(e, "value", true);
                     String map2 = this.getSubElemText(e, "range", true);
                     if (maps == null) {
                        maps = map1 + ":" + map2;
                     } else {
                        maps = maps + "|" + map1 + ":" + map2;
                     }
                  }
               }
            }

            if (DebugOn) {
               System.out.println("getPmaps returns: " + maps);
            }

            return maps;
         }
      }
   }

   private String getEAVal(Element ee, String attr) throws Exception {
      if (DebugOn) {
         System.out.println("getEAVal entry: " + attr);
      }

      String ev = ee.getAttribute(attr);
      if (ev == null) {
         System.out.println("ERROR: The element " + ee.getTagName() + " does not have an attribute " + attr + " defined!");
         throw new Exception("Element missing attribute");
      } else {
         if (DebugOn) {
            System.out.println("getEAVal returns: " + ev);
         }

         return ev;
      }
   }

   private String getSubElemText(Element ee, String subtname, boolean required) throws Exception {
      if (DebugOn) {
         System.out.println("getSubElemText entry: " + subtname);
      }

      NodeList nl = ee.getElementsByTagName(subtname);
      if (nl.getLength() != 1) {
         if (required) {
            System.out.println("ERROR: The parent element " + ee.getTagName() + " does not have one(and only one) sub element of the tag name " + subtname + "!");
            throw new Exception("Count error on subelements");
         } else {
            return null;
         }
      } else {
         Node sn = nl.item(0);
         Node snn = sn.getFirstChild();
         if (snn == null) {
            if (required) {
               System.out.println("ERROR: The parent element " + ee.getTagName() + " has zero sub element of the tag name " + subtname + "!");
               throw new Exception("Missing subelement");
            } else {
               return null;
            }
         } else {
            String snnv = snn.getNodeValue();
            if (snnv == null) {
               if (required) {
                  System.out.println("ERROR: The parent element " + ee.getTagName() + " has the sub element of the tag name " + subtname + " without value!");
                  throw new Exception("Missing subelement value");
               } else {
                  return null;
               }
            } else {
               if (DebugOn) {
                  System.out.println("getSubElemText returns: " + snnv);
               }

               return snnv.trim();
            }
         }
      }
   }

   private String getSubEAVal(Element ee, String subtname, String attr) throws Exception {
      if (DebugOn) {
         System.out.println("getSubEAVal entry: " + subtname + " " + attr);
      }

      NodeList nl = ee.getElementsByTagName(subtname);
      if (nl.getLength() != 1) {
         System.out.println("ERROR: The parent element " + ee.getTagName() + " does not have one(and only one) sub element of the tag name " + subtname + "!");
         throw new Exception("Missing subelement");
      } else {
         Element se = (Element)nl.item(0);
         String ev = se.getAttribute(attr);
         if (ev == null) {
            System.out.println("ERROR: The element " + ee.getTagName() + " does not have an attribute " + subtname + " defined!");
            throw new Exception("Missing subelement value");
         } else {
            if (DebugOn) {
               System.out.println("getSubEAVal returns: " + ev);
            }

            return ev;
         }
      }
   }

   private Element getSubElem(Element ee, String subtname, boolean required) throws Exception {
      if (DebugOn) {
         System.out.println("getSubElem entry: " + subtname);
      }

      NodeList nl = ee.getElementsByTagName(subtname);
      if (nl.getLength() != 1) {
         if (required) {
            System.out.println("ERROR: The parent element " + ee.getTagName() + " does not have one(and only one) sub element of the tag name " + subtname + "!");
            throw new Exception("Missing subelement");
         } else {
            return null;
         }
      } else {
         if (DebugOn) {
            System.out.println("getSubElem returns: ");
         }

         return (Element)nl.item(0);
      }
   }
}
