package org.glassfish.admin.rest.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.CompositeUtil;
import org.glassfish.admin.rest.composite.RestModel;

@Provider
@Produces({"application/json"})
@Consumes({"application/json"})
public class RestModelReader implements MessageBodyReader {
   public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
      String submittedType = mediaType.toString();
      int index = submittedType.indexOf(";");
      if (index > -1) {
         submittedType = submittedType.substring(0, index);
      }

      return submittedType.equals("application/json") && RestModel.class.isAssignableFrom(type);
   }

   public RestModel readFrom(Class type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap mm, InputStream entityStream) throws WebApplicationException, IOException {
      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(entityStream));
         StringBuilder sb = new StringBuilder();

         for(String line = in.readLine(); line != null; line = in.readLine()) {
            sb.append(line);
         }

         Locale locale = CompositeUtil.instance().getLocale(mm);
         JSONObject o = new JSONObject(sb.toString());
         RestModel model = (RestModel)CompositeUtil.instance().unmarshallClass(locale, type, o);
         Set cv = CompositeUtil.instance().validateRestModel(locale, model);
         if (!cv.isEmpty()) {
            Response response = Response.status(Status.BAD_REQUEST).entity(CompositeUtil.instance().getValidationFailureMessages(locale, cv, model)).build();
            throw new WebApplicationException(response);
         } else {
            return model;
         }
      } catch (JSONException var15) {
         throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(var15.getLocalizedMessage()).build());
      }
   }
}
