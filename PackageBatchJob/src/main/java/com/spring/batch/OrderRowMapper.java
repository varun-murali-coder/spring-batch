package com.spring.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrderRowMapper<T> implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

		Order o=new Order();
		o.setOrderId(rs.getLong("order_id"));
		o.setFirstName(rs.getString("first_name"));
		o.setLastName(rs.getString("last_name"));
		o.setItemId(rs.getLong("item_id"));
		o.setItemName(rs.getString("item_name"));
		o.setCost(rs.getBigDecimal("cost"));
		o.setEmail(rs.getString("email"));
		o.setPurchaseDate(rs.getDate("purchase_date"));

		
		return o;
	}

}
