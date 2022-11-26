package weblogic.wtc.gwt;

import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import weblogic.wtc.jatmi.InvokeInfo;
import weblogic.wtc.jatmi.gwatmi;

public final class ServiceParameters {
   private InvokeInfo ii;
   private gwatmi ro;

   ServiceParameters(InvokeInfo invokeinfo, gwatmi rplyObj) {
      this.ii = invokeinfo;
      this.ro = rplyObj;
   }

   public InvokeInfo get_invokeInfo() {
      return this.ii;
   }

   public gwatmi get_gwatmi() {
      return this.ro;
   }

   public InvokeInfo getInvokeInfo() {
      return this.ii;
   }

   public void setUser() {
      this.ii.setUser();
   }

   public void removeUser() {
      this.ii.removeUser();
   }

   public TCAuthenticatedUser getUser() {
      return this.ii.getUser();
   }
}
