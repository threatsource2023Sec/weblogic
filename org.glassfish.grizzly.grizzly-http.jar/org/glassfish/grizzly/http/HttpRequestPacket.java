package org.glassfish.grizzly.http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.DefaultAttributeBuilder;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.http.util.RequestURIRef;

public abstract class HttpRequestPacket extends HttpHeader {
   public static final String READ_ONLY_ATTR_PREFIX = "@RoA.";
   private static final AttributeBuilder ATTR_BUILDER = new DefaultAttributeBuilder();
   private Connection connection;
   private HttpResponsePacket response;
   private int serverPort = -1;
   protected int remotePort = -1;
   protected int localPort = -1;
   private final RequestURIRef requestURIRef = new RequestURIRef();
   private String localHost;
   private final DataChunk methodC = DataChunk.newInstance();
   protected Method parsedMethod;
   private final DataChunk queryC = DataChunk.newInstance();
   protected final DataChunk remoteAddressC = DataChunk.newInstance();
   protected final DataChunk remoteHostC = DataChunk.newInstance();
   protected final DataChunk localNameC = DataChunk.newInstance();
   protected final DataChunk localAddressC = DataChunk.newInstance();
   private final DataChunk serverNameC = DataChunk.newInstance();
   private final DataChunk authTypeC = DataChunk.newInstance();
   private final DataChunk remoteUserC = DataChunk.newInstance();
   private boolean requiresAcknowledgement;
   protected DataChunk unparsedHostC;
   private boolean hostHeaderParsed;
   private final transient AttributeHolder notesHolder;
   protected final Map attributes;

   public static Builder builder() {
      return new Builder();
   }

   protected HttpRequestPacket() {
      this.notesHolder = ATTR_BUILDER.createUnsafeAttributeHolder();
      this.attributes = new HashMap();
      this.setMethod(Method.GET);
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public HttpResponsePacket getResponse() {
      return this.response;
   }

   public DataChunk getMethodDC() {
      this.parsedMethod = null;
      return this.methodC;
   }

   public Method getMethod() {
      if (this.parsedMethod != null) {
         return this.parsedMethod;
      } else {
         this.parsedMethod = Method.valueOf(this.methodC);
         return this.parsedMethod;
      }
   }

   public void setMethod(String method) {
      this.methodC.setString(method);
      this.parsedMethod = null;
   }

   public void setMethod(Method method) {
      this.methodC.setString(method.getMethodString());
      this.parsedMethod = method;
   }

   public RequestURIRef getRequestURIRef() {
      return this.requestURIRef;
   }

   public String getRequestURI() {
      return this.requestURIRef.getURI();
   }

   public void setRequestURI(String requestURI) {
      this.requestURIRef.setURI(requestURI);
   }

   public DataChunk getQueryStringDC() {
      return this.queryC;
   }

   public String getQueryString() {
      return this.queryC.isNull() ? null : this.queryC.toString();
   }

   public void setQueryString(String query) {
      this.queryC.setString(query);
   }

   protected DataChunk serverNameRaw() {
      return this.serverNameC;
   }

   public DataChunk serverName() {
      this.parseHostHeader();
      return this.serverNameC;
   }

   public int getServerPort() {
      this.parseHostHeader();
      return this.serverPort;
   }

   public void setServerPort(int serverPort) {
      this.serverPort = serverPort;
   }

   public DataChunk remoteAddr() {
      if (this.remoteAddressC.isNull()) {
         this.remoteAddressC.setString(((InetSocketAddress)this.connection.getPeerAddress()).getAddress().getHostAddress());
      }

      return this.remoteAddressC;
   }

   public String getRemoteAddress() {
      return this.remoteAddr().toString();
   }

   public DataChunk remoteHost() {
      if (this.remoteHostC.isNull()) {
         String remoteHost = null;
         InetAddress inetAddr = ((InetSocketAddress)this.connection.getPeerAddress()).getAddress();
         if (inetAddr != null) {
            remoteHost = inetAddr.getHostName();
         }

         if (remoteHost == null) {
            if (!this.remoteAddressC.isNull()) {
               remoteHost = this.remoteAddressC.toString();
            } else {
               this.remoteHostC.recycle();
            }
         }

         this.remoteHostC.setString(remoteHost);
      }

      return this.remoteHostC;
   }

   public String getRemoteHost() {
      return this.remoteHost().toString();
   }

   protected void requiresAcknowledgement(boolean requiresAcknowledgement) {
      this.requiresAcknowledgement = requiresAcknowledgement;
   }

   public boolean requiresAcknowledgement() {
      return this.requiresAcknowledgement;
   }

   public DataChunk localName() {
      if (this.localNameC.isNull()) {
         InetAddress inetAddr = ((InetSocketAddress)this.connection.getLocalAddress()).getAddress();
         this.localNameC.setString(inetAddr.getHostName());
      }

      return this.localNameC;
   }

   public String getLocalName() {
      return this.localName().toString();
   }

   public DataChunk localAddr() {
      if (this.localAddressC.isNull()) {
         InetAddress inetAddr = ((InetSocketAddress)this.connection.getLocalAddress()).getAddress();
         this.localAddressC.setString(inetAddr.getHostAddress());
      }

      return this.localAddressC;
   }

   public String getLocalAddress() {
      return this.localAddr().toString();
   }

   public int getRemotePort() {
      if (this.remotePort == -1) {
         this.remotePort = ((InetSocketAddress)this.connection.getPeerAddress()).getPort();
      }

      return this.remotePort;
   }

   public void setRemotePort(int port) {
      this.remotePort = port;
   }

   public int getLocalPort() {
      if (this.localPort == -1) {
         this.localPort = ((InetSocketAddress)this.connection.getLocalAddress()).getPort();
      }

      return this.localPort;
   }

   public void setLocalPort(int port) {
      this.localPort = port;
   }

   public String getLocalHost() {
      return this.localHost;
   }

   public void setLocalHost(String host) {
      this.localHost = host;
   }

   public DataChunk authType() {
      return this.authTypeC;
   }

   public DataChunk remoteUser() {
      return this.remoteUserC;
   }

   public static Note createNote(String name) {
      return new Note(ATTR_BUILDER.createAttribute(name));
   }

   public Object getNote(Note note) {
      return note.attribute.get(this.notesHolder);
   }

   public Set getNoteNames() {
      return this.notesHolder.getAttributeNames();
   }

   public Object removeNote(Note note) {
      return note.attribute.remove(this.notesHolder);
   }

   public void setNote(Note note, Object value) {
      note.attribute.set(this.notesHolder, value);
   }

   public Object getAttribute(String name) {
      return this.attributes.get(name);
   }

   public Set getAttributeNames() {
      Set attrNames = new HashSet(this.attributes.size());
      Iterator var2 = this.attributes.keySet().iterator();

      while(true) {
         String name;
         do {
            if (!var2.hasNext()) {
               return Collections.unmodifiableSet(attrNames);
            }

            name = (String)var2.next();
         } while(name != null && name.startsWith("@RoA."));

         attrNames.add(name);
      }
   }

   public void setAttribute(String name, Object value) {
      Object oldValue = this.attributes.put(name, value);
      if (oldValue != null && name != null && name.startsWith("@RoA.")) {
         this.attributes.put(name, oldValue);
      }

   }

   public void removeAttribute(String name) {
      if (name == null || !name.startsWith("@RoA.")) {
         this.attributes.remove(name);
      }

   }

   public boolean isHeadRequest() {
      return Method.HEAD.equals(this.getMethod());
   }

   protected void reset() {
      this.requestURIRef.recycle();
      this.queryC.recycle();
      this.methodC.recycle();
      this.parsedMethod = null;
      this.hostHeaderParsed = false;
      this.unparsedHostC = null;
      this.remoteAddressC.recycle();
      this.remoteHostC.recycle();
      this.localAddressC.recycle();
      this.localNameC.recycle();
      this.serverNameC.recycle();
      this.authTypeC.recycle();
      this.remoteUserC.recycle();
      this.attributes.clear();
      this.requiresAcknowledgement = false;
      this.remotePort = -1;
      this.localPort = -1;
      this.serverPort = -1;
      this.connection = null;
      this.localHost = null;
      this.response = null;
      super.reset();
   }

   public final boolean isRequest() {
      return true;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(256);
      sb.append("HttpRequestPacket (\n   method=").append(this.getMethod()).append("\n   url=").append(this.getRequestURI()).append("\n   query=").append(this.getQueryString()).append("\n   protocol=").append(this.getProtocol().getProtocolString()).append("\n   content-length=").append(this.getContentLength()).append("\n   headers=[");
      MimeHeaders headersLocal = this.getHeaders();
      Iterator var3 = headersLocal.names().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Iterator var5 = headersLocal.values(name).iterator();

         while(var5.hasNext()) {
            String value = (String)var5.next();
            sb.append("\n      ").append(name).append('=').append(value);
         }
      }

      sb.append("]\n)");
      return sb.toString();
   }

