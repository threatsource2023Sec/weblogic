package com.bea.xbean.values;

import com.bea.xbean.schema.SchemaTypeImpl;
import com.bea.xbean.schema.SchemaTypeVisitorImpl;
import com.bea.xml.GDate;
import com.bea.xml.GDateSpecification;
import com.bea.xml.GDuration;
import com.bea.xml.GDurationSpecification;
import com.bea.xml.QNameSet;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;

public class XmlComplexContentImpl extends XmlObjectBase {
   private SchemaTypeImpl _schemaType;

   public XmlComplexContentImpl(SchemaType type) {
      this._schemaType = (SchemaTypeImpl)type;
      this.initComplexType(true, true);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public String compute_text(NamespaceManager nsm) {
      return null;
   }

   protected final void set_String(String v) {
      assert this._schemaType.getContentType() != 2;

      if (this._schemaType.getContentType() != 4 && !this._schemaType.isNoType()) {
         throw new IllegalArgumentException("Type does not allow for textual content: " + this._schemaType);
      } else {
         super.set_String(v);
      }
   }

   public void set_text(String str) {
      assert this._schemaType.getContentType() == 4 || this._schemaType.isNoType();

   }

   protected void update_from_complex_content() {
   }

   public void set_nil() {
   }

   public boolean equal_to(XmlObject complexObject) {
      return this._schemaType.equals(complexObject.schemaType());
   }

   protected int value_hash_code() {
      throw new IllegalStateException("Complex types cannot be used as hash keys");
   }

   public TypeStoreVisitor new_visitor() {
      return new SchemaTypeVisitorImpl(this._schemaType.getContentModel());
   }

   public boolean is_child_element_order_sensitive() {
      return this.schemaType().isOrderSensitive();
   }

   public int get_elementflags(QName eltName) {
      SchemaProperty prop = this.schemaType().getElementProperty(eltName);
      if (prop == null) {
         return 0;
      } else {
         return prop.hasDefault() != 1 && prop.hasFixed() != 1 && prop.hasNillable() != 1 ? (prop.hasDefault() == 0 ? 0 : 2) | (prop.hasFixed() == 0 ? 0 : 4) | (prop.hasNillable() == 0 ? 0 : 1) : -1;
      }
   }

   public String get_default_attribute_text(QName attrName) {
      return super.get_default_attribute_text(attrName);
   }

   public String get_default_element_text(QName eltName) {
      SchemaProperty prop = this.schemaType().getElementProperty(eltName);
      return prop == null ? "" : prop.getDefaultText();
   }

   protected void unionArraySetterHelper(Object[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).objectSet(sources[i]);
      }

   }

   protected SimpleValue[] arraySetterHelper(int sourcesLength, QName elemName) {
      SimpleValue[] dests = new SimpleValue[sourcesLength];
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > sourcesLength; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < sourcesLength; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         dests[i] = (SimpleValue)user;
      }

