package org.apache.velocity.runtime.resource;

import java.util.Iterator;
import org.apache.velocity.runtime.RuntimeServices;

public interface ResourceCache {
   void initialize(RuntimeServices var1);

   Resource get(Object var1);

   Resource put(Object var1, Resource var2);

   Resource remove(Object var1);

   Iterator enumerateKeys();
}
