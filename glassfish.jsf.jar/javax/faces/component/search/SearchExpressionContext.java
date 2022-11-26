package javax.faces.component.search;

import java.util.Set;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class SearchExpressionContext {
   public abstract UIComponent getSource();

   public abstract Set getVisitHints();

   public abstract Set getExpressionHints();

   public abstract FacesContext getFacesContext();

   public static SearchExpressionContext createSearchExpressionContext(FacesContext context, UIComponent source) {
      return createSearchExpressionContext(context, source, (Set)null, (Set)null);
   }

   public static SearchExpressionContext createSearchExpressionContext(FacesContext context, UIComponent source, Set expressionHints, Set visitHints) {
      SearchExpressionContextFactory factory = (SearchExpressionContextFactory)FactoryFinder.getFactory("javax.faces.component.search.SearchExpressionContextFactory");
      return factory.getSearchExpressionContext(context, source, expressionHints, visitHints);
   }
}
