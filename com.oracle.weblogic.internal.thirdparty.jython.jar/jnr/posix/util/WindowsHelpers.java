package jnr.posix.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.posix.POSIX;

public class WindowsHelpers {
   static final Runtime runtime = Runtime.getSystemRuntime();
   static final int WORDSIZE = Runtime.getSystemRuntime().addressSize();
   private static final String COMMAND_DOT_COM = "command.com";
   private static final int CDC_LENGTH = "command.com".length();
   private static Map INTERNAL_COMMANDS = new HashMap() {
      {
         this.put("assoc", WindowsHelpers.InternalType.COMMAND);
         this.put("break", WindowsHelpers.InternalType.BOTH);
         this.put("call", WindowsHelpers.InternalType.BOTH);
         this.put("cd", WindowsHelpers.InternalType.BOTH);
         this.put("chcp", WindowsHelpers.InternalType.SHELL);
         this.put("chdir", WindowsHelpers.InternalType.BOTH);
         this.put("cls", WindowsHelpers.InternalType.BOTH);
         this.put("color", WindowsHelpers.InternalType.COMMAND);
         this.put("copy", WindowsHelpers.InternalType.BOTH);
         this.put("ctty", WindowsHelpers.InternalType.SHELL);
         this.put("date", WindowsHelpers.InternalType.BOTH);
         this.put("del", WindowsHelpers.InternalType.BOTH);
         this.put("dir", WindowsHelpers.InternalType.BOTH);
         this.put("echo", WindowsHelpers.InternalType.BOTH);
         this.put("endlocal", WindowsHelpers.InternalType.COMMAND);
         this.put("erase", WindowsHelpers.InternalType.BOTH);
         this.put("exit", WindowsHelpers.InternalType.BOTH);
         this.put("for", WindowsHelpers.InternalType.BOTH);
         this.put("ftype", WindowsHelpers.InternalType.COMMAND);
         this.put("goto", WindowsHelpers.InternalType.BOTH);
         this.put("if", WindowsHelpers.InternalType.BOTH);
         this.put("lfnfor", WindowsHelpers.InternalType.SHELL);
         this.put("lh", WindowsHelpers.InternalType.SHELL);
         this.put("lock", WindowsHelpers.InternalType.SHELL);
         this.put("md", WindowsHelpers.InternalType.BOTH);
         this.put("mkdir", WindowsHelpers.InternalType.BOTH);
         this.put("move", WindowsHelpers.InternalType.COMMAND);
         this.put("path", WindowsHelpers.InternalType.BOTH);
         this.put("pause", WindowsHelpers.InternalType.BOTH);
         this.put("popd", WindowsHelpers.InternalType.COMMAND);
         this.put("prompt", WindowsHelpers.InternalType.BOTH);
         this.put("pushd", WindowsHelpers.InternalType.COMMAND);
         this.put("rd", WindowsHelpers.InternalType.BOTH);
         this.put("rem", WindowsHelpers.InternalType.BOTH);
         this.put("ren", WindowsHelpers.InternalType.BOTH);
         this.put("rename", WindowsHelpers.InternalType.BOTH);
         this.put("rmdir", WindowsHelpers.InternalType.BOTH);
         this.put("set", WindowsHelpers.InternalType.BOTH);
         this.put("setlocal", WindowsHelpers.InternalType.COMMAND);
         this.put("shift", WindowsHelpers.InternalType.BOTH);
         this.put("start", WindowsHelpers.InternalType.COMMAND);
         this.put("time", WindowsHelpers.InternalType.BOTH);
         this.put("title", WindowsHelpers.InternalType.COMMAND);
         this.put("truename", WindowsHelpers.InternalType.SHELL);
         this.put("type", WindowsHelpers.InternalType.BOTH);
         this.put("unlock", WindowsHelpers.InternalType.SHELL);
         this.put("ver", WindowsHelpers.InternalType.BOTH);
         this.put("verify", WindowsHelpers.InternalType.BOTH);
         this.put("vol", WindowsHelpers.InternalType.BOTH);
      }
   };

   public static byte[] toWPath(String path) {
      return toWString(path);
   }

   public static byte[] toWString(String string) {
      if (string == null) {
         return null;
      } else {
         string = string + '\u0000';

         try {
            return string.getBytes("UTF-16LE");
         } catch (UnsupportedEncodingException var2) {
            return null;
         }
      }
   }

