package weblogic.net.http;

import java.util.Map;

public interface HttpRequestHeaderContributor {
   Map getHeadersForOutgoingRequest();
}
