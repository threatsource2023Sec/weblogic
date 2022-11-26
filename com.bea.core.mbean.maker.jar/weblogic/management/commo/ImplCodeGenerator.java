package weblogic.management.commo;

import java.util.Enumeration;
import java.util.Vector;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.utils.PlatformConstants;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class ImplCodeGenerator extends AbstractCodeGenerator implements PlatformConstants {
   CodeGenerator.Output currentOutput = null;

   ImplCodeGenerator(WebLogicMBeanMaker weblogicmbeanmaker) {
      super(weblogicmbeanmaker);
   }

   protected Enumeration outputs(Object[] aobj) throws Exception {
      int i = 0;
      if (i < aobj.length) {
         String s = (String)aobj[i];
         Vector vector = new Vector();
         AbstractCodeGenerator.Output output = new AbstractCodeGenerator.Output(s + "Impl.java", "CustomMBeanImpl.j", "");
         vector.add(output);
         return vector.elements();
      } else {
         return null;
      }
   }

   protected void prepare(CodeGenerator.Output output) throws Exception {
      this.currentOutput = output;
   }

   public String packageDescriptor() {
      String s = "";
      if (this.tool.getMBeanPackageName() != null) {
         try {
            s = s + this.parse(this.getProductionRule("package"));
         } catch (CodeGenerationException var3) {
            var3.printStackTrace();
         }
      }

      return s;
   }

   public String implementsSpec() {
      String s = "";

      for(Enumeration enumeration = this.tool.implementsSet.elements(); enumeration.hasMoreElements(); s = s + ", " + ((Node)enumeration.nextElement()).getFirstChild().getNodeValue() + "MBean") {
      }

      return s;
   }

   public String requiredModelMBeanSpec() {
      return this.getDValue("Extends") == null ? "protected ModelMBean requiredModelMBean = null; public ModelMBean getRequiredModelMBean() { return requiredModelMBean; }" : "";
   }

   public String extendsSpec() {
      String s = this.getDValue("Extends");
      return s != null ? "extends " + s + "Impl" : "";
   }

   public String constructorBody() {
      String s = this.getDValue("Extends");
      return s != null ? "super(base);" : "super(); requiredModelMBean = (ModelMBean)base;";
   }

   public String mBeanName() {
      return this.tool.mBeanName();
   }

   public String versionID() {
      String s = this.getDValue("VersionID");
      if (s == null) {
         s = "1L";
      }

      return s;
   }

   public String mBeanImplFileName() {
      return this.tool.mBeanName() + "Impl";
   }

   public String delegateClassName() {
      return this.tool.mBeanName() + "Impl";
   }

   public String mBeanDescriptors() {
      String s = "";
      NamedNodeMap namednodemap = this.tool.root.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node = namednodemap.item(i);
         this.currentTag = node;
         s = s + this.handleMBeanTag();
      }

      return s;
   }

   public String packageName() {
      return this.tool.getMBeanPackageName();
   }

   public String generatedMethods() {
      return "";
   }

   public String CheckMBeanListenerRegistration() {
      String s = "";
      return s;
   }

   public String CheckMBeanListener() {
      String s = "";
      return s;
   }

   public String attributeDefinitions() {
      String s = "";
      return s;
   }

   public String tagName() {
      return this.lowerCaseFirst(this.currentTag.getNodeName());
   }

   String lowerCaseFirst(String s) {
      String s1 = s.substring(0, 1).toLowerCase();
      if (s.length() > 1) {
         s1 = s1 + s.substring(1);
      }

      return s1;
   }

   public String tagValue() {
      return this.currentTag.getNodeValue();
   }

   String collapse(String s) {
      StringBuffer stringbuffer = new StringBuffer();

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if (!Character.isWhitespace(c)) {
            stringbuffer.append(c);
         }
      }

      return stringbuffer.toString();
   }

   public String attributeVariableList() {
      String s = "";
      Enumeration enumeration = this.tool.attributes.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            String s1 = this.parse(this.getProductionRule("attributeVariable") + ",");
            s = s + "          " + this.collapse(s1) + PlatformConstants.EOL;
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String attributeVariable() {
      return "mmai_" + this.objectName();
   }

   public String attributeTypeSimple() {
      String s = this.attributeType();
      return s.substring(0, s.indexOf("["));
   }

   public String operationDefinitionsPerAccessors() {
      String s = "";
      Enumeration enumeration = this.tool.attributes.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();
         boolean flag = false;
         if (this.getAValue("GetMethod") != null) {
            try {
               s = s + this.parse(this.getProductionRule("operationGetterDefinition"));
               flag = true;
            } catch (CodeGenerationException var5) {
               var5.printStackTrace();
            }
         }

         if (this.getAValue("SetMethod") != null) {
            try {
               s = s + this.parse(this.getProductionRule("operationSetterDefinition"));
               flag = true;
            } catch (CodeGenerationException var6) {
               var6.printStackTrace();
            }
         }

         if (this.tool.isArray(this.currentObject) && this.getAValueBool("GenerateExtendedAccessors")) {
            try {
               s = s + this.parse(this.getProductionRule("operationElementAcessorDefinitions"));
               flag = true;
            } catch (CodeGenerationException var7) {
               var7.printStackTrace();
            }
         }

         if (flag) {
            ++this.count;
         }
      }

      return s;
   }

   public String operationParamTypeList() {
      String s = "";
      Vector vector = this.tool.getParameterTypes(this.currentObject);

      String s1;
      for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); s = s + this.getType(s1) + ", ") {
         s1 = (String)enumeration.nextElement();
      }

      return s;
   }

   public String operationReturnValue() {
      return this.returnValue(this.operationReturnType());
   }

   public String attributeValue() {
      return this.returnValue(this.attributeType());
   }

   String returnValue(String s) {
      String s1 = s.trim();
      if (s1.trim().equals("int")) {
         return "0";
      } else if (s1.trim().equals("boolean")) {
         return "false";
      } else if (s1.trim().equals("long")) {
         return "0";
      } else if (s1.trim().equals("float")) {
         return "0";
      } else if (s1.trim().equals("double")) {
         return "0";
      } else if (s1.trim().equals("character")) {
         return "''";
      } else if (s1.trim().equals("byte")) {
         return "0";
      } else {
         return s1.trim().equals("short") ? "0" : "null";
      }
   }

   public String operationDefinitions() {
      this.count = 1;
      String s = "";
      Enumeration enumeration = this.tool.operations.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("operationDefinition"));
            ++this.count;
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String operationMethods() {
      String s = "";
      Enumeration enumeration = this.tool.operations.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("operationMethodDefinition"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String constructorMethods() {
      String s = "";
      Enumeration enumeration = this.tool.constructors.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("constructorMethodDefinition"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String attributeAccessorMethods() {
      String s = "";
      Enumeration enumeration = this.tool.attributes.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            boolean isReadable = this.getAValueBool("Readable");
            isReadable = true;
            String s1 = this.getAValue("GetMethod");
            if (isReadable && s1 != null) {
               s = s + this.parse(this.getProductionRule("getterMethodDefinition"));
            }

            boolean isWritable = this.getAValueBool("Writeable");
            isWritable = true;
            String s2 = this.getAValue("SetMethod");
            if (isWritable && s2 != null) {
               s = s + this.parse(this.getProductionRule("setterMethodDefinition"));
            }

            String s3 = this.getAValue("Validator");
            if (isWritable && s3 != null) {
               s = s + this.parse(this.getProductionRule("validatorMethodDefinition"));
            }
         } catch (CodeGenerationException var8) {
            var8.printStackTrace();
         }
      }

      return s;
   }

   public String attributeArrayAccessorMethods() {
      String s = "";
      Enumeration enumeration = this.tool.attributes.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            boolean flag = this.getAValueBool("GenerateExtendedAccessors");
            boolean flag1 = this.getAValueBool("Readable");
            if (flag1 && flag && this.tool.isArray(this.currentObject)) {
               s = s + this.parse(this.getProductionRule("arrayIndexGetterMethodDefinition"));
            }

            boolean flag2 = this.getAValueBool("Writeable");
            if (flag2 && flag && this.tool.isArray(this.currentObject)) {
               s = s + this.parse(this.getProductionRule("arrayIndexSetterMethodDefinition"));
            }
         } catch (CodeGenerationException var6) {
            var6.printStackTrace();
         }
      }

      return s;
   }

   public String operationVariableList() {
      this.count = 1;
      String s = "";
      Enumeration enumeration1 = this.tool.operations.elements();

      while(enumeration1.hasMoreElements()) {
         this.currentObject = (Node)enumeration1.nextElement();

         try {
            String s1 = this.parse(this.getProductionRule("operationVariable") + ",");
            s = s + "          " + this.collapse(s1) + PlatformConstants.EOL;
            ++this.count;
         } catch (CodeGenerationException var8) {
            var8.printStackTrace();
         }
      }

      enumeration1 = this.tool.attributes.elements();

      while(enumeration1.hasMoreElements()) {
         this.currentObject = (Node)enumeration1.nextElement();
         boolean flag = false;
         String s4;
         if (this.getAValue("GetMethod") != null) {
            try {
               s4 = this.parse(this.getProductionRule("operationGetterVariable") + ",");
               s = s + "          " + this.collapse(s4) + PlatformConstants.EOL;
               flag = true;
            } catch (CodeGenerationException var5) {
               var5.printStackTrace();
            }
         }

         if (this.getAValue("SetMethod") != null) {
            try {
               s4 = this.parse(this.getProductionRule("operationSetterVariable") + ",");
               s = s + "          " + this.collapse(s4) + PlatformConstants.EOL;
               flag = true;
            } catch (CodeGenerationException var6) {
               var6.printStackTrace();
            }
         }

         if (this.tool.isArray(this.currentObject) && this.getAValueBool("GenerateExtendedAccessors")) {
            try {
               s4 = this.parse(this.getProductionRule("operationIndexGetterVariable") + ",");
               s = s + "          " + this.collapse(s4) + PlatformConstants.EOL;
               s4 = this.parse(this.getProductionRule("operationIndexSetterVariable") + ",");
               s = s + "          " + this.collapse(s4) + PlatformConstants.EOL;
               flag = true;
            } catch (CodeGenerationException var7) {
               var7.printStackTrace();
            }
         }

         if (flag) {
            ++this.count;
         }
      }

      return s;
   }

   public String operationsList() {
      String s = "";
      Enumeration enumeration = this.tool.operations.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("operation"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String operationReturnType() {
      return this.getOValue("ReturnType");
   }

   public String operationBody() {
      if (this.getOValue("ReturnType").equals("void")) {
         return "";
      } else {
         String s = "                " + this.operationReturnType() + " returnValue = " + this.operationReturnValue() + ";\n\n";
         s = s + "                // &&& add your code here\n\n";
         s = s + "                return returnValue;\n";
         return s;
      }
   }

   public String operationParams() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();
      boolean flag = true;

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         if (node.getNodeType() == 1 && node.getNodeName().equals("MBeanOperationArg")) {
            this.currentParam = node;
            if (!flag) {
               s = s + ", ";
            }

            flag = false;
            s = s + this.paramType() + " " + this.paramName();
         }
      }

      return s;
   }

   public String operationParamsDoc() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         if (node.getNodeType() == 1 && node.getNodeName().equals("MBeanOperationArg")) {
            this.currentParam = node;

            try {
               s = s + this.parse(this.getProductionRule("operationParamDoc"));
            } catch (CodeGenerationException var6) {
               var6.printStackTrace();
            }
         }
      }

      return s;
   }

   public String paramName() {
      return this.tool.attrVal(this.currentParam, "Name");
   }

   public String paramType() {
      return this.tool.attrVal(this.currentParam, "Type");
   }

   public String paramDescription() {
      String s = null;
      s = this.tool.attrVal(this.currentParam, "Description");
      if (s == null) {
         s = "No description provided.";
      }

      return s;
   }

   public String operationExceptions() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         if (node.getNodeType() == 1 && node.getNodeName().equals("MBeanException")) {
            s = s + ", ";
            s = s + node.getFirstChild().getNodeValue();
         }
      }

      return s;
   }

   public String operationExceptionsDoc() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         if (node.getNodeType() == 1 && node.getNodeName().equals("MBeanException")) {
            this.currentException = node;

            try {
               s = s + this.parse(this.getProductionRule("operationExceptionDoc"));
            } catch (CodeGenerationException var6) {
               var6.printStackTrace();
            }
         }
      }

      return s;
   }

   public String exceptionName() {
      return this.currentException.getFirstChild().getNodeValue();
   }

   public String imports() {
      String s = "";
      Enumeration enumeration = this.tool.imports.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("import"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String importName() {
      return this.currentObject.getFirstChild().getNodeValue();
   }

   public String constructorDefinitions() {
      this.count = 1;
      String s = "";
      Enumeration enumeration = this.tool.constructors.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("constructorDefinition"));
            ++this.count;
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String constructorVariableList() {
      this.count = 1;
      String s = "";
      Enumeration enumeration = this.tool.constructors.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            String s1 = this.parse(this.getProductionRule("constructorVariable") + ",");
            s = s + "          " + this.collapse(s1) + PlatformConstants.EOL;
            ++this.count;
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String notificationClassName() {
      String s = this.className();
      if (s == null) {
         s = this.packageName() + ".GeneratedNotificationClass" + this.count();
      }

      return s;
   }

   public String notificationDefinitions() {
      this.count = 1;
      String s = "";

      for(Enumeration enumeration = this.tool.notifications.elements(); enumeration.hasMoreElements(); ++this.count) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("notificationDefinition"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String generatedNotificationClasses() {
      this.count = 1;
      String s = "";

      for(Enumeration enumeration = this.tool.notifications.elements(); enumeration.hasMoreElements(); ++this.count) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("notificationClassDefinition"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String notificationVariableList() {
      this.count = 1;
      String s = "";

      for(Enumeration enumeration = this.tool.notifications.elements(); enumeration.hasMoreElements(); ++this.count) {
         this.currentObject = (Node)enumeration.nextElement();
         s = s + "          " + this.notificationVariable() + "," + PlatformConstants.EOL;
      }

      return s;
   }

   public String notificationVariable() {
      return "mmni_notification" + this.count();
   }

   public String checkAbstract() {
      String s = this.getDValue("Abstract");
      if (s == null) {
         return "";
      } else {
         return s.substring(0, 0).equalsIgnoreCase("t") ? "abstract" : "";
      }
   }

   public String attributeDescriptors() {
      String s = "";
      NamedNodeMap namednodemap = this.currentObject.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node = namednodemap.item(i);
         this.currentTag = node;
         s = s + this.handleAttributeTag();
      }

      return s;
   }

   public String notificationDescriptors() {
      String s = "";
      NamedNodeMap namednodemap = this.currentObject.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node = namednodemap.item(i);
         this.currentTag = node;
         s = s + this.handleNotificationTag();
      }

      return s;
   }

   public String operationDescriptors() {
      String s = "";
      NamedNodeMap namednodemap = this.currentObject.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node = namednodemap.item(i);
         this.currentTag = node;
         s = s + this.handleOperationTag();
      }

      return s;
   }

   public String constructorDescriptors() {
      String s = "";
      NamedNodeMap namednodemap = this.currentObject.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node = namednodemap.item(i);
         this.currentTag = node;
         s = s + this.handleConstructorTag();
      }

      return s;
   }

   String handleTagGeneral() {
      try {
         return this.tagType().equals("String") ? this.parse(this.getProductionRule("descriptorString")) : this.parse(this.getProductionRule("descriptorOther"));
      } catch (Exception var2) {
         System.out.println("Cannot process tag " + this.currentTag.getNodeName() + " for item " + this.currentObject.getLocalName() + ": " + var2);
         this.tool.error = true;
         return "";
      }
   }

   public String tagType() {
      return "String";
   }

   String handleMBeanTag() {
      return this.handleTagGeneral();
   }

   String handleAttributeTag() {
      return this.handleTagGeneral();
   }

   String handleNotificationTag() {
      return this.handleTagGeneral();
   }

   String handleConstructorTag() {
      return this.handleTagGeneral();
   }

   String handleOperationTag() {
      return this.handleTagGeneral();
   }

   public static boolean isSubclass(Class class1, Class class2) {
      if (class1.equals(class2)) {
         return true;
      } else {
         Class class3 = class1.getSuperclass();
         return class3 == null ? false : isSubclass(class3, class2);
      }
   }

   public String attributeGetMethodName() {
      return this.getAValue("GetMethod");
   }

   public String attributeSetMethodName() {
      return this.objectName() + "SetMethod";
   }
}
