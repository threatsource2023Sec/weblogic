package javolution.xml;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.realtime.MemoryArea;
import javolution.lang.ClassInitializer;
import javolution.lang.Reflection;
import javolution.lang.Text;
import javolution.lang.TypeFormat;
import javolution.util.FastCollection;
import javolution.util.FastComparator;
import javolution.util.FastMap;
import javolution.util.FastTable;
import javolution.util.LocalMap;

public abstract class XmlFormat {
   private static final FastMap DEFAULT_INSTANCES = (new FastMap()).setShared(true);
   private static final LocalMap DYNAMIC_INSTANCES = new LocalMap();
   private static final LocalMap CLASS_TO_FORMAT = new LocalMap();
   private static final LocalMap CLASS_TO_ALIAS = new LocalMap();
   private static FastMap CLASS_NAME_TO_CLASS = (new FastMap()).setShared(true);
   private static FastMap LOCAL_NAME_TO_URI_CLASS = (new FastMap()).setShared(true);
   private static final LocalMap ALIAS_TO_CLASS = new LocalMap();
   public static final XmlFormat DEFAULT_XML = new XmlFormat() {
      public void format(Object var1, XmlElement var2) {
      }

      public Object parse(XmlElement var1) {
         return var1.object();
      }
   };
   static final Null NULL = new Null();
   static final XmlFormat NULL_XML;
   public static final XmlFormat CLASS_XML;
   public static final XmlFormat STRING_XML;
   public static final XmlFormat TEXT_XML;
   public static final XmlFormat APPENDABLE_XML;
   static final XmlFormat FAST_COMPARATOR_XML;
   public static final XmlFormat COLLECTION_XML;
   public static final XmlFormat MAP_XML;
   public static final XmlFormat BOOLEAN_XML;
   public static final XmlFormat BYTE_XML;
   public static final XmlFormat CHARACTER_XML;
   public static final XmlFormat INTEGER_XML;
   public static final XmlFormat LONG_XML;
   public static final XmlFormat SHORT_XML;
   public static final XmlFormat FLOAT_XML;
   public static final XmlFormat DOUBLE_XML;
   private static Reflection.Method STRING_INTERN;

   protected XmlFormat() {
   }

   protected XmlFormat(Class var1) {
      if (DEFAULT_INSTANCES.put(var1, this) != null) {
         throw new IllegalStateException("XmlFormat already mapped to " + var1);
      }
   }

   protected XmlFormat(String var1) {
      this(classForName(var1));
   }

   private static Class classForName(String var0) {
      Class var1 = Reflection.getClass(var0);
      if (var1 == null) {
         throw new XmlException("Class: " + var1 + " not found");
      } else {
         return var1;
      }
   }

   public static void setFormat(Class var0, XmlFormat var1) {
      DYNAMIC_INSTANCES.put(var0, var1);
      CLASS_TO_FORMAT.clear();
   }

   public static XmlFormat getInstance(Class var0) {
      Object var1 = CLASS_TO_FORMAT.get(var0);
      return var1 != null ? (XmlFormat)var1 : searchInstanceFor(var0, false);
   }

   public static void setAlias(Class var0, String var1) {
      CLASS_TO_ALIAS.put(var0, var1);
      ALIAS_TO_CLASS.put(var1, var0);
   }

   public String defaultName() {
      return null;
   }

   public String identifier(boolean var1) {
      return var1 ? "j:ref" : "j:id";
   }

   public Object allocate(XmlElement var1) {
      return null;
   }

   public abstract void format(Object var1, XmlElement var2);

   public abstract Object parse(XmlElement var1);

   private static XmlFormat searchInstanceFor(Class var0, boolean var1) {
      ClassInitializer.initialize(var0);
      XmlFormat var2 = null;
      Class var3 = null;
      FastCollection var4 = (FastCollection)DYNAMIC_INSTANCES.entrySet();
      FastCollection.Record var5 = var4.head();
      FastCollection.Record var6 = var4.tail();

      while(true) {
         Map.Entry var7;
         Class var8;
         do {
            do {
               do {
                  if ((var5 = var5.getNext()) == var6) {
                     FastMap.Entry var9 = DEFAULT_INSTANCES.head();
                     FastMap.Entry var10 = DEFAULT_INSTANCES.tail();

                     while(true) {
                        do {
                           do {
                              do {
                                 Class var11;
                                 do {
                                    if ((var9 = var9.getNext()) == var10) {
                                       if (var2 == null) {
                                          var2 = DEFAULT_XML;
                                       }

                                       if (!var1) {
                                          CLASS_TO_FORMAT.put(var0, var2);
                                       }

                                       return var2;
                                    }

                                    var11 = (Class)var9.getKey();
                                 } while(var11 == var0 && var1);

                                 var8 = (Class)var9.getKey();
                              } while(var8 == var0 && var1);
                           } while(!var8.isAssignableFrom(var0));
                        } while(var3 != null && (var3 == var8 || !var3.isAssignableFrom(var8)));

                        var3 = var8;
                        var2 = (XmlFormat)var9.getValue();
                     }
                  }

                  var7 = (Map.Entry)var5;
                  var8 = (Class)var7.getKey();
               } while(var8 == var0 && var1);
            } while(!var8.isAssignableFrom(var0));
         } while(var3 != null && !var3.isAssignableFrom(var8));

         var3 = var8;
         var2 = (XmlFormat)var7.getValue();
      }
   }

