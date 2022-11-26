package weblogic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import weblogic.application.ApplicationDescriptor;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.utils.io.StreamUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ClientDeployer {
   private static final int SUFFIX_LENGTH = ".runtime.xml".length();

   public static void main(String[] argv) throws Exception {
      if (argv.length < 2) {
         usage();
      }

      VirtualJarFile ear = null;
      File source = new File(argv[0]);
      if (source.isDirectory()) {
         ear = VirtualJarFactory.createVirtualJar(source);
      } else {
         ear = VirtualJarFactory.createVirtualJar(new JarFile(argv[0]));
      }

      File dir = source.getParentFile();

      for(int i = 1; i < argv.length; ++i) {
         processClientJar(ear, argv[i] + ".jar", new File(dir, argv[i] + ".runtime.xml"));
      }

   }

   public static File deploy(File runtimeFile, JarFile ear, File outputDir) {
      String clientJar = getJarFile(runtimeFile.getName());
      File res = new File(outputDir, clientJar);

      try {
         processClientJar((JarFile)ear, clientJar, runtimeFile, new BufferedOutputStream(new FileOutputStream(res)));
      } catch (Exception var6) {
         System.out.println("Deployment problem ear=" + ear.getName() + ", runtime=" + runtimeFile + ", problem=" + var6);
         var6.printStackTrace();
      }

      return res;
   }

   private static void usage() {
      System.out.println("Usage: java weblogic.ClientDeployer <ear-file> <client>...");
      System.out.println("  where the ear-file directory contains client.runtime.xml for every client");
      System.out.println("Example: 'java weblogic.ClientDeployer app.ear client', where app.ear contains client.jar and current directory contains client.runtime.xml");
      System.exit(1);
   }

   private static String getJarFile(String name) {
      return name.substring(0, name.length() - SUFFIX_LENGTH) + ".jar";
   }

   static void processClientJar(VirtualJarFile ear, String clientJar, File runtimeDD) throws IOException, ParserConfigurationException, SAXException {
      processClientJar((VirtualJarFile)ear, clientJar, runtimeDD, new FileOutputStream(clientJar));
   }

   static void processClientJar(JarFile ear, String clientJar, File runtimeDD) throws IOException, ParserConfigurationException, SAXException {
      processClientJar((JarFile)ear, clientJar, runtimeDD, new FileOutputStream(clientJar));
   }

   private static void processClientJar(JarFile ear, String clientJar, File runtimeDD, OutputStream os) throws IOException, ParserConfigurationException, SAXException {
      processClientJar(VirtualJarFactory.createVirtualJar(ear), clientJar, runtimeDD, os);
   }

   static void processClientJar(VirtualJarFile ear, String clientJar, File runtimeDD, OutputStream os) throws IOException, ParserConfigurationException, SAXException {
      ZipInputStream zis;
      try {
         zis = new ZipInputStream(ear.getInputStream(ear.getEntry(clientJar)));
      } catch (NullPointerException var25) {
         throw new IOException("No entry " + clientJar + " found in ear file");
      }

      ZipOutputStream zos = new ZipOutputStream(os);
      String altDDUri = findAltDDUri(ear, clientJar);

      ZipEntry permissionsZE;
      for(permissionsZE = zis.getNextEntry(); permissionsZE != null; permissionsZE = zis.getNextEntry()) {
         if (altDDUri == null || !"META-INF/application-client.xml".equals(permissionsZE.getName())) {
            if (permissionsZE.getName().equals(altDDUri)) {
               zos.putNextEntry(new ZipEntry("META-INF/application-client.xml"));
               StreamUtils.writeTo(zis, zos);
            } else {
               zos.putNextEntry(permissionsZE);
               StreamUtils.writeTo(zis, zos);
            }
         }
      }

      if (altDDUri != null) {
         zos.putNextEntry(new ZipEntry("META-INF/application-client.xml"));
         InputStream is = null;

         try {
            is = ear.getInputStream(new ZipEntry(altDDUri));
            StreamUtils.writeTo(is, zos);
         } catch (NullPointerException var24) {
            throw new IOException("Your client-jar specified an alt-dd of " + altDDUri + " but that uri was not found in the EAR");
         } finally {
            if (is != null) {
               is.close();
            }

         }
      }

      if (runtimeDD.exists()) {
         System.out.println("Using weblogic-application-client.xml from " + runtimeDD.getAbsolutePath());
         zos.putNextEntry(new ZipEntry("META-INF/weblogic-application-client.xml"));
         InputStream fis = null;

         try {
            fis = new FileInputStream(runtimeDD);
            StreamUtils.writeTo(fis, zos);
         } finally {
            if (fis != null) {
               try {
                  fis.close();
               } catch (Exception var23) {
               }
            }

         }
      }

      permissionsZE = ear.getEntry("META-INF/permissions.xml");
      if (permissionsZE != null) {
         zos.putNextEntry(permissionsZE);
         StreamUtils.writeTo(ear.getInputStream(permissionsZE), zos);
         System.out.println("Copying META-INF/permissions.xml from the EAR archive to " + clientJar);
      }

      zos.close();
      zis.close();
   }

   private static String findAltDDUri(VirtualJarFile vjf, String clientJar) {
      try {
         ApplicationDescriptor desc = new ApplicationDescriptor(vjf);
         ApplicationBean appDD = desc.getApplicationDescriptor();
         if (appDD == null) {
            return null;
         } else {
            ModuleBean[] m = appDD.getModules();

            for(int i = 0; i < m.length; ++i) {
               if (clientJar.equals(m[i].getJava())) {
                  return m[i].getAltDd();
               }
            }

            System.out.println("Warning: client-jar " + clientJar + " was not found in the application.xml");
            return null;
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         return null;
      }
   }
}
