package weblogic.utils.codegen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.utils.StringUtils;

public abstract class AttributeBinderBase implements AttributeBinder, AttributeBinderFactory {
   private Map primitiveData = new HashMap();
   private Map beanData = new HashMap();

   protected AttributeBinderBase() {
   }

   public AttributeBinder getAttributeBinder() {
      return this;
   }

   public AttributeBinder bindAttribute(String name, Object o) {
      Map data = o instanceof AttributeBinder ? this.beanData : this.primitiveData;
      Object prev = data.put(name, o);
      if (prev == null) {
         return this;
      } else {
         List l = listify(prev);
         l.add(o);
         data.put(name, l);
         return this;
      }
   }

   public Object findAttribute(String name) {
      Object o = this.primitiveData.get(name);
      return o == null ? this.beanData.get(name) : o;
   }

   public void toXML(PrintStream out, String parentTag) {
      out.print("<");
      out.print(parentTag);
      Iterator i = this.primitiveData.entrySet().iterator();

      Map.Entry e;
      while(i.hasNext()) {
         e = (Map.Entry)i.next();
         out.print('\n');
         out.print(e.getKey());
         out.print("=\"");
         out.print(e.getValue());
         out.print('"');
      }

      if (this.beanData.size() == 0) {
         out.print("/>\n");
      } else {
         out.print(">\n");
         i = this.beanData.entrySet().iterator();

         while(true) {
            while(i.hasNext()) {
               e = (Map.Entry)i.next();
               out.print('\n');
               String key = (String)e.getKey();
               Object v = e.getValue();
               if (v instanceof List) {
                  Iterator j = ((List)v).iterator();

                  while(j.hasNext()) {
                     Object next = j.next();
                     if (next instanceof AttributeBinderBase) {
                        AttributeBinderBase ab = (AttributeBinderBase)next;
                        ab.toXML(out, key);
                     }
                  }
               } else if (v instanceof AttributeBinderBase) {
                  ((AttributeBinderBase)v).toXML(out, key);
               } else {
                  System.out.println("***unexpected value** " + v + " class=" + v.getClass().getName());
               }
            }

            out.print("</");
            out.print(parentTag);
            out.print(">\n");
            return;
         }
      }
   }

   private static List listify(Object o) {
      if (o instanceof List) {
         return (List)o;
      } else {
         List l = new ArrayList(2);
         l.add(o);
         return l;
      }
   }

   protected final String[] parseStringArrayInitializer(String o) {
      return o.indexOf("|") != -1 ? StringUtils.splitCompletely(o, "|") : StringUtils.splitCompletely(o, ",");
   }

   protected final Properties parsePropertiesInitializer(String o) {
      String[] list = StringUtils.splitCompletely(o, ";");
      Properties props = new Properties();

      for(int i = 0; i < list.length; ++i) {
         String[] pair = StringUtils.split(list[i], '=');
         props.setProperty(pair[0], pair[1]);
      }

      return props;
   }

   protected final Hashtable parseHashtableInitializer(String o) {
      String[] list = StringUtils.splitCompletely(o, ";");
      Hashtable h = new Hashtable();

      for(int i = 0; i < list.length; ++i) {
         String[] pair = StringUtils.split(list[i], '=');
         h.put(pair[0], pair[1]);
      }

      return h;
   }

   protected final HashSet parseHashSetInitializer(String o) {
      String[] list = StringUtils.splitCompletely(o, ";");
      HashSet h = new HashSet();

      for(int i = 0; i < list.length; ++i) {
         h.add(list[i]);
      }

      return h;
   }

   protected final Map parseMapInitializer(String o) {
      String[] list = StringUtils.splitCompletely(o, ";");
      HashMap h = new HashMap();

      for(int i = 0; i < list.length; ++i) {
         String[] pair = StringUtils.split(list[i], '=');
         h.put(pair[0], pair[1]);
      }

      return h;
   }
}
