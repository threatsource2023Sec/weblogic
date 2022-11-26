package weblogic.j2ee.dd.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.dd.J2EEDeploymentDescriptor;
import weblogic.j2ee.descriptors.ApplicationDescriptorMBeanImpl;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.descriptors.ApplicationDescriptorMBean;
import weblogic.management.descriptors.Encoding;
import weblogic.management.descriptors.application.J2EEApplicationDescriptorMBean;
import weblogic.management.descriptors.application.weblogic.WeblogicApplicationMBean;
import weblogic.utils.Debug;
import weblogic.utils.io.XMLDeclaration;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.dd.DDConstants;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.ProcessorFactoryException;
import weblogic.xml.process.SAXProcessorException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.process.XMLProcessor;

public final class DDUtils {
   private static final boolean debug = Debug.getCategory("weblogic.j2ee.dd").isEnabled();
   public static final int MARK_SIZE = 1048576;

   public static ApplicationDescriptorMBean loadDeploymentDescriptor(VirtualJarFile vjf, File stdAppDD, File wlsAppDD) throws DeploymentException {
      if (debug) {
         Debug.say("loadDeploymentDescriptor \nVirtualJarFile : " + vjf.getName() + "\nstdAppDD : " + (stdAppDD != null ? stdAppDD.getPath() : "null") + "\nwlsAppDD : " + (wlsAppDD != null ? wlsAppDD.getPath() : "null"));
      }

      ApplicationDescriptorMBean top = new ApplicationDescriptorMBeanImpl();
      return loadDeploymentDescriptor(top, vjf, stdAppDD, wlsAppDD);
   }

   public static ApplicationDescriptorMBean loadDeploymentDescriptor(ApplicationDescriptorMBean top, VirtualJarFile jar, File stdAppDD, File wlsAppDD) throws DeploymentException {
      if (top == null) {
         top = new ApplicationDescriptorMBeanImpl();
      }

      loadStdDescriptor((ApplicationDescriptorMBean)top, jar, stdAppDD);
      loadWlsDescriptor((ApplicationDescriptorMBean)top, jar, wlsAppDD);
      return (ApplicationDescriptorMBean)top;
   }

   public static J2EEApplicationDescriptorMBean loadStdDescriptor(ApplicationDescriptorMBean top, VirtualJarFile jar, File stdAppDD) throws DeploymentException {
      if (debug) {
         Debug.say("loadStdDescriptor \nVirtualJarFile : " + jar.getName() + "\nstdAppDD : " + (stdAppDD != null ? stdAppDD.getPath() : "null"));
      }

      String ddUri = findFirstEntry(jar, DDConstants.STD_DESCRIPTOR_PATHS);
      if (debug) {
         Debug.say("loadStdDescriptor \nddURI : " + ddUri);
      }

      if (ddUri == null && stdAppDD == null) {
         return null;
      } else {
         if (top == null) {
            top = new ApplicationDescriptorMBeanImpl();
         }

         String err;
         try {
            processXML((ApplicationDescriptorMBean)top, jar, ddUri, stdAppDD);
         } catch (IOException var7) {
            err = "Error while loading descriptors: " + var7;
            throw new DeploymentException(err, var7);
         } catch (XMLProcessingException var8) {
            Throwable e = var8.getNestedException();
            if (e instanceof SAXProcessorException) {
               e = ((SAXProcessorException)e).getException();
            }

            if (e instanceof DeploymentException) {
               throw (DeploymentException)e;
            }

            String err = "Error while loading descriptors: " + var8;
            throw new DeploymentException(err, var8);
         } catch (XMLParsingException var9) {
            err = "Error while loading descriptors: " + var9;
            throw new DeploymentException(err, var9);
         }

         return ((ApplicationDescriptorMBean)top).getJ2EEApplicationDescriptor();
      }
   }

   public static WeblogicApplicationMBean loadWlsDescriptor(ApplicationDescriptorMBean top, VirtualJarFile jar, File wlsAppDD) throws DeploymentException {
      if (debug) {
         Debug.say("loadWlsDescriptor \nVirtualJarFile : " + jar.getName() + "\nstdAppDD : " + (wlsAppDD != null ? wlsAppDD.getPath() : "null"));
      }

      String ddUri = findFirstEntry(jar, DDConstants.WLS_DESCRIPTOR_PATHS);
      if (debug) {
         Debug.say("loadWlsdDescriptor \nddURI : " + ddUri);
      }

      if (ddUri == null && wlsAppDD == null) {
         return null;
      } else {
         if (top == null) {
            top = new ApplicationDescriptorMBeanImpl();
         }

         String err;
         try {
            processXML((ApplicationDescriptorMBean)top, jar, ddUri, wlsAppDD);
         } catch (IOException var7) {
            err = "Error while loading descriptors: " + var7;
            throw new DeploymentException(err, var7);
         } catch (XMLProcessingException var8) {
            Throwable e = var8.getNestedException();
            if (e instanceof SAXProcessorException) {
               e = ((SAXProcessorException)e).getException();
            }

            if (e instanceof DeploymentException) {
               throw (DeploymentException)e;
            }

            String err = "Error while loading descriptors: " + var8;
            throw new DeploymentException(err, var8);
         } catch (XMLParsingException var9) {
            err = "Error while loading descriptors: " + var9;
            throw new DeploymentException(err, var9);
         }

         return ((ApplicationDescriptorMBean)top).getWeblogicApplicationDescriptor();
      }
   }

