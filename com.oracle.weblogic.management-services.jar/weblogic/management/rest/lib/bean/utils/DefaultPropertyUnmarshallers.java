package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class DefaultPropertyUnmarshallers {
   public static final DefaultPropertyUnmarshallers _instance = new DefaultPropertyUnmarshallers();
   private Set unUnmarshallableTypes = new HashSet();
   private Map unmarshallers = new HashMap();

   public static DefaultPropertyUnmarshallers instance() {
      return _instance;
   }

   public PropertyUnmarshaller getUnmarshaller(HttpServletRequest request, Class javaType) throws Exception {
      PropertyUnmarshaller m = this.findUnmarshaller(request, javaType);
      if (m == null) {
         throw new Exception(MessageUtils.beanFormatter(request).msgUnmarshalUnknownPropertyType(javaType.getName()));
      } else {
         return m;
      }
   }

   public PropertyUnmarshaller findUnmarshaller(HttpServletRequest request, Class javaType) throws Exception {
      if (this.unUnmarshallableTypes.contains(javaType)) {
         return null;
      } else {
         PropertyUnmarshaller unmarshaller = (PropertyUnmarshaller)this.unmarshallers.get(javaType);
         if (unmarshaller == null) {
            Unmarshaller valueUnmarshaller = DefaultMarshallers.instance().findUnmarshaller(request, javaType);
            if (valueUnmarshaller != null) {
               unmarshaller = new DelegatingPropertyUnmarshaller(valueUnmarshaller);
               this.unmarshallers.put(javaType, unmarshaller);
            } else {
               this.unUnmarshallableTypes.add(javaType);
            }
         }

         return (PropertyUnmarshaller)unmarshaller;
      }
   }
}
