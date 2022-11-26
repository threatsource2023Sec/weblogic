package com.sun.faces.application.view;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.context.StateContext;
import com.sun.faces.facelets.el.ContextualCompositeMethodExpression;
import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.impl.DefaultFaceletFactory;
import com.sun.faces.facelets.impl.XMLFrontMatterSaver;
import com.sun.faces.facelets.tag.composite.CompositeComponentBeanInfo;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import com.sun.faces.facelets.tag.ui.UIDebug;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.Cache;
import com.sun.faces.util.ComponentStruct;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.HtmlUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewVisitOption;
import javax.faces.component.ActionSource2;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.render.RenderKit;
import javax.faces.render.ResponseStateManager;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.view.ActionSource2AttachedObjectHandler;
import javax.faces.view.ActionSource2AttachedObjectTarget;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.AttachedObjectTarget;
import javax.faces.view.BehaviorHolderAttachedObjectHandler;
import javax.faces.view.BehaviorHolderAttachedObjectTarget;
import javax.faces.view.EditableValueHolderAttachedObjectHandler;
import javax.faces.view.EditableValueHolderAttachedObjectTarget;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ValueHolderAttachedObjectHandler;
import javax.faces.view.ValueHolderAttachedObjectTarget;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;
import javax.faces.view.ViewMetadata;
import javax.faces.view.facelets.Facelet;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.http.HttpSession;

public class FaceletViewHandlingStrategy extends ViewHandlingStrategy {
   private static final Logger LOGGER;
   private ViewDeclarationLanguageFactory vdlFactory;
   private DefaultFaceletFactory faceletFactory;
   private String[] extensionsArray;
   private String[] prefixesArray;
   public static final String IS_BUILDING_METADATA;
   public static final String RESOURCE_LIBRARY_CONTRACT_DATA_STRUCTURE_KEY;
   private MethodRetargetHandlerManager retargetHandlerManager = new MethodRetargetHandlerManager();
   private int responseBufferSize;
   private boolean responseBufferSizeSet;
   private boolean isTrinidadStateManager;
   private Cache metadataCache;
   private Map contractMappings;

   public FaceletViewHandlingStrategy() {
      this.initialize();
   }

   public static boolean isBuildingMetadata(FacesContext context) {
      return context.getAttributes().containsKey(IS_BUILDING_METADATA);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      Util.notNull("context", context);
      Util.notNull("viewId", viewId);
      if (UIDebug.debugRequest(context)) {
         context.getApplication().createComponent("javax.faces.ViewRoot");
      }

      ViewHandler outerViewHandler = context.getApplication().getViewHandler();
      String renderKitId = outerViewHandler.calculateRenderKitId(context);
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      UIViewRoot viewRoot;
      ViewDeclarationLanguage vdl;
      if (rsm.isStateless(context, viewId)) {
         try {
            context.setProcessingEvents(true);
            vdl = this.vdlFactory.getViewDeclarationLanguage(viewId);
            viewRoot = vdl.createView(context, viewId);
            context.setViewRoot(viewRoot);
            vdl.buildView(context, viewRoot);
            if (!viewRoot.isTransient()) {
               throw new FacesException("Unable to restore view " + viewId);
            } else {
               return viewRoot;
            }
         } catch (IOException var12) {
            throw new FacesException(var12);
         }
      } else {
         if (StateContext.getStateContext(context).isPartialStateSaving(context, viewId)) {
            try {
               context.setProcessingEvents(false);
               vdl = this.vdlFactory.getViewDeclarationLanguage(viewId);
               viewRoot = vdl.getViewMetadata(context, viewId).createMetadataView(context);
               context.setViewRoot(viewRoot);
               outerViewHandler = context.getApplication().getViewHandler();
               renderKitId = outerViewHandler.calculateRenderKitId(context);
               rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
               Object[] rawState = (Object[])((Object[])rsm.getState(context, viewId));
               if (rawState != null) {
                  Map state = (Map)rawState[1];
                  if (state != null) {
                     String clientId = viewRoot.getClientId(context);
                     Object stateObj = state.get(clientId);
                     if (stateObj != null) {
                        context.getAttributes().put("com.sun.faces.application.view.restoreViewScopeOnly", true);
                        viewRoot.restoreState(context, stateObj);
                        context.getAttributes().remove("com.sun.faces.application.view.restoreViewScopeOnly");
                     }
                  }
               }

               context.setProcessingEvents(true);
               vdl.buildView(context, viewRoot);
            } catch (IOException var13) {
               throw new FacesException(var13);
            }
         }

         UIViewRoot root = super.restoreView(context, viewId);
         ViewHandler viewHandler = context.getApplication().getViewHandler();
         ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(context, viewId);
         context.setResourceLibraryContracts(vdl.calculateResourceLibraryContracts(context, viewId));
         StateContext stateCtx = StateContext.getStateContext(context);
         stateCtx.startTrackViewModifications(context, root);
         return root;
      }
   }

   public ViewMetadata getViewMetadata(FacesContext context, String viewId) {
      Util.notNull("context", context);
      Util.notNull("viewId", viewId);
      return new ViewMetadataImpl(viewId);
   }

   public UIViewRoot createView(FacesContext ctx, String viewId) {
      Util.notNull("context", ctx);
      Util.notNull("viewId", viewId);
      UIViewRoot result;
      if (UIDebug.debugRequest(ctx)) {
         result = (UIViewRoot)ctx.getApplication().createComponent("javax.faces.ViewRoot");
         result.setViewId(viewId);
         return result;
      } else {
         result = super.createView(ctx, viewId);
         ViewHandler viewHandler = ctx.getApplication().getViewHandler();
         ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(ctx, viewId);
         ctx.setResourceLibraryContracts(vdl.calculateResourceLibraryContracts(ctx, viewId));
         return result;
      }
   }

