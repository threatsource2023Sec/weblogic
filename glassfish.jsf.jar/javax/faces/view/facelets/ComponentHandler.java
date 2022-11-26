package javax.faces.view.facelets;

import javax.faces.component.UIComponent;

public class ComponentHandler extends DelegatingMetaTagHandler {
   private TagHandlerDelegate helper = null;
   private ComponentConfig componentConfig = null;

   public ComponentHandler(ComponentConfig config) {
      super(config);
      this.componentConfig = config;
   }

   protected TagHandlerDelegate getTagHandlerDelegate() {
      if (null == this.helper) {
         this.helper = this.delegateFactory.createComponentHandlerDelegate(this);
      }

      return this.helper;
   }

   public ComponentConfig getComponentConfig() {
      return this.componentConfig;
   }

   public UIComponent createComponent(FaceletContext ctx) {
      return null;
   }

   public void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
   }

   public void onComponentPopulated(FaceletContext ctx, UIComponent c, UIComponent parent) {
   }

   public static boolean isNew(UIComponent component) {
      UIComponent c = component;
      if (component != null) {
         UIComponent parent = component.getParent();
         if (parent != null && UIComponent.isCompositeComponent(parent)) {
            c = parent;
         }

         return c.getParent() == null;
      } else {
         return false;
      }
   }
}
