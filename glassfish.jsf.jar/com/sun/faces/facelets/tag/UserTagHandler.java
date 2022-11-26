package com.sun.faces.facelets.tag;

import com.sun.faces.facelets.FaceletContextImplBase;
import com.sun.faces.facelets.TemplateClient;
import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.tag.ui.DefineHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

final class UserTagHandler extends TagHandlerImpl implements TemplateClient {
   protected final TagAttribute[] vars;
   protected final URL location;
   protected final Map handlers;

   public UserTagHandler(TagConfig config, URL location) {
      super(config);
      this.vars = this.tag.getAttributes().getAll();
      this.location = location;
      Iterator itr = this.findNextByType(DefineHandler.class);
      if (itr.hasNext()) {
         this.handlers = new HashMap();
         DefineHandler d = null;

         while(itr.hasNext()) {
            d = (DefineHandler)itr.next();
            this.handlers.put(d.getName(), d);
         }
      } else {
         this.handlers = null;
      }

   }

   public void apply(FaceletContext ctxObj, UIComponent parent) throws IOException {
      FaceletContextImplBase ctx = (FaceletContextImplBase)ctxObj;
      VariableMapper orig = ctx.getVariableMapper();
      if (this.vars.length > 0) {
         VariableMapper varMapper = new VariableMapperWrapper(orig);

         for(int i = 0; i < this.vars.length; ++i) {
            varMapper.setVariable(this.vars[i].getLocalName(), this.vars[i].getValueExpression(ctx, Object.class));
         }

         ctx.setVariableMapper(varMapper);
      }

      try {
         ctx.pushClient(this);
         ctx.includeFacelet(parent, this.location);
      } catch (FileNotFoundException var10) {
         throw new TagException(this.tag, var10.getMessage());
      } finally {
         ctx.popClient(this);
         ctx.setVariableMapper(orig);
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
}
