package org.antlr.stringtemplate.language;

import antlr.RecognitionException;
import antlr.collections.AST;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateWriter;

public class ASTExpr extends Expr {
   public static final int MISSING = -1;
   public static final String DEFAULT_ATTRIBUTE_NAME = "it";
   public static final String DEFAULT_ATTRIBUTE_NAME_DEPRECATED = "attr";
   public static final String DEFAULT_INDEX_VARIABLE_NAME = "i";
   public static final String DEFAULT_INDEX0_VARIABLE_NAME = "i0";
   public static final String DEFAULT_MAP_VALUE_NAME = "_default_";
   public static final String DEFAULT_MAP_KEY_NAME = "key";
   public static final StringTemplate MAP_KEY_VALUE = new StringTemplate();
   public static final String EMPTY_OPTION = "empty expr option";
   public static final Map defaultOptionValues = new HashMap() {
      {
         this.put("anchor", new StringTemplateAST(34, "true"));
         this.put("wrap", new StringTemplateAST(34, "\n"));
      }
   };
   public static final Set supportedOptions = new HashSet() {
      {
         this.add("anchor");
         this.add("format");
         this.add("null");
         this.add("separator");
         this.add("wrap");
      }
   };
   AST exprTree = null;
   Map options = null;
   String wrapString = null;
   String nullValue = null;
   String separatorString = null;
   String formatString = null;

   public ASTExpr(StringTemplate enclosingTemplate, AST exprTree, Map options) {
      super(enclosingTemplate);
      this.exprTree = exprTree;
      this.options = options;
   }

   public AST getAST() {
      return this.exprTree;
   }

   public int write(StringTemplate self, StringTemplateWriter out) throws IOException {
      if (this.exprTree != null && self != null && out != null) {
         StringTemplateAST anchorAST = (StringTemplateAST)this.getOption("anchor");
         if (anchorAST != null) {
            out.pushAnchorPoint();
         }

         out.pushIndentation(this.getIndentation());
         this.handleExprOptions(self);
         ActionEvaluator eval = new ActionEvaluator(self, this, out);
         int n = 0;

         try {
            n = eval.action(this.exprTree);
         } catch (RecognitionException var7) {
            self.error("can't evaluate tree: " + this.exprTree.toStringList(), var7);
         }

         out.popIndentation();
         if (anchorAST != null) {
            out.popAnchorPoint();
         }

         return n;
      } else {
         return 0;
      }
   }

   protected void handleExprOptions(StringTemplate self) {
      this.formatString = null;
      StringTemplateAST wrapAST = (StringTemplateAST)this.getOption("wrap");
      if (wrapAST != null) {
         this.wrapString = this.evaluateExpression(self, wrapAST);
      }

      StringTemplateAST nullValueAST = (StringTemplateAST)this.getOption("null");
      if (nullValueAST != null) {
         this.nullValue = this.evaluateExpression(self, nullValueAST);
      }

      StringTemplateAST separatorAST = (StringTemplateAST)this.getOption("separator");
      if (separatorAST != null) {
         this.separatorString = this.evaluateExpression(self, separatorAST);
      }

      StringTemplateAST formatAST = (StringTemplateAST)this.getOption("format");
      if (formatAST != null) {
         this.formatString = this.evaluateExpression(self, formatAST);
      }

      if (this.options != null) {
         Iterator it = this.options.keySet().iterator();

         while(it.hasNext()) {
            String option = (String)it.next();
            if (!supportedOptions.contains(option)) {
               self.warning("ignoring unsupported option: " + option);
            }
         }
      }

   }

