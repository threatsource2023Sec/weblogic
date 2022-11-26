package com.bea.core.repackaged.springframework.beans.factory.groovy;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.xml.StreamingMarkupBuilder;
import java.io.StringWriter;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.w3c.dom.Element;

class GroovyDynamicElementReader extends GroovyObjectSupport {
   private final String rootNamespace;
   private final Map xmlNamespaces;
   private final BeanDefinitionParserDelegate delegate;
   private final GroovyBeanDefinitionWrapper beanDefinition;
   protected final boolean decorating;
   private boolean callAfterInvocation;
   // $FF: synthetic field
   private static ClassInfo $staticClassInfo;
   // $FF: synthetic field
   public static transient boolean __$stMC;
   // $FF: synthetic field
   private static ClassInfo $staticClassInfo$;
   // $FF: synthetic field
   private static SoftReference $callSiteArray;
   // $FF: synthetic field
   private static Class $class$org$springframework$beans$factory$groovy$GroovyBeanDefinitionWrapper;

   public GroovyDynamicElementReader(String namespace, Map namespaceMap, BeanDefinitionParserDelegate delegate, GroovyBeanDefinitionWrapper beanDefinition, boolean decorating) {
      CallSite[] var6 = $getCallSiteArray();
      super();
      boolean var7 = true;
      this.callAfterInvocation = var7;
      this.rootNamespace = (String)ShortTypeHandling.castToString(namespace);
      this.xmlNamespaces = (Map)ScriptBytecodeAdapter.castToType(namespaceMap, Map.class);
      this.delegate = (BeanDefinitionParserDelegate)ScriptBytecodeAdapter.castToType(delegate, BeanDefinitionParserDelegate.class);
      this.beanDefinition = (GroovyBeanDefinitionWrapper)ScriptBytecodeAdapter.castToType(beanDefinition, $get$$class$org$springframework$beans$factory$groovy$GroovyBeanDefinitionWrapper());
      this.decorating = DefaultTypeTransformation.booleanUnbox(decorating);
   }

