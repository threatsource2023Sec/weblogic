package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitor;

public class WlsMonitorImpl extends XmlComplexContentImpl implements WlsMonitor {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("", "type");
   private static final QName SCOPE$2 = new QName("", "scope");
   private static final QName LOCATION$4 = new QName("", "location");
   private static final QName POINTCUT$6 = new QName("", "pointcut");
   private static final QName CODEGENERATOR$8 = new QName("", "code-generator");
   private static final QName ATTRIBUTENAMES$10 = new QName("", "attribute-names");
   private static final QName ACTIONGROUP$12 = new QName("", "action-group");
   private static final QName CAPTUREARGS$14 = new QName("", "capture-args");
   private static final QName CAPTURERETURN$16 = new QName("", "capture-return");
   private static final QName DIAGNOSTICVOLUME$18 = new QName("", "diagnostic-volume");
   private static final QName SERVERMANAGED$20 = new QName("", "server-managed");
   private static final QName EVENTCLASSNAME$22 = new QName("", "event-class-name");

   public WlsMonitorImpl(SchemaType sType) {
      super(sType);
   }

   public String getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPE$0);
         return target;
      }
   }

   public void setType(String type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TYPE$0);
         }

         target.setStringValue(type);
      }
   }

   public void xsetType(XmlString type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPE$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(TYPE$0);
         }

         target.set(type);
      }
   }

   public String getScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SCOPE$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SCOPE$2);
         return target;
      }
   }

   public void setScope(String scope) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SCOPE$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SCOPE$2);
         }

         target.setStringValue(scope);
      }
   }

   public void xsetScope(XmlString scope) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SCOPE$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(SCOPE$2);
         }

         target.set(scope);
      }
   }

   public String getLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LOCATION$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(LOCATION$4);
         return target;
      }
   }

   public void setLocation(String location) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LOCATION$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(LOCATION$4);
         }

         target.setStringValue(location);
      }
   }

   public void xsetLocation(XmlString location) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(LOCATION$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(LOCATION$4);
         }

         target.set(location);
      }
   }

   public String getPointcut() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(POINTCUT$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPointcut() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(POINTCUT$6);
         return target;
      }
   }

   public void setPointcut(String pointcut) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(POINTCUT$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(POINTCUT$6);
         }

         target.setStringValue(pointcut);
      }
   }

   public void xsetPointcut(XmlString pointcut) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(POINTCUT$6);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(POINTCUT$6);
         }

         target.set(pointcut);
      }
   }

   public String getCodeGenerator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CODEGENERATOR$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCodeGenerator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CODEGENERATOR$8);
         return target;
      }
   }

   public boolean isSetCodeGenerator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(CODEGENERATOR$8) != null;
      }
   }

   public void setCodeGenerator(String codeGenerator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CODEGENERATOR$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CODEGENERATOR$8);
         }

         target.setStringValue(codeGenerator);
      }
   }

   public void xsetCodeGenerator(XmlString codeGenerator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CODEGENERATOR$8);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(CODEGENERATOR$8);
         }

         target.set(codeGenerator);
      }
   }

   public void unsetCodeGenerator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(CODEGENERATOR$8);
      }
   }

   public String getAttributeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ATTRIBUTENAMES$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAttributeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ATTRIBUTENAMES$10);
         return target;
      }
   }

   public boolean isSetAttributeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ATTRIBUTENAMES$10) != null;
      }
   }

   public void setAttributeNames(String attributeNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ATTRIBUTENAMES$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ATTRIBUTENAMES$10);
         }

         target.setStringValue(attributeNames);
      }
   }

   public void xsetAttributeNames(XmlString attributeNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ATTRIBUTENAMES$10);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ATTRIBUTENAMES$10);
         }

         target.set(attributeNames);
      }
   }

   public void unsetAttributeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ATTRIBUTENAMES$10);
      }
   }

   public String getActionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ACTIONGROUP$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetActionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ACTIONGROUP$12);
         return target;
      }
   }

   public boolean isSetActionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ACTIONGROUP$12) != null;
      }
   }

   public void setActionGroup(String actionGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ACTIONGROUP$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ACTIONGROUP$12);
         }

         target.setStringValue(actionGroup);
      }
   }

   public void xsetActionGroup(XmlString actionGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ACTIONGROUP$12);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ACTIONGROUP$12);
         }

         target.set(actionGroup);
      }
   }

   public void unsetActionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ACTIONGROUP$12);
      }
   }

   public boolean getCaptureArgs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CAPTUREARGS$14);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCaptureArgs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(CAPTUREARGS$14);
         return target;
      }
   }

   public boolean isSetCaptureArgs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(CAPTUREARGS$14) != null;
      }
   }

   public void setCaptureArgs(boolean captureArgs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CAPTUREARGS$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CAPTUREARGS$14);
         }

         target.setBooleanValue(captureArgs);
      }
   }

   public void xsetCaptureArgs(XmlBoolean captureArgs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(CAPTUREARGS$14);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(CAPTUREARGS$14);
         }

         target.set(captureArgs);
      }
   }

   public void unsetCaptureArgs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(CAPTUREARGS$14);
      }
   }

   public boolean getCaptureReturn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CAPTURERETURN$16);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCaptureReturn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(CAPTURERETURN$16);
         return target;
      }
   }

   public boolean isSetCaptureReturn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(CAPTURERETURN$16) != null;
      }
   }

   public void setCaptureReturn(boolean captureReturn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CAPTURERETURN$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CAPTURERETURN$16);
         }

         target.setBooleanValue(captureReturn);
      }
   }

   public void xsetCaptureReturn(XmlBoolean captureReturn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(CAPTURERETURN$16);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(CAPTURERETURN$16);
         }

         target.set(captureReturn);
      }
   }

   public void unsetCaptureReturn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(CAPTURERETURN$16);
      }
   }

   public String getDiagnosticVolume() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DIAGNOSTICVOLUME$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDiagnosticVolume() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DIAGNOSTICVOLUME$18);
         return target;
      }
   }

   public boolean isSetDiagnosticVolume() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(DIAGNOSTICVOLUME$18) != null;
      }
   }

   public void setDiagnosticVolume(String diagnosticVolume) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DIAGNOSTICVOLUME$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(DIAGNOSTICVOLUME$18);
         }

         target.setStringValue(diagnosticVolume);
      }
   }

   public void xsetDiagnosticVolume(XmlString diagnosticVolume) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DIAGNOSTICVOLUME$18);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(DIAGNOSTICVOLUME$18);
         }

         target.set(diagnosticVolume);
      }
   }

   public void unsetDiagnosticVolume() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(DIAGNOSTICVOLUME$18);
      }
   }

   public boolean getServerManaged() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SERVERMANAGED$20);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetServerManaged() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(SERVERMANAGED$20);
         return target;
      }
   }

   public boolean isSetServerManaged() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(SERVERMANAGED$20) != null;
      }
   }

   public void setServerManaged(boolean serverManaged) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SERVERMANAGED$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SERVERMANAGED$20);
         }

         target.setBooleanValue(serverManaged);
      }
   }

   public void xsetServerManaged(XmlBoolean serverManaged) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(SERVERMANAGED$20);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(SERVERMANAGED$20);
         }

         target.set(serverManaged);
      }
   }

   public void unsetServerManaged() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(SERVERMANAGED$20);
      }
   }

   public String getEventClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EVENTCLASSNAME$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEventClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(EVENTCLASSNAME$22);
         return target;
      }
   }

   public boolean isSetEventClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(EVENTCLASSNAME$22) != null;
      }
   }

   public void setEventClassName(String eventClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EVENTCLASSNAME$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(EVENTCLASSNAME$22);
         }

         target.setStringValue(eventClassName);
      }
   }

   public void xsetEventClassName(XmlString eventClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(EVENTCLASSNAME$22);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(EVENTCLASSNAME$22);
         }

         target.set(eventClassName);
      }
   }

   public void unsetEventClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(EVENTCLASSNAME$22);
      }
   }
}
