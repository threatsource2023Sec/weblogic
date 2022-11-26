package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsAction;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActions;

public class WlsActionsImpl extends XmlComplexContentImpl implements WlsActions {
   private static final long serialVersionUID = 1L;
   private static final QName WLSACTION$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-action");

   public WlsActionsImpl(SchemaType sType) {
      super(sType);
   }

   public WlsAction[] getWlsActionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSACTION$0, targetList);
         WlsAction[] result = new WlsAction[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsAction getWlsActionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsAction target = null;
         target = (WlsAction)this.get_store().find_element_user(WLSACTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsActionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSACTION$0);
      }
   }

   public void setWlsActionArray(WlsAction[] wlsActionArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsActionArray, WLSACTION$0);
   }

   public void setWlsActionArray(int i, WlsAction wlsAction) {
      this.generatedSetterHelperImpl(wlsAction, WLSACTION$0, i, (short)2);
   }

   public WlsAction insertNewWlsAction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsAction target = null;
         target = (WlsAction)this.get_store().insert_element_user(WLSACTION$0, i);
         return target;
      }
   }

   public WlsAction addNewWlsAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsAction target = null;
         target = (WlsAction)this.get_store().add_element_user(WLSACTION$0);
         return target;
      }
   }

   public void removeWlsAction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSACTION$0, i);
      }
   }
}
