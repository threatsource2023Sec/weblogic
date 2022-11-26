package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.util.IntStack;
import com.bea.xbean.piccolo.util.StringStack;

public class FastNamespaceSupport {
   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
   private String[] prefixes = new String[20];
   private String[] uris = new String[20];
   private int prefixPos;
   private String defaultURI;
   private StringStack defaultURIs = new StringStack(20);
   private int prefixCount;
   private IntStack contextPrefixCounts = new IntStack(20);
   private int defaultURIContexts;
   private IntStack defaultURIContextCounts = new IntStack(20);

   public FastNamespaceSupport() {
      this.reset();
   }

   public void reset() {
      this.defaultURIs.clear();
      this.contextPrefixCounts.clear();
      this.defaultURIContextCounts.clear();
      this.prefixPos = -1;
      this.defaultURI = "";
      this.prefixCount = 0;
      this.defaultURIContexts = 0;
   }

   public void pushContext() {
      ++this.defaultURIContexts;
      this.contextPrefixCounts.push(this.prefixCount);
      this.prefixCount = 0;
   }

   public void popContext() {
      if (this.defaultURIContexts <= 0) {
         this.defaultURIContexts = this.defaultURIContextCounts.pop();
         this.defaultURI = this.defaultURIs.pop();
      } else {
         --this.defaultURIContexts;
      }

      this.prefixPos -= this.prefixCount;
      this.prefixCount = this.contextPrefixCounts.pop();
   }

   public void declarePrefix(String prefix, String uri) {
      if (prefix.length() == 0) {
         --this.defaultURIContexts;
         this.defaultURIContextCounts.push(this.defaultURIContexts);
         this.defaultURIs.push(this.defaultURI);
         this.defaultURIContexts = 0;
         this.defaultURI = uri;
      } else {
         int oldLength;
         for(oldLength = 0; oldLength < this.prefixCount; ++oldLength) {
            if (prefix == this.prefixes[this.prefixPos - oldLength]) {
               this.uris[this.prefixPos - oldLength] = uri;
               return;
            }
         }

         ++this.prefixPos;
         ++this.prefixCount;
         if (this.prefixPos >= this.prefixes.length) {
            oldLength = this.prefixes.length;
            int newLength = oldLength * 2;
            String[] newPrefixes = new String[newLength];
            String[] newURIs = new String[newLength];
            System.arraycopy(this.prefixes, 0, newPrefixes, 0, oldLength);
            System.arraycopy(this.uris, 0, newURIs, 0, oldLength);
            this.prefixes = newPrefixes;
            this.uris = newURIs;
         }

         this.prefixes[this.prefixPos] = prefix;
         this.uris[this.prefixPos] = uri;
      }

   }

   public String[] processName(String qName, String[] parts, boolean isAttribute) {
      int colon = qName.indexOf(58);
      parts[2] = qName;
      if (colon < 0) {
         parts[1] = qName;
         if (isAttribute) {
            parts[0] = "";
         } else {
            parts[0] = this.defaultURI;
         }

         return parts;
      } else {
         String prefix = qName.substring(0, colon);
         parts[1] = qName.substring(colon + 1);
         return (parts[0] = this.getURI(prefix)) == "" ? null : parts;
      }
   }

   public String getDefaultURI() {
      return this.defaultURI;
   }

   public String getURI(String prefix) {
      if (prefix != null && prefix.length() != 0) {
         if (prefix == "xml") {
            return "http://www.w3.org/XML/1998/namespace";
         } else {
            for(int i = this.prefixPos; i >= 0; --i) {
               if (prefix == this.prefixes[i]) {
                  return this.uris[i];
               }
            }

            return "";
         }
      } else {
         return this.defaultURI;
      }
   }

   public int getContextSize() {
      return this.prefixCount + (this.defaultURIContexts == 0 && this.defaultURI != "" ? 1 : 0);
   }

   public String getContextPrefix(int index) {
      return index == this.prefixCount && this.defaultURIContexts == 0 && this.defaultURI != "" ? "" : this.prefixes[this.prefixPos - index];
   }

   public String getContextURI(int index) {
      return index == this.prefixCount && this.defaultURIContexts == 0 && this.defaultURI != "" ? this.defaultURI : this.uris[this.prefixPos - index];
   }
}
