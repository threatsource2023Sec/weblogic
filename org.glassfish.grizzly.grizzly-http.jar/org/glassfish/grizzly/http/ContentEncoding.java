package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;

public interface ContentEncoding {
   String getName();

   String[] getAliases();

   boolean wantDecode(HttpHeader var1);

   boolean wantEncode(HttpHeader var1);

   ParsingResult decode(Connection var1, HttpContent var2);

   HttpContent encode(Connection var1, HttpContent var2);
}
