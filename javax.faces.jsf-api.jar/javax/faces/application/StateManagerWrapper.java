package javax.faces.application;

import java.io.IOException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class StateManagerWrapper extends StateManager {
   protected abstract StateManager getWrapped();

   public StateManager.SerializedView saveSerializedView(FacesContext context) {
      return this.getWrapped().saveSerializedView(context);
   }

   public Object saveView(FacesContext context) {
      return this.getWrapped().saveView(context);
   }

   protected Object getTreeStructureToSave(FacesContext context) {
      return this.getWrapped().getTreeStructureToSave(context);
   }

   protected Object getComponentStateToSave(FacesContext context) {
      return this.getWrapped().getComponentStateToSave(context);
   }

   public void writeState(FacesContext context, Object state) throws IOException {
      this.getWrapped().writeState(context, state);
   }

   public void writeState(FacesContext context, StateManager.SerializedView state) throws IOException {
      this.getWrapped().writeState(context, state);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      return this.getWrapped().restoreView(context, viewId, renderKitId);
   }

   protected UIViewRoot restoreTreeStructure(FacesContext context, String viewId, String renderKitId) {
      return this.getWrapped().restoreTreeStructure(context, viewId, renderKitId);
   }

   protected void restoreComponentState(FacesContext context, UIViewRoot viewRoot, String renderKitId) {
      this.getWrapped().restoreComponentState(context, viewRoot, renderKitId);
   }

   public boolean isSavingStateInClient(FacesContext context) {
      return this.getWrapped().isSavingStateInClient(context);
   }
}
