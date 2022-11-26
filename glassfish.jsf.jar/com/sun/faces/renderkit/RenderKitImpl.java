package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.html_basic.HtmlResponseWriter;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

public class RenderKitImpl extends RenderKit {
   private static final Logger LOGGER;
   private static final String[] SUPPORTED_CONTENT_TYPES_ARRAY;
   private static final String SUPPORTED_CONTENT_TYPES = "text/html,application/xhtml+xml,application/xml,text/xml";
   private ConcurrentHashMap rendererFamilies = new ConcurrentHashMap();
   private ConcurrentHashMap behaviorRenderers = new ConcurrentHashMap();
   private ResponseStateManager responseStateManager = new ResponseStateManagerImpl();
   private WebConfiguration webConfig;

   public RenderKitImpl() {
      FacesContext context = FacesContext.getCurrentInstance();
      this.webConfig = WebConfiguration.getInstance(context.getExternalContext());
   }

   public void addRenderer(String family, String rendererType, Renderer renderer) {
      Util.notNull("family", family);
      Util.notNull("rendererType", rendererType);
      Util.notNull("renderer", renderer);
      HashMap renderers = (HashMap)this.rendererFamilies.get(family);
      if (renderers == null) {
         renderers = new HashMap();
         this.rendererFamilies.put(family, renderers);
      }

      if (LOGGER.isLoggable(Level.FINE) && renderers.containsKey(rendererType)) {
         LOGGER.log(Level.FINE, "rendererType {0} has already been registered for family {1}.  Replacing existing renderer class type {2} with {3}.", new Object[]{rendererType, family, ((Renderer)renderers.get(rendererType)).getClass().getName(), renderer.getClass().getName()});
      }

      renderers.put(rendererType, renderer);
   }

   public Renderer getRenderer(String family, String rendererType) {
      Util.notNull("family", family);
      Util.notNull("rendererType", rendererType);

      assert this.rendererFamilies != null;

      HashMap renderers = (HashMap)this.rendererFamilies.get(family);
      return renderers != null ? (Renderer)renderers.get(rendererType) : null;
   }

   public void addClientBehaviorRenderer(String behaviorRendererType, ClientBehaviorRenderer behaviorRenderer) {
      Util.notNull("behaviorRendererType", behaviorRendererType);
      Util.notNull("behaviorRenderer", behaviorRenderer);
      if (LOGGER.isLoggable(Level.FINE) && this.behaviorRenderers.containsKey(behaviorRendererType)) {
         LOGGER.log(Level.FINE, "behaviorRendererType {0} has already been registered.  Replacing existing behavior renderer class type {1} with {2}.", new Object[]{behaviorRendererType, ((ClientBehaviorRenderer)this.behaviorRenderers.get(behaviorRendererType)).getClass().getName(), behaviorRenderer.getClass().getName()});
      }

      this.behaviorRenderers.put(behaviorRendererType, behaviorRenderer);
   }

   public ClientBehaviorRenderer getClientBehaviorRenderer(String behaviorRendererType) {
      Util.notNull("behaviorRendererType", behaviorRendererType);
      return this.behaviorRenderers != null ? (ClientBehaviorRenderer)this.behaviorRenderers.get(behaviorRendererType) : null;
   }

   public Iterator getClientBehaviorRendererTypes() {
      if (null == this.behaviorRenderers) {
         Set empty = Collections.emptySet();
         return empty.iterator();
      } else {
         return this.behaviorRenderers.keySet().iterator();
      }
   }

   public synchronized ResponseStateManager getResponseStateManager() {
      if (this.responseStateManager == null) {
         this.responseStateManager = new ResponseStateManagerImpl();
      }

      return this.responseStateManager;
   }

