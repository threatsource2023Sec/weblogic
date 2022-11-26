package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackage;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackages;

public class WlsPackagesImpl extends XmlComplexContentImpl implements WlsPackages {
   private static final long serialVersionUID = 1L;
   private static final QName WLSPACKAGE$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-package");

   public WlsPackagesImpl(SchemaType sType) {
      super(sType);
   }

   public WlsPackage[] getWlsPackageArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSPACKAGE$0, targetList);
         WlsPackage[] result = new WlsPackage[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsPackage getWlsPackageArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsPackage target = null;
         target = (WlsPackage)this.get_store().find_element_user(WLSPACKAGE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsPackageArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSPACKAGE$0);
      }
   }

   public void setWlsPackageArray(WlsPackage[] wlsPackageArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsPackageArray, WLSPACKAGE$0);
   }

   public void setWlsPackageArray(int i, WlsPackage wlsPackage) {
      this.generatedSetterHelperImpl(wlsPackage, WLSPACKAGE$0, i, (short)2);
   }

   public WlsPackage insertNewWlsPackage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsPackage target = null;
         target = (WlsPackage)this.get_store().insert_element_user(WLSPACKAGE$0, i);
         return target;
      }
   }

   public WlsPackage addNewWlsPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsPackage target = null;
         target = (WlsPackage)this.get_store().add_element_user(WLSPACKAGE$0);
         return target;
      }
   }

   public void removeWlsPackage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSPACKAGE$0, i);
      }
   }
}
