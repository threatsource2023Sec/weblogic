package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingProperty;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.xml.XmlException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;

abstract class AttributeRuntimeBindingType extends RuntimeBindingType {
   private final AttributeQNameProperty[] attributeProperties;
   private final boolean hasDefaultAttributes;
   private Constructor ctor;
   private AttributeQNameProperty[] qnameAttributes;

   AttributeRuntimeBindingType(BindingType btype) throws XmlException {
      super(btype);
      Class java_type = this.getJavaType();
      if (!java_type.isPrimitive() && !java_type.isArray()) {
         int att_prop_cnt = 0;
         boolean has_attribute_defaults = false;
         Collection type_props = this.getQNameProperties();
         Iterator itr = type_props.iterator();

         while(itr.hasNext()) {
            QNameProperty p = (QNameProperty)itr.next();
            if (p.isAttribute()) {
               ++att_prop_cnt;
               if (p.getDefault() != null) {
                  has_attribute_defaults = true;
               }
            }
         }

         this.attributeProperties = new AttributeQNameProperty[att_prop_cnt];
         this.hasDefaultAttributes = has_attribute_defaults;
      } else {
         String msg = "invalid ByNameBean java type: " + java_type + " found in " + btype;
         throw new XmlException(msg);
      }
   }

   public abstract boolean needsHolder();

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      int att_idx = 0;
      int elem_idx = 0;
      Collection properties = this.getQNameProperties();
      Iterator itr = properties.iterator();

      while(itr.hasNext()) {
         QNameProperty bprop = (QNameProperty)itr.next();
         if (bprop.isAttribute()) {
            AttributeQNameProperty aprop = new AttributeQNameProperty(this.getJavaType(), bprop, this, typeTable, loader);
            this.initAttributeProperty(aprop, att_idx++);
         } else {
            this.initElementProperty(bprop, elem_idx++, typeTable, loader);
         }
      }

