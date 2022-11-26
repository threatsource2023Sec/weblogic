package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.OutputSink;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public class HttpContext implements AttributeStorage {
   private static final Attribute HTTP_CONTEXT_ATTR;
   private final AttributeStorage contextStorage;
   private final OutputSink outputSink;
   private final Closeable closeable;
   private final HttpRequestPacket request;

   protected HttpContext(AttributeStorage attributeStorage, OutputSink outputSink, Closeable closeable, HttpRequestPacket request) {
      this.contextStorage = attributeStorage;
      this.closeable = closeable;
      this.outputSink = outputSink;
      this.request = request;
   }

   public HttpRequestPacket getRequest() {
      return this.request;
   }

   public HttpContext attach(FilterChainContext ctx) {
      HTTP_CONTEXT_ATTR.set(ctx, this);
      return this;
   }

   public final AttributeHolder getAttributes() {
      return this.contextStorage.getAttributes();
   }

   public AttributeStorage getContextStorage() {
      return this.contextStorage;
   }

   public OutputSink getOutputSink() {
      return this.outputSink;
   }

   public Closeable getCloseable() {
      return this.closeable;
   }

   public void close() {
      this.closeable.closeSilently();
   }

   public static HttpContext newInstance(AttributeStorage attributeStorage, OutputSink outputSink, Closeable closeable, HttpRequestPacket request) {
      return new HttpContext(attributeStorage, outputSink, closeable, request);
   }

   public static HttpContext get(FilterChainContext ctx) {
      return (HttpContext)HTTP_CONTEXT_ATTR.get(ctx);
   }

   static {
      HTTP_CONTEXT_ATTR = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(HttpContext.class.getName());
   }
}
