package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WTCRuntimeMBean;
import weblogic.management.runtime.WTCStatisticsRuntimeMBean;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.gwatmi;

public class WTCStatisticsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WTCStatisticsRuntimeMBean {
   private WTCService wtcRT = WTCService.getWTCService();
   private WTCStatisticsComponent[] conn_stat_list = null;
   private Hashtable im_svc_stat_list = null;
   private Hashtable ex_svc_stat_list = null;
   private static final String[] CONN_STAT_ITEM_NAMES = new String[]{"CONNECTION_PAIR", "CONNECTION_STATUS", "INBOUND_MESSAGE_NUMBER_TOTAL", "OUTBOUND_MESSAGE_NUMBER_TOTAL", "INBOUND_NETWORK_MESSAGE_SIZE_TOTAL", "OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL", "OUTSTANDING_NETWORK_REQUEST_NUMBER", "INBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL", "OUTBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL", "INBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL", "OUTBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL", "RESET_TIMESTAMP", "CURRENT_TIMESTAMP"};
   private static final OpenType[] CONN_STAT_ITEM_TYPE;
   private static final String[] CONN_STAT_TABL_INDEX;
   private static final String[] EX_SVC_STAT_ITEM_NAMES;
   private static final String[] EX_SVC_STAT_TABL_INDEX;
   private static final OpenType[] EX_SVC_STAT_ITEM_TYPE;
   private static final String[] IM_SVC_STAT_ITEM_NAMES;
   private static final OpenType[] IM_SVC_STAT_ITEM_TYPE;
   private static final String[] IM_SVC_STAT_TABL_INDEX;
   private static CompositeType CONN_STAT_COMP_TYPE;
   private static TabularType CONN_STAT_TABL_TYPE;
   private static CompositeType EX_SVC_STAT_COMP_TYPE;
   private static TabularType EX_SVC_STAT_TABL_TYPE;
   private static CompositeType IM_SVC_STAT_COMP_TYPE;
   private static TabularType IM_SVC_STAT_TABL_TYPE;

   public WTCStatisticsRuntimeMBeanImpl(WTCRuntimeMBean parent) throws ManagementException {
      super("WTCStatisticsRuntimeMBean", parent);
   }