      this.ctor = this.findCtor();
      this.qnameAttributes = this.findQNameProps();
   }

   private AttributeQNameProperty[] findQNameProps() {
      ArrayList qname_props = null;
      int i = 0;

      for(int alen = this.attributeProperties.length; i < alen; ++i) {
         AttributeQNameProperty prop = this.attributeProperties[i];
         if (isQNameProp(prop)) {
            if (qname_props == null) {
               qname_props = new ArrayList();
            }

            qname_props.add(prop);
         }
      }

      AttributeQNameProperty[] qname_atts;
      if (qname_props != null) {
         qname_atts = new AttributeQNameProperty[qname_props.size()];
         qname_props.toArray(qname_atts);
      } else {
         qname_atts = new AttributeQNameProperty[0];
      }

      return qname_atts;
   }

   protected static boolean isQNameProp(RuntimeBindingProperty prop) {
      TypeUnmarshaller um = prop.getRuntimeBindingType().getUnmarshaller();
      return um instanceof QNameTypeConverter;
   }

   protected final Object getFinalObjectFromIntermediary(Object inter, UnmarshalResult context) throws XmlException {
      if (this.needsHolder()) {
         ObjectIntermediary rh = (ObjectIntermediary)inter;
         return rh.getFinalValue();
      } else {
         return inter;
      }
   }

   protected abstract void initElementProperty(QNameProperty var1, int var2, RuntimeBindingTypeTable var3, BindingLoader var4) throws XmlException;

   private void initAttributeProperty(AttributeQNameProperty prop, int att_idx) {
      this.attributeProperties[att_idx] = prop;
   }

   protected abstract Collection getQNameProperties();

   final RuntimeBindingProperty getAttributeProperty(int index) {
      return this.attributeProperties[index];
   }

   final RuntimeBindingProperty getMatchingAttributeProperty(String uri, String localname, UnmarshalResult context) {
      Map nsmapping = context.getNamespaceMapping();
      if (nsmapping == null) {
         nsmapping = Collections.EMPTY_MAP;
      }

      Map namespaceMap = new HashMap(nsmapping);
      String defaultNamespace = context.getNamespaceURI();
      namespaceMap.put(defaultNamespace, "");
      int i = 0;

      for(int len = this.attributeProperties.length; i < len; ++i) {
         QNameRuntimeProperty prop = this.attributeProperties[i];
         if (doesPropMatch(uri, localname, prop, namespaceMap)) {
            if (this.hasDefaultAttributes && prop.typedDefaultValue != null) {
               context.attributePresent(i);
            }

            return prop;
         }
      }

      return null;
   }

   private static boolean doesPropMatch(String uri, String localname, QNameRuntimeProperty prop, Map namespaceMapping) {
      assert localname != null;

      return UnmarshalResult.doesElementMatch(prop.getQName(), localname, uri, namespaceMapping);
   }

   final boolean hasCtor() {
      return this.ctor != null;
   }

   abstract int getElementPropertyCount();

   final int getAttributePropertyCount() {
      return this.attributeProperties.length;
   }

   final void fillDefaultAttributes(Object inter, UnmarshalResult context) throws XmlException {
      if (this.hasDefaultAttributes) {
         int aidx = 0;

         for(int alen = this.attributeProperties.length; aidx < alen; ++aidx) {
            QNameRuntimeProperty p = this.attributeProperties[aidx];
            if (p.typedDefaultValue != null && !context.isAttributePresent(aidx)) {
               p.fillDefaultValue(inter);
            }
         }

      }
   }

   protected abstract boolean hasMulti();

   final int getCtorParamCount() {
      return this.ctor.getParameterTypes().length;
   }

   private Constructor findCtor() throws XmlException {
      ArrayList props = this.getAllCtorProperties();
      if (props.isEmpty()) {
         return null;
      } else {
         Collections.sort(props, AttributeRuntimeBindingType.CtorPropComparator.INSTANCE);
         int len = props.size();
         Class[] ctor_params = new Class[len];

         for(int i = 0; i < len; ++i) {
            RuntimeBindingProperty p = (RuntimeBindingProperty)props.get(i);
            if (i != p.getCtorArgIndex()) {
               throw new XmlException("invalid constructor index values for type: " + this);
            }

            ctor_params[i] = p.getJavaType();
         }

         Class javaType = this.getJavaType();

         try {
            return javaType.getConstructor(ctor_params);
         } catch (NoSuchMethodException var6) {
            throw new XmlException("unable to find matching constructor for type " + this + " with parameters: " + Arrays.asList(ctor_params), var6);
         }
      }
   }

   protected abstract ArrayList getAllCtorProperties();

   public final Constructor getConstructor() {
      return this.ctor;
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      int i = 0;

      for(int alen = this.qnameAttributes.length; i < alen; ++i) {
         AttributeQNameProperty property = this.qnameAttributes[i];

         assert property != null;

         assert property.getRuntimeBindingType().getUnmarshaller() instanceof QNameTypeConverter;

         QName qn = (QName)property.getValue(obj);
         if (qn != null && "".equals(qn.getNamespaceURI())) {
            return false;
         }
      }

      return true;
   }

   static final class CtorPropComparator implements Comparator {
      static final CtorPropComparator INSTANCE = new CtorPropComparator();

      private CtorPropComparator() {
      }

      public int compare(Object o, Object o1) {
         RuntimeBindingProperty p1 = (RuntimeBindingProperty)o;
         RuntimeBindingProperty p2 = (RuntimeBindingProperty)o1;
         int i1 = p1.getCtorArgIndex();
         int i2 = p2.getCtorArgIndex();
         return i1 < i2 ? -1 : (i1 == i2 ? 0 : 1);
      }
   }

   protected abstract static class QNameRuntimeProperty extends RuntimeBindingType.BeanRuntimeProperty {
      protected final QNameProperty bindingProperty;
      protected final AttributeRuntimeBindingType containingType;
      protected final String lexicalDefaultValue;
      protected final Object typedDefaultValue;

      QNameRuntimeProperty(Class bean_class, QNameProperty prop, AttributeRuntimeBindingType containing_type, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         super(bean_class, prop, containing_type, typeTable, loader);
         if (prop.getQName() == null) {
            String msg = "property " + prop + " of " + bean_class + " has no qname";
            throw new IllegalArgumentException(msg);
         } else {
            this.bindingProperty = prop;
            this.containingType = containing_type;
            this.lexicalDefaultValue = this.bindingProperty.getDefault();
            if (this.lexicalDefaultValue != null) {
               this.typedDefaultValue = RuntimeBindingType.extractDefaultObject(this.lexicalDefaultValue, this.getRuntimeBindingType().getBindingType(), typeTable, loader);
            } else {
               this.typedDefaultValue = null;
            }

         }
      }

      protected final BindingProperty getBindingProperty() {
         return this.bindingProperty;
      }

      final RuntimeBindingType getContainingType() {
         return this.containingType;
      }

      final QName getName() {
         return this.bindingProperty.getQName();
      }

      final void fillDefaultValue(Object inter) throws XmlException {
         assert this.typedDefaultValue != null;

         this.fill(inter, this.typedDefaultValue);
      }

      final void fillCollection(Object inter, Object prop_obj) throws XmlException {
         assert this.isMultiple();

         this.setValue(inter, prop_obj);
      }

      final boolean isMultiple() {
         return this.bindingProperty.isMultiple();
      }

      final boolean isNillable() {
         return this.bindingProperty.isNillable();
      }

      final boolean isOptional() {
         return this.bindingProperty.isOptional();
      }

      final String getLexicalDefault() {
         return this.lexicalDefaultValue;
      }

      final QName getQName() {
         return this.bindingProperty.getQName();
      }
   }

   protected static final class AttributeQNameProperty extends QNameRuntimeProperty {
      AttributeQNameProperty(Class bean_class, QNameProperty prop, AttributeRuntimeBindingType containing_type, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         super(bean_class, prop, containing_type, typeTable, loader);

         assert prop.isAttribute();

      }

      public void fill(Object inter, Object prop_obj) throws XmlException {
         if (this.containingType.needsHolder()) {
            ObjectIntermediary rh = (ObjectIntermediary)inter;
            rh.setValue(this, prop_obj);
         } else {
            this.setValue(inter, prop_obj);
         }

      }
   }
}
