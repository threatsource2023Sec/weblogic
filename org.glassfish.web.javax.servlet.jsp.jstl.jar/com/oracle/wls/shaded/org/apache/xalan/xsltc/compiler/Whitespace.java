package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import java.util.StringTokenizer;
import java.util.Vector;

final class Whitespace extends TopLevelElement {
   public static final int USE_PREDICATE = 0;
   public static final int STRIP_SPACE = 1;
   public static final int PRESERVE_SPACE = 2;
   public static final int RULE_NONE = 0;
   public static final int RULE_ELEMENT = 1;
   public static final int RULE_NAMESPACE = 2;
   public static final int RULE_ALL = 3;
   private String _elementList;
   private int _action;
   private int _importPrecedence;

   public void parseContents(Parser parser) {
      this._action = this._qname.getLocalPart().endsWith("strip-space") ? 1 : 2;
      this._importPrecedence = parser.getCurrentImportPrecedence();
      this._elementList = this.getAttribute("elements");
      if (this._elementList != null && this._elementList.length() != 0) {
         SymbolTable stable = parser.getSymbolTable();
         StringTokenizer list = new StringTokenizer(this._elementList);
         StringBuffer elements = new StringBuffer("");

         while(list.hasMoreElements()) {
            String token = list.nextToken();
            int col = token.indexOf(58);
            if (col != -1) {
               String namespace = this.lookupNamespace(token.substring(0, col));
               if (namespace != null) {
                  elements.append(namespace + ":" + token.substring(col + 1, token.length()));
               } else {
                  elements.append(token);
               }
            } else {
               elements.append(token);
            }

            if (list.hasMoreElements()) {
               elements.append(" ");
            }
         }

         this._elementList = elements.toString();
      } else {
         this.reportError(this, parser, "REQUIRED_ATTR_ERR", "elements");
      }
   }

   public Vector getRules() {
      Vector rules = new Vector();
      StringTokenizer list = new StringTokenizer(this._elementList);

      while(list.hasMoreElements()) {
         rules.add(new WhitespaceRule(this._action, list.nextToken(), this._importPrecedence));
      }

      return rules;
   }

   private static WhitespaceRule findContradictingRule(Vector rules, WhitespaceRule rule) {
      for(int i = 0; i < rules.size(); ++i) {
         WhitespaceRule currentRule = (WhitespaceRule)rules.elementAt(i);
         if (currentRule == rule) {
            return null;
         }

         switch (currentRule.getStrength()) {
            case 1:
               if (!rule.getElement().equals(currentRule.getElement())) {
                  break;
               }
            case 2:
               if (rule.getNamespace().equals(currentRule.getNamespace())) {
                  return currentRule;
               }
               break;
            case 3:
               return currentRule;
         }
      }

      return null;
   }

   private static int prioritizeRules(Vector rules) {
      int defaultAction = 2;
      quicksort(rules, 0, rules.size() - 1);
      boolean strip = false;

      int idx;
      WhitespaceRule currentRule;
      for(idx = 0; idx < rules.size(); ++idx) {
         currentRule = (WhitespaceRule)rules.elementAt(idx);
         if (currentRule.getAction() == 1) {
            strip = true;
         }
      }

      if (!strip) {
         rules.removeAllElements();
         return 2;
      } else {
         idx = 0;

         while(true) {
            while(idx < rules.size()) {
               currentRule = (WhitespaceRule)rules.elementAt(idx);
               if (findContradictingRule(rules, currentRule) != null) {
                  rules.remove(idx);
               } else {
                  if (currentRule.getStrength() == 3) {
                     defaultAction = currentRule.getAction();

                     for(int i = idx; i < rules.size(); ++i) {
                        rules.removeElementAt(i);
                     }
                  }

                  ++idx;
               }
            }

            if (rules.size() == 0) {
               return defaultAction;
            }

            do {
               currentRule = (WhitespaceRule)rules.lastElement();
               if (currentRule.getAction() != defaultAction) {
                  break;
               }

               rules.removeElementAt(rules.size() - 1);
            } while(rules.size() > 0);

            return defaultAction;
         }
      }
   }

   public static void compileStripSpace(BranchHandle[] strip, int sCount, InstructionList il) {
      InstructionHandle target = il.append(ICONST_1);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IRETURN);

