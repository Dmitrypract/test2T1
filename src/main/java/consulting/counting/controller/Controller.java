package consulting.counting.controller;

import consulting.counting.exception.InvalidTextException;
import consulting.counting.service.Calculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/calculate")
public class Controller {
    private final Calculator calculator;

    @GetMapping()
    public Map<Character, Integer> calculate(@RequestParam(required = false) String text) {
        if (text.isEmpty() || text.isBlank()) {
            log.info("Передана пустая строка или строка состоит из пробелов");
            throw new InvalidTextException("Передана пустая строка или строка состоит из пробелов");
        }
        log.info("Считаем количество символов в строке");
        return calculator.calculate(text);
    }
}
