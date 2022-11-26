package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingProperty;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.staxb.buildtime.internal.bts.SimpleContentBean;
import com.bea.staxb.buildtime.internal.bts.SimpleContentProperty;
import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.namespace.QName;

class SimpleContentRuntimeBindingType extends AttributeRuntimeBindingType {
   private SimpleContentRuntimeProperty simpleTypeProperty;
   private boolean isSimplePropertyQNameType;

   SimpleContentRuntimeBindingType(SimpleContentBean simpleContentBean) throws XmlException {
      super(simpleContentBean);
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public boolean needsHolder() {
      return this.hasCtor();
   }

   public final void initialize(RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      SimpleContentBean scb = this.getSimpleContentBean();
      SimpleContentProperty simpleContentProperty = scb.getSimpleContentProperty();
      this.simpleTypeProperty = new SimpleContentRuntimeProperty(this.getJavaType(), simpleContentProperty, this, typeTable, loader);
      super.initialize(typeTable, loader);
      this.isSimplePropertyQNameType = isQNameProp(this.simpleTypeProperty);
   }

   boolean hasElementChildren() {
      return false;
   }

   private SimpleContentBean getSimpleContentBean() {
      return (SimpleContentBean)this.getBindingType();
   }

   protected void initElementProperty(QNameProperty prop, int elem_idx, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      throw new AssertionError("invalid property for this type: " + prop);
   }

   protected Collection getQNameProperties() {
      SimpleContentBean narrowed_type = (SimpleContentBean)this.getBindingType();
      return narrowed_type.getAttributeProperties();
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      if (!super.canUseDefaultNamespace(obj)) {
         return false;
      } else if (!this.isSimplePropertyQNameType) {
         return true;
      } else {
         Object value = this.simpleTypeProperty.getValue(obj);
         QName qn = (QName)value;
         return !"".equals(qn.getNamespaceURI());
      }
   }

   protected Object createIntermediary(UnmarshalResult context) {
      if (this.needsHolder()) {
         assert this.hasCtor();

         return new CtorArgsIntermediary(this);
      } else {
         assert !this.hasCtor();

         return ClassLoadingUtils.newInstance(this.getJavaType());
      }
   }

   int getElementPropertyCount() {
      return 0;
   }

   protected boolean hasMulti() {
      return false;
   }

   protected ArrayList getAllCtorProperties() {
      ArrayList props = new ArrayList();
      int att_cnt = this.getAttributePropertyCount();

      for(int i = 0; i < att_cnt; ++i) {
         RuntimeBindingProperty p = this.getAttributeProperty(i);
         if (p.getCtorArgIndex() >= 0) {
            props.add(p);
         }
      }

      if (this.simpleTypeProperty.getCtorArgIndex() >= 0) {
         props.add(this.simpleTypeProperty);
      }

      return props;
   }

   RuntimeBindingProperty getSimpleContentProperty() {
      return this.simpleTypeProperty;
   }

   private static final class SimpleContentRuntimeProperty extends RuntimeBindingType.BeanRuntimeProperty {
      private final SimpleContentProperty simpleContentProperty;
      private final SimpleContentRuntimeBindingType containingType;

      SimpleContentRuntimeProperty(Class beanClass, SimpleContentProperty prop, SimpleContentRuntimeBindingType containing_type, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         super(beanClass, prop, containing_type, typeTable, loader);
         this.simpleContentProperty = prop;
         this.containingType = containing_type;
      }

      QName getName() {
         throw new AssertionError("prop has no name by design");
      }

      RuntimeBindingType getContainingType() {
         return this.containingType;
      }

      boolean isMultiple() {
         return false;
      }

      boolean isNillable() {
         return false;
      }

      boolean isOptional() {
         return false;
      }

      String getLexicalDefault() {
         return null;
      }

      protected BindingProperty getBindingProperty() {
         return this.simpleContentProperty;
      }

      public void fill(Object inter, Object prop_obj) throws XmlException {
         if (this.containingType.needsHolder()) {
            ObjectIntermediary rh = (ObjectIntermediary)inter;
            rh.setValue(this, prop_obj);
         } else {
            this.setValue(inter, prop_obj);
         }

      }

      public String toString() {
         return "SimpleContentRuntimeProperty{type=" + this.getRuntimeBindingType() + "}";
      }
   }
}
