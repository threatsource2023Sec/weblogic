package org.opensaml.core.xml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSingletonFactory implements SingletonFactory {
   private final Logger log = LoggerFactory.getLogger(AbstractSingletonFactory.class);

   public synchronized Object getInstance(Object input) {
      Object output = this.get(input);
      if (output != null) {
         this.log.trace("Input key mapped to a non-null value, returning output");
         return output;
      } else {
         this.log.trace("Input key mapped to a null value");
         this.log.trace("Creating new output instance and inserting to factory map");
         output = this.createNewInstance(input);
         if (output == null) {
            this.log.error("New output instance was not created");
            return null;
         } else {
            this.put(input, output);
            return output;
         }
      }
   }

   protected abstract Object get(Object var1);

   protected abstract void put(Object var1, Object var2);

   protected abstract Object createNewInstance(Object var1);
}
