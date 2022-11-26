package com.oracle.wls.shaded.org.apache.xalan.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/** @deprecated */
public abstract class SerializerFactory {
   private SerializerFactory() {
   }

   /** @deprecated */
   public static Serializer getSerializer(Properties format) {
      com.oracle.wls.shaded.org.apache.xml.serializer.Serializer ser = com.oracle.wls.shaded.org.apache.xml.serializer.SerializerFactory.getSerializer(format);
      SerializerWrapper si = new SerializerWrapper(ser);
      return si;
   }

   private static class DOMSerializerWrapper implements DOMSerializer {
      private final com.oracle.wls.shaded.org.apache.xml.serializer.DOMSerializer m_dom;

      DOMSerializerWrapper(com.oracle.wls.shaded.org.apache.xml.serializer.DOMSerializer domser) {
         this.m_dom = domser;
      }

      public void serialize(Node node) throws IOException {
         this.m_dom.serialize(node);
      }
   }

   /** @deprecated */
   private static class SerializerWrapper implements Serializer {
      private final com.oracle.wls.shaded.org.apache.xml.serializer.Serializer m_serializer;
      private DOMSerializer m_old_DOMSerializer;

      SerializerWrapper(com.oracle.wls.shaded.org.apache.xml.serializer.Serializer ser) {
         this.m_serializer = ser;
      }

      public void setOutputStream(OutputStream output) {
         this.m_serializer.setOutputStream(output);
      }

      public OutputStream getOutputStream() {
         return this.m_serializer.getOutputStream();
      }

      public void setWriter(Writer writer) {
         this.m_serializer.setWriter(writer);
      }

      public Writer getWriter() {
         return this.m_serializer.getWriter();
      }

      public void setOutputFormat(Properties format) {
         this.m_serializer.setOutputFormat(format);
      }

      public Properties getOutputFormat() {
         return this.m_serializer.getOutputFormat();
      }

      public ContentHandler asContentHandler() throws IOException {
         return this.m_serializer.asContentHandler();
      }

      public DOMSerializer asDOMSerializer() throws IOException {
         if (this.m_old_DOMSerializer == null) {
            this.m_old_DOMSerializer = new DOMSerializerWrapper(this.m_serializer.asDOMSerializer());
         }

         return this.m_old_DOMSerializer;
      }

      public boolean reset() {
         return this.m_serializer.reset();
      }
   }
}
