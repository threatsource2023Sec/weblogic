package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.query.option.QueryOptions;
import java.io.Closeable;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

public class CloseableRequestResources implements Closeable {
   final Collection requestResources = Collections.newSetFromMap(new IdentityHashMap());

   public void add(Closeable closeable) {
      this.requestResources.add(closeable);
   }

   public CloseableResourceGroup addGroup() {
      CloseableResourceGroup group = new CloseableResourceGroup();
      this.add(group);
      return group;
   }

   public void close() {
      Iterator iterator = this.requestResources.iterator();

      while(iterator.hasNext()) {
         Closeable closeable = (Closeable)iterator.next();
         closeQuietly(closeable);
         iterator.remove();
      }

   }

   public static CloseableRequestResources forQueryOptions(QueryOptions queryOptions) {
      CloseableRequestResources closeableRequestResources = (CloseableRequestResources)queryOptions.get(CloseableRequestResources.class);
      if (closeableRequestResources == null) {
         closeableRequestResources = new CloseableRequestResources();
         queryOptions.put(CloseableRequestResources.class, closeableRequestResources);
      }

      return closeableRequestResources;
   }

   public static void closeForQueryOptions(QueryOptions queryOptions) {
      closeQuietly((Closeable)queryOptions.get(CloseableRequestResources.class));
   }

   public static void closeQuietly(Closeable closeable) {
      if (closeable != null) {
         try {
            closeable.close();
         } catch (Exception var2) {
         }
      }

   }

   public class CloseableResourceGroup implements Closeable {
      final Set groupResources = Collections.newSetFromMap(new IdentityHashMap());

      public boolean add(Closeable closeable) {
         return this.groupResources.add(closeable);
      }

      public void close() {
         Iterator iterator = this.groupResources.iterator();

         while(iterator.hasNext()) {
            Closeable closeable = (Closeable)iterator.next();
            CloseableRequestResources.closeQuietly(closeable);
            iterator.remove();
         }

         CloseableRequestResources.this.requestResources.remove(this);
      }

      public String toString() {
         return this.groupResources.toString();
      }
   }
}
