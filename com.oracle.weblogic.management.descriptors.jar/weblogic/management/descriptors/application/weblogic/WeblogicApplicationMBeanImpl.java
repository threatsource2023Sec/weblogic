package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.application.ModuleMBean;
import weblogic.management.tools.ToXML;

public class WeblogicApplicationMBeanImpl extends XMLElementMBeanDelegate implements WeblogicApplicationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_startups = false;
   private List startups;
   private boolean isSet_parameters = false;
   private List parameters;
   private boolean isSet_modules = false;
   private List modules;
   private boolean isSet_encoding = false;
   private String encoding;
   private boolean isSet_security = false;
   private SecurityMBean security;
   private boolean isSet_listeners = false;
   private List listeners;
   private boolean isSet_classloaderStructure = false;
   private ClassloaderStructureMBean classloaderStructure;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_xml = false;
   private XMLMBean xml;
   private boolean isSet_shutdowns = false;
   private List shutdowns;
   private boolean isSet_libraries = false;
   private List libraries;
   private boolean isSet_moduleProviders = false;
   private List moduleProviders;
   private boolean isSet_ejb = false;
   private EjbMBean ejb;

   public StartupMBean[] getStartups() {
      if (this.startups == null) {
         return new StartupMBean[0];
      } else {
         StartupMBean[] result = new StartupMBean[this.startups.size()];
         result = (StartupMBean[])((StartupMBean[])this.startups.toArray(result));
         return result;
      }
   }

   public void setStartups(StartupMBean[] value) {
      StartupMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getStartups();
      }

      this.isSet_startups = true;
      if (this.startups == null) {
         this.startups = Collections.synchronizedList(new ArrayList());
      } else {
         this.startups.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.startups.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Startups", _oldVal, this.getStartups());
      }

   }

   public void addStartup(StartupMBean value) {
      this.isSet_startups = true;
      if (this.startups == null) {
         this.startups = Collections.synchronizedList(new ArrayList());
      }

      this.startups.add(value);
   }

   public void removeStartup(StartupMBean value) {
      if (this.startups != null) {
         this.startups.remove(value);
      }
   }

   public ApplicationParamMBean[] getParameters() {
      if (this.parameters == null) {
         return new ApplicationParamMBean[0];
      } else {
         ApplicationParamMBean[] result = new ApplicationParamMBean[this.parameters.size()];
         result = (ApplicationParamMBean[])((ApplicationParamMBean[])this.parameters.toArray(result));
         return result;
      }
   }

   public void setParameters(ApplicationParamMBean[] value) {
      ApplicationParamMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getParameters();
      }

      this.isSet_parameters = true;
      if (this.parameters == null) {
         this.parameters = Collections.synchronizedList(new ArrayList());
      } else {
         this.parameters.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.parameters.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Parameters", _oldVal, this.getParameters());
      }

   }

   public void addParameter(ApplicationParamMBean value) {
      this.isSet_parameters = true;
      if (this.parameters == null) {
         this.parameters = Collections.synchronizedList(new ArrayList());
      }

      this.parameters.add(value);
   }

   public void removeParameter(ApplicationParamMBean value) {
      if (this.parameters != null) {
         this.parameters.remove(value);
      }
   }

   public ModuleMBean[] getModules() {
      if (this.modules == null) {
         return new ModuleMBean[0];
      } else {
         ModuleMBean[] result = new ModuleMBean[this.modules.size()];
         result = (ModuleMBean[])((ModuleMBean[])this.modules.toArray(result));
         return result;
      }
   }

   public void setModules(ModuleMBean[] value) {
      ModuleMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getModules();
      }

      this.isSet_modules = true;
      if (this.modules == null) {
         this.modules = Collections.synchronizedList(new ArrayList());
      } else {
         this.modules.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.modules.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Modules", _oldVal, this.getModules());
      }

   }

   public void addModule(ModuleMBean value) {
      this.isSet_modules = true;
      if (this.modules == null) {
         this.modules = Collections.synchronizedList(new ArrayList());
      }

      this.modules.add(value);
   }

   public void removeModule(ModuleMBean value) {
      if (this.modules != null) {
         this.modules.remove(value);
      }
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public SecurityMBean getSecurity() {
      return this.security;
   }

   public void setSecurity(SecurityMBean value) {
      SecurityMBean old = this.security;
      this.security = value;
      this.isSet_security = value != null;
      this.checkChange("security", old, this.security);
   }

   public ListenerMBean[] getListeners() {
      if (this.listeners == null) {
         return new ListenerMBean[0];
      } else {
         ListenerMBean[] result = new ListenerMBean[this.listeners.size()];
         result = (ListenerMBean[])((ListenerMBean[])this.listeners.toArray(result));
         return result;
      }
   }

   public void setListeners(ListenerMBean[] value) {
      ListenerMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getListeners();
      }

      this.isSet_listeners = true;
      if (this.listeners == null) {
         this.listeners = Collections.synchronizedList(new ArrayList());
      } else {
         this.listeners.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.listeners.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Listeners", _oldVal, this.getListeners());
      }

   }

   public void addListener(ListenerMBean value) {
      this.isSet_listeners = true;
      if (this.listeners == null) {
         this.listeners = Collections.synchronizedList(new ArrayList());
      }

      this.listeners.add(value);
   }

   public void removeListener(ListenerMBean value) {
      if (this.listeners != null) {
         this.listeners.remove(value);
      }
   }

   public ClassloaderStructureMBean getClassloaderStructure() {
      return this.classloaderStructure;
   }

   public void setClassloaderStructure(ClassloaderStructureMBean value) {
      ClassloaderStructureMBean old = this.classloaderStructure;
      this.classloaderStructure = value;
      this.isSet_classloaderStructure = value != null;
      this.checkChange("classloaderStructure", old, this.classloaderStructure);
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.version;
      this.version = value;
      this.isSet_version = value != null;
      this.checkChange("version", old, this.version);
   }

   public XMLMBean getXML() {
      return this.xml;
   }

   public void setXML(XMLMBean value) {
      XMLMBean old = this.xml;
      this.xml = value;
      this.isSet_xml = value != null;
      this.checkChange("xml", old, this.xml);
   }

   public ShutdownMBean[] getShutdowns() {
      if (this.shutdowns == null) {
         return new ShutdownMBean[0];
      } else {
         ShutdownMBean[] result = new ShutdownMBean[this.shutdowns.size()];
         result = (ShutdownMBean[])((ShutdownMBean[])this.shutdowns.toArray(result));
         return result;
      }
   }

   public void setShutdowns(ShutdownMBean[] value) {
      ShutdownMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getShutdowns();
      }

      this.isSet_shutdowns = true;
      if (this.shutdowns == null) {
         this.shutdowns = Collections.synchronizedList(new ArrayList());
      } else {
         this.shutdowns.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.shutdowns.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Shutdowns", _oldVal, this.getShutdowns());
      }

   }

   public void addShutdown(ShutdownMBean value) {
      this.isSet_shutdowns = true;
      if (this.shutdowns == null) {
         this.shutdowns = Collections.synchronizedList(new ArrayList());
      }

      this.shutdowns.add(value);
   }

   public void removeShutdown(ShutdownMBean value) {
      if (this.shutdowns != null) {
         this.shutdowns.remove(value);
      }
   }

   public LibraryRefMBean[] getLibraries() {
      if (this.libraries == null) {
         return new LibraryRefMBean[0];
      } else {
         LibraryRefMBean[] result = new LibraryRefMBean[this.libraries.size()];
         result = (LibraryRefMBean[])((LibraryRefMBean[])this.libraries.toArray(result));
         return result;
      }
   }

   public void setLibraries(LibraryRefMBean[] value) {
      LibraryRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getLibraries();
      }

      this.isSet_libraries = true;
      if (this.libraries == null) {
         this.libraries = Collections.synchronizedList(new ArrayList());
      } else {
         this.libraries.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.libraries.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Libraries", _oldVal, this.getLibraries());
      }

   }

   public void addLibrary(LibraryRefMBean value) {
      this.isSet_libraries = true;
      if (this.libraries == null) {
         this.libraries = Collections.synchronizedList(new ArrayList());
      }

      this.libraries.add(value);
   }

   public void removeLibrary(LibraryRefMBean value) {
      if (this.libraries != null) {
         this.libraries.remove(value);
      }
   }

   public ModuleProviderMBean[] getModuleProviders() {
      if (this.moduleProviders == null) {
         return new ModuleProviderMBean[0];
      } else {
         ModuleProviderMBean[] result = new ModuleProviderMBean[this.moduleProviders.size()];
         result = (ModuleProviderMBean[])((ModuleProviderMBean[])this.moduleProviders.toArray(result));
         return result;
      }
   }

   public void setModuleProviders(ModuleProviderMBean[] value) {
      ModuleProviderMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getModuleProviders();
      }

      this.isSet_moduleProviders = true;
      if (this.moduleProviders == null) {
         this.moduleProviders = Collections.synchronizedList(new ArrayList());
      } else {
         this.moduleProviders.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.moduleProviders.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ModuleProviders", _oldVal, this.getModuleProviders());
      }

   }

   public void addModuleProvider(ModuleProviderMBean value) {
      this.isSet_moduleProviders = true;
      if (this.moduleProviders == null) {
         this.moduleProviders = Collections.synchronizedList(new ArrayList());
      }

      this.moduleProviders.add(value);
   }

   public void removeModuleProvider(ModuleProviderMBean value) {
      if (this.moduleProviders != null) {
         this.moduleProviders.remove(value);
      }
   }

   public EjbMBean getEjb() {
      return this.ejb;
   }

   public void setEjb(EjbMBean value) {
      EjbMBean old = this.ejb;
      this.ejb = value;
      this.isSet_ejb = value != null;
      this.checkChange("ejb", old, this.ejb);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-application");
      result.append(">\n");
      if (null != this.getEjb()) {
         result.append(this.getEjb().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getXML()) {
         result.append(this.getXML().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getSecurity()) {
         result.append(this.getSecurity().toXML(indentLevel + 2)).append("\n");
      }

      int i;
      if (null != this.getParameters()) {
         for(i = 0; i < this.getParameters().length; ++i) {
            result.append(this.getParameters()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getClassloaderStructure()) {
         result.append(this.getClassloaderStructure().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getListeners()) {
         for(i = 0; i < this.getListeners().length; ++i) {
            result.append(this.getListeners()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getStartups()) {
         for(i = 0; i < this.getStartups().length; ++i) {
            result.append(this.getStartups()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getShutdowns()) {
         for(i = 0; i < this.getShutdowns().length; ++i) {
            result.append(this.getShutdowns()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getModuleProviders()) {
         for(i = 0; i < this.getModuleProviders().length; ++i) {
            result.append(this.getModuleProviders()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getModules()) {
         for(i = 0; i < this.getModules().length; ++i) {
            result.append(this.getModules()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getLibraries()) {
         for(i = 0; i < this.getLibraries().length; ++i) {
            result.append(this.getLibraries()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-application>\n");
      return result.toString();
   }
}
