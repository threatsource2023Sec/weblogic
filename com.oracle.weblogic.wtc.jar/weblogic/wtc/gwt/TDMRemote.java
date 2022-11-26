package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.gwatmi;

public abstract class TDMRemote extends WTCMBeanObject implements TuxedoConnectorRAP {
   static final long serialVersionUID = 8838736910331494533L;
   private String myAccessPoint;
   private String myAccessPointId;
   private String myLocalAccessPoint;
   private String myType;
   private String myAclPolicy;
   private String myConnPrincipalName;
   private String myCredentialPolicy;
   private String myTpUsrFile;
   private String myConnectionPolicy;
   private long myMaxRetries;
   private long myRetryInterval;
   private int myConnPolicyConfigState;
   private TDMLocal myLocalAccessPointObject;
   private TuxXidRply myXidRplyObj;
   public static final int NOTDEFINED_STATE = 0;
   public static final int DEFINED_STATE = 1;
   public static final int DEFAULT_STATE = 2;
   public static final int FORCEDEFAULT_STATE = 3;

   public TDMRemote(String AccessPoint, TuxXidRply anXidRply) throws Exception {
      if (AccessPoint == null) {
         throw new Exception("AccessPoint may not be null");
      } else {
         this.myAccessPoint = AccessPoint;
         this.myXidRplyObj = anXidRply;
         this.myConnPolicyConfigState = 0;
         this.myMaxRetries = -1L;
         this.myRetryInterval = -1L;
      }
   }

