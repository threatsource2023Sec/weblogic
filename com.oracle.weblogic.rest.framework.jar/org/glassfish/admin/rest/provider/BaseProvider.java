package org.glassfish.admin.rest.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;

public abstract class BaseProvider implements MessageBodyWriter {
   public static final String HEADER_DEBUG = "__debug";
   public static final String JSONP_CALLBACK = "jsoncallback";
   @Context
   protected UriInfo uriInfo;
   @Context
   protected HttpHeaders requestHeaders;
   protected Class desiredType;
   protected MediaType[] supportedMediaTypes;

   public BaseProvider(Class desiredType, MediaType... mediaType) {
      this.desiredType = desiredType;
      if (mediaType == null) {
         mediaType = new MediaType[0];
      }

      this.supportedMediaTypes = mediaType;
   }

   public boolean isWriteable(Class type, Type genericType, Annotation[] antns, MediaType mt) {
      if (this.isGivenTypeWritable(type, genericType)) {
         MediaType[] var5 = this.supportedMediaTypes;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            MediaType supportedMediaType = var5[var7];
            if (mt.isCompatible(supportedMediaType)) {
               return true;
            }
         }
      }

      return false;
   }

   protected boolean isGivenTypeWritable(Class type, Type genericType) {
      return this.desiredType.isAssignableFrom(type);
   }

   public long getSize(Object t, Class type, Type type1, Annotation[] antns, MediaType mt) {
      return -1L;
   }

   public void writeTo(Object proxy, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
      entityStream.write(this.getContent(proxy).getBytes("UTF-8"));
   }

   public abstract String getContent(Object var1);

   protected int getFormattingIndentLevel() {
      return 4;
   }

   protected String getCallBackJSONP() {
      if (this.uriInfo == null) {
         return null;
      } else {
         MultivaluedMap l = this.uriInfo.getQueryParameters();
         return l == null ? null : (String)l.getFirst("jsoncallback");
      }
   }
}
