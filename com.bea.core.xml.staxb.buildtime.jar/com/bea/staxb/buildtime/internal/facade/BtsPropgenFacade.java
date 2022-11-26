package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.Java2Schema;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.staxb.buildtime.internal.bts.ParentInstanceFactory;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JField;
import com.bea.util.jam.JMethod;
import com.bea.xbean.xb.xsdschema.RestrictionDocument;
import javax.xml.namespace.QName;

public class BtsPropgenFacade implements PropgenFacade {
   protected static final QName XS_STRING = new QName("http://www.w3.org/2001/XMLSchema", "string");
   protected static final String CHARACTER = Character.class.getName();
   protected static final String CHAR;
   protected boolean mFinished = false;
   protected QNameProperty mBtsProp = null;
   protected JElement mSrcContext = null;
   protected Java2SchemaContext mJ2sContext;
   protected String mXsTargetNamespace;
   protected JClass mType = null;

   public BtsPropgenFacade(Java2SchemaContext j2sContext, JElement srcContext, QNameProperty btsProp, String targetNamespace) {
      this.mJ2sContext = j2sContext;
      this.mSrcContext = srcContext;
      this.mBtsProp = btsProp;
      this.mXsTargetNamespace = targetNamespace;
   }

   public QNameProperty getBtsProperty() {
      return this.mBtsProp;
   }

   public JClass getType() {
      return this.mType;
   }

   public void finish() {
      this.mFinished = true;
   }

