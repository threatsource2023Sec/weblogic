package org.glassfish.grizzly.attributes;

import java.util.Set;

public interface AttributeHolder {
   Object removeAttribute(String var1);

   void setAttribute(String var1, Object var2);

   Object getAttribute(String var1);

   Object getAttribute(String var1, org.glassfish.grizzly.utils.NullaryFunction var2);

   Set getAttributeNames();

   void clear();

   void recycle();

   AttributeBuilder getAttributeBuilder();

   IndexedAttributeAccessor getIndexedAttributeAccessor();

   void copyTo(AttributeHolder var1);

   void copyFrom(AttributeHolder var1);
}
