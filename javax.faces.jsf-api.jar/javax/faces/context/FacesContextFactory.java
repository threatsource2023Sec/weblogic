package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;

public abstract class FacesContextFactory {
   public abstract FacesContext getFacesContext(Object var1, Object var2, Object var3, Lifecycle var4) throws FacesException;
}
