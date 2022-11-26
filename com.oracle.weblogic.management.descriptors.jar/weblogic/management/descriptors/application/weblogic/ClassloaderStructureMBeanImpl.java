package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ClassloaderStructureMBeanImpl extends XMLElementMBeanDelegate implements ClassloaderStructureMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_classloaderStructures = false;
   private List classloaderStructures;
   private boolean isSet_moduleRefs = false;
   private List moduleRefs;

   public ClassloaderStructureMBean[] getClassloaderStructures() {
      if (this.classloaderStructures == null) {
         return new ClassloaderStructureMBean[0];
      } else {
         ClassloaderStructureMBean[] result = new ClassloaderStructureMBean[this.classloaderStructures.size()];
         result = (ClassloaderStructureMBean[])((ClassloaderStructureMBean[])this.classloaderStructures.toArray(result));
         return result;
      }
   }

   public void setClassloaderStructures(ClassloaderStructureMBean[] value) {
      ClassloaderStructureMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getClassloaderStructures();
      }

      this.isSet_classloaderStructures = true;
      if (this.classloaderStructures == null) {
         this.classloaderStructures = Collections.synchronizedList(new ArrayList());
      } else {
         this.classloaderStructures.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.classloaderStructures.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ClassloaderStructures", _oldVal, this.getClassloaderStructures());
      }

   }

   public void addClassloaderStructure(ClassloaderStructureMBean value) {
      this.isSet_classloaderStructures = true;
      if (this.classloaderStructures == null) {
         this.classloaderStructures = Collections.synchronizedList(new ArrayList());
      }

      this.classloaderStructures.add(value);
   }

   public void removeClassloaderStructure(ClassloaderStructureMBean value) {
      if (this.classloaderStructures != null) {
         this.classloaderStructures.remove(value);
      }
   }

   public ModuleRefMBean[] getModuleRefs() {
      if (this.moduleRefs == null) {
         return new ModuleRefMBean[0];
      } else {
         ModuleRefMBean[] result = new ModuleRefMBean[this.moduleRefs.size()];
         result = (ModuleRefMBean[])((ModuleRefMBean[])this.moduleRefs.toArray(result));
         return result;
      }
   }

   public void setModuleRefs(ModuleRefMBean[] value) {
      ModuleRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getModuleRefs();
      }

      this.isSet_moduleRefs = true;
      if (this.moduleRefs == null) {
         this.moduleRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.moduleRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.moduleRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ModuleRefs", _oldVal, this.getModuleRefs());
      }

   }

   public void addModuleRef(ModuleRefMBean value) {
      this.isSet_moduleRefs = true;
      if (this.moduleRefs == null) {
         this.moduleRefs = Collections.synchronizedList(new ArrayList());
      }

      this.moduleRefs.add(value);
   }

   public void removeModuleRef(ModuleRefMBean value) {
      if (this.moduleRefs != null) {
         this.moduleRefs.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<classloader-structure");
      result.append(">\n");
      int i;
      if (null != this.getModuleRefs()) {
         for(i = 0; i < this.getModuleRefs().length; ++i) {
            result.append(this.getModuleRefs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getClassloaderStructures()) {
         for(i = 0; i < this.getClassloaderStructures().length; ++i) {
            result.append(this.getClassloaderStructures()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</classloader-structure>\n");
      return result.toString();
   }
}
