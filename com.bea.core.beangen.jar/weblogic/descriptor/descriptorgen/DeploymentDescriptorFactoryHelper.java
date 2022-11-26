package weblogic.descriptor.descriptorgen;

import java.util.HashMap;
import weblogic.utils.codegen.AttributeBinderFactory;
import weblogic.utils.codegen.AttributeBinderFactoryHelper;

public class DeploymentDescriptorFactoryHelper implements AttributeBinderFactoryHelper {
   private static String IMPL_SUFFIX = "Impl";
   private static String[][] elementList = new String[][]{{"application", "weblogic/j2ee/descriptor/j2ee/application/Application"}, {"connector", "weblogic/j2ee/descriptor/j2ee/application/ConnectorModule"}, {"ejb", "weblogic/j2ee/descriptor/j2ee/application/EJBModule"}, {"java", "weblogic/j2ee/descriptor/j2ee/application/JavaModule"}, {"module", "weblogic/j2ee/descriptor/j2ee/application/Module"}, {"security-role", "weblogic/j2ee/descriptor/j2ee/application/SecurityRole"}, {"web", "weblogic/j2ee/descriptor/j2ee/application/WebModule"}, {"CharsetMapping", "weblogic/j2ee/descriptor/web/wl/CharsetMapping"}, {"CharsetParams", "weblogic/j2ee/descriptor/web/wl/CharsetParams"}, {"ContainerDescriptor", "weblogic/j2ee/descriptor/web/wl/ContainerDescriptor"}, {"DestroyAs", "weblogic/j2ee/descriptor/web/wl/DestroyAs"}, {"EjbReferenceDescription", "weblogic/j2ee/descriptor/web/wl/EjbReferenceDescription"}, {"InitAs", "weblogic/j2ee/descriptor/web/wl/InitAs"}, {"InputCharset", "weblogic/j2ee/descriptor/web/wl/InputCharset"}, {"JspParam", "weblogic/j2ee/descriptor/web/wl/JspParam"}, {"Preprocessor", "weblogic/j2ee/descriptor/web/wl/Preprocessor"}, {"PreprocessorMapping", "weblogic/j2ee/descriptor/web/wl/PreprocessorMapping"}, {"ResourceDescription", "weblogic/j2ee/descriptor/web/wl/ResourceDescription"}, {"ResourceEnvDescription", "weblogic/j2ee/descriptor/web/wl/ResourceEnvDescription"}, {"RunAsRoleAssignment", "weblogic/j2ee/descriptor/web/wl/RunAsRoleAssignment"}, {"SecurityPermission", "weblogic/j2ee/descriptor/web/wl/SecurityPermission"}, {"SecurityRoleAssignment", "weblogic/j2ee/descriptor/web/wl/SecurityRoleAssignment"}, {"ServletDescriptor", "weblogic/j2ee/descriptor/web/wl/ServletDescriptor"}, {"SessionParam", "weblogic/j2ee/descriptor/web/wl/SessionParam"}, {"VirtualDirectoryMapping", "weblogic/j2ee/descriptor/web/wl/VirtualDirectoryMapping"}, {"WLWebApp", "weblogic/j2ee/descriptor/web/wl/WLWebApp"}};
   private HashMap elementNameMap = new HashMap();

   public DeploymentDescriptorFactoryHelper() {
      for(int i = 0; i < elementList.length; ++i) {
         this.elementNameMap.put(elementList[i][0], elementList[i][1]);
      }

   }

   public AttributeBinderFactory getAttributeBinderFactory(String elementName) throws ClassNotFoundException {
      String rootName = (String)this.elementNameMap.get(elementName);
      if (rootName == null) {
         return null;
      } else {
         StringBuffer factoryName = (new StringBuffer(rootName)).append(IMPL_SUFFIX);
         Class c = this.getClassLoader().loadClass(factoryName.toString());

         try {
            return (AttributeBinderFactory)c.newInstance();
         } catch (InstantiationException var6) {
            throw new AssertionError(var6);
         } catch (IllegalAccessException var7) {
            throw new AssertionError(var7);
         }
      }
   }

   private ClassLoader getClassLoader() {
      ClassLoader cl = this.getClass().getClassLoader();
      return cl == null ? ClassLoader.getSystemClassLoader() : cl;
   }
}
