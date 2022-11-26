package weblogic.j2ee.dd.xml.validator;

import weblogic.descriptor.DescriptorBean;
import weblogic.utils.ErrorCollectionException;

public interface AnnotationValidator {
   void validate(DescriptorBean var1, ClassLoader var2) throws ErrorCollectionException;
}
