package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class DefaultPropertyMarshallers {
   public static final DefaultPropertyMarshallers _instance = new DefaultPropertyMarshallers();
   private Set unmarshallableTypes = new HashSet();
   private Map marshallers = new HashMap();

   public static DefaultPropertyMarshallers instance() {
      return _instance;
   }

   public PropertyMarshaller getMarshaller(HttpServletRequest request, Class javaType) throws Exception {
      PropertyMarshaller m = this.findMarshaller(request, javaType);
      if (m == null) {
         throw new Exception(MessageUtils.beanFormatter(request).msgMarshalUnknownPropertyType(javaType.getName()));
      } else {
         return m;
      }
   }

   public PropertyMarshaller findMarshaller(HttpServletRequest request, Class javaType) throws Exception {
      if (this.unmarshallableTypes.contains(javaType)) {
         return null;
      } else {
         PropertyMarshaller marshaller = (PropertyMarshaller)this.marshallers.get(javaType);
         if (marshaller == null) {
            Marshaller valueMarshaller = DefaultMarshallers.instance().findMarshaller(request, javaType);
            if (valueMarshaller != null) {
               marshaller = new DelegatingPropertyMarshaller(valueMarshaller);
               this.marshallers.put(javaType, marshaller);
            } else {
               this.unmarshallableTypes.add(javaType);
            }
         }

         return (PropertyMarshaller)marshaller;
      }
   }
}
