package com.bea.xml.stream;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class ReadOnlyNamespaceContextBase implements NamespaceContext {
   private String[] prefixes;
   private String[] uris;

   public ReadOnlyNamespaceContextBase(String[] prefixArray, String[] uriArray, int size) {
      this.prefixes = new String[size];
      this.uris = new String[size];
      System.arraycopy(prefixArray, 0, this.prefixes, 0, this.prefixes.length);
      System.arraycopy(uriArray, 0, this.uris, 0, this.uris.length);
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("Prefix may not be null.");
      } else {
         int i;
         if (!"".equals(prefix)) {
            for(i = this.uris.length - 1; i >= 0; --i) {
               if (prefix.equals(this.prefixes[i])) {
                  return this.uris[i];
               }
            }

            if ("xml".equals(prefix)) {
               return "http://www.w3.org/XML/1998/namespace";
            }

            if ("xmlns".equals(prefix)) {
               return "http://www.w3.org/2000/xmlns/";
            }
         } else {
            for(i = this.uris.length - 1; i >= 0; --i) {
               if (this.prefixes[i] == null) {
                  return this.uris[i];
               }
            }
         }

         return null;
      }
   }

   public String getPrefix(String uri) {
      if (uri == null) {
         throw new IllegalArgumentException("uri may not be null");
      } else if ("".equals(uri)) {
         throw new IllegalArgumentException("uri may not be empty string");
      } else {
         if (uri != null) {
            for(int i = this.uris.length - 1; i >= 0; --i) {
               if (uri.equals(this.uris[i])) {
                  return this.checkNull(this.prefixes[i]);
               }
            }
         }

         return null;
      }
   }

   public String getDefaultNameSpace() {
      for(int i = this.uris.length - 1; i >= 0; --i) {
         if (this.prefixes[i] == null) {
            return this.uris[i];
         }
      }

      return null;
   }

   private String checkNull(String s) {
      return s == null ? "" : s;
   }

   public Iterator getPrefixes(String uri) {
      if (uri == null) {
         throw new IllegalArgumentException("uri may not be null");
      } else if ("".equals(uri)) {
         throw new IllegalArgumentException("uri may not be empty string");
      } else {
         HashSet s = new HashSet();

         for(int i = this.uris.length - 1; i >= 0; --i) {
            String prefix = this.checkNull(this.prefixes[i]);
            if (uri.equals(this.uris[i]) && !s.contains(prefix)) {
               s.add(prefix);
            }
         }

         return s.iterator();
      }
   }

   public String toString() {
      StringBuffer b = new StringBuffer();

      for(int i = 0; i < this.uris.length; ++i) {
         b.append("[" + this.checkNull(this.prefixes[i]) + "<->" + this.uris[i] + "]");
      }

      return b.toString();
   }

   public static void main(String[] args) throws Exception {
      MXParser p = new MXParser();
      p.setInput((Reader)(new FileReader(args[0])));

      for(; p.hasNext(); p.next()) {
         if (p.isStartElement()) {
            System.out.println("context[" + p.getNamespaceContext() + "]");
            Iterator i = p.getNamespaceContext().getPrefixes("a");

            while(i.hasNext()) {
               System.out.println("Found prefix:" + i.next());
            }
         }
      }

   }
}
