package weblogic.j2ee.dd.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;

@Contract
public interface WseeAnnotationProcessor {
   boolean isSupportedType(Class var1);

   void addWebServiceRefs(Class var1, J2eeClientEnvironmentBean var2, BaseJ2eeAnnotationProcessor var3);

   void addWebServiceRef(Field var1, J2eeClientEnvironmentBean var2, BaseJ2eeAnnotationProcessor var3);

   void addWebServiceRef(Method var1, J2eeClientEnvironmentBean var2, BaseJ2eeAnnotationProcessor var3);

   List getWebServiceMethods(Class var1, Class var2);

   boolean hasWSAnnotation(Class var1);
}
