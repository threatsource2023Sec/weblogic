package com.sun.faces.facelets.impl;

import com.sun.faces.facelets.FaceletContextImplBase;
import com.sun.faces.facelets.TemplateClient;
import com.sun.faces.facelets.el.DefaultVariableMapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;

final class DefaultFaceletContext extends FaceletContextImplBase {
   private final FacesContext faces;
   private final ELContext ctx;
   private final DefaultFacelet facelet;
   private final List faceletHierarchy;
   private VariableMapper varMapper;
   private FunctionMapper fnMapper;
   private final Map ids;
   private final Map prefixes;
   private String prefix;
   private final StringBuilder uniqueIdBuilder = new StringBuilder(30);
   private final List clients;

   public DefaultFaceletContext(DefaultFaceletContext ctx, DefaultFacelet facelet) {
      this.ctx = ctx.ctx;
      this.clients = ctx.clients;
      this.faces = ctx.faces;
      this.fnMapper = ctx.fnMapper;
      this.ids = ctx.ids;
      this.prefixes = ctx.prefixes;
      this.varMapper = ctx.varMapper;
      this.faceletHierarchy = new ArrayList(ctx.faceletHierarchy.size() + 1);
      this.faceletHierarchy.addAll(ctx.faceletHierarchy);
      this.faceletHierarchy.add(facelet);
      this.facelet = facelet;
      this.faces.getAttributes().put(FaceletContext.FACELET_CONTEXT_KEY, this);
   }

   public DefaultFaceletContext(FacesContext faces, DefaultFacelet facelet) {
      this.ctx = faces.getELContext();
      this.ids = new HashMap();
      this.prefixes = new HashMap();
      this.clients = new ArrayList(5);
      this.faces = faces;
      this.faceletHierarchy = new ArrayList(1);
      this.faceletHierarchy.add(facelet);
      this.facelet = facelet;
      this.varMapper = this.ctx.getVariableMapper();
      if (this.varMapper == null) {
         this.varMapper = new DefaultVariableMapper();
      }

      this.fnMapper = this.ctx.getFunctionMapper();
      this.faces.getAttributes().put(FaceletContext.FACELET_CONTEXT_KEY, this);
   }

   public FacesContext getFacesContext() {
      return this.faces;
   }

   public ExpressionFactory getExpressionFactory() {
      return this.facelet.getExpressionFactory();
   }

   public void setVariableMapper(VariableMapper varMapper) {
      this.varMapper = varMapper;
   }

   public void setFunctionMapper(FunctionMapper fnMapper) {
      this.fnMapper = fnMapper;
   }

   public void includeFacelet(UIComponent parent, String relativePath) throws IOException, FacesException, ELException {
      this.facelet.include(this, parent, relativePath);
   }

   public FunctionMapper getFunctionMapper() {
      return this.fnMapper;
   }

   public VariableMapper getVariableMapper() {
      return this.varMapper;
   }

   public Object getContext(Class key) {
      return this.ctx.getContext(key);
   }

   public void putContext(Class key, Object contextObject) {
      this.ctx.putContext(key, contextObject);
   }

   public String generateUniqueId(String base) {
      int i;
      if (this.prefix == null) {
         StringBuilder builder = new StringBuilder(this.faceletHierarchy.size() * 30);

         for(i = 0; i < this.faceletHierarchy.size(); ++i) {
            DefaultFacelet facelet = (DefaultFacelet)this.faceletHierarchy.get(i);
            builder.append(facelet.getAlias());
         }

         Integer prefixInt = builder.toString().hashCode();
         Integer cnt = (Integer)this.prefixes.get(prefixInt);
         if (cnt == null) {
            this.prefixes.put(prefixInt, 0);
            this.prefix = prefixInt.toString();
         } else {
            int i = cnt + 1;
            this.prefixes.put(prefixInt, i);
            this.prefix = prefixInt + "_" + i;
         }
      }

      Integer cnt = (Integer)this.ids.get(base);
      if (cnt == null) {
         this.ids.put(base, 0);
         this.uniqueIdBuilder.delete(0, this.uniqueIdBuilder.length());
         this.uniqueIdBuilder.append(this.prefix);
         this.uniqueIdBuilder.append("_");
         this.uniqueIdBuilder.append(base);
         return this.uniqueIdBuilder.toString();
      } else {
         i = cnt + 1;
         this.ids.put(base, i);
         this.uniqueIdBuilder.delete(0, this.uniqueIdBuilder.length());
         this.uniqueIdBuilder.append(this.prefix);
         this.uniqueIdBuilder.append("_");
         this.uniqueIdBuilder.append(base);
         this.uniqueIdBuilder.append("_");
         this.uniqueIdBuilder.append(i);
         return this.uniqueIdBuilder.toString();
      }
   }

   public Object getAttribute(String name) {
      if (this.varMapper != null) {
         ValueExpression ve = this.varMapper.resolveVariable(name);
         if (ve != null) {
            return ve.getValue(this);
         }
      }

      return null;
   }

   public void setAttribute(String name, Object value) {
      if (this.varMapper != null) {
         if (value == null) {
            this.varMapper.setVariable(name, (ValueExpression)null);
         } else {
            this.varMapper.setVariable(name, this.facelet.getExpressionFactory().createValueExpression(value, Object.class));
         }
      }

   }

   public void includeFacelet(UIComponent parent, URL absolutePath) throws IOException, FacesException, ELException {
      this.facelet.include(this, parent, absolutePath);
   }

   public ELResolver getELResolver() {
      return this.ctx.getELResolver();
   }

   public void popClient(TemplateClient client) {
      if (!this.clients.isEmpty()) {
         Iterator itr = this.clients.iterator();

         while(itr.hasNext()) {
            if (itr.next().equals(client)) {
               itr.remove();
               return;
            }
         }
      }

      throw new IllegalStateException(client + " not found");
   }

   public void pushClient(TemplateClient client) {
      this.clients.add(0, new TemplateManager(this.facelet, client, true));
   }

   public void extendClient(TemplateClient client) {
      this.clients.add(new TemplateManager(this.facelet, client, false));
   }

   public boolean includeDefinition(UIComponent parent, String name) throws IOException {
      boolean found = false;
      int i = 0;

      for(int size = this.clients.size(); i < size && !found; ++i) {
         TemplateManager client = (TemplateManager)this.clients.get(i);
         if (!client.equals(this.facelet)) {
            found = client.apply(this, parent, name);
         }
      }

      return found;
   }

   public boolean isPropertyResolved() {
      return this.ctx.isPropertyResolved();
   }

   public void setPropertyResolved(boolean resolved) {
      this.ctx.setPropertyResolved(resolved);
   }

   private static final class TemplateManager implements TemplateClient {
      private final DefaultFacelet owner;
      private final TemplateClient target;
      private final boolean root;
      private final Set names = new HashSet();

      public TemplateManager(DefaultFacelet owner, TemplateClient target, boolean root) {
         this.owner = owner;
         this.target = target;
         this.root = root;
      }

      public boolean apply(FaceletContext ctx, UIComponent parent, String name) throws IOException {
         String testName = name != null ? name : "facelets._NULL_DEF_";
         if (this.names.contains(testName)) {
            return false;
         } else {
            this.names.add(testName);
            boolean found = this.target.apply(new DefaultFaceletContext((DefaultFaceletContext)ctx, this.owner), parent, name);
            this.names.remove(testName);
            return found;
         }
      }

      public boolean equals(Object o) {
         return this.owner == o || this.target == o;
      }

      public boolean isRoot() {
         return this.root;
      }
   }
}