   static String aliasFor(Class var0) {
      return (String)CLASS_TO_ALIAS.get(var0);
   }

   static Class classFor(CharSequence var0) {
      Class var1 = (Class)CLASS_NAME_TO_CLASS.get(var0);
      return var1 != null ? var1 : searchClassFor(var0);
   }

   private static Class searchClassFor(CharSequence var0) {
      Class var1 = (Class)ALIAS_TO_CLASS.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         String var2 = var0.toString();
         var1 = Reflection.getClass(var2);
         if (var1 == null) {
            throw new XmlException("Class: " + var1 + " not found");
         } else {
            CLASS_NAME_TO_CLASS.put(intern(var2), var1);
            return var1;
         }
      }
   }

   static Class classFor(CharSequence var0, CharSequence var1) {
      FastTable var2 = (FastTable)LOCAL_NAME_TO_URI_CLASS.get(var1);
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.size(); var3 += 2) {
            if (var0.equals(var2.get(var3))) {
               return (Class)var2.get(var3 + 1);
            }
         }
      }

      return searchClassFor(var0, var1);
   }

   private static Class searchClassFor(CharSequence var0, CharSequence var1) {
      String var2 = var0.toString();
      final String var3 = var1.toString();
      if (!var2.startsWith("java:")) {
         throw new XmlException("Invalid package uri (" + var2 + "), the package uri should start with \"java:\"");
      } else {
         String var4 = var2.substring(5) + (var2.length() > 5 ? "." : "") + var3;
         Class var5 = Reflection.getClass(var4);
         if (var5 == null) {
            throw new XmlException("Class: " + var5 + " not found");
         } else {
            FastTable var6 = (FastTable)LOCAL_NAME_TO_URI_CLASS.get(var1);
            if (var6 == null) {
               MemoryArea.getMemoryArea(LOCAL_NAME_TO_URI_CLASS).executeInArea(new Runnable() {
                  public void run() {
                     XmlFormat.access$200().put(XmlFormat.access$100(var3), new FastTable());
                  }
               });
               var6 = (FastTable)LOCAL_NAME_TO_URI_CLASS.get(var1);
            }

            var6.add(intern(var2));
            var6.add(var5);
            return var5;
         }
      }
   }

   private static String intern(String var0) {
      return STRING_INTERN != null ? (String)STRING_INTERN.invoke(var0) : var0;
   }

   static String access$100(String var0) {
      return intern(var0);
   }

   static FastMap access$200() {
      return LOCAL_NAME_TO_URI_CLASS;
   }

   static {
      NULL_XML = new XmlFormat(NULL.getClass()) {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
         }

         public Object parse(XmlElement var1) {
            return null;
         }
      };
      CLASS_TO_ALIAS.putDefault(NULL.getClass(), "null");
      ALIAS_TO_CLASS.putDefault("null", NULL.getClass());
      CLASS_XML = new XmlFormat("java.lang.Class") {
         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("name", ((Class)var1).getName());
         }

         public Object parse(XmlElement var1) {
            Class var2 = Reflection.getClass(var1.getAttribute("name", ""));
            if (var2 == null) {
               throw new XmlException("Class: " + var2 + " not found");
            } else {
               return var2;
            }
         }
      };
      STRING_XML = new XmlFormat("java.lang.String") {
         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (String)var1);
         }

         public Object parse(XmlElement var1) {
            return var1.getAttribute("value", "");
         }
      };
      TEXT_XML = new XmlFormat("javolution.lang.Text") {
         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (CharSequence)((Text)var1));
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            return var2 != null ? Text.valueOf((Object)var2) : Text.EMPTY;
         }
      };
      APPENDABLE_XML = new XmlFormat("java.lang.Appendable") {
         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (CharSequence)Text.valueOf(var1));
         }

         public Object parse(XmlElement var1) {
            Appendable var2 = (Appendable)var1.object();
            CharSequence var3 = var1.getAttribute("value");

            try {
               return var3 != null ? var2.append(var3) : var2;
            } catch (IOException var5) {
               throw new XmlException(var5);
            }
         }
      };
      FAST_COMPARATOR_XML = new XmlFormat("javolution.util.FastComparator") {
         public Object allocate(XmlElement var1) {
            if (var1.objectClass() == FastComparator.DEFAULT.getClass()) {
               return FastComparator.DEFAULT;
            } else if (var1.objectClass() == FastComparator.DIRECT.getClass()) {
               return FastComparator.DIRECT;
            } else if (var1.objectClass() == FastComparator.IDENTITY.getClass()) {
               return FastComparator.IDENTITY;
            } else if (var1.objectClass() == FastComparator.LEXICAL.getClass()) {
               return FastComparator.LEXICAL;
            } else {
               return var1.objectClass() == FastComparator.REHASH.getClass() ? FastComparator.REHASH : null;
            }
         }

         public void format(Object var1, XmlElement var2) {
         }

         public Object parse(XmlElement var1) {
            return var1.object();
         }
      };
      COLLECTION_XML = new XmlFormat("java.util.Collection") {
         public Object allocate(XmlElement var1) {
            return var1.object();
         }

         public void format(Object var1, XmlElement var2) {
            Collection var3 = (Collection)var1;
            if (var3 instanceof FastCollection) {
               FastComparator var4 = ((FastCollection)var3).getValueComparator();
               if (var4 != FastComparator.DEFAULT) {
                  var2.add(var4, "ValueComparator");
               }
            }

            Iterator var5 = var3.iterator();

            while(var5.hasNext()) {
               var2.add(var5.next());
            }

         }

         public Object parse(XmlElement var1) {
            Collection var2 = (Collection)var1.object();
            if (var2 instanceof FastCollection) {
               FastComparator var3 = (FastComparator)var1.get("ValueComparator");
               if (var3 != null) {
                  ((FastCollection)var2).setValueComparator(var3);
               }
            }

            while(var1.hasNext()) {
               var2.add(var1.getNext());
            }

            return var2;
         }
      };
      MAP_XML = new XmlFormat("java.util.Map") {
         public Object allocate(XmlElement var1) {
            return var1.object();
         }

         public void format(Object var1, XmlElement var2) {
            Map var3 = (Map)var1;
            if (var3 instanceof FastMap) {
               FastComparator var4 = ((FastMap)var3).getKeyComparator();
               if (var4 != FastComparator.DEFAULT) {
                  var2.add(var4, "KeyComparator");
               }

               FastComparator var5 = ((FastMap)var3).getValueComparator();
               if (var5 != FastComparator.DEFAULT) {
                  var2.add(var5, "ValueComparator");
               }
            }

            Iterator var7 = var3.entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry var6 = (Map.Entry)var7.next();
               var2.add(var6.getKey(), "Key");
               var2.add(var6.getValue(), "Value");
            }

         }

         public Object parse(XmlElement var1) {
            Map var2 = (Map)var1.object();
            if (var2 instanceof FastMap) {
               FastComparator var3 = (FastComparator)var1.get("KeyComparator");
               if (var3 != null) {
                  ((FastMap)var2).setKeyComparator(var3);
               }

               FastComparator var4 = (FastComparator)var1.get("ValueComparator");
               if (var4 != null) {
                  ((FastMap)var2).setValueComparator(var4);
               }
            }

            while(var1.hasNext()) {
               Object var5 = var1.get("Key");
               Object var6 = var1.get("Value");
               var2.put(var5, var6);
            }

            return var2;
         }
      };
      BOOLEAN_XML = new XmlFormat("java.lang.Boolean") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Boolean)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Boolean(TypeFormat.parseBoolean(var2));
            }
         }
      };
      BYTE_XML = new XmlFormat("java.lang.Byte") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Byte)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Byte(TypeFormat.parseByte(var2));
            }
         }
      };
      CHARACTER_XML = new XmlFormat("java.lang.Character") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (CharSequence)Text.valueOf((Character)var1));
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 != null && var2.length() == 1) {
               return new Character(var2.charAt(0));
            } else {
               throw new XmlException("Missing or invalid value attribute");
            }
         }
      };
      INTEGER_XML = new XmlFormat("java.lang.Integer") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Integer)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Integer(TypeFormat.parseInt(var2));
            }
         }
      };
      LONG_XML = new XmlFormat("java.lang.Long") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Long)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Long(TypeFormat.parseLong(var2));
            }
         }
      };
      SHORT_XML = new XmlFormat("java.lang.Short") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Short)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Short(TypeFormat.parseShort(var2));
            }
         }
      };
      FLOAT_XML = new XmlFormat("java.lang.Float") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Float)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Float(TypeFormat.parseFloat(var2));
            }
         }
      };
      DOUBLE_XML = new XmlFormat("java.lang.Double") {
         public String identifier(boolean var1) {
            return null;
         }

         public void format(Object var1, XmlElement var2) {
            var2.setAttribute("value", (Double)var1);
         }

         public Object parse(XmlElement var1) {
            CharSequence var2 = var1.getAttribute("value");
            if (var2 == null) {
               throw new XmlException("Missing value attribute");
            } else {
               return new Double(TypeFormat.parseDouble(var2));
            }
         }
      };
      STRING_INTERN = Reflection.getMethod("java.lang.String.intern()");
   }

   private static class Null {
      private Null() {
      }

      Null(Object var1) {
         this();
      }
   }
}