   public static Pointer createWideEnv(String[] envp) {
      if (envp == null) {
         return null;
      } else {
         byte[] marker = new byte[]{0};
         int envLength = envp.length;
         Pointer result = Memory.allocateDirect(runtime, WORDSIZE * (envLength + 1));

         for(int i = 0; i < envLength; ++i) {
            byte[] bytes = toWString(envp[i]);
            Pointer envElement = Memory.allocateDirect(runtime, bytes.length + 1);
            envElement.put(0L, (byte[])bytes, 0, bytes.length);
            envElement.put((long)bytes.length, (byte[])marker, 0, marker.length);
            result.putPointer((long)(i * WORDSIZE), envElement);
         }

         Pointer nullMarker = Memory.allocateDirect(runtime, marker.length);
         nullMarker.put(0L, (byte[])marker, 0, marker.length);
         result.putPointer((long)(WORDSIZE * envLength), nullMarker);
         return result;
      }
   }

   private static void joinSingleArgv(StringBuilder buffer, String arg, boolean quote, boolean escape) {
      int backslashCount = 0;
      int start = 0;
      if (quote) {
         buffer.append('"');
      }

      for(int i = 0; i < arg.length(); ++i) {
         char c = arg.charAt(i);
         switch (c) {
            case '"':
               buffer.append(arg.substring(start, i));

               for(int j = 0; j < backslashCount + 1; ++j) {
                  buffer.append('\\');
               }

               backslashCount = 0;
               start = i;
            case '<':
            case '>':
            case '^':
            case '|':
               if (escape && !quote) {
                  buffer.append(arg.substring(start, i));
                  buffer.append('^');
                  start = i;
                  continue;
               }
               break;
            case '\\':
               ++backslashCount;
               continue;
         }

         backslashCount = 0;
      }

      buffer.append(arg.substring(start));
      if (quote) {
         buffer.append('"');
      }

   }

   public static String joinArgv(String command, String[] argv, boolean escape) {
      StringBuilder buffer = new StringBuilder();
      if (command != null) {
         buffer.append(command);
         buffer.append(' ');
      }

      int last_index = argv.length - 1;

      for(int i = 0; i <= last_index; ++i) {
         joinSingleArgv(buffer, argv[i], quotable(argv[i]), escape);
         if (i != last_index) {
            buffer.append(' ');
         }
      }

      return buffer.toString();
   }

   public static boolean quotable(String value) {
      if (value == null) {
         return false;
      } else {
         StringTokenizer toker = new StringTokenizer(value, " \t\"'");
         toker.nextToken();
         return toker.hasMoreTokens();
      }
   }

   public static boolean isBatch(String value) {
      if (value == null) {
         return false;
      } else {
         int length = value.length();
         if (length < 5) {
            return false;
         } else {
            String end = value.substring(length - 4);
            return end.equalsIgnoreCase(".bat") || end.equalsIgnoreCase(".cmd");
         }
      }
   }

   public static String[] processCommandLine(POSIX posix, String command, String program, String path) {
      String shell = null;
      if (program != null) {
         String fullPath = Finder.findFileInPath(posix, program, path);
         shell = fullPath == null ? program : fullPath.replace('/', '\\');
      } else {
         command = command.substring(firstNonWhitespaceIndex(command));
         shell = System.getenv("COMSPEC");
         boolean notHandledYet = true;
         if (shell != null) {
            boolean commandDotCom = isCommandDotCom(shell);
            if (hasBuiltinSpecialNeeds(command) || isInternalCommand(command, commandDotCom)) {
               String quote = commandDotCom ? "\"" : "";
               command = shell + " /c " + quote + command + quote;
               notHandledYet = false;
            }
         }

         if (notHandledYet) {
            char firstChar = command.charAt(0);
            char quote = firstChar == '"' ? firstChar : (firstChar == '\'' ? firstChar : 0);
            int commandLength = command.length();
            int i = quote == 0 ? 0 : 1;

            while(true) {
               if (i == commandLength) {
                  shell = command;
                  break;
               }

               char c = command.charAt(i);
               if (c == quote) {
                  shell = command.substring(1, i);
                  break;
               }

               if (quote == 0 && (Character.isSpaceChar(c) || isFunnyChar(c))) {
                  shell = command.substring(0, i);
                  break;
               }

               ++i;
            }

            shell = Finder.findFileInPath(posix, shell, path);
            if (shell == null) {
               shell = command.substring(0, i);
            } else {
               if (!shell.contains(" ")) {
                  char quote = false;
               }

               shell = shell.replace('/', '\\');
            }
         }
      }

      return new String[]{command, shell};
   }

