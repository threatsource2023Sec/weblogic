package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.TypeInfo;

public abstract class BaseMarshallerImpl implements BaseMarshaller {
   private String jsonTypeDescription;
   private Class javaType;
   private TypeInfo docType;

   protected BaseMarshallerImpl(String jsonTypeDescription, Class javaType, TypeInfo docType) {
      this(jsonTypeDescription, javaType);
      this.docType = docType;
   }

   protected BaseMarshallerImpl(String jsonTypeDescription, Class javaType) {
      this.jsonTypeDescription = jsonTypeDescription;
      this.javaType = javaType;
   }

   public Class javaType() {
      return this.javaType;
   }

   public String describeJavaType() {
      return this.javaType().getName();
   }

   public String describeJsonType() {
      return this.jsonTypeDescription;
   }

   public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
      return this.docType;
   }

   protected String getTypeMismatchMessage(HttpServletRequest request, Object json, String javaTypeDescription) {
      String j = json != null ? json.toString() : null;
      return MessageUtils.beanFormatter(request).msgJsonToJavaTypeMismatch(j, javaTypeDescription);
   }

   protected boolean isJsonNull(Object json) {
      return json == null || json == JSONObject.NULL;
   }
}
