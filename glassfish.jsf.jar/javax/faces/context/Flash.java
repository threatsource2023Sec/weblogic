package javax.faces.context;

import java.util.Map;

public abstract class Flash implements Map {
   public static final String NULL_VALUE = "javax.faces.context.Flash.NULL_VALUE";

   public abstract boolean isKeepMessages();

   public abstract void setKeepMessages(boolean var1);

   public abstract boolean isRedirect();

   public abstract void setRedirect(boolean var1);

   public abstract void putNow(String var1, Object var2);

   public abstract void keep(String var1);

   public abstract void doPrePhaseActions(FacesContext var1);

   public abstract void doPostPhaseActions(FacesContext var1);
}
