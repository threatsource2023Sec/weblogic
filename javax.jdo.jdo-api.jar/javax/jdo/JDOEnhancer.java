package javax.jdo;

import java.lang.instrument.ClassFileTransformer;
import java.util.Properties;
import javax.jdo.metadata.JDOMetadata;

public interface JDOEnhancer extends ClassFileTransformer {
   Properties getProperties();

   JDOEnhancer setVerbose(boolean var1);

   JDOEnhancer setOutputDirectory(String var1);

   JDOEnhancer setClassLoader(ClassLoader var1);

   JDOEnhancer addPersistenceUnit(String var1);

   JDOEnhancer addClass(String var1, byte[] var2);

   JDOEnhancer addClasses(String... var1);

   JDOEnhancer addFiles(String... var1);

   JDOEnhancer addJar(String var1);

   int enhance();

   int validate();

   byte[] getEnhancedBytes(String var1);

   void registerMetadata(JDOMetadata var1);

   JDOMetadata newMetadata();
}
