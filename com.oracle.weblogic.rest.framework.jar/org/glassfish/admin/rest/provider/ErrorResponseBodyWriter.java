package org.glassfish.admin.rest.provider;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;
import org.codehaus.jettison.json.JSONException;
import org.glassfish.admin.rest.Constants;
import org.glassfish.admin.rest.model.ErrorResponseBody;
import org.glassfish.admin.rest.utils.ExceptionUtil;

@Provider
@Produces({"application/json"})
public class ErrorResponseBodyWriter extends BaseProvider {
   public ErrorResponseBodyWriter() {
      super(ErrorResponseBody.class, Constants.MEDIA_TYPE_JSON_TYPE);
   }

   public String getContent(ErrorResponseBody body) {
      StringBuilder sb = new StringBuilder();

      try {
         sb.append(body.toJson().toString(this.getFormattingIndentLevel()));
      } catch (JSONException var4) {
         ExceptionUtil.log(var4);
      }

      return sb.toString();
   }
}
