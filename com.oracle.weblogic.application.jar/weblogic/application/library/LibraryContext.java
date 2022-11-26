package weblogic.application.library;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.utils.classloaders.ClassFinder;

public interface LibraryContext {
   void registerLink(File var1) throws IOException;

   void registerLink(String var1, File var2) throws IOException;

   ApplicationBean getApplicationDD();

   ApplicationDescriptor getApplicationDescriptor();

   void addClassFinder(ClassFinder var1);

   void addInstanceAppLibClassFinder(ClassFinder var1);

   void addSharedAppLibClassFinder(ClassFinder var1);

   void notifyDescriptorUpdate() throws LoggableLibraryProcessingException;

   String getRefappName();

   String getRefappUri();

   Map getContextRootOverrideMap();

   void setContextRootOverrideMap(Map var1);

   void addLibraryClassInfoFinderFirst(ClassInfoFinder var1);
}
