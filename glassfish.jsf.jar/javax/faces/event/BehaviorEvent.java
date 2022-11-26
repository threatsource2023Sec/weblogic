package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;

public abstract class BehaviorEvent extends FacesEvent {
   private static final long serialVersionUID = 6516644738910462065L;
   private final Behavior behavior;

   public BehaviorEvent(UIComponent component, Behavior behavior) {
      super(component);
      if (null == behavior) {
         throw new IllegalArgumentException("Behavior agrument cannot be null");
      } else {
         this.behavior = behavior;
      }
   }

   public BehaviorEvent(FacesContext facesContext, UIComponent component, Behavior behavior) {
      super(facesContext, component);
      if (null == behavior) {
         throw new IllegalArgumentException("Behavior agrument cannot be null");
      } else {
         this.behavior = behavior;
      }
   }

   public Behavior getBehavior() {
      return this.behavior;
   }
}
