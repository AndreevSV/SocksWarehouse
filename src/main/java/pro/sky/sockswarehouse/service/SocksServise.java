package pro.sky.sockswarehouse.service;

//import pro.sky.sockswarehouse.dto.SocksDTO;
import pro.sky.sockswarehouse.model.Sock;

import java.util.Map;

public interface SocksServise {

    void socksIn(Sock sock, Integer quantity);

    Integer getQuantity(Sock sock);

    Map<Sock, Integer> getAllAvailableSocks();

    Integer getQuantityByCottonPartMin(Sock sock);

    Integer getQuantityByCottonPartMax(Sock sock);

    void socksOut(Sock sock, Integer quantity);

    void socksUtilized(Sock sock, Integer quantity);

    Map<Sock, Integer> getOutSocks();

    Map<Sock, Integer> getUtilizedSocks();
}
