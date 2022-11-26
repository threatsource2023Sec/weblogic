package weblogic.application;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import weblogic.application.naming.Environment;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.utils.classloaders.GenericClassLoader;

public interface ModuleExtensionContext {
   Environment getEnvironment(String var1);

   Collection getEnvironments();

   List getSources(String var1);

   Set getAnnotatedClasses(boolean var1, Class... var2) throws AnnotationProcessingException;

   GenericClassLoader getTemporaryClassLoader();

   Collection getBeanClassNames();

   ClassInfoFinder getClassInfoFinder();

   PojoEnvironmentBean getPojoEnvironmentBean();
}
