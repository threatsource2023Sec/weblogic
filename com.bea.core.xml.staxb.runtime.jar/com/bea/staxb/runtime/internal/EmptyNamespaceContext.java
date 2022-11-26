package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.collections.EmptyIterator;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

final class EmptyNamespaceContext implements NamespaceContext {
   private static final NamespaceContext INSTANCE = new EmptyNamespaceContext();

   public static NamespaceContext getInstance() {
      return INSTANCE;
   }

   private EmptyNamespaceContext() {
   }

   public String getNamespaceURI(String s) {
      return null;
   }

   public String getPrefix(String s) {
      return null;
   }

   public Iterator getPrefixes(String s) {
      return EmptyIterator.getInstance();
   }
}