   public void setSchemaName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else {
         this.assertNotFinished();
         this.setSchemaName_internal(this.makeNcNameSafe(name));
      }
   }

   protected void setSchemaName_internal(String ncSafename) {
      this.mBtsProp.setQName(new QName(this.mXsTargetNamespace, ncSafename));
   }

   public void setGetter(JMethod g) {
      if (g == null) {
         throw new IllegalArgumentException("null method");
      } else {
         this.assertNotFinished();
         if (this.mBtsProp.getField() != null) {
            throw new IllegalStateException("field already set");
         } else {
            this.mBtsProp.setGetterName(MethodName.create(g));
         }
      }
   }

   public void setSetter(JMethod s) {
      if (s == null) {
         throw new IllegalArgumentException("null method");
      } else {
         this.assertNotFinished();
         if (this.mBtsProp.getField() != null) {
            throw new IllegalStateException("field already set");
         } else {
            this.mBtsProp.setSetterName(MethodName.create(s));
         }
      }
   }

   public void setField(JField f) {
      if (f == null) {
         throw new IllegalArgumentException("null field");
      } else {
         this.assertNotFinished();
         if (!f.isPublic()) {
            throw new IllegalStateException(f.getSimpleName() + " is not a public field");
         } else if (this.mBtsProp.getSetter() != null) {
            throw new IllegalStateException("setter already set");
         } else if (this.mBtsProp.getGetter() != null) {
            throw new IllegalStateException("getter already set");
         } else {
            this.mBtsProp.setField(f.getSimpleName());
         }
      }
   }

   public void setIssetter(JMethod s) {
      if (s == null) {
         throw new IllegalArgumentException("null method");
      } else {
         this.assertNotFinished();
         this.mBtsProp.setIssetterName(MethodName.create(s));
      }
   }

   public void setFactory(JMethod s) {
      if (s == null) {
         throw new IllegalArgumentException("null method");
      } else {
         this.assertNotFinished();
         this.mBtsProp.setJavaInstanceFactory(ParentInstanceFactory.forMethodName(MethodName.create(s)));
      }
   }

   public void setType(JClass propType) {
      if (propType == null) {
         throw new IllegalArgumentException("null class");
      } else {
         this.assertResolved(propType);
         this.assertNotFinished();
         this.mType = propType;
         if (!propType.isArrayType()) {
            if (!this.checkSetType_char(propType)) {
               this.setType(this.getQnameFor(propType));
            }

            BindingType bt = this.mJ2sContext.findOrCreateBindingTypeFor(propType);
            this.mBtsProp.setBindingType(bt);
            String propNs = this.mBtsProp.getQName().getNamespaceURI();
            BindingTypeName btn = bt.getName();
            XmlTypeName xmlName = btn.getXmlName();
            String typeNs = xmlName.getQName().getNamespaceURI();
            this.mJ2sContext.checkNsForImport(propNs, typeNs);
         } else {
            BindingTypeName btn = this.mJ2sContext.getBindingLoader().lookupTypeFor(JavaTypeName.forJClass(propType));
            if (btn != null) {
               this.setType(btn.getXmlName().getQName());
               this.mBtsProp.setBindingType(this.mJ2sContext.getBindingLoader().getBindingType(btn));
            } else if (propType.getArrayDimensions() == 1) {
               JClass componentType = propType.getArrayComponentType();
               this.setComponentTypeOnSchema(componentType);
               this.mBtsProp.setMultiple(true);
               this.mBtsProp.setCollectionClass(JavaTypeName.forString(componentType.getQualifiedName() + "[]"));
               this.mBtsProp.setBindingType(this.mJ2sContext.findOrCreateBindingTypeFor(componentType));
            }
         }

      }
   }

   protected void setComponentTypeOnSchema(JClass componentType) {
   }

   public void setNillable(boolean b) {
      this.assertNotFinished();
      this.mBtsProp.setNillable(b);
   }

   public void setRequired(boolean b) {
      this.assertNotFinished();
   }

   public void setDefault(String val) {
      if (val == null) {
         throw new IllegalArgumentException("null value");
      } else {
         this.assertNotFinished();
      }
   }

   public void addFacet(int facetType, String val) {
      if (val == null) {
         throw new IllegalArgumentException("null value");
      } else {
         this.assertNotFinished();
      }
   }

   public void setCtorParamIndex(int i) {
      this.assertNotFinished();
      this.mBtsProp.setCtorParamIndex(i);
   }

   public void setForm(String form) {
      if (form == null) {
         throw new IllegalArgumentException("null form");
      } else {
         this.assertNotFinished();
      }
   }

   public void setDocumentation(String docs) {
   }

   protected void setType(QName q) {
   }

   protected QName getQnameFor(JClass clazz) {
      this.mJ2sContext.findOrCreateBindingTypeFor(clazz);
      JavaTypeName jtn = JavaTypeName.forString(clazz.getQualifiedName());
      BindingTypeName btn = this.mJ2sContext.getBindingLoader().lookupTypeFor(jtn);
      if (btn != null) {
         BindingType bt = this.mJ2sContext.getBindingLoader().getBindingType(btn);
         if (bt != null) {
            return bt.getName().getXmlName().getQName();
         }
      }

      this.mJ2sContext.getLogger().logError("could not get qname", clazz);
      return new QName("ERROR", clazz.getQualifiedName());
   }

   protected boolean checkSetType_char(JClass propType) {
      this.assertResolved(propType);
      if (!propType.getQualifiedName().equals(CHAR) && !propType.getQualifiedName().equals(CHARACTER)) {
         return false;
      } else {
         this.setType(XS_STRING);
         this.addFacet(0, "1");
         return true;
      }
   }

   protected String makeNcNameSafe(String name) {
      return Java2Schema.makeNcNameSafe(name);
   }

   protected RestrictionDocument.Restriction findOrCreateSimpleTypeRestriction() {
      this.assertNotFinished();
      return null;
   }

   protected void assertResolved(JClass clazz) {
      if (clazz.isUnresolvedType()) {
         this.mJ2sContext.getLogger().logError("Could not resolve class: " + clazz.getQualifiedName(), clazz);
      }

   }

   protected void assertNotFinished() {
      if (this.mFinished) {
         throw new IllegalStateException("finish() has been called on this PropgenFacade");
      }
   }

   private void p(String s) {
      System.out.println(" [BtsPropgenFacade]  " + s);
   }

   static {
      CHAR = Character.TYPE.getName();
   }
}
