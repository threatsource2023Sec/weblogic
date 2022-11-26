package com.sun.faces.mgbean;

import java.util.Map;
import javax.faces.context.FacesContext;

public class ManagedMapBeanBuilder extends BeanBuilder {
   private Map mapEntries;

   public ManagedMapBeanBuilder(ManagedBeanInfo beanInfo) {
      super(beanInfo);
   }

   void bake() {
      if (!this.isBaked()) {
         super.bake();
         ManagedBeanInfo.MapEntry entry = this.beanInfo.getMapEntry();
         this.mapEntries = this.getBakedMap(entry.getKeyClass(), entry.getValueClass(), entry.getEntries());
         this.baked();
      }

   }

   protected void buildBean(Object bean, FacesContext context) {
      this.initMap(this.mapEntries, (Map)bean, context);
   }
}