      return dests;
   }

   protected SimpleValue[] arraySetterHelper(int sourcesLength, QName elemName, QNameSet set) {
      SimpleValue[] dests = new SimpleValue[sourcesLength];
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > sourcesLength; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < sourcesLength; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         dests[i] = (SimpleValue)user;
      }

      return dests;
   }

   protected void arraySetterHelper(boolean[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(float[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(double[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(byte[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(short[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(int[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(long[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(BigDecimal[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(BigInteger[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(String[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(byte[][] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(GDate[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set((GDateSpecification)sources[i]);
      }

   }

   protected void arraySetterHelper(GDuration[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set((GDurationSpecification)sources[i]);
      }

   }

   protected void arraySetterHelper(Calendar[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(Date[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(QName[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(StringEnumAbstractBase[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(List[] sources, QName elemName) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(elemName); m > n; --m) {
         store.remove_element(elemName, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(elemName, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void unionArraySetterHelper(Object[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).objectSet(sources[i]);
      }

   }

   protected void arraySetterHelper(boolean[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(float[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(double[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(byte[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(short[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(int[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(long[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(BigDecimal[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(BigInteger[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(String[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(byte[][] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(GDate[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set((GDateSpecification)sources[i]);
      }

   }

   protected void arraySetterHelper(GDuration[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set((GDurationSpecification)sources[i]);
      }

   }

   protected void arraySetterHelper(Calendar[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(Date[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(QName[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(StringEnumAbstractBase[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(List[] sources, QName elemName, QNameSet set) {
      int n = sources == null ? 0 : sources.length;
      TypeStore store = this.get_store();

      int m;
      for(m = store.count_elements(set); m > n; --m) {
         store.remove_element(set, m - 1);
      }

      for(int i = 0; i < n; ++i) {
         TypeStoreUser user;
         if (i >= m) {
            user = store.add_element_user(elemName);
         } else {
            user = store.find_element_user(set, i);
         }

         ((XmlObjectBase)user).set(sources[i]);
      }

   }

   protected void arraySetterHelper(XmlObject[] sources, QName elemName) {
      TypeStore store = this.get_store();
      int i;
      if (sources != null && sources.length != 0) {
         int m = store.count_elements(elemName);
         int startSrc = 0;
         int startDest = 0;

         for(i = 0; i < sources.length; ++i) {
            if (!sources[i].isImmutable()) {
               XmlCursor c = sources[i].newCursor();
               if (c.toParent() && c.getObject() == this) {
                  c.dispose();
                  break;
               }

               c.dispose();
            }
         }

         TypeStoreUser user;
         int j;
         if (i < sources.length) {
            TypeStoreUser current = store.find_element_user((QName)elemName, 0);
            if (current == sources[i]) {
               int j = false;

               for(j = 0; j < i; ++j) {
                  user = store.insert_element_user(elemName, j);
                  ((XmlObjectBase)user).set(sources[j]);
               }

               ++i;
               ++j;

               while(i < sources.length) {
                  XmlCursor c = sources[i].isImmutable() ? null : sources[i].newCursor();
                  if (c != null && c.toParent() && c.getObject() == this) {
                     c.dispose();
                     current = store.find_element_user(elemName, j);
                     if (current != sources[i]) {
                        break;
                     }
                  } else {
                     c.dispose();
                     TypeStoreUser user = store.insert_element_user(elemName, j);
                     ((XmlObjectBase)user).set(sources[i]);
                  }

                  ++i;
                  ++j;
               }

               startDest = j;
               startSrc = i;
               m = store.count_elements(elemName);
            }
         }

         int n;
         for(n = i; n < sources.length; ++n) {
            TypeStoreUser user = store.add_element_user(elemName);
            ((XmlObjectBase)user).set(sources[n]);
         }

         for(n = i; m > n - startSrc + startDest; --m) {
            store.remove_element(elemName, m - 1);
         }

         i = startSrc;

         for(j = startDest; i < n; ++j) {
            if (j >= m) {
               user = store.add_element_user(elemName);
            } else {
               user = store.find_element_user(elemName, j);
            }

            ((XmlObjectBase)user).set(sources[i]);
            ++i;
         }

      } else {
         for(i = store.count_elements(elemName); i > 0; --i) {
            store.remove_element((QName)elemName, 0);
         }

      }
   }

   protected void arraySetterHelper(XmlObject[] sources, QName elemName, QNameSet set) {
      TypeStore store = this.get_store();
      int i;
      if (sources != null && sources.length != 0) {
         int m = store.count_elements(set);
         int startSrc = 0;
         int startDest = 0;

         for(i = 0; i < sources.length; ++i) {
            if (!sources[i].isImmutable()) {
               XmlCursor c = sources[i].newCursor();
               if (c.toParent() && c.getObject() == this) {
                  c.dispose();
                  break;
               }

               c.dispose();
            }
         }

         TypeStoreUser user;
         int j;
         if (i < sources.length) {
            TypeStoreUser current = store.find_element_user((QNameSet)set, 0);
            if (current == sources[i]) {
               int j = false;

               for(j = 0; j < i; ++j) {
                  user = store.insert_element_user(set, elemName, j);
                  ((XmlObjectBase)user).set(sources[j]);
               }

               ++i;
               ++j;

               while(i < sources.length) {
                  XmlCursor c = sources[i].isImmutable() ? null : sources[i].newCursor();
                  if (c != null && c.toParent() && c.getObject() == this) {
                     c.dispose();
                     current = store.find_element_user(set, j);
                     if (current != sources[i]) {
                        break;
                     }
                  } else {
                     c.dispose();
                     TypeStoreUser user = store.insert_element_user(set, elemName, j);
                     ((XmlObjectBase)user).set(sources[i]);
                  }

                  ++i;
                  ++j;
               }

               startDest = j;
               startSrc = i;
               m = store.count_elements(elemName);
            }
         }

         int n;
         for(n = i; n < sources.length; ++n) {
            TypeStoreUser user = store.add_element_user(elemName);
            ((XmlObjectBase)user).set(sources[n]);
         }

         for(n = i; m > n - startSrc + startDest; --m) {
            store.remove_element(set, m - 1);
         }

         i = startSrc;

         for(j = startDest; i < n; ++j) {
            if (j >= m) {
               user = store.add_element_user(elemName);
            } else {
               user = store.find_element_user(set, j);
            }

            ((XmlObjectBase)user).set(sources[i]);
            ++i;
         }

      } else {
         for(i = store.count_elements(set); i > 0; --i) {
            store.remove_element((QNameSet)set, 0);
         }

      }
   }
}
