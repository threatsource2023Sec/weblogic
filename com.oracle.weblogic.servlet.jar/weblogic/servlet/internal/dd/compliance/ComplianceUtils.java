package weblogic.servlet.internal.dd.compliance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ComplianceUtils {
   private static final boolean debug = false;

   public static void checkCompliance(DeploymentInfo info) throws ErrorCollectionException {
      ComplianceChecker[] checkers = BaseComplianceChecker.makeComplianceCheckers(info);
      ErrorCollectionException exceptions = null;

      for(int i = 0; i < checkers.length; ++i) {
         checkers[i].setVerbose(info.isVerbose());

         try {
            checkers[i].check(info);
         } catch (ErrorCollectionException var7) {
            if (exceptions == null) {
               exceptions = new ErrorCollectionException();
            }

            if (var7 != null && !var7.isEmpty()) {
               Collection errors = var7.getExceptions();
               Iterator iter = errors.iterator();

               while(iter.hasNext()) {
                  exceptions.add((Throwable)iter.next());
               }
            }
         } catch (Exception var8) {
            if (exceptions == null) {
               exceptions = new ErrorCollectionException();
            }

            exceptions.add(var8);
         }
      }

      if (exceptions != null && !exceptions.isEmpty()) {
         throw exceptions;
      }
   }

   public static void checkCompliance(File docRoot, ClassLoader cl) throws ErrorCollectionException, IOException {
      WebAppBean bean = null;
      WeblogicWebAppBean wlBean = null;
      VirtualJarFile vjf = null;
      ErrorCollectionException exceptions = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(docRoot);
         WebAppDescriptor wad = new WebAppDescriptor(vjf);
         bean = wad.getWebAppBean();
         wlBean = wad.getWeblogicWebAppBean();
      } catch (FileNotFoundException var11) {
      } catch (XMLStreamException var12) {
         if (exceptions == null) {
            exceptions = new ErrorCollectionException();
         }

         exceptions.add(var12);
         throw exceptions;
      } finally {
         if (vjf != null) {
            vjf.close();
         }

      }

      DeploymentInfo info = new DeploymentInfo(bean, wlBean);
      info.setClassLoader(cl);
      checkCompliance(info);
   }
}
