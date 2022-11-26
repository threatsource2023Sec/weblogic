package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.CompositeTagDecorator;
import com.sun.faces.facelets.tag.CompositeTagLibrary;
import com.sun.faces.facelets.tag.TagLibrary;
import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.TagDecorator;

public abstract class Compiler {
   protected static final Logger log;
   public static final String EXPRESSION_FACTORY = "compiler.ExpressionFactory";
   private static final TagLibrary EMPTY_LIBRARY;
   private static final TagDecorator EMPTY_DECORATOR;
   private boolean validating = false;
   private boolean trimmingWhitespace = false;
   private boolean trimmingComments = false;
   private final List libraries = new ArrayList();
   private final List decorators = new ArrayList();
   private final Map features = new HashMap();

   public final FaceletHandler compile(URL src, String alias) throws IOException {
      return this.doCompile(src, alias);
   }

   public final FaceletHandler metadataCompile(URL src, String alias) throws IOException {
      return this.doMetadataCompile(src, alias);
   }

   protected abstract FaceletHandler doMetadataCompile(URL var1, String var2) throws IOException;

   protected abstract FaceletHandler doCompile(URL var1, String var2) throws IOException;

   public final TagDecorator createTagDecorator() {
      return (TagDecorator)(this.decorators.size() > 0 ? new CompositeTagDecorator((TagDecorator[])((TagDecorator[])this.decorators.toArray(new TagDecorator[this.decorators.size()]))) : EMPTY_DECORATOR);
   }

   public final void addTagDecorator(TagDecorator decorator) {
      Util.notNull("decorator", decorator);
      if (!this.decorators.contains(decorator)) {
         this.decorators.add(decorator);
      }

   }

   public final ExpressionFactory createExpressionFactory() {
      ExpressionFactory el = null;
      el = (ExpressionFactory)this.featureInstance("compiler.ExpressionFactory");
      if (el == null) {
         try {
            el = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
            if (el == null && log.isLoggable(Level.WARNING)) {
               log.warning("No default ExpressionFactory from Faces Implementation, attempting to load from Feature[compiler.ExpressionFactory]");
            }
         } catch (Exception var3) {
            if (log.isLoggable(Level.FINEST)) {
               log.log(Level.FINEST, "Unable to get ExpressionFactory because of: ", var3);
            }
         }
      }

      if (el == null) {
         this.features.put("compiler.ExpressionFactory", "com.sun.el.ExpressionFactoryImpl");
         el = (ExpressionFactory)this.featureInstance("compiler.ExpressionFactory");
      }

      return el;
   }

   private final Object featureInstance(String name) {
      String type = (String)this.features.get(name);
      if (type != null) {
         try {
            return ReflectionUtil.forName(type).newInstance();
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var4) {
            throw new FaceletException("Could not instantiate feature[" + name + "]: " + type);
         }
      } else {
         return null;
      }
   }

   public final TagLibrary createTagLibrary(CompilationMessageHolder unit) {
      return (TagLibrary)(this.libraries.size() > 0 ? new CompositeTagLibrary((TagLibrary[])((TagLibrary[])this.libraries.toArray(new TagLibrary[this.libraries.size()])), unit) : EMPTY_LIBRARY);
   }

   public final void addTagLibrary(TagLibrary library) {
      Util.notNull("library", library);
      if (!this.libraries.contains(library)) {
         this.libraries.add(library);
      }

   }

   public final void setFeature(String name, String value) {
      this.features.put(name, value);
   }

   public final String getFeature(String name) {
      return (String)this.features.get(name);
   }

   public final boolean isTrimmingComments() {
      return this.trimmingComments;
   }

   public final void setTrimmingComments(boolean trimmingComments) {
      this.trimmingComments = trimmingComments;
   }

   public final boolean isTrimmingWhitespace() {
      return this.trimmingWhitespace;
   }

   public final void setTrimmingWhitespace(boolean trimmingWhitespace) {
      this.trimmingWhitespace = trimmingWhitespace;
   }

   public final boolean isValidating() {
      return this.validating;
   }

   public final void setValidating(boolean validating) {
      this.validating = validating;
   }

   static {
      log = FacesLogger.FACELETS_COMPILER.getLogger();
      EMPTY_LIBRARY = new CompositeTagLibrary(new TagLibrary[0]);
      EMPTY_DECORATOR = new CompositeTagDecorator(new TagDecorator[0]);
   }
}
