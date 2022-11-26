package org.jboss.weld.metadata;

import java.security.AccessController;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jboss.weld.bootstrap.spi.ClassAvailableActivation;
import org.jboss.weld.bootstrap.spi.Filter;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.bootstrap.spi.SystemPropertyActivation;
import org.jboss.weld.bootstrap.spi.WeldFilter;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.security.GetSystemPropertyAction;

public class FilterPredicate implements Predicate {
   private final boolean active;
   private final Matcher matcher;

   public FilterPredicate(Metadata filter, ResourceLoader resourceLoader) {
      boolean active = true;
      Iterator var4;
      Metadata systemPropertyActivation;
      String propertyName;
      boolean inverted;
      if (((Filter)filter.getValue()).getClassAvailableActivations() != null) {
         for(var4 = ((Filter)filter.getValue()).getClassAvailableActivations().iterator(); var4.hasNext(); active = active && isClassAvailable(propertyName, resourceLoader, inverted)) {
            systemPropertyActivation = (Metadata)var4.next();
            if (systemPropertyActivation.getValue() == null) {
               throw new IllegalStateException("Class available activation metadata not available at " + systemPropertyActivation);
            }

            propertyName = ((ClassAvailableActivation)systemPropertyActivation.getValue()).getClassName();
            if (propertyName == null) {
               throw new IllegalStateException("Must specify class name at " + systemPropertyActivation);
            }

            inverted = isInverted(propertyName) || ((ClassAvailableActivation)systemPropertyActivation.getValue()).isInverted();
            if (inverted) {
               propertyName = removeInversion(propertyName);
            }
         }
      }

      if (((Filter)filter.getValue()).getSystemPropertyActivations() != null) {
         var4 = ((Filter)filter.getValue()).getSystemPropertyActivations().iterator();

         while(var4.hasNext()) {
            systemPropertyActivation = (Metadata)var4.next();
            if (systemPropertyActivation.getValue() == null) {
               throw new IllegalStateException("System property activation metadata not available at " + systemPropertyActivation);
            }

            propertyName = ((SystemPropertyActivation)systemPropertyActivation.getValue()).getName();
            String requiredPropertyValue = ((SystemPropertyActivation)systemPropertyActivation.getValue()).getValue();
            if (propertyName == null) {
               throw new IllegalStateException("Must specify system property name at " + systemPropertyActivation);
            }

            boolean propertyNameInverted = isInverted(propertyName);
            if (propertyNameInverted && requiredPropertyValue != null) {
               throw new IllegalStateException("Cannot invert property name and specify property value at " + systemPropertyActivation);
            }

            if (propertyNameInverted) {
               propertyName = removeInversion(propertyName);
            }

            String actualPropertyValue = (String)AccessController.doPrivileged(new GetSystemPropertyAction(propertyName));
            if (requiredPropertyValue == null) {
               active = active && isNotNull(actualPropertyValue, propertyNameInverted);
            } else {
               boolean requiredPropertyValueInverted = isInverted(requiredPropertyValue);
               if (requiredPropertyValueInverted) {
                  requiredPropertyValue = removeInversion(requiredPropertyValue);
               }

               active = active && isEqual(requiredPropertyValue, actualPropertyValue, requiredPropertyValueInverted);
            }
         }
      }

      this.active = active;
      if (filter.getValue() instanceof WeldFilter) {
         WeldFilter weldFilter = (WeldFilter)filter.getValue();
         if (weldFilter.getName() != null && weldFilter.getPattern() != null || weldFilter.getName() == null && weldFilter.getPattern() == null) {
            throw new IllegalStateException("Cannot specify both a pattern and a name at " + filter);
         }

         if (weldFilter.getPattern() != null) {
            this.matcher = new PatternMatcher(filter, weldFilter.getPattern());
         } else {
            this.matcher = new AntSelectorMatcher(weldFilter.getName());
         }
      } else {
         if (((Filter)filter.getValue()).getName() == null) {
            throw new IllegalStateException("Name must be specified at " + filter);
         }

         String name = ((Filter)filter.getValue()).getName();
         String suffixDotDoubleStar = ".**";
         propertyName = ".*";
         if (name.endsWith(suffixDotDoubleStar)) {
            this.matcher = new PrefixMatcher(name.substring(0, name.length() - suffixDotDoubleStar.length()), filter);
         } else if (name.endsWith(propertyName)) {
            this.matcher = new PackageMatcher(name.substring(0, name.length() - propertyName.length()), filter);
         } else {
            this.matcher = new FullyQualifierClassNameMatcher(name, filter);
         }
      }

   }

