package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.ArrayList;
import java.util.StringTokenizer;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCLocalTuxDomMBean;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.config.WTCLocalAccessPoint;
import weblogic.wtc.jatmi.TPException;

public class TDMLocalTDomain extends TDMLocal implements BeanUpdateListener {
   static final long serialVersionUID = -8456049645166331478L;
   private String[] myNWAddr;
   private String[] ipaddress;
   private int[] port;
   private int myCmpLimit;
   private int MinEncryptionBits;
   private int MaxEncryptionBits;
   private boolean myInteroperate;
   private WTCLocalTuxDomMBean mBean = null;
   private OatmialListener oatmialListener = null;
   private boolean registered = false;
   private int keepAlive = -1;
   private int keepAliveWait;
   private String origNWAddr;
   private boolean isSSL = false;
   private boolean[] useSDP;

   public TDMLocalTDomain(String AccessPoint) throws Exception {
      super(AccessPoint);
      super.setType("TDOMAIN");
      this.setSecurity("NONE");
      this.setConnectionPolicy("ON_DEMAND");
      this.setCmpLimit(Integer.MAX_VALUE);
      this.setMinEncryptBits(0);
      this.setMaxEncryptBits(128);
      this.setInteroperate("No");
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
            if (idx < this.myNWAddr.length) {
               var2 = this.myNWAddr[idx];
               return var2;
            }

            var2 = null;
         } finally {
            this.r.unlock();
         }

