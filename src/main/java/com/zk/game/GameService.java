package com.zk.game;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface GameService {
    List<List<Clan>> calculate(@Min(value = 1, message = "Rozmiar grupy musi być większy od 0") int groupCount,
                               @NotNull List<Clan> clans);
}
