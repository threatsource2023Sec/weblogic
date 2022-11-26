package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class StringAttribute extends AttributeValue {
   private String value;

   public StringAttribute(String value) {
      this.value = value;
   }

   public Type getType() {
      return Type.STRING;
   }

   public String getValue() {
      return this.value;
   }

   public void encodeValue(PrintStream ps) {
      String s = this.getValue();
      if (s.startsWith("<![CDATA[") && s.endsWith("]]>")) {
         ps.print(s);
      } else {
         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
               case '"':
                  ps.print("&quot;");
                  break;
               case '&':
                  ps.print("&amp;");
                  break;
               case '\'':
                  ps.print("&apos;");
                  break;
               case '<':
                  ps.print("&lt;");
                  break;
               case '>':
                  ps.print("&gt;");
                  break;
               default:
                  ps.print(c);
            }
         }
      }

   }

   public String toString() {
      return this.value;
   }

   public int compareTo(StringAttribute other) {
      return this.value.compareTo(other.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StringAttribute)) {
         return false;
      } else {
         StringAttribute other = (StringAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(StringAttribute o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return new Iterator() {
         boolean nextNotCalled = true;

         public boolean hasNext() {
            return this.nextNotCalled;
         }

         public StringAttribute next() {
            this.nextNotCalled = false;
            return StringAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
