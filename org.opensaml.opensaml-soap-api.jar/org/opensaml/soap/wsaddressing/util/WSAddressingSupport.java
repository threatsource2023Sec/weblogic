package org.opensaml.soap.wsaddressing.util;

import java.util.Objects;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wsaddressing.IsReferenceParameterBearing;

public final class WSAddressingSupport {
   private WSAddressingSupport() {
   }

   public static void addWSAIsReferenceParameter(XMLObject soapObject, boolean isReferenceParameter) {
      if (soapObject instanceof IsReferenceParameterBearing) {
         ((IsReferenceParameterBearing)soapObject).setWSAIsReferenceParameter(new XSBooleanValue(isReferenceParameter, false));
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither IsReferenceParameterBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(IsReferenceParameterBearing.WSA_IS_REFERENCE_PARAMETER_ATTR_NAME, (new XSBooleanValue(isReferenceParameter, false)).toString());
      }

   }

   public static boolean getWSAIsReferenceParameter(XMLObject soapObject) {
      if (soapObject instanceof IsReferenceParameterBearing) {
         XSBooleanValue value = ((IsReferenceParameterBearing)soapObject).isWSAIsReferenceParameterXSBoolean();
         if (value != null) {
            return value.getValue();
         }
      }

      if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
         return false;
      } else {
         String valueStr = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(IsReferenceParameterBearing.WSA_IS_REFERENCE_PARAMETER_ATTR_NAME));
         return Objects.equals("1", valueStr) || Objects.equals("true", valueStr);
      }
   }
}
