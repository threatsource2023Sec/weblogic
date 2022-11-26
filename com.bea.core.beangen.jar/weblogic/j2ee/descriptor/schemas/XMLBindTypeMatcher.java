package weblogic.j2ee.descriptor.schemas;

import com.bea.staxb.buildtime.internal.logger.BindingLogger;
import com.bea.staxb.buildtime.internal.mbean.TypeMatcher;
import com.bea.staxb.buildtime.internal.mbean.TypeMatcherContext;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import com.bea.xbean.common.NameUtil;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import weblogic.descriptor.annotation.PropertyAnnotations;
import weblogic.descriptor.codegen.Utils;

public class XMLBindTypeMatcher implements TypeMatcher {
   private static final Object[] matchedPackageNamespaces = new Object[]{"weblogic.j2ee.descriptor", new String[]{"http://xmlns.jcp.org/xml/ns/javaee", "http://java.sun.com/xml/ns/javaee", "http://xmlns.jcp.org/xml/ns/persistence", "http://java.sun.com/xml/ns/j2ee"}, "weblogic.j2ee.descriptor.wl", new String[]{"http://xmlns.oracle.com/weblogic/weblogic-application", "http://xmlns.oracle.com/weblogic/weblogic-extension", "http://xmlns.oracle.com/weblogic/weblogic-application-client", "http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "http://xmlns.oracle.com/weblogic/weblogic-interception", "http://xmlns.oracle.com/weblogic/jdbc-data-source", "http://xmlns.oracle.com/weblogic/weblogic-jms", "http://xmlns.oracle.com/weblogic/weblogic-connector", "http://xmlns.oracle.com/weblogic/weblogic-connector-extension", "http://xmlns.oracle.com/weblogic/weblogic-web-app", "http://xmlns.oracle.com/weblogic/weblogic-webservices", "http://xmlns.oracle.com/weblogic/weblogic-wsee-clientHandlerChain", "http://xmlns.oracle.com/weblogic/webservice-policy-ref", "http://xmlns.oracle.com/weblogic/weblogic-wsee-standaloneclient", "http://xmlns.oracle.com/weblogic/deployment-plan", "http://xmlns.oracle.com/weblogic/resource-deployment-plan", "http://www.bea.com/ns/weblogic/10.0", "http://www.bea.com/ns/weblogic/90"}, "weblogic.j2ee.descriptor.wl60", new String[]{"http://www.bea.com/ns/weblogic/60"}};
   private static final Map packageForNamespace = new HashMap();
   private static final Map preferredNamespaceForPackage = new HashMap();
   private BindingLogger logger = null;
   private Map substituteClasses;
   private final CaseInsensitiveMap javaTypeByShortName = new CaseInsensitiveMap();
   private final CaseInsensitiveMap javaTypeByFullName = new CaseInsensitiveMap();
   private String[] acronyms = new String[]{"JNDI", "TagLib", "URI", "XA", "JDBC", "SAF", "JMS"};
   private String[] replacements = new String[]{"Jndi", "Taglib", "Uri", "Xa", "Jdbc", "Saf", "Jms"};
   String[] irregularElementNames = new String[]{"activationspec-class", "adminobject-interface", "adminobject-class", "managedconnectionfactory-class", "connectionfactory-interface", "connectionfactory-impl-class", "resourceadapter-version", "resourceadapter", "messageadapter", "taglib", "messagelistener", "messagelistener-type", "activationspec", "namespaceURI", "resourceadapter-class", "outbound-resourceadapter", "inbound-resourceadapter", "adminobject", "taglib-uri", "taglib-location", "rtexprvalue", "jdbcxa-debug-level", "interception-point", "allow-close-in-onMessage", "connection-params", "native-io-enabled", "processor-type", "name-segment", "jdbc-xa-debug-level", "jdbc-xa-params", "saf-imported-destinations", "loginURL", "attach-jmsx-user-id", "group-params", "jndi-name"};
   private static final Set IGNOREABLE_INTERFACES;

   protected Map getPackageForNamespace() {
      return packageForNamespace;
   }

   public void init(TypeMatcherContext ctx) {
      this.logger = ctx.getLogger();
   }

   public TypeMatcher.MatchedType[] matchTypes(JClass[] jClasses, SchemaTypeSystem sts) {
      this.substituteClasses = new HashMap();
      List result = new ArrayList();
      this.startMatch();
      this.verbose("Starting match");
      this.populateJavaTypeNameMaps(jClasses);
      Map schemasToClasses = new HashMap();
      SchemaType[] types = sts.globalTypes();

      for(int i = 0; i < types.length; ++i) {
         SchemaType sType = types[i];
         JClass jClass = (JClass)this.lookupSchemaTypeName(sType.getName());
         if (jClass != null) {
            result.add(new TypeMatcher.MatchedType(jClass, sType));
            schemasToClasses.put(sType, jClass);
         }
      }

      SchemaType[] docTypes = sts.documentTypes();

      for(int i = 0; i < docTypes.length; ++i) {
         SchemaType docType = docTypes[i];
         SchemaType insideType = docType.getElementProperties()[0].getType();
         JClass rootJClass = (JClass)schemasToClasses.get(insideType);
         if (rootJClass != null) {
            result.add(new TypeMatcher.MatchedType(rootJClass, docType));
         }
      }

      return (TypeMatcher.MatchedType[])((TypeMatcher.MatchedType[])result.toArray(new TypeMatcher.MatchedType[result.size()]));
   }

   private void populateJavaTypeNameMaps(JClass[] jClasses) {
      for(int i = 0; i < jClasses.length; ++i) {
         JClass primaryInterface = null;
         if (!jClasses[i].isInterface()) {
            primaryInterface = this.getPrimaryInterface(jClasses[i]);
         }

         if (primaryInterface != null) {
            this.substituteClasses.put(primaryInterface, jClasses[i]);
         } else {
            primaryInterface = jClasses[i];
         }

         this.recordFullyQualifiedJavaType(primaryInterface, jClasses[i]);
      }

   }

   private void addMatchedType(Map classesToSchemas, Map schemasToClasses, SchemaType sType, JClass jType) {
      String preferredNamespace = (String)preferredNamespaceForPackage.get(jType.getContainingPackage());
      if (preferredNamespace != null && classesToSchemas.containsKey(jType)) {
         SchemaType foundType = (SchemaType)classesToSchemas.get(jType);
         if (foundType.getName().getNamespaceURI().equals(preferredNamespace)) {
            return;
         }
      }

      Object foundType = classesToSchemas.put(jType, sType);
      if (foundType != null) {
         this.verbose("Replacing existing type " + foundType + " with new type " + sType + " for key " + jType);
      }

      schemasToClasses.put(sType, jType);
   }

   private String findContainingNamespace(SchemaType sType) {
      while(!sType.isDocumentType()) {
         if (sType.isAttributeType()) {
            return sType.getAttributeTypeAttributeName().getNamespaceURI();
         }

         if (sType.getName() != null) {
            return sType.getName().getNamespaceURI();
         }

         sType = sType.getOuterType();
      }

      return sType.getDocumentElementName().getNamespaceURI();
   }

   private void echoNames(SchemaGlobalElement[] gElement) {
      for(int i = 0; i < gElement.length; ++i) {
         this.echoNames(gElement[i].getType().getProperties());
      }

   }

   private void echoNames(SchemaProperty[] sProps) {
      for(int i = 0; i < sProps.length; ++i) {
         this.echoName(sProps[i].getType());
      }

   }

   private void echoName(SchemaType ssType) {
      for(QName qname = null; qname == null; ssType = ssType.getOuterType()) {
         if (ssType.isDocumentType()) {
            qname = ssType.getDocumentElementName();
         } else if (ssType.isAttributeType()) {
            qname = ssType.getAttributeTypeAttributeName();
         } else if (ssType.getName() != null) {
            qname = ssType.getName();
         } else if (ssType.getContainerField() != null) {
            qname = ssType.getContainerField().getName();
            if (qname.getNamespaceURI().length() == 0) {
               qname = new QName(this.findContainingNamespace(ssType), qname.getLocalPart());
            }
         }
      }

   }

   private String singular(String plural) {
      if (plural.endsWith("ies")) {
         return plural.substring(0, plural.length() - 3) + "y";
      } else if (plural.endsWith("sses")) {
         return plural.substring(0, plural.length() - 2);
      } else {
         return plural.endsWith("s") ? plural.substring(0, plural.length() - 1) : plural;
      }
   }

   private boolean hasCaps(StringBuffer n) {
      for(int i = 0; i < n.length(); ++i) {
         if (Character.isUpperCase(n.charAt(i))) {
            return true;
         }
      }

      return false;
   }

   private int nextCap(StringBuffer n) {
      for(int i = 0; i < n.length(); ++i) {
         if (Character.isUpperCase(n.charAt(i))) {
            return i;
         }
      }

      return n.length();
   }

   private String normalize(JProperty jProp) {
      JAnnotationValue val = jProp.getAnnotationValue("xsdgen:element.name");
      if (val != null) {
         return val.asString();
      } else {
         boolean isArray = jProp.getType().isArrayType();
         StringBuffer name = new StringBuffer(isArray ? this.singular(jProp.getSimpleName()) : jProp.getSimpleName());

         int i;
         for(i = 0; i < this.acronyms.length; ++i) {
            int index = name.indexOf(this.acronyms[i]);
            if (index > -1) {
               name = name.replace(index, index + this.acronyms[i].length(), this.replacements[i]);
            }
         }

         for(i = 0; this.hasCaps(name); i = this.nextCap(name)) {
            name.setCharAt(i, Character.toLowerCase(name.charAt(i)));
            if (i > 0) {
               name = name.insert(i, '-');
            }
         }

         return name.toString();
      }
   }

   public boolean isDoubleEncodingFixed() {
      return true;
   }

   private JProperty[] getAllProperties(JClass jClass) {
      List properties = new ArrayList();
      JProperty[] props = jClass.getProperties();

      for(int count = 0; count < props.length; ++count) {
         if (!props[count].getQualifiedName().equals("java.lang.Object.Class")) {
            properties.add(props[count]);
         }
      }

      return (JProperty[])((JProperty[])properties.toArray(new JProperty[0]));
   }

   private boolean isExcludedProperty(JProperty property) {
      JAnnotation[] annotations = property.getAllJavadocTags();

      for(int count = 0; count < annotations.length; ++count) {
         if (annotations[count].getQualifiedName().equals(PropertyAnnotations.TRANSIENT.toString())) {
            return true;
         }
      }

      return false;
   }

   private String getElementName(JProperty property) {
      JAnnotation[] annotations = property.getAllJavadocTags();

      for(int count = 0; count < annotations.length; ++count) {
         if (annotations[count].getQualifiedName().equals("xsdgen:element.name")) {
            JAnnotationValue value = annotations[count].getValue("value");
            if (value != null) {
               return value.asString();
            }
         }
      }

      return null;
   }

   private JProperty findMatchingElementName(JProperty[] properties, String elementName) {
      for(int count = 0; count < properties.length; ++count) {
         String annotatedElementName = this.getElementName(properties[count]);
         if (annotatedElementName != null && annotatedElementName.equals(elementName)) {
            return properties[count];
         }
      }

      return null;
   }

   public TypeMatcher.MatchedProperties[] matchProperties(JClass jClass, SchemaType sType) {
      SchemaProperty[] sProps = sType.getProperties();
      JProperty[] jProps = this.getAllProperties(jClass);
      JClass sup = jClass;

      HashMap localPropMap;
      for(localPropMap = new HashMap(); sup != null; sup = sup.getSuperclass()) {
         JProperty[] localProps = sup.getDeclaredProperties();

         for(int i = 0; i < localProps.length; ++i) {
            if (!localPropMap.containsKey(localProps[i].getSimpleName())) {
               localPropMap.put(localProps[i].getSimpleName(), localProps[i]);
            }
         }

         if (sup.getAnnotation("xsdgen:complexType.ignoreSuper") != null) {
            break;
         }
      }

      List result = new ArrayList();
      this.startMatch();

      for(int i = 0; i < jProps.length; ++i) {
         if (!this.isExcludedProperty(jProps[i])) {
            this.recordJavaProperty(jProps[i].getSimpleName(), jProps[i]);
         } else {
            this.verbose("Excluding property " + jProps[i].getQualifiedName());
         }
      }

      JClass primaryInterface = null;
      if (!jClass.isInterface()) {
         this.getPrimaryInterface(jClass);
      }

      boolean propMatch = true;
      StringBuffer errors = new StringBuffer();

      for(int i = 0; i < sProps.length; ++i) {
         JProperty jProp = (JProperty)this.lookupSchemaPropertyName(sProps[i].getName().getLocalPart());
         if (jProp == null) {
            jProp = this.findMatchingElementName(jProps, sProps[i].getName().getLocalPart());
         }

         boolean alternatePropFound = false;
         JProperty savedJProp = null;
         if (jProp != null && sProps[i].getType().getName() != null && sProps[i].getType().getName().getLocalPart() != null && sProps[i].getType().getName().getLocalPart().equals("string") && !jProp.getType().getQualifiedName().equals(String.class.getName())) {
            savedJProp = jProp;
            jProp = (JProperty)this.lookupSchemaPropertyName(sProps[i].getName().getLocalPart() + "AsString");
            if (jProp != null) {
               JAnnotationValue val = jProp.getAnnotationValue("xsdgen:element.name");
               if (val != null && val.asString().equals(sProps[i].getName().getLocalPart())) {
                  alternatePropFound = true;
               }
            }

            if (!alternatePropFound) {
               jProp = savedJProp;
            }
         }

         if (jProp != null) {
            if (localPropMap.get(jProp.getSimpleName()) != null) {
               jProp = (JProperty)localPropMap.get(jProp.getSimpleName());
            }

            String spName = sProps[i].getName().getLocalPart();
            String jName = this.normalize(jProp);
            if (!spName.equals(jName)) {
               propMatch = false;
               errors.append("Property name [" + jName + "] for property [" + jProp + "] and schema name [" + spName + "] are mismatch!\n");
            }

            JMethod isSetter = null;
            boolean alternateIsSetterFound = false;
            if (alternatePropFound) {
               isSetter = this.findIsSetter(jClass, savedJProp);
               JAnnotationValue val = isSetter.getAnnotationValue("xsdgen:isSetMethodFor");
               if (val != null && val.asString().equals(sProps[i].getName().getLocalPart())) {
                  alternateIsSetterFound = true;
               }
            }

            if (!alternateIsSetterFound) {
               isSetter = this.findIsSetter(jClass, jProp);
            }

            JMethod objectFactory = this.findObjectFactory(jClass, jProp);
            if (isSetter == null) {
               result.add(new TypeMatcher.MatchedProperties(jProp, sProps[i]));
            } else if (objectFactory == null) {
               result.add(new TypeMatcher.MatchedProperties(jProp, sProps[i], isSetter));
            } else {
               result.add(new TypeMatcher.MatchedProperties(jProp, sProps[i], isSetter, objectFactory));
            }
         }
      }

      if (!propMatch) {
         throw new AssertionError(errors.toString());
      } else {
         return (TypeMatcher.MatchedProperties[])((TypeMatcher.MatchedProperties[])result.toArray(new TypeMatcher.MatchedProperties[result.size()]));
      }
   }

   JProperty[] getOrderedProperties(JClass primaryInterface) {
      JClass[] supers = this.getSupers(primaryInterface.getInterfaces());
      ArrayList interfaces = new ArrayList();

      for(int i = 0; i < supers.length; ++i) {
         this.filterInterfaces(supers[i], interfaces);
      }

      this.filterInterface(primaryInterface, interfaces);
      ArrayList list = new ArrayList();
      Iterator i = interfaces.iterator();

      while(true) {
         JClass ifc;
         do {
            if (!i.hasNext()) {
               return (JProperty[])((JProperty[])list.toArray(new JProperty[0]));
            }

            ifc = (JClass)i.next();
         } while(ifc.getSimpleName().equals("EnterpriseBeanBean"));

         JProperty[] props = ifc.getDeclaredProperties();
         ArrayList propList = new ArrayList(Arrays.asList(props));

         for(int j = 0; j < props.length; ++j) {
            if (props[j].getQualifiedName().endsWith("NamedEntityBean.Name")) {
               propList.remove(props[j]);
            }

            if (props[j].getQualifiedName().endsWith("DestinationBean.Template")) {
               propList.remove(props[j]);
            }

            if (list.contains(props[j])) {
               propList.remove(props[j]);
            }
         }

         list.addAll(propList);
      }
   }

   private void filterInterfaces(JClass ifc, ArrayList interfaces) {
      JClass[] supers = this.getSupers(ifc.getInterfaces());

      for(int i = 0; i < supers.length; ++i) {
         this.filterInterfaces(supers[i], interfaces);
      }

      this.filterInterface(ifc, interfaces);
   }

   private void filterInterface(JClass ifc, ArrayList interfaces) {
      if (!interfaces.contains(ifc)) {
         interfaces.add(ifc);
      }

   }

   JClass[] getSupers(JClass[] interfaces) {
      List list = new ArrayList(Arrays.asList((Object[])interfaces));

      for(int count = 0; count < interfaces.length; ++count) {
         JClass ifc1 = interfaces[count];
         if (!ifc1.getSimpleName().endsWith("Bean")) {
            list.remove(ifc1);
         } else {
            for(int subcount = count + 1; subcount < interfaces.length; ++subcount) {
               JClass ifc2 = interfaces[subcount];
               if (ifc2.isAssignableFrom(ifc1)) {
                  list.remove(ifc2);
               } else if (ifc1.isAssignableFrom(ifc2)) {
                  list.remove(ifc1);
                  break;
               }
            }
         }
      }

      return (JClass[])((JClass[])list.toArray(new JClass[0]));
   }

   private JMethod findIsSetter(JClass jClass, JProperty jProp) {
      String pName = jProp.getSimpleName();
      String mName = "is" + pName + "Set";
      JMethod[] m = jClass.getMethods();

      for(int i = 0; i < m.length; ++i) {
         if (m[i].getSimpleName().equals(mName) && m[i].getReturnType().getSimpleName().equals("boolean") && m[i].getParameters().length == 0) {
            return m[i];
         }
      }

      return null;
   }

   private JMethod findObjectFactory(JClass jClass, JProperty jProp) {
      String pName = jProp.getType().getSimpleName();
      if (pName.endsWith("Bean") || pName.endsWith("Bean[]")) {
         String mName = "createObject";
         JMethod[] m = jClass.getMethods();

         for(int i = 0; i < m.length; ++i) {
            if (m[i].getSimpleName().equals(mName) && m[i].getParameters().length == 1 && m[i].getParameters()[0].getType().getSimpleName().equals("Class")) {
               return m[i];
            }
         }
      }

      return null;
   }

   public JClass substituteClass(JClass declaredClass) {
      return this.substituteClasses.containsKey(declaredClass) ? (JClass)this.substituteClasses.get(declaredClass) : declaredClass;
   }

   private JClass getPrimaryInterface(JClass aClass) {
      JClass[] interfaces = aClass.getInterfaces();
      int cnt = interfaces.length;
      JClass candidate = null;
      int i = 0;

      for(int alen = interfaces.length; i < alen; ++i) {
         JClass anInterface = interfaces[i];
         String qualifiedName = anInterface.getQualifiedName();
         if (IGNOREABLE_INTERFACES.contains(qualifiedName)) {
            --cnt;
         } else {
            candidate = anInterface;
         }
      }

      if (cnt > 1) {
         this.verbose("WARNING: multiple interfaces found on " + aClass);
         return null;
      } else {
         return candidate;
      }
   }

   private void startMatch() {
      this.javaTypeByShortName.clear();
      this.javaTypeByFullName.clear();
   }

   private void recordFullyQualifiedJavaType(JClass key, JClass value) {
      String shortName = key.getSimpleName();
      String fullName = key.getQualifiedName();
      this.javaTypeByFullName.put(fullName, value);
      this.javaTypeByShortName.put(shortName, value);
   }

   private void recordJavaProperty(String shortName, Object value) {
      this.javaTypeByShortName.put(shortName, value);
   }

   private JElement lookupSchemaTypeName(QName name) {
      Object result = null;
      String localName = name.getLocalPart();
      String pkg = (String)this.getPackageForNamespace().get(name.getNamespaceURI());
      return pkg != null ? this.probeForName(this.javaTypeByFullName, pkg + ".", localName) : this.probeForName(this.javaTypeByShortName, "", localName);
   }

   private JElement lookupSchemaPropertyName(String localName) {
      JElement result = null;
      result = this.probeForName(this.javaTypeByShortName, "", localName);
      return result != null ? result : null;
   }

   private JElement probeForName(CaseInsensitiveMap table, String prefix, String nameToFind) {
      JElement result = (JElement)table.get(prefix + nameToFind);
      if (result != null) {
         return result;
      } else {
         String jaxbName = prefix + NameUtil.upperCamelCase(nameToFind);
         result = (JElement)table.get(jaxbName);
         if (result != null) {
            return result;
         } else {
            String beanName;
            if (jaxbName.endsWith("Type")) {
               beanName = jaxbName.substring(0, jaxbName.lastIndexOf("Type")) + "Bean";
               result = (JElement)table.get(beanName);
               if (result != null) {
                  return result;
               }
            }

            beanName = plural(jaxbName);
            result = (JElement)table.get(beanName);
            if (result == null && jaxbName.endsWith("PreDestroy") && beanName.endsWith("PreDestroies")) {
               beanName = beanName.substring(0, beanName.length() - 3) + "ys";
               result = (JElement)table.get(beanName);
            }

            return result;
         }
      }
   }

   private static String plural(String name) {
      return Utils.plural(name);
   }

   private void verbose(String msg) {
      System.out.println("XMLBindTypeMatcher::" + msg);
   }

   static {
      for(int i = 0; i < matchedPackageNamespaces.length; i += 2) {
         String[] namespaces = (String[])((String[])matchedPackageNamespaces[i + 1]);

         for(int count = 0; count < namespaces.length; ++count) {
            packageForNamespace.put(namespaces[count], matchedPackageNamespaces[i]);
         }

         if (namespaces.length > 1) {
            preferredNamespaceForPackage.put(matchedPackageNamespaces[i], namespaces[0]);
         }
      }

      IGNOREABLE_INTERFACES = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])(new String[]{"java.io.Serializable", "java.lang.Comparable", "java.lang.Cloneable"}))));
   }

   private static class CaseInsensitiveMap {
      private final Map caseSensitive = new HashMap();
      private final Map caseInsensitive = new HashMap();

      public CaseInsensitiveMap() {
      }

      public void put(String key, Object value) {
         this.caseSensitive.put(key, value);
         this.caseInsensitive.put(key.toLowerCase(Locale.US), value);
      }

      public Object get(String key) {
         Object result = this.caseSensitive.get(key);
         return result != null ? result : this.caseInsensitive.get(key.toLowerCase(Locale.US));
      }

      public void clear() {
         this.caseSensitive.clear();
         this.caseInsensitive.clear();
      }
   }
}
