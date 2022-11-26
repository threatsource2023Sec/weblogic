package com.oracle.buzzmessagebus.impl.internalapi;

import com.oracle.buzzmessagebus.api.BuzzTypes;

public interface BuzzTypesInternalApi extends BuzzTypes {
   byte BUZZ_RST_MESSAGE_FRAME = 3;
   byte BUZZ_INIT_FRAME = -2;
   long BUZZ_CONNECTION_MESSAGE_ID = 0L;
   int BUZZ_NO_ERROR = 0;
   int BUZZ_PROTOCOL_ERROR = 1;
   int BUZZ_INTERNAL_ERROR = 2;
   int BUZZ_CLOSE_MY_ID = 8;
   int BUZZ_CLOSE_YOUR_ID = 9;
   int BUZZ_START_ON_NON_CLOSED_ID_ERROR = -6;
   int BUZZ_DATA_ON_CLOSED_ID_ERROR = -5;
   int BUZZ_DATA_WITH_ZERO_ID_ERROR = -4;
   int BUZZ_START_WITH_ZERO_ID_ERROR = -3;
   int BUZZ_UNKNOWN_FRAME_TYPE_ERROR = -1;
}
