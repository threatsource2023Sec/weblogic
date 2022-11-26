package com.sun.faces.application.view;

import java.util.Arrays;
import java.util.List;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;

public class ViewDeclarationLanguageFactoryImpl extends ViewDeclarationLanguageFactory {
   private ViewHandlingStrategyManager viewHandlingStrategyManager;
   private List allViewDeclarationLanguages;

   public ViewDeclarationLanguageFactoryImpl() {
      super((ViewDeclarationLanguageFactory)null);
   }

   public ViewDeclarationLanguage getViewDeclarationLanguage(String viewId) {
      return this.getViewHandlingStrategyManager().getStrategy(viewId);
   }

   public List getAllViewDeclarationLanguages() {
      if (this.allViewDeclarationLanguages == null) {
         this.allViewDeclarationLanguages = Arrays.asList(this.getViewHandlingStrategyManager().getViewHandlingStrategies());
      }

      return this.allViewDeclarationLanguages;
   }

   private ViewHandlingStrategyManager getViewHandlingStrategyManager() {
      if (this.viewHandlingStrategyManager == null) {
         this.viewHandlingStrategyManager = new ViewHandlingStrategyManager();
      }

      return this.viewHandlingStrategyManager;
   }
}
