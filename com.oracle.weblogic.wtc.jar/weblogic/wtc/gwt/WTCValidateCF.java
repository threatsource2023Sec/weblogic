package weblogic.wtc.gwt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Timer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.wtc.jatmi.FldTbl;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.ViewHelper;

public final class WTCValidateCF {
   private Hashtable myImportedServices = new Hashtable();
   private Hashtable myExportedServices = new Hashtable();
   private FldTbl[] myFldTbls16;
   private FldTbl[] myFldTbls32;
   private TDMLocalTDomain[] ltd_list;
   private TDMRemoteTDomain[] rtd_list;
   private TDMPasswd[] pwd_list;
   private String myAppPwIV;
   private String myAppPwPWD;
   private int ltdcnt = 0;
   private int rtdcnt = 0;
   private int pwdcnt = 0;
   private File c_fname = null;
   private HashMap accessMap = new HashMap();
   private String currSection = null;
   private static final String localDomainSection = "T_DM_LOCAL_TDOMAIN";
   private static final String remoteDomainSection = "T_DM_REMOTE_TDOMAIN";
   private static final String exportSection = "T_DM_EXPORT";
   private static final String importSection = "T_DM_IMPORT";
   private static final String passwdSection = "T_DM_PASSWORD";
   private static final String resourceSection = "T_DM_RESOURCES";

   public static void main(String[] args) {
      if (args.length != 1) {
         System.out.println("Usage: WTCValidateCF <BDMCONFIG>");
      } else {
         String bdmconfig = args[0];
         WTCValidateCF c = new WTCValidateCF();
         c.checkSyntax(bdmconfig);
      }
   }

   WTCValidateCF() {
   }

