package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import weblogic.descriptor.annotation.AnnotationDefinition;
import weblogic.descriptor.annotation.BeanAnnotations;
import weblogic.descriptor.annotation.PropertyAnnotations;
import weblogic.descriptor.codegen.Utils;
import weblogic.utils.Debug;

public class BeanClass extends BeanElement {
   private JClass beanInterface;
   private String superClassName;
   private Map properties = new TreeMap();
   private Set declaredProperties = new TreeSet();
   private Map methods = new TreeMap();
   private Set declaredMethods = new TreeSet();
   private List sortedDeclMethods;
   private Map operations = new TreeMap();
   private Set fields = new TreeSet();
   private BeanCustomizer customizer;
   private Map propertyMap = new HashMap();
   private Context options = Context.get();
   private Logger log = Context.get().getLog();
   private int propertyCount = 0;
   private ClassIntrospector baseClassIntrospector;
   private BindingInfos[] allBindingInfos;
   private BindingInfos[] keyBindingInfos;
   private BindingInfos[] singletonBindingInfos;
   private BindingInfos[] attributeBindingInfos;
   private BindingInfos[] childKeyBindingInfos;
   private boolean needsPostGenValidation = false;
   private Map delegateMap = new HashMap();
   String rootElementName;

   public BeanClass(JClass beanInterface) throws BeanGenerationException {
      super(beanInterface);
      this.beanInterface = beanInterface;
      this.customizer = this.createCustomizer(beanInterface);
      this.processInterfaces(beanInterface);
      this.implement();
      this.sortedDeclMethods = new ArrayList(this.declaredMethods);
      Collections.sort(this.sortedDeclMethods, new Comparator() {
         public int compare(Object o1, Object o2) {
            if (o1 instanceof MethodDeclaration && o2 instanceof MethodDeclaration) {
               MethodDeclaration m1 = (MethodDeclaration)o1;
               MethodDeclaration m2 = (MethodDeclaration)o2;
               return m1.getOrder() - m2.getOrder();
            } else {
               return -1;
            }
         }
      });
      this.initBindingInfos();
   }

   public String getPackageName() {
      return this.options.getPackageName();
   }

   public String[] getImports() {
      JClass[] imports = this.beanInterface.getImportedClasses();
      String[] names = new String[imports.length];

      for(int i = 0; i < names.length; ++i) {
         names[i] = imports[i].getQualifiedName();
      }

      return names;
   }

   public String getInterfaceName() {
      return this.beanInterface.getSimpleName();
   }

   public String getQualifiedInterfaceName() {
      return this.beanInterface.getQualifiedName();
   }

   public JClass getJClass() {
      return this.beanInterface;
   }

   public String getSuperClassName() {
      return this.superClassName == null ? this.options.getBaseClassName() : this.superClassName;
   }

   public boolean isAnnotationDefinedOnSuperInterface(String tag) {
      JClass[] ifs = this.getJClass().getInterfaces();
      if (ifs != null && ifs.length != 0) {
         BeanClass sif = new BeanClass(ifs[0]);
         return sif.isAnnotationDefined(tag);
      } else {
         return false;
      }
   }

   public String getQualifiedImplClassName() {
      String packageName = this.getQualifiedClassNameWithoutPackageSuffix();
      int lastPeriod = packageName.lastIndexOf(46);
      if (lastPeriod > -1) {
         packageName = packageName.substring(0, lastPeriod);
      }

      String implPackageSuffix = this.options.getOptions().getImplPackageSuffix();
      if (implPackageSuffix != null && implPackageSuffix.trim().length() > 0) {
         packageName = packageName + '.' + implPackageSuffix;
      }

      String className = this.getClassName();
      int beanInfoIndex = className.lastIndexOf("BeanInfo");
      if (beanInfoIndex > 0) {
         className = className.substring(0, beanInfoIndex);
      } else {
         className = this.getInterfaceName() + "Impl";
      }

      String result = packageName + '.' + className;
      return result;
   }

   public String getClassName() {
      if (this.options.isGenerateToCustom()) {
         String customizerClassName = this.getAnnotationString(BeanAnnotations.CUSTOMIZER);
         if (customizerClassName != null && customizerClassName.length() != 0) {
            int classNameIndex = customizerClassName.lastIndexOf(46) + 1;
            if (classNameIndex != -1) {
               customizerClassName = customizerClassName.substring(classNameIndex);
            }
         }

         return customizerClassName + this.options.getSuffix();
      } else {
         return this.options.interfaceToClassName(this.beanInterface);
      }
   }

