package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.StringTokenizer;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.WTCImportMBean;
import weblogic.wtc.jatmi.TPException;

public final class TDMImport extends WTCMBeanObject implements BeanUpdateListener {
   static final long serialVersionUID = -1088113749163657268L;
   private String myResourceName;
   private String myLocalAccessPoint;
   private String[] myRemoteAccessPointList;
   private TDMLocal myLocalAccessPointObject;
   private TDMRemote[] myRemoteAccessPointObjectList;
   private String myRemoteName;
   private int myTranTime;
   private WTCImportMBean mBean = null;
   private boolean registered = false;
   private boolean suspended = false;
   private WTCService myWTC;
   private String myRemoteAccessPointListString = null;

   public TDMImport(String ResourceName, TDMLocal LocalAccessPoint, TDMRemote[] RemoteAccessPointList) throws Exception {
      if (ResourceName != null && LocalAccessPoint != null) {
         this.myResourceName = ResourceName;
         if (this.myRemoteName == null || this.myRemoteName.length() == 0) {
            this.myRemoteName = ResourceName;
         }

         this.myLocalAccessPointObject = LocalAccessPoint;
         this.myLocalAccessPoint = LocalAccessPoint.getAccessPoint();
         int ev = RemoteAccessPointList.length;
         this.myRemoteAccessPointList = new String[ev];

         for(int lcv = 0; lcv < ev; ++lcv) {
            TDMRemote ro = RemoteAccessPointList[lcv];
            if (!this.myLocalAccessPoint.equals(ro.getLocalAccessPoint())) {
               throw new Exception("A remote access point in list not connected to the local access point");
            }

            this.myRemoteAccessPointList[lcv] = ro.getAccessPoint();
         }

         this.myRemoteAccessPointObjectList = RemoteAccessPointList;
         this.myTranTime = 30;
         this.myWTC = WTCService.getWTCService();
      } else {
         throw new Exception("ResourceName and LocalAccessPoint may not be null");
      }
   }

