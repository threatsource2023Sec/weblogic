package javax.faces.event;

import javax.faces.context.FacesContext;

public class PreDestroyCustomScopeEvent extends SystemEvent {
   private static final long serialVersionUID = -3646173841788025206L;

   public PreDestroyCustomScopeEvent(ScopeContext scopeContext) {
      super(scopeContext);
   }

   public PreDestroyCustomScopeEvent(FacesContext facesContext, ScopeContext scopeContext) {
      super(facesContext, scopeContext);
   }

   public ScopeContext getContext() {
      return (ScopeContext)this.getSource();
   }
}
