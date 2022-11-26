package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.WTCExportMBean;
import weblogic.wtc.jatmi.TPException;

public final class TDMExport extends WTCMBeanObject implements BeanUpdateListener {
   static final long serialVersionUID = -2363046738928971863L;
   private String myResourceName;
   private String myLocalAccessPoint;
   private TDMLocal myLocalAccessPointObject;
   private String myRemoteName;
   private String myEJBName;
   private String myTargetClass;
   private String myTargetJar;
   private WTCExportMBean mBean = null;
   private boolean registered = false;
   private WTCService myWTC;
   private boolean suspended = false;

   public TDMExport(String ResourceName, TDMLocal LocalAccessPoint) throws Exception {
      if (ResourceName != null && LocalAccessPoint != null && ResourceName.length() != 0) {
         this.myResourceName = ResourceName;
         this.myLocalAccessPoint = LocalAccessPoint.getAccessPoint();
         this.myLocalAccessPointObject = LocalAccessPoint;
         if (this.myRemoteName == null || this.myRemoteName.length() == 0) {
            this.myRemoteName = ResourceName;
         }

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

   public String getEJBName() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myEJBName;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean setEJBName(String EJBName) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/setEJBName/name=" + EJBName);
      }

      if (EJBName != null) {
         this.w.lock();
         this.myEJBName = EJBName;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setEJBName/10/true");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setEJBName/15/false");
         }

