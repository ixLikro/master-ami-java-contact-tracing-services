package de.ami.team1.pojo;

/**
 * Pojoclass for sending a generator task to the data generator
 */
public class GenTaskPojo {
    private int amountDataPointPerUsers;
    private int users;
    private int newUsers;

    public GenTaskPojo() {

    }

    /**
     * Constructor for GenTaskPojo
     *
     * @param amountDataPointPerUsers amount of datapoints, which shall be created by each user
     * @param users                   amount of users to be tested
     * @param newUsers       amount of users, which shall be newly created
     */
    public GenTaskPojo(int amountDataPointPerUsers, int users, int newUsers) {
        this.amountDataPointPerUsers = amountDataPointPerUsers;
        this.users = users;
        this.newUsers = newUsers;
    }

    //getter and setter
    public int getAmountDataPointPerUsers() {
        return amountDataPointPerUsers;
    }

    public void setAmountDataPointPerUsers(int amountDataPointPerUsers) {
        this.amountDataPointPerUsers = amountDataPointPerUsers;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }
}
