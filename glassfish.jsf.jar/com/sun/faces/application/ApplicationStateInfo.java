package com.sun.faces.application;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ApplicationStateInfo {
   private boolean partialStateSaving;
   private Set fullStateViewIds;

   public ApplicationStateInfo() {
      WebConfiguration config = WebConfiguration.getInstance();
      this.partialStateSaving = config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.PartialStateSaving);
      if (this.partialStateSaving) {
         String[] viewIds = config.getOptionValue(WebConfiguration.WebContextInitParameter.FullStateSavingViewIds, ",");
         this.fullStateViewIds = new HashSet(viewIds.length, 1.0F);
         this.fullStateViewIds.addAll(Arrays.asList(viewIds));
      }

   }

   public boolean usePartialStateSaving(String viewId) {
      Util.notNullViewId(viewId);
      return this.partialStateSaving && !this.fullStateViewIds.contains(viewId);
   }
}
