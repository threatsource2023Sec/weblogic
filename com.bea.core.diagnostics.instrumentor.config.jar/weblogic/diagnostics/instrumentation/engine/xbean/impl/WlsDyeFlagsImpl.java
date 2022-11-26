package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlag;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlags;

public class WlsDyeFlagsImpl extends XmlComplexContentImpl implements WlsDyeFlags {
   private static final long serialVersionUID = 1L;
   private static final QName WLSDYEFLAG$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-dye-flag");

   public WlsDyeFlagsImpl(SchemaType sType) {
      super(sType);
   }

   public WlsDyeFlag[] getWlsDyeFlagArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSDYEFLAG$0, targetList);
         WlsDyeFlag[] result = new WlsDyeFlag[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsDyeFlag getWlsDyeFlagArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsDyeFlag target = null;
         target = (WlsDyeFlag)this.get_store().find_element_user(WLSDYEFLAG$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsDyeFlagArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSDYEFLAG$0);
      }
   }

   public void setWlsDyeFlagArray(WlsDyeFlag[] wlsDyeFlagArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsDyeFlagArray, WLSDYEFLAG$0);
   }

   public void setWlsDyeFlagArray(int i, WlsDyeFlag wlsDyeFlag) {
      this.generatedSetterHelperImpl(wlsDyeFlag, WLSDYEFLAG$0, i, (short)2);
   }

   public WlsDyeFlag insertNewWlsDyeFlag(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsDyeFlag target = null;
         target = (WlsDyeFlag)this.get_store().insert_element_user(WLSDYEFLAG$0, i);
         return target;
      }
   }

   public WlsDyeFlag addNewWlsDyeFlag() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsDyeFlag target = null;
         target = (WlsDyeFlag)this.get_store().add_element_user(WLSDYEFLAG$0);
         return target;
      }
   }

   public void removeWlsDyeFlag(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSDYEFLAG$0, i);
      }
   }
}
