package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.apache.commons.csv.CSVFormat.RFC4180;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Reader in = new FileReader(new File(Objects.requireNonNull(Main.class.getResource("/PGA.csv")).toURI()));
        CSVFormat csvFormat = CSVFormat.Builder.create(RFC4180).setHeader().setSkipHeaderRecord(true).build();
        CSVParser parser = csvFormat.parse(in);
//        Map<String, TreeMap<LocalDate, List<PgaStatsStrokesGained>>> res = parser.stream().map(record -> PGAStatRecord.builder()
        HashMap<String, TreeMap<LocalDate, PgaStatsStrokesGained>> res = parser.stream().map(record -> PGAStatRecord.builder()
                        .tournamentId(record.get("tournament id"))
                        .playerId(record.get("player id"))
                        .holePar(record.get("hole_par"))
                        .strokes(record.get("strokes"))
                        .nRounds(record.get("n_rounds"))
                        .madeCut(record.get("made_cut"))
                        .pos(record.get("pos"))
                        .course(record.get("course"))
                        .date(record.get("date"))
                        .purse(record.get("purse"))
                        .season(record.get("season"))
                        .finish(record.get("Finish"))
                        .sgPutt(record.get("sg_putt"))
                        .sgArg(record.get("sg_arg"))
                        .sgApp(record.get("sg_app"))
                        .sgOtt(record.get("sg_ott"))
                        .sgT2g(record.get("sg_t2g"))
                        .sgTotal(record.get("sg_total"))
                        .build())
//                .filter(pStat -> pStat.playerId.equalsIgnoreCase("4848"))
                .filter(pStat -> !pStat.finish.equalsIgnoreCase("NA"))
                .collect(Collectors.groupingBy(PGAStatRecord::getPlayerId,
                        Collectors.groupingBy(r -> LocalDate.parse(r.date), TreeMap::new,
                                Collectors.mapping(PGAStatRecord::strokedGained,
                                        Collectors.toList()
//                                        Collectors.reducing((o, o2) -> {
//                                            return o;
//                                        })
                                )
                        ))).entrySet().stream()
                .filter(stringTreeMapEntry -> {
                    boolean pass = true;
                    for( Map.Entry<LocalDate, List<PgaStatsStrokesGained>> l : stringTreeMapEntry.getValue().entrySet()) {
                        if( l.getValue().size()!= 1) {
                            pass = false;
                            break;
                        }
                    }
                    if (!pass ) {
                            for( Map.Entry<LocalDate, List<PgaStatsStrokesGained>> l : stringTreeMapEntry.getValue().entrySet()) {
                                if ( l.getValue().size()!= 1) {
                                    System.out.println("Duplicate date for [playerId:date:occurances] -> [" + stringTreeMapEntry.getKey() + ":" + l.getKey() + ":" +l.getValue().size()+"]");
                                }
                            }
                    }
                    return pass;
                })
                .collect(Collector.of(() -> new HashMap<String, TreeMap<LocalDate, PgaStatsStrokesGained>>(),
                        (m1, m2) -> {
                            m2.getValue().forEach((localDate, pgaStatsStrokesGaineds) -> {
                                if (!m1.containsKey(m2.getKey())) {
                                    m1.put(m2.getKey(), new TreeMap<>());
                                }
                                m1.get(m2.getKey()).put(localDate, pgaStatsStrokesGaineds.get(0));
                            });
                        },
                        (m1, m2) -> {
                            throw new RuntimeException("Should be nothing to combine");
                        }));

