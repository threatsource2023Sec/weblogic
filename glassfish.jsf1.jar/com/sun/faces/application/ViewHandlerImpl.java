package com.sun.faces.application;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

public class ViewHandlerImpl extends ViewHandler {
   private static final Logger logger;
   private ApplicationAssociate associate;
   private String contextDefaultSuffix;
   private int bufSize = -1;

   public ViewHandlerImpl() {
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Created ViewHandler instance ");
      }

   }

   public void initView(FacesContext context) throws FacesException {
      if (context.getExternalContext().getRequestCharacterEncoding() == null) {
         super.initView(context);
      }

   }

   public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      if (viewToRender.isRendered()) {
         ExternalContext extContext = context.getExternalContext();
         ServletRequest request = (ServletRequest)extContext.getRequest();
         ServletResponse response = (ServletResponse)extContext.getResponse();

         try {
            if (this.executePageToBuildView(context, viewToRender)) {
               response.flushBuffer();
               ApplicationAssociate associate = this.getAssociate(context);
               if (associate != null) {
                  associate.responseRendered();
               }

               return;
            }
         } catch (IOException var17) {
            throw new FacesException(var17);
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Completed building view for : \n" + viewToRender.getViewId());
         }

         RenderKitFactory renderFactory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
         RenderKit renderKit = renderFactory.getRenderKit(context, viewToRender.getRenderKitId());
         ResponseWriter oldWriter = context.getResponseWriter();
         if (this.bufSize == -1) {
            WebConfiguration webConfig = WebConfiguration.getInstance(context.getExternalContext());

            try {
               this.bufSize = Integer.parseInt(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResponseBufferSize));
            } catch (NumberFormatException var15) {
               this.bufSize = Integer.parseInt(WebConfiguration.WebContextInitParameter.ResponseBufferSize.getDefaultValue());
            }
         }

         WriteBehindStateWriter stateWriter = new WriteBehindStateWriter(response.getWriter(), context, this.bufSize);
         ResponseWriter newWriter;
         if (null != oldWriter) {
            newWriter = oldWriter.cloneWithWriter(stateWriter);
         } else {
            newWriter = renderKit.createResponseWriter(stateWriter, (String)null, request.getCharacterEncoding());
         }

         context.setResponseWriter(newWriter);

         try {
            newWriter.startDocument();
            this.doRenderView(context, viewToRender);
            newWriter.endDocument();
            if (stateWriter.stateWritten()) {
               stateWriter.flushToWriter();
            }
         } finally {
            stateWriter.release();
            if (null != oldWriter) {
               context.setResponseWriter(oldWriter);
            }

         }

         ViewHandlerResponseWrapper wrapper = (ViewHandlerResponseWrapper)RequestStateManager.remove(context, "com.sun.faces.AFTER_VIEW_CONTENT");
         if (null != wrapper) {
            wrapper.flushToWriter(response.getWriter(), response.getCharacterEncoding());
         }

         response.flushBuffer();
      }
   }

   private void doRenderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      ApplicationAssociate associate = this.getAssociate(context);
      if (null != associate) {
         associate.responseRendered();
      }

      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "About to render view " + viewToRender.getViewId());
      }

      viewToRender.encodeAll(context);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
         ExternalContext extContext = context.getExternalContext();
         String mapping = Util.getFacesMapping(context);
         UIViewRoot viewRoot = null;
         if (mapping != null) {
            if (!Util.isPrefixMapped(mapping)) {
               viewId = this.convertViewId(context, viewId);
            } else {
               viewId = this.normalizeRequestURI(viewId, mapping);
            }
         }

         if (extContext.getRequestPathInfo() == null && mapping != null && Util.isPrefixMapped(mapping)) {
            try {
               context.responseComplete();
               if (logger.isLoggable(Level.FINE)) {
                  logger.log(Level.FINE, "Response Complete for" + viewId);
               }

               extContext.redirect(extContext.getRequestContextPath());
            } catch (IOException var8) {
               throw new FacesException(var8);
            }
         } else {
            ViewHandler outerViewHandler = context.getApplication().getViewHandler();
            String renderKitId = outerViewHandler.calculateRenderKitId(context);
            viewRoot = Util.getStateManager(context).restoreView(context, viewId, renderKitId);
         }

         return viewRoot;
      }
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
         UIViewRoot result = (UIViewRoot)context.getApplication().createComponent("javax.faces.ViewRoot");
         if (viewId != null) {
            String mapping = Util.getFacesMapping(context);
            if (mapping != null) {
               if (!Util.isPrefixMapped(mapping)) {
                  viewId = this.convertViewId(context, viewId);
               } else {
                  viewId = this.normalizeRequestURI(viewId, mapping);
                  if (!Util.isPortletRequest(context) && viewId.equals(mapping)) {
                     this.send404Error(context);
                  }
               }
            }

            result.setViewId(viewId);
         }

         Locale locale = null;
         String renderKitId = null;
         if (context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
            renderKitId = context.getViewRoot().getRenderKitId();
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Created new view for " + viewId);
         }

         if (locale == null) {
            locale = context.getApplication().getViewHandler().calculateLocale(context);
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("Locale for this view as determined by calculateLocale " + locale.toString());
            }
         } else if (logger.isLoggable(Level.FINE)) {
            logger.fine("Using locale from previous view " + locale.toString());
         }

         if (renderKitId == null) {
            renderKitId = context.getApplication().getViewHandler().calculateRenderKitId(context);
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("RenderKitId for this view as determined by calculateRenderKitId " + renderKitId);
            }
         } else if (logger.isLoggable(Level.FINE)) {
            logger.fine("Using renderKitId from previous view " + renderKitId);
         }

         result.setLocale(locale);
         result.setRenderKitId(renderKitId);
         return result;
      }
   }

   private boolean executePageToBuildView(FacesContext context, UIViewRoot viewToExecute) throws IOException {
      String message;
      if (null == context) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (null == viewToExecute) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "viewToExecute");
         throw new NullPointerException(message);
      } else {
         ExternalContext extContext = context.getExternalContext();
         Map stateMap = RequestStateManager.getStateMap(context);
         if ("/*".equals(stateMap.get("com.sun.faces.INVOCATION_PATH"))) {
            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.FACES_SERVLET_MAPPING_INCORRECT"));
         } else {
            String requestURI = viewToExecute.getViewId();
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("About to execute view " + requestURI);
            }

            if (extContext.getRequest() instanceof ServletRequest) {
               Config.set((ServletRequest)extContext.getRequest(), "javax.servlet.jsp.jstl.fmt.locale", context.getViewRoot().getLocale());
            }

            if (logger.isLoggable(Level.FINE)) {
               logger.fine("Before dispacthMessage to viewId " + requestURI);
            }

            Object originalResponse = extContext.getResponse();
            ViewHandlerResponseWrapper wrapped = getWrapper(extContext);
            extContext.setResponse(wrapped);
            extContext.dispatch(requestURI);
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("After dispacthMessage to viewId " + requestURI);
            }

            extContext.setResponse(originalResponse);
            if (wrapped.getStatus() >= 200 && wrapped.getStatus() <= 299) {
               stateMap.put("com.sun.faces.AFTER_VIEW_CONTENT", wrapped);
               return false;
            } else {
               wrapped.flushContentToWrappedResponse();
               return true;
            }
         }
      }
   }

   public Locale calculateLocale(FacesContext context) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
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
   }

   public String calculateRenderKitId(FacesContext context) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
         Map requestParamMap = context.getExternalContext().getRequestParameterMap();
         String result = (String)requestParamMap.get("javax.faces.RenderKitId");
         if (result == null && null == (result = context.getApplication().getDefaultRenderKitId())) {
            result = "HTML_BASIC";
         }

         return result;
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

      if (null == result) {
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

   public void writeState(FacesContext context) throws IOException {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("Begin writing marker for viewId " + context.getViewRoot().getViewId());
         }

         WriteBehindStateWriter writer = ViewHandlerImpl.WriteBehindStateWriter.getCurrentInstance();
         if (writer != null) {
            writer.writingState();
         }

         context.getResponseWriter().write("~com.sun.faces.saveStateFieldMarker~");
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("End writing marker for viewId " + context.getViewRoot().getViewId());
         }

      }
   }

   public String getActionURL(FacesContext context, String viewId) {
      String message;
      if (context == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (viewId == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "viewId");
         throw new NullPointerException(message);
      } else if (viewId.charAt(0) != '/') {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_VIEW_ID", viewId);
         if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, "jsf.illegal_view_id_error", viewId);
         }

         throw new IllegalArgumentException(message);
      } else {
         ExternalContext extContext = context.getExternalContext();
         String contextPath = extContext.getRequestContextPath();
         String mapping = Util.getFacesMapping(context);
         if (mapping == null) {
            return contextPath + viewId;
         } else if (Util.isPrefixMapped(mapping)) {
            return mapping.equals("/*") ? contextPath + viewId : contextPath + mapping + viewId;
         } else {
            return viewId.endsWith(this.getDefaultSuffix(context)) ? contextPath + viewId.substring(0, viewId.lastIndexOf(46)) + mapping : contextPath + viewId;
         }
      }
   }

   public String getResourceURL(FacesContext context, String path) {
      ExternalContext extContext = context.getExternalContext();
      return path.startsWith("/") ? extContext.getRequestContextPath() + path : path;
   }

   private String normalizeRequestURI(String uri, String mapping) {
      if (mapping != null && Util.isPrefixMapped(mapping)) {
         int length = mapping.length() + 1;
         StringBuilder builder = new StringBuilder(length);
         builder.append(mapping).append('/');
         String mappingMod = builder.toString();

         for(boolean logged = false; uri.startsWith(mappingMod); uri = uri.substring(length - 1)) {
            if (!logged && logger.isLoggable(Level.WARNING)) {
               logged = true;
               logger.log(Level.WARNING, "jsf.viewhandler.requestpath.recursion", new Object[]{uri, mapping});
            }
         }

         return uri;
      } else {
         return uri;
      }
   }

   private void send404Error(FacesContext context) {
      HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();

      try {
         context.responseComplete();
         response.sendError(404);
      } catch (IOException var4) {
         throw new FacesException(var4);
      }
   }

   private String convertViewId(FacesContext context, String viewId) {
      String suffix = this.getDefaultSuffix(context);
      String convertedViewId = viewId;
      if (!viewId.endsWith(suffix)) {
         StringBuilder buffer = new StringBuilder(viewId);
         int extIdx = viewId.lastIndexOf(46);
         if (extIdx != -1) {
            buffer.replace(extIdx, viewId.length(), suffix);
         } else {
            buffer.append(suffix);
         }

         convertedViewId = buffer.toString();
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("viewId after appending the context suffix " + convertedViewId);
         }
      }

      return convertedViewId;
   }

   private String getDefaultSuffix(FacesContext context) {
      if (this.contextDefaultSuffix == null) {
         this.contextDefaultSuffix = WebConfiguration.getInstance(context.getExternalContext()).getOptionValue(WebConfiguration.WebContextInitParameter.JspDefaultSuffix);
         if (this.contextDefaultSuffix == null) {
            this.contextDefaultSuffix = ".jsp";
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.fine("contextDefaultSuffix " + this.contextDefaultSuffix);
         }
      }

      return this.contextDefaultSuffix;
   }

   private ApplicationAssociate getAssociate(FacesContext context) {
      if (this.associate == null) {
         this.associate = ApplicationAssociate.getInstance(context.getExternalContext());
      }

      return this.associate;
   }

   private static ViewHandlerResponseWrapper getWrapper(ExternalContext extContext) {
      Object response = extContext.getResponse();
      if (response instanceof HttpServletResponse) {
         return new ViewHandlerResponseWrapper((HttpServletResponse)response);
      } else {
         throw new IllegalArgumentException();
      }
   }

   static {
      logger = FacesLogger.APPLICATION.getLogger();
   }

   private static final class WriteBehindStateWriter extends Writer {
      private static final int STATE_MARKER_LEN = "~com.sun.faces.saveStateFieldMarker~".length();
      private static final ThreadLocal CUR_WRITER = new ThreadLocal();
      private Writer out;
      private Writer orig;
      private FastStringWriter fWriter;
      private boolean stateWritten;
      private int bufSize;
      private char[] buf;
      private FacesContext context;

      public WriteBehindStateWriter(Writer out, FacesContext context, int bufSize) {
         this.out = out;
         this.orig = out;
         this.context = context;
         this.bufSize = bufSize;
         this.buf = new char[bufSize];
         CUR_WRITER.set(this);
      }

      public void write(int c) throws IOException {
         this.out.write(c);
      }

      public void write(char[] cbuf) throws IOException {
         this.out.write(cbuf);
      }

      public void write(String str) throws IOException {
         this.out.write(str);
      }

      public void write(String str, int off, int len) throws IOException {
         this.out.write(str, off, len);
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
         this.out.write(cbuf, off, len);
      }

      public void flush() throws IOException {
      }

      public void close() throws IOException {
      }

      public static WriteBehindStateWriter getCurrentInstance() {
         return (WriteBehindStateWriter)CUR_WRITER.get();
      }

      public void release() {
         CUR_WRITER.remove();
      }

      public void writingState() {
         if (!this.stateWritten) {
            this.stateWritten = true;
            this.out = this.fWriter = new FastStringWriter(1024);
         }

      }

      public boolean stateWritten() {
         return this.stateWritten;
      }

      public void flushToWriter() throws IOException {
         StateManager stateManager = Util.getStateManager(this.context);
         ResponseWriter origWriter = this.context.getResponseWriter();
         FastStringWriter state = new FastStringWriter(stateManager.isSavingStateInClient(this.context) ? this.bufSize : 128);
         this.context.setResponseWriter(origWriter.cloneWithWriter(state));
         stateManager.writeState(this.context, stateManager.saveView(this.context));
         this.context.setResponseWriter(origWriter);
         StringBuilder builder = this.fWriter.getBuffer();
         int totalLen = builder.length();
         StringBuilder stateBuilder = state.getBuffer();
         int stateLen = stateBuilder.length();
         int pos = 0;
         int tildeIdx = getNextDelimiterIndex(builder, pos);

         while(true) {
            while(true) {
               while(pos < totalLen) {
                  int len;
                  if (tildeIdx != -1) {
                     if (tildeIdx > pos && tildeIdx - pos > this.bufSize) {
                        builder.getChars(pos, pos + this.bufSize, this.buf, 0);
                        this.orig.write(this.buf);
                        pos += this.bufSize;
                     } else {
                        builder.getChars(pos, tildeIdx, this.buf, 0);
                        len = tildeIdx - pos;
                        this.orig.write(this.buf, 0, len);
                        if (builder.indexOf("~com.sun.faces.saveStateFieldMarker~", pos) != tildeIdx) {
                           pos = tildeIdx;
                           tildeIdx = getNextDelimiterIndex(builder, tildeIdx + 1);
                        } else {
                           int statePos = 0;

                           while(statePos < stateLen) {
                              if (stateLen - statePos > this.bufSize) {
                                 stateBuilder.getChars(statePos, statePos + this.bufSize, this.buf, 0);
                                 this.orig.write(this.buf);
                                 statePos += this.bufSize;
                              } else {
                                 int slen = stateLen - statePos;
                                 stateBuilder.getChars(statePos, stateLen, this.buf, 0);
                                 this.orig.write(this.buf, 0, slen);
                                 statePos += slen;
                              }
                           }

                           pos += len + STATE_MARKER_LEN;
                           tildeIdx = getNextDelimiterIndex(builder, pos);
                        }
                     }
                  } else if (totalLen - pos > this.bufSize) {
                     builder.getChars(pos, pos + this.bufSize, this.buf, 0);
                     this.orig.write(this.buf);
                     pos += this.bufSize;
                  } else {
                     builder.getChars(pos, totalLen, this.buf, 0);
                     len = totalLen - pos;
                     this.orig.write(this.buf, 0, len);
                     pos += len + 1;
                  }
               }

               this.out = this.orig;
               return;
            }
         }
      }

      private static int getNextDelimiterIndex(StringBuilder builder, int offset) {
         return builder.indexOf("~", offset);
      }
   }
}
