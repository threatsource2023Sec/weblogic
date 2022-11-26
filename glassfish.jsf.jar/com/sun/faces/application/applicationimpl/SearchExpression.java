package com.sun.faces.application.applicationimpl;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.component.search.CompositeSearchKeywordResolver;
import com.sun.faces.component.search.SearchKeywordResolverImplAll;
import com.sun.faces.component.search.SearchKeywordResolverImplChild;
import com.sun.faces.component.search.SearchKeywordResolverImplComposite;
import com.sun.faces.component.search.SearchKeywordResolverImplForm;
import com.sun.faces.component.search.SearchKeywordResolverImplId;
import com.sun.faces.component.search.SearchKeywordResolverImplNamingContainer;
import com.sun.faces.component.search.SearchKeywordResolverImplNext;
import com.sun.faces.component.search.SearchKeywordResolverImplNone;
import com.sun.faces.component.search.SearchKeywordResolverImplParent;
import com.sun.faces.component.search.SearchKeywordResolverImplPrevious;
import com.sun.faces.component.search.SearchKeywordResolverImplRoot;
import com.sun.faces.component.search.SearchKeywordResolverImplThis;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchKeywordResolver;

public class SearchExpression {
   private static final Logger LOGGER;
   private final ApplicationAssociate associate;
   private CompositeSearchKeywordResolver searchKeywordResolvers;

   public SearchExpression(ApplicationAssociate applicationAssociate) {
      this.associate = applicationAssociate;
      this.searchKeywordResolvers = new CompositeSearchKeywordResolver();
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplThis());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplParent());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplForm());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplComposite());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplNext());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplPrevious());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplNone());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplNamingContainer());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplRoot());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplId());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplChild());
      this.searchKeywordResolvers.add(new SearchKeywordResolverImplAll());
   }

   public SearchExpressionHandler getSearchExpressionHandler() {
      return this.associate.getSearchExpressionHandler();
   }

   public void setSearchExpressionHandler(SearchExpressionHandler searchExpressionHandler) {
      Util.notNull("searchExpressionHandler", searchExpressionHandler);
      this.associate.setSearchExpressionHandler(searchExpressionHandler);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("Set SearchExpressionHandler Instance to ''{0}''", searchExpressionHandler.getClass().getName()));
      }

   }

   public void addSearchKeywordResolver(SearchKeywordResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_APPLICATION_ARTIFACT", "SearchKeywordResolver"));
      } else {
         this.searchKeywordResolvers.add(resolver);
      }
   }

   public SearchKeywordResolver getSearchKeywordResolver() {
      return this.searchKeywordResolvers;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
