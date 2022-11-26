package weblogic.utils.classloaders;

import weblogic.utils.StringUtils;

public final class Annotation {
   public static final char SEPARATOR_CHAR = '@';
   private static final String implVersionString = "#@";
   private static final String implVersionReplacer = "##";
   private final String annotation;
   private final String application;
   private final String module;
   private String description;

   public Annotation(String appName) {
      if (appName != null) {
         appName = appName.replaceFirst("#@", "##");
      }

      if (StringUtils.isEmptyString(appName)) {
         this.annotation = "";
         this.application = null;
         this.module = null;
      } else if (appName.indexOf(64) >= 0) {
         this.annotation = appName;
         String[] s = StringUtils.split(appName, '@');
         this.application = s[0];
         this.module = s[1];
      } else {
         this.annotation = appName + '@';
         this.application = appName;
         this.module = null;
      }

   }

   public Annotation(String appName, String moduleName) {
      if (appName == null) {
         throw new IllegalArgumentException("Application name is null");
      } else {
         appName = appName.replaceFirst("#@", "##");
         if (moduleName == null) {
            moduleName = "";
         }

         this.annotation = appName + '@' + moduleName;
         this.application = appName;
         this.module = moduleName;
      }
   }

   public static Annotation createNonAppAnnotation(String description) {
      Annotation nonAppAnnotation = new Annotation((String)null);
      nonAppAnnotation.description = description;
      return nonAppAnnotation;
   }

   public String getAnnotationString() {
      return this.annotation;
   }

   public String toString() {
      return this.annotation;
   }

   public String getApplicationName() {
      return this.application;
   }

   public String getModuleName() {
      return this.module;
   }

   public int hashCode() {
      return this.annotation.hashCode();
   }

   public boolean equals(Object o) {
      if (o instanceof Annotation) {
         String s1 = ((Annotation)o).getAnnotationString();
         String s2 = this.getAnnotationString();
         if (s1.equals(s2)) {
            return true;
         }
      }

      return false;
   }

   public String getDescription() {
      return !StringUtils.isEmptyString(this.annotation) ? this.annotation : this.description;
   }
}
