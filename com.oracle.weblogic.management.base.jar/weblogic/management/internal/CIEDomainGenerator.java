package weblogic.management.internal;

import java.lang.reflect.Method;
import java.util.Properties;
import weblogic.management.ManagementException;
import weblogic.management.utils.PDevHelper;

public class CIEDomainGenerator extends DomainGenerator {
   private Class cieDomainInfoHelperClass;

   public void validateConfigFramework() throws ManagementException {
      ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

      try {
         ClassLoader newCls = PDevHelper.getPDevClassLoader(this.getClass().getClassLoader());
         this.cieDomainInfoHelperClass = Class.forName("com.oracle.cie.domain.DomainInfoHelper", true, newCls);
      } catch (ClassNotFoundException var7) {
         String message = mgmtTextFormatter.unAvailableConfigWizardCompoment();
         throw new ManagementException(message);
      } finally {
         Thread.currentThread().setContextClassLoader(prevCls);
      }

   }

   public void generateDefaultDomain(String root, String ts1, String ts2) throws Exception {
      if (this.cieDomainInfoHelperClass == null) {
         throw new AssertionError("CIE config framework not located/initialized");
      } else {
         Class[] signature = new Class[]{String.class, String.class, String.class, Properties.class};
         Method method = this.cieDomainInfoHelperClass.getMethod("createDefaultDomain", signature);
         Properties configProps = new Properties();
         configProps.put("DomainName", this.domainName);
         configProps.put("ServerName", this.serverName);
         if (this.listenAddress != null) {
            configProps.put("ListenAddress", this.listenAddress);
         }

         if (this.listenPort != null) {
            configProps.put("ListenPort", this.listenPort);
         }

         configProps.put("domain.OverwriteDomain", "true");
         Object[] parameters = new Object[]{root, ts1, ts2, configProps};
         ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

         try {
            Thread.currentThread().setContextClassLoader(this.cieDomainInfoHelperClass.getClassLoader());
            method.invoke(this.cieDomainInfoHelperClass.newInstance(), parameters);
         } finally {
            Thread.currentThread().setContextClassLoader(prevCls);
         }

      }
   }
}
