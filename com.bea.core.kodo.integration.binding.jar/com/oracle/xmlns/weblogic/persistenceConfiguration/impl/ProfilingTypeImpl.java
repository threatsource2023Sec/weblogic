package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExportProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LocalProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProfilingType;
import javax.xml.namespace.QName;

public class ProfilingTypeImpl extends XmlComplexContentImpl implements ProfilingType {
   private static final long serialVersionUID = 1L;
   private static final QName NONEPROFILING$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-profiling");
   private static final QName LOCALPROFILING$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "local-profiling");
   private static final QName EXPORTPROFILING$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "export-profiling");
   private static final QName GUIPROFILING$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gui-profiling");

   public ProfilingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public NoneProfilingType getNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONEPROFILING$0) != 0;
      }
   }

   public void setNoneProfiling(NoneProfilingType noneProfiling) {
      this.generatedSetterHelperImpl(noneProfiling, NONEPROFILING$0, 0, (short)1);
   }

   public NoneProfilingType addNewNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().add_element_user(NONEPROFILING$0);
         return target;
      }
   }

   public void setNilNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$0, 0);
         if (target == null) {
            target = (NoneProfilingType)this.get_store().add_element_user(NONEPROFILING$0);
         }

         target.setNil();
      }
   }

   public void unsetNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONEPROFILING$0, 0);
      }
   }

   public LocalProfilingType getLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALPROFILING$2) != 0;
      }
   }

   public void setLocalProfiling(LocalProfilingType localProfiling) {
      this.generatedSetterHelperImpl(localProfiling, LOCALPROFILING$2, 0, (short)1);
   }

   public LocalProfilingType addNewLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().add_element_user(LOCALPROFILING$2);
         return target;
      }
   }

   public void setNilLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$2, 0);
         if (target == null) {
            target = (LocalProfilingType)this.get_store().add_element_user(LOCALPROFILING$2);
         }

         target.setNil();
      }
   }

   public void unsetLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALPROFILING$2, 0);
      }
   }

   public ExportProfilingType getExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXPORTPROFILING$4) != 0;
      }
   }

   public void setExportProfiling(ExportProfilingType exportProfiling) {
      this.generatedSetterHelperImpl(exportProfiling, EXPORTPROFILING$4, 0, (short)1);
   }

   public ExportProfilingType addNewExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().add_element_user(EXPORTPROFILING$4);
         return target;
      }
   }

   public void setNilExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$4, 0);
         if (target == null) {
            target = (ExportProfilingType)this.get_store().add_element_user(EXPORTPROFILING$4);
         }

         target.setNil();
      }
   }

   public void unsetExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXPORTPROFILING$4, 0);
      }
   }

   public GuiProfilingType getGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GUIPROFILING$6) != 0;
      }
   }

   public void setGuiProfiling(GuiProfilingType guiProfiling) {
      this.generatedSetterHelperImpl(guiProfiling, GUIPROFILING$6, 0, (short)1);
   }

   public GuiProfilingType addNewGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().add_element_user(GUIPROFILING$6);
         return target;
      }
   }

   public void setNilGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$6, 0);
         if (target == null) {
            target = (GuiProfilingType)this.get_store().add_element_user(GUIPROFILING$6);
         }

         target.setNil();
      }
   }

   public void unsetGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GUIPROFILING$6, 0);
      }
   }
}