   protected void setResponse(HttpResponsePacket response) {
      this.response = response;
   }

   private void parseHostHeader() {
      if (!this.hostHeaderParsed) {
         this.doParseHostHeader();
         this.hostHeaderParsed = true;
      }

   }

   protected void doParseHostHeader() {
      HttpCodecUtils.parseHost(this.unparsedHostC, this.serverNameC, this);
   }

   public static class Builder extends HttpHeader.Builder {
      protected Method method;
      protected String methodString;
      protected String uri;
      protected String queryString;
      protected String host;

      public Builder method(Method method) {
         this.method = method;
         this.methodString = null;
         return this;
      }

      public Builder method(String method) {
         this.methodString = method;
         this.method = null;
         return this;
      }

      public Builder uri(String uri) {
         this.uri = uri;
         return this;
      }

      public Builder host(String host) {
         this.host = host;
         return this;
      }

      public Builder query(String queryString) {
         this.queryString = queryString;
         return this;
      }

      public final HttpRequestPacket build() {
         HttpRequestPacket packet = (HttpRequestPacket)super.build();
         if (this.method != null) {
            packet.setMethod(this.method);
         }

         if (this.methodString != null) {
            packet.setMethod(this.methodString);
         }

         if (this.uri != null) {
            packet.setRequestURI(this.uri);
         }

         if (this.queryString != null) {
            packet.setQueryString(this.queryString);
         }

         if (this.host != null) {
            packet.addHeader(Header.Host, this.host);
         }

         return packet;
      }

      public void reset() {
         super.reset();
         this.method = null;
         this.uri = null;
         this.queryString = null;
      }

      protected HttpHeader create() {
         return HttpRequestPacketImpl.create();
      }
   }
}
