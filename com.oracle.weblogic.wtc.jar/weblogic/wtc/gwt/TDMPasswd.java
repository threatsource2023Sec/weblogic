package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.WTCPasswordMBean;

public final class TDMPasswd extends WTCMBeanObject implements BeanUpdateListener {
   private String myLocalAccessPoint;
   private String myRemoteAccessPoint;
   private String myLocalIV;
   private String myLocalPasswd;
   private String myRemoteIV;
   private String myRemotePasswd;
   private WTCPasswordMBean mBean = null;
   private boolean registered = false;

   public TDMPasswd(String LocalAccessPoint, String RemoteAccessPoint) throws Exception {
      if (LocalAccessPoint != null && RemoteAccessPoint != null) {
         this.myLocalAccessPoint = LocalAccessPoint;
         this.myRemoteAccessPoint = RemoteAccessPoint;
      } else {
         throw new Exception("LocalAccessPoint and RemoteAccessPoint may not be null");
      }
   }

   public void setLocalAccessPoint(String accesspnt) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (accesspnt != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMPasswd/setLocalAccessPoint/" + accesspnt);
         }

         this.w.lock();
         this.myLocalAccessPoint = accesspnt;
         this.w.unlock();
      } else if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setLocalAccessPoint/null");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMPasswd/setLocalAccessPoint/10");
      }

   }

   public String getLocalAccessPoint() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/getLocalAccessPoint/");
      }

      this.r.lock();

      String var2;
      try {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/getLocalAccessPoint/" + this.myLocalAccessPoint);
         }

         var2 = this.myLocalAccessPoint;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public void setRemoteAccessPoint(String accesspnt) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (accesspnt != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMPasswd/setRemoteAccessPoint/" + accesspnt);
         }

         this.w.lock();
         this.myRemoteAccessPoint = accesspnt;
         this.w.unlock();
      } else if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setRemoteAccessPoint/null");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMPasswd/setRemoteAccessPoint/10");
      }

   }

   public String getRemoteAccessPoint() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/getRemoteAccessPoint/");
      }

      this.r.lock();

      String var2;
      try {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/getRemoteAccessPoint/" + this.myRemoteAccessPoint);
         }

         var2 = this.myRemoteAccessPoint;
      } finally {
         this.r.unlock();
      }

      return var2;
   }

   public boolean equals(Object compare_with_me) {
      TDMPasswd cwm = (TDMPasswd)compare_with_me;
      if (cwm == null) {
         return false;
      } else {
         String localName;
         String remoteName;
         if ((localName = cwm.getLocalAccessPoint()) != null && (remoteName = cwm.getRemoteAccessPoint()) != null) {
            this.r.lock();
            if (this.myLocalAccessPoint != null && this.myRemoteAccessPoint != null && this.myLocalAccessPoint.equals(localName) && this.myRemoteAccessPoint.equals(remoteName)) {
               this.r.unlock();
               return true;
            } else {
               this.r.unlock();
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public TDMRemoteTDomain getVirtualTDomainSession() {
      TDMRemoteTDomain vtd_session = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/getVirtualTDomainSession/");
      }

      this.r.lock();
      if (this.myLocalAccessPoint != null && this.myRemoteAccessPoint != null) {
         vtd_session = WTCService.getWTCService().getVTDomainSession(this.myLocalAccessPoint, this.myRemoteAccessPoint);
      }

      if (traceEnabled) {
         if (vtd_session != null) {
            ntrace.doTrace("]/TDMPasswd/getVirtualTDomainSession/(" + this.myLocalAccessPoint + ", " + this.myRemoteAccessPoint + ") found");
         } else {
            ntrace.doTrace("]/TDMPasswd/getVirtualTDomainSession/(" + this.myLocalAccessPoint + ", " + this.myRemoteAccessPoint + ") not found");
         }
      }

      this.r.unlock();
      return vtd_session;
   }

   public String getLocalPasswordIV() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myLocalIV;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setLocalPasswordIV(String LocalIV) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setLocalIV/iv=" + LocalIV);
      }

      this.w.lock();
      if (LocalIV != null) {
         this.myLocalIV = LocalIV;
         if (this.myRemoteIV == null) {
            this.myRemoteIV = LocalIV;
         }

         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setLocalIV/10/changed");
         }

      } else {
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setLocalIV/15/no change");
         }

      }
   }

   public String getLocalPassword() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myLocalPasswd;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setLocalPassword(String LocalPasswd) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setLocalPassword/pwd=" + LocalPasswd);
      }

      if (LocalPasswd != null) {
         this.w.lock();
         this.myLocalPasswd = LocalPasswd;
         if (this.myRemotePasswd == null) {
            this.myRemotePasswd = LocalPasswd;
         }

         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setLocalPassword/10/changed");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setLocalPassword/15/no change");
         }

      }
   }

   public String getRemotePasswordIV() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemoteIV;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public String getRemoteIV() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemoteIV;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setRemotePasswordIV(String RemoteIV) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setRemoteIV/iv=" + RemoteIV);
      }

      if (RemoteIV != null) {
         this.w.lock();
         this.myRemoteIV = RemoteIV;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setRemoteIV/10/changed");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setRemoteIV/15/no change");
         }

      }
   }

   public String getRemotePassword() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemotePasswd;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean setRemotePassword(String RemotePasswd) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/setRemotePassword/pwd=" + RemotePasswd);
      }

      if (RemotePasswd != null) {
         this.w.lock();
         this.myRemotePasswd = RemotePasswd;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setRemotePassword/10/changed");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMPasswd/setRemotePassword/20/no change");
         }

         return false;
      }
   }

   public WTCPasswordMBean getMBean() {
      return this.mBean;
   }

   public void setMBean(WTCPasswordMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMPasswd/setMBname/MBeanName=" + mb.getName());
         }

         if (this.mBean == mb) {
            if (traceEnabled) {
               ntrace.doTrace("]/TDMPasswd/setMBname/same, no change");
            }

            return;
         }

         if (this.mBean != null) {
            this.unregisterListener();
         }

         this.mBean = mb;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMPasswd/setMBname/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMPasswd/setMBname/20/DONE");
      }

   }

   public int hashCode() {
      this.r.lock();
      int LocalAccessPointCode;
      if (this.myLocalAccessPoint == null) {
         LocalAccessPointCode = 0;
      } else {
         LocalAccessPointCode = this.myLocalAccessPoint.hashCode();
      }

      int RemoteAccessPointCode;
      if (this.myRemoteAccessPoint == null) {
         RemoteAccessPointCode = 0;
      } else {
         RemoteAccessPointCode = this.myRemoteAccessPoint.hashCode();
      }

      this.r.unlock();
      return LocalAccessPointCode + RemoteAccessPointCode;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCPasswordMBean newBean = (WTCPasswordMBean)event.getProposedBean();
      String tmpLAP = null;
      String tmpRAP = null;
      String tmpLPwdIV = null;
      String tmpLPwd = null;
      String tmpRPwdIV = null;
      String tmpRPwd = null;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMPasswd/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMPassword!");
      } else {
         String key;
         for(int i = 0; i < updates.length; ++i) {
            key = updates[i].getPropertyName();
            int opType = updates[i].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + i + ", optype = " + opType + ", key = " + key);
            }

            if (opType == 1) {
               if (key.equals("LocalAccessPoint")) {
                  tmpLAP = newBean.getLocalAccessPoint();
                  if (traceEnabled) {
                     ntrace.doTrace("LocalAccessPoint: " + tmpLAP);
                  }
               } else if (key.equals("RemoteAccessPoint")) {
                  tmpRAP = newBean.getRemoteAccessPoint();
                  if (traceEnabled) {
                     ntrace.doTrace("RemoteAccessPoint: " + tmpRAP);
                  }
               } else if (key.equals("LocalPasswordIV")) {
                  tmpLPwdIV = newBean.getLocalPasswordIV();
                  if (traceEnabled) {
                     ntrace.doTrace("LocalPasswordIV: " + tmpLPwdIV);
                  }
               } else if (key.equals("LocalPassword")) {
                  tmpLPwd = newBean.getLocalPassword();
                  if (traceEnabled) {
                     ntrace.doTrace("LocalPassword: " + tmpLPwd);
                  }
               } else if (key.equals("RemotePasswordIV")) {
                  tmpRPwdIV = newBean.getRemotePasswordIV();
                  if (traceEnabled) {
                     ntrace.doTrace("RemotePasswordIV: " + tmpRPwdIV);
                  }
               } else if (key.equals("RemotePassword")) {
                  tmpRPwd = newBean.getRemotePassword();
                  if (traceEnabled) {
                     ntrace.doTrace("RemotePassword: " + tmpRPwd);
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("Key: " + key + " not supported, ignored!");
               }
            } else if (opType == 2) {
               if (traceEnabled) {
                  ntrace.doTrace("Unexpected ADD operation, ignored!");
               }
            } else {
               if (opType != 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMPasswd/prepareUpdate/20/Unknown operation " + opType);
                  }

                  throw new BeanUpdateRejectedException("Unknown operation(" + opType + ") for TDMPasswd.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("Unexpected REMOVE operation, ignored!");
               }
            }
         }

         String lap;
         if (tmpLAP != null) {
            lap = tmpLAP;
         } else {
            lap = this.myLocalAccessPoint;
         }

         if (tmpRAP != null) {
            key = tmpRAP;
         } else {
            key = this.myRemoteAccessPoint;
         }

         if ((tmpLAP != null || tmpRAP != null) && WTCService.getWTCService().getVTDomainSession(lap, key) == null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMPasswd/prepareUpdate/30/TDomSession(" + lap + ", " + key + ") is not defined!");
            }

            throw new BeanUpdateRejectedException("TDomain Session(" + lap + ", " + key + ") is not defined!");
         } else {
            this.w.lock();
            if (tmpLAP != null) {
               this.myLocalAccessPoint = tmpLAP;
            }

            if (tmpRAP != null) {
               this.myRemoteAccessPoint = tmpRAP;
            }

            if (tmpLPwd != null) {
               this.myLocalPasswd = tmpLPwd;
            }

            if (tmpLPwdIV != null) {
               this.myLocalIV = tmpLPwdIV;
            }

            if (tmpRPwd != null) {
               this.myRemotePasswd = tmpRPwd;
            }

            if (tmpRPwdIV != null) {
               this.myRemoteIV = tmpRPwdIV;
            }

            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMPasswd/prepareUpdate/40/DONE");
            }

         }
      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/activeUpdate");
         ntrace.doTrace("]/TDMPasswd/activeUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/rollbackUpdate");
         ntrace.doTrace("]/TDMPasswd/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMPasswd: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMPasswd/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMPasswd/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMPasswd: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMPasswd/unregisterListener/10/DONE");
      }

   }
}
