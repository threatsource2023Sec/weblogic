package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.XMLMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;
import serp.util.Strings;

public abstract class AbstractExpressionBuilder {
   protected static final int EX_USER = 0;
   protected static final int EX_FATAL = 1;
   protected static final int EX_UNSUPPORTED = 2;
   protected static final Class TYPE_OBJECT = Object.class;
   protected static final Class TYPE_STRING = String.class;
   protected static final Class TYPE_CHAR_OBJ = Character.class;
   protected static final Class TYPE_NUMBER = Number.class;
   protected static final Class TYPE_COLLECTION = Collection.class;
   protected static final Class TYPE_MAP = Map.class;
   protected static final int CONTAINS_TYPE_ELEMENT = 1;
   protected static final int CONTAINS_TYPE_KEY = 2;
   protected static final int CONTAINS_TYPE_VALUE = 3;
   private static final Localizer _loc = Localizer.forPackage(AbstractExpressionBuilder.class);
   protected final Resolver resolver;
   protected ExpressionFactory factory;
   private final Set _accessPath = new HashSet();
   private Map _seenVars = null;
   private Set _boundVars = null;

   public AbstractExpressionBuilder(ExpressionFactory factory, Resolver resolver) {
      this.factory = factory;
      this.resolver = resolver;
   }

   protected abstract ClassLoader getClassLoader();

   protected OpenJPAException parseException(int e, String token, Object[] args, Exception nest) {
      String argStr;
      if (args == null) {
         argStr = this.getLocalizer().get(token).getMessage();
      } else {
         argStr = this.getLocalizer().get(token, args).getMessage();
      }

      Localizer.Message msg = _loc.get("parse-error", argStr, this.currentQuery());
      switch (e) {
         case 1:
            throw new InternalException(msg, nest);
         case 2:
            throw new UnsupportedException(msg, nest);
         default:
            throw new UserException(msg, nest);
      }
   }

   protected ClassMetaData addAccessPath(ClassMetaData meta) {
      this._accessPath.add(meta);
      return meta;
   }

   protected ClassMetaData[] getAccessPath() {
      return (ClassMetaData[])((ClassMetaData[])this._accessPath.toArray(new ClassMetaData[this._accessPath.size()]));
   }

   protected boolean isBound(Value var) {
      return this._boundVars != null && this._boundVars.contains(var);
   }

   protected void bind(Value var) {
      if (this._boundVars == null) {
         this._boundVars = new HashSet();
      }

      this._boundVars.add(var);
   }

   protected Value getVariable(String id, boolean bind) {
      if (this.isSeenVariable(id)) {
         return (Value)this._seenVars.get(id);
      } else {
         Class type = this.getDeclaredVariableType(id);
         ClassMetaData meta = null;
         if (type == null) {
            type = TYPE_OBJECT;
         } else {
            meta = this.getMetaData(type, false);
         }

         if (meta != null) {
            this._accessPath.add(meta);
         }

         Value var;
         if (bind) {
            var = this.factory.newBoundVariable(id, type);
         } else {
            var = this.factory.newUnboundVariable(id, type);
         }

         var.setMetaData(meta);
         if (this._seenVars == null) {
            this._seenVars = new HashMap();
         }

         this._seenVars.put(id, var);
         return var;
      }
   }

   protected void assertUnboundVariablesValid() {
      if (this._seenVars != null) {
         Iterator itr = this._seenVars.entrySet().iterator();

         Map.Entry entry;
         Value var;
         do {
            if (!itr.hasNext()) {
               return;
            }

            entry = (Map.Entry)itr.next();
            var = (Value)entry.getValue();
         } while(var.getMetaData() != null || this.isBound(var) || this.isDeclaredVariable((String)entry.getKey()));

         throw this.parseException(0, "not-unbound-var", new Object[]{entry.getKey()}, (Exception)null);
      }
   }

   protected abstract boolean isDeclaredVariable(String var1);

   protected boolean isSeenVariable(String id) {
      return this._seenVars != null && this._seenVars.containsKey(id);
   }