   public void buildView(FacesContext ctx, UIViewRoot view) throws IOException {
      StateContext stateCtx = StateContext.getStateContext(ctx);
      Facelet facelet;
      if (Util.isViewPopulated(ctx, view)) {
         facelet = this.faceletFactory.getFacelet(ctx, view.getViewId());

         try {
            stateCtx.setTrackViewModifications(false);
            facelet.apply(ctx, view);
            this.reapplyDynamicActions(ctx);
            if (stateCtx.isPartialStateSaving(ctx, view.getViewId())) {
               this.markInitialStateIfNotMarked(view);
            }
         } finally {
            stateCtx.setTrackViewModifications(true);
         }

      } else {
         view.setViewId(view.getViewId());
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Building View: " + view.getViewId());
         }

         if (this.faceletFactory == null) {
            ApplicationAssociate associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
            this.faceletFactory = associate.getFaceletFactory();

            assert this.faceletFactory != null;
         }

         RequestStateManager.set(ctx, "com.sun.faces.FACELET_FACTORY", this.faceletFactory);
         facelet = this.faceletFactory.getFacelet(ctx, view.getViewId());

         try {
            ctx.getAttributes().put("javax.faces.IS_BUILDING_INITIAL_STATE", Boolean.TRUE);
            stateCtx.setTrackViewModifications(false);
            facelet.apply(ctx, view);
            if (facelet instanceof XMLFrontMatterSaver) {
               XMLFrontMatterSaver frontMatterSaver = (XMLFrontMatterSaver)facelet;
               String docType = frontMatterSaver.getSavedDoctype();
               if (docType != null) {
                  Util.saveDOCTYPEToFacesContextAttributes(docType);
               }

               String XMLDECL = frontMatterSaver.getSavedXMLDecl();
               if (XMLDECL != null) {
                  Util.saveXMLDECLToFacesContextAttributes(XMLDECL);
               }
            }

            if (!stateCtx.isPartialStateSaving(ctx, view.getViewId())) {
               this.reapplyDynamicActions(ctx);
            }

            this.doPostBuildActions(ctx, view);
         } finally {
            ctx.getAttributes().remove("javax.faces.IS_BUILDING_INITIAL_STATE");
         }

         ctx.getApplication().publishEvent(ctx, PostAddToViewEvent.class, UIViewRoot.class, view);
         this.markInitialState(ctx, view);
         Util.setViewPopulated(ctx, view);
      }
   }

   public void renderView(FacesContext ctx, UIViewRoot viewToRender) throws IOException {
      if (viewToRender.isRendered()) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Rendering View: " + viewToRender.getViewId());
         }

         WriteBehindStateWriter stateWriter = null;

         try {
            if (!Util.isViewPopulated(ctx, viewToRender)) {
               ViewDeclarationLanguage vdl = this.vdlFactory.getViewDeclarationLanguage(viewToRender.getViewId());
               vdl.buildView(ctx, viewToRender);
            }

            ResponseWriter origWriter = ctx.getResponseWriter();
            if (origWriter == null) {
               origWriter = this.createResponseWriter(ctx);
            }

            ExternalContext extContext = ctx.getExternalContext();
            if (this.isServerStateSaving() && !viewToRender.isTransient()) {
               this.getSession(ctx);
            }

            Writer outputWriter = extContext.getResponseOutputWriter();
            stateWriter = new WriteBehindStateWriter(outputWriter, ctx, this.responseBufferSize);
            ResponseWriter writer = origWriter.cloneWithWriter(stateWriter);
            ctx.setResponseWriter(writer);
            if (ctx.getPartialViewContext().isPartialRequest()) {
               viewToRender.encodeAll(ctx);

               try {
                  ctx.getExternalContext().getFlash().doPostPhaseActions(ctx);
               } catch (UnsupportedOperationException var17) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
                  }
               }
            } else {
               if (ctx.isProjectStage(ProjectStage.Development)) {
                  FormOmittedChecker.check(ctx);
               }

               String xmlDecl = Util.getXMLDECLFromFacesContextAttributes(ctx);
               if (null != xmlDecl) {
                  writer.writePreamble(xmlDecl);
               }

               String docType = Util.getDOCTYPEFromFacesContextAttributes(ctx);
               if (null != docType) {
                  writer.writeDoctype(docType);
               }

               writer.startDocument();
               viewToRender.encodeAll(ctx);

               try {
                  ctx.getExternalContext().getFlash().doPostPhaseActions(ctx);
               } catch (UnsupportedOperationException var18) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
                  }
               }

               writer.endDocument();
            }

            writer.close();
            if (stateWriter.stateWritten()) {
               stateWriter.flushToWriter();
            }
         } catch (FileNotFoundException var19) {
            this.handleFaceletNotFound(ctx, viewToRender.getViewId(), var19.getMessage());
         } catch (Exception var20) {
            this.handleRenderException(ctx, var20);
         } finally {
            if (stateWriter != null) {
               stateWriter.release();
            }

         }

      }
   }

   public StateManagementStrategy getStateManagementStrategy(FacesContext context, String viewId) {
      StateContext stateCtx = StateContext.getStateContext(context);
      Object result;
      if (stateCtx.isPartialStateSaving(context, viewId)) {
         result = new FaceletPartialStateManagementStrategy(context);
      } else {
         result = this.isTrinidadStateManager ? null : new JspStateManagementStrategy(context);
      }

      return (StateManagementStrategy)result;
   }

   public BeanInfo getComponentMetadata(FacesContext context, Resource ccResource) {
      DefaultFaceletFactory factory = (DefaultFaceletFactory)RequestStateManager.get(context, "com.sun.faces.FACELET_FACTORY");
      if (factory.needsToBeRefreshed(ccResource.getURL())) {
         this.metadataCache.remove(ccResource);
      }

      return (BeanInfo)this.metadataCache.get(ccResource);
   }

   public BeanInfo createComponentMetadata(FacesContext context, Resource ccResource) {
      FaceletContext faceletContext = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      DefaultFaceletFactory factory = (DefaultFaceletFactory)RequestStateManager.get(context, "com.sun.faces.FACELET_FACTORY");
      VariableMapper orig = faceletContext.getVariableMapper();
      UIComponent tmp = context.getApplication().createComponent("javax.faces.NamingContainer");
      UIPanel facetComponent = (UIPanel)context.getApplication().createComponent("javax.faces.Panel");
      facetComponent.setRendererType("javax.faces.Group");
      tmp.getFacets().put("javax.faces.component.COMPOSITE_FACET_NAME", facetComponent);
      tmp.getAttributes().put("javax.faces.application.Resource.ComponentResource", ccResource);

      try {
         Facelet facelet = factory.getFacelet(context, ccResource.getURL());
         VariableMapper wrapper = new VariableMapperWrapper(orig) {
            public ValueExpression resolveVariable(String variable) {
               return super.resolveVariable(variable);
            }
         };
         faceletContext.setVariableMapper(wrapper);
         context.getAttributes().put(IS_BUILDING_METADATA, Boolean.TRUE);
         facelet.apply(context, facetComponent);
      } catch (Exception var13) {
         if (var13 instanceof FacesException) {
            throw (FacesException)var13;
         }

         throw new FacesException(var13);
      } finally {
         context.getAttributes().remove(IS_BUILDING_METADATA);
         faceletContext.setVariableMapper(orig);
      }

      return (CompositeComponentBeanInfo)tmp.getAttributes().get("javax.faces.component.BEANINFO_KEY");
   }

   public Resource getScriptComponentResource(FacesContext context, Resource componentResource) {
      Util.notNull("context", context);
      Util.notNull("componentResource", componentResource);
      return null;
   }

   public void retargetAttachedObjects(FacesContext context, UIComponent topLevelComponent, List handlers) {
      Util.notNull("context", context);
      Util.notNull("topLevelComponent", topLevelComponent);
      Util.notNull("handlers", handlers);
      if (handlers != null && !handlers.isEmpty()) {
         BeanInfo componentBeanInfo = (BeanInfo)topLevelComponent.getAttributes().get("javax.faces.component.BEANINFO_KEY");
         if (componentBeanInfo != null) {
            BeanDescriptor componentDescriptor = componentBeanInfo.getBeanDescriptor();
            List targetList = (List)componentDescriptor.getValue("javax.faces.view.AttachedObjectTargets");
            Iterator var10 = handlers.iterator();

            while(true) {
               label94:
               while(var10.hasNext()) {
                  AttachedObjectHandler curHandler = (AttachedObjectHandler)var10.next();
                  String forAttributeValue = curHandler.getFor();
                  Iterator var12 = targetList.iterator();

                  while(true) {
                     while(true) {
                        if (!var12.hasNext()) {
                           continue label94;
                        }

                        AttachedObjectTarget curTarget = (AttachedObjectTarget)var12.next();
                        String curTargetName = curTarget.getName();
                        List targetComponents = curTarget.getTargets(topLevelComponent);
                        Iterator var19;
                        UIComponent curTargetComponent;
                        if (curHandler instanceof ActionSource2AttachedObjectHandler && curTarget instanceof ActionSource2AttachedObjectTarget) {
                           if (forAttributeValue.equals(curTargetName)) {
                              var19 = targetComponents.iterator();

                              while(var19.hasNext()) {
                                 curTargetComponent = (UIComponent)var19.next();
                                 this.retargetHandler(context, curHandler, curTargetComponent);
                              }
                              continue label94;
                           }
                        } else if (curHandler instanceof EditableValueHolderAttachedObjectHandler && curTarget instanceof EditableValueHolderAttachedObjectTarget) {
                           if (forAttributeValue.equals(curTargetName)) {
                              var19 = targetComponents.iterator();

                              while(true) {
                                 if (!var19.hasNext()) {
                                    continue label94;
                                 }

                                 curTargetComponent = (UIComponent)var19.next();
                                 this.retargetHandler(context, curHandler, curTargetComponent);
                              }
                           }
                        } else if (curHandler instanceof ValueHolderAttachedObjectHandler && curTarget instanceof ValueHolderAttachedObjectTarget) {
                           if (forAttributeValue.equals(curTargetName)) {
                              var19 = targetComponents.iterator();

                              while(true) {
                                 if (!var19.hasNext()) {
                                    continue label94;
                                 }

                                 curTargetComponent = (UIComponent)var19.next();
                                 this.retargetHandler(context, curHandler, curTargetComponent);
                              }
                           }
                        } else if (curHandler instanceof BehaviorHolderAttachedObjectHandler && curTarget instanceof BehaviorHolderAttachedObjectTarget) {
                           BehaviorHolderAttachedObjectHandler behaviorHandler = (BehaviorHolderAttachedObjectHandler)curHandler;
                           BehaviorHolderAttachedObjectTarget behaviorTarget = (BehaviorHolderAttachedObjectTarget)curTarget;
                           String eventName = behaviorHandler.getEventName();
                           if (null != eventName && eventName.equals(curTargetName) || null == eventName && behaviorTarget.isDefaultEvent()) {
                              Iterator var17 = targetComponents.iterator();

                              while(var17.hasNext()) {
                                 UIComponent curTargetComponent = (UIComponent)var17.next();
                                 this.retargetHandler(context, curHandler, curTargetComponent);
                              }
                           }
                        }
                     }
                  }
               }

               return;
            }
         }
      }
   }

   public void retargetMethodExpressions(FacesContext context, UIComponent topLevelComponent) {
      Util.notNull("context", context);
      Util.notNull("topLevelComponent", topLevelComponent);
      BeanInfo componentBeanInfo = (BeanInfo)topLevelComponent.getAttributes().get("javax.faces.component.BEANINFO_KEY");
      if (componentBeanInfo != null) {
         PropertyDescriptor[] attributes = componentBeanInfo.getPropertyDescriptors();
         MethodMetadataIterator allMetadata = new MethodMetadataIterator(context, attributes);
         Iterator var6 = allMetadata.iterator();

         CompCompInterfaceMethodMetadata metadata;
         String attrName;
         Map attrs;
         label71:
         do {
            for(; var6.hasNext(); topLevelComponent.setValueExpression(attrName, (ValueExpression)null)) {
               metadata = (CompCompInterfaceMethodMetadata)var6.next();
               attrName = metadata.getName();
               String[] targets = metadata.getTargets(context);
               Object attrValue = topLevelComponent.getValueExpression(attrName);
               if (attrValue == null) {
                  attrs = topLevelComponent.getAttributes();
                  attrValue = attrs.containsKey(attrName) ? attrs.get(attrName) : metadata.getDefault();
                  if (attrValue == null) {
                     continue label71;
                  }
               }

               String targetAttributeName = metadata.getTargetAttributeName(context);
               UIComponent targetComp = null;
               if (targetAttributeName != null) {
                  attrName = targetAttributeName;
               }

               MethodRetargetHandler handler;
               if (targets != null) {
                  handler = this.retargetHandlerManager.getRetargetHandler(attrName);
                  if (handler != null) {
                     String[] var14 = targets;
                     int var15 = targets.length;

                     for(int var16 = 0; var16 < var15; ++var16) {
                        String curTarget = var14[var16];
                        targetComp = topLevelComponent.findComponent(curTarget);
                        if (targetComp == null) {
                           throw new FacesException(attrValue.toString() + " : Unable to re-target MethodExpression as inner component referenced by target id '" + curTarget + "' cannot be found.");
                        }

                        handler.retarget(context, metadata, attrValue, targetComp);
                     }
                  } else {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.compcomp.unecessary.targets.attribute", new Object[]{this.getCompositeComponentName(topLevelComponent), attrName});
                     }

                     handler = this.retargetHandlerManager.getDefaultHandler();
                     handler.retarget(context, metadata, attrValue, topLevelComponent);
                  }
               } else {
                  handler = null;
                  if (targetAttributeName != null) {
                     targetComp = topLevelComponent.findComponent(metadata.getName());
                     handler = this.retargetHandlerManager.getRetargetHandler(attrName);
                  }

                  if (handler == null) {
                     targetComp = topLevelComponent;
                     handler = this.retargetHandlerManager.getDefaultHandler();
                  }

                  handler.retarget(context, metadata, attrValue, targetComp);
               }
            }

            return;
         } while(!metadata.isRequired(context));

         Object location = attrs.get("javax.faces.component.VIEW_LOCATION_KEY");
         if (location == null) {
            location = "";
         }

         throw new FacesException(location.toString() + ": Unable to find attribute with name \"" + attrName + "\" in top level component in consuming page,  or with default value in composite component.  Page author or composite component author error.");
      }
   }

   public UIComponent createComponent(FacesContext context, String taglibURI, String tagName, Map attributes) {
      Util.notNull("context", context);
      Util.notNull("taglibURI", taglibURI);
      Util.notNull("tagName", tagName);
      return this.associate.getFaceletFactory()._createComponent(context, taglibURI, tagName, attributes);
   }

   public List calculateResourceLibraryContracts(FacesContext context, String viewId) {
      List result = null;
      String longestPattern = null;
      if (this.contractMappings == null) {
         return Collections.emptyList();
      } else {
         String longestMatch = null;
         Iterator var6 = this.contractMappings.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry mappings = (Map.Entry)var6.next();
            String urlPattern = (String)mappings.getKey();
            if (urlPattern.endsWith("*")) {
               String prefix = urlPattern.substring(0, urlPattern.length() - 1);
               if (viewId.startsWith(prefix)) {
                  if (longestPattern == null) {
                     longestPattern = urlPattern;
                     longestMatch = prefix;
                  } else if (longestMatch.length() < prefix.length()) {
                     longestPattern = urlPattern;
                     longestMatch = prefix;
                  }
               }
            } else if (viewId.equals(urlPattern)) {
               longestPattern = urlPattern;
               break;
            }
         }

         if (longestPattern != null) {
            result = (List)this.contractMappings.get(longestPattern);
         }

         if (result == null) {
            result = (List)this.contractMappings.get("*");
         }

         return result;
      }
   }

   public boolean viewExists(FacesContext context, String viewId) {
      if (this.handlesViewId(viewId)) {
         return this.getFaceletFactory().getResourceResolver().resolveUrl(viewId) != null;
      } else {
         return false;
      }
   }

   public Stream getViews(FacesContext context, String path, ViewVisitOption... options) {
      return this.mapIfNeeded(super.getViews(context, path, new ViewVisitOption[0]).filter((viewId) -> {
         return this.handlesViewId(viewId);
      }), options);
   }

   public Stream getViews(FacesContext context, String path, int maxDepth, ViewVisitOption... options) {
      return this.mapIfNeeded(super.getViews(context, path, maxDepth, new ViewVisitOption[0]).filter((viewId) -> {
         return this.handlesViewId(viewId);
      }), options);
   }

   public boolean handlesViewId(String viewId) {
      if (viewId != null) {
         if (this.handlesByPrefixOrSuffix(viewId)) {
            return true;
         }

         if (Util.isViewIdExactMappedToFacesServlet(viewId)) {
            return true;
         }
      }

      return false;
   }

   private boolean handlesByPrefixOrSuffix(String viewId) {
      if (viewId.endsWith("-flow.xml")) {
         return true;
      } else if (this.extensionsArray == null && this.prefixesArray == null) {
         return this.isMatchedWithFaceletsSuffix(viewId) ? true : viewId.endsWith(".xhtml");
      } else {
         String[] var2;
         int var3;
         int var4;
         String prefix;
         if (this.extensionsArray != null) {
            var2 = this.extensionsArray;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               prefix = var2[var4];
               if (viewId.endsWith(prefix)) {
                  return true;
               }
            }
         }

         if (this.prefixesArray != null) {
            var2 = this.prefixesArray;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               prefix = var2[var4];
               if (viewId.startsWith(prefix)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public String getId() {
      return "java.faces.Facelets";
   }

   protected void initialize() {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Initializing FaceletViewHandlingStrategy");
      }

      this.initializeMappings();
      this.metadataCache = new Cache(new Cache.Factory() {
         public BeanInfo newInstance(Resource ccResource) throws InterruptedException {
            FacesContext context = FacesContext.getCurrentInstance();
            return FaceletViewHandlingStrategy.this.createComponentMetadata(context, ccResource);
         }
      });

      try {
         this.responseBufferSizeSet = this.webConfig.isSet(WebConfiguration.WebContextInitParameter.FaceletsBufferSize);
         this.responseBufferSize = Integer.parseInt(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsBufferSize));
      } catch (NumberFormatException var7) {
         this.responseBufferSize = Integer.parseInt(WebConfiguration.WebContextInitParameter.FaceletsBufferSize.getDefaultValue());
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Initialization Successful");
      }

      this.vdlFactory = (ViewDeclarationLanguageFactory)FactoryFinder.getFactory("javax.faces.view.ViewDeclarationLanguageFactory");
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext extContext = context.getExternalContext();
      Map appMap = extContext.getApplicationMap();
      Map contractDataStructure = (Map)appMap.remove(RESOURCE_LIBRARY_CONTRACT_DATA_STRUCTURE_KEY);
      if (!Util.isEmpty((Object)contractDataStructure)) {
         this.contractMappings = new ConcurrentHashMap();
         Iterator var5 = contractDataStructure.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry cur = (Map.Entry)var5.next();
            this.contractMappings.put(cur.getKey(), new CopyOnWriteArrayList((Collection)cur.getValue()));
            ((List)cur.getValue()).clear();
         }

         contractDataStructure.clear();
      }

      if (context != null) {
         StateManager stateManager = Util.getStateManager(context);
         if (stateManager != null) {
            this.isTrinidadStateManager = stateManager.getClass().getName().contains("trinidad");
         }
      }

   }

   protected void initializeMappings() {
      String viewMappings = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsViewMappings);
      if (viewMappings != null && viewMappings.length() > 0) {
         Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
         String[] mappingsArray = Util.split(appMap, viewMappings, ";");
         List extensionsList = new ArrayList(mappingsArray.length);
         List prefixesList = new ArrayList(mappingsArray.length);
         String[] var6 = mappingsArray;
         int var7 = mappingsArray.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String aMappingsArray = var6[var8];
            String mapping = aMappingsArray.trim();
            int mappingLength = mapping.length();
            if (mappingLength > 1) {
               if (mapping.charAt(0) == '*') {
                  extensionsList.add(mapping.substring(1));
               } else if (mapping.charAt(mappingLength - 1) == '*') {
                  prefixesList.add(mapping.substring(0, mappingLength - 1));
               }
            }
         }

         this.extensionsArray = new String[extensionsList.size()];
         extensionsList.toArray(this.extensionsArray);
         this.prefixesArray = new String[prefixesList.size()];
         prefixesList.toArray(this.prefixesArray);
      }

   }

   protected ResponseWriter createResponseWriter(FacesContext context) throws IOException {
      ExternalContext extContext = context.getExternalContext();
      RenderKit renderKit = context.getRenderKit();
      String contentType;
      if (renderKit == null) {
         contentType = context.getViewRoot().getRenderKitId();
         throw new IllegalStateException("No render kit was available for id \"" + contentType + "\"");
      } else {
         if (this.responseBufferSizeSet) {
            extContext.setResponseBufferSize(this.responseBufferSize);
         }

         contentType = (String)context.getAttributes().get("facelets.ContentType");
         String encoding = (String)context.getAttributes().get("facelets.Encoding");
         ResponseWriter writer = renderKit.createResponseWriter(FaceletViewHandlingStrategy.NullWriter.INSTANCE, contentType, encoding);
         contentType = this.getResponseContentType(context, writer.getContentType());
         encoding = this.getResponseEncoding(context, writer.getCharacterEncoding());
         char[] buffer = new char[1028];
         HtmlUtils.writeTextForXML(writer, contentType, buffer);
         String str = String.valueOf(buffer).trim();
         extContext.setResponseContentType(str);
         extContext.setResponseCharacterEncoding(encoding);
         writer = writer.cloneWithWriter(extContext.getResponseOutputWriter());
         return writer;
      }
   }

   protected void handleRenderException(FacesContext context, Exception e) throws IOException {
      if (LOGGER.isLoggable(Level.SEVERE)) {
         UIViewRoot root = context.getViewRoot();
         StringBuffer sb = new StringBuffer(64);
         sb.append("Error Rendering View");
         if (root != null) {
            sb.append('[');
            sb.append(root.getViewId());
            sb.append(']');
         }

         LOGGER.log(Level.SEVERE, sb.toString(), e);
      }

      if (e instanceof RuntimeException) {
         throw (RuntimeException)e;
      } else if (e instanceof IOException) {
         throw (IOException)e;
      } else {
         throw new FacesException(e.getMessage(), e);
      }
   }

   protected void handleFaceletNotFound(FacesContext context, String viewId, String message) throws IOException {
      context.getExternalContext().responseSendError(404, message != null ? viewId + ": " + message : viewId);
      context.responseComplete();
   }

   protected String getResponseEncoding(FacesContext context, String orig) {
      String encoding = context.getExternalContext().getRequestCharacterEncoding();
      Map ctxAttributes;
      if (encoding == null && null != context.getExternalContext().getSession(false)) {
         ctxAttributes = context.getExternalContext().getSessionMap();
         encoding = (String)ctxAttributes.get("javax.faces.request.charset");
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Session specified alternate encoding {0}", encoding);
         }
      }

      ctxAttributes = context.getAttributes();
      if (ctxAttributes.containsKey("facelets.Encoding")) {
         encoding = (String)ctxAttributes.get("facelets.Encoding");
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Facelet specified alternate encoding {0}", encoding);
         }

         if (null != context.getExternalContext().getSession(false)) {
            Map sessionMap = context.getExternalContext().getSessionMap();
            sessionMap.put("javax.faces.request.charset", encoding);
         }
      }

      if (encoding == null) {
         if (null != orig && 0 < orig.length()) {
            encoding = orig;
         } else {
            encoding = "UTF-8";
         }

         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "ResponseWriter created had a null CharacterEncoding, defaulting to {0}", orig);
         }
      }

      return encoding;
   }

   protected String getResponseContentType(FacesContext context, String orig) {
      String contentType = orig;
      Map m = context.getAttributes();
      if (m.containsKey("facelets.ContentType")) {
         contentType = (String)m.get("facelets.ContentType");
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest("Facelet specified alternate contentType '" + contentType + "'");
         }
      }

      if (contentType == null) {
         contentType = "text/html";
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest("ResponseWriter created had a null ContentType, defaulting to text/html");
         }
      }

      return contentType;
   }

   private String getCompositeComponentName(UIComponent compositeComponent) {
      Resource resource = (Resource)compositeComponent.getAttributes().get("javax.faces.application.Resource.ComponentResource");
      String name = resource.getResourceName();
      String library = resource.getLibraryName();
      return library != null ? "Composite Component: " + name + ", library: " + library : "Composite Component: " + name;
   }

   private void doPostBuildActions(FacesContext ctx, UIViewRoot root) {
      StateContext stateCtx = StateContext.getStateContext(ctx);
      stateCtx.startTrackViewModifications(ctx, root);
   }

   private void markInitialState(FacesContext ctx, UIViewRoot root) {
      StateContext stateCtx = StateContext.getStateContext(ctx);
      if (stateCtx.isPartialStateSaving(ctx, root.getViewId())) {
         try {
            ctx.getAttributes().put("javax.faces.IS_BUILDING_INITIAL_STATE", Boolean.TRUE);
            if (!root.isTransient()) {
               this.markInitialState(root);
            }
         } finally {
            ctx.getAttributes().remove("javax.faces.IS_BUILDING_INITIAL_STATE");
         }
      }

   }

   private void markInitialState(UIComponent component) {
      component.markInitialState();
      Iterator it = component.getFacetsAndChildren();

      while(it.hasNext()) {
         UIComponent child = (UIComponent)it.next();
         if (!child.isTransient()) {
            this.markInitialState(child);
         }
      }

   }

   private void retargetHandler(FacesContext context, AttachedObjectHandler handler, UIComponent targetComponent) {
      if (UIComponent.isCompositeComponent(targetComponent)) {
         List nHandlers = CompositeComponentTagHandler.getAttachedObjectHandlers(targetComponent);
         nHandlers.add(handler);
         this.retargetAttachedObjects(context, targetComponent, nHandlers);
      } else {
         handler.applyAttachedObject(context, targetComponent);
      }

   }

   private UIComponent locateComponentByClientId(FacesContext context, UIComponent parent, final String clientId) {
      final List found = new ArrayList();
      UIComponent result = null;
      parent.invokeOnComponent(context, clientId, new ContextCallback() {
         public void invokeContextCallback(FacesContext context, UIComponent target) {
            found.add(target);
         }
      });
      if (found.isEmpty()) {
         VisitContext visitContext = VisitContext.createVisitContext(context);
         parent.visitTree(visitContext, new VisitCallback() {
            public VisitResult visit(VisitContext visitContext, UIComponent component) {
               VisitResult result = VisitResult.ACCEPT;
               if (component.getClientId(visitContext.getFacesContext()).equals(clientId)) {
                  found.add(component);
                  result = VisitResult.COMPLETE;
               }

               return result;
            }
         });
      }

      if (!found.isEmpty()) {
         result = (UIComponent)found.get(0);
      }

      return result;
   }

   private void reapplyDynamicActions(FacesContext context) {
      StateContext stateContext = StateContext.getStateContext(context);
      List actions = stateContext.getDynamicActions();
      if (actions != null) {
         Iterator var4 = actions.iterator();

         while(var4.hasNext()) {
            ComponentStruct action = (ComponentStruct)var4.next();
            if ("REMOVE".equals(action.getAction())) {
               this.reapplyDynamicRemove(context, action);
            }

            if ("ADD".equals(action.getAction())) {
               this.reapplyDynamicAdd(context, action);
            }
         }
      }

   }

   private void reapplyDynamicAdd(FacesContext context, ComponentStruct struct) {
      UIComponent parent = this.locateComponentByClientId(context, context.getViewRoot(), struct.getParentClientId());
      if (parent != null) {
         UIComponent child = this.locateComponentByClientId(context, parent, struct.getClientId());
         StateContext stateContext = StateContext.getStateContext(context);
         if (child == null) {
            child = (UIComponent)stateContext.getDynamicComponents().get(struct.getClientId());
         }

         if (child != null) {
            if (struct.getFacetName() != null) {
               parent.getFacets().remove(struct.getFacetName());
               parent.getFacets().put(struct.getFacetName(), child);
               child.getClientId();
            } else {
               int childIndex = -1;
               if (child.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
                  childIndex = (Integer)child.getAttributes().get("com.sun.faces.DynamicComponent");
               }

               child.setId(struct.getId());
               if (childIndex < parent.getChildCount() && childIndex != -1) {
                  parent.getChildren().add(childIndex, child);
               } else {
                  parent.getChildren().add(child);
               }

               child.getClientId();
               child.getAttributes().put("com.sun.faces.DynamicComponent", child.getParent().getChildren().indexOf(child));
            }

            stateContext.getDynamicComponents().put(struct.getClientId(), child);
         }
      }

   }

   private void reapplyDynamicRemove(FacesContext context, ComponentStruct struct) {
      UIComponent child = this.locateComponentByClientId(context, context.getViewRoot(), struct.getClientId());
      if (child != null) {
         StateContext stateContext = StateContext.getStateContext(context);
         stateContext.getDynamicComponents().put(struct.getClientId(), child);
         UIComponent parent = child.getParent();
         parent.getChildren().remove(child);
      }

   }

   private boolean isServerStateSaving() {
      return "server".equals(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.StateSavingMethod));
   }

   private HttpSession getSession(FacesContext context) {
      Object sessionObj = context.getExternalContext().getSession(true);
      return sessionObj instanceof HttpSession ? (HttpSession)sessionObj : null;
   }

   private DefaultFaceletFactory getFaceletFactory() {
      if (this.faceletFactory == null) {
         this.faceletFactory = this.associate.getFaceletFactory();

         assert this.faceletFactory != null;
      }

      return this.faceletFactory;
   }

   private boolean isMatchedWithFaceletsSuffix(String viewId) {
      String[] defaultsuffixes = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsSuffix, " ");
      String[] var3 = defaultsuffixes;
      int var4 = defaultsuffixes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String suffix = var3[var5];
         if (viewId.endsWith(suffix)) {
            return true;
         }
      }

      return false;
   }

   private String getMatchedWithFaceletsSuffix(String viewId) {
      String[] defaultsuffixes = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsSuffix, " ");
      String[] var3 = defaultsuffixes;
      int var4 = defaultsuffixes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String suffix = var3[var5];
         if (viewId.endsWith(suffix)) {
            return suffix;
         }
      }

      return null;
   }

   private Stream mapIfNeeded(Stream views, ViewVisitOption... options) {
      return !this.returnAsImplicitOutCome(options) ? views : views.map((view) -> {
         return this.toImplicitOutcome(view);
      });
   }

   private boolean returnAsImplicitOutCome(ViewVisitOption... options) {
      return Arrays.stream(options).filter((option) -> {
         return option == ViewVisitOption.RETURN_AS_MINIMAL_IMPLICIT_OUTCOME;
      }).findAny().isPresent();
   }

   private String toImplicitOutcome(String viewId) {
      String suffix = this.getConfiguredSuffix(viewId);
      if (suffix != null) {
         return viewId.substring(0, viewId.lastIndexOf(suffix));
      } else {
         String prefix = this.getConfiguredPrefix(viewId);
         return prefix != null ? viewId.substring(prefix.length()) : viewId;
      }
   }

   private String getConfiguredSuffix(String viewId) {
      if (viewId != null) {
         if (viewId.endsWith("-flow.xml")) {
            return "-flow.xml";
         }

         if (this.extensionsArray == null && this.prefixesArray == null) {
            String suffix = this.getMatchedWithFaceletsSuffix(viewId);
            if (suffix != null) {
               return suffix;
            }

            if (viewId.endsWith(".xhtml")) {
               return ".xhtml";
            }
         }

         if (this.extensionsArray != null) {
            String[] var6 = this.extensionsArray;
            int var3 = var6.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String extension = var6[var4];
               if (viewId.endsWith(extension)) {
                  return extension;
               }
            }
         }
      }

      return null;
   }

   private String getConfiguredPrefix(String viewId) {
      if (this.prefixesArray != null) {
         String[] var2 = this.prefixesArray;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String prefix = var2[var4];
            if (viewId.startsWith(prefix)) {
               return prefix;
            }
         }
      }

      return null;
   }

   private void markInitialStateIfNotMarked(UIComponent component) {
      if (!component.isTransient()) {
         if (!component.getAttributes().containsKey("com.sun.faces.DynamicComponent") && !component.initialStateMarked()) {
            component.markInitialState();
         }

         Iterator it = component.getFacetsAndChildren();

         while(it.hasNext()) {
            UIComponent child = (UIComponent)it.next();
            this.markInitialStateIfNotMarked(child);
         }
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      IS_BUILDING_METADATA = FaceletViewHandlingStrategy.class.getName() + ".IS_BUILDING_METADATA";
      RESOURCE_LIBRARY_CONTRACT_DATA_STRUCTURE_KEY = FaceletViewHandlingStrategy.class.getName() + ".RESOURCE_LIBRARY_CONTRACT_DATA_STRUCTURE";
   }

   protected static final class NullWriter extends Writer {
      static final NullWriter INSTANCE = new NullWriter();

      public void write(char[] buffer) {
      }

      public void write(char[] buffer, int off, int len) {
      }

      public void write(String str) {
      }

      public void write(int c) {
      }

      public void write(String str, int off, int len) {
      }

      public void close() {
      }

      public void flush() {
      }
   }

   private interface MethodRetargetHandler {
      void retarget(FacesContext var1, CompCompInterfaceMethodMetadata var2, Object var3, UIComponent var4);

      String getAttribute();
   }

   private static final class MethodRetargetHandlerManager {
      private Map handlerMap = new HashMap(4, 1.0F);
      private MethodRetargetHandler arbitraryHandler = new ArbitraryMethodRegargetHandler();

      MethodRetargetHandlerManager() {
         MethodRetargetHandler[] handlers = new MethodRetargetHandler[]{new ActionRegargetHandler(), new ActionListenerRegargetHandler(), new ValidatorRegargetHandler(), new ValueChangeListenerRegargetHandler()};
         MethodRetargetHandler[] var2 = handlers;
         int var3 = handlers.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MethodRetargetHandler h = var2[var4];
            this.handlerMap.put(h.getAttribute(), h);
         }

      }

      private MethodRetargetHandler getRetargetHandler(String attrName) {
         return (MethodRetargetHandler)this.handlerMap.get(attrName);
      }

      private MethodRetargetHandler getDefaultHandler() {
         return this.arbitraryHandler;
      }

      private static final class ArbitraryMethodRegargetHandler extends AbstractRetargetHandler {
         private ArbitraryMethodRegargetHandler() {
            super(null);
         }

         public void retarget(FacesContext ctx, CompCompInterfaceMethodMetadata metadata, Object sourceValue, UIComponent target) {
            ValueExpression ve = (ValueExpression)sourceValue;
            ExpressionFactory f = ctx.getApplication().getExpressionFactory();
            String methodSignature = metadata.getMethodSignature(ctx);

            assert null != methodSignature;

            methodSignature = methodSignature.trim();
            Class[] expectedParameters = NO_ARGS;
            int i = methodSignature.indexOf(" ");
            if (-1 != i) {
               String strValue = methodSignature.substring(0, i);

               Class expectedReturnType;
               try {
                  expectedReturnType = Util.getTypeFromString(strValue.trim());
               } catch (ClassNotFoundException var16) {
                  throw new FacesException(methodSignature + " : Unable to load type '" + strValue + '\'');
               }

               i = methodSignature.indexOf("(");
               if (-1 != i) {
                  int j = methodSignature.indexOf(")", i + 1);
                  if (-1 != j) {
                     strValue = methodSignature.substring(i + 1, j);
                     if (0 < strValue.length()) {
                        String[] params = strValue.split(",");
                        expectedParameters = new Class[params.length];
                        boolean exceptionThrown = false;

                        for(i = 0; i < params.length; ++i) {
                           try {
                              expectedParameters[i] = Util.getTypeFromString(params[i].trim());
                           } catch (ClassNotFoundException var17) {
                              if (FaceletViewHandlingStrategy.LOGGER.isLoggable(Level.SEVERE)) {
                                 FaceletViewHandlingStrategy.LOGGER.log(Level.SEVERE, "Unable to determine parameter type for " + methodSignature, var17);
                              }

                              exceptionThrown = true;
                              break;
                           }
                        }

                        if (exceptionThrown) {
                           return;
                        }
                     } else {
                        expectedParameters = NO_ARGS;
                     }
                  }
               }

               assert null != expectedReturnType;

               assert null != expectedParameters;

               ELContext elContext = (ELContext)ctx.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
               if (null == elContext) {
                  elContext = ctx.getELContext();
               }

               MethodExpression me = f.createMethodExpression(elContext, ve.getExpressionString(), expectedReturnType, expectedParameters);
               target.getAttributes().put(metadata.getName(), new ContextualCompositeMethodExpression(ve, me));
            } else {
               if (FaceletViewHandlingStrategy.LOGGER.isLoggable(Level.SEVERE)) {
                  FaceletViewHandlingStrategy.LOGGER.severe("Unable to determine expected return type for " + methodSignature);
               }

            }
         }

         public String getAttribute() {
            return null;
         }

         // $FF: synthetic method
         ArbitraryMethodRegargetHandler(Object x0) {
            this();
         }
      }

      private static final class ValueChangeListenerRegargetHandler extends AbstractRetargetHandler {
         private static final String VALUE_CHANGE_LISTENER = "valueChangeListener";
         private static final Class[] VALUE_CHANGE_LISTENER_ARGS = new Class[]{ValueChangeEvent.class};

         private ValueChangeListenerRegargetHandler() {
            super(null);
         }

         public void retarget(FacesContext ctx, CompCompInterfaceMethodMetadata metadata, Object sourceValue, UIComponent target) {
            ValueExpression ve = (ValueExpression)sourceValue;
            ExpressionFactory f = ctx.getApplication().getExpressionFactory();
            MethodExpression me = f.createMethodExpression(ctx.getELContext(), ve.getExpressionString(), Void.TYPE, VALUE_CHANGE_LISTENER_ARGS);
            MethodExpression noArg = f.createMethodExpression(ctx.getELContext(), ve.getExpressionString(), Void.TYPE, NO_ARGS);
            ((EditableValueHolder)target).addValueChangeListener(new MethodExpressionValueChangeListener(new ContextualCompositeMethodExpression(ve, me), new ContextualCompositeMethodExpression(ve, noArg)));
         }

         public String getAttribute() {
            return "valueChangeListener";
         }

         // $FF: synthetic method
         ValueChangeListenerRegargetHandler(Object x0) {
            this();
         }
      }

      private static final class ValidatorRegargetHandler extends AbstractRetargetHandler {
         private static final String VALIDATOR = "validator";
         private static final Class[] VALIDATOR_ARGS = new Class[]{FacesContext.class, UIComponent.class, Object.class};

         private ValidatorRegargetHandler() {
            super(null);
         }

         public void retarget(FacesContext ctx, CompCompInterfaceMethodMetadata metadata, Object sourceValue, UIComponent target) {
            ValueExpression ve = (ValueExpression)sourceValue;
            ExpressionFactory f = ctx.getApplication().getExpressionFactory();
            MethodExpression me = f.createMethodExpression(ctx.getELContext(), ve.getExpressionString(), Void.TYPE, VALIDATOR_ARGS);
            ((EditableValueHolder)target).addValidator(new MethodExpressionValidator(new ContextualCompositeMethodExpression(ve, me)));
         }

         public String getAttribute() {
            return "validator";
         }

         // $FF: synthetic method
         ValidatorRegargetHandler(Object x0) {
            this();
         }
      }

      private static final class ActionListenerRegargetHandler extends AbstractRetargetHandler {
         private static final String ACTION_LISTENER = "actionListener";
         private static final Class[] ACTION_LISTENER_ARGS = new Class[]{ActionEvent.class};

         private ActionListenerRegargetHandler() {
            super(null);
         }

         public void retarget(FacesContext ctx, CompCompInterfaceMethodMetadata metadata, Object sourceValue, UIComponent target) {
            ValueExpression ve = (ValueExpression)sourceValue;
            ExpressionFactory f = ctx.getApplication().getExpressionFactory();
            MethodExpression me = f.createMethodExpression(ctx.getELContext(), ve.getExpressionString(), Void.TYPE, ACTION_LISTENER_ARGS);
            MethodExpression noArg = f.createMethodExpression(ctx.getELContext(), ve.getExpressionString(), Void.TYPE, NO_ARGS);
            ((ActionSource2)target).addActionListener(new MethodExpressionActionListener(new ContextualCompositeMethodExpression(ve, me), new ContextualCompositeMethodExpression(ve, noArg)));
         }

         public String getAttribute() {
            return "actionListener";
         }

         // $FF: synthetic method
         ActionListenerRegargetHandler(Object x0) {
            this();
         }
      }

      private static final class ActionRegargetHandler extends AbstractRetargetHandler {
         private static final String ACTION = "action";

         private ActionRegargetHandler() {
            super(null);
         }

         public void retarget(FacesContext ctx, CompCompInterfaceMethodMetadata metadata, Object sourceValue, UIComponent target) {
            String expr = sourceValue instanceof ValueExpression ? ((ValueExpression)sourceValue).getExpressionString() : sourceValue.toString();
            ExpressionFactory f = ctx.getApplication().getExpressionFactory();
            MethodExpression me = f.createMethodExpression(ctx.getELContext(), expr, Object.class, NO_ARGS);
            ((ActionSource2)target).setActionExpression(new ContextualCompositeMethodExpression(sourceValue instanceof ValueExpression ? (ValueExpression)sourceValue : null, me));
         }

         public String getAttribute() {
            return "action";
         }

         // $FF: synthetic method
         ActionRegargetHandler(Object x0) {
            this();
         }
      }

      private abstract static class AbstractRetargetHandler implements MethodRetargetHandler {
         protected static final Class[] NO_ARGS = new Class[0];

         private AbstractRetargetHandler() {
         }

         // $FF: synthetic method
         AbstractRetargetHandler(Object x0) {
            this();
         }
      }
   }

   private static final class CompCompInterfaceMethodMetadata {
      private final PropertyDescriptor pd;

      CompCompInterfaceMethodMetadata(PropertyDescriptor pd) {
         this.pd = pd;
      }

      public String getMethodSignature(FacesContext ctx) {
         ValueExpression ms = (ValueExpression)this.pd.getValue("method-signature");
         return ms != null ? (String)ms.getValue(ctx.getELContext()) : null;
      }

      public String[] getTargets(FacesContext ctx) {
         ValueExpression ts = (ValueExpression)this.pd.getValue("targets");
         if (ts != null) {
            String targets = (String)ts.getValue(ctx.getELContext());
            if (targets != null) {
               return Util.split(ctx.getExternalContext().getApplicationMap(), targets, " ");
            }
         }

         return null;
      }

      public String getTargetAttributeName(FacesContext ctx) {
         ValueExpression ve = (ValueExpression)this.pd.getValue("targetAttributeName");
         return ve != null ? (String)ve.getValue(ctx.getELContext()) : null;
      }

      public boolean isRequired(FacesContext ctx) {
         ValueExpression rd = (ValueExpression)this.pd.getValue("required");
         return rd != null ? Boolean.valueOf(rd.getValue(ctx.getELContext()).toString()) : false;
      }

      public Object getDefault() {
         return this.pd.getValue("default");
      }

      public String getName() {
         return this.pd.getName();
      }
   }

   private static final class MethodMetadataIterator implements Iterable, Iterator {
      private final PropertyDescriptor[] descriptors;
      private FacesContext context;
      private int curIndex = -1;

      MethodMetadataIterator(FacesContext context, PropertyDescriptor[] descriptors) {
         this.context = context;
         this.descriptors = descriptors;
         if (descriptors != null && descriptors.length > 0) {
            this.curIndex = 0;
         }

      }

      public Iterator iterator() {
         return this;
      }

      public boolean hasNext() {
         if (this.curIndex != -1 && this.curIndex < this.descriptors.length) {
            for(int idx = this.curIndex; idx < this.descriptors.length; ++idx) {
               PropertyDescriptor pd = this.descriptors[idx];
               if (!this.shouldSkip(pd)) {
                  if (idx != this.curIndex) {
                     this.curIndex = idx;
                  }

                  return this.curIndex < this.descriptors.length;
               }
            }
         }

         return false;
      }

      public CompCompInterfaceMethodMetadata next() {
         return new CompCompInterfaceMethodMetadata(this.descriptors[this.curIndex++]);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private boolean shouldSkip(PropertyDescriptor pd) {
         String name = pd.getName();
         ValueExpression ve = (ValueExpression)pd.getValue("targetAttributeName");
         String targetAttributeName = ve != null ? (String)ve.getValue(this.context.getELContext()) : "";
         boolean isSpecialAttributeName = Util.isSpecialAttributeName(name) || Util.isSpecialAttributeName(targetAttributeName);
         boolean result = !isSpecialAttributeName && (pd.getValue("type") != null || pd.getValue("method-signature") == null);
         return result;
      }
   }
}
