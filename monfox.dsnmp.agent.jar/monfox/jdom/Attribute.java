package monfox.jdom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Attribute implements Serializable, Cloneable {
   protected String name;
   protected transient Namespace namespace;
   protected String value;
   protected Element parent;

   protected Attribute() {
   }

   public Attribute(String var1, String var2, Namespace var3) {
      String var4;
      if ((var4 = Verifier.checkAttributeName(var1)) != null) {
         throw new IllegalNameException(var1, a("\u001b~|PM\u0018\u007f|G"), var4);
      } else {
         if (var3 == null) {
            var3 = Namespace.NO_NAMESPACE;
         }

         if (var3 != Namespace.NO_NAMESPACE && var3.getPrefix().equals("")) {
            throw new IllegalNameException("", a("\u001b~|PM\u0018\u007f|G\u0004\u0014keGW\nkkG"), a(";d(CP\u000exa@Q\u000eo(LE\u0017o{RE\u0019o(UM\u000ebgWPZk(RV\u001flaZ\u0004\u0019kf\u0002K\u0014fq\u0002F\u001f*|JAZDG}j;GMqt;IM\u0002J\u001bgmQT\u001bim"));
         } else {
            this.name = var1;
            this.setValue(var2);
            this.namespace = var3;
         }
      }
   }

   public Attribute(String var1, String var2, String var3, String var4) {
      this(var1, var4, Namespace.getNamespace(var2, var3));
   }

   public Attribute(String var1, String var2) {
      this(var1, var2, Namespace.NO_NAMESPACE);
   }

   public Element getParent() {
      return this.parent;
   }

   protected Attribute setParent(Element var1) {
      this.parent = var1;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public String getQualifiedName() {
      StringBuffer var1 = new StringBuffer();
      String var2 = this.namespace.getPrefix();
      if (var2 != null && !var2.equals("")) {
         var1.append(var2).append(":");
      }

      var1.append(this.name);
      return var1.toString();
   }

   public String getNamespacePrefix() {
      return this.namespace.getPrefix();
   }

   public String getNamespaceURI() {
      return this.namespace.getURI();
   }

   public Namespace getNamespace() {
      return this.namespace;
   }

   public String getValue() {
      return this.value;
   }

   public Attribute setValue(String var1) {
      String var2 = null;
      if ((var2 = Verifier.checkCharacterData(var1)) != null) {
         throw new IllegalDataException(var1, a("\u001b~|PM\u0018\u007f|G"), var2);
      } else {
         this.value = var1;
         return this;
      }
   }

   public String toString() {
      return a("!K|VV\u0013h}VA@*") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      return this.getQualifiedName() + a("G(") + this.value + "\"";
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      Attribute var1 = new Attribute(this.name, this.value, this.namespace);
      return var1;
   }

   public int getIntValue() throws DataConversionException {
      try {
         return Integer.parseInt(this.value);
      } catch (NumberFormatException var2) {
         throw new DataConversionException(this.name, a("\u0013d|"));
      }
   }

   public long getLongValue() throws DataConversionException {
      try {
         return Long.parseLong(this.value);
      } catch (NumberFormatException var2) {
         throw new DataConversionException(this.name, a("\u0016efE"));
      }
   }

   public float getFloatValue() throws DataConversionException {
      try {
         return Float.valueOf(this.value);
      } catch (NumberFormatException var2) {
         throw new DataConversionException(this.name, a("\u001cfgCP"));
      }
   }

   public double getDoubleValue() throws DataConversionException {
      try {
         return Double.valueOf(this.value);
      } catch (NumberFormatException var2) {
         throw new DataConversionException(this.name, a("\u001ee}@H\u001f"));
      }
   }

   public boolean getBooleanValue() throws DataConversionException {
      if (!this.value.equalsIgnoreCase(a("\u000ex}G")) && !this.value.equalsIgnoreCase(a("\u0015d")) && !this.value.equalsIgnoreCase(a("\u0003o{"))) {
         if (!this.value.equalsIgnoreCase(a("\u001ckdQA")) && !this.value.equalsIgnoreCase(a("\u0015ln")) && !this.value.equalsIgnoreCase(a("\u0014e"))) {
            throw new DataConversionException(this.name, a("\u0018egNA\u001bd"));
         } else {
            return false;
         }
      } else {
         return true;
      }
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

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 122;
               break;
            case 1:
               var10003 = 10;
               break;
            case 2:
               var10003 = 8;
               break;
            case 3:
               var10003 = 34;
               break;
            default:
               var10003 = 36;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
