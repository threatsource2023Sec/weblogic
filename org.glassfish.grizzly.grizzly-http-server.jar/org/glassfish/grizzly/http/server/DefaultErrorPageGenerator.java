package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;

public class DefaultErrorPageGenerator implements ErrorPageGenerator {
   public String generate(Request request, int status, String reasonPhrase, String description, Throwable exception) {
      return status == 404 ? HtmlHelper.getErrorPage(HttpStatus.NOT_FOUND_404.getReasonPhrase(), "Resource identified by path '" + HttpUtils.filter(request.getRequestURI()) + "', does not exist.", request.getServerFilter().getFullServerName()) : HtmlHelper.getExceptionErrorPage(reasonPhrase, description, request.getServerFilter().getFullServerName(), exception);
   }
}
