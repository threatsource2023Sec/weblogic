package com.sun.faces.application.view;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewVisitOption;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;
import javax.faces.view.ViewMetadata;

public class MultiViewHandler extends ViewHandler {
   private static final Logger LOGGER;
   private String[] configuredExtensions;
   private Set protectedViews;
   private boolean extensionsSet;
   private ViewDeclarationLanguageFactory vdlFactory;

   public MultiViewHandler() {
      WebConfiguration config = WebConfiguration.getInstance();
      this.configuredExtensions = config.getConfiguredExtensions();
      this.extensionsSet = config.isSet(WebConfiguration.WebContextInitParameter.DefaultSuffix);
      this.vdlFactory = (ViewDeclarationLanguageFactory)FactoryFinder.getFactory("javax.faces.view.ViewDeclarationLanguageFactory");
      this.protectedViews = new CopyOnWriteArraySet();
   }

   public void initView(FacesContext context) throws FacesException {
      super.initView(context);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      Util.notNull("context", context);
      String physicalViewId = this.derivePhysicalViewId(context, viewId, false);
      return this.vdlFactory.getViewDeclarationLanguage(physicalViewId).restoreView(context, physicalViewId);
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      Util.notNull("context", context);
      String physicalViewId = this.derivePhysicalViewId(context, viewId, false);
      return this.vdlFactory.getViewDeclarationLanguage(physicalViewId).createView(context, physicalViewId);
   }

