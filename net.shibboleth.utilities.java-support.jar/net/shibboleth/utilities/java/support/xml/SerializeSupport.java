package net.shibboleth.utilities.java.support.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

public final class SerializeSupport {
   private static Map prettyPrintParams = new LazyMap();

   private SerializeSupport() {
   }

   @Nonnull
   public static String nodeToString(@Nonnull Node node) {
      return nodeToString(node, (Map)null);
   }

   @Nonnull
   public static String nodeToString(@Nonnull Node node, @Nullable Map serializerParams) {
      Constraint.isNotNull(node, "Node may not be null");
      ByteArrayOutputStream baout = new ByteArrayOutputStream();
      writeNode(node, baout, serializerParams);

      try {
         return new String(baout.toByteArray(), "UTF-8");
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException(var4);
      }
   }

   @Nonnull
   public static String prettyPrintXML(@Nonnull Node node) {
      Constraint.isNotNull(node, "Node may not be null");
      ByteArrayOutputStream baout = new ByteArrayOutputStream();
      writeNode(node, baout, prettyPrintParams);

      try {
         return new String(baout.toByteArray(), "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         throw new RuntimeException(var3);
      }
   }

   public static void writeNode(@Nonnull Node node, @Nonnull OutputStream output) {
      writeNode(node, output, (Map)null);
   }

   public static void writeNode(@Nonnull Node node, @Nonnull OutputStream output, @Nullable Map serializerParams) {
      Constraint.isNotNull(node, "Node may not be null");
      Constraint.isNotNull(output, "Outputstream may not be null");
      DOMImplementationLS domImplLS = getDOMImplementationLS(node);
      LSSerializer serializer = getLSSerializer(domImplLS, serializerParams);
      LSOutput serializerOut = domImplLS.createLSOutput();
      serializerOut.setByteStream(output);
      serializer.write(node, serializerOut);
   }

   @Nonnull
   public static LSSerializer getLSSerializer(@Nonnull DOMImplementationLS domImplLS, @Nullable Map serializerParams) {
      Constraint.isNotNull(domImplLS, "DOM implementation can not be null");
      LSSerializer serializer = domImplLS.createLSSerializer();
      serializer.setFilter(new LSSerializerFilter() {
         public short acceptNode(Node arg0) {
            return 1;
         }

         public int getWhatToShow() {
            return -1;
         }
      });
      if (serializerParams != null) {
         DOMConfiguration serializerDOMConfig = serializer.getDomConfig();
         Iterator i$ = serializerParams.keySet().iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            serializerDOMConfig.setParameter(key, serializerParams.get(key));
         }
      }

      return serializer;
   }

   @Nonnull
   public static DOMImplementationLS getDOMImplementationLS(@Nonnull Node node) {
      Constraint.isNotNull(node, "DOM node can not be null");
      DOMImplementation domImpl;
      if (node instanceof Document) {
         domImpl = ((Document)node).getImplementation();
      } else {
         domImpl = node.getOwnerDocument().getImplementation();
      }

      return (DOMImplementationLS)domImpl.getFeature("LS", "3.0");
   }

   static {
      prettyPrintParams.put("format-pretty-print", Boolean.TRUE);
   }
}
