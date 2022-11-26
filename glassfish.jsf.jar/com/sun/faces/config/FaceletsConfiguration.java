package com.sun.faces.config;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;

public class FaceletsConfiguration {
   public static final String FACELETS_CONFIGURATION_ATTRIBUTE_NAME = "com.sun.faces.config.FaceletsConfiguration";
   private static final String ESCAPE_INLINE_TEXT_ATTRIBUTE_NAME = "com.sun.faces.config.EscapeInlineText";
   private static Pattern EXTENSION_PATTERN = Pattern.compile("\\.[^/]+$");
   private WebConfiguration config;
   private Map faceletsProcessingMappings;

   public FaceletsConfiguration(WebConfiguration config) {
      this.config = config;
      this.faceletsProcessingMappings = config.getFacesConfigOptionValue(WebConfiguration.WebContextInitParameter.FaceletsProcessingFileExtensionProcessAs);
   }

   public boolean isProcessCurrentDocumentAsFaceletsXhtml(String alias) {
      boolean currentModeIsXhtml = true;
      String extension = getExtension(alias);

      assert null != this.faceletsProcessingMappings;

      if (this.faceletsProcessingMappings.containsKey(extension)) {
         String value = (String)this.faceletsProcessingMappings.get(extension);
         currentModeIsXhtml = value.equals("xhtml");
      }

      return currentModeIsXhtml;
   }

   public boolean isOutputHtml5Doctype(String alias) {
      boolean currentModeIsHtml5 = true;
      String extension = getExtension(alias);

      assert null != this.faceletsProcessingMappings;

      if (this.faceletsProcessingMappings.containsKey(extension)) {
         String value = (String)this.faceletsProcessingMappings.get(extension);
         currentModeIsHtml5 = value.equals("html5");
      }

      return currentModeIsHtml5;
   }

   public boolean isConsumeComments(String alias) {
      boolean consumeComments = false;
      String extension = getExtension(alias);

      assert null != this.faceletsProcessingMappings;

      if (this.faceletsProcessingMappings.containsKey(extension)) {
         String value = (String)this.faceletsProcessingMappings.get(extension);
         consumeComments = value.equals("xml") || value.equals("jspx");
      }

      return consumeComments;
   }

   public boolean isConsumeCDATA(String alias) {
      boolean consumeCDATA = false;
      String extension = getExtension(alias);

      assert null != this.faceletsProcessingMappings;

      if (this.faceletsProcessingMappings.containsKey(extension)) {
         String value = (String)this.faceletsProcessingMappings.get(extension);
         consumeCDATA = value.equals("jspx") || value.equals("xml");
      }

      return consumeCDATA;
   }

   public boolean isEscapeInlineText(FacesContext context) {
      Boolean result = Boolean.TRUE;
      result = (Boolean)context.getAttributes().get("com.sun.faces.config.EscapeInlineText");
      if (null == result) {
         String extension = getExtension(context.getViewRoot().getViewId());

         assert null != this.faceletsProcessingMappings;

         if (!this.faceletsProcessingMappings.containsKey(extension)) {
            result = Boolean.TRUE;
         } else {
            String value = (String)this.faceletsProcessingMappings.get(extension);
            result = value.equals("xml") || value.equals("xhtml");
         }

         context.getAttributes().put("com.sun.faces.config.EscapeInlineText", result);
      }

      return result;
   }

   public static FaceletsConfiguration getInstance(FacesContext context) {
      FaceletsConfiguration result = null;
      Map attrs = context.getAttributes();
      result = (FaceletsConfiguration)attrs.get("com.sun.faces.config.FaceletsConfiguration");
      if (null == result) {
         WebConfiguration config = WebConfiguration.getInstance(context.getExternalContext());
         result = config.getFaceletsConfiguration();
         attrs.put("com.sun.faces.config.FaceletsConfiguration", result);
      }

      return result;
   }

   public static FaceletsConfiguration getInstance() {
      FacesContext context = FacesContext.getCurrentInstance();
      return getInstance(context);
   }

   private static String getExtension(String alias) {
      String ext = null;
      if (alias != null) {
         Matcher matcher = EXTENSION_PATTERN.matcher(alias);
         if (matcher.find()) {
            ext = alias.substring(matcher.start(), matcher.end());
         }
      }

      return ext == null ? "xhtml" : ext;
   }
}
