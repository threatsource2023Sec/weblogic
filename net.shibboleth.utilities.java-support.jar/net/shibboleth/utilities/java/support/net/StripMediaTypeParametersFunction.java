package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Function;
import com.google.common.net.MediaType;

public class StripMediaTypeParametersFunction implements Function {
   public MediaType apply(MediaType input) {
      return input == null ? null : input.withoutParameters();
   }
}
