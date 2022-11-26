package weblogic.xml.dtdc;

import com.ibm.xml.parser.AttDef;
import com.ibm.xml.parser.DTD;
import com.ibm.xml.parser.ElementDecl;
import com.ibm.xml.parser.InsertableElement;
import com.ibm.xml.parser.Parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.BadOutputException;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class DTD2Parser extends CodeGenerator {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static final String EOL = System.getProperty("line.separator");
   private static final String ROOT = "root";
   private ParserOutput currentOutput;
   private Vector outputs;
   private DTD dtd;
   private String root;
   private String packageName;
   private ElementDecl element;
   SortedMap allAttributes = this.createTreeMap();
   AttDef currentAttribute;
   private int depth = 0;
   private boolean writingAttribute = false;

   public DTD2Parser(Getopt2 opts) {
      super(opts);
      opts.addOption("package", "weblogic.xml.parsers", "Name of the base package for objects");
      opts.addOption("root", "xsl:stylesheet", "Root element of the DTD");
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.packageName = opts.getOption("package", "weblogic.xml.objects");
      this.root = opts.getOption("root");
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      String[] filenames = (String[])((String[])inputs);
      this.outputs = new Vector();

      for(int i = 0; i < filenames.length; ++i) {
         File f = new File((new File(filenames[i])).getAbsolutePath());
         InputStream is = new FileInputStream(f);
         Parser parse = new Parser(f.getParentFile().toURL().toString());
         DTD dtd = parse.readDTDStream(is);
         if (this.root == null) {
            String fn = f.getName();
            this.root = fn.substring(0, fn.lastIndexOf("."));
         }

         this.outputs.addElement(new ParserOutput(NameMangler.upcase(NameMangler.depackage(this.root)) + "Parser.java", this.packageName, "parser.j", dtd, this.root));
      }

      return this.outputs.elements();
   }

   protected void prepare(CodeGenerator.Output output) throws BadOutputException {
      this.currentOutput = (ParserOutput)output;
   }

   public DTD getDTD() {
      return this.currentOutput.getDTD();
   }

   public String package_name() {
      return this.packageName;
   }

   public String element_class_name() {
      return NameMangler.upcase(NameMangler.depackage(this.root));
   }

   public String parser_class_name() {
      return this.element_class_name() + "Parser";
   }

   public String parser() {
      SortedMap sm = this.createTreeMap();
      sm.put("<?xml", "// Ignore XML version\nwhile(chars[current++] != '>');");
      sm.put("<!DOCTYPE", "// Ignore DTD specification\nwhile(chars[current++] != '>');");
      sm.put(" ", "// Ignore whitespace");
      sm.put("\t", "// Ignore whitespace");
      sm.put("\r", "// Ignore whitespace");
      sm.put("\n", "nextLine();");
      sm.put("<!--", "eatComment(chars);");
      this.depth = 0;
      this.element = this.getDTD().getElementDeclaration(this.root);
      if (this.element == null) {
         throw new Error("No element <" + this.root + "> found in DTD");
      } else {
         sm.put("<" + this.root, this.readElement());
         this.depth = 3;
         this.writingAttribute = false;
         return this.match(sm, "chars[current++]", true);
      }
   }

   public String readElement() {
      StringBuffer sb = new StringBuffer();
      this.format(sb, "sendCharacters(chars, " + (this.element.getName().length() + 1) + ");");
      this.format(sb, "read" + NameMangler.upcase(NameMangler.depackage(this.element.getName())) + "(chars);");
      return sb.toString();
   }

   public String element_name() {
      return this.element.getName();
   }

   public String attribute_name() {
      return NameMangler.depackage(this.currentAttribute.getName()) + "Value";
   }

   public String attribute_realname() {
      return this.currentAttribute.getName();
   }

   public String attribute_typename() {
      return NameMangler.depackage(this.currentAttribute.getName()) + "Type";
   }

   public String attribute_type() {
      return AttDef.S_TYPESTR[this.currentAttribute.getDeclaredType()];
   }

   public String read_element_declarations() throws CodeGenerationException {
      Enumeration elementDeclartions = this.getDTD().getElementDeclarations();
      StringBuffer sb = new StringBuffer();
      this.depth = 2;

      while(elementDeclartions.hasMoreElements()) {
         this.element = (ElementDecl)elementDeclartions.nextElement();
         sb.append(this.parse(this.getProductionRule("read_element_declaration")));
      }

      return sb.toString();
   }

   public String read_element_method_name() {
      return "read" + NameMangler.upcase(NameMangler.depackage(this.element.getName()));
   }

   public String read_element_attributes_method_name() {
      return "read" + NameMangler.upcase(NameMangler.depackage(this.element.getName())) + "Attributes";
   }

   public void readAttributes(StringBuffer sb) {
      this.format(sb, "read" + NameMangler.upcase(NameMangler.depackage(this.element.getName())) + "Attributes(chars);");
   }

   public String declare_required_attributes() {
      Enumeration attributes = this.getDTD().getAttributeDeclarations(this.element.getName());
      StringBuffer sb = new StringBuffer();
      this.depth = 1;

      while(attributes.hasMoreElements()) {
         AttDef attr = (AttDef)attributes.nextElement();
         if (attr.getDefaultType() == 2) {
            this.format(sb, "boolean " + NameMangler.depackage(attr.getName()) + "Found = false;");
         }
      }

      return sb.toString();
   }

   public String required_flag() {
      return NameMangler.depackage(this.currentAttribute.getName()) + "Found";
   }

   public String set_defaults() {
      return "";
   }

   public String set_attribute_found() {
      return this.currentAttribute.getDefaultType() == 2 ? this.required_flag() + " = true;" : "// not required";
   }

   public String ensure_unique() {
      return this.currentAttribute.getDeclaredType() == 2 ? "if(ids.put(value, this) != null) throw new SAXParseException(\"Duplicate ID found: \" + value, this);" : "// not an id";
   }

   public String ensure_required_attributes() {
      Enumeration attributes = this.getDTD().getAttributeDeclarations(this.element.getName());
      StringBuffer sb = new StringBuffer();
      this.depth = 2;

      while(attributes.hasMoreElements()) {
         AttDef attr = (AttDef)attributes.nextElement();
         if (attr.getDefaultType() == 2) {
            this.format(sb, "if(!" + NameMangler.depackage(attr.getName()) + "Found) throw new SAXParseException(\"Required attribute " + attr.getName() + " not found\", this);");
         }
      }

      return sb.toString();
   }

   public String read_attributes() throws CodeGenerationException {
      Enumeration attributes = this.getDTD().getAttributeDeclarations(this.element.getName());
      SortedMap localAttributes = this.createTreeMap();
      localAttributes.put(" ", "// Ignore whitespace");
      localAttributes.put("\t", "// Ignore whitespace");
      localAttributes.put("\r", "// Ignore whitespace");
      localAttributes.put("\n", "// Next line\ncurrentLine++;\nlastLinePosition=current;");
      localAttributes.put("/>", "// Done\nemptyTag=true;done=true;");
      localAttributes.put(">", "// Done\ndone = true;");
      this.depth = 0;

      while(attributes.hasMoreElements()) {
         this.currentAttribute = (AttDef)attributes.nextElement();
         this.allAttributes.put(this.currentAttribute.getName(), this.currentAttribute);
         localAttributes.put(this.currentAttribute.getName(), this.parse(this.getProductionRule("readAttribute")));
      }

      this.depth = 2;
      this.writingAttribute = true;
      return this.match(localAttributes, "chars[current++]", false);
   }

   public String read_element() {
      if (this.element.getContentType() == 1) {
         return "done = true;";
      } else {
         ElementDecl startElement = this.element;
         SortedMap elements = this.createTreeMap();
         elements.put(" ", "// Ignore whitespace");
         elements.put("\t", "// Ignore whitespace");
         elements.put("\r", "// Ignore whitespace");
         elements.put("\n", "nextLine();");
         elements.put("</" + this.element.getName() + ">", "sendCharacters(chars, " + (this.element.getName().length() + 3) + ");\ndone = true;");
         elements.put("&", "handleEscapes(chars);");
         elements.put("<![CDATA[", "handleCDATA(chars);");
         elements.put("<!--", "eatComment(chars);");
         Hashtable ht = this.getDTD().prepareTable(this.element.getName());
         Enumeration insertableElementsNames = ht.keys();
         this.depth = 0;

         while(insertableElementsNames.hasMoreElements()) {
            InsertableElement ie = (InsertableElement)ht.get(insertableElementsNames.nextElement());
            if ((this.element = this.getDTD().getElementDeclaration(ie.name)) != null) {
               elements.put("<" + ie.name, this.readElement());
            }
         }

         this.depth = 2;
         this.element = startElement;
         this.writingAttribute = false;
         return this.match(elements, "chars[current++]", false);
      }
   }

   public String max_attributes() {
      return "" + this.allAttributes.size();
   }

   public String set_attribute_types() {
      this.depth = 2;
      Iterator attrs = this.allAttributes.keySet().iterator();
      StringBuffer sb = new StringBuffer();

      while(attrs.hasNext()) {
         AttDef attr = (AttDef)this.allAttributes.get(attrs.next());
         this.format(sb, "htypes.put(\"" + attr.getName() + "\", \"" + AttDef.S_TYPESTR[attr.getDeclaredType()] + "\");");
      }

      return sb.toString();
   }

   public String valid_attribute() {
      if (this.currentAttribute.getDeclaredType() != 10) {
         return this.currentAttribute.getDefaultType() == 1 ? "&& !value.equals(\"" + this.currentAttribute.getDefaultStringValue() + "\")" : "&& false";
      } else {
         StringBuffer sb = new StringBuffer();
         Enumeration tokens = this.currentAttribute.elements();

         while(tokens.hasMoreElements()) {
            String token = (String)tokens.nextElement();
            sb.append(" && !value.equals(\"" + token + "\")");
         }

         return sb.toString();
      }
   }

   private StringBuffer format(StringBuffer sb, String line) {
      for(int i = this.depth; i > 0; --i) {
         sb.append("  ");
      }

      return sb.append(line).append(EOL);
   }

   private StringBuffer format(StringBuffer sb, String text, boolean multiline) {
      if (multiline) {
         try {
            BufferedReader br = new BufferedReader(new StringReader(text));

            String line;
            while((line = br.readLine()) != null) {
               this.format(sb, line);
            }
         } catch (IOException var6) {
         }
      } else {
         this.format(sb, text);
      }

      return sb;
   }

   private String match(SortedMap nameCodePairs, String nextChar, boolean isRoot) {
      StringBuffer sb = new StringBuffer();
      this.makeSwitch(sb, nameCodePairs, nameCodePairs, 0, nextChar, (String)null, isRoot);
      return sb.toString();
   }

   private SortedMap makeTreeAtCurrentLookahead(Iterator keys, SortedMap nameCodePairs, int lookahead) {
      String lastKey = null;
      if (lookahead == 0) {
         return nameCodePairs;
      } else {
         SortedMap sm = this.createTreeMap();

         while(true) {
            while(keys.hasNext()) {
               String key = (String)keys.next();
               if (lastKey != null) {
                  if (key.length() < lookahead || lastKey.length() < lookahead || key.charAt(lookahead - 1) != lastKey.charAt(lookahead - 1)) {
                     return sm;
                  }

                  sm.put(key, nameCodePairs.get(key));
               } else {
                  sm.put(key, nameCodePairs.get(key));
                  lastKey = key;
               }
            }

            return sm;
         }
      }
   }

   private void makeSwitch(StringBuffer sb, SortedMap subSwitch, SortedMap nameCodePairs, int lookahead, String nextChar, String character, boolean isRoot) {
      this.format(sb, "switch(" + nextChar + ") {");
      Iterator keys = subSwitch.keySet().iterator();

      while(keys.hasNext()) {
         String key = (String)keys.next();
         SortedMap subCases = subSwitch.tailMap(key);
         int skip = this.makeCase(sb, subCases, nameCodePairs, lookahead, nextChar, isRoot);

         while(true) {
            --skip;
            if (skip <= 0) {
               break;
            }

            keys.next();
         }
      }

      this.makeDefault(sb, lookahead, character, isRoot);
   }

   private void makeDefault(StringBuffer sb, int lookahead, String character, boolean isRoot) {
      this.format(sb, "default:");
      ++this.depth;
      if (!this.writingAttribute && lookahead == 0) {
         this.format(sb, "if(startCharacterData == -1) startCharacterData = current - 1;");
      } else if (!isRoot && character != null && character.charAt(0) == '<') {
         this.format(sb, "current -= 2; sendCharacters(chars, 0); done = true; continue;");
      } else {
         this.format(sb, "throw new SAXParseException(\"Could not parse: " + this.element.getName() + " starting at line \" + startLine, this);");
      }

      --this.depth;
      this.format(sb, "}");
   }

   private int makeCase(StringBuffer sb, SortedMap subCases, SortedMap nameCodePairs, int lookahead, String nextChar, boolean isRoot) {
      String key = (String)subCases.firstKey();
      SortedMap newSubCases = this.makeTreeAtCurrentLookahead(subCases.keySet().iterator(), nameCodePairs, lookahead + 1);
      if (key.length() == lookahead) {
         newSubCases.remove(key);
         this.format(sb, "case '\\t': case ' ': case '\\r': case '\\n':");
         if (this.writingAttribute) {
            this.format(sb, "case '=':");
         } else {
            this.format(sb, "case '>':");
         }

         ++this.depth;
         this.format(sb, "current--;");
         this.format(sb, nameCodePairs.get(key).toString(), true);
         this.format(sb, "continue;");
      } else {
         char charValue = key.charAt(lookahead);
         String character;
         switch (charValue) {
            case '\t':
               character = "\\t";
               break;
            case '\n':
               character = "\\n";
               break;
            case '\u000b':
            case '\f':
            default:
               character = (new Character(charValue)).toString();
               break;
            case '\r':
               character = "\\r";
         }

         this.format(sb, "case '" + character + "':");
         ++this.depth;
         if (newSubCases.size() != 1) {
            this.makeSwitch(sb, newSubCases, nameCodePairs, lookahead + 1, nextChar, character, isRoot);
         } else {
            if (character.charAt(0) == '/') {
               this.format(sb, "try {");
               ++this.depth;
            }

            if (key.length() != lookahead + 1) {
               StringBuffer vb = new StringBuffer();
               vb.append("match(chars, \"");

               for(int i = 0; i < key.length() - lookahead - 1; ++i) {
                  vb.append(key.charAt(i + lookahead + 1));
               }

               vb.append("\", \"" + this.element.getName() + "\", startLine);");
               this.format(sb, vb.toString());
            }

            this.format(sb, nameCodePairs.get(key).toString(), true);
            if (character.charAt(0) == '/') {
               --this.depth;
               this.format(sb, "} catch(Exception e) { current -= 2; sendCharacters(chars, 0); done = true; continue; }");
            }

            this.format(sb, "continue;");
         }
      }

      --this.depth;
      return newSubCases.size();
   }

   public TreeMap createTreeMap() {
      TreeMap tm = new TreeMap(new Comparator() {
         public int compare(Object o1, Object o2) {
            String s1 = (String)o1;
            String s2 = (String)o2;
            return s1.compareTo(s2);
         }
      });
      return tm;
   }
}
