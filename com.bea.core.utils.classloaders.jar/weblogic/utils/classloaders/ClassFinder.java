package weblogic.utils.classloaders;

import java.util.Enumeration;

public interface ClassFinder {
   Source getSource(String var1);

   Enumeration getSources(String var1);

   Source getClassSource(String var1);

   String getClassPath();

   ClassFinder getManifestFinder();

   Enumeration entries();

   void freeze();

   void close();
}
