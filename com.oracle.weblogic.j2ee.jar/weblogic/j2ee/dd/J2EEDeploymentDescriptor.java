package weblogic.j2ee.dd;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.TopLevelDescriptorMBean;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.application.ConnectorModuleMBean;
import weblogic.management.descriptors.application.EJBModuleMBean;
import weblogic.management.descriptors.application.J2EEApplicationDescriptorMBean;
import weblogic.management.descriptors.application.JavaModuleMBean;
import weblogic.management.descriptors.application.ModuleMBean;
import weblogic.management.descriptors.application.SecurityRoleMBean;
import weblogic.management.descriptors.application.WebModuleMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.utils.Debug;
import weblogic.utils.application.WarDetector;
import weblogic.utils.io.XMLWriter;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class J2EEDeploymentDescriptor extends XMLElementMBeanDelegate implements J2EEApplicationDescriptorMBean, TopLevelDescriptorMBean, UIMBean {
   private static final long serialVersionUID = 5882884876092791011L;
   private static boolean debug = false;
   private String version;
   private String description;
   private String displayName;
   private String largeIconFileName;
   private String smallIconFileName;
   private ArrayList orderedModules;
   private HashMap roleDescriptors;
   private String j2eeAppDescrEncoding;
   private String descriptorVersion;

   public void setEncoding(String encoding) {
      String old = this.j2eeAppDescrEncoding;
      this.j2eeAppDescrEncoding = encoding;
      this.checkChange("encoding", old, this.j2eeAppDescrEncoding);
   }

   public String getEncoding() {
      return this.j2eeAppDescrEncoding;
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public void setVersion(String version) {
      this.descriptorVersion = version;
   }

   public J2EEDeploymentDescriptor() {
      this.orderedModules = new ArrayList();
      this.roleDescriptors = new HashMap();
      this.j2eeAppDescrEncoding = null;
      this.descriptorVersion = null;
   }

   public J2EEDeploymentDescriptor(JarFile jar) throws IOException {
      this(VirtualJarFactory.createVirtualJar(jar));
   }

   public J2EEDeploymentDescriptor(VirtualJarFile jar) {
      this.orderedModules = new ArrayList();
      this.roleDescriptors = new HashMap();
      this.j2eeAppDescrEncoding = null;
      this.descriptorVersion = null;
      Iterator entries = jar.entries();

      while(entries.hasNext()) {
         JarEntry entry = (JarEntry)entries.next();
         String uri = entry.getName();
         if (uri.indexOf(47) == uri.lastIndexOf(47)) {
            if (uri.startsWith("/")) {
               uri = uri.substring(1, uri.length());
            }

            ModuleDescriptor module = null;
            if (WarDetector.instance.suffixed(uri)) {
               if (debug) {
                  Debug.say("found web app...");
               }

               module = new WebModuleDescriptor(uri, (String)null);
            } else if (uri.endsWith(".jar")) {
               module = new EJBModuleDescriptor(uri);
            } else {
               String uriPath = uri;
               if (!uri.endsWith("/")) {
                  uriPath = uri + "/";
               }

               String ejbDD = uriPath + "META-INF/ejb-jar.xml";
               String webDD = uriPath + "WEB-INF/web.xml";
               if (jar.getEntry(webDD) != null) {
                  module = new WebModuleDescriptor(uri, uri);
               } else if (jar.getEntry(ejbDD) != null) {
                  module = new EJBModuleDescriptor(uri);
               }
            }

            if (module != null) {
               this.addModuleDescriptor((ModuleDescriptor)module);
            }
         }
      }

      this.displayName = jar.getName();
      this.description = "Exploded J2EE Application, " + this.displayName;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String d) {
      if (debug) {
         Debug.say("setDescription(" + d + ")");
      }

      String old = this.description;
      this.description = d;
      this.checkChange("description", old, d);
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String d) {
      if (debug) {
         Debug.say("setDisplayName(" + d + ")");
      }

      String old = this.displayName;
      this.displayName = d;
      this.checkChange("displayName", old, d);
   }

   public String getLargeIconFileName() {
      return this.largeIconFileName;
   }

   public void setLargeIconFileName(String fileName) {
      if (debug) {
         Debug.say("setLargeIconFileName(" + fileName + ")");
      }

      String old = this.largeIconFileName;
      this.largeIconFileName = fileName;
      this.checkChange("largeIconFileName", old, this.largeIconFileName);
   }

   public String getSmallIconFileName() {
      return this.smallIconFileName;
   }

   public void setSmallIconFileName(String fileName) {
      if (debug) {
         Debug.say("setSmallIconFileName(" + fileName + ")");
      }

      String old = this.smallIconFileName;
      this.smallIconFileName = fileName;
      this.checkChange("smallIconFileName", old, this.smallIconFileName);
   }

   public void setJ2EEVersion(String version) {
      String old = this.version;
      this.version = version;
      this.checkChange("j2eeVersion", old, version);
   }

   public void addModuleDescriptor(ModuleDescriptor module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0, module.getName() != null ? module.getName() : "<null>");
      this.addModuleDescriptor(module, module.getName());
   }

   private void addModuleDescriptor(ModuleDescriptor module, String name) {
      this.orderedModules.add(module);
   }

   public void addModule(ModuleMBean module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0);
      this.addModuleDescriptor((ModuleDescriptor)module, module.getModuleKey());
      Debug.assertion(Arrays.asList((Object[])this.getModules()).contains(module));
   }

   public void addEJBModule(EJBModuleMBean module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0);
      this.addModule(module);
      Debug.assertion(Arrays.asList((Object[])this.getEJBModules()).contains(module));
   }

   public void addJavaModule(JavaModuleMBean module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0);
      this.addModule(module);
      Debug.assertion(Arrays.asList((Object[])this.getJavaModules()).contains(module));
   }

   public void addWebModule(WebModuleMBean module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0);
      this.addModule(module);
      Debug.assertion(Arrays.asList((Object[])this.getWebModules()).contains(module));
   }

   public void addConnectorModule(ConnectorModuleMBean module) {
      Debug.assertion(module.getName() != null && module.getName().length() > 0);
      this.addModule(module);
      Debug.assertion(Arrays.asList((Object[])this.getConnectorModules()).contains(module));
   }

   private void removeModule(ModuleMBean module) {
      int index = this.orderedModules.indexOf(module);
      if (index > -1) {
         this.orderedModules.remove(index);
      }

   }

   public ModuleDescriptor removeBean(String name) {
      ModuleDescriptor beanToRemove = this.getModule(name);
      int index = this.orderedModules.indexOf(beanToRemove);
      Object result = this.orderedModules.remove(index);
      if (debug) {
         if (result != null) {
            Debug.say("FOUND!");
         } else {
            Debug.say("NOT FOUND!");
         }
      }

      return (ModuleDescriptor)result;
   }

   public void removeEJBModule(EJBModuleMBean module) {
      this.removeModule(module);
      Debug.assertion(!Arrays.asList((Object[])this.getEJBModules()).contains(module));
   }

   public void removeJavaModule(JavaModuleMBean module) {
      this.removeModule(module);
      Debug.assertion(!Arrays.asList((Object[])this.getJavaModules()).contains(module));
   }

   public void removeWebModule(WebModuleMBean module) {
      this.removeModule(module);
      Debug.assertion(!Arrays.asList((Object[])this.getWebModules()).contains(module));
   }

   public void removeConnectorModule(ConnectorModuleMBean module) {
      this.removeModule(module);
      Debug.assertion(!Arrays.asList((Object[])this.getConnectorModules()).contains(module));
   }

   public ModuleDescriptor getModule(String name) {
      ModuleDescriptor result = null;
      Iterator i = this.orderedModules.iterator();

      while(i.hasNext()) {
         ModuleDescriptor temp = (ModuleDescriptor)i.next();
         if (temp.getURI().equals(name)) {
            result = temp;
            break;
         }
      }

      return result;
   }

   public void setModules(ModuleMBean[] mods) {
      ModuleMBean[] old = this.getModules();
      this.orderedModules.clear();

      for(int i = 0; i < mods.length; ++i) {
         this.addModuleDescriptor((ModuleDescriptor)mods[i]);
      }

      this.checkChange("modules", old, mods);
   }

   public ModuleMBean[] getWebModules() {
      List l = new ArrayList();
      Iterator i = this.getAllModules().iterator();

      while(true) {
         Object m;
         do {
            if (!i.hasNext()) {
               ModuleMBean[] dummy = new ModuleMBean[0];
               return (ModuleMBean[])((ModuleMBean[])l.toArray(dummy));
            }

            m = i.next();
         } while(!(m instanceof WebModuleMBean));

         l.add(m);
         Debug.assertion(((ModuleMBean)m).getName() != null && ((ModuleMBean)m).getName().length() > 0);
      }
   }

   public ModuleMBean[] getEJBModules() {
      List l = new ArrayList();
      Iterator i = this.getAllModules().iterator();

      while(true) {
         Object m;
         do {
            if (!i.hasNext()) {
               ModuleMBean[] dummy = new ModuleMBean[0];
               return (ModuleMBean[])((ModuleMBean[])l.toArray(dummy));
            }

            m = i.next();
         } while(!(m instanceof EJBModuleMBean));

         l.add(m);
         Debug.assertion(((ModuleMBean)m).getName() != null && ((ModuleMBean)m).getName().length() > 0);
      }
   }

   public ModuleMBean[] getJavaModules() {
      List l = new ArrayList();
      Iterator i = this.getAllModules().iterator();

      while(true) {
         Object m;
         do {
            if (!i.hasNext()) {
               ModuleMBean[] dummy = new ModuleMBean[0];
               return (ModuleMBean[])((ModuleMBean[])l.toArray(dummy));
            }

            m = i.next();
         } while(!(m instanceof JavaModuleMBean));

         l.add(m);
         Debug.assertion(((ModuleMBean)m).getName() != null && ((ModuleMBean)m).getName().length() > 0);
      }
   }

   public ModuleMBean[] getConnectorModules() {
      List l = new ArrayList();
      Iterator i = this.getAllModules().iterator();

      while(true) {
         Object m;
         do {
            if (!i.hasNext()) {
               ModuleMBean[] dummy = new ModuleMBean[0];
               return (ModuleMBean[])((ModuleMBean[])l.toArray(dummy));
            }

            m = i.next();
         } while(!(m instanceof ConnectorModuleMBean));

         l.add(m);
         Debug.assertion(((ModuleMBean)m).getName() != null && ((ModuleMBean)m).getName().length() > 0);
      }
   }

   public ModuleMBean[] getModules() {
      ModuleMBean[] dummy = new ModuleMBean[0];
      return (ModuleMBean[])((ModuleMBean[])this.orderedModules.toArray(dummy));
   }

   public Collection getAllModules() {
      return this.orderedModules;
   }

   private ModuleMBean[] getModules(Class type) {
      ModuleMBean[] dummy = new ModuleMBean[0];
      List result = new ArrayList();
      Iterator i = this.orderedModules.iterator();

      while(i.hasNext()) {
         ModuleMBean m = (ModuleMBean)i.next();
         if (debug) {
            Debug.say("module mbean is " + m + ", with getClass=" + m.getClass());
         }

         Debug.assertion(m.getName() != null);
         if (m.getClass() == type) {
            result.add(m);
         }
      }

      return (ModuleMBean[])((ModuleMBean[])result.toArray(dummy));
   }

   public SecurityRoleMBean[] getSecurityRoles() {
      SecurityRoleMBean[] dummy = new SecurityRoleMBean[0];
      return (SecurityRoleMBean[])((SecurityRoleMBean[])this.roleDescriptors.values().toArray(dummy));
   }

   public void setSecurityRoles(SecurityRoleMBean[] rs) {
      SecurityRoleMBean[] old = this.getSecurityRoles();
      this.roleDescriptors = new HashMap();

      for(int i = 0; i < rs.length; ++i) {
         this.roleDescriptors.put(rs[i].getName(), rs[i]);
      }

      this.checkChange("securityRoles", old, rs);
   }

   public RoleDescriptor getRoleDescriptor(String roleName) {
      return (RoleDescriptor)this.roleDescriptors.get(roleName);
   }

   public Collection getAllRoleDescriptors() {
      return this.roleDescriptors.values();
   }

   public void addRoleDescriptor(RoleDescriptor rd) {
      this.addRoleDescriptor(rd, rd.getName());
   }

   private void addRoleDescriptor(RoleDescriptor rd, String name) {
      this.roleDescriptors.put(name, rd);
   }

   public void addSecurityRole(SecurityRoleMBean rd) {
      this.addRoleDescriptor((RoleDescriptor)rd, rd.getRoleName());
      if (debug) {
         Debug.say("add security role ... roles descriptors ...\n" + this.roleDescriptors.values());
      }

   }

   public void removeSecurityRole(SecurityRoleMBean rd) {
      if (this.removeRoleDescriptor(((RoleDescriptor)rd).getRoleName()) == null && debug) {
         Debug.say("REALLY NOT FOUND!");
      }

   }

   public RoleDescriptor removeRoleDescriptor(String roleName) {
      RoleDescriptor r = (RoleDescriptor)this.roleDescriptors.remove(roleName);
      return r;
   }

   public String toXML(int i) {
      StringWriter sw = new StringWriter();
      XMLWriter x = new XMLWriter(sw);
      x.setIndent(i);
      this.toXML(x);
      return sw.toString();
   }

   public void toXML(XMLWriter x) {
      String descrEncoding = this.getEncoding();
      if (descrEncoding != null) {
         x.println("<?xml version=\"1.0\" encoding=\"" + descrEncoding + "\"?>");
      }

      int j2eeVersion = true;
      x.println("<!DOCTYPE application PUBLIC '-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN' 'http://java.sun.com/dtd/application_1_3.dtd'>");
      x.println("<application>");
      x.incrIndent();
      String smallIcon = this.getSmallIconFileName();
      String largeIcon = this.getLargeIconFileName();
      if (smallIcon != null || largeIcon != null) {
         x.println("<icon>");
         x.incrIndent();
         if (smallIcon != null) {
            x.println("<small-icon>" + smallIcon + "</small-icon>");
         }

         if (largeIcon != null) {
            x.println("<large-icon>" + largeIcon + "</large-icon>");
         }

         x.println("</icon>");
         x.decrIndent();
      }

      String s = this.getDisplayName();
      if (s == null) {
         s = "";
      }

      x.println("<display-name>" + s + "</display-name>");
      if (this.getDescription() != null) {
         x.println("<description>" + this.getDescription() + "</description>");
      }

      if (debug) {
         Debug.say("toXML ... modules ...\n" + this.getAllModules());
      }

      ModuleMBean[] modules = this.getModules();

      for(int i = 0; i < modules.length; ++i) {
         ((ModuleDescriptor)modules[i]).toXML(x);
      }

      if (this.roleDescriptors != null) {
         if (debug) {
            Debug.say("toXML ... roles descriptors ...\n" + this.roleDescriptors);
         }

         Iterator i = this.roleDescriptors.values().iterator();

         while(i.hasNext()) {
            SecurityRoleMBean sm = (SecurityRoleMBean)i.next();
            int indent = x.getIndent();
            x.printNoIndent(sm.toXML(indent));
         }
      }

      x.decrIndent();
      x.println("</application>");
   }

   public String toString() {
      String result = "J2EE Deployment Descriptor for " + this.getDisplayName();
      result = result + "\n Description: " + this.getDescription();
      result = result + "\n Modules: " + this.orderedModules.size();
      result = result + "\n Security Roles: " + this.roleDescriptors.size();
      return result;
   }

   public void validate() throws DescriptorValidationException {
      throw new RuntimeException("NYI");
   }

   public void usePersistenceDestination(String dir) {
      throw new RuntimeException("NYI");
   }

   public void persist() throws IOException {
      throw new RuntimeException("NYI");
   }

   public void persist(Properties changelist) throws IOException {
   }

   public void unregister() throws ManagementException {
      super.unregister();
      Iterator iter = this.getAllRoleDescriptors().iterator();

      while(iter.hasNext()) {
         SecurityRoleMBean sr = (SecurityRoleMBean)iter.next();
         sr.unregister();
      }

      iter = this.getAllModules().iterator();

      while(iter.hasNext()) {
         ModuleMBean mb = (ModuleMBean)iter.next();
         mb.unregister();
      }

   }

   public String toXML() {
      StringWriter w = new StringWriter();
      this.toXML(new XMLWriter(w));
      return w.toString();
   }

   public void addDescriptorError(String s) {
   }

   public void removeDescriptorError(String s) {
   }

   public boolean isValid() {
      return true;
   }

   public String[] getDescriptorErrors() {
      return null;
   }

   public void setDescriptorErrors(String[] x) {
   }
}
