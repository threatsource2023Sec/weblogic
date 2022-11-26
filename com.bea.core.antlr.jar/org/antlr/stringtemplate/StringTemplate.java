package org.antlr.stringtemplate;

import antlr.CharScanner;
import antlr.CommonAST;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.collections.ASTEnumeration;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import org.antlr.stringtemplate.language.ASTExpr;
import org.antlr.stringtemplate.language.ActionLexer;
import org.antlr.stringtemplate.language.ActionParser;
import org.antlr.stringtemplate.language.ConditionalExpr;
import org.antlr.stringtemplate.language.Expr;
import org.antlr.stringtemplate.language.FormalArgument;
import org.antlr.stringtemplate.language.NewlineRef;
import org.antlr.stringtemplate.language.StringTemplateAST;
import org.antlr.stringtemplate.language.TemplateParser;

public class StringTemplate {
   public static final String VERSION = "3.2.1";
   public static final int REGION_IMPLICIT = 1;
   public static final int REGION_EMBEDDED = 2;
   public static final int REGION_EXPLICIT = 3;
   public static final String ANONYMOUS_ST_NAME = "anonymous";
   static boolean lintMode = false;
   protected List referencedAttributes;
   protected String name;
   private static int templateCounter = 0;
   protected int templateID;
   protected StringTemplate enclosingInstance;
   protected Map argumentContext;
   protected StringTemplateAST argumentsAST;
   protected LinkedHashMap formalArguments;
   protected int numberOfDefaultArgumentValues;
   protected boolean passThroughAttributes;
   protected StringTemplateGroup nativeGroup;
   protected StringTemplateGroup group;
   protected int groupFileLine;
   StringTemplateErrorListener listener;
   protected String pattern;
   protected Map attributes;
   protected Map attributeRenderers;
   protected List chunks;
   protected int regionDefType;
   protected boolean isRegion;
   protected Set regions;
   public static StringTemplateGroup defaultGroup = new StringTemplateGroup("defaultGroup", ".");

   private static synchronized int getNextTemplateCounter() {
      ++templateCounter;
      return templateCounter;
   }

   public static void resetTemplateCounter() {
      templateCounter = 0;
   }

   public StringTemplate() {
      this.referencedAttributes = null;
      this.name = "anonymous";
      this.templateID = getNextTemplateCounter();
      this.enclosingInstance = null;
      this.argumentContext = null;
      this.argumentsAST = null;
      this.formalArguments = FormalArgument.UNKNOWN;
      this.numberOfDefaultArgumentValues = 0;
      this.passThroughAttributes = false;
      this.listener = null;
      this.group = defaultGroup;
   }

   public StringTemplate(String template) {
      this((StringTemplateGroup)null, (String)template);
   }

   public StringTemplate(String template, Class lexer) {
      this();
      this.setGroup(new StringTemplateGroup("defaultGroup", lexer));
      this.setTemplate(template);
   }

   public StringTemplate(StringTemplateGroup group, String template) {
      this();
      if (group != null) {
         this.setGroup(group);
      }

      this.setTemplate(template);
   }

   public StringTemplate(StringTemplateGroup group, String template, HashMap attributes) {
      this(group, template);
      this.attributes = attributes;
   }

   protected void dup(StringTemplate from, StringTemplate to) {
      to.attributeRenderers = from.attributeRenderers;
      to.pattern = from.pattern;
      to.chunks = from.chunks;
      to.formalArguments = from.formalArguments;
      to.numberOfDefaultArgumentValues = from.numberOfDefaultArgumentValues;
      to.name = from.name;
      to.group = from.group;
      to.nativeGroup = from.nativeGroup;
      to.listener = from.listener;
      to.regions = from.regions;
      to.isRegion = from.isRegion;
      to.regionDefType = from.regionDefType;
   }

   public StringTemplate getInstanceOf() {
      StringTemplate t = null;
      if (this.nativeGroup != null) {
         t = this.nativeGroup.createStringTemplate();
      } else {
         t = this.group.createStringTemplate();
      }

      this.dup(this, t);
      return t;
   }

   public StringTemplate getEnclosingInstance() {
      return this.enclosingInstance;
   }

