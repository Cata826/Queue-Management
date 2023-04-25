package org.example;
import java.util.Random;
public class RandomG {
    private static int id = 0;
    private static final Random random = new Random();
    public static Client generateClient(int MAX_SIMULATION_TIME,int MAX_ARRIVAL_TIME,int MIN_ARRIVAL_TIME,
                                        int MAX_PROCESSING_TIME,int MIN_PROCESSING_TIME)
    {
        int arrivalTime = random.nextInt(MAX_ARRIVAL_TIME - MIN_ARRIVAL_TIME + 1) + MIN_ARRIVAL_TIME;
        int procesingTime = random.nextInt(MAX_PROCESSING_TIME - MIN_PROCESSING_TIME + 1) + MIN_PROCESSING_TIME;
        Client client = new Client(0,0,0);
        client.setId(id++);
        client.setDurata(arrivalTime);
        client.setSosire(procesingTime);
        return client;
    }
}