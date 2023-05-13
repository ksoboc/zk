package com.zk.onlinegame.entities;

public record Clan(int numberOfPlayers, int points) {

    @Override
    public String toString() {
        return "{" +
                "numberOfPlayers=" + numberOfPlayers +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clan clan = (Clan) o;
        return numberOfPlayers == clan.numberOfPlayers && points == clan.points;
    }

}