   void checkSyntax(String uri) {
      try {
         this.c_fname = new File(uri);
         System.out.println("Validating Config file " + this.c_fname.getPath() + "...");
         if (!this.loadFile(this.c_fname)) {
            System.out.println("ERROR: Validation failed for configuration XML file!");
            return;
         }

         System.out.println("The XML configuration file validation is done!");
         System.out.println("No error found!!!");
      } catch (NullPointerException var3) {
         System.out.println("ERROR: " + var3.toString());
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
      } catch (TPException var9) {
         System.out.println("ERROR: TP Exception, reason(" + var9.getMessage() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      } catch (NumberFormatException var10) {
         System.out.println("ERROR: Invalid number format(" + var10.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      } catch (Exception var11) {
         System.out.println("ERROR: Exception, reason(" + var11.toString() + ")!");
         if (this.currSection != null) {
            System.out.println("INFO: error occurred in " + this.currSection + " element.");
         }

         return false;
      }
   }

   private boolean extractInfo(Document doc) throws TPException {
      Element top = doc.getDocumentElement();
      if (top == null) {
         System.out.println("ERROR: No top element from file " + this.c_fname.getPath());
         return false;
      } else {
         String rootname = top.getTagName();
         if (rootname != null && rootname.equals("WTC_CONFIG")) {
            Element bdm = null;

            Node lc;
            Element e;
            String en;
            for(lc = top.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
               if (lc.getNodeType() == 1 && lc instanceof Element) {
                  e = (Element)lc;
                  en = e.getTagName();
                  if (en.equals("tBridge")) {
                     System.out.println("INFO: tBridge is configured.");
                     break;
                  }
               }
            }

            for(lc = top.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
               if (lc.getNodeType() == 1 && lc instanceof Element) {
                  e = (Element)lc;
                  en = e.getTagName();
                  if (en.equals("BDMCONFIG")) {
                     bdm = e;
                     break;
                  }
               }
            }

            if (bdm == null) {
               System.out.println("ERROR: No BDMCONFIG element in config file " + this.c_fname.getPath());
               return false;
            } else {
               for(lc = bdm.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                  if (lc.getNodeType() == 1 && lc instanceof Element) {
                     e = (Element)lc;
                     en = e.getTagName();
                     if (en.equals("T_DM_LOCAL_TDOMAIN")) {
                        ++this.ltdcnt;
                     } else if (en.equals("T_DM_REMOTE_TDOMAIN")) {
                        ++this.rtdcnt;
                     } else if (en.equals("T_DM_PASSWORD")) {
                        ++this.pwdcnt;
                     }
                  }
               }

               if (this.ltdcnt == 0) {
                  System.out.println("ERROR: Requires at least one local domain defined.");
                  return false;
               } else {
                  this.ltd_list = new TDMLocalTDomain[this.ltdcnt];
                  if (this.rtdcnt != 0) {
                     this.rtd_list = new TDMRemoteTDomain[this.rtdcnt];
                  }

                  if (this.pwdcnt != 0) {
                     this.pwd_list = new TDMPasswd[this.pwdcnt];
                  }

                  boolean ok = true;
                  int rtdi = 0;
                  int ltdi = 0;
                  int pwdi = 0;

                  for(lc = bdm.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                     if (lc.getNodeType() == 1 && lc instanceof Element) {
                        e = (Element)lc;
                        en = e.getTagName();
                        if (en.equals("T_DM_LOCAL_TDOMAIN")) {
                           ok = this.setupTDMLocalTD(e, ltdi);
                           if (!ok) {
                              System.out.println("ERROR: Could not complete processing of element " + en);
                              return false;
                           }

                           ++ltdi;
                        }
                     }
                  }

                  for(lc = bdm.getFirstChild(); lc != null; lc = lc.getNextSibling()) {
                     if (lc.getNodeType() == 1 && lc instanceof Element) {
                        e = (Element)lc;
                        en = e.getTagName();
                        if (en.equals("T_DM_REMOTE_TDOMAIN")) {
                           ok = this.setupTDMRemoteTD(e, rtdi);
                           if (ok) {
                              ++rtdi;
                           }
                        } else if (en.equals("T_DM_EXPORT")) {
                           ok = this.setupTDMExport(e);
                        } else if (en.equals("T_DM_IMPORT")) {
                           ok = this.setupTDMImport(e);
                        } else if (en.equals("T_DM_PASSWORD")) {
                           ok = this.setupTDMPasswd(e, pwdi);
                           if (ok) {
                              ++pwdi;
                           }
                        } else if (en.equals("T_DM_RESOURCES")) {
                           ok = this.setupTDMResources(e);
                        }

                        if (!ok) {
                           System.out.println("ERROR: Could not complete processing of element " + en);
                           return false;
                        }
                     }
                  }

                  if (!this.crossChecking()) {
                     System.out.println("ERROR: Second phase processing failed.");
                     return false;
                  } else {
                     return true;
                  }
               }
            }
         } else {
            System.out.println("ERROR: Bad top element name \"" + rootname + "\" from config file " + this.c_fname.getPath());
            return false;
         }
      }
   }

   private boolean setupTDMLocalTD(Element ee, int ltdindex) throws TPException {
      String oSection = this.currSection;
      this.currSection = "T_DM_LOCAL_TDOMAIN";
      String label = this.getEAVal(ee, "AccessPoint");

      TDMLocalTDomain ltd;
      try {
         ltd = new TDMLocalTDomain(label);
         if (this.accessMap.put(label, ltd) != null) {
            System.out.println("ERROR: Duplicated AccessPoint " + label + " found in Local TDomain definition!");
            this.currSection = oSection;
            return false;
         }
      } catch (Exception var7) {
         System.out.println("ERROR: Could not create TDMLocalTDomain " + label + ", reason(" + var7.toString() + ")!");
         this.currSection = oSection;
         return false;
      }

      ltd.setWlsClusterName(this.getSubElemText(ee, "WlsClusterName", true));
      ltd.setAccessPointId(this.getSubElemText(ee, "AccessPointId", true));
      ltd.setNWAddr(this.getSubElemText(ee, "NWAddr", true));
      ltd.setType(this.getSubElemText(ee, "Type", false));
      ltd.setSecurity(this.getSubElemText(ee, "Security", false));
      ltd.setConnectionPolicy(this.getSubElemText(ee, "ConnectionPolicy", false));
      ltd.setInteroperate(this.getSubElemText(ee, "Interoperate", false));
      this.getSubElemText(ee, "RetryInterval", false);
      String tmps;
      if ((tmps = this.getSubElemText(ee, "RetryInterval", false)) != null) {
         ltd.setRetryInterval(Long.parseLong(tmps, 10));
      }

      if ((tmps = this.getSubElemText(ee, "MaxRetries", false)) != null) {
         ltd.setMaxRetries(Long.parseLong(tmps, 10));
      }

      if ((tmps = this.getSubElemText(ee, "BlockTime", false)) != null) {
         ltd.setBlockTime(Long.parseLong(tmps) * 1000L);
      }

      if ((tmps = this.getSubElemText(ee, "CmpLimit", false)) != null) {
         ltd.setCmpLimit(Integer.parseInt(tmps, 10));
      }

      if ((tmps = this.getSubElemText(ee, "MinEncryptBits", false)) != null) {
         ltd.setMinEncryptBits(Integer.parseInt(tmps, 10));
      }

      if ((tmps = this.getSubElemText(ee, "MaxEncryptBits", false)) != null) {
         ltd.setMaxEncryptBits(Integer.parseInt(tmps, 10));
      }

      this.ltd_list[ltdindex] = ltd;
      this.currSection = oSection;
      return true;
   }

   private boolean setupTDMRemoteTD(Element ee, int rtdindex) throws TPException {
      String oSection = this.currSection;
      this.currSection = "T_DM_REMOTE_TDOMAIN";
      String label = this.getEAVal(ee, "AccessPoint");

      TDMRemoteTDomain rtd;
      try {
         rtd = new TDMRemoteTDomain(label, (TuxXidRply)null, (Timer)null);
         if (this.accessMap.put(label, rtd) != null) {
            System.out.println("ERROR: Duplicated AccessPoint " + label + " found in Remote TDomain definition!");
            this.currSection = oSection;
            return false;
         }
      } catch (Exception var9) {
         System.out.println("ERROR: Could not create TDMRemoteTDomain " + label + ", reason(" + var9.toString() + ")!");
         this.currSection = oSection;
         return false;
      }

      String lapnm = this.getSubElemText(ee, "LocalAccessPoint", true);
      int lapidx = this.getLTDindex(lapnm);
      if (lapidx == -1) {
         if (lapnm != null) {
            System.out.println("ERROR: The local TDomain " + lapnm + " for remote TDomain " + rtd.getAccessPoint() + " can not be found!");
         } else {
            System.out.println("ERROR: The local TDomain is not specified for remote TDomain " + rtd.getAccessPoint() + "!");
         }

         this.currSection = oSection;
         return false;
      } else {
         rtd.setLocalAccessPoint(lapnm);
         rtd.setAccessPointId(this.getSubElemText(ee, "AccessPointId", true));
         rtd.setNWAddr(this.getSubElemText(ee, "NWAddr", true));
         rtd.setType(this.getSubElemText(ee, "Type", false));
         rtd.setAclPolicy(this.getSubElemText(ee, "AclPolicy", false));
         rtd.setCredentialPolicy(this.getSubElemText(ee, "CredentialPolicy", false));
         rtd.setTpUsrFile(this.getSubElemText(ee, "TpUsrFile", false));
         String tmps;
         if ((tmps = this.getSubElemText(ee, "CmpLimit", false)) != null) {
            rtd.setCmpLimit(Integer.parseInt(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "MinEncryptBits", false)) != null) {
            rtd.setMinEncryptBits(Integer.parseInt(tmps, 10));
         }

         if ((tmps = this.getSubElemText(ee, "MaxEncryptBits", false)) != null) {
            rtd.setMaxEncryptBits(Integer.parseInt(tmps, 10));
         }

         tmps = this.getSubElemText(ee, "ConnectionPolicy", false);
         if (tmps != null && !tmps.equals("LOCAL")) {
            rtd.setConnPolicyConfigState(1);
         } else {
            if (tmps == null) {
               rtd.setConnPolicyConfigState(2);
            } else {
               rtd.setConnPolicyConfigState(3);
            }

            tmps = this.ltd_list[lapidx].getConnectionPolicy();
         }

         rtd.setConnectionPolicy(tmps);
         if ((tmps = this.getSubElemText(ee, "RetryInterval", false)) != null) {
            rtd.setRetryInterval(Long.parseLong(tmps, 10));
         } else {
            rtd.setRetryInterval(this.ltd_list[lapidx].getRetryInterval());
         }

         if ((tmps = this.getSubElemText(ee, "MaxRetries", false)) != null) {
            rtd.setMaxRetries(Long.parseLong(tmps, 10));
         } else {
            rtd.setMaxRetries(this.ltd_list[lapidx].getMaxRetries());
         }

         this.rtd_list[rtdindex] = rtd;
         this.currSection = oSection;
         return true;
      }
   }

   private boolean setupTDMExport(Element ee) {
      String oSection = this.currSection;
      this.currSection = "T_DM_EXPORT";
      String lapnm = this.getEAVal(ee, "LocalAccessPoint");
      int lapidx = this.getLTDindex(lapnm);
      if (lapidx == -1) {
         if (lapnm != null) {
            System.out.println("ERROR: The Local TDomain " + lapnm + " for exported service " + this.getEAVal(ee, "ResourceName") + " can not be found!");
         } else {
            System.out.println("ERROR: The Local TDomain is not specified for exported service " + this.getEAVal(ee, "ResourceName") + "!");
         }

         this.currSection = oSection;
         return false;
      } else {
         TDMLocalTDomain eltd = this.ltd_list[lapidx];

         TDMExport exp_svc;
         try {
            exp_svc = new TDMExport(this.getEAVal(ee, "ResourceName"), eltd);
         } catch (Exception var8) {
            System.out.println("ERROR: Could not create TDMExport for exported service " + this.getEAVal(ee, "ResourceName"));
            this.currSection = oSection;
            return false;
         }

         exp_svc.setRemoteName(this.getSubElemText(ee, "RemoteName", false));
         exp_svc.setEJBName(this.getSubElemText(ee, "EJBName", false));
         this.currSection = oSection;
         return true;
      }
   }

   private boolean setupTDMImport(Element ee) throws TPException {
      String oSection = this.currSection;
      this.currSection = "T_DM_IMPORT";
      String lapnm = this.getEAVal(ee, "LocalAccessPoint");
      int lapidx = this.getLTDindex(lapnm);
      if (lapidx == -1) {
         if (lapnm != null) {
            System.out.println("ERROR: The local TDomain " + lapnm + " for imported service " + this.getEAVal(ee, "ResourceName") + " can not be found!");
         } else {
            System.out.println("ERROR: The local TDomain is not specified for imported service " + this.getEAVal(ee, "ResourceName") + "!");
         }

         this.currSection = oSection;
         return false;
      } else {
         TDMLocalTDomain iltd = this.ltd_list[lapidx];
         String raplist = this.getEAVal(ee, "RemoteAccessPointList");
         TDMRemoteTDomain irtd;
         TDMRemoteTDomain[] irdoms;
         int rapidx;
         if (raplist.indexOf(44) == -1) {
            rapidx = this.getRTDindex(raplist);
            if (rapidx == -1) {
               System.out.println("ERROR: The remote TDomain list " + raplist + " is not defined for imported service " + this.getEAVal(ee, "ResourceName") + "!");
               this.currSection = oSection;
               return false;
            }

            irtd = this.rtd_list[rapidx];
            irtd.setLocalAccessPoint(lapnm);
            irdoms = new TDMRemoteTDomain[]{irtd};
         } else {
            StringTokenizer st = new StringTokenizer(raplist, ",");
            irdoms = new TDMRemoteTDomain[st.countTokens()];

            for(int j = 0; st.hasMoreTokens(); ++j) {
               String rapnm = st.nextToken();
               rapidx = this.getRTDindex(rapnm);
               if (rapidx == -1) {
                  System.out.println("ERROR: The remote TDomain " + rapnm + " is not defined for imported service " + this.getEAVal(ee, "ResourceName") + "!");
                  this.currSection = oSection;
                  return false;
               }

               irtd = this.rtd_list[rapidx];
               irtd.setLocalAccessPoint(lapnm);
               irdoms[j] = irtd;
            }
         }

         TDMImport imp_svc;
         try {
            imp_svc = new TDMImport(this.getEAVal(ee, "ResourceName"), iltd, irdoms);
         } catch (Exception var13) {
            System.out.println("ERROR: Could not create TDMImport for imported service " + this.getEAVal(ee, "ResourceName"));
            return false;
         }

         imp_svc.setRemoteName(this.getSubElemText(ee, "RemoteName", false));
         String tmps = this.getSubElemText(ee, "TranTime", false);
         if (tmps != null) {
            imp_svc.setTranTime(Integer.parseInt(tmps, 10));
         }

         this.currSection = oSection;
         return true;
      }
   }

   private boolean setupTDMPasswd(Element ee, int pwdindex) {
      String oSection = this.currSection;
      this.currSection = "T_DM_PASSWORD";
      String lapnm = this.getEAVal(ee, "LocalAccessPoint");
      int lapidx = this.getLTDindex(lapnm);
      if (lapidx == -1) {
         if (lapnm != null) {
            System.out.println("ERROR: The local TDomain " + lapnm + " for password " + this.getEAVal(ee, "LocalAccessPoint") + " can not be found!");
         } else {
            System.out.println("ERROR: The local TDomain is not specified for password ");
         }

         this.currSection = oSection;
         return false;
      } else {
         TDMLocalTDomain var10000 = this.ltd_list[lapidx];
         String rapnm = this.getEAVal(ee, "RemoteAccessPoint");
         int rapidx = this.getRTDindex(rapnm);
         if (rapidx == -1) {
            if (rapnm != null) {
               System.out.println("ERROR: Can not continue processing the TDMPassword object because the RemoteDomain " + rapnm + " is not valid!");
            } else {
               System.out.println("ERROR: Can not continue processing the TDMPassword object because the RemoteDomain  is not specified!");
            }

            this.currSection = oSection;
            return false;
         } else {
            TDMRemoteTDomain var13 = this.rtd_list[rapidx];

            TDMPasswd passwd;
            try {
               passwd = new TDMPasswd(lapnm, rapnm);
            } catch (Exception var12) {
               System.out.println("ERROR: Could not construct TDMPasswd object for local domain " + lapnm + " and remote domain " + rapnm + " (" + var12.toString() + ")!");
               this.currSection = oSection;
               return false;
            }

            passwd.setLocalPasswordIV(this.getSubEAVal(ee, "LocalPassword", "IV"));
            passwd.setLocalPassword(this.getSubElemText(ee, "LocalPassword", true));
            passwd.setRemotePasswordIV(this.getSubEAVal(ee, "RemotePassword", "IV"));
            passwd.setRemotePassword(this.getSubElemText(ee, "RemotePassword", true));
            this.pwd_list[pwdindex] = passwd;
            this.currSection = oSection;
            return true;
         }
      }
   }

   private boolean setupTDMResources(Element ee) {
      String oSection = this.currSection;
      this.currSection = "T_DM_RESOURCES";
      String sseav = null;

      for(Node cn = ee.getFirstChild(); cn != null; cn = cn.getNextSibling()) {
         if (cn.getNodeType() == 1 && cn instanceof Element) {
            Element se = (Element)cn;
            String sen = se.getTagName();
            Node ccn;
            Node tnode;
            Element sse;
            String ssen;
            if (!sen.equals("FieldTables")) {
               String tblnm;
               if (sen.equals("ViewTables")) {
                  tblnm = null;
                  String viewname = null;
                  ViewHelper viewhelper = new ViewHelper();
                  ViewHelper viewinst = ViewHelper.getInstance();

                  for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                     if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                        sse = (Element)ccn;
                        ssen = sse.getTagName();
                        if (ssen.equals("ViewTblClass")) {
                           tnode = sse.getFirstChild();
                           if (tnode == null) {
                              System.out.println("ERROR: Can not get ViewTbl Text NODE!");
                              this.currSection = oSection;
                              return false;
                           }

                           tblnm = tnode.getNodeValue();
                           if (tblnm == null) {
                              System.out.println("ERROR: Can not get ViewTbl NODE value!");
                              this.currSection = oSection;
                              return false;
                           }

                           try {
                              sseav = this.getEAVal(sse, "Type");
                              if (!sseav.equals("view32") && !sseav.equals("view16")) {
                                 System.out.println("WARNING: Unknown Type label for ViewTblClass: " + sseav);
                              }

                              viewname = tblnm.substring(tblnm.lastIndexOf(46) + 1);
                              viewinst.setViewClass(viewname, tblnm.trim());
                           } catch (Exception var18) {
                              System.out.println("ERROR: Resources " + sseav + " Info , reason(" + var18.toString() + ")!");
                              this.currSection = oSection;
                              return false;
                           }
                        }
                     }
                  }
               } else if (sen.equals("AppPassword")) {
                  this.myAppPwIV = this.getEAVal(se, "IV");
                  tnode = se.getFirstChild();
                  if (tnode == null) {
                     System.out.println("ERROR: No TNODE for AppPassword was found!");
                     this.currSection = oSection;
                     return false;
                  }

                  tblnm = tnode.getNodeValue();
                  if (tblnm == null) {
                     System.out.println("ERROR: Failed to get AppPassword text!");
                     this.currSection = oSection;
                     return false;
                  }

                  this.myAppPwPWD = tblnm.trim();
               }
            } else {
               int tblcnt16 = 0;
               int tblcnt32 = 0;

               for(ccn = se.getFirstChild(); ccn != null; ccn = ccn.getNextSibling()) {
                  if (ccn.getNodeType() == 1 && ccn instanceof Element) {
                     sse = (Element)ccn;
                     ssen = sse.getTagName();
                     if (ssen.equals("FldTblClass")) {
                        sseav = this.getEAVal(sse, "Type");
                        if (sseav == null) {
                           System.out.println("ERROR: Type for the Field Table is not  specified!");
                           this.currSection = oSection;
                           return false;
                        }

                        if (sseav.equals("fml32")) {
                           ++tblcnt32;
                        } else {
                           ++tblcnt16;
                        }
                     }
                  }
               }

               if (tblcnt16 != 0) {
                  this.myFldTbls16 = new FldTbl[tblcnt16];
               }

               if (tblcnt32 != 0) {
                  this.myFldTbls32 = new FldTbl[tblcnt32];
               }

               int itbl16 = 0;
               int itbl32 = 0;

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

                        String locftcl = tnode.getNodeValue();
                        if (locftcl == null) {
                           System.out.println("ERROR: Can not get TNODE value!");
                           this.currSection = oSection;
                           return false;
                        }

                        try {
                           Class ftcl = Class.forName(locftcl.trim());
                           sseav = this.getEAVal(sse, "Type");
                           if (sseav.equals("fml32")) {
                              this.myFldTbls32[itbl32] = (FldTbl)ftcl.newInstance();
                              ++itbl32;
                           } else {
                              this.myFldTbls16[itbl16] = (FldTbl)ftcl.newInstance();
                              ++itbl16;
                           }
                        } catch (Exception var19) {
                           System.out.println("ERROR: Can not create TDMResources object, reason(" + var19.toString() + ")!");
                           this.currSection = oSection;
                           return false;
                        }
                     }
                  }
               }
            }
         }
      }

      this.currSection = oSection;
      return true;
   }

   private int getLTDindex(String ltdname) {
      for(int i = 0; i < this.ltdcnt; ++i) {
         String apname = this.ltd_list[i].getAccessPoint();
         if (apname.equals(ltdname)) {
            return i;
         }
      }

      return -1;
   }

   private int getRTDindex(String rtdname) {
      for(int i = 0; i < this.rtdcnt; ++i) {
         String apname = this.rtd_list[i].getAccessPoint();
         if (apname.equals(rtdname)) {
            return i;
         }
      }

      return -1;
   }

   private String getEAVal(Element ee, String attr) {
      String ev = ee.getAttribute(attr);
      if (ev == null) {
         System.out.println("ERROR: The element " + ee.getTagName() + " does not have an attribute " + attr + " defined!");
         return null;
      } else {
         return ev;
      }
   }

   private String getSubElemText(Element ee, String subtname, boolean required) {
      NodeList nl = ee.getElementsByTagName(subtname);
      if (nl.getLength() != 1) {
         if (required) {
            System.out.println("ERROR: The parent element " + ee.getTagName() + " has more than one sub element of the tag name " + subtname + "!");
         }

         return null;
      } else {
         Node sn = nl.item(0);
         Node snn = sn.getFirstChild();
         if (snn == null) {
            if (required) {
               System.out.println("ERROR: The parent element " + ee.getTagName() + " has zero sub element of the tag name " + subtname + "!");
            }

            return null;
         } else {
            String snnv = snn.getNodeValue();
            if (snnv == null) {
               if (required) {
                  System.out.println("ERROR: The parent element " + ee.getTagName() + " has the sub element of the tag name " + subtname + " without value!");
               }

               return null;
            } else {
               return snnv.trim();
            }
         }
      }
   }

   private String getSubEAVal(Element ee, String subtname, String attr) {
      NodeList nl = ee.getElementsByTagName(subtname);
      if (nl.getLength() != 1) {
         System.out.println("ERROR: The parent element " + ee.getTagName() + " has more than one sub element of the tag name " + subtname + "!");
         return null;
      } else {
         Element se = (Element)nl.item(0);
         String ev = se.getAttribute(attr);
         if (ev == null) {
            System.out.println("ERROR: The element " + ee.getTagName() + " does not have an attribute " + subtname + " defined!");
            return null;
         } else {
            return ev;
         }
      }
   }

   private boolean crossChecking() {
      HashMap domid_map = new HashMap();
      HashMap rdom_map = new HashMap();

      int i;
      RDomainListEntry entry;
      for(i = 0; i < this.rtd_list.length; ++i) {
         TDMRemoteTDomain rdom = this.rtd_list[i];

         try {
            rdom.checkConfigIntegrity();
         } catch (TPException var12) {
            return false;
         }

         domid_map.put(rdom.getAccessPointId(), rdom);
         RDomainListEntry crdle = new RDomainListEntry(rdom);
         if ((entry = (RDomainListEntry)rdom_map.put(rdom.getAccessPointId(), crdle)) != null) {
            crdle.setNext(entry);
         }
      }

      for(i = 0; i < this.ltd_list.length; ++i) {
         TDMLocalTDomain ldom = this.ltd_list[i];

         try {
            ldom.checkConfigIntegrity();
         } catch (TPException var11) {
            return false;
         }

         if (domid_map.put(ldom.getAccessPointId(), ldom) != null) {
            System.out.println("ERROR: Duplicated local domain id " + ldom.getAccessPointId() + " found!");
            return false;
         }
      }

      Iterator rdomIterator = rdom_map.values().iterator();

      while(rdomIterator.hasNext()) {
         entry = (RDomainListEntry)rdomIterator.next();
         HashMap ldom_map = new HashMap();

         while(true) {
            TDMRemoteTDomain rtde = entry.getRDom();
            TDMLocal ltde = rtde.getLocalAccessPointObject();
            if (ldom_map.put(ltde.getAccessPointId(), ltde) != null) {
               System.out.println("ERROR: There are two remote domain " + rtde.getAccessPointId() + " with same local domain " + ltde.getAccessPointId());
               return false;
            }

            if ((entry = entry.getNext()) == null) {
               break;
            }
         }
      }

      return true;
   }
}
