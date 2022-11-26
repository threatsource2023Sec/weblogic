package javax.faces.view;

import java.util.Collections;
import java.util.List;
import javax.faces.FacesWrapper;

public abstract class ViewDeclarationLanguageFactory implements FacesWrapper {
   private ViewDeclarationLanguageFactory wrapped;

   /** @deprecated */
   @Deprecated
   public ViewDeclarationLanguageFactory() {
   }

   public ViewDeclarationLanguageFactory(ViewDeclarationLanguageFactory wrapped) {
      this.wrapped = wrapped;
   }

   public ViewDeclarationLanguageFactory getWrapped() {
      return this.wrapped;
   }

   public abstract ViewDeclarationLanguage getViewDeclarationLanguage(String var1);

   public List getAllViewDeclarationLanguages() {
      return Collections.emptyList();
   }
}
