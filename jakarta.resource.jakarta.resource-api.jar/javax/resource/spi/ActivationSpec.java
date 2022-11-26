package javax.resource.spi;

public interface ActivationSpec extends ResourceAdapterAssociation {
   void validate() throws InvalidPropertyException;
}
