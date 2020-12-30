package com.AccountsService.Server;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;
import org.javamoney.moneta.Money;

@Entity
public class Transfer {

	@Id
	@GeneratedValue
	
	private int id;

	private int sendingAccountid;
	private int receivingAccountid;
	// @Lob
	private BigDecimal money;
	private String status;

	public Transfer() {
	}

	public Transfer(int id2, int sendingAccountid, int receivingAccountid, BigDecimal money, String status) {

		this.id = id2;
		this.sendingAccountid = sendingAccountid;
		this.receivingAccountid = receivingAccountid;
		this.money = money;
		this.status = status;
	}

	public int getReceivingAccountid() {
		return receivingAccountid;
	}

	public void setReceivingAccountid(int receivingAccountid) {
		this.receivingAccountid = receivingAccountid;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getStatus() {
		return status;

	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSendingAccountid() {
		return sendingAccountid;
	}

	public void setSendingAccountid(int sendingAccountid) {
		this.sendingAccountid = sendingAccountid;
	}

}