   public void initialize(TDMRemoteTDomain[] rtd_list) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      int rtdcnt = rtd_list.length;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/WTC connections");
      }

      if (rtdcnt != 0) {
         this.conn_stat_list = new WTCStatisticsComponent[rtdcnt];
      }

      for(int idx = 0; idx < rtdcnt; ++idx) {
         TDMLocal ldom = rtd_list[idx].getLocalAccessPointObject();
         TDMRemoteTDomain rdom = rtd_list[idx];
         WTCStatisticsComponent stat = new WTCStatisticsComponent(ldom.getAccessPointId(), rdom.getAccessPointId());
         this.conn_stat_list[idx] = stat;
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/10/created " + rtdcnt + " WTCStatisticsComponent for WTC connections");
      }

   }

   public void initialize(TDMImport[] svc_list) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      int svc_cnt = svc_list.length;
      int comp_cnt = 0;
      if (this.im_svc_stat_list == null) {
         this.im_svc_stat_list = new Hashtable();
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/WTC Imported Services");
      }

      for(int idx = 0; idx < svc_cnt; ++idx) {
         String rsvcName = svc_list[idx].getResourceName();
         HashSet hs;
         if ((hs = (HashSet)this.im_svc_stat_list.get(rsvcName)) == null) {
            String n = new String(rsvcName);
            hs = new HashSet();
            this.im_svc_stat_list.put(n, hs);
            ++comp_cnt;
         }

         WTCStatisticsComponent comp = new WTCStatisticsComponent(svc_list[idx]);
         if (!hs.add(comp)) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/initialize/10/failed to add resource " + svc_list[idx].getResourceName());
            }

            return;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/10/created " + comp_cnt + " WTCStatisticsComponent for WTC imported services");
      }

   }

   public void initialize(TDMExport[] svc_list) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      int svc_cnt = svc_list.length;
      int comp_cnt = 0;
      if (this.ex_svc_stat_list == null) {
         this.ex_svc_stat_list = new Hashtable();
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/WTC Exported Services");
      }

      for(int idx = 0; idx < svc_cnt; ++idx) {
         String rsName = svc_list[idx].getResourceName();
         HashSet hs;
         if ((hs = (HashSet)this.ex_svc_stat_list.get(rsName)) == null) {
            String n = new String(rsName);
            hs = new HashSet();
            this.ex_svc_stat_list.put(n, hs);
            ++comp_cnt;
         }

         WTCStatisticsComponent comp = new WTCStatisticsComponent(svc_list[idx]);
         if (!hs.add(comp)) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/initialize/10/failed to add resource " + svc_list[idx].getResourceName());
            }

            return;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/initialize/10/created " + comp_cnt + " WTCStatisticsComponent for WTC exported services");
      }

   }

   public synchronized void addComp(TDMRemoteTDomain rdom) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/addComp/add WTCStatisticsComponent for RDOM " + rdom.getAccessPointId());
      }

      TDMLocal ldom = rdom.getLocalAccessPointObject();
      int rtdcnt = 0;
      if (this.conn_stat_list != null) {
         rtdcnt = this.conn_stat_list.length;

         int i;
         for(i = 0; i < this.conn_stat_list.length && (!this.conn_stat_list[i].getLDomAccessPointId().equals(ldom.getAccessPointId()) || !this.conn_stat_list[i].getRDomAccessPointId().equals(rdom.getAccessPointId())); ++i) {
         }

         if (i < this.conn_stat_list.length) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/addComp/WTC connection " + ldom.getAccessPointId() + ":" + rdom.getAccessPointId() + " exist.");
            }

            return;
         }
      }

      WTCStatisticsComponent[] oldstat_list = this.conn_stat_list;
      this.conn_stat_list = new WTCStatisticsComponent[rtdcnt + 1];
      if (rtdcnt > 0) {
         System.arraycopy(oldstat_list, 0, this.conn_stat_list, 0, rtdcnt);
      }

      WTCStatisticsComponent stat = new WTCStatisticsComponent(ldom.getAccessPointId(), rdom.getAccessPointId());
      this.conn_stat_list[rtdcnt] = stat;
      if (traceEnabled) {
         ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/addComp/" + ldom.getAccessPointId() + "_" + rdom.getAccessPointId() + "/10/Success");
      }

   }

   public synchronized void addComp(TDMImport svc) {
      String rsvcName = svc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/addComp/add WTCStatisticsComponent for imported service " + svc.getResourceName() + " (Remote name: " + svc.getRemoteName() + ")");
      }

      if (this.im_svc_stat_list == null) {
         this.im_svc_stat_list = new Hashtable();
      }

      HashSet hs;
      if ((hs = (HashSet)this.im_svc_stat_list.get(rsvcName)) == null) {
         String n = new String(rsvcName);
         hs = new HashSet();
         this.im_svc_stat_list.put(n, hs);
      }

      WTCStatisticsComponent comp = new WTCStatisticsComponent(svc);
      if (!hs.add(comp)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/addComp/10/failed to add imported service " + svc.getResourceName());
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/addComp/10/Success");
         }

      }
   }

   public synchronized void addComp(TDMExport svc) {
      String rsName = svc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/addComp/add WTCStatisticsComponent for exported service " + svc.getResourceName() + " (Remote name:" + svc.getRemoteName() + ")");
      }

      if (this.ex_svc_stat_list == null) {
         this.ex_svc_stat_list = new Hashtable();
      }

      HashSet hs;
      if ((hs = (HashSet)this.ex_svc_stat_list.get(rsName)) == null) {
         String n = new String(rsName);
         hs = new HashSet();
         this.ex_svc_stat_list.put(n, hs);
      }

      WTCStatisticsComponent comp = new WTCStatisticsComponent(svc);
      if (!hs.add(comp)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/addComp/10/failed to add exported service " + svc.getResourceName());
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/addComp/10/Success");
         }

      }
   }

   public synchronized void removeComp(TDMRemoteTDomain rdom) {
      int idx = false;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/remove WTCStatisticsComponent for RDOM " + rdom.getAccessPointId());
      }

      int compcnt = this.conn_stat_list.length;
      TDMLocal ldom = rdom.getLocalAccessPointObject();

      int idx;
      for(idx = 0; idx < compcnt && (!this.conn_stat_list[idx].getLDomAccessPointId().equals(ldom.getAccessPointId()) || !this.conn_stat_list[idx].getRDomAccessPointId().equals(rdom.getAccessPointId())); ++idx) {
      }

      if (idx >= compcnt) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removeRemoteTuxDom/40/statistics component not found");
         }

      } else {
         int new_compcnt = compcnt - 1;
         if (new_compcnt > 0) {
            WTCStatisticsComponent[] newstat_list = new WTCStatisticsComponent[compcnt - 1];
            this.removeFromArray(this.conn_stat_list, compcnt, newstat_list, idx);
            this.conn_stat_list = newstat_list;
         } else {
            this.conn_stat_list = null;
         }

         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/10/Success");
         }

      }
   }

   public synchronized void removeComp(TDMImport svc) {
      String rsvcName = svc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/remove WTCStatisticsComponent for imported service " + svc.getResourceName() + " (Remote name: " + svc.getRemoteName() + ")");
      }

      HashSet hs = (HashSet)this.im_svc_stat_list.get(rsvcName);
      TDMImport registeredExp = null;
      WTCStatisticsComponent removeMe = null;
      if (null != hs) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            WTCStatisticsComponent comp = (WTCStatisticsComponent)iter.next();
            registeredExp = comp.getImport();
            if (registeredExp == svc) {
               removeMe = comp;
               break;
            }
         }
      }

      if (registeredExp != svc) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/removeComp/10/not found");
         }

      } else {
         if (removeMe != null) {
            hs.remove(removeMe);
         }

         if (hs.isEmpty()) {
            this.im_svc_stat_list.remove(rsvcName);
         }

         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/10/Success");
         }

      }
   }

   public synchronized void removeComp(TDMExport svc) {
      String rsName = svc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/remove WTCStatisticsComponent for exported service " + svc.getResourceName() + " (Remote name: " + svc.getRemoteName() + ")");
      }

      HashSet hs = (HashSet)this.ex_svc_stat_list.get(rsName);
      TDMExport registeredExp = null;
      WTCStatisticsComponent removeMe = null;
      if (null != hs) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            WTCStatisticsComponent comp = (WTCStatisticsComponent)iter.next();
            registeredExp = comp.getExport();
            if (registeredExp == svc) {
               removeMe = comp;
               break;
            }
         }
      }

      if (registeredExp != svc) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/removeComp/10/not found");
         }

      } else {
         if (removeMe != null) {
            hs.remove(removeMe);
         }

         if (hs.isEmpty()) {
            this.ex_svc_stat_list.remove(rsName);
         }

         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/removeComp/10/Success");
         }

      }
   }

   private void removeFromArray(Object oldList, int oldCnt, Object newList, int index) {
      if (index > 0) {
         System.arraycopy(oldList, 0, newList, 0, index);
      }

      if (index < oldCnt - 1) {
         System.arraycopy(oldList, index + 1, newList, index, oldCnt - index - 1);
      }

   }

   public String convertRAPList(String[] raplist) {
      int len = raplist.length;
      String val = raplist[0];

      for(int i = 1; i < len; ++i) {
         val = val + "," + raplist[i];
      }

      return val;
   }

   public long getInboundMessageTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getInboundMessageCount();
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setInboundMessageTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setInboundMessageCount(num);
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/10/SUCCESS");
         }

      }
   }

   public void updInboundMessageTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundMessageTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundMessageTotalCount(ldom, rdom);
         val += num;
         this.setInboundMessageTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundMessageTotalCount/INBOUND_MESSAGE_NUMBER_TOTAL(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getInboundMessageTotalCount(String svcName, String lap, boolean isImport) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/for imported svc = " + svcName + ", return 0");
         }

         return val;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/exported svc = " + svcName);
         }

         if (this.ex_svc_stat_list == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/WTC exported services statistics list is empty");
            }

            return 0L;
         } else {
            HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            WTCStatisticsComponent comp = null;
            if (null != hs) {
               Iterator iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/found match (" + svcName + "," + lap + ")");
                     }

                     val = comp.getInboundMessageCount();
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundMessageTotalCount/10/ret = " + val);
            }

            return val;
         }
      }
   }

   public void setInboundMessageTotalCount(String svcName, String lap, boolean isImport, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/for imported svc = " + svcName + ", no need to monitor inbound message");
         }

      } else if (this.ex_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/WTC exported services statistics list is empty");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/exported svc = " + svcName + ", new number = " + num);
         }

         HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMExport exp = comp.getExport();
               if (exp.match(lap)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/found match (" + svcName + "," + lap + ")");
                  }

                  comp.setInboundMessageCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/SUCCESS");
         }

      }
   }

   public void updInboundMessageTotalCount(TDMExport exp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundMessageTotalCount/");
      }

      String svcname = exp.getResourceName();
      String lap = exp.getLocalAccessPoint();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundMessageTotalCount(svcname, lap, false);
         val += num;
         this.setInboundMessageTotalCount(svcname, lap, false, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundMessageTotalCount/INBOUND_MESSAGE_NUMBER_TOTAL(" + svcname + ":" + lap + ") = " + val + "]");
      }

   }

   public long getInboundSuccessReqTotalCount(String svcName, String lap, boolean isImport) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundSuccessReqTotalCount/for imported svc = " + svcName + ", return 0");
         }

         return val;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundSuccessReqTotalCount/exported svc = " + svcName);
         }

         if (this.ex_svc_stat_list == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundSuccessReqTotalCount/WTC exported services statistics list is empty");
            }

            return 0L;
         } else {
            HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            WTCStatisticsComponent comp = null;
            if (null != hs) {
               Iterator iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundSuccessReqTotalCount/found match (" + svcName + "," + lap + ")");
                     }

                     val = comp.getInboundSuccessReqCount();
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundSuccessReqTotalCount/10/ret = " + val);
            }

            return val;
         }
      }
   }

   public void setInboundSuccessReqTotalCount(String svcName, String lap, boolean isImport, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundSuccessReqTotalCount/for imported svc = " + svcName + ", no need to monitor inbound message");
         }

      } else if (this.ex_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundSuccessReqTotalCount/WTC exported services statistics list is empty");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundSuccessReqTotalCount/exported svc = " + svcName + ", new number = " + num);
         }

         HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMExport exp = comp.getExport();
               if (exp.match(lap)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundMessageTotalCount/found match (" + svcName + "," + lap + ")");
                  }

                  comp.setInboundSuccessReqCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundSuccessReqTotalCount/SUCCESS");
         }

      }
   }

   public void updInboundSuccessReqTotalCount(TDMExport exp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundSuccessReqTotalCount/");
      }

      String svcname = exp.getResourceName();
      String lap = exp.getLocalAccessPoint();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundSuccessReqTotalCount(svcname, lap, false);
         val += num;
         this.setInboundSuccessReqTotalCount(svcname, lap, false, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundSuccessReqTotalCount/INBOUND_SUCCESS_REQ_TOTAL(" + svcname + ":" + lap + ") = " + val + "]");
      }

   }

   public long getInboundFailReqTotalCount(String svcName, String lap, boolean isImport) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundFailReqTotalCount/for imported svc = " + svcName + ", return 0");
         }

         return val;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundFailReqTotalCount/exported svc = " + svcName);
         }

         if (this.ex_svc_stat_list == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundFailReqTotalCount/WTC exported services statistics list is empty");
            }

            return 0L;
         } else {
            HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            WTCStatisticsComponent comp = null;
            if (null != hs) {
               Iterator iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundFailReqTotalCount/found match (" + svcName + "," + lap + ")");
                     }

                     val = comp.getInboundFailReqCount();
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundFailReqTotalCount/10/ret = " + val);
            }

            return val;
         }
      }
   }

   public void setInboundFailReqTotalCount(String svcName, String lap, boolean isImport, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundFailReqTotalCount/for imported svc = " + svcName + ", no need to monitor inbound message");
         }

      } else if (this.ex_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundFailReqTotalCount/WTC exported services statistics list is empty");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundFailReqTotalCount/exported svc = " + svcName + ", new number = " + num);
         }

         HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMExport exp = comp.getExport();
               if (exp.match(lap)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundFailReqTotalCount/found match (" + svcName + "," + lap + ")");
                  }

                  comp.setInboundFailReqCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundFailReqTotalCount/SUCCESS");
         }

      }
   }

   public void updInboundFailReqTotalCount(TDMExport exp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundFailReqTotalCount/");
      }

      String svcname = exp.getResourceName();
      String lap = exp.getLocalAccessPoint();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundFailReqTotalCount(svcname, lap, false);
         val += num;
         this.setInboundFailReqTotalCount(svcname, lap, false, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundFailReqTotalCount/INBOUND_FAIL_REQ_TOTAL(" + svcname + ":" + lap + ") = " + val + "]");
      }

   }

   public long getOutboundMessageTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/found match");
               }

               val = this.conn_stat_list[i].getOutboundMessageCount();
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundMessageTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/found match");
               }

               this.conn_stat_list[i].setOutboundMessageCount(num);
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/SUCCESS");
         }

      }
   }

   public void updOutboundMessageTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundMessageTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundMessageTotalCount(ldom, rdom);
         val += num;
         this.setOutboundMessageTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundMessageTotalCount/OUTBOUND_MESSAGE_NUMBER_TOTAL(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getOutboundMessageTotalCount(String svcName, String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/imported svc = " + svcName);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/WTC imported services statistics list is empty");
         }

         return 0L;
      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  val = comp.getOutboundMessageCount();
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundMessageTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundMessageTotalCount(String svcName, String lap, String raplist, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/imported svc = " + svcName + ", new number = " + num);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/WTC imported services statistics list is empty");
         }

      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  comp.setOutboundMessageCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundMessageTotalCount/SUCCESS");
         }

      }
   }

   public void updOutboundMessageTotalCount(TDMImport imp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundMessageTotalCount/");
      }

      String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundMessageTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist);
         val += num;
         this.setOutboundMessageTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/updOutboundMessageTotalCount/OUTBOUND_MESSAGE_NUMBER_TOTAL(" + imp.getResourceName() + ":" + imp.getLocalAccessPoint() + ":" + raplist + ") = " + val);
      }

   }

   public long getOutboundSuccessReqTotalCount(String svcName, String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundSuccessReqTotalCount/imported svc = " + svcName);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundSuccessReqTotalCount/WTC imported services statistics list is empty");
         }

         return 0L;
      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundSuccessReqTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  val = comp.getOutboundSuccessReqCount();
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundSuccessReqTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundSuccessReqTotalCount(String svcName, String lap, String raplist, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundSuccessReqTotalCount/imported svc = " + svcName + ", new number = " + num);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundSuccessReqTotalCount/WTC imported services statistics list is empty");
         }

      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundSuccessReqTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  comp.setOutboundSuccessReqCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundSuccessReqTotalCount/SUCCESS");
         }

      }
   }

   public void updOutboundSuccessReqTotalCount(TDMImport imp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundSuccessReqTotalCount/");
      }

      String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundSuccessReqTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist);
         val += num;
         this.setOutboundSuccessReqTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/updOutboundSuccessReqTotalCount/OUTBOUND_SUCCESS_REQ_TOTAL(" + imp.getResourceName() + ":" + imp.getLocalAccessPoint() + ":" + raplist + ") = " + val);
      }

   }

   public long getOutboundFailReqTotalCount(String svcName, String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundFailReqTotalCount/imported svc = " + svcName);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundFailReqTotalCount/WTC imported services statistics list is empty");
         }

         return 0L;
      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundFailReqTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  val = comp.getOutboundFailReqCount();
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundFailReqTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundFailReqTotalCount(String svcName, String lap, String raplist, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundFailReqTotalCount/imported svc = " + svcName + ", new number = " + num);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundFailReqTotalCount/WTC imported services statistics list is empty");
         }

      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundFailReqTotalCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  comp.setOutboundFailReqCount(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundFailReqTotalCount/SUCCESS");
         }

      }
   }

   public void updOutboundFailReqTotalCount(TDMImport imp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundFailReqTotalCount/");
      }

      String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundFailReqTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist);
         val += num;
         this.setOutboundFailReqTotalCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/updOutboundFailReqTotalCount/OUTBOUND_FAIL_REQ_TOTAL(" + imp.getResourceName() + ":" + imp.getLocalAccessPoint() + ":" + raplist + ") = " + val);
      }

   }

   public long getInboundNWMessageTotalSize(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getInboundNWMessageSize();
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/10/ret = " + val);
         }

         return val;
      }
   }

   public void setInboundNWMessageTotalSize(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setInboundNWMessageSize(num);
               break;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/SUCCESS");
         }

      }
   }

   public void updInboundNWMessageTotalSize(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundNWMessageTotalSize/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundNWMessageTotalSize(ldom, rdom);
         val += num;
         this.setInboundNWMessageTotalSize(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundNWMessageTotalSize/INBOUND_MESSAGE_TOTAL_SIZE(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getInboundNWMessageTotalSize(String svcName, String lap, boolean isImport) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/for imported svc = " + svcName + ", return 0");
         }

         return val;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/exported svc = " + svcName);
         }

         if (this.ex_svc_stat_list == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/WTC exported services statistics list is empty");
            }

            return 0L;
         } else {
            HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            WTCStatisticsComponent comp = null;
            if (null != hs) {
               Iterator iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/found match (" + svcName + "," + lap + ")");
                     }

                     val = comp.getInboundNWMessageSize();
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/10/ret = " + val);
            }

            return val;
         }
      }
   }

   public void setInboundNWMessageTotalSize(String svcName, String lap, boolean isImport, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (isImport) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/for imported svc = " + svcName + ", return");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/exported svc = " + svcName + ", new number = " + num);
         }

         if (this.ex_svc_stat_list == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/WTC exported services statistics list is empty");
            }

         } else {
            HashSet hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            WTCStatisticsComponent comp = null;
            if (null != hs) {
               Iterator iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/found match (" + svcName + "," + lap + ")");
                     }

                     comp.setInboundNWMessageSize(num);
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInboundNWMessageTotalSize/SUCCESS");
            }

         }
      }
   }

   public void updInboundNWMessageTotalSize(TDMExport exp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundNWMessageTotalSize/");
      }

      String svcname = exp.getResourceName();
      String lap = exp.getLocalAccessPoint();
      long val = 0L;
      synchronized(this) {
         val = this.getInboundNWMessageTotalSize(svcname, lap, false);
         val += num;
         this.setInboundNWMessageTotalSize(svcname, lap, false, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInboundNWMessageTotalSize/INBOUND_NETWORK_MESSAGE_SIZE_TOTAL(" + svcname + ":" + lap + ") = " + val + "]");
      }

   }

   public long getOutboundNWMessageTotalSize(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getOutboundNWMessageSize();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundNWMessageTotalSize(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setOutboundNWMessageSize(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/SUCCESS");
         }

      }
   }

   public void updOutboundNWMessageTotalSize(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundNWMessageTotalSize/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundNWMessageTotalSize(ldom, rdom);
         val += num;
         this.setOutboundNWMessageTotalSize(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundNWMessageTotalSize/OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getOutboundNWMessageTotalSize(String svcName, String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/imported svc = " + svcName);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/WTC imported services statistics list is empty");
         }

         return 0L;
      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutboundNWMessageTotalSize/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  val = comp.getOutboundNWMessageSize();
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInboundNWMessageTotalSize/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutboundNWMessageTotalSize(String svcName, String lap, String raplist, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/imported svc = " + svcName + ",new number = " + num);
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/WTC imported services statistics list is empty");
         }

      } else {
         HashSet hs = (HashSet)this.im_svc_stat_list.get(svcName);
         WTCStatisticsComponent comp = null;
         if (null != hs) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               comp = (WTCStatisticsComponent)iter.next();
               TDMImport imp = comp.getImport();
               if (imp.match(lap, raplist)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                  }

                  comp.setOutboundNWMessageSize(num);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutboundNWMessageTotalSize/SUCCESS");
         }

      }
   }

   public void updOutboundNWMessageTotalSize(TDMImport imp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundNWMessageTotalSize/");
      }

      String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
      long val = 0L;
      synchronized(this) {
         val = this.getOutboundNWMessageTotalSize(imp.getResourceName(), imp.getLocalAccessPoint(), raplist);
         val += num;
         this.setOutboundNWMessageTotalSize(imp.getResourceName(), imp.getLocalAccessPoint(), raplist, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutboundNWMessageTotalSize/OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL(" + imp.getResourceName() + ":" + imp.getLocalAccessPoint() + ":" + raplist + ") = " + val + "]");
      }

   }

   public long getOutstandingNWReqCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getOutstandingNWReqCount();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutstandingNWReqCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setOutstandingNWReqCount(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/SUCCESS");
         }

      }
   }

   public void updOutstandingNWReqCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutstandingNWReqCount(ldom, rdom);
         val += num;
         this.setOutstandingNWReqCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/OUTSTANDING_NETWORK_REQUEST_NUMBER (" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public void updOutstandingNWReqCount(gwatmi myAtmi, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/");
      }

      dsession ss = (dsession)myAtmi;
      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutstandingNWReqCount(ldom, rdom);
         val += num;
         this.setOutstandingNWReqCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/OUTSTANDING_NETWORK_REQUEST_NUMBER (" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getOutstandingNWReqCount(String svcName, String lap, String raplist) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      boolean isImport = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/svc = " + svcName + ", lap = " + lap + ", raplist = " + raplist);
      }

      if (raplist != null) {
         isImport = true;
      }

      if ((!isImport || this.im_svc_stat_list != null) && (isImport || this.ex_svc_stat_list != null)) {
         HashSet hs;
         WTCStatisticsComponent comp;
         Iterator iter;
         if (isImport) {
            hs = (HashSet)this.im_svc_stat_list.get(svcName);
            comp = null;
            if (null != hs) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMImport imp = comp.getImport();
                  if (imp.match(lap, raplist)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                     }

                     val = comp.getOutstandingNWReqCount();
                  }
               }
            }
         } else {
            hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            comp = null;
            if (null != hs) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/found match (" + svcName + "/" + lap + ")");
                     }

                     val = comp.getOutstandingNWReqCount();
                  }
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/10/ret = " + val);
         }

         return val;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutstandingNWReqCount/WTC services statistics list is empty");
         }

         return 0L;
      }
   }

   public void setOutstandingNWReqCount(String svcName, String lap, String raplist, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      boolean isImport = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/svc = " + svcName + ", lap = " + lap + ", raplist = " + raplist + ", new number = " + num);
      }

      if (raplist != null) {
         isImport = true;
      }

      if ((!isImport || this.im_svc_stat_list != null) && (isImport || this.ex_svc_stat_list != null)) {
         HashSet hs;
         WTCStatisticsComponent comp;
         Iterator iter;
         if (isImport) {
            hs = (HashSet)this.im_svc_stat_list.get(svcName);
            comp = null;
            if (null != hs) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMImport imp = comp.getImport();
                  if (imp.match(lap, raplist)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/found match (" + svcName + "/" + lap + "/" + raplist + ")");
                     }

                     comp.setOutstandingNWReqCount(num);
                  }
               }
            }
         } else {
            hs = (HashSet)this.ex_svc_stat_list.get(svcName);
            comp = null;
            if (null != hs) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  comp = (WTCStatisticsComponent)iter.next();
                  TDMExport exp = comp.getExport();
                  if (exp.match(lap)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/found match (" + svcName + "/" + lap + ")");
                     }

                     comp.setOutstandingNWReqCount(num);
                  }
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/SUCCESS");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutstandingNWReqCount/WTC services statistics list is empty");
         }

      }
   }

   public void updOutstandingNWReqCount(TDMImport imp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/");
      }

      String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
      long val = 0L;
      synchronized(this) {
         val = this.getOutstandingNWReqCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist);
         val += num;
         this.setOutstandingNWReqCount(imp.getResourceName(), imp.getLocalAccessPoint(), raplist, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/OUTSTANDING_NETWORK_REQUEST_NUMBER (" + imp.getResourceName() + ":" + imp.getLocalAccessPoint() + ":" + raplist + ") = " + val + "]");
      }

   }

   public void updOutstandingNWReqCount(TDMExport exp, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/");
      }

      long val = 0L;
      synchronized(this) {
         val = this.getOutstandingNWReqCount(exp.getResourceName(), exp.getLocalAccessPoint(), (String)null);
         val += num;
         this.setOutstandingNWReqCount(exp.getResourceName(), exp.getLocalAccessPoint(), (String)null, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutstandingNWReqCount/OUTSTANDING_NETWORK_REQUEST_NUMBER (" + exp.getResourceName() + ":" + exp.getLocalAccessPoint() + ":null) = " + val + "]");
      }

   }

   public long getInTransactionCommittedTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInTransactionCommittedTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInTransactionCommittedTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getInTransactionCommittedTotalCount();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInTransactionCommittedTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setInTransactionCommittedTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInTransactionCommittedTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInTransactionCommittedTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setInTransactionCommittedTotalCount(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInTransactionCommittedTotalCount/SUCCESS");
         }

      }
   }

   public void updInTransactionCommittedTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInTransactionCommittedTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getInTransactionCommittedTotalCount(ldom, rdom);
         val += num;
         this.setInTransactionCommittedTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInTransactionCommittedTotalCount/INBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL (" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getInTransactionRolledBackTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getInTransactionRolledBackTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInTransactionRolledBackTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getInTransactionRolledBackTotalCount();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getInTransactionRolledBackTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setInTransactionRolledBackTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setInTransactionRolledBackTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInTransactionRolledBackTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setInTransactionRolledBackTotalCount(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setInTransactionRolledBackTotalCount/SUCCESS");
         }

      }
   }

   public void updInTransactionRolledBackTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInTransactionRolledBackTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getInTransactionRolledBackTotalCount(ldom, rdom);
         val += num;
         this.setInTransactionRolledBackTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updInTransactionRolledBackTotalCount/INBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL (" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getOutTransactionCommittedTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutTransactionCommittedTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutTransactionCommittedTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getOutTransactionCommittedTotalCount();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutTransactionCommittedTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutTransactionCommittedTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutTransactionCommittedTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutTransactionCommittedTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setOutTransactionCommittedTotalCount(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutTransactionCommittedTotalCount/SUCCESS");
         }

      }
   }

   public void updOutTransactionCommittedTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutTransactionCommittedTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutTransactionCommittedTotalCount(ldom, rdom);
         val += num;
         this.setOutTransactionCommittedTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutTransactionCommittedTotalCount/OUTBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public long getOutTransactionRolledBackTotalCount(String ldom, String rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      long val = 0L;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getOutTransactionRolledBackTotalCount/ldom = " + ldom + ", rdom = " + rdom);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutTransactionRolledBackTotalCount/WTC connection statistics list is empty");
         }

         return 0L;
      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               val = this.conn_stat_list[i].getOutTransactionRolledBackTotalCount();
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getOutTransactionRolledBackTotalCount/10/ret = " + val);
         }

         return val;
      }
   }

   public void setOutTransactionRolledBackTotalCount(String ldom, String rdom, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/setOutTransactionRolledBackTotalCount/ldom = " + ldom + ", rdom = " + rdom + ", new number = " + num);
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutTransactionRolledBackTotalCount/WTC connection statistics list is empty");
         }

      } else {
         for(int i = 0; i < this.conn_stat_list.length; ++i) {
            if (this.conn_stat_list[i].getLDomAccessPointId().equals(ldom) && this.conn_stat_list[i].getRDomAccessPointId().equals(rdom)) {
               this.conn_stat_list[i].setOutTransactionRolledBackTotalCount(num);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/setOutTransactionRolledBackTotalCount/SUCCESS");
         }

      }
   }

   public void updOutTransactionRolledBackTotalCount(dsession ss, long num) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutTransactionRolledBackTotalCount/");
      }

      String ldom = ss.get_local_domain_name();
      String rdom = ss.getRemoteDomainId();
      long val = 0L;
      synchronized(this) {
         val = this.getOutTransactionRolledBackTotalCount(ldom, rdom);
         val += num;
         this.setOutTransactionRolledBackTotalCount(ldom, rdom, val);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/updOutTransactionRolledBackTotalCount/OUTBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL(" + ldom + ":" + rdom + ") = " + val + "]");
      }

   }

   public TabularData getConnectionStatistics() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      TabularData connTable = null;
      Object[] connEntry = null;
      String conn_pair = null;
      DSessConnInfo[] list_info = this.wtcRT.listConnectionsConfigured();
      String conn_status = null;
      long reset_time = this.wtcRT.getWTCServerStartTime();
      long current_time = (new Date()).getTime();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getConnectionStatistics");
      }

      if (this.conn_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getConnectionStatistics/WTC connection statistics list is empty");
         }

         return null;
      } else {
         try {
            connTable = new TabularDataSupport(CONN_STAT_TABL_TYPE);

            for(int i = 0; i < this.conn_stat_list.length; ++i) {
               conn_pair = this.conn_stat_list[i].getLDomAccessPointId() + "_" + this.conn_stat_list[i].getRDomAccessPointId();

               for(int j = 0; j < list_info.length; ++j) {
                  if (this.conn_stat_list[i].getLDomAccessPointId().equals(list_info[j].getLocalAccessPointId()) && this.conn_stat_list[i].getRDomAccessPointId().equals(list_info[j].getRemoteAccessPointId())) {
                     conn_status = list_info[j].getConnected();
                     break;
                  }
               }

               connEntry = new Object[]{conn_pair, conn_status, this.conn_stat_list[i].getInboundMessageCount(), this.conn_stat_list[i].getOutboundMessageCount(), this.conn_stat_list[i].getInboundNWMessageSize(), this.conn_stat_list[i].getOutboundNWMessageSize(), this.conn_stat_list[i].getOutstandingNWReqCount(), this.conn_stat_list[i].getInTransactionCommittedTotalCount(), this.conn_stat_list[i].getOutTransactionCommittedTotalCount(), this.conn_stat_list[i].getInTransactionRolledBackTotalCount(), this.conn_stat_list[i].getOutTransactionRolledBackTotalCount(), reset_time, current_time};
               connTable.put(new CompositeDataSupport(CONN_STAT_COMP_TYPE, CONN_STAT_ITEM_NAMES, connEntry));
               if (traceEnabled) {
                  ntrace.doTrace("/WTCStatisticsRuntimeMBeanImpl/getConnectionStatistics/table entry " + connTable.size() + " dump:");
                  ntrace.doTrace("CONNECTION_PAIR: " + conn_pair);
                  ntrace.doTrace("CONNECTION_STATUS: " + conn_status);
                  ntrace.doTrace("INBOUND_MESSAGE_NUMBER_TOTAL: " + this.conn_stat_list[i].getInboundMessageCount());
                  ntrace.doTrace("OUTBOUND_MESSAGE_NUMBER_TOTAL: " + this.conn_stat_list[i].getOutboundMessageCount());
                  ntrace.doTrace("INBOUND_NETWORK_MESSAGE_SIZE_TOTAL: " + this.conn_stat_list[i].getInboundNWMessageSize());
                  ntrace.doTrace("OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL: " + this.conn_stat_list[i].getOutboundNWMessageSize());
                  ntrace.doTrace("OUTSTANDING_NETWORK_REQUEST_NUMBER: " + this.conn_stat_list[i].getOutstandingNWReqCount());
                  ntrace.doTrace("INBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL: " + this.conn_stat_list[i].getInTransactionCommittedTotalCount());
                  ntrace.doTrace("OUTBOUND_COMMITTED_TRANSACTION_NUMBER_TOTAL: " + this.conn_stat_list[i].getOutTransactionCommittedTotalCount());
                  ntrace.doTrace("INBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL: " + this.conn_stat_list[i].getInTransactionRolledBackTotalCount());
                  ntrace.doTrace("OUTBOUND_ROLLBACK_TRANSACTION_NUMBER_TOTAL: " + this.conn_stat_list[i].getOutTransactionRolledBackTotalCount());
                  ntrace.doTrace("RESET_TIMESTAMP: " + reset_time);
                  ntrace.doTrace("CURRENT_TIMESTAMP: " + current_time);
               }
            }
         } catch (OpenDataException var13) {
            throw new RuntimeException(var13);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getConnectionStatistics/DONE");
         }

         return connTable;
      }
   }

   public TabularData getImportedServiceStatistics() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      TabularData svcTable = null;
      Object[] svcEntry = null;
      String svc_name = null;
      String svc_status = null;
      long reset_time = this.wtcRT.getWTCServerStartTime();
      long current_time = (new Date()).getTime();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getImportedServiceStatistics");
      }

      if (this.im_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getImportedServiceStatistics/WTC imported services statistics list is empty");
         }

         return null;
      } else {
         try {
            svcTable = new TabularDataSupport(IM_SVC_STAT_TABL_TYPE);
            Iterator iter = this.im_svc_stat_list.values().iterator();

            while(iter.hasNext()) {
               HashSet hs = (HashSet)iter.next();
               Iterator tt = hs.iterator();

               while(tt.hasNext()) {
                  WTCStatisticsComponent comp = (WTCStatisticsComponent)tt.next();
                  svc_name = comp.getServiceName();
                  TDMImport imp = comp.getImport();
                  switch (imp.getStatus()) {
                     case 1:
                        svc_status = "SUSPENDED";
                        break;
                     case 2:
                        svc_status = "UNAVAILABLE";
                        break;
                     case 3:
                        svc_status = "AVAILABLE";
                        break;
                     default:
                        svc_status = "UNKNOWN";
                  }

                  String raplist = this.convertRAPList(imp.getRemoteAccessPointList());
                  svcEntry = new Object[]{svc_name, imp.getLocalAccessPoint(), raplist, imp.getRemoteName(), svc_status, comp.getOutboundMessageCount(), comp.getOutboundSuccessReqCount(), comp.getOutboundFailReqCount(), comp.getOutboundNWMessageSize(), comp.getOutstandingNWReqCount(), reset_time, current_time};
                  svcTable.put(new CompositeDataSupport(IM_SVC_STAT_COMP_TYPE, IM_SVC_STAT_ITEM_NAMES, svcEntry));
                  if (traceEnabled) {
                     ntrace.doTrace("/WTCStatisticsRuntimeMBeanImpl/getImportedServiceStatistics/table entry " + svcTable.size() + " dump:");
                     ntrace.doTrace("SERVICE_NAME: " + svc_name);
                     ntrace.doTrace("LOCAL_ACCESS_POINT: " + imp.getLocalAccessPoint());
                     ntrace.doTrace("REMOTE_ACESS_POINT_LIST: " + raplist);
                     ntrace.doTrace("REMOTE_NAME: " + imp.getRemoteName());
                     ntrace.doTrace("SERVICE_STATUS: " + svc_status);
                     ntrace.doTrace("OUTBOUND_MESSAGE_NUMBER_TOTAL: " + comp.getOutboundMessageCount());
                     ntrace.doTrace("OUTBOUND_SUCCESS_REQ_TOTAL: " + comp.getOutboundSuccessReqCount());
                     ntrace.doTrace("OUTBOUND_FAIL_REQ_TOTAL: " + comp.getOutboundFailReqCount());
                     ntrace.doTrace("OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL: " + comp.getOutboundNWMessageSize());
                     ntrace.doTrace("OUTSTANDING_NETWORK_REQUEST_NUMBER: " + comp.getOutstandingNWReqCount());
                     ntrace.doTrace("RESET_TIMESTAMP: " + reset_time);
                     ntrace.doTrace("CURRENT_TIMESTAMP: " + current_time);
                  }
               }
            }
         } catch (OpenDataException var16) {
            throw new RuntimeException(var16);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getImportedServiceStatistics/DONE");
         }

         return svcTable;
      }
   }

   public TabularData getExportedServiceStatistics() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      TabularData svcTable = null;
      Object[] svcEntry = null;
      String svc_name = null;
      String svc_status = null;
      long reset_time = this.wtcRT.getWTCServerStartTime();
      long current_time = (new Date()).getTime();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/getExportedServiceStatistics");
      }

      if (this.ex_svc_stat_list == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getExportedServiceStatistics/WTC exported services statistics list is empty");
         }

         return null;
      } else {
         try {
            svcTable = new TabularDataSupport(EX_SVC_STAT_TABL_TYPE);
            Iterator iter = this.ex_svc_stat_list.values().iterator();

            while(iter.hasNext()) {
               HashSet hs = (HashSet)iter.next();
               Iterator tt = hs.iterator();

               while(tt.hasNext()) {
                  WTCStatisticsComponent comp = (WTCStatisticsComponent)tt.next();
                  svc_name = comp.getServiceName();
                  TDMExport exp = comp.getExport();
                  switch (exp.getStatus()) {
                     case 1:
                        svc_status = "SUSPENDED";
                        break;
                     case 2:
                        svc_status = "UNAVAILABLE";
                        break;
                     case 3:
                        svc_status = "AVAILABLE";
                        break;
                     default:
                        svc_status = "UNKNOWN";
                  }

                  svcEntry = new Object[]{svc_name, exp.getLocalAccessPoint(), exp.getEJBName(), exp.getRemoteName(), svc_status, comp.getInboundMessageCount(), comp.getInboundSuccessReqCount(), comp.getInboundFailReqCount(), comp.getInboundNWMessageSize(), comp.getOutstandingNWReqCount(), reset_time, current_time};
                  svcTable.put(new CompositeDataSupport(EX_SVC_STAT_COMP_TYPE, EX_SVC_STAT_ITEM_NAMES, svcEntry));
                  if (traceEnabled) {
                     ntrace.doTrace("/WTCStatisticsRuntimeMBeanImpl/getExportedServiceStatistics/table entry " + svcTable.size() + " dump:");
                     ntrace.doTrace("SERVICE_NAME: " + svc_name);
                     ntrace.doTrace("LOCAL_ACCESS_POINT: " + exp.getLocalAccessPoint());
                     ntrace.doTrace("EJB_HOME: " + exp.getEJBName());
                     ntrace.doTrace("REMOTE_NAME: " + exp.getRemoteName());
                     ntrace.doTrace("SERVICE_STATUS: " + svc_status);
                     ntrace.doTrace("INBOUND_MESSAGE_NUMBER_TOTAL: " + comp.getInboundMessageCount());
                     ntrace.doTrace("INBOUND_SUCCESS_REQ_TOTAL: " + comp.getInboundSuccessReqCount());
                     ntrace.doTrace("INBOUND_FAIL_REQ_TOTAL: " + comp.getInboundFailReqCount());
                     ntrace.doTrace("INBOUND_NETWORK_MESSAGE_SIZE_TOTAL: " + comp.getInboundNWMessageSize());
                     ntrace.doTrace("OUTSTANDING_NETWORK_REQUEST_NUMBER: " + comp.getOutstandingNWReqCount());
                     ntrace.doTrace("RESET_TIMESTAMP: " + reset_time);
                     ntrace.doTrace("CURRENT_TIMESTAMP: " + current_time);
                  }
               }
            }
         } catch (OpenDataException var15) {
            throw new RuntimeException(var15);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/getExportedServiceStatistics/DONE");
         }

         return svcTable;
      }
   }

   public void clear() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCStatisticsRuntimeMBeanImpl/clear");
      }

      if (this.conn_stat_list != null) {
         this.conn_stat_list = null;
      }

      if (this.im_svc_stat_list != null) {
         this.im_svc_stat_list.clear();
      }

      if (this.ex_svc_stat_list != null) {
         this.ex_svc_stat_list.clear();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCStatisticsRuntimeMBeanImpl/clear/DONE");
      }

   }

   static {
      CONN_STAT_ITEM_TYPE = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
      CONN_STAT_TABL_INDEX = new String[]{"CONNECTION_PAIR"};
      EX_SVC_STAT_ITEM_NAMES = new String[]{"RESOURCE_NAME", "LOCAL_ACCESS_POINT", "EJB_HOME", "REMOTE_NAME", "SERVICE_STATUS", "INBOUND_MESSAGE_NUMBER_TOTAL", "INBOUND_SUCCESS_REQ_TOTAL", "INBOUND_FAIL_REQ_TOTAL", "INBOUND_NETWORK_MESSAGE_SIZE_TOTAL", "OUTSTANDING_NETWORK_REQUEST_NUMBER", "RESET_TIMESTAMP", "CURRENT_TIMESTAMP"};
      EX_SVC_STAT_TABL_INDEX = new String[]{"RESOURCE_NAME", "LOCAL_ACCESS_POINT"};
      EX_SVC_STAT_ITEM_TYPE = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
      IM_SVC_STAT_ITEM_NAMES = new String[]{"RESOURCE_NAME", "LOCAL_ACCESS_POINT", "REMOTE_ACCESS_POINT_LIST", "REMOTE_NAME", "SERVICE_STATUS", "OUTBOUND_MESSAGE_NUMBER_TOTAL", "OUTBOUND_SUCCESS_REQ_TOTAL", "OUTBOUND_FAIL_REQ_TOTAL", "OUTBOUND_NETWORK_MESSAGE_SIZE_TOTAL", "OUTSTANDING_NETWORK_REQUEST_NUMBER", "RESET_TIMESTAMP", "CURRENT_TIMESTAMP"};
      IM_SVC_STAT_ITEM_TYPE = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG};
      IM_SVC_STAT_TABL_INDEX = new String[]{"RESOURCE_NAME", "LOCAL_ACCESS_POINT", "REMOTE_ACCESS_POINT_LIST"};
      CONN_STAT_COMP_TYPE = null;
      CONN_STAT_TABL_TYPE = null;
      EX_SVC_STAT_COMP_TYPE = null;
      EX_SVC_STAT_TABL_TYPE = null;
      IM_SVC_STAT_COMP_TYPE = null;
      IM_SVC_STAT_TABL_TYPE = null;

      try {
         CONN_STAT_COMP_TYPE = new CompositeType("conn_stat_entry", "A entry for WTC connection statistics metrics", CONN_STAT_ITEM_NAMES, CONN_STAT_ITEM_NAMES, CONN_STAT_ITEM_TYPE);
         CONN_STAT_TABL_TYPE = new TabularType("conn_stat_tabular_type", "A tablular data type for WTC connection statistics metrics", CONN_STAT_COMP_TYPE, CONN_STAT_TABL_INDEX);
         EX_SVC_STAT_COMP_TYPE = new CompositeType("ex_svc_stat_entry", "A entry for WTC exported service statistics metrics", EX_SVC_STAT_ITEM_NAMES, EX_SVC_STAT_ITEM_NAMES, EX_SVC_STAT_ITEM_TYPE);
         EX_SVC_STAT_TABL_TYPE = new TabularType("ex_svc_stat_tabular_type", "A tablular data type for WTC exported service statistics metrics", EX_SVC_STAT_COMP_TYPE, EX_SVC_STAT_TABL_INDEX);
         IM_SVC_STAT_COMP_TYPE = new CompositeType("im_svc_stat_entry", "A entry for WTC imported service statistics metrics", IM_SVC_STAT_ITEM_NAMES, IM_SVC_STAT_ITEM_NAMES, IM_SVC_STAT_ITEM_TYPE);
         IM_SVC_STAT_TABL_TYPE = new TabularType("im_svc_stat_tabular_type", "A tablular data type for WTC imported service statistics metrics", IM_SVC_STAT_COMP_TYPE, IM_SVC_STAT_TABL_INDEX);
      } catch (OpenDataException var1) {
         throw new RuntimeException(var1);
      }
   }
}
