package org.python.apache.xerces.stax;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.namespace.NamespaceContext;

public final class DefaultNamespaceContext implements NamespaceContext {
   private static final DefaultNamespaceContext DEFAULT_NAMESPACE_CONTEXT_INSTANCE = new DefaultNamespaceContext();

   private DefaultNamespaceContext() {
   }

   public static DefaultNamespaceContext getInstance() {
      return DEFAULT_NAMESPACE_CONTEXT_INSTANCE;
   }

   public String getNamespaceURI(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Prefix cannot be null.");
      } else if ("xml".equals(var1)) {
         return "http://www.w3.org/XML/1998/namespace";
      } else {
         return "xmlns".equals(var1) ? "http://www.w3.org/2000/xmlns/" : "";
      }
   }

   public String getPrefix(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Namespace URI cannot be null.");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(var1)) {
         return "xml";
      } else {
         return "http://www.w3.org/2000/xmlns/".equals(var1) ? "xmlns" : null;
      }
   }

   public Iterator getPrefixes(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Namespace URI cannot be null.");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(var1)) {
         return new Iterator() {
            boolean more = true;

            public boolean hasNext() {
               return this.more;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.more = false;
                  return "xml";
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      } else {
         return "http://www.w3.org/2000/xmlns/".equals(var1) ? new Iterator() {
            boolean more = true;

            public boolean hasNext() {
               return this.more;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.more = false;
                  return "xmlns";
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         } : Collections.EMPTY_LIST.iterator();
      }
   }
}
