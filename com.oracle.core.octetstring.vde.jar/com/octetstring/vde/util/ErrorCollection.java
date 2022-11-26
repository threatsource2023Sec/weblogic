package com.octetstring.vde.util;

import java.util.Collection;

public interface ErrorCollection {
   void add(Throwable var1);

   Collection getExceptions();

   boolean isEmpty();
}