   public Object applyTemplateToListOfAttributes(StringTemplate self, List attributes, StringTemplate templateToApply) {
      if (attributes != null && templateToApply != null && attributes.size() != 0) {
         Map argumentContext = null;
         List results = new StringTemplate.STAttributeList();

         int numAttributes;
         for(numAttributes = 0; numAttributes < attributes.size(); ++numAttributes) {
            Object o = attributes.get(numAttributes);
            if (o != null) {
               Object o = convertAnythingToIterator(o);
               attributes.set(numAttributes, o);
            }
         }

         numAttributes = attributes.size();
         Map formalArguments = templateToApply.getFormalArguments();
         if (formalArguments != null && formalArguments.size() != 0) {
            Object[] formalArgumentNames = formalArguments.keySet().toArray();
            int i;
            if (formalArgumentNames.length != numAttributes) {
               self.error("number of arguments " + formalArguments.keySet() + " mismatch between attribute list and anonymous" + " template in context " + self.getEnclosingInstanceStackString());
               i = Math.min(formalArgumentNames.length, numAttributes);
               numAttributes = i;
               Object[] newFormalArgumentNames = new Object[i];
               System.arraycopy(formalArgumentNames, 0, newFormalArgumentNames, 0, i);
               formalArgumentNames = newFormalArgumentNames;
            }

            i = 0;

            while(true) {
               argumentContext = new HashMap();
               int numEmpty = 0;

               for(int a = 0; a < numAttributes; ++a) {
                  Iterator it = (Iterator)attributes.get(a);
                  if (it != null && it.hasNext()) {
                     String argName = (String)formalArgumentNames[a];
                     Object iteratedValue = it.next();
                     argumentContext.put(argName, iteratedValue);
                  } else {
                     ++numEmpty;
                  }
               }

               if (numEmpty == numAttributes) {
                  return results;
               }

               argumentContext.put("i", new Integer(i + 1));
               argumentContext.put("i0", new Integer(i));
               StringTemplate embedded = templateToApply.getInstanceOf();
               embedded.setEnclosingInstance(self);
               embedded.setArgumentContext(argumentContext);
               results.add(embedded);
               ++i;
            }
         } else {
            self.error("missing arguments in anonymous template in context " + self.getEnclosingInstanceStackString());
            return null;
         }
      } else {
         return null;
      }
   }

   public Object applyListOfAlternatingTemplates(StringTemplate self, Object attributeValue, List templatesToApply) {
      if (attributeValue != null && templatesToApply != null && templatesToApply.size() != 0) {
         StringTemplate embedded = null;
         Map argumentContext = null;
         attributeValue = convertArrayToList(attributeValue);
         attributeValue = convertAnythingIteratableToIterator(attributeValue);
         if (attributeValue instanceof Iterator) {
            List resultVector = new StringTemplate.STAttributeList();
            Iterator iter = (Iterator)attributeValue;
            int i = 0;

            while(true) {
               Object ithValue;
               while(true) {
                  if (!iter.hasNext()) {
                     if (resultVector.size() == 0) {
                        resultVector = null;
                     }

                     return resultVector;
                  }

                  ithValue = iter.next();
                  if (ithValue != null) {
                     break;
                  }

                  if (this.nullValue != null) {
                     ithValue = this.nullValue;
                     break;
                  }
               }

               int templateIndex = i % templatesToApply.size();
               embedded = (StringTemplate)templatesToApply.get(templateIndex);
               StringTemplateAST args = embedded.getArgumentsAST();
               embedded = embedded.getInstanceOf();
               embedded.setEnclosingInstance(self);
               embedded.setArgumentsAST(args);
               argumentContext = new HashMap();
               Map formalArgs = embedded.getFormalArguments();
               boolean isAnonymous = embedded.getName() == "anonymous";
               this.setSoleFormalArgumentToIthValue(embedded, argumentContext, ithValue);
               if (!isAnonymous || formalArgs == null || formalArgs.size() <= 0) {
                  argumentContext.put("it", ithValue);
                  argumentContext.put("attr", ithValue);
               }

               argumentContext.put("i", new Integer(i + 1));
               argumentContext.put("i0", new Integer(i));
               embedded.setArgumentContext(argumentContext);
               this.evaluateArguments(embedded);
               resultVector.add(embedded);
               ++i;
            }
         } else {
            embedded = (StringTemplate)templatesToApply.get(0);
            argumentContext = new HashMap();
            Map formalArgs = embedded.getFormalArguments();
            StringTemplateAST args = embedded.getArgumentsAST();
            this.setSoleFormalArgumentToIthValue(embedded, argumentContext, attributeValue);
            boolean isAnonymous = embedded.getName() == "anonymous";
            if (!isAnonymous || formalArgs == null || formalArgs.size() <= 0) {
               argumentContext.put("it", attributeValue);
               argumentContext.put("attr", attributeValue);
            }

            argumentContext.put("i", new Integer(1));
            argumentContext.put("i0", new Integer(0));
            embedded.setArgumentContext(argumentContext);
            this.evaluateArguments(embedded);
            return embedded;
         }
      } else {
         return null;
      }
   }

