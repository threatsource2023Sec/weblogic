package weblogic.apache.xerces.xs;

import java.util.Map;

public interface XSNamedMap extends Map {
   int getLength();

   XSObject item(int var1);

   XSObject itemByName(String var1, String var2);
}
