package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PGAStatRecord {
    String tournamentId;
    String playerId;
    String holePar;
    String strokes;
    String nRounds;
    String number;
    String madeCut;
    String pos;
    String course;
    String date;
    String purse;
    String season;
    String finish;
    String sgPutt;
    String sgArg;
    String sgApp;
    String sgOtt;
    String sgT2g;
    String sgTotal;

    public PgaStatsStrokesGained strokedGained() {
        return PgaStatsStrokesGained.builder()
                .finish(finish)
                .sgPutt(sgPutt)
                .sgArg(sgArg)
                .sgApp(sgApp)
                .sgOtt(sgOtt)
                .sgT2g(sgT2g)
                .sgTotal(sgTotal)
                .build();
    }
}
