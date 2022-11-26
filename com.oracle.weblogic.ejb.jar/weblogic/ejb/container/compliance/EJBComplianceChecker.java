package weblogic.ejb.container.compliance;

import com.oracle.injection.integration.CDIUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.deployer.DeploymentDescriptorException;
import weblogic.ejb.container.deployer.DeploymentInfoImpl;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public final class EJBComplianceChecker extends BaseComplianceChecker implements ComplianceChecker, PlatformConstants {
   public static final boolean isNeedCheck = Boolean.getBoolean("ignoreEJBChecker");
   static final ComplianceChecker INSTANCE = new EJBComplianceChecker();

   private EJBComplianceChecker() {
   }

   public void checkDeploymentInfo(DeploymentInfo di) throws ErrorCollectionException {
      if (!isNeedCheck) {
         Set deploymentInfoCheckers = new HashSet();
         Object interceptorChecker = null;
         Object[] relationshipCheckers = null;
         Iterator var5 = di.getBeanInfos().iterator();

         BeanInfo bi;
         while(var5.hasNext()) {
            bi = (BeanInfo)var5.next();
            EJBCheckerFactory creator = bi.getEJBCheckerFactory(di);
            if (interceptorChecker == null && creator.getInterceptorChecker() != null) {
               interceptorChecker = creator.getInterceptorChecker();
            }

            if (relationshipCheckers == null && creator.getRelationShipCheckers() != null) {
               relationshipCheckers = creator.getRelationShipCheckers();
            }

            Class[] var8 = creator.getDeploymentInfoCheckerClasses();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Class c = var8[var10];
               deploymentInfoCheckers.add(c);
            }
         }

         var5 = deploymentInfoCheckers.iterator();

         while(var5.hasNext()) {
            Class checkClass = (Class)var5.next();
            Object check = this.getInstance(checkClass, di);
            this.check(check);
         }

         var5 = di.getBeanInfos().iterator();

         while(var5.hasNext()) {
            bi = (BeanInfo)var5.next();
            Object[] checkers = bi.getEJBCheckerFactory(di).getBeanInfoCheckers();
            this.check(checkers);
         }

         if (interceptorChecker != null) {
            this.check(interceptorChecker);
         }

         if (relationshipCheckers != null) {
            this.check(relationshipCheckers);
         }

      }
   }

   protected void check(Object[] checkers) throws ErrorCollectionException {
      Object[] var2 = checkers;
      int var3 = checkers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object o = var2[var4];
         this.check(o);
      }

   }

   public void check(Object o) throws AssertionError, ErrorCollectionException {
      if (o != null) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Method[] var3 = o.getClass().getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.getName().startsWith("check")) {
               try {
                  m.invoke(o, (Object[])null);
               } catch (IllegalAccessException var9) {
                  throw new AssertionError(var9);
               } catch (InvocationTargetException var10) {
                  Throwable t = var10.getTargetException();
                  if (t instanceof NoClassDefFoundError) {
                     t = new NoClassDefFoundError("Class not found: " + ((Throwable)t).getMessage());
                  }

                  errors.add((Throwable)t);
               }
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   private Object getInstance(Class c, Object di) {
      try {
         Constructor ctr = c.getConstructor(DeploymentInfo.class);
         return ctr.newInstance(di);
      } catch (Exception var4) {
         throw new AssertionError("Exception creating checker instance : " + var4);
      }
   }

   private static boolean checkFile(File f) {
      if (!f.exists()) {
         System.err.println(EJBComplianceTextFormatter.getInstance().jarFileMissing(f.getAbsolutePath()));
         return false;
      } else if (f.isDirectory()) {
         System.err.println(EJBComplianceTextFormatter.getInstance().jarFileIsDirectory(f.getAbsolutePath()));
         return false;
      } else {
         return true;
      }
   }

   public static void complianceCheckEJB(DeploymentInfo di) throws ErrorCollectionException {
      try {
         ComplianceCheckerFactory.getComplianceChecker().checkDeploymentInfo(di);
      } catch (ErrorCollectionException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new ErrorCollectionException(var3);
      }
   }

   private static void initParser() {
   }

   private static void complianceCheckEJBJar(File f) throws IOException, XMLParsingException, XMLProcessingException, ErrorCollectionException {
      ClasspathClassFinder2 finder = new ClasspathClassFinder2(f.getPath());
      GenericClassLoader cl = new GenericClassLoader(finder);

      try {
         EjbDescriptorBean desc = EjbDescriptorFactory.createDescriptorFromJarFile(VirtualJarFactory.createVirtualJar(f));
         VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(f);
         boolean isCDIEnabled = CDIUtils.isVirtualJarCdiEnabled(vjf, (ClassInfoFinder)null, cl, (ApplicationContextInternal)null);
         DeploymentInfo di = new DeploymentInfoImpl(desc, cl, "", "", "", vjf, (ApplicationContextInternal)null, isCDIEnabled);
         complianceCheckEJB(di);
         finder.close();
      } catch (DeploymentDescriptorException var12) {
         EJBLogger.logStackTrace(var12);
      } catch (WLDeploymentException var13) {
         EJBLogger.logStackTrace(var13);
      } catch (XMLStreamException var14) {
         EJBLogger.logStackTrace(var14);
      } finally {
         cl.close();
      }

   }

   public static void checkJar(File f) throws IOException, XMLParsingException, XMLProcessingException, ErrorCollectionException {
      initParser();
      complianceCheckEJBJar(f);
   }

   public static void main(String[] args) {
      EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();
      initParser();
      if (args.length == 0) {
         Localizer l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBComplianceTextLocalizer");
         System.err.println(l10n.get("usage"));
      } else {
         String[] var2 = args;
         int var3 = args.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String fileName = var2[var4];
            System.out.println(fmt.checkingJarFile(fileName));
            System.out.println("");
            File f = new File(fileName);
            if (checkFile(f)) {
               try {
                  complianceCheckEJBJar(f);
                  System.out.println(fmt.compliant(fileName));
                  System.out.println("");
               } catch (IOException var10) {
                  System.err.println(fmt.notValid(f.getName(), var10));
               } catch (XMLParsingException var11) {
                  System.err.println(fmt.failedToParse(f.getName(), var11));
               } catch (XMLProcessingException var12) {
                  System.err.println(fmt.failedToLoad(f.getName(), var12));
               } catch (ErrorCollectionException var13) {
                  Iterator var8 = var13.getExceptions().iterator();

                  while(var8.hasNext()) {
                     Throwable th = (Throwable)var8.next();
                     if (th instanceof ComplianceException) {
                        System.err.println(fmt.complianceError(th.getMessage()));
                     } else if (th instanceof ClassNotFoundException) {
                        System.err.println(fmt.loadFailure(th.getMessage()));
                     } else {
                        System.err.println(fmt.complianceError(StackTraceUtilsClient.throwable2StackTrace(th)));
                     }
                  }
               }
            }
         }

      }
   }
}
