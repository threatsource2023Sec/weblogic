package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface SubjectMatchType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectMatch";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectMatch", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "SubjectMatchType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectMatchType", "xacml");
   String MATCH_ID_ATTRIB_NAME = "MatchId";

   AttributeValueType getAttributeValue();

   void setAttributeValue(AttributeValueType var1);

   AttributeDesignatorType getSubjectAttributeDesignator();

   void setSubjectAttributeDesignator(AttributeDesignatorType var1);

   AttributeSelectorType getAttributeSelector();

   void setAttributeSelector(AttributeSelectorType var1);

   String getMatchId();

   void setMatchId(String var1);
}
