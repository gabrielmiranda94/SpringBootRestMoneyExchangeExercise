package com.AccountsService.Server;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class AccountAux {

	@Id
	@GeneratedValue
	private int id;

	private String name;
	// private Currency currency;
	private Currency currency;

	// private Money money;
	@Lob
	// @JsonProperty("amountOfMoney")

	private BigDecimal money;

	private boolean treasury;

	public AccountAux() {
	}

	public AccountAux(int id, String name, Currency currency, BigDecimal money, boolean treasury) {
		this.id = id;
		this.name = name;
		this.currency = currency;
		this.money = money;
		this.treasury = treasury;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public boolean isTreasury() {
		return treasury;
	}

	public void setTreasury(boolean treasury) {
		this.treasury = treasury;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, id, money, name, treasury);
	}

}
