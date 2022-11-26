package weblogic.apache.xerces.impl.xs.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.xml.namespace.QName;
import weblogic.apache.xerces.util.SymbolHash;
import weblogic.apache.xerces.xs.XSNamedMap;
import weblogic.apache.xerces.xs.XSObject;

public class XSNamedMapImpl extends AbstractMap implements XSNamedMap {
   public static final XSNamedMapImpl EMPTY_MAP = new XSNamedMapImpl(new XSObject[0], 0);
   final String[] fNamespaces;
   final int fNSNum;
   final SymbolHash[] fMaps;
   XSObject[] fArray = null;
   int fLength = -1;
   private Set fEntrySet = null;

   public XSNamedMapImpl(String var1, SymbolHash var2) {
      this.fNamespaces = new String[]{var1};
      this.fMaps = new SymbolHash[]{var2};
      this.fNSNum = 1;
   }

   public XSNamedMapImpl(String[] var1, SymbolHash[] var2, int var3) {
      this.fNamespaces = var1;
      this.fMaps = var2;
      this.fNSNum = var3;
   }

   public XSNamedMapImpl(XSObject[] var1, int var2) {
      if (var2 == 0) {
         this.fNamespaces = null;
         this.fMaps = null;
         this.fNSNum = 0;
         this.fArray = var1;
         this.fLength = 0;
      } else {
         this.fNamespaces = new String[]{var1[0].getNamespace()};
         this.fMaps = null;
         this.fNSNum = 1;
         this.fArray = var1;
         this.fLength = var2;
      }
   }

   public synchronized int getLength() {
      if (this.fLength == -1) {
         this.fLength = 0;

         for(int var1 = 0; var1 < this.fNSNum; ++var1) {
            this.fLength += this.fMaps[var1].getLength();
         }
      }

      return this.fLength;
   }

   public XSObject itemByName(String var1, String var2) {
      for(int var3 = 0; var3 < this.fNSNum; ++var3) {
         if (isEqual(var1, this.fNamespaces[var3])) {
            if (this.fMaps != null) {
               return (XSObject)this.fMaps[var3].get(var2);
            }

            for(int var5 = 0; var5 < this.fLength; ++var5) {
               XSObject var4 = this.fArray[var5];
               if (var4.getName().equals(var2)) {
                  return var4;
               }
            }

            return null;
         }
      }

      return null;
   }

   public synchronized XSObject item(int var1) {
      if (this.fArray == null) {
         this.getLength();
         this.fArray = new XSObject[this.fLength];
         int var2 = 0;

         for(int var3 = 0; var3 < this.fNSNum; ++var3) {
            var2 += this.fMaps[var3].getValues(this.fArray, var2);
         }
      }

      return var1 >= 0 && var1 < this.fLength ? this.fArray[var1] : null;
   }

   static boolean isEqual(String var0, String var1) {
      return var0 != null ? var0.equals(var1) : var1 == null;
   }

   public boolean containsKey(Object var1) {
      return this.get(var1) != null;
   }

   public Object get(Object var1) {
      if (var1 instanceof QName) {
         QName var2 = (QName)var1;
         String var3 = var2.getNamespaceURI();
         if ("".equals(var3)) {
            var3 = null;
         }

         String var4 = var2.getLocalPart();
         return this.itemByName(var3, var4);
      } else {
         return null;
      }
   }

   public int size() {
      return this.getLength();
   }

   public synchronized Set entrySet() {
      if (this.fEntrySet == null) {
         final int var1 = this.getLength();
         final XSNamedMapEntry[] var2 = new XSNamedMapEntry[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            XSObject var4 = this.item(var3);
            var2[var3] = new XSNamedMapEntry(new QName(var4.getNamespace(), var4.getName()), var4);
         }

         this.fEntrySet = new AbstractSet() {
            public Iterator iterator() {
               return new Iterator() {
                  private int index = 0;

                  public boolean hasNext() {
                     return this.index < var1;
                  }

                  public Object next() {
                     if (this.index < var1) {
                        return var2[this.index++];
                     } else {
                        throw new NoSuchElementException();
                     }
                  }

                  public void remove() {
                     throw new UnsupportedOperationException();
                  }
               };
            }

            public int size() {
               return var1;
            }
         };
      }

      return this.fEntrySet;
   }

   private static final class XSNamedMapEntry implements Map.Entry {
      private final QName key;
      private final XSObject value;

      public XSNamedMapEntry(QName var1, XSObject var2) {
         this.key = var1;
         this.value = var2;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object var1) {
         throw new UnsupportedOperationException();
      }

      public boolean equals(Object var1) {
         if (var1 instanceof Map.Entry) {
            Map.Entry var2 = (Map.Entry)var1;
            Object var3 = var2.getKey();
            Object var4 = var2.getValue();
            return (this.key == null ? var3 == null : this.key.equals(var3)) && (this.value == null ? var4 == null : this.value.equals(var4));
         } else {
            return false;
         }
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         var1.append(String.valueOf(this.key));
         var1.append('=');
         var1.append(String.valueOf(this.value));
         return var1.toString();
      }
   }
}
