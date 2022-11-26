package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitor;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitors;

public class WlsMonitorsImpl extends XmlComplexContentImpl implements WlsMonitors {
   private static final long serialVersionUID = 1L;
   private static final QName WLSMONITOR$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-monitor");

   public WlsMonitorsImpl(SchemaType sType) {
      super(sType);
   }

   public WlsMonitor[] getWlsMonitorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSMONITOR$0, targetList);
         WlsMonitor[] result = new WlsMonitor[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsMonitor getWlsMonitorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsMonitor target = null;
         target = (WlsMonitor)this.get_store().find_element_user(WLSMONITOR$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsMonitorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSMONITOR$0);
      }
   }

   public void setWlsMonitorArray(WlsMonitor[] wlsMonitorArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsMonitorArray, WLSMONITOR$0);
   }

   public void setWlsMonitorArray(int i, WlsMonitor wlsMonitor) {
      this.generatedSetterHelperImpl(wlsMonitor, WLSMONITOR$0, i, (short)2);
   }

   public WlsMonitor insertNewWlsMonitor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsMonitor target = null;
         target = (WlsMonitor)this.get_store().insert_element_user(WLSMONITOR$0, i);
         return target;
      }
   }

   public WlsMonitor addNewWlsMonitor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsMonitor target = null;
         target = (WlsMonitor)this.get_store().add_element_user(WLSMONITOR$0);
         return target;
      }
   }

   public void removeWlsMonitor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSMONITOR$0, i);
      }
   }
}
