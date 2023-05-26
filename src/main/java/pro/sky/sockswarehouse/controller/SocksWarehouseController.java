package pro.sky.sockswarehouse.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.sockswarehouse.model.Color;
import pro.sky.sockswarehouse.model.Size;
import pro.sky.sockswarehouse.model.Sock;
import pro.sky.sockswarehouse.service.impl.SocksServiceImpl;
import pro.sky.sockswarehouse.util.SockNotFoundException;
import pro.sky.sockswarehouse.util.SocksErrorResponse;

import java.util.Map;


@Slf4j
@Data
@RestController
@RequestMapping("/api/socks")
public class SocksWarehouseController {

    private final SocksServiceImpl socksService;

    public SocksWarehouseController(SocksServiceImpl socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> socksIn(@RequestParam Color color, Size size, int cottonPart, int quantity) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);

        socksService.socksIn(sock, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> getQuantity(@RequestParam Color color, Size size, int cottonPart) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);
        return ResponseEntity.ok().body(socksService.getQuantity(sock));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<Sock, Integer>> getAllAvailableSocks() {
        return ResponseEntity.ok().body(socksService.getAllAvailableSocks());
    }

    @GetMapping("/cottonMax")
    public ResponseEntity<Integer> getQuantityByCottonPartMax(@RequestParam Color color, Size size, int cottonPart) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);
        return ResponseEntity.ok().body(socksService.getQuantityByCottonPartMax(sock));
    }

    @GetMapping("/cottonMin")
    public ResponseEntity<Integer> getQuantityByCottonPartMin(@RequestParam Color color, Size size, int cottonPart) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);
        return ResponseEntity.ok().body(socksService.getQuantityByCottonPartMin(sock));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> socksOut(@RequestParam Color color, Size size, int cottonPart, int quantity) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);
        socksService.socksOut(sock, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> socksUtilized(@RequestParam Color color, Size size, int cottonPart, Integer quantity) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setSize(size);
        sock.setCottonPart(cottonPart);
        socksService.socksUtilized(sock, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/out")
    public ResponseEntity<Map<Sock, Integer>> getOutSocks() {
        return ResponseEntity.ok().body(socksService.getOutSocks());
    }

    @GetMapping("/utilized")
    public ResponseEntity<Map<Sock, Integer>> getUtilizedSocks() {
        return ResponseEntity.ok().body(socksService.getUtilizedSocks());
    }

    @ExceptionHandler
    private ResponseEntity<SocksErrorResponse> handleException(SockNotFoundException  e) {
        log.error(e.getMessage(), e);
        SocksErrorResponse response = new SocksErrorResponse("Носки с такими параметрами не найдены");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
