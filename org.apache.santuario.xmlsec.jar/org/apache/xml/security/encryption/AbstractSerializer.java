package org.apache.xml.security.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractSerializer implements Serializer {
   protected Canonicalizer canon;
   protected boolean secureValidation;

   public void setCanonicalizer(Canonicalizer canon) {
      this.canon = canon;
   }

   public String serialize(Element element) throws Exception {
      return this.canonSerialize(element);
   }

   public byte[] serializeToByteArray(Element element) throws Exception {
      return this.canonSerializeToByteArray(element);
   }

   public String serialize(NodeList content) throws Exception {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Throwable var3 = null;

      try {
         this.canon.setSecureValidation(this.secureValidation);
         this.canon.setWriter(baos);
         this.canon.notReset();

         for(int i = 0; i < content.getLength(); ++i) {
            this.canon.canonicalizeSubtree(content.item(i));
         }

         String ret = baos.toString(StandardCharsets.UTF_8.name());
         baos.reset();
         String var5 = ret;
         return var5;
      } catch (Throwable var9) {
         var3 = var9;
         throw var9;
      } finally {
         $closeResource(var3, baos);
      }
   }

   public byte[] serializeToByteArray(NodeList content) throws Exception {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Throwable var3 = null;

      try {
         this.canon.setSecureValidation(this.secureValidation);
         this.canon.setWriter(baos);
         this.canon.notReset();

         for(int i = 0; i < content.getLength(); ++i) {
            this.canon.canonicalizeSubtree(content.item(i));
         }

         byte[] var10 = baos.toByteArray();
         return var10;
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         $closeResource(var3, baos);
      }
   }

   public String canonSerialize(Node node) throws Exception {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Throwable var3 = null;

      String var5;
      try {
         this.canon.setSecureValidation(this.secureValidation);
         this.canon.setWriter(baos);
         this.canon.notReset();
         this.canon.canonicalizeSubtree(node);
         String ret = baos.toString(StandardCharsets.UTF_8.name());
         baos.reset();
         var5 = ret;
      } catch (Throwable var9) {
         var3 = var9;
         throw var9;
      } finally {
         $closeResource(var3, baos);
      }

      return var5;
   }

   public byte[] canonSerializeToByteArray(Node node) throws Exception {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Throwable var3 = null;

      byte[] var4;
      try {
         this.canon.setSecureValidation(this.secureValidation);
         this.canon.setWriter(baos);
         this.canon.notReset();
         this.canon.canonicalizeSubtree(node);
         var4 = baos.toByteArray();
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         $closeResource(var3, baos);
      }

      return var4;
   }

   public abstract Node deserialize(String var1, Node var2) throws XMLEncryptionException;

   public abstract Node deserialize(byte[] var1, Node var2) throws XMLEncryptionException, IOException;

   protected static byte[] createContext(byte[] source, Node ctx) throws XMLEncryptionException {
      try {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         Throwable var3 = null;

         try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
            Map storedNamespaces = new HashMap();

            for(Node wk = ctx; wk != null; wk = wk.getParentNode()) {
               NamedNodeMap atts = wk.getAttributes();
               if (atts != null) {
                  for(int i = 0; i < atts.getLength(); ++i) {
                     Node att = atts.item(i);
                     String nodeName = att.getNodeName();
                     if (("xmlns".equals(nodeName) || nodeName.startsWith("xmlns:")) && !storedNamespaces.containsKey(att.getNodeName())) {
                        outputStreamWriter.write(" ");
                        outputStreamWriter.write(nodeName);
                        outputStreamWriter.write("=\"");
                        outputStreamWriter.write(att.getNodeValue());
                        outputStreamWriter.write("\"");
                        storedNamespaces.put(nodeName, att.getNodeValue());
                     }
                  }
               }
            }

            outputStreamWriter.write(">");
            outputStreamWriter.flush();
            byteArrayOutputStream.write(source);
            outputStreamWriter.write("</dummy>");
            outputStreamWriter.close();
            byte[] var20 = byteArrayOutputStream.toByteArray();
            return var20;
         } catch (Throwable var16) {
            var3 = var16;
            throw var16;
         } finally {
            $closeResource(var3, byteArrayOutputStream);
         }
      } catch (UnsupportedEncodingException var18) {
         throw new XMLEncryptionException(var18);
      } catch (IOException var19) {
         throw new XMLEncryptionException(var19);
      }
   }

   protected static String createContext(String source, Node ctx) {
      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
      Map storedNamespaces = new HashMap();

      for(Node wk = ctx; wk != null; wk = wk.getParentNode()) {
         NamedNodeMap atts = wk.getAttributes();
         if (atts != null) {
            for(int i = 0; i < atts.getLength(); ++i) {
               Node att = atts.item(i);
               String nodeName = att.getNodeName();
               if (("xmlns".equals(nodeName) || nodeName.startsWith("xmlns:")) && !storedNamespaces.containsKey(att.getNodeName())) {
                  sb.append(" ");
                  sb.append(nodeName);
                  sb.append("=\"");
                  sb.append(att.getNodeValue());
                  sb.append("\"");
                  storedNamespaces.put(nodeName, att.getNodeValue());
               }
            }
         }
      }

      sb.append(">");
      sb.append(source);
      sb.append("</dummy>");
      return sb.toString();
   }

   public boolean isSecureValidation() {
      return this.secureValidation;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