   public boolean isAllowDiffWithSiblingClass() {
      return this.isAnnotationDefined(BeanAnnotations.ALLOW_DIFF_WITH_SIBLING_CLASS);
   }

   public String getQualifiedClassNameWithoutPackageSuffix() {
      return this.options.isGenerateToCustom() ? this.getAnnotationString(BeanAnnotations.CUSTOMIZER) + this.options.getSuffix() : this.options.interfaceToClassName(this.beanInterface, false, true);
   }

   public boolean isAbstract() {
      return this.isAnnotationDefined(BeanAnnotations.ABSTRACT);
   }

   public boolean isRoot() {
      return this.isAnnotationDefined(BeanAnnotations.ROOT);
   }

   public String[] getJavadocAnnotations() {
      List sl = new ArrayList();
      if (this.isAnnotationDefined(BeanAnnotations.REFERENCEABLE)) {
         sl.add(BeanAnnotations.REFERENCEABLE.toString());
      }

      if (this.isObsolete()) {
         sl.add("@xsdgen:element.exclude");
      }

      String typeName = this.getAnnotationString(BeanAnnotations.XML_TYPE_NAME);
      if (typeName == null) {
         typeName = XMLHelper.toTypeName(this.getInterfaceName());
      }

      sl.add("@xsdgen:complexType.typeName " + typeName);
      String namespace = this.getTargetNamespace();
      if (namespace != null) {
         sl.add("@xsdgen:complexType.targetNamespace " + namespace);
      }

      if (this.isRoot()) {
         String rootName = this.getAnnotationString(BeanAnnotations.ROOT);
         if (rootName != null) {
            sl.add("@xsdgen:complexType.rootElement " + XMLHelper.toElementName(this.getAnnotationString(BeanAnnotations.ROOT)));
         }
      }

      if (this.isAnnotationDefined(BeanAnnotations.BASE_INTERFACE) || this.getSuperClassName().equals(this.options.getBaseClassName())) {
         sl.add("@xsdgen:complexType.ignoreSuper");
      }

      return (String[])((String[])sl.toArray(new String[0]));
   }

   public String getTargetNamespace() {
      String namespace = Context.get().getOptions().getTargetNamespace();
      if (namespace == null || namespace.trim().equals("")) {
         namespace = this.getAnnotationString(BeanAnnotations.TARGET_NAMESPACE);
         if (namespace != null && namespace.trim().equals("")) {
            return null;
         }
      }

      return namespace;
   }

   public String getSchemaLocation() {
      String location = Context.get().getOptions().getSchemaLocation();
      if (location == null || location.trim().equals("")) {
         location = this.getAnnotationString(BeanAnnotations.SCHEMA_LOCATION);
         if (location != null && location.trim().equals("")) {
            return null;
         }
      }

      return location;
   }

   public PropertyDeclaration[] getProperties() {
      ArrayList array = new ArrayList(this.properties.values());
      return (PropertyDeclaration[])((PropertyDeclaration[])array.toArray(new PropertyDeclaration[0]));
   }

   public PropertyDeclaration[] getDeclaredProperties() {
      return (PropertyDeclaration[])((PropertyDeclaration[])this.declaredProperties.toArray(new PropertyDeclaration[0]));
   }

   public int getFirstDeclaredPropertyIndex() {
      return this.declaredProperties.size() == 0 ? -1 : ((PropertyDeclaration)this.declaredProperties.iterator().next()).getIndex();
   }

   public MethodDeclaration[] getMethods() {
      ArrayList array = new ArrayList(this.methods.values());
      return (MethodDeclaration[])((MethodDeclaration[])array.toArray(new MethodDeclaration[0]));
   }

   public MethodDeclaration[] getDeclaredMethods() {
      return (MethodDeclaration[])((MethodDeclaration[])this.sortedDeclMethods.toArray(new MethodDeclaration[0]));
   }

   public BeanField[] getFields() {
      return (BeanField[])((BeanField[])this.fields.toArray(new BeanField[0]));
   }

