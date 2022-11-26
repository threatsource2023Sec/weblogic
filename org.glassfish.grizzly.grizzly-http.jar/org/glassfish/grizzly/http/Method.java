package org.glassfish.grizzly.http;

import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.http.util.DataChunk;

public final class Method {
   public static final Method OPTIONS;
   public static final Method GET;
   public static final Method HEAD;
   public static final Method POST;
   public static final Method PUT;
   public static final Method DELETE;
   public static final Method TRACE;
   public static final Method CONNECT;
   public static final Method PATCH;
   public static final Method PRI;
   private final String methodString;
   private final byte[] methodBytes;
   private final PayloadExpectation payloadExpectation;

   public static Method CUSTOM(String methodName) {
      return CUSTOM(methodName, Method.PayloadExpectation.ALLOWED);
   }

   public static Method CUSTOM(String methodName, PayloadExpectation payloadExpectation) {
      return new Method(methodName, payloadExpectation);
   }

   public static Method valueOf(DataChunk methodC) {
      if (methodC.equals(GET.getMethodString())) {
         return GET;
      } else if (methodC.equals(POST.getMethodBytes())) {
         return POST;
      } else if (methodC.equals(HEAD.getMethodBytes())) {
         return HEAD;
      } else if (methodC.equals(PUT.getMethodBytes())) {
         return PUT;
      } else if (methodC.equals(DELETE.getMethodBytes())) {
         return DELETE;
      } else if (methodC.equals(TRACE.getMethodBytes())) {
         return TRACE;
      } else if (methodC.equals(CONNECT.getMethodBytes())) {
         return CONNECT;
      } else if (methodC.equals(OPTIONS.getMethodBytes())) {
         return OPTIONS;
      } else if (methodC.equals(PATCH.getMethodBytes())) {
         return PATCH;
      } else {
         return methodC.equals(PRI.getMethodBytes()) ? PRI : CUSTOM(methodC.toString());
      }
   }

   public static Method valueOf(String method) {
      if (method.equals(GET.getMethodString())) {
         return GET;
      } else if (method.equals(POST.getMethodString())) {
         return POST;
      } else if (method.equals(HEAD.getMethodString())) {
         return HEAD;
      } else if (method.equals(PUT.getMethodString())) {
         return PUT;
      } else if (method.equals(DELETE.getMethodString())) {
         return DELETE;
      } else if (method.equals(TRACE.getMethodString())) {
         return TRACE;
      } else if (method.equals(CONNECT.getMethodString())) {
         return CONNECT;
      } else if (method.equals(OPTIONS.getMethodString())) {
         return OPTIONS;
      } else if (method.equals(PATCH.getMethodString())) {
         return PATCH;
      } else {
         return method.equals(PRI.getMethodString()) ? PRI : CUSTOM(method);
      }
   }

   private Method(String methodString, PayloadExpectation payloadExpectation) {
      this.methodString = methodString;

      try {
         this.methodBytes = methodString.getBytes("US-ASCII");
      } catch (UnsupportedEncodingException var4) {
         throw new IllegalStateException(var4);
      }

      this.payloadExpectation = payloadExpectation;
   }

   public String getMethodString() {
      return this.methodString;
   }

   public byte[] getMethodBytes() {
      return this.methodBytes;
   }

   public PayloadExpectation getPayloadExpectation() {
      return this.payloadExpectation;
   }

   public String toString() {
      return this.methodString;
   }

   public boolean matchesMethod(String method) {
      return this.methodString.equals(method);
   }

   static {
      OPTIONS = new Method("OPTIONS", Method.PayloadExpectation.ALLOWED);
      GET = new Method("GET", Method.PayloadExpectation.UNDEFINED);
      HEAD = new Method("HEAD", Method.PayloadExpectation.UNDEFINED);
      POST = new Method("POST", Method.PayloadExpectation.ALLOWED);
      PUT = new Method("PUT", Method.PayloadExpectation.ALLOWED);
      DELETE = new Method("DELETE", Method.PayloadExpectation.UNDEFINED);
      TRACE = new Method("TRACE", Method.PayloadExpectation.NOT_ALLOWED);
      CONNECT = new Method("CONNECT", Method.PayloadExpectation.NOT_ALLOWED);
      PATCH = new Method("PATCH", Method.PayloadExpectation.ALLOWED);
      PRI = new Method("PRI", Method.PayloadExpectation.NOT_ALLOWED);
   }

   public static enum PayloadExpectation {
      ALLOWED,
      NOT_ALLOWED,
      UNDEFINED;
   }
}
