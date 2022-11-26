package org.python.icu.impl.coll;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;
import org.python.icu.impl.ICUBinary;

public final class CollationRoot {
   private static final CollationTailoring rootSingleton;
   private static final RuntimeException exception;

   public static final CollationTailoring getRoot() {
      if (exception != null) {
         throw exception;
      } else {
         return rootSingleton;
      }
   }

   public static final CollationData getData() {
      CollationTailoring root = getRoot();
      return root.data;
   }

   static final CollationSettings getSettings() {
      CollationTailoring root = getRoot();
      return (CollationSettings)root.settings.readOnly();
   }

   static {
      CollationTailoring t = null;
      RuntimeException e2 = null;

      try {
         ByteBuffer bytes = ICUBinary.getRequiredData("coll/ucadata.icu");
         CollationTailoring t2 = new CollationTailoring((SharedObject.Reference)null);
         CollationDataReader.read((CollationTailoring)null, bytes, t2);
         t = t2;
      } catch (IOException var4) {
         e2 = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt59b/coll/ucadata.icu");
      } catch (RuntimeException var5) {
         e2 = var5;
      }

      rootSingleton = t;
      exception = (RuntimeException)e2;
   }
}
