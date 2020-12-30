package com.AccountsService.Server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.eclipse.persistence.sessions.serializers.JavaSerializer;
import org.javamoney.moneta.Money;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Currency currency;
	@Lob
	private Money money;
	private boolean treasury;

	public Account() {
	}

	public Account(int id, String name, Currency currency, Money money, boolean treasury) {
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

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(currency, other.currency) && id == other.id && Objects.equals(money, other.money)
				&& Objects.equals(name, other.name) && treasury == other.treasury;
	}

}
