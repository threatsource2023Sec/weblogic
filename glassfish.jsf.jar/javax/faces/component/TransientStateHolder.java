package javax.faces.component;

import javax.faces.context.FacesContext;

public interface TransientStateHolder {
   Object saveTransientState(FacesContext var1);

   void restoreTransientState(FacesContext var1, Object var2);
}
