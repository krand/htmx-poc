package sandbox.htmxpoc.web;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateUtils {

    public static final String STATE_PARAM_NAME = "state_";

    public static String tailQuery(Map<String, String> params, int since) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        for (String paramName : params.keySet()) {
            if (paramName.startsWith(STATE_PARAM_NAME)) {
                int index = Integer.parseInt(paramName.substring(STATE_PARAM_NAME.length()));
                if (index < since) continue;
            }
            builder.replaceQueryParam(paramName, params.get(paramName));
        }
        return Optional.ofNullable(builder.build().getQuery()).map(st -> "?" + st).orElse("");
    }

    public static Map<Integer, String> states(Map<String, String> params) {
        Map<Integer, String> result = new HashMap<>();
        for (String paramName : params.keySet()) {
            if (paramName.startsWith(STATE_PARAM_NAME)) {
                int index = Integer.parseInt(paramName.substring(STATE_PARAM_NAME.length()));
                result.put(index, params.get(paramName));
            }
        }
        return result;
    }
}
