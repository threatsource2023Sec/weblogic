package com.sun.faces.application.view;

import com.sun.faces.config.WebConfiguration;

public class ViewHandlingStrategyManager {
   private volatile ViewHandlingStrategy[] strategies;

   public ViewHandlingStrategyManager() {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      boolean pdlDisabled = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) || webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated);
      this.strategies = pdlDisabled ? new ViewHandlingStrategy[]{new JspViewHandlingStrategy()} : new ViewHandlingStrategy[]{new FaceletViewHandlingStrategy(), new JspViewHandlingStrategy()};
   }

   public ViewHandlingStrategy getStrategy(String viewId) {
      ViewHandlingStrategy[] var2 = this.strategies;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ViewHandlingStrategy strategy = var2[var4];
         if (strategy.handlesViewId(viewId)) {
            return strategy;
         }
      }

      throw new ViewHandlingStrategyNotFoundException();
   }

   public ViewHandlingStrategy[] getViewHandlingStrategies() {
      return (ViewHandlingStrategy[])this.strategies.clone();
   }

   public synchronized void setViewHandlingStrategies(ViewHandlingStrategy[] stratagies) {
      this.strategies = (ViewHandlingStrategy[])stratagies.clone();
   }
}
