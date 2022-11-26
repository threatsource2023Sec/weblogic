package com.bea.core.repackaged.aspectj.weaver.loadtime.definition;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LightXMLParser {
   private static final char NULL_CHAR = '\u0000';
   private Map attributes = new HashMap();
   private ArrayList children = new ArrayList();
   private String name = null;
   private char pushedBackChar;
   private Reader reader;
   private static Map entities = new HashMap();

   public ArrayList getChildrens() {
      return this.children;
   }

   public String getName() {
      return this.name;
   }

   public void parseFromReader(Reader reader) throws Exception {
      this.pushedBackChar = 0;
      this.attributes = new HashMap();
      this.name = null;
      this.children = new ArrayList();
      this.reader = reader;

      while(true) {
         char c = this.skipBlanks();
         if (c != '<') {
            throw new Exception("LightParser Exception: Expected < but got: " + c);
         }

         c = this.getNextChar();
         if (c != '!' && c != '?') {
            this.pushBackChar(c);
            this.parseNode(this);
            return;
         }

         this.skipCommentOrXmlTag(0);
      }
   }

   private char skipBlanks() throws Exception {
      while(true) {
         char c = this.getNextChar();
         switch (c) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               break;
            default:
               return c;
         }
      }
   }

   private char getWhitespaces(StringBuffer result) throws Exception {
      while(true) {
         char c = this.getNextChar();
         switch (c) {
            case '\t':
            case '\n':
            case ' ':
               result.append(c);
            case '\r':
               break;
            default:
               return c;
         }
      }
   }

   private void getNodeName(StringBuffer result) throws Exception {
      while(true) {
         char c = this.getNextChar();
         if ((c < 'a' || c > 'z') && (c > 'Z' || c < 'A') && (c > '9' || c < '0') && c != '_' && c != '-' && c != '.' && c != ':') {
            this.pushBackChar(c);
            return;
         }

         result.append(c);
      }
   }

   private void getString(StringBuffer string) throws Exception {
      char delimiter = this.getNextChar();
      if (delimiter != '\'' && delimiter != '"') {
         throw new Exception("Parsing error. Expected ' or \"  but got: " + delimiter);
      } else {
         while(true) {
            char c = this.getNextChar();
            if (c == delimiter) {
               return;
            }

            if (c == '&') {
               this.mapEntity(string);
            } else {
               string.append(c);
            }
         }
      }
   }

   private void getPCData(StringBuffer data) throws Exception {
      while(true) {
         char c = this.getNextChar();
         if (c == '<') {
            c = this.getNextChar();
            if (c != '!') {
               this.pushBackChar(c);
               return;
            }

            this.checkCDATA(data);
         } else {
            data.append(c);
         }
      }
   }

   private boolean checkCDATA(StringBuffer buf) throws Exception {
      char c = this.getNextChar();
      if (c != '[') {
         this.pushBackChar(c);
         this.skipCommentOrXmlTag(0);
         return false;
      } else if (!this.checkLiteral("CDATA[")) {
         this.skipCommentOrXmlTag(1);
         return false;
      } else {
         int delimiterCharsSkipped = 0;

         while(true) {
            while(delimiterCharsSkipped < 3) {
               c = this.getNextChar();
               int i;
               switch (c) {
                  case '>':
                     if (delimiterCharsSkipped >= 2) {
                        delimiterCharsSkipped = 3;
                        continue;
                     }

                     for(i = 0; i < delimiterCharsSkipped; ++i) {
                        buf.append(']');
                     }

                     delimiterCharsSkipped = 0;
                     buf.append('>');
                     continue;
                  case ']':
                     if (delimiterCharsSkipped < 2) {
                        ++delimiterCharsSkipped;
                     } else {
                        buf.append(']');
                        buf.append(']');
                        delimiterCharsSkipped = 0;
                     }
                     continue;
                  default:
                     i = 0;
               }

               while(i < delimiterCharsSkipped) {
                  buf.append(']');
                  ++i;
               }

               buf.append(c);
               delimiterCharsSkipped = 0;
            }

            return true;
         }
      }
   }

   private void skipCommentOrXmlTag(int bracketLevel) throws Exception {
      char delim = 0;
      int level = 1;
      char c;
      if (bracketLevel == 0) {
         c = this.getNextChar();
         if (c == '-') {
            c = this.getNextChar();
            if (c == ']') {
               --bracketLevel;
            } else if (c == '[') {
               ++bracketLevel;
            } else if (c == '-') {
               this.skipComment();
               return;
            }
         } else if (c == '[') {
            ++bracketLevel;
         }
      }

      while(true) {
         while(level > 0) {
            c = this.getNextChar();
            if (delim == 0) {
               if (c != '"' && c != '\'') {
                  if (bracketLevel <= 0) {
                     if (c == '<') {
                        ++level;
                     } else if (c == '>') {
                        --level;
                     }
                  }
               } else {
                  delim = c;
               }

               if (c == '[') {
                  ++bracketLevel;
               } else if (c == ']') {
                  --bracketLevel;
               }
            } else if (c == delim) {
               delim = 0;
            }
         }

         return;
      }
   }

   private void parseNode(LightXMLParser elt) throws Exception {
      StringBuffer buf = new StringBuffer();
      this.getNodeName(buf);
      String name = buf.toString();
      elt.setName(name);

      char c;
      for(c = this.skipBlanks(); c != '>' && c != '/'; c = this.skipBlanks()) {
         this.emptyBuf(buf);
         this.pushBackChar(c);
         this.getNodeName(buf);
         String key = buf.toString();
         c = this.skipBlanks();
         if (c != '=') {
            throw new Exception("Parsing error. Expected = but got: " + c);
         }

         this.pushBackChar(this.skipBlanks());
         this.emptyBuf(buf);
         this.getString(buf);
         elt.setAttribute(key, buf);
      }

      if (c == '/') {
         c = this.getNextChar();
         if (c != '>') {
            throw new Exception("Parsing error. Expected > but got: " + c);
         }
      } else {
         this.emptyBuf(buf);
         c = this.getWhitespaces(buf);
         if (c != '<') {
            this.pushBackChar(c);
            this.getPCData(buf);
         } else {
            while(true) {
               c = this.getNextChar();
               if (c != '!') {
                  if (c != '/') {
                     this.emptyBuf(buf);
                  }

                  if (c == '/') {
                     this.pushBackChar(c);
                  }
                  break;
               }

               if (this.checkCDATA(buf)) {
                  this.getPCData(buf);
                  break;
               }

               c = this.getWhitespaces(buf);
               if (c != '<') {
                  this.pushBackChar(c);
                  this.getPCData(buf);
                  break;
               }
            }
         }

         if (buf.length() == 0) {
            while(true) {
               if (c == '/') {
                  this.pushBackChar(c);
                  break;
               }

               if (c != '!') {
                  this.pushBackChar(c);
                  LightXMLParser child = this.createAnotherElement();
                  this.parseNode(child);
                  elt.addChild(child);
               } else {
                  for(int i = 0; i < 2; ++i) {
                     c = this.getNextChar();
                     if (c != '-') {
                        throw new Exception("Parsing error. Expected element or comment");
                     }
                  }

                  this.skipComment();
               }

               c = this.skipBlanks();
               if (c != '<') {
                  throw new Exception("Parsing error. Expected <, but got: " + c);
               }

               c = this.getNextChar();
            }
         }

         c = this.getNextChar();
         if (c != '/') {
            throw new Exception("Parsing error. Expected /, but got: " + c);
         } else {
            this.pushBackChar(this.skipBlanks());
            if (!this.checkLiteral(name)) {
               throw new Exception("Parsing error. Expected " + name);
            } else if (this.skipBlanks() != '>') {
               throw new Exception("Parsing error. Expected >, but got: " + c);
            }
         }
      }
   }

   private void skipComment() throws Exception {
      int dashes = 2;

      char nextChar;
      while(dashes > 0) {
         nextChar = this.getNextChar();
         if (nextChar == '-') {
            --dashes;
         } else {
            dashes = 2;
         }
      }

      nextChar = this.getNextChar();
      if (nextChar != '>') {
         throw new Exception("Parsing error. Expected > but got: " + nextChar);
      }
   }

   private boolean checkLiteral(String literal) throws Exception {
      int length = literal.length();

      for(int i = 0; i < length; ++i) {
         if (this.getNextChar() != literal.charAt(i)) {
            return false;
         }
      }

      return true;
   }

   private char getNextChar() throws Exception {
      if (this.pushedBackChar != 0) {
         char c = this.pushedBackChar;
         this.pushedBackChar = 0;
         return c;
      } else {
         int i = this.reader.read();
         if (i < 0) {
            throw new Exception("Parsing error. Unexpected end of data");
         } else {
            return (char)i;
         }
      }
   }

   private void mapEntity(StringBuffer buf) throws Exception {
      char c = false;
      StringBuffer keyBuf = new StringBuffer();

      while(true) {
         char c = this.getNextChar();
         if (c == ';') {
            String key = keyBuf.toString();
            if (key.charAt(0) == '#') {
               try {
                  if (key.charAt(1) == 'x') {
                     c = (char)Integer.parseInt(key.substring(2), 16);
                  } else {
                     c = (char)Integer.parseInt(key.substring(1), 10);
                  }
               } catch (NumberFormatException var6) {
                  throw new Exception("Unknown entity: " + key);
               }

               buf.append(c);
            } else {
               char[] value = (char[])((char[])entities.get(key));
               if (value == null) {
                  throw new Exception("Unknown entity: " + key);
               }

               buf.append(value);
            }

            return;
         }

         keyBuf.append(c);
      }
   }

   private void pushBackChar(char c) {
      this.pushedBackChar = c;
   }

   private void addChild(LightXMLParser child) {
      this.children.add(child);
   }

   private void setAttribute(String name, Object value) {
      this.attributes.put(name, value.toString());
   }

   public Map getAttributes() {
      return this.attributes;
   }

   private LightXMLParser createAnotherElement() {
      return new LightXMLParser();
   }

   private void setName(String name) {
      this.name = name;
   }

   private void emptyBuf(StringBuffer buf) {
      buf.setLength(0);
   }

   static {
      entities.put("amp", new char[]{'&'});
      entities.put("quot", new char[]{'"'});
      entities.put("apos", new char[]{'\''});
      entities.put("lt", new char[]{'<'});
      entities.put("gt", new char[]{'>'});
   }
}
