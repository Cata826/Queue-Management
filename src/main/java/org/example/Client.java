package org.example;
public class Client implements Runnable {
    private int id;
    private int sosire;
    private int durata;
    public Client(int id, int sosire, int durata) {
        this.id = id;
        this.sosire = sosire;
        this.durata = durata;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSosire() {
        return sosire;
    }
    public void setSosire(int sosire) {
        this.sosire = sosire;
    }
    public int getDurata() {
        return durata;
    }
    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sosire);
            Thread.sleep(durata);
        } catch (InterruptedException e) {

        }
    }
}
