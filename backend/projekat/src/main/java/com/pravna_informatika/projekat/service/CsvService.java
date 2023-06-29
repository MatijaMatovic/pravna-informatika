package com.pravna_informatika.projekat.service;

import com.pravna_informatika.projekat.model.CaseDescription;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseBaseFilter;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Connector;
import es.ucm.fdi.gaia.jcolibri.exception.InitializingException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CsvService implements Connector {
    @Override
    public void initFromXMLfile(URL file) throws InitializingException {

    }

    @Override
    public void close() {

    }

    @Override
    public void storeCases(Collection<CBRCase> cases) {

    }

    @Override
    public void deleteCases(Collection<CBRCase> cases) {

    }

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        List<CBRCase> cases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/home/matija/programi/Pravna informatika/Nas projekat/backend/projekat/src/main/resources/static/metadata.txt"))))) {
            String line = "";
            int case_count = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || (line.length() == 0))
                    continue;
                String[] values = line.split(";");

                CBRCase cbrCase = new CBRCase();

                CaseDescription caseDescription = new CaseDescription();
                caseDescription.setId(++case_count);
                caseDescription.setPoslovniBroj(values[0].replace("K", "presuda"));
                caseDescription.setSupstanca(values[2]);
                caseDescription.setKrivicnoDelo(values[7]);
                caseDescription.setKazna(values[6]);
                caseDescription.setSud(values[5]);

                cbrCase.setDescription(caseDescription);
                cases.add(cbrCase);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return cases;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter filter) {
        return null;
    }
}
