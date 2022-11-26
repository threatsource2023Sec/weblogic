package weblogic.ejb.container.ejbc;

import java.util.Vector;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.utils.Getopt2;

public final class Ejb2Rmi extends EjbCodeGenerator {
   public Ejb2Rmi(Getopt2 opts) {
      super(opts);
   }

   protected void addOutputs(Vector outputs, BeanInfo bi, NamingConvention nc) throws EJBCException {
      this.interpretBeanInfo(bi);
      EjbCodeGenerator.Output beanImpl;
      EjbCodeGenerator.Output beanIntf;
      if (this.hasRemoteClientView && this.hasDeclaredRemoteHome) {
         beanImpl = new EjbCodeGenerator.Output();
         beanImpl.setBeanInfo(bi);
         beanImpl.setNamingConvention(nc);
         beanImpl.setTemplate("ejbHomeImpl.j");
         beanImpl.setPackage(nc.getBeanPackageName());
         beanImpl.setOutputFile(nc.getSimpleHomeClassName() + ".java");
         outputs.addElement(beanImpl);
         beanIntf = new EjbCodeGenerator.Output();
         beanIntf.setBeanInfo(bi);
         beanIntf.setNamingConvention(nc);
         beanIntf.setTemplate("ejbEOImpl.j");
         beanIntf.setPackage(nc.getBeanPackageName());
         beanIntf.setOutputFile(nc.getSimpleEJBObjectClassName() + ".java");
         outputs.addElement(beanIntf);
      }

      if (this.hasLocalClientView && this.hasDeclaredLocalHome) {
         beanImpl = new EjbCodeGenerator.Output();
         beanImpl.setBeanInfo(bi);
         beanImpl.setNamingConvention(nc);
         beanImpl.setTemplate("ejbLocalHomeImpl.j");
         beanImpl.setPackage(nc.getBeanPackageName());
         beanImpl.setOutputFile(nc.getSimpleLocalHomeClassName() + ".java");
         outputs.addElement(beanImpl);
         beanIntf = new EjbCodeGenerator.Output();
         beanIntf.setBeanInfo(bi);
         beanIntf.setNamingConvention(nc);
         beanIntf.setTemplate("ejbELOImpl.j");
         beanIntf.setPackage(nc.getBeanPackageName());
         beanIntf.setOutputFile(nc.getSimpleEJBLocalObjectClassName() + ".java");
         outputs.addElement(beanIntf);
      }

      if (((EntityBeanInfo)bi).getIsBeanManagedPersistence()) {
         beanImpl = new EjbCodeGenerator.Output();
         beanImpl.setBeanInfo(bi);
         beanImpl.setNamingConvention(nc);
         beanImpl.setTemplate("ejbBeanImpl.j");
         beanImpl.setPackage(nc.getBeanPackageName());
         beanImpl.setOutputFile(nc.getSimpleGeneratedBeanClassName() + ".java");
         outputs.addElement(beanImpl);
         beanIntf = new EjbCodeGenerator.Output();
         beanIntf.setBeanInfo(bi);
         beanIntf.setNamingConvention(nc);
         beanIntf.setTemplate("ejbBeanIntf.j");
         beanIntf.setPackage(nc.getBeanPackageName());
         beanIntf.setOutputFile(nc.getSimpleGeneratedBeanInterfaceName() + ".java");
         outputs.addElement(beanIntf);
      }

   }
}
