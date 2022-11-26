package com.bea.core.repackaged.aspectj.bridge.context;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class CompilationAndWeavingContext {
   private static int nextTokenId = 1;
   public static final int BATCH_BUILD = 0;
   public static final int INCREMENTAL_BUILD = 1;
   public static final int PROCESSING_COMPILATION_UNIT = 2;
   public static final int RESOLVING_COMPILATION_UNIT = 3;
   public static final int ANALYSING_COMPILATION_UNIT = 4;
   public static final int GENERATING_UNWOVEN_CODE_FOR_COMPILATION_UNIT = 5;
   public static final int COMPLETING_TYPE_BINDINGS = 6;
   public static final int PROCESSING_DECLARE_PARENTS = 7;
   public static final int CHECK_AND_SET_IMPORTS = 8;
   public static final int CONNECTING_TYPE_HIERARCHY = 9;
   public static final int BUILDING_FIELDS_AND_METHODS = 10;
   public static final int COLLECTING_ITDS_AND_DECLARES = 11;
   public static final int PROCESSING_DECLARE_ANNOTATIONS = 12;
   public static final int WEAVING_INTERTYPE_DECLARATIONS = 13;
   public static final int RESOLVING_POINTCUT_DECLARATIONS = 14;
   public static final int ADDING_DECLARE_WARNINGS_AND_ERRORS = 15;
   public static final int VALIDATING_AT_ASPECTJ_ANNOTATIONS = 16;
   public static final int ACCESS_FOR_INLINE = 17;
   public static final int ADDING_AT_ASPECTJ_ANNOTATIONS = 18;
   public static final int FIXING_SUPER_CALLS_IN_ITDS = 19;
   public static final int FIXING_SUPER_CALLS = 20;
   public static final int OPTIMIZING_THIS_JOIN_POINT_CALLS = 21;
   public static final int WEAVING = 22;
   public static final int PROCESSING_REWEAVABLE_STATE = 23;
   public static final int PROCESSING_TYPE_MUNGERS = 24;
   public static final int WEAVING_ASPECTS = 25;
   public static final int WEAVING_CLASSES = 26;
   public static final int WEAVING_TYPE = 27;
   public static final int MATCHING_SHADOW = 28;
   public static final int IMPLEMENTING_ON_SHADOW = 29;
   public static final int MATCHING_POINTCUT = 30;
   public static final int MUNGING_WITH = 31;
   public static final int PROCESSING_ATASPECTJTYPE_MUNGERS_ONLY = 32;
   public static final String[] PHASE_NAMES = new String[]{"batch building", "incrementally building", "processing compilation unit", "resolving types defined in compilation unit", "analysing types defined in compilation unit", "generating unwoven code for type defined in compilation unit", "completing type bindings", "processing declare parents", "checking and setting imports", "connecting type hierarchy", "building fields and methods", "collecting itds and declares", "processing declare annotations", "weaving intertype declarations", "resolving pointcut declarations", "adding declare warning and errors", "validating @AspectJ annotations", "creating accessors for inlining", "adding @AspectJ annotations", "fixing super calls in ITDs in interface context", "fixing super calls in ITDs", "optimizing thisJoinPoint calls", "weaving", "processing reweavable state", "processing type mungers", "weaving aspects", "weaving classes", "weaving type", "matching shadow", "implementing on shadow", "matching pointcut", "type munging with", "type munging for @AspectJ aspectOf"};
   private static ThreadLocal contextMap = new ThreadLocal();
   private static Stack contextStack = new Stack();
   private static Map formatterMap = new HashMap();
   private static ContextFormatter defaultFormatter = new DefaultFormatter();
   private static boolean multiThreaded = true;

   private CompilationAndWeavingContext() {
   }

   public static void reset() {
      if (!multiThreaded) {
         contextMap.remove();
         contextStack.clear();
         formatterMap.clear();
         nextTokenId = 1;
      } else {
         contextMap.remove();
      }

   }

   public static void setMultiThreaded(boolean mt) {
      multiThreaded = mt;
   }

   public static void registerFormatter(int phaseId, ContextFormatter aFormatter) {
      formatterMap.put(new Integer(phaseId), aFormatter);
   }

   public static String getCurrentContext() {
      Stack contextStack = getContextStack();
      Stack explanationStack = new Stack();
      Iterator i$ = contextStack.iterator();

      while(i$.hasNext()) {
         ContextStackEntry entry = (ContextStackEntry)i$.next();
         Object data = entry.getData();
         if (data != null) {
            explanationStack.push(getFormatter(entry).formatEntry(entry.phaseId, data));
         }
      }

      StringBuffer sb = new StringBuffer();

      while(!explanationStack.isEmpty()) {
         sb.append("when ");
         sb.append(((String)explanationStack.pop()).toString());
         sb.append("\n");
      }

      return sb.toString();
   }

   public static ContextToken enteringPhase(int phaseId, Object data) {
      Stack contextStack = getContextStack();
      ContextTokenImpl nextToken = nextToken();
      contextStack.push(new ContextStackEntry(nextToken, phaseId, new WeakReference(data)));
      return nextToken;
   }

   public static void leavingPhase(ContextToken aToken) {
      Stack contextStack = getContextStack();

      while(!contextStack.isEmpty()) {
         ContextStackEntry entry = (ContextStackEntry)contextStack.pop();
         if (entry.contextToken == aToken) {
            break;
         }
      }

   }

   public static void resetForThread() {
      if (multiThreaded) {
         contextMap.remove();
      }
   }

   private static Stack getContextStack() {
      if (!multiThreaded) {
         return CompilationAndWeavingContext.contextStack;
      } else {
         Stack contextStack = (Stack)contextMap.get();
         if (contextStack == null) {
            contextStack = new Stack();
            contextMap.set(contextStack);
         }

         return contextStack;
      }
   }

   private static ContextTokenImpl nextToken() {
      return new ContextTokenImpl(nextTokenId++);
   }

   private static ContextFormatter getFormatter(ContextStackEntry entry) {
      Integer key = new Integer(entry.phaseId);
      return formatterMap.containsKey(key) ? (ContextFormatter)formatterMap.get(key) : defaultFormatter;
   }

   private static class DefaultFormatter implements ContextFormatter {
      private DefaultFormatter() {
      }

      public String formatEntry(int phaseId, Object data) {
         StringBuffer sb = new StringBuffer();
         sb.append(CompilationAndWeavingContext.PHASE_NAMES[phaseId]);
         sb.append(" ");
         if (data instanceof char[]) {
            sb.append(new String((char[])((char[])data)));
         } else {
            try {
               sb.append(data.toString());
            } catch (RuntimeException var5) {
               sb.append("** broken toString in data object **");
            }
         }

         return sb.toString();
      }

      // $FF: synthetic method
      DefaultFormatter(Object x0) {
         this();
      }
   }

   private static class ContextStackEntry {
      public ContextTokenImpl contextToken;
      public int phaseId;
      private WeakReference dataRef;

      public ContextStackEntry(ContextTokenImpl ct, int phase, WeakReference data) {
         this.contextToken = ct;
         this.phaseId = phase;
         this.dataRef = data;
      }

      public Object getData() {
         return this.dataRef.get();
      }

      public String toString() {
         Object data = this.getData();
         return data == null ? "referenced context entry has gone out of scope" : CompilationAndWeavingContext.getFormatter(this).formatEntry(this.phaseId, data);
      }
   }

   private static class ContextTokenImpl implements ContextToken {
      public int tokenId;

      public ContextTokenImpl(int id) {
         this.tokenId = id;
      }
   }
}
