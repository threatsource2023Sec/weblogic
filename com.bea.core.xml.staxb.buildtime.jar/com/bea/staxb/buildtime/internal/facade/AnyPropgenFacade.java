package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.GenericXmlProperty;
import com.bea.util.jam.JElement;
import com.bea.xbean.xb.xsdschema.Annotated;
import com.bea.xbean.xb.xsdschema.AnyDocument;
import java.math.BigInteger;
import org.w3c.dom.Node;

public class AnyPropgenFacade extends BtsPropgenFacade implements PropgenFacade {
   private AnyDocument.Any any = null;

   public AnyPropgenFacade(Java2SchemaContext j2sContext, JElement srcContext, GenericXmlProperty btsProp, String targetNamespace, AnyDocument.Any any) {
      super(j2sContext, srcContext, btsProp, targetNamespace);
      this.any = any;
   }

   public void setRequired(boolean required) {
      super.setRequired(required);
      this.any.setMinOccurs(BigInteger.valueOf(required ? 1L : 0L));
      if (!required) {
         this.any.setMaxOccurs("unbounded");
      }

   }

   public void setDocumentation(String docs) {
      super.setDocumentation(docs);
      setDocumentation(this.any, docs);
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
