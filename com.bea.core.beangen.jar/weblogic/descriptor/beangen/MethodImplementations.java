package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JParameter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.descriptor.annotation.BeanAnnotations;
import weblogic.descriptor.annotation.PropertyAnnotations;

public class MethodImplementations {
   public static final MethodFactory.ImplementationFactory FACTORY = new MethodFactory.ImplementationFactory() {
      private MethodFactory.ImplementationFactory[] getSubFactories() {
         Class[] classes = MethodImplementations.class.getDeclaredClasses();
         MethodFactory.ImplementationFactory[] subFactories = new MethodFactory.ImplementationFactory[classes.length];

         for(int i = 0; i < classes.length; ++i) {
            try {
               Field field = classes[i].getField("FACTORY");
               subFactories[i] = (MethodFactory.ImplementationFactory)field.get((Object)null);
            } catch (IllegalAccessException var5) {
               throw new AssertionError(classes[i].getName() + ".FACTORY must be visible");
            } catch (NoSuchFieldException var6) {
               throw new AssertionError(classes[i].getName() + " must define \"static FACTORY\"");
            }
         }

         return subFactories;
      }

      public MethodImplementation create(MethodDeclaration decl, BeanCustomizer customizer) {
         MethodFactory.ImplementationFactory[] subFactories = this.getSubFactories();
         MethodImplementation impl = null;

         for(int i = 0; i < subFactories.length && impl == null; ++i) {
            impl = subFactories[i].create(decl, customizer);
         }

         return impl;
      }
   };

   private static boolean isBeanArrayDelegate(PropertyDeclaration prop) {
      return prop.isArray() && prop.isBean() && prop.isChild() && prop.isAnnotationDefinedOnPropertyInterface(BeanAnnotations.DELEGATE_BEAN.toString());
   }

   public static class KeyGetter extends MethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public KeyGetter(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      private String getKeyValue(PropertyDeclaration prop, boolean isKeyComponent) {
         String propGetter = prop.getGetter().getName() + "()";
         int index = prop.getIndex();
         if (prop.getJClass().equals(JClasses.STRING)) {
            return propGetter;
         } else if (prop.getJClass().equals(JClasses.STRING_ARRAY)) {
            return "new CompoundKey(" + propGetter + ")";
         } else if (prop.getJClass().equals(JClasses.INT)) {
            return "!_isSet(" + index + ") ? null : new Integer(" + propGetter + ")";
         } else if (prop.isBean()) {
            return "_getBeanKey((AbstractDescriptorBean)" + propGetter + ")";
         } else if (isKeyComponent) {
            if (prop.getJClass().equals(JClasses.BOOLEAN)) {
               return "!_isSet(" + index + ") ? null : new Boolean(" + propGetter + ")";
            } else {
               prop.error("@keyComponent may only be used on a String, String[], int, boolean or DescriptorBean property");
               return "null";
            }
         } else {
            prop.error("@key/@keyChoice may only be used on a String, String[], int or DescriptorBean property");
            return "null";
         }
      }

      protected void addBody(List sl) {
         MethodDeclaration decl = this.getDeclaration();
         PropertyDeclaration[] props = decl.getBean().getDeclaredProperties();
         PropertyDeclaration keyProperty = null;
         List keyChoiceList = new ArrayList();
         List keyComponentList = new ArrayList();

         PropertyDeclaration prop;
         for(int i = 0; i < props.length; ++i) {
            prop = props[i];
            if (prop.isAnnotationDefined(PropertyAnnotations.KEY)) {
               if (keyProperty != null) {
                  prop.error("It is illegal to designate multiple properties as @key; " + keyProperty + " is also defined as a @key at " + keyProperty.getLocation());
               } else {
                  keyProperty = prop;
               }
            }

            if (prop.isAnnotationDefined(PropertyAnnotations.KEY_CHOICE)) {
               keyChoiceList.add(prop);
            }

            if (prop.isAnnotationDefined(PropertyAnnotations.KEY_COMPONENT)) {
               keyComponentList.add(prop);
            }
         }

         BeanElement element;
         if (keyProperty != null) {
            if (keyComponentList.size() > 0) {
               element = (BeanElement)keyComponentList.get(0);
               element.error("It is illegal to designate a @keyComponent since a @key has already been defined at " + keyProperty.getLocation());
            } else if (keyChoiceList.size() > 0) {
               element = (BeanElement)keyChoiceList.get(0);
               element.error("It is illegal to designate a @keyChoice since a @key has already been defined at " + keyProperty.getLocation());
            } else {
               sl.add("return " + this.getKeyValue(keyProperty, false) + ";");
            }
         } else {
            Iterator it;
            if (keyChoiceList.size() > 0) {
               if (keyComponentList.size() > 0) {
                  element = (BeanElement)keyChoiceList.get(0);
                  BeanElement component = (BeanElement)keyComponentList.get(0);
                  element.error("It is illegal to designate a @keyChoice since a @keyComponent has been defined at " + component.getLocation());
               } else {
                  sl.add("Object keyChoice = null;");
                  it = keyChoiceList.iterator();

                  while(it.hasNext()) {
                     prop = (PropertyDeclaration)it.next();
                     sl.add("if (keyChoice == null) keyChoice = " + this.getKeyValue(prop, false) + ";");
                  }

                  sl.add("return keyChoice;");
               }
            } else if (keyComponentList.size() > 0) {
               sl.add("return new CompoundKey(new Object[] {");
               it = keyComponentList.iterator();

               while(it.hasNext()) {
                  prop = (PropertyDeclaration)it.next();
                  sl.add("  " + this.getKeyValue(prop, true) + ",");
               }

               sl.add("});");
            } else {
               sl.add("return super._getKey();");
            }
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(MethodType.KEY_GETTER, KeyGetter.class);
      }
   }

   public static class Validator extends MethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Validator(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addBody(List sl) {
         MethodDeclaration decl = this.getDeclaration();
         String beanValidator = decl.getBean().getAnnotationString(BeanAnnotations.VALIDATOR);
         sl.add("super._validate();");
         if (beanValidator != null) {
            sl.add(beanValidator + "(this);");
         }

         PropertyDeclaration[] props = decl.getBean().getDeclaredProperties();
         String lc = "weblogic.descriptor.beangen.LegalChecks.";
         ArrayList deferredValidatorList = new ArrayList();

         for(int i = 0; i < props.length; ++i) {
            String validator = props[i].getAnnotationString(PropertyAnnotations.DEFERRED_VALIDATOR);
            if (validator != null && !deferredValidatorList.contains(validator)) {
               deferredValidatorList.add(validator);
               if (validator.indexOf(40) == -1) {
                  validator = validator + "(this);";
               } else {
                  validator = this.replaceExpressions(validator, props[i].getGetter().getName() + "()") + ";";
               }

               sl.add(validator);
            }

            if (props[i].isAnnotationDefined(PropertyAnnotations.REQUIRED)) {
               String name = props[i].getName();
               BeanClass bc = this.getDeclaration().getBean();
               if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
                  sl.add(lc + "checkIsSet(\"" + name + "\", is" + name + "Set() || is" + name + "Inherited());");
               } else {
                  sl.add(lc + "checkIsSet(\"" + name + "\", is" + name + "Set());");
               }
            }
         }

         if (this.isImplementedByCustomizer()) {
            this.addDelegateToCustomizer(sl, "");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(MethodType.VALIDATOR, Validator.class);
      }
   }

   public static class Operation extends MethodImplementation {
      public static final MethodFactory.ImplementationFactory FACTORY;

      public Operation(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
         if (decl.isDeclared() && !BeanAnnotations.ABSTRACT.isDefined(decl.getDeclaringClass()) && !decl.getBean().isImplementedByBaseClass(decl)) {
            if (customizer == null) {
               decl.error("No customizer specified to implement custom operation " + decl);
            } else if (!this.isImplementedByCustomizer()) {
               decl.error("Custom operation " + decl + " must be implemented by " + customizer.getField().getType());
            }
         }

      }

      protected void addBody(List sl) {
         if (this.isImplementedByCustomizer()) {
            this.addDelegateToCustomizer(sl, this.rJClass().isVoidType() ? "" : "return ");
         } else {
            sl.add("throw new AssertionError(\"Method not implemented\");");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(MethodType.OPERATION, Operation.class);
      }
   }

   public static class Finder extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Finder(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addBody(List sl) {
         PropertyMethodDeclaration decl = this.getPropertyMethodDeclaration();
         String paramName = decl.getMethod().getParameters()[0].getSimpleName();
         String keyName = Character.toUpperCase(paramName.charAt(0)) + paramName.substring(1);
         String rt = Context.get().interfaceToClassName(this.rJClass());
         BeanClass bc = this.getPropertyMethodDeclaration().getBean();
         String pfName = this.pfName();
         if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null && MethodImplementations.isBeanArrayDelegate(this.prop())) {
            pfName = "get" + this.pName() + "()";
         }

         if (JClasses.LIST.isAssignableFrom(this.pJClass())) {
            sl.add("List llist = (List) " + pfName);
            sl.add("int size = llist.size();");
            sl.add("java.util.ListIterator it = llist.listIterator(size);");
         } else {
            sl.add("Object[] aary = (Object[]) " + pfName + ";");
            sl.add("int size = aary.length;");
            sl.add("java.util.ListIterator it = java.util.Arrays.asList(aary).listIterator(size);");
         }

         sl.add("while (it.hasPrevious()) {");
         sl.add("  " + rt + " bean = (" + rt + ")it.previous();");
         sl.add("  Object key = " + this.params() + ";");
         sl.add("  if (bean.get" + keyName + "().equals(key)) {");
         sl.add("    return bean;");
         sl.add("  }");
         sl.add("}");
         sl.add("return null;");
      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.FINDER, Finder.class);
      }
   }

