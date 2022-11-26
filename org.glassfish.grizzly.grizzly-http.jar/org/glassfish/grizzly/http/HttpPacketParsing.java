package org.glassfish.grizzly.http;

import org.glassfish.grizzly.http.util.MimeHeaders;

public interface HttpPacketParsing {
   boolean isHeaderParsed();

   void setHeaderParsed(boolean var1);

   HttpCodecFilter.HeaderParsingState getHeaderParsingState();

   HttpCodecFilter.ContentParsingState getContentParsingState();

   MimeHeaders getHeaders();
}
