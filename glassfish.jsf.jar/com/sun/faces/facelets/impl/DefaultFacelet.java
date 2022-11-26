package com.sun.faces.facelets.impl;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ExpressionFactory;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Facelet;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;

final class DefaultFacelet extends Facelet implements XMLFrontMatterSaver {
   private static final Logger log;
   private static final String APPLIED_KEY = "com.sun.faces.facelets.APPLIED";
   private static final String JAVAX_FACES_ERROR_XHTML = "javax.faces.error.xhtml";
   private final String alias;
   private final ExpressionFactory elFactory;
   private final DefaultFaceletFactory factory;
   private final long createTime;
   private final long refreshPeriod;
   private final FaceletHandler root;
   private final URL src;
   private IdMapper mapper;
   private String savedDoctype;
   private String savedXMLDecl;

   public DefaultFacelet(DefaultFaceletFactory factory, ExpressionFactory el, URL src, String alias, FaceletHandler root) {
      this.factory = factory;
      this.elFactory = el;
      this.src = src;
      this.root = root;
      this.alias = alias;
      this.mapper = (IdMapper)factory.idMappers.get(alias);
      this.createTime = System.currentTimeMillis();
      this.refreshPeriod = this.factory.getRefreshPeriod();
      String DOCTYPE = Util.getDOCTYPEFromFacesContextAttributes(FacesContext.getCurrentInstance());
      if (null != DOCTYPE) {
         this.setSavedDoctype(DOCTYPE);
      }

      String XMLDECL = Util.getXMLDECLFromFacesContextAttributes(FacesContext.getCurrentInstance());
      if (null != XMLDECL) {
         this.setSavedXMLDecl(XMLDECL);
      }

   }

   public void apply(FacesContext facesContext, UIComponent parent) throws IOException {
      IdMapper idMapper = IdMapper.getMapper(facesContext);
      boolean mapperSet = false;
      if (idMapper == null) {
         IdMapper.setMapper(facesContext, this.mapper);
         mapperSet = true;
      }

      DefaultFaceletContext ctx = new DefaultFaceletContext(facesContext, this);
      this.refresh(parent);
      ComponentSupport.markForDeletion(parent);
      this.root.apply(ctx, parent);
      ComponentSupport.finalizeForDeletion(parent);
      this.markApplied(parent);
      if (mapperSet) {
         IdMapper.setMapper(facesContext, (IdMapper)null);
      }

   }

   private void refresh(UIComponent c) {
      if (this.refreshPeriod > 0L) {
         int sz = c.getChildCount();
         if (sz > 0) {
            List cl = c.getChildren();

            while(true) {
               --sz;
               if (sz < 0) {
                  break;
               }

               UIComponent cc = (UIComponent)cl.get(sz);
               if (!cc.isTransient()) {
                  ApplyToken token = (ApplyToken)cc.getAttributes().get("com.sun.faces.facelets.APPLIED");
                  if (token != null && token.time < this.createTime && token.alias.equals(this.alias)) {
                     if (log.isLoggable(Level.INFO)) {
                        DateFormat df = SimpleDateFormat.getTimeInstance();
                        log.info("Facelet[" + this.alias + "] was modified @ " + df.format(new Date(this.createTime)) + ", flushing component applied @ " + df.format(new Date(token.time)));
                     }

                     cl.remove(sz);
                  }
               }
            }
         }

         if (c.getFacets().size() > 0) {
            Collection col = c.getFacets().values();
            Iterator itr = col.iterator();

            while(itr.hasNext()) {
               UIComponent fc = (UIComponent)itr.next();
               if (!fc.isTransient()) {
                  ApplyToken token = (ApplyToken)fc.getAttributes().get("com.sun.faces.facelets.APPLIED");
                  if (token != null && token.time < this.createTime && token.alias.equals(this.alias)) {
                     if (log.isLoggable(Level.INFO)) {
                        DateFormat df = SimpleDateFormat.getTimeInstance();
                        log.info("Facelet[" + this.alias + "] was modified @ " + df.format(new Date(this.createTime)) + ", flushing component applied @ " + df.format(new Date(token.time)));
                     }

                     itr.remove();
                  }
               }
            }
         }
      }

   }

   private void markApplied(UIComponent parent) {
      if (this.refreshPeriod > 0L) {
         Iterator itr = parent.getFacetsAndChildren();
         ApplyToken token = new ApplyToken(this.alias, System.currentTimeMillis() + this.refreshPeriod);

         while(itr.hasNext()) {
            UIComponent c = (UIComponent)itr.next();
            if (!c.isTransient()) {
               Map attr = c.getAttributes();
               if (!attr.containsKey("com.sun.faces.facelets.APPLIED")) {
                  attr.put("com.sun.faces.facelets.APPLIED", token);
               }
            }
         }
      }

   }

   public String getAlias() {
      return this.alias;
   }

   public ExpressionFactory getExpressionFactory() {
      return this.elFactory;
   }

   public long getCreateTime() {
      return this.createTime;
   }

   private URL getRelativePath(String path) throws IOException {
      return this.factory.resolveURL(this.src, path);
   }

   public URL getSource() {
      return this.src;
   }

   private void include(DefaultFaceletContext ctx, UIComponent parent) throws IOException {
      this.refresh(parent);
      this.root.apply(new DefaultFaceletContext(ctx, this), parent);
      this.markApplied(parent);
   }

   public void include(DefaultFaceletContext ctx, UIComponent parent, String path) throws IOException {
      URL url;
      if (path.equals("javax.faces.error.xhtml")) {
         if (!this.isDevelopment(ctx)) {
            return;
         }

         url = this.getErrorFacelet(DefaultFacelet.class.getClassLoader());
         if (url == null) {
            url = this.getErrorFacelet(Util.getCurrentLoader(this));
         }
      } else {
         url = this.getRelativePath(path);
      }

      this.include(ctx, parent, url);
   }

   public void include(DefaultFaceletContext ctx, UIComponent parent, URL url) throws IOException {
      DefaultFacelet f = (DefaultFacelet)this.factory.getFacelet(ctx.getFacesContext(), url);
      f.include(ctx, parent);
   }

   public String toString() {
      return this.alias;
   }

   public String getSavedDoctype() {
      return this.savedDoctype;
   }

   public void setSavedDoctype(String savedDoctype) {
      this.savedDoctype = savedDoctype;
   }

   public String getSavedXMLDecl() {
      return this.savedXMLDecl;
   }

   public void setSavedXMLDecl(String savedXMLDecl) {
      this.savedXMLDecl = savedXMLDecl;
   }

   private boolean isDevelopment(FaceletContext ctx) {
      return ctx.getFacesContext().isProjectStage(ProjectStage.Development);
   }

   private URL getErrorFacelet(ClassLoader loader) {
      return loader.getResource("META-INF/error-include.xhtml");
   }

   static {
      log = FacesLogger.FACELETS_FACELET.getLogger();
   }

   private static class ApplyToken implements Externalizable {
      public String alias;
      public long time;

      public ApplyToken() {
      }

      public ApplyToken(String alias, long time) {
         this.alias = alias;
         this.time = time;
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.alias = in.readUTF();
         this.time = in.readLong();
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeUTF(this.alias);
         out.writeLong(this.time);
      }
   }
}
