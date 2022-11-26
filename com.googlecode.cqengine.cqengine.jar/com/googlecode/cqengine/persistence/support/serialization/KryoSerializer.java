package com.googlecode.cqengine.persistence.support.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoSerializer implements PojoSerializer {
   protected final Class objectType;
   protected final boolean polymorphic;
   protected final ThreadLocal kryoCache = new ThreadLocal() {
      protected Kryo initialValue() {
         return KryoSerializer.this.createKryo(KryoSerializer.this.objectType);
      }
   };

   public KryoSerializer(Class objectType, PersistenceConfig persistenceConfig) {
      this.objectType = objectType;
      this.polymorphic = persistenceConfig.polymorphic();
   }

   protected Kryo createKryo(Class objectType) {
      Kryo kryo = new Kryo();
      kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
      kryo.register(objectType);
      kryo.setRegistrationRequired(false);
      kryo.register(Arrays.asList().getClass(), new ArraysAsListSerializer());
      UnmodifiableCollectionsSerializer.registerSerializers(kryo);
      SynchronizedCollectionsSerializer.registerSerializers(kryo);
      return kryo;
   }

   public byte[] serialize(Object object) {
      if (object == null) {
         throw new NullPointerException("Object was null");
      } else {
         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Output output = new Output(baos);
            Kryo kryo = (Kryo)this.kryoCache.get();
            if (this.polymorphic) {
               kryo.writeClassAndObject(output, object);
            } else {
               kryo.writeObject(output, object);
            }

            output.close();
            return baos.toByteArray();
         } catch (Throwable var5) {
            throw new IllegalStateException("Failed to serialize object, object type: " + this.objectType + ". Configure @PersistenceConfig.polymorphic if the collection will contain a mix of object types. Use the KryoSerializer.validateObjectIsRoundTripSerializable() method to test your object is compatible with CQEngine.", var5);
         }
      }
   }

   public Object deserialize(byte[] bytes) {
      try {
         Input input = new Input(new ByteArrayInputStream(bytes));
         Kryo kryo = (Kryo)this.kryoCache.get();
         Object object;
         if (this.polymorphic) {
            object = kryo.readClassAndObject(input);
         } else {
            object = kryo.readObject(input, this.objectType);
         }

         input.close();
         return object;
      } catch (Throwable var5) {
         throw new IllegalStateException("Failed to deserialize object, object type: " + this.objectType + ". Configure @PersistenceConfig.polymorphic if the collection will contain a mix of object types. Use the KryoSerializer.validateObjectIsRoundTripSerializable() method to test your object is compatible with CQEngine.", var5);
      }
   }

   public static void validateObjectIsRoundTripSerializable(Object candidatePojo) {
      Class objectType = candidatePojo.getClass();
      validateObjectIsRoundTripSerializable(candidatePojo, objectType, PersistenceConfig.DEFAULT_CONFIG);
   }

   static void validateObjectIsRoundTripSerializable(Object candidatePojo, Class objectType, PersistenceConfig persistenceConfig) {
      try {
         KryoSerializer serializer = new KryoSerializer(objectType, persistenceConfig);
         byte[] serialized = serializer.serialize(candidatePojo);
         Object deserializedPojo = serializer.deserialize(serialized);
         serializer.kryoCache.remove();
         validateObjectEquality(candidatePojo, deserializedPojo);
         validateHashCodeEquality(candidatePojo, deserializedPojo);
      } catch (Exception var6) {
         throw new IllegalStateException("POJO object failed round trip serialization-deserialization test, object type: " + objectType + ", object: " + candidatePojo, var6);
      }
   }

   static void validateObjectEquality(Object candidate, Object deserializedPojo) {
      if (!deserializedPojo.equals(candidate)) {
         throw new IllegalStateException("The POJO after round trip serialization is not equal to the original POJO");
      }
   }

   static void validateHashCodeEquality(Object candidate, Object deserializedPojo) {
      if (deserializedPojo.hashCode() != candidate.hashCode()) {
         throw new IllegalStateException("The POJO's hashCode after round trip serialization differs from its original hashCode");
      }
   }
}
