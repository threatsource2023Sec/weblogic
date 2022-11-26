package weblogic.diagnostics.instrumentation.engine.base;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public class PointcutParser extends LLkParser implements PointcutParserTokenTypes {
   private Map valueRendererByNameMap;
   private Map pointcutMap;
   private List pointcutNames;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "IDENTIFIER", "EQ", "SEMI", "\"OR\"", "\"AND\"", "\"NOT\"", "LPAREN", "RPAREN", "\"execution\"", "\"construction\"", "\"within\"", "\"catch\"", "\"call\"", "\"newcall\"", "\"public\"", "\"private\"", "\"protected\"", "\"static\"", "\"*\"", "AT", "COMMA", "ELIPSES", "PERCENT", "PLUS", "TILDA", "POUND", "\"byte\"", "\"char\"", "\"boolean\"", "\"short\"", "\"int\"", "\"float\"", "\"long\"", "\"double\"", "\"void\"", "LBRACKET", "RBRACKET", "DIGIT", "LETTER", "DOT", "OR", "AND", "NOT", "WS", "COMMENT_DATA", "COMMENT"};

   public void setValueRendererByNameMap(Map valueRendererByNameMap) {
      this.valueRendererByNameMap = valueRendererByNameMap;
   }

   public Map getPointcuts() {
      return this.pointcutMap;
   }

   List getPointcutNames() {
      return this.pointcutNames;
   }

   private String toRegexPattern(String pat) {
      if (!pat.equals("*") && !pat.equals("**")) {
         pat = pat.replaceAll("\\.", "/");
         pat = pat.replaceAll("\\$", "\\\\\\$");
         if (!pat.startsWith("*")) {
            pat = "^" + pat;
         }

         if (!pat.endsWith("*")) {
            pat = pat + "$";
         }

         pat = pat.replaceAll("\\*", "(.*)");
         return pat;
      } else {
         return "(.*)";
      }
   }

   private PointcutSpecification createPointcut(int type, ModifierExpression modExpr, TypeSpecification rSpec, TypeSpecification cSpec, String methodPattern, List argsList) throws InvalidPointcutException {
      PointcutSpecification pSpec = new PointcutSpecification();
      pSpec.setType(type);
      pSpec.setAccess(modExpr);
      pSpec.setReturnSelector(rSpec);
      pSpec.setClassSelector(cSpec);
      int argsCnt = argsList != null ? argsList.size() : 0;
      if (argsCnt > 0) {
         TypeSpecification[] aSpecs = new TypeSpecification[argsCnt];
         aSpecs = (TypeSpecification[])((TypeSpecification[])argsList.toArray(aSpecs));
         pSpec.setArgumentSelectors(aSpecs);
      }

      if (methodPattern != null) {
         pSpec.setMethodSelector(methodPattern);
      }

      return pSpec;
   }

   protected PointcutParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.valueRendererByNameMap = new HashMap();
      this.pointcutMap = new HashMap();
      this.pointcutNames = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public PointcutParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected PointcutParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.valueRendererByNameMap = new HashMap();
      this.pointcutMap = new HashMap();
      this.pointcutNames = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public PointcutParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public PointcutParser(ParserSharedInputState state) {
      super(state, 1);
      this.valueRendererByNameMap = new HashMap();
      this.pointcutMap = new HashMap();
      this.pointcutNames = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public final void parse() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      int _cnt3;
      for(_cnt3 = 0; this.LA(1) == 4; ++_cnt3) {
         this.pointcutStatement();
      }

      if (_cnt3 < 1) {
         throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final void pointcutStatement() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token name = null;
      PointcutExpression pExpr = null;
      name = this.LT(1);
      this.match(4);
      this.match(5);
      pExpr = this.pointcutExpr();
      this.match(6);
      String pointcutName = name.getText();
      if (this.pointcutMap.get(pointcutName) != null) {
         throw new InvalidPointcutException("Duplicate definition of pointcut " + pointcutName);
      } else {
         this.pointcutMap.put(pointcutName, pExpr);
         this.pointcutNames.add(pointcutName);
      }
   }

   public final PointcutExpression pointcutExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutExpression pExpr = null;
      PointcutExpression tmpExpr = null;

      for(pExpr = this.termExpr(); this.LA(1) == 7; pExpr = new OrPointcutExpression((PointcutExpression)pExpr, tmpExpr)) {
         this.match(7);
         tmpExpr = this.termExpr();
      }

      return (PointcutExpression)pExpr;
   }

   public final PointcutExpression termExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutExpression pExpr = null;
      PointcutExpression tmpExpr = null;

      for(pExpr = this.factorExpr(); this.LA(1) == 8; pExpr = new AndPointcutExpression((PointcutExpression)pExpr, tmpExpr)) {
         this.match(8);
         tmpExpr = this.factorExpr();
      }

      return (PointcutExpression)pExpr;
   }

   public final PointcutExpression factorExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutExpression pExpr = null;
      PointcutExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 4:
         case 10:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
            pExpr = this.atomExpr();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 11:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 9:
            this.match(9);
            tmpExpr = this.atomExpr();
            pExpr = new NotPointcutExpression(tmpExpr);
      }

      return (PointcutExpression)pExpr;
   }

   public final PointcutExpression atomExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token id = null;
      PointcutExpression pExpr = null;
      PointcutExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 4:
            id = this.LT(1);
            this.match(4);
            String pointcutName = id.getText();
            pExpr = (PointcutExpression)this.pointcutMap.get(pointcutName);
            if (pExpr == null) {
               throw new InvalidPointcutException("Pointcut " + pointcutName + " is used before defined");
            }
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 11:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 10:
            this.match(10);
            pExpr = this.pointcutExpr();
            this.match(11);
            break;
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
            pExpr = this.pointcut();
      }

      return (PointcutExpression)pExpr;
   }

   public final PointcutSpecification pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      switch (this.LA(1)) {
         case 12:
            pSpec = this.exec_pointcut();
            break;
         case 13:
            pSpec = this.construct_pointcut();
            break;
         case 14:
            pSpec = this.within_pointcut();
            break;
         case 15:
            pSpec = this.catch_pointcut();
            break;
         case 16:
            pSpec = this.call_pointcut();
            break;
         case 17:
            pSpec = this.newcall_pointcut();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return pSpec;
   }

   public final PointcutSpecification call_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification rSpec = null;
      TypeSpecification cSpec = null;
      String methodPattern = null;
      List argsList = null;
      ModifierExpression modExpr = null;
      this.match(16);
      this.match(10);
      switch (this.LA(1)) {
         case 5:
         case 6:
         case 7:
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 20:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 9:
         case 10:
         case 19:
         case 21:
            modExpr = this.callmodifierExpr();
         case 4:
         case 22:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            rSpec = this.returnSpec();
            cSpec = this.classSpec();
            methodPattern = this.methodSpec();
            this.match(10);
            switch (this.LA(1)) {
               case 4:
               case 22:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
                  argsList = this.argsSpec();
               case 11:
                  this.match(11);
                  this.match(11);
                  pSpec = this.createPointcut(1, modExpr, rSpec, cSpec, methodPattern, argsList);
                  return pSpec;
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 23:
               case 24:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
      }
   }

   public final PointcutSpecification exec_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification rSpec = null;
      TypeSpecification cSpec = null;
      String methodPattern = null;
      List argsList = null;
      ModifierExpression modExpr = null;
      this.match(12);
      this.match(10);
      switch (this.LA(1)) {
         case 5:
         case 6:
         case 7:
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 9:
         case 10:
         case 18:
         case 19:
         case 20:
         case 21:
            modExpr = this.modifierExpr();
         case 4:
         case 22:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            rSpec = this.returnSpec();
            cSpec = this.classSpecWithAnnotations();
            methodPattern = this.methodSpecWithAnnotation();
            this.match(10);
            switch (this.LA(1)) {
               case 4:
               case 22:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
                  argsList = this.argsSpec();
               case 11:
                  this.match(11);
                  this.match(11);
                  pSpec = this.createPointcut(0, modExpr, rSpec, cSpec, methodPattern, argsList);
                  return pSpec;
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 23:
               case 24:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
      }
   }

   public final PointcutSpecification construct_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification cSpec = null;
      String constructorPattern = "<init>";
      List argsList = null;
      ModifierExpression modExpr = null;
      this.match(13);
      this.match(10);
      switch (this.LA(1)) {
         case 5:
         case 6:
         case 7:
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 9:
         case 10:
         case 18:
         case 19:
         case 20:
         case 21:
            modExpr = this.modifierExpr();
         case 4:
         case 22:
         case 23:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            cSpec = this.classSpecWithAnnotations();
            switch (this.LA(1)) {
               case 23:
                  constructorPattern = this.constructorSpecWithAnnotation();
               case 10:
                  this.match(10);
                  switch (this.LA(1)) {
                     case 4:
                     case 22:
                     case 25:
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                     case 34:
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                        argsList = this.argsSpec();
                     case 11:
                        this.match(11);
                        this.match(11);
                        pSpec = this.createPointcut(3, modExpr, cSpec, cSpec, constructorPattern, argsList);
                        return pSpec;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 23:
                     case 24:
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
      }
   }

   public final PointcutSpecification newcall_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification cSpec = null;
      List argsList = null;
      this.match(17);
      this.match(10);
      cSpec = this.classSpec();
      this.match(10);
      switch (this.LA(1)) {
         case 4:
         case 22:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            argsList = this.argsSpec();
         case 11:
            this.match(11);
            this.match(11);
            pSpec = this.createPointcut(4, (ModifierExpression)null, cSpec, cSpec, "<init>", argsList);
            return pSpec;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 24:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final PointcutSpecification within_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification rSpec = null;
      TypeSpecification cSpec = null;
      String methodPattern = null;
      List argsList = null;
      ModifierExpression modExpr = null;
      this.match(14);
      this.match(10);
      cSpec = this.classSpecWithAnnotations();
      switch (this.LA(1)) {
         case 4:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 22:
         case 23:
            methodPattern = this.methodSpecWithAnnotation();
         case 10:
         case 11:
            switch (this.LA(1)) {
               case 10:
                  this.match(10);
                  switch (this.LA(1)) {
                     case 4:
                     case 22:
                     case 25:
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                     case 34:
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                        argsList = this.argsSpec();
                     case 11:
                        this.match(11);
                        break;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 23:
                     case 24:
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }
               case 11:
                  this.match(11);
                  if (methodPattern == null) {
                     methodPattern = this.toRegexPattern("*");
                     argsList = new ArrayList();
                     TypeSpecification tSpec = new TypeSpecification();
                     tSpec.setType(2);
                     ((List)argsList).add(tSpec);
                  }

                  pSpec = this.createPointcut(2, (ModifierExpression)modExpr, (TypeSpecification)rSpec, cSpec, methodPattern, (List)argsList);
                  return pSpec;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final PointcutSpecification catch_pointcut() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      PointcutSpecification pSpec = null;
      TypeSpecification cSpec = null;
      this.match(15);
      this.match(10);
      cSpec = this.classSpec();
      this.match(11);
      pSpec = this.createPointcut(5, (ModifierExpression)null, (TypeSpecification)null, cSpec, (String)null, (List)null);
      return pSpec;
   }

   public final ModifierExpression modifierExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;

      for(mExpr = this.modifierTermExpr(); this.LA(1) == 7; mExpr = new OrModifierExpression((ModifierExpression)mExpr, tmpExpr)) {
         this.match(7);
         tmpExpr = this.modifierTermExpr();
      }

      return (ModifierExpression)mExpr;
   }

   public final TypeSpecification returnSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      TypeSpecification tSpec = new TypeSpecification();
      switch (this.LA(1)) {
         case 4:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            tSpec = this.typeSpec();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 22:
            this.match(22);
            tSpec.setType(1);
      }

      return tSpec;
   }

   public final TypeSpecification classSpecWithAnnotations() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token id1 = null;
      Token id2 = null;
      TypeSpecification tSpec = null;
      String pattern = null;
      switch (this.LA(1)) {
         case 4:
         case 22:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            tSpec = this.classSpec();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 23:
            this.match(23);
            id1 = this.LT(1);
            this.match(4);

            for(pattern = this.toRegexPattern(id1.getText()); this.LA(1) == 7; pattern = pattern + "|" + this.toRegexPattern(id2.getText())) {
               this.match(7);
               id2 = this.LT(1);
               this.match(4);
            }

            tSpec = new TypeSpecification();
            tSpec.setType(3);
            tSpec.setPattern(pattern, true);
      }

      return tSpec;
   }

   public final String methodSpecWithAnnotation() throws RecognitionException, TokenStreamException {
      Token pat1 = null;
      Token pat2 = null;
      String methodPattern = null;
      switch (this.LA(1)) {
         case 4:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 22:
            methodPattern = this.methodSpec();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 23:
            this.match(23);
            pat1 = this.LT(1);
            this.match(4);

            for(methodPattern = "@" + this.toRegexPattern(pat1.getText()); this.LA(1) == 24; methodPattern = methodPattern + "|" + this.toRegexPattern(pat2.getText())) {
               this.match(24);
               pat2 = this.LT(1);
               this.match(4);
            }
      }

      return methodPattern;
   }

   public final List argsSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      List argsList = null;
      TypeSpecification tSpec = this.argSpec();
      argsList = new ArrayList();
      argsList.add(tSpec);

      while(this.LA(1) == 24) {
         this.match(24);
         tSpec = this.argSpec();
         argsList.add(tSpec);
      }

      return argsList;
   }

   public final String constructorSpecWithAnnotation() throws RecognitionException, TokenStreamException {
      Token pat1 = null;
      Token pat2 = null;
      String constructorPattern = null;
      this.match(23);
      pat1 = this.LT(1);
      this.match(4);

      for(constructorPattern = "@" + this.toRegexPattern(pat1.getText()); this.LA(1) == 24; constructorPattern = constructorPattern + "|" + this.toRegexPattern(pat2.getText())) {
         this.match(24);
         pat2 = this.LT(1);
         this.match(4);
      }

      return constructorPattern;
   }

   public final TypeSpecification classSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      TypeSpecification tSpec = null;
      switch (this.LA(1)) {
         case 4:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            tSpec = this.basicTypeSpec();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 22:
            this.match(22);
            tSpec = new TypeSpecification();
            tSpec.setType(1);
      }

      return tSpec;
   }

   public final ModifierExpression callmodifierExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;

      for(mExpr = this.callmodifierTermExpr(); this.LA(1) == 7; mExpr = new OrModifierExpression((ModifierExpression)mExpr, tmpExpr)) {
         this.match(7);
         tmpExpr = this.callmodifierTermExpr();
      }

      return (ModifierExpression)mExpr;
   }

   public final String methodSpec() throws RecognitionException, TokenStreamException {
      Token pattern = null;
      String methodPattern = null;
      switch (this.LA(1)) {
         case 4:
            pattern = this.LT(1);
            this.match(4);
            methodPattern = this.toRegexPattern(pattern.getText());
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
            methodPattern = this.keyWord();
            break;
         case 22:
            this.match(22);
            methodPattern = this.toRegexPattern("*");
      }

      return methodPattern;
   }

   public final ModifierExpression modifierTermExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;

      for(mExpr = this.modifierFactorExpr(); this.LA(1) == 8; mExpr = new AndModifierExpression((ModifierExpression)mExpr, tmpExpr)) {
         this.match(8);
         tmpExpr = this.modifierFactorExpr();
      }

      return (ModifierExpression)mExpr;
   }

   public final ModifierExpression modifierFactorExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 9:
            this.match(9);
            tmpExpr = this.modifierAtomExpr();
            mExpr = new NotModifierExpression(tmpExpr);
            break;
         case 10:
         case 18:
         case 19:
         case 20:
         case 21:
            mExpr = this.modifierAtomExpr();
            break;
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return (ModifierExpression)mExpr;
   }

   public final ModifierExpression modifierAtomExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 10:
            this.match(10);
            mExpr = this.modifierExpr();
            this.match(11);
            break;
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 18:
            this.match(18);
            mExpr = new ModifierValue(1);
            break;
         case 19:
            this.match(19);
            mExpr = new ModifierValue(2);
            break;
         case 20:
            this.match(20);
            mExpr = new ModifierValue(4);
            break;
         case 21:
            this.match(21);
            mExpr = new ModifierValue(8);
      }

      return (ModifierExpression)mExpr;
   }

   public final ModifierExpression callmodifierTermExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;

      for(mExpr = this.callmodifierFactorExpr(); this.LA(1) == 8; mExpr = new AndModifierExpression((ModifierExpression)mExpr, tmpExpr)) {
         this.match(8);
         tmpExpr = this.callmodifierFactorExpr();
      }

      return (ModifierExpression)mExpr;
   }

   public final ModifierExpression callmodifierFactorExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 9:
            this.match(9);
            tmpExpr = this.callmodifierAtomExpr();
            mExpr = new NotModifierExpression(tmpExpr);
            break;
         case 10:
         case 19:
         case 21:
            mExpr = this.callmodifierAtomExpr();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return (ModifierExpression)mExpr;
   }

   public final ModifierExpression callmodifierAtomExpr() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      ModifierExpression mExpr = null;
      ModifierExpression tmpExpr = null;
      switch (this.LA(1)) {
         case 10:
            this.match(10);
            mExpr = this.callmodifierExpr();
            this.match(11);
            break;
         case 19:
            this.match(19);
            mExpr = new ModifierValue(2);
            break;
         case 21:
            this.match(21);
            mExpr = new ModifierValue(8);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return (ModifierExpression)mExpr;
   }

   public final TypeSpecification typeSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      new TypeSpecification();
      TypeSpecification tSpec = this.basicTypeSpec();

      while(this.LA(1) == 39) {
         this.arrayDim();
         tSpec.setDimension(tSpec.getDimension() + 1);
      }

      return tSpec;
   }

   public final TypeSpecification basicTypeSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      boolean sensitive = true;
      String argumentName = null;
      String valueRendererClassName = null;
      switch (this.LA(1)) {
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 26:
            this.match(26);
            sensitive = false;
         case 4:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            switch (this.LA(1)) {
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               case 29:
                  argumentName = this.argumentNameSpec();
               case 4:
               case 27:
               case 28:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
                  switch (this.LA(1)) {
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 11:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 22:
                     case 23:
                     case 24:
                     case 25:
                     case 26:
                     case 29:
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                     case 28:
                        valueRendererClassName = this.valueRendererClassNameSpec();
                     case 4:
                     case 27:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                     case 34:
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                        TypeSpecification tSpec;
                        switch (this.LA(1)) {
                           case 4:
                           case 27:
                              tSpec = this.classBasicTypeSpec();
                              break;
                           case 5:
                           case 6:
                           case 7:
                           case 8:
                           case 9:
                           case 10:
                           case 11:
                           case 12:
                           case 13:
                           case 14:
                           case 15:
                           case 16:
                           case 17:
                           case 18:
                           case 19:
                           case 20:
                           case 21:
                           case 22:
                           case 23:
                           case 24:
                           case 25:
                           case 26:
                           case 28:
                           case 29:
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                           case 30:
                           case 31:
                           case 32:
                           case 33:
                           case 34:
                           case 35:
                           case 36:
                           case 37:
                           case 38:
                              tSpec = this.primitiveBasicTypeSpec();
                        }

                        tSpec.setSensitive(sensitive);
                        tSpec.setArgumentName(argumentName);
                        if (valueRendererClassName != null) {
                           String mappedClassName = (String)this.valueRendererByNameMap.get(valueRendererClassName);
                           if (mappedClassName != null) {
                              tSpec.setValueRendererClassName(mappedClassName);
                           } else {
                              tSpec.setValueRendererClassName(valueRendererClassName);
                           }
                        }

                        if (argumentName != null && !sensitive) {
                           tSpec.setGatherable(true);
                        }

                        return tSpec;
                  }
            }
      }
   }

   public final String keyWord() throws RecognitionException, TokenStreamException {
      String key = null;
      switch (this.LA(1)) {
         case 12:
            this.match(12);
            key = "execution";
            break;
         case 13:
            this.match(13);
            key = "construction";
            break;
         case 14:
            this.match(14);
            key = "within";
            break;
         case 15:
            this.match(15);
            key = "catch";
            break;
         case 16:
            this.match(16);
            key = "call";
            break;
         case 17:
            this.match(17);
            key = "newcall";
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return key;
   }

   public final TypeSpecification argSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      TypeSpecification tSpec = new TypeSpecification();
      switch (this.LA(1)) {
         case 4:
         case 22:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            tSpec = this.returnSpec();
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 24:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 25:
            this.match(25);
            tSpec.setType(2);
      }

      return tSpec;
   }

   public final void arrayDim() throws RecognitionException, TokenStreamException {
      this.match(39);
      this.match(40);
   }

   public final String argumentNameSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token id1 = null;
      String aSpec = null;
      this.match(29);
      id1 = this.LT(1);
      this.match(4);
      if (id1 == null) {
         aSpec = "";
      } else {
         aSpec = id1.getText();
      }

      return aSpec;
   }

   public final String valueRendererClassNameSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token id1 = null;
      String vSpec = null;
      this.match(28);
      id1 = this.LT(1);
      this.match(4);
      vSpec = id1.getText();
      return vSpec;
   }

   public final TypeSpecification primitiveBasicTypeSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      TypeSpecification tSpec = new TypeSpecification();
      String pattern = null;
      String prim = this.primitiveType();

      for(pattern = prim; this.LA(1) == 7; pattern = pattern + "|" + prim) {
         this.match(7);
         prim = this.primitiveType();
      }

      tSpec.setPattern(pattern);
      tSpec.setPrimitive(true);
      tSpec.setType(3);
      return tSpec;
   }

   public final TypeSpecification classBasicTypeSpec() throws RecognitionException, TokenStreamException, InvalidPointcutException {
      Token id3 = null;
      Token id4 = null;
      TypeSpecification tSpec = new TypeSpecification();
      boolean allowInheritance = false;
      String pattern = null;
      switch (this.LA(1)) {
         case 27:
            this.match(27);
            allowInheritance = true;
         case 4:
            id3 = this.LT(1);
            this.match(4);

            for(pattern = this.toRegexPattern(id3.getText()); this.LA(1) == 7; pattern = pattern + "|" + this.toRegexPattern(id4.getText())) {
               this.match(7);
               id4 = this.LT(1);
               this.match(4);
            }

            tSpec.setPattern(pattern);
            tSpec.setPrimitive(false);
            tSpec.setType(3);
            tSpec.setInheritanceAllowed(allowInheritance);
            return tSpec;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final String primitiveType() throws RecognitionException, TokenStreamException {
      String str = null;
      switch (this.LA(1)) {
         case 30:
            this.match(30);
            str = "B";
            break;
         case 31:
            this.match(31);
            str = "C";
            break;
         case 32:
            this.match(32);
            str = "Z";
            break;
         case 33:
            this.match(33);
            str = "S";
            break;
         case 34:
            this.match(34);
            str = "I";
            break;
         case 35:
            this.match(35);
            str = "F";
            break;
         case 36:
            this.match(36);
            str = "J";
            break;
         case 37:
            this.match(37);
            str = "D";
            break;
         case 38:
            this.match(38);
            str = "V";
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return str;
   }
}
