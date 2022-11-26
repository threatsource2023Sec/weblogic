package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;

public class WebFaceletTaglibResourceProvider extends BaseWebConfigResourceProvider {
   private static final String SEPARATOR = ";";
   private static final String[] EXCLUDES = new String[0];

   protected WebConfiguration.WebContextInitParameter getParameter() {
      return WebConfiguration.WebContextInitParameter.FaceletsLibraries;
   }

   protected String[] getExcludedResources() {
      return EXCLUDES;
   }

   protected String getSeparatorRegex() {
      return ";";
   }
}
