package com.sun.faces.facelets.tag;

import com.sun.faces.facelets.compiler.CompilationMessageHolder;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagLibrary;
import com.sun.faces.facelets.tag.jsf.FacesComponentTagLibrary;
import com.sun.faces.facelets.tag.jsf.LazyTagLibrary;
import com.sun.faces.util.Util;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public final class CompositeTagLibrary implements TagLibrary {
   private TagLibrary[] libraries;
   private CompilationMessageHolder messageHolder;

   public CompositeTagLibrary(TagLibrary[] libraries, CompilationMessageHolder unit) {
      Util.notNull("libraries", libraries);
      this.libraries = libraries;
      this.messageHolder = unit;
   }

   public CompositeTagLibrary(TagLibrary[] libraries) {
      this(libraries, (CompilationMessageHolder)null);
   }

   public boolean containsNamespace(String ns, Tag t) {
      boolean result = true;

      for(int i = 0; i < this.libraries.length; ++i) {
         if (this.libraries[i].containsNamespace(ns, (Tag)null)) {
            return true;
         }
      }

      LazyTagLibrary[] lazyLibraries = new LazyTagLibrary[]{new CompositeComponentTagLibrary(ns), new FacesComponentTagLibrary(ns)};
      LazyTagLibrary toTest = null;

      for(int i = 0; i < lazyLibraries.length; ++i) {
         if (lazyLibraries[i].tagLibraryForNSExists(ns)) {
            toTest = lazyLibraries[i];
            break;
         }
      }

      if (null == toTest) {
         FacesContext context = FacesContext.getCurrentInstance();
         if (context.isProjectStage(ProjectStage.Development) && null != t && !ns.equals("http://www.w3.org/1999/xhtml")) {
            assert null != this.messageHolder;

            String prefix = this.getPrefixFromTag(t);
            if (null != prefix) {
               List prefixMessages = this.messageHolder.getNamespacePrefixMessages(context, prefix);
               prefixMessages.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning: This page calls for XML namespace " + ns + " declared with prefix " + prefix + " but no taglibrary exists for that namespace.", ""));
            }
         }

         return false;
      } else {
         TagLibrary[] librariesPlusOne = new TagLibrary[this.libraries.length + 1];
         System.arraycopy(this.libraries, 0, librariesPlusOne, 0, this.libraries.length);
         librariesPlusOne[this.libraries.length] = toTest;

         for(int i = 0; i < this.libraries.length; ++i) {
            this.libraries[i] = null;
         }

         this.libraries = librariesPlusOne;
         return true;
      }
   }

   private String getPrefixFromTag(Tag t) {
      String result = t.getQName();
      int i;
      if (null != result && -1 != (i = result.indexOf(":"))) {
         result = result.substring(0, i);
      }

      return result;
   }

   public boolean containsTagHandler(String ns, String localName) {
      for(int i = 0; i < this.libraries.length; ++i) {
         if (this.libraries[i].containsTagHandler(ns, localName)) {
            return true;
         }
      }

      return false;
   }

   public TagHandler createTagHandler(String ns, String localName, TagConfig tag) throws FacesException {
      for(int i = 0; i < this.libraries.length; ++i) {
         if (this.libraries[i].containsTagHandler(ns, localName)) {
            return this.libraries[i].createTagHandler(ns, localName, tag);
         }
      }

      return null;
   }

   public boolean containsFunction(String ns, String name) {
      for(int i = 0; i < this.libraries.length; ++i) {
         if (this.libraries[i].containsFunction(ns, name)) {
            return true;
         }
      }

      return false;
   }

   public Method createFunction(String ns, String name) {
      for(int i = 0; i < this.libraries.length; ++i) {
         if (this.libraries[i].containsFunction(ns, name)) {
            return this.libraries[i].createFunction(ns, name);
         }
      }

      return null;
   }
}
