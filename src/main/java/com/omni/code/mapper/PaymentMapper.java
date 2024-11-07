package com.omni.code.mapper;

import com.omni.code.entity.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentMapper {


    @Insert("INSERT INTO payment( order_id, payment_method, payment_amount, payment_date) " +
            "VALUES(#{payment.orderId}, #{payment.paymentMethod}, #{payment.paymentAmount}, Now())")
    void savePayment(@Param("payment") Payment payment);

    @Select("SELECT * FROM payment WHERE order_id = #{orderId}")
    Payment findByOrderId(@Param("orderId") Integer orderId);
}

