package sandbox.htmxpoc.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;

class StateUtilsTest {
    @Test
    void toQuery() {
        Map<String, String> params = new TreeMap<>(Map.of(
                "state_0", "A",
                "state_1", "B",
                "state_2", "C",
                "param", "D",
                "foo", "E"
        ));

        String resultValue = StateUtils.toQuery(params);

        assertThat(resultValue, Matchers.equalTo("?foo=E&param=D&state_0=A&state_1=B&state_2=C"));
    }

    @Test
    void toQueryEmpty() {
        assertThat(StateUtils.toQuery(Collections.emptyMap()), Matchers.equalTo(""));
    }

    @Test
    void states() {
        Map<String, String> params = Map.of(
                "state_0", "A",
                "state_1", "B",
                "state_2", "C",
                "param", "D",
                "foo", "E"
        );

        Map<Integer, String> resultValue = StateUtils.states(params);

        assertThat(resultValue.size(), Matchers.equalTo(3));
        assertThat(resultValue, Matchers.hasEntry(0, "A"));
        assertThat(resultValue, Matchers.hasEntry(1, "B"));
        assertThat(resultValue, Matchers.hasEntry(2, "C"));
    }
}