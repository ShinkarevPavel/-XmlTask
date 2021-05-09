package main.java.com.epam.xmlparse.entity;

import main.java.com.epam.xmlparse.builder.type.DepositType;
import java.time.LocalDateTime;

public class Bank {
    private String name;
    private String country;
    private DepositType type;
    private AbstractDepositor depositor;
    private String account_id;
    private long amount_on_deposit;
    private double profitability;
    private LocalDateTime constraints;

    public Bank(String name, String country, DepositType type, AbstractDepositor depositor, String account_id,
                long amount_on_deposit, double profitability, LocalDateTime constraints) {
        this.name = name;
        this.country = country;
        this.type = type;
        this.depositor = depositor;
        this.account_id = account_id;
        this.amount_on_deposit = amount_on_deposit;
        this.profitability = profitability;
        this.constraints = constraints;
    }

    public Bank(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DepositType getType() {
        return type;
    }

    public void setType(DepositType type) {
        this.type = type;
    }

    public AbstractDepositor getDepositor() {
        return depositor;
    }

    public void setDepositor(AbstractDepositor depositor) {
        this.depositor = depositor;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public long getAmount_on_deposit() {
        return amount_on_deposit;
    }

    public void setAmount_on_deposit(long amount_on_deposit) {
        this.amount_on_deposit = amount_on_deposit;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public LocalDateTime getConstraints() {
        return constraints;
    }

    public void setConstraints(LocalDateTime constraints) {
        this.constraints = constraints;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return amount_on_deposit == bank.amount_on_deposit &&
                Double.compare(bank.profitability, profitability) == 0 &&
                name.equals(bank.name) &&
                country.equals(bank.country) &&
                type == bank.type &&
                depositor.equals(bank.depositor) &&
                account_id.equals(bank.account_id) &&
                constraints.equals(bank.constraints);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? this.name.hashCode() : 0);
        result = prime * result + ((this.country == null) ? this.country.hashCode() : 0);
        result = prime * result + ((this.type == null) ? this.type.hashCode() : 0);
        result = prime * result + ((this.depositor == null) ? this.depositor.hashCode() : 0);
        result = prime * result + ((this.account_id == null) ? this.account_id.hashCode() : 0);
        result = prime * result + ((this.amount_on_deposit == 0) ? (int)this.amount_on_deposit : 0);
        result = prime * result + ((this.profitability == 0) ? (int)this.profitability : 0);
        result = prime * result + ((this.constraints == null) ? this.constraints.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Bank [name=");
        stringBuilder
                .append(this.name)
                .append(", country=" + this.country)
                .append(", type=" + this.type)
                .append(", depositor=" + this.depositor.toString())
                .append(", account_id=" + this.account_id)
                .append(", amount_on_deposit=" + this.amount_on_deposit)
                .append(", profitability=" + this.profitability)
                .append(", constraints=" + this.constraints)
                .append("]");
        return stringBuilder.toString();
    }
}