   public boolean hasCustomizer() {
      return this.customizer != null;
   }

   public BeanField getCustomizerField() {
      return this.customizer.getField();
   }

   public BeanCustomizer getCustomizer() {
      return this.customizer;
   }

   public boolean implementPostCreate() {
      return this.customizer == null ? false : this.customizer.definesMethod("_postCreate", new JClass[0]);
   }

   public boolean implementPreDestroy() {
      return this.customizer == null ? false : this.customizer.definesMethod("_preDestroy", new JClass[0]);
   }

   public boolean isImplementedByBaseClass(MethodDeclaration decl) {
      if (this.baseClassIntrospector == null) {
         this.baseClassIntrospector = new ClassIntrospector(Context.get().getBaseClass());
      }

      return this.baseClassIntrospector.definesMethod(decl);
   }

   public boolean implementIsPropertyAKey() {
      return this.keyBindingInfos != null;
   }

   public boolean implementIsPropertyASingleton() {
      return this.singletonBindingInfos != null;
   }

   public boolean implementIsPropertyAnAttribute() {
      return this.attributeBindingInfos != null;
   }

   public boolean implementIsPropertyAdditive() {
      return false;
   }

   public boolean implementIsChildPropertyAKey() {
      return this.childKeyBindingInfos != null;
   }

