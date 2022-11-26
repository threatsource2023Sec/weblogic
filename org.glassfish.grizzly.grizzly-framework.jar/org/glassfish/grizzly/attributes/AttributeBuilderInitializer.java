package org.glassfish.grizzly.attributes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

class AttributeBuilderInitializer {
   private static final String PROP = "org.glassfish.grizzly.DEFAULT_ATTRIBUTE_BUILDER";
   private static final Logger LOGGER = Grizzly.logger(AttributeBuilderInitializer.class);

   static AttributeBuilder initBuilder() {
      String className = System.getProperty("org.glassfish.grizzly.DEFAULT_ATTRIBUTE_BUILDER");
      if (className != null) {
         try {
            Class builderClass = Class.forName(className, true, AttributeBuilder.class.getClassLoader());
            return (AttributeBuilder)builderClass.newInstance();
         } catch (Exception var2) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unable to load or create a new instance of AttributeBuilder {0}.  Cause: {1}", new Object[]{className, var2.getMessage()});
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var2.toString(), var2);
            }

            return new DefaultAttributeBuilder();
         }
      } else {
         return new DefaultAttributeBuilder();
      }
   }
}
