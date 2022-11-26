package javax.enterprise.inject.spi;

public interface EventContext {
   Object getEvent();

   EventMetadata getMetadata();
}
