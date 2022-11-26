package org.python.jline.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.jline.internal.Log;

public class ConsoleKeys {
   private KeyMap keys;
   private Map keyMaps = KeyMap.keyMaps();
   private Map variables = new HashMap();

   public ConsoleKeys(String appName, URL inputrcUrl) {
      this.setVar("editing-mode", "emacs");
      this.loadKeys(appName, inputrcUrl);
   }

   protected boolean setKeyMap(String name) {
      KeyMap map = (KeyMap)this.keyMaps.get(name);
      if (map == null) {
         return false;
      } else {
         this.keys = map;
         return true;
      }
   }

   protected Map getKeyMaps() {
      return this.keyMaps;
   }

   protected KeyMap getKeys() {
      return this.keys;
   }

   protected void setKeys(KeyMap keys) {
      this.keys = keys;
   }

   protected void loadKeys(String appName, URL inputrcUrl) {
      this.keys = (KeyMap)this.keyMaps.get("emacs");

      try {
         InputStream input = inputrcUrl.openStream();

         try {
            this.loadKeys(input, appName);
            Log.debug("Loaded user configuration: ", inputrcUrl);
         } finally {
            try {
               input.close();
            } catch (IOException var11) {
            }

         }
      } catch (IOException var13) {
         if (inputrcUrl.getProtocol().equals("file")) {
            File file = new File(inputrcUrl.getPath());
            if (file.exists()) {
               Log.warn("Unable to read user configuration: ", inputrcUrl, var13);
            }
         } else {
            Log.warn("Unable to read user configuration: ", inputrcUrl, var13);
         }
      }

   }

   private void loadKeys(InputStream input, String appName) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      boolean parsing = true;
      List ifsStack = new ArrayList();