   public boolean implementSchemaHelperIsArray() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isArray()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsAttribute() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isAttribute()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsBean() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isBean()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsConfigurable() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isConfigurable()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasKey() {
      PropertyDeclaration[] decls = this.getDeclaredProperties();

      for(int i = 0; i < decls.length; ++i) {
         if (decls[i].isKey() || decls[i].isKeyComponent() || decls[i].isKeyChoice()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsKey() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isKey()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsKeyChoise() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isKeyChoice()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperIsKeyComponent() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isKeyComponent()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementMergePrepend() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isMergeRulePrependDefined()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementMergeIgnoreSource() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isMergeRuleIgnoreSourceDefined()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementMergeIgnoreTarget() {
      PropertyDeclaration[] ps = this.getProperties();

      for(int i = 0; i < ps.length; ++i) {
         if (ps[i].isMergeRuleIgnoreTargetDefined()) {
            return true;
         }
      }

      return false;
   }

   public boolean implementSchemaHelperHasKey() {
      return this.hasKey();
   }

   public boolean implementSchemaHelperGetRootElementName() {
      return this.isRoot();
   }

   public BindingInfos[] getAllBindingInfos() {
      return this.allBindingInfos;
   }

   public BindingInfos[] getKeyBindingInfos() {
      return this.keyBindingInfos;
   }

   public BindingInfos[] getSingletonBindingInfos() {
      return this.singletonBindingInfos;
   }

   public BindingInfos[] getAttributeBindingInfos() {
      return this.attributeBindingInfos;
   }

   public BindingInfos[] getAdditiveBindingInfos() {
      return null;
   }

   public BindingInfos[] getChildKeyBindingInfos() {
      return this.childKeyBindingInfos;
   }

   public String toString() {
      return this.getClassName();
   }

   public String getRootElementName() {
      if (this.rootElementName == null) {
         this.rootElementName = (new NameMangler(this)).getElementName();
      }

      return this.rootElementName;
   }

   private void addBindingInfos(PropertyDeclaration pd, ArrayList l) {
      BindingInfo bi = new BindingInfo(pd);
      Iterator i = l.iterator();

      BindingInfos bis;
      do {
         if (!i.hasNext()) {
            l.add(new BindingInfos(bi));
            return;
         }

         bis = (BindingInfos)i.next();
      } while(bis.getIndex() != bi.getElementName().length());

      bis.addBindingInfo(bi);
   }

   private void initBindingInfos() {
      this.allBindingInfos = this.initAllBindingInfos();
      this.keyBindingInfos = this.initBindingInfos(PropertyAnnotations.KEY);
      if (this.keyBindingInfos == null) {
         this.keyBindingInfos = this.initBindingInfos(PropertyAnnotations.KEY_CHOICE);
      }

      this.singletonBindingInfos = this.initBindingInfos(PropertyAnnotations.SINGLETON);
      this.attributeBindingInfos = this.initBindingInfos(PropertyAnnotations.ATTRIBUTE);
   }

   private BindingInfos[] initAllBindingInfos() {
      PropertyDeclaration[] pds = this.getDeclaredProperties();
      ArrayList l = null;

      for(int i = 0; i < pds.length; ++i) {
         this.addBindingInfos(pds[i], l == null ? (l = new ArrayList()) : l);
      }

      if (l != null) {
         return (BindingInfos[])((BindingInfos[])l.toArray(new BindingInfos[0]));
      } else {
         return null;
      }
   }

   private BindingInfos[] initBindingInfos(AnnotationDefinition annotation) {
      PropertyDeclaration[] pds = this.getDeclaredProperties();
      ArrayList l = null;
      ArrayList m = null;

      for(int i = 0; i < pds.length; ++i) {
         if (pds[i].isAnnotationDefined(annotation)) {
            if (!pds[i].isBean()) {
               this.addBindingInfos(pds[i], l == null ? (l = new ArrayList()) : l);
            } else {
               this.addBindingInfos(pds[i], m == null ? (m = new ArrayList()) : m);
            }
         }
      }

      if (m != null) {
         this.childKeyBindingInfos = (BindingInfos[])((BindingInfos[])m.toArray(new BindingInfos[0]));
      }

      if (l != null) {
         return (BindingInfos[])((BindingInfos[])l.toArray(new BindingInfos[0]));
      } else {
         return null;
      }
   }

   private void processInterfaces(JClass ifc) {
      JClass[] supers = this.reduceInterfaces(ifc.getInterfaces());
      if (supers.length > 0) {
         JClass superInterface = supers[0];
         if (superInterface.getQualifiedName().equals(this.options.getBaseInterfaceName())) {
            this.superClassName = this.options.getBaseClassName();
         } else {
            if (this.options.isGenerateToCustom()) {
               JAnnotationValue customizer = BeanAnnotations.CUSTOMIZER.getAnnotationValue(superInterface);
               if (customizer != null) {
                  String customizerClassName = customizer.asString();
                  this.superClassName = customizerClassName + this.options.getSuffix();
               }
            }

            if (this.superClassName == null) {
               this.superClassName = this.options.interfaceToClassName(superInterface);
            }
         }
      }

      Set processed = new HashSet();

      for(int i = 0; i < supers.length; ++i) {
         this.processInterfaceTree(supers[i], i != 0, processed);
      }

      this.processInterface(ifc, true, processed);
   }

   private void processInterfaceTree(JClass ifc, boolean declare, Set processed) {
      JClass[] supers = this.reduceInterfaces(ifc.getInterfaces());

      for(int i = 0; i < supers.length; ++i) {
         this.processInterfaceTree(supers[i], declare, processed);
      }

      this.processInterface(ifc, declare, processed);
   }

   private void processInterface(JClass ifc, boolean declare, Set processed) {
      if (!processed.contains(ifc)) {
         this.processMethods(ifc.getDeclaredMethods(), declare);
         processed.add(ifc);
      }

   }

   private void processMethods(JMethod[] methods, boolean declared) {
      List unmatched = new ArrayList();
      List foundProperties = new ArrayList();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].isPublic()) {
            MethodDeclaration m = this.createMethod(methods[i], declared, i);
            if (m != null) {
               if (!m.isType(PropertyMethodType.PROPERTY_METHOD)) {
                  this.operations.put(m, m);
               } else if (m.isType(PropertyMethodType.GETTER)) {
                  foundProperties.add(this.createProperty((PropertyMethodDeclaration)m, true));
               } else {
                  unmatched.add(m);
               }
            }
         }
      }

      this.associatePropertyMethods(unmatched);
      this.createDelegates(foundProperties);
   }

   private MethodDeclaration createMethod(JMethod method, boolean declare, int srcOrder) {
      MethodDeclaration m = MethodFactory.SINGLETON.createDeclaration(this, method, declare);
      if (m == null) {
         return null;
      } else {
         m.setOrder(srcOrder);
         MethodDeclaration existing = (MethodDeclaration)this.methods.put(m, m);
         if (existing != null) {
            m.setOrder(existing.getOrder());
         }

         return m;
      }
   }

