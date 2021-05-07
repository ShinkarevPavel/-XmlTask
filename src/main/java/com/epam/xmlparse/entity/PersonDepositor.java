package main.java.com.epam.xmlparse.entity;

public class PersonDepositor extends AbstractDepositor{
    private int age;

    public PersonDepositor(){

    }

    public PersonDepositor(String name, String sureName, int age) {
        super(name, sureName);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!(o instanceof AbstractDepositor)){
            return false;
        }
        if (!super.equals(o)) return false;
        PersonDepositor that = (PersonDepositor) o;
        return age == that.age;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = result * prime + super.hashCode();
        result = result * prime + this.age;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("PersonDepositor{")
                .append(super.toString())
                .append(" age=")
                .append(this.age)
                .append("};")
                .toString();
    }
}
