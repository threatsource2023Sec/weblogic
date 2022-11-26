package weblogic.aspects;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import weblogic.utils.classloaders.GenericClassLoader;

public interface AspectSystem {
   String WEBLOGIC_ASPECTS_PROPERTY = "weblogic.aspects";
   String ASPECTS_PROPERTY_FILE = "wlaspect.properties";
   String ASPECT_SYSTEM = "aspect.system";
   String ASPECT_ENABLE = "aspect.enable";
   String ASPECT_INCLUDE = "aspect.include";
   String ASPECT_EXCLUDE = "aspect.exclude";
   String ASPECTS_DEBUG = "weblogic.aspects.debug";

   void init(GenericClassLoader var1, Properties var2);

   byte[] weaveClass(String var1, byte[] var2) throws IOException;

   Map getAllSources();
}
