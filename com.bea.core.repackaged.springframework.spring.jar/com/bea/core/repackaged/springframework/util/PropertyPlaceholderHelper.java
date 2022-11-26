package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyPlaceholderHelper {
   private static final Log logger = LogFactory.getLog(PropertyPlaceholderHelper.class);
   private static final Map wellKnownSimplePrefixes = new HashMap(4);
   private final String placeholderPrefix;
   private final String placeholderSuffix;
   private final String simplePrefix;
   @Nullable
   private final String valueSeparator;
   private final boolean ignoreUnresolvablePlaceholders;

   public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix) {
      this(placeholderPrefix, placeholderSuffix, (String)null, true);
   }

   public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix, @Nullable String valueSeparator, boolean ignoreUnresolvablePlaceholders) {
      Assert.notNull(placeholderPrefix, (String)"'placeholderPrefix' must not be null");
      Assert.notNull(placeholderSuffix, (String)"'placeholderSuffix' must not be null");
      this.placeholderPrefix = placeholderPrefix;
      this.placeholderSuffix = placeholderSuffix;
      String simplePrefixForSuffix = (String)wellKnownSimplePrefixes.get(this.placeholderSuffix);
      if (simplePrefixForSuffix != null && this.placeholderPrefix.endsWith(simplePrefixForSuffix)) {
         this.simplePrefix = simplePrefixForSuffix;
      } else {
         this.simplePrefix = this.placeholderPrefix;
      }

      this.valueSeparator = valueSeparator;
      this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
   }

   public String replacePlaceholders(String value, Properties properties) {
      Assert.notNull(properties, (String)"'properties' must not be null");
      properties.getClass();
      return this.replacePlaceholders(value, properties::getProperty);
   }

   public String replacePlaceholders(String value, PlaceholderResolver placeholderResolver) {
      Assert.notNull(value, (String)"'value' must not be null");
      return this.parseStringValue(value, placeholderResolver, (Set)null);
   }

   protected String parseStringValue(String value, PlaceholderResolver placeholderResolver, @Nullable Set visitedPlaceholders) {
      int startIndex = value.indexOf(this.placeholderPrefix);
      if (startIndex == -1) {
         return value;
      } else {
         StringBuilder result = new StringBuilder(value);

         while(startIndex != -1) {
            int endIndex = this.findPlaceholderEndIndex(result, startIndex);
            if (endIndex != -1) {
               String placeholder = result.substring(startIndex + this.placeholderPrefix.length(), endIndex);
               String originalPlaceholder = placeholder;
               if (visitedPlaceholders == null) {
                  visitedPlaceholders = new HashSet(4);
               }

               if (!((Set)visitedPlaceholders).add(placeholder)) {
                  throw new IllegalArgumentException("Circular placeholder reference '" + placeholder + "' in property definitions");
               }

               placeholder = this.parseStringValue(placeholder, placeholderResolver, (Set)visitedPlaceholders);
               String propVal = placeholderResolver.resolvePlaceholder(placeholder);
               if (propVal == null && this.valueSeparator != null) {
                  int separatorIndex = placeholder.indexOf(this.valueSeparator);
                  if (separatorIndex != -1) {
                     String actualPlaceholder = placeholder.substring(0, separatorIndex);
                     String defaultValue = placeholder.substring(separatorIndex + this.valueSeparator.length());
                     propVal = placeholderResolver.resolvePlaceholder(actualPlaceholder);
                     if (propVal == null) {
                        propVal = defaultValue;
                     }
                  }
               }

               if (propVal != null) {
                  propVal = this.parseStringValue(propVal, placeholderResolver, (Set)visitedPlaceholders);
                  result.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);
                  if (logger.isTraceEnabled()) {
                     logger.trace("Resolved placeholder '" + placeholder + "'");
                  }

                  startIndex = result.indexOf(this.placeholderPrefix, startIndex + propVal.length());
               } else {
                  if (!this.ignoreUnresolvablePlaceholders) {
                     throw new IllegalArgumentException("Could not resolve placeholder '" + placeholder + "' in value \"" + value + "\"");
                  }

                  startIndex = result.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
               }

               ((Set)visitedPlaceholders).remove(originalPlaceholder);
            } else {
               startIndex = -1;
            }
         }

         return result.toString();
      }
   }

   private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
      int index = startIndex + this.placeholderPrefix.length();
      int withinNestedPlaceholder = 0;

      while(index < buf.length()) {
         if (StringUtils.substringMatch(buf, index, this.placeholderSuffix)) {
            if (withinNestedPlaceholder <= 0) {
               return index;
            }

            --withinNestedPlaceholder;
            index += this.placeholderSuffix.length();
         } else if (StringUtils.substringMatch(buf, index, this.simplePrefix)) {
            ++withinNestedPlaceholder;
            index += this.simplePrefix.length();
         } else {
            ++index;
         }
      }

      return -1;
   }

   static {
      wellKnownSimplePrefixes.put("}", "{");
      wellKnownSimplePrefixes.put("]", "[");
      wellKnownSimplePrefixes.put(")", "(");
   }

   @FunctionalInterface
   public interface PlaceholderResolver {
      @Nullable
      String resolvePlaceholder(String var1);
   }
}
