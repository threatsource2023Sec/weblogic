package org.glassfish.grizzly.http.server.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;

public class HtmlHelper {
   private static final int MAX_STACK_ELEMENTS = 10;
   private static final String CSS = "div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}";

   public static void sendErrorPage(Request request, Response response, ErrorPageGenerator generator, int status, String reasonPhrase, String description, Throwable exception) throws IOException {
      if (generator != null && !response.isCommitted() && response.getOutputBuffer().getBufferedDataSize() == 0) {
         String errorPage = generator.generate(request, status, reasonPhrase, description, exception);
         Writer writer = response.getWriter();
         if (errorPage != null) {
            if (!response.getResponse().isContentTypeSet()) {
               response.setContentType("text/html");
            }

            writer.write(errorPage);
         }

         writer.close();
      }

   }

   public static void setErrorAndSendErrorPage(Request request, Response response, ErrorPageGenerator generator, int status, String reasonPhrase, String description, Throwable exception) throws IOException {
      response.setStatus(status, reasonPhrase);
      if (generator != null && !response.isCommitted() && response.getOutputBuffer().getBufferedDataSize() == 0) {
         String errorPage = generator.generate(request, status, reasonPhrase, description, exception);
         Writer writer = response.getWriter();
         if (errorPage != null) {
            if (!response.getResponse().isContentTypeSet()) {
               response.setContentType("text/html");
            }

            writer.write(errorPage);
         }

         writer.close();
      }

   }

   public static void writeTraceMessage(Request request, Response response) throws IOException {
      response.setStatus(HttpStatus.OK_200);
      response.setContentType("message/http");
      Writer writer = response.getWriter();
      writer.append(request.getMethod().toString()).append(' ').append(request.getRequest().getRequestURIRef().getOriginalRequestURIBC().toString()).append(' ').append(request.getProtocol().getProtocolString()).append("\r\n");
      Iterator var3 = request.getHeaderNames().iterator();

      while(var3.hasNext()) {
         String headerName = (String)var3.next();
         Iterator var5 = request.getHeaders(headerName).iterator();

         while(var5.hasNext()) {
            String headerValue = (String)var5.next();
            writer.append(headerName).append(": ").append(headerValue).append("\r\n");
         }
      }

   }

   public static String getErrorPage(String headerMessage, String message, String serverName) {
      return prepareBody(headerMessage, message, serverName);
   }

   public static String getExceptionErrorPage(String headerMessage, String message, String serverName, Throwable t) {
      return prepareExceptionBody(headerMessage, message, serverName, t);
   }

   private static String prepareBody(String headerMessage, String message, String serverName) {
      StringBuilder sb = new StringBuilder();
      sb.append("<html><head><title>");
      sb.append(serverName);
      sb.append("</title>");
      sb.append("<style><!--");
      sb.append("div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}");
      sb.append("--></style> ");
      sb.append("</head><body>");
      sb.append("<div class=\"header\">");
      sb.append(headerMessage);
      sb.append("</div>");
      sb.append("<div class=\"body\">");
      sb.append(message != null ? message : "<HR size=\"1\" noshade>");
      sb.append("</div>");
      sb.append("<div class=\"footer\">").append(serverName).append("</div>");
      sb.append("</body></html>");
      return sb.toString();
   }

   private static String prepareExceptionBody(String headerMessage, String message, String serverName, Throwable t) {
      if (t == null) {
         return prepareBody(headerMessage, message, serverName);
      } else {
         Throwable rootCause = getRootCause(t);
         StackTraceElement[] elements = t.getStackTrace();
         StackTraceElement[] rootCauseElements = null;
         if (rootCause != null) {
            rootCauseElements = rootCause.getStackTrace();
         }

         StringBuilder tBuilder = new StringBuilder();
         formatStackElements(elements, tBuilder);
         StringBuilder rootBuilder = new StringBuilder();
         if (rootCause != null) {
            formatStackElements(rootCauseElements, rootBuilder);
         }

         String exMessage = t.getMessage() != null ? HttpUtils.filter(t.getMessage()) : HttpUtils.filter(t.toString());
         StringBuilder sb = new StringBuilder();
         sb.append("<html><head><title>");
         sb.append(serverName);
         sb.append("</title>");
         sb.append("<style><!--");
         sb.append("div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}");
         sb.append("--></style> ");
         sb.append("</head><body>");
         sb.append("<div class=\"header\">");
         sb.append(headerMessage);
         sb.append("</div>");
         sb.append("<div class=\"body\">");
         sb.append("<b>").append(exMessage).append("</b>");
         sb.append("<pre>");
         sb.append(tBuilder.toString());
         sb.append("</pre>");
         if (rootCause != null) {
            sb.append("<b>Root Cause: ").append(rootCause.toString()).append("</b>");
            sb.append("<pre>");
            sb.append(rootBuilder.toString());
            sb.append("</pre>");
         }

         sb.append("Please see the log for more detail.");
         sb.append("</div>");
         sb.append("<div class=\"footer\">").append(serverName).append("</div>");
         sb.append("</body></html>");
         return sb.toString();
      }
   }

   private static Throwable getRootCause(Throwable t) {
      Throwable rootCause = null;
      if (t.getCause() != null) {
         for(rootCause = t.getCause(); rootCause.getCause() != null; rootCause = rootCause.getCause()) {
         }
      }

      return rootCause;
   }

   private static void formatStackElements(StackTraceElement[] elements, StringBuilder builder) {
      int maxLines = getMaxStackElementsToDisplay(elements);

      for(int i = 0; i < maxLines; ++i) {
         builder.append(i + 1 > 9 ? "    " : "     ").append(i + 1).append(": ").append(elements[i].toString()).append('\n');
      }

      boolean ellipse = elements.length > 10;
      if (ellipse) {
         builder.append("        ... ").append(elements.length - 10).append(" more");
      }

   }

   private static int getMaxStackElementsToDisplay(StackTraceElement[] elements) {
      return elements.length > 10 ? 10 : elements.length;
   }
}
