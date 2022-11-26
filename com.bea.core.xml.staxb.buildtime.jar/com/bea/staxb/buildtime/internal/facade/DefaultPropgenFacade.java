package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.xbean.xb.xsdschema.Annotated;
import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.FormChoice;
import com.bea.xbean.xb.xsdschema.LocalElement;
import com.bea.xbean.xb.xsdschema.LocalSimpleType;
import com.bea.xbean.xb.xsdschema.RestrictionDocument;
import com.bea.xbean.xb.xsdschema.Attribute.Use;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlAnySimpleType.Factory;
import java.math.BigInteger;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

public class DefaultPropgenFacade extends BtsPropgenFacade implements PropgenFacade {
   private LocalElement mXsElement = null;
   private Attribute mXsAttribute = null;
   private RestrictionDocument.Restriction mRestriction = null;

   public DefaultPropgenFacade(Java2SchemaContext j2sContext, JElement srcContext, QNameProperty btsProp, String targetNamespace, LocalElement element) {
      super(j2sContext, srcContext, btsProp, targetNamespace);
      this.mXsElement = element;
   }

   public DefaultPropgenFacade(Java2SchemaContext j2sContext, JElement srcContext, QNameProperty btsProp, String targetNamespace, Attribute att) {
      super(j2sContext, srcContext, btsProp, targetNamespace);
      this.mXsAttribute = att;
   }

   public void setSchemaName(String name) {
      super.setSchemaName_internal(name = this.makeNcNameSafe(name));
      if (this.mXsElement != null) {
         this.mXsElement.setName(name);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mXsAttribute.setName(name);
      }

   }

   protected void setComponentTypeOnSchema(JClass componentType) {
      if (this.mXsAttribute != null) {
         this.mJ2sContext.getLogger().logError("Java array properties cannot be mapped to xml attributes", this.mSrcContext);
      } else {
         if (this.mXsElement == null) {
            throw new IllegalStateException("neither element nor attribute present");
         }

         this.mXsElement.setMaxOccurs("unbounded");
         if (!this.checkSetType_char(componentType)) {
            this.setType(this.getQnameFor(componentType));
         }
      }

   }

   public void setNillable(boolean b) {
      super.setNillable(b);
      if (this.mXsElement != null) {
         this.mXsElement.setNillable(b);
         this.mBtsProp.setNillable(b);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mJ2sContext.getLogger().logError("Attributes cannot be nillable:", this.mSrcContext);
      }

   }

   public void setRequired(boolean b) {
      super.setRequired(b);
      if (this.mXsElement != null) {
         this.mXsElement.setMinOccurs(BigInteger.valueOf(b ? 1L : 0L));
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mXsAttribute.setUse(b ? Use.REQUIRED : Use.OPTIONAL);
      }

   }

   public void setDefault(String val) {
      super.setDefault(val);
      if (this.mXsElement != null) {
         this.mXsElement.setDefault(val);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mXsAttribute.setDefault(val);
      }

   }

   public void addFacet(int facetType, String val) {
      super.addFacet(facetType, val);
      RestrictionDocument.Restriction r = this.findOrCreateSimpleTypeRestriction();
      XmlAnySimpleType xmlval = Factory.newInstance();
      xmlval.setStringValue(val);
      switch (facetType) {
         case 0:
            r.addNewLength().setValue(xmlval);
            break;
         case 1:
            r.addNewMinLength().setValue(xmlval);
            break;
         case 2:
            r.addNewMaxLength().setValue(xmlval);
            break;
         case 3:
            r.addNewMinExclusive().setValue(xmlval);
            break;
         case 4:
            r.addNewMinInclusive().setValue(xmlval);
            break;
         case 5:
            r.addNewMaxInclusive().setValue(xmlval);
            break;
         case 6:
            r.addNewMaxExclusive().setValue(xmlval);
            break;
         case 7:
            r.addNewTotalDigits().setValue(xmlval);
            break;
         case 8:
            r.addNewFractionDigits().setValue(xmlval);
            break;
         case 9:
            r.addNewWhiteSpace().setValue(xmlval);
            break;
         case 10:
            r.addNewPattern().setValue(xmlval);
            break;
         case 11:
            r.addNewEnumeration().setValue(xmlval);
            break;
         default:
            throw new IllegalStateException("invalid facet type: " + facetType);
      }

   }

   public void setForm(String form) {
      super.setForm(form);
      FormChoice.Enum fc;
      if (form.equals("qualified")) {
         fc = FormChoice.QUALIFIED;
      } else {
         if (!form.equals("unqualified")) {
            throw new IllegalArgumentException("invalid element/attribute form '" + form + "'");
         }

         fc = FormChoice.UNQUALIFIED;
      }

      if (this.mXsElement != null) {
         this.mXsElement.setForm(fc);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mXsAttribute.setForm(fc);
      }

   }

   public void setDocumentation(String docs) {
      super.setDocumentation(docs);
      if (this.mXsElement != null) {
         setDocumentation(this.mXsElement, docs);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         setDocumentation(this.mXsAttribute, docs);
      }

   }

   protected void setType(QName q) {
      super.setType(q);
      if (this.mXsElement != null) {
         this.mXsElement.setType(q);
      } else {
         if (this.mXsAttribute == null) {
            throw new IllegalStateException();
         }

         this.mXsAttribute.setType(q);
      }

   }

   protected RestrictionDocument.Restriction findOrCreateSimpleTypeRestriction() {
      super.findOrCreateSimpleTypeRestriction();
      if (this.mRestriction != null) {
         return this.mRestriction;
      } else {
         LocalSimpleType lst;
         if (this.mXsElement != null) {
            lst = this.mXsElement.addNewSimpleType();
            this.mRestriction = lst.addNewRestriction();
            this.mRestriction.setBase(this.mXsElement.getType());
            this.mXsElement.unsetType();
         } else {
            if (this.mXsAttribute == null) {
               throw new IllegalStateException();
            }

            lst = this.mXsAttribute.addNewSimpleType();
            this.mRestriction = lst.addNewRestriction();
            this.mRestriction.setBase(this.mXsAttribute.getType());
            this.mXsAttribute.unsetType();
         }

         return this.mRestriction;
      }
   }

   static void setDocumentation(Annotated xmlBean, String docs) {
      if (docs == null) {
         throw new IllegalArgumentException("null docs");
      } else if (xmlBean == null) {
         throw new IllegalArgumentException("null xmlBean");
      } else {
         Node doc = xmlBean.addNewAnnotation().addNewDocumentation().getDomNode();
         doc.appendChild(doc.getOwnerDocument().createCDATASection(docs));
      }
   }
}
