package org.opensaml.core.xml.util;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWrappedSingletonFactory extends AbstractSingletonFactory {
   private final Logger log;
   private WeakHashMap map;
   private HashSet outputSet;
   private boolean explicitRelease;

   public AbstractWrappedSingletonFactory() {
      this(false);
   }

   public AbstractWrappedSingletonFactory(boolean requireExplicitRelease) {
      this.log = LoggerFactory.getLogger(AbstractWrappedSingletonFactory.class);
      this.map = new WeakHashMap();
      this.explicitRelease = requireExplicitRelease;
      this.outputSet = new HashSet();
   }

   public synchronized Object getInstance(Object input) {
      Object output = super.getInstance(input);
      if (this.explicitRelease && output != null) {
         this.log.trace("Explicit release was indicated, registering output instance to inhibit garbage collection");
         this.register(output);
      }

      return output;
   }

   public boolean isRequireExplicitRelease() {
      return this.explicitRelease;
   }

   public synchronized void release(Object output) {
      this.outputSet.remove(output);
   }

   public synchronized void releaseAll() {
      this.outputSet.clear();
   }

   protected synchronized void register(Object output) {
      this.outputSet.add(output);
   }

   protected synchronized Object get(Object input) {
      WeakReference outputRef = (WeakReference)this.map.get(input);
      if (outputRef != null) {
         this.log.trace("Input key mapped to a non-null WeakReference");
         if (outputRef.get() != null) {
            this.log.trace("WeakReference referent was non-null, returning referent");
            return outputRef.get();
         }

         this.log.trace("WeakReference referent was null, removing WeakReference entry from map");
         this.map.remove(input);
      }

      return null;
   }

   protected synchronized void put(Object input, Object output) {
      this.map.put(input, new WeakReference(output));
   }
}
