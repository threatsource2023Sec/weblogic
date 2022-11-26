package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nullable;

public interface IdentifiedComponent extends Component {
   @Nullable
   String getId();
}
