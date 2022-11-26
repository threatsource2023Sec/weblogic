package org.hibernate.validator.path;

import javax.validation.Path;

public interface PropertyNode extends Path.PropertyNode {
   Object getValue();
}
