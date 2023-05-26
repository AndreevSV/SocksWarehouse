package pro.sky.sockswarehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.model.Sock;
import pro.sky.sockswarehouse.repository.SocksRepository;
import pro.sky.sockswarehouse.service.SocksServise;
import pro.sky.sockswarehouse.util.SockNotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksServise {

    private final SocksRepository socksRepository;

    @Override
    public void socksIn(Sock sock, Integer quantity) {
        socksRepository.socksIn(sock, quantity);
    }

    @Override
    public Integer getQuantity(Sock sock) {
        Optional<Integer> quantity = Optional.of(socksRepository.getQuantity(sock));
        return quantity.orElseThrow(SockNotFoundException::new);
    }

    @Override
    public Map<Sock, Integer> getAllAvailableSocks() {
        return socksRepository.getAllAvailableSocks();
    }

    @Override
    public Integer getQuantityByCottonPartMin(Sock sock) {
        Optional<Integer> quantity = Optional.of(socksRepository.getQuantityByCottonPartMin(sock));
        return quantity.orElseThrow(SockNotFoundException::new);
    }

    @Override
    public Integer getQuantityByCottonPartMax(Sock sock) {
        Optional<Integer> quantity = Optional.of(socksRepository.getQuantityByCottonPartMax(sock));
        return quantity.orElseThrow(SockNotFoundException::new);
    }

    @Override
    public void socksOut(Sock sock, Integer quantity) {
        try {
            socksRepository.socksOut(sock, quantity);
        } catch (SockNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void socksUtilized(Sock sock, Integer quantity) {
        try {
        socksRepository.socksUtilized(sock, quantity);
        } catch (SockNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Map<Sock, Integer> getOutSocks() {
        return socksRepository.getOutSocks();
    }

    @Override
    public Map<Sock, Integer> getUtilizedSocks() {
        return socksRepository.getUtilizedSocks();
    }

}
