package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCLicenseManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCRemoteTuxDomMBean;
import weblogic.security.utils.MBeanKeyStoreConfiguration;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.DomainRegistry;
import weblogic.wtc.jatmi.OnTerm;
import weblogic.wtc.jatmi.PasswordUtils;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPINIT;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.gwatmi;

public class TDMRemoteTDomain extends TDMRemote implements OnTerm, BeanUpdateListener {
   static final long serialVersionUID = -6844147720547859282L;
   private String[] myNWAddr;
   private String[] ipaddress;
   private InetAddress[] myInetAddr;
   private int[] port;
   private boolean need_ia = true;
   private String federationURL = null;
   private String federationName = null;
   private int myCmpLimit;
   private int MinEncryptionBits;
   private int MaxEncryptionBits;
   private gwdsession myDsession;
   private Timer myTimeService;
   private String myAppKeyType;
   private boolean myAllowAnonymous;
   private int myDfltAppKey;
   private String myUidKw = null;
   private String myGidKw = null;
   private String myAppKeyClass = null;
   private String myParam = null;
   private WTCRemoteTuxDomMBean mBean = null;
   private boolean registered = false;
   private int keepAlive = -2;
   private int keepAliveWait = -1;
   private final ReentrantLock lock = new ReentrantLock();
   private boolean timedOut;
   private boolean[] useSDP;
   ScheduledReconnect connectingTask = null;

   public TDMRemoteTDomain(String AccessPoint, TuxXidRply anXidRply, Timer aTimeService) throws Exception {
      super(AccessPoint, anXidRply);
      super.setType("TDOMAIN");
      this.setAclPolicy("LOCAL");
      this.setCredentialPolicy("LOCAL");
      this.setCmpLimit(Integer.MAX_VALUE);
      this.setMinEncryptBits(0);
      this.setMaxEncryptBits(128);
      this.myTimeService = aTimeService;
      this.myDfltAppKey = -1;
      this.myAllowAnonymous = false;
   }

   public String getNWAddr() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myNWAddr[0];
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public String getNWAddr(int idx) {
      if (idx < 0) {
         return null;
      } else {
         this.r.lock();

         String var2;
         try {
            if (idx >= this.myNWAddr.length) {
               var2 = null;
               return var2;
            }

            var2 = this.myNWAddr[idx];
         } finally {
            this.r.unlock();
         }

         return var2;
      }
   }

   public void setNWAddr(String NWAddr) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (NWAddr == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/setNWAddr/addr=null");
            ntrace.doTrace("*]/TDMRemoteTDomain/setNWAddr/10/TPEINVAL");
         }

