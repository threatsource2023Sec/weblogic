package javax.faces.event;

import javax.faces.context.FacesContext;

public class PostConstructCustomScopeEvent extends SystemEvent {
   private static final long serialVersionUID = 4749939775937702379L;

   public PostConstructCustomScopeEvent(ScopeContext scopeContext) {
      super(scopeContext);
   }

   public PostConstructCustomScopeEvent(FacesContext facesContext, ScopeContext scopeContext) {
      super(facesContext, scopeContext);
   }

   public ScopeContext getContext() {
      return (ScopeContext)this.getSource();
   }
}
