package consulting.counting.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculatorImpl implements Calculator {

    @Override
    public Map<Character, Integer> calculate(String text) {
        Map<Character, Integer> map = new HashMap<>();

        for (char symbol : text.toLowerCase().toCharArray()) {
            map.put(symbol, map.getOrDefault(symbol, 0) + 1);
        }

        return map.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));
    }
}
