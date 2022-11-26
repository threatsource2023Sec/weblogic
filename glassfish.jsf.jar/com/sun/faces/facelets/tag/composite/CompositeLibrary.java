package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class CompositeLibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://java.sun.com/jsf/composite";
   public static final String XMLNSNamespace = "http://xmlns.jcp.org/jsf/composite";
   public static final CompositeLibrary Instance = new CompositeLibrary();

   public CompositeLibrary() {
      this("http://java.sun.com/jsf/composite");
   }

   public CompositeLibrary(String namespace) {
      super(namespace);
      this.addTagHandler("interface", InterfaceHandler.class);
      this.addTagHandler("attribute", AttributeHandler.class);
      this.addTagHandler("extension", ExtensionHandler.class);
      this.addTagHandler("editableValueHolder", EditableValueHolderAttachedObjectTargetHandler.class);
      this.addTagHandler("actionSource", ActionSource2AttachedObjectTargetHandler.class);
      this.addTagHandler("valueHolder", ValueHolderAttachedObjectTargetHandler.class);
      this.addTagHandler("clientBehavior", BehaviorHolderAttachedObjectTargetHandler.class);
      this.addTagHandler("facet", DeclareFacetHandler.class);
      this.addTagHandler("implementation", ImplementationHandler.class);
      this.addTagHandler("insertChildren", InsertChildrenHandler.class);
      this.addTagHandler("insertFacet", InsertFacetHandler.class);
      this.addComponent("renderFacet", "javax.faces.Output", "javax.faces.CompositeFacet", RenderFacetHandler.class);
   }
}
