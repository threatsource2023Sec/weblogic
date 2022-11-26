package weblogic.servlet.internal;

import weblogic.utils.http.HttpReasonPhraseCoder;

public final class ErrorMessages {
   public static final String CITATION = "From RFC 2068 <i>Hypertext Transfer Protocol -- HTTP/1.1</i>:";
   public static final String SECTION_400 = "10.4.1 400 Bad Request";
   public static final String MESSAGE_400 = "The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.";
   public static final String SECTION_401 = "10.4.2 401 Unauthorized";
   public static final String MESSAGE_401 = "The request requires user authentication. The response MUST include a WWW-Authenticate header field (section 14.46) containing a challenge applicable to the requested resource. The client MAY repeat the request with a suitable Authorization header field (section 14.8). If the request already included Authorization credentials, then the 401 response indicates that authorization has been refused for those credentials. If the 401 response contains the same challenge as the prior response, and the user agent has already attempted authentication at least once, then the user SHOULD be presented the entity that was given in the response, since that entity MAY include relevant diagnostic information. HTTP access authentication is explained in section 11.";
   public static final String SECTION_402 = "10.4.3 402 Payment Required";
   public static final String MESSAGE_402 = "This code is reserved for future use.";
   public static final String SECTION_403 = "10.4.4 403 Forbidden";
   public static final String MESSAGE_403 = "The server understood the request, but is refusing to fulfill it. Authorization will not help and the request SHOULD NOT be repeated. If the request method was not HEAD and the server wishes to make public why the request has not been fulfilled, it SHOULD describe the reason for the refusal in the entity. This status code is commonly used when the server does not wish to reveal exactly why the request has been refused, or when no other response is applicable.";
   public static final String SECTION_404 = "10.4.5 404 Not Found";
   public static final String MESSAGE_404 = "The server has not found anything matching the Request-URI. No indication is given of whether the condition is temporary or permanent.</p><p>If the server does not wish to make this information available to the client, the status code 403 (Forbidden) can be used instead. The 410 (Gone) status code SHOULD be used if the server knows, through some internally configurable mechanism, that an old resource is permanently unavailable and has no forwarding address.";
   public static final String SECTION_405 = "10.4.6 405 Method Not Allowed";
   public static final String MESSAGE_405 = "The method specified in the Request-Line is not allowed for the resource identified by the Request-URI. The response MUST include an Allow header containing a list of valid methods for the requested resource.";
   public static final String SECTION_406 = "10.4.7 406 Not Acceptable";
   public static final String MESSAGE_406 = "The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request.</p><p>Unless it was a HEAD request, the response SHOULD include an entity containing a list of available entity characteristics and location(s) from which the user or user agent can choose the one most appropriate.  The entity format is specified by the media type given in the Content-Type header field. Depending upon the format and the capabilities of the user agent, selection of the most appropriate choice may be performed automatically. However, this specification does not define any standard for such automatic selection.<blockquote>Note: HTTP/1.1 servers are allowed to return responses which are not acceptable according to the accept headers sent in the request. In some cases, this may even be preferable to sending a 406 response. User agents are encouraged to inspect the headers of an incoming response to determine if it is acceptable. If the response could be unacceptable, a user agent SHOULD temporarily stop receipt of more data and query the user for a decision on further actions.</blockquote>";
   public static final String SECTION_407 = "10.4.8 407 Proxy Authentication Required";
   public static final String MESSAGE_407 = "This code is similar to 401 (Unauthorized), but indicates that the client MUST first authenticate itself with the proxy. The proxy MUST return a Proxy-Authenticate header field (section 14.33) containing a challenge applicable to the proxy for the requested resource. The client MAY repeat the request with a suitable Proxy-Authorization header field (section 14.34). HTTP access authentication is explained in section 11.";
   public static final String SECTION_408 = "10.4.9 408 Request Timeout";
   public static final String MESSAGE_408 = "The client did not produce a request within the time that the server was prepared to wait. The client MAY repeat the request without modifications at any later time.";
   public static final String SECTION_409 = "10.4.10 409 Conflict";
   public static final String MESSAGE_409 = "The request could not be completed due to a conflict with the current state of the resource. This code is only allowed in situations where it is expected that the user might be able to resolve the conflict and resubmit the request. The response body SHOULD include enough information for the user to recognize the source of the conflict. Ideally, the response entity would include enough information for the user or user agent to fix the problem; however, that may not be possible and is not required.</p><p>Conflicts are most likely to occur in response to a PUT request. If versioning is being used and the entity being PUT includes changes to a resource which conflict with those made by an earlier (third-party) request, the server MAY use the 409 response to indicate that it can't complete the request. In this case, the response entity SHOULD contain a list of the differences between the two versions in a format defined by the response Content-Type.";
   public static final String SECTION_410 = "10.4.11 410 Gone";
   public static final String MESSAGE_410 = "The requested resource is no longer available at the server and no forwarding address is known. This condition SHOULD be considered permanent. Clients with link editing capabilities SHOULD delete references to the Request-URI after user approval. If the server does not know, or has no facility to determine, whether or not the condition is permanent, the status code 404 (Not Found) SHOULD be used instead.  This response is cachable unless indicated otherwise.</p><p>The 410 response is primarily intended to assist the task of web maintenance by notifying the recipient that the resource is intentionally unavailable and that the server owners desire that remote links to that resource be removed. Such an event is common for limited-time, promotional services and for resources belonging to individuals no longer working at the server's site. It is not necessary to mark all permanently unavailable resources as \"gone\" or to keep the mark for any length of time -- that is left to the discretion of the server owner.";
   public static final String SECTION_411 = "10.4.12 411 Length Required";
   public static final String MESSAGE_411 = "The server refuses to accept the request without a defined Content- Length. The client MAY repeat the request if it adds a valid Content-Length header field containing the length of the message-body in the request message.";
   public static final String SECTION_412 = "10.4.13 412 Precondition Failed";
   public static final String MESSAGE_412 = "The precondition given in one or more of the request-header fields evaluated to false when it was tested on the server. This response code allows the client to place preconditions on the current resource metainformation (header field data) and thus prevent the requested method from being applied to a resource other than the one intended.";
   public static final String SECTION_413 = "10.4.14 413 Request Entity Too Large";
   public static final String MESSAGE_413 = "The server is refusing to process a request because the request entity is larger than the server is willing or able to process. The server may close the connection to prevent the client from continuing the request.</p><p>If the condition is temporary, the server SHOULD include a Retry- After header field to indicate that it is temporary and after what time the client may try again.";
   public static final String SECTION_414 = "10.4.15 414 Request-URI Too Long";
   public static final String MESSAGE_414 = "The server is refusing to service the request because the Request-URI is longer than the server is willing to interpret. This rare condition is only likely to occur when a client has improperly converted a POST request to a GET request with long query information, when the client has descended into a URL \"black hole\" of redirection (e.g., a redirected URL prefix that points to a suffix of itself), or when the server is under attack by a client attempting to exploit security holes present in some servers using fixed-length buffers for reading or manipulating the Request-URI.";
   public static final String SECTION_415 = "10.4.16 415 Unsupported Media Type";
   public static final String MESSAGE_415 = "The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method.";
   public static final String SECTION_500 = "10.5.1 500 Internal Server Error";
   public static final String MESSAGE_500 = "The server encountered an unexpected condition which prevented it from fulfilling the request.";
   public static final String SECTION_501 = "10.5.2 501 Not Implemented";
   public static final String MESSAGE_501 = "The server does not support the functionality required to fulfill the request. This is the appropriate response when the server does not recognize the request method and is not capable of supporting it for any resource.";
   public static final String SECTION_502 = "10.5.3 502 Bad Gateway";
   public static final String MESSAGE_502 = "The server, while acting as a gateway or proxy, received an invalid response from the upstream server it accessed in attempting to fulfill the request.";
   public static final String SECTION_503 = "10.5.4 503 Service Unavailable";
   public static final String MESSAGE_503 = "The server is currently unable to handle the request due to a temporary overloading or maintenance of the server. The implication is that this is a temporary condition which will be alleviated after some delay. If known, the length of the delay may be indicated in a Retry-After header.  If no Retry-After is given, the client SHOULD handle the response as it would for a 500 response.<blockquote>Note: The existence of the 503 status code does not imply that a server must use it when becoming overloaded. Some servers may wish to simply refuse the connection.</blockquote>";
   public static final String SECTION_504 = "10.5.5 504 Gateway Timeout";
   public static final String MESSAGE_504 = "The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server it accessed in attempting to complete the request.";
   public static final String SECTION_505 = "10.5.6 505 HTTP Version Not Supported";
   public static final String MESSAGE_505 = "The server does not support, or refuses to support, the HTTP protocol version that was used in the request message. The server is indicating that it is unable or unwilling to complete the request using the same major version as the client, as described in section 3.1, other than with this error message. The response SHOULD contain an entity describing why that version is not supported and what other protocols are supported by that server.";

