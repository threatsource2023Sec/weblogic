package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.TagLibrary;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;

class TagUnit extends CompilationUnit implements TagConfig {
   private final TagLibrary library;
   private final String id;
   private final Tag tag;
   private final String namespace;
   private final String name;

   public TagUnit(TagLibrary library, String namespace, String name, Tag tag, String id) {
      this.library = library;
      this.tag = tag;
      this.namespace = namespace;
      this.name = name;
      this.id = id;
   }

   protected void startNotify(CompilationManager manager) {
      if (this.name.equals("composition") && (this.namespace.equals("http://java.sun.com/jsf/facelets") || this.namespace.equals("http://java.sun.com/jsf/facelets"))) {
         CompilerPackageCompilationMessageHolder messageHolder = (CompilerPackageCompilationMessageHolder)manager.getCompilationMessageHolder();
         CompilationManager compositeComponentCompilationManager = messageHolder.getCurrentCompositeComponentCompilationManager();
         if (manager.equals(compositeComponentCompilationManager)) {
            String messageStr = "Because the definition of ui:composition causes any parent content to be ignored, it is invalid to use ui:composition directly inside of a composite component. Consider ui:decorate instead.";
            throw new FaceletException(messageStr);
         }
      }

   }

   public FaceletHandler createFaceletHandler() {
      return this.library.createTagHandler(this.namespace, this.name, this);
   }

   public FaceletHandler getNextHandler() {
      return this.getNextFaceletHandler();
   }

   public Tag getTag() {
      return this.tag;
   }

   public String getTagId() {
      return this.id;
   }

   public String toString() {
      return this.tag.toString();
   }
}
