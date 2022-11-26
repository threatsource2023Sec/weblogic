package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroup;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroups;

public class WlsActionGroupsImpl extends XmlComplexContentImpl implements WlsActionGroups {
   private static final long serialVersionUID = 1L;
   private static final QName WLSACTIONGROUP$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-action-group");

   public WlsActionGroupsImpl(SchemaType sType) {
      super(sType);
   }

   public WlsActionGroup[] getWlsActionGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSACTIONGROUP$0, targetList);
         WlsActionGroup[] result = new WlsActionGroup[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsActionGroup getWlsActionGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsActionGroup target = null;
         target = (WlsActionGroup)this.get_store().find_element_user(WLSACTIONGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsActionGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSACTIONGROUP$0);
      }
   }

   public void setWlsActionGroupArray(WlsActionGroup[] wlsActionGroupArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsActionGroupArray, WLSACTIONGROUP$0);
   }

   public void setWlsActionGroupArray(int i, WlsActionGroup wlsActionGroup) {
      this.generatedSetterHelperImpl(wlsActionGroup, WLSACTIONGROUP$0, i, (short)2);
   }

   public WlsActionGroup insertNewWlsActionGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsActionGroup target = null;
         target = (WlsActionGroup)this.get_store().insert_element_user(WLSACTIONGROUP$0, i);
         return target;
      }
   }

   public WlsActionGroup addNewWlsActionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsActionGroup target = null;
         target = (WlsActionGroup)this.get_store().add_element_user(WLSACTIONGROUP$0);
         return target;
      }
   }

   public void removeWlsActionGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSACTIONGROUP$0, i);
      }
   }
}
