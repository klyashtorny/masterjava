package ru.javaops.masterjava.web.handler;

import com.sun.xml.ws.api.handler.MessageHandlerContext;
import org.slf4j.event.Level;
import ru.javaops.masterjava.web.Statistics;

import java.util.EnumMap;
import java.util.Map;

import static ru.javaops.masterjava.web.Statistics.RESULT.FAIL;
import static ru.javaops.masterjava.web.Statistics.RESULT.SUCCESS;

public class SoapStatisticHandler extends SoapBaseHandler {

    private Level loggingLevel;
    private long startTime;

    public SoapStatisticHandler() {
        this.loggingLevel = Level.INFO;
    }

    public SoapStatisticHandler(Level loggingLevel) {
        this.loggingLevel = loggingLevel == null ? Level.INFO : loggingLevel;
    }

    private static final Map<Level, Statistics.RESULT> RESULT_MAP = new EnumMap<Level, Statistics.RESULT>(Level.class) {
        {
            put(Level.TRACE, SUCCESS);
            put(Level.DEBUG, SUCCESS);
            put(Level.INFO, SUCCESS);
            put(Level.WARN, SUCCESS);
            put(Level.ERROR, FAIL);
        }
    };

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean handleMessage(MessageHandlerContext context) {
        RESULT_MAP.get(loggingLevel).handleMessage(context, startTime, isOutbound(context));
        return true;
    }

    @Override
    public boolean handleFault(MessageHandlerContext context) {
        RESULT_MAP.get(loggingLevel).handleFault(context, startTime);
        return true;
    }
}
