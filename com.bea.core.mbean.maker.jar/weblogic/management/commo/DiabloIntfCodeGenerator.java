package weblogic.management.commo;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.management.ObjectName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class DiabloIntfCodeGenerator extends AbstractCodeGenerator {
   HashMap encryptedAttributeNames = new HashMap();
   List attributesList = new ArrayList();
   final String SECURITY_PACKAGE_NAME = "weblogic.management.security";
   final String USERLOCKOUT_PACKAGE_NAME = "weblogic.management.security.authentication";
   final String REALM_NAME = "Realm";
   final String USERLOCKUT_NAME = "UserLockoutManager";
   CodeGenerator.Output currentOutput = null;

   DiabloIntfCodeGenerator(WebLogicMBeanMaker weblogicmbeanmaker) {
      super(weblogicmbeanmaker);
   }

   protected Enumeration outputs(Object[] aobj) throws Exception {
      int i = 0;
      if (i < aobj.length) {
         String s = (String)aobj[i];
         Vector vector = new Vector();
         AbstractCodeGenerator.Output output = new AbstractCodeGenerator.Output(s + "MBean.java", "DiabloCustomMBeanIntf.j", this.tool.getMBeanPackageName());
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

   public String mBeanName() {
      return this.tool.mBeanName();
   }

   public String extendsSpec() {
      String s = "";
      String extendsStr = this.getDValue("Extends");
      if (extendsStr != null) {
         s = s + ", " + extendsStr + "MBean";
      }

      String implementsListStr = this.getDValue("Implements");
      if (implementsListStr != null) {
         int index = true;

         int nextIndex;
         String implementsStr;
         int index;
         for(nextIndex = 0; (index = implementsListStr.indexOf(",", nextIndex)) != -1; nextIndex = index + 1) {
            implementsStr = implementsListStr.substring(nextIndex, index);
            s = s + ", " + implementsStr.trim() + "MBean";
         }

         implementsStr = implementsListStr.substring(nextIndex);
         s = s + ", " + implementsStr.trim() + "MBean";
      }

      return s;
   }

   public String mBeanImplFileName() {
      return this.tool.mBeanName() + "Impl";
   }

   public String mBeanIntfFileName() {
      return this.tool.mBeanName() + "MBean";
   }

   public String packageName() {
      return this.tool.getMBeanPackageName();
   }

   public String attributeList() {
      String s = "";
      Enumeration enumeration = this.tool.attributes.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("attribute"));
         } catch (CodeGenerationException var4) {
            var4.printStackTrace();
         }
      }

      return s;
   }

   public String attributeGetterName() {
      return this.getAValueBool("IsIs") ? "is" + this.tool.getName(this.currentObject) : "get" + this.tool.getName(this.currentObject);
   }

   public String attributeType() {
      String refType = this.getAValue("InterfaceType");
      String attrType = this.getAValue("Type");
      String outType = attrType;
      String subscripts = "";
      int indexAttrTypeSubscripts = attrType.indexOf("[");
      String suffix = "";
      if (indexAttrTypeSubscripts > -1) {
         subscripts = attrType.substring(indexAttrTypeSubscripts);
         outType = attrType = attrType.substring(0, indexAttrTypeSubscripts);
      }

      if (refType != null) {
         Class cl = null;

         try {
            cl = Class.forName(attrType);
         } catch (ClassNotFoundException var9) {
         }

         if (cl != null && isSubtype(cl, ObjectName.class)) {
            suffix = "MBean";
         }

         int indexRefTypeSubscripts = refType.indexOf("[");
         if (indexRefTypeSubscripts > -1) {
            refType = refType.substring(0, indexRefTypeSubscripts);
         }

         outType = refType;
      }

      return outType + suffix + subscripts;
   }

   public String attributeTypeSimple() {
      String type = this.attributeType();
      return type.substring(0, type.indexOf("["));
   }

   public String attributeComments() {
      String s = "";

      try {
         s = s + this.parse(this.getProductionRule("attributeTypeDoc"));
         if (this.attributeMin() != null) {
            s = s + this.parse(this.getProductionRule("attributeMinDoc"));
         }

         if (this.attributeMax() != null) {
            s = s + this.parse(this.getProductionRule("attributeMaxDoc"));
         }

         if (this.attributeLegalValues() != null) {
            s = s + this.parse(this.getProductionRule("attributeLegalValuesDoc"));
         }

         if (this.attributeDefault() != null) {
            s = s + this.parse(this.getProductionRule("attributeInterfaceDefaultDoc"));
         }

         if (this.attributeValidator() != null) {
            s = s + this.parse(this.getProductionRule("attributeValidatorDoc"));
         }

         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("attributeDeprecatedDoc"));
         }

         if (this.attributeSince() != null) {
            s = s + this.parse(this.getProductionRule("attributeSinceDoc"));
         }

         if (this.attributeExclude() != null) {
            s = s + this.parse(this.getProductionRule("attributeExcludeDoc"));
         }

         if (this.attributeRestName() != null) {
            s = s + this.parse(this.getProductionRule("attributeRestNameDoc"));
         }

         if (this.attributeExcludeFromRest() != null) {
            s = s + this.parse(this.getProductionRule("attributeExcludeFromRestDoc"));
         }

         if (this.attributeRestInternal() != null) {
            s = s + this.parse(this.getProductionRule("attributeRestInternalDoc"));
         }

         if (this.attributeVisibleToPartitions() != null) {
            s = s + this.parse(this.getProductionRule("attributeVisibleToPartitionsDoc"));
         }
      } catch (CodeGenerationException var3) {
         var3.printStackTrace();
      }

      return s;
   }

   public String returnValueDescription() {
      return this.getAValue("ReturnTypeDescription");
   }

   public String returnValueDescriptionDoc() {
      String descr = this.getAValue("ReturnTypeDescription");
      if (descr != null) {
         try {
            return this.parse(this.getProductionRule("operationReturnValueDescriptionDocRule"));
         } catch (CodeGenerationException var3) {
            var3.printStackTrace();
         }
      }

      return "";
   }

   public String attributeJavaDocComments() {
      String attrName = this.tool.getName(this.currentObject);
      if (!this.attributesList.contains(attrName)) {
         this.attributesList.add(attrName);
      }

      String s = "";

      try {
         if (this.attributePreprocessor() != null) {
            s = s + this.parse(this.getProductionRule("attributePreprocessor"));
         }

         String _s;
         if (this.attributeMin() != null) {
            _s = this.parse(this.getProductionRule("attributeMinJavaDoc"));
            s = s + _s;
         }

         if (this.attributeMax() != null) {
            s = s + this.parse(this.getProductionRule("attributeMaxJavaDoc"));
         }

         if (this.attributeLegalValues() != null) {
            s = s + this.parse(this.getProductionRule("attributeLegalValuesJavaDoc"));
         }

         if (this.attributePresentationString() != null) {
            s = s + this.parse(this.getProductionRule("attributePresentationStringJavaDoc"));
         }

         if (this.attributeDefault() != null) {
            s = s + this.parse(this.getProductionRule("attributeInterfaceDefaultJavaDoc"));
         }

         if (this.attributeDerivedDefault() != null) {
            s = s + this.parse(this.getProductionRule("attributeDerivedDefaultJavaDoc"));
         }

         if (this.attributeSecureValue() != null) {
            s = s + this.parse(this.getProductionRule("attributeSecureValueJavaDoc"));
         }

         if (this.attributeDefaultString() != null) {
            s = s + this.parse(this.getProductionRule("attributeDefaultStringJavaDoc"));
         }

         if (this.getAttributePersistPolicy() != null) {
            s = s + this.parse(this.getProductionRule("attributePersistPolicy"));
         }

         if (this.attributeObsolete() != null) {
            s = s + this.parse(this.getProductionRule("obsoleteJavaDoc"));
         }

         if (this.getAValueBool("PreviouslyPersisted")) {
            s = s + this.parse(this.getProductionRule("previouslyPersistedJavaDoc"));
         }

         if (this.getAValueBool("NoDoc")) {
            s = s + this.parse(this.getProductionRule("noDocJavaDoc"));
         }

         if (this.getAttributeDynamicPolicy() != null) {
            s = s + this.parse(this.getProductionRule("attributeDynamicPolicy"));
         }

         if (this.attributeValidator() != null) {
            s = s + this.parse(this.getProductionRule("attributeValidatorJavaDoc"));
         }

         if (this.getAValueBool("Encrypted")) {
            _s = this.tool.getName(this.currentObject);
            if (!_s.endsWith("Encrypted")) {
               s = s + this.parse(this.getProductionRule("encryptedJavaDoc"));
               if (!this.encryptedAttributeNames.containsKey(_s)) {
                  this.encryptedAttributeNames.put(_s, this.getAttributeDynamicPolicy());
               }
            }
         }

         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("deprecatedJavaDoc"));
         }

         if (this.attributeSince() != null) {
            s = s + this.parse(this.getProductionRule("sinceJavaDoc"));
         }

         if (this.attributeExclude() != null) {
            s = s + this.parse(this.getProductionRule("excludeJavaDoc"));
         }

         if (this.attributeRestName() != null) {
            s = s + this.parse(this.getProductionRule("restNameJavaDoc"));
         }

         if (this.attributeExcludeFromRest() != null) {
            s = s + this.parse(this.getProductionRule("excludeFromRestJavaDoc"));
         }

         if (this.attributeRestInternal() != null) {
            s = s + this.parse(this.getProductionRule("restInternalJavaDoc"));
         }

         if (this.attributeVisibleToPartitions() != null) {
            s = s + this.parse(this.getProductionRule("visibleToPartitionsJavaDoc"));
         }

         if (this.getAValueBool("Abstract")) {
            s = s + this.parse(this.getProductionRule("abstractJavaDoc"));
         }

         if (!this.getAValueBool("Writeable")) {
            s = s + this.parse(this.getProductionRule("attributeNotWriteableJavaDoc"));
         }
      } catch (CodeGenerationException var4) {
         var4.printStackTrace();
      }

      return s;
   }

   public String attributeDeprecationAnnotation() {
      String attrName = this.tool.getName(this.currentObject);
      if (!this.attributesList.contains(attrName)) {
         this.attributesList.add(attrName);
      }

      String s = "";

      try {
         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("deprecatedAnnotation"));
         }
      } catch (CodeGenerationException var4) {
         var4.printStackTrace();
      }

      return s;
   }

   public String operationJavaDocComments() {
      String s = "";

      try {
         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("deprecatedJavaDoc"));
         }

         if (this.attributeSince() != null) {
            s = s + this.parse(this.getProductionRule("sinceJavaDoc"));
         }

         if (this.attributeExclude() != null) {
            s = s + this.parse(this.getProductionRule("excludeJavaDoc"));
         }

         if (this.attributeRestName() != null) {
            s = s + this.parse(this.getProductionRule("restNameJavaDoc"));
         }

         if (this.attributeExcludeFromRest() != null) {
            s = s + this.parse(this.getProductionRule("excludeFromRestJavaDoc"));
         }

         if (this.attributeRestInternal() != null) {
            s = s + this.parse(this.getProductionRule("restInternalJavaDoc"));
         }

         if (this.attributeVisibleToPartitions() != null) {
            s = s + this.parse(this.getProductionRule("visibleToPartitionsDoc"));
         }
      } catch (CodeGenerationException var3) {
         var3.printStackTrace();
      }

      return s;
   }

   public String getXmlTypeNameString() {
      return this.mbeanPresentationString() + "Type";
   }

   public String mBeanJavaDocComments() {
      String s = "";

      try {
         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("mBeanDeprecatedJavaDoc"));
         }

         if (this.attributeSince() != null) {
            s = s + this.parse(this.getProductionRule("mBeanSinceJavaDoc"));
         }

         if (this.attributeExclude() != null) {
            s = s + this.parse(this.getProductionRule("mBeanExcludeJavaDoc"));
         }

         if (this.tool.attrValBool(this.getCurrentObject(), "Abstract")) {
            s = s + this.parse(this.getProductionRule("abstractJavaDoc"));
         }

         if (this.getAttributeDynamicPolicy() != null) {
            s = s + this.parse(this.getProductionRule("mbeanDynamicPolicy"));
         }

         if (this.tool.attrValBool(this.getCurrentObject(), "NoDoc")) {
            s = s + this.parse(this.getProductionRule("mBeanNoDocJavaDoc"));
         }

         if (this.mbeanPresentationString() != null) {
            s = s + this.parse(this.getProductionRule("mbeanPresentationStringJavaDoc"));
         }
      } catch (CodeGenerationException var3) {
         var3.printStackTrace();
      }

      return s;
   }

   public String mBeanAnnotations() {
      String s = "";

      try {
         if (this.attributeDeprecated() != null) {
            s = s + this.parse(this.getProductionRule("mBeanDeprecatedAnnotation"));
         }
      } catch (CodeGenerationException var3) {
         var3.printStackTrace();
      }

      return s;
   }

   public String attributeMin() {
      String s = this.getAValue("Min");
      if (s != null) {
         NamedNodeMap map = this.currentObject.getAttributes();
         if (map.getNamedItem("Type") != null && map.getNamedItem("InterfaceType") == null) {
            String type = map.getNamedItem("Type").getNodeValue();
            return this.getRightType(type, s);
         }
      }

      return s;
   }

   private String getRightType(String type, String s) {
      if (type.indexOf("Integer") != -1) {
         return "new java.lang.Integer(" + s + ")";
      } else if (type.indexOf("Long") != -1) {
         return "new java.lang.Long(" + s + ")";
      } else if (type.indexOf("Boolean") != -1) {
         return "new java.lang.Boolean(" + s + ")";
      } else if (type.indexOf("Double") != -1) {
         return "new java.lang.Double(" + s + ")";
      } else {
         return type.indexOf("Float") != -1 ? "new java.lang.Float(" + s + ")" : s;
      }
   }

   public String attributeMax() {
      String s = this.getAValue("Max");
      if (s != null) {
         NamedNodeMap map = this.currentObject.getAttributes();
         if (map.getNamedItem("Type") != null && map.getNamedItem("InterfaceType") == null) {
            String type = map.getNamedItem("Type").getNodeValue();
            return this.getRightType(type, s);
         }
      }

      return s;
   }

   public String attributeNull() {
      return this.getAValue("LegalNull");
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

   public String attributeInterfaceDefault() {
      String refType = this.getAValue("InterfaceType");
      if (refType != null) {
         refType = refType.trim();
      }

      String def = this.getAValue("Default");
      if (refType != null) {
         if (refType.equals("int")) {
            def = StringUtils.replaceGlobal(def, "new Integer(", "");
            def = StringUtils.replaceGlobal(def, "new java.lang.Integer(", "");
            def = StringUtils.replaceGlobal(def, ")", "");
         } else if (refType.equals("long")) {
            def = "(" + def + ").longValue()";
         } else if (refType.equals("short")) {
            def = "(" + def + ").shortValue()";
         } else if (refType.equals("float")) {
            def = "(" + def + ").floatValue()";
         } else if (refType.equals("double")) {
            def = "(" + def + ").doubleValue()";
         } else if (refType.equals("char")) {
            def = "(" + def + ").charValue()";
         } else if (refType.equals("byte")) {
            def = "(" + def + ").byteValue()";
         } else if (refType.equals("boolean")) {
            def = StringUtils.replaceGlobal(def, "new Boolean(", "");
            def = StringUtils.replaceGlobal(def, "new java.lang.Boolean(", "");
            if (def.trim().equals("Boolean.FALSE")) {
               def = "false";
            } else if (def.trim().equals("Boolean.TRUE")) {
               def = "true";
            }

            def = StringUtils.replaceGlobal(def, ")", "");
         }
      }

      if (def.indexOf("String[]") != -1) {
         int f = def.indexOf("{");
         int l = def.lastIndexOf("}");
         String s = def.substring(f + 1, l);
         String[] attrs = StringUtils.splitCompletely(s, ",");
         String[] modAttrs = new String[attrs.length];
         boolean hasQuotes = false;

         for(int i = 0; i < attrs.length; ++i) {
            String attr = attrs[i].trim();
            if (attr.indexOf(34) != -1) {
               attr = StringUtils.replaceGlobal(attr, "\"", "");
               hasQuotes = true;
               attr = attr.trim();
            }

            if (hasQuotes) {
               attr = "\"" + attr + "\"";
            }

            modAttrs[i] = attr;
         }

         String str = StringUtils.join(modAttrs, ",");
         def = str;
      } else if (def.indexOf("Integer[]") == -1 && def.indexOf("String[0]") != -1) {
         def = "\"\"";
      }

      return def;
   }

   public String mbeanPresentationString() {
      return this.getAValue("PresentationString");
   }

   public String attributeDefaultString() {
      return this.getAValue("DefaultString");
   }

   public String getAttributePersistPolicy() {
      return this.getAValue("PersistPolicy");
   }

   public String getAttributeDynamicPolicy() {
      String x = this.getAValue("Dynamic");
      return x == null ? "false" : x;
   }

   public String getMBeanDynamicPolicy() {
      return this.getAValue("Dynamic");
   }

   public String attributeLegalValues() {
      String x = this.getAValue("LegalValues");
      if (x != null) {
         String[] values = StringUtils.splitCompletely(x, ",");
         String[] newValues = new String[values.length];

         for(int i = 0; i < values.length; ++i) {
            newValues[i] = "\"" + values[i].trim() + "\"";
         }

         x = StringUtils.join(newValues, ",");
      }

      return x;
   }

   public String attributePresentationString() {
      return this.getAValue("PresentationString");
   }

   public String attributePreprocessor() {
      return this.getAValue("Preprocessor");
   }

   public String attributeObsolete() {
      return this.getAValue("Obsolete");
   }

   public String attributeValidator() {
      String methodName = this.getAValue("Validator");
      return methodName == null ? null : "try { if (!(customizerField." + methodName + "(value))) throw new IllegalArgumentException()\\; } catch (javax.management.InvalidAttributeValueException e) { throw new IllegalArgumentException(e.toString())\\; }";
   }

   public String isDynamic() {
      String x = this.getAValue("Dynamic");
      return x == null ? "false" : x;
   }

   public String attributeGenerateEncrypted() {
      return this.getAValue("Encrypted");
   }

   public String attributeMethods() {
      String s = "";

      try {
         boolean flag = this.getAValueBool("GenerateExtendedAccessors");
         boolean flag1 = this.getAValueBool("Readable");
         String s1 = this.getAValue("GetMethod");
         if (flag1) {
            s = s + this.parse(this.getProductionRule("attributeGetter"));
            if (flag && this.tool.isArray(this.currentObject)) {
               s = s + this.parse(this.getProductionRule("attributeArrayIndexGetter"));
            }
         }

         boolean flag2 = this.getAValueBool("Writeable");
         String s2 = this.getAValue("SetMethod");
         if (flag2) {
            s = s + this.parse(this.getProductionRule("attributeSetter"));
            if (flag && this.tool.isArray(this.currentObject)) {
               s = s + this.parse(this.getProductionRule("attributeArrayIndexSetter"));
            }
         }
      } catch (CodeGenerationException var7) {
         var7.printStackTrace();
      }

      return s;
   }

   public String attributePersistPolicy() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         NamedNodeMap nnp = node.getParentNode().getAttributes();
         if (nnp != null) {
            for(int k = 0; k < nnp.getLength(); ++k) {
               Node nde = nnp.item(k);
               if (nde.getNodeName().equals("PersistPolicy")) {
                  try {
                     if (nde.getNodeValue() != null || nde.getNodeValue().equals("never")) {
                        return s + this.parse(this.getProductionRule("persistPolicy"));
                     }
                  } catch (CodeGenerationException var9) {
                     var9.printStackTrace();
                  }
               }
            }
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

   public String paramType() {
      String type = this.tool.attrVal(this.currentParam, "Type");
      int indexType = type.indexOf("[");
      if (indexType == -1) {
         indexType = type.length();
      }

      Class cl = null;

      try {
         cl = Class.forName(type.substring(0, indexType));
      } catch (ClassNotFoundException var6) {
      }

      if (cl != null && isSubtype(cl, ObjectName.class)) {
         String refType = this.tool.attrVal(this.currentParam, "InterfaceType");
         if (refType != null) {
            int indexRefType = refType.indexOf("[");
            if (indexRefType > -1) {
               refType = refType.substring(0, indexRefType);
            }

            refType = refType + "MBean";
            if (indexType > -1) {
               refType = refType + type.substring(indexType);
            }

            return refType;
         }
      }

      return type;
   }

   public String customizer() {
      return this.mBeanImplFileName();
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
      boolean flag = true;
      NodeList nodelist = this.currentObject.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         if (node.getNodeType() == 1 && node.getNodeName().equals("MBeanException")) {
            if (flag) {
               s = s + " throws ";
            } else {
               s = s + ", ";
            }

            flag = false;
            s = s + node.getFirstChild().getNodeValue();
         }
      }

      return s;
   }

   public String generateImplementation() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();
      boolean found = false;

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         NamedNodeMap nnp = node.getParentNode().getAttributes();
         if (nnp != null) {
            for(int k = 0; k < nnp.getLength(); ++k) {
               Node nde = nnp.item(k);
               if (nde.getNodeName().equals("GenerateImplementation")) {
                  found = true;

                  try {
                     if (nde.getNodeValue() != null || nde.getNodeValue().equals("true")) {
                        return "";
                     }

                     s = s + this.parse(this.getProductionRule("generateImplementationDoc"));
                     if (this.getOValue("Impact") != null) {
                        s = s + this.parse(this.getProductionRule("operationImpactDoc"));
                     }
                  } catch (CodeGenerationException var11) {
                     var11.printStackTrace();
                  }
               }
            }
         }
      }

      if (!found) {
         try {
            s = this.parse(this.getProductionRule("generateImplementationDoc"));
            if (this.getOValue("Impact") != null) {
               s = s + this.parse(this.getProductionRule("operationImpactDoc"));
            }
         } catch (CodeGenerationException var10) {
            var10.printStackTrace();
         }
      }

      return s;
   }

   public String root() {
      String s = "@root";
      NodeList nodelist = this.currentObject.getChildNodes();
      boolean found = false;
      boolean isAbstract = false;
      String name = "";
      String packageName = "";

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         NamedNodeMap nnp = node.getParentNode().getAttributes();
         if (nnp != null) {
            for(int k = 0; k < nnp.getLength(); ++k) {
               Node nde = nnp.item(k);
               if (nde.getNodeName().equals("Name")) {
                  name = nde.getNodeValue();
               } else if (nde.getNodeName().equals("Package")) {
                  packageName = nde.getNodeValue();
               } else if (nde.getNodeName().equals("Abstract") && nde.getNodeValue().toLowerCase().equals("true")) {
                  isAbstract = true;
               }
            }
         }
      }

      if (isAbstract) {
         return "";
      } else if (packageName.indexOf("weblogic.management.security") != -1) {
         if (packageName.indexOf("weblogic.management.security") != -1) {
            if (packageName.equals("weblogic.management.security") && name.equals("Realm")) {
               return "";
            }

            if (packageName.equals("weblogic.management.security.authentication") && name.equals("UserLockoutManager")) {
               return "";
            }
         }

         return s + " " + name;
      } else {
         return "@root " + name;
      }
   }

   public String getDefaultDisplayName() {
      String s = "\"No DisplayName\"";
      NodeList nodelist = this.currentObject.getParentNode().getChildNodes();
      boolean found = false;

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         NamedNodeMap nnp = node.getParentNode().getAttributes();
         if (nnp != null) {
            for(int k = 0; k < nnp.getLength(); ++k) {
               Node nde = nnp.item(k);
               if (nde.getNodeName().equals("DisplayName")) {
                  found = true;
                  if (nde.getNodeValue() != null) {
                     return "\"" + nde.getNodeValue() + "\"";
                  }
               }
            }
         }
      }

      return s;
   }

   public String dnComment() {
      String s = "";
      NodeList nodelist = this.currentObject.getParentNode().getChildNodes();
      boolean found = false;

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node = nodelist.item(i);
         NamedNodeMap nnp = node.getParentNode().getAttributes();
         if (nnp != null) {
            for(int k = 0; k < nnp.getLength(); ++k) {
               Node nde = nnp.item(k);
               if (nde.getNodeName().equals("DisplayName")) {
                  return "";
               }
            }
         }
      }

      return "//";
   }

   public String allowsSubTypes() {
      String s = "";
      NodeList nodelist = this.currentObject.getChildNodes();
      if (this.currentObject.getNodeName().equals("MBeanAttribute")) {
         NamedNodeMap nnMap = this.currentObject.getAttributes();
         if (nnMap != null) {
            for(int l = 0; l < nnMap.getLength(); ++l) {
               Node nde = nnMap.item(l);
               if (nde.getNodeName().equals("AllowsSubTypes")) {
                  try {
                     if (nde.getNodeValue() == null || !nde.getNodeValue().equals("true")) {
                        return "";
                     }

                     s = s + this.parse(this.getProductionRule("allowsSubTypesDoc"));
                  } catch (CodeGenerationException var7) {
                     var7.printStackTrace();
                  }
               }
            }
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
      Node currentObjectSave = this.currentObject;
      Enumeration enumeration = this.tool.imports.elements();

      while(enumeration.hasMoreElements()) {
         this.currentObject = (Node)enumeration.nextElement();

         try {
            s = s + this.parse(this.getProductionRule("import"));
         } catch (CodeGenerationException var5) {
            var5.printStackTrace();
         }
      }

      this.currentObject = currentObjectSave;
      return s;
   }

   public String importName() {
      return this.currentObject.getFirstChild().getNodeValue();
   }

   public String generateTheEncrypted() {
      if (this.encryptedAttributeNames.isEmpty()) {
         return "";
      } else {
         String s = "";
         Iterator iter = this.encryptedAttributeNames.keySet().iterator();

         while(iter.hasNext()) {
            String attr = (String)iter.next();
            if (!this.attributesList.contains(attr + "Encrypted")) {
               String dynamicPolicy = (String)this.encryptedAttributeNames.get(attr);
               s = s + "/**\n\t*@dynamic " + dynamicPolicy + "\n\t*/\n";
               s = s + "\tpublic void set" + attr + "Encrypted(byte[] _bytes);\n";
               s = s + "\t/**\n\t*@dynamic " + dynamicPolicy + "\n\t*/\n";
               s = s + "\tpublic byte[] get" + attr + "Encrypted();\n";
            }
         }

         return s;
      }
   }
}
