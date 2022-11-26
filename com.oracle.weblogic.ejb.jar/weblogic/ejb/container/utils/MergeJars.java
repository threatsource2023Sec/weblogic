package weblogic.ejb.container.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.metadata.EJBDescriptorBeanUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.ContainerTransactionBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EjbRelationBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodPermissionBean;
import weblogic.j2ee.descriptor.RelationshipsBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.TransactionIsolationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsRelationBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public final class MergeJars {
   private static final String CMP_JAR = "META-INF/weblogic-cmp-rdbms-jar.xml";
   private String targetJarFileName;
   private final Collection virtJarFiles = new LinkedList();
   private final Collection ejbDescriptorBeans = new LinkedList();

   public void mergeJars(String[] jarFileNames) {
      this.targetJarFileName = jarFileNames[0];

      for(int i = 1; i < jarFileNames.length; ++i) {
         try {
            VirtualJarFile jf = VirtualJarFactory.createVirtualJar(new File(jarFileNames[i]));
            this.virtJarFiles.add(jf);
            EjbDescriptorBean ejbDesc = EJBDescriptorBeanUtils.createDescriptorFromJarFile(jf);
            this.ejbDescriptorBeans.add(ejbDesc);
         } catch (IOException var9) {
            EJBLogger.logStackTrace(var9);
         } catch (XMLParsingException var10) {
            EJBLogger.logStackTrace(var10);
         } catch (XMLProcessingException var11) {
            EJBLogger.logStackTrace(var11);
         } catch (Exception var12) {
            EJBLogger.logStackTrace(var12);
         }
      }

      EjbDescriptorBean mergedDescriptor = new EjbDescriptorBean();
      EjbJarBean mEJBJar = mergedDescriptor.createEjbJarBean();
      mEJBJar.addDescription("Merged EJB Jar");
      mEJBJar.addDisplayName("Merged EJB Jar");
      mEJBJar.createEnterpriseBeans();
      WeblogicEjbJarBean mWlEJBJar = mergedDescriptor.createWeblogicEjbJarBean();
      mWlEJBJar.setDescription("Merged Weblogic EJB Jar");
      WeblogicRdbmsJarBean mCmpJar = null;
      weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean mCmpJar20 = null;
      Iterator var7 = this.ejbDescriptorBeans.iterator();

      while(var7.hasNext()) {
         EjbDescriptorBean d = (EjbDescriptorBean)var7.next();
         addEJBJar20(mEJBJar, d.getEjbJarBean());
         addWlEJBJar(mWlEJBJar, d.getWeblogicEjbJarBean());
         if (null != d.getWeblogicRdbms11JarBeans() && d.getWeblogicRdbms11JarBeans().length > 0) {
            mCmpJar = mergedDescriptor.createWeblogicRdbms11JarBean();
            addCmpJar(mCmpJar, d.getWeblogicRdbms11JarBeans());
            adjustPersistenceTypeStorage(mWlEJBJar);
         }

         if (null != d.getWeblogicRdbmsJarBeans() && d.getWeblogicRdbmsJarBeans().length > 0) {
            mCmpJar20 = mergedDescriptor.createWeblogicRdbmsJarBean();
            addCmpJar(mCmpJar20, d.getWeblogicRdbmsJarBeans());
            adjustPersistenceTypeStorage(mWlEJBJar);
         }
      }

      createOutputJar(this.targetJarFileName, mergedDescriptor, this.virtJarFiles);
   }

   private static void adjustPersistenceTypeStorage(WeblogicEjbJarBean wl) {
      WeblogicEnterpriseBeanBean[] beans = wl.getWeblogicEnterpriseBeans();

      for(int i = 0; i < beans.length; ++i) {
         if (beans[i].isEntityDescriptorSet()) {
            PersistenceBean p = beans[i].getEntityDescriptor().getPersistence();
            if (beans[i].getEntityDescriptor().isPersistenceSet() && p.isPersistenceUseSet()) {
               PersistenceUseBean pt = p.getPersistenceUse();
               pt.setTypeStorage("META-INF/weblogic-cmp-rdbms-jar.xml");
            } else {
               System.out.println("Warning:  couldn't find a persistence use for EJB:" + beans[i].getEjbName());
            }
         }
      }

   }

   private static boolean mustSkip(JarEntry je) {
      String n = je.getName();
      if ("META-INF/MANIFEST.MF".equals(n)) {
         return false;
      } else {
         return 0 == n.indexOf("META-INF/") || n.equals("_WL_GENERATED");
      }
   }

   private static void createOutputJar(String fileName, EjbDescriptorBean ejbDesc, Collection jars) {
      HashMap badEntries = new HashMap();
      JarOutputStream jf = null;
      FileOutputStream fos = null;

      try {
         fos = new FileOutputStream(fileName);
         jf = new JarOutputStream(fos);
         HashMap entries = new HashMap();
         Iterator it = jars.iterator();

         label183:
         while(it.hasNext()) {
            VirtualJarFile thisJar = (VirtualJarFile)it.next();
            Iterator e = thisJar.entries();

            while(true) {
               while(true) {
                  JarEntry je;
                  do {
                     if (!e.hasNext()) {
                        continue label183;
                     }

                     je = (JarEntry)e.next();
                  } while(mustSkip(je));

                  Object o = entries.get(je.getName());
                  JarEntry je2;
                  if (null == o) {
                     entries.put(je.getName(), je);
                     je2 = new JarEntry(je.getName());
                     jf.putNextEntry(je2);
                     InputStream is = thisJar.getInputStream(je);
                     byte[] buf = new byte[1024];

                     int len;
                     while((len = is.read(buf)) > 0) {
                        jf.write(buf, 0, len);
                     }

                     jf.closeEntry();
                  } else {
                     je2 = (JarEntry)o;
                     if (je2.getSize() != je.getSize()) {
                        badEntries.put(je.getName(), je);
                     }
                  }
               }
            }
         }

         ejbDesc.usePersistenceDestination(fileName);
         ejbDesc.persist();
      } catch (IOException var24) {
         EJBLogger.logStackTrace(var24);
      } finally {
         try {
            if (null != jf) {
               jf.close();
            }

            if (null != fos) {
               fos.close();
            }
         } catch (IOException var23) {
         }

      }

      if (!badEntries.isEmpty()) {
         System.out.println("Warning:  the following files appear in several Jar files and have \ndifferent sizes.  The output JAR file may therefore fail to deploy.");
         Iterator it2 = badEntries.values().iterator();

         while(it2.hasNext()) {
            System.out.println("   " + ((JarEntry)it2.next()).getName());
         }
      }

   }

   private static void addCmpJar(WeblogicRdbmsJarBean result, WeblogicRdbmsJarBean[] jars) {
      DescriptorBean db = (DescriptorBean)result;

      for(int i = 0; i < jars.length; ++i) {
         WeblogicRdbmsBeanBean[] beans = jars[i].getWeblogicRdbmsBeans();

         for(int j = 0; j < beans.length; ++j) {
            db.createChildCopy("WeblogicRdbmsBean", (DescriptorBean)beans[j]);
         }
      }

   }

   private static void addCmpJar(weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean result, weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean[] jars) {
      DescriptorBean db = (DescriptorBean)result;

      for(int i = 0; i < jars.length; ++i) {
         weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean[] beans = jars[i].getWeblogicRdbmsBeans();

         for(int j = 0; j < beans.length; ++j) {
            db.createChildCopy("WeblogicRdbmsBean", (DescriptorBean)beans[j]);
         }

         WeblogicRdbmsRelationBean[] relations = jars[i].getWeblogicRdbmsRelations();

         for(int j = 0; j < relations.length; ++j) {
            db.createChildCopy("WeblogicRdbmsRelation", (DescriptorBean)relations[j]);
         }
      }

   }

   private static void addWlEJBJar(WeblogicEjbJarBean result, WeblogicEjbJarBean j) {
      DescriptorBean db = (DescriptorBean)result;
      WeblogicEnterpriseBeanBean[] beans = j.getWeblogicEnterpriseBeans();

      for(int i = 0; i < beans.length; ++i) {
         db.createChildCopy("WeblogicEnterpriseBean", (DescriptorBean)beans[i]);
      }

      SecurityRoleAssignmentBean[] sra = j.getSecurityRoleAssignments();

      for(int i = 0; i < sra.length; ++i) {
         db.createChildCopy("SecurityRoleAssignment", (DescriptorBean)sra[i]);
      }

      TransactionIsolationBean[] ti = j.getTransactionIsolations();

      for(int i = 0; i < ti.length; ++i) {
         db.createChildCopy("TransactionIsolation", (DescriptorBean)ti[i]);
      }

   }

   private static void addEJBJar20(EjbJarBean result, EjbJarBean j) {
      addBeans(result, j.getEnterpriseBeans());
      addRelations(result, j.getRelationships());
      addAssemblyDescriptor(result, j.getAssemblyDescriptor());
   }

   private static void addRelations(EjbJarBean result, RelationshipsBean r) {
      if (null != r) {
         RelationshipsBean rr = result.getRelationships();
         if (null == rr) {
            rr = result.createRelationships();
         }

         DescriptorBean db = (DescriptorBean)rr;
         EjbRelationBean[] relations = r.getEjbRelations();

         for(int i = 0; i < relations.length; ++i) {
            db.createChildCopy("EjbRelation", (DescriptorBean)relations[i]);
         }
      }

   }

   private static void addAssemblyDescriptor(EjbJarBean result, AssemblyDescriptorBean ad) {
      if (null != ad) {
         AssemblyDescriptorBean resultAd = result.getAssemblyDescriptor();
         if (null == resultAd) {
            resultAd = result.createAssemblyDescriptor();
         }

         DescriptorBean db = (DescriptorBean)resultAd;
         SecurityRoleBean[] roles = ad.getSecurityRoles();

         for(int i = 0; i < roles.length; ++i) {
            db.createChildCopy("SecurityRole", (DescriptorBean)roles[i]);
         }

         MethodPermissionBean[] permissions = ad.getMethodPermissions();

         for(int i = 0; i < permissions.length; ++i) {
            db.createChildCopy("MethodPermission", (DescriptorBean)permissions[i]);
         }

         ContainerTransactionBean[] transactions = ad.getContainerTransactions();

         for(int i = 0; i < transactions.length; ++i) {
            db.createChildCopy("ContainerTransaction", (DescriptorBean)transactions[i]);
         }
      }

   }

   private static void addBeans(EjbJarBean result, EnterpriseBeansBean b) {
      EnterpriseBeansBean beans = result.getEnterpriseBeans();
      DescriptorBean db = (DescriptorBean)beans;
      EntityBeanBean[] entities = b.getEntities();

      for(int i = 0; i < entities.length; ++i) {
         db.createChildCopy("Entity", (DescriptorBean)entities[i]);
      }

      SessionBeanBean[] sessions = b.getSessions();

      for(int i = 0; i < sessions.length; ++i) {
         db.createChildCopy("Session", (DescriptorBean)sessions[i]);
      }

      MessageDrivenBeanBean[] mdbs = b.getMessageDrivens();

      for(int i = 0; i < mdbs.length; ++i) {
         db.createChildCopy("MessageDriven", (DescriptorBean)mdbs[i]);
      }

   }

   public static void main(String[] argv) {
      if (argv.length < 2) {
         System.out.println("MergeJars v0.81:  Merge several jar files into one.");
         System.out.println("Usage:  MergeJars outputJar inputJar [inputJar inputJar...]");
      } else {
         (new MergeJars()).mergeJars(argv);
      }

   }
}
