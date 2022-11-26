package com.bea.common.security.xacml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;

public abstract class SchemaObject implements Serializable {
   private transient int hash = 0;

   public void encode(OutputStream out) {
      this.encode(new HashMap(), out);
   }

   public void encode(Map nsMap, OutputStream out) {
      boolean topLevel = nsMap.isEmpty();
      String ns = this.getNamespace();
      boolean writeNS = !nsMap.containsKey(ns);
      String prefix;
      if (writeNS) {
         prefix = topLevel ? null : this.getDesiredNamespacePrefix();
         nsMap.put(ns, prefix);
      } else {
         prefix = (String)nsMap.get(ns);
      }

      PrintStream ps = this.getPrintStream(out);
      if (topLevel) {
         ps.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
         ps.println();
      }

      ps.print('<');
      if (prefix != null) {
         ps.print(prefix);
         ps.print(':');
      }

      ps.print(this.getElementName());
      if (writeNS) {
         ps.print(" xmlns");
         if (prefix != null) {
            ps.print(':');
            ps.print(prefix);
         }

         ps.print("=\"");
         ps.print(ns);
         ps.print('"');
      }

      this.encodeAttributes(ps);
      if (!this.hasChildren() && !this.hasBody()) {
         ps.print("/>");
      } else {
         ps.print('>');
         if (this.hasChildren()) {
            this.encodeChildren(nsMap, ps);
         }

         if (this.hasBody()) {
            this.encodeBody(ps);
         }

         ps.print("</");
         if (prefix != null) {
            ps.print(prefix);
            ps.print(':');
         }

         ps.print(this.getElementName());
         ps.print('>');
      }

      if (topLevel) {
         ps.flush();
      }

   }

   public abstract String getElementName();

   public void encodeAttributes(PrintStream ps) {
   }

   public boolean hasChildren() {
      return false;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
   }

   public boolean hasBody() {
      return false;
   }

   public void encodeBody(PrintStream ps) {
   }

   public abstract String getNamespace();

   public abstract String getDesiredNamespacePrefix();

   public int hashCode() {
      if (this.hash == 0) {
         this.hash = this.internalHashCode();
      }

      return this.hash;
   }

   public abstract int internalHashCode();

   protected PrintStream getPrintStream(OutputStream out) {
      if (out instanceof PrintStream) {
         return (PrintStream)out;
      } else {
         PrintStream out;
         try {
            out = new PrintStream(out, false, "UTF8");
         } catch (UnsupportedEncodingException var3) {
            out = new PrintStream(out);
         }

         return (PrintStream)out;
      }
   }

   public String toString() {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      this.encode(os);

      try {
         return new String(os.toByteArray(), "UTF8");
      } catch (UnsupportedEncodingException var3) {
         return new String(os.toByteArray());
      }
   }

   protected String getLocalName(Node node) {
      String name = node.getLocalName();
      if (name == null) {
         name = node.getNodeName();
         if (name != null) {
            int idx = name.indexOf(58);
            if (idx >= 0) {
               return name.substring(idx + 1);
            }
         }
      }

      return name;
   }

   protected String escapeXML(String string) {
      if (string != null && string.trim().length() != 0) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < string.length(); ++i) {
            switch (string.charAt(i)) {
               case '"':
                  sb.append("&quot;");
                  break;
               case '&':
                  sb.append("&amp;");
                  break;
               case '\'':
                  sb.append("&apos;");
                  break;
               case '<':
                  sb.append("&lt;");
                  break;
               case '>':
                  sb.append("&gt;");
                  break;
               default:
                  sb.append(string.charAt(i));
            }
         }

         return sb.toString();
      } else {
         return string;
      }
   }
}
