package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Lint {
   Map kinds = new HashMap();
   World world;
   public final Kind invalidAbsoluteTypeName = new Kind("invalidAbsoluteTypeName", "no match for this type name: {0}");
   public final Kind invalidWildcardTypeName = new Kind("invalidWildcardTypeName", "no match for this type pattern: {0}");
   public final Kind unresolvableMember = new Kind("unresolvableMember", "can not resolve this member: {0}");
   public final Kind typeNotExposedToWeaver = new Kind("typeNotExposedToWeaver", "this affected type is not exposed to the weaver: {0}");
   public final Kind shadowNotInStructure = new Kind("shadowNotInStructure", "the shadow for this join point is not exposed in the structure model: {0}");
   public final Kind unmatchedSuperTypeInCall = new Kind("unmatchedSuperTypeInCall", "does not match because declaring type is {0}, if match desired use target({1})");
   public final Kind unmatchedTargetKind = new Kind("unmatchedTargetKind", "does not match because annotation {0} has @Target{1}");
   public final Kind canNotImplementLazyTjp = new Kind("canNotImplementLazyTjp", "can not implement lazyTjp on this joinpoint {0} because around advice is used");
   public final Kind multipleAdviceStoppingLazyTjp = new Kind("multipleAdviceStoppingLazyTjp", "can not implement lazyTjp at joinpoint {0} because of advice conflicts, see secondary locations to find conflicting advice");
   public final Kind needsSerialVersionUIDField = new Kind("needsSerialVersionUIDField", "serialVersionUID of type {0} needs to be set because of {1}");
   public final Kind serialVersionUIDBroken = new Kind("brokeSerialVersionCompatibility", "serialVersionUID of type {0} is broken because of added field {1}");
   public final Kind noInterfaceCtorJoinpoint = new Kind("noInterfaceCtorJoinpoint", "no interface constructor-execution join point - use {0}+ for implementing classes");
   public final Kind noJoinpointsForBridgeMethods = new Kind("noJoinpointsForBridgeMethods", "pointcut did not match on the method call to a bridge method.  Bridge methods are generated by the compiler and have no join points");
   public final Kind enumAsTargetForDecpIgnored = new Kind("enumAsTargetForDecpIgnored", "enum type {0} matches a declare parents type pattern but is being ignored");
   public final Kind annotationAsTargetForDecpIgnored = new Kind("annotationAsTargetForDecpIgnored", "annotation type {0} matches a declare parents type pattern but is being ignored");
   public final Kind cantMatchArrayTypeOnVarargs = new Kind("cantMatchArrayTypeOnVarargs", "an array type as the last parameter in a signature does not match on the varargs declared method: {0}");
   public final Kind adviceDidNotMatch = new Kind("adviceDidNotMatch", "advice defined in {0} has not been applied");
   public final Kind invalidTargetForAnnotation = new Kind("invalidTargetForAnnotation", "{0} is not a valid target for annotation {1}, this annotation can only be applied to {2}");
   public final Kind elementAlreadyAnnotated = new Kind("elementAlreadyAnnotated", "{0} - already has an annotation of type {1}, cannot add a second instance");
   public final Kind runtimeExceptionNotSoftened = new Kind("runtimeExceptionNotSoftened", "{0} will not be softened as it is already a RuntimeException");
   public final Kind uncheckedArgument = new Kind("uncheckedArgument", "unchecked match of {0} with {1} when argument is an instance of {2} at join point {3}");
   public final Kind uncheckedAdviceConversion = new Kind("uncheckedAdviceConversion", "unchecked conversion when advice applied at shadow {0}, expected {1} but advice uses {2}");
   public final Kind noGuardForLazyTjp = new Kind("noGuardForLazyTjp", "can not build thisJoinPoint lazily for this advice since it has no suitable guard");
   public final Kind noExplicitConstructorCall = new Kind("noExplicitConstructorCall", "inter-type constructor does not contain explicit constructor call: field initializers in the target type will not be executed");
   public final Kind aspectExcludedByConfiguration = new Kind("aspectExcludedByConfiguration", "aspect {0} exluded for class loader {1}");
   public final Kind unorderedAdviceAtShadow = new Kind("unorderedAdviceAtShadow", "at this shadow {0} no precedence is specified between advice applying from aspect {1} and aspect {2}");
   public final Kind swallowedExceptionInCatchBlock = new Kind("swallowedExceptionInCatchBlock", "exception swallowed in catch block");
   public final Kind calculatingSerialVersionUID = new Kind("calculatingSerialVersionUID", "calculated SerialVersionUID for type {0} to be {1}");
   public final Kind nonReweavableTypeEncountered = new Kind("nonReweavableTypeEncountered", "class '{0}' is already woven and has not been built in reweavable mode");
   public final Kind cantFindType = new Kind("cantFindType", "{0}");
   public final Kind cantFindTypeAffectingJoinPointMatch = new Kind("cantFindTypeAffectingJPMatch", "{0}");
   public final Kind advisingSynchronizedMethods = new Kind("advisingSynchronizedMethods", "advice matching the synchronized method shadow ''{0}'' will be executed outside the lock rather than inside (compiler limitation)");
   public final Kind mustWeaveXmlDefinedAspects = new Kind("mustWeaveXmlDefinedAspects", "XML Defined aspects must be woven in cases where cflow pointcuts are involved. Currently the include/exclude patterns exclude ''{0}''");
   public final Kind cannotAdviseJoinpointInInterfaceWithAroundAdvice = new Kind("cannotAdviseJoinpointInInterfaceWithAroundAdvice", "The joinpoint ''{0}'' cannot be advised and is being skipped as the compiler implementation will lead to creation of methods with bodies in an interface (compiler limitation)");
   public final Kind missingAspectForReweaving = new Kind("missingAspectForReweaving", "aspect {0} cannot be found when reweaving {1}");
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(Lint.class);

   public Lint(World world) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object)world);
      }

      this.world = world;
      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   public void setAll(String messageKind) {
      if (trace.isTraceEnabled()) {
         trace.enter("setAll", this, (Object)messageKind);
      }

      this.setAll(this.getMessageKind(messageKind));
      if (trace.isTraceEnabled()) {
         trace.exit("setAll");
      }

   }

   private void setAll(IMessage.Kind messageKind) {
      Iterator i$ = this.kinds.values().iterator();

      while(i$.hasNext()) {
         Kind kind = (Kind)i$.next();
         kind.setKind(messageKind);
      }

   }

   public void setFromMap(Map lintOptionsMap) {
      Iterator i$ = lintOptionsMap.keySet().iterator();

      while(i$.hasNext()) {
         String key = (String)i$.next();
         String value = (String)lintOptionsMap.get(key);
         Kind kind = (Kind)this.kinds.get(key);
         if (kind == null) {
            MessageUtil.error(this.world.getMessageHandler(), WeaverMessages.format("invalidXLintKey", key));
         } else {
            kind.setKind(this.getMessageKind(value));
         }
      }

   }

   public void setFromProperties(File file) {
      if (trace.isTraceEnabled()) {
         trace.enter("setFromProperties", this, (Object)file);
      }

      InputStream s = null;

      try {
         s = new FileInputStream(file);
         this.setFromProperties((InputStream)s);
      } catch (IOException var12) {
         MessageUtil.error(this.world.getMessageHandler(), WeaverMessages.format("problemLoadingXLint", file.getPath(), var12.getMessage()));
      } finally {
         if (s != null) {
            try {
               s.close();
            } catch (IOException var11) {
            }
         }

      }

      if (trace.isTraceEnabled()) {
         trace.exit("setFromProperties");
      }

   }

   public void loadDefaultProperties() {
      InputStream s = this.getClass().getResourceAsStream("XlintDefault.properties");
      if (s == null) {
         MessageUtil.warn(this.world.getMessageHandler(), WeaverMessages.format("unableToLoadXLintDefault"));
      } else {
         try {
            this.setFromProperties(s);
         } catch (IOException var11) {
            MessageUtil.error(this.world.getMessageHandler(), WeaverMessages.format("errorLoadingXLintDefault", var11.getMessage()));
         } finally {
            try {
               s.close();
            } catch (IOException var10) {
            }

         }

      }
   }

   private void setFromProperties(InputStream s) throws IOException {
      Properties p = new Properties();
      p.load(s);
      this.setFromProperties(p);
   }

   public void setFromProperties(Properties properties) {
      Iterator i = properties.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         Kind kind = (Kind)this.kinds.get(entry.getKey());
         if (kind == null) {
            MessageUtil.error(this.world.getMessageHandler(), WeaverMessages.format("invalidXLintKey", entry.getKey()));
         } else {
            kind.setKind(this.getMessageKind((String)entry.getValue()));
         }
      }

   }

   public Collection allKinds() {
      return this.kinds.values();
   }

   public Kind getLintKind(String name) {
      return (Kind)this.kinds.get(name);
   }

   public void suppressKinds(Collection lintKind) {
      if (!lintKind.isEmpty()) {
         Iterator i$ = lintKind.iterator();

         while(i$.hasNext()) {
            Kind k = (Kind)i$.next();
            k.setSuppressed(true);
         }

      }
   }

   public void clearAllSuppressions() {
      Iterator i$ = this.kinds.values().iterator();

      while(i$.hasNext()) {
         Kind k = (Kind)i$.next();
         k.setSuppressed(false);
      }

   }

   public void clearSuppressions(Collection lintKinds) {
      Iterator i$ = lintKinds.iterator();

      while(i$.hasNext()) {
         Kind k = (Kind)i$.next();
         k.setSuppressed(false);
      }

   }

   private IMessage.Kind getMessageKind(String v) {
      if (v.equals("ignore")) {
         return null;
      } else if (v.equals("warning")) {
         return IMessage.WARNING;
      } else if (v.equals("error")) {
         return IMessage.ERROR;
      } else {
         MessageUtil.error(this.world.getMessageHandler(), WeaverMessages.format("invalidXLintMessageKind", v));
         return null;
      }
   }

   public Kind fromKey(String lintkey) {
      return (Kind)this.kinds.get(lintkey);
   }

   public class Kind {
      private final String name;
      private final String message;
      private IMessage.Kind kind;
      private boolean isSupressed;

      public Kind(String name, String message) {
         this.kind = IMessage.WARNING;
         this.isSupressed = false;
         this.name = name;
         this.message = message;
         Lint.this.kinds.put(this.name, this);
      }

      public void setSuppressed(boolean shouldBeSuppressed) {
         this.isSupressed = shouldBeSuppressed;
      }

      public boolean isEnabled() {
         return this.kind != null && !this.isSupressed();
      }

      private boolean isSupressed() {
         return this.isSupressed && this.kind != IMessage.ERROR;
      }

      public String getName() {
         return this.name;
      }

      public IMessage.Kind getKind() {
         return this.kind;
      }

      public void setKind(IMessage.Kind kind) {
         this.kind = kind;
      }

      public void signal(String info, ISourceLocation location) {
         if (this.kind != null) {
            String text = MessageFormat.format(this.message, info);
            text = text + " [Xlint:" + this.name + "]";
            Lint.this.world.getMessageHandler().handleMessage(new LintMessage(text, this.kind, location, (ISourceLocation[])null, Lint.this.getLintKind(this.name)));
         }
      }

      public void signal(String[] infos, ISourceLocation location, ISourceLocation[] extraLocations) {
         if (this.kind != null) {
            String text = MessageFormat.format(this.message, (Object[])infos);
            text = text + " [Xlint:" + this.name + "]";
            Lint.this.world.getMessageHandler().handleMessage(new LintMessage(text, this.kind, location, extraLocations, Lint.this.getLintKind(this.name)));
         }
      }
   }
}
