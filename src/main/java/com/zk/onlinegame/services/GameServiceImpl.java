package com.zk.onlinegame.services;

import com.zk.onlinegame.entities.Clan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private final Comparator<Clan> clanComparator =
            Comparator.comparingDouble(Clan::points).reversed()
                    .thenComparing(Clan::numberOfPlayers);
    @Override
    public List<List<Clan>> calculate(final int maxPlayersPerGroup, final List<Clan> clans) {
        logger.info("calculate called");

        PriorityQueue<Clan> priorityQueue = new PriorityQueue<>(clanComparator);
        priorityQueue.addAll(clans);

        List<List<Clan>> groupsOfClans = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            List<Clan> group = new ArrayList<>();
            int totalNrOfPlayers = 0;
            List<Clan> toBeRestored = new ArrayList<>();
            while (!priorityQueue.isEmpty() && totalNrOfPlayers < maxPlayersPerGroup) {
                var clan = priorityQueue.poll();
                if (clan.numberOfPlayers() + totalNrOfPlayers <= maxPlayersPerGroup) {
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
