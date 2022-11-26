package monfox.jdom;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Document implements Serializable, Cloneable {
   protected List content;
   protected Element rootElement;
   protected DocType docType;

   protected Document() {
   }

   public Document(Element var1, DocType var2) {
      this.rootElement = var1;
      this.docType = var2;
      this.content = new LinkedList();
      if (var1 != null) {
         var1.setDocument(this);
         this.content.add(var1);
      }

   }

   public Document(Element var1) {
      this(var1, (DocType)null);
   }

   public Element getRootElement() {
      return this.rootElement;
   }

   public Document setRootElement(Element var1) {
      int var2 = this.content.size();
      if (this.rootElement != null) {
         this.rootElement.setDocument((Document)null);
         var2 = this.content.indexOf(this.rootElement);
         this.content.remove(var2);
      }

      if (var1 != null) {
         var1.setDocument(this);
         this.content.add(var2, var1);
      }

      this.rootElement = var1;
      return this;
   }

   public DocType getDocType() {
      return this.docType;
   }

   public Document setDocType(DocType var1) {
      this.docType = var1;
      return this;
   }

   public List getProcessingInstructions() {
      a var1 = new a(this.content);
      Iterator var2 = this.content.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof ProcessingInstruction) {
            var1.addPartial(var3);
         }

         if (Element.b) {
            break;
         }
      }

      return var1;
   }

   public List getProcessingInstructions(String var1) {
      a var2 = new a(this.content);
      Iterator var3 = this.content.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4 instanceof ProcessingInstruction && ((ProcessingInstruction)var4).getTarget().equals(var1)) {
            var2.addPartial(var4);
         }

         if (Element.b) {
            break;
         }
      }

      return var2;
   }

   public ProcessingInstruction getProcessingInstruction(String var1) {
      Iterator var2 = this.content.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof ProcessingInstruction && ((ProcessingInstruction)var3).getTarget().equals(var1)) {
            return (ProcessingInstruction)var3;
         }

         if (Element.b) {
            break;
         }
      }

      return null;
   }

   public boolean removeProcessingInstruction(String var1) {
      ProcessingInstruction var2 = this.getProcessingInstruction(var1);
      return var2 == null ? false : this.removeContent(var2);
   }

   public boolean removeProcessingInstructions(String var1) {
      boolean var6 = Element.b;
      boolean var2 = false;
      Iterator var3 = this.content.iterator();

      boolean var10000;
      while(true) {
         if (var3.hasNext()) {
            Object var4 = var3.next();
            var10000 = var4 instanceof ProcessingInstruction;
            if (var6) {
               break;
            }

            if (var10000) {
               ProcessingInstruction var5 = (ProcessingInstruction)var4;
               if (var5.getTarget().equals(var1)) {
                  var2 = true;
                  var3.remove();
                  var5.setDocument((Document)null);
               }
            }

            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public Document setProcessingInstructions(List var1) {
      boolean var4 = Element.b;
      List var2 = this.getProcessingInstructions();
      Iterator var3 = var2.iterator();

      while(true) {
         if (var3.hasNext()) {
            var3.remove();
            if (var4) {
               break;
            }

            if (!var4) {
               continue;
            }
         }

         this.content.addAll(var1);
         break;
      }

      return this;
   }

   public Document addContent(ProcessingInstruction var1) {
      if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, a("\"\u001f(\u0004=?W,H\u001f\u0013\u0016)]M\u001e\u0016>\u0004\f\u0018W(\\\u0004\u0005\u0003$J\nV\u0007,V\b\u0018\u0003m\u0006") + var1.getParent().getQualifiedName() + "\"");
      } else if (var1.getDocument() != null) {
         throw new IllegalAddException(this, var1, a("\"\u001f(\u0004=?W,H\u001f\u0013\u0016)]M\u001e\u0016>\u0004\f\u0018W(\\\u0004\u0005\u0003$J\nV\u0007,V\b\u0018\u0003m\f\u0019\u001e\u0012m@\u0002\u0015\u0002 A\u0003\u0002W?K\u0002\u0002^"));
      } else {
         this.content.add(var1);
         var1.setDocument(this);
         return this;
      }
   }

   public Document addContent(Comment var1) {
      if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, a("\"\u001f(\u0004\u000e\u0019\u001a A\u0003\u0002W,H\u001f\u0013\u0016)]M\u001e\u0016>\u0004\f\u0018W(\\\u0004\u0005\u0003$J\nV\u0007,V\b\u0018\u0003m\u0006") + var1.getParent().getQualifiedName() + "\"");
      } else if (var1.getDocument() != null) {
         throw new IllegalAddException(this, var1, a("\"\u001f(\u0004\b\u001a\u0012 A\u0003\u0002W,H\u001f\u0013\u0016)]M\u001e\u0016>\u0004\f\u0018W(\\\u0004\u0005\u0003$J\nV\u0007,V\b\u0018\u0003m\f\u0019\u001e\u0012m@\u0002\u0015\u0002 A\u0003\u0002W?K\u0002\u0002^"));
      } else {
         this.content.add(var1);
         var1.setDocument(this);
         return this;
      }
   }

   public List getMixedContent() {
      return this.content;
   }

   public Document setMixedContent(List var1) {
      boolean var4 = Element.b;
      this.content.clear();
      this.rootElement = null;
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         label40: {
            Object var3 = var2.next();
            if (var3 instanceof Element) {
               this.setRootElement((Element)var3);
               if (!var4) {
                  break label40;
               }
            }

            if (var3 instanceof Comment) {
               this.addContent((Comment)var3);
               if (!var4) {
                  break label40;
               }
            }

            if (!(var3 instanceof ProcessingInstruction)) {
               throw new IllegalAddException(a("7W\tK\u000e\u0003\u001a(J\u0019V\u001a,]M\u0015\u0018#P\f\u001f\u0019mK\u0003\u001a\u000emK\u000f\u001c\u0012.P\u001eV\u0018+\u0004\u0019\u000f\u0007(\u0004(\u001a\u0012 A\u0003\u0002[mg\u0002\u001b\u001a(J\u0019ZW,J\tV'?K\u000e\u0013\u0004>M\u0003\u0011>#W\u0019\u0004\u0002.P\u0004\u0019\u0019"));
            }

            this.addContent((ProcessingInstruction)var3);
            if (var4) {
               throw new IllegalAddException(a("7W\tK\u000e\u0003\u001a(J\u0019V\u001a,]M\u0015\u0018#P\f\u001f\u0019mK\u0003\u001a\u000emK\u000f\u001c\u0012.P\u001eV\u0018+\u0004\u0019\u000f\u0007(\u0004(\u001a\u0012 A\u0003\u0002[mg\u0002\u001b\u001a(J\u0019ZW,J\tV'?K\u000e\u0013\u0004>M\u0003\u0011>#W\u0019\u0004\u0002.P\u0004\u0019\u0019"));
            }
         }

         if (var4) {
            break;
         }
      }

      return this;
   }

   public String toString() {
      StringBuffer var1;
      boolean var2;
      label19: {
         var2 = Element.b;
         var1 = (new StringBuffer()).append(a("-3\"G\u0018\u001b\u0012#PWV"));
         if (this.docType != null) {
            var1.append(this.docType.toString()).append(" ");
            if (!var2) {
               break label19;
            }
         }

         var1.append(a("V9\"\u0004)94\u0019}=3W)A\u000e\u001a\u0016?E\u0019\u001f\u0018#\nM"));
      }

      label14: {
         if (this.rootElement != null) {
            var1.append(a("$\u0018\"PM[W")).append(this.rootElement.toString());
            if (!var2) {
               break label14;
            }
         }

         var1.append(a("V9\"\u0004?\u0019\u00189\u0004(\u001a\u0012 A\u0003\u0002Y"));
      }

      var1.append("]");
      return var1.toString();
   }

   public final String getSerializedForm() {
      throw new RuntimeException(a("2\u0018.Q\u0000\u0013\u00199\n\n\u0013\u0003\u001eA\u001f\u001f\u0016!M\u0017\u0013\u0013\u000bK\u001f\u001b_d\u0004\u0004\u0005W#K\u0019V\u000e(PM\u001f\u001a=H\b\u001b\u0012#P\b\u0012"));
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      boolean var5 = Element.b;
      Document var1 = new Document((Element)null);
      Iterator var2 = this.content.iterator();

      while(true) {
         if (var2.hasNext()) {
            Object var3 = var2.next();
            if (var5) {
               break;
            }

            label38: {
               if (var3 instanceof Element) {
                  Element var4 = (Element)var3;
                  var1.setRootElement((Element)var4.clone());
                  if (!var5) {
                     break label38;
                  }
               }

               if (var3 instanceof Comment) {
                  Comment var6 = (Comment)var3;
                  var1.addContent((Comment)var6.clone());
                  if (!var5) {
                     break label38;
                  }
               }

               if (var3 instanceof ProcessingInstruction) {
                  ProcessingInstruction var7 = (ProcessingInstruction)var3;
                  var1.addContent((ProcessingInstruction)var7.clone());
               }
            }

            if (!var5) {
               continue;
            }
         }

         if (this.docType != null) {
            var1.docType = (DocType)this.docType.clone();
         }
         break;
      }

      return var1;
   }

   public boolean removeContent(ProcessingInstruction var1) {
      return this.content == null ? false : this.content.remove(var1);
   }

   public boolean removeContent(Comment var1) {
      if (this.content == null) {
         return false;
      } else if (this.content.remove(var1)) {
         var1.setDocument((Document)null);
         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public Document addContent(Element var1) {
      if (this.getRootElement() != null) {
         throw new IllegalAddException(this, var1, a("\"\u001f(\u0004\t\u0019\u00148I\b\u0018\u0003mE\u0001\u0004\u0012,@\u0014V\u001f,WM\u0017W?K\u0002\u0002W(H\b\u001b\u0012#P"));
      } else {
         this.setRootElement(var1);
         return this;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 118;
               break;
            case 1:
               var10003 = 119;
               break;
            case 2:
               var10003 = 77;
               break;
            case 3:
               var10003 = 36;
               break;
            default:
               var10003 = 109;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