//        Collectors.mapping(wList -> {
//            Map<String, TreeMap<LocalDate, PgaStatsStrokesGained>> newres = new HashMap<>();
//            wList.getValue().forEach((localDate, pgaStatsStrokesGaineds) -> {
//                if (!newres.containsKey(wList.getKey())) {
//                    newres.put(wList.getKey(), new TreeMap<>());
//                }
//                newres.get(wList.getKey()).put(localDate, pgaStatsStrokesGaineds.get(0));
//            });
//            return newres;
//        }, null)

        Set<Map.Entry<String, TreeMap<LocalDate, PgaStatsStrokesGained>>> playerE = res.entrySet();
        Map.Entry<String, TreeMap<LocalDate, PgaStatsStrokesGained>>[] ra = playerE.toArray(new Map.Entry[0]);
        Map.Entry<String, TreeMap<LocalDate, PgaStatsStrokesGained>> player = ra[2];
        System.out.println(player.getKey());
        for(Map.Entry<LocalDate, PgaStatsStrokesGained> k: player.getValue().entrySet()) {
            System.out.println(k);
        }
        for(Map.Entry<LocalDate, PgaStatsStrokesGained> k: player.getValue().entrySet()) {
            int f = Integer.parseInt(k.getValue().finish.replace("T", "").replace("CU", "0").replace("W/D", "0"));
            System.out.println("" + f +", " + k.getValue().sgTotal);
        }
        System.out.println();

//        Set<Map.Entry<String, TreeMap<LocalDate, Optional<PgaStatsStrokesGained>>>> playerE = res.entrySet();
//        Map.Entry<String, TreeMap<LocalDate, Optional<PgaStatsStrokesGained>>>[] ra = playerE.toArray(new Map.Entry[0]);
//        Map.Entry<String, TreeMap<LocalDate, Optional<PgaStatsStrokesGained>>> player = ra[2];
//        System.out.println(player.getKey());
//        for(LocalDate k: player.getValue().keySet()) {
//            System.out.println(k);
//        }
//        System.out.println();
//        System.out.println(pgaStatRecordList.size());
//        System.out.println(parser.getHeaderNames());
    }
}

//List<PGAStatRecord> pgaStatRecordList = parser.stream().map(record-> PGAStatRecord.builder()
//                .tournamentId(record.get("tournament id"))
//                .playerId(record.get("player id"))
//                .holePar(record.get("hole_par"))
//                .strokes(record.get("strokes"))
//                .nRounds(record.get("n_rounds"))
//                .madeCut(record.get("made_cut"))
//                .pos(record.get("pos"))
//                .course(record.get("course"))
//                .date(record.get("date"))
//                .purse(record.get("purse"))
//                .season(record.get("season"))
//                .finish(record.get("Finish"))
//                .sgPutt(record.get("sg_putt"))
//                .sgArg(record.get("sg_arg"))
//                .sgApp(record.get("sg_app"))
//                .sgOtt(record.get("sg_ott"))
//                .sgT2g(record.get("sg_t2g"))
//                .sgTotal(record.get("sg_total"))
//                .build())
//        .toList();


//List<PGAStatRecord> pgaStatList = parser.stream().map(record->PGAStatRecord.builder()
//                .tournamentId(record.get("tournament id"))
//                .playerId(record.get("player id"))
//                .holePar(Integer.parseInt(record.get("hole_par")))
//                .strokes(Integer.parseInt(record.get("strokes")))
//                .nRounds(Integer.parseInt(record.get("n_rounds")))
//                .madeCut(Integer.parseInt(record.get("made_cut")))
//                .pos(Integer.parseInt(record.get("pos")))
//                .course(record.get("course"))
//                .date(LocalDate.parse(record.get("date")))
//                .purse(Integer.parseInt(record.get("purse")))
//                .season(record.get("season"))
//                .finish(record.get("Finish"))
//                .sgPutt(new BigDecimal(record.get("sg_putt")).setScale(2, RoundingMode.UNNECESSARY))
//                .sgArg(new BigDecimal(record.get("sg_arg")).setScale(2, RoundingMode.UNNECESSARY))
//                .sgApp(new BigDecimal(record.get("sg_app")).setScale(2, RoundingMode.UNNECESSARY))
//                .sgOtt(new BigDecimal(record.get("sg_ott")).setScale(2, RoundingMode.UNNECESSARY))
//                .sgT2g(new BigDecimal(record.get("sg_t2g")).setScale(2, RoundingMode.UNNECESSARY))
//                .sgTotal(new BigDecimal(record.get("sg_total")).setScale(2, RoundingMode.UNNECESSARY))
//                .build())
//        .toList();
