package org.example;

import org.example.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member member = new Member();
            member.setName("Chamy");
            Address address = new Address("Seoul", "Dobong-ro", "00011");
            member.setAddress(address);
            em.persist(member);

            Album album = new Album();
            album.setName("Air pot 4");
            album.setPrice(100);
            album.setStockQuantity(5);
            em.persist(album);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(album);
            orderItem.setOrderPrice(100);

            Delivery delivery = new Delivery();
            Address deliveryAddress = new Address("Seoul", "Dobong-ro", "00011");
            delivery.setAddress(deliveryAddress);
            delivery.setStatus(DeliveryStatus.READY);

            Order order = new Order();
            order.setOrderDate(new Date());
            order.setOrderStatus(OrderStatus.ORDER);
            order.setMember(member);
            order.addOrderItem(orderItem);
            order.setDelivery(delivery);
            em.persist(order);

            Order o = em.find(Order.class, order.getId());
            Member m = o.getMember();

            System.out.println("Order ID: " + o.getId());
            System.out.println("Member Name: " + m.getName());

            OrderItem oi = o.getOrderItems().get(0);
            System.out.println("Order Item ID: " + oi.getId());
            Item i = oi.getItem();
            System.out.println("Item Name: " + i.getName());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}