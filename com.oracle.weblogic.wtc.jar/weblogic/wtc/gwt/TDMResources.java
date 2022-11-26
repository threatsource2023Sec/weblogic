package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.ArrayList;
import java.util.Arrays;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCResourcesMBean;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.config.WTCResources;
import weblogic.wtc.jatmi.FldTbl;
import weblogic.wtc.jatmi.PasswordUtils;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.ViewHelper;

public final class TDMResources extends WTCMBeanObject implements BeanUpdateListener {
   static final long serialVersionUID = 7822438947350357255L;
   private static TDMResources myGlobalResources = null;
   private static String key = null;
   private static String encType = null;
   private static String myName = null;
   private String myAppPasswordIV;
   private String myAppPassword;
   private String myTpUsrFile;
   private FldTbl[] myFldTbl16Classes;
   private FldTbl[] myFldTbl32Classes;
   private WTCResourcesMBean mBean;
   private String myRemoteMBEncoding;
   private String myMBEncodingMapFile;
   private boolean registered = false;
   private String[] myFtbl16;
   private String[] myFtbl32;
   private String[] myVtbl16;
   private String[] myVtbl32;

   public TDMResources() {
      setGlobalResources(this);
      this.myAppPasswordIV = null;
      this.myAppPassword = null;
      this.myTpUsrFile = null;
      this.myFldTbl16Classes = null;
      this.myFldTbl32Classes = null;
      this.myRemoteMBEncoding = null;
      this.myMBEncodingMapFile = null;
      this.mBean = null;
      key = System.getProperty("weblogic.wtc.PasswordKey");
      encType = System.getProperty("weblogic.wtc.EncryptionType");
   }

   private static void setGlobalResources(TDMResources toMe) {
      myGlobalResources = toMe;
   }