   public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      Util.notNull("context", context);
      Util.notNull("viewToRender", viewToRender);
      this.vdlFactory.getViewDeclarationLanguage(viewToRender.getViewId()).renderView(context, viewToRender);
   }

   public Locale calculateLocale(FacesContext context) {
      Util.notNull("context", context);
      Locale result = null;
      Iterator locales = context.getExternalContext().getRequestLocales();

      while(locales.hasNext()) {
         Locale perf = (Locale)locales.next();
         result = this.findMatch(context, perf);
         if (result != null) {
            break;
         }
      }

      if (result == null) {
         if (context.getApplication().getDefaultLocale() == null) {
            result = Locale.getDefault();
         } else {
            result = context.getApplication().getDefaultLocale();
         }
      }

      return result;
   }

   public String calculateRenderKitId(FacesContext context) {
      Util.notNull("context", context);
      String result = RenderKitUtils.PredefinedPostbackParameter.RENDER_KIT_ID_PARAM.getValue(context);
      if (result == null && null == (result = context.getApplication().getDefaultRenderKitId())) {
         result = "HTML_BASIC";
      }

      return result;
   }

   public void writeState(FacesContext context) throws IOException {
      Util.notNull("context", context);
      if (!context.getPartialViewContext().isAjaxRequest()) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Begin writing marker for viewId " + context.getViewRoot().getViewId());
         }

         WriteBehindStateWriter writer = WriteBehindStateWriter.getCurrentInstance();
         if (writer != null) {
            writer.writingState();
         }

         context.getResponseWriter().write("~com.sun.faces.saveStateFieldMarker~");
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("End writing marker for viewId " + context.getViewRoot().getViewId());
         }
      }

   }

   public String getActionURL(FacesContext context, String viewId) {
      String result = this.getActionURLWithoutViewProtection(context, viewId);
      ViewHandler viewHandler = context.getApplication().getViewHandler();
      Set urlPatterns = viewHandler.getProtectedViewsUnmodifiable();
      boolean viewIdIsProtected = false;
      Iterator var7 = urlPatterns.iterator();

      String rkId;
      while(var7.hasNext()) {
         rkId = (String)var7.next();
         if (rkId.equals(viewId)) {
            viewIdIsProtected = true;
         }

         if (viewIdIsProtected) {
            break;
         }
      }

      if (viewIdIsProtected) {
         StringBuilder builder = new StringBuilder(result);
         if (result.contains("?")) {
            builder.append("&");
         } else {
            builder.append("?");
         }

         rkId = viewHandler.calculateRenderKitId(context);
         ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, rkId);
         String tokenValue = rsm.getCryptographicallyStrongTokenFromSession(context);
         builder.append("javax.faces.Token").append("=").append(tokenValue);
         result = builder.toString();
      }

      return result;
   }

   public String getResourceURL(FacesContext context, String path) {
      if (context != null && path != null) {
         return path.charAt(0) == '/' ? context.getExternalContext().getRequestContextPath() + path : path;
      } else {
         throw new NullPointerException();
      }
   }

   public String getWebsocketURL(FacesContext context, String channel) {
      Objects.requireNonNull(context, "context");
      Objects.requireNonNull(channel, "channel");
      ExternalContext externalContext = context.getExternalContext();
      String contextPath = externalContext.getRequestContextPath();
      return externalContext.encodeWebsocketURL(contextPath + "/javax.faces.push" + "/" + channel);
   }

   public String getBookmarkableURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      Map params;
      if (includeViewParams) {
         params = this.getFullParameterList(context, viewId, parameters);
      } else {
         params = parameters;
      }

      ExternalContext ectx = context.getExternalContext();
      return ectx.encodeActionURL(ectx.encodeBookmarkableURL(Util.getViewHandler(context).getActionURL(context, viewId), params));
   }

   public void addProtectedView(String urlPattern) {
      this.protectedViews.add(urlPattern);
   }

   public Set getProtectedViewsUnmodifiable() {
      return Collections.unmodifiableSet(this.protectedViews);
   }

   public boolean removeProtectedView(String urlPattern) {
      return this.protectedViews.remove(urlPattern);
   }

   public String getRedirectURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      String encodingFromContext = (String)context.getAttributes().get("facelets.Encoding");
      if (encodingFromContext == null) {
         encodingFromContext = (String)context.getViewRoot().getAttributes().get("facelets.Encoding");
      }

      String responseEncoding;
      if (encodingFromContext == null) {
         try {
            responseEncoding = context.getExternalContext().getResponseCharacterEncoding();
         } catch (Exception var17) {
            if (LOGGER.isLoggable(Level.FINE)) {
               String message = "Unable to obtain response character encoding from ExternalContext {0}.  Using UTF-8.";
               message = MessageFormat.format(message, context.getExternalContext());
               LOGGER.log(Level.FINE, message, var17);
            }

            responseEncoding = "UTF-8";
         }
      } else {
         responseEncoding = encodingFromContext;
      }

      if (parameters != null) {
         Map decodedParameters = new HashMap();
         Iterator var19 = ((Map)parameters).entrySet().iterator();

         while(var19.hasNext()) {
            Map.Entry entry = (Map.Entry)var19.next();
            String string = (String)entry.getKey();
            List list = (List)entry.getValue();
            List values = new ArrayList();

            String value;
            for(Iterator it = list.iterator(); it.hasNext(); values.add(value)) {
               value = (String)it.next();

               try {
                  value = URLDecoder.decode(value, responseEncoding);
               } catch (UnsupportedEncodingException var16) {
                  throw new RuntimeException("Unable to decode");
               }
            }

            decodedParameters.put(string, values);
         }

         parameters = decodedParameters;
      }

      Object params;
      if (includeViewParams) {
         params = this.getFullParameterList(context, viewId, (Map)parameters);
      } else {
         params = parameters;
      }

      ExternalContext ectx = context.getExternalContext();
      return ectx.encodeActionURL(ectx.encodeRedirectURL(Util.getViewHandler(context).getActionURL(context, viewId), (Map)params));
   }

   public ViewDeclarationLanguage getViewDeclarationLanguage(FacesContext context, String viewId) {
      return this.vdlFactory.getViewDeclarationLanguage(viewId);
   }

   public Stream getViews(FacesContext context, String path, ViewVisitOption... options) {
      return this.vdlFactory.getAllViewDeclarationLanguages().stream().flatMap((vdl) -> {
         return vdl.getViews(context, path, options);
      });
   }

   public Stream getViews(FacesContext context, String path, int maxDepth, ViewVisitOption... options) {
      return this.vdlFactory.getAllViewDeclarationLanguages().stream().flatMap((vdl) -> {
         return vdl.getViews(context, path, maxDepth, options);
      });
   }

   public String deriveViewId(FacesContext context, String requestViewId) {
      return this.derivePhysicalViewId(context, requestViewId, true);
   }

   public String deriveLogicalViewId(FacesContext context, String requestViewId) {
      return this.derivePhysicalViewId(context, requestViewId, false);
   }

   protected String normalizeRequestURI(String uri, String mapping) {
      if (mapping != null && Util.isPrefixMapped(mapping)) {
         int length = mapping.length() + 1;
         StringBuilder builder = new StringBuilder(length);
         builder.append(mapping).append('/');
         String mappingMod = builder.toString();

         for(boolean logged = false; uri.startsWith(mappingMod); uri = uri.substring(length - 1)) {
            if (!logged && LOGGER.isLoggable(Level.WARNING)) {
               logged = true;
               LOGGER.log(Level.WARNING, "jsf.viewhandler.requestpath.recursion", new Object[]{uri, mapping});
            }
         }

         return uri;
      } else {
         return uri;
      }
   }

   protected String convertViewId(FacesContext context, String viewId) {
      int extIdx = viewId.lastIndexOf(46);
      int length = viewId.length();
      StringBuilder buffer = new StringBuilder(length);
      String[] var6 = this.configuredExtensions;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String ext = var6[var8];
         if (viewId.endsWith(ext)) {
            return viewId;
         }

         this.appendOrReplaceExtension(viewId, ext, length, extIdx, buffer);
         String convertedViewId = buffer.toString();
         ViewDeclarationLanguage vdl = this.getViewDeclarationLanguage(context, convertedViewId);
         if (vdl.viewExists(context, convertedViewId)) {
            return convertedViewId;
         }
      }

      return this.legacyConvertViewId(viewId, length, extIdx, buffer);
   }

   protected String derivePhysicalViewId(FacesContext ctx, String requestViewId, boolean checkPhysical) {
      if (requestViewId != null) {
         String mapping = Util.getFacesMapping(ctx);
         if (mapping != null) {
            String physicalViewId;
            if (Util.isExactMapped(mapping)) {
               physicalViewId = this.convertViewId(ctx, requestViewId);
            } else if (!Util.isPrefixMapped(mapping)) {
               physicalViewId = this.convertViewId(ctx, requestViewId);
            } else {
               physicalViewId = this.normalizeRequestURI(requestViewId, mapping);
               if (physicalViewId.equals(mapping)) {
                  this.send404Error(ctx);
               }
            }

            if (checkPhysical) {
               ViewDeclarationLanguage vdl = this.getViewDeclarationLanguage(ctx, physicalViewId);
               return vdl.viewExists(ctx, physicalViewId) ? physicalViewId : null;
            }

            return physicalViewId;
         }
      }

      return requestViewId;
   }

   protected Map getFullParameterList(FacesContext ctx, String viewId, Map existingParameters) {
      LinkedHashMap copy;
      if (existingParameters != null && !existingParameters.isEmpty()) {
         copy = new LinkedHashMap(existingParameters);
      } else {
         copy = new LinkedHashMap(4);
      }

      this.addViewParameters(ctx, viewId, copy);
      return copy;
   }

   protected void addViewParameters(FacesContext ctx, String viewId, Map existingParameters) {
      UIViewRoot currentRoot = ctx.getViewRoot();
      String currentViewId = currentRoot.getViewId();
      Collection toViewParams = Collections.emptyList();
      boolean currentIsSameAsNew = false;
      Collection currentViewParams = ViewMetadata.getViewParameters(currentRoot);
      if (currentViewId.equals(viewId)) {
         currentIsSameAsNew = true;
         toViewParams = currentViewParams;
      } else {
         ViewDeclarationLanguage pdl = this.getViewDeclarationLanguage(ctx, viewId);
         ViewMetadata viewMetadata = pdl.getViewMetadata(ctx, viewId);
         if (null != viewMetadata) {
            UIViewRoot root = viewMetadata.createMetadataView(ctx);
            toViewParams = ViewMetadata.getViewParameters(root);
         }
      }

      if (!((Collection)toViewParams).isEmpty()) {
         Iterator var13 = ((Collection)toViewParams).iterator();

         while(var13.hasNext()) {
            UIViewParameter viewParam = (UIViewParameter)var13.next();
            String value = null;
            if (!existingParameters.containsKey(viewParam.getName())) {
               if (paramHasValueExpression(viewParam)) {
                  value = viewParam.getStringValueFromModel(ctx);
               }

               if (value == null) {
                  if (currentIsSameAsNew) {
                     value = viewParam.getStringValue(ctx);
                  } else {
                     value = getStringValueToTransfer(ctx, viewParam, currentViewParams);
                  }
               }

               if (value != null) {
                  List existing = (List)existingParameters.get(viewParam.getName());
                  if (existing == null) {
                     existing = new ArrayList(4);
                     existingParameters.put(viewParam.getName(), existing);
                  }

                  ((List)existing).add(value);
               }
            }
         }

      }
   }

   protected Locale findMatch(FacesContext context, Locale pref) {
      Locale result = null;
      Iterator it = context.getApplication().getSupportedLocales();

      Locale defaultLocale;
      while(it.hasNext()) {
         defaultLocale = (Locale)it.next();
         if (pref.equals(defaultLocale)) {
            result = defaultLocale;
            break;
         }

         if (pref.getLanguage().equals(defaultLocale.getLanguage()) && defaultLocale.getCountry().length() == 0) {
            result = defaultLocale;
         }
      }

      if (result == null) {
         defaultLocale = context.getApplication().getDefaultLocale();
         if (defaultLocale != null) {
            if (pref.equals(defaultLocale)) {
               result = defaultLocale;
            } else if (pref.getLanguage().equals(defaultLocale.getLanguage()) && defaultLocale.getCountry().length() == 0) {
               result = defaultLocale;
            }
         }
      }

      return result;
   }

   protected void send404Error(FacesContext context) {
      try {
         context.responseComplete();
         context.getExternalContext().responseSendError(404, "");
      } catch (IOException var3) {
         throw new FacesException(var3);
      }
   }

   private String getActionURLWithoutViewProtection(FacesContext context, String viewId) {
      Util.notNull("context", context);
      Util.notNull("viewId", viewId);
      if (viewId.length() != 0 && viewId.charAt(0) == '/') {
         ExternalContext extContext = context.getExternalContext();
         String contextPath = extContext.getRequestContextPath();
         String mapping = Util.getFacesMapping(context);
         if (mapping == null) {
            return contextPath + viewId;
         } else {
            int var8;
            String extension;
            if (Util.isExactMapped(mapping)) {
               if (viewId.contains(".")) {
                  String[] var6 = this.configuredExtensions;
                  int var7 = var6.length;

                  for(var8 = 0; var8 < var7; ++var8) {
                     String extension = var6[var8];
                     if (viewId.endsWith(extension)) {
                        extension = viewId.substring(0, viewId.lastIndexOf(extension));
                        if (Util.isViewIdExactMappedToFacesServlet(extension)) {
                           return contextPath + extension;
                        }
                     }
                  }
               } else if (Util.isViewIdExactMappedToFacesServlet(viewId)) {
                  return contextPath + viewId;
               }

               String servletMapping = Util.getFirstWildCardMappingToFacesServlet(context.getExternalContext());
               if (servletMapping == null) {
                  throw new IllegalStateException("No suitable mapping for FacesServlet found. To serve views that are not exact mapped FacesServlet should have at least one prefix or suffix mapping.");
               }

               mapping = servletMapping.replace("*", "");
            }

            if (Util.isPrefixMapped(mapping)) {
               return mapping.equals("/*") ? contextPath + viewId : contextPath + mapping + viewId;
            } else {
               int period = viewId.lastIndexOf(46);
               if (period < 0) {
                  return contextPath + viewId + mapping;
               } else if (!viewId.endsWith(mapping)) {
                  String[] var13 = this.configuredExtensions;
                  var8 = var13.length;

                  for(int var14 = 0; var14 < var8; ++var14) {
                     extension = var13[var14];
                     if (viewId.endsWith(extension)) {
                        return contextPath + viewId.substring(0, viewId.indexOf(extension)) + mapping;
                     }
                  }

                  return contextPath + viewId.substring(0, period) + mapping;
               } else {
                  return contextPath + viewId;
               }
            }
         }
      } else {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "jsf.illegal_view_id_error", viewId);
         }

         throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_VIEW_ID", viewId));
      }
   }

   private static boolean paramHasValueExpression(UIViewParameter param) {
      return param.getValueExpression("value") != null;
   }

   private static String getStringValueToTransfer(FacesContext context, UIViewParameter param, Collection viewParams) {
      if (viewParams != null && !viewParams.isEmpty()) {
         Iterator var3 = viewParams.iterator();

         while(var3.hasNext()) {
            UIViewParameter candidate = (UIViewParameter)var3.next();
            if (candidate.getName() != null && param.getName() != null && candidate.getName().equals(param.getName())) {
               return candidate.getStringValue(context);
            }
         }
      }

      return param.getStringValue(context);
   }

   private void appendOrReplaceExtension(String viewId, String extension, int length, int extensionIndex, StringBuilder buffer) {
      buffer.setLength(0);
      buffer.append(viewId);
      if (extensionIndex != -1) {
         buffer.replace(extensionIndex, length, extension);
      } else {
         buffer.append(extension);
      }

   }

   private String legacyConvertViewId(String viewId, int length, int extensionIndex, StringBuilder buffer) {
      String extension = this.extensionsSet && this.configuredExtensions.length != 0 ? this.configuredExtensions[0] : ".jsp";
      if (viewId.endsWith(extension)) {
         return viewId;
      } else {
         this.appendOrReplaceExtension(viewId, extension, length, extensionIndex, buffer);
         return buffer.toString();
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
