package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class FViewFld implements Serializable {
   TypedView32 data = null;
   String name = null;
   int vflags;

   public FViewFld() {
   }

   public FViewFld(String viewname) {
      this.name = viewname;
   }

   public FViewFld(String viewname, TypedView32 viewdata) {
      this.name = viewname;
      this.data = viewdata;
   }

   public FViewFld(FViewFld to_be_clone) {
      this.data = to_be_clone.getViewData().doClone();
      this.name = new String(to_be_clone.getViewName());
   }

   public void setViewName(String vname) {
      this.name = vname;
   }

   public String getViewName() {
      return this.name;
   }

   public void setViewData(TypedView32 vdata) {
      this.data = vdata;
   }

   public TypedView32 getViewData() {
      return this.data;
   }
}
