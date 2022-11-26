package com.sun.faces.application;

import com.sun.faces.application.view.JspStateManagementStrategy;
import java.io.IOException;
import java.util.Map;
import javax.faces.application.StateManager;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.ResponseStateManager;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewDeclarationLanguage;

public class StateManagerImpl extends StateManager {
   public Object saveView(FacesContext context) {
      Object result = null;
      if (context != null && !context.getViewRoot().isTransient()) {
         UIViewRoot viewRoot = context.getViewRoot();
         StateManagementStrategy strategy = null;
         String viewId = viewRoot.getViewId();
         ViewDeclarationLanguage vdl = context.getApplication().getViewHandler().getViewDeclarationLanguage(context, viewId);
         if (vdl != null) {
            strategy = vdl.getStateManagementStrategy(context, viewId);
         }

         Map contextAttributes = context.getAttributes();

         try {
            contextAttributes.put("javax.faces.IS_SAVING_STATE", Boolean.TRUE);
            if (strategy != null) {
               result = strategy.saveView(context);
            } else {
               StateManagementStrategy strategy = new JspStateManagementStrategy(context);
               result = strategy.saveView(context);
            }
         } finally {
            contextAttributes.remove("javax.faces.IS_SAVING_STATE");
         }
      }

      return result;
   }

   public void writeState(FacesContext context, Object state) throws IOException {
      RenderKit rk = context.getRenderKit();
      ResponseStateManager rsm = rk.getResponseStateManager();
      rsm.writeState(context, state);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      StateManagementStrategy strategy = null;
      ViewDeclarationLanguage vdl = context.getApplication().getViewHandler().getViewDeclarationLanguage(context, viewId);
      if (vdl != null) {
         strategy = vdl.getStateManagementStrategy(context, viewId);
      }

      UIViewRoot result;
      if (strategy != null) {
         result = strategy.restoreView(context, viewId, renderKitId);
      } else {
         StateManagementStrategy strategy = new JspStateManagementStrategy(context);
         result = strategy.restoreView(context, viewId, renderKitId);
      }

      return result;
   }
}
