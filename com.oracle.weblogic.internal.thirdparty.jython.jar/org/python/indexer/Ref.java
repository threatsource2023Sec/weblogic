package org.python.indexer;

import org.python.indexer.ast.NAttribute;
import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NStr;

public class Ref {
   private static final int ATTRIBUTE = 1;
   private static final int CALL = 2;
   private static final int NEW = 4;
   private static final int STRING = 8;
   private int start;
   private String file;
   private String name;
   private int flags;

   public Ref(NNode node) {
      if (node == null) {
         throw new IllegalArgumentException("null node");
      } else {
         this.file = node.getFile();
         this.start = node.start();
         if (node instanceof NName) {
            NName nn = (NName)node;
            this.name = nn.id;
            if (nn.isCall()) {
               this.markAsCall();
            }
         } else {
            if (!(node instanceof NStr)) {
               throw new IllegalArgumentException("I don't know what " + node + " is.");
            }

            this.markAsString();
            this.name = ((NStr)node).n.toString();
         }

         NNode parent = node.getParent();
         if (parent instanceof NAttribute && node == ((NAttribute)parent).attr) {
            this.markAsAttribute();
         }

      }
   }

   public Ref(String path, int offset, String text) {
      if (path == null) {
         throw new IllegalArgumentException("'path' cannot be null");
      } else if (text == null) {
         throw new IllegalArgumentException("'text' cannot be null");
      } else {
         this.file = path;
         this.start = offset;
         this.name = text;
      }
   }

   public String getFile() {
      return this.file;
   }

   public String getName() {
      return this.name;
   }

   public int start() {
      return this.start;
   }

   public int end() {
      return this.start + this.length();
   }

   public int length() {
      return this.isString() ? this.name.length() + 2 : this.name.length();
   }

   public boolean isName() {
      return !this.isString();
   }

   public boolean isAttribute() {
      return (this.flags & 1) != 0;
   }

   public void markAsAttribute() {
      this.flags |= 1;
   }

   public boolean isString() {
      return (this.flags & 8) != 0;
   }

   public void markAsString() {
      this.flags |= 8;
   }

   public boolean isCall() {
      return (this.flags & 2) != 0;
   }

   public void markAsCall() {
      this.flags |= 2;
      this.flags &= -5;
   }

   public boolean isNew() {
      return (this.flags & 4) != 0;
   }

   public void markAsNew() {
      this.flags |= 4;
      this.flags &= -3;
   }

   public boolean isRef() {
      return !this.isCall() && !this.isNew();
   }

   public String toString() {
      return "<Ref:" + this.file + ":" + this.name + ":" + this.start + ">";
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof Ref)) {
         return false;
      } else {
         Ref ref = (Ref)obj;
         if (this.start != ref.start) {
            return false;
         } else {
            if (this.name != null) {
               if (!this.name.equals(ref.name)) {
                  return false;
               }
            } else if (ref.name != null) {
               return false;
            }

            if (this.file != null) {
               if (!this.file.equals(ref.file)) {
                  return false;
               }
            } else if (ref.file != null) {
               return false;
            }

            return this.flags == ref.flags;
         }
      }
   }

   public int hashCode() {
      return ("" + this.file + this.name + this.start).hashCode();
   }
}
