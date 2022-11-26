package com.sun.faces.component.search;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.component.search.UntargetableComponent;

public class SearchKeywordResolverImplChild extends SearchKeywordResolver {
   private static final Pattern PATTERN = Pattern.compile("child\\((\\d+)\\)");

   public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
      Matcher matcher = PATTERN.matcher(keyword);
      if (!matcher.matches()) {
         throw new FacesException("Expression does not match following pattern @child(n). Expression: \"" + keyword + "\"");
      } else {
         int childNumber = Integer.parseInt(matcher.group(1));
         if (childNumber + 1 > current.getChildCount()) {
            throw new FacesException("Component with clientId \"" + current.getClientId(searchKeywordContext.getSearchExpressionContext().getFacesContext()) + "\" has fewer children as \"" + childNumber + "\". Expression: \"" + keyword + "\"");
         } else {
            List list = current.getChildren();
            int count = 0;

            for(int i = 0; i < current.getChildCount(); ++i) {
               if (!(list.get(i) instanceof UntargetableComponent)) {
                  ++count;
               }

               if (count == childNumber + 1) {
                  searchKeywordContext.invokeContextCallback((UIComponent)current.getChildren().get(childNumber));
                  break;
               }
            }

            if (count < childNumber) {
               throw new FacesException("Component with clientId \"" + current.getClientId(searchKeywordContext.getSearchExpressionContext().getFacesContext()) + "\" has fewer children as \"" + childNumber + "\". Expression: \"" + keyword + "\"");
            }
         }
      }
   }

   public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
      if (keyword.startsWith("child")) {
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
}
