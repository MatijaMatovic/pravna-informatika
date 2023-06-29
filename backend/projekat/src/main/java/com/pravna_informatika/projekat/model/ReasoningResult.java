package com.pravna_informatika.projekat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReasoningResult {

    private String appliedProvisions = "Nema informacija o primenjenim zakonskim odredbama.";
    private List<String> similarCases = new ArrayList<>(List.of("Nema informacija o slicnim slucajevima."));
}