   public String getAppPasswordIV() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myAppPasswordIV;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setAppPasswordIV(String AppPwdIV) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/setAppPasswordIV/iv=" + AppPwdIV);
      }

      if (AppPwdIV != null && AppPwdIV.length() != 0) {
         this.w.lock();
         this.myAppPasswordIV = AppPwdIV;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setAppPasswordIV/10/set");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setAppPasswordIV/15/DONE");
      }

   }

   public String getAppPassword() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myAppPassword;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setAppPassword(String AppPwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/setAppPassword/pwd=" + AppPwd);
      }

      if (AppPwd != null && AppPwd.length() != 0) {
         this.w.lock();
         this.myAppPassword = AppPwd;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setAppPassword/10/set");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setAppPassword/15/DONE");
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

   public void setTpUsrFile(String TpUsrFile) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (TpUsrFile == null) {
            ntrace.doTrace("[/TDMResources/setTpUsrFile/file=null");
         } else {
            ntrace.doTrace("[/TDMResources/setTpUsrFile/file=" + TpUsrFile);
         }
      }

      if (TpUsrFile != null && TpUsrFile.length() != 0) {
         this.w.lock();
         this.myTpUsrFile = TpUsrFile;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setTpUsrFile/10/set");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setTpUsrFile/20/DONE");
      }

   }

   public String getRemoteMBEncoding() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myRemoteMBEncoding;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setRemoteMBEncoding(String MBEncoding) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (MBEncoding == null) {
            ntrace.doTrace("[/TDMResources/setRemoteMBEncoding/null");
         } else {
            ntrace.doTrace("[/TDMResources/setRemoteMBEncoding/encoding=" + MBEncoding);
         }
      }

      if (MBEncoding != null && MBEncoding.length() != 0) {
         this.w.lock();
         this.myRemoteMBEncoding = MBEncoding;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setRemoteMBEncoding/10/set");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setRemoteMBEncoding/20/DONE");
      }

   }

   public String getMBEncodingMapFile() {
      this.r.lock();

      String var1;
      try {
         var1 = this.myMBEncodingMapFile;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMBEncodingMapFile(String mapFile) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (mapFile == null) {
            ntrace.doTrace("[/TDMResources/setMBEncodingMapFile/null");
         } else {
            ntrace.doTrace("[/TDMResources/setMBEncodingMapFile/mapFile=" + mapFile);
         }
      }

      if (mapFile != null && mapFile.length() != 0) {
         this.w.lock();
         this.myMBEncodingMapFile = mapFile;
         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setMBEncodingMapFile/10/set");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setMBEncodingMapFile/20/DONE");
      }

   }

   public FldTbl[] getFieldTables(boolean type32) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/getFieldTables/type32=" + type32);
      }

      this.r.lock();

      FldTbl[] var3;
      try {
         if (type32) {
            if (traceEnabled) {
               ntrace.doTrace("[/TDMResources/getFieldTables/10/DONE");
            }

            var3 = this.myFldTbl32Classes;
            return var3;
         }

         if (traceEnabled) {
            ntrace.doTrace("[/TDMResources/getFieldTables/20/DONE");
         }

         var3 = this.myFldTbl16Classes;
      } finally {
         this.r.unlock();
      }

      return var3;
   }

   public void setFldTbl32Classes(String[] tbl) throws TPException {
      this.setFieldTables(tbl, true);
   }

   public void setFldTbl16Classes(String[] tbl) throws TPException {
      this.setFieldTables(tbl, false);
   }

   public void setFieldTables(String[] FldTblNames, boolean type32) throws TPException {
      int i = false;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (FldTblNames == null) {
            ntrace.doTrace("[/TDMResources/setFieldTables/FldTblNames=null");
         } else {
            ntrace.doTrace("[/TDMResources/setFieldTables/FldTblNames=" + Arrays.toString(FldTblNames) + " type32=" + type32);
         }
      }

      if (FldTblNames != null && FldTblNames.length != 0) {
         if (type32) {
            this.myFtbl32 = FldTblNames;
         } else {
            this.myFtbl16 = FldTblNames;
         }

         if (traceEnabled) {
            ntrace.doTrace("FldTblNames = " + FldTblNames.length);
         }

         int count = 0;

         int i;
         for(i = 0; i < FldTblNames.length; ++i) {
            if (FldTblNames[i] != null && FldTblNames[i].length() != 0) {
               ++count;
            } else if (traceEnabled) {
               ntrace.doTrace("skip " + i);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("count = " + count);
         }

         if (count == 0) {
            if (type32) {
               this.myFldTbl32Classes = null;
            } else {
               this.myFldTbl16Classes = null;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TDMResources/setFieldTables/20/not set");
            }

         } else {
            this.w.lock();
            if (type32) {
               this.myFldTbl32Classes = new FldTbl[count];
            } else {
               this.myFldTbl16Classes = new FldTbl[count];
            }

            String tblclnm = null;
            int tblcnt = 0;

            for(i = 0; i < FldTblNames.length; ++i) {
               if (FldTblNames[i] != null && FldTblNames[i].length() != 0) {
                  tblclnm = FldTblNames[i];

                  try {
                     Class tblcl = Class.forName(tblclnm);
                     if (type32) {
                        this.myFldTbl32Classes[tblcnt] = (FldTbl)tblcl.newInstance();
                     } else {
                        this.myFldTbl16Classes[tblcnt] = (FldTbl)tblcl.newInstance();
                     }

                     ++tblcnt;
                  } catch (Exception var10) {
                     this.w.unlock();
                     if (traceEnabled) {
                        ntrace.doTrace("/TDMResources/setFieldTables/30/Classloader problem with: " + tblclnm);
                     }

                     throw new TPException(4, "Class loader problem with: " + tblclnm);
                  }
               }
            }

            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/TDMResources/setFieldTables/40/DONE");
            }

         }
      } else {
         if (type32) {
            this.myFldTbl32Classes = null;
         } else {
            this.myFldTbl16Classes = null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setFieldTables/10/not set");
         }

      }
   }

   public void setViewTbl32Classes(String[] ViewTblName) throws TPException {
      this.setViewTables(ViewTblName, true);
   }

   public void setViewTbl16Classes(String[] ViewTblName) throws TPException {
      this.setViewTables(ViewTblName, false);
   }

   public void setViewTables(String[] ViewTblNames, boolean type32) throws TPException {
      int i = false;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         if (ViewTblNames == null) {
            ntrace.doTrace("[/TDMResources/setViewTables/ViewTblNames=null");
         } else {
            ntrace.doTrace("[/TDMResources/setViewTables/ViewTblNames=" + Arrays.toString(ViewTblNames) + " type32=" + type32);
         }
      }

      if (ViewTblNames != null && ViewTblNames.length != 0) {
         if (type32) {
            this.myVtbl32 = ViewTblNames;
         } else {
            this.myVtbl16 = ViewTblNames;
         }

         ViewHelper viewhelper = new ViewHelper();
         ViewHelper viewinst = ViewHelper.getInstance();
         String viewname = null;
         String tblclnm = null;

         for(int i = 0; i < ViewTblNames.length; ++i) {
            if (ViewTblNames[i] != null && ViewTblNames[i].length() != 0) {
               tblclnm = ViewTblNames[i];

               try {
                  viewname = tblclnm.substring(tblclnm.lastIndexOf(46) + 1);
                  viewinst.setViewClass(viewname, tblclnm);
               } catch (Exception var10) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMResources/setViewTables/30/Class loader problem with: " + tblclnm);
                  }

                  throw new TPException(4, "Class loader problem with: " + tblclnm);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setViewTables/40/DONE");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/setViewTables/10/not set");
         }

      }
   }

   public WTCResourcesMBean getMBean() {
      this.r.lock();

      WTCResourcesMBean var1;
      try {
         var1 = this.mBean;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public void setMBean(WTCResourcesMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (mb != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMResources/setMBean/MBeanName=" + mb.getName());
         }

         if (this.mBean != null) {
            if (this.mBean == mb) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TDMResources/setMBean/10/same, no change");
               }

               return;
            }

            this.unregisterListener();
         }

         this.mBean = mb;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/TDMResources/setMBean/MBeanName=null");
         }

         if (this.mBean != null) {
            this.unregisterListener();
            this.mBean = null;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/setMBean/20/DONE");
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      WTCResourcesMBean newBean = (WTCResourcesMBean)event.getProposedBean();
      String tmpAppPwdIV = null;
      String tmpAppPwd = null;
      String tmpTpUsrFile = null;
      String[] tmpFldTbl16 = null;
      String[] tmpFldTbl32 = null;
      String[] tmpViewTbl16 = null;
      String[] tmpViewTbl32 = null;
      String tmpMBEncoding = null;
      String tmpMBEMapFile = null;
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      boolean doUpdate = false;
      boolean doRemove = false;
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/prepareUpdate");
      }

      if (newBean == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMResources/prepareUpdate/10/null MBean");
         }

         throw new BeanUpdateRejectedException("A null MBean for TDMResources class!");
      } else {
         for(int i = 0; i < updates.length; ++i) {
            String key = updates[i].getPropertyName();
            int opType = updates[i].getUpdateType();
            if (traceEnabled) {
               ntrace.doTrace("i = " + i + ", optype = " + opType + ", key = " + key);
            }

            if (opType != 1 && opType != 2) {
               if (opType != 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMResources/prepareUpdate/20/Unknown operation " + opType);
                  }

                  throw new BeanUpdateRejectedException("Unknown operation(" + opType + ") for TDMResources.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("REMOVE operation, deactivate it!");
               }

               this.setMBean((WTCResourcesMBean)null);
               this.myAppPasswordIV = null;
               this.myAppPassword = null;
               this.myTpUsrFile = null;
               this.myFldTbl16Classes = null;
               this.myFldTbl32Classes = null;
               this.myRemoteMBEncoding = null;
               this.myMBEncodingMapFile = null;
               doRemove = true;
            } else {
               if (opType == 2) {
                  doUpdate = true;
               }

               if (key.equals("FldTbl16Classes")) {
                  tmpFldTbl16 = newBean.getFldTbl16Classes();
                  if (traceEnabled) {
                     for(i = 0; i < tmpFldTbl16.length; ++i) {
                        ntrace.doTrace("FldTbl16Class[" + i + "] = " + tmpFldTbl16[i]);
                     }
                  }
               } else if (key.equals("FldTbl32Classes")) {
                  tmpFldTbl32 = newBean.getFldTbl32Classes();
                  if (traceEnabled) {
                     for(i = 0; i < tmpFldTbl32.length; ++i) {
                        ntrace.doTrace("FldTbl32Class[" + i + "] = " + tmpFldTbl32[i]);
                     }
                  }
               } else if (key.equals("ViewTbl16Classes")) {
                  tmpViewTbl16 = newBean.getViewTbl16Classes();
                  if (traceEnabled) {
                     for(i = 0; i < tmpViewTbl16.length; ++i) {
                        ntrace.doTrace("FldView16Class[" + i + "] = " + tmpViewTbl16[i]);
                     }
                  }
               } else if (key.equals("ViewTbl32Classes")) {
                  tmpViewTbl32 = newBean.getViewTbl32Classes();
                  if (traceEnabled) {
                     for(i = 0; i < tmpViewTbl32.length; ++i) {
                        ntrace.doTrace("ViewTbl32Class[" + i + "] = " + tmpViewTbl32[i]);
                     }
                  }
               } else if (key.equals("TpUsrFile")) {
                  tmpTpUsrFile = newBean.getTpUsrFile();
                  if (traceEnabled) {
                     ntrace.doTrace("TpUsrFile: " + tmpTpUsrFile);
                  }
               } else if (key.equals("AppPasswordIV")) {
                  tmpAppPwdIV = newBean.getAppPasswordIV();
                  if (traceEnabled) {
                     ntrace.doTrace("AppPasswordIV: " + tmpAppPwdIV);
                  }
               } else if (key.equals("AppPassword")) {
                  tmpAppPwd = newBean.getAppPassword();
                  if (traceEnabled) {
                     ntrace.doTrace("AppPassword: " + tmpAppPwd);
                  }
               } else if (key.equals("RemoteMBEncoding")) {
                  tmpMBEncoding = newBean.getRemoteMBEncoding();
                  if (traceEnabled) {
                     ntrace.doTrace("RemoteMBEncoding: " + tmpMBEncoding);
                  }
               } else if (key.equals("MBEncodingMapFile")) {
                  tmpMBEMapFile = newBean.getMBEncodingMapFile();
                  if (traceEnabled) {
                     ntrace.doTrace("MBEncodingMapFile: " + tmpMBEMapFile);
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("Unknown attribute " + key + ", ignored!");
               }
            }
         }

         this.w.lock();

         try {
            if (tmpFldTbl16 != null) {
               this.setFieldTables(tmpFldTbl16, false);
            }

            if (tmpFldTbl32 != null) {
               this.setFieldTables(tmpFldTbl32, true);
            }

            if (tmpViewTbl16 != null) {
               this.setViewTables(tmpViewTbl16, false);
            }

            if (tmpViewTbl16 != null) {
               this.setViewTables(tmpViewTbl32, true);
            }

            if (tmpTpUsrFile != null) {
               this.myTpUsrFile = tmpTpUsrFile;
            }

            if (tmpAppPwdIV != null) {
               this.myAppPasswordIV = tmpAppPwdIV;
            }

            if (tmpAppPwd != null) {
               this.myAppPassword = tmpAppPwd;
            }

            if (tmpMBEncoding != null) {
               this.myRemoteMBEncoding = tmpMBEncoding;
            }

            if (tmpMBEMapFile != null) {
               this.myMBEncodingMapFile = tmpMBEMapFile;
            }
         } catch (TPException var19) {
            this.w.unlock();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMResources/prepareUpdate/30/update rejected");
            }

            throw new BeanUpdateRejectedException("Error: " + var19.getMessage());
         }

         if (doUpdate && this.isObjectSuspended()) {
            this.setMBean(newBean);
            this.registerListener();
            this.activateObject();
         }

         this.w.unlock();
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/prepareUpdate/40/DONE");
         }

      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/activeUpdate");
         ntrace.doTrace("]/TDMResources/activeUpdate/10/DONE");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      boolean traceEnabled = ntrace.isTraceEnabled(16);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/rollbackUpdate");
         ntrace.doTrace("]/TDMResources/rollbackUpdate/10/DONE");
      }

   }

   public void registerListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/registerListener");
      }

      if (this.mBean != null && !this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMResources: add Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).addBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/registerListener/10/DONE");
      }

   }

   public void unregisterListener() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/unregisterListener");
      }

      if (this.mBean != null && this.registered) {
         if (traceEnabled) {
            ntrace.doTrace("TDMResources: remove Bean Update Listener: " + this);
         }

         ((AbstractDescriptorBean)this.mBean).removeBeanUpdateListener(this);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TDMResources/unregisterListener/10/DONE");
      }

   }

   public static void setTuxedoConnectorName(String name) {
      myName = name;
   }

   public static String getTuxedoConnectorName() {
      return myName;
   }

   public String[] getViewTbl16Classes() {
      return this.myVtbl16;
   }

   public String[] getViewTbl32Classes() {
      return this.myVtbl32;
   }

   public String[] getFldTbl16Classes() {
      return this.myFtbl16;
   }

   public String[] getFldTbl32Classes() {
      return this.myFtbl32;
   }

   public static TDMResources getGlobalResources() {
      return myGlobalResources;
   }

   public static String getGlobalTpUsrFile() {
      return myGlobalResources == null ? null : myGlobalResources.getTpUsrFile();
   }

   public static String getApplicationPassword() {
      return myGlobalResources == null ? null : PasswordUtils.decryptPassword(key, myGlobalResources.getAppPasswordIV(), myGlobalResources.getAppPassword(), encType);
   }

   public static String getGlobalRemoteMBEncoding() {
      return myGlobalResources == null ? null : myGlobalResources.getRemoteMBEncoding();
   }

   public static String getGlobalMBEncodingMapFile() {
      return myGlobalResources == null ? null : myGlobalResources.getMBEncodingMapFile();
   }

   public static TDMResources create(WTCResourcesMBean mb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMResources/create/");
      }

      myGlobalResources = new TDMResources();
      String[] FtblNames16 = mb.getFldTbl16Classes();
      if (FtblNames16 != null && FtblNames16.length != 0) {
         try {
            myGlobalResources.setFieldTables(FtblNames16, false);
         } catch (TPException var13) {
            Loggable le = WTCLogger.logUEconstructTDMResourcesFTLoggable(var13.getMessage());
            le.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMResources/create/10/fld tbl exception");
            }

            throw new TPException(4, le.getMessage());
         }
      }

      String[] FtblNames32 = mb.getFldTbl32Classes();
      if (FtblNames32 != null && FtblNames32.length != 0) {
         try {
            myGlobalResources.setFieldTables(FtblNames32, true);
         } catch (TPException var16) {
            Loggable le = WTCLogger.logUEconstructTDMResourcesFTLoggable(var16.getMessage());
            le.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMResources/create/20/fld tbl32 exception");
            }

            throw new TPException(4, le.getMessage());
         }
      }

      String[] VtblNames16 = mb.getViewTbl16Classes();
      if (VtblNames16 != null && VtblNames16.length != 0) {
         try {
            myGlobalResources.setViewTables(VtblNames16, false);
         } catch (TPException var14) {
            Loggable lia = WTCLogger.logInvalidMBeanAttrLoggable("ViewTbl16Classes", mb.getName());
            lia.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMResources/create/30/view tbl exception");
            }

            throw new TPException(4, lia.getMessage());
         }
      }

      String[] VtblNames32 = mb.getViewTbl32Classes();
      if (VtblNames32 != null && VtblNames32.length != 0) {
         try {
            myGlobalResources.setViewTables(VtblNames32, true);
         } catch (TPException var15) {
            Loggable lia = WTCLogger.logInvalidMBeanAttrLoggable("ViewTbl32Classes", mb.getName());
            lia.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMResources/create/40/view tbl32 exception");
            }

            throw new TPException(4, lia.getMessage());
         }
      }

      boolean setPwd = false;
      boolean setIv = false;
      String app_pwd = mb.getAppPassword();
      if (app_pwd != null && app_pwd.length() != 0) {
         setPwd = true;
         myGlobalResources.setAppPassword(app_pwd);
         if (traceEnabled) {
            ntrace.doTrace("AppPassword is set.");
         }
      }

      String app_pwdiv = mb.getAppPasswordIV();
      if (app_pwdiv != null && app_pwdiv.length() != 0) {
         setIv = true;
         myGlobalResources.setAppPasswordIV(app_pwdiv);
         if (traceEnabled) {
            ntrace.doTrace("AppPasswordIV is set.");
         }
      }

      if (setIv != setPwd) {
         Loggable lia = WTCLogger.logErrorPasswordInfoLoggable("App");
         lia.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMResources/create/50/inconsistent App Password");
         }

         throw new TPException(4, lia.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("checked App Passwd,PasswdIV.");
         }

         String tpusrfile = mb.getTpUsrFile();
         if (tpusrfile != null) {
            myGlobalResources.setTpUsrFile(tpusrfile);
            if (traceEnabled) {
               ntrace.doTrace("global TpUsrFile =" + tpusrfile);
            }
         } else if (traceEnabled) {
            ntrace.doTrace("global TpUsrFile not set");
         }

         String remoteMBEncoding = mb.getRemoteMBEncoding();
         if (remoteMBEncoding != null) {
            myGlobalResources.setRemoteMBEncoding(remoteMBEncoding);
            if (traceEnabled) {
               ntrace.doTrace("RemoteMBEncoding =" + remoteMBEncoding);
            }
         } else if (traceEnabled) {
            ntrace.doTrace("RemoteMBEncoding not set");
         }

         String MBEncodingMapFile = mb.getMBEncodingMapFile();
         if (MBEncodingMapFile != null) {
            myGlobalResources.setMBEncodingMapFile(MBEncodingMapFile);
            if (traceEnabled) {
               ntrace.doTrace("MBEncodingMapFile =" + MBEncodingMapFile);
            }
         } else if (traceEnabled) {
            ntrace.doTrace("MBEncodingMapFile not set");
         }

         myGlobalResources.setMBean(mb);
         if (traceEnabled) {
            ntrace.doTrace("]/TDMResources/create/60/success");
         }

         return myGlobalResources;
      }
   }

   public ArrayList assembleConfigObjects() throws TPException {
      WTCResources res = new WTCResources();
      res.setFldTbl16Classes(this.myFtbl16);
      res.setFldTbl32Classes(this.myFtbl32);
      res.setViewTbl16Classes(this.myVtbl16);
      res.setViewTbl32Classes(this.myVtbl32);
      res.setAppPassword(getApplicationPassword());
      res.setTpUsrFile(this.myTpUsrFile);
      res.setRemoteMBEncoding(this.myRemoteMBEncoding);
      res.setMBEncodingMapFile(this.myMBEncodingMapFile);
      res.setConfigSource(this);
      this.addConfigObj(res);
      return this.getConfigObjList();
   }
}
