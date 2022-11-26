package com.bea.core.repackaged.jdt.internal.compiler.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public final class Messages {
   private static String[] nlSuffixes;
   private static final String EXTENSION = ".properties";
   private static final String BUNDLE_NAME = "com.bea.core.repackaged.jdt.internal.compiler.messages";
   public static String compilation_unresolvedProblem;
   public static String compilation_unresolvedProblems;
   public static String compilation_request;
   public static String compilation_loadBinary;
   public static String compilation_process;
   public static String compilation_write;
   public static String compilation_done;
   public static String compilation_units;
   public static String compilation_unit;
   public static String compilation_internalError;
   public static String compilation_beginningToCompile;
   public static String compilation_processing;
   public static String output_isFile;
   public static String output_notValidAll;
   public static String output_notValid;
   public static String problem_noSourceInformation;
   public static String problem_atLine;
   public static String abort_invalidAttribute;
   public static String abort_invalidExceptionAttribute;
   public static String abort_invalidOpcode;
   public static String abort_missingCode;
   public static String abort_againstSourceModel;
   public static String abort_externaAnnotationFile;
   public static String accept_cannot;
   public static String parser_incorrectPath;
   public static String parser_moveFiles;
   public static String parser_syntaxRecovery;
   public static String parser_regularParse;
   public static String parser_missingFile;
   public static String parser_corruptedFile;
   public static String parser_endOfFile;
   public static String parser_endOfConstructor;
   public static String parser_endOfMethod;
   public static String parser_endOfInitializer;
   public static String ast_missingCode;
   public static String constant_cannotCastedInto;
   public static String constant_cannotConvertedTo;

   static {
      initializeMessages("com.bea.core.repackaged.jdt.internal.compiler.messages", Messages.class);
   }

   private Messages() {
   }

   public static String bind(String message) {
      return bind(message, (Object[])null);
   }

   public static String bind(String message, Object binding) {
      return bind(message, new Object[]{binding});
   }

   public static String bind(String message, Object binding1, Object binding2) {
      return bind(message, new Object[]{binding1, binding2});
   }

   public static String bind(String message, Object[] bindings) {
      return MessageFormat.format(message, bindings);
   }

   private static String[] buildVariants(String root) {
      if (nlSuffixes == null) {
         String nl = Locale.getDefault().toString();
         ArrayList result = new ArrayList(4);

         while(true) {
            result.add('_' + nl + ".properties");
            int lastSeparator = nl.lastIndexOf(95);
            if (lastSeparator == -1) {
               result.add(".properties");
               nlSuffixes = (String[])result.toArray(new String[result.size()]);
               break;
            }

            nl = nl.substring(0, lastSeparator);
         }
      }

      root = root.replace('.', '/');
      String[] variants = new String[nlSuffixes.length];

      for(int i = 0; i < variants.length; ++i) {
         variants[i] = root + nlSuffixes[i];
      }

      return variants;
   }

   public static void initializeMessages(String bundleName, Class clazz) {
      Field[] fields = clazz.getDeclaredFields();
      load(bundleName, clazz.getClassLoader(), fields);
      int numFields = fields.length;

      for(int i = 0; i < numFields; ++i) {
         Field field = fields[i];
         if ((field.getModifiers() & 25) == 9) {
            try {
               if (field.get(clazz) == null) {
                  String value = "Missing message: " + field.getName() + " in: " + bundleName;
                  field.set((Object)null, value);
               }
            } catch (IllegalArgumentException var7) {
            } catch (IllegalAccessException var8) {
            }
         }
      }

   }

   public static void load(String bundleName, ClassLoader loader, Field[] fields) {
      String[] variants = buildVariants(bundleName);
      int i = variants.length;

      while(true) {
         --i;
         if (i < 0) {
            return;
         }

         InputStream input = loader == null ? ClassLoader.getSystemResourceAsStream(variants[i]) : loader.getResourceAsStream(variants[i]);
         if (input != null) {
            try {
               MessagesProperties properties = new MessagesProperties(fields, bundleName);
               properties.load(input);
            } catch (IOException var14) {
            } finally {
               try {
                  input.close();
               } catch (IOException var13) {
               }

            }
         }
      }
   }

   private static class MessagesProperties extends Properties {
      private static final int MOD_EXPECTED = 9;
      private static final int MOD_MASK = 25;
      private static final long serialVersionUID = 1L;
      private final Map fields;

      public MessagesProperties(Field[] fieldArray, String bundleName) {
         int len = fieldArray.length;
         this.fields = new HashMap(len * 2);

         for(int i = 0; i < len; ++i) {
            this.fields.put(fieldArray[i].getName(), fieldArray[i]);
         }

      }

      public synchronized Object put(Object key, Object value) {
         try {
            Field field = (Field)this.fields.get(key);
            if (field == null) {
               return null;
            }

            if ((field.getModifiers() & 25) != 9) {
               return null;
            }

            try {
               field.set((Object)null, value);
            } catch (Exception var4) {
            }
         } catch (SecurityException var5) {
         }

         return null;
      }
   }
}
