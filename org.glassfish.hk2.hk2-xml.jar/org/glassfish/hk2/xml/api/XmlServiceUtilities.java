package org.glassfish.hk2.xml.api;

import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DuplicateServiceException;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.api.ManagerUtilities;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.xml.internal.DomXmlParser;
import org.glassfish.hk2.xml.internal.XmlServiceImpl;
import org.glassfish.hk2.xml.jaxb.internal.JAXBXmlParser;
import org.glassfish.hk2.xml.spi.XmlServiceParser;

public class XmlServiceUtilities {
   private static boolean isDuplicateException(MultiException me) {
      Iterator var1 = me.getErrors().iterator();

      while(var1.hasNext()) {
         for(Throwable th = (Throwable)var1.next(); th != null; th = th.getCause()) {
            if (th instanceof DuplicateServiceException) {
               return true;
            }
         }
      }

      return false;
   }

   public static void enableXmlService(ServiceLocator locator) {
      ManagerUtilities.enableConfigurationHub(locator);

      try {
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{JAXBXmlParser.class});
      } catch (MultiException var2) {
         if (!isDuplicateException(var2)) {
            throw var2;
         }
      }

      enableAllFoundParsers(locator);
   }

   private static void enableAllFoundParsers(ServiceLocator locator) {
      List allParsers = locator.getDescriptors(BuilderHelper.createContractFilter(XmlServiceParser.class.getName()));
      Iterator var2 = allParsers.iterator();

      while(var2.hasNext()) {
         ActiveDescriptor parserDescriptor = (ActiveDescriptor)var2.next();
         String name = parserDescriptor.getName();
         if (name != null) {
            ActiveDescriptor found = locator.getBestDescriptor(BuilderHelper.createNameAndContractFilter(XmlService.class.getName(), name));
            if (found == null) {
               DescriptorImpl di = BuilderHelper.createDescriptorFromClass(XmlServiceImpl.class);
               di.setName(name);
               di.setRanking(parserDescriptor.getRanking());
               ServiceLocatorUtilities.addOneDescriptor(locator, di, false);
            }
         }
      }

   }

   public static void enableDomXmlService(ServiceLocator locator) {
      ManagerUtilities.enableConfigurationHub(locator);

      try {
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{DomXmlParser.class});
      } catch (MultiException var2) {
         if (!isDuplicateException(var2)) {
            throw var2;
         }
      }

      enableAllFoundParsers(locator);
   }
}
