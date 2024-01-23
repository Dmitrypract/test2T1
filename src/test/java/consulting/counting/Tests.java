package consulting.counting;

import consulting.counting.controller.Controller;
import consulting.counting.exception.InvalidTextException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CountingApplication.class)
class Tests {

    @Autowired
    private Controller controller;


    @Test
    @DisplayName("Проверка подсчета количества символов в строке")
    public void testCalculate() {
        String text = "abc";

        Map<Character, Integer> map = new LinkedHashMap<>();
        map.put('a', 1);
        map.put('b', 1);
        map.put('c', 1);

        assertEquals(controller.calculate(text), map);
        assertEquals(text.length(), map.size());
    }

    @Test
    @DisplayName("Исключение при пустой строке")
    public void shouldThrowExceptionEmptyString() {
        String text = "";

        final InvalidTextException exception = assertThrows(
                InvalidTextException.class,
                () -> {
                    controller.calculate(text);
                });
        assertEquals("Передана пустая строка или строка состоит из пробелов", exception.getMessage());
    }

    @Test
    @DisplayName("Исключение при строке из пробелов")
    public void shouldThrowExceptionStringOfSpaces() {
        String text = "     ";

        final InvalidTextException exception = assertThrows(
                InvalidTextException.class,
                () -> {
                    controller.calculate(text);
                });
        assertEquals("Передана пустая строка или строка состоит из пробелов", exception.getMessage());
    }
}
