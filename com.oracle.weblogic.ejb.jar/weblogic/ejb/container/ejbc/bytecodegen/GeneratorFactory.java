package weblogic.ejb.container.ejbc.bytecodegen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.Remote;
import java.util.Iterator;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.kernel.KernelStatus;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public final class GeneratorFactory {
   private GeneratorFactory() {
   }

   public static void generate(MessageDrivenBeanInfo mdbi, NamingConvention nc, String destDir) throws EJBCException {
      if (!mdbi.getIsJMSBased()) {
         GenericClassLoader gcl = (GenericClassLoader)mdbi.getModuleClassLoader();
         if (mdbi.exposesNoInterfaceClientView()) {
            MessageDrivenBeanImplGenerator generator = new MessageDrivenBeanImplGenerator(nc, mdbi);
            generateClass(destDir, gcl, generator.generateBeanIntf());
            generateClass(destDir, gcl, generator.generate());
         } else if (!mdbi.implementsMessageListener()) {
            generateClass(destDir, gcl, (new MessageDrivenBeanImplGenerator(nc, mdbi)).generate());
         }

         generateClass(destDir, gcl, (new MDOImplGenerator(nc, mdbi)).generate());
      } else if (!mdbi.implementsMessageListener()) {
         Generator.Output o = (new MessageDrivenBeanImplGenerator(nc, mdbi)).generate();
         generateClass(destDir, (GenericClassLoader)mdbi.getModuleClassLoader(), o);
      }

   }

   public static void generate(InterceptorBean ib, NamingConvention nc, String destDir, GenericClassLoader gcl) throws EJBCException {
      Generator gen = new SerializableIcptrGenerator(nc.getInterceptorImplClassName(), ib.getInterceptorClass());
      generateClass(destDir, gcl, gen.generate());
   }

   public static void generate(SessionBeanInfo sbi, NamingConvention nc, String destDir) throws EJBCException {
      GenericClassLoader moduleCL = (GenericClassLoader)sbi.getModuleClassLoader();
      generateClass(destDir, moduleCL, (new BeanIntfGenerator(nc, sbi)).generate());
      generateClass(destDir, sbi.getDeploymentInfo().isEnableBeanClassRedeploy() ? (GenericClassLoader)sbi.getClassLoader() : moduleCL, (new BeanImplGenerator(nc, sbi)).generate());
      Iterator var4;
      Class bl;
      if (sbi.hasRemoteClientView()) {
         if (sbi.hasDeclaredRemoteHome()) {
            assert sbi.isStateless() || sbi.isStateful();

            generateClass(destDir, moduleCL, (new HomeImplGenerator(nc, sbi)).generate());
            generateClass(destDir, moduleCL, (new EOImplGenerator(nc, sbi)).generate());
         }

         for(var4 = sbi.getBusinessRemotes().iterator(); var4.hasNext(); generateClass(destDir, moduleCL, (new RemoteBusImplGenerator(nc, sbi, bl)).generate())) {
            bl = (Class)var4.next();
            if (!Remote.class.isAssignableFrom(bl)) {
               generateClass(destDir, moduleCL, (new RemoteBusIntfGenerator(nc.getRemoteBusinessIntfClassName(bl), bl)).generate());
            }
         }
      }

      if (sbi.hasLocalClientView()) {
         if (sbi.hasDeclaredLocalHome()) {
            assert sbi.isStateless() || sbi.isStateful();

            generateClass(destDir, moduleCL, (new LocalHomeImplGenerator(nc, sbi)).generate());
            generateClass(destDir, moduleCL, (new ELOImplGenerator(nc, sbi)).generate());
         }

         var4 = sbi.getBusinessLocals().iterator();

         while(var4.hasNext()) {
            bl = (Class)var4.next();
            generateClass(destDir, moduleCL, (new LocalBusImplGenerator(nc, sbi, bl)).generate());
         }

         if (sbi.hasNoIntfView()) {
            generateClass(destDir, moduleCL, (new LocalBusImplGenerator(nc, sbi, sbi.getBeanClass())).generate());
         }
      }

      if (sbi.hasWebserviceClientView()) {
         generateClass(destDir, moduleCL, (new WSOImplGenerator(nc, sbi)).generate());
      }

   }

   private static void generateClass(String destDir, GenericClassLoader gcl, Generator.Output o) throws EJBCException {
      try {
         if (KernelStatus.isServer()) {
            String relPath = o.relativeFilePath();
            String name = relPath.substring(0, relPath.length() - 6).replace(File.separatorChar, '.');
            gcl.defineCodeGenClass(name, o.bytes(), (URL)null);
         }

         FileUtils.writeToFile(o.bytes(), destDir + File.separator + o.relativeFilePath());
      } catch (IOException var5) {
         throw new EJBCException("Error writing bytes to class file.", var5);
      } catch (ClassNotFoundException var6) {
         throw new EJBCException("Error defining generated class.", var6);
      }
   }
}