   public static final String getSection(int code) {
      switch (code) {
         case 400:
            return "10.4.1 400 Bad Request";
         case 401:
            return "10.4.2 401 Unauthorized";
         case 402:
            return "10.4.3 402 Payment Required";
         case 403:
            return "10.4.4 403 Forbidden";
         case 404:
            return "10.4.5 404 Not Found";
         case 405:
            return "10.4.6 405 Method Not Allowed";
         case 406:
            return "10.4.7 406 Not Acceptable";
         case 407:
            return "10.4.8 407 Proxy Authentication Required";
         case 408:
            return "10.4.9 408 Request Timeout";
         case 409:
            return "10.4.10 409 Conflict";
         case 410:
            return "10.4.11 410 Gone";
         case 411:
            return "10.4.12 411 Length Required";
         case 412:
            return "10.4.13 412 Precondition Failed";
         case 413:
            return "10.4.14 413 Request Entity Too Large";
         case 414:
            return "10.4.15 414 Request-URI Too Long";
         case 415:
            return "10.4.16 415 Unsupported Media Type";
         case 500:
            return "10.5.1 500 Internal Server Error";
         case 501:
            return "10.5.2 501 Not Implemented";
         case 502:
            return "10.5.3 502 Bad Gateway";
         case 503:
            return "10.5.4 503 Service Unavailable";
         case 504:
            return "10.5.5 504 Gateway Timeout";
         case 505:
            return "10.5.6 505 HTTP Version Not Supported";
         default:
            return "Unrecognized Error Code.";
      }
   }

