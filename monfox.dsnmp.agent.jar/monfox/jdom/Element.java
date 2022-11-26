package monfox.jdom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;

public class Element implements Serializable, Cloneable {
   protected String name;
   protected transient Namespace namespace;
   protected transient LinkedList additionalNamespaces;
   protected Element parent;
   protected Document document;
   protected List attributes;
   protected LinkedList content;
   // $FF: synthetic field
   static Class a;
   public static boolean b;

   protected Element() {
   }

   public Element(String var1, Namespace var2) {
      String var3;
      if ((var3 = Verifier.checkElementName(var1)) != null) {
         throw new IllegalNameException(var1, b("ZyuBTQa"), var3);
      } else {
         this.name = var1;
         if (var2 == null) {
            var2 = Namespace.NO_NAMESPACE;
         }

         this.namespace = var2;
         this.document = null;
      }
   }

   public Element(String var1) {
      this(var1, Namespace.NO_NAMESPACE);
   }

   public Element(String var1, String var2) {
      this(var1, Namespace.getNamespace("", var2));
   }

   public Element(String var1, String var2, String var3) {
      this(var1, Namespace.getNamespace(var2, var3));
   }

   public Element getCopy(String var1, Namespace var2) {
      Element var3 = (Element)this.clone();
      var3.namespace = var2;
      var3.name = var1;
      return var3;
   }

   public Element getCopy(String var1) {
      return this.getCopy(var1, Namespace.NO_NAMESPACE);
   }

   public String getName() {
      return this.name;
   }

   public Namespace getNamespace() {
      return this.namespace;
   }

   public String getNamespacePrefix() {
      return this.namespace.getPrefix();
   }

   public String getNamespaceURI() {
      return this.namespace.getURI();
   }

   public Namespace getNamespace(String var1) {
      if (var1 == null) {
         return null;
      } else if (var1.equals(this.getNamespacePrefix())) {
         return this.getNamespace();
      } else {
         List var2 = this.getAdditionalNamespaces();
         if (var2.size() > 0) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               Namespace var4 = (Namespace)var3.next();
               if (var1.equals(var4.getPrefix())) {
                  return var4;
               }

               if (b) {
                  break;
               }
            }
         }