   protected ClassMetaData getMetaData(Class c, boolean required) {
      return this.getMetaData(c, required, this.getClassLoader());
   }

   protected ClassMetaData getMetaData(Class c, boolean required, ClassLoader loader) {
      return this.resolver.getConfiguration().getMetaDataRepositoryInstance().getMetaData(c, loader, required);
   }

   protected Value traversePath(Path path, String field) {
      return this.traversePath(path, field, false, false);
   }

   protected Value traverseXPath(Path path, String field) {
      XMLMetaData meta = path.getXmlMapping();
      if (meta.getFieldMapping(field) == null) {
         throw this.parseException(0, "no-field", new Object[]{meta.getType(), field}, (Exception)null);
      } else {
         int type = meta.getFieldMapping(field).getTypeCode();
         switch (type) {
            case 11:
            case 12:
            case 13:
               throw new UserException(_loc.get("collection-valued-path", (Object)field));
            default:
               path.get(meta, field);
               return path;
         }
      }
   }

   protected Value traversePath(Path path, String field, boolean pcOnly, boolean allowNull) {
      ClassMetaData meta = path.getMetaData();
      if (meta == null) {
         throw this.parseException(0, "path-no-meta", new Object[]{field, path.getType()}, (Exception)null);
      } else {
         FieldMetaData fmd = meta.getField(field);
         if (fmd == null) {
            Object val = this.traverseStaticField(meta.getDescribedType(), field);
            if (val == null) {
               throw this.parseException(0, "no-field", new Object[]{meta.getDescribedType(), field}, (Exception)null);
            } else {
               return this.factory.newLiteral(val, 0);
            }
         } else {
            if (fmd.isEmbedded()) {
               meta = fmd.getEmbeddedMetaData();
            } else {
               meta = fmd.getDeclaredTypeMetaData();
            }

            if (meta != null) {
               this.addAccessPath(meta);
               path.setMetaData(meta);
            } else {
               XMLMetaData xmlmeta = fmd.getRepository().getXMLMetaData(fmd);
               if (xmlmeta != null) {
                  path.get(fmd, xmlmeta);
                  return path;
               }
            }

            if (meta != null || !pcOnly) {
               path.get(fmd, allowNull);
            }

            return path;
         }
      }
   }

   protected Object traverseStaticField(Class cls, String field) {
      try {
         return cls.getField(field).get((Object)null);
      } catch (Exception var4) {
         return null;
      }
   }

   protected abstract Class getDeclaredVariableType(String var1);

   protected void setImplicitTypes(Value val1, Value val2, Class expected) {
      Class c1 = val1.getType();
      Class c2 = val2.getType();
      boolean o1 = c1 == TYPE_OBJECT;
      boolean o2 = c2 == TYPE_OBJECT;
      if (o1 && !o2) {
         val1.setImplicitType(c2);
         if (val1.getMetaData() == null && !val1.isXPath()) {
            val1.setMetaData(val2.getMetaData());
         }
      } else if (!o1 && o2) {
         val2.setImplicitType(c1);
         if (val2.getMetaData() == null && !val1.isXPath()) {
            val2.setMetaData(val1.getMetaData());
         }
      } else if (o1 && o2 && expected != null) {
         val1.setImplicitType(expected);
         val2.setImplicitType(expected);
      } else if (isNumeric(val1.getType()) != isNumeric(val2.getType())) {
         if (this.resolver.getConfiguration().getCompatibilityInstance().getQuotedNumbersInQueries()) {
            this.convertTypesQuotedNumbers(val1, val2);
         } else {
            this.convertTypes(val1, val2);
         }
      }

   }

