package net.shibboleth.utilities.java.support.primitive;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DeprecationSupport {
   @Nonnull
   @NotEmpty
   public static final String LOG_CATEGORY = "DEPRECATED";
   @Nonnull
   private static final Logger LOG = LoggerFactory.getLogger("DEPRECATED");
   @Nonnull
   @NonnullElements
   private static final Set WARNED_SET = new HashSet();

   private DeprecationSupport() {
   }

   public static void warn(@Nonnull ObjectType type, @Nonnull @NotEmpty String name, @Nullable String context, @Nullable String replacement) {
      if (context != null && replacement != null) {
         LOG.warn("{} '{}', ({}): This will be removed in the next major version of this software; replacement is {}", new Object[]{type, name, context, replacement});
      } else if (context != null) {
         LOG.warn("{} '{}', ({}): This will be removed in the next major version of this software", new Object[]{type, name, context});
      } else if (replacement != null) {
         LOG.warn("{} '{}': This will be removed in the next major version of this software; replacement is {}", new Object[]{type, name, replacement});
      } else {
         LOG.warn("{} '{}': This will be removed in the next major version of this software.", type, name);
      }

   }

   public static void warnOnce(@Nonnull ObjectType type, @Nonnull @NotEmpty String name, @Nullable String context, @Nullable String replacement) {
      synchronized(WARNED_SET) {
         if (!WARNED_SET.add(type.toString() + ':' + name)) {
            return;
         }
      }

      warn(type, name, context, replacement);
   }

   public static void clearWarningState() {
      synchronized(WARNED_SET) {
         WARNED_SET.clear();
      }
   }

   public static enum ObjectType {
      CLASS("Java class"),
      METHOD("Java class method"),
      PROPERTY("property"),
      BEAN("Spring bean"),
      NAMESPACE("XML Namespace"),
      XSITYPE("xsi:type"),
      ELEMENT("XML Element"),
      ATTRIBUTE("XML Attribute"),
      WEBFLOW("Spring WebFlow"),
      ACTION("Spring WebFlow action"),
      CONFIGURATION("configuration");

      @Nonnull
      @NotEmpty
      private final String text;

      private ObjectType(@Nonnull @NotEmpty String s) {
         this.text = s;
      }

      public String toString() {
         return this.text;
      }
   }
}
