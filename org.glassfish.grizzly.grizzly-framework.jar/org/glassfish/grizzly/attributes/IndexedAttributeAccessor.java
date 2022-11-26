package org.glassfish.grizzly.attributes;

public interface IndexedAttributeAccessor {
   Object getAttribute(int var1);

   Object getAttribute(int var1, org.glassfish.grizzly.utils.NullaryFunction var2);

   void setAttribute(int var1, Object var2);

   Object removeAttribute(int var1);
}
