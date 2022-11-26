package com.bea.xbean.values;

import com.bea.xbean.common.PrefixResolver;
import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.common.XMLChar;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlSimpleList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlListImpl extends XmlObjectBase implements XmlAnySimpleType {
   private SchemaType _schemaType;
   private XmlSimpleList _value;
   private XmlSimpleList _jvalue;
   private static final String[] EMPTY_STRINGARRAY = new String[0];

   public XmlListImpl(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   private static String nullAsEmpty(String s) {
      return s == null ? "" : s;
   }

   private static String compute_list_text(List xList) {
      if (xList.size() == 0) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append(nullAsEmpty(((SimpleValue)xList.get(0)).getStringValue()));

         for(int i = 1; i < xList.size(); ++i) {
            sb.append(' ');
            sb.append(nullAsEmpty(((SimpleValue)xList.get(i)).getStringValue()));
         }

         return sb.toString();
      }
   }

   protected String compute_text(NamespaceManager nsm) {
      return compute_list_text(this._value);
   }

   protected boolean is_defaultable_ws(String v) {
      try {
         XmlSimpleList savedValue = this._value;
         this.set_text(v);
         this._value = savedValue;
         return false;
      } catch (XmlValueOutOfRangeException var3) {
         return true;
      }
   }

   protected void set_text(String s) {
      if (this._validateOnSet() && !this._schemaType.matchPatternFacet(s)) {
         throw new XmlValueOutOfRangeException("cvc-datatype-valid.1.1", new Object[]{"list", s, QNameHelper.readable(this._schemaType)});
      } else {
         SchemaType itemType = this._schemaType.getListItemType();
         XmlSimpleList newval = lex(s, itemType, _voorVc, this.has_store() ? this.get_store() : null);
         if (this._validateOnSet()) {
            validateValue(newval, this._schemaType, _voorVc);
         }

         this._value = newval;
         this._jvalue = null;
      }
   }

   public static String[] split_list(String s) {
      if (s.length() == 0) {
         return EMPTY_STRINGARRAY;
      } else {
         List result = new ArrayList();
         int i = 0;
         int start = false;

         while(true) {
            while(i >= s.length() || !XMLChar.isSpace(s.charAt(i))) {
               if (i >= s.length()) {
                  return (String[])((String[])result.toArray(EMPTY_STRINGARRAY));
               }

               int start;
               for(start = i; i < s.length() && !XMLChar.isSpace(s.charAt(i)); ++i) {
               }

               result.add(s.substring(start, i));
            }

            ++i;
         }
      }
   }

   public static XmlSimpleList lex(String s, SchemaType itemType, ValidationContext ctx, PrefixResolver resolver) {
      String[] parts = split_list(s);
      XmlAnySimpleType[] newArray = new XmlAnySimpleType[parts.length];
      boolean pushed = false;
      if (resolver != null) {
         NamespaceContext.push(new NamespaceContext(resolver));
         pushed = true;
      }

      int i = false;

      try {
         for(int i = 0; i < parts.length; ++i) {
            try {
               newArray[i] = itemType.newValue(parts[i]);
            } catch (XmlValueOutOfRangeException var12) {
               ctx.invalid("list", new Object[]{"item '" + parts[i] + "' is not a valid value of " + QNameHelper.readable(itemType)});
            }
         }
      } finally {
         if (pushed) {
            NamespaceContext.pop();
         }

      }

      return new XmlSimpleList(Arrays.asList(newArray));
   }

   protected void set_nil() {
      this._value = null;
   }

   public List xgetListValue() {
      this.check_dated();
      return this._value;
   }

   public List getListValue() {
      this.check_dated();
      if (this._value == null) {
         return null;
      } else if (this._jvalue != null) {
         return this._jvalue;
      } else {
         List javaResult = new ArrayList();

         for(int i = 0; i < this._value.size(); ++i) {
            javaResult.add(java_value((XmlObject)this._value.get(i)));
         }

         this._jvalue = new XmlSimpleList(javaResult);
         return this._jvalue;
      }
   }

   private static boolean permits_inner_space(XmlObject obj) {
      switch (((SimpleValue)obj).instanceType().getPrimitiveType().getBuiltinTypeCode()) {
         case 1:
         case 2:
         case 6:
         case 12:
            return true;
         default:
            return false;
      }
   }

   private static boolean contains_white_space(String s) {
      return s.indexOf(32) >= 0 || s.indexOf(9) >= 0 || s.indexOf(10) >= 0 || s.indexOf(13) >= 0;
   }

   public void set_list(List list) {
      SchemaType itemType = this._schemaType.getListItemType();
      boolean pushed = false;
      if (this.has_store()) {
         NamespaceContext.push(new NamespaceContext(this.get_store()));
         pushed = true;
      }

      XmlSimpleList xList;
      try {
         XmlAnySimpleType[] newval = new XmlAnySimpleType[list.size()];
         int i = 0;

         while(true) {
            if (i >= list.size()) {
               xList = new XmlSimpleList(Arrays.asList(newval));
               break;
            }

            Object entry = list.get(i);
            if (entry instanceof XmlObject && permits_inner_space((XmlObject)list.get(i))) {
               String stringrep = list.get(i).toString();
               if (contains_white_space(stringrep)) {
                  throw new XmlValueOutOfRangeException();
               }
            }

            newval[i] = itemType.newValue(entry);
            ++i;
         }
      } finally {
         if (pushed) {
            NamespaceContext.pop();
         }

      }

      if (this._validateOnSet()) {
         validateValue(xList, this._schemaType, _voorVc);
      }

      this._value = xList;
      this._jvalue = null;
   }

   public static void validateValue(XmlSimpleList items, SchemaType sType, ValidationContext context) {
      XmlObject[] enumvals = sType.getEnumerationValues();
      if (enumvals != null) {
         int i = 0;

         while(true) {
            if (i >= enumvals.length) {
               context.invalid("cvc-enumeration-valid", new Object[]{"list", items, QNameHelper.readable(sType)});
               break;
            }

            if (equal_xmlLists(items, ((XmlObjectBase)enumvals[i]).xlistValue())) {
               break;
            }

            ++i;
         }
      }

      int i;
      XmlAnySimpleType o;
      if ((o = sType.getFacet(0)) != null && (i = ((SimpleValue)o).getIntValue()) != items.size()) {
         context.invalid("cvc-length-valid.2", new Object[]{items, new Integer(items.size()), new Integer(i), QNameHelper.readable(sType)});
      }

      if ((o = sType.getFacet(1)) != null && (i = ((SimpleValue)o).getIntValue()) > items.size()) {
         context.invalid("cvc-minLength-valid.2", new Object[]{items, new Integer(items.size()), new Integer(i), QNameHelper.readable(sType)});
      }

      if ((o = sType.getFacet(2)) != null && (i = ((SimpleValue)o).getIntValue()) < items.size()) {
         context.invalid("cvc-maxLength-valid.2", new Object[]{items, new Integer(items.size()), new Integer(i), QNameHelper.readable(sType)});
      }

   }

   protected boolean equal_to(XmlObject obj) {
      return equal_xmlLists(this._value, ((XmlObjectBase)obj).xlistValue());
   }

   private static boolean equal_xmlLists(List a, List b) {
      if (a.size() != b.size()) {
         return false;
      } else {
         for(int i = 0; i < a.size(); ++i) {
            if (!a.get(i).equals(b.get(i))) {
               return false;
            }
         }

         return true;
      }
   }

   protected int value_hash_code() {
      if (this._value == null) {
         return 0;
      } else {
         int hash = this._value.size();
         int incr = this._value.size() / 9;
         if (incr < 1) {
            incr = 1;
         }

         int i;
         for(i = 0; i < this._value.size(); i += incr) {
            hash *= 19;
            hash += this._value.get(i).hashCode();
         }

         if (i < this._value.size()) {
            hash *= 19;
            hash += this._value.get(i).hashCode();
         }

         return hash;
      }
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateValue((XmlSimpleList)this.xlistValue(), this.schemaType(), ctx);
   }
}
