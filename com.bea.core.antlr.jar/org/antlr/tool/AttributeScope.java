package org.antlr.tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.antlr.codegen.CodeGenerator;
import org.antlr.runtime.Token;

public class AttributeScope {
   public static final AttributeScope tokenScope = new AttributeScope("Token", (Token)null);
   public Token derivedFromToken;
   public Grammar grammar;
   private String name;
   public boolean isDynamicGlobalScope;
   public boolean isDynamicRuleScope;
   public boolean isParameterScope;
   public boolean isReturnScope;
   public boolean isPredefinedRuleScope;
   public boolean isPredefinedLexerRuleScope;
   protected LinkedHashMap attributes;
   public LinkedHashMap actions;

   public AttributeScope(String name, Token derivedFromToken) {
      this((Grammar)null, name, derivedFromToken);
   }

   public AttributeScope(Grammar grammar, String name, Token derivedFromToken) {
      this.attributes = new LinkedHashMap();
      this.actions = new LinkedHashMap();
      this.grammar = grammar;
      this.name = name;
      this.derivedFromToken = derivedFromToken;
   }

   public String getName() {
      if (this.isParameterScope) {
         return this.name + "_parameter";
      } else {
         return this.isReturnScope ? this.name + "_return" : this.name;
      }
   }

   public void addAttributes(String definitions, int separator) {
      List attrs = new ArrayList();
      CodeGenerator.getListOfArgumentsFromAction(definitions, 0, -1, separator, attrs);

      Attribute attr;
      for(Iterator i$ = attrs.iterator(); i$.hasNext(); this.attributes.put(attr.name, attr)) {
         String a = (String)i$.next();
         attr = new Attribute(a);
         if (!this.isReturnScope && attr.initValue != null) {
            ErrorManager.grammarError(148, this.grammar, this.derivedFromToken, attr.name);
            attr.initValue = null;
         }
      }

   }

   public void addAttribute(String name, String decl) {
      this.attributes.put(name, new Attribute(name, decl));
   }

   public final void defineNamedAction(GrammarAST nameAST, GrammarAST actionAST) {
      String actionName = nameAST.getText();
      GrammarAST a = (GrammarAST)this.actions.get(actionName);
      if (a != null) {
         ErrorManager.grammarError(144, this.grammar, nameAST.getToken(), nameAST.getText());
      } else {
         this.actions.put(actionName, actionAST);
      }

   }

   public Attribute getAttribute(String name) {
      return (Attribute)this.attributes.get(name);
   }

   public List getAttributes() {
      List a = new ArrayList();
      a.addAll(this.attributes.values());
      return a;
   }

   public Set intersection(AttributeScope other) {
      if (other != null && other.size() != 0 && this.size() != 0) {
         Set inter = new HashSet();
         Set thisKeys = this.attributes.keySet();
         Iterator i$ = thisKeys.iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            if (other.attributes.get(key) != null) {
               inter.add(key);
            }
         }

         if (inter.isEmpty()) {
            return null;
         } else {
            return inter;
         }
      } else {
         return null;
      }
   }

   public int size() {
      return this.attributes == null ? 0 : this.attributes.size();
   }

   public String toString() {
      return (this.isDynamicGlobalScope ? "global " : "") + this.getName() + ":" + this.attributes;
   }

   static {
      tokenScope.addAttribute("text", (String)null);
      tokenScope.addAttribute("type", (String)null);
      tokenScope.addAttribute("line", (String)null);
      tokenScope.addAttribute("index", (String)null);
      tokenScope.addAttribute("pos", (String)null);
      tokenScope.addAttribute("channel", (String)null);
      tokenScope.addAttribute("tree", (String)null);
      tokenScope.addAttribute("int", (String)null);
   }
}
