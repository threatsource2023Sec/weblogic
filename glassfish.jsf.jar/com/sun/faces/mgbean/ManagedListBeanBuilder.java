package com.sun.faces.mgbean;

import java.util.List;
import javax.faces.context.FacesContext;

public class ManagedListBeanBuilder extends BeanBuilder {
   private List values;

   public ManagedListBeanBuilder(ManagedBeanInfo beanInfo) {
      super(beanInfo);
   }

   void bake() {
      if (!this.isBaked()) {
         super.bake();
         ManagedBeanInfo.ListEntry entry = this.beanInfo.getListEntry();
         this.values = this.getBakedList(entry.getValueClass(), entry.getValues());
         this.baked();
      }

   }

   protected void buildBean(Object bean, FacesContext context) {
      this.initList(this.values, (List)bean, context);
   }
}