   public boolean test(String className) {
      return this.active ? this.matcher.matches(className) : false;
   }

   private static boolean isClassAvailable(String className, ResourceLoader resourceLoader, boolean invert) {
      if (invert) {
         return !isClassAvailable(className, resourceLoader);
      } else {
         return isClassAvailable(className, resourceLoader);
      }
   }

   private static boolean isClassAvailable(String className, ResourceLoader resourceLoader) {
      try {
         resourceLoader.classForName(className);
         return true;
      } catch (ResourceLoadingException var3) {
         return false;
      }
   }

   private static boolean isNotNull(String string, boolean invert) {
      if (invert) {
         return string == null;
      } else {
         return string != null;
      }
   }

   private static boolean isEqual(String string1, String string2, boolean invert) {
      if (invert) {
         return !string1.equals(string2);
      } else {
         return string1.equals(string2);
      }
   }

   private static boolean isInverted(String string) {
      return string.startsWith("!");
   }

   private static String removeInversion(String string) {
      return !string.startsWith("!") ? string : string.substring(1);
   }

   private static class PackageMatcher extends CDI11Matcher {
      private PackageMatcher(String pkg, Metadata filter) {
         super(pkg, filter, null);
      }

      public boolean matches(String input) {
         if (input == null) {
            return false;
         } else {
            int lastDot = input.lastIndexOf(46);
            return lastDot == -1 ? false : this.expression.equals(input.substring(0, lastDot));
         }
      }

      // $FF: synthetic method
      PackageMatcher(String x0, Metadata x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class PrefixMatcher extends CDI11Matcher {
      private PrefixMatcher(String prefix, Metadata filter) {
         super(prefix, filter, null);
      }

      public boolean matches(String input) {
         return input != null && input.startsWith(this.expression);
      }

      // $FF: synthetic method
      PrefixMatcher(String x0, Metadata x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class FullyQualifierClassNameMatcher extends CDI11Matcher {
      private FullyQualifierClassNameMatcher(String fqcn, Metadata filter) {
         super(fqcn, filter, null);
      }

      public boolean matches(String input) {
         return this.expression.equals(input);
      }

      // $FF: synthetic method
      FullyQualifierClassNameMatcher(String x0, Metadata x1, Object x2) {
         this(x0, x1);
      }
   }

   private abstract static class CDI11Matcher implements Matcher {
      private static final Pattern CDI11_EXCLUDE_PATTERN = Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");
      protected final String expression;

      private CDI11Matcher(String expression, Metadata filter) {
         this.expression = expression;
         if (!CDI11_EXCLUDE_PATTERN.matcher(expression).matches()) {
            throw new IllegalArgumentException("Invalid expression " + filter);
         }
      }

      // $FF: synthetic method
      CDI11Matcher(String x0, Metadata x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class AntSelectorMatcher implements Matcher {
      private final String name;

      private AntSelectorMatcher(String name) {
         this.name = name;
      }

      public boolean matches(String input) {
         return Selectors.matchPath(this.name, input);
      }

      // $FF: synthetic method
      AntSelectorMatcher(String x0, Object x1) {
         this(x0);
      }
   }

   private static class PatternMatcher implements Matcher {
      private final Pattern pattern;

      private PatternMatcher(Metadata filter, String pattern) {
         try {
            this.pattern = Pattern.compile(pattern);
         } catch (PatternSyntaxException var4) {
            throw new IllegalStateException("Error parsing pattern at " + filter, var4);
         }
      }

      public boolean matches(String input) {
         return this.pattern.matcher(input).matches();
      }

      // $FF: synthetic method
      PatternMatcher(Metadata x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   private interface Matcher {
      boolean matches(String var1);
   }
}