   public static class Destroyer extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Destroyer(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addUnregisterCode(List sl, String intendation) {
         if (JClasses.LIST.isAssignableFrom(this.pJClass())) {
            sl.add(intendation + this.pfName() + ".remove(param0);");
         } else {
            sl.add(intendation + this.pType() + " _old = get" + this.pName() + "();");
            sl.add(intendation + this.pType() + " _new = (" + this.pType() + ")_getHelper()._removeElement(_old, " + this.pcType() + ".class, param0);");
            sl.add(intendation + "if (_old.length != _new.length) {");
            if (this.prop().isChild()) {
               sl.add(intendation + "  _preDestroy((AbstractDescriptorBean)param0);");
            }

            this.beginImpossibleExceptionWrapper(sl, intendation + "  ");
            if (this.prop().isChild()) {
               this.addBeanRemoveCode(sl, "param0", intendation + "    ");
               sl.add(intendation + "    set" + this.pName() + "(_new);");
            }

            this.endImpossibleExceptionWrapper(sl, intendation + "  ");
            sl.add(intendation + "}");
         }

      }

      private void addBeanRemoveCode(List sl, String param, String indent) {
         if (this.prop().isChild()) {
            sl.add(indent + "AbstractDescriptorBean _child = (AbstractDescriptorBean)" + param + ";");
            sl.add(indent + "if (_child == null) return;");
            sl.add(indent + "List _refs = _getReferenceManager().getResolvedReferences(_child);");
            sl.add(indent + "if (_refs != null && _refs.size() > 0) {");
            sl.add(indent + "  throw new BeanRemoveRejectedException(_child, _refs);");
            sl.add(indent + "} else {");
            sl.add(indent + "  _getReferenceManager().unregisterBean(_child);");
            sl.add(indent + "  _markDestroyed(_child);");
            sl.add(indent + "}");
            BeanClass bc = this.getPropertyMethodDeclaration().getBean();
            if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
               PropertyMethodDeclaration lookupMethodDecl = this.prop().findMethod(PropertyMethodType.FINDER);
               if (lookupMethodDecl == null) {
                  return;
               }

               sl.add(indent + "for (" + this.prop().getBean().getClassName() + " source : _DelegateSources) {");
               String lookupMethodName = lookupMethodDecl.getName();
               sl.add(indent + "  " + this.pcImpl() + " childImpl = (" + this.pcImpl() + ") _child;");
               sl.add(indent + "  final " + this.pcImpl() + " lookup = (" + this.pcImpl() + ")source." + lookupMethodName + "(childImpl.getName());");
               sl.add(indent + "  if (lookup != null) {");
               PropertyMethodDeclaration decl = this.getPropertyMethodDeclaration();
               String methodName = decl.getMethod().getSimpleName();
               sl.add(indent + "    source." + methodName + "(lookup);");
               sl.add(indent + "  }");
               sl.add(indent + "}");
            }
         }

      }

