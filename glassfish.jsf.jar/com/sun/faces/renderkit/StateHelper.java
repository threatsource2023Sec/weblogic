package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.SerializationProvider;
import com.sun.faces.spi.SerializationProviderFactory;
import com.sun.faces.util.ByteArrayGuardAESCTR;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.ClientWindow;
import javax.servlet.http.HttpSession;

public abstract class StateHelper {
   private static final Logger LOGGER;
   protected SerializationProvider serialProvider;
   protected WebConfiguration webConfig;
   protected boolean compressViewState;
   protected char[] stateFieldStart;
   protected char[] fieldMiddle;
   protected char[] fieldEnd;
   private static final String TOKEN_NAME = "com.sun.faces.TOKEN";

   public StateHelper() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      this.serialProvider = SerializationProviderFactory.createInstance(ctx.getExternalContext());
      this.webConfig = WebConfiguration.getInstance(ctx.getExternalContext());
      this.compressViewState = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CompressViewState);
      if (this.serialProvider == null) {
         this.serialProvider = SerializationProviderFactory.createInstance(FacesContext.getCurrentInstance().getExternalContext());
      }

   }

   public static void createAndStoreCryptographicallyStrongTokenInSession(HttpSession session) {
      ByteArrayGuardAESCTR guard = new ByteArrayGuardAESCTR();
      String clearText = "" + System.currentTimeMillis();
      String result = guard.encrypt(clearText);

      try {
         result = URLEncoder.encode(result, "UTF-8");
      } catch (UnsupportedEncodingException var5) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unable to URL encode cryptographically strong token, storing clear text in session instead.", var5);
         }

         result = clearText;
      }

      session.setAttribute("com.sun.faces.TOKEN", result);
   }

   public String getCryptographicallyStrongTokenFromSession(FacesContext context) {
      String result = (String)context.getExternalContext().getSessionMap().get("com.sun.faces.TOKEN");
      if (null == result) {
         context.getExternalContext().getSession(true);
      }

      result = (String)context.getExternalContext().getSessionMap().get("com.sun.faces.TOKEN");
      return result;
   }

   public abstract void writeState(FacesContext var1, Object var2, StringBuilder var3) throws IOException;

   public abstract Object getState(FacesContext var1, String var2) throws IOException;

   public abstract boolean isStateless(FacesContext var1, String var2) throws IllegalStateException;

   protected static String getStateParamValue(FacesContext context) {
      String pValue = RenderKitUtils.PredefinedPostbackParameter.VIEW_STATE_PARAM.getValue(context);
      if (pValue != null && pValue.length() == 0) {
         pValue = null;
      }

      return pValue;
   }

   protected void writeRenderKitIdField(FacesContext context, ResponseWriter writer) throws IOException {
      String result = context.getViewRoot().getRenderKitId();
      String defaultRkit = context.getApplication().getDefaultRenderKitId();
      if (null == defaultRkit) {
         defaultRkit = "HTML_BASIC";
      }

      if (result != null && !defaultRkit.equals(result)) {
         writer.startElement("input", context.getViewRoot());
         writer.writeAttribute("type", "hidden", "type");
         writer.writeAttribute("name", RenderKitUtils.PredefinedPostbackParameter.RENDER_KIT_ID_PARAM.getName(context), "name");
         writer.writeAttribute("value", result, "value");
         writer.endElement("input");
      }

   }

   protected void writeClientWindowField(FacesContext context, ResponseWriter writer) throws IOException {
      ClientWindow window = context.getExternalContext().getClientWindow();
      if (null != window) {
         writer.startElement("input", (UIComponent)null);
         writer.writeAttribute("type", "hidden", (String)null);
         writer.writeAttribute("name", RenderKitUtils.PredefinedPostbackParameter.CLIENT_WINDOW_PARAM.getName(context), (String)null);
         writer.writeAttribute("id", Util.getClientWindowId(context), (String)null);
         writer.writeAttribute("value", window.getId(), (String)null);
         if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AutoCompleteOffOnViewState)) {
            writer.writeAttribute("autocomplete", "off", (String)null);
         }

         writer.endElement("input");
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
