package interfata;
import org.example.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class CalcModel {
    static final String INITIAL_VALUE = "0";
    private String m_total;
    private String m_total1;
    CalcModel() {
        reset();
    }
    public void reset() {
        m_total = "0";
        m_total1 ="0";
    }
    public static double calculateAverageSosire(Coada waiting) {
        List<Client> clients = waiting.getClients();
        int totalSosire = 0;
        for (Client client : clients) {
            totalSosire += client.getSosire();
        }
        return (double) totalSosire / clients.size();
    }
    public static double calculateAverageDurata(Coada waiting) {
        List<Client> clients = waiting.getClients();
        int totalDurata = 0;
        for (Client client : clients) {
            totalDurata += client.getDurata();
        }
        return (double) totalDurata / clients.size();
    }
    public static int findPeakHour(List<Coada> queues, Coada waiting) {
        int prima = Integer.MAX_VALUE;
        int ultima = 0;
        for (Client client : waiting.getClients()) {
            prima = Math.min(prima, client.getSosire());
            ultima = Math.max(ultima, client.getSosire() + client.getDurata());
        }
        for (Coada queue : queues) {
            for (Client client : queue.getClients()) {
                prima = Math.min(prima, client.getSosire());
                ultima = Math.max(ultima, client.getSosire() + client.getDurata());
            }
        }
        int[] clientCount = new int[ultima - prima + 1];
        for (Client client : waiting.getClients()) {
            int sosire = client.getSosire() - prima;
            int plecare = client.getSosire() + client.getDurata() - prima;
            for (int i = sosire; i <= plecare; i++) {
                clientCount[i]++;
            }
        }
        for (Coada queue : queues) {
            for (Client client : queue.getClients()) {
                int sosire = client.getSosire() - prima;
                int plecare = client.getSosire() + client.getDurata() - prima;
                for (int i = sosire; i <= plecare; i++) {
                    clientCount[i]++;
                }
            }
        }
        int peakHour = 0;
        int maxClient = 0;
        for (int i = 0; i < clientCount.length; i++) {
            if (clientCount[i] > maxClient) {
                peakHour = i + prima;
                maxClient = clientCount[i];
            }
        }
        return peakHour;
    }
    public static String generateClients2(int numClients, int numQueues, int aa, int bb, int cc, int dd, int ee) throws InterruptedException {

        List<Coada> queues = new ArrayList<>();
        for (int i = 0; i < numQueues; i++) {
            queues.add(new Coada());}
        Coada waiting = new Coada();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < numClients; i++) {
            Client client1 = RandomG.generateClient(aa, bb, cc, dd, ee);
            waiting.addClient(client1);}
        double averageSosire = calculateAverageSosire(waiting);
        output.append("Average Sosire from Waiting:").append(averageSosire).append('\n');
        double averageDurata = calculateAverageDurata(waiting);
        output.append("Average Durata from Waiting:").append(averageDurata).append('\n');
        int peakHour = findPeakHour(queues, waiting);
        output.append("Peak:").append(peakHour).append('\n');
        for (int k = 0; k < aa; k++) {
            List<Client> clientsToBeAdded = new ArrayList<>();
            for (Client waitingClient1 : waiting.getClients()) {
                if (waitingClient1.getSosire() == k) {
                    clientsToBeAdded.add(waitingClient1);}}
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
            output.append("Time:").append(k).append("\n");
            output.append("Waiting Queue:\n");
            for (Client waitingClient : waiting.getClients()) {
                output.append("(").append(waitingClient.getId()).append(",").append(waitingClient.getSosire()).append(",").append(waitingClient.getDurata()).append(")\n");
            }
            for (Coada queue : queues) {
                output.append("Queue ").append(queues.indexOf(queue)).append(":\n");
                for (Client client1 : queue.getClients()) {
                    output.append("(").append(client1.getId()).append(",").append(client1.getSosire()).append(",").append(client1.getDurata()).append(")\n");
                }
            }
            List<Client> clientsToBeRemoved = new ArrayList<>();
            for (Coada queue : queues) {
                for (Client client : queue.getClients()) {
                    if (k >= client.getSosire() + client.getDurata()) {
                        clientsToBeRemoved.add(client);
                    }}
                for (Client client : clientsToBeRemoved) {
                    queue.removeClient(client);
                }
                clientsToBeRemoved.clear();
            }
        }
         return output.toString();
    }
    public void multiplyBy(int numClients, int numQueues, int aa, int bb, int cc, int dd, int ee) {
        Coada.generateClients( numClients,  numQueues,  aa,  bb,  cc,  dd,  ee);
    }
    public void setValue(String value) {
        m_total=value;
    }
}