   private PropertyDeclaration createProperty(PropertyMethodDeclaration getter, boolean replaceExisting) {
      PropertyDeclaration p = new PropertyDeclaration(getter, this.propertyCount);
      PropertyDeclaration existing = (PropertyDeclaration)this.properties.put(p, p);
      if (existing != null) {
         if (replaceExisting) {
            p.setIndex(existing.getIndex());
            PropertyMethodDeclaration[] methods = existing.getMethods();

            for(int i = 0; i < methods.length; ++i) {
               if (!methods[i].isType(PropertyMethodType.GETTER)) {
                  p.addMethod(methods[i], false);
               }
            }
         } else {
            this.properties.put(existing, existing);
            p = existing;
         }
      } else {
         ++this.propertyCount;
      }

      this.propertyMap.put(p.getName(), p);
      this.propertyMap.put(p.getComponentName(), p);
      return p;
   }

   private JClass[] reduceInterfaces(JClass[] interfaces) {
      List list = new ArrayList(Arrays.asList((Object[])interfaces));

      for(int count = 0; count < interfaces.length; ++count) {
         JClass ifc1 = interfaces[count];
         if (!Context.get().isBean(ifc1)) {
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

   private List associatePropertyMethods(List unmatched) {
      Iterator it = unmatched.iterator();

      while(it.hasNext()) {
         PropertyMethodDeclaration m = (PropertyMethodDeclaration)it.next();
         String pName = m.getPropertyName();
         PropertyDeclaration p = (PropertyDeclaration)this.propertyMap.get(pName);
         if (p != null) {
            p.addMethod(m, false);
            it.remove();
         } else if (m.isType(PropertyMethodType.CREATOR)) {
            PropertyDeclaration superProp = this.findSuperProperty(m);
            if (superProp != null) {
               superProp.addMethod(m, false);
               it.remove();
            }
         }
      }

      return unmatched;
   }

   private void createDelegates(List foundProperties) {
      Iterator it = foundProperties.iterator();

      while(it.hasNext()) {
         PropertyDeclaration declaration = (PropertyDeclaration)it.next();
         if (declaration.isDeclaredEncrypted()) {
            int count = this.propertyCount;
            PropertyDeclaration delegateDeclaration = this.createProperty(declaration.getEncryptionDelgateGetterTemplate(), false);
            declaration.setRestrictiveAccess();
            delegateDeclaration.setRestrictiveAccess();
            if (this.propertyCount > count) {
               this.properties.remove(delegateDeclaration);
            }

            if (delegateDeclaration.getSetter() != null) {
               delegateDeclaration.addMethod(declaration.getEncryptionDelgateSetterTemplate(), false);
            }

            this.delegateMap.put(declaration, delegateDeclaration);
         }
      }

   }

   private PropertyDeclaration findSuperProperty(PropertyMethodDeclaration creator) {
      Iterator it = this.properties.values().iterator();

      PropertyDeclaration p;
      JClass pClass;
      JClass mClass;
      do {
         if (!it.hasNext()) {
            return null;
         }

         p = (PropertyDeclaration)it.next();
         pClass = p.getComponentJClass();
         mClass = creator.getReturnJClass();
      } while(!pClass.isAssignableFrom(mClass));

      return p;
   }

   private String prependPackage(String className) {
      int offset = className.lastIndexOf(46);
      return offset > -1 ? className : this.getPackageName() + "." + className;
   }

   private void implement() throws BeanGenerationException {
      this.implementPropertyMethods(this.customizer);
      this.implementConstructors(this.customizer);
      this.implementOperations(this.customizer);
      this.implementBeanValidator(this.customizer);
      this.implementKeyGetter(this.customizer);
      if (this.log.getErrorCount() > 0) {
         throw new BeanGenerationException(this.log.getErrorCount() + " errors occurred during processing " + this.getInterfaceName());
      }
   }

   private void implementConstructors(BeanCustomizer customizer) {
      List constructors = new ArrayList();
      constructors.add(ConstructorType.CONSTRUCTOR.createDeclaration(this, new JClass[0]));
      constructors.add(ConstructorType.CONSTRUCTOR.createDeclaration(this, new JClass[]{JClasses.DESCRIPTOR_BEAN, JClasses.INT}));
      constructors.add(ConstructorType.CONSTRUCTOR.createDeclaration(this, new JClass[]{JClasses.DESCRIPTOR_BEAN, JClasses.INT, JClasses.BOOLEAN}));
      Iterator it = constructors.iterator();

      while(it.hasNext()) {
         MethodDeclaration constructor = (MethodDeclaration)it.next();
         constructor.implement(customizer);
         this.methods.put(constructor, constructor);
         this.declaredMethods.add(constructor);
      }

   }

   private void implementOperations(BeanCustomizer customizer) {
      Iterator it = this.operations.values().iterator();

      while(it.hasNext()) {
         MethodDeclaration m = (MethodDeclaration)it.next();
         if (!m.implement(customizer).isImplementedBySuperClass()) {
            this.declaredMethods.add(m);
         }
      }

   }

   private void implementPropertyMethods(BeanCustomizer customizer) {
      Iterator it = this.properties.values().iterator();

      while(it.hasNext()) {
         PropertyDeclaration pd = (PropertyDeclaration)it.next();
         if (!this.delegateMap.containsValue(pd)) {
            PropertyImplementation pi = this.implementProperty(pd, customizer);
            if (pd.isDeclaredEncrypted()) {
               PropertyDeclaration delegateDecl = (PropertyDeclaration)this.delegateMap.get(pd);
               delegateDecl.setDelegatedProperty(pd);
               if (!pi.isImplementedBySuperClass()) {
                  if (!Context.get().getNoSynthetics()) {
                     pi.implementDelegate(delegateDecl);
                     this.registerDeclaration(delegateDecl);
                  } else if (this.properties.containsValue(delegateDecl)) {
                     this.implementProperty(delegateDecl, customizer);
                  }
               }
            }
         }
      }

   }

   private PropertyImplementation implementProperty(PropertyDeclaration declaration, BeanCustomizer customizer) {
      PropertyImplementation pi = declaration.implement(customizer);
      if (declaration.needsPostGenValidation() && !this.needsPostGenValidation) {
         this.needsPostGenValidation = true;
      }

      this.registerDeclaration(declaration);
      return pi;
   }

   private void registerDeclaration(PropertyDeclaration pd) {
      PropertyImplementation pi = pd.getImplementation();
      if (!pi.isImplementedBySuperClass()) {
         this.declaredProperties.add(pd);
         if (pi.getField() != null) {
            this.fields.add(pi.getField());
         }

         MethodDeclaration[] pMethods = pd.getMethods();

         for(int mi = 0; mi < pMethods.length; ++mi) {
            MethodDeclaration m = pMethods[mi];
            this.methods.put(m, m);
            if (!m.getImplementation().isImplementedBySuperClass()) {
               this.declaredMethods.add(m);
            }
         }
      }

   }

   private void implementBeanValidator(BeanCustomizer customizer) {
      MethodDeclaration m = MethodType.VALIDATOR.createDeclaration(this);
      m.implement(customizer);
      this.methods.put(m, m);
      this.declaredMethods.add(m);
   }

   private void implementKeyGetter(BeanCustomizer customizer) {
      MethodDeclaration m = MethodType.KEY_GETTER.createDeclaration(this);
      m.implement(customizer);
      this.methods.put(m, m);
      this.declaredMethods.add(m);
   }

   private BeanCustomizer createCustomizer(JClass ifc) throws BeanGenerationException {
      String className = this.getAnnotationString(BeanAnnotations.CUSTOMIZER);
      if (className == null) {
         return null;
      } else {
         className = this.prependPackage(className);
         String factoryClassName = this.getAnnotationString(BeanAnnotations.CUSTOMIZER_FACTORY);
         BeanCustomizer customizer;
         if (factoryClassName != null && factoryClassName.length() != 0) {
            customizer = BeanCustomizer.create(ifc, className, factoryClassName);
         } else {
            customizer = BeanCustomizer.create(ifc, className);
         }

         if (customizer == null) {
            this.log.error("Unable to create customizer " + className, ifc);
            throw new BeanGenerationException("Unable to create customizer " + className);
         } else {
            this.fields.add(customizer.getField());
            return customizer;
         }
      }
   }

   private void dump() {
      Debug.say("---------------------");
      Debug.say(this.getClassName());
      Debug.say("---------------------");
      Debug.say("  extends " + this.getSuperClassName());
      Debug.say("  implements " + this.getInterfaceName());
      Debug.say("  // fields --------------------");
      this.dumpArray(this.getFields());
      Debug.say("  // methods -------------------");
      this.dumpArray(this.getDeclaredMethods());
   }

   private void dumpArray(Object[] array) {
      for(int i = 0; i < array.length; ++i) {
         Debug.say("    " + array[i]);
      }

   }

   private void debug(String s) {
      Debug.say(s);
   }

   public boolean needsPostGenValidation() {
      return this.needsPostGenValidation;
   }

   public static class NameMangler {
      String elementName;
      private String[] acronyms = new String[]{"JNDI", "TagLib", "URI", "XA", "JDBC", "SAF", "JMS", "WLDF", "URL", "HSQL", "SQL", "JDOR", "JDO", "JVM"};
      private String[] replacements = new String[]{"Jndi", "Taglib", "Uri", "Xa", "Jdbc", "Saf", "Jms", "Wldf", "Url", "Hsql", "Sql", "Jdor", "Jdo", "Jvm"};

      NameMangler(BeanElement be) {
         this.elementName = this.getNormalizedElementName(be);
      }

      public String getElementName() {
         return this.elementName;
      }

      protected StringBuffer filterName(BeanElement be) {
         if (be instanceof PropertyDeclaration) {
            PropertyDeclaration prop = (PropertyDeclaration)be;
            return new StringBuffer(prop.isArray() ? Utils.singular(prop.getName()) : prop.getName());
         } else {
            BeanClass bc = (BeanClass)be;
            String n = bc.getClassName();
            return new StringBuffer(n.substring(0, n.length() - "BeanImpl".length()));
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

      private int nextLowerCaseAfterCap(StringBuffer buffer) {
         if (buffer.length() > 1) {
            for(int count = 1; count < buffer.length(); ++count) {
               if (Character.isLowerCase(buffer.charAt(count)) && Character.isUpperCase(buffer.charAt(count - 1))) {
                  return count;
               }
            }
         }

         return buffer.length();
      }

      private void toLowerCase(StringBuffer buffer, int startIndex, int endIndex) {
         for(int count = startIndex; count < endIndex; ++count) {
            buffer.setCharAt(count, Character.toLowerCase(buffer.charAt(count)));
         }

      }

      private String getNormalizedElementName(BeanElement be) {
         String elementName = be.getAnnotationString(PropertyAnnotations.XML_ELEMENT_NAME);
         if (elementName != null) {
            return elementName;
         } else {
            StringBuffer name = this.filterName(be);

            int lastStop;
            int foundIndex;
            for(lastStop = 0; lastStop < this.acronyms.length; ++lastStop) {
               foundIndex = name.indexOf(this.acronyms[lastStop]);
               if (foundIndex > -1) {
                  name = name.replace(foundIndex, foundIndex + this.acronyms[lastStop].length(), this.replacements[lastStop]);
               }
            }

            if (name.length() > 0) {
               for(lastStop = 0; this.hasCaps(name); lastStop = foundIndex) {
                  foundIndex = this.nextLowerCaseAfterCap(name);
                  this.toLowerCase(name, lastStop, foundIndex);
                  if (foundIndex < name.length() && foundIndex > 1) {
                     name.insert(foundIndex - 1, '-');
                  }
               }
            }

            return name.toString();
         }
      }
   }

   public static class BindingInfo extends NameMangler {
      PropertyDeclaration pd;

      BindingInfo(PropertyDeclaration pd) {
         super(pd);
         this.pd = pd;
      }

      public int getPropertyIndex() {
         return this.pd.getIndex();
      }
   }

   public static class BindingInfos {
      BindingInfo[] infos;

      public BindingInfos(BindingInfo bi) {
         this.infos = new BindingInfo[]{bi};
      }

      public BindingInfos(PropertyDeclaration pd) {
         this(new BindingInfo(pd));
      }

      public int getIndex() {
         return this.infos[0].getElementName().length();
      }

      public BindingInfo[] getKeyInfos() {
         return this.infos;
      }

      public void addBindingInfo(BindingInfo info) {
         BindingInfo[] tmp = new BindingInfo[this.infos.length + 1];

         for(int i = 0; i < this.infos.length; ++i) {
            tmp[i] = this.infos[i];
         }

         tmp[this.infos.length] = info;
         this.infos = tmp;
      }
   }
}
