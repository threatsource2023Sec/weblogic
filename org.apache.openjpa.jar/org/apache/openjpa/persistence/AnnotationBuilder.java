package org.apache.openjpa.persistence;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import serp.util.Strings;

public class AnnotationBuilder {
   private Class type;
   private List components = new ArrayList();

   protected AnnotationBuilder(Class type) {
      this.type = type;
   }

   public Class getType() {
      return this.type;
   }

   public AnnotationBuilder add(String key, String val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, boolean val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, int val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, Class val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, EnumSet val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, Enum val) {
      return this.doAdd(key, val);
   }

   public AnnotationBuilder add(String key, AnnotationBuilder val) {
      if (null == val) {
         return this;
      } else {
         AnnotationEntry ae = this.find(key);
         if (null == ae) {
            this.doAdd(key, val);
         } else {
            Object list;
            if (ae.value instanceof List) {
               list = (List)ae.value;
            } else {
               if (!(ae.value instanceof AnnotationBuilder)) {
                  throw new IllegalArgumentException("Unexpected type: " + ae.value);
               }

               list = new ArrayList();
               ((List)list).add((AnnotationBuilder)ae.value);
               ae.value = list;
            }

            ((List)list).add(val);
         }

         return this;
      }
   }

   public boolean hasComponents() {
      return this.components.size() > 0;
   }

   private AnnotationBuilder doAdd(String key, Object val) {
      if (null != val) {
         this.components.add(new AnnotationEntry(key, val));
      }

      return this;
   }

   private AnnotationEntry find(String key) {
      Iterator i$ = this.components.iterator();

      AnnotationEntry ae;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         ae = (AnnotationEntry)i$.next();
      } while(!StringUtils.equals(ae.key, key));

      return ae;
   }

   static String enumToString(Enum e) {
      StringBuilder sb = new StringBuilder();
      sb.append(Strings.getClassName(e.getClass())).append(".").append(e);
      return sb.toString();
   }

   static String enumSetToString(EnumSet set) {
      StringBuilder sb = new StringBuilder();
      Iterator i = set.iterator();

      while(i.hasNext()) {
         Object e = i.next();
         sb.append(Strings.getClassName(e.getClass())).append(".").append(e);
         if (i.hasNext()) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   protected void toString(StringBuilder sb) {
      sb.append("@").append(Strings.getClassName(this.type));
      if (this.components.size() != 0) {
         sb.append("(");
         Iterator i = this.components.iterator();

         while(i.hasNext()) {
            AnnotationEntry e = (AnnotationEntry)i.next();
            e.toString(sb);
            if (i.hasNext()) {
               sb.append(", ");
            }
         }

         sb.append(")");
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      this.toString(sb);
      return sb.toString();
   }

   class AnnotationEntry {
      String key;
      Object value;

      AnnotationEntry(String key, Object value) {
         this.key = key;
         this.value = value;
      }

      void toString(StringBuilder sb) {
         if (null != this.key) {
            sb.append(this.key).append("=");
         }

         List.class.getTypeParameters();
         if (this.value instanceof List) {
            sb.append("{");
            List l = (List)this.value;
            Iterator i = l.iterator();

            while(i.hasNext()) {
               AnnotationBuilder ab = (AnnotationBuilder)i.next();
               sb.append(ab.toString());
               if (i.hasNext()) {
                  sb.append(", ");
               }
            }

            sb.append("}");
         } else if (this.value instanceof Class) {
            String cls = ((Class)this.value).getName().replace('$', '.');
            sb.append(cls).append(".class");
         } else if (this.value instanceof String) {
            sb.append('"').append(this.value).append('"');
         } else if (this.value instanceof Enum) {
            sb.append(AnnotationBuilder.enumToString((Enum)this.value));
         } else if (this.value instanceof EnumSet) {
            sb.append(AnnotationBuilder.enumSetToString((EnumSet)this.value));
         } else {
            sb.append(this.value);
         }

      }
   }
}
