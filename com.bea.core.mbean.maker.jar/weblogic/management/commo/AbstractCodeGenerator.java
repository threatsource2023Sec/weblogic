package weblogic.management.commo;

import javax.management.Descriptor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.utils.compiler.CodeGenerator;

public class AbstractCodeGenerator extends CodeGenerator {
   WebLogicMBeanMaker tool = null;
   Node currentObject = null;
   Node currentParam = null;
   Node currentException = null;
   Node currentTag = null;
   int count;
   private static Descriptor defaultDescriptor = null;
   private static Descriptor defaultAttrDescriptor = null;
   private static Descriptor defaultOperDescriptor = null;

   AbstractCodeGenerator(WebLogicMBeanMaker tool) {
      this.tool = tool;
   }

   Node getCurrentObject() {
      if (this.currentObject == null) {
         this.currentObject = this.tool.root;
      }

      return this.currentObject;
   }

   public String count() {
      String ret = "" + this.count;
      return ret;
   }

   public String objectName() {
      return this.tool.getName(this.getCurrentObject());
   }

   public String className() {
      return this.tool.attrVal(this.getCurrentObject(), "ClassName");
   }

   public String description() throws Exception {
      return this.tool.getDescription(this.getCurrentObject());
   }

   public String attributeType() {
      return this.tool.attrVal(this.getCurrentObject(), "Type");
   }

   public String attributeTypeSimple() {
      String type = this.attributeType();
      return type.substring(0, type.indexOf("["));
   }

   public String attributeTypeType() {
      String s;
      if (this.attributeType().equals("javax.management.ObjectName")) {
         s = this.tool.attrVal(this.getCurrentObject(), "InterfaceType");
         return s + "MBean.class";
      } else if (this.attributeType().equals("javax.management.ObjectName[]")) {
         s = this.tool.attrVal(this.getCurrentObject(), "InterfaceType");
         return s + "MBean[].class";
      } else {
         return this.getType(this.attributeType());
      }
   }

   public String attributeTypeTypeSimple() {
      return this.getType(this.attributeTypeSimple());
   }

   String getType(String typeI) {
      String type = typeI.trim();
      if (type.trim().equals("int")) {
         return "Integer.TYPE";
      } else if (type.trim().equals("boolean")) {
         return "Boolean.TYPE";
      } else if (type.trim().equals("long")) {
         return "Long.TYPE";
      } else if (type.trim().equals("float")) {
         return "Float.TYPE";
      } else if (type.trim().equals("double")) {
         return "Double.TYPE";
      } else if (type.trim().equals("character")) {
         return "Character.TYPE";
      } else if (type.trim().equals("byte")) {
         return "Byte.TYPE";
      } else {
         return type.trim().equals("short") ? "Short.TYPE" : type + ".class";
      }
   }

   public String attributeGetterName() {
      return this.tool.attrVal(this.getCurrentObject(), "GetMethod");
   }

   public String attributeSetterName() {
      return this.tool.attrVal(this.getCurrentObject(), "SetMethod");
   }

   public String attributeValidatorName() {
      return this.tool.attrVal(this.getCurrentObject(), "Validator");
   }

   public String attributeArrayIndexGetterName() {
      return "get" + this.objectName() + "ElementAt";
   }

   public String attributeArrayIndexSetterName() {
      return "set" + this.objectName() + "ElementAt";
   }

   public String operationReturnType() {
      return this.tool.attrVal(this.getCurrentObject(), "ReturnType");
   }

   public String operationParams() {
      String params = "";
      NodeList args = this.getCurrentObject().getChildNodes();
      boolean starting = true;

      for(int j = 0; j < args.getLength(); ++j) {
         Node element = args.item(j);
         if (element.getNodeType() == 1 && element.getNodeName().equals("MBeanOperationArg")) {
            this.currentParam = element;
            if (!starting) {
               params = params + ", ";
            }

            starting = false;
            params = params + this.paramType() + " " + this.paramName();
         }
      }

      return params;
   }

   public String paramName() {
      return this.tool.attrVal(this.currentParam, "Name");
   }

   public String paramType() {
      return this.tool.attrVal(this.currentParam, "Type");
   }

