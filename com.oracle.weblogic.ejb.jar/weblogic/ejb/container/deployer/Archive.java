package weblogic.ejb.container.deployer;

import java.io.IOException;
import java.util.Set;
import weblogic.application.AnnotationProcessingException;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public interface Archive {
   String getStandardDescriptorRoot();

   Source getSource(String var1) throws IOException;

   Set getAnnotatedClasses(Class... var1) throws AnnotationProcessingException;

   GenericClassLoader getTemporaryClassLoader();

   boolean isCdiEnabled();
}