         return var2;
      }
   }

   public void setNWAddr(String NWAddr) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (NWAddr != null) {
            ntrace.doTrace("[/TDMLocalTDomain/setNWAddr/addr=" + NWAddr);
         } else {
            ntrace.doTrace("[/TDMLocalTDomain/setNWAddr/addr=null");
         }
      }

      if (NWAddr == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setNWAddr/10/TPEINVAL");
         }

         throw new TPException(4, "null NWAddr found in local domain " + this.getAccessPointId());
      } else if (!NWAddr.startsWith("//") && !NWAddr.toLowerCase().startsWith("sdp://")) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setNWAddr/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid NWAddr format \"" + NWAddr + "\" found in local domain " + this.getAccessPointId());
      } else {
         StringTokenizer tok_nwaddr = new StringTokenizer(NWAddr, ",");
         int count = tok_nwaddr.countTokens();
         String[] tok = new String[count];
         String[] ipa = new String[count];
         int[] port_a = new int[count];
         boolean[] tmp_useSDP = new boolean[count];

         for(int i = 0; i < count; ++i) {
            tok[i] = tok_nwaddr.nextToken();
            if (!TDMRemoteTDomain.isNWAddrFormat(tok[i])) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/setNWAddr/30/TPEINVAL");
               }

               throw new TPException(4, "Invalid NWAddr format \"" + NWAddr + "\" found in local domain " + this.getAccessPointId());
            }

            tmp_useSDP[i] = false;
            if (tok[i].toLowerCase().startsWith("sdp://")) {
               tok[i] = tok[i].substring(4);
               tmp_useSDP[i] = true;
            }

            int colon_index = tok[i].indexOf(58);
            ipa[i] = tok[i].substring(2, colon_index);
            String port_address = tok[i].substring(colon_index + 1);
            port_a[i] = Integer.parseInt(port_address, 10);
         }

         this.w.lock();
         this.myNWAddr = tok;
         this.ipaddress = ipa;
         this.port = port_a;
         this.origNWAddr = NWAddr;
         this.useSDP = tmp_useSDP;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setNWAddr/40/SUCCESS");
         }

      }
   }

   public String[] get_ipaddress() {
      this.r.lock();

      String[] var1;
      try {
         var1 = this.ipaddress;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public int[] get_port() {
      this.r.lock();

      int[] var1;
      try {
         var1 = this.port;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public WTCLocalTuxDomMBean getMBean() {
      return this.mBean;
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
         ntrace.doTrace("[/TDMLocalTDomain/setCmpLimit/limit=" + CmpLimit);
      }

      if (CmpLimit >= 0 && CmpLimit <= Integer.MAX_VALUE) {
         this.w.lock();
         this.myCmpLimit = CmpLimit;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setCmpLimit/20/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setCmpLimit/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid compression limit value " + CmpLimit + " found in local domain " + this.getAccessPointId());
      }
   }

   public int getMinEncryptBits() {
      this.r.lock();

      int var1;
      try {
         if (this.MinEncryptionBits >= 256 && !this.isSSL) {
            short var5 = 128;
            return var5;
         }

         var1 = this.MinEncryptionBits;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMinEncryptBits(int bits) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/setMinEncryptBits/bits=" + bits);
      }

      if (bits != 0 && bits != 40 && bits != 56 && bits != 112 && bits != 128 && bits != 256) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setMinEncryptBits/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid MIN Encryption bit value " + this.MinEncryptionBits + " found in local domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.MinEncryptionBits = bits;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setMinEncryptBits/5/SUCCESS");
         }

      }
   }

   public int getMaxEncryptBits() {
      this.r.lock();

      int var1;
      try {
         if (this.MaxEncryptionBits >= 256 && !this.isSSL) {
            short var5 = 128;
            return var5;
         }

         var1 = this.MaxEncryptionBits;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMaxEncryptBits(int bits) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/setMaxEncryptBits/bits=" + bits);
      }

      if (bits != 0 && bits != 40 && bits != 56 && bits != 112 && bits != 128 && bits != 256) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setMaxEncryptBits/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid MAX Encryption bit value " + this.MaxEncryptionBits + " found in local domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.MaxEncryptionBits = bits;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setMaxEncryptBits/5/SUCCESS");
         }

      }
   }

   public String getInteroperate() {
      return this.myInteroperate ? "YES" : "NO";
   }

   public boolean isInteroperate() {
      this.r.lock();

      boolean var1;
      try {
         var1 = this.myInteroperate;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setInteroperate(String v) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (v != null) {
            ntrace.doTrace("[/TDMLocalTDomain/setInteroperate/interop=" + v);
         } else {
            ntrace.doTrace("[/TDMLocalTDomain/setInteroperate/interop=null");
         }
      }

      if (v != null) {
         if (v.compareToIgnoreCase("Yes") == 0) {
            this.w.lock();
            this.myInteroperate = true;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMLocalTDomain/setInteroperate/5/SUCCESS");
            }

            return;
         }

         if (v.compareToIgnoreCase("No") == 0) {
            this.w.lock();
            this.myInteroperate = false;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMLocalTDomain/setInteroperate/10/SUCCESS");
            }

            return;
         }
      }

      if (v != null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setInteroperate/15/TPEINVAL");
         }

         throw new TPException(4, "Invalid Interoperate value specified \"" + v + "\" found in local domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setInteroperate/20/SUCCESS");
         }

      }
   }

   public void setType(String Type) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (Type != null) {
            ntrace.doTrace("[/TDMLocalTDomain/setType/type=" + Type);
         } else {
            ntrace.doTrace("[/TDMLocalTDomain/setType/type=null");
         }
      }

      if (Type != null && Type.equals("TDOMAIN")) {
         super.setType(Type);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setType/5/SUCCESS");
         }

      } else if (Type != null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setType/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid Domain type \"" + Type + "\" found in local domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setType/20/SUCCESS");
         }

      }
   }

   public void setKeepAlive(int ka) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/setKeepAlive/ka =" + ka);
      }

      if (ka >= 0 && ka <= Integer.MAX_VALUE) {
         this.w.lock();
         this.keepAlive = ka;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setKeepAlive/20");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setKeepAlive/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid KeepAlive value \"" + ka + "\" found in local domain " + this.getAccessPointId());
      }
   }

   public int getKeepAlive() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/getKeepAlive");
      }

      this.r.lock();

      int var3;
      try {
         int rv = this.keepAlive;
         if (rv == -1) {
            rv = 0;
         }

         var3 = rv;
      } finally {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/getKeepAlive/10");
         }

         this.r.unlock();
      }

      return var3;
   }

   public void setKeepAliveWait(int kaw) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/setKeepAliveWait/kaw =" + kaw);
      }

      if (kaw >= 0 && kaw <= Integer.MAX_VALUE) {
         this.w.lock();
         this.keepAliveWait = kaw;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/setKeepAliveWait/20");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/setKeepAliveWait/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid KeepAliveWait value \"" + kaw + "\" found in local domain " + this.getAccessPointId());
      }
   }

   public int getKeepAliveWait() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/getKeepAliveWait");
      }

      this.r.lock();

      int var3;
      try {
         int rv;
         if (this.keepAlive != -1 && this.keepAliveWait != -1) {
            rv = this.keepAliveWait;
         } else {
            rv = 0;
         }

         var3 = rv;
      } finally {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/getKeepAliveWait/10");
         }

         this.r.unlock();
      }

      return var3;
   }

   public boolean isUseSSL() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/isUseSSL");
      }

      this.r.lock();

      boolean var2;
      try {
         var2 = this.isSSL;
      } finally {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/isUseSSL/10");
         }

         this.r.unlock();
      }

      return var2;
   }

   public void setMBean(WTCLocalTuxDomMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocalTDomain/setMBname/MBeanName=" + mb.getName());
         }

         if (this.mBean != null) {
            if (this.mBean == mb) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMLocalTDomain/setMBname/10/no change");
               }

               return;
            }

            this.unregisterListener();
         }

         this.mBean = mb;
         String useSSL = mb.getUseSSL();
         if (useSSL.equals("TwoWay") || useSSL.equals("OneWay")) {
            this.isSSL = true;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocalTDomain/setMBname/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }

         this.isSSL = false;
      }

      if (!this.isSSL && (this.MinEncryptionBits >= 256 || this.MaxEncryptionBits >= 256)) {
         WTCLogger.logInfoLLEEncryptBitsDowngrade(this.getAccessPointId());
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocalTDomain/setMBname/20/DONE");
      }

   }

   public void checkConfigIntegrity() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/checkConfigIntegrity/ldom=" + this.getAccessPointId());
      }

      if (this.MinEncryptionBits > this.MaxEncryptionBits) {
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Local", this.getAccessPointId());
         le.log();
         throw new TPException(4, le.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocalTDomain/checkConfigIntegrity/20/true");
         }

      }
   }

   public void setOatmialListener(OatmialListener lst) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/setOatmialListener/10/BEGIN");
      }

      this.oatmialListener = lst;
      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocalTDomain/setOatmialListener/20/DONE");
      }

   }

   public OatmialListener getOatmialListener() {
      return this.oatmialListener;
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

   public boolean hasActiveTsession() {
      TDMRemoteTDomain[] myRemoteAccessPointList = (TDMRemoteTDomain[])((TDMRemoteTDomain[])this.get_remote_domains());

      for(int i = 0; i < myRemoteAccessPointList.length; ++i) {
         if (myRemoteAccessPointList[i].getTsession(false) != null) {
            return true;
         }
      }

      return false;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCLocalTuxDomMBean newBean = (WTCLocalTuxDomMBean)event.getProposedBean();
      String tmpNWAddr = null;
      String tmpAP = null;
      String tmpAPId = null;
      String tmpSecurity = null;
      String tmpConnPolicy = null;
      long tmpMaxRetries = -1L;
      long tmpRetryInterval = -1L;
      String tmpConnPrincipalName = null;
      long tmpBlockTime = -1L;
      int tmpCmpLimit = -1;
      int tmpMinEncryptBits = -1;
      int tmpMaxEncryptBits = -1;
      int tmpKeepAlive = -1;
      int tmpKeepAliveWait = -1;
      String tmpInteroperate = null;
      String tmpUseSSL = null;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMLocalTuxDom!");
      } else {
         int minEnc;
         for(minEnc = 0; minEnc < updates.length; ++minEnc) {
            String key = updates[minEnc].getPropertyName();
            int opType = updates[minEnc].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + minEnc + ", optype = " + opType + ", key = " + key);
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
               } else if (key.equals("Security")) {
                  tmpSecurity = newBean.getSecurity();
                  if (traceEnabled) {
                     ntrace.doTrace("Security: " + tmpSecurity);
                  }
               } else if (key.equals("ConnectionPolicy")) {
                  tmpConnPolicy = newBean.getConnectionPolicy();
                  if (traceEnabled) {
                     ntrace.doTrace("ConnectionPolicy: " + tmpConnPolicy);
                  }
               } else if (key.equals("Interoperate")) {
                  tmpInteroperate = newBean.getInteroperate();
                  if (traceEnabled) {
                     ntrace.doTrace("Interoperate: " + tmpInteroperate);
                  }
               } else if (key.equals("RetryInterval")) {
                  tmpRetryInterval = newBean.getRetryInterval();
                  if (traceEnabled) {
                     ntrace.doTrace("RetryInterval: " + tmpRetryInterval);
                  }
               } else if (key.equals("MaxRetries")) {
                  tmpMaxRetries = newBean.getMaxRetries();
                  if (traceEnabled) {
                     ntrace.doTrace("MaxRetries: " + tmpMaxRetries);
                  }
               } else if (key.equals("BlockTime")) {
                  tmpBlockTime = newBean.getBlockTime();
                  if (traceEnabled) {
                     ntrace.doTrace("BlockTime: " + tmpBlockTime);
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
                     tmpKeepAlive = newBean.getKeepAliveWait();
                     if (traceEnabled) {
                        ntrace.doTrace("KeepAliveWait: " + tmpKeepAliveWait);
                     }
                  } else if (key.equals("UseSSL")) {
                     tmpUseSSL = newBean.getUseSSL();
                     if (traceEnabled) {
                        ntrace.doTrace("UseSSL: " + tmpUseSSL);
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

         if (tmpAP != null && !this.getAccessPoint().equals(tmpAP)) {
            TDMLocal lapObj = WTCService.getWTCService().getLocalDomain(tmpAP);
            if (lapObj != null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/30/LAP  " + tmpAP + " already exists!");
               }

               throw new BeanUpdateRejectedException("LocalTuxDom" + tmpAP + " already exists!");
            }
         }

         if (tmpUseSSL != null) {
            if (tmpUseSSL.equals("Off")) {
               this.isSSL = false;
            } else {
               this.isSSL = true;
            }
         }

         if (tmpMinEncryptBits != -1 && tmpMinEncryptBits != this.MinEncryptionBits) {
            if (tmpMinEncryptBits != 0 && tmpMinEncryptBits != 40 && tmpMinEncryptBits != 56 && tmpMinEncryptBits != 128 && tmpMinEncryptBits != 256) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/40/Invalid MinEncryptionBits " + tmpMinEncryptBits);
               }

               throw new BeanUpdateRejectedException("Invalid MinEncryptionBits value:" + tmpMinEncryptBits);
            }

            minEnc = tmpMinEncryptBits;
         } else {
            minEnc = this.MinEncryptionBits;
         }

         int maxEnc;
         if (tmpMaxEncryptBits != -1 && tmpMaxEncryptBits != this.MaxEncryptionBits) {
            if (tmpMaxEncryptBits != 0 && tmpMaxEncryptBits != 40 && tmpMaxEncryptBits != 56 && tmpMaxEncryptBits != 128 && tmpMaxEncryptBits != 256) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/50/Invalid MaxEncryptionBits " + tmpMaxEncryptBits);
               }

               throw new BeanUpdateRejectedException("Invalid MaxEncryptionBits value:" + tmpMaxEncryptBits);
            }

            maxEnc = tmpMaxEncryptBits;
         } else {
            maxEnc = this.MaxEncryptionBits;
         }

         if ((tmpMaxEncryptBits != -1 || tmpMinEncryptBits != -1) && maxEnc < minEnc) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/60/MaxEncryptionBits (" + maxEnc + ") less than MinEncryptionBits (" + minEnc + " )");
            }

            throw new BeanUpdateRejectedException("MaxEncryptionBits value less than MinEncryptionBits value");
         } else if (tmpSecurity != null && !tmpSecurity.equals("NONE") && !tmpSecurity.equals("APP_PW") && !tmpSecurity.equals("DM_PW")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/70/Invalid security value  " + tmpSecurity);
            }

            throw new BeanUpdateRejectedException("Invalid security value: " + tmpSecurity);
         } else if (tmpConnPolicy != null && !tmpConnPolicy.equals("ON_DEMAND") && !tmpConnPolicy.equals("ON_STARTUP") && !tmpConnPolicy.equals("INCOMING_ONLY")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/80/Invalid connection policy " + tmpConnPolicy);
            }

            throw new BeanUpdateRejectedException("Invalid connection policy: " + tmpConnPolicy);
         } else if (tmpInteroperate != null && tmpInteroperate.compareToIgnoreCase("Yes") == 0 && tmpInteroperate.compareToIgnoreCase("No") == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/90/Invalid interoperate value " + tmpInteroperate);
            }

            throw new BeanUpdateRejectedException("Invalid interoperate value: " + tmpInteroperate);
         } else {
            try {
               if (tmpAP != null) {
                  this.setAccessPoint(tmpAP);
               }

               if (tmpAPId != null) {
                  this.setAccessPointId(tmpAPId);
               }

               if (tmpNWAddr != null) {
                  this.setNWAddr(tmpNWAddr);
               }

               if (tmpSecurity != null) {
                  this.setSecurity(tmpSecurity);
               }

               if (tmpConnPolicy != null) {
                  this.setConnectionPolicy(tmpConnPolicy);
               }

               if (tmpMaxRetries != -1L) {
                  this.setMaxRetries(tmpMaxRetries);
               }

               if (tmpRetryInterval != -1L) {
                  this.setRetryInterval(tmpRetryInterval);
               }

               if (tmpBlockTime != -1L) {
                  this.setBlockTime(tmpBlockTime * 1000L);
               }

               if (tmpCmpLimit != -1) {
                  this.setCmpLimit(tmpCmpLimit);
               }

               if (tmpMinEncryptBits != -1) {
                  this.setMinEncryptBits(tmpMinEncryptBits);
               }

               if (tmpMaxEncryptBits != -1) {
                  this.setMaxEncryptBits(tmpMaxEncryptBits);
               }

               if (tmpInteroperate != null) {
                  this.setInteroperate(tmpInteroperate);
               }

               if (tmpKeepAlive != -1) {
                  this.setKeepAlive(tmpKeepAlive);
               }

               if (tmpKeepAliveWait != -1) {
                  this.setKeepAliveWait(tmpKeepAliveWait);
               }
            } catch (TPException var28) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/prepareUpdate/100/Exception: " + var28.getMessage());
               }

               throw new BeanUpdateRejectedException(var28.getMessage());
            }

            if ((tmpUseSSL != null || tmpMinEncryptBits != -1 || tmpMaxEncryptBits != -1) && !this.isSSL && (this.MinEncryptionBits >= 256 || this.MaxEncryptionBits >= 256)) {
               WTCLogger.logInfoLLEEncryptBitsDowngrade(this.getAccessPointId());
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TDMLocalTDomain/prepareUpdate/110/DONE");
            }

         }
      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/activeUpdate");
         ntrace.doTrace("]/TDMLocalTDomain/activeUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/rollbackUpdate");
         ntrace.doTrace("]/TDMLocalTDomain/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMLocalTDomain: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocalTDomain/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMLocalTDomain: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocalTDomain/unregisterListener/10/DONE");
      }

   }

   public static TDMLocalTDomain create(WTCLocalTuxDomMBean mb) throws TPException {
      String accpnt = mb.getAccessPoint();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocalTDomain/create/" + accpnt);
      }

      if (accpnt == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocalTDomain/create/10/no AP");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("AccessPoint: " + accpnt);
         }

         String accpntId = mb.getAccessPointId();
         if (accpntId == null) {
            Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPointId", mb.getName());
            lua.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocalTDomain/create/20/no APId");
            }

            throw new TPException(4, lua.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("AccessPointId:" + accpntId);
            }

            String nwaddr = mb.getNWAddr();
            if (nwaddr == null) {
               Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("NWAddr", mb.getName());
               lua.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMLocalTDomain/create/30/no NWAddr");
               }

               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("NWAddr:" + nwaddr);
               }

               if (traceEnabled) {
                  ntrace.doTrace("create ltd from " + accpnt);
               }

               TDMLocalTDomain ltd;
               Loggable lia;
               try {
                  ltd = new TDMLocalTDomain(accpnt);
               } catch (Exception var8) {
                  lia = WTCLogger.logUEconstructTDMLocalTDLoggable(var8.getMessage());
                  lia.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMLocalTDomain/create/40/create failed");
                  }

                  throw new TPException(4, lia.getMessage());
               }

               ltd.setAccessPointId(accpntId);
               ltd.setSecurity(mb.getSecurity());
               ltd.setConnectionPolicy(mb.getConnectionPolicy());
               ltd.setInteroperate(mb.getInteroperate());
               ltd.setRetryInterval(mb.getRetryInterval());
               ltd.setMaxRetries(mb.getMaxRetries());
               ltd.setBlockTime(mb.getBlockTime() * 1000L);

               try {
                  ltd.setNWAddr(nwaddr);
               } catch (TPException var9) {
                  lia = WTCLogger.logInvalidMBeanAttrLoggable("NWAddr", mb.getName());
                  lia.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMLocalTDomain/create/50/bad NWAddr:" + var9.getMessage());
                  }

                  throw new TPException(4, lia.getMessage());
               }

               try {
                  ltd.setCmpLimit(mb.getCmpLimit());
               } catch (TPException var10) {
                  lia = WTCLogger.logInvalidMBeanAttrLoggable("CmpLimit", mb.getName());
                  lia.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMLocalTDomain/create/60/bad CMPLIMIT:" + var10.getMessage());
                  }

                  throw new TPException(4, lia.getMessage());
               }

               String tmps = mb.getMinEncryptBits();
               if (tmps != null) {
                  ltd.setMinEncryptBits(Integer.parseInt(tmps, 10));
               }

               tmps = mb.getMaxEncryptBits();
               if (tmps != null) {
                  ltd.setMaxEncryptBits(Integer.parseInt(tmps, 10));
               }

               ltd.setKeepAlive(mb.getKeepAlive());
               ltd.setKeepAliveWait(mb.getKeepAliveWait());
               ltd.setMBean(mb);
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMLocalTDomain/create/70/success");
               }

               return ltd;
            }
         }
      }
   }

   public ArrayList assembleConfigObject() throws TPException {
      WTCLocalAccessPoint lap = new WTCLocalAccessPoint();
      String[] epg = new String[]{this.origNWAddr};
      lap.setNetworkAddr(epg);
      lap.setEndPointGroup(this.myNWAddr);
      lap.fillFromSource(this);
      this.addConfigObj(lap);
      return this.getConfigObjList();
   }
}