   public Object invokeMethod(String name, Object args) {
      final Reference name = new Reference(name);
      final Reference args = new Reference(args);
      CallSite[] var5 = $getCallSiteArray();
      Object var10000;
      if (DefaultTypeTransformation.booleanUnbox(var5[0].call((String)name.get(), "doCall"))) {
         Object callable = var5[1].call((Object)args.get(), 0);
         Object var7 = var5[2].callGetProperty(Closure.class);
         ScriptBytecodeAdapter.setProperty(var7, (Class)null, callable, (String)"resolveStrategy");
         ScriptBytecodeAdapter.setProperty(this, (Class)null, callable, (String)"delegate");
         Object result = var5[3].call(callable);
         if (this.callAfterInvocation) {
            if (!__$stMC && !BytecodeInterface8.disabledStandardMetaClass()) {
               this.afterInvocation();
               var10000 = null;
            } else {
               var5[4].callCurrent(this);
            }

            boolean var10 = false;
            this.callAfterInvocation = DefaultTypeTransformation.booleanUnbox(var10);
         }

         return result;
      } else {
         final Reference builder = new Reference((StreamingMarkupBuilder)ScriptBytecodeAdapter.castToType(var5[5].callConstructor(StreamingMarkupBuilder.class), StreamingMarkupBuilder.class));
         final Reference myNamespace = new Reference(this.rootNamespace);
         final Reference myNamespaces = new Reference(this.xmlNamespaces);

         final class _invokeMethod_closure1 extends Closure implements GeneratedClosure {
            // $FF: synthetic field
            private static ClassInfo $staticClassInfo;
            // $FF: synthetic field
            public static transient boolean __$stMC;
            // $FF: synthetic field
            private static SoftReference $callSiteArray;

            public _invokeMethod_closure1(Object _outerInstance, Object _thisObject) {
               CallSite[] var8 = $getCallSiteArray();
               super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
               CallSite[] var2 = $getCallSiteArray();
               Object namespace = null;
               Iterator var4 = (Iterator)ScriptBytecodeAdapter.castToType(var2[0].call(myNamespaces.get()), Iterator.class);

               while(var4.hasNext()) {
                  namespace = var4.next();
                  var2[1].call(var2[2].callGroovyObjectGetProperty(this), ScriptBytecodeAdapter.createMap(new Object[]{var2[3].callGetProperty(namespace), var2[4].callGetProperty(namespace)}));
               }

               if (DefaultTypeTransformation.booleanUnbox(args.get()) && var2[5].call(args.get(), -1) instanceof Closure) {
                  Object var5 = var2[6].callGetProperty(Closure.class);
                  ScriptBytecodeAdapter.setProperty(var5, (Class)null, var2[7].call(args.get(), -1), (String)"resolveStrategy");
                  Object var6 = builder.get();
                  ScriptBytecodeAdapter.setProperty(var6, (Class)null, var2[8].call(args.get(), -1), (String)"delegate");
               }

               return ScriptBytecodeAdapter.invokeMethodN(_invokeMethod_closure1.class, ScriptBytecodeAdapter.getProperty(_invokeMethod_closure1.class, var2[9].callGroovyObjectGetProperty(this), (String)ShortTypeHandling.castToString(new GStringImpl(new Object[]{myNamespace.get()}, new String[]{"", ""}))), (String)ShortTypeHandling.castToString(new GStringImpl(new Object[]{name.get()}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args.get()}, new int[]{0}));
            }

            public Object getMyNamespaces() {
               CallSite[] var1 = $getCallSiteArray();
               return myNamespaces.get();
            }

            public Object getArgs() {
               CallSite[] var1 = $getCallSiteArray();
               return args.get();
            }

            public StreamingMarkupBuilder getBuilder() {
               CallSite[] var1 = $getCallSiteArray();
               return (StreamingMarkupBuilder)ScriptBytecodeAdapter.castToType(builder.get(), StreamingMarkupBuilder.class);
            }

            public Object getMyNamespace() {
               CallSite[] var1 = $getCallSiteArray();
               return myNamespace.get();
            }

            public String getName() {
               CallSite[] var1 = $getCallSiteArray();
               return (String)ShortTypeHandling.castToString(name.get());
            }

            @Generated
            public Object doCall() {
               CallSite[] var1 = $getCallSiteArray();
               return this.doCall((Object)null);
            }

            // $FF: synthetic method
            protected MetaClass $getStaticMetaClass() {
               if (this.getClass() != _invokeMethod_closure1.class) {
                  return ScriptBytecodeAdapter.initMetaClass(this);
               } else {
                  ClassInfo var1 = $staticClassInfo;
                  if (var1 == null) {
                     $staticClassInfo = var1 = ClassInfo.getClassInfo(this.getClass());
                  }

                  return var1.getMetaClass();
               }
            }

            // $FF: synthetic method
            private static void $createCallSiteArray_1(String[] var0) {
               var0[0] = "iterator";
               var0[1] = "declareNamespace";
               var0[2] = "mkp";
               var0[3] = "key";
               var0[4] = "value";
               var0[5] = "getAt";
               var0[6] = "DELEGATE_FIRST";
               var0[7] = "getAt";
               var0[8] = "getAt";
               var0[9] = "delegate";
            }

            // $FF: synthetic method
            private static CallSiteArray $createCallSiteArray() {
               String[] var0 = new String[10];
               $createCallSiteArray_1(var0);
               return new CallSiteArray(_invokeMethod_closure1.class, var0);
            }

            // $FF: synthetic method
            private static CallSite[] $getCallSiteArray() {
               CallSiteArray var0;
               if ($callSiteArray == null || (var0 = (CallSiteArray)$callSiteArray.get()) == null) {
                  var0 = $createCallSiteArray();
                  $callSiteArray = new SoftReference(var0);
               }

               return var0.array;
            }
         }

         Object callable = new _invokeMethod_closure1(this, this);
         Object var15 = var5[6].callGetProperty(Closure.class);
         ScriptBytecodeAdapter.setProperty(var15, (Class)null, callable, (String)"resolveStrategy");
         StreamingMarkupBuilder var16 = (StreamingMarkupBuilder)builder.get();
         ScriptBytecodeAdapter.setProperty(var16, (Class)null, callable, (String)"delegate");
         Object writable = var5[7].call((StreamingMarkupBuilder)builder.get(), callable);
         Object sw = var5[8].callConstructor(StringWriter.class);
         var5[9].call(writable, sw);
         Element element = (Element)ScriptBytecodeAdapter.castToType(var5[10].callGetProperty(var5[11].call(var5[12].callGetProperty(this.delegate), var5[13].call(sw))), Element.class);
         var5[14].call(this.delegate, element);
         if (this.decorating) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder)ScriptBytecodeAdapter.castToType(var5[15].callGetProperty(this.beanDefinition), BeanDefinitionHolder.class);
            Object var21 = var5[16].call(this.delegate, element, holder, (Object)null);
            holder = (BeanDefinitionHolder)ScriptBytecodeAdapter.castToType(var21, BeanDefinitionHolder.class);
            var5[17].call(this.beanDefinition, holder);
         } else {
            Object beanDefinition = var5[18].call(this.delegate, element);
            if (DefaultTypeTransformation.booleanUnbox(beanDefinition)) {
               var5[19].call(this.beanDefinition, beanDefinition);
            }
         }

