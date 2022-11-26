package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
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

   public void process(ServletContext sc, Document[] documents) throws Exception {
      Map renderers = new LinkedHashMap();
      RenderKitFactory rkf = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");

      for(int i = 0; i < documents.length; ++i) {
         Document document = documents[i];
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing render-kit elements for document: ''{0}''", document.getDocumentURI()));
         }

         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList renderkits = document.getDocumentElement().getElementsByTagNameNS(namespace, "render-kit");
         if (renderkits != null && renderkits.getLength() > 0) {
            this.addRenderKits(renderkits, document, renderers, rkf);
         }
      }

      Iterator i$ = renderers.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         RenderKit rk = rkf.getRenderKit((FacesContext)null, (String)entry.getKey());
         if (rk == null) {
            throw new ConfigurationException(MessageUtils.getExceptionMessageString("com.sun.faces.CONFIG_RENDERER_REGISTRATION_MISSING_RENDERKIT", entry.getKey()));
         }

         Iterator i$ = ((Map)entry.getValue()).entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry renderEntry = (Map.Entry)i$.next();
            this.addRenderers(rk, (Document)renderEntry.getKey(), (List)renderEntry.getValue());
         }
      }

      this.invokeNext(sc, documents);
   }

   private void addRenderKits(NodeList renderKits, Document owningDocument, Map renderers, RenderKitFactory rkf) {
      String namespace = owningDocument.getDocumentElement().getNamespaceURI();
      int i = 0;

      for(int size = renderKits.getLength(); i < size; ++i) {
         Node renderKit = renderKits.item(i);
         NodeList children = ((Element)renderKit).getElementsByTagNameNS(namespace, "*");
         String rkId = null;
         String rkClass = null;
         List renderersList = new ArrayList(children.getLength());
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if ("render-kit-id".equals(n.getLocalName())) {
               rkId = this.getNodeText(n);
            } else if ("render-kit-class".equals(n.getLocalName())) {
               rkClass = this.getNodeText(n);
            } else if ("renderer".equals(n.getLocalName())) {
               renderersList.add(n);
            }
         }

         rkId = rkId == null ? "HTML_BASIC" : rkId;
         RenderKit rk = rkf.getRenderKit((FacesContext)null, rkId);
         if (rk == null && rkClass != null) {
            rk = (RenderKit)this.createInstance(rkClass, RenderKit.class, (Object)null, renderKit);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling RenderKitFactory.addRenderKit({0}, {1})", rkId, rkClass));
            }

            rkf.addRenderKit(rkId, rk);
         }

         Map existingRenderers = (Map)renderers.get(rkId);
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

         renderers.put(rkId, existingRenderers);
      }

   }

   private void addRenderers(RenderKit renderKit, Document owningDocument, List renderers) {
      String namespace = owningDocument.getDocumentElement().getNamespaceURI();
      Iterator i$ = renderers.iterator();

      while(i$.hasNext()) {
         Node renderer = (Node)i$.next();
         NodeList children = ((Element)renderer).getElementsByTagNameNS(namespace, "*");
         String rendererFamily = null;
         String rendererType = null;
         String rendererClass = null;
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node n = children.item(i);
            if ("component-family".equals(n.getLocalName())) {
               rendererFamily = this.getNodeText(n);
            } else if ("renderer-type".equals(n.getLocalName())) {
               rendererType = this.getNodeText(n);
            } else if ("renderer-class".equals(n.getLocalName())) {
               rendererClass = this.getNodeText(n);
            }
         }

         if (rendererFamily != null && rendererType != null && rendererClass != null) {
            Renderer r = (Renderer)this.createInstance(rendererClass, Renderer.class, (Object)null, renderer);
            if (r != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling RenderKit.addRenderer({0},{1}, {2}) for RenderKit ''{3}''", rendererFamily, rendererType, rendererClass, renderKit.getClass()));
               }

               renderKit.addRenderer(rendererFamily, rendererType, r);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
