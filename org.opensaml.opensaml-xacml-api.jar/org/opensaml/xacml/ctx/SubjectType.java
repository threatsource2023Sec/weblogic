package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface SubjectType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Subject", "xacml-context");
   String TYPE_LOCAL_NAME = "SubjectType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "SubjectType", "xacml-context");
   String SUBJECT_CATEGORY_ATTTRIB_NAME = "SubjectCategory";
   String SUBJECT_CATEGORY_ATTTRIB_DEFAULT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";

   List getAttributes();

   String getSubjectCategory();

   void setSubjectCategory(String var1);
}
