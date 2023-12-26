package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PgaStatsStrokesGained {
    String finish;
    String sgPutt;
    String sgArg;
    String sgApp;
    String sgOtt;
    String sgT2g;
    String sgTotal;
}