   public StringTemplate getOutermostEnclosingInstance() {
      return this.enclosingInstance != null ? this.enclosingInstance.getOutermostEnclosingInstance() : this;
   }

   public void setEnclosingInstance(StringTemplate enclosingInstance) {
      if (this == enclosingInstance) {
         throw new IllegalArgumentException("cannot embed template " + this.getName() + " in itself");
      } else {
         this.enclosingInstance = enclosingInstance;
      }
   }

   public Map getArgumentContext() {
      return this.argumentContext;
   }

   public void setArgumentContext(Map ac) {
      this.argumentContext = ac;
   }

   public StringTemplateAST getArgumentsAST() {
      return this.argumentsAST;
   }

   public void setArgumentsAST(StringTemplateAST argumentsAST) {
      this.argumentsAST = argumentsAST;
   }

   public String getName() {
      return this.name;
   }

   public String getOutermostName() {
      return this.enclosingInstance != null ? this.enclosingInstance.getOutermostName() : this.getName();
   }

   public void setName(String name) {
      this.name = name;
   }

   public StringTemplateGroup getGroup() {
      return this.group;
   }

   public void setGroup(StringTemplateGroup group) {
      this.group = group;
   }

   public StringTemplateGroup getNativeGroup() {
      return this.nativeGroup;
   }

   public void setNativeGroup(StringTemplateGroup nativeGroup) {
      this.nativeGroup = nativeGroup;
   }

   public int getGroupFileLine() {
      return this.enclosingInstance != null ? this.enclosingInstance.getGroupFileLine() : this.groupFileLine;
   }

   public void setGroupFileLine(int groupFileLine) {
      this.groupFileLine = groupFileLine;
   }

   public void setTemplate(String template) {
      this.pattern = template;
      this.breakTemplateIntoChunks();
   }

   public String getTemplate() {
      return this.pattern;
   }

   public void setErrorListener(StringTemplateErrorListener listener) {
      this.listener = listener;
   }

   public StringTemplateErrorListener getErrorListener() {
      return this.listener == null ? this.group.getErrorListener() : this.listener;
   }

   public void reset() {
      this.attributes = new HashMap();
   }

   public void setPredefinedAttributes() {
      if (inLintMode()) {
         ;
      }
   }

   public void removeAttribute(String name) {
      if (this.attributes != null) {
         this.attributes.remove(name);
      }

   }

   public void setAttribute(String name, Object value) {
      if (value != null && name != null) {
         if (name.indexOf(46) >= 0) {
            throw new IllegalArgumentException("cannot have '.' in attribute names");
         } else {
            if (this.attributes == null) {
               this.attributes = new HashMap();
            }

            if (value instanceof StringTemplate) {
               ((StringTemplate)value).setEnclosingInstance(this);
            } else {
               value = ASTExpr.convertArrayToList(value);
            }

            Object o = this.attributes.get(name);
            if (o == null) {
               this.rawSetAttribute(this.attributes, name, value);
            } else {
               STAttributeList v = null;
               if (o.getClass() == STAttributeList.class) {
                  v = (STAttributeList)o;
               } else if (o instanceof List) {
                  List listAttr = (List)o;
                  v = new STAttributeList(listAttr.size());
                  v.addAll(listAttr);
                  this.rawSetAttribute(this.attributes, name, v);
               } else {
                  v = new STAttributeList();
                  this.rawSetAttribute(this.attributes, name, v);
                  v.add(o);
               }

               if (value instanceof List) {
                  if (v != value) {
                     v.addAll((List)value);
                  }
               } else {
                  v.add(value);
               }

            }
         }
      }
   }

   public void setAttribute(String name, int value) {
      this.setAttribute(name, (Object)(new Integer(value)));
   }

