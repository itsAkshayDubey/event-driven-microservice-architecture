package com.github.itsAkshayDubey.eventdrivenarchitecture.paymentservice.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {
	
	private static final long serialVersionUID = 5313493413859894403L;
	
	@Id
	private String paymentId;
	@Column
	private String orderId;

}
