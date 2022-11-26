package javax.enterprise.inject.spi;

public interface ProcessProducerField extends ProcessBean {
   AnnotatedField getAnnotatedProducerField();

   AnnotatedParameter getAnnotatedDisposedParameter();
}