   public TuxXidRply getUnknownXidRplyObj() {
      return this.myXidRplyObj;
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

   public void setAccessPointId(String AccessPointId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (AccessPointId == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAccessPointId/id=null");
            ntrace.doTrace("*]TDMRemote/setAccessPointId/10/TPEINVAL");
         }

         throw new TPException(4, "null AccessPointId found in one of the remotedomain.");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAccessPointId/id=" + AccessPointId);
         }

         this.w.lock();
         this.myAccessPointId = AccessPointId;
         this.myConnPrincipalName = AccessPointId;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setAccessPointId/20/SUCCESS");
         }

      }
   }

   public void setAccessPoint(String AccessPoint) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (AccessPoint == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAccessPoint/name=null");
            ntrace.doTrace("*]/TDMRemote/setAccessPoint/10/TPEINVAL");
         }

         throw new TPException(4, "null AccessPoint found in one of the remote domains");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAccessPoint/name=" + AccessPoint);
         }

         this.w.lock();
         this.myAccessPoint = AccessPoint;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setAccessPoint/20/SUCCESS");
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
            ntrace.doTrace("[/TDMRemote/setType/type=" + Type);
         } else {
            ntrace.doTrace("[/TDMRemote/setType/type=null");
         }
      }

      if (Type != null) {
         this.myType = Type;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setType/10/SUCCESS");
         }

      }
   }

   public String getConnectionPolicy() {
      this.r.lock();

      String var1;
      try {
         if ((this.myConnectionPolicy == null || this.myConnectionPolicy.equals("LOCAL")) && this.myLocalAccessPointObject != null) {
            var1 = this.myLocalAccessPointObject.getConnectionPolicy();
            return var1;
         }

         var1 = this.myConnectionPolicy;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public String getConfigConnectionPolicy() {
      this.r.lock();

      String var1;
      try {
         if (this.myConnPolicyConfigState != 3) {
            var1 = this.myConnectionPolicy;
            return var1;
         }

         var1 = "LOCAL";
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setConnectionPolicy(String ConnectionPolicy) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (ConnectionPolicy != null) {
            ntrace.doTrace("[/TDMRemote/setConnectionPolicy/policy=" + ConnectionPolicy);
         } else {
            ntrace.doTrace("[/TDMRemote/setConnectionPolicy/policy=null");
         }
      }

      if (ConnectionPolicy != null && !ConnectionPolicy.equals("LOCAL")) {
         if (!ConnectionPolicy.equals("ON_DEMAND") && !ConnectionPolicy.equals("ON_STARTUP") && !ConnectionPolicy.equals("INCOMING_ONLY")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMRemote/setConnectionPolicy/40/TPEINVAL");
            }

            throw new TPException(4, "Invalid ConnectionPolicy \"" + ConnectionPolicy + "\" found in remote domain " + this.getAccessPointId());
         }

         this.setConnPolicyConfigState(1);
      } else if (ConnectionPolicy == null) {
         this.setConnPolicyConfigState(2);
      } else {
         this.setConnPolicyConfigState(3);
      }

      this.w.lock();
      this.myConnectionPolicy = ConnectionPolicy;
      this.w.unlock();
      if (traceEnabled) {
         ntrace.doTrace("]/TDMRemote/setConnectionPolicy/50/SUCCESS");
      }

   }

   public int getConnPolicyConfigState() {
      this.r.lock();

      int var1;
      try {
         var1 = this.myConnPolicyConfigState;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean setConnPolicyConfigState(int ConfigState) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemote/setConnPolicyConfigState/state=" + ConfigState);
      }

      switch (ConfigState) {
         case 0:
         case 1:
         case 2:
         case 3:
            this.w.lock();
            this.myConnPolicyConfigState = ConfigState;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setConnPolicyConfigState/10/true");
            }

            return true;
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setConnPolicyConfigState/15/false");
            }

            return false;
      }
   }

   public String getAclPolicy() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myAclPolicy;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setAclPolicy(String AclPolicy) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (AclPolicy == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAclPolicy/policy=null");
            ntrace.doTrace("]/TDMRemote/setAclPolicy/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setAclPolicy/policy=" + AclPolicy);
         }

         if (!AclPolicy.equals("LOCAL") && !AclPolicy.equals("GLOBAL")) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setAclPolicy/15/false");
            }

            throw new TPException(4, "Invalid AclPolicy \"" + AclPolicy + "\" found in remote domain " + this.getAccessPointId());
         } else {
            this.w.lock();
            this.myAclPolicy = AclPolicy;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setAclPolicy/10/SUCCESS");
            }

         }
      }
   }

   public String getCredentialPolicy() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myCredentialPolicy;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setCredentialPolicy(String CredentialPolicy) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (CredentialPolicy == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setCredentialPolicy/policy=null");
            ntrace.doTrace("]/TDMRemote/setCredentialPolicy/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setCredentialPolicy/policy=" + CredentialPolicy);
         }

         if (!CredentialPolicy.equals("LOCAL") && !CredentialPolicy.equals("GLOBAL")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMRemote/setCredentialPolicy/30/false");
            }

            throw new TPException(4, "Invalid CredentialPolicy \"" + CredentialPolicy + "\" found in remote domain " + this.getAccessPointId());
         } else {
            this.w.lock();
            this.myCredentialPolicy = CredentialPolicy;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setCredentialPolicy/20/SUCCESS");
            }

         }
      }
   }

   public String getTpUsrFile() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myTpUsrFile;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setTpUsrFile(String TpUsrFile) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (TpUsrFile == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setTpUsrFile/file=" + TpUsrFile);
            ntrace.doTrace("]/TDMRemote/setTpUsrFile/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setTpUsrFile/file=null");
         }

         this.w.lock();
         this.myTpUsrFile = TpUsrFile;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setTpUsrFile/20/SUCCESS");
         }

      }
   }

   public String getLocalAccessPoint() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myLocalAccessPoint;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setLocalAccessPoint(String lap) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (lap == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setLocalAccessPoint/accesspoint=null");
            ntrace.doTrace("*]/TDMRemote/setLocalAccessPoint/10/TPEINVAL");
         }

         throw new TPException(4, "null LocalAccessPoint found in remote domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setLocalAccessPoint/accesspoint=" + lap);
         }

         this.w.lock();
         if (this.myLocalAccessPoint != null) {
            if (this.myLocalAccessPoint.equals(lap)) {
               this.w.unlock();
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemote/setLocalAccessPoint/10/no change");
               }

               return;
            }

            TDMLocal saveLDom = this.myLocalAccessPointObject;
            if (this.myLocalAccessPointObject != null) {
               this.myLocalAccessPointObject.remove_remote_domain(this);
               this.myLocalAccessPointObject = null;
            }

            this.myLocalAccessPointObject = WTCService.getWTCService().getLocalDomain(lap);
            if (this.myLocalAccessPointObject == null || !this.myLocalAccessPointObject.add_remote_domain(this)) {
               this.myLocalAccessPointObject = saveLDom;
               this.w.unlock();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemote/setLocalAccessPoint/20/TPEINVAL");
               }

               throw new TPException(4, "Failed to add remote domain " + this.myAccessPoint + " to local domain " + lap);
            }
         } else {
            this.myLocalAccessPointObject = WTCService.getWTCService().getLocalDomain(lap);
            if (this.myLocalAccessPointObject == null || !this.myLocalAccessPointObject.add_remote_domain(this)) {
               this.w.unlock();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemote/setLocalAccessPoint/30/TPEINVAL");
               }

               throw new TPException(4, "Failed to add remote domain " + this.myAccessPoint + " to local domain " + lap);
            }
         }

         this.myLocalAccessPoint = lap;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setLocalAccessPoint/40/changed");
         }

      }
   }

   public TDMLocal getLocalAccessPointObject() {
      this.r.lock();

      TDMLocal var1;
      try {
         var1 = this.myLocalAccessPointObject;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setLocalAccessPointObject(TDMLocal LocalAccessPointObject) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (LocalAccessPointObject == null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setLocalAccessPointObject/lapObject=null");
            ntrace.doTrace("*]/TDMRemote/setLocalAccessPointObject/10/TPEINVAL");
         }

         throw new TPException(4, "null LocalAccessPoint found in remote domain " + this.getAccessPointId());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setLocalAccessPointObject/accesspoint=" + LocalAccessPointObject.getAccessPoint());
         }

         this.w.lock();
         if (this.myLocalAccessPointObject != null) {
            if (this.myLocalAccessPointObject.equals(LocalAccessPointObject)) {
               this.w.unlock();
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMRemote/setLocalAccessPointObject/10/no change");
               }

               return;
            }

            this.myLocalAccessPointObject.remove_remote_domain(this);
         }

         if (!LocalAccessPointObject.add_remote_domain(this)) {
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMRemote/setLocalAccessPoint/20/TPESYSTEM");
            }

            throw new TPException(12, "Failed to add remote domain " + this.myAccessPoint + " to local domain " + LocalAccessPointObject.getAccessPoint());
         } else {
            this.myLocalAccessPointObject = LocalAccessPointObject;
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMRemote/setLocalAccessPoint/30/SUCCESS");
            }

         }
      }
   }

   public String getConnPrincipalName() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myConnPrincipalName;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setConnPrincipalName(String ConnPrincipalName) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (ConnPrincipalName != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setConnPrincipalName/name=" + ConnPrincipalName);
         }

         this.w.lock();
         this.myConnPrincipalName = ConnPrincipalName;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setConnPrincipalName/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMRemote/setConnPrincipalName/name=null");
            ntrace.doTrace("]/TDMRemote/setConnPrincipalName/20/FAILURE");
         }

      }
   }

   public long getRetryInterval() {
      long retval = 0L;
      this.r.lock();
      if (this.myConnectionPolicy.equals("ON_STARTUP")) {
         retval = this.myRetryInterval;
         if (this.myMaxRetries != 0L && retval == -1L) {
            retval = 60L;
         }
      } else if (this.myConnectionPolicy.equals("LOCAL") && this.myLocalAccessPointObject != null) {
         retval = this.myLocalAccessPointObject.getRetryInterval();
      }

      this.r.unlock();
      return retval;
   }

   public void setRetryInterval(long RetryInterval) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemote/setRetryInterval/interval=" + RetryInterval);
      }

      if (RetryInterval >= -1L && RetryInterval <= 2147483647L) {
         this.w.lock();
         this.myRetryInterval = RetryInterval;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setRetryInterval/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemote/setRetryInterval/20/TPEINVAL");
         }

         throw new TPException(4, "Invalid RetryInterval  value" + RetryInterval + " found in remote domain " + this.getAccessPointId());
      }
   }

   public long getMaxRetries() {
      long retval = 0L;
      this.r.lock();
      if (this.myConnectionPolicy.equals("ON_STARTUP")) {
         if ((retval = this.myMaxRetries) == -1L) {
            retval = Long.MAX_VALUE;
         }
      } else if (this.myConnectionPolicy.equals("LOCAL") && this.myLocalAccessPointObject != null) {
         retval = this.myLocalAccessPointObject.getMaxRetries();
      }

      this.r.unlock();
      return retval;
   }

   public void setMaxRetries(long MaxRetries) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemote/setMaxRetries/retries=" + MaxRetries);
      }

      if (MaxRetries >= -1L && MaxRetries <= Long.MAX_VALUE) {
         this.w.lock();
         this.myMaxRetries = MaxRetries;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMRemote/setMaxRetries/10/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemote/setMaxRetries/15/TPEINVAL");
         }

         throw new TPException(4, "Invalid MaxRetry value " + MaxRetries + " found in remote domain " + this.getAccessPointId());
      }
   }

   public boolean equals(Object compare_with_me) {
      TDMRemote cwm = (TDMRemote)compare_with_me;
      if (this.myAccessPoint != null && this.myLocalAccessPoint != null && cwm != null) {
         TDMLocal cwm_local = cwm.getLocalAccessPointObject();
         if (cwm_local != null && this.myAccessPoint.equals(cwm.getAccessPoint()) && this.myLocalAccessPoint.equals(cwm_local.getAccessPoint())) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      return this.myAccessPoint == null ? 0 : this.myAccessPoint.hashCode();
   }

   abstract gwatmi getTsession(boolean var1);

   abstract void setTsession(gwatmi var1);

   abstract boolean getTimedOut();
}
