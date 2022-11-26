package javax.faces.lifecycle;

import java.util.Map;
import javax.faces.context.FacesContext;

public abstract class ClientWindow {
   public static final String CLIENT_WINDOW_MODE_PARAM_NAME = "javax.faces.CLIENT_WINDOW_MODE";
   private static final String PER_USE_CLIENT_WINDOW_URL_QUERY_PARAMETER_DISABLED_KEY = "javax.faces.lifecycle.ClientWindowRenderModeEnablement";

   public abstract Map getQueryURLParameters(FacesContext var1);

   public abstract String getId();

   public abstract void decode(FacesContext var1);

   public void disableClientWindowRenderMode(FacesContext context) {
      Map attrMap = context.getAttributes();
      attrMap.put("javax.faces.lifecycle.ClientWindowRenderModeEnablement", Boolean.TRUE);
   }

   public void enableClientWindowRenderMode(FacesContext context) {
      Map attrMap = context.getAttributes();
      attrMap.remove("javax.faces.lifecycle.ClientWindowRenderModeEnablement");
   }

   public boolean isClientWindowRenderModeEnabled(FacesContext context) {
      boolean result = false;
      Map attrMap = context.getAttributes();
      result = !attrMap.containsKey("javax.faces.lifecycle.ClientWindowRenderModeEnablement");
      return result;
   }
}
