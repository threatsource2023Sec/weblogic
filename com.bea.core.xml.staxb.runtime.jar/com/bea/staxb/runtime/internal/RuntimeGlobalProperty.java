package com.bea.staxb.runtime.internal;

import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlException;
import java.math.BigInteger;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

final class RuntimeGlobalProperty extends RuntimeBindingProperty {
   private final QName rootElement;
   private final RuntimeBindingType runtimeBindingType;
   private final Node parent;
   private final SchemaTypeLoader schemaTypeLoader;

   public RuntimeGlobalProperty(QName rootElement, RuntimeBindingType runtimeBindingType) {
      this(rootElement, runtimeBindingType, (Node)null, (SchemaTypeLoader)null);
   }

   public RuntimeGlobalProperty(QName rootElement, RuntimeBindingType runtimeBindingType, Node parent, SchemaTypeLoader schemaTypeLoader) {
      super((RuntimeBindingType)null);
      this.rootElement = rootElement;
      this.runtimeBindingType = runtimeBindingType;
      this.parent = parent;
      this.schemaTypeLoader = schemaTypeLoader;
   }

   public RuntimeBindingType getRuntimeBindingType() {
      return this.runtimeBindingType;
   }

   public QName getName() {
      return this.rootElement;
   }

   RuntimeBindingType getContainingType() {
      throw new AssertionError("unused");
   }

   void setValue(Object target, Object prop_obj) throws XmlException {
      throw new AssertionError("not used");
   }

   public void fill(Object inter, Object prop_obj) throws XmlException {
      throw new UnsupportedOperationException();
   }

   public Object getValue(Object parent_obj) throws XmlException {
      throw new AssertionError("UNIMP: " + this);
   }

   public boolean isSet(Object parentObject) throws XmlException {
      return true;
   }

   public boolean isMultiple() {
      SchemaProperty prop = this.getSchemaProperty();
      if (prop == null) {
         return false;
      } else {
         return prop.getMaxOccurs() == null || prop.getMaxOccurs().compareTo(BigInteger.ONE) > 0;
      }
   }

   public boolean isNillable() {
      SchemaProperty prop = this.getSchemaProperty();
      if (prop != null) {
         return prop.hasNillable() != 0;
      } else {
         return true;
      }
   }

   public boolean isOptional() {
      SchemaProperty prop = this.getSchemaProperty();
      if (prop != null) {
         return prop.getMinOccurs().signum() == 0;
      } else {
         return false;
      }
   }

   private SchemaProperty getSchemaProperty() {
      if (this.schemaTypeLoader != null && this.parent != null) {
         SchemaGlobalElement sge = this.schemaTypeLoader.findElement(new QName(this.parent.getNamespaceURI(), this.parent.getLocalName()));
         SchemaType st = null;
         SchemaProperty prop = null;
         if (sge != null) {
            st = sge.getType();
         }

         if (st != null) {
            prop = st.getElementProperty(this.rootElement);
         }

         return prop;
      } else {
         return null;
      }
   }

   public String getLexicalDefault() {
      throw new AssertionError("UNIMP");
   }

   public boolean isTransient(Object currObject) {
      return false;
   }
}
