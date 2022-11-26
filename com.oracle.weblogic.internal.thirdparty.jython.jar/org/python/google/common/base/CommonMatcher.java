package org.python.google.common.base;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class CommonMatcher {
   abstract boolean matches();

   abstract boolean find();

   abstract boolean find(int var1);

   abstract String replaceAll(String var1);

   abstract int end();

   abstract int start();
}