   public static boolean isSubtype(Class subclass, Class superclass) {
      if (subclass.equals(superclass)) {
         return true;
      } else {
         Class subSup = subclass.getSuperclass();
         if (subSup != null && isSubtype(subSup, superclass)) {
            return true;
         } else {
            Class[] subInt = subclass.getInterfaces();

            for(int i = 0; i < subInt.length; ++i) {
               if (isSubtype(subInt[i], superclass)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private Descriptor getDefaultDescriptor() {
      try {
         if (defaultDescriptor == null) {
            defaultDescriptor = CommoModelMBeanInfoSupport.makeMBeanDescriptorSupport();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return defaultDescriptor;
   }

   String getDValue(String name) {
      String s = this.tool.attrVal(this.tool.root, name);
      if (s == null) {
         s = (String)this.getDefaultDescriptor().getFieldValue(name);
      }

      return s;
   }

   private Descriptor getDefaultAttrDescriptor() {
      try {
         if (defaultAttrDescriptor == null) {
            defaultAttrDescriptor = CommoModelMBeanAttributeInfo.makeAttributeDescriptorSupport();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return defaultAttrDescriptor;
   }

   String getAValue(String name) {
      String s = this.tool.attrVal(this.currentObject, name);
      if (s == null) {
         s = (String)this.getDefaultAttrDescriptor().getFieldValue(name);
      }

      return s;
   }

   private Descriptor getDefaultOperDescriptor() {
      try {
         if (defaultOperDescriptor == null) {
            defaultOperDescriptor = CommoModelMBeanOperationInfo.makeOperationDescriptorSupport();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return defaultOperDescriptor;
   }

   String getOValue(String name) {
      String s = this.tool.attrVal(this.currentObject, name);
      if (s == null) {
         s = (String)this.getDefaultOperDescriptor().getFieldValue(name);
      }

      return s;
   }

   boolean getAValueBool(String name) {
      String s = this.tool.attrVal(this.currentObject, name);
      boolean flag = false;
      if (s == null) {
         s = (String)this.getDefaultAttrDescriptor().getFieldValue(name);
      }

      try {
         flag = Boolean.valueOf(s);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return flag;
   }

   public String attributeIsReadable() {
      return this.getAValue("Readable");
   }

   public String attributeIsWriteable() {
      return this.getAValue("Writeable");
   }

   public String attributeIsIs() {
      return this.getAValue("IsIs");
   }

   public String attributeMin() {
      return this.getAValue("Min");
   }

   public String attributeMax() {
      return this.getAValue("Max");
   }

   public String attributeDefault() {
      return this.getAValue("Default");
   }

   public String attributeDerivedDefault() {
      return this.getAValue("DerivedDefault");
   }

   public String attributeSecureValue() {
      return this.getAValue("SecureValue");
   }

   public String attributeDefaultDefault() {
      return this.getAValue("DefaultString");
   }

   public String attributeLegalValues() {
      return this.getAValue("LegalValues");
   }

   public String notificationTypes() {
      return this.getAValue("NotificationTypes");
   }

   public String attributeValidator() {
      return this.getAValue("Validator");
   }

   public String attributeDeprecated() {
      String val = this.getAValue("Deprecated");
      if (val != null) {
         if ("true".equals(val.toLowerCase())) {
            return "";
         }

         if ("false".equals(val.toLowerCase())) {
            return null;
         }
      }

      return val;
   }

   public String attributeSince() {
      return this.getAValue("Since");
   }

   public String attributeObsolete() {
      return this.getAValue("Obsolete");
   }

   public String attributeIsPreviouslyPersisted() {
      return this.getAValue("PreviouslyPersisted");
   }

   public String attributeRestName() {
      return this.getAValue("RestName");
   }

   public String attributeExclude() {
      return this.getAValue("Exclude");
   }

   public String attributeExcludeFromRest() {
      return this.getAValue("ExcludeFromRest");
   }

   public String attributeRestInternal() {
      return this.getAValue("RestInternal");
   }

   public String attributeVisibleToPartitions() {
      return this.getAValue("VisibleToPartitions");
   }

   public String operationImpact() {
      String impact = this.getOValue("Impact");
      if (impact == null) {
         return null;
      } else {
         int impactVal = Integer.parseInt(impact);
         switch (impactVal) {
            case 0:
               return "info";
            case 1:
               return "action";
            case 2:
               return "action_info";
            default:
               return "unknown";
         }
      }
   }

   class Output extends CodeGenerator.Output {
      public Output(String output, String template, String pkg) {
         super(output, template, pkg);
      }
   }
}