   public String getResourceName() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myResourceName;
      } finally {
         this.r.unlock();
      }

      return var1;
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

   public String[] getRemoteAccessPointList() {
      this.r.lock();

      String[] var1;
      try {
         var1 = this.myRemoteAccessPointList;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public TDMRemote[] getRemoteAccessPointObjectList() {
      this.r.lock();

      TDMRemote[] var1;
      try {
         var1 = this.myRemoteAccessPointObjectList;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public String getPrimaryRemoteAccessPoint() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemoteAccessPointList[0];
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean hasRemoteDomain(TDMRemote rdom) {
      this.r.lock();

      for(int i = 0; i < this.myRemoteAccessPointList.length; ++i) {
         if (this.myRemoteAccessPointObjectList[i] == rdom) {
            this.r.unlock();
            return true;
         }
      }

      this.r.unlock();
      return false;
   }

   public boolean hasActiveTsession() {
      this.r.lock();

      for(int i = 0; i < this.myRemoteAccessPointObjectList.length; ++i) {
         if (this.myRemoteAccessPointObjectList[i].isObjectActivated() && this.myRemoteAccessPointObjectList[i].getTsession(false) != null) {
            this.r.unlock();
            return true;
         }
      }

      this.r.unlock();
      return false;
   }

   public boolean hasDynamicRemotePolicy() {
      this.r.lock();

      for(int i = 0; i < this.myRemoteAccessPointObjectList.length; ++i) {
         String remoteConnectionPolicy;
         if ((remoteConnectionPolicy = this.myRemoteAccessPointObjectList[i].getConnectionPolicy()) != null && (remoteConnectionPolicy.equals("ON_STARTUP") || remoteConnectionPolicy.equals("INCOMING_ONLY"))) {
            this.r.unlock();
            return true;
         }
      }

      this.r.unlock();
      return false;
   }

   public String getRemoteName() {
      this.r.lock();

      String var1;
      try {
         if (this.myRemoteName == null || this.myRemoteName.length() == 0) {
            var1 = this.myResourceName;
            return var1;
         }

         var1 = this.myRemoteName;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setRemoteName(String RemoteName) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/setRemoteName/name=" + RemoteName);
      }

      if (RemoteName != null && RemoteName.length() != 0) {
         this.w.lock();
         this.myRemoteName = RemoteName;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setRemoteName/10/changed");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setRemoteName/15/not changed");
         }

      }
   }

   public int getTranTime() {
      this.r.lock();

      int var1;
      try {
         var1 = this.myTranTime;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public WTCImportMBean getMBean() {
      return this.mBean;
   }

   public void setTranTime(int TranTime) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/setTranTime/time=" + TranTime);
      }

      if (TranTime >= 0 && TranTime <= Integer.MAX_VALUE) {
         this.w.lock();
         this.myTranTime = TranTime;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setTranTime/10/changed");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setTranTime/15/no change");
         }

      }
   }

   public void setResourceName(String resource) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (resource != null && resource.length() != 0) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setResourceName/ResourceName=" + resource);
         }

         String saveResourceName = this.myResourceName;
         this.myResourceName = resource;
         this.myWTC.changeImportResourceName(this, saveResourceName);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setResourceName/20/DONE");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setResourceName/ResourceName=null");
            ntrace.doTrace("*]/TDMImport/setResourceName/10/TPEINVAL");
         }

         throw new TPException(4, "ResourceName may not be null");
      }
   }

   public void setMBean(WTCImportMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setMBname/MBeanName=" + mb.getName());
         }

         if (this.mBean != null) {
            if (this.mBean == mb) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMImport/setMBname/10/same, no change");
               }

               return;
            }

            this.unregisterListener();
         }

         this.mBean = mb;
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setMBname/20/changed");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setMBname/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setMBname/30/");
         }
      }

   }

   public void setLocalAccessPoint(String accesspnt) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (accesspnt != null && accesspnt.length() != 0) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setLocalAccessPoint/" + accesspnt);
         }

         this.w.lock();
         this.myLocalAccessPointObject = this.myWTC.getLocalDomain(accesspnt);
         this.myLocalAccessPoint = accesspnt;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setLocalAccessPoint/20/DONE");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setLocalAccessPoint/null");
            ntrace.doTrace("*]/TDMImport/setLocalAccessPoint/10");
         }

         throw new TPException(4, "LocalAccessPoint may not be null");
      }
   }

   public void setRemoteAccessPointList(String remotelist) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (remotelist != null && remotelist.length() != 0) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setRemoteAccessPointList/" + remotelist);
         }

         TDMRemoteTDomain irtd;
         TDMRemoteTDomain[] irdoms;
         String[] irap;
         if (remotelist.indexOf(44) == -1) {
            irtd = this.myWTC.getRTDbyAP(remotelist);
            if (irtd == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMImport/setRemoteAccessPointList/20/Unknown RTD name " + remotelist);
               }

               throw new TPException(4, "Unknown Remote TDomain name " + remotelist);
            }

            irdoms = new TDMRemoteTDomain[1];
            irap = new String[1];
            irdoms[0] = irtd;
            irap[0] = remotelist;
         } else {
            StringTokenizer st = new StringTokenizer(remotelist, ",");
            irdoms = new TDMRemoteTDomain[st.countTokens()];
            irap = new String[st.countTokens()];

            for(int j = 0; st.hasMoreTokens(); ++j) {
               if (j > 2) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMImport/setRemoteAccessPointList/40/list size limited to three");
                  }

                  throw new TPException(4, "Remote access point list cannot have more than three elements");
               }

               String rapnm = st.nextToken();
               irtd = this.myWTC.getRTDbyAP(rapnm);
               if (irtd == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMImport/setRemoteAccessPointList/50/Known RTDs list does not include " + rapnm);
                  }

                  throw new TPException(4, "Known RTDs list does not include " + rapnm);
               }

               irdoms[j] = irtd;
               irap[j] = rapnm;
            }
         }

         this.w.lock();
         this.myRemoteAccessPointObjectList = irdoms;
         this.myRemoteAccessPointList = irap;
         this.myRemoteAccessPointListString = remotelist;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/setRemoteAccessPointList/60/DONE");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMImport/setRemoteAccessPointList/null");
            ntrace.doTrace("*]/TDMImport/setRemoteAccessPointList/10/List is NULL");
         }

         throw new TPException(4, "RemoteAccessPointList can not be null");
      }
   }

   public boolean equals(Object compare_with_me) {
      TDMImport cwm = (TDMImport)compare_with_me;
      if (cwm == this) {
         return true;
      } else {
         this.r.lock();
         String cwm_name;
         String cwm_lap;
         String[] cwm_rap;
         if (cwm != null && (cwm_name = cwm.getResourceName()) != null && (cwm_lap = cwm.getLocalAccessPoint()) != null && (cwm_rap = cwm.getRemoteAccessPointList()) != null && cwm_rap.length == this.myRemoteAccessPointList.length) {
            if (cwm_name.equals(this.myResourceName) && cwm_lap.equals(this.myLocalAccessPoint)) {
               for(int lcv = 0; lcv < this.myRemoteAccessPointList.length; ++lcv) {
                  if (!this.myRemoteAccessPointList[lcv].equals(cwm_rap[lcv])) {
                     this.r.unlock();
                     return false;
                  }
               }

               this.r.unlock();
               return true;
            } else {
               this.r.unlock();
               return false;
            }
         } else {
            this.r.unlock();
            return false;
         }
      }
   }

   public int hashCode() {
      this.r.lock();
      int ResourceNameCode;
      if (this.myResourceName == null) {
         ResourceNameCode = 0;
      } else {
         ResourceNameCode = this.myResourceName.hashCode();
      }

      int LocalAccessPointCode;
      if (this.myLocalAccessPoint == null) {
         LocalAccessPointCode = 0;
      } else {
         LocalAccessPointCode = this.myLocalAccessPoint.hashCode();
      }

      int RemoteAccessPointListCode = 0;
      if (this.myRemoteAccessPointList != null) {
         int ev = this.myRemoteAccessPointList.length;

         for(int lcv = 0; lcv < ev; ++lcv) {
            RemoteAccessPointListCode += this.myRemoteAccessPointList[lcv].hashCode();
         }
      }

      this.r.unlock();
      return ResourceNameCode + LocalAccessPointCode + RemoteAccessPointListCode;
   }

   public void setRemoteAccessPointListString(String list) {
      this.myRemoteAccessPointListString = list;
   }

   public String getRemoteAccessPointListString() {
      return this.myRemoteAccessPointListString;
   }

   public static String[] parseCommaSeparatedList(String str) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/parseCommaSeparatedList/string = " + str);
      }

      String[] csl;
      if (str.indexOf(44) == -1) {
         csl = new String[]{str};
      } else {
         StringTokenizer st = new StringTokenizer(str, ",");
         csl = new String[st.countTokens()];

         for(int i = 0; st.hasMoreTokens(); ++i) {
            csl[i] = st.nextToken();
            if (traceEnabled) {
               ntrace.doTrace("Token " + i + ":" + csl[i]);
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMImport/parseCommaSeparateList/10/DONE");
      }

      return csl;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCImportMBean newBean = (WTCImportMBean)event.getProposedBean();
      String tmpResourceName = null;
      String tmpLocalAccessPoint = null;
      String tmpRemoteAccessPointList = null;
      String tmpRemoteName = null;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMImport/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMImport!");
      } else {
         String lap;
         for(int i = 0; i < updates.length; ++i) {
            lap = updates[i].getPropertyName();
            int opType = updates[i].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + i + ", opType = " + opType + ", key = " + lap);
            }

            if (opType == 1) {
               if (lap.equals("ResourceName")) {
                  tmpResourceName = newBean.getResourceName();
                  if (tmpResourceName.length() == 0) {
                     tmpResourceName = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Resource Name:" + tmpResourceName);
                  }
               } else if (lap.equals("LocalAccessPoint")) {
                  tmpLocalAccessPoint = newBean.getLocalAccessPoint();
                  if (tmpLocalAccessPoint.length() == 0) {
                     tmpLocalAccessPoint = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Local AP:" + tmpLocalAccessPoint);
                  }
               } else if (lap.equals("RemoteAccessPointList")) {
                  tmpRemoteAccessPointList = newBean.getRemoteAccessPointList();
                  if (tmpRemoteAccessPointList.length() == 0) {
                     tmpRemoteAccessPointList = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Local AP:" + tmpRemoteAccessPointList);
                  }
               } else if (lap.equals("RemoteName")) {
                  tmpRemoteName = newBean.getRemoteName();
                  if (tmpRemoteName.length() == 0) {
                     tmpRemoteName = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Remote Name:" + tmpRemoteName);
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("Key " + lap + " not supported, ignored!");
               }
            } else if (opType == 2) {
               if (traceEnabled) {
                  ntrace.doTrace("Unexpected ADD operation, ignored!");
               }
            } else {
               if (opType != 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMImport/prepareUpdate/20/Unknown operation " + opType);
                  }

                  throw new BeanUpdateRejectedException("Unknown operation(" + opType + ") for TDMImport.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("Unexpected REMOVE operation, ignored!");
               }
            }
         }

         TDMLocal lapObj = null;
         lap = null;
         if (tmpLocalAccessPoint != null && !tmpLocalAccessPoint.equals(this.myLocalAccessPoint)) {
            lapObj = this.myWTC.getLocalDomain(tmpLocalAccessPoint);
            if (lapObj == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMImport/prepareUpdate/30/LAP " + tmpLocalAccessPoint + " is not configured!");
               }

               throw new BeanUpdateRejectedException("Unknown local access point " + tmpLocalAccessPoint);
            }

            lap = tmpLocalAccessPoint;
         } else {
            lapObj = this.myLocalAccessPointObject;
            lap = this.myLocalAccessPoint;
         }

         TDMRemoteTDomain[] rdom = (TDMRemoteTDomain[])((TDMRemoteTDomain[])this.myRemoteAccessPointObjectList);
         String[] raplist;
         if (tmpRemoteAccessPointList != null) {
            raplist = parseCommaSeparatedList(tmpRemoteAccessPointList);
            if (raplist.length == this.myRemoteAccessPointList.length) {
               int i;
               for(i = 0; i < raplist.length && raplist[i].equals(this.myRemoteAccessPointList[i]); ++i) {
               }

               if (i == raplist.length) {
                  raplist = this.myRemoteAccessPointList;
               } else {
                  rdom = new TDMRemoteTDomain[raplist.length];

                  for(i = 0; i < raplist.length; ++i) {
                     if ((rdom[i] = this.myWTC.getVTDomainSession(lap, raplist[i])) == null) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TDMImport/prepareUpdate/40/Imported svc " + tmpResourceName + " is configured for a existing TDomain session(" + lap + ", " + raplist[i] + ")!");
                        }

                        throw new BeanUpdateRejectedException("Remote TDomain Session(" + lap + ", " + raplist[i] + ") not defined.");
                     }
                  }
               }
            }
         } else {
            raplist = this.myRemoteAccessPointList;
         }

         if (tmpResourceName != null) {
            TDMImport tmp = this.myWTC.getImport(tmpResourceName, lap, raplist[0]);
            if (tmp != null && tmp != this) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMImport/prepareUpdate/50/Imported svc " + tmpResourceName + " already configured!");
               }

               throw new BeanUpdateRejectedException("Imported resouce " + tmpResourceName + " already exists for TDomain session(" + lap + ", " + raplist[0] + ")!");
            }
         }

         this.w.lock();
         if (tmpResourceName != null) {
            String save = this.myResourceName;
            this.myResourceName = tmpResourceName;
            this.myWTC.changeImportResourceName(this, save);
         }

         if (tmpLocalAccessPoint != null) {
            this.myLocalAccessPointObject = (TDMLocal)lapObj;
            this.myLocalAccessPoint = lap;
         }

         if (raplist != this.myRemoteAccessPointList) {
            this.myRemoteAccessPointList = raplist;
            this.myRemoteAccessPointObjectList = (TDMRemote[])rdom;
            this.myRemoteAccessPointListString = tmpRemoteAccessPointList;
         }

         if (tmpRemoteName != null) {
            this.myRemoteName = tmpRemoteName;
         }

         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/prepareUpdate/60/DONE");
         }

      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/activeUpdate");
         ntrace.doTrace("]/TDMImport/activeUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/rollbackUpdate");
         ntrace.doTrace("]/TDMImport/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMimport: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMImport/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMImport: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMImport/unregisterListener/10/DONE");
      }

   }

   public void suspend() {
      this.suspended = true;
   }

   public void resume() {
      this.suspended = false;
   }

   public int getStatus() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/getStatus/");
      }

      if (this.suspended) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/getStatus/10/SUSPENDED");
         }

         return 1;
      } else if (!this.hasActiveTsession() && this.hasDynamicRemotePolicy()) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/getStatus/40/UNAVAILABLE");
         }

         return 2;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMImport/getStatus/30/AVAILABLE");
         }

         return 3;
      }
   }

   public boolean match(String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMImport/match/lap = " + lap + ", raplist = " + raplist);
      }

      if (this.myLocalAccessPoint.equals(lap)) {
         if (raplist == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMImport/match/10/true");
            }

            return true;
         }

         if (this.myRemoteAccessPointListString != null && this.myRemoteAccessPointListString.equals(raplist)) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMImport/match/20/true");
            }

            return true;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMImport/match/30/false");
      }

      return false;
   }

   public DServiceInfo getServiceInfo() {
      return new DServiceInfo(this.myResourceName, this.myLocalAccessPoint, 1, this.getStatus());
   }
}
