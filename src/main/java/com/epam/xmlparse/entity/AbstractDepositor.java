package main.java.com.epam.xmlparse.entity;

public abstract class AbstractDepositor {
    private String name;
    private String sureName;

    public AbstractDepositor(){}

    public AbstractDepositor(String name, String sureName) {
        this.name = name;
        this.sureName = sureName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
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
        AbstractDepositor that = (AbstractDepositor) o;
        return name.equals(that.name) &&
                sureName.equals(that.sureName);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = result * prime + this.name.hashCode();
        result = result * prime + this.sureName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("name=")
                .append(this. name)
                .append(" sureName=")
                .append(this.sureName)
                .toString();
    }
}