      String line;
      while((line = reader.readLine()) != null) {
         try {
            line = line.trim();
            if (line.length() != 0 && line.charAt(0) != '#') {
               int i = 0;
               String keySeq;
               int start;
               String val;
               if (line.charAt(i) == '$') {
                  ++i;

                  while(i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                     ++i;
                  }

                  for(start = i; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; ++i) {
                  }

                  String cmd;
                  for(cmd = line.substring(start, i); i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); ++i) {
                  }

                  for(start = i; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; ++i) {
                  }

                  keySeq = line.substring(start, i);
                  if ("if".equalsIgnoreCase(cmd)) {
                     ifsStack.add(parsing);
                     if (parsing && !keySeq.startsWith("term=")) {
                        if (keySeq.startsWith("mode=")) {
                           val = (String)this.variables.get("editing-mode");
                           parsing = keySeq.substring("mode=".length()).equalsIgnoreCase(val);
                        } else {
                           parsing = keySeq.equalsIgnoreCase(appName);
                        }
                     }
                  } else if (!"else".equalsIgnoreCase(cmd)) {
                     if ("endif".equalsIgnoreCase(cmd)) {
                        if (ifsStack.isEmpty()) {
                           throw new IllegalArgumentException("endif found without matching $if");
                        }

                        parsing = (Boolean)ifsStack.remove(ifsStack.size() - 1);
                     } else if ("include".equalsIgnoreCase(cmd)) {
                     }
                  } else {
                     if (ifsStack.isEmpty()) {
                        throw new IllegalArgumentException("$else found without matching $if");
                     }

                     boolean invert = true;
                     Iterator var24 = ifsStack.iterator();

                     while(var24.hasNext()) {
                        boolean b = (Boolean)var24.next();
                        if (!b) {
                           invert = false;
                           break;
                        }
                     }

                     if (invert) {
                        parsing = !parsing;
                     }
                  }
               } else if (parsing) {
                  keySeq = "";
                  if (line.charAt(i++) == '"') {
                     boolean esc = false;

                     while(true) {
                        if (i >= line.length()) {
                           throw new IllegalArgumentException("Missing closing quote on line '" + line + "'");
                        }

                        if (esc) {
                           esc = false;
                        } else if (line.charAt(i) == '\\') {
                           esc = true;
                        } else if (line.charAt(i) == '"') {
                           break;
                        }

                        ++i;
                     }
                  }

                  while(i < line.length() && line.charAt(i) != ':' && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                     ++i;
                  }

                  keySeq = line.substring(0, i);
                  boolean equivalency = i + 1 < line.length() && line.charAt(i) == ':' && line.charAt(i + 1) == '=';
                  ++i;
                  if (equivalency) {
                     ++i;
                  }

                  if (keySeq.equalsIgnoreCase("set")) {
                     while(i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        ++i;
                     }

                     int s;
                     for(s = i; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; ++i) {
                     }

                     String key;
                     for(key = line.substring(s, i); i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); ++i) {
                     }

                     for(s = i; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; ++i) {
                     }

                     val = line.substring(s, i);
                     this.setVar(key, val);
                  } else {
                     while(i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        ++i;
                     }

                     start = i;
                     if (i < line.length() && (line.charAt(i) == '\'' || line.charAt(i) == '"')) {
                        char delim = line.charAt(i++);

                        for(boolean esc = false; i < line.length(); ++i) {
                           if (esc) {
                              esc = false;
                           } else if (line.charAt(i) == '\\') {
                              esc = true;
                           } else if (line.charAt(i) == delim) {
                              break;
                           }
                        }
                     }

                     while(i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                        ++i;
                     }

                     val = line.substring(Math.min(start, line.length()), Math.min(i, line.length()));
                     String operationName;
                     if (keySeq.charAt(0) == '"') {
                        keySeq = translateQuoted(keySeq);
                     } else {
                        operationName = keySeq.lastIndexOf(45) > 0 ? keySeq.substring(keySeq.lastIndexOf(45) + 1) : keySeq;
                        char key = getKeyFromName(operationName);
                        operationName = keySeq.toLowerCase();
                        keySeq = "";
                        if (operationName.contains("meta-") || operationName.contains("m-")) {
                           keySeq = keySeq + "\u001b";
                        }

                        if (operationName.contains("control-") || operationName.contains("c-") || operationName.contains("ctrl-")) {
                           key = (char)(Character.toUpperCase(key) & 31);
                        }

                        keySeq = keySeq + key;
                     }

                     if (val.length() > 0 && (val.charAt(0) == '\'' || val.charAt(0) == '"')) {
                        this.keys.bind(keySeq, translateQuoted(val));
                     } else {
                        operationName = val.replace('-', '_').toUpperCase();

                        try {
                           this.keys.bind(keySeq, Operation.valueOf(operationName));
                        } catch (IllegalArgumentException var14) {
                           Log.info("Unable to bind key for unsupported operation: ", val);
                        }
                     }
                  }
               }
            }
         } catch (IllegalArgumentException var15) {
            Log.warn("Unable to parse user configuration: ", var15);
         }
      }

   }

   private static String translateQuoted(String keySeq) {
      String str = keySeq.substring(1, keySeq.length() - 1);
      keySeq = "";

      for(int i = 0; i < str.length(); ++i) {
         char c = str.charAt(i);
         if (c == '\\') {
            boolean ctrl = str.regionMatches(i, "\\C-", 0, 3) || str.regionMatches(i, "\\M-\\C-", 0, 6);
            boolean meta = str.regionMatches(i, "\\M-", 0, 3) || str.regionMatches(i, "\\C-\\M-", 0, 6);
            i += (meta ? 3 : 0) + (ctrl ? 3 : 0) + (!meta && !ctrl ? 1 : 0);
            if (i >= str.length()) {
               break;
            }

            c = str.charAt(i);
            if (meta) {
               keySeq = keySeq + "\u001b";
            }

            if (ctrl) {
               c = c == '?' ? 127 : (char)(Character.toUpperCase(c) & 31);
            }

            if (!meta && !ctrl) {
               int j;
               int k;
               label105:
               switch (c) {
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                     c = 0;

                     for(j = 0; j < 3 && i < str.length(); ++i) {
                        k = Character.digit(str.charAt(i), 8);
                        if (k < 0) {
                           break;
                        }

                        c = (char)(c * 8 + k);
                        ++j;
                     }

                     c = (char)(c & 255);
                  case '8':
                  case '9':
                  case ':':
                  case ';':
                  case '<':
                  case '=':
                  case '>':
                  case '?':
                  case '@':
                  case 'A':
                  case 'B':
                  case 'C':
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'G':
                  case 'H':
                  case 'I':
                  case 'J':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'S':
                  case 'T':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case '[':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  case 'c':
                  case 'g':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'l':
                  case 'm':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 's':
                  case 'w':
                  default:
                     break;
                  case '\\':
                     c = '\\';
                     break;
                  case 'a':
                     c = 7;
                     break;
                  case 'b':
                     c = '\b';
                     break;
                  case 'd':
                     c = 127;
                     break;
                  case 'e':
                     c = 27;
                     break;
                  case 'f':
                     c = '\f';
                     break;
                  case 'n':
                     c = '\n';
                     break;
                  case 'r':
                     c = '\r';
                     break;
                  case 't':
                     c = '\t';
                     break;
                  case 'u':
                     ++i;
                     c = 0;
                     j = 0;

                     while(true) {
                        if (j >= 4 || i >= str.length()) {
                           break label105;
                        }

                        k = Character.digit(str.charAt(i), 16);
                        if (k < 0) {
                           break label105;
                        }

                        c = (char)(c * 16 + k);
                        ++j;
                        ++i;
                     }
                  case 'v':
                     c = 11;
                     break;
                  case 'x':
                     ++i;
                     c = 0;

                     for(j = 0; j < 2 && i < str.length(); ++i) {
                        k = Character.digit(str.charAt(i), 16);
                        if (k < 0) {
                           break;
                        }

                        c = (char)(c * 16 + k);
                        ++j;
                     }

                     c = (char)(c & 255);
               }
            }

            keySeq = keySeq + c;
         } else {
            keySeq = keySeq + c;
         }
      }

      return keySeq;
   }

   private static char getKeyFromName(String name) {
      if (!"DEL".equalsIgnoreCase(name) && !"Rubout".equalsIgnoreCase(name)) {
         if (!"ESC".equalsIgnoreCase(name) && !"Escape".equalsIgnoreCase(name)) {
            if (!"LFD".equalsIgnoreCase(name) && !"NewLine".equalsIgnoreCase(name)) {
               if (!"RET".equalsIgnoreCase(name) && !"Return".equalsIgnoreCase(name)) {
                  if (!"SPC".equalsIgnoreCase(name) && !"Space".equalsIgnoreCase(name)) {
                     return "Tab".equalsIgnoreCase(name) ? '\t' : name.charAt(0);
                  } else {
                     return ' ';
                  }
               } else {
                  return '\r';
               }
            } else {
               return '\n';
            }
         } else {
            return '\u001b';
         }
      } else {
         return '\u007f';
      }
   }

   private void setVar(String key, String val) {
      if ("keymap".equalsIgnoreCase(key)) {
         if (this.keyMaps.containsKey(val)) {
            this.keys = (KeyMap)this.keyMaps.get(val);
         }
      } else if ("editing-mode".equals(key)) {
         if ("vi".equalsIgnoreCase(val)) {
            this.keys = (KeyMap)this.keyMaps.get("vi-insert");
         } else if ("emacs".equalsIgnoreCase(key)) {
            this.keys = (KeyMap)this.keyMaps.get("emacs");
         }
      } else if ("blink-matching-paren".equals(key)) {
         if ("on".equalsIgnoreCase(val)) {
            this.keys.setBlinkMatchingParen(true);
         } else if ("off".equalsIgnoreCase(val)) {
            this.keys.setBlinkMatchingParen(false);
         }
      }

      this.variables.put(key, val);
   }

   public String getVariable(String var) {
      return (String)this.variables.get(var);
   }
}
