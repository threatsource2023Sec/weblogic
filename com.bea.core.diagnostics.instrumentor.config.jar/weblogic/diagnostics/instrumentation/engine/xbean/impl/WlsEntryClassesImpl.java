package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClass;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClasses;

public class WlsEntryClassesImpl extends XmlComplexContentImpl implements WlsEntryClasses {
   private static final long serialVersionUID = 1L;
   private static final QName WLSENTRYCLASS$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-entry-class");

   public WlsEntryClassesImpl(SchemaType sType) {
      super(sType);
   }

   public WlsEntryClass[] getWlsEntryClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSENTRYCLASS$0, targetList);
         WlsEntryClass[] result = new WlsEntryClass[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsEntryClass getWlsEntryClassArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsEntryClass target = null;
         target = (WlsEntryClass)this.get_store().find_element_user(WLSENTRYCLASS$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsEntryClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSENTRYCLASS$0);
      }
   }

   public void setWlsEntryClassArray(WlsEntryClass[] wlsEntryClassArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsEntryClassArray, WLSENTRYCLASS$0);
   }

   public void setWlsEntryClassArray(int i, WlsEntryClass wlsEntryClass) {
      this.generatedSetterHelperImpl(wlsEntryClass, WLSENTRYCLASS$0, i, (short)2);
   }

   public WlsEntryClass insertNewWlsEntryClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsEntryClass target = null;
         target = (WlsEntryClass)this.get_store().insert_element_user(WLSENTRYCLASS$0, i);
         return target;
      }
   }

   public WlsEntryClass addNewWlsEntryClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsEntryClass target = null;
         target = (WlsEntryClass)this.get_store().add_element_user(WLSENTRYCLASS$0);
         return target;
      }
   }

   public void removeWlsEntryClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSENTRYCLASS$0, i);
      }
   }
}
