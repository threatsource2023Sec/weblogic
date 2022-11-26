package org.python.core.util;

import jnr.posix.util.Platform;

public class PlatformUtil {
   public static boolean isCaseInsensitive() {
      return Platform.IS_MAC || Platform.IS_WINDOWS;
   }
}
