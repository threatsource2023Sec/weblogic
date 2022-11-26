package weblogic.tools.ui;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiBeanPropertySet extends PropertySet {
   protected PropertySet[] ps;

   public MultiBeanPropertySet(Class bc, PropertySet[] ps) {
      super(bc, mergeProps(ps));
      this.ps = (PropertySet[])((PropertySet[])ps.clone());
   }

   public abstract void setBean(Object var1);

   public Object createNewBean() {
      Object ret = super.createNewBean();
      this.customizeNewBean(ret);
      return ret;
   }

   protected void customizeNewBean(Object o) {
   }

   public void modelToUI() {
      for(int i = 0; i < this.ps.length; ++i) {
         this.ps[i].modelToUI();
      }

   }

   public void uiToModel() {
      for(int i = 0; i < this.ps.length; ++i) {
         this.ps[i].uiToModel();
      }

   }

   private static Property[] mergeProps(PropertySet[] ps) {
      List l = new ArrayList();

      for(int i = 0; i < ps.length; ++i) {
         Property[] props = ps[i].getProps();

         for(int j = 0; j < props.length; ++j) {
            l.add(props[j]);
         }
      }

      Property[] ret = new Property[l.size()];
      l.toArray(ret);
      return ret;
   }
}
