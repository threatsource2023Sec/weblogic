package org.apache.xml.security.stax.impl.transformer;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class TransformEnvelopedSignature extends TransformIdentity {
   private int curLevel;
   private int sigElementLevel = -1;

   public XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod forInput) {
      switch (forInput) {
         case XMLSecEvent:
            return XMLSecurityConstants.TransformMethod.XMLSecEvent;
         case InputStream:
            return XMLSecurityConstants.TransformMethod.XMLSecEvent;
         default:
            throw new IllegalArgumentException("Unsupported class " + forInput.name());
      }
   }

   public void transform(XMLSecEvent xmlSecEvent) throws XMLStreamException {
      switch (xmlSecEvent.getEventType()) {
         case 1:
            ++this.curLevel;
            XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
            if (XMLSecurityConstants.TAG_dsig_Signature.equals(xmlSecStartElement.getName())) {
               this.sigElementLevel = this.curLevel;
               return;
            }
            break;
         case 2:
            XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
            if (this.sigElementLevel == this.curLevel && XMLSecurityConstants.TAG_dsig_Signature.equals(xmlSecEndElement.getName())) {
               this.sigElementLevel = -1;
               return;
            }

            --this.curLevel;
      }

      if (this.sigElementLevel == -1) {
         super.transform(xmlSecEvent);
      }

   }

   public void transform(InputStream inputStream) throws XMLStreamException {
      throw new UnsupportedOperationException("transform(InputStream) not supported");
   }
}
