package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.HashSet;
import weblogic.wtc.jatmi.TPException;

public abstract class TDMLocal extends WTCMBeanObject {
   private String myAccessPoint;
   private String myWlsClusterName;
   private String myAccessPointId;
   private String myType;
   private String mySecurity;
   private String myConnectionPolicy;
   private long myMaxRetries;
   private long myRetryInterval;
   private String myConnPrincipalName;
   private long myBlockTime;
   private HashSet myRemoteDomains;

   public TDMLocal(String AccessPoint) throws Exception {
      if (AccessPoint == null) {
         throw new Exception("AccessPoint cannot be null");
      } else {
         this.myAccessPoint = AccessPoint;
         this.myRemoteDomains = new HashSet();
         this.myBlockTime = 60000L;
         this.myMaxRetries = -1L;
         this.myRetryInterval = -1L;
      }
   }

   public String getAccessPoint() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myAccessPoint;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public String getWlsClusterName() {
      return this.myWlsClusterName;
   }

   public boolean setWlsClusterName(String WlsClusterName) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocal/setWlsClusterName/name=" + WlsClusterName);
      }

      this.myWlsClusterName = WlsClusterName;
      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocal/setWlsClusterName/15/true");
      }

      return true;
   }

   public String getAccessPointId() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myAccessPointId;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setAccessPoint(String AccessPoint) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (AccessPoint == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setAccessPoint/id=null");
            ntrace.doTrace("*]/TDMLocal/setAccessPoint/10/TPEINVAL");
         }

         throw new TPException(4, "null AccessPoint found in one of the local domain");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setAccessPoint/id=" + AccessPoint);
         }

         this.w.lock();
         this.myAccessPoint = AccessPoint;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocal/setAccessPoint/20/SUCCESS");
         }

      }
   }

   public void setAccessPointId(String AccessPointId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (AccessPointId == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setAccessPointId/id=null");
            ntrace.doTrace("*]/TDMLocal/setAccessPointId/10/TPEINVAL");
         }

         throw new TPException(4, "null AccessPointId found in one of the local domain");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setAccessPointId/id=" + AccessPointId);
         }

         this.w.lock();
         this.myAccessPointId = AccessPointId;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocal/setAccessPointId/20/SUCCESS");
         }

      }
   }

   public synchronized String getType() {
      return this.myType;
   }

   public synchronized void setType(String Type) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (Type != null) {
            ntrace.doTrace("[/TDMLocal/setType/type=" + Type);
         } else {
            ntrace.doTrace("[/TDMLocal/setType/type=null");
         }
      }

      if (Type != null) {
         this.myType = Type;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocal/setType/10/SUCCESS");
      }

   }

   public String getSecurity() {
      this.r.lock();

      String var1;
      try {
         var1 = this.mySecurity;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setSecurity(String Security) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (Security == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setSecurity/security=null");
            ntrace.doTrace("]/TDMLocal/setSecurity/10/TPEINVAL");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setSecurity/security=" + Security);
         }

         if (!Security.equals("NONE") && !Security.equals("APP_PW") && !Security.equals("DM_PW")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocal/setSecurity/25/TPEINVAL");
            }

            throw new TPException(4, "Invalid Domain Security type \"" + Security + "\" found in local domain " + this.getAccessPointId());
         } else {
            this.w.lock();
            this.mySecurity = Security;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMLocal/setSecurity/20/SUCCESS");
            }

         }
      }
   }

   public String getConnectionPolicy() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myConnectionPolicy;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setConnectionPolicy(String ConnectionPolicy) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (ConnectionPolicy == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setConnectionPolicy/policy=null");
            ntrace.doTrace("]/TDMLocal/setConnectionPolicy/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setConnectionPolicy/policy=" + ConnectionPolicy);
         }

         if (!ConnectionPolicy.equals("ON_DEMAND") && !ConnectionPolicy.equals("ON_STARTUP") && !ConnectionPolicy.equals("INCOMING_ONLY")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMLocal/setConnectionPolicy/30/TPEINVAL");
            }

            throw new TPException(4, "Invalid ConnectionPolicy \"" + ConnectionPolicy + "\" found in local domain " + this.getAccessPointId());
         } else {
            this.w.lock();
            this.myConnectionPolicy = ConnectionPolicy;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMLocal/setConnectionPolicy/20/SUCCESS");
            }

         }
      }
   }

   public String getConnPrincipalName() {
      this.r.lock();

      String var1;
      try {
         if (this.myConnPrincipalName == null) {
            var1 = this.myAccessPointId;
            return var1;
         }

         var1 = this.myConnPrincipalName;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setConnPrincipalName(String ConnPrincipalName) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (ConnPrincipalName != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMLocal/setConnPrincipalName/name=" + ConnPrincipalName);
         }

         this.myConnPrincipalName = ConnPrincipalName;
      } else if (traceEnabled) {
         ntrace.doTrace("[/TDMLocal/setConnPrincipalName/name=null");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMLocal/setConnPrincipalName/10/SUCCESS");
      }

   }

   public long getRetryInterval() {
      long retval = Long.MAX_VALUE;
      this.r.lock();
      if (this.myConnectionPolicy.equals("ON_STARTUP")) {
         retval = this.myRetryInterval;
         if (this.myMaxRetries != 0L && retval == -1L) {
            retval = 60L;
         }
      }

      this.r.unlock();
      return retval;
   }

   public void setRetryInterval(long RetryInterval) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocal/setRetryInterval/name=" + RetryInterval);
      }

      if (RetryInterval >= 0L && RetryInterval <= 2147483647L) {
         this.w.lock();
         this.myRetryInterval = RetryInterval;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocal/setRetryInterval/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocal/setRetryInterval/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid RetryInterval value " + RetryInterval + " found in one of the local domain" + this.getAccessPointId());
      }
   }

   public long getMaxRetries() {
      long retval = 0L;
      this.r.lock();
      if (this.myConnectionPolicy.equals("ON_STARTUP") && (retval = this.myMaxRetries) == -1L) {
         retval = Long.MAX_VALUE;
      }

      this.r.unlock();
      return retval;
   }

   public void setMaxRetries(long MaxRetries) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocal/setMaxRetries/retries=" + MaxRetries);
      }

      if (MaxRetries >= 0L && MaxRetries <= Long.MAX_VALUE) {
         this.w.lock();
         this.myMaxRetries = MaxRetries;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocal/setMaxRetries/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocal/setMaxRetries/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid MAXRETRY value \"" + MaxRetries + "\" found in local domain " + this.getAccessPointId());
      }
   }

   public void setBlockTime(long blocktime) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMLocal/setBlockTime/time=" + blocktime);
      }

      if (blocktime < 0L) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMLocal/setBlockTime/10/TPEINVAL");
         }

         throw new TPException(4, "Invalid block time value \"" + blocktime + "\" found in local domain " + this.getAccessPointId());
      } else {
         this.w.lock();
         this.myBlockTime = blocktime;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMLocal/setBlockTime/20/SUCCESS");
         }

      }
   }

   public long getBlockTime() {
      this.r.lock();

      long var1;
      try {
         var1 = this.myBlockTime;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public synchronized boolean add_remote_domain(TDMRemote rd) {
      return this.myRemoteDomains.add(rd);
   }

   public synchronized void remove_remote_domain(TDMRemote rd) {
      this.myRemoteDomains.remove(rd);
   }

   public synchronized TDMRemote[] get_remote_domains() {
      return (TDMRemote[])((TDMRemote[])this.myRemoteDomains.toArray(new TDMRemote[0]));
   }

   public boolean equals(Object compare_with_me) {
      TDMLocal cwm = (TDMLocal)compare_with_me;
      String cwm_name = cwm.getAccessPoint();
      this.r.lock();

      boolean var4;
      try {
         if (this.myAccessPoint != null && cwm != null) {
            var4 = cwm_name.equals(this.myAccessPoint);
            return var4;
         }

         var4 = false;
      } finally {
         this.r.unlock();
      }

      return var4;
   }

   public int hashCode() {
      this.r.lock();

      int var1;
      try {
         if (this.myAccessPoint != null) {
            var1 = this.myAccessPoint.hashCode();
            return var1;
         }

         var1 = 0;
      } finally {
         this.r.unlock();
      }

      return var1;
   }
}
