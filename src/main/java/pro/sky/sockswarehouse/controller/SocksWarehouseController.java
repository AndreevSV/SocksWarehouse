package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.sky.sockswarehouse.model.Color;
import pro.sky.sockswarehouse.model.Size;
import pro.sky.sockswarehouse.model.Sock;
import pro.sky.sockswarehouse.service.impl.SocksServiceImpl;
import pro.sky.sockswarehouse.util.SockNotFoundException;
import pro.sky.sockswarehouse.util.SocksErrorResponse;

import javax.validation.Valid;
import java.util.Map;


@Slf4j
@Data
@Validated
@RestController
@RequestMapping("/api/socks")
@Tag(name = "Контроллер учета носков",
        description = "Позволяет регистрировать и отпускать носки, списывать, искать по составу шерсти, " +
                "выводить остатки, выводить, что списано, выводить, что утилизировано")
public class SocksWarehouseController {

    private final SocksServiceImpl socksService;

    public SocksWarehouseController(SocksServiceImpl socksService) {
        this.socksService = socksService;
    }

    @Operation(summary = "Регистрация новой партии носков",
            description = "В параметрах запроса поставляется цвет, размер, количество хлопка в составе, количество носков и " +
                    "производится сохранение носков в Map, где ключом служит носок, а значением - количество")
    @PostMapping
    public ResponseEntity<HttpStatus> socksIn(@RequestParam
                                              @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                              @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                              @Valid @Parameter(description = "Доля хлопка в носках. Целое число") int cottonPart,
                                              @Valid @Parameter(description = "Количество носков. Целое число") int quantity) {
        socksService.socksIn(createSock(color, size, cottonPart), quantity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Вывод количества носков определенных параметров",
            description = "В параметрах запроса поставляется цвет, размер, количество хлопка в составе. Вывод - количество носков")
    @GetMapping
    public ResponseEntity<Integer> getQuantity(@RequestParam
                                               @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                               @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                               @Valid @Parameter(description = "Доля хлопка в носках. Целое число") int cottonPart) {
            return ResponseEntity.ok().body(socksService.getQuantity(createSock(color, size, cottonPart)));
    }

    @Operation(summary = "Вывод всех имеющихся в наличии носков",
            description = "Вывод в виде Map носок : количество, обращение на эндпоинт api/socks/all")
    @GetMapping("/all")
    public ResponseEntity<Map<Sock, Integer>> getAllAvailableSocks() {
        return ResponseEntity.ok().body(socksService.getAllAvailableSocks());
    }

    @Operation(summary = "Вывод количества носков с долей шерсти свыше заданного",
            description = "Поставляются параметры носка, выводится количество всех носков с долей хлопка равного" +
                    "или выше поставленного в запросе; запрос на эндпоинт - api/socks/cottonMax")
    @GetMapping("/cottonMax")
    public ResponseEntity<Integer> getQuantityByCottonPartMax(@RequestParam
                                                              @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                                              @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                                              @Valid @Parameter(description = "Максимальная доля хлопка в носках. Целое число") int cottonPart) {
        return ResponseEntity.ok().body(socksService.getQuantityByCottonPartMax(createSock(color, size, cottonPart)));
    }

    @Operation(summary = "Вывод количества носков с долей шерсти ниже заданного",
            description = "Поставляются параметры носка, выводится количество всех носков с долей хлопка равного" +
                    "или меньше поставленного в запросе; запрос на эндпоинт - api/socks/cottonMin")
    @GetMapping("/cottonMin")
    public ResponseEntity<Integer> getQuantityByCottonPartMin(@RequestParam
                                                              @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                                              @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                                              @Valid @Parameter(description = "Минимальная доля хлопка в носках. Целое число") int cottonPart) {
        return ResponseEntity.ok().body(socksService.getQuantityByCottonPartMin(createSock(color, size, cottonPart)));
    }

    @Operation(summary = "Отпуск носков со склада",
            description = "В параметрах запроса поставляется цвет, размер, количество хлопка в составе, количество носков и " +
                    "производится уменьшение количества носков из Map на поставленное в запросе количество")
    @PutMapping
    public ResponseEntity<HttpStatus> socksOut(@RequestParam
                                               @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                               @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                               @Valid @Parameter(description = "Доля хлопка в носках. Целое число") int cottonPart,
                                               @Valid @Parameter(description = "Количество носков. Целое число") int quantity) {
        socksService.socksOut(createSock(color, size, cottonPart), quantity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Списание бракованных носков", description = "В параметрах запроса поставляется цвет, размер, " +
            "количество хлопка в составе, количество носков, которые нужно списать со склада, и производится их списание " +
            "и добавление в Map списанных носков")
    @DeleteMapping
    public ResponseEntity<HttpStatus> socksUtilizing(@RequestParam
                                                     @Valid @Parameter(description = "Цвет носков. Фиксированный список") Color color,
                                                     @Valid @Parameter(description = "Размер носков. Фискированный список") Size size,
                                                     @Valid @Parameter(description = "Доля хлопка в носках. Целое число") int cottonPart,
                                                     @Valid @Parameter(description = "Количество носков. Целое число") int quantity) {
        socksService.socksUtilized(createSock(color, size, cottonPart), quantity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Вывод списка отпущенных со склада носков",
            description = "Вывод списка отпущенных со склада носков. Запрос без параметров на эндпоинт api/socks/out")
    @GetMapping("/out")
    public ResponseEntity<Map<Sock, Integer>> getOutSocks() {
        return ResponseEntity.ok().body(socksService.getOutSocks());
    }

    @Operation(summary = "Вывод списка списанных (утилизированных) носков",
            description = "Вывод списка утилизированных носков. Запрос без параметров на эндпоинт api/socks/utilized")
    @GetMapping("/utilized")
    public ResponseEntity<Map<Sock, Integer>> getUtilizedSocks() {
        return ResponseEntity.ok().body(socksService.getUtilizedSocks());
    }


    @ExceptionHandler(SockNotFoundException.class)
    public ResponseEntity<SocksErrorResponse> handleException(SockNotFoundException e) {
        log.error(e.getMessage(), e);
        SocksErrorResponse response = new SocksErrorResponse("Носки с такими параметрами не найдены");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public Sock createSock(Color color, Size size, int cottonPart) {
        return new Sock(color, size, cottonPart);
    }

}