      protected void addBody(List sl) {
         this.beginImpossibleExceptionWrapper(sl);
         this.addRemoveValidator(sl, "param0", this.prop());
         if (this.prop().hasAddressableComponents()) {
            sl.add("  _checkIsPotentialChild(param0, " + this.prop().getIndex() + ");");
            this.addUnregisterCode(sl, "  ");
         } else {
            this.addBeanRemoveCode(sl, this.pfName(), "  ");
            sl.add("  set" + this.pName() + "(null);");
            sl.add("  _unSet(" + this.prop().getIndex() + ");");
         }

         this.endImpossibleExceptionWrapper(sl);
      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.DESTROYER, Destroyer.class);
      }
   }

   public static class Remover extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Remover(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addBody(List sl) {
         this.addRemoveValidator(sl, "param0", this.prop());
         if (this.isImplementedByCustomizer()) {
            JClass customizerReturnJClass = this.getCustomizer().getMethod(this.getDeclaration()).getReturnJClass();
            if (!customizerReturnJClass.isVoidType() && !this.rJClass().isVoidType()) {
               this.addDelegateToCustomizer(sl, "return ");
            } else {
               this.addDelegateToCustomizer(sl, "");
               if (!this.rJClass().isVoidType()) {
                  sl.add("    return true;");
               }
            }
         } else if (JClasses.LIST.isAssignableFrom(this.pJClass())) {
            if (this.rJClass().isVoidType()) {
               sl.add(this.pfName() + ".remove(param0);");
            } else {
               sl.add("return " + this.pfName() + ".remove(param0);");
            }
         } else if (this.prop().isChild() && this.prop().getDestroyer() != null) {
            sl.add(this.prop().getDestroyer().getName() + "(param0);");
            if (!this.rJClass().isVoidType()) {
               sl.add("return true;");
            }
         } else {
            sl.add(this.pType() + " _old = get" + this.pName() + "();");
            sl.add(this.pType() + " _new = (" + this.pType() + ")_getHelper()._removeElement(_old, " + this.pcType() + ".class, param0);");
            sl.add("if (_new.length != _old.length) {");
            if (this.prop().isChild()) {
               sl.add("  _preDestroy((AbstractDescriptorBean)param0);");
            }

            this.beginImpossibleExceptionWrapper(sl, "  ");
            if (this.prop().isChild()) {
               sl.add("  _getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);");
            }

            sl.add("    set" + this.pName() + "(_new);");
            this.endImpossibleExceptionWrapper(sl, "  ");
            if (!this.rJClass().isVoidType()) {
               sl.add("  return true;");
            }

            sl.add("};");
            if (!this.rJClass().isVoidType()) {
               sl.add("return false;");
            }
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.REMOVER, Remover.class);
      }
   }

   public static class Creator extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Creator(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addBody(List sl) {
         PropertyMethodDeclaration decl = this.getPropertyMethodDeclaration();
         JParameter[] params = decl.getMethod().getParameters();
         JClass type = decl.getReturnJClass();
         String implClass = Context.get().interfaceToClassName(type);
         if (this.isImplementedByCustomizer()) {
            this.addDelegateToCustomizer(sl, "return ");
         } else {
            if (params.length > 0) {
               PropertyMethodDeclaration lookupMethodDecl = this.prop().findMethod(PropertyMethodType.FINDER);
               if (lookupMethodDecl != null && lookupMethodDecl.getParamTypes().length == 1 && lookupMethodDecl.getParamTypes()[0].equals(String.class.getSimpleName()) && JClasses.STRING.isAssignableFrom(params[0].getType()) && params[0].getSimpleName().equals("name") && this.prop().hasAddressableComponents() && this.prop().isBean()) {
                  String lookupMethodName = lookupMethodDecl.getName();
                  sl.add("final " + implClass + " lookup = (" + implClass + ")" + lookupMethodName + "(param0);");
                  sl.add("if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {");
                  sl.add("  throw new BeanAlreadyExistsException(\"Bean already exists: \" + lookup);");
                  sl.add("}");
               }
            }

            int curParam = 0;
            String paramName;
            if (params.length > 0 && JClasses.CLASS.isAssignableFrom(params[0].getType())) {
               sl.add(implClass + " _val = (" + implClass + ")_createChildBean(param0, -1);");
               ++curParam;
            } else if (params.length > 0 && decl.isCreatorClone() && type.isAssignableFrom(params[0].getType())) {
               StringBuffer props = new StringBuffer();

               for(int i = curParam + 1; i < params.length; ++i) {
                  paramName = params[i].getSimpleName();
                  String propName = Character.toUpperCase(paramName.charAt(0)) + paramName.substring(1);
                  if (i == params.length - 1) {
                     props.append("\"" + propName + "\"");
                  } else {
                     props.append("\"" + propName + "\", ");
                  }
               }

               sl.add("List excludeProps = Arrays.asList(new String[] {" + props.toString() + "});");
               sl.add(implClass + " _val = (" + implClass + ")_cloneInternal((AbstractDescriptorBean) param" + curParam + ", false, excludeProps);");
               ++curParam;
            } else {
               sl.add(implClass + " _val = new " + implClass + "(this, -1);");
            }

            this.beginImpossibleExceptionWrapper(sl);

            for(int i = curParam; i < params.length; ++i) {
               String paramName = params[i].getSimpleName();
               paramName = Character.toUpperCase(paramName.charAt(0)) + paramName.substring(1);
               sl.add("  _val.set" + paramName + "(param" + i + ");");
            }

            if (this.prop().hasAddressableComponents()) {
               sl.add("  add" + this.pcName() + "(_val);");
            } else {
               sl.add("  set" + this.pName() + "(_val);");
            }

            this.endImpossibleExceptionWrapper(sl);
            sl.add("return _val;");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.CREATOR, Creator.class);
      }
   }

   public static class Adder extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Adder(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addAdderCode(List sl, String value) {
         if (JClasses.LIST.isAssignableFrom(this.pJClass())) {
            if (this.rJClass().isVoidType()) {
               sl.add("get" + this.pName() + "().add(" + value + ");");
            } else {
               sl.add("return get" + this.pName() + "().add(" + value + ");");
            }
         } else {
            String ifBeanIntendation = "";
            sl.add("_getHelper()._ensureNonNull(" + value + ");");
            if (this.prop().isBean()) {
               sl.add("if (!((AbstractDescriptorBean)" + value + ").isChildProperty(this, " + this.prop().getIndex() + ")) {");
               ifBeanIntendation = "  ";
            }

            sl.add(ifBeanIntendation + this.pType() + " _new;");
            String arrayExtensionCode = "_new = (" + this.pType() + ") _getHelper()._extendArray(get" + this.pName() + "(), " + this.pcType() + ".class, " + value + ");";
            if (!this.prop().getGetter().getImplementation().isImplementedByCustomizer() && !this.prop().isTransient()) {
               sl.add(ifBeanIntendation + "if (_isSet(" + this.prop().getIndex() + ")) {");
               sl.add(ifBeanIntendation + "  " + arrayExtensionCode);
               sl.add(ifBeanIntendation + "} else {");
               sl.add(ifBeanIntendation + "  _new = new " + this.pType() + " { " + value + " };");
               sl.add(ifBeanIntendation + "}");
            } else {
               sl.add(ifBeanIntendation + arrayExtensionCode);
            }

            this.beginImpossibleExceptionWrapper(sl, ifBeanIntendation);
            sl.add(ifBeanIntendation + "  set" + this.pName() + "(_new);");
            this.endImpossibleExceptionWrapper(sl, ifBeanIntendation);
            if (this.prop().isBean()) {
               sl.add("}");
            }
         }

      }

      protected void addBody(List sl) {
         if (this.isImplementedByCustomizer()) {
            JClass customizerReturnJClass = this.getCustomizer().getMethod(this.getDeclaration()).getReturnJClass();
            if (!customizerReturnJClass.isVoidType() && !this.rJClass().isVoidType()) {
               this.addDelegateToCustomizer(sl, "return ");
               return;
            }

            this.addDelegateToCustomizer(sl, "");
         } else {
            this.addAdderCode(sl, "param0");
         }

         if (!this.rJClass().isVoidType()) {
            sl.add("return true;");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.ADDER, Adder.class);
      }
   }

   public static class IsInherited extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public IsInherited(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addComments(List sl) {
         sl.add("return true if the value of property " + this.prop().getName() + " is inherited from template");
      }

      protected void addBody(List sl) {
         int index = this.prop().getIndex();
         BeanClass bc = this.getPropertyMethodDeclaration().getBean();
         if (!bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) || bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) != null || this.prop().getSetter() == null && !this.prop().isArray()) {
            sl.add("return false;");
         } else if (MethodImplementations.isBeanArrayDelegate(this.prop()) && !this.prop().isTransient()) {
            sl.add("if(_getDelegateBean() != null && _getDelegateBean()._isSet(" + index + ")) {");
            sl.add("  " + this.pType() + " elements = " + this.prop().getGetter().getName() + "();");
            sl.add("  for (Object o : elements) { ");
            sl.add("    if (o instanceof AbstractDescriptorBean) {");
            sl.add("      AbstractDescriptorBean adBean = (AbstractDescriptorBean) o;");
            sl.add("      if (!adBean._isTransient() || !adBean._isSynthetic()) {");
            sl.add("        return false;");
            sl.add("      }  ");
            sl.add("    }");
            sl.add("  }");
            sl.add("  return true;");
            sl.add("  } else {");
            sl.add("    return false;");
            sl.add("  }");
         } else {
            sl.add("  return (!_isSet(" + index + ") && _getDelegateBean() != null && _getDelegateBean()._isSet(" + index + "));");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.IS_INHERITED, IsInherited.class);
      }
   }

   public static class IsSet extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public IsSet(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addComments(List sl) {
         sl.add("@xsdgen:isSetMethodFor " + this.prop().getXMLElementName());
      }

      protected void addBody(List sl) {
         if (this.prop().isChild() && this.prop().isReadOnly() && !this.prop().isArray()) {
            sl.add("return _isSet(" + this.prop().getIndex() + ") ||");
            sl.add("  _isAnythingSet((AbstractDescriptorBean)" + this.prop().getGetter().getName() + "());");
         } else if (this.prop().isDeclaredEncrypted() && this.prop().getImplementation().getDelegate() != null) {
            PropertyDeclaration delegateDeclaration = this.prop().getImplementation().getDelegate().getDeclaration();
            sl.add("return is" + delegateDeclaration.getName() + "Set();");
         } else {
            sl.add("return _isSet(" + this.prop().getIndex() + ");");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.ISSET, IsSet.class);
      }
   }

   public static class StringSetter extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public StringSetter(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addRegisterReferenceCode(List sl, String ref, String resolver) {
         sl.add("  _getReferenceManager().registerUnresolvedReference(");
         sl.add("    " + ref + ", " + this.pcType() + ".class,");
         if (this.prop().isArray()) {
            sl.add("    new Resolver(this, " + this.prop().getIndex() + ", param0) {");
         } else {
            sl.add("    new Resolver(this, " + this.prop().getIndex() + ") {");
         }

         sl.add("      public void resolveReference(Object value) {");
         sl.add("        try {");
         sl.add("          " + resolver);
         if (this.prop().isArray()) {
            sl.add("          _getHelper().reorderArrayObjects((Object[]) " + this.pfName() + ", getHandbackObject());");
         }

         sl.add("        } catch (RuntimeException e) {");
         sl.add("          throw e;");
         sl.add("        } catch (Exception e) {");
         sl.add("          throw new AssertionError(\"Impossible exception: \" + e);");
         sl.add("        }");
         sl.add("      }");
         sl.add("    }");
         sl.add("  );");
      }

      protected void addBody(List sl) {
         if (this.prop().isBean()) {
            sl.add("if (param0 == null || param0.length() == 0) {");
            int i = this.prop().getIndex();
            sl.add("  " + this.pType() + " _oldVal = " + this.pfName() + ";");
            sl.add("  _initializeProperty(" + i + ");");
            BeanClass bc = this.getPropertyMethodDeclaration().getBean();
            if (this.pName().equals(bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN))) {
               sl.add("  _setDelegateBean(null);");
            }

            sl.add("  _postSet(" + i + ", _oldVal, " + this.pfName() + ");");
            sl.add("} else {");
            String resolver;
            if (!this.prop().isArray()) {
               resolver = "set" + this.pName() + "((" + this.pType() + ")value);";
               sl.add("  param0 = (param0 == null) ? null : param0.trim();");
               this.addRegisterReferenceCode(sl, "param0", resolver);
            } else {
               resolver = "add" + this.pcName() + "((" + this.pcType() + ")value);";
               sl.add("String[] refs = _getHelper()._splitKeyList(param0);");
               sl.add("List<String> oldRefs = _getHelper()._getKeyList(" + this.pfName() + ");");
               sl.add("for (int i = 0; i < refs.length; i++) {");
               sl.add("  String ref = refs[i];");
               sl.add("  ref = (ref == null) ? null : ref.trim();");
               sl.add("  if (oldRefs.contains(ref)) {");
               sl.add("    oldRefs.remove(ref);");
               sl.add("  } else {");
               this.addRegisterReferenceCode(sl, "ref", resolver);
               sl.add("  }");
               sl.add("}");
               sl.add("for (String ref : oldRefs) {");
               sl.add("  for (" + this.pcType() + " member : " + this.pfName() + ") {");
               sl.add("    if (ref.equals(member.getName())) {");
               sl.add("      try {");
               sl.add("        remove" + this.pcName() + "(member);");
               sl.add("      } catch (RuntimeException e) {");
               sl.add("        throw e;");
               sl.add("      } catch (Exception e) {");
               sl.add("        throw new AssertionError(\"Impossible exception: \" + e);");
               sl.add("      }");
               sl.add("      break;");
               sl.add("    }");
               sl.add("  }");
               sl.add("}");
            }

            sl.add("}");
         } else {
            this.beginImpossibleExceptionWrapper(sl);
            String propType = this.prop().getJClass().getQualifiedName();
            if (propType.equals(Properties.class.getName())) {
               sl.add("set" + this.pName() + "(StringHelper.stringToProperties(param0));");
            } else if (propType.equals(Hashtable.class.getName())) {
               sl.add("set" + this.pName() + "(StringHelper.stringToHashtable(param0));");
            } else if (propType.equals(Map.class.getName())) {
               sl.add("set" + this.pName() + "(StringHelper.stringToMap(param0));");
            } else if (!propType.equals(HashSet.class.getName()) && !propType.equals(Set.class.getName())) {
               if (this.prop().getImplementation() != null && this.prop().getImplementation().getDelegateType() == 1) {
                  sl.add(this.prop().getType() + " encryptedBytes = (param0 == null) ? null : param0.getBytes();");
                  sl.add("set" + this.pName() + "(encryptedBytes);");
               } else {
                  this.prop().error(this.prop().getJClass() + " is not a supported property type");
               }
            } else {
               sl.add("set" + this.pName() + "(StringHelper.stringToHashSet(param0));");
            }

            this.endImpossibleExceptionWrapper(sl);
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.STRING_SETTER, StringSetter.class);
      }
   }

   public static class StringGetter extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public StringGetter(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addComments(List sl) {
         if (this.prop().isExcludedFromSchema()) {
            sl.add("@xsdgen:element.exclude");
         } else {
            sl.add("@xsdgen:element.name " + this.getXMLElementName(this.prop()));
            if (!this.prop().isAnnotationDefined(PropertyAnnotations.REQUIRED)) {
               sl.add("@xsdgen:element.required false");
            }

            if (this.prop().isAnnotationDefined(PropertyAnnotations.NULLABLE)) {
               JAnnotationValue nullableValue = this.prop().getAnnotationValue(PropertyAnnotations.NULLABLE);
               boolean isNonNull = nullableValue != null && "false".equals(nullableValue.asString());
               sl.add("@xsdgen:element.nillable " + !isNonNull);
            } else {
               sl.add("@xsdgen:element.nillable true");
            }
         }

      }

      private String getXMLElementName(PropertyDeclaration pd) {
         PropertyDeclaration parent = pd.getDelegatedProperty();
         if (parent == null) {
            return pd.getXMLElementName();
         } else {
            String parentAnnotation = parent.getAnnotationString(PropertyAnnotations.XML_ELEMENT_NAME);
            return parentAnnotation != null ? parentAnnotation : pd.getXMLElementName();
         }
      }

      protected void addBody(List sl) {
         if (this.prop().isBean()) {
            if (!this.prop().isArray()) {
               sl.add("AbstractDescriptorBean bean = (AbstractDescriptorBean)get" + this.pName() + "();");
               sl.add("return (bean == null) ? null : bean._getKey().toString();");
            } else {
               sl.add("return _getHelper()._serializeKeyList(get" + this.pName() + "());");
            }
         } else if (this.prop().getImplementation() != null && this.prop().getImplementation().getDelegateType() == 1) {
            sl.add(this.prop().getType() + " obj = get" + this.pName() + "();");
            sl.add("return (obj == null) ? null : (new String(obj));");
         } else {
            sl.add("return StringHelper.objectToString(get" + this.pName() + "());");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.STRING_GETTER, StringGetter.class);
      }
   }

   public static class Setter extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Setter(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addComments(List sl) {
         if (this.prop().getGetter().isAnnotationDefined(BeanAnnotations.DYNAMIC) && this.getPropertyMethodDeclaration().isAnnotationDefined(BeanAnnotations.DYNAMIC)) {
            boolean getterDynamic = this.prop().getGetter().isAnnotationTrue(BeanAnnotations.DYNAMIC);
            boolean setterDynamic = this.getPropertyMethodDeclaration().isAnnotationTrue(BeanAnnotations.DYNAMIC);
            if (getterDynamic && !setterDynamic || setterDynamic && !getterDynamic) {
               this.prop().error("@dynamic specified on setter does not match value specified on getter for " + this.prop().getName());
            }
         }

      }

      private void addValidators(List sl, String paramName, BeanElement element, JClass propJClass, String type) {
         String pfx = "LegalChecks.";
         String min;
         if (Context.get().generateLocalValidators()) {
            if (propJClass.isPrimitiveType()) {
               min = element.getAnnotationString(PropertyAnnotations.LEGAL_MIN);
               String max = element.getAnnotationString(PropertyAnnotations.LEGAL_MAX);
               if (min != null && max != null) {
                  sl.add(pfx + "checkInRange(\"" + this.pName() + "\", " + paramName + ", " + min + ", " + max + ");");
               } else if (min != null) {
                  sl.add(pfx + "checkMin(\"" + this.pName() + "\", " + paramName + ", " + min + ");");
               } else if (max != null) {
                  sl.add(pfx + "checkMax(\"" + this.pName() + "\", " + paramName + ", " + max + ");");
               } else {
                  this.addEnumerationValidator(sl, paramName, type, element);
               }
            } else if (propJClass.equals(JClasses.STRING)) {
               JAnnotationValue nullableValue = element.getAnnotationValue(PropertyAnnotations.NULLABLE);
               boolean isNonNull = nullableValue != null && "false".equals(nullableValue.asString());
               JAnnotationValue zeroLengthValue = element.getAnnotationValue(PropertyAnnotations.LEGAL_ZERO_LENGTH);
               boolean allowZeroLength = zeroLengthValue == null || zeroLengthValue.asBoolean();
               this.addEnumerationValidator(sl, paramName, type, element);
               if (!allowZeroLength) {
                  sl.add(pfx + "checkNonEmptyString(\"" + this.pName() + "\", " + paramName + ");");
               }

               if (isNonNull) {
                  sl.add(pfx + "checkNonNull(\"" + this.pName() + "\", " + paramName + ");");
               }
            }
         }

         min = element.getAnnotationString(PropertyAnnotations.VALIDATOR);
         if (min != null) {
            if (min.indexOf(40) == -1) {
               min = min + "(" + paramName + ");";
            } else {
               min = this.replaceExpressions(min, paramName) + ";";
            }

            sl.add(min);
         }

         if (Context.get().generateLocalValidators()) {
         }

      }

      private void addEnumerationValidator(List sl, String paramName, String type, BeanElement prop) {
         String enumeration = prop.getAnnotationString(PropertyAnnotations.ENUMERATION);
         if (enumeration != null) {
            sl.add(type + "[] _set = { " + enumeration + " };");
            sl.add(paramName + " = LegalChecks.checkInEnum(\"" + this.pName() + "\", " + paramName + ", _set);");
         }

      }

      protected void addChildCode(List sl, String valueExp) {
         sl.add("  AbstractDescriptorBean _child = (AbstractDescriptorBean)" + valueExp + ";");
         sl.add("  if (_setParent(_child, this, " + this.prop().getIndex() + ")) {");
         PropertyMethodDeclaration[] props = this.prop().getMethods();
         if (props != null && props.length > 0) {
            for(int i = 0; i < props.length; ++i) {
               if (props[i].isAdder() && !props[i].isSynthetic()) {
                  String[] types = props[i].getParamTypes();

                  assert types.length == 1;

                  this.addValidators(sl, valueExp, props[i], props[i].getPropertyJClass(), types[0]);
               }
            }
         }

         if (!this.prop().isReadOnly()) {
            sl.add("    _getReferenceManager().registerBean(_child, " + this.prop().isReferenceable() + ");");
         }

         sl.add("    _postCreate(_child);");
         sl.add("  }");
      }

      private void addResolvedReferenceCode(List sl) {
         String ref = "param0";
         String indent = "";
         if (this.prop().isArray()) {
            ref = "param0[i]";
            sl.add("for (int i = 0; i < param0.length; i++) {");
            indent = "  ";
         }

         sl.add(indent + "if (" + ref + " != null) {");
         sl.add(indent + "  ResolvedReference _ref =");
         sl.add(indent + "    new ResolvedReference(this, " + this.prop().getIndex() + ", (AbstractDescriptorBean)" + ref + ") {");
         sl.add(indent + "      protected Object getPropertyValue() {");
         sl.add(indent + "        return " + this.prop().getGetter().getName() + "();");
         sl.add(indent + "      }");
         sl.add(indent + "    };");
         sl.add(indent + "  _getReferenceManager().registerResolvedReference(");
         sl.add(indent + "    (AbstractDescriptorBean)" + ref + ", _ref");
         sl.add(indent + "  );");
         sl.add(indent + "}");
         if (this.prop().isArray()) {
            sl.add("}");
         }

      }

      protected void addBody(List sl) {
         if (this.prop().hasTransientOverride() && this.prop().getSetter() != null) {
            JClass[] types = this.prop().getSetter().getExceptionTypes();
            if (types != null) {
               for(int i = 0; i < types.length; ++i) {
                  if ("javax.management.InvalidAttributeValueException".equals(types[i].getQualifiedName())) {
                     sl.add("if (true) throw new javax.management.InvalidAttributeValueException();");
                     break;
                  }
               }
            }
         }

         if (this.prop().isAnnotationDefined(PropertyAnnotations.UN_SET_VALUE)) {
            String unSetValue = this.prop().getAnnotationString(PropertyAnnotations.UN_SET_VALUE);
            if (this.pJClass().isPrimitiveType()) {
               sl.add("if (param0 == " + unSetValue + ") {");
            } else if (unSetValue == null) {
               sl.add("if (param0 == null) {");
            } else {
               sl.add("if (" + unSetValue + ".equals(param0)) {");
            }

            sl.add("  _unSet(" + this.prop().getIndex() + ");");
            sl.add("  return;");
            sl.add("}");
         }

         if (this.prop().isChild() && !this.prop().hasAddressableComponents() && !this.prop().isReadOnly()) {
            sl.add("if (param0 != null && get" + this.pName() + "() != null && param0 != get" + this.pName() + "()) {");
            sl.add("  throw new BeanAlreadyExistsException(");
            sl.add("    get" + this.pName() + "() + \" has already been created\");");
            sl.add("}");
         }

         if (this.pJClass().equals(JClasses.STRING)) {
            if (!this.prop().isAnnotationTrue(PropertyAnnotations.PRESERVE_WHITE_SPACE)) {
               sl.add("param0 = (param0 == null) ? null : param0.trim();");
            }
         } else if (this.pJClass().equals(JClasses.STRING_ARRAY)) {
            sl.add("param0 = (param0 == null) ? new String[0] : param0;");
            if (!this.prop().isAnnotationTrue(PropertyAnnotations.PRESERVE_WHITE_SPACE)) {
               sl.add("param0 = _getHelper()._trimElements(param0);");
            } else {
               sl.add("_getHelper()._ensureNonNullElements(param0);");
            }
         } else if (this.prop().isBean() && this.prop().isArray()) {
            sl.add("param0 = (param0 == null) ? new " + this.pcImpl() + "[0] : param0;");
         }

         BeanClass bc = this.getPropertyMethodDeclaration().getBean();
         if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
            String componentType = this.prop().getComponentType();
            sl.add("if (_isTransient() && _isSynthetic() && _getDelegateBean() != null) {");
            if (MethodImplementations.isBeanArrayDelegate(this.prop())) {
               sl.add("  final ArrayUtils.CollectAllDiffHandler<" + componentType + "> handler = new ArrayUtils.CollectAllDiffHandler<>();");
               sl.add("  ArrayUtils.computeDiff(" + this.pfName() + ", param0, handler, new Comparator<" + componentType + ">() {");
               sl.add("      public int compare(" + componentType + " o1, " + componentType + " o2) {");
               sl.add("        return StringUtils.compare(o1.getName(), o2.getName());");
               sl.add("      }");
               sl.add("    });");
               sl.add("  for (" + componentType + " bean : handler.getAll()) {");
               sl.add("    " + this.pcImpl() + " beanImpl = (" + this.pcImpl() + ") bean;");
               sl.add("    if ((!beanImpl._isTransient()) && beanImpl._isSynthetic()) {");
               sl.add("      _untransient();");
               sl.add("      break;");
               sl.add("    }");
               sl.add("  }");
            } else if (!"Name".equals(this.pName()) && !"Parent".equals(this.pName())) {
               sl.add("  _untransient();");
            }

            sl.add("}");
         }

         String[] preprocessors = this.prop().getAllAnnotationInstancesAsStrings(PropertyAnnotations.PREPROCESSOR);
         if (preprocessors != null && preprocessors.length > 0) {
            int count;
            if (!this.pJClass().isArrayType()) {
               sl.add(this.pType() + " _copy = param0;");

               for(count = 0; count < preprocessors.length; ++count) {
                  sl.add("_copy = " + this.replaceExpressions(preprocessors[count], "_copy") + ";");
               }

               sl.add("param0 = _copy;");
            } else {
               sl.add(this.pcType() + "[] _copy = new " + this.pcType() + "[param0.length];");
               sl.add("for (int i = 0; i < param0.length; i++) {");
               sl.add("  _copy[i] = param0[i];");

               for(count = 0; count < preprocessors.length; ++count) {
                  sl.add("  _copy[i] = " + this.replaceExpressions(preprocessors[count], "_copy[i]") + ";");
               }

               sl.add("}");
               sl.add("param0 = _copy;");
            }
         }

         if (this.prop().isChild()) {
            this.addValidators(sl, "param0", this.prop(), this.pJClass(), this.prop().getType());
            if (!this.prop().hasAddressableComponents()) {
               if (!this.prop().isReadOnly()) {
                  sl.add("if (param0 != null) {");
               }

               this.addChildCode(sl, "param0");
               if (!this.prop().isReadOnly()) {
                  sl.add("}");
               }
            } else {
               sl.add("for (int i = 0; i < param0.length; i++) {");
               this.addChildCode(sl, "param0[i]");
               sl.add("}");
            }
         } else if (this.prop().isReference() && !this.prop().isExcludedFromSchema()) {
            if (this.prop().isArray()) {
               sl.add("param0 = (" + this.pType() + ") _getHelper()._cleanAndValidateArray(param0, " + this.pcType() + ".class);");
            }

            this.addValidators(sl, "param0", this.prop(), this.pJClass(), this.prop().getType());
            this.addResolvedReferenceCode(sl);
         } else {
            this.addValidators(sl, "param0", this.prop(), this.pJClass(), this.prop().getType());
         }

         if (this.isImplementedByCustomizer()) {
            if (!this.prop().isTransient()) {
               if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
                  sl.add("boolean wasSet = _isSet(" + this.prop().getIndex() + ");");
               }

               sl.add(this.pType() + " _oldVal = " + this.prop().getGetter().getName() + "();");
            }

            this.addDelegateToCustomizer(sl, "");
         } else if (this.prop().isDeclaredEncrypted() && this.prop().getImplementation().getDelegate() != null) {
            PropertyDeclaration delegateDeclaration = this.prop().getImplementation().getDelegate().getDeclaration();
            boolean catchException = false;
            if ((delegateDeclaration.getSetter() == null || delegateDeclaration.getSetter().getExceptionList().indexOf(JClasses.INVALID_ATTRIBUTE_VALUE_EXCEPTION.getSimpleName()) >= 0) && this.prop().getSetter() != null && this.prop().getSetter().getExceptionList().indexOf(JClasses.INVALID_ATTRIBUTE_VALUE_EXCEPTION.getSimpleName()) < 0) {
               catchException = true;
            }

            if (catchException) {
               sl.add("try {");
            }

            sl.add("set" + delegateDeclaration.getName() + "(param0 == null ? null : _encrypt(\"" + this.prop().getName() + "\", param0));");
            if (catchException) {
               sl.add("}");
               sl.add("catch(javax.management.InvalidAttributeValueException iave) {");
               sl.add("  // this is an impossible case");
               sl.add("}");
            }
         } else {
            boolean isEncryptionDelegate = this.prop().getImplementation() != null && this.prop().getImplementation().getDelegateType() == 1;
            if (!this.prop().isTransient()) {
               if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
                  sl.add("boolean wasSet = _isSet(" + this.prop().getIndex() + ");");
               }

               sl.add(this.pType() + " _oldVal = " + this.pfName() + ";");
            }

            if (isEncryptionDelegate) {
               sl.add("if(_isProductionModeEnabled()) {");
               sl.add("  if(param0 != null && !_isEncrypted(param0)) {");
               sl.add("    throw new IllegalArgumentException(\"In production mode, it's not allowed to set a clear text value to the property: " + this.pName() + " of " + this.prop().getBean().getInterfaceName() + "\");");
               sl.add("  }");
               sl.add("}");
            }

            if (isEncryptionDelegate) {
               sl.add("_getHelper()._clearArray(" + this.pfName() + ");");
               sl.add(this.pfName() + " = _getHelper()._cloneArray(param0);");
            } else {
               sl.add(this.pfName() + " = param0;");
            }
         }

         if (this.pName().equals(bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN))) {
            sl.add("if (param0 != _oldVal) {");
            PropertyDeclaration[] var16 = this.prop().getBean().getProperties();
            int var17 = var16.length;

            for(int var6 = 0; var6 < var17; ++var6) {
               PropertyDeclaration propertyDeclaration = var16[var6];
               PropertyMethodDeclaration destroyMethod = propertyDeclaration.findMethod(PropertyMethodType.DESTROYER);
               if (destroyMethod != null && (propertyDeclaration.getSetter() != null || propertyDeclaration.isArray())) {
                  sl.add("  if (_isSet(" + propertyDeclaration.getIndex() + ")) {");
                  String implName;
                  if (propertyDeclaration.getJClass().isArrayType()) {
                     sl.add("    for (" + propertyDeclaration.getJClass().getArrayComponentType().getSimpleName() + " p : " + propertyDeclaration.findMethod(PropertyMethodType.GETTER).getName() + "()) {");
                     implName = Context.get().interfaceToClassName(propertyDeclaration.getJClass().getArrayComponentType(), false);
                     sl.add("      if (((" + implName + ")p)._getDelegateBean() != null) {");
                     sl.add("        " + destroyMethod.getName() + "(p);");
                     sl.add("      }");
                     sl.add("    }");
                  } else {
                     sl.add("    " + propertyDeclaration.getJClass().getSimpleName() + " p = " + propertyDeclaration.findMethod(PropertyMethodType.GETTER).getName() + "();");
                     implName = Context.get().interfaceToClassName(propertyDeclaration.getJClass(), false);
                     sl.add("    if (((" + implName + ")p)._getDelegateBean() != null) {");
                     sl.add("      " + destroyMethod.getName() + "();");
                     sl.add("    }");
                  }

                  sl.add("  }");
               }
            }

            sl.add("}");
            sl.add("// set template delegate: " + this.prop().getBean().getClassName());
            sl.add("_setDelegateBean((" + this.pImpl() + ") param0);");
         }

         if (!this.prop().isTransient()) {
            sl.add("_postSet(" + this.prop().getIndex() + ", _oldVal, param0);");
            if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null) {
               sl.add("for (" + this.prop().getBean().getClassName() + " source : _DelegateSources) {");
               sl.add("  if (source != null && !source._isSet(" + this.prop().getIndex() + ")) {");
               sl.add("    source._postSetFirePropertyChange(" + this.prop().getIndex() + ", wasSet, _oldVal, param0);");
               sl.add("  }");
               sl.add("}");
            }
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.SETTER, Setter.class);
      }
   }

   public static class Getter extends PropertyMethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Getter(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addComments(List sl) {
         if (this.prop() == null) {
            throw new AssertionError(this.getDeclaration() + "'s prop is null");
         } else {
            if (!this.prop().getImplementation().isMarshalledAsString() && !this.prop().isExcludedFromSchema()) {
               sl.add("@xsdgen:element.name " + this.prop().getXMLElementName());
               String val;
               if (this.prop().isBean()) {
                  val = Context.get().interfaceToClassName(this.prop().getComponentJClass(), false);
                  sl.add("@xsdgen:element.astype " + val);
               }

               if (!this.prop().isAnnotationDefined(PropertyAnnotations.REQUIRED)) {
                  sl.add("@xsdgen:element.required false");
               }

               boolean abortEnumerations;
               JAnnotationValue zeroLengthValue;
               if (this.prop().isAnnotationDefined(PropertyAnnotations.NULLABLE)) {
                  if (this.pJClass().equals(JClasses.STRING)) {
                     zeroLengthValue = this.prop().getAnnotationValue(PropertyAnnotations.NULLABLE);
                     abortEnumerations = zeroLengthValue != null && "false".equals(zeroLengthValue.asString());
                     sl.add("@xsdgen:element.nillable " + !abortEnumerations);
                  }
               } else {
                  sl.add("@xsdgen:element.nillable true");
               }

               Object value;
               if (this.pJClass().isPrimitiveType()) {
                  if (this.prop().isAnnotationDefined(PropertyAnnotations.LEGAL_MIN)) {
                     val = this.prop().getAnnotationString(PropertyAnnotations.LEGAL_MIN);
                     sl.add("@xsdgen:element.minInclusive " + JamHelper.findNumber(val, this.prop().getBean().getJClass(), this.prop().getGetter().getReturnJClass()));
                     if (this.prop().isAnnotationDefined(PropertyAnnotations.LEGAL_MAX)) {
                        String legalMax = this.prop().getAnnotationString(PropertyAnnotations.LEGAL_MAX);
                        sl.add("@xsdgen:element.maxInclusive " + JamHelper.findNumber(legalMax, this.prop().getBean().getJClass(), this.prop().getGetter().getReturnJClass()));
                     }
                  }

                  if (this.prop().isAnnotationDefined(PropertyAnnotations.DEFAULT) && !this.prop().isAnnotationDefined(PropertyAnnotations.ENUMERATION)) {
                     val = this.prop().getAnnotationString(PropertyAnnotations.DEFAULT);
                     value = JamHelper.findDefaultValue(val, this.prop().getBean().getJClass(), this.prop().getGetter().getReturnJClass());
                     if (value != null) {
                        sl.add("@xsdgen:element.default " + value);
                     }
                  }
               }

               if (this.pJClass().equals(JClasses.STRING)) {
                  if (this.prop().isAnnotationDefined(PropertyAnnotations.LEGAL_ZERO_LENGTH)) {
                     zeroLengthValue = this.prop().getAnnotationValue(PropertyAnnotations.LEGAL_ZERO_LENGTH);
                     abortEnumerations = zeroLengthValue == null || zeroLengthValue.asBoolean();
                     if (!abortEnumerations) {
                        sl.add("@xsdgen:element.minLength 1");
                     }
                  }

                  if (this.prop().isAnnotationDefined(PropertyAnnotations.DEFAULT) && !this.prop().isAnnotationDefined(PropertyAnnotations.ENUMERATION)) {
                     val = this.prop().getAnnotationString(PropertyAnnotations.DEFAULT);
                     if (val != null) {
                        value = JamHelper.findDefaultValue(val, this.prop().getBean().getJClass(), this.prop().getGetter().getReturnJClass());
                        if (value != null) {
                           sl.add("@xsdgen:element.default " + value);
                        }
                     }
                  }
               }

               if (this.prop().isAnnotationDefined(PropertyAnnotations.ENUMERATION)) {
                  val = this.prop().getAnnotationString(PropertyAnnotations.ENUMERATION);
                  abortEnumerations = false;
                  String[] values = val.split(",");
                  List enumerationValues = new ArrayList(values.length);

                  for(int i = 0; i < values.length; ++i) {
                     Object value = JamHelper.findValue(values[i], this.prop().getBean().getJClass());
                     if (value == null) {
                        abortEnumerations = true;
                        break;
                     }

                     enumerationValues.add(value);
                  }

                  if (!abortEnumerations) {
                     Iterator i = enumerationValues.iterator();

                     while(i.hasNext()) {
                        sl.add("@xsdgen:element.enumeration " + i.next());
                     }
                  }
               }

               boolean dynamic = this.prop().isAnnotationTrue(BeanAnnotations.DYNAMIC);
               sl.add("@" + BeanAnnotations.DYNAMIC + " " + dynamic);
            } else {
               sl.add("@xsdgen:element.exclude");
               if (this.prop().isTransient()) {
                  sl.add("@" + PropertyAnnotations.TRANSIENT);
               }
            }

            this.checkAndAddRestartsAnnotation(sl);
         }
      }

      private void checkAndAddRestartsAnnotation(List sl) {
         String restartMethod = this.prop().getAnnotationString(BeanAnnotations.RESTARTS);
         if (restartMethod != null && restartMethod.trim().length() > 0) {
            sl.add("@" + BeanAnnotations.RESTARTS + " " + restartMethod);
         }

      }

      protected void addBody(List sl) {
         int index = this.prop().getIndex();
         BeanClass bc = this.getPropertyMethodDeclaration().getBean();
         String derivedDefaultExpression;
         if (bc.isAnnotationDefined(BeanAnnotations.DELEGATE_BEAN) && bc.getAnnotationString(BeanAnnotations.DELEGATE_BEAN) == null && (this.prop().getSetter() != null || this.prop().isArray())) {
            sl.add("// delegate to template mbean");
            String ifSetStmt = "if (!_isSet(" + index + ") && _getDelegateBean() != null && _getDelegateBean()._isSet(" + index + ")) {";
            derivedDefaultExpression = "if (_getDelegateBean() != null && _getDelegateBean()._isSet(" + index + ")) {";
            if (this.pJClass().equals(JClasses.STRING)) {
               sl.add(ifSetStmt);
               sl.add("  return _performMacroSubstitution(_getDelegateBean()." + this.mName() + "(), this);");
               sl.add("}");
            } else if (MethodImplementations.isBeanArrayDelegate(this.prop())) {
               JClass pjComponentClass = this.pJClass().getArrayComponentType();
               String implClass = Context.get().interfaceToClassName(pjComponentClass);
               sl.add(this.pJClass() + " delegateArray;");
               sl.add(derivedDefaultExpression);
               boolean hasTemplate = this.getDeclaration().getBean().getClassName().equals("ResourceGroupMBeanImpl") || this.getDeclaration().getBean().getClassName().equals("ServerMBeanImpl");
               if (hasTemplate) {
                  sl.add("  if(!_isSynthetic()) {");
                  sl.add("  delegateArray = _getDelegateBean()." + this.mName() + "();");
                  sl.add("  " + implClass + "[] ret = new " + implClass + "[delegateArray.length + " + this.pfName() + ".length];");
                  sl.add("  for (int i = 0; i < delegateArray.length; i++) {");
                  sl.add("    try {");
                  sl.add("      ret[i] = new " + implClass + "(this, -1, true);");
                  sl.add("      _setParent(ret[i], this, " + this.prop().getIndex() + ");");
                  sl.add("      ret[i].setName(delegateArray[i].getName());");
                  sl.add("      ret[i]._setDelegateBean((" + implClass + ") delegateArray[i]);");
                  sl.add("    } catch (Exception e) { throw new java.lang.reflect.UndeclaredThrowableException(e); }");
                  sl.add("  }");
                  sl.add("  if (!_isSet(" + index + "))");
                  sl.add("    return ret;");
                  sl.add("  int numElements = delegateArray.length;");
                  sl.add("  for (int i = 0; i < " + this.pfName() + ".length; i++) {");
                  sl.add("    boolean found = false;");
                  sl.add("    for (int j = 0; j < delegateArray.length && !found; j++) {");
                  sl.add("      if (delegateArray[j].getName().equals(" + this.pfName() + "[i].getName())) {");
                  sl.add("        ret[j] = (" + implClass + ")" + this.pfName() + "[i];");
                  sl.add("        ((" + implClass + ") " + this.pfName() + "[i])._setDelegateBean((" + implClass + ") delegateArray[j]);");
                  sl.add("        found = true;");
                  sl.add("      }");
                  sl.add("    }");
                  sl.add("    if (!found) {");
                  sl.add("      ret[numElements++] = (" + implClass + ")" + this.pfName() + "[i];");
                  sl.add("    }");
                  sl.add("  }");
                  sl.add("  return (" + implClass + "[]) Arrays.copyOf(ret, numElements);");
                  sl.add("  } else {");
               }

               sl.add("  delegateArray = _getDelegateBean()." + this.mName() + "();");
               sl.add("  for (int i = 0; i < delegateArray.length; i++) {");
               sl.add("    boolean found = false;");
               sl.add("    for (int j = 0; j < " + this.pfName() + ".length; j++) {");
               sl.add("      if (delegateArray[i].getName().equals(" + this.pfName() + "[j].getName())) {");
               sl.add("        ((" + implClass + ") " + this.pfName() + "[j])._setDelegateBean((" + implClass + ") delegateArray[i]);");
               sl.add("        found = true;");
               sl.add("      }");
               sl.add("    }");
               sl.add("    if (!found) {");
               sl.add("      try {");
               sl.add("        final " + implClass + " mbean = new " + implClass + "(this, -1, true);");
               sl.add("        _setParent(mbean, this, " + this.prop().getIndex() + ");");
               sl.add("        mbean.setName(delegateArray[i].getName());");
               sl.add("        mbean._setDelegateBean((" + implClass + ") delegateArray[i]);");
               sl.add("        mbean._setTransient(true);");
               String arrayExtensionCode = "set" + this.pName() + "((" + this.pType() + ") _getHelper()._extendArray(" + this.pfName() + ", " + this.pcType() + ".class, mbean));";
               if (!this.prop().getGetter().getImplementation().isImplementedByCustomizer() && !this.prop().isTransient()) {
                  sl.add("        if (_isSet(" + this.prop().getIndex() + ")) {");
                  sl.add("          " + arrayExtensionCode);
                  sl.add("        } else {");
                  sl.add("          set" + this.pName() + "( new " + this.pType() + " { mbean });");
                  sl.add("        }");
               } else {
                  sl.add(arrayExtensionCode);
               }

               sl.add("        mbean._setSynthetic(true);");
               sl.add("      } catch (Exception e) { throw new java.lang.reflect.UndeclaredThrowableException(e); }");
               sl.add("    }");
               sl.add("  }");
               if (hasTemplate) {
                  sl.add("  }");
               }

               sl.add("} else {");
               String zeroArray = this.pJClass().getQualifiedName();
               zeroArray = zeroArray.substring(0, zeroArray.length() - 1) + "0]";
               sl.add("  delegateArray = new " + zeroArray + ";");
               sl.add("}");
               sl.add("//Check if changes in delegation removes some transient beans");
               sl.add("if (" + this.pfName() + " != null) {");
               sl.add("  final List<" + pjComponentClass + "> removeList = new ArrayList<>();");
               sl.add("  for (" + pjComponentClass + " bn : " + this.pfName() + ") {");
               sl.add("    final " + implClass + " bni = (" + implClass + ") bn;");
               sl.add("    if (bni._isTransient() && bni._isSynthetic()) {");
               sl.add("      final String nameToSearch = bni._getDelegateBean().getName();");
               sl.add("      boolean found = false;");
               sl.add("      for (" + pjComponentClass + " delegateTo : delegateArray) {");
               sl.add("        if (nameToSearch.equals(delegateTo.getName())) {");
               sl.add("          found = true;");
               sl.add("          break;");
               sl.add("        }");
               sl.add("      }");
               sl.add("      if (!found) {");
               sl.add("        removeList.add(bn);");
               sl.add("      }");
               sl.add("    }");
               sl.add("  }");
               sl.add("  //Do hard removes (one by one)");
               sl.add("  for (" + pjComponentClass + " removeIt : removeList) {");
               sl.add("    final " + implClass + " removeItImpl = (" + implClass + ") removeIt;");
               sl.add("    final " + this.pJClass() + " _new = (" + this.pJClass() + ")_getHelper()._removeElement(" + this.pfName() + ", " + this.pcType() + ".class, removeIt);");
               sl.add("    try {");
               sl.add("      _preDestroy(removeItImpl);");
               sl.add("      _getReferenceManager().unregisterBean(removeItImpl, false);");
               sl.add("      _markDestroyed(removeItImpl);");
               sl.add("    } catch (Exception any) {} // still remove from the array");
               sl.add("    try {");
               sl.add("      set" + this.pName() + "(_new);");
               sl.add("    } catch (Exception e) { throw new java.lang.reflect.UndeclaredThrowableException(e); }");
               sl.add("  }");
               sl.add("}");
            } else {
               sl.add(ifSetStmt);
               sl.add("  return _getDelegateBean()." + this.mName() + "();");
               sl.add("}");
            }
         }

         if (this.prop().isAnnotationDefined(PropertyAnnotations.UN_SET_VALUE)) {
            sl.add("if (!_isSet(" + index + ")) {");
            sl.add("  return " + this.prop().getAnnotationString(PropertyAnnotations.UN_SET_VALUE) + ";");
            sl.add("}");
         }

         PropertyMethodDeclaration decl = this.getPropertyMethodDeclaration();
         derivedDefaultExpression = this.prop().getAnnotationString(PropertyAnnotations.DERIVED_DEFAULT);
         if (derivedDefaultExpression != null) {
            sl.add("if (!_isSet(" + index + ")) {");
            sl.add(" try {");
            sl.add("   return " + derivedDefaultExpression + ";");
            sl.add(" } catch (NullPointerException e) {");
            sl.add("   // indicates that property in derived chain is not set");
            sl.add("   // fall through");
            sl.add(" }");
            sl.add("}");
         }

         if (this.prop().getImplementation() != null) {
            String deferredInitializerExpression = this.prop().getImplementation().getDeferredInitializer(this.prop());
            if (deferredInitializerExpression != null) {
               sl.add("if (!_isSet(" + index + ")) {");
               sl.add(" return " + deferredInitializerExpression);
               sl.add("}");
            }
         }

         if (this.isImplementedByCustomizer()) {
            this.addDelegateToCustomizer(sl, "return ");
         } else if (!decl.getPropertyName().equals(this.prop().getName())) {
            sl.add("return get" + this.pName() + "();");
            sl.add("return o;");
         } else if (this.prop().isDeclaredEncrypted() && this.prop().getImplementation().getDelegate() != null) {
            sl.add("byte[] bEncrypted = get" + this.prop().getImplementation().getDelegate().getDeclaration().getName() + "();");
            sl.add("return (bEncrypted == null) ? null : _decrypt(\"" + this.prop().getName() + "\", bEncrypted);");
         } else if (this.prop().getImplementation() != null && this.prop().getImplementation().getDelegateType() == 1) {
            sl.add("return _getHelper()._cloneArray(" + this.pfName() + ");");
         } else {
            sl.add("return " + this.pfName() + ";");
         }

      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(PropertyMethodType.GETTER, Getter.class);
      }
   }

   public static class Constructor extends MethodImplementation {
      public static MethodFactory.ImplementationFactory FACTORY;

      public Constructor(MethodDeclaration decl, BeanCustomizer customizer) {
         super(decl, customizer);
      }

      protected void addBody(List sl) {
         String baseName = Context.get().getBaseClassName();
         if (!baseName.equals(this.mName())) {
            if (this.getDeclaration().getParamTypes().length < 3) {
               sl.add("super(" + this.params() + ");");
            } else {
               sl.add("super(param0, param1);");
               sl.add("_setTransient(param2);");
            }
         }

         BeanCustomizer customizer = this.getCustomizer();
         BeanClass bean = this.getDeclaration().getBean();
         if (bean.isAnnotationDefined(BeanAnnotations.ROOT)) {
            sl.add("_initializeRootBean(getDescriptor());");
         }

         if (customizer != null) {
            this.beginImpossibleExceptionWrapper(sl);
            if (customizer.isUseFactory()) {
               sl.add("  " + customizer.getFactoryInit() + ";");
            }

            sl.add("  " + customizer.getField().getName() + " = " + customizer.getInitializer() + ";");
            this.endImpossibleExceptionWrapper(sl);
         }

         sl.add("_initializeProperty(-1);");
      }

      static {
         FACTORY = new MethodFactory.ImplementationFactory(ConstructorType.CONSTRUCTOR, Constructor.class);
      }
   }
}
