package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.RxReactiveStreams;

public class ReactiveAdapterRegistry {
   @Nullable
   private static volatile ReactiveAdapterRegistry sharedInstance;
   private final boolean reactorPresent;
   private final List adapters = new ArrayList();

   public ReactiveAdapterRegistry() {
      ClassLoader classLoader = ReactiveAdapterRegistry.class.getClassLoader();
      boolean reactorRegistered = false;
      if (ClassUtils.isPresent("reactor.core.publisher.Flux", classLoader)) {
         (new ReactorRegistrar()).registerAdapters(this);
         reactorRegistered = true;
      }

      this.reactorPresent = reactorRegistered;
      if (ClassUtils.isPresent("rx.Observable", classLoader) && ClassUtils.isPresent("rx.RxReactiveStreams", classLoader)) {
         (new RxJava1Registrar()).registerAdapters(this);
      }

      if (ClassUtils.isPresent("io.reactivex.Flowable", classLoader)) {
         (new RxJava2Registrar()).registerAdapters(this);
      }

      if (ClassUtils.isPresent("java.util.concurrent.Flow.Publisher", classLoader)) {
         (new ReactorJdkFlowAdapterRegistrar()).registerAdapter(this);
      }

   }

   public boolean hasAdapters() {
      return !this.adapters.isEmpty();
   }

   public void registerReactiveType(ReactiveTypeDescriptor descriptor, Function toAdapter, Function fromAdapter) {
      if (this.reactorPresent) {
         this.adapters.add(new ReactorAdapter(descriptor, toAdapter, fromAdapter));
      } else {
         this.adapters.add(new ReactiveAdapter(descriptor, toAdapter, fromAdapter));
      }

   }

   @Nullable
   public ReactiveAdapter getAdapter(Class reactiveType) {
      return this.getAdapter(reactiveType, (Object)null);
   }

   @Nullable
   public ReactiveAdapter getAdapter(@Nullable Class reactiveType, @Nullable Object source) {
      if (this.adapters.isEmpty()) {
         return null;
      } else {
         Object sourceToUse = source instanceof Optional ? ((Optional)source).orElse((Object)null) : source;
         Class clazz = sourceToUse != null ? sourceToUse.getClass() : reactiveType;
         if (clazz == null) {
            return null;
         } else {
            Iterator var5 = this.adapters.iterator();

            ReactiveAdapter adapter;
            do {
               if (!var5.hasNext()) {
                  var5 = this.adapters.iterator();

                  do {
                     if (!var5.hasNext()) {
                        return null;
                     }

                     adapter = (ReactiveAdapter)var5.next();
                  } while(!adapter.getReactiveType().isAssignableFrom(clazz));

                  return adapter;
               }

               adapter = (ReactiveAdapter)var5.next();
            } while(adapter.getReactiveType() != clazz);

            return adapter;
         }
      }
   }

   public static ReactiveAdapterRegistry getSharedInstance() {
      ReactiveAdapterRegistry registry = sharedInstance;
      if (registry == null) {
         Class var1 = ReactiveAdapterRegistry.class;
         synchronized(ReactiveAdapterRegistry.class) {
            registry = sharedInstance;
            if (registry == null) {
               registry = new ReactiveAdapterRegistry();
               sharedInstance = registry;
            }
         }
      }

      return registry;
   }

   private static class ReactorAdapter extends ReactiveAdapter {
      ReactorAdapter(ReactiveTypeDescriptor descriptor, Function toPublisherFunction, Function fromPublisherFunction) {
         super(descriptor, toPublisherFunction, fromPublisherFunction);
      }

      public Publisher toPublisher(@Nullable Object source) {
         Publisher publisher = super.toPublisher(source);
         return (Publisher)(this.isMultiValue() ? Flux.from(publisher) : Mono.from(publisher));
      }
   }

   private static class ReactorJdkFlowAdapterRegistrar {
      private ReactorJdkFlowAdapterRegistrar() {
      }

