package com.sun.faces.facelets.tag.ui;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.FaceletContextImplBase;
import com.sun.faces.facelets.TemplateClient;
import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

public final class CompositionHandler extends TagHandlerImpl implements TemplateClient {
   private static final Logger log;
   public static final String Name = "composition";
   protected final TagAttribute template = this.getAttribute("template");
   protected final Map handlers;
   protected final ParamHandler[] params;

   public CompositionHandler(TagConfig config) {
      super(config);
      if (this.template != null) {
         this.handlers = new HashMap();
         Iterator itr = this.findNextByType(DefineHandler.class);
         DefineHandler d = null;

         while(itr.hasNext()) {
            d = (DefineHandler)itr.next();
            this.handlers.put(d.getName(), d);
            if (log.isLoggable(Level.FINE)) {
               log.fine(this.tag + " found Define[" + d.getName() + "]");
            }
         }

         List paramC = new ArrayList();
         itr = this.findNextByType(ParamHandler.class);

         while(itr.hasNext()) {
            paramC.add(itr.next());
         }

         if (paramC.size() > 0) {
            this.params = new ParamHandler[paramC.size()];

            for(int i = 0; i < this.params.length; ++i) {
               this.params[i] = (ParamHandler)paramC.get(i);
            }
         } else {
            this.params = null;
         }
      } else {
         this.params = null;
         this.handlers = null;
      }

   }

   public void apply(FaceletContext ctxObj, UIComponent parent) throws IOException {
      FaceletContextImplBase ctx = (FaceletContextImplBase)ctxObj;
      if (this.template != null) {
         FacesContext facesContext = ctx.getFacesContext();
         Integer compositionCount = (Integer)facesContext.getAttributes().get("com.sun.faces.uiCompositionCount");
         if (compositionCount == null) {
            compositionCount = 1;
         } else {
            compositionCount = compositionCount + 1;
         }

         facesContext.getAttributes().put("com.sun.faces.uiCompositionCount", compositionCount);
         VariableMapper orig = ctx.getVariableMapper();
         VariableMapperWrapper path;
         if (this.params != null) {
            path = new VariableMapperWrapper(orig);
            ctx.setVariableMapper(path);

            for(int i = 0; i < this.params.length; ++i) {
               this.params[i].apply(ctx, parent);
            }
         }

         ctx.extendClient(this);
         path = null;
         boolean var15 = false;

         try {
            var15 = true;
            String path = this.template.getValue(ctx);
            if (path.trim().length() == 0) {
               throw new TagAttributeException(this.tag, this.template, "Invalid path : " + path);
            }

            WebConfiguration webConfig = WebConfiguration.getInstance();
            if (path.startsWith(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory))) {
               throw new TagAttributeException(this.tag, this.template, "Invalid path, contract resources cannot be accessed this way : " + path);
            }

            ctx.includeFacelet(parent, path);
            var15 = false;
         } catch (IOException var16) {
            if (log.isLoggable(Level.FINE)) {
               log.log(Level.FINE, var16.toString(), var16);
            }

            throw new TagAttributeException(this.tag, this.template, "Invalid path : " + path);
         } finally {
            if (var15) {
               ctx.popClient(this);
               ctx.setVariableMapper(orig);
               compositionCount = (Integer)facesContext.getAttributes().get("com.sun.faces.uiCompositionCount");
               compositionCount = compositionCount - 1;
               if (compositionCount == 0) {
                  facesContext.getAttributes().remove("com.sun.faces.uiCompositionCount");
               } else {
                  facesContext.getAttributes().put("com.sun.faces.uiCompositionCount", compositionCount);
               }

            }
         }

         ctx.popClient(this);
         ctx.setVariableMapper(orig);
         compositionCount = (Integer)facesContext.getAttributes().get("com.sun.faces.uiCompositionCount");
         compositionCount = compositionCount - 1;
         if (compositionCount == 0) {
            facesContext.getAttributes().remove("com.sun.faces.uiCompositionCount");
         } else {
            facesContext.getAttributes().put("com.sun.faces.uiCompositionCount", compositionCount);
         }
      } else {
         this.nextHandler.apply(ctx, parent);
      }

   }

   public boolean apply(FaceletContext ctx, UIComponent parent, String name) throws IOException {
      if (name != null) {
         if (this.handlers == null) {
            return false;
         } else {
            DefineHandler handler = (DefineHandler)this.handlers.get(name);
            if (handler != null) {
               handler.applyDefinition(ctx, parent);
               return true;
            } else {
               return false;
            }
         }
      } else {
         this.nextHandler.apply(ctx, parent);
         return true;
      }
   }

   static {
      log = FacesLogger.FACELETS_COMPOSITION.getLogger();
   }
}
