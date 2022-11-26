package weblogic.rmi.provider;

import java.util.Arrays;
import java.util.Iterator;
import weblogic.rmi.spi.ServiceContext;

public class BasicServiceContextList implements Iterator {
   public static final int MAX_CONTEXTS = 32;
   private ServiceContext[] contexts = new ServiceContext[32];
   private int numContexts = 0;
   private int numUserContexts = 0;
   private int iteratorIndex = 0;
   private int pos = 0;

   public final int size() {
      return this.numContexts;
   }

   public final int sizeUser() {
      return this.numUserContexts;
   }

   public final ServiceContext getContext(int id) {
      if (id > 32) {
         throw new IllegalArgumentException("Invalid context id: " + id);
      } else {
         return this.contexts[id];
      }
   }

   public final Object getContextData(int id) {
      ServiceContext sc = this.getContext(id);
      return sc == null ? null : sc.getContextData();
   }

   public final Iterator iterator() {
      this.iteratorIndex = 0;
      this.pos = 0;
      return this;
   }

   public final boolean hasNext() {
      return this.iteratorIndex < this.numContexts;
   }

   public final Object next() {
      Object curr = null;

      do {
         if ((curr = this.contexts[this.pos++]) != null) {
            ++this.iteratorIndex;
            return curr;
         }
      } while(this.pos != 32);

      throw new IllegalStateException("Bad iterator state");
   }

   public final void remove() {
      this.contexts[this.iteratorIndex] = null;
   }

   public final void setContext(ServiceContext sc) {
      int id = 0;
      if (sc != null && (id = sc.getContextId()) <= 32) {
         if (this.contexts[id] != null) {
            throw new IllegalStateException("Context set twice for id: " + id);
         } else {
            this.contexts[id] = sc;
            ++this.numContexts;
            if (sc.isUser()) {
               ++this.numUserContexts;
            }

         }
      } else {
         throw new IllegalArgumentException("Invalid context id: " + id);
      }
   }

   public final void reset() {
      Arrays.fill(this.contexts, (Object)null);
      this.numContexts = 0;
      this.pos = 0;
      this.numUserContexts = 0;
   }

   public final void setContextData(int id, Object obj) {
      if (obj != null) {
         this.setContext(new BasicServiceContext(id, obj));
      }

   }

   public final void copyContexts(BasicServiceContextList other) {
      System.arraycopy(other.contexts, 0, this.contexts, 0, 32);
      this.numContexts = other.numContexts;
      this.numUserContexts = other.numUserContexts;
      this.iteratorIndex = 0;
      this.pos = 0;
   }
}
