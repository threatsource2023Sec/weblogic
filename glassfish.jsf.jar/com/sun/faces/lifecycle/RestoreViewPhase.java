package com.sun.faces.lifecycle;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.application.ProtectedViewException;
import javax.faces.application.ViewExpiredException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.flow.FlowHandler;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.ResponseStateManager;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewMetadata;

public class RestoreViewPhase extends Phase {
   private static final String WEBAPP_ERROR_PAGE_MARKER = "javax.servlet.error.message";
   private static final Logger LOGGER;
   private WebConfiguration webConfig;
   private static String SKIP_ITERATION_HINT;
   // $FF: synthetic field
   static final boolean $assertionsDisabled = !RestoreViewPhase.class.desiredAssertionStatus();

   public PhaseId getId() {
      return PhaseId.RESTORE_VIEW;
   }

   public void doPhase(FacesContext context, Lifecycle lifecycle, ListIterator listeners) {
      Util.getViewHandler(context).initView(context);
      super.doPhase(context, lifecycle, listeners);
      this.notifyAfter(context, lifecycle);
   }

   public void execute(FacesContext facesContext) throws FacesException {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Entering RestoreViewPhase");
      }

      if (facesContext == null) {
         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_CONTEXT_ERROR"));
      } else {
         UIViewRoot viewRoot = facesContext.getViewRoot();
         if (viewRoot != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Found a pre created view in FacesContext");
            }

            facesContext.getViewRoot().setLocale(facesContext.getExternalContext().getRequestLocale());
            this.deliverPostRestoreStateEvent(facesContext);
            if (!facesContext.isPostback()) {
               facesContext.renderResponse();
            }

         } else {
            FacesException thrownException = null;
            boolean var14 = false;

            label303: {
               FlowHandler flowHandler;
               label304: {
                  try {
                     var14 = true;
                     Map requestMap = facesContext.getExternalContext().getRequestMap();
                     String viewId = (String)requestMap.get("javax.servlet.include.path_info");
                     if (viewId == null) {
                        viewId = facesContext.getExternalContext().getRequestPathInfo();
                     }

                     if (viewId == null) {
                        viewId = (String)requestMap.get("javax.servlet.include.servlet_path");
                     }

                     if (viewId == null) {
                        viewId = facesContext.getExternalContext().getRequestServletPath();
                     }

                     if (viewId == null) {
                        throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_REQUEST_VIEW_ERROR"));
                     }

                     ViewHandler viewHandler = Util.getViewHandler(facesContext);
                     if (facesContext.isPostback() && !isErrorPage(facesContext)) {
                        facesContext.setProcessingEvents(false);
                        viewRoot = viewHandler.restoreView(facesContext, viewId);
                        if (viewRoot == null) {
                           if (!this.is11CompatEnabled(facesContext)) {
                              Object[] params = new Object[]{viewId};
                              throw new ViewExpiredException(MessageUtils.getExceptionMessageString("com.sun.faces.RESTORE_VIEW_ERROR", params), viewId);
                           }

                           if (LOGGER.isLoggable(Level.FINE)) {
                              LOGGER.fine("Postback: recreating a view for " + viewId);
                           }

                           viewRoot = viewHandler.createView(facesContext, viewId);
                           facesContext.renderResponse();
                        }

                        facesContext.setViewRoot(viewRoot);
                        facesContext.setProcessingEvents(true);
                        if (LOGGER.isLoggable(Level.FINE)) {
                           LOGGER.fine("Postback: restored view for " + viewId);
                           var14 = false;
                        } else {
                           var14 = false;
                        }
                     } else {
                        if (LOGGER.isLoggable(Level.FINE)) {
                           LOGGER.fine("New request: creating a view for " + viewId);
                        }

                        String logicalViewId = viewHandler.deriveLogicalViewId(facesContext, viewId);
                        ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(facesContext, logicalViewId);
                        this.maybeTakeProtectedViewAction(facesContext, viewHandler, vdl, logicalViewId);
                        ViewMetadata metadata = null;
                        if (vdl != null) {
                           metadata = vdl.getViewMetadata(facesContext, logicalViewId);
                           if (metadata != null) {
                              viewRoot = metadata.createMetadataView(facesContext);
                              if (!ViewMetadata.hasMetadata(viewRoot)) {
                                 facesContext.renderResponse();
                              }
                           }
                        }

                        if (vdl == null || metadata == null) {
                           facesContext.renderResponse();
                        }

                        if (viewRoot == null) {
                           viewRoot = Util.getViewHandler(facesContext).createView(facesContext, logicalViewId);
                        }

                        facesContext.setViewRoot(viewRoot);
                        if (!$assertionsDisabled) {
                           if (viewRoot == null) {
                              throw new AssertionError();
                           }

                           var14 = false;
                        } else {
                           var14 = false;
                        }
                     }
                     break label304;
                  } catch (Throwable var15) {
                     if (var15 instanceof FacesException) {
                        thrownException = (FacesException)var15;
                        var14 = false;
                     } else {
                        thrownException = new FacesException(var15);
                        var14 = false;
                     }
                  } finally {
                     if (var14) {
                        if (thrownException == null) {
                           FlowHandler flowHandler = facesContext.getApplication().getFlowHandler();
                           if (flowHandler != null) {
                              flowHandler.clientWindowTransition(facesContext);
                           }

                           this.deliverPostRestoreStateEvent(facesContext);
                        }

                        throw thrownException;
                     }
                  }

                  if (thrownException != null) {
                     throw thrownException;
                  }

                  flowHandler = facesContext.getApplication().getFlowHandler();
                  if (flowHandler != null) {
                     flowHandler.clientWindowTransition(facesContext);
                  }

                  this.deliverPostRestoreStateEvent(facesContext);
                  break label303;
               }

               if (thrownException != null) {
                  throw thrownException;
               }

               flowHandler = facesContext.getApplication().getFlowHandler();
               if (flowHandler != null) {
                  flowHandler.clientWindowTransition(facesContext);
               }

               this.deliverPostRestoreStateEvent(facesContext);
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Exiting RestoreViewPhase");
            }

         }
      }
   }

   private void maybeTakeProtectedViewAction(FacesContext context, ViewHandler viewHandler, ViewDeclarationLanguage vdl, String viewId) {
      Set urlPatterns = viewHandler.getProtectedViewsUnmodifiable();
      boolean currentViewIsProtected = this.isProtectedView(viewId, urlPatterns);
      if (currentViewIsProtected) {
         ExternalContext extContext = context.getExternalContext();
         Map headers = extContext.getRequestHeaderMap();
         String rkId = viewHandler.calculateRenderKitId(context);
         ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, rkId);
         String incomingSecretKeyValue = (String)extContext.getRequestParameterMap().get("javax.faces.Token");
         if (null != incomingSecretKeyValue) {
            try {
               incomingSecretKeyValue = URLEncoder.encode(incomingSecretKeyValue, "UTF-8");
            } catch (UnsupportedEncodingException var19) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to re-encode value of request parameter javax.faces.Token:" + incomingSecretKeyValue, var19);
               }

               incomingSecretKeyValue = null;
            }
         }

         String correctSecretKeyValue = rsm.getCryptographicallyStrongTokenFromSession(context);
         if (null == incomingSecretKeyValue || !correctSecretKeyValue.equals(incomingSecretKeyValue)) {
            LOGGER.log(Level.SEVERE, "correctSecretKeyValue = {0} incomingSecretKeyValue = {1}", new Object[]{correctSecretKeyValue, incomingSecretKeyValue});
            throw new ProtectedViewException();
         }

         String origin;
         boolean originIsInProtectedSet;
         boolean originOriginatesInThisWebapp;
         String message;
         if (headers.containsKey("Referer")) {
            origin = (String)headers.get("Referer");
            originIsInProtectedSet = this.isProtectedView(origin, urlPatterns);
            if (!originIsInProtectedSet) {
               originOriginatesInThisWebapp = false;

               try {
                  originOriginatesInThisWebapp = this.originatesInWebapp(context, origin, vdl);
               } catch (URISyntaxException var18) {
                  throw new ProtectedViewException(var18);
               }

               if (!originOriginatesInThisWebapp) {
                  message = FacesLogger.LIFECYCLE.interpolateMessage(context, "jsf.lifecycle.invalid.referer", new String[]{origin, viewId});
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, message);
                  }

                  throw new ProtectedViewException(message);
               }
            }
         }

         if (headers.containsKey("Origin")) {
            origin = (String)headers.get("Origin");
            originIsInProtectedSet = this.isProtectedView(origin, urlPatterns);
            if (!originIsInProtectedSet) {
               originOriginatesInThisWebapp = false;

               try {
                  originOriginatesInThisWebapp = this.originatesInWebapp(context, origin, vdl);
               } catch (URISyntaxException var17) {
                  throw new ProtectedViewException(var17);
               }

               if (!originOriginatesInThisWebapp) {
                  message = FacesLogger.LIFECYCLE.interpolateMessage(context, "jsf.lifecycle.invalid.origin", new String[]{origin, viewId});
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, message);
                  }

                  throw new ProtectedViewException(message);
               }
            }
         }
      }

   }

   private boolean isProtectedView(String viewToCheck, Set urlPatterns) {
      boolean isProtected = false;
      Iterator var4 = urlPatterns.iterator();

      while(var4.hasNext()) {
         String urlPattern = (String)var4.next();
         if (urlPattern.equals(viewToCheck)) {
            isProtected = true;
            break;
         }
      }

      return isProtected;
   }

   private boolean originatesInWebapp(FacesContext context, String view, ViewDeclarationLanguage vdl) throws URISyntaxException {
      boolean doesOriginate = false;
      ExternalContext extContext = context.getExternalContext();
      String sep = "/";
      URI uri = null;
      String path = null;
      boolean isAbsoluteURI = view.matches("^[a-z]+://.*");
      if (!isAbsoluteURI) {
         URI absoluteURI = null;
         URI relativeURI = null;
         String base = extContext.getRequestScheme() + ":" + sep + sep + extContext.getRequestServerName() + ":" + extContext.getRequestServerPort();
         absoluteURI = new URI(base);
         relativeURI = new URI(view);
         uri = absoluteURI.resolve(relativeURI);
      }

      boolean hostsMatch = false;
      boolean portsMatch = false;
      boolean contextPathsMatch = false;
      if (null == uri) {
         uri = new URI(view);
      }

      if (null == uri.getHost()) {
         hostsMatch = false;
      } else {
         hostsMatch = uri.getHost().equals(extContext.getRequestServerName());
      }

      if (-1 == uri.getPort()) {
         portsMatch = Util.isOneOf(extContext.getRequestServerPort(), 80, 443);
      } else {
         portsMatch = uri.getPort() == extContext.getRequestServerPort();
      }

      path = uri.getPath();
      contextPathsMatch = path.contains(extContext.getApplicationContextPath());
      doesOriginate = hostsMatch && portsMatch && contextPathsMatch;
      if (!doesOriginate) {
         int idx = path.lastIndexOf(sep);
         if (-1 != idx) {
            path = path.substring(idx);
         }

         if (null != path && vdl.viewExists(context, path)) {
            doesOriginate = true;
         } else {
            doesOriginate = false;
         }
      }

      return doesOriginate;
   }

   private void deliverPostRestoreStateEvent(FacesContext facesContext) throws FacesException {
      UIViewRoot root = facesContext.getViewRoot();
      final PostRestoreStateEvent postRestoreStateEvent = new PostRestoreStateEvent(root);

      try {
         facesContext.getAttributes().put(SKIP_ITERATION_HINT, true);
         facesContext.getApplication().publishEvent(facesContext, PostRestoreStateEvent.class, root);
         Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
         VisitContext visitContext = VisitContext.createVisitContext(facesContext, (Collection)null, hints);
         root.visitTree(visitContext, new VisitCallback() {
            public VisitResult visit(VisitContext context, UIComponent target) {
               postRestoreStateEvent.setComponent(target);
               target.processEvent(postRestoreStateEvent);
               return VisitResult.ACCEPT;
            }
         });
      } catch (AbortProcessingException var9) {
         facesContext.getApplication().publishEvent(facesContext, ExceptionQueuedEvent.class, new ExceptionQueuedEventContext(facesContext, var9, (UIComponent)null, PhaseId.RESTORE_VIEW));
      } finally {
         facesContext.getAttributes().remove(SKIP_ITERATION_HINT);
      }

   }

   private void notifyAfter(FacesContext context, Lifecycle lifecycle) {
      UIViewRoot viewRoot = context.getViewRoot();
      if (null != viewRoot) {
         MethodExpression afterPhase = viewRoot.getAfterPhaseListener();
         if (null != afterPhase) {
            try {
               PhaseEvent event = new PhaseEvent(context, PhaseId.RESTORE_VIEW, lifecycle);
               afterPhase.invoke(context.getELContext(), new Object[]{event});
            } catch (Exception var6) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "severe.component.unable_to_process_expression", new Object[]{afterPhase.getExpressionString(), "afterPhase"});
               }

               return;
            }
         }

      }
   }

   private static boolean isErrorPage(FacesContext context) {
      return context.getExternalContext().getRequestMap().get("javax.servlet.error.message") != null;
   }

   private WebConfiguration getWebConfig(FacesContext context) {
      if (this.webConfig == null) {
         this.webConfig = WebConfiguration.getInstance(context.getExternalContext());
      }

      return this.webConfig;
   }

   private boolean is11CompatEnabled(FacesContext context) {
      return this.getWebConfig(context).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableRestoreView11Compatibility);
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
      SKIP_ITERATION_HINT = "javax.faces.visit.SKIP_ITERATION";
   }
}
