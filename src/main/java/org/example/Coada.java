package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
public class Coada {
    private final LinkedList<Client> coada;
    private final AtomicInteger waitingPeriod;
    private final AtomicInteger arrive;
    private final ExecutorService executorService;
    public Coada() {
        coada = new LinkedList<>();
        waitingPeriod = new AtomicInteger(0);
        arrive = new AtomicInteger(0);
        executorService = Executors.newSingleThreadExecutor();
    }
    public void processQueue() {
        ExecutorService executor = Executors.newFixedThreadPool(coada.size());
        for (Client client : coada) {
            executor.execute(() -> {
                try {
                    Thread.sleep(client.getDurata() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                removeClient(client);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public void addClient(Client client) {
        synchronized (this) {
            coada.addLast(client);
            waitingPeriod.addAndGet(client.getDurata());
            arrive.addAndGet(client.getSosire());

        }
        executorService.submit(() -> {
            processQueue();
        });
    }
    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }
    public void removeClient(Client client) {
        coada.remove(client);
    }
    public static void generateClients(int numClients, int numQueues, int aa, int bb, int cc, int dd, int ee) {
        List<Coada> queues = new ArrayList<>();
        for (int i = 0; i < numQueues; i++) {
            queues.add(new Coada());
        }
        Coada waiting = new Coada();
        try {
            FileWriter writer = new FileWriter("clients.txt", false);
            for (int i = 0; i < numClients; i++) {
                Client client1 = RandomG.generateClient(aa, bb, cc, dd, ee);
                waiting.addClient(client1);
            }

            for (int k = 0; k < aa; k++) {
                List<Client> clientsToBeAdded = new ArrayList<>();
                for (Client waitingClient1 : waiting.getClients()) {
                    if (waitingClient1.getSosire() == k) {
                        clientsToBeAdded.add(waitingClient1);
                    }
                }
                if (!clientsToBeAdded.isEmpty()) {
                    queues.sort(Comparator.comparingInt(Coada::getWaitingPeriod));
                    int i = 0;
                    for (Client nextClient : clientsToBeAdded) {
                        Coada shortestQueue = queues.get(i);
                        Client client = new Client(nextClient.getId(), nextClient.getSosire(), nextClient.getDurata());
                        shortestQueue.addClient(client);
                        waiting.removeClient(nextClient);
                        i = (i + 1) % numQueues;
                    }
                }

                writer.write("Time:" + k + "\n");
                writer.write("Waiting Queue:\n");
                for (Client waitingClient : waiting.getClients()) {
                    writer.write("(" + waitingClient.getId() + "," + waitingClient.getSosire() +
                            "," + waitingClient.getDurata() + ")\n");
                }
                for (Coada queue : queues) {
                    writer.write("Queue " + queues.indexOf(queue) + ":\n");
                    for (Client client1 : queue.getClients()) {
                        writer.write("(" + client1.getId() + "," + client1.getSosire() +
                                "," + client1.getDurata() + ")\n");
                    }
                }
                List<Client> clientsToBeRemoved = new ArrayList<>();
                for (Coada queue : queues) {
                    for (Client client : queue.getClients()) {
                        if (k >= client.getSosire() + client.getDurata()) {
                            clientsToBeRemoved.add(client);
                        }
                    }
                    for (Client client : clientsToBeRemoved) {
                        queue.removeClient(client);
                    }
                    clientsToBeRemoved.clear();
                }
            }

            writer.close();
            Thread.sleep(1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Client> getClients() {
        return coada;
    }

}