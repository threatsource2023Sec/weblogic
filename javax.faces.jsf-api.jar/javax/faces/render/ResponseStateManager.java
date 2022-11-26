package javax.faces.render;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;

public abstract class ResponseStateManager {
   private static Logger log = Logger.getLogger("javax.faces.render");
   public static final String RENDER_KIT_ID_PARAM = "javax.faces.RenderKitId";
   public static final String VIEW_STATE_PARAM = "javax.faces.ViewState";

   public void writeState(FacesContext context, Object state) throws IOException {
      StateManager.SerializedView view;
      if (state instanceof StateManager.SerializedView) {
         view = (StateManager.SerializedView)state;
      } else {
         if (!(state instanceof Object[])) {
            if (log.isLoggable(Level.SEVERE)) {
               log.log(Level.SEVERE, "State is not an expected array of length 2.");
            }

            throw new IOException("State is not an expected array of length 2.");
         }

         Object[] stateArray = (Object[])((Object[])state);
         if (2 != stateArray.length) {
            if (log.isLoggable(Level.SEVERE)) {
               log.log(Level.SEVERE, "State is not an expected array of length 2.");
            }

            throw new IOException("State is not an expected array of length 2.");
         }

         StateManager stateManager = context.getApplication().getStateManager();
         view = stateManager.new SerializedView(stateArray[0], stateArray[1]);
      }

      this.writeState(context, view);
   }

   /** @deprecated */
   public void writeState(FacesContext context, StateManager.SerializedView state) throws IOException {
   }

   public Object getState(FacesContext context, String viewId) {
      Object[] stateArray = new Object[]{this.getTreeStructureToRestore(context, viewId), this.getComponentStateToRestore(context)};
      return stateArray;
   }

   /** @deprecated */
   public Object getTreeStructureToRestore(FacesContext context, String viewId) {
      return null;
   }

   /** @deprecated */
   public Object getComponentStateToRestore(FacesContext context) {
      return null;
   }

   public boolean isPostback(FacesContext context) {
      return !context.getExternalContext().getRequestParameterMap().isEmpty();
   }
}
