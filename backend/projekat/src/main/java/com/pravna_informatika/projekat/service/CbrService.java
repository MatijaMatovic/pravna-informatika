package com.pravna_informatika.projekat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pravna_informatika.projekat.model.CaseDescription;
import com.pravna_informatika.projekat.model.CaseDescriptionDTO;
import com.pravna_informatika.projekat.service.similarity.StringSimilarity;
import es.ucm.fdi.gaia.jcolibri.casebase.LinealCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.*;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CbrService implements StandardCBRApplication{

    Connector _connector;  /** Connector object */
    CBRCaseBase _caseBase;  /** CaseBase object */

    NNConfig simConfig;  /** KNN configuration */

    public List<String> start(CaseDescription caseDescription) {
        List<String> similarCases = new ArrayList<>();
        try {
            configure();

            preCycle();

            CBRQuery query = new CBRQuery();

            query.setDescription(caseDescription);

            similarCases = getSimilarCases(query);

            postCycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return similarCases;
    }


    @Override
    public void configure() throws ExecutionException {
        _connector =  new CsvService();

        _caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization

        simConfig = new NNConfig(); // KNN configuration
        simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average

        simConfig.addMapping(new Attribute("krivicnoDelo", CaseDescription.class), new StringSimilarity());
        simConfig.addMapping(new Attribute("supstanca", CaseDescription.class), new StringSimilarity());
        //simConfig.addMapping(new Attribute("sud", CaseDescription.class), new StringSimilarity());

    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        Collection<CBRCase> cases = _caseBase.getCases();

        return _caseBase;
    }

    @Override
    public void cycle(CBRQuery query) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval)
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
    }

    public List<String> getSimilarCases(CBRQuery query) throws JsonProcessingException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        List<String> similarCases = new ArrayList<>();
        for (RetrievalResult nse : eval) {

            CaseDescription caseDescriptionObj = (CaseDescription) nse.get_case().getDescription();
            String caseDescription = getJSONString(CaseDescriptionDTO.fromCaseDescription(caseDescriptionObj));
//            String caseDescription = caseDescriptionObj.toString();
//            String fullDescriptionWithSimilarity = caseDescription.substring(0, caseDescription.length()-1) + ", similarity:" + nse.getEval() + "}";
            similarCases.add(caseDescription.substring(0, caseDescription.length()-1) + ", \"similarity\": " + nse.getEval() + "}");
//            similarCases.add(fullDescriptionWithSimilarity.substring(15).replace("=", ": "));
        }
        return similarCases;
    }

    public static String getJSONString(CaseDescriptionDTO dto) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postCycle() throws ExecutionException {

    }
}
