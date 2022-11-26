package weblogic.ejb.spi;

import java.util.Collection;
import weblogic.application.naming.ModuleRegistry;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ICompilerFactory;
import weblogic.utils.jars.VirtualJarFile;

public interface EJBC {
   void compileEJB(GenericClassLoader var1, EjbDescriptorBean var2, VirtualJarFile var3) throws ErrorCollectionException;

   void compileEJB(GenericClassLoader var1, VirtualJarFile var2, VersionHelper var3, Collection var4) throws ErrorCollectionException;

   void compileEJB(GenericClassLoader var1, EjbDescriptorBean var2, VirtualJarFile var3, ModuleValidationInfo var4, ModuleRegistry var5, boolean var6) throws ErrorCollectionException;

   void populateValidationInfo(GenericClassLoader var1, EjbDescriptorBean var2, VirtualJarFile var3, boolean var4, ModuleValidationInfo var5) throws ErrorCollectionException;

   void setCompilerFactory(ICompilerFactory var1);
}
