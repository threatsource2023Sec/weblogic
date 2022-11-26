package weblogic.utils.classloaders;

import java.util.Enumeration;
import weblogic.utils.StringUtils;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.SingleItemEnumeration;

public abstract class AbstractClassFinder implements ClassFinder {
   public static final char[] CLASS_ARRAY = new char[]{'.', 'c', 'l', 'a', 's', 's'};

   public Enumeration getSources(String name) {
      Source s = this.getSource(name);
      return (Enumeration)(s == null ? new EmptyEnumerator() : new SingleItemEnumeration(s));
   }

   public Source getClassSource(String name) {
      int nameLength = name.length();
      char[] newChars = new char[nameLength + CLASS_ARRAY.length];
      name.getChars(0, nameLength, newChars, 0);

      for(int i = 0; i < nameLength; ++i) {
         if (newChars[i] == '.') {
            newChars[i] = '/';
         }
      }

      System.arraycopy(CLASS_ARRAY, 0, newChars, nameLength, CLASS_ARRAY.length);
      return this.getSource(StringUtils.getString(newChars, 0, newChars.length));
   }

   public ClassFinder getManifestFinder() {
      return null;
   }

   public Enumeration entries() {
      return EmptyEnumerator.getEmptyEnumerator();
   }

   public void freeze() {
   }

   public void close() {
   }
}
