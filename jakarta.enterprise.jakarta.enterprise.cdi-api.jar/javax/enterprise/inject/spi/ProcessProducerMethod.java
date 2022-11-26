package javax.enterprise.inject.spi;

public interface ProcessProducerMethod extends ProcessBean {
   AnnotatedMethod getAnnotatedProducerMethod();

   AnnotatedParameter getAnnotatedDisposedParameter();
}
