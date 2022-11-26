package com.oracle.injection;

import java.net.URL;
import java.util.Collection;
import javax.naming.Context;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;

public interface InjectionArchive {
   ClassLoader getClassLoader();

   Collection getEjbDescriptors();

   Collection getBeanClassNames();

   URL getResource(String var1);

   Object getCustomContext();

   InjectionArchiveType getArchiveType();

   String getArchiveName();

   String getClassPathArchiveName();

   Collection getEmbeddedArchives();

   Collection getComponentClassNamesForProcessInjectionTarget();

   PojoEnvironmentBean getPojoEnvironmentBean();

   Context getRootContext(String var1);
}
