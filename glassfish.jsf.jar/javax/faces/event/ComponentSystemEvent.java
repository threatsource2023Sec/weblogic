package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class ComponentSystemEvent extends SystemEvent {
   private static final long serialVersionUID = -4726746661822507506L;

   public ComponentSystemEvent(UIComponent component) {
      super(component);
   }

   public ComponentSystemEvent(FacesContext facesContext, UIComponent component) {
      super(facesContext, component);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      boolean result = listener instanceof ComponentSystemEventListener;
      if (!result) {
         result = super.isAppropriateListener(listener);
      }

      return result;
   }

   public void processListener(FacesListener listener) {
      UIComponent c = this.getComponent();
      boolean didPush = false;
      FacesContext context = FacesContext.getCurrentInstance();
      UIComponent cFromStack = UIComponent.getCurrentComponent(context);
      if (null == cFromStack) {
         didPush = true;
         c.pushComponentToEL(context, (UIComponent)null);
      }

      try {
         if (listener instanceof SystemEventListener) {
            super.processListener(listener);
         } else if (listener instanceof ComponentSystemEventListener) {
            ((ComponentSystemEventListener)listener).processEvent(this);
         }
      } finally {
         if (didPush) {
            c.popComponentFromEL(context);
         }

      }

   }

   public UIComponent getComponent() {
      return (UIComponent)this.getSource();
   }
}
