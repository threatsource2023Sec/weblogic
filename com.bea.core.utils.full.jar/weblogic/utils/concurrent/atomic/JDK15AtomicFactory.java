package weblogic.utils.concurrent.atomic;

final class JDK15AtomicFactory extends AtomicFactory.Factory {
   AtomicInteger createAtomicInteger() {
      return new JDK15AtomicInteger();
   }

   AtomicLong createAtomicLong() {
      return new JDK15AtomicLong();
   }
}
