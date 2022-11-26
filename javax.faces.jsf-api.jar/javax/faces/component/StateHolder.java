package javax.faces.component;

import javax.faces.context.FacesContext;

public interface StateHolder {
   Object saveState(FacesContext var1);

   void restoreState(FacesContext var1, Object var2);

   boolean isTransient();

   void setTransient(boolean var1);
}