   public static String[] processCommandArgs(POSIX posix, String program, String[] argv, String path) {
      if (program == null || program.length() == 0) {
         program = argv[0];
      }

      boolean addSlashC = false;
      boolean isNotBuiltin = false;
      boolean notHandledYet = true;
      String shell = System.getenv("COMSPEC");
      String command = null;
      if (shell != null) {
         boolean commandDotCom = isCommandDotCom(shell);
         if (isInternalCommand(program, commandDotCom)) {
            isNotBuiltin = !commandDotCom;
            program = shell;
            addSlashC = true;
            notHandledYet = false;
         }
      }

      if (notHandledYet) {
         command = Finder.findFileInPath(posix, program, path);
         if (command != null) {
            program = command.replace('/', '\\');
         } else if (program.contains("/")) {
            command = program.replace('/', '\\');
            program = command;
         }
      }

      if (!addSlashC && !isBatch(program)) {
         command = joinArgv((String)null, argv, false);
      } else {
         if (addSlashC) {
            command = program + " /c ";
         } else {
            String[] newArgv = new String[argv.length - 1];
            System.arraycopy(argv, 1, newArgv, 0, argv.length - 1);
            argv = newArgv;
         }

         if (argv.length > 0) {
            command = joinArgv(command, argv, isNotBuiltin);
         }

         program = addSlashC ? shell : null;
      }

      return new String[]{command, program};
   }

   private static boolean isFunnyChar(char c) {
      return c == '<' || c == '>' || c == '|' || c == '*' || c == '?' || c == '"';
   }

   private static boolean hasBuiltinSpecialNeeds(String value) {
      int length = value.length();
      char quote = 0;

      for(int i = 0; i < length; ++i) {
         char c = value.charAt(i);
         switch (c) {
            case '\n':
            case '<':
            case '>':
            case '|':
               if (quote != 0) {
                  return true;
               }
               break;
            case '"':
            case '\'':
               if (quote == 0) {
                  quote = c;
               } else if (quote == c) {
                  quote = 0;
               }
               break;
            case '%':
               if (i + 1 < length) {
                  ++i;
                  char c2 = value.charAt(i);
                  if (c2 == ' ' || Character.isLetter(c2)) {
                     for(int j = i; j < length; ++j) {
                        c2 = value.charAt(j);
                        if (c2 != ' ' && !Character.isLetterOrDigit(c2)) {
                           break;
                        }
                     }

                     if (c2 == '%') {
                        return true;
                     }
                  }
               }
         }
      }

      return false;
   }

   private static int firstNonWhitespaceIndex(String value) {
      int length = value.length();

      int i;
      for(i = 0; i < length && Character.isSpaceChar(value.charAt(i)); ++i) {
      }

      return i;
   }

   public static String escapePath(String path) {
      StringBuilder buf = new StringBuilder();

      for(int i = 0; i < path.length(); ++i) {
         char c = path.charAt(i);
         buf.append(c);
         if (c == '\\') {
            buf.append(c);
         }
      }

      return buf.toString() + "\\\\";
   }

   private static boolean isDirectorySeparator(char value) {
      return value == '/' || value == '\\';
   }

   private static boolean isCommandDotCom(String command) {
      int length = command.length();
      int i = length - CDC_LENGTH;
      return i == 0 || i > 0 && isDirectorySeparator(command.charAt(i - 1)) && command.regionMatches(true, i, "command.com", 0, CDC_LENGTH);
   }

   private static boolean isInternalCommand(String command, boolean hasCommandDotCom) {
      assert command != null && !Character.isSpaceChar(command.charAt(0)) : "Spaces should have been stripped off already";

      int length = command.length();
      StringBuilder buf = new StringBuilder();
      int i = 0;

      char c;
      for(c = 0; i < length; ++i) {
         c = command.charAt(i);
         if (!Character.isLetter(c)) {
            break;
         }

         buf.append(Character.toLowerCase(c));
      }

      if (i < length) {
         if (c == '.' && i + 1 < length) {
            ++i;
         }

         switch (command.charAt(i)) {
            case '\u0000':
            case '\t':
            case '\n':
            case ' ':
               break;
            case '<':
            case '>':
            case '|':
               return true;
            default:
               return false;
         }
      }

      InternalType kindOf = (InternalType)INTERNAL_COMMANDS.get(buf.toString());
      boolean var10000;
      if (kindOf != WindowsHelpers.InternalType.BOTH) {
         label73: {
            if (hasCommandDotCom) {
               if (kindOf == WindowsHelpers.InternalType.COMMAND) {
                  break label73;
               }
            } else if (kindOf == WindowsHelpers.InternalType.SHELL) {
               break label73;
            }

            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   public static boolean isDriveLetterPath(String path) {
      return path.length() >= 2 && Character.isLetter(path.charAt(0)) && path.charAt(1) == ':';
   }

   private static enum InternalType {
      SHELL,
      COMMAND,
      BOTH;
   }
}