   public static final String getMessage(int code) {
      switch (code) {
         case 400:
            return "The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.";
         case 401:
            return "The request requires user authentication. The response MUST include a WWW-Authenticate header field (section 14.46) containing a challenge applicable to the requested resource. The client MAY repeat the request with a suitable Authorization header field (section 14.8). If the request already included Authorization credentials, then the 401 response indicates that authorization has been refused for those credentials. If the 401 response contains the same challenge as the prior response, and the user agent has already attempted authentication at least once, then the user SHOULD be presented the entity that was given in the response, since that entity MAY include relevant diagnostic information. HTTP access authentication is explained in section 11.";
         case 402:
            return "This code is reserved for future use.";
         case 403:
            return "The server understood the request, but is refusing to fulfill it. Authorization will not help and the request SHOULD NOT be repeated. If the request method was not HEAD and the server wishes to make public why the request has not been fulfilled, it SHOULD describe the reason for the refusal in the entity. This status code is commonly used when the server does not wish to reveal exactly why the request has been refused, or when no other response is applicable.";
         case 404:
            return "The server has not found anything matching the Request-URI. No indication is given of whether the condition is temporary or permanent.</p><p>If the server does not wish to make this information available to the client, the status code 403 (Forbidden) can be used instead. The 410 (Gone) status code SHOULD be used if the server knows, through some internally configurable mechanism, that an old resource is permanently unavailable and has no forwarding address.";
         case 405:
            return "The method specified in the Request-Line is not allowed for the resource identified by the Request-URI. The response MUST include an Allow header containing a list of valid methods for the requested resource.";
         case 406:
            return "The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request.</p><p>Unless it was a HEAD request, the response SHOULD include an entity containing a list of available entity characteristics and location(s) from which the user or user agent can choose the one most appropriate.  The entity format is specified by the media type given in the Content-Type header field. Depending upon the format and the capabilities of the user agent, selection of the most appropriate choice may be performed automatically. However, this specification does not define any standard for such automatic selection.<blockquote>Note: HTTP/1.1 servers are allowed to return responses which are not acceptable according to the accept headers sent in the request. In some cases, this may even be preferable to sending a 406 response. User agents are encouraged to inspect the headers of an incoming response to determine if it is acceptable. If the response could be unacceptable, a user agent SHOULD temporarily stop receipt of more data and query the user for a decision on further actions.</blockquote>";
         case 407:
            return "This code is similar to 401 (Unauthorized), but indicates that the client MUST first authenticate itself with the proxy. The proxy MUST return a Proxy-Authenticate header field (section 14.33) containing a challenge applicable to the proxy for the requested resource. The client MAY repeat the request with a suitable Proxy-Authorization header field (section 14.34). HTTP access authentication is explained in section 11.";
         case 408:
            return "The client did not produce a request within the time that the server was prepared to wait. The client MAY repeat the request without modifications at any later time.";
         case 409:
            return "The request could not be completed due to a conflict with the current state of the resource. This code is only allowed in situations where it is expected that the user might be able to resolve the conflict and resubmit the request. The response body SHOULD include enough information for the user to recognize the source of the conflict. Ideally, the response entity would include enough information for the user or user agent to fix the problem; however, that may not be possible and is not required.</p><p>Conflicts are most likely to occur in response to a PUT request. If versioning is being used and the entity being PUT includes changes to a resource which conflict with those made by an earlier (third-party) request, the server MAY use the 409 response to indicate that it can't complete the request. In this case, the response entity SHOULD contain a list of the differences between the two versions in a format defined by the response Content-Type.";
         case 410:
            return "The requested resource is no longer available at the server and no forwarding address is known. This condition SHOULD be considered permanent. Clients with link editing capabilities SHOULD delete references to the Request-URI after user approval. If the server does not know, or has no facility to determine, whether or not the condition is permanent, the status code 404 (Not Found) SHOULD be used instead.  This response is cachable unless indicated otherwise.</p><p>The 410 response is primarily intended to assist the task of web maintenance by notifying the recipient that the resource is intentionally unavailable and that the server owners desire that remote links to that resource be removed. Such an event is common for limited-time, promotional services and for resources belonging to individuals no longer working at the server's site. It is not necessary to mark all permanently unavailable resources as \"gone\" or to keep the mark for any length of time -- that is left to the discretion of the server owner.";
         case 411:
            return "The server refuses to accept the request without a defined Content- Length. The client MAY repeat the request if it adds a valid Content-Length header field containing the length of the message-body in the request message.";
         case 412:
            return "The precondition given in one or more of the request-header fields evaluated to false when it was tested on the server. This response code allows the client to place preconditions on the current resource metainformation (header field data) and thus prevent the requested method from being applied to a resource other than the one intended.";
         case 413:
            return "The server is refusing to process a request because the request entity is larger than the server is willing or able to process. The server may close the connection to prevent the client from continuing the request.</p><p>If the condition is temporary, the server SHOULD include a Retry- After header field to indicate that it is temporary and after what time the client may try again.";
         case 414:
            return "The server is refusing to service the request because the Request-URI is longer than the server is willing to interpret. This rare condition is only likely to occur when a client has improperly converted a POST request to a GET request with long query information, when the client has descended into a URL \"black hole\" of redirection (e.g., a redirected URL prefix that points to a suffix of itself), or when the server is under attack by a client attempting to exploit security holes present in some servers using fixed-length buffers for reading or manipulating the Request-URI.";
         case 415:
            return "The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method.";
         case 500:
            return "The server encountered an unexpected condition which prevented it from fulfilling the request.";
         case 501:
            return "The server does not support the functionality required to fulfill the request. This is the appropriate response when the server does not recognize the request method and is not capable of supporting it for any resource.";
         case 502:
            return "The server, while acting as a gateway or proxy, received an invalid response from the upstream server it accessed in attempting to fulfill the request.";
         case 503:
            return "The server is currently unable to handle the request due to a temporary overloading or maintenance of the server. The implication is that this is a temporary condition which will be alleviated after some delay. If known, the length of the delay may be indicated in a Retry-After header.  If no Retry-After is given, the client SHOULD handle the response as it would for a 500 response.<blockquote>Note: The existence of the 503 status code does not imply that a server must use it when becoming overloaded. Some servers may wish to simply refuse the connection.</blockquote>";
         case 504:
            return "The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server it accessed in attempting to complete the request.";
         case 505:
            return "The server does not support, or refuses to support, the HTTP protocol version that was used in the request message. The server is indicating that it is unable or unwilling to complete the request using the same major version as the client, as described in section 3.1, other than with this error message. The response SHOULD contain an entity describing why that version is not supported and what other protocols are supported by that server.";
         default:
            return "Unrecognized Error Code.";
      }
   }