         return false;
      }
   }

   public String getTargetJar() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myTargetJar;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean setTargetJar(String targetJar) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/setTargetJar/name=" + targetJar);
      }

      if (targetJar != null) {
         this.w.lock();
         this.myTargetJar = targetJar;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setTargetJar/10/true");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setTargetJar/20/false");
         }

         return false;
      }
   }

   public String getTargetClass() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myTargetClass;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean setTargetClass(String targetClass) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/setTargetClass/name=" + targetClass);
      }

      if (targetClass != null) {
         this.w.lock();
         this.myTargetClass = targetClass;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setTargetClass/10/true");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setTargetClass/20/false");
         }

         return false;
      }
   }

   public String getRemoteName() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemoteName;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public WTCExportMBean getMBean() {
      return this.mBean;
   }

   public void setRemoteName(String RemoteName) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/setRemoteName/name=" + RemoteName);
      }

      if (RemoteName != null && RemoteName.length() != 0) {
         this.w.lock();
         String saveRemoteName = this.myRemoteName;
         this.myRemoteName = RemoteName;
         this.myWTC.changeExportResourceName(this, saveRemoteName);
         this.w.unlock();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMExport/setRemoteName/10/true");
      }

   }

   public void setResourceName(String resource) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (resource != null && resource.length() != 0) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMExport/setResourceName/ResourceName=" + resource);
         }

         this.w.lock();
         this.myResourceName = resource;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setResourceName/20/DONE");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMExport/setResourceName/ResourceName=null");
         }

         throw new TPException(4, "ResourceName may not be null");
      }
   }

   public void setLocalAccessPoint(String accesspnt) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (accesspnt != null) {
            ntrace.doTrace("[/TDMExport/setLocalAccessPoint/" + accesspnt);
         } else {
            ntrace.doTrace("[/TDMExport/setLocalAccessPoint/null");
         }
      }

      if (accesspnt != null && accesspnt.length() != 0) {
         TDMLocal lap = this.myWTC.getLocalDomain(accesspnt);
         this.w.lock();
         this.myLocalAccessPoint = accesspnt;
         this.myLocalAccessPointObject = lap;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setLocalAccessPoint/20/DONE");
         }

      } else {
         throw new TPException(4, "LocalAccessPoint may not be null");
      }
   }

   public void setMBean(WTCExportMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMExport/setMBean/MBeanName=" + mb.getName());
         }

         if (this.mBean != null) {
            if (this.mBean == mb) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMExport/setMBname/same, no change");
               }

               return;
            }

            this.unregisterListener();
         }

         this.mBean = mb;
         if (this.myRemoteName == null && this.myResourceName != null) {
            this.myRemoteName = this.myResourceName;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setMBname/20/change");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMExport/setMBname/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/setMBname/30/");
         }
      }

   }

   public boolean equals(Object compare_with_me) {
      TDMExport cwm = (TDMExport)compare_with_me;
      this.r.lock();
      if (this.myLocalAccessPoint != null && this.myResourceName != null && this.myRemoteName != null && cwm != null && this.myLocalAccessPoint.equals(cwm.getLocalAccessPoint()) && this.myRemoteName.equals(cwm.getRemoteName()) && this.myResourceName.equals(cwm.getResourceName())) {
         this.r.unlock();
         return true;
      } else {
         this.r.unlock();
         return false;
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

      int RemoteCode;
      if (this.myRemoteName == null) {
         RemoteCode = 0;
      } else {
         RemoteCode = this.myRemoteName.hashCode();
      }

      this.r.unlock();
      return ResourceNameCode + LocalAccessPointCode + RemoteCode;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCExportMBean newBean = (WTCExportMBean)event.getProposedBean();
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      String tmpResourceName = null;
      String tmpLocalAccessPoint = null;
      String tmpRemoteName = null;
      String tmpEJBName = null;
      String saveRemoteName = this.myRemoteName;
      boolean useResourceName = false;
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMExport/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMExport!");
      } else {
         String key;
         for(int i = 0; i < updates.length; ++i) {
            key = updates[i].getPropertyName();
            int opType = updates[i].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + i + ", optype = " + opType + ", key = " + key);
            }

            if (opType == 1) {
               if (key.equals("ResourceName")) {
                  tmpResourceName = newBean.getResourceName();
                  if (tmpResourceName.length() == 0) {
                     tmpResourceName = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Resource Name: " + tmpResourceName);
                  }
               } else if (key.equals("LocalAccessPoint")) {
                  tmpLocalAccessPoint = newBean.getLocalAccessPoint();
                  if (tmpLocalAccessPoint.length() == 0) {
                     tmpLocalAccessPoint = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Local AP: " + tmpLocalAccessPoint);
                  }
               } else if (key.equals("RemoteName")) {
                  tmpRemoteName = newBean.getRemoteName();
                  if (tmpRemoteName.length() == 0) {
                     tmpRemoteName = null;
                     useResourceName = true;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("Remote Name: " + tmpRemoteName);
                  }
               } else if (key.equals("EJBName")) {
                  tmpEJBName = newBean.getEJBName();
                  if (tmpEJBName.length() == 0) {
                     tmpEJBName = null;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("EJB Name: " + tmpEJBName);
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("key: " + key + "not supported, ignored!");
               }
            } else if (opType == 2) {
               if (traceEnabled) {
                  ntrace.doTrace("Unexpected ADD operation, ignored!");
               }
            } else if (opType == 3 && traceEnabled) {
               ntrace.doTrace("Unexpected REMOVE operation, ignored!");
            }
         }

         TDMLocalTDomain lapObj = null;
         if (tmpLocalAccessPoint != null && !tmpLocalAccessPoint.equals(this.myLocalAccessPoint)) {
            lapObj = this.myWTC.getLocalDomain(tmpLocalAccessPoint);
            if (lapObj == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMExport/prepareUpdate/30/LAP " + tmpLocalAccessPoint + " is not configured!");
               }

               throw new BeanUpdateRejectedException("Unknow local access point " + tmpLocalAccessPoint);
            }

            key = tmpLocalAccessPoint;
         } else {
            key = this.myLocalAccessPoint;
         }

         if (useResourceName) {
            if (tmpResourceName != null) {
               tmpRemoteName = tmpResourceName;
            } else if (this.myResourceName != null) {
               tmpRemoteName = this.myResourceName;
            }
         }

         if (tmpRemoteName != null) {
            TDMExport lsvc = this.myWTC.getExportedService(tmpRemoteName, key);
            if (lsvc != null && lsvc != this) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMExport/prepareUpdate/40/duplicated rname: " + tmpRemoteName + " for LAP:" + key);
               }

               String svcName = tmpResourceName == null ? this.myResourceName : tmpResourceName;
               throw new BeanUpdateRejectedException("Ambiguous or duplicated exported service: " + svcName + ", remote Name:" + tmpRemoteName);
            }
         }

         this.w.lock();
         if (tmpResourceName != null) {
            this.myResourceName = tmpResourceName;
         }

         if (tmpRemoteName != null) {
            this.myRemoteName = tmpRemoteName;
            if (saveRemoteName != null && !tmpRemoteName.equals(saveRemoteName)) {
               this.myWTC.changeExportResourceName(this, saveRemoteName);
            }
         }

         if (tmpLocalAccessPoint != null) {
            this.myLocalAccessPoint = tmpLocalAccessPoint;
            this.myLocalAccessPointObject = lapObj;
         }

         if (tmpEJBName != null) {
            this.myEJBName = tmpEJBName;
         }

         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/prepareUpdate/50/DONE");
         }

      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/activeUpdate");
         ntrace.doTrace("]/TDMExport/activeUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/rollbackUpdate");
         ntrace.doTrace("]/TDMExport/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMExport: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMExport/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMExport: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMExport/unregisterListener/10/DONE");
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
         ntrace.doTrace("[/TDMExport/getStatus/");
      }

      if (this.suspended) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/getStatus/10/SUSPENDED");
         }

         return 1;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/getStatus/20/AVAILABLE");
         }

         return 3;
      }
   }

   public boolean match(String lap) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMExport/match/lap = " + lap);
      }

      if (this.myLocalAccessPoint.equals(lap)) {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/getResourceName/10/false");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMExport/getResourceName/20/false");
         }

         return false;
      }
   }

   public DServiceInfo getServiceInfo() {
      return new DServiceInfo(this.myResourceName, this.myLocalAccessPoint, 2, this.getStatus());
   }
}
