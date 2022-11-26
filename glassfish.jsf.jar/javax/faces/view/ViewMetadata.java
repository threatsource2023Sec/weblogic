package javax.faces.view;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.faces.component.UIImportConstants;
import javax.faces.component.UIViewAction;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class ViewMetadata {
   public abstract String getViewId();

   public abstract UIViewRoot createMetadataView(FacesContext var1);

   public static Collection getViewParameters(UIViewRoot root) {
      return getMetadataChildren(root, UIViewParameter.class);
   }

   public static Collection getViewActions(UIViewRoot root) {
      return getMetadataChildren(root, UIViewAction.class);
   }

   public static Collection getImportConstants(UIViewRoot root) {
      return getMetadataChildren(root, UIImportConstants.class);
   }

   public static boolean hasMetadata(UIViewRoot root) {
      return (Boolean)getMetadataFacet(root).map((m) -> {
         return m.getChildCount() > 0;
      }).orElse(false);
   }

   private static List getMetadataChildren(UIViewRoot root, Class type) {
      return (List)((List)getMetadataFacet(root).map((m) -> {
         return m.getChildren();
      }).orElseGet(Collections::emptyList)).stream().filter((c) -> {
         return type.isInstance(c);
      }).collect(Collectors.toList());
   }

   private static Optional getMetadataFacet(UIViewRoot root) {
      return Optional.ofNullable(root.getFacet("javax_faces_metadata"));
   }
}
