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
        Integer quantity = socksRepository.getQuantity(sock);
        if (quantity != null) {
            return quantity;
        } else {
            throw new SockNotFoundException();
        }
    }

    @Override
    public Map<Sock, Integer> getAllAvailableSocks() {
        return socksRepository.getAllAvailableSocks();
    }

    @Override
    public Integer getQuantityByCottonPartMin(Sock sock) {
        Integer quantity = socksRepository.getQuantityByCottonPartMin(sock);
        if (quantity != null) {
            return quantity;
        } else throw new SockNotFoundException();
    }

    @Override
    public Integer getQuantityByCottonPartMax(Sock sock) {
        Optional<Integer> quantity = Optional.of(socksRepository.getQuantityByCottonPartMax(sock));
        return quantity.orElseThrow(SockNotFoundException::new);
    }

    @Override
    public void socksOut(Sock sock, Integer quantity) {
        if (sock != null) {
            socksRepository.socksOut(sock, quantity);
        } else throw new SockNotFoundException();
    }

    @Override
    public void socksUtilized(Sock sock, Integer quantity) {
        if (sock != null) {
            socksRepository.socksUtilized(sock, quantity);
        } else throw new SockNotFoundException();
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
