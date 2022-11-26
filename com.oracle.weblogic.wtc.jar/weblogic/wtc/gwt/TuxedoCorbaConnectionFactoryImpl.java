package weblogic.wtc.gwt;

import weblogic.wtc.jatmi.TPException;

public final class TuxedoCorbaConnectionFactoryImpl implements TuxedoCorbaConnectionFactory {
   private static final long serialVersionUID = -1214596436048576404L;

   public TuxedoCorbaConnection getTuxedoCorbaConnection() throws TPException {
      return new TuxedoCorbaConnectionImpl();
   }
}
