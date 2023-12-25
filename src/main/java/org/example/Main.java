package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.csv.CSVFormat.RFC4180;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Reader in = new FileReader(new File(Objects.requireNonNull(Main.class.getResource("/PGA.csv")).toURI()));
        CSVFormat csvFormat = CSVFormat.Builder.create(RFC4180).setHeader().setSkipHeaderRecord(true).build();
        CSVParser parser = csvFormat.parse(in);
        List<PGAStatRecord> pgaStatRecordList = parser.stream().map(record-> PGAStatRecord.builder()
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
                .toList();
        System.out.println(pgaStatRecordList.size());
        System.out.println(parser.getHeaderNames());
    }
}

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
