package javax.faces.application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class ViewHandler {
   private static Logger log = Logger.getLogger("javax.faces.application");
   public static final String CHARACTER_ENCODING_KEY = "javax.faces.request.charset";
   public static final String DEFAULT_SUFFIX_PARAM_NAME = "javax.faces.DEFAULT_SUFFIX";
   public static final String DEFAULT_SUFFIX = ".jsp";

   public abstract Locale calculateLocale(FacesContext var1);

   public String calculateCharacterEncoding(FacesContext context) {
      ExternalContext extContext = context.getExternalContext();
      Map headerMap = extContext.getRequestHeaderMap();
      String contentType = (String)headerMap.get("Content-Type");
      String charEnc = null;
      if (null != contentType) {
         String charsetStr = "charset=";
         int len = charsetStr.length();
         int idx = contentType.indexOf(charsetStr);
         if (idx != -1 && idx + len < contentType.length()) {
            charEnc = contentType.substring(idx + len);
         }
      }

      if (null == charEnc && null != extContext.getSession(false)) {
         charEnc = (String)extContext.getSessionMap().get("javax.faces.request.charset");
      }

      return charEnc;
   }

   public abstract String calculateRenderKitId(FacesContext var1);

   public abstract UIViewRoot createView(FacesContext var1, String var2);

   public abstract String getActionURL(FacesContext var1, String var2);

   public abstract String getResourceURL(FacesContext var1, String var2);

   public void initView(FacesContext context) throws FacesException {
      String encoding = this.calculateCharacterEncoding(context);
      if (null != encoding) {
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

   public abstract void renderView(FacesContext var1, UIViewRoot var2) throws IOException, FacesException;

   public abstract UIViewRoot restoreView(FacesContext var1, String var2);

   public abstract void writeState(FacesContext var1) throws IOException;
}
