package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemAllFieldsDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.marker.Create;
import ru.practicum.shareit.marker.Update;

import java.util.Collection;

import static ru.practicum.shareit.util.Constant.USER_ID_HEADER;

@RestController
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto saveItem(@Validated(Create.class) @RequestBody ItemDto itemDto,
                              @RequestHeader(USER_ID_HEADER) long userId) {
        Item item = itemService.save(ItemMapper.mapToItem(itemDto), userId);
        log.info("Получен POST-запрос к эндпоинту: '/items' на добавление вещи владельцем с ID={}", userId);
        return ItemMapper.mapToItemDto(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Validated(Update.class) @RequestBody ItemDto itemDto,
                              @RequestHeader(USER_ID_HEADER) long userId,
                              @PathVariable long itemId) {
        log.info("Получен PATCH-запрос к эндпоинту: '/items' на обновление вещи с ID={}", itemId);
        Item item = itemService.update(ItemMapper.mapToItem(itemDto), itemId, userId);
        return ItemMapper.mapToItemDto(item);
    }

    @GetMapping("/{itemId}")
    public ItemDto findItemById(@RequestHeader(USER_ID_HEADER) long userId,
                               @PathVariable long itemId) {
        log.info("Получен GET-запрос к эндпоинту: '/items' на получение вещи с ID={}", itemId);
        return itemService.findById(userId, itemId);
    }

    @GetMapping
    public Collection<ItemAllFieldsDto> findItemsByUserId(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Получен GET-запрос к эндпоинту: '/items' на получение всех вещей владельца с ID={}", userId);
        return itemService.findItemsByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemAllFieldsDto> searchByText(@RequestParam(name = "text") String text,
                                            @RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Получен GET-запрос к эндпоинту: '/items/search' на поиск вещи с текстом={}", text);
        return itemService.searchByText(text, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto saveComment(@PathVariable long itemId, @RequestHeader(USER_ID_HEADER) long userId,
                                          @RequestBody CommentRequestDto commentRequestDto) {
        log.info("Получен POST-запрос к эндпоинту: '/items/comment' на" +
                " добавление отзыва пользователем с ID={}", userId);
        return itemService.saveComment(itemId, userId, commentRequestDto.getText());
    }
}