   public void setAttribute(String aggrSpec, Object v1, Object v2) {
      this.setAttribute(aggrSpec, new Object[]{v1, v2});
   }

   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3) {
      this.setAttribute(aggrSpec, new Object[]{v1, v2, v3});
   }

   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3, Object v4) {
      this.setAttribute(aggrSpec, new Object[]{v1, v2, v3, v4});
   }

   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3, Object v4, Object v5) {
      this.setAttribute(aggrSpec, new Object[]{v1, v2, v3, v4, v5});
   }

   protected void setAttribute(String aggrSpec, Object[] values) {
      List properties = new ArrayList();
      String aggrName = this.parseAggregateAttributeSpec(aggrSpec, properties);
      if (values != null && properties.size() != 0) {
         if (values.length != properties.size()) {
            throw new IllegalArgumentException("number of properties in '" + aggrSpec + "' != number of values");
         } else {
            Aggregate aggr = new Aggregate();

            for(int i = 0; i < values.length; ++i) {
               Object value = values[i];
               if (value instanceof StringTemplate) {
                  ((StringTemplate)value).setEnclosingInstance(this);
               } else {
                  value = ASTExpr.convertArrayToList(value);
               }

               aggr.put((String)properties.get(i), value);
            }

            this.setAttribute(aggrName, (Object)aggr);
         }
      } else {
         throw new IllegalArgumentException("missing properties or values for '" + aggrSpec + "'");
      }
   }

   protected String parseAggregateAttributeSpec(String aggrSpec, List properties) {
      int dot = aggrSpec.indexOf(46);
      if (dot <= 0) {
         throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
      } else {
         String aggrName = aggrSpec.substring(0, dot);
         String propString = aggrSpec.substring(dot + 1, aggrSpec.length());
         boolean error = true;
         StringTokenizer tokenizer = new StringTokenizer(propString, "{,}", true);
         if (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            token = token.trim();
            if (token.equals("{")) {
               token = tokenizer.nextToken();
               token = token.trim();
               properties.add(token);
               token = tokenizer.nextToken();

               for(token = token.trim(); token.equals(","); token = token.trim()) {
                  token = tokenizer.nextToken();
                  token = token.trim();
                  properties.add(token);
                  token = tokenizer.nextToken();
               }

               if (token.equals("}")) {
                  error = false;
               }
            }
         }

         if (error) {
            throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
         } else {
            return aggrName;
         }
      }
   }

   protected void rawSetAttribute(Map attributes, String name, Object value) {
      if (this.formalArguments != FormalArgument.UNKNOWN && this.getFormalArgument(name) == null) {
         throw new NoSuchElementException("no such attribute: " + name + " in template context " + this.getEnclosingInstanceStackString());
      } else if (value != null) {
         attributes.put(name, value);
      }
   }

   public void rawSetArgumentAttribute(StringTemplate embedded, Map attributes, String name, Object value) {
      if (embedded.formalArguments != FormalArgument.UNKNOWN && embedded.getFormalArgument(name) == null) {
         throw new NoSuchElementException("template " + embedded.getName() + " has no such attribute: " + name + " in template context " + this.getEnclosingInstanceStackString());
      } else if (value != null) {
         attributes.put(name, value);
      }
   }

   public Object getAttribute(String name) {
      Object v = this.get(this, name);
      if (v == null) {
         this.checkNullAttributeAgainstFormalArguments(this, name);
      }

      return v;
   }

   public int write(StringTemplateWriter out) throws IOException {
      if (this.group.debugTemplateOutput) {
         this.group.emitTemplateStartDebugString(this, out);
      }

      int n = 0;
      boolean missing = true;
      this.setPredefinedAttributes();
      this.setDefaultArgumentValues();

      for(int i = 0; this.chunks != null && i < this.chunks.size(); ++i) {
         Expr a = (Expr)this.chunks.get(i);
         int chunkN = a.write(this, out);
         if (chunkN <= 0 && i == 0 && i + 1 < this.chunks.size() && this.chunks.get(i + 1) instanceof NewlineRef) {
            ++i;
         } else {
            if (chunkN <= 0 && i - 1 >= 0 && this.chunks.get(i - 1) instanceof NewlineRef && i + 1 < this.chunks.size() && this.chunks.get(i + 1) instanceof NewlineRef) {
               ++i;
            }

            if (chunkN != -1) {
               n += chunkN;
               missing = false;
            }
         }
      }

      if (this.group.debugTemplateOutput) {
         this.group.emitTemplateStopDebugString(this, out);
      }

      if (lintMode) {
         this.checkForTrouble();
      }

      return missing && this.chunks != null && this.chunks.size() > 0 ? -1 : n;
   }

   public Object get(StringTemplate self, String attribute) {
      if (self == null) {
         return null;
      } else {
         if (lintMode) {
            self.trackAttributeReference(attribute);
         }

         Object o = null;
         if (self.attributes != null) {
            o = self.attributes.get(attribute);
         }

         if (o == null) {
            Map argContext = self.getArgumentContext();
            if (argContext != null) {
               o = argContext.get(attribute);
            }
         }

         if (o == null && !self.passThroughAttributes && self.getFormalArgument(attribute) != null) {
            return null;
         } else {
            if (o == null && self.enclosingInstance != null) {
               Object valueFromEnclosing = this.get(self.enclosingInstance, attribute);
               o = valueFromEnclosing;
            } else if (o == null && self.enclosingInstance == null) {
               o = self.group.getMap(attribute);
            }

            return o;
         }
      }
   }

   protected void breakTemplateIntoChunks() {
      if (this.pattern != null) {
         try {
            Class lexerClass = this.group.getTemplateLexerClass();
            Constructor ctor = lexerClass.getConstructor(StringTemplate.class, Reader.class);
            CharScanner chunkStream = (CharScanner)ctor.newInstance(this, new StringReader(this.pattern));
            chunkStream.setTokenObjectClass("org.antlr.stringtemplate.language.ChunkToken");
            TemplateParser chunkifier = new TemplateParser(chunkStream);
            chunkifier.template(this);
         } catch (Exception var5) {
            String name = "<unknown>";
            String outerName = this.getOutermostName();
            if (this.getName() != null) {
               name = this.getName();
            }

            if (outerName != null && !name.equals(outerName)) {
               name = name + " nested in " + outerName;
            }

            this.error("problem parsing template '" + name + "'", var5);
         }

      }
   }

   public ASTExpr parseAction(String action) {
      ActionLexer lexer = new ActionLexer(new StringReader(action.toString()));
      ActionParser parser = new ActionParser(lexer, this);
      parser.setASTNodeClass("org.antlr.stringtemplate.language.StringTemplateAST");
      lexer.setTokenObjectClass("org.antlr.stringtemplate.language.StringTemplateToken");
      ASTExpr a = null;

      try {
         Map options = parser.action();
         AST tree = parser.getAST();
         if (tree != null) {
            if (tree.getType() == 8) {
               a = new ConditionalExpr(this, tree);
            } else {
               a = new ASTExpr(this, tree, options);
            }
         }
      } catch (RecognitionException var7) {
         this.error("Can't parse chunk: " + action.toString(), var7);
      } catch (TokenStreamException var8) {
         this.error("Can't parse chunk: " + action.toString(), var8);
      }

      return (ASTExpr)a;
   }

   public int getTemplateID() {
      return this.templateID;
   }

   public Map getAttributes() {
      return this.attributes;
   }

   public List getChunks() {
      return this.chunks;
   }

   public void addChunk(Expr e) {
      if (this.chunks == null) {
         this.chunks = new ArrayList();
      }

      this.chunks.add(e);
   }

   public void setAttributes(Map attributes) {
      this.attributes = attributes;
   }

   public Map getFormalArguments() {
      return this.formalArguments;
   }

   public void setFormalArguments(LinkedHashMap args) {
      this.formalArguments = args;
   }

   public void setDefaultArgumentValues() {
      if (this.numberOfDefaultArgumentValues != 0) {
         if (this.argumentContext == null) {
            this.argumentContext = new HashMap();
         }

         if (this.formalArguments != FormalArgument.UNKNOWN) {
            Set argNames = this.formalArguments.keySet();
            Iterator it = argNames.iterator();

            while(it.hasNext()) {
               String argName = (String)it.next();
               FormalArgument arg = (FormalArgument)this.formalArguments.get(argName);
               if (arg.defaultValueST != null) {
                  Object existingValue = this.getAttribute(argName);
                  if (existingValue == null) {
                     Object defaultValue = arg.defaultValueST;
                     int nchunks = arg.defaultValueST.chunks.size();
                     if (nchunks == 1) {
                        Object a = arg.defaultValueST.chunks.get(0);
                        if (a instanceof ASTExpr) {
                           ASTExpr e = (ASTExpr)a;
                           if (e.getAST().getType() == 9) {
                              defaultValue = e.evaluateExpression(this, e.getAST());
                           }
                        }
                     }

                     this.argumentContext.put(argName, defaultValue);
                  }
               }
            }
         }

      }
   }

   public FormalArgument lookupFormalArgument(String name) {
      FormalArgument arg = this.getFormalArgument(name);
      if (arg == null && this.enclosingInstance != null) {
         arg = this.enclosingInstance.lookupFormalArgument(name);
      }

      return arg;
   }

   public FormalArgument getFormalArgument(String name) {
      return (FormalArgument)this.formalArguments.get(name);
   }

   public void defineEmptyFormalArgumentList() {
      this.setFormalArguments(new LinkedHashMap());
   }

   public void defineFormalArgument(String name) {
      this.defineFormalArgument(name, (StringTemplate)null);
   }

   public void defineFormalArguments(List names) {
      if (names != null) {
         for(int i = 0; i < names.size(); ++i) {
            String name = (String)names.get(i);
            this.defineFormalArgument(name);
         }

      }
   }

   public void defineFormalArgument(String name, StringTemplate defaultValue) {
      if (defaultValue != null) {
         ++this.numberOfDefaultArgumentValues;
      }

      FormalArgument a = new FormalArgument(name, defaultValue);
      if (this.formalArguments == FormalArgument.UNKNOWN) {
         this.formalArguments = new LinkedHashMap();
      }

      this.formalArguments.put(name, a);
   }

   public void setPassThroughAttributes(boolean passThroughAttributes) {
      this.passThroughAttributes = passThroughAttributes;
   }

   public void setAttributeRenderers(Map renderers) {
      this.attributeRenderers = renderers;
   }

   public void registerRenderer(Class attributeClassType, AttributeRenderer renderer) {
      if (this.attributeRenderers == null) {
         this.attributeRenderers = new HashMap();
      }

      this.attributeRenderers.put(attributeClassType, renderer);
   }

   public AttributeRenderer getAttributeRenderer(Class attributeClassType) {
      AttributeRenderer renderer = null;
      if (this.attributeRenderers != null) {
         renderer = (AttributeRenderer)this.attributeRenderers.get(attributeClassType);
      }

      if (renderer != null) {
         return renderer;
      } else {
         return this.enclosingInstance != null ? this.enclosingInstance.getAttributeRenderer(attributeClassType) : this.group.getAttributeRenderer(attributeClassType);
      }
   }

   public void error(String msg) {
      this.error(msg, (Throwable)null);
   }

   public void warning(String msg) {
      if (this.getErrorListener() != null) {
         this.getErrorListener().warning(msg);
      } else {
         System.err.println("StringTemplate: warning: " + msg);
      }

   }

   public void error(String msg, Throwable e) {
      if (this.getErrorListener() != null) {
         this.getErrorListener().error(msg, e);
      } else if (e != null) {
         System.err.println("StringTemplate: error: " + msg + ": " + e.toString());
         if (e instanceof InvocationTargetException) {
            e = ((InvocationTargetException)e).getTargetException();
         }

         e.printStackTrace(System.err);
      } else {
         System.err.println("StringTemplate: error: " + msg);
      }

   }

   public static void setLintMode(boolean lint) {
      lintMode = lint;
   }

   public static boolean inLintMode() {
      return lintMode;
   }

   protected void trackAttributeReference(String name) {
      if (this.referencedAttributes == null) {
         this.referencedAttributes = new ArrayList();
      }

      this.referencedAttributes.add(name);
   }

   public static boolean isRecursiveEnclosingInstance(StringTemplate st) {
      if (st == null) {
         return false;
      } else {
         StringTemplate p = st.enclosingInstance;
         if (p == st) {
            return true;
         } else {
            while(p != null) {
               if (p == st) {
                  return true;
               }

               p = p.enclosingInstance;
            }

            return false;
         }
      }
   }

   public String getEnclosingInstanceStackTrace() {
      StringBuffer buf = new StringBuffer();
      Set seen = new HashSet();

      for(StringTemplate p = this; p != null; p = p.enclosingInstance) {
         if (seen.contains(p)) {
            buf.append(p.getTemplateDeclaratorString());
            buf.append(" (start of recursive cycle)");
            buf.append("\n");
            buf.append("...");
            break;
         }

         seen.add(p);
         buf.append(p.getTemplateDeclaratorString());
         if (p.attributes != null) {
            buf.append(", attributes=[");
            int i = 0;
            Iterator iter = p.attributes.keySet().iterator();

            while(true) {
               while(iter.hasNext()) {
                  String attrName = (String)iter.next();
                  if (i > 0) {
                     buf.append(", ");
                  }

                  ++i;
                  buf.append(attrName);
                  Object o = p.attributes.get(attrName);
                  if (o instanceof StringTemplate) {
                     StringTemplate st = (StringTemplate)o;
                     buf.append("=");
                     buf.append("<");
                     buf.append(st.getName());
                     buf.append("()@");
                     buf.append(String.valueOf(st.getTemplateID()));
                     buf.append(">");
                  } else if (o instanceof List) {
                     buf.append("=List[..");
                     List list = (List)o;
                     int n = 0;

                     for(int j = 0; j < list.size(); ++j) {
                        Object listValue = list.get(j);
                        if (listValue instanceof StringTemplate) {
                           if (n > 0) {
                              buf.append(", ");
                           }

                           ++n;
                           StringTemplate st = (StringTemplate)listValue;
                           buf.append("<");
                           buf.append(st.getName());
                           buf.append("()@");
                           buf.append(String.valueOf(st.getTemplateID()));
                           buf.append(">");
                        }
                     }

                     buf.append("..]");
                  }
               }

               buf.append("]");
               break;
            }
         }

         if (p.referencedAttributes != null) {
            buf.append(", references=");
            buf.append(p.referencedAttributes);
         }

         buf.append(">\n");
      }

      return buf.toString();
   }

   public String getTemplateDeclaratorString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<");
      buf.append(this.getName());
      buf.append("(");
      buf.append(this.formalArguments.keySet());
      buf.append(")@");
      buf.append(String.valueOf(this.getTemplateID()));
      buf.append(">");
      return buf.toString();
   }

   protected String getTemplateHeaderString(boolean showAttributes) {
      if (showAttributes) {
         StringBuffer buf = new StringBuffer();
         buf.append(this.getName());
         if (this.attributes != null) {
            buf.append(this.attributes.keySet());
         }

         return buf.toString();
      } else {
         return this.getName();
      }
   }

   protected void checkNullAttributeAgainstFormalArguments(StringTemplate self, String attribute) {
      if (self.getFormalArguments() == FormalArgument.UNKNOWN) {
         if (self.enclosingInstance != null) {
            this.checkNullAttributeAgainstFormalArguments(self.enclosingInstance, attribute);
         }

      } else {
         FormalArgument formalArg = self.lookupFormalArgument(attribute);
         if (formalArg == null) {
            throw new NoSuchElementException("no such attribute: " + attribute + " in template context " + this.getEnclosingInstanceStackString());
         }
      }
   }

   protected void checkForTrouble() {
      if (this.attributes != null) {
         Set names = this.attributes.keySet();
         Iterator iter = names.iterator();

         while(iter.hasNext()) {
            String name = (String)iter.next();
            if (this.referencedAttributes != null && !this.referencedAttributes.contains(name)) {
               this.warning(this.getName() + ": set but not used: " + name);
            }
         }

      }
   }

   public String getEnclosingInstanceStackString() {
      List names = new LinkedList();

      for(StringTemplate p = this; p != null; p = p.enclosingInstance) {
         String name = p.getName();
         names.add(0, name + (p.passThroughAttributes ? "(...)" : ""));
      }

      return names.toString().replaceAll(",", "");
   }

   public boolean isRegion() {
      return this.isRegion;
   }

   public void setIsRegion(boolean isRegion) {
      this.isRegion = isRegion;
   }

   public void addRegionName(String name) {
      if (this.regions == null) {
         this.regions = new HashSet();
      }

      this.regions.add(name);
   }

   public boolean containsRegionName(String name) {
      return this.regions == null ? false : this.regions.contains(name);
   }

   public int getRegionDefType() {
      return this.regionDefType;
   }

   public void setRegionDefType(int regionDefType) {
      this.regionDefType = regionDefType;
   }

   public String toDebugString() {
      StringBuffer buf = new StringBuffer();
      buf.append("template-" + this.getTemplateDeclaratorString() + ":");
      buf.append("chunks=");
      if (this.chunks != null) {
         buf.append(this.chunks.toString());
      }

      buf.append("attributes=[");
      if (this.attributes != null) {
         Set attrNames = this.attributes.keySet();
         int n = 0;

         for(Iterator iter = attrNames.iterator(); iter.hasNext(); ++n) {
            if (n > 0) {
               buf.append(',');
            }

            String name = (String)iter.next();
            buf.append(name + "=");
            Object value = this.attributes.get(name);
            if (value instanceof StringTemplate) {
               buf.append(((StringTemplate)value).toDebugString());
            } else {
               buf.append(value);
            }
         }

         buf.append("]");
      }

      return buf.toString();
   }

   public String toStructureString() {
      return this.toStructureString(0);
   }

   public String toStructureString(int indent) {
      StringBuffer buf = new StringBuffer();

      for(int i = 1; i <= indent; ++i) {
         buf.append("  ");
      }

      buf.append(this.getName());
      buf.append(this.attributes.keySet());
      buf.append(":\n");
      if (this.attributes != null) {
         Set attrNames = this.attributes.keySet();
         Iterator iter = attrNames.iterator();

         while(true) {
            while(iter.hasNext()) {
               String name = (String)iter.next();
               Object value = this.attributes.get(name);
               if (value instanceof StringTemplate) {
                  buf.append(((StringTemplate)value).toStructureString(indent + 1));
               } else if (value instanceof List) {
                  List alist = (List)value;

                  for(int i = 0; i < alist.size(); ++i) {
                     Object o = alist.get(i);
                     if (o instanceof StringTemplate) {
                        buf.append(((StringTemplate)o).toStructureString(indent + 1));
                     }
                  }
               } else if (value instanceof Map) {
                  Map m = (Map)value;
                  Collection mvalues = m.values();
                  Iterator iterator = mvalues.iterator();

                  while(iterator.hasNext()) {
                     Object o = iterator.next();
                     if (o instanceof StringTemplate) {
                        buf.append(((StringTemplate)o).toStructureString(indent + 1));
                     }
                  }
               }
            }

            return buf.toString();
         }
      } else {
         return buf.toString();
      }
   }

   public StringTemplate getDOTForDependencyGraph(boolean showAttributes) {
      String structure = "digraph StringTemplateDependencyGraph {\nnode [shape=$shape$, $if(width)$width=$width$,$endif$      $if(height)$height=$height$,$endif$ fontsize=$fontsize$];\n$edges:{e|\"$e.src$\" -> \"$e.trg$\"\n}$}\n";
      StringTemplate graphST = new StringTemplate(structure);
      HashMap edges = new HashMap();
      this.getDependencyGraph(edges, showAttributes);
      Set sourceNodes = edges.keySet();
      Iterator it = sourceNodes.iterator();

      while(it.hasNext()) {
         String src = (String)it.next();
         Set targetNodes = (Set)edges.get(src);
         Iterator it2 = targetNodes.iterator();

         while(it2.hasNext()) {
            String trg = (String)it2.next();
            graphST.setAttribute("edges.{src,trg}", src, trg);
         }
      }

      graphST.setAttribute("shape", (Object)"none");
      graphST.setAttribute("fontsize", (Object)"11");
      graphST.setAttribute("height", (Object)"0");
      return graphST;
   }

   public void getDependencyGraph(Map edges, boolean showAttributes) {
      String srcNode = this.getTemplateHeaderString(showAttributes);
      String targetNode;
      if (this.attributes != null) {
         Set attrNames = this.attributes.keySet();
         Iterator iter = attrNames.iterator();

         label73:
         while(true) {
            while(true) {
               if (!iter.hasNext()) {
                  break label73;
               }

               String name = (String)iter.next();
               Object value = this.attributes.get(name);
               if (value instanceof StringTemplate) {
                  String targetNode = ((StringTemplate)value).getTemplateHeaderString(showAttributes);
                  this.putToMultiValuedMap(edges, srcNode, targetNode);
                  ((StringTemplate)value).getDependencyGraph(edges, showAttributes);
               } else if (value instanceof List) {
                  List alist = (List)value;

                  for(int i = 0; i < alist.size(); ++i) {
                     Object o = alist.get(i);
                     if (o instanceof StringTemplate) {
                        targetNode = ((StringTemplate)o).getTemplateHeaderString(showAttributes);
                        this.putToMultiValuedMap(edges, srcNode, targetNode);
                        ((StringTemplate)o).getDependencyGraph(edges, showAttributes);
                     }
                  }
               } else if (value instanceof Map) {
                  Map m = (Map)value;
                  Collection mvalues = m.values();
                  Iterator iterator = mvalues.iterator();

                  while(iterator.hasNext()) {
                     Object o = iterator.next();
                     if (o instanceof StringTemplate) {
                        String targetNode = ((StringTemplate)o).getTemplateHeaderString(showAttributes);
                        this.putToMultiValuedMap(edges, srcNode, targetNode);
                        ((StringTemplate)o).getDependencyGraph(edges, showAttributes);
                     }
                  }
               }
            }
         }
      }

      for(int i = 0; this.chunks != null && i < this.chunks.size(); ++i) {
         Expr expr = (Expr)this.chunks.get(i);
         if (expr instanceof ASTExpr) {
            ASTExpr e = (ASTExpr)expr;
            AST tree = e.getAST();
            AST includeAST = new CommonAST(new CommonToken(7, "include"));
            ASTEnumeration it = tree.findAllPartial(includeAST);

            while(it.hasMoreNodes()) {
               AST t = it.nextNode();
               targetNode = t.getFirstChild().getText();
               System.out.println("found include " + targetNode);
               this.putToMultiValuedMap(edges, srcNode, targetNode);
               StringTemplateGroup group = this.getGroup();
               if (group != null) {
                  StringTemplate st = group.getInstanceOf(targetNode);
                  st.getDependencyGraph(edges, showAttributes);
               }
            }
         }
      }

   }

   protected void putToMultiValuedMap(Map map, Object key, Object value) {
      HashSet bag = (HashSet)map.get(key);
      if (bag == null) {
         bag = new HashSet();
         map.put(key, bag);
      }

      bag.add(value);
   }

   public void printDebugString() {
      System.out.println("template-" + this.getName() + ":");
      System.out.print("chunks=");
      System.out.println(this.chunks.toString());
      if (this.attributes != null) {
         System.out.print("attributes=[");
         Set attrNames = this.attributes.keySet();
         int n = 0;

         for(Iterator iter = attrNames.iterator(); iter.hasNext(); ++n) {
            if (n > 0) {
               System.out.print(',');
            }

            String name = (String)iter.next();
            Object value = this.attributes.get(name);
            if (value instanceof StringTemplate) {
               System.out.print(name + "=");
               ((StringTemplate)value).printDebugString();
            } else if (value instanceof List) {
               ArrayList alist = (ArrayList)value;

               for(int i = 0; i < alist.size(); ++i) {
                  Object o = alist.get(i);
                  System.out.print(name + "[" + i + "] is " + o.getClass().getName() + "=");
                  if (o instanceof StringTemplate) {
                     ((StringTemplate)o).printDebugString();
                  } else {
                     System.out.println(o);
                  }
               }
            } else {
               System.out.print(name + "=");
               System.out.println(value);
            }
         }

         System.out.print("]\n");
      }
   }

   public String toString() {
      return this.toString(-1);
   }

   public String toString(int lineWidth) {
      StringWriter out = new StringWriter();
      StringTemplateWriter wr = this.group.getStringTemplateWriter(out);
      wr.setLineWidth(lineWidth);

      try {
         this.write(wr);
      } catch (IOException var5) {
         this.error("Got IOException writing to writer " + wr.getClass().getName());
      }

      wr.setLineWidth(-1);
      return out.toString();
   }

   public static final class STAttributeList extends ArrayList {
      public STAttributeList(int size) {
         super(size);
      }

      public STAttributeList() {
      }
   }

   public static final class Aggregate {
      protected HashMap properties = new HashMap();

      protected void put(String propName, Object propValue) {
         this.properties.put(propName, propValue);
      }

      public Object get(String propName) {
         return this.properties.get(propName);
      }

      public String toString() {
         return this.properties.toString();
      }
   }
}
