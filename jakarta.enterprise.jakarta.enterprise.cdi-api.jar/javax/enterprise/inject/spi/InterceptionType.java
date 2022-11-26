package javax.enterprise.inject.spi;

public enum InterceptionType {
   AROUND_INVOKE,
   AROUND_TIMEOUT,
   AROUND_CONSTRUCT,
   POST_CONSTRUCT,
   PRE_DESTROY,
   PRE_PASSIVATE,
   POST_ACTIVATE;
}
