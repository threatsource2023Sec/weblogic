package weblogic.jaxrs.onwls.ejb;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.spi.ExceptionMappers;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

public class EjbExceptionMapper implements ExtendedExceptionMapper {
   private final Provider mappers;

   @Inject
   public EjbExceptionMapper(Provider mappers) {
      this.mappers = mappers;
   }

   public Response toResponse(EJBException exception) {
      return this.causeToResponse(exception);
   }

   public boolean isMappable(EJBException exception) {
      try {
         return this.causeToResponse(exception) != null;
      } catch (Throwable var3) {
         return false;
      }
   }

   private Response causeToResponse(EJBException exception) {
      Exception cause = exception.getCausedByException();
      if (cause != null) {
         ExceptionMapper mapper = ((ExceptionMappers)this.mappers.get()).findMapping(cause);
         if (mapper != null) {
            return mapper.toResponse(cause);
         }

         if (cause instanceof WebApplicationException) {
            return ((WebApplicationException)cause).getResponse();
         }
      }

      return null;
   }
}
