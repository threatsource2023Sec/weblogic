package weblogic.messaging.saf.utils;

import java.io.StreamCorruptedException;

public final class SAFClientUtil {
   public static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return new StreamCorruptedException("Unsupported class version " + version + ".  Expected a value between " + minExpectedVersion + " and " + maxExpectedVersion + " inclusive." + (version > minExpectedVersion ? "  Possible attempt to access newer version then current version." : "  Possible attempt to access unsupported older version."));
   }
}