   public static String getErrorPage(int code) {
      String message = getExplanation(code);
      return getErrorPage(code, message);
   }

   public static String getErrorPage(int code, String message) {
      String title = "Error " + code + "--" + HttpReasonPhraseCoder.getReasonPhrase(code);
      String ret = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Draft//EN\">\n" + "<HTML>\n" + "<HEAD>\n" + "<TITLE>" + title + "</TITLE>\n" + "</HEAD>\n" + "<BODY bgcolor=\"white\">\n" + "<FONT FACE=Helvetica><BR CLEAR=all>\n" + "<TABLE border=0 cellspacing=5><TR><TD><BR CLEAR=all>\n" + "<FONT FACE=\"Helvetica\" COLOR=\"black\" SIZE=\"3\">" + "<H2>" + title + "</H2>\n" + "</FONT></TD></TR>\n" + "</TABLE>\n" + "<TABLE border=0 width=100% cellpadding=10><TR><TD VALIGN=top WIDTH=100% BGCOLOR=white><FONT FACE=\"Courier New\">" + message + "</FONT></TD></TR>\n" + "</TABLE>\n\n" + "</BODY>\n" + "</HTML>\n";
      return ret;
   }

   private static String getExplanation(int code) {
      String title = getSection(code);
      String message = getMessage(code);
      return "<FONT FACE=\"Helvetica\" SIZE=\"3\"><H3>From RFC 2068 <i>Hypertext Transfer Protocol -- HTTP/1.1</i>:</H3>\n" + "</FONT><FONT FACE=\"Helvetica\" SIZE=\"3\"><H4>" + title + "</H4>\n" + "</FONT><P><FONT FACE=\"Courier New\">" + message + "</FONT></P>\n";
   }
}
