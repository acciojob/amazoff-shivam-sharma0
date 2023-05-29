package com.driver;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Map<String,List<String>> pairMap=orderRepository.getPartnerOrderPairMap();
        Optional<DeliveryPartner> deliveryPartner=orderRepository.getPartnerById(partnerId);
        if(deliveryPartner.isEmpty())return 0;

        if(pairMap.containsKey(partnerId)){
        List<String> list=pairMap.get(partnerId);
        return list.size();
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        Map<String,List<String>> pairMap=orderRepository.getPartnerOrderPairMap();
        Optional<DeliveryPartner> deliveryPartner=orderRepository.getPartnerById(partnerId);
        if(deliveryPartner.isEmpty()) return new ArrayList<>();

        if(pairMap.containsKey(partnerId)){
            List<String> list=pairMap.get(partnerId);
            return list;
        }
        return new ArrayList<>();
    }

    public List<String> getAllOrders() {
        Map<String,Order> orderMap=orderRepository.getOrderMap();
        List<String> allOrders=new ArrayList<>(orderMap.keySet());
        return allOrders;
    }

    public Integer getCountOfUnassignedOrders() {
        Map<String,Order> orderMap=orderRepository.getOrderMap();
        Map<String,String> orderPartner=orderRepository.getOrderPartner();
        return orderMap.size()-orderPartner.size();
    }
}
