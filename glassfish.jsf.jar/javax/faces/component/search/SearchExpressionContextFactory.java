package javax.faces.component.search;

import java.util.Set;
import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class SearchExpressionContextFactory implements FacesWrapper {
   private final SearchExpressionContextFactory wrapped;

   public SearchExpressionContextFactory(SearchExpressionContextFactory wrapped) {
      this.wrapped = wrapped;
   }

   public SearchExpressionContextFactory getWrapped() {
      return this.wrapped;
   }

   public abstract SearchExpressionContext getSearchExpressionContext(FacesContext var1, UIComponent var2, Set var3, Set var4);
}
