package com.sun.faces.cdi;

import com.sun.faces.component.CompositeComponentStackManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class CompositeComponentProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public CompositeComponentProducer() {
      super.name("cc").beanClassAndType(UIComponent.class).create((e) -> {
         FacesContext context = FacesContext.getCurrentInstance();
         UIComponent component = CompositeComponentStackManager.getManager(context).peek();
         if (component == null) {
            component = UIComponent.getCurrentCompositeComponent(context);
         }

         return component;
      });
   }
}
