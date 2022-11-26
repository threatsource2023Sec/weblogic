package monfox.jdom;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Entity implements Serializable, Cloneable {
   protected String name;
   protected List content;
   protected Element parent;
   protected Document document;

   protected Entity() {
   }

   public Entity(String var1) {
      this.name = var1;
      this.content = new LinkedList();
   }

   public String getName() {
      return this.name;
   }

   public Element getParent() {
      return this.parent;
   }

   protected Entity setParent(Element var1) {
      this.parent = var1;
      return this;
   }

   public Document getDocument() {
      if (this.document != null) {
         return this.document;
      } else {
         Element var1 = this.getParent();
         return var1 != null ? var1.getDocument() : null;
      }
   }

   protected Entity setDocument(Document var1) {
      this.document = var1;
      return this;
   }

   public String getContent() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = this.content.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof String) {
            var1.append((String)var3);
         }

         if (Element.b) {
            break;
         }
      }

      return var1.toString();
   }

   public Entity setContent(String var1) {
      this.content.clear();
      this.content.add(var1);
      return this;
   }

   public boolean hasMixedContent() {
      boolean var6 = Element.b;
      boolean var1 = false;
      boolean var2 = false;
      boolean var3 = false;
      Iterator var4 = this.content.iterator();

      boolean var10000;
      while(true) {
         if (var4.hasNext()) {
            Object var5 = var4.next();
            var10000 = var5 instanceof String;
            if (var6) {
               break;
            }

            label64: {
               if (var10000) {
                  if (var2 || var3) {
                     return true;
                  }

                  var1 = true;
                  if (!var6) {
                     break label64;
                  }
               }

               if (var5 instanceof Element) {
                  if (var1 || var3) {
                     return true;
                  }

                  var2 = true;
                  if (!var6) {
                     break label64;
                  }
               }

               if (var5 instanceof Comment) {
                  if (var1 || var2) {
                     return true;
                  }

                  var3 = true;
               }
            }

            if (!var6) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   public List getMixedContent() {
      return this.content;
   }

   public Entity setMixedContent(List var1) {
      this.content = var1;
      return this;
   }

   public List getChildren() {
      LinkedList var1 = new LinkedList();
      Iterator var2 = this.content.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof Element) {
            var1.add(var3);
         }

         if (Element.b) {
            break;
         }
      }

      return var1;
   }

   public Entity setChildren(List var1) {
      return this.setMixedContent(var1);
   }

   public Entity addText(String var1) {
      this.content.add(var1);
      return this;
   }

   public Entity addChild(Element var1) {
      this.content.add(var1);
      return this;
   }

   public Entity addChild(String var1) {
      this.content.add(var1);
      return this;
   }

   public String toString() {
      return a("M\"\u001ds)b\u001eI'") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      return "&" + this.name + ";";
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      Entity var1 = new Entity(this.name);
      var1.content = (List)((LinkedList)this.content).clone();
      return var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 22;
               break;
            case 1:
               var10003 = 103;
               break;
            case 2:
               var10003 = 115;
               break;
            case 3:
               var10003 = 7;
               break;
            default:
               var10003 = 64;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
