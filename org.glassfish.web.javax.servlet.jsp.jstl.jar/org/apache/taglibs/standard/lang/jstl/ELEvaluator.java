package org.apache.taglibs.standard.lang.jstl;

import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.taglibs.standard.lang.jstl.parser.ELParser;
import org.apache.taglibs.standard.lang.jstl.parser.ParseException;
import org.apache.taglibs.standard.lang.jstl.parser.Token;
import org.apache.taglibs.standard.lang.jstl.parser.TokenMgrError;

public class ELEvaluator {
   static Map sCachedExpressionStrings = Collections.synchronizedMap(new HashMap());
   static Map sCachedExpectedTypes = new HashMap();
   static Logger sLogger;
   VariableResolver mResolver;
   boolean mBypassCache;

   public ELEvaluator(VariableResolver pResolver) {
      this.mResolver = pResolver;
   }

   public ELEvaluator(VariableResolver pResolver, boolean pBypassCache) {
      this.mResolver = pResolver;
      this.mBypassCache = pBypassCache;
   }

   public Object evaluate(String pExpressionString, Object pContext, Class pExpectedType, Map functions, String defaultPrefix) throws ELException {
      return this.evaluate(pExpressionString, pContext, pExpectedType, functions, defaultPrefix, sLogger);
   }

   Object evaluate(String pExpressionString, Object pContext, Class pExpectedType, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      if (pExpressionString == null) {
         throw new ELException(Constants.NULL_EXPRESSION_STRING);
      } else {
         Object parsedValue = this.parseExpressionString(pExpressionString);
         String strValue;
         if (parsedValue instanceof String) {
            strValue = (String)parsedValue;
            return this.convertStaticValueToExpectedType(strValue, pExpectedType, pLogger);
         } else if (parsedValue instanceof Expression) {
            Object value = ((Expression)parsedValue).evaluate(pContext, this.mResolver, functions, defaultPrefix, pLogger);
            return this.convertToExpectedType(value, pExpectedType, pLogger);
         } else if (parsedValue instanceof ExpressionString) {
            strValue = ((ExpressionString)parsedValue).evaluate(pContext, this.mResolver, functions, defaultPrefix, pLogger);
            return this.convertToExpectedType(strValue, pExpectedType, pLogger);
         } else {
            return null;
         }
      }
   }

   public Object parseExpressionString(String pExpressionString) throws ELException {
      if (pExpressionString.length() == 0) {
         return "";
      } else {
         Object ret = this.mBypassCache ? null : sCachedExpressionStrings.get(pExpressionString);
         if (ret == null) {
            Reader r = new StringReader(pExpressionString);
            ELParser parser = new ELParser(r);

            try {
               ret = parser.ExpressionString();
               sCachedExpressionStrings.put(pExpressionString, ret);
            } catch (ParseException var6) {
               throw new ELException(formatParseException(pExpressionString, var6));
            } catch (TokenMgrError var7) {
               throw new ELException(var7.getMessage());
            }
         }

         return ret;
      }
   }

   Object convertToExpectedType(Object pValue, Class pExpectedType, Logger pLogger) throws ELException {
      return Coercions.coerce(pValue, pExpectedType, pLogger);
   }

   Object convertStaticValueToExpectedType(String pValue, Class pExpectedType, Logger pLogger) throws ELException {
      if (pExpectedType != String.class && pExpectedType != Object.class) {
         Map valueByString = getOrCreateExpectedTypeMap(pExpectedType);
         if (!this.mBypassCache && valueByString.containsKey(pValue)) {
            return valueByString.get(pValue);
         } else {
            Object ret = Coercions.coerce(pValue, pExpectedType, pLogger);
            valueByString.put(pValue, ret);
            return ret;
         }
      } else {
         return pValue;
      }
   }

   static Map getOrCreateExpectedTypeMap(Class pExpectedType) {
      synchronized(sCachedExpectedTypes) {
         Map ret = (Map)sCachedExpectedTypes.get(pExpectedType);
         if (ret == null) {
            ret = Collections.synchronizedMap(new HashMap());
            sCachedExpectedTypes.put(pExpectedType, ret);
         }

         return ret;
      }
   }

   static String formatParseException(String pExpressionString, ParseException pExc) {
      StringBuffer expectedBuf = new StringBuffer();
      int maxSize = 0;
      boolean printedOne = false;
      if (pExc.expectedTokenSequences == null) {
         return pExc.toString();
      } else {
         for(int i = 0; i < pExc.expectedTokenSequences.length; ++i) {
            if (maxSize < pExc.expectedTokenSequences[i].length) {
               maxSize = pExc.expectedTokenSequences[i].length;
            }

            for(int j = 0; j < pExc.expectedTokenSequences[i].length; ++j) {
               if (printedOne) {
                  expectedBuf.append(", ");
               }

               expectedBuf.append(pExc.tokenImage[pExc.expectedTokenSequences[i][j]]);
               printedOne = true;
            }
         }

         String expected = expectedBuf.toString();
         StringBuffer encounteredBuf = new StringBuffer();
         Token tok = pExc.currentToken.next;

         for(int i = 0; i < maxSize; ++i) {
            if (i != 0) {
               encounteredBuf.append(" ");
            }

            if (tok.kind == 0) {
               encounteredBuf.append(pExc.tokenImage[0]);
               break;
            }

            encounteredBuf.append(addEscapes(tok.image));
            tok = tok.next;
         }

         String encountered = encounteredBuf.toString();
         return MessageFormat.format(Constants.PARSE_EXCEPTION, expected, encountered);
      }
   }

   static String addEscapes(String str) {
      StringBuffer retval = new StringBuffer();

      for(int i = 0; i < str.length(); ++i) {
         switch (str.charAt(i)) {
            case '\u0000':
               break;
            case '\u0001':
            case '\u0002':
            case '\u0003':
            case '\u0004':
            case '\u0005':
            case '\u0006':
            case '\u0007':
            case '\u000b':
            default:
               char ch;
               if ((ch = str.charAt(i)) >= ' ' && ch <= '~') {
                  retval.append(ch);
                  break;
               }

               String s = "0000" + Integer.toString(ch, 16);
               retval.append("\\u" + s.substring(s.length() - 4, s.length()));
               break;
            case '\b':
               retval.append("\\b");
               break;
            case '\t':
               retval.append("\\t");
               break;
            case '\n':
               retval.append("\\n");
               break;
            case '\f':
               retval.append("\\f");
               break;
            case '\r':
               retval.append("\\r");
         }
      }

      return retval.toString();
   }

   public String parseAndRender(String pExpressionString) throws ELException {
      Object val = this.parseExpressionString(pExpressionString);
      if (val instanceof String) {
         return (String)val;
      } else if (val instanceof Expression) {
         return "${" + ((Expression)val).getExpressionString() + "}";
      } else {
         return val instanceof ExpressionString ? ((ExpressionString)val).getExpressionString() : "";
      }
   }

   static {
      sLogger = new Logger(System.out);
   }
}