   public ResponseWriter createResponseWriter(Writer writer, String desiredContentTypeList, String characterEncoding) {
      if (writer == null) {
         return null;
      } else {
         String contentType = null;
         boolean contentTypeNullFromResponse = false;
         FacesContext context = FacesContext.getCurrentInstance();
         if (null != desiredContentTypeList) {
            contentType = this.findMatch(desiredContentTypeList, SUPPORTED_CONTENT_TYPES_ARRAY);
         }

         if (null == desiredContentTypeList) {
            desiredContentTypeList = context.getExternalContext().getResponseContentType();
            if (null != desiredContentTypeList) {
               contentType = this.findMatch(desiredContentTypeList, SUPPORTED_CONTENT_TYPES_ARRAY);
               if (null == contentType) {
                  contentTypeNullFromResponse = true;
               }
            }
         }

         String[] typeArray;
         int i;
         int len;
         if (null == desiredContentTypeList || contentTypeNullFromResponse) {
            typeArray = (String[])context.getExternalContext().getRequestHeaderValuesMap().get("Accept");
            if (typeArray.length > 0) {
               StringBuffer buff = new StringBuffer();
               buff.append(typeArray[0]);
               i = 1;

               for(len = typeArray.length; i < len; ++i) {
                  buff.append(',');
                  buff.append(typeArray[i]);
               }

               desiredContentTypeList = buff.toString();
            }

            if (null != desiredContentTypeList) {
               desiredContentTypeList = RenderKitUtils.determineContentType(desiredContentTypeList, "text/html,application/xhtml+xml,application/xml,text/xml", this.preferXhtml() ? "application/xhtml+xml" : null);
               if (null != desiredContentTypeList) {
                  contentType = this.findMatch(desiredContentTypeList, SUPPORTED_CONTENT_TYPES_ARRAY);
               }
            }
         }

         if (contentType == null) {
            if (null == desiredContentTypeList) {
               contentType = this.getDefaultContentType();
            } else {
               typeArray = this.contentTypeSplit(desiredContentTypeList);
               String[] var13 = typeArray;
               i = typeArray.length;

               for(len = 0; len < i; ++len) {
                  String desiredContentType = var13[len];
                  if ("*/*".equals(desiredContentType.trim())) {
                     contentType = this.getDefaultContentType();
                  }
               }
            }
         }

         if (null == contentType) {
            throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.CONTENT_TYPE_ERROR"));
         } else {
            if (characterEncoding == null) {
               characterEncoding = "UTF-8";
            }

            boolean scriptHiding = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableJSStyleHiding);
            boolean scriptInAttributes = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableScriptInAttributeValue);
            WebConfiguration.DisableUnicodeEscaping escaping = WebConfiguration.DisableUnicodeEscaping.getByValue(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.DisableUnicodeEscaping));
            boolean isPartial = context.getPartialViewContext().isPartialRequest();
            return new HtmlResponseWriter(writer, contentType, characterEncoding, scriptHiding, scriptInAttributes, escaping, isPartial);
         }
      }
   }

   private boolean preferXhtml() {
      return this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.PreferXHTMLContentType);
   }

   private String getDefaultContentType() {
      return this.preferXhtml() ? "application/xhtml+xml" : "text/html";
   }

   private String[] contentTypeSplit(String contentTypeString) {
      Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
      String[] result = Util.split(appMap, contentTypeString, ",");

      for(int i = 0; i < result.length; ++i) {
         int semicolon = result[i].indexOf(";");
         if (-1 != semicolon) {
            result[i] = result[i].substring(0, semicolon);
         }
      }

      return result;
   }

   private String findMatch(String desiredContentTypeList, String[] supportedTypes) {
      String contentType = null;
      String[] desiredTypes = this.contentTypeSplit(desiredContentTypeList);
      int i = 0;

      for(int ilen = desiredTypes.length; i < ilen; ++i) {
         String curDesiredType = desiredTypes[i];
         int j = 0;

         for(int jlen = supportedTypes.length; j < jlen; ++j) {
            String curContentType = supportedTypes[j].trim();
            if (curDesiredType.contains(curContentType)) {
               if (curContentType.contains("text/html")) {
                  contentType = "text/html";
               } else if (curContentType.contains("application/xhtml+xml") || curContentType.contains("application/xml") || curContentType.contains("text/xml")) {
                  contentType = "application/xhtml+xml";
               }
               break;
            }
         }

         if (null != contentType) {
            break;
         }
      }

      return contentType;
   }

   public ResponseStream createResponseStream(final OutputStream out) {
      return new ResponseStream() {
         public void write(int b) throws IOException {
            out.write(b);
         }

         public void write(byte[] b) throws IOException {
            out.write(b);
         }

         public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
         }

         public void flush() throws IOException {
            out.flush();
         }

         public void close() throws IOException {
            out.close();
         }
      };
   }

   public Iterator getComponentFamilies() {
      return this.rendererFamilies.keySet().iterator();
   }

   public Iterator getRendererTypes(String componentFamily) {
      Map family = (Map)this.rendererFamilies.get(componentFamily);
      if (family != null) {
         return family.keySet().iterator();
      } else {
         Set empty = Collections.emptySet();
         return empty.iterator();
      }
   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
      SUPPORTED_CONTENT_TYPES_ARRAY = new String[]{"text/html", "application/xhtml+xml", "application/xml", "text/xml"};
   }
}
