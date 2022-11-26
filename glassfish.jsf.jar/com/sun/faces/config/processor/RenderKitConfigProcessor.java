package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RenderKitConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String RENDERKIT = "render-kit";
   private static final String RENDERKIT_ID = "render-kit-id";
   private static final String RENDERKIT_CLASS = "render-kit-class";
   private static final String RENDERER = "renderer";
   private static final String RENDERER_FAMILY = "component-family";
   private static final String RENDERER_TYPE = "renderer-type";
   private static final String RENDERER_CLASS = "renderer-class";
   private static final String CLIENT_BEHAVIOR_RENDERER = "client-behavior-renderer";
   private static final String CLIENT_BEHAVIOR_RENDERER_TYPE = "client-behavior-renderer-type";
   private static final String CLIENT_BEHAVIOR_RENDERER_CLASS = "client-behavior-renderer-class";

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      Map renderers = new LinkedHashMap();
      Map behaviorRenderers = new LinkedHashMap();
      RenderKitFactory renderKitFactory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing render-kit elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList renderkits = document.getDocumentElement().getElementsByTagNameNS(namespace, "render-kit");
         if (renderkits != null && renderkits.getLength() > 0) {
            this.addRenderKits(servletContext, facesContext, renderkits, document, renderers, behaviorRenderers, renderKitFactory);
         }
      }

      this.processAnnotations(facesContext, FacesRenderer.class);
      this.processAnnotations(facesContext, FacesBehaviorRenderer.class);
      Iterator var12 = renderers.entrySet().iterator();

      Map.Entry renderEntry;
      Map.Entry entry;
      RenderKit renderKit;
      Iterator var15;
      while(var12.hasNext()) {
         entry = (Map.Entry)var12.next();
         renderKit = renderKitFactory.getRenderKit((FacesContext)null, (String)entry.getKey());
         if (renderKit == null) {
            throw new ConfigurationException(MessageUtils.getExceptionMessageString("com.sun.faces.CONFIG_RENDERER_REGISTRATION_MISSING_RENDERKIT", entry.getKey()));
         }

         var15 = ((Map)entry.getValue()).entrySet().iterator();

         while(var15.hasNext()) {
            renderEntry = (Map.Entry)var15.next();
            this.addRenderers(servletContext, facesContext, renderKit, (Document)renderEntry.getKey(), (List)renderEntry.getValue());
         }
      }

      var12 = behaviorRenderers.entrySet().iterator();

      while(var12.hasNext()) {
         entry = (Map.Entry)var12.next();
         renderKit = renderKitFactory.getRenderKit((FacesContext)null, (String)entry.getKey());
         if (renderKit == null) {
            throw new ConfigurationException(MessageUtils.getExceptionMessageString("com.sun.faces.CONFIG_RENDERER_REGISTRATION_MISSING_RENDERKIT", entry.getKey()));
         }

         var15 = ((Map)entry.getValue()).entrySet().iterator();

         while(var15.hasNext()) {
            renderEntry = (Map.Entry)var15.next();
            this.addClientBehaviorRenderers(servletContext, facesContext, renderKit, (Document)renderEntry.getKey(), (List)renderEntry.getValue());
         }
      }

   }

   private void addRenderKits(ServletContext sc, FacesContext facesContext, NodeList renderKits, Document owningDocument, Map renderers, Map behaviorRenderers, RenderKitFactory renderKitFactory) {
      String namespace = owningDocument.getDocumentElement().getNamespaceURI();
      int i = 0;

      for(int size = renderKits.getLength(); i < size; ++i) {
         Node renderKitNode = renderKits.item(i);
         NodeList children = ((Element)renderKitNode).getElementsByTagNameNS(namespace, "*");
         String renderKitId = null;
         String renderKitClass = null;
         List renderersList = new ArrayList(children.getLength());
         List behaviorRenderersList = new ArrayList(children.getLength());
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            switch (n.getLocalName()) {
               case "render-kit-id":
                  renderKitId = this.getNodeText(n);
                  break;
               case "render-kit-class":
                  renderKitClass = this.getNodeText(n);
                  break;
               case "renderer":
                  renderersList.add(n);
                  break;
               case "client-behavior-renderer":
                  behaviorRenderersList.add(n);
            }
         }

         renderKitId = renderKitId == null ? "HTML_BASIC" : renderKitId;
         if (renderKitClass != null) {
            RenderKit previousRenderKit = renderKitFactory.getRenderKit(facesContext, renderKitId);
            RenderKit renderKit = (RenderKit)this.createInstance(sc, facesContext, renderKitClass, RenderKit.class, previousRenderKit, renderKitNode);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling RenderKitFactory.addRenderKit({0}, {1})", renderKitId, renderKitClass));
            }

            renderKitFactory.addRenderKit(renderKitId, renderKit);
         }

         Map existingRenderers = (Map)renderers.get(renderKitId);
         if (existingRenderers != null) {
            List list = (List)((Map)existingRenderers).get(owningDocument);
            if (list != null) {
               list.addAll(renderersList);
            } else {
               ((Map)existingRenderers).put(owningDocument, renderersList);
            }
         } else {
            existingRenderers = new LinkedHashMap();
            ((Map)existingRenderers).put(owningDocument, renderersList);
         }

         renderers.put(renderKitId, existingRenderers);
         Map existingBehaviorRenderers = (Map)behaviorRenderers.get(renderKitId);
         if (existingBehaviorRenderers != null) {
            List list = (List)((Map)existingBehaviorRenderers).get(owningDocument);
            if (list != null) {
               list.addAll(behaviorRenderersList);
            } else {
               ((Map)existingBehaviorRenderers).put(owningDocument, behaviorRenderersList);
            }
         } else {
            existingBehaviorRenderers = new LinkedHashMap();
            ((Map)existingBehaviorRenderers).put(owningDocument, behaviorRenderersList);
         }

         behaviorRenderers.put(renderKitId, existingBehaviorRenderers);
      }

   }

   private void addRenderers(ServletContext sc, FacesContext facesContext, RenderKit renderKit, Document owningDocument, List renderers) {
      String namespace = owningDocument.getDocumentElement().getNamespaceURI();
      Iterator var7 = renderers.iterator();

      while(var7.hasNext()) {
         Node rendererNode = (Node)var7.next();
         NodeList children = ((Element)rendererNode).getElementsByTagNameNS(namespace, "*");
         String rendererFamily = null;
         String rendererType = null;
         String rendererClass = null;
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node n = children.item(i);
            switch (n.getLocalName()) {
               case "component-family":
                  rendererFamily = this.getNodeText(n);
                  break;
               case "renderer-type":
                  rendererType = this.getNodeText(n);
                  break;
               case "renderer-class":
                  rendererClass = this.getNodeText(n);
            }
         }

         if (rendererFamily != null && rendererType != null && rendererClass != null) {
            Renderer renderer = (Renderer)this.createInstance(sc, facesContext, rendererClass, Renderer.class, (Object)null, rendererNode);
            if (renderer != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling RenderKit.addRenderer({0},{1}, {2}) for RenderKit ''{3}''", rendererFamily, rendererType, rendererClass, renderKit.getClass()));
               }

               renderKit.addRenderer(rendererFamily, rendererType, renderer);
            }
         }
      }

   }

   private void addClientBehaviorRenderers(ServletContext servletContext, FacesContext facesContext, RenderKit renderKit, Document owningDocument, List behaviorRenderers) {
      String namespace = owningDocument.getDocumentElement().getNamespaceURI();
      Iterator var7 = behaviorRenderers.iterator();

      while(var7.hasNext()) {
         Node behaviorRendererNode = (Node)var7.next();
         NodeList children = ((Element)behaviorRendererNode).getElementsByTagNameNS(namespace, "*");
         String behaviorRendererType = null;
         String behaviorRendererClass = null;
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node n = children.item(i);
            switch (n.getLocalName()) {
               case "client-behavior-renderer-type":
                  behaviorRendererType = this.getNodeText(n);
                  break;
               case "client-behavior-renderer-class":
                  behaviorRendererClass = this.getNodeText(n);
            }
         }

         if (behaviorRendererType != null && behaviorRendererClass != null) {
            ClientBehaviorRenderer behaviorRenderer = (ClientBehaviorRenderer)this.createInstance(servletContext, facesContext, behaviorRendererClass, ClientBehaviorRenderer.class, (Object)null, behaviorRendererNode);
            if (behaviorRenderer != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling RenderKit.addClientBehaviorRenderer({0},{1}, {2}) for RenderKit ''{2}''", behaviorRendererType, behaviorRendererClass, renderKit.getClass()));
               }

               renderKit.addClientBehaviorRenderer(behaviorRendererType, behaviorRenderer);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
