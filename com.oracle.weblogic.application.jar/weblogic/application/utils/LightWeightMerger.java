package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarFile;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.DeployableObjectInfo;
import weblogic.application.Module;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.Merger;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.compiler.deploymentview.EditableJ2eeApplicationObject;
import weblogic.application.compiler.utils.ApplicationResourceFinder;
import weblogic.application.config.DefaultModule;
import weblogic.application.internal.flow.ModuleListenerInvoker;
import weblogic.application.internal.flow.ScopedModuleDriver;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.utils.FileUtils;
import weblogic.utils.application.WarDetector;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class LightWeightMerger implements Merger {
   private final CompilerCtx ctx;
   private ApplicationContextInternal appCtx = null;
   private VirtualJarFile vjf = null;
   private EditableJ2eeApplicationObject deployableApplication = null;
   private GenericClassLoader appClassLoader = null;
   private File baseDir = null;
   private static final String WEB_XML = "WEB-INF/web.xml";
   private static final String WEBLOGIC_XML = "WEB-INF/weblogic.xml";

   LightWeightMerger(CompilerCtx ctx) {
      this.ctx = ctx;
      String workingDirName = ".appmergegen_" + System.currentTimeMillis();
      this.baseDir = new File(PathUtils.getTempDir(), workingDirName);
      if (this.baseDir.exists() && !this.baseDir.isDirectory()) {
         this.baseDir.delete();
      }

      this.baseDir.mkdirs();
   }

   private GenericClassLoader createAppClassLoader(String appId) {
      GenericClassLoader appClassLoader = new GenericClassLoader(new MultiClassFinder(), Thread.currentThread().getContextClassLoader());
      appClassLoader.setAnnotation(new Annotation(appId));
      return appClassLoader;
   }

   public void merge() throws ToolFailureException {
      EditableDeployableObjectFactory objectFactory = this.ctx.getObjectFactory();
      if (objectFactory != null) {
         this.appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(this.ctx.getLightWeightAppName());
         this.appClassLoader = this.createAppClassLoader(this.ctx.getLightWeightAppName());
         Module[] modules;
         if (this.appCtx.isEar()) {
            try {
               this.deployableApplication = objectFactory.createApplicationObject();
               this.vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile();
               DescriptorBean bean = (DescriptorBean)this.appCtx.getApplicationDescriptor().getApplicationDescriptor();
               DescriptorBean clonedBean;
               if (bean != null) {
                  clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                  this.deployableApplication.addRootBean("META-INF/application.xml", clonedBean, (ModuleType)null);
                  this.deployableApplication.setRootBean(clonedBean);
               }

               bean = (DescriptorBean)this.appCtx.getApplicationDescriptor().getWeblogicApplicationDescriptor();
               if (bean != null) {
                  clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                  this.deployableApplication.addRootBean("META-INF/weblogic-application.xml", clonedBean, (ModuleType)null);
               }

               bean = (DescriptorBean)this.appCtx.getApplicationDescriptor().getWeblogicExtensionDescriptor();
               if (bean != null) {
                  clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                  this.deployableApplication.addRootBean("META-INF/weblogic-extension.xml", clonedBean, (ModuleType)null);
               }

               modules = this.appCtx.getApplicationModules();

               for(int i = 0; i < modules.length; ++i) {
                  ModuleType type = WebLogicModuleType.getTypeFromString(modules[i].getType());
                  if (type.equals(WebLogicModuleType.WLDF)) {
                     DescriptorBean[] wldfBeans = modules[i].getDescriptors();
                     DescriptorBean clonedBean = ((Descriptor)wldfBeans[0].getDescriptor().clone()).getRootBean();
                     this.deployableApplication.addRootBean("META-INF/weblogic-diagnostics.xml", clonedBean, type);
                  } else {
                     EditableDeployableObject deployableObject = this.processModules(objectFactory, modules[i]);
                     if (deployableObject != null) {
                        this.deployableApplication.addDeployableObject(deployableObject);
                     }
                  }
               }

               this.deployableApplication.setClassLoader(this.appClassLoader);
               ApplicationResourceFinder appResourceFinder = new ApplicationResourceFinder(this.appCtx.getEar().getURI(), this.appClassLoader.getClassFinder());
               this.deployableApplication.setResourceFinder(appResourceFinder);
               this.deployableApplication.setVirtualJarFile(this.vjf);
            } catch (XMLStreamException var10) {
               this.deployableApplication = null;
            } catch (IOException var11) {
               this.deployableApplication = null;
            } catch (ToolFailureException var12) {
               this.deployableApplication = null;
            }

            this.ctx.setDeployableApplication(this.deployableApplication);
         } else {
            try {
               this.vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile();
            } catch (IOException var9) {
               return;
            }

            EditableDeployableObject deployableObject = null;
            modules = this.appCtx.getApplicationModules();
            Module wldfModule = null;
            Module mainModule = null;

            for(int counter = 0; (wldfModule == null || mainModule == null) && counter < modules.length; ++counter) {
               if (WebLogicModuleType.MODULETYPE_WLDF.equals(modules[counter].getType())) {
                  wldfModule = modules[counter];
               } else {
                  mainModule = modules[counter];
               }
            }

            if (mainModule != null) {
               deployableObject = this.processModules(objectFactory, mainModule);
               if (wldfModule != null) {
                  DescriptorBean[] descriptors = wldfModule.getDescriptors();
                  if (descriptors != null && descriptors.length > 0) {
                     DescriptorBean clonedBean = ((Descriptor)descriptors[0].getDescriptor().clone()).getRootBean();
                     deployableObject.addRootBean("META-INF/weblogic-diagnostics.xml", clonedBean, WebLogicModuleType.WLDF);
                  }
               }

               deployableObject.setVirtualJarFile(this.vjf);
            }

            this.ctx.setDeployableApplication(deployableObject);
         }

      }
   }

   private EditableDeployableObject processModules(EditableDeployableObjectFactory objectFactory, Module module) throws ToolFailureException {
      try {
         EditableDeployableObject deployableObject = null;
         ModuleType type = WebLogicModuleType.getTypeFromString(module.getType());
         DescriptorBean[] beans = module.getDescriptors();
         if (type.equals(ModuleType.EJB)) {
            return this.processEJBModule(objectFactory, module);
         } else if (type.equals(ModuleType.WAR)) {
            return this.processWARModule(objectFactory, module);
         } else if (type.equals(WebLogicModuleType.CONFIG)) {
            return this.processCustomModule(objectFactory, module);
         } else if (!type.equals(WebLogicModuleType.JDBC) && !type.equals(WebLogicModuleType.JMS) && !type.equals(WebLogicModuleType.RAR) && !type.equals(WebLogicModuleType.CAR)) {
            return null;
         } else {
            Module m = module;
            String altDD = null;
            if (module instanceof ModuleListenerInvoker) {
               m = ((ModuleListenerInvoker)module).getDelegate();
            }

            if (m instanceof DeployableObjectInfo) {
               altDD = ((DeployableObjectInfo)m).getAltDD();
            }

            deployableObject = objectFactory.createDeployableObject(module.getId(), altDD, type);
            if (beans != null && beans.length > 0) {
               if (!type.equals(WebLogicModuleType.JMS) && !type.equals(WebLogicModuleType.JDBC)) {
                  int var9;
                  int var10;
                  DescriptorBean bean;
                  DescriptorBean clonedBean;
                  DescriptorBean[] var14;
                  if (type.equals(WebLogicModuleType.RAR)) {
                     var14 = beans;
                     var9 = beans.length;

                     for(var10 = 0; var10 < var9; ++var10) {
                        bean = var14[var10];
                        clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                        if (clonedBean instanceof ConnectorBean) {
                           deployableObject.setRootBean(clonedBean);
                           deployableObject.addRootBean("META-INF/ra.xml", clonedBean, type);
                        } else if (clonedBean instanceof WeblogicConnectorBean) {
                           deployableObject.addRootBean("META-INF/weblogic-ra.xml", clonedBean, type);
                        }
                     }
                  } else if (type.equals(WebLogicModuleType.CAR)) {
                     var14 = beans;
                     var9 = beans.length;

                     for(var10 = 0; var10 < var9; ++var10) {
                        bean = var14[var10];
                        clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                        if (clonedBean instanceof ApplicationClientBean) {
                           deployableObject.setRootBean(clonedBean);
                           deployableObject.addRootBean("META-INF/application-client.xml", clonedBean, type);
                        } else if (clonedBean instanceof WeblogicApplicationClientBean) {
                           deployableObject.addRootBean("META-INF/weblogic-application-client.xml", clonedBean, type);
                        }
                     }
                  }
               } else {
                  DescriptorBean clonedBean = ((Descriptor)beans[0].getDescriptor().clone()).getRootBean();
                  deployableObject.addRootBean(module.getId(), clonedBean, type);
                  deployableObject.setRootBean(clonedBean);
               }
            }

            deployableObject.setClassLoader(this.appCtx.getAppClassLoader());
            deployableObject.setVirtualJarFile(this.vjf);
            return deployableObject;
         }
      } catch (IOException var13) {
         return null;
      }
   }

   private EditableDeployableObject processEJBModule(EditableDeployableObjectFactory objectFactory, Module module) throws ToolFailureException {
      ModuleType type = WebLogicModuleType.getTypeFromString(module.getType());
      if (module instanceof ModuleListenerInvoker) {
         module = ((ModuleListenerInvoker)module).getDelegate();
      }

      if (!(module instanceof DeployableObjectInfo)) {
         throw new ToolFailureException("unknown module");
      } else {
         String altDD = ((DeployableObjectInfo)module).getAltDD();

         try {
            EditableDeployableObject deployableObject = objectFactory.createDeployableObject(module.getId(), altDD, type);
            DescriptorBean[] beans = module.getDescriptors();
            DescriptorBean[] var7 = beans;
            int var8 = beans.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               DescriptorBean bean = var7[var9];
               DescriptorBean clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
               if (clonedBean instanceof EjbJarBean) {
                  deployableObject.setRootBean(clonedBean);
                  deployableObject.addRootBean("META-INF/ejb-jar.xml", clonedBean, type);
               } else if (clonedBean instanceof WeblogicEjbJarBean) {
                  deployableObject.addRootBean("META-INF/weblogic-ejb-jar.xml", clonedBean, type);
               } else if (clonedBean instanceof WeblogicRdbmsJarBean) {
                  deployableObject.addRootBean("META-INF/weblogic-cmp-rdbms-jar.xml", clonedBean, type);
               }
            }

            MultiClassFinder ejbClassFinder = new MultiClassFinder();
            String path = this.appCtx.getApplicationFileManager().getSourcePath(module.getId()).getParent();
            ejbClassFinder.addFinder(new ClasspathClassFinder2(path));
            if (this.appClassLoader != null) {
               this.appClassLoader.addClassFinder(ejbClassFinder);
               deployableObject.setClassLoader(new GenericClassLoader(this.appClassLoader));
            } else {
               deployableObject.setClassLoader(new GenericClassLoader(ejbClassFinder));
            }

            File file = this.appCtx.getApplicationFileManager().getSourcePath(module.getId());
            VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(file);
            deployableObject.setVirtualJarFile(vjf);
            return deployableObject;
         } catch (IOException var12) {
            throw new ToolFailureException("unable process EJB module", var12);
         }
      }
   }

   private EditableDeployableObject processWARModule(EditableDeployableObjectFactory objectFactory, Module module) throws ToolFailureException {
      ModuleType type = WebLogicModuleType.getTypeFromString(module.getType());
      if (!(module instanceof ModuleListenerInvoker)) {
         throw new ToolFailureException("unknown module");
      } else {
         Module delegateModule = ((ModuleListenerInvoker)module).getDelegate();
         String moduleURI;
         DeployableObjectInfo web;
         DescriptorBean descriptorBean;
         DescriptorBean clonedwlWebAppBean;
         if (delegateModule instanceof ScopedModuleDriver) {
            Module m = ((ScopedModuleDriver)delegateModule).getDelegate();
            Map map = ((ScopedModuleDriver)delegateModule).getDescriptorMappings();
            moduleURI = null;
            String moduleURI = null;
            if (m instanceof ModuleLocationInfo) {
               moduleURI = ((ModuleLocationInfo)m).getModuleURI();
            }

            if (m instanceof DeployableObjectInfo) {
               web = (DeployableObjectInfo)m;
               moduleURI = ((DeployableObjectInfo)m).getAltDD();

               try {
                  EditableDeployableObject deployableObject = objectFactory.createDeployableObject(m.getId(), moduleURI, type);
                  Iterator keys = map.keySet().iterator();

                  while(keys.hasNext()) {
                     String uri = (String)keys.next();
                     descriptorBean = (DescriptorBean)map.get(uri);
                     clonedwlWebAppBean = ((Descriptor)descriptorBean.getDescriptor().clone()).getRootBean();
                     deployableObject.addRootBean(uri, clonedwlWebAppBean, type);
                     if (clonedwlWebAppBean instanceof WebAppBean) {
                        deployableObject.setRootBean(clonedwlWebAppBean);
                     }
                  }

                  MultiClassFinder moduleClassFinder = new MultiClassFinder();
                  MultiClassFinder resourceFinder = new MultiClassFinder();
                  File file = this.appCtx.getApplicationFileManager().getSourcePath(moduleURI);
                  boolean archived = !file.isDirectory() && WarDetector.instance.suffixed(file.getName());
                  VirtualJarFile vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile(moduleURI);
                  web.populateViewFinders(this.baseDir, this.ctx.getLightWeightAppName(), archived, vjf, this.appCtx.getSplitDirectoryInfo(), moduleClassFinder, resourceFinder);
                  GenericClassLoader gcl;
                  if (this.appClassLoader != null) {
                     gcl = new GenericClassLoader(moduleClassFinder, this.appClassLoader);
                     deployableObject.setClassLoader(gcl);
                  } else {
                     gcl = new GenericClassLoader(moduleClassFinder);
                     deployableObject.setClassLoader(gcl);
                  }

                  deployableObject.setResourceFinder(resourceFinder);
                  if (archived) {
                     VirtualJarFile archivedVJF = VirtualJarFactory.createVirtualJar(new JarFile(file));
                     deployableObject.setVirtualJarFile(archivedVJF);
                  } else {
                     deployableObject.setVirtualJarFile(vjf);
                  }

                  return deployableObject;
               } catch (IOException var18) {
                  throw new ToolFailureException("unable process WAR module", var18);
               } catch (IllegalSpecVersionTypeException var19) {
                  throw new ToolFailureException("Illegal web library spec version whilebuilding light weight deployment view for " + moduleURI, var19);
               }
            }
         }

         if (!(delegateModule instanceof DeployableObjectInfo)) {
            throw new ToolFailureException("unable process WAR module");
         } else {
            DeployableObjectInfo web = (DeployableObjectInfo)delegateModule;
            String altDD = web.getAltDD();
            moduleURI = null;
            if (web instanceof ModuleLocationInfo) {
               moduleURI = ((ModuleLocationInfo)web).getModuleURI();
            }

            try {
               EditableDeployableObject deployableObject = objectFactory.createDeployableObject(delegateModule.getId(), altDD, type);
               web = null;
               DescriptorBean[] var10 = delegateModule.getDescriptors();
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  descriptorBean = var10[var12];
                  if (descriptorBean instanceof WebAppBean) {
                     DescriptorBean clonedwebAppBean = ((Descriptor)descriptorBean.getDescriptor().clone()).getRootBean();
                     deployableObject.setRootBean(clonedwebAppBean);
                     deployableObject.addRootBean("WEB-INF/web.xml", clonedwebAppBean, type);
                  }

                  if (descriptorBean instanceof WeblogicWebAppBean && descriptorBean != null) {
                     clonedwlWebAppBean = ((Descriptor)descriptorBean.getDescriptor().clone()).getRootBean();
                     deployableObject.addRootBean("WEB-INF/weblogic.xml", clonedwlWebAppBean, type);
                  }
               }

               MultiClassFinder moduleClassFinder = new MultiClassFinder();
               MultiClassFinder resourceFinder = new MultiClassFinder();
               File file = this.appCtx.getApplicationFileManager().getSourcePath(moduleURI);
               boolean archived = !file.isDirectory() && WarDetector.instance.suffixed(file.getName());
               VirtualJarFile vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile(moduleURI);
               web.populateViewFinders(this.baseDir, this.ctx.getLightWeightAppName(), archived, vjf, this.appCtx.getSplitDirectoryInfo(), moduleClassFinder, resourceFinder);
               GenericClassLoader gcl;
               if (this.appClassLoader != null) {
                  gcl = new GenericClassLoader(moduleClassFinder, this.appClassLoader);
                  deployableObject.setClassLoader(gcl);
               } else {
                  gcl = new GenericClassLoader(moduleClassFinder);
                  deployableObject.setClassLoader(gcl);
               }

               deployableObject.setResourceFinder(resourceFinder);
               if (archived) {
                  VirtualJarFile archivedVJF = VirtualJarFactory.createVirtualJar(new JarFile(file));
                  deployableObject.setVirtualJarFile(archivedVJF);
               } else {
                  deployableObject.setVirtualJarFile(vjf);
               }

               return deployableObject;
            } catch (IOException var20) {
               throw new ToolFailureException("unable process WAR module", var20);
            } catch (IllegalSpecVersionTypeException var21) {
               throw new ToolFailureException("Illegal web library spec version whilebuilding light weight deployment view for " + moduleURI, var21);
            }
         }
      }
   }

   private EditableDeployableObject processCustomModule(EditableDeployableObjectFactory objectFactory, Module module) throws ToolFailureException {
      ModuleType type = WebLogicModuleType.getTypeFromString(module.getType());
      if (!(module instanceof ModuleListenerInvoker)) {
         throw new ToolFailureException("unknown module");
      } else {
         Module delegateModule = ((ModuleListenerInvoker)module).getDelegate();
         if (delegateModule instanceof DefaultModule) {
            DefaultModule custom = (DefaultModule)delegateModule;
            Map map = custom.getDescriptorMappings();
            if (map != null) {
               Iterator keys = map.keySet().iterator();

               while(keys.hasNext()) {
                  String uri = (String)keys.next();
                  DescriptorBean bean = (DescriptorBean)map.get(uri);
                  DescriptorBean clonedBean = ((Descriptor)bean.getDescriptor().clone()).getRootBean();
                  this.deployableApplication.addRootBean(uri, clonedBean, type);
               }
            }
         } else if (delegateModule.getDescriptors() != null && delegateModule.getDescriptors().length > 0) {
            DescriptorBean clonedBean = ((Descriptor)delegateModule.getDescriptors()[0].getDescriptor().clone()).getRootBean();
            this.deployableApplication.addRootBean(delegateModule.getId(), clonedBean, type);
         }

         return null;
      }
   }

   public void cleanup() throws ToolFailureException {
      if (this.appClassLoader != null) {
         this.appClassLoader.close();
      }

      if (this.baseDir.exists()) {
         FileUtils.remove(this.baseDir);
      }

   }
}
