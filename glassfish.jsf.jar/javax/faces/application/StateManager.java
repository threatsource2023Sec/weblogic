package javax.faces.application;

import java.io.IOException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class StateManager {
   public static final String STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.STATE_SAVING_METHOD";
   public static final String PARTIAL_STATE_SAVING_PARAM_NAME = "javax.faces.PARTIAL_STATE_SAVING";
   public static final String FULL_STATE_SAVING_VIEW_IDS_PARAM_NAME = "javax.faces.FULL_STATE_SAVING_VIEW_IDS";
   public static final String IS_SAVING_STATE = "javax.faces.IS_SAVING_STATE";
   public static final String IS_BUILDING_INITIAL_STATE = "javax.faces.IS_BUILDING_INITIAL_STATE";
   public static final String SERIALIZE_SERVER_STATE_PARAM_NAME = "javax.faces.SERIALIZE_SERVER_STATE";
   public static final String STATE_SAVING_METHOD_CLIENT = "client";
   public static final String STATE_SAVING_METHOD_SERVER = "server";
   private static final String IS_CALLED_FROM_API_CLASS = "javax.faces.ensureOverriddenInvocation";
   private Boolean savingStateInClient;

   /** @deprecated */
   public SerializedView saveSerializedView(FacesContext context) {
      context.getAttributes().put("javax.faces.ensureOverriddenInvocation", Boolean.TRUE);
      Object stateObj = null;

      try {
         stateObj = this.saveView(context);
      } finally {
         context.getAttributes().remove("javax.faces.ensureOverriddenInvocation");
      }

      SerializedView result = null;
      if (stateObj instanceof Object[]) {
         Object[] state = (Object[])((Object[])stateObj);
         if (state.length == 2) {
            result = new SerializedView(state[0], state[1]);
         }
      }

      return result;
   }

   /** @deprecated */
   @Deprecated
   public Object saveView(FacesContext context) {
      Object[] stateArray = null;
      if (!context.getAttributes().containsKey("javax.faces.ensureOverriddenInvocation")) {
         SerializedView view = this.saveSerializedView(context);
         if (view != null) {
            stateArray = new Object[]{view.getStructure(), view.getState()};
         }
      }

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
      if (state != null && state.getClass().isArray() && state.getClass().getComponentType().equals(Object.class)) {
         Object[] stateArray = (Object[])((Object[])state);
         if (stateArray.length == 2) {
            SerializedView view = new SerializedView(stateArray[0], stateArray[1]);
            this.writeState(context, view);
         }
      }

   }

   /** @deprecated */
   public void writeState(FacesContext context, SerializedView state) throws IOException {
      if (state != null) {
         this.writeState(context, (Object)(new Object[]{state.getStructure(), state.getState()}));
      }

   }

   /** @deprecated */
   @Deprecated
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

   public String getViewState(FacesContext context) {
      Object state = this.saveView(context);
      return context.getRenderKit().getResponseStateManager().getViewState(context, state);
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
