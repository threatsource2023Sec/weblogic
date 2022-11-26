package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public interface TransferEncoding {
   boolean wantDecode(HttpHeader var1);

   boolean wantEncode(HttpHeader var1);

   void prepareSerialize(FilterChainContext var1, HttpHeader var2, HttpContent var3);

   ParsingResult parsePacket(FilterChainContext var1, HttpHeader var2, Buffer var3);

   Buffer serializePacket(FilterChainContext var1, HttpContent var2);
}
