package pro.sky.sockswarehouse.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pro.sky.sockswarehouse.model.Sock;
import pro.sky.sockswarehouse.util.SockNotFoundException;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SocksRepository {

    private final Map<Sock, Integer> socks = new HashMap<>();

    private final Map<Sock, Integer> outSocks = new HashMap<>();

    private final Map<Sock, Integer> utilizedSocks = new HashMap<>();

    public void socksIn(Sock sock, Integer quantity) {
        if (socks.containsKey(sock)) {
            int increasedQuantity = socks.get(sock) + quantity;
            socks.put(sock, increasedQuantity);
        } else {
            socks.put(sock, quantity);
        }
    }

    public Integer getQuantity(Sock sock) {
        if (socks.containsKey(sock)) {
            return socks.get(sock);
        } else {
            throw new SockNotFoundException();
        }
    }

    public Map<Sock, Integer> getAllAvailableSocks () {
        return socks;
    }

    public Integer getQuantityByCottonPartMin(Sock sock) {
        int quantity = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (entry.getKey().compareTo(sock) <= 0) {
                quantity += entry.getValue();
            }
        }
        return quantity;
    }
    public Integer getQuantityByCottonPartMax(Sock sock) {
        int quantity = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (entry.getKey().compareTo(sock) >= 0) {
                quantity += entry.getValue();
            }
        }
        return quantity;
    }

    public void socksOut(Sock sock, Integer quantity) {
        if (socks.containsKey(sock)) {
            if (socks.get(sock) >= quantity) {
                int decreasedQuantity = socks.get(sock) - quantity;
                socks.put(sock, decreasedQuantity);
                if (outSocks.containsKey(sock)) {
                    int increasedQuantity = outSocks.get(sock) + quantity;
                    socks.put(sock, increasedQuantity);
                } else {
                    outSocks.put(sock, quantity);
                }
            } else {
                socks.put(sock, 0);
                int outSocksQuantity = socks.get(sock);
                outSocks.put(sock, outSocksQuantity);
            }
        } else {
            throw new SockNotFoundException();
        }
    }

    public void socksUtilized(Sock sock, Integer quantity) {
        if (socks.containsKey(sock)) {
            if (socks.get(sock) >= quantity) {
                int decreasedQuantity = socks.get(sock) - quantity;
                socks.put(sock, decreasedQuantity);
                if (outSocks.containsKey(sock)) {
                    int increasedQuantity = outSocks.get(sock) + quantity;
                    socks.put(sock, increasedQuantity);
                } else {
                    utilizedSocks.put(sock, quantity);
                }
            } else {
                socks.put(sock, 0);
                int outSocksQuantity = socks.get(sock);
                utilizedSocks.put(sock, outSocksQuantity);
            }
        } else {
            throw new SockNotFoundException();
        }
    }

    public Map<Sock, Integer> getOutSocks() {
        return outSocks;
    }

    public Map<Sock, Integer> getUtilizedSocks() {
        return utilizedSocks;
    }
}