      for(int i = 0; i < sCount; ++i) {
         strip[i].setTarget(target);
      }

   }

   public static void compilePreserveSpace(BranchHandle[] preserve, int pCount, InstructionList il) {
      InstructionHandle target = il.append(ICONST_0);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IRETURN);

      for(int i = 0; i < pCount; ++i) {
         preserve[i].setTarget(target);
      }

   }

   private static void compilePredicate(Vector rules, int defaultAction, ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = new InstructionList();
      XSLTC xsltc = classGen.getParser().getXSLTC();
      MethodGenerator stripSpace = new MethodGenerator(17, Type.BOOLEAN, new Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Type.INT, Type.INT}, new String[]{"dom", "node", "type"}, "stripSpace", classGen.getClassName(), il, cpg);
      classGen.addInterface("com/oracle/wls/shaded/org/apache/xalan/xsltc/StripFilter");
      int paramDom = stripSpace.getLocalIndex("dom");
      int paramCurrent = stripSpace.getLocalIndex("node");
      int paramType = stripSpace.getLocalIndex("type");
      BranchHandle[] strip = new BranchHandle[rules.size()];
      BranchHandle[] preserve = new BranchHandle[rules.size()];
      int sCount = 0;
      int pCount = 0;

      for(int i = 0; i < rules.size(); ++i) {
         WhitespaceRule rule = (WhitespaceRule)rules.elementAt(i);
         int gns = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getNamespaceName", "(I)Ljava/lang/String;");
         int strcmp = cpg.addMethodref("java/lang/String", "compareTo", "(Ljava/lang/String;)I");
         if (rule.getStrength() == 2) {
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ALOAD(paramDom)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(paramCurrent)));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(gns, 2)));
            il.append((CompoundInstruction)(new PUSH(cpg, rule.getNamespace())));
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(strcmp)));
            il.append(ICONST_0);
            if (rule.getAction() == 1) {
               strip[sCount++] = il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null)));
            } else {
               preserve[pCount++] = il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null)));
            }
         } else if (rule.getStrength() == 1) {
            Parser parser = classGen.getParser();
            QName qname;
            if (rule.getNamespace() != "") {
               qname = parser.getQName(rule.getNamespace(), (String)null, rule.getElement());
            } else {
               qname = parser.getQName(rule.getElement());
            }

            int elementType = xsltc.registerElement(qname);
            il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(paramType)));
            il.append((CompoundInstruction)(new PUSH(cpg, elementType)));
            if (rule.getAction() == 1) {
               strip[sCount++] = il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null)));
            } else {
               preserve[pCount++] = il.append((BranchInstruction)(new IF_ICMPEQ((InstructionHandle)null)));
            }
         }
      }

      if (defaultAction == 1) {
         compileStripSpace(strip, sCount, il);
         compilePreserveSpace(preserve, pCount, il);
      } else {
         compilePreserveSpace(preserve, pCount, il);
         compileStripSpace(strip, sCount, il);
      }

      classGen.addMethod(stripSpace);
   }

   private static void compileDefault(int defaultAction, ClassGenerator classGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = new InstructionList();
      XSLTC xsltc = classGen.getParser().getXSLTC();
      MethodGenerator stripSpace = new MethodGenerator(17, Type.BOOLEAN, new Type[]{Util.getJCRefType("Lcom/oracle/wls/shaded/org/apache/xalan/xsltc/DOM;"), Type.INT, Type.INT}, new String[]{"dom", "node", "type"}, "stripSpace", classGen.getClassName(), il, cpg);
      classGen.addInterface("com/oracle/wls/shaded/org/apache/xalan/xsltc/StripFilter");
      if (defaultAction == 1) {
         il.append(ICONST_1);
      } else {
         il.append(ICONST_0);
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)IRETURN);
      classGen.addMethod(stripSpace);
   }

   public static int translateRules(Vector rules, ClassGenerator classGen) {
      int defaultAction = prioritizeRules(rules);
      if (rules.size() == 0) {
         compileDefault(defaultAction, classGen);
         return defaultAction;
      } else {
         compilePredicate(rules, defaultAction, classGen);
         return 0;
      }
   }

   private static void quicksort(Vector rules, int p, int r) {
      while(p < r) {
         int q = partition(rules, p, r);
         quicksort(rules, p, q);
         p = q + 1;
      }

   }

   private static int partition(Vector rules, int p, int r) {
      WhitespaceRule x = (WhitespaceRule)rules.elementAt(p + r >>> 1);
      int i = p - 1;
      int j = r + 1;

      while(true) {
         while(true) {
            --j;
            if (x.compareTo((WhitespaceRule)rules.elementAt(j)) >= 0) {
               do {
                  ++i;
               } while(x.compareTo((WhitespaceRule)rules.elementAt(i)) > 0);

               if (i >= j) {
                  return j;
               }

               WhitespaceRule tmp = (WhitespaceRule)rules.elementAt(i);
               rules.setElementAt(rules.elementAt(j), i);
               rules.setElementAt(tmp, j);
            }
         }
      }
   }

   public com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable) throws TypeCheckError {
      return com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
   }

   private static final class WhitespaceRule {
      private final int _action;
      private String _namespace;
      private String _element;
      private int _type;
      private int _priority;

      public WhitespaceRule(int action, String element, int precedence) {
         this._action = action;
         int colon = element.lastIndexOf(58);
         if (colon >= 0) {
            this._namespace = element.substring(0, colon);
            this._element = element.substring(colon + 1, element.length());
         } else {
            this._namespace = "";
            this._element = element;
         }

         this._priority = precedence << 2;
         if (this._element.equals("*")) {
            if (this._namespace == "") {
               this._type = 3;
               this._priority += 2;
            } else {
               this._type = 2;
               ++this._priority;
            }
         } else {
            this._type = 1;
         }

      }

      public int compareTo(WhitespaceRule other) {
         return this._priority < other._priority ? -1 : (this._priority > other._priority ? 1 : 0);
      }

      public int getAction() {
         return this._action;
      }

      public int getStrength() {
         return this._type;
      }

      public int getPriority() {
         return this._priority;
      }

      public String getElement() {
         return this._element;
      }

      public String getNamespace() {
         return this._namespace;
      }
   }
}
