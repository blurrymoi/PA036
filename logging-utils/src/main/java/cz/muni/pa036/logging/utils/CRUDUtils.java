package cz.muni.pa036.logging.utils;

import java.util.Map;

public class CRUDUtils {

    public static String prettyPrintMap(Map<Object, Object> values) {
        StringBuilder builder = new StringBuilder("");
        builder.append("[");
        int mapLength = values.keySet().size();
        int currentIndex = 1;
        for (Object by : values.keySet()) {
            Object byValue = values.get(by);

            builder.append(by + ":\"" + byValue + "\"");
            if (currentIndex < mapLength) {
                builder.append(", ");
            }
            currentIndex++;
        }
        builder.append("]");
        return builder.toString();
    }
}
