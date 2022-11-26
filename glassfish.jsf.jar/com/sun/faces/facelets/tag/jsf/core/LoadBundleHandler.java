package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

public final class LoadBundleHandler extends TagHandlerImpl {
   private final TagAttribute basename = this.getRequiredAttribute("basename");
   private final TagAttribute var = this.getRequiredAttribute("var");

   public LoadBundleHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
      ResourceBundle bundle = null;

      try {
         String name = this.basename.getValue(ctx);
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (root != null && root.getLocale() != null) {
            bundle = ResourceBundle.getBundle(name, root.getLocale(), cl);
         } else {
            bundle = ResourceBundle.getBundle(name, Locale.getDefault(), cl);
         }
      } catch (Exception var7) {
         throw new TagAttributeException(this.tag, this.basename, var7);
      }

      ResourceBundleMap map = new ResourceBundleMap(bundle);
      FacesContext faces = ctx.getFacesContext();
      faces.getExternalContext().getRequestMap().put(this.var.getValue(ctx), map);
   }

   private static final class ResourceBundleMap implements Map {
      protected final ResourceBundle bundle;

      public ResourceBundleMap(ResourceBundle bundle) {
         this.bundle = bundle;
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(Object key) {
         try {
            this.bundle.getString(key.toString());
            return true;
         } catch (MissingResourceException var3) {
            return false;
         }
      }

      public boolean containsValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public Set entrySet() {
         Enumeration e = this.bundle.getKeys();
         Set s = new HashSet();

         while(e.hasMoreElements()) {
            String k = (String)e.nextElement();
            s.add(new ResourceEntry(k, this.bundle.getString(k)));
         }

         return s;
      }

      public Object get(Object key) {
         try {
            return this.bundle.getObject((String)key);
         } catch (MissingResourceException var3) {
            return "???" + key + "???";
         }
      }

      public boolean isEmpty() {
         return false;
      }

      public Set keySet() {
         Enumeration e = this.bundle.getKeys();
         Set s = new HashSet();

         while(e.hasMoreElements()) {
            s.add(e.nextElement());
         }

         return s;
      }

      public Object put(Object key, Object value) {
         throw new UnsupportedOperationException();
      }

      public void putAll(Map t) {
         throw new UnsupportedOperationException();
      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.keySet().size();
      }

      public Collection values() {
         Enumeration e = this.bundle.getKeys();
         Set s = new HashSet();

         while(e.hasMoreElements()) {
            s.add(this.bundle.getObject((String)e.nextElement()));
         }

         return s;
      }

      private static final class ResourceEntry implements Map.Entry {
         protected final String key;
         protected final String value;

         public ResourceEntry(String key, String value) {
            this.key = key;
            this.value = value;
         }

         public Object getKey() {
            return this.key;
         }

         public Object getValue() {
            return this.value;
         }

         public Object setValue(Object value) {
            throw new UnsupportedOperationException();
         }

         public int hashCode() {
            return this.key.hashCode();
         }

         public boolean equals(Object obj) {
            return obj instanceof ResourceEntry && this.hashCode() == obj.hashCode();
         }
      }
   }
}
