package javax.faces.application;

import java.io.IOException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class StateManager {
   public static final String STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.STATE_SAVING_METHOD";
   public static final String STATE_SAVING_METHOD_CLIENT = "client";
   public static final String STATE_SAVING_METHOD_SERVER = "server";
   private Boolean savingStateInClient = null;

   /** @deprecated */
   public SerializedView saveSerializedView(FacesContext context) {
      return null;
   }

   public Object saveView(FacesContext context) {
      SerializedView view = this.saveSerializedView(context);
      Object[] stateArray = new Object[]{view.getStructure(), view.getState()};
      return stateArray;
   }

   /** @deprecated */
   protected Object getTreeStructureToSave(FacesContext context) {
      return null;
   }

   /** @deprecated */
   protected Object getComponentStateToSave(FacesContext context) {
      return null;
   }

   public void writeState(FacesContext context, Object state) throws IOException {
      if (null != state && state.getClass().isArray() && state.getClass().getComponentType().equals(Object.class)) {
         Object[] stateArray = (Object[])((Object[])state);
         if (2 == stateArray.length) {
            SerializedView view = new SerializedView(stateArray[0], stateArray[1]);
            this.writeState(context, view);
         }
      }

   }

   /** @deprecated */
   public void writeState(FacesContext context, SerializedView state) throws IOException {
   }

   public abstract UIViewRoot restoreView(FacesContext var1, String var2, String var3);

   /** @deprecated */
   protected UIViewRoot restoreTreeStructure(FacesContext context, String viewId, String renderKitId) {
      return null;
   }

   /** @deprecated */
   protected void restoreComponentState(FacesContext context, UIViewRoot viewRoot, String renderKitId) {
   }

   public boolean isSavingStateInClient(FacesContext context) {
      if (null != this.savingStateInClient) {
         return this.savingStateInClient;
      } else {
         this.savingStateInClient = Boolean.FALSE;
         String saveStateParam = context.getExternalContext().getInitParameter("javax.faces.STATE_SAVING_METHOD");
         if (saveStateParam != null && saveStateParam.equalsIgnoreCase("client")) {
            this.savingStateInClient = Boolean.TRUE;
         }

         return this.savingStateInClient;
      }
   }

   /** @deprecated */
   public class SerializedView {
      private Object structure = null;
      private Object state = null;

      public SerializedView(Object newStructure, Object newState) {
         this.structure = newStructure;
         this.state = newState;
      }

      public Object getStructure() {
         return this.structure;
      }

      public Object getState() {
         return this.state;
      }
   }
}