   private void convertTypes(Value val1, Value val2) {
      Class t1 = val1.getType();
      Class t2 = val2.getType();
      if (t1 == TYPE_STRING && Filters.wrap(t2) == TYPE_CHAR_OBJ && !(val2 instanceof Path)) {
         val2.setImplicitType(String.class);
      } else if (t2 == TYPE_STRING && Filters.wrap(t1) == TYPE_CHAR_OBJ && !(val1 instanceof Path)) {
         val1.setImplicitType(String.class);
      } else if (t1 == TYPE_STRING && val1 instanceof Literal && ((String)((Literal)val1).getValue()).length() == 1) {
         val1.setImplicitType(Character.class);
      } else if (t2 == TYPE_STRING && val2 instanceof Literal && ((String)((Literal)val2).getValue()).length() == 1) {
         val2.setImplicitType(Character.class);
      } else {
         String left;
         if (val1 instanceof Path && ((Path)val1).last() != null) {
            left = _loc.get("non-numeric-path", ((Path)val1).last().getName(), t1.getName()).getMessage();
         } else {
            left = _loc.get("non-numeric-value", (Object)t1.getName()).getMessage();
         }

         String right;
         if (val2 instanceof Path && ((Path)val2).last() != null) {
            right = _loc.get("non-numeric-path", ((Path)val2).last().getName(), t2.getName()).getMessage();
         } else {
            right = _loc.get("non-numeric-value", (Object)t2.getName()).getMessage();
         }

         throw new UserException(_loc.get("non-numeric-comparison", left, right));
      }
   }

   private void convertTypesQuotedNumbers(Value val1, Value val2) {
      Class t1 = val1.getType();
      Class t2 = val2.getType();
      String s;
      if (t1 == TYPE_STRING && val1 instanceof Literal && ((Literal)val1).getParseType() == 4) {
         s = (String)((Literal)val1).getValue();
         if (s.length() > 1) {
            ((Literal)val1).setValue(s.substring(0, 1));
            val1.setImplicitType(Character.TYPE);
         }
      }

      if (t2 == TYPE_STRING && val2 instanceof Literal && ((Literal)val2).getParseType() == 4) {
         s = (String)((Literal)val2).getValue();
         if (s.length() > 1) {
            ((Literal)val2).setValue(s.substring(0, 1));
            val2.setImplicitType(Character.TYPE);
         }
      }

      if (t1 == TYPE_STRING && val1 instanceof Literal && ((Literal)val1).getParseType() == 3) {
         s = (String)((Literal)val1).getValue();
         ((Literal)val1).setValue(Strings.parse(s, Filters.wrap(t2)));
         val1.setImplicitType(t2);
      }

      if (t2 == TYPE_STRING && val2 instanceof Literal && ((Literal)val2).getParseType() == 3) {
         s = (String)((Literal)val2).getValue();
         ((Literal)val2).setValue(Strings.parse(s, Filters.wrap(t1)));
         val2.setImplicitType(t1);
      }

   }

   private static boolean isNumeric(Class type) {
      type = Filters.wrap(type);
      return Number.class.isAssignableFrom(type) || type == Character.TYPE || type == TYPE_CHAR_OBJ;
   }

   protected void setImplicitContainsTypes(Value val1, Value val2, int op) {
      if (val1.getType() == TYPE_OBJECT) {
         if (op == 1) {
            val1.setImplicitType(Collection.class);
         } else {
            val1.setImplicitType(Map.class);
         }
      }

      if (val2.getType() == TYPE_OBJECT && val1 instanceof Path) {
         FieldMetaData fmd = ((Path)val1).last();
         if (fmd != null) {
            ClassMetaData meta;
            if (op != 1 && op != 3) {
               val2.setImplicitType(fmd.getKey().getDeclaredType());
               meta = fmd.getKey().getDeclaredTypeMetaData();
               if (meta != null) {
                  val2.setMetaData(meta);
                  this.addAccessPath(meta);
               }
            } else {
               val2.setImplicitType(fmd.getElement().getDeclaredType());
               meta = fmd.getElement().getDeclaredTypeMetaData();
               if (meta != null) {
                  val2.setMetaData(meta);
                  this.addAccessPath(meta);
               }
            }
         }
      }

   }

   protected static void setImplicitType(Value val, Class expected) {
      if (val.getType() == TYPE_OBJECT) {
         val.setImplicitType(expected);
      }

   }

   protected abstract Localizer getLocalizer();

   protected abstract String currentQuery();
}
