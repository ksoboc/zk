package com.zk.onlinegame.services;

import com.zk.onlinegame.entities.Clan;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final Comparator<Clan> clanComparator =
            Comparator.comparingDouble(Clan::points).reversed()
                    .thenComparing(Clan::numberOfPlayers);
    @Override
    public List<List<Clan>> calculate(final int groupCount, final List<Clan> clans) {

        PriorityQueue<Clan> priorityQueue = new PriorityQueue<>(clanComparator);
        priorityQueue.addAll(clans);

        List<List<Clan>> groupsOfClans = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            List<Clan> group = new ArrayList<>();
            int totalNrOfPlayers = 0;
            List<Clan> toBeRestored = new ArrayList<>();
            while (!priorityQueue.isEmpty() && totalNrOfPlayers < groupCount) {
                var clan = priorityQueue.poll();
                if (clan.numberOfPlayers() + totalNrOfPlayers <= groupCount) {
                    group.add(clan);
                    totalNrOfPlayers += clan.numberOfPlayers();
                } else toBeRestored.add(clan);
            }
            priorityQueue.addAll(toBeRestored);
            groupsOfClans.add(group);
        }
        return groupsOfClans;
    }
}
