package com.sun.faces.component.search;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class SearchKeywordResolverImplId extends AbstractSearchKeywordResolverImpl {
   private static final Pattern PATTERN = Pattern.compile("id\\(([\\w-]+)\\)");

   public void resolve(final SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      FacesContext facesContext = searchKeywordContext.getSearchExpressionContext().getFacesContext();
      final String id = this.extractId(keyword);
      if (this.isHintSet(searchKeywordContext.getSearchExpressionContext(), SearchExpressionHint.SKIP_VIRTUAL_COMPONENTS)) {
         this.findWithId(facesContext, id, current, searchKeywordContext.getCallback());
      } else {
         current.visitTree(VisitContext.createVisitContext(facesContext, (Collection)null, searchKeywordContext.getSearchExpressionContext().getVisitHints()), new VisitCallback() {
            public VisitResult visit(VisitContext context, UIComponent target) {
               if (id.equals(target.getId())) {
                  searchKeywordContext.invokeContextCallback(target);
                  return SearchKeywordResolverImplId.this.isHintSet(searchKeywordContext.getSearchExpressionContext(), SearchExpressionHint.RESOLVE_SINGLE_COMPONENT) ? VisitResult.COMPLETE : VisitResult.ACCEPT;
               } else {
                  return VisitResult.ACCEPT;
               }
            }
         });
      }

      searchKeywordContext.setKeywordResolved(true);
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      if (keyword.startsWith("id")) {
         try {
            Matcher matcher = PATTERN.matcher(keyword);
            return matcher.matches();
         } catch (Exception var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   protected String extractId(String expression) {
      Matcher matcher = PATTERN.matcher(expression);
      if (matcher.matches()) {
         return matcher.group(1);
      } else {
         throw new FacesException("Expression does not match following pattern @id(id). Expression: \"" + expression + "\"");
      }
   }

   private void findWithId(FacesContext context, String id, UIComponent base, ContextCallback callback) {
      if (id.equals(base.getId())) {
         callback.invokeContextCallback(context, base);
      }

      if (base.getFacetCount() > 0) {
         Iterator var5 = base.getFacets().values().iterator();

         while(var5.hasNext()) {
            UIComponent facet = (UIComponent)var5.next();
            this.findWithId(context, id, facet, callback);
         }
      }

      if (base.getChildCount() > 0) {
         int i = 0;

         for(int childCount = base.getChildCount(); i < childCount; ++i) {
            UIComponent child = (UIComponent)base.getChildren().get(i);
            this.findWithId(context, id, child, callback);
         }
      }

   }
}