   private static void processXML(ApplicationDescriptorMBean top, VirtualJarFile jar, String uri, File altFile) throws IOException, XMLProcessingException, XMLParsingException {
      InputStream is = null;

      try {
         if (altFile != null) {
            is = new FileInputStream(altFile);
            processXML((InputStream)is, top, altFile.getPath());
         } else {
            ZipEntry entry = jar.getEntry(uri);
            if (entry == null) {
               throw new FileNotFoundException("Could not find " + uri);
            }

            is = jar.getInputStream(entry);
            processXML((InputStream)is, top, uri);
         }
      } finally {
         if (is != null) {
            ((InputStream)is).close();
         }

      }

   }

   private static void processXML(InputStream xml, ApplicationDescriptorMBean top, String fileName) throws XMLProcessingException, XMLParsingException, IOException {
      if (!((InputStream)xml).markSupported()) {
         xml = new BufferedInputStream((InputStream)xml);
      }

      String encoding = getXMLEncoding((InputStream)xml, fileName);
      ((InputStream)xml).mark(1048576);
      XMLProcessor processor = null;

      try {
         processor = (new ProcessorFactory()).getProcessor((InputStream)xml, DDConstants.validPublicIds);
      } catch (ProcessorFactoryException var12) {
         throw new XMLProcessingException(var12, fileName);
      }

      ((InputStream)xml).reset();
      if (processor instanceof J2EEDeploymentDescriptorLoader) {
         J2EEDeploymentDescriptorLoader loader = (J2EEDeploymentDescriptorLoader)processor;
         J2EEApplicationDescriptorMBean std = new J2EEDeploymentDescriptor();
         top.setJ2EEApplicationDescriptor(std);
         std.setEncoding(encoding);
         loader.setDD(std);

         try {
            loader.process((InputStream)xml);
         } catch (XMLParsingException var10) {
            var10.setFileName(fileName);
            throw var10;
         } catch (XMLProcessingException var11) {
            var11.setFileName(fileName);
            throw var11;
         }
      } else {
         if (!(processor instanceof WADDLoader)) {
            throw new XMLProcessingException("Invalid descriptor file: " + fileName);
         }

         WADDLoader loader = (WADDLoader)processor;
         loader.setApplicationDescriptor(top);

         try {
            loader.process((InputStream)xml);
         } catch (XMLParsingException var8) {
            var8.setFileName(fileName);
            throw var8;
         } catch (XMLProcessingException var9) {
            var9.setFileName(fileName);
            throw var9;
         }

         WeblogicApplicationMBean wam = top.getWeblogicApplicationDescriptor();
         wam.setEncoding(encoding);
      }

   }

   private static String findFirstEntry(VirtualJarFile jar, String[] uri) {
      if (uri != null && jar != null) {
         for(int i = 0; i < uri.length; ++i) {
            ZipEntry ent = jar.getEntry(uri[i]);
            if (ent != null) {
               return uri[i];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static String getXMLEncoding(InputStream xml, String fileName) throws IOException {
      String encoding = null;
      xml.mark(1048576);

      try {
         XMLDeclaration xd = new XMLDeclaration();
         xd.parse(xml);
         encoding = xd.getEncoding();
      } finally {
         xml.reset();
      }

      validateEncoding(encoding, fileName);
      return encoding;
   }

   private static void validateEncoding(String encoding, String fileName) throws IOException {
      if (encoding != null && Encoding.getIANA2JavaMapping(encoding) == null && Encoding.getJava2IANAMapping(encoding) == null && !Charset.isSupported(encoding)) {
         Loggable l = J2EELogger.logDescriptorUsesInvalidEncodingLoggable(fileName, encoding);
         throw new UnsupportedEncodingException(l.getMessage());
      }
   }

   public static void main(String[] a) {
      try {
         if (a.length <= 0) {
            Debug.say("Usage : java weblogic.j2ee.dd.xml.DDUtils $jarfilename");
            return;
         }

         File f = new File(a[0]);
         ApplicationDescriptorMBean topMBean = new ApplicationDescriptorMBeanImpl();
         System.out.println("OPEN " + f);
         VirtualJarFile vjFile = VirtualJarFactory.createVirtualJar(f);
         ApplicationDescriptorMBean top = loadDeploymentDescriptor(topMBean, vjFile, (File)null, (File)null);
         J2EEApplicationDescriptorMBean std = top.getJ2EEApplicationDescriptor();
         WeblogicApplicationMBean wls = top.getWeblogicApplicationDescriptor();
         Debug.say("Printing xml file ......");
         Debug.say(std.toXML(2));
         Debug.say("=============");
         Debug.say(wls != null ? wls.toXML(2) : "WLS descriptor null");
         Debug.say("Printed xml file ......");
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }
}
