package com.driver;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@Service
public class OrderService {
@Autowired
private OrderRepository orderRepository;
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
      orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Map<DeliveryPartner, List<Order>> orderParterMap=orderRepository.getPartnerOrderPairMap();
        DeliveryPartner deliveryPartner=orderRepository.getPartnerById(partnerId);
        Integer totalOrders=orderParterMap.get(deliveryPartner).size();
        return totalOrders;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        Map<DeliveryPartner, List<Order>> orderParterMap=orderRepository.getPartnerOrderPairMap();
        DeliveryPartner deliveryPartner=orderRepository.getPartnerById(partnerId);
        List<Order>parterOrderList=orderParterMap.getOrDefault(deliveryPartner,new ArrayList<>());
        List<String> orderIds=new ArrayList<>();
        for(Order order:parterOrderList){
            orderIds.add(order.getId());
        }
        return orderIds;
    }

    public List<String> getAllOrders() {
        Map<String,Order> orderMap=orderRepository.getOrderMap();
        List<String> allOrders=new ArrayList<>(orderMap.keySet());
        return allOrders;
    }

    public Integer getCountOfUnassignedOrders() {
        Map<String,Order> orderMap=orderRepository.getOrderMap();
        Map<DeliveryPartner, List<Order>> orderPartnerMap=orderRepository.getPartnerOrderPairMap();
        Integer assignedOrderSize=0;
        for (DeliveryPartner deliveryPartner:orderPartnerMap.keySet()){
            List<Order> listOrOrder=orderPartnerMap.get(deliveryPartner);
            assignedOrderSize+=listOrOrder.size();
        }
        Integer totalOrderSize=orderMap.size();
        return totalOrderSize-assignedOrderSize;
    }
}
