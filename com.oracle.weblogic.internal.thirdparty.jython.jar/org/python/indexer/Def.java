package org.python.indexer;

import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NUrl;

public class Def {
   private int start;
   private int end;
   private NBinding binding;
   private String fileOrUrl;
   private String name;

   public Def(NNode node) {
      this(node, (NBinding)null);
   }

   public Def(NNode node, NBinding b) {
      if (node == null) {
         throw new IllegalArgumentException("null 'node' param");
      } else {
         this.binding = b;
         if (node instanceof NUrl) {
            String url = ((NUrl)node).getURL();
            if (url.startsWith("file://")) {
               this.fileOrUrl = url.substring("file://".length());
            } else {
               this.fileOrUrl = url;
            }

         } else {
            this.start = node.start();
            this.end = node.end();
            this.fileOrUrl = node.getFile();
            if (this.fileOrUrl == null) {
               throw new IllegalArgumentException("Non-URL nodes must have a non-null file");
            } else {
               if (node instanceof NName) {
                  this.name = ((NName)node).id;
               }

            }
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public String getFile() {
      return this.isURL() ? null : this.fileOrUrl;
   }

   public String getURL() {
      return this.isURL() ? this.fileOrUrl : null;
   }

   public String getFileOrUrl() {
      return this.fileOrUrl;
   }

   public boolean isURL() {
      return this.fileOrUrl.startsWith("http://");
   }

   public boolean isModule() {
      return this.binding != null && this.binding.kind == NBinding.Kind.MODULE;
   }

   public int start() {
      return this.start;
   }

   public int end() {
      return this.end;
   }

   public int length() {
      return this.end - this.start;
   }

   public boolean isName() {
      return this.name != null;
   }

   void setBinding(NBinding b) {
      this.binding = b;
   }

   public NBinding getBinding() {
      return this.binding;
   }

   public String toString() {
      return "<Def:" + (this.name == null ? "" : this.name) + ":" + this.start + ":" + this.fileOrUrl + ">";
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof Def)) {
         return false;
      } else {
         Def def = (Def)obj;
         if (this.start != def.start) {
            return false;
         } else if (this.end != def.end) {
            return false;
         } else {
            if (this.name != null) {
               if (!this.name.equals(def.name)) {
                  return false;
               }
            } else if (def.name != null) {
               return false;
            }

            if (this.fileOrUrl != null) {
               if (!this.fileOrUrl.equals(def.fileOrUrl)) {
                  return false;
               }
            } else if (def.fileOrUrl != null) {
               return false;
            }

            return true;
         }
      }
   }

   public int hashCode() {
      return ("" + this.fileOrUrl + this.name + this.start + this.end).hashCode();
   }
}
