package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroups;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActions;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlags;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEngineDocument;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClasses;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsInstrumentationSupport;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitors;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackages;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsValueRenderers;

public class WlsEngineDocumentImpl extends XmlComplexContentImpl implements WlsEngineDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WLSENGINE$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-engine");

   public WlsEngineDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WlsEngineDocument.WlsEngine getWlsEngine() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsEngineDocument.WlsEngine target = null;
         target = (WlsEngineDocument.WlsEngine)this.get_store().find_element_user(WLSENGINE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWlsEngine(WlsEngineDocument.WlsEngine wlsEngine) {
      this.generatedSetterHelperImpl(wlsEngine, WLSENGINE$0, 0, (short)1);
   }

   public WlsEngineDocument.WlsEngine addNewWlsEngine() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsEngineDocument.WlsEngine target = null;
         target = (WlsEngineDocument.WlsEngine)this.get_store().add_element_user(WLSENGINE$0);
         return target;
      }
   }

   public static class WlsEngineImpl extends XmlComplexContentImpl implements WlsEngineDocument.WlsEngine {
      private static final long serialVersionUID = 1L;
      private static final QName WLSINSTRUMENTATIONSUPPORT$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-instrumentation-support");
      private static final QName WLSENTRYCLASSES$2 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-entry-classes");
      private static final QName WLSDYEFLAGS$4 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-dye-flags");
      private static final QName WLSPACKAGES$6 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-packages");
      private static final QName WLSACTIONS$8 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-actions");
      private static final QName WLSACTIONGROUPS$10 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-action-groups");
      private static final QName WLSVALUERENDERERS$12 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-value-renderers");
      private static final QName WLSPOINTCUTS$14 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-pointcuts");
      private static final QName WLSMONITORS$16 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-monitors");
      private static final QName NAME$18 = new QName("", "name");
      private static final QName PARENT$20 = new QName("", "parent");

      public WlsEngineImpl(SchemaType sType) {
         super(sType);
      }

      public WlsInstrumentationSupport getWlsInstrumentationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsInstrumentationSupport target = null;
            target = (WlsInstrumentationSupport)this.get_store().find_element_user(WLSINSTRUMENTATIONSUPPORT$0, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetWlsInstrumentationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(WLSINSTRUMENTATIONSUPPORT$0) != 0;
         }
      }

      public void setWlsInstrumentationSupport(WlsInstrumentationSupport wlsInstrumentationSupport) {
         this.generatedSetterHelperImpl(wlsInstrumentationSupport, WLSINSTRUMENTATIONSUPPORT$0, 0, (short)1);
      }

      public WlsInstrumentationSupport addNewWlsInstrumentationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsInstrumentationSupport target = null;
            target = (WlsInstrumentationSupport)this.get_store().add_element_user(WLSINSTRUMENTATIONSUPPORT$0);
            return target;
         }
      }

      public void unsetWlsInstrumentationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WLSINSTRUMENTATIONSUPPORT$0, 0);
         }
      }

      public WlsEntryClasses getWlsEntryClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsEntryClasses target = null;
            target = (WlsEntryClasses)this.get_store().find_element_user(WLSENTRYCLASSES$2, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetWlsEntryClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(WLSENTRYCLASSES$2) != 0;
         }
      }

      public void setWlsEntryClasses(WlsEntryClasses wlsEntryClasses) {
         this.generatedSetterHelperImpl(wlsEntryClasses, WLSENTRYCLASSES$2, 0, (short)1);
      }

      public WlsEntryClasses addNewWlsEntryClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsEntryClasses target = null;
            target = (WlsEntryClasses)this.get_store().add_element_user(WLSENTRYCLASSES$2);
            return target;
         }
      }

      public void unsetWlsEntryClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WLSENTRYCLASSES$2, 0);
         }
      }

      public WlsDyeFlags getWlsDyeFlags() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsDyeFlags target = null;
            target = (WlsDyeFlags)this.get_store().find_element_user(WLSDYEFLAGS$4, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetWlsDyeFlags() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(WLSDYEFLAGS$4) != 0;
         }
      }

      public void setWlsDyeFlags(WlsDyeFlags wlsDyeFlags) {
         this.generatedSetterHelperImpl(wlsDyeFlags, WLSDYEFLAGS$4, 0, (short)1);
      }

      public WlsDyeFlags addNewWlsDyeFlags() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsDyeFlags target = null;
            target = (WlsDyeFlags)this.get_store().add_element_user(WLSDYEFLAGS$4);
            return target;
         }
      }

      public void unsetWlsDyeFlags() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WLSDYEFLAGS$4, 0);
         }
      }

      public WlsPackages getWlsPackages() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsPackages target = null;
            target = (WlsPackages)this.get_store().find_element_user(WLSPACKAGES$6, 0);
            return target == null ? null : target;
         }
      }

      public void setWlsPackages(WlsPackages wlsPackages) {
         this.generatedSetterHelperImpl(wlsPackages, WLSPACKAGES$6, 0, (short)1);
      }

      public WlsPackages addNewWlsPackages() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsPackages target = null;
            target = (WlsPackages)this.get_store().add_element_user(WLSPACKAGES$6);
            return target;
         }
      }

      public WlsActions getWlsActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsActions target = null;
            target = (WlsActions)this.get_store().find_element_user(WLSACTIONS$8, 0);
            return target == null ? null : target;
         }
      }

      public void setWlsActions(WlsActions wlsActions) {
         this.generatedSetterHelperImpl(wlsActions, WLSACTIONS$8, 0, (short)1);
      }

      public WlsActions addNewWlsActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsActions target = null;
            target = (WlsActions)this.get_store().add_element_user(WLSACTIONS$8);
            return target;
         }
      }

      public WlsActionGroups getWlsActionGroups() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsActionGroups target = null;
            target = (WlsActionGroups)this.get_store().find_element_user(WLSACTIONGROUPS$10, 0);
            return target == null ? null : target;
         }
      }

      public void setWlsActionGroups(WlsActionGroups wlsActionGroups) {
         this.generatedSetterHelperImpl(wlsActionGroups, WLSACTIONGROUPS$10, 0, (short)1);
      }

      public WlsActionGroups addNewWlsActionGroups() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsActionGroups target = null;
            target = (WlsActionGroups)this.get_store().add_element_user(WLSACTIONGROUPS$10);
            return target;
         }
      }

      public WlsValueRenderers getWlsValueRenderers() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsValueRenderers target = null;
            target = (WlsValueRenderers)this.get_store().find_element_user(WLSVALUERENDERERS$12, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetWlsValueRenderers() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(WLSVALUERENDERERS$12) != 0;
         }
      }

      public void setWlsValueRenderers(WlsValueRenderers wlsValueRenderers) {
         this.generatedSetterHelperImpl(wlsValueRenderers, WLSVALUERENDERERS$12, 0, (short)1);
      }

      public WlsValueRenderers addNewWlsValueRenderers() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsValueRenderers target = null;
            target = (WlsValueRenderers)this.get_store().add_element_user(WLSVALUERENDERERS$12);
            return target;
         }
      }

      public void unsetWlsValueRenderers() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WLSVALUERENDERERS$12, 0);
         }
      }

      public String getWlsPointcuts() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(WLSPOINTCUTS$14, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetWlsPointcuts() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(WLSPOINTCUTS$14, 0);
            return target;
         }
      }

      public void setWlsPointcuts(String wlsPointcuts) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(WLSPOINTCUTS$14, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(WLSPOINTCUTS$14);
            }

            target.setStringValue(wlsPointcuts);
         }
      }

      public void xsetWlsPointcuts(XmlString wlsPointcuts) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(WLSPOINTCUTS$14, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(WLSPOINTCUTS$14);
            }

            target.set(wlsPointcuts);
         }
      }

      public WlsMonitors getWlsMonitors() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsMonitors target = null;
            target = (WlsMonitors)this.get_store().find_element_user(WLSMONITORS$16, 0);
            return target == null ? null : target;
         }
      }

      public void setWlsMonitors(WlsMonitors wlsMonitors) {
         this.generatedSetterHelperImpl(wlsMonitors, WLSMONITORS$16, 0, (short)1);
      }

      public WlsMonitors addNewWlsMonitors() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WlsMonitors target = null;
            target = (WlsMonitors)this.get_store().add_element_user(WLSMONITORS$16);
            return target;
         }
      }

      public String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$18);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$18);
            return target;
         }
      }

      public boolean isSetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(NAME$18) != null;
         }
      }

      public void setName(String name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$18);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAME$18);
            }

            target.setStringValue(name);
         }
      }

      public void xsetName(XmlString name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$18);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(NAME$18);
            }

            target.set(name);
         }
      }

      public void unsetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(NAME$18);
         }
      }

      public String getParent() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(PARENT$20);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetParent() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(PARENT$20);
            return target;
         }
      }

      public boolean isSetParent() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(PARENT$20) != null;
         }
      }

      public void setParent(String parent) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(PARENT$20);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(PARENT$20);
            }

            target.setStringValue(parent);
         }
      }

      public void xsetParent(XmlString parent) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(PARENT$20);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(PARENT$20);
            }

            target.set(parent);
         }
      }

      public void unsetParent() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(PARENT$20);
         }
      }
   }
}
