package org.apache.openjpa.util;

import java.util.Collection;
import java.util.Comparator;

public interface ProxyCollection extends Proxy, Collection {
   Class getElementType();

   ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4);
}
