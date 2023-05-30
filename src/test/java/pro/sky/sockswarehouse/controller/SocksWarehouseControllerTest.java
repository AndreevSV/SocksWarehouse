package pro.sky.sockswarehouse.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.sockswarehouse.model.Color;
import pro.sky.sockswarehouse.model.Size;
import pro.sky.sockswarehouse.model.Sock;
import pro.sky.sockswarehouse.service.impl.SocksServiceImpl;
import pro.sky.sockswarehouse.util.SockNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocksWarehouseControllerTest {
    @Mock
    private SocksServiceImpl socksServiceMock;
    @InjectMocks
    private SocksWarehouseController socksWarehouseController;


    final Sock EXPECTED_SOCK = new Sock(Color.black, Size.s36, 80);
    final int EXPECTED_QUANTITY = 100;

    final Sock WRONG_SOCK = new Sock(Color.red, Size.s37, 100);

    private Map<Sock, Integer> getExpectedMap() {

        Sock sock1 = new Sock(Color.black, Size.s36, 80);
        Sock sock2 = new Sock(Color.red, Size.s425, 50);
        Sock sock3 = new Sock(Color.blue, Size.s425, 100);

        Map<Sock, Integer> socks = new HashMap<>();
        socks.put(sock1, 200);
        socks.put(sock2, 100);
        socks.put(sock3, 100);

        return socks;
    }

    @Test
    public void createSockTest() {
        Sock expectedSock = new Sock(Color.black, Size.s36, 80);
        Sock sockActual = socksWarehouseController.createSock(Color.black, Size.s36, 80);
        Assertions.assertEquals(expectedSock, sockActual);
    }

    @Test
    public void socksInTest_shouldAddSockToMapOfSocks() {

        socksWarehouseController.socksIn(Color.black, Size.s36, 80, 100);

        verify(socksServiceMock).socksIn(EXPECTED_SOCK, EXPECTED_QUANTITY);
    }

    @Test
     public void getQuantityTest_shouldReturnResponseEntityWithQuantityOfSock() {

            when(socksServiceMock.getQuantity(EXPECTED_SOCK)).thenReturn(EXPECTED_QUANTITY);

            ResponseEntity<Integer> response = socksWarehouseController.getQuantity(Color.black, Size.s36, 80);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(EXPECTED_QUANTITY, response.getBody());
    }

    @Test
    public void getQuantityTest_shouldReturnResponseEntityWithSockNotFoundException() {

        when(socksServiceMock.getQuantity(WRONG_SOCK)).thenThrow(new SockNotFoundException());
        Assertions.assertThrows(SockNotFoundException.class, () -> socksWarehouseController.getQuantity(Color.red, Size.s37, 100));


//        when(socksServiceMock.getQuantity(WRONG_SOCK)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build().getStatusCodeValue());
//        ResponseEntity<Integer> response = socksWarehouseController.getQuantity(Color.red, Size.s37, 100);
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCodeValue());


    }

    @Test
    public void getAllAvailableSocksTest_shouldReturnMapOfSocks() {
        when(socksServiceMock.getAllAvailableSocks()).thenReturn(getExpectedMap());

        ResponseEntity<Map<Sock, Integer>> response = socksWarehouseController.getAllAvailableSocks();

        Assertions.assertEquals(getExpectedMap(), response.getBody());
        verify(socksServiceMock).getAllAvailableSocks();
    }

    @Test
    public void getQuantityByCottonPartMaxTest_shouldReturnQuantityOfSocksWithCottonPartMoreThenGiven() {
        when(socksServiceMock.getQuantityByCottonPartMax(EXPECTED_SOCK)).thenReturn(EXPECTED_QUANTITY);

        ResponseEntity<Integer> quantityByCottonPartMax = socksWarehouseController.getQuantityByCottonPartMax(Color.black, Size.s36, 80);

        Assertions.assertEquals(EXPECTED_QUANTITY, quantityByCottonPartMax.getBody());

    }

    @Test
    public void getQuantityByCottonPartMinTest_shouldReturnQuantityOfSocksWithCottonPartMoreThenGiven() {

        when(socksServiceMock.getQuantityByCottonPartMin(EXPECTED_SOCK)).thenReturn(EXPECTED_QUANTITY);

        ResponseEntity<Integer> quantityByCottonPartMin = socksWarehouseController.getQuantityByCottonPartMin(Color.black, Size.s36, 80);

        Assertions.assertEquals(EXPECTED_QUANTITY, quantityByCottonPartMin.getBody());
        verify(socksServiceMock).getQuantityByCottonPartMin(EXPECTED_SOCK);

    }

    @Test
    public void socksOutTest_shouldRerurnHttpStatusOk() {

        socksWarehouseController.socksOut(Color.black, Size.s36, 80, EXPECTED_QUANTITY);

        verify(socksServiceMock).socksOut(EXPECTED_SOCK, EXPECTED_QUANTITY);
    }

    @Test
    public void socksUtilizingTest_shouldRerurnHttpStatusOk() {
        socksWarehouseController.socksUtilizing(Color.black, Size.s36, 80, EXPECTED_QUANTITY);

        verify(socksServiceMock).socksUtilized(EXPECTED_SOCK, EXPECTED_QUANTITY);
    }

    @Test
    public void getOutSocksTest_shouldReturnResponseEntityWithMapSockInteger() {
        when(socksServiceMock.getOutSocks()).thenReturn(getExpectedMap());

        ResponseEntity<Map<Sock, Integer>> response = socksWarehouseController.getOutSocks();

        Assertions.assertEquals(getExpectedMap(), response.getBody());

    }

    @Test
    public void getUtilizedSocksTest_shouldReturnResponseEntityWithMapSockInteger() {
        when(socksServiceMock.getUtilizedSocks()).thenReturn(getExpectedMap());

        ResponseEntity<Map<Sock, Integer>> response = socksWarehouseController.getUtilizedSocks();

        Assertions.assertEquals(getExpectedMap(), response.getBody());

    }
}