         if (this.callAfterInvocation) {
            if (!__$stMC && !BytecodeInterface8.disabledStandardMetaClass()) {
               this.afterInvocation();
               var10000 = null;
            } else {
               var5[20].callCurrent(this);
            }

            boolean var23 = false;
            this.callAfterInvocation = DefaultTypeTransformation.booleanUnbox(var23);
         }

         return element;
      }
   }

   protected void afterInvocation() {
      CallSite[] var1 = $getCallSiteArray();
   }

   // $FF: synthetic method
   protected MetaClass $getStaticMetaClass() {
      if (this.getClass() != GroovyDynamicElementReader.class) {
         return ScriptBytecodeAdapter.initMetaClass(this);
      } else {
         ClassInfo var1 = $staticClassInfo;
         if (var1 == null) {
            $staticClassInfo = var1 = ClassInfo.getClassInfo(this.getClass());
         }

         return var1.getMetaClass();
      }
   }

   // $FF: synthetic method
   public Object super$2$invokeMethod(String var1, Object var2) {
      return super.invokeMethod(var1, var2);
   }

   // $FF: synthetic method
   private static void $createCallSiteArray_1(String[] var0) {
      var0[0] = "equals";
      var0[1] = "getAt";
      var0[2] = "DELEGATE_FIRST";
      var0[3] = "call";
      var0[4] = "afterInvocation";
      var0[5] = "<$constructor$>";
      var0[6] = "DELEGATE_FIRST";
      var0[7] = "bind";
      var0[8] = "<$constructor$>";
      var0[9] = "writeTo";
      var0[10] = "documentElement";
      var0[11] = "readDocumentFromString";
      var0[12] = "readerContext";
      var0[13] = "toString";
      var0[14] = "initDefaults";
      var0[15] = "beanDefinitionHolder";
      var0[16] = "decorateIfRequired";
      var0[17] = "setBeanDefinitionHolder";
      var0[18] = "parseCustomElement";
      var0[19] = "setBeanDefinition";
      var0[20] = "afterInvocation";
   }

   // $FF: synthetic method
   private static CallSiteArray $createCallSiteArray() {
      String[] var0 = new String[21];
      $createCallSiteArray_1(var0);
      return new CallSiteArray(GroovyDynamicElementReader.class, var0);
   }

   // $FF: synthetic method
   private static CallSite[] $getCallSiteArray() {
      CallSiteArray var0;
      if ($callSiteArray == null || (var0 = (CallSiteArray)$callSiteArray.get()) == null) {
         var0 = $createCallSiteArray();
         $callSiteArray = new SoftReference(var0);
      }

      return var0.array;
   }

   // $FF: synthetic method
   private static Class $get$$class$org$springframework$beans$factory$groovy$GroovyBeanDefinitionWrapper() {
      Class var10000 = $class$org$springframework$beans$factory$groovy$GroovyBeanDefinitionWrapper;
      if (var10000 == null) {
         var10000 = $class$org$springframework$beans$factory$groovy$GroovyBeanDefinitionWrapper = class$("com.bea.core.repackaged.springframework.beans.factory.groovy.GroovyBeanDefinitionWrapper");
      }

      return var10000;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