         throw new TPException(4, "null NWAddr found in remote domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/setNWAddr/addr=" + NWAddr);
         }

         this.parseNWAddr(NWAddr, false);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setNWAddr/40/SUCCESS");
         }

      }
   }

   public static boolean isNWAddrFormat(String nwaddr) {
      if (nwaddr != null && (nwaddr.startsWith("//") || nwaddr.toLowerCase().startsWith("sdp://"))) {
         if (nwaddr.toLowerCase().startsWith("sdp://")) {
            nwaddr = nwaddr.substring(4);
         }

         int colon_idx = nwaddr.indexOf(58);
         if (colon_idx >= 3 && colon_idx + 1 < nwaddr.length()) {
            String ipa = nwaddr.substring(2, colon_idx);
            if (Character.isDigit(ipa.charAt(0))) {
               int count = 0;
               int intval = false;
               String strval = null;
               StringTokenizer st = new StringTokenizer(ipa, ".");

               while(true) {
                  if (!st.hasMoreTokens()) {
                     if (count != 4) {
                        return false;
                     }
                     break;
                  }

                  strval = st.nextToken();

                  try {
                     int intval = Integer.parseInt(strval, 10);
                     if (intval < 0 || intval > 255) {
                        return false;
                     }
                  } catch (NumberFormatException var9) {
                     return false;
                  }

                  ++count;
               }
            }

            try {
               return Integer.parseInt(nwaddr.substring(colon_idx + 1), 10) >= 0;
            } catch (NumberFormatException var8) {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void parseNWAddr(String NWAddr, boolean validate) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (NWAddr == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/parseNWAddr/addr=null");
            ntrace.doTrace("*]/TDMRemoteTDomain/parseNWAddr/10/TPEINVAL");
         }

         throw new TPException(4, "null NWAddr found in remote domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/parseNWAddr/addr=" + NWAddr + ", validate = " + validate);
         }

         StringTokenizer tok_nwaddr = new StringTokenizer(NWAddr, ",");
         int count = tok_nwaddr.countTokens();
         String[] tok = new String[count];
         String[] ipa = new String[count];
         int[] port_a = new int[count];
         boolean[] tmp_useSDP = new boolean[count];

         for(int i = 0; i < count; ++i) {
            tok[i] = tok_nwaddr.nextToken();
            if (!isNWAddrFormat(tok[i])) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/parseNWAddr/20/TPEINVAL");
               }

               throw new TPException(4, "Unsupported NWAddr format \"" + NWAddr + "\" found in remote domain " + this.getAccessPointId());
            }

            tmp_useSDP[i] = false;
            if (tok[i].toLowerCase().startsWith("sdp://")) {
               tok[i] = tok[i].substring(4);
               tmp_useSDP[i] = true;
            }

            if (!validate) {
               int colon_idx = tok[i].indexOf(58);
               ipa[i] = tok[i].substring(2, colon_idx);
               String port_address = tok[i].substring(colon_idx + 1);
               port_a[i] = Integer.parseInt(port_address, 10);
            }
         }

         if (!validate) {
            this.w.lock();
            this.myNWAddr = tok;
            this.ipaddress = ipa;
            this.port = port_a;
            this.myInetAddr = null;
            this.need_ia = true;
            this.useSDP = tmp_useSDP;
            this.w.unlock();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/parseNWAddr/40/SUCCESS");
         }

      }
   }

   public String getFederationURL() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getFederationURL/");
      }

      this.r.lock();

      String var2;
      try {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getFederationURL/10/" + this.federationURL);
         }

         var2 = this.federationURL;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public boolean setFederationURL(String url) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setFederationURL/url=" + url);
      }

      this.w.lock();
      if (url != null && url.length() != 0) {
         this.federationURL = url;
      } else {
         this.federationURL = null;
         if (traceEnabled) {
            ntrace.doTrace("set to null");
         }
      }

      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setFederationURL/10");
      }

      return true;
   }

   public String getFederationName() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getFederationName/");
      }

      this.r.lock();

      String var2;
      try {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getFederationName/10/" + this.federationName);
         }

         var2 = this.federationName;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public boolean setFederationName(String name) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setFederationName/name=" + name);
      }

      this.w.lock();
      if (name != null && name.length() != 0) {
         this.federationName = name;
      } else {
         this.federationName = null;
         if (traceEnabled) {
            ntrace.doTrace("set to null");
         }
      }

      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setFederationName/10");
      }

      return true;
   }

   public gwdsession getDomainSession() {
      return this.myDsession;
   }

   public synchronized boolean getTimedOut() {
      return this.timedOut;
   }

   public boolean[] get_useSDP() {
      this.r.lock();

      boolean[] var1;
      try {
         var1 = this.useSDP;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public gwatmi getTsession(boolean docreate) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getTsession/" + this.getAccessPoint() + ", create = " + docreate);
      }

      gwdsession new_session = null;
      TDMLocalTDomain ldom = (TDMLocalTDomain)this.getLocalAccessPointObject();
      ldom.getBlockTime();
      this.timedOut = false;
      if (docreate || this.myDsession != null && this.myDsession.get_is_connected()) {
         if (docreate) {
            try {
               if (!this.lock.tryLock(ldom.getBlockTime(), TimeUnit.MILLISECONDS)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TDMRemoteTDomain/getTsession/03/Timeout");
                  }

                  this.timedOut = true;
                  return null;
               }
            } catch (InterruptedException var31) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/getTsession/03/Timeout");
               }

               this.timedOut = true;
               return null;
            }
         } else {
            this.lock.lock();
         }
      } else if (!this.lock.tryLock()) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getTsession/05/null");
         }

         return null;
      }

      gwatmi retVal = null;

      try {
         if (this.myDsession != null && this.myDsession.getIsTerminated()) {
            this.myDsession = null;
         }

         if (this.myDsession != null) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/getTsession/10/" + this.myDsession);
            }

            retVal = this.myDsession;
            throw new DoneException();
         }

         if (!docreate) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/getTsession/20/null");
            }

            retVal = null;
            throw new DoneException();
         }

         if (this.getConnectionPolicy().equals("INCOMING_ONLY")) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/getTsession/30/null");
            }

            retVal = null;
            throw new DoneException();
         }

         int count;
         if (this.myNWAddr != null && (count = this.myNWAddr.length) != 0) {
            if (this.myInetAddr == null) {
               this.myInetAddr = new InetAddress[count];
               this.need_ia = true;
            }

            if (this.need_ia) {
               this.need_ia = false;
               int j = 0;
               int i = 0;

               while(true) {
                  if (i >= count) {
                     if (j == count) {
                        WTCLogger.logWarnNoValidHostAddress(this.getAccessPointId());
                        if (traceEnabled) {
                           ntrace.doTrace("]/TDMRemoteTDomain/getTsession/45/null");
                        }

                        retVal = null;
                        throw new DoneException();
                     }
                     break;
                  }

                  if (this.myInetAddr[i] == null) {
                     try {
                        this.myInetAddr[i] = InetAddress.getByName(this.ipaddress[i]);
                     } catch (UnknownHostException var34) {
                        this.myInetAddr[i] = null;
                        this.need_ia = true;
                        if (traceEnabled) {
                           ntrace.doTrace("unknown host(" + this.ipaddress[i] + ") skip it");
                        }

                        ++j;
                     }
                  }

                  ++i;
               }
            }

            OatmialServices os = WTCService.getOatmialServices();

            try {
               ldom = (TDMLocalTDomain)this.getLocalAccessPointObject();
               atntdom80 myAtn = new atntdom80(ldom.getConnPrincipalName());
               WLSInvoke invoker = new WLSInvoke(ldom, this);
               new_session = new gwdsession(os.getTimeService(), this.myInetAddr, this.port, myAtn, invoker, WTCService.getUniqueGwdsessionId(), this.getUnknownXidRplyObj());
               gwdsession old_session = (gwdsession)DomainRegistry.addDomainSession(new_session);
               if (old_session != null) {
                  this.myDsession = old_session;
                  if (traceEnabled) {
                     ntrace.doTrace("]/TDMRemoteTDomain/getTsession/110/" + this.myDsession);
                  }

                  retVal = this.myDsession;
                  throw new DoneException();
               }

               this.myDsession = new_session;
               this.myDsession.set_BlockTime(ldom.getBlockTime());
               this.myDsession.setTerminationHandler(this);
               this.myDsession.set_compression_threshold(ldom.getCmpLimit());
               String ldomname = ldom.getAccessPoint();
               String rdomname = this.getAccessPoint();
               String sec_type = ldom.getSecurity();
               this.myDsession.set_sess_sec(sec_type);
               this.myDsession.setDesiredName(ldom.getConnPrincipalName());
               this.myDsession.set_dom_target_name(this.getConnPrincipalName());
               this.myDsession.set_local_domain_name(ldomname);
               this.myDsession.setRemoteDomainId(this.getAccessPointId());
               this.myDsession.setInteroperate(ldom.isInteroperate());
               this.myDsession.setKeepAlive(this.getKeepAlive());
               this.myDsession.setKeepAliveWait(this.getKeepAliveWait());
               this.myDsession.setAclPolicy(this.getAclPolicy());
               this.myDsession.setCredentialPolicy(this.getCredentialPolicy());
               this.myDsession.setAppKey(this.myAppKeyType);
               if (this.myAppKeyType != null && !this.myAppKeyType.equals("TpUsrFile")) {
                  if (this.myAppKeyType.equals("LDAP")) {
                     this.myDsession.setUidKw(this.myUidKw);
                     this.myDsession.setGidKw(this.myGidKw);
                  } else {
                     this.myDsession.setCustomAppKeyClass(this.myAppKeyClass);
                     this.myDsession.setCustomAppKeyClassParam(this.myParam);
                  }
               } else {
                  this.myDsession.setTpUserFile(this.getTpUsrFile());
               }

               this.myDsession.setAllowAnonymous(this.myAllowAnonymous);
               this.myDsession.setDfltAppKey(this.myDfltAppKey);
               String iv;
               String keyStore;
               String trustKSType;
               String trustKSLoc;
               if (sec_type.equals("DM_PW")) {
                  TDMPasswd passwd = WTCService.getWTCService().getTDMPasswd(ldomname, rdomname);
                  iv = WTCService.getPasswordKey();
                  keyStore = WTCService.getEncryptionType();
                  trustKSType = PasswordUtils.decryptPassword(iv, passwd.getLocalPasswordIV(), passwd.getLocalPassword(), keyStore);
                  trustKSLoc = PasswordUtils.decryptPassword(iv, passwd.getRemoteIV(), passwd.getRemotePassword(), keyStore);
                  if (trustKSType == null || trustKSLoc == null) {
                     this.myDsession = null;
                     WTCLogger.logErrorTDomainPassword(ldomname, rdomname);
                     if (traceEnabled) {
                        ntrace.doTrace("]/TDMRemoteTDomain/getTsession/60/null");
                     }

                     retVal = null;
                     throw new DoneException();
                  }

                  this.myDsession.setLocalPassword(trustKSType);
                  this.myDsession.setRemotePassword(trustKSLoc);
               } else if (sec_type.equals("APP_PW")) {
                  String passwd = WTCService.getAppPasswordPWD();
                  iv = WTCService.getAppPasswordIV();
                  keyStore = WTCService.getPasswordKey();
                  trustKSType = WTCService.getEncryptionType();
                  trustKSLoc = PasswordUtils.decryptPassword(keyStore, iv, passwd, trustKSType);
                  if (trustKSLoc == null) {
                     this.myDsession = null;
                     if (traceEnabled) {
                        ntrace.doTrace("]/TDMRemoteTDomain/getTsession/65/null");
                     }

                     retVal = null;
                     throw new DoneException();
                  }

                  this.myDsession.setApplicationPassword(trustKSLoc);
               }

               int eflags = 1;
               if (!ldom.getMBean().getUseSSL().equals("TwoWay") && !ldom.getMBean().getUseSSL().equals("OneWay")) {
                  eflags = TCLicenseManager.decideEncryptionLevel(20, this.MinEncryptionBits, this.MaxEncryptionBits);
               } else {
                  this.myDsession.setUseSSL(true);
                  if (ldom.getMBean().getKeyStoresLocation().equals("WLS Stores")) {
                     MBeanKeyStoreConfiguration ksConfig = MBeanKeyStoreConfiguration.getInstance();
                     keyStore = ksConfig.getKeyStores();
                     trustKSType = null;
                     trustKSLoc = null;
                     String trustKSPwd = null;
                     this.myDsession.setIdentityKeyStoreType(ksConfig.getCustomIdentityKeyStoreType());
                     this.myDsession.setIdentityKeyStore(ksConfig.getCustomIdentityKeyStoreFileName());
                     this.myDsession.setIdentityKeyStorePassphrase(ksConfig.getCustomIdentityKeyStorePassPhrase());
                     this.myDsession.setIdentityKeyAlias(ksConfig.getCustomIdentityAlias());
                     this.myDsession.setIdentityKeyPassphrase(ksConfig.getCustomIdentityPrivateKeyPassPhrase());
                     if ("CustomIdentityAndCustomTrust".equals(keyStore)) {
                        this.myDsession.setTrustKeyStoreType(ksConfig.getCustomTrustKeyStoreType());
                        this.myDsession.setTrustKeyStore(ksConfig.getCustomTrustKeyStoreFileName());
                        this.myDsession.setTrustKeyStorePassphrase(ksConfig.getCustomTrustKeyStorePassPhrase());
                     } else {
                        this.myDsession.setTrustKeyStoreType((String)null);
                        this.myDsession.setTrustKeyStore((String)null);
                        this.myDsession.setTrustKeyStorePassphrase((String)null);
                     }
                  } else {
                     this.myDsession.setIdentityKeyStoreType("jks");
                     this.myDsession.setIdentityKeyStore(ldom.getMBean().getIdentityKeyStoreFileName());
                     this.myDsession.setIdentityKeyStorePassphrase(ldom.getMBean().getIdentityKeyStorePassPhrase());
                     this.myDsession.setIdentityKeyAlias(ldom.getMBean().getPrivateKeyAlias());
                     this.myDsession.setIdentityKeyPassphrase(ldom.getMBean().getPrivateKeyPassPhrase());
                     this.myDsession.setTrustKeyStoreType("jks");
                     this.myDsession.setTrustKeyStore(ldom.getMBean().getTrustKeyStoreFileName());
                     this.myDsession.setTrustKeyStorePassphrase(ldom.getMBean().getTrustKeyStorePassPhrase());
                  }

                  this.myDsession.setMinEncryptBits(ldom.getMinEncryptBits());
                  this.myDsession.setMaxEncryptBits(ldom.getMaxEncryptBits());
               }

               this.myDsession.setEncryptionFlags(eflags);
               this.myDsession.setUseSDP(this.useSDP);
               TPINIT myinit = new TPINIT();
               myinit.usrname = ldom.getAccessPointId();
               this.myDsession.tpinit(myinit);
            } catch (TPException var32) {
               if (this.myDsession != null) {
                  this.myDsession._dom_drop();
                  this.myDsession = null;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/getTsession/70/null/" + var32);
               }

               if (var32.gettperrno() == 13) {
                  this.timedOut = true;
               }

               retVal = null;
               throw new DoneException();
            } catch (Exception var33) {
               if (this.myDsession != null) {
                  this.myDsession._dom_drop();
                  this.myDsession = null;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/getTsession/80/null/" + var33);
               }

               retVal = null;
               throw new DoneException();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/getTsession/90/" + this.myDsession);
            }

            retVal = this.myDsession;
            throw new DoneException();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getTsession/40/null");
         }

         retVal = null;
         throw new DoneException();
      } catch (DoneException var35) {
         if (traceEnabled) {
            ntrace.doTrace("/TDMRemoteTDomain/getTsession/100/" + retVal);
         }
      } finally {
         this.lock.unlock();
      }

      return retVal;
   }

   public void setTsession(gwatmi gsession) {
      this.myDsession = (gwdsession)gsession;
   }

   public int getCmpLimit() {
      this.r.lock();

      int var1;
      try {
         var1 = this.myCmpLimit;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setCmpLimit(int CmpLimit) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setCmpLimit/limit=" + CmpLimit);
      }

      if (CmpLimit >= 0 && CmpLimit <= Integer.MAX_VALUE) {
         this.w.lock();
         this.myCmpLimit = CmpLimit;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setCmpLimit/20/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setCmpLimit/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid compression limit value \"" + CmpLimit + "\" found in remote domain " + this.getAccessPointId());
      }
   }

   public synchronized int getMinEncryptBits() {
      this.r.lock();

      int var1;
      try {
         var1 = this.MinEncryptionBits;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMinEncryptBits(int MEBits) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setMinEncryptBits/numbits=" + MEBits);
      }

      if (MEBits != 0 && MEBits != 56 && MEBits != 40 && MEBits != 128) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setMinEncryptBits/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid MinEncryptionBits " + this.MinEncryptionBits + " found in remote domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.MinEncryptionBits = MEBits;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setMinEncryptBits/10/SUCCESS");
         }

      }
   }

   public int getMaxEncryptBits() {
      this.r.lock();

      int var1;
      try {
         var1 = this.MaxEncryptionBits;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMaxEncryptBits(int MEBits) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setMaxEncryptBits/numbits=" + MEBits);
      }

      if (MEBits != 0 && MEBits != 56 && MEBits != 40 && MEBits != 128) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setMaxEncryptBits/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid MaxEncryptionBits \"" + this.MaxEncryptionBits + "\" found in remote domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.MaxEncryptionBits = MEBits;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setMaxEncryptBits/10/SUCCESS");
         }

      }
   }

   public void setType(String Type) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (Type != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setType/type=" + Type);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setType/type=null");
         }
      }

      if (Type != null && Type.equals("TDOMAIN")) {
         super.setType(Type);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setType/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setType/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid Domain Type \"" + Type + "\" found in remote domain " + this.getAccessPointId());
      }
   }

   public void setAppKey(String AppKey) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (AppKey != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setAppKey/type=" + AppKey);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setAppKey/type=null");
         }
      }

      if (AppKey != null && !AppKey.equals("TpUsrFile") && !AppKey.equals("LDAP") && !AppKey.equals("Custom")) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setAppKey/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid AppKey generator plug-in Type \"" + AppKey + "\" found in remote domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.myAppKeyType = AppKey;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setAppKey/10/SUCCESS");
         }

      }
   }

   public String getAppKey() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getAppKey/");
      }

      this.r.lock();

      String var2;
      try {
         if (traceEnabled) {
            if (this.myAppKeyType != null) {
               ntrace.doTrace("]/TDMRemoteTDomain/getAppKey/10/" + this.myAppKeyType);
            } else {
               ntrace.doTrace("]/TDMRemoteTDomain/getAppKey/10/null");
            }
         }

         var2 = this.myAppKeyType;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setAllowAnonymous(boolean AllowAnonymous) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setAllowAnonymous/" + AllowAnonymous);
      }

      this.w.lock();
      this.myAllowAnonymous = AllowAnonymous;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setAllowAnonymous/10/SUCCESS");
      }

   }

   public boolean getAllowAnonymous() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getAllowAnonymous/");
      }

      this.r.lock();

      boolean var2;
      try {
         var2 = this.myAllowAnonymous;
      } finally {
         this.r.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getAllowAnonymous/10/" + this.myAllowAnonymous);
         }

      }

      return var2;
   }

   public boolean isAllowAnonymous() {
      return this.getAllowAnonymous();
   }

   public void setDefaultAppKey(int dfltAppKey) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setDefaultAppKey/" + dfltAppKey);
      }

      this.w.lock();
      this.myDfltAppKey = dfltAppKey;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setDefaultAppKey/10/");
      }

   }

   public int getDefaultAppKey() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getDefaultAppKey/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/getDefaultAppKey/10/" + this.myDfltAppKey);
      }

      this.r.lock();

      int var2;
      try {
         var2 = this.myDfltAppKey;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setTuxedoUidKw(String UidKw) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (UidKw != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setTuxedoUidKw/" + UidKw);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setTuxedoUidKw/null");
         }
      }

      this.w.lock();
      this.myUidKw = UidKw;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setTuxedoUidKw/10/");
      }

   }

   public String getTuxedoUidKw() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getTuxedoUidKw/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setTuxedoUidKw/10/" + this.myUidKw);
      }

      this.r.lock();

      String var2;
      try {
         var2 = this.myUidKw;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setTuxedoGidKw(String GidKw) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (GidKw != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setTuxedoGidKw/" + GidKw);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setTuxedoGidKw/null");
         }
      }

      this.w.lock();
      this.myGidKw = GidKw;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setTuxedoGidKw/10/");
      }

   }

   public String getTuxedoGidKw() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getTuxedoGidKw/");
         ntrace.doTrace("]/TDMRemoteTDomain/getTuxedoGidKw/10/" + this.myGidKw);
      }

      this.r.lock();

      String var2;
      try {
         var2 = this.myGidKw;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setCustomAppKeyClass(String AppKeyClass) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (AppKeyClass != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setCustomAppKey/" + AppKeyClass);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setCustomAppKey/null");
         }
      }

      this.w.lock();
      this.myAppKeyClass = AppKeyClass;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setCustomAppKey/10/");
      }

   }

   public String getCustomAppKeyClass() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getCustomAppKeyClass/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/getCustomAppKeyClass/10/" + this.myAppKeyClass);
      }

      this.r.lock();

      String var2;
      try {
         var2 = this.myAppKeyClass;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setCustomAppKeyClassParam(String Param) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (Param != null) {
            ntrace.doTrace("[/TDMRemoteTDomain/setCustomAppKeyClassParam/" + Param);
         } else {
            ntrace.doTrace("[/TDMRemoteTDomain/setCustomAppKeyClassParam/null");
         }
      }

      this.w.lock();
      this.myParam = Param;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/setCustomAppKeyClassParam/10/");
      }

   }

   void setKeepAlive(int ka) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setKeepAlive/ka =" + ka);
      }

      if (ka >= -1 && ka <= Integer.MAX_VALUE) {
         this.w.lock();
         this.keepAlive = ka;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setKeepAlive/20");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setKeepAlive/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid KeepAlive value \"" + ka + "\" found in local domain " + this.getAccessPointId());
      }
   }

   public int getKeepAlive() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getKeepAlive");
      }

      this.r.lock();

      try {
         int rv = this.keepAlive;
         if (rv == -2) {
            rv = 0;
         } else if (rv == -1) {
            TDMLocalTDomain ldom = (TDMLocalTDomain)this.getLocalAccessPointObject();
            int var4 = ldom.getKeepAlive();
            return var4;
         }

         int var3 = rv;
         return var3;
      } finally {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getKeepAlive/10");
         }

         this.r.unlock();
      }
   }

   void setKeepAliveWait(int kaw) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/setKeepAliveWait/kaw =" + kaw);
      }

      if (kaw >= 0 && kaw <= Integer.MAX_VALUE) {
         this.w.lock();
         this.keepAliveWait = kaw;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setKeepAliveWait/20");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/setKeepAliveWait/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid KeepAliveWait value \"" + kaw + "\" found in local domain " + this.getAccessPointId());
      }
   }

   public int getKeepAliveWait() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getKeepAliveWait");
      }

      this.r.lock();

      int var7;
      try {
         int rv;
         if (this.keepAlive == -1) {
            TDMLocalTDomain ldom = (TDMLocalTDomain)this.getLocalAccessPointObject();
            rv = ldom.getKeepAliveWait();
         } else if (this.keepAlive != -2 && this.keepAliveWait != -1) {
            rv = this.keepAliveWait;
         } else {
            rv = 0;
         }

         var7 = rv;
      } finally {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/getKeepAliveWait/10");
         }

         this.r.unlock();
      }

      return var7;
   }

   public void setMBean(WTCRemoteTuxDomMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/setMBname/MBeanName=" + mb.getName());
         }

         if (this.mBean != null) {
            if (this.mBean == mb) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/setMBname/no change");
               }

               return;
            }

            this.unregisterListener();
         }

         this.mBean = mb;
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setMBname/20/change & activated");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemoteTDomain/setMBname/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/setMBname/30/remove & deactivated");
         }
      }

   }

   public String getCustomAppKeyClassParam() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/getCustomAppKeyClassParam/");
         ntrace.doTrace("]/TDMRemoteTDomain/getCustomAppKeyClassParam/" + this.myParam);
      }

      this.r.lock();

      String var2;
      try {
         var2 = this.myParam;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public WTCRemoteTuxDomMBean getMBean() {
      return this.mBean;
   }

   public void onTerm(int term_type) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/onTerm/" + term_type);
      }

      switch (term_type) {
         case 0:
         case 2:
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/onTerm/10");
            }

            return;
         case 1:
         case 3:
            String connection_policy;
            if ((connection_policy = this.getConnectionPolicy()) != null && connection_policy.equals("ON_STARTUP")) {
               if (term_type == 1) {
                  this.outboundConnect(false);
               } else if (term_type != 4) {
                  this.outboundConnect(true);
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/onTerm/30");
               }

            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/onTerm/20");
               }

            }
      }
   }

   private void outboundConnect(boolean delay) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/outboundConnect/" + this.getAccessPointId());
      }

      if (this.getTsession(false) != null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/10");
         }

      } else if (this.connectingTask != null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/20");
         }

      } else {
         long retriesLeft;
         String connectionPolicy;
         if ((retriesLeft = this.getMaxRetries()) < 0L) {
            connectionPolicy = null;
            if ((connectionPolicy = this.getConnectionPolicy()) != null && !connectionPolicy.equals("ON_STARTUP")) {
               this.connectingTask = new ScheduledReconnect(this, 0L);

               try {
                  this.myTimeService.schedule(this.connectingTask, 0L);
               } catch (IllegalArgumentException var9) {
                  WTCLogger.logTTEstdSchedule(var9.getMessage());
                  if (traceEnabled) {
                     ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/30");
                  }

                  return;
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/40");
            }

         } else {
            long retryInterval = this.getRetryInterval() * 1000L;
            connectionPolicy = this.getConnectionPolicy();
            if (connectionPolicy != null && connectionPolicy.equals("ON_DEMAND")) {
               this.getTsession(true);
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/45");
               }

            } else {
               this.connectingTask = new ScheduledReconnect(this, retriesLeft);

               try {
                  if (delay) {
                     this.myTimeService.scheduleAtFixedRate(this.connectingTask, retryInterval, retryInterval);
                  } else {
                     this.myTimeService.scheduleAtFixedRate(this.connectingTask, 0L, retryInterval);
                  }
               } catch (IllegalArgumentException var10) {
                  WTCLogger.logTTEstdSchedule(var10.getMessage());
                  if (traceEnabled) {
                     ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/50");
                  }

                  return;
               } catch (IllegalStateException var11) {
                  WTCLogger.logTTEstdSchedule(var11.getMessage());
                  if (traceEnabled) {
                     ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/60");
                  }

                  return;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/outboundConnect/70");
               }

            }
         }
      }
   }

   public void startConnection() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/startConnection/");
      }

      String connection_policy;
      if ((connection_policy = this.getConnectionPolicy()) != null && !connection_policy.equals("INCOMING_ONLY")) {
         this.outboundConnect(false);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/startConnection/DONE");
         }

      } else {
         Loggable l = WTCLogger.logErrorIncomingOnlyLoggable(this.getAccessPointId());
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/startConnection/10/INCOMING_ONLY");
         }

         throw new TPException(12, l.getMessage());
      }
   }

   public void checkConfigIntegrity() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/checkConfigIntegrity/rdom=" + this.getAccessPointId());
      }

      this.r.lock();
      if (this.MinEncryptionBits > this.MaxEncryptionBits) {
         this.r.unlock();
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Remote", this.getAccessPointId());
         le.log();
         throw new TPException(4, le.getMessage());
      } else {
         this.r.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemoteTDomain/checkConfigIntegrity/20/true");
         }

      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCRemoteTuxDomMBean newBean = (WTCRemoteTuxDomMBean)event.getProposedBean();
      String tmpNWAddr = null;
      String tmpFederationURL = null;
      String tmpFederationName = null;
      int tmpCmpLimit = this.myCmpLimit;
      int tmpMinEncryptBits = this.MinEncryptionBits;
      int tmpMaxEncryptBits = this.MaxEncryptionBits;
      String tmpAppKey = null;
      boolean tmpAllowAnon = this.myAllowAnonymous;
      int tmpDfltAppKey = this.myDfltAppKey;
      String tmpUidKw = null;
      String tmpGidKw = null;
      String tmpAppKeyClass = null;
      String tmpParam = null;
      String tmpLAP = null;
      String tmpAP = null;
      String tmpAPId = null;
      String tmpAclPolicy = null;
      String tmpCredPolicy = null;
      String tmpConnPrincipal = null;
      String tmpTpUsrFile = null;
      String tmpConnPolicy = null;
      long tmpMaxRetries = -1L;
      long tmpRetryInterval = -1L;
      int tmpKeepAlive = -1;
      int tmpKeepAliveWait = -1;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMRemoteTuxDom!");
      } else {
         String key;
         for(int i = 0; i < updates.length; ++i) {
            key = updates[i].getPropertyName();
            int opType = updates[i].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + i + ", optype = " + opType + ", key = " + key);
            }

            if (opType == 1) {
               if (key.equals("AccessPointId")) {
                  tmpAPId = newBean.getAccessPointId();
                  if (traceEnabled) {
                     ntrace.doTrace("AccessPointId: " + tmpAPId);
                  }
               } else if (key.equals("AccessPoint")) {
                  tmpAP = newBean.getAccessPoint();
                  if (traceEnabled) {
                     ntrace.doTrace("AccessPoint: " + tmpAP);
                  }
               } else if (key.equals("NWAddr")) {
                  tmpNWAddr = newBean.getNWAddr();
                  if (traceEnabled) {
                     ntrace.doTrace("NWAddr: " + tmpNWAddr);
                  }
               } else if (key.equals("LocalAccessPoint")) {
                  tmpLAP = newBean.getLocalAccessPoint();
                  if (traceEnabled) {
                     ntrace.doTrace("LocalAccessPoint: " + tmpLAP);
                  }
               } else if (key.equals("ConnectionPolicy")) {
                  tmpConnPolicy = newBean.getConnectionPolicy();
                  if (traceEnabled) {
                     ntrace.doTrace("ConnectionPolicy: " + tmpConnPolicy);
                  }
               } else if (key.equals("AclPolicy")) {
                  tmpAclPolicy = newBean.getAclPolicy();
                  if (traceEnabled) {
                     ntrace.doTrace("AclPolicy: " + tmpAclPolicy);
                  }
               } else if (key.equals("CredentialPolicy")) {
                  tmpCredPolicy = newBean.getCredentialPolicy();
                  if (traceEnabled) {
                     ntrace.doTrace("CredentialPolicy: " + tmpCredPolicy);
                  }
               } else if (key.equals("TpUsrFile")) {
                  tmpTpUsrFile = newBean.getTpUsrFile();
                  if (traceEnabled) {
                     ntrace.doTrace("TpUsrFile: " + tmpTpUsrFile);
                  }
               } else if (key.equals("AppKey")) {
                  tmpAppKey = newBean.getAppKey();
                  if (traceEnabled) {
                     ntrace.doTrace("AppKey: " + tmpAppKey);
                  }
               } else if (key.equals("DefaultAppKey")) {
                  tmpDfltAppKey = newBean.getDefaultAppKey();
                  if (traceEnabled) {
                     ntrace.doTrace("DefaultAppKey: " + tmpDfltAppKey);
                  }
               } else if (key.equals("AllowAnonymous")) {
                  tmpAllowAnon = newBean.getAllowAnonymous();
                  if (traceEnabled) {
                     ntrace.doTrace("AllowAnonymous: " + tmpAllowAnon);
                  }
               } else if (key.equals("TuxedoUidKw")) {
                  tmpUidKw = newBean.getTuxedoUidKw();
                  if (traceEnabled) {
                     ntrace.doTrace("TuxedoUidKw: " + tmpUidKw);
                  }
               } else if (key.equals("TuxedoGidKw")) {
                  tmpGidKw = newBean.getTuxedoGidKw();
                  if (traceEnabled) {
                     ntrace.doTrace("TuxedoGidKw: " + tmpGidKw);
                  }
               } else if (key.equals("CustomAppKeyClass")) {
                  tmpAppKeyClass = newBean.getCustomAppKeyClass();
                  if (traceEnabled) {
                     ntrace.doTrace("CustomAppKeyClass: " + tmpAppKeyClass);
                  }
               } else if (key.equals("CustomAppKeyClassParam")) {
                  tmpParam = newBean.getCustomAppKeyClassParam();
                  if (traceEnabled) {
                     ntrace.doTrace("CustomAppKeyClassParam: " + tmpParam);
                  }
               } else if (key.equals("FederationURL")) {
                  tmpFederationURL = newBean.getFederationURL();
                  if (traceEnabled) {
                     ntrace.doTrace("FederationURL: " + tmpFederationURL);
                  }
               } else if (key.equals("FederationName")) {
                  tmpFederationName = newBean.getFederationName();
                  if (traceEnabled) {
                     ntrace.doTrace("FederationName: " + tmpFederationName);
                  }
               } else if (key.equals("RetryInterval")) {
                  tmpRetryInterval = newBean.getRetryInterval();
                  if (traceEnabled) {
                     ntrace.doTrace("ConnectionPolicy: " + tmpConnPolicy);
                  }
               } else if (key.equals("MaxRetries")) {
                  tmpMaxRetries = newBean.getMaxRetries();
                  if (traceEnabled) {
                     ntrace.doTrace("MaxRetries: " + tmpMaxRetries);
                  }
               } else if (key.equals("CmpLimit")) {
                  tmpCmpLimit = newBean.getCmpLimit();
                  if (traceEnabled) {
                     ntrace.doTrace("CmpLimit: " + tmpCmpLimit);
                  }
               } else {
                  String tmpStr;
                  if (key.equals("MinEncryptBits")) {
                     tmpStr = newBean.getMinEncryptBits();
                     tmpMinEncryptBits = Integer.parseInt(tmpStr, 10);
                     if (traceEnabled) {
                        ntrace.doTrace("MinEncryptBits: " + tmpMinEncryptBits);
                     }
                  } else if (key.equals("MaxEncryptBits")) {
                     tmpStr = newBean.getMaxEncryptBits();
                     tmpMaxEncryptBits = Integer.parseInt(tmpStr, 10);
                     if (traceEnabled) {
                        ntrace.doTrace("MaxEncryptBits: " + tmpMaxEncryptBits);
                     }
                  } else if (key.equals("KeepAlive")) {
                     tmpKeepAlive = newBean.getKeepAlive();
                     if (traceEnabled) {
                        ntrace.doTrace("KeepAlive: " + tmpKeepAlive);
                     }
                  } else if (key.equals("KeepAliveWait")) {
                     tmpKeepAliveWait = newBean.getKeepAliveWait();
                     if (traceEnabled) {
                        ntrace.doTrace("KeepAliveWait: " + tmpKeepAliveWait);
                     }
                  }
               }
            } else if (opType == 2) {
               if (traceEnabled) {
                  ntrace.doTrace("Unexpected ADD operation, ignored!");
               }
            } else {
               if (opType != 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/20/Unknown operation " + opType);
                  }

                  throw new BeanUpdateRejectedException("Unknown operation(" + opType + ") for TDMResources.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("Unexpected REMOVE operation, ignored!");
               }
            }
         }

         WTCService myWTC = WTCService.getWTCService();
         if (tmpAP != null && !tmpAP.equals(this.getLocalAccessPoint()) && myWTC.getLocalDomain(tmpAP) == null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/30/LAP (" + tmpLAP + ") not defined!");
            }

            throw new BeanUpdateRejectedException("Local Access Point " + tmpLAP + " is not configured.");
         } else {
            key = tmpLAP == null ? this.getLocalAccessPoint() : tmpLAP;
            if (tmpLAP != null || tmpAP != null) {
               TDMRemoteTDomain rdom = myWTC.getVTDomainSession(key, tmpAP);
               if (rdom != null && rdom != this) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/40/duplicate TDomain session(" + tmpLAP + ", " + tmpAP + ").");
                  }

                  throw new BeanUpdateRejectedException("Duplicate TDomain Session(" + tmpLAP + ", " + tmpAP + ").");
               }
            }

            if (tmpMinEncryptBits != this.MinEncryptionBits && tmpMinEncryptBits != 0 && tmpMinEncryptBits != 40 && tmpMinEncryptBits != 56 && tmpMinEncryptBits != 128) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/50/invalid MinEncryptBits value(" + tmpMinEncryptBits + ").");
               }

               throw new BeanUpdateRejectedException("Invalid MinEncryptBits Value: " + tmpMinEncryptBits);
            } else if (tmpMaxEncryptBits != this.MaxEncryptionBits && tmpMaxEncryptBits != 0 && tmpMaxEncryptBits != 40 && tmpMaxEncryptBits != 56 && tmpMaxEncryptBits != 128) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/60/invalid MinEncryptBits value(" + tmpMinEncryptBits + ").");
               }

               throw new BeanUpdateRejectedException("Invalid MaxEncryptBits Value: " + tmpMaxEncryptBits);
            } else if (tmpMaxEncryptBits < tmpMinEncryptBits) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/70/MinEncryptBits value(" + tmpMinEncryptBits + ") greater than MaxEncryptBits value(" + tmpMaxEncryptBits + ").");
               }

               throw new BeanUpdateRejectedException("MinEncryptBits value greater than MaxEncryptBits value for RemoteTuxDom " + tmpAP == null ? this.getAccessPoint() : tmpAP);
            } else if (tmpConnPolicy != null && !tmpConnPolicy.equals("LOCAL") && !tmpConnPolicy.equals("ON_DEMAND") && !tmpConnPolicy.equals("ON_STARTUP") && !tmpConnPolicy.equals("INCOMING_ONLY")) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/80/Invalid Connection Policy value: " + tmpConnPolicy);
               }

               throw new BeanUpdateRejectedException("Invalid Connection Policy Value: " + tmpConnPolicy);
            } else if (tmpAclPolicy != null && !tmpAclPolicy.equals("LOCAL") && !tmpAclPolicy.equals("GLOBAL")) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/90/Invalid ACL Policy value: " + tmpAclPolicy);
               }

               throw new BeanUpdateRejectedException("Invalid ACL Policy Value: " + tmpAclPolicy);
            } else if (tmpCredPolicy != null && !tmpCredPolicy.equals("LOCAL") && !tmpCredPolicy.equals("GLOBAL")) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/100/Invalid Credential Policy value: " + tmpCredPolicy);
               }

               throw new BeanUpdateRejectedException("Invalid Credential Policy Value: " + tmpCredPolicy);
            } else {
               if (tmpNWAddr != null) {
                  try {
                     this.parseNWAddr(tmpNWAddr, true);
                  } catch (TPException var36) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/110/invalid NWAddr format(" + tmpNWAddr + ").");
                     }

                     throw new BeanUpdateRejectedException(var36.getMessage());
                  }
               }

               try {
                  if (tmpAP != null) {
                     this.setAccessPoint(tmpAP);
                  }

                  if (tmpLAP != null) {
                     this.setLocalAccessPoint(tmpLAP);
                  }

                  if (tmpAPId != null) {
                     this.setAccessPointId(tmpAPId);
                  }

                  if (tmpAclPolicy != null) {
                     this.setAclPolicy(tmpAclPolicy);
                  }

                  if (tmpCredPolicy != null) {
                     this.setCredentialPolicy(tmpCredPolicy);
                  }

                  if (tmpTpUsrFile != null) {
                     this.setTpUsrFile(tmpTpUsrFile);
                  }

                  if (tmpFederationURL != null) {
                     this.setFederationURL(tmpFederationURL);
                  }

                  if (tmpFederationName != null) {
                     this.setFederationName(tmpFederationName);
                  }

                  if (tmpCmpLimit != this.myCmpLimit) {
                     this.setCmpLimit(tmpCmpLimit);
                  }

                  if (tmpMinEncryptBits != this.MinEncryptionBits) {
                     this.setMinEncryptBits(tmpMinEncryptBits);
                  }

                  if (tmpMaxEncryptBits != this.MaxEncryptionBits) {
                     this.setMaxEncryptBits(tmpMaxEncryptBits);
                  }

                  if (tmpConnPolicy != null) {
                     this.setConnectionPolicy(tmpConnPolicy);
                  }

                  if (tmpRetryInterval != -1L) {
                     this.setRetryInterval(tmpRetryInterval);
                  }

                  if (tmpMaxRetries != -1L) {
                     this.setMaxRetries(tmpMaxRetries);
                  }

                  if (tmpAppKey != null) {
                     this.setAppKey(tmpAppKey);
                  }

                  if (tmpAllowAnon != this.myAllowAnonymous) {
                     this.setAllowAnonymous(tmpAllowAnon);
                  }

                  if (tmpDfltAppKey != this.myDfltAppKey) {
                     this.setDefaultAppKey(tmpDfltAppKey);
                  }

                  if (tmpUidKw != null) {
                     this.setTuxedoUidKw(tmpUidKw);
                  }

                  if (tmpGidKw != null) {
                     this.setTuxedoGidKw(tmpGidKw);
                  }

                  if (tmpAppKeyClass != null) {
                     this.setCustomAppKeyClass(tmpAppKeyClass);
                  }

                  if (tmpParam != null) {
                     this.setCustomAppKeyClassParam(tmpParam);
                  }

                  if (tmpKeepAlive != -1) {
                     this.setKeepAlive(tmpKeepAlive);
                     if (this.myDsession != null) {
                        this.myDsession.setKeepAlive(this.getKeepAlive());
                     }
                  }

                  if (tmpKeepAliveWait != -1) {
                     this.setKeepAliveWait(tmpKeepAliveWait);
                     if (this.myDsession != null) {
                        this.myDsession.setKeepAliveWait(this.getKeepAliveWait());
                     }
                  }
               } catch (TPException var37) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMRemoteTDomain/prepareUpdate/120/change failed for RemoteTuxDom: " + this.getAccessPoint());
                  }

                  throw new BeanUpdateRejectedException(var37.getMessage());
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemoteTDomain/prepareUpdate/130/DONE");
               }

            }
         }
      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/activateUpdate");
         ntrace.doTrace("]/TDMRemoteTDomain/activateUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/rollbackUpdate");
         ntrace.doTrace("]/TDMRemoteTDomain/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMRemoteTDomain: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMRemoteTD: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/unregisterListener/10/DONE");
      }

   }

   public void removeConnectingTaskReference() {
      this.connectingTask = null;
   }

   public boolean hasConnectingTask() {
      return this.connectingTask != null;
   }

   public void terminateConnectingTask() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/terminateConnectingTask/0");
      }

      if (this.connectingTask != null) {
         this.connectingTask.connectingTerminate();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemoteTDomain/terminateConnectingTask/10/DONE");
      }

   }

   public void setNetworkAddr(String[] addr) {
   }

   public String[] getNetworkAddr() {
      return this.myNWAddr;
   }

   private final class TimedMutex {
      private boolean locked = false;

      public synchronized boolean acquire(long timeout) {
         if (timeout == 0L) {
            while(this.locked) {
               try {
                  this.wait();
               } catch (InterruptedException var5) {
                  this.notify();
               }
            }
         } else if (this.locked) {
            try {
               this.wait(timeout);
            } catch (InterruptedException var4) {
               this.notify();
            }

            if (this.locked) {
               return false;
            }
         }

         this.locked = true;
         return true;
      }

      public synchronized boolean attempt() {
         if (this.locked) {
            return false;
         } else {
            this.locked = true;
            return true;
         }
      }

      public synchronized void release() {
         this.locked = false;
         this.notify();
      }

      public boolean isLocked() {
         return this.locked;
      }
   }

   private class DoneException extends Exception {
      static final long serialVersionUID = -6282186224051838441L;

      private DoneException() {
      }

      // $FF: synthetic method
      DoneException(Object x1) {
         this();
      }
   }
}
