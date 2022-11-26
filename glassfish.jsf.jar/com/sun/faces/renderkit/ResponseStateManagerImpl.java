package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.RequestStateManager;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

public class ResponseStateManagerImpl extends ResponseStateManager {
   private StateHelper helper;

   public ResponseStateManagerImpl() {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      String stateMode = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.StateSavingMethod);
      this.helper = (StateHelper)("client".equalsIgnoreCase(stateMode) ? new ClientSideStateHelper() : new ServerSideStateHelper());
   }

   public boolean isPostback(FacesContext context) {
      return context.getExternalContext().getRequestParameterMap().containsKey(RenderKitUtils.PredefinedPostbackParameter.VIEW_STATE_PARAM.getName(context));
   }

   public String getCryptographicallyStrongTokenFromSession(FacesContext context) {
      return this.helper.getCryptographicallyStrongTokenFromSession(context);
   }

   public Object getState(FacesContext context, String viewId) {
      Object state = RequestStateManager.get(context, "com.sun.faces.FACES_VIEW_STATE");
      if (state == null) {
         try {
            state = this.helper.getState(context, viewId);
            if (state != null) {
               RequestStateManager.set(context, "com.sun.faces.FACES_VIEW_STATE", state);
            }
         } catch (IOException var5) {
            throw new FacesException(var5);
         }
      }

      return state;
   }

   public void writeState(FacesContext context, Object state) throws IOException {
      this.helper.writeState(context, state, (StringBuilder)null);
   }

   public String getViewState(FacesContext context, Object state) {
      StringBuilder sb = new StringBuilder(32);

      try {
         this.helper.writeState(context, state, sb);
      } catch (IOException var5) {
         throw new FacesException(var5);
      }

      return sb.toString();
   }

   public Object getTreeStructureToRestore(FacesContext context, String viewId) {
      Object[] state = (Object[])((Object[])this.getState(context, viewId));
      return state != null ? state[0] : null;
   }

   public boolean isStateless(FacesContext facesContext, String viewId) {
      return this.helper.isStateless(facesContext, viewId);
   }
}
