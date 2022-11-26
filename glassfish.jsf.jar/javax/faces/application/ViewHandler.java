package javax.faces.application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

public abstract class ViewHandler {
   private static final Logger log = Logger.getLogger("javax.faces.application");
   public static final String CHARACTER_ENCODING_KEY = "javax.faces.request.charset";
   public static final String DEFAULT_SUFFIX_PARAM_NAME = "javax.faces.DEFAULT_SUFFIX";
   public static final String DEFAULT_SUFFIX = ".xhtml .view.xml .jsp";
   public static final String FACELETS_SKIP_COMMENTS_PARAM_NAME = "javax.faces.FACELETS_SKIP_COMMENTS";
   public static final String FACELETS_SUFFIX_PARAM_NAME = "javax.faces.FACELETS_SUFFIX";
   public static final String DEFAULT_FACELETS_SUFFIX = ".xhtml";
   public static final String FACELETS_VIEW_MAPPINGS_PARAM_NAME = "javax.faces.FACELETS_VIEW_MAPPINGS";
   public static final String FACELETS_BUFFER_SIZE_PARAM_NAME = "javax.faces.FACELETS_BUFFER_SIZE";
   public static final String FACELETS_REFRESH_PERIOD_PARAM_NAME = "javax.faces.FACELETS_REFRESH_PERIOD";
   public static final String FACELETS_LIBRARIES_PARAM_NAME = "javax.faces.FACELETS_LIBRARIES";
   public static final String FACELETS_DECORATORS_PARAM_NAME = "javax.faces.FACELETS_DECORATORS";
   public static final String DISABLE_FACELET_JSF_VIEWHANDLER_PARAM_NAME = "javax.faces.DISABLE_FACELET_JSF_VIEWHANDLER";

   public void initView(FacesContext context) throws FacesException {
      String encoding = context.getExternalContext().getRequestCharacterEncoding();
      if (encoding == null) {
         encoding = this.calculateCharacterEncoding(context);
         if (encoding != null) {
            try {
               context.getExternalContext().setRequestCharacterEncoding(encoding);
            } catch (UnsupportedEncodingException var5) {
               String message = "Can't set encoding to: " + encoding + " Exception:" + var5.getMessage();
               if (log.isLoggable(Level.WARNING)) {
                  log.fine(message);
               }

               throw new FacesException(message, var5);
            }
         }

      }
   }

   public abstract UIViewRoot restoreView(FacesContext var1, String var2);

   public abstract UIViewRoot createView(FacesContext var1, String var2);

   public abstract void renderView(FacesContext var1, UIViewRoot var2) throws IOException, FacesException;

   public abstract Locale calculateLocale(FacesContext var1);

   public String calculateCharacterEncoding(FacesContext context) {
      ExternalContext extContext = context.getExternalContext();
      Map headerMap = extContext.getRequestHeaderMap();
      String contentType = (String)headerMap.get("Content-Type");
      String charEnc = null;
      if (contentType != null) {
         String charsetStr = "charset=";
         int len = charsetStr.length();
         int idx = contentType.indexOf(charsetStr);
         if (idx != -1 && idx + len < contentType.length()) {
            charEnc = contentType.substring(idx + len);
         }
      }

      if (charEnc == null && extContext.getSession(false) != null) {
         charEnc = (String)extContext.getSessionMap().get("javax.faces.request.charset");
      }

      return charEnc;
   }

   public abstract String calculateRenderKitId(FacesContext var1);

   public String deriveViewId(FacesContext context, String requestViewId) {
      return requestViewId;
   }

   public String deriveLogicalViewId(FacesContext context, String requestViewId) {
      return requestViewId;
   }

   public abstract String getActionURL(FacesContext var1, String var2);

   public String getRedirectURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      return this.getActionURL(context, viewId);
   }

   public String getBookmarkableURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      return this.getActionURL(context, viewId);
   }

   public abstract String getResourceURL(FacesContext var1, String var2);

   public abstract String getWebsocketURL(FacesContext var1, String var2);

   public Set getProtectedViewsUnmodifiable() {
      return Collections.unmodifiableSet(Collections.emptySet());
   }

   public void addProtectedView(String urlPattern) {
   }

   public boolean removeProtectedView(String urlPattern) {
      return false;
   }

   public ViewDeclarationLanguage getViewDeclarationLanguage(FacesContext context, String viewId) {
      return null;
   }

   public Stream getViews(FacesContext facesContext, String path, int maxDepth, ViewVisitOption... options) {
      return Stream.empty();
   }

   public Stream getViews(FacesContext facesContext, String path, ViewVisitOption... options) {
      return Stream.empty();
   }

   public abstract void writeState(FacesContext var1) throws IOException;
}
