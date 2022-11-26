package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ObligationType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Obligation";
   QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Obligation", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ObligationType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ObligationType", "xacml");
   String OBLIGATION_ID_ATTRIB_NAME = "ObligationId";
   String FULFILL_ON_ATTRIB_NAME = "FulfillOn";

   List getAttributeAssignments();

   String getObligationId();

   void setObligationId(String var1);

   EffectType getFulfillOn();

   void setFulfillOn(EffectType var1);
}
