package org.apache.openjpa.util;

import java.util.Comparator;
import java.util.Map;

public interface ProxyMap extends Proxy, Map {
   Class getKeyType();

   Class getValueType();

   ProxyMap newInstance(Class var1, Class var2, Comparator var3, boolean var4, boolean var5);
}
