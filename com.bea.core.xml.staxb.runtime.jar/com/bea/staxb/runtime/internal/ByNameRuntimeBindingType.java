package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.GenericXmlProperty;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

final class ByNameRuntimeBindingType extends AttributeRuntimeBindingType {
   private final ElementQNameProperty[] elementProperties;
   private Collection qNameProperties = null;
   private boolean hasMulti;
   private final ElementQNameProperty[] multiNamespacedProps;
   private static final ElementQNameProperty[] EMPTY_PROPS = new ElementQNameProperty[0];

   ByNameRuntimeBindingType(ByNameBean btype) throws XmlException {
      super(btype);
      Class java_type = this.getJavaType();
      if (!java_type.isPrimitive() && !java_type.isArray()) {
         int elem_prop_cnt = 0;
         int multi_with_uri_cnt = 0;
         boolean has_multi = false;
         Collection type_props = this.getQNameProperties();
         Iterator itr = type_props.iterator();

         while(itr.hasNext()) {
            QNameProperty p = (QNameProperty)itr.next();
            if (!p.isAttribute()) {
               if (p.isMultiple()) {
                  has_multi = true;
                  String uri = p.getQName().getNamespaceURI();

                  assert uri != null;

                  if (!"".equals(uri)) {
                     ++multi_with_uri_cnt;
                  }
               }

               ++elem_prop_cnt;
            }
         }

         this.elementProperties = new ElementQNameProperty[elem_prop_cnt];
         this.hasMulti = has_multi;
         if (multi_with_uri_cnt > 0) {
            assert this.hasMulti;

            this.multiNamespacedProps = new ElementQNameProperty[multi_with_uri_cnt];
         } else {
            this.multiNamespacedProps = EMPTY_PROPS;
         }

      } else {
         String msg = "invalid ByNameBean java type: " + java_type + " found in " + btype;
         throw new XmlException(msg);
      }
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   Object getObjectFromIntermediate(Object inter) {
      if (this.needsHolder()) {
         ObjectIntermediary res = (ObjectIntermediary)inter;
         return res.getActualValue();
      } else {
         return inter;
      }
   }

   void predefineNamespaces(Object instance, MarshalResult result) throws XmlException {
      String prev_ensured_uri = null;
      int i = 0;

      for(int alen = this.multiNamespacedProps.length; i < alen; ++i) {
         ElementQNameProperty prop = this.multiNamespacedProps[i];
         String uri = prop.getName().getNamespaceURI();

         assert uri != null;

         if (!uri.equals(prev_ensured_uri) && prop.isSet(instance)) {
            Object val = prop.getValue(instance);
            if (WrappedArrayRuntimeBindingType.hasSignificantContent(val)) {
               result.ensureElementPrefix(uri);
               prev_ensured_uri = uri;
            }
         }
      }

   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      super.initialize(typeTable, loader);
      int pidx = 0;
      int i = 0;

      for(int alen = this.elementProperties.length; i < alen; ++i) {
         ElementQNameProperty p = this.elementProperties[i];
         if (p.isMultiple()) {
            String uri = p.getQName().getNamespaceURI();

            assert uri != null;

            if (!"".equals(uri)) {
               this.multiNamespacedProps[pidx++] = p;
            }
         }
      }

      Arrays.sort(this.multiNamespacedProps, new Comparator() {
         public int compare(Object o, Object o1) {
            ElementQNameProperty p1 = (ElementQNameProperty)o;
            ElementQNameProperty p2 = (ElementQNameProperty)o1;
            return p1.getQName().getNamespaceURI().compareTo(p2.getQName().getNamespaceURI());
         }
      });
   }

   boolean hasElementChildren() {
      return true;
   }

   protected ElementQNameProperty[] getElementProperties() {
      return this.elementProperties;
   }

   protected Collection getQNameProperties() {
      if (this.qNameProperties == null) {
         ByNameBean narrowed_type = (ByNameBean)this.getBindingType();
         Collection properties = narrowed_type.getProperties();
         if (narrowed_type.getAnyElementProperty() != null) {
            List list = new ArrayList();
            list.addAll(properties);
            list.add(narrowed_type.getAnyElementProperty());
            this.qNameProperties = Collections.unmodifiableCollection(list);
         } else {
            this.qNameProperties = properties;
         }
      }

      return this.qNameProperties;
   }

   public boolean needsHolder() {
      return this.hasMulti || this.hasCtor();
   }

   protected void initElementProperty(QNameProperty prop, int elem_idx, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      this.elementProperties[elem_idx] = new ElementQNameProperty(elem_idx, this.getJavaType(), prop, this, typeTable, loader);
   }

   protected Object createIntermediary(UnmarshalResult context) {
      if (this.needsHolder()) {
         return this.hasCtor() ? new CtorArgsMultiIntermediary(this) : new MultiIntermediary(this);
      } else {
         assert !this.hasCtor();

         return ClassLoadingUtils.newInstance(this.getJavaType());
      }
   }

   protected Object createIntermediary(UnmarshalResult context, Object actual_object) throws XmlException {
      if (this.needsHolder()) {
         if (this.hasCtor()) {
            throw new XmlException("cannot unmarshal into existing object with constructor parameters");
         } else {
            return new MultiIntermediary(this, actual_object);
         }
      } else {
         return actual_object;
      }
   }

   RuntimeBindingProperty getElementProperty(int index) {
      return this.elementProperties[index];
   }

   ElementQNameProperty getMatchingElementProperty(String uri, String localname, UnmarshalResult context, boolean wild) {
      if (wild) {
         return this.getWildProperty();
      } else {
         ElementQNameProperty prop = null;
         int i = 0;

         for(int len = this.elementProperties.length; i < len; ++i) {
            prop = this.elementProperties[i];
            if (propMatches(uri, localname, prop, context)) {
               return prop;
            }
         }

         if (prop != null && prop.wild) {
            return prop;
         } else {
            return null;
         }
      }
   }

   ElementQNameProperty getWildProperty() {
      return this.elementProperties[this.elementProperties.length - 1];
   }

   private static boolean propMatches(String uri, String localname, AttributeRuntimeBindingType.QNameRuntimeProperty prop, UnmarshalResult context) {
      assert localname != null;

      return context.doesByNameElementMatch(prop.getQName(), localname, uri, context.getNamespaceMapping());
   }

   public int getElementPropertyCount() {
      return this.elementProperties.length;
   }

   protected boolean hasMulti() {
      return this.hasMulti;
   }

   protected ArrayList getAllCtorProperties() {
      ArrayList props = new ArrayList();
      int att_cnt = this.getAttributePropertyCount();

      int elem_cnt;
      for(elem_cnt = 0; elem_cnt < att_cnt; ++elem_cnt) {
         RuntimeBindingProperty p = this.getAttributeProperty(elem_cnt);
         if (p.getCtorArgIndex() >= 0) {
            props.add(p);
         }
      }

      elem_cnt = this.getElementPropertyCount();

      for(int i = 0; i < elem_cnt; ++i) {
         ElementQNameProperty p = this.elementProperties[i];
         if (p.getCtorArgIndex() >= 0) {
            props.add(p);
         }
      }

      return props;
   }

   int getPropertyCount() {
      return this.getAttributePropertyCount() + this.getElementPropertyCount();
   }

   protected static class ElementQNameProperty extends AttributeRuntimeBindingType.QNameRuntimeProperty {
      protected final int propertyIndex;
      private boolean wild = false;

      ElementQNameProperty(int property_index, Class bean_class, QNameProperty prop, AttributeRuntimeBindingType containing_type, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         super(bean_class, prop, containing_type, typeTable, loader);
         this.propertyIndex = property_index;
         this.wild = prop instanceof GenericXmlProperty;

         assert !prop.isAttribute();

      }

      public void fill(Object inter, Object prop_obj) throws XmlException {
         if (prop_obj != null || !this.getRuntimeBindingType().isJavaPrimitive()) {
            if (this.containingType.needsHolder()) {
               ObjectIntermediary rh = (ObjectIntermediary)inter;
               if (this.isMultiple()) {
                  rh.addItem(this.propertyIndex, prop_obj);
               } else {
                  rh.setValue(this, prop_obj);
               }
            } else {
               this.setValue(inter, prop_obj);
            }

         }
      }

      boolean isWildUnbounded() {
         return this.wild && this.isMultiple();
      }
   }
}
