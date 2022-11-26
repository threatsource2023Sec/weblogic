package org.python.apache.commons.compress.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class ServiceLoaderIterator implements Iterator {
   private Object nextServiceLoader;
   private final Class service;
   private final Iterator serviceLoaderIterator;

   public ServiceLoaderIterator(Class service) {
      this(service, ClassLoader.getSystemClassLoader());
   }

   public ServiceLoaderIterator(Class service, ClassLoader classLoader) {
      this.service = service;
      ServiceLoader serviceLoader = ServiceLoader.load(service, classLoader);
      this.serviceLoaderIterator = serviceLoader.iterator();
      this.nextServiceLoader = null;
   }

   private boolean getNextServiceLoader() {
      while(this.nextServiceLoader == null) {
         try {
            if (!this.serviceLoaderIterator.hasNext()) {
               return false;
            }

            this.nextServiceLoader = this.serviceLoaderIterator.next();
         } catch (ServiceConfigurationError var2) {
            if (!(var2.getCause() instanceof SecurityException)) {
               throw var2;
            }
         }
      }

      return true;
   }

   public boolean hasNext() {
      return this.getNextServiceLoader();
   }

   public Object next() {
      if (!this.getNextServiceLoader()) {
         throw new NoSuchElementException("No more elements for service " + this.service.getName());
      } else {
         Object tempNext = this.nextServiceLoader;
         this.nextServiceLoader = null;
         return tempNext;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("service=" + this.service.getName());
   }
}