         Element var5 = this.getParent();
         return var5 != null ? var5.getNamespace(var1) : null;
      }
   }

   public String getQualifiedName() {
      return this.namespace.getPrefix().equals("") ? this.getName() : this.namespace.getPrefix() + ":" + this.name;
   }

   public void addNamespaceDeclaration(Namespace var1) {
      if (this.additionalNamespaces == null) {
         this.additionalNamespaces = new LinkedList();
      }

      this.additionalNamespaces.add(var1);
   }

   public List getAdditionalNamespaces() {
      return (List)(this.additionalNamespaces == null ? Collections.EMPTY_LIST : this.additionalNamespaces);
   }

   public Element getParent() {
      return this.parent;
   }

   protected Element setParent(Element var1) {
      this.parent = var1;
      return this;
   }

   public boolean isRootElement() {
      return this.document != null;
   }

   protected Element setDocument(Document var1) {
      this.document = var1;
      return this;
   }

   public Document getDocument() {
      if (this.isRootElement()) {
         return this.document;
      } else {
         return this.getParent() != null ? this.getParent().getDocument() : null;
      }
   }

   public String getText() {
      boolean var5 = b;
      if (this.content != null && this.content.size() >= 1 && this.content.get(0) != null) {
         if (this.content.size() == 1 && this.content.get(0) instanceof String) {
            return (String)this.content.get(0);
         } else {
            StringBuffer var1 = new StringBuffer();
            boolean var2 = false;
            Iterator var3 = this.content.iterator();

            boolean var10000;
            while(true) {
               if (var3.hasNext()) {
                  Object var4 = var3.next();
                  var10000 = var4 instanceof String;
                  if (var5) {
                     break;
                  }

                  label35: {
                     if (var10000) {
                        var1.append((String)var4);
                        var2 = true;
                        if (!var5) {
                           break label35;
                        }
                     }

                     if (var4 instanceof CDATA) {
                        var1.append(((CDATA)var4).getText());
                        var2 = true;
                     }
                  }

                  if (!var5) {
                     continue;
                  }
               }

               var10000 = var2;
               break;
            }

            return !var10000 ? "" : var1.toString();
         }
      } else {
         return "";
      }
   }

   public String getTextTrim() {
      boolean var5 = b;
      String var1 = this.getText();
      StringBuffer var2 = new StringBuffer();
      StringTokenizer var3 = new StringTokenizer(var1);

      StringBuffer var10000;
      while(true) {
         if (var3.hasMoreTokens()) {
            String var4 = var3.nextToken();
            var10000 = var2.append(var4);
            if (var5) {
               break;
            }

            if (var3.hasMoreTokens()) {
               var2.append(" ");
            }

            if (!var5) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000.toString();
   }

   public String getChildText(String var1) {
      Element var2 = this.getChild(var1);
      return var2 == null ? null : var2.getText();
   }

   public String getChildTextTrim(String var1) {
      Element var2 = this.getChild(var1);
      return var2 == null ? null : var2.getTextTrim();
   }

   public String getChildText(String var1, Namespace var2) {
      Element var3 = this.getChild(var1, var2);
      return var3 == null ? null : var3.getText();
   }

   public String getChildTextTrim(String var1, Namespace var2) {
      Element var3 = this.getChild(var1, var2);
      return var3 == null ? null : var3.getTextTrim();
   }

   public Element setText(String var1) {
      label15: {
         if (this.content != null) {
            this.content.clear();
            if (!b) {
               break label15;
            }
         }

         this.content = new LinkedList();
      }

      if (var1 != null) {
         this.content.add(var1);
      }

      return this;
   }

   public boolean hasMixedContent() {
      if (this.content == null) {
         return false;
      } else {
         Class var1 = null;
         Iterator var2 = this.content.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            Class var4 = var3.getClass();
            if (var4 != var1) {
               if (var1 != null) {
                  return true;
               }

               var1 = var4;
            }

            if (b) {
               break;
            }
         }

         return false;
      }
   }

   public List getMixedContent() {
      if (this.content == null) {
         this.content = new LinkedList();
      }

      a var1 = new a(this.content, this);
      var1.addAllPartial(this.content);
      return var1;
   }

   public Element setMixedContent(List var1) {
      label15: {
         if (this.content != null) {
            this.content.clear();
            if (!b) {
               break label15;
            }
         }

         this.content = new LinkedList();
      }

      if (var1 != null) {
         this.content.addAll(var1);
      }

      return this;
   }

   public boolean hasChildren() {
      if (this.content != null && this.content.size() != 0) {
         Iterator var1 = this.content.iterator();

         while(var1.hasNext()) {
            Object var2 = var1.next();
            Class var3 = var2.getClass();
            if (var3 == (a == null ? (a = a(b("Rz~I^G;zK^R;UCTRp~["))) : a)) {
               return true;
            }

            if (b) {
               break;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public List getChildren() {
      if (this.content == null) {
         this.content = new LinkedList();
      }

      a var1 = new a(this.content, this);
      Iterator var2 = this.content.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof Element) {
            var1.addPartial(var3);
         }

         if (b) {
            break;
         }
      }

      return var1;
   }

   public Element setChildren(List var1) {
      return this.setMixedContent(var1);
   }

   public List getChildren(String var1) {
      return this.getChildren(var1, Namespace.NO_NAMESPACE);
   }

   public List getChildren(String var1, Namespace var2) {
      a var3 = new a(this.getChildren(), this);
      if (this.content != null) {
         String var4 = var2.getURI();
         Iterator var5 = this.content.iterator();

         while(var5.hasNext()) {
            Object var6 = var5.next();
            if (var6 instanceof Element) {
               Element var7 = (Element)var6;
               if (var7.getNamespaceURI().equals(var4) && var7.getName().equals(var1)) {
                  var3.addPartial(var7);
               }
            }

            if (b) {
               break;
            }
         }
      }

      return var3;
   }

   public Element getChild(String var1, Namespace var2) {
      if (this.content == null) {
         return null;
      } else {
         String var3 = var2.getURI();
         Iterator var4 = this.content.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            if (var5 instanceof Element) {
               Element var6 = (Element)var5;
               if (var6.getNamespaceURI().equals(var3) && var6.getName().equals(var1)) {
                  return var6;
               }
            }

            if (b) {
               break;
            }
         }

         return null;
      }
   }

   public Element getChild(String var1) {
      return this.getChild(var1, Namespace.NO_NAMESPACE);
   }

   public Element addContent(String var1) {
      if (this.content == null) {
         this.content = new LinkedList();
      }

      if (this.content.size() > 0) {
         Object var2 = this.content.getLast();
         if (var2 instanceof String) {
            var1 = (String)var2 + var1;
            this.content.removeLast();
         }
      }

      this.content.add(var1);
      return this;
   }

   public Element addContent(Element var1) {
      boolean var2 = b;
      if (var1.isRootElement()) {
         throw new IllegalAddException(this, var1, b("k}u\u000fTSp}J_K5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\u0007EWp0K^\\`}J_K5b@^K<"));
      } else if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fTSp}J_K5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\r") + var1.getParent().getQualifiedName() + "\"");
      } else if (var1 == this) {
         throw new IllegalAddException(this, var1, b("k}u\u000fTSp}J_K5sN_Qzd\u000fSZ5qKUZq0[^\u001f|d\\TSs"));
      } else if (this.a(var1)) {
         throw new IllegalAddException(this, var1, b("k}u\u000fTSp}J_K5sN_Qzd\u000fSZ5qKUZq0NB\u001ft0KTLvuAUZ{d\u000f^Y5y[BZyv"));
      } else {
         if (this.content == null) {
            this.content = new LinkedList();
         }

         var1.setParent(this);
         this.content.add(var1);
         if (SnmpException.b) {
            b = !var2;
         }

         return this;
      }
   }

   private boolean a(Element var1) {
      Element var2 = this.getParent();

      while(var2 != null) {
         if (var2 == var1) {
            return true;
         }

         var2 = var2.getParent();
         if (b) {
            break;
         }
      }

      return false;
   }

   public Element addContent(ProcessingInstruction var1) {
      if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fav5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\r") + var1.getParent().getQualifiedName() + "\"");
      } else if (var1.getDocument() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fav5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\u0007EWp0K^\\`}J_K5b@^K<"));
      } else {
         if (this.content == null) {
            this.content = new LinkedList();
         }

         this.content.add(var1);
         var1.setParent(this);
         return this;
      }
   }

   public Element addContent(Entity var1) {
      if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fTQay[H\u001ft|]T^qi\u000fY^f0N_\u001fphFBK|~H\u0011OtbJ_K52") + var1.getParent().getQualifiedName() + "\"");
      } else {
         if (this.content == null) {
            this.content = new LinkedList();
         }

         this.content.add(var1);
         var1.setParent(this);
         return this;
      }
   }

   public Element addContent(CDATA var1) {
      if (this.content == null) {
         this.content = new LinkedList();
      }

      this.content.add(var1);
      return this;
   }

   public Element addContent(Comment var1) {
      if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fRPx}J_K5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\r") + var1.getParent().getQualifiedName() + "\"");
      } else if (var1.getDocument() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fRPx}J_K5qCCZttV\u0011Wtc\u000fPQ5uWXLayAV\u001feq]TQa0\u0007EWp0K^\\`}J_K5b@^K<"));
      } else {
         if (this.content == null) {
            this.content = new LinkedList();
         }

         this.content.add(var1);
         var1.setParent(this);
         return this;
      }
   }

   public boolean removeChild(String var1) {
      return this.removeChild(var1, Namespace.NO_NAMESPACE);
   }

   public boolean removeChild(String var1, Namespace var2) {
      boolean var7 = b;
      if (this.content == null) {
         return false;
      } else {
         String var3 = var2.getURI();
         Iterator var4 = this.content.iterator();

         boolean var10000;
         while(true) {
            if (var4.hasNext()) {
               Object var5 = var4.next();
               var10000 = var5 instanceof Element;
               if (var7) {
                  break;
               }

               if (var10000) {
                  Element var6 = (Element)var5;
                  if (var6.getNamespaceURI().equals(var3) && var6.getName().equals(var1)) {
                     var6.setParent((Element)null);
                     var4.remove();
                     return true;
                  }
               }

               if (!var7) {
                  continue;
               }
            }

            var10000 = false;
            break;
         }

         return var10000;
      }
   }

   public boolean removeChildren(String var1) {
      return this.removeChildren(var1, Namespace.NO_NAMESPACE);
   }

   public boolean removeChildren(String var1, Namespace var2) {
      boolean var8 = b;
      if (this.content == null) {
         return false;
      } else {
         String var3 = var2.getURI();
         boolean var4 = false;
         Iterator var5 = this.content.iterator();

         boolean var10000;
         while(true) {
            if (var5.hasNext()) {
               Object var6 = var5.next();
               var10000 = var6 instanceof Element;
               if (var8) {
                  break;
               }

               if (var10000) {
                  Element var7 = (Element)var6;
                  if (var7.getNamespaceURI().equals(var3) && var7.getName().equals(var1)) {
                     var7.setParent((Element)null);
                     var5.remove();
                     var4 = true;
                  }
               }

               if (!var8) {
                  continue;
               }
            }

            var10000 = var4;
            break;
         }

         return var10000;
      }
   }

   public boolean removeChildren() {
      boolean var5 = b;
      boolean var1 = false;
      boolean var10000;
      if (this.content != null) {
         Iterator var2 = this.content.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            var10000 = var3 instanceof Element;
            if (var5) {
               return var10000;
            }

            if (var10000) {
               Element var4 = (Element)var3;
               var2.remove();
               var4.setParent((Element)null);
               var1 = true;
            }

            if (var5) {
               break;
            }
         }
      }

      var10000 = var1;
      return var10000;
   }

   public List getAttributes() {
      if (this.attributes == null) {
         this.attributes = new LinkedList();
      }

      a var1 = new a(this.attributes, this);
      var1.addAllPartial(this.attributes);
      return var1;
   }

   public Attribute getAttribute(String var1) {
      return this.getAttribute(var1, Namespace.NO_NAMESPACE);
   }

   public Attribute getAttribute(String var1, Namespace var2) {
      if (this.attributes == null) {
         return null;
      } else {
         String var3 = var2.getURI();
         Iterator var4 = this.attributes.iterator();

         while(var4.hasNext()) {
            Attribute var5 = (Attribute)var4.next();
            if (var5.getNamespaceURI().equals(var3) && var5.getName().equals(var1)) {
               return var5;
            }

            if (b) {
               break;
            }
         }

         return null;
      }
   }

   public String getAttributeValue(String var1) {
      return this.getAttributeValue(var1, Namespace.NO_NAMESPACE);
   }

   public String getAttributeValue(String var1, Namespace var2) {
      Attribute var3 = this.getAttribute(var1, var2);
      return var3 == null ? null : var3.getValue();
   }

   public Element setAttributes(List var1) {
      this.attributes = var1;
      return this;
   }

   public Element addAttribute(Attribute var1) {
      if (this.getAttribute(var1.getName(), var1.getNamespace()) != null) {
         throw new IllegalAddException(this, var1, b("{``CX\\tdJ\u0011^ad]X]`dJB\u001ftbJ\u0011Qzd\u000fPSy\u007fXT["));
      } else if (var1.getParent() != null) {
         throw new IllegalAddException(this, var1, b("k}u\u000fPKabFSJau\u000fPSguNUF5xNB\u001ft~\u000fTG|c[XQr0_PMp~[\u0011\u001d") + var1.getParent().getQualifiedName() + "\"");
      } else {
         if (this.attributes == null) {
            this.attributes = new LinkedList();
         }

         this.attributes.add(var1);
         var1.setParent(this);
         return this;
      }
   }

   public Element addAttribute(String var1, String var2) {
      return this.addAttribute(new Attribute(var1, var2));
   }

   public boolean removeAttribute(String var1, String var2) {
      boolean var5 = b;
      Iterator var3 = this.attributes.iterator();

      boolean var10000;
      while(true) {
         if (var3.hasNext()) {
            Attribute var4 = (Attribute)var3.next();
            var10000 = var4.getNamespaceURI().equals(var2);
            if (var5) {
               break;
            }

            if (var10000 && var4.getName().equals(var1)) {
               var3.remove();
               var4.setParent((Element)null);
               return true;
            }

            if (!var5) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   public boolean removeAttribute(String var1) {
      return this.removeAttribute(var1, Namespace.NO_NAMESPACE);
   }

   public boolean removeAttribute(String var1, Namespace var2) {
      boolean var6 = b;
      if (this.attributes == null) {
         return false;
      } else {
         String var3 = var2.getURI();
         Iterator var4 = this.attributes.iterator();

         boolean var10000;
         while(true) {
            if (var4.hasNext()) {
               Attribute var5 = (Attribute)var4.next();
               var10000 = var5.getNamespaceURI().equals(var3);
               if (var6) {
                  break;
               }

               if (var10000 && var5.getName().equals(var1)) {
                  var4.remove();
                  var5.setParent((Element)null);
                  return true;
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
   }

   public String toString() {
      StringBuffer var1 = (new StringBuffer(64)).append(b("dP|J\\Z{d\u0015\u0011\u0003")).append(this.getQualifiedName());
      String var2 = this.getNamespaceURI();
      if (!var2.equals("")) {
         var1.append(b("\u001fN^N\\Zf`NRZ/0")).append(var2).append("]");
      }

      var1.append(b("\u0010+M"));
      return var1.toString();
   }

   public final String getSerializedForm() {
      throw new RuntimeException(b("zyuBTQa>HTKFu]X^yyUT[S\u007f]\\\u0017<0FB\u001f{\u007f[\u0011Fpd\u000fXRe|J\\Z{dJU"));
   }

   public final boolean equals(Object var1) {
      return this == var1;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      Element var1;
      label65: {
         boolean var4 = b;
         var1 = new Element(this.name, this.namespace);
         if (this.attributes != null) {
            new LinkedList();
            Iterator var3 = this.attributes.iterator();

            while(var3.hasNext()) {
               var1.addAttribute((Attribute)((Attribute)var3.next()).clone());
               if (var4 && var4) {
                  break label65;
               }
            }
         }

         if (this.content != null) {
            Iterator var2 = this.content.iterator();

            while(var2.hasNext()) {
               Object var5 = var2.next();
               if (var4) {
                  return var1;
               }

               label70: {
                  if (var5 instanceof String) {
                     var1.addContent((String)var5);
                     if (!var4) {
                        break label70;
                     }
                  }

                  if (var5 instanceof Comment) {
                     var1.addContent((Comment)((Comment)var5).clone());
                     if (!var4) {
                        break label70;
                     }
                  }

                  if (var5 instanceof Entity) {
                     var1.addContent((Entity)((Entity)var5).clone());
                     if (!var4) {
                        break label70;
                     }
                  }

                  if (var5 instanceof Element) {
                     var1.addContent((Element)((Element)var5).clone());
                     if (!var4) {
                        break label70;
                     }
                  }

                  if (var5 instanceof CDATA) {
                     var1.addContent((CDATA)((CDATA)var5).clone());
                  }
               }

               if (var4) {
                  break;
               }
            }
         }
      }

      if (this.additionalNamespaces != null) {
         var1.additionalNamespaces = (LinkedList)this.additionalNamespaces.clone();
      }

      var1.setParent((Element)null);
      return var1;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.namespace.getPrefix());
      var1.writeObject(this.namespace.getURI());
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.namespace = Namespace.getNamespace((String)var1.readObject(), (String)var1.readObject());
   }

   public boolean removeContent(Element var1) {
      if (this.content == null) {
         return false;
      } else if (this.content.remove(var1)) {
         var1.setParent((Element)null);
         return true;
      } else {
         return false;
      }
   }

   public boolean removeContent(ProcessingInstruction var1) {
      if (this.content == null) {
         return false;
      } else if (this.content.remove(var1)) {
         var1.setParent((Element)null);
         return true;
      } else {
         return false;
      }
   }

   public boolean removeContent(Entity var1) {
      if (this.content == null) {
         return false;
      } else if (this.content.remove(var1)) {
         var1.setParent((Element)null);
         return true;
      } else {
         return false;
      }
   }

   public boolean removeContent(Comment var1) {
      if (this.content == null) {
         return false;
      } else if (this.content.remove(var1)) {
         var1.setParent((Element)null);
         return true;
      } else {
         return false;
      }
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 63;
               break;
            case 1:
               var10003 = 21;
               break;
            case 2:
               var10003 = 16;
               break;
            case 3:
               var10003 = 47;
               break;
            default:
               var10003 = 49;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