   protected void setSoleFormalArgumentToIthValue(StringTemplate embedded, Map argumentContext, Object ithValue) {
      Map formalArgs = embedded.getFormalArguments();
      if (formalArgs != null) {
         String soleArgName = null;
         boolean isAnonymous = embedded.getName() == "anonymous";
         if (formalArgs.size() == 1 || isAnonymous && formalArgs.size() > 0) {
            if (isAnonymous && formalArgs.size() > 1) {
               embedded.error("too many arguments on {...} template: " + formalArgs);
            }

            Set argNames = formalArgs.keySet();
            soleArgName = (String)argNames.toArray()[0];
            argumentContext.put(soleArgName, ithValue);
         }
      }

   }

   public Object getObjectProperty(StringTemplate self, Object o, Object propertyName) {
      if (o != null && propertyName != null) {
         Object value = this.rawGetObjectProperty(self, o, propertyName);
         value = convertArrayToList(value);
         return value;
      } else {
         return null;
      }
   }

   protected Object rawGetObjectProperty(StringTemplate self, Object o, Object property) {
      Class c = o.getClass();
      Object value = null;
      if (c == StringTemplate.Aggregate.class) {
         String propertyName = (String)property;
         value = ((StringTemplate.Aggregate)o).get(propertyName);
         return value;
      } else {
         Map map;
         String propertyName;
         if (c == StringTemplate.class) {
            map = ((StringTemplate)o).getAttributes();
            if (map != null) {
               propertyName = (String)property;
               value = map.get(propertyName);
               return value;
            }
         }

         if (o instanceof Map) {
            map = (Map)o;
            if (property.equals("keys")) {
               value = map.keySet();
            } else if (property.equals("values")) {
               value = map.values();
            } else if (map.containsKey(property)) {
               value = map.get(property);
            } else if (map.containsKey(property.toString())) {
               value = map.get(property.toString());
            } else if (map.containsKey("_default_")) {
               value = map.get("_default_");
            }

            if (value == MAP_KEY_VALUE) {
               value = property;
            }

            return value;
         } else {
            map = null;
            propertyName = (String)property;
            String methodSuffix = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1, propertyName.length());
            Method m = this.getMethod(c, "get" + methodSuffix);
            if (m == null) {
               m = this.getMethod(c, "is" + methodSuffix);
            }

            if (m != null) {
               try {
                  value = this.invokeMethod(m, o, value);
               } catch (Exception var13) {
                  self.error("Can't get property " + propertyName + " using method get/is" + methodSuffix + " from " + c.getName() + " instance", var13);
               }
            } else {
               try {
                  Field f = c.getField(propertyName);

                  try {
                     value = this.accessField(f, o, value);
                  } catch (IllegalAccessException var11) {
                     self.error("Can't access property " + propertyName + " using method get/is" + methodSuffix + " or direct field access from " + c.getName() + " instance", var11);
                  }
               } catch (NoSuchFieldException var12) {
                  self.error("Class " + c.getName() + " has no such attribute: " + propertyName + " in template context " + self.getEnclosingInstanceStackString(), var12);
               }
            }

            return value;
         }
      }
   }

   protected Object accessField(Field f, Object o, Object value) throws IllegalAccessException {
      try {
         f.setAccessible(true);
      } catch (SecurityException var5) {
      }

      value = f.get(o);
      return value;
   }

   protected Object invokeMethod(Method m, Object o, Object value) throws IllegalAccessException, InvocationTargetException {
      try {
         m.setAccessible(true);
      } catch (SecurityException var5) {
      }

      value = m.invoke(o, (Object[])null);
      return value;
   }

   protected Method getMethod(Class c, String methodName) {
      Method m;
      try {
         m = c.getMethod(methodName, (Class[])null);
      } catch (NoSuchMethodException var5) {
         m = null;
      }

      return m;
   }

   public boolean testAttributeTrue(Object a) {
      if (a == null) {
         return false;
      } else if (a instanceof Boolean) {
         return (Boolean)a;
      } else if (a instanceof Collection) {
         return ((Collection)a).size() > 0;
      } else if (a instanceof Map) {
         return ((Map)a).size() > 0;
      } else {
         return a instanceof Iterator ? ((Iterator)a).hasNext() : true;
      }
   }

   public Object add(Object a, Object b) {
      if (a == null) {
         return b;
      } else {
         return b == null ? a : a.toString() + b.toString();
      }
   }

   public StringTemplate getTemplateInclude(StringTemplate enclosing, String templateName, StringTemplateAST argumentsAST) {
      StringTemplateGroup group = enclosing.getGroup();
      StringTemplate embedded = group.getEmbeddedInstanceOf(enclosing, templateName);
      if (embedded == null) {
         enclosing.error("cannot make embedded instance of " + templateName + " in template " + enclosing.getName());
         return null;
      } else {
         embedded.setArgumentsAST(argumentsAST);
         this.evaluateArguments(embedded);
         return embedded;
      }
   }

   public int writeAttribute(StringTemplate self, Object o, StringTemplateWriter out) {
      return this.write(self, o, out);
   }

   protected int write(StringTemplate self, Object o, StringTemplateWriter out) {
      if (o == null) {
         if (this.nullValue == null) {
            return -1;
         }

         o = this.nullValue;
      }

      int n = 0;

      try {
         if (o instanceof StringTemplate) {
            return this.writeTemplate(self, o, out);
         } else {
            o = convertAnythingIteratableToIterator(o);
            return o instanceof Iterator ? this.writeIterableValue(self, o, out) : this.writePOJO(self, o, out);
         }
      } catch (IOException var6) {
         self.error("problem writing object: " + o, var6);
         return n;
      }
   }

   protected int writePOJO(StringTemplate self, Object o, StringTemplateWriter out) throws IOException {
      int n = false;
      AttributeRenderer renderer = self.getAttributeRenderer(o.getClass());
      String v = null;
      if (renderer != null) {
         if (this.formatString != null) {
            v = renderer.toString(o, this.formatString);
         } else {
            v = renderer.toString(o);
         }
      } else {
         v = o.toString();
      }

      int n;
      if (this.wrapString != null) {
         n = out.write(v, this.wrapString);
      } else {
         n = out.write(v);
      }

      return n;
   }

   protected int writeTemplate(StringTemplate self, Object o, StringTemplateWriter out) throws IOException {
      int n = false;
      StringTemplate stToWrite = (StringTemplate)o;
      stToWrite.setEnclosingInstance(self);
      if (StringTemplate.inLintMode() && StringTemplate.isRecursiveEnclosingInstance(stToWrite)) {
         throw new IllegalStateException("infinite recursion to " + stToWrite.getTemplateDeclaratorString() + " referenced in " + stToWrite.getEnclosingInstance().getTemplateDeclaratorString() + "; stack trace:\n" + stToWrite.getEnclosingInstanceStackTrace());
      } else {
         if (this.wrapString != null) {
            out.writeWrapSeparator(this.wrapString);
         }

         int n;
         if (this.formatString != null) {
            AttributeRenderer renderer = self.getAttributeRenderer(String.class);
            if (renderer != null) {
               StringWriter buf = new StringWriter();
               StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
               stToWrite.write(sw);
               n = out.write(renderer.toString(buf.toString(), this.formatString));
               return n;
            }
         }

         n = stToWrite.write(out);
         return n;
      }
   }

   protected int writeIterableValue(StringTemplate self, Object o, StringTemplateWriter out) throws IOException {
      int n = 0;
      Iterator iter = (Iterator)o;
      boolean seenAValue = false;

      while(true) {
         while(true) {
            Object iterValue;
            do {
               if (!iter.hasNext()) {
                  return n;
               }

               iterValue = iter.next();
               if (iterValue == null) {
                  iterValue = this.nullValue;
               }
            } while(iterValue == null);

            int nw;
            if (this.separatorString == null) {
               nw = this.write(self, iterValue, out);
               if (nw != -1) {
                  n += nw;
               }
            } else {
               int nw;
               if (iterValue instanceof StringTemplate) {
                  StringTemplate st = (StringTemplate)iterValue;
                  int nchunks = st.getChunks() != null ? st.getChunks().size() : 0;
                  boolean nullable = true;

                  for(nw = 0; nw < nchunks; ++nw) {
                     Expr a = (Expr)st.getChunks().get(nw);
                     if (!(a instanceof ConditionalExpr)) {
                        nullable = false;
                     }
                  }

                  if (!nullable) {
                     if (seenAValue && this.separatorString != null) {
                        n += out.writeSeparator(this.separatorString);
                     }

                     nw = this.write(self, iterValue, out);
                     n += nw;
                     seenAValue = true;
                     continue;
                  }
               }

               if (!(iterValue instanceof StringTemplate) && !(iterValue instanceof Iterator)) {
                  if (seenAValue && this.separatorString != null) {
                     n += out.writeSeparator(this.separatorString);
                  }

                  nw = this.write(self, iterValue, out);
                  seenAValue = true;
                  n += nw;
               } else {
                  StringWriter buf = new StringWriter();
                  StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
                  int tmpsize = this.write(self, iterValue, sw);
                  if (tmpsize != -1) {
                     if (seenAValue && this.separatorString != null) {
                        n += out.writeSeparator(this.separatorString);
                     }

                     nw = this.write(self, iterValue, out);
                     n += nw;
                     seenAValue = true;
                  }
               }
            }
         }
      }
   }

   public String evaluateExpression(StringTemplate self, Object expr) {
      if (expr == null) {
         return null;
      } else if (expr instanceof StringTemplateAST) {
         StringTemplateAST exprAST = (StringTemplateAST)expr;
         StringWriter buf = new StringWriter();
         StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
         ActionEvaluator eval = new ActionEvaluator(self, this, sw);

         try {
            eval.action(exprAST);
         } catch (RecognitionException var8) {
            self.error("can't evaluate tree: " + this.exprTree.toStringList(), var8);
         }

         return buf.toString();
      } else {
         return expr.toString();
      }
   }

   protected void evaluateArguments(StringTemplate self) {
      StringTemplateAST argumentsAST = self.getArgumentsAST();
      if (argumentsAST != null && argumentsAST.getFirstChild() != null) {
         StringTemplate enclosing = self.getEnclosingInstance();
         StringTemplate argContextST = new StringTemplate(self.getGroup(), "");
         argContextST.setName("<invoke " + self.getName() + " arg context>");
         argContextST.setEnclosingInstance(enclosing);
         argContextST.setArgumentContext(self.getArgumentContext());
         ActionEvaluator eval = new ActionEvaluator(argContextST, this, (StringTemplateWriter)null);

         try {
            Map ac = eval.argList(argumentsAST, self, self.getArgumentContext());
            self.setArgumentContext(ac);
         } catch (RecognitionException var7) {
            self.error("can't evaluate tree: " + argumentsAST.toStringList(), var7);
         }

      }
   }

   public static Object convertArrayToList(Object value) {
      if (value == null) {
         return null;
      } else if (value.getClass().isArray()) {
         return value.getClass().getComponentType().isPrimitive() ? new ArrayWrappedInList(value) : Arrays.asList((Object[])((Object[])value));
      } else {
         return value;
      }
   }

   protected static Object convertAnythingIteratableToIterator(Object o) {
      Iterator iter = null;
      if (o instanceof Collection) {
         iter = ((Collection)o).iterator();
      } else if (o instanceof Map) {
         iter = ((Map)o).values().iterator();
      } else if (o instanceof Iterator) {
         iter = (Iterator)o;
      }

      return iter == null ? o : iter;
   }

   protected static Iterator convertAnythingToIterator(Object o) {
      Iterator iter = null;
      if (o instanceof Collection) {
         iter = ((Collection)o).iterator();
      } else if (o instanceof Map) {
         iter = ((Map)o).values().iterator();
      } else if (o instanceof Iterator) {
         iter = (Iterator)o;
      }

      if (iter == null) {
         List singleton = new StringTemplate.STAttributeList(1);
         singleton.add(o);
         return singleton.iterator();
      } else {
         return iter;
      }
   }

   public Object first(Object attribute) {
      if (attribute == null) {
         return null;
      } else {
         Object f = attribute;
         attribute = convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            Iterator it = (Iterator)attribute;
            if (it.hasNext()) {
               f = it.next();
            }
         }

         return f;
      }
   }

   public Object rest(Object attribute) {
      if (attribute == null) {
         return null;
      } else {
         attribute = convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            List a = new ArrayList();
            Iterator it = (Iterator)attribute;
            if (!it.hasNext()) {
               return null;
            } else {
               it.next();

               while(it.hasNext()) {
                  Object o = it.next();
                  if (o != null) {
                     a.add(o);
                  }
               }

               return a;
            }
         } else {
            Object theRest = null;
            return theRest;
         }
      }
   }

   public Object last(Object attribute) {
      if (attribute == null) {
         return null;
      } else {
         Object last = attribute;
         attribute = convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            for(Iterator it = (Iterator)attribute; it.hasNext(); last = it.next()) {
            }
         }

         return last;
      }
   }

   public Object strip(Object attribute) {
      if (attribute == null) {
         return null;
      } else {
         attribute = convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            List a = new ArrayList();
            Iterator it = (Iterator)attribute;

            while(it.hasNext()) {
               Object o = it.next();
               if (o != null) {
                  a.add(o);
               }
            }

            return a;
         } else {
            return attribute;
         }
      }
   }

   public Object trunc(Object attribute) {
      if (attribute == null) {
         return null;
      } else {
         attribute = convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            List a = new ArrayList();
            Iterator it = (Iterator)attribute;

            while(it.hasNext()) {
               Object o = it.next();
               if (it.hasNext()) {
                  a.add(o);
               }
            }

            return a;
         } else {
            return null;
         }
      }
   }

   public Object length(Object attribute) {
      if (attribute == null) {
         return new Integer(0);
      } else {
         int i = 1;
         if (attribute instanceof Map) {
            i = ((Map)attribute).size();
         } else if (attribute instanceof Collection) {
            i = ((Collection)attribute).size();
         } else if (attribute instanceof Object[]) {
            Object[] list = (Object[])((Object[])attribute);
            i = list.length;
         } else if (attribute instanceof int[]) {
            int[] list = (int[])((int[])attribute);
            i = list.length;
         } else if (attribute instanceof long[]) {
            long[] list = (long[])((long[])attribute);
            i = list.length;
         } else if (attribute instanceof float[]) {
            float[] list = (float[])((float[])attribute);
            i = list.length;
         } else if (attribute instanceof double[]) {
            double[] list = (double[])((double[])attribute);
            i = list.length;
         } else if (attribute instanceof Iterator) {
            Iterator it = (Iterator)attribute;

            for(i = 0; it.hasNext(); ++i) {
               it.next();
            }
         }

         return new Integer(i);
      }
   }

   public Object getOption(String name) {
      Object value = null;
      if (this.options != null) {
         value = this.options.get(name);
         if (value == "empty expr option") {
            return defaultOptionValues.get(name);
         }
      }

      return value;
   }

   public String toString() {
      return this.exprTree.toStringList();
   }
}
