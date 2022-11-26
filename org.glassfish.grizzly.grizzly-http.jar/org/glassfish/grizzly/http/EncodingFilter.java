package org.glassfish.grizzly.http;

public interface EncodingFilter {
   boolean applyEncoding(HttpHeader var1);

   boolean applyDecoding(HttpHeader var1);
}