      void registerAdapter(ReactiveAdapterRegistry registry) {
         try {
            String publisherName = "java.util.concurrent.Flow.Publisher";
            Class publisherClass = ClassUtils.forName(publisherName, this.getClass().getClassLoader());
            String adapterName = "reactor.adapter.JdkFlowAdapter";
            Class flowAdapterClass = ClassUtils.forName(adapterName, this.getClass().getClassLoader());
            Method toFluxMethod = flowAdapterClass.getMethod("flowPublisherToFlux", publisherClass);
            Method toFlowMethod = flowAdapterClass.getMethod("publisherToFlowPublisher", Publisher.class);
            Object emptyFlow = ReflectionUtils.invokeMethod(toFlowMethod, (Object)null, Flux.empty());
            registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(publisherClass, () -> {
               return emptyFlow;
            }), (source) -> {
               return (Publisher)ReflectionUtils.invokeMethod(toFluxMethod, (Object)null, source);
            }, (publisher) -> {
               return ReflectionUtils.invokeMethod(toFlowMethod, (Object)null, publisher);
            });
         } catch (Throwable var9) {
         }

      }

      // $FF: synthetic method
      ReactorJdkFlowAdapterRegistrar(Object x0) {
         this();
      }
   }

   private static class RxJava2Registrar {
      private RxJava2Registrar() {
      }

      void registerAdapters(ReactiveAdapterRegistry registry) {
         registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(Flowable.class, Flowable::empty), (source) -> {
            return (Flowable)source;
         }, Flowable::fromPublisher);
         registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(Observable.class, Observable::empty), (source) -> {
            return ((Observable)source).toFlowable(BackpressureStrategy.BUFFER);
         }, (source) -> {
            return Flowable.fromPublisher(source).toObservable();
         });
         registry.registerReactiveType(ReactiveTypeDescriptor.singleRequiredValue(Single.class), (source) -> {
            return ((Single)source).toFlowable();
         }, (source) -> {
            return Flowable.fromPublisher(source).toObservable().singleElement().toSingle();
         });
         registry.registerReactiveType(ReactiveTypeDescriptor.singleOptionalValue(Maybe.class, Maybe::empty), (source) -> {
            return ((Maybe)source).toFlowable();
         }, (source) -> {
            return Flowable.fromPublisher(source).toObservable().singleElement();
         });
         registry.registerReactiveType(ReactiveTypeDescriptor.noValue(Completable.class, Completable::complete), (source) -> {
            return ((Completable)source).toFlowable();
         }, (source) -> {
            return Flowable.fromPublisher(source).toObservable().ignoreElements();
         });
      }

      // $FF: synthetic method
      RxJava2Registrar(Object x0) {
         this();
      }
   }

   private static class RxJava1Registrar {
      private RxJava1Registrar() {
      }

      void registerAdapters(ReactiveAdapterRegistry registry) {
         registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(rx.Observable.class, rx.Observable::empty), (source) -> {
            return RxReactiveStreams.toPublisher((rx.Observable)source);
         }, RxReactiveStreams::toObservable);
         registry.registerReactiveType(ReactiveTypeDescriptor.singleRequiredValue(rx.Single.class), (source) -> {
            return RxReactiveStreams.toPublisher((rx.Single)source);
         }, RxReactiveStreams::toSingle);
         registry.registerReactiveType(ReactiveTypeDescriptor.noValue(rx.Completable.class, rx.Completable::complete), (source) -> {
            return RxReactiveStreams.toPublisher((rx.Completable)source);
         }, RxReactiveStreams::toCompletable);
      }

      // $FF: synthetic method
      RxJava1Registrar(Object x0) {
         this();
      }
   }

   private static class ReactorRegistrar {
      private ReactorRegistrar() {
      }

      void registerAdapters(ReactiveAdapterRegistry registry) {
         registry.registerReactiveType(ReactiveTypeDescriptor.singleOptionalValue(Mono.class, Mono::empty), (source) -> {
            return (Mono)source;
         }, Mono::from);
         registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(Flux.class, Flux::empty), (source) -> {
            return (Flux)source;
         }, Flux::from);
         registry.registerReactiveType(ReactiveTypeDescriptor.multiValue(Publisher.class, Flux::empty), (source) -> {
            return (Publisher)source;
         }, (source) -> {
            return source;
         });
         registry.registerReactiveType(ReactiveTypeDescriptor.singleOptionalValue(CompletableFuture.class, () -> {
            CompletableFuture empty = new CompletableFuture();
            empty.complete((Object)null);
            return empty;
         }), (source) -> {
            return Mono.fromFuture((CompletableFuture)source);
         }, (source) -> {
            return Mono.from(source).toFuture();
         });
      }

      // $FF: synthetic method
      ReactorRegistrar(Object x0) {
         this();
      }
   }
}